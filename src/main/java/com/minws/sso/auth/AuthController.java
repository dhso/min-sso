/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.minws.sso.auth;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.minws.sso.frame.util.AceUtils;
import com.minws.sso.frame.util.Identities;
import com.minws.sso.frame.util.ProsMap;
import com.minws.sso.frame.util.StringUtils;

public class AuthController extends Controller {

	@Before(LoginInterceptor.class)
	public void index() {
		String sso_access_token = getCookie("sso_access_token", "");
		Serializable ser = AceUtils.cacheGet(sso_access_token);
		QQUserInfo qquerInfo = (null != ser) ? (QQUserInfo) ser : null;
		setAttr("qquerInfo", qquerInfo);
		render("index.jsp");
	}

	public void login() {
		String qstr = getRequest().getQueryString();
		qstr = (!StringUtils.isNotBlank(qstr)) ? "" : qstr;
		String key = Identities.uuid2();
		setAttr("sso_state", key);
		setAttr("sso_qstr", "?" + key);
		setCookie("sso_state", key, 365 * 24 * 60 * 60, "/", ProsMap.getStrPro("sso.cookie.domain"));
		AceUtils.cachePut(key, qstr, 60 * 60 * 24);
		render("login.jsp");
	}

	public void qqInfo() throws ClientProtocolException, IOException {
		String qqCode = getPara("code", "");
		String qqState = getPara("state", "");
		String cookieState = getCookie("sso_state", "");
		// CSRF攻击检测
		if (!cookieState.equals(qqState)) {
			renderText("state不合法，可能受到CSRF攻击！");
			return;
		}

		String qqAppId = ProsMap.getStrPro("sso.qqauth.appId");
		String qqAppKey = ProsMap.getStrPro("sso.qqauth.appKey");
		String qqRedirectUrl = ProsMap.getStrPro("sso.qqauth.redirectUrl") + "?" + qqState;
		String accessTokenUrl = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + qqAppId + "&client_secret=" + qqAppKey + "&code=" + qqCode + "&redirect_uri=" + qqRedirectUrl;
		String AccessTokenRes = AceUtils.httpGet(accessTokenUrl);
		if (StringUtils.isNotBlank(AccessTokenRes)) {
			Map<String, Object> mapres = StringUtils.getUrlParams(AccessTokenRes);
			Object accessTokenObj = mapres.get("access_token");
			String accessTokenStr = (null != accessTokenObj) ? accessTokenObj.toString() : "";
			Object expiresInObj = mapres.get("expires_in");
			Integer expiresInInt = (null != expiresInObj) ? StringUtils.toInteger(expiresInObj.toString()) : -1;

			String openId = "";
			String clientId = "";
			if (StringUtils.isNotBlank(accessTokenStr)) {
				String openIdUrl = "https://graph.qq.com/oauth2.0/me?access_token=" + accessTokenStr;
				String openIdRes = AceUtils.httpGet(openIdUrl);
				if (StringUtils.isNotBlank(openIdRes)) {
					openIdRes = StringUtils.replace(openIdRes, "callback(", "");
					openIdRes = StringUtils.replace(openIdRes, ");", "");
					JsonNode rootNode = new ObjectMapper().readTree(openIdRes);
					clientId = rootNode.path("client_id").asText();
					openId = rootNode.path("openid").asText();
				}
			}

			QQUserInfo userInfo = null;
			if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(clientId)) {
				String userInfoUrl = "https://graph.qq.com/user/get_user_info?access_token=" + accessTokenStr + "&oauth_consumer_key=" + clientId + "&openid=" + openId;
				String userInfoRes = AceUtils.httpGet(userInfoUrl);
				if (StringUtils.isNotBlank(userInfoRes)) {
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
					mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
					userInfo = mapper.readValue(userInfoRes, QQUserInfo.class);
					AceUtils.cachePut(accessTokenStr, userInfo, expiresInInt);
				}
			}

			setCookie("sso_access_token", accessTokenStr, expiresInInt, "/", ProsMap.getStrPro("sso.cookie.domain"));
			if (null != userInfo && userInfo.getRet() == 0) {
				setCookie("sso_nickName", URLEncoder.encode(userInfo.getNickname(), "utf-8"), expiresInInt, "/", ProsMap.getStrPro("sso.cookie.domain"));
			}
		}

		String urlKey = getRequest().getQueryString().replaceAll("&code=" + qqCode, "").replaceAll("/?code=" + qqCode, "").replaceAll("&state=" + qqState, "").replaceAll("/?state=" + qqState, "");
		String gotoUrl = "";
		if (StringUtils.isNotBlank(urlKey)) {
			Serializable urlValue = AceUtils.cacheGet(urlKey);
			gotoUrl = (null != urlValue) ? urlValue.toString().replace("goto=", "") : "";
		}

		if (StringUtils.isNotBlank(gotoUrl)) {
			redirect(gotoUrl);
		}

	}
}