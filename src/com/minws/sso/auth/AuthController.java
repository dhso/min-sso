/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.minws.sso.auth;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

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
		// setAttr("blogList", Blog.me.findAll());
		render("index.jsp");
	}

	public void login() {
		setCookie("sso_state", Identities.randomBase62(10), 365 * 24 * 60 * 60, "/", ProsMap.getStrPro("sso.cookie.domain"));
		render("login.jsp");
	}

	public void qqInfo() throws ClientProtocolException, IOException {
		String qqCode = getPara("code", "");
		String qqState = getPara("state", "");
		String cookieState = getCookie("sso_state", "");
		if (!cookieState.equals(qqState)) {
			renderText("state不合法，可能受到CSRF攻击！");
		}
		String qqAppId = ProsMap.getStrPro("sso.qqauth.appId");
		String qqAppKey = ProsMap.getStrPro("sso.qqauth.appKey");
		String qqRedirectUrl = ProsMap.getStrPro("sso.qqauth.redirectUrl");
		String accessTokenUrl = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + qqAppId + "&client_secret=" + qqAppKey + "&code=" + qqCode + "&redirect_uri=" + qqRedirectUrl;
		String AccessTokenRes = AceUtils.httpGet(accessTokenUrl);
		Map<String, Object> mapres = StringUtils.getUrlParams(AccessTokenRes);
		String accessToken = mapres.get("access_token").toString();
		accessToken = (null == accessToken) ? "" : accessToken;
		String expiresInStr = mapres.get("expires_in").toString();
		expiresInStr = (null == expiresInStr) ? "-1" : expiresInStr;
		Integer expiresIn = StringUtils.toInteger(expiresInStr);
		String openId = "";
		String clientId = "";
		QQUserInfo userInfo = null;
		if (StringUtils.isNotBlank(accessToken)) {
			String openIdUrl = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
			String openIdRes = AceUtils.httpGet(openIdUrl);
			if (StringUtils.isNotBlank(openIdRes)) {
				openIdRes = StringUtils.replace(openIdRes, "callback(", "");
				openIdRes = StringUtils.replace(openIdRes, ");", "");
				JsonNode rootNode = new ObjectMapper().readTree(openIdRes);
				clientId = rootNode.path("client_id").asText();
				openId = rootNode.path("openid").asText();
			}
		}
		if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(clientId)) {
			String userInfoUrl = "https://graph.qq.com/user/get_user_info?access_token=" + accessToken + "&oauth_consumer_key=" + clientId + "&openid=" + openId;
			String userInfoRes = AceUtils.httpGet(userInfoUrl);
			userInfo = new ObjectMapper().convertValue(userInfoRes, QQUserInfo.class);
		}

		setCookie("sso_access_token", accessToken, expiresIn, "/", ProsMap.getStrPro("sso.cookie.domain"));
		if (null != userInfo && "0".equals(userInfo.getRet())) {
			setCookie("sso_nickName", userInfo.getNickname(), expiresIn, "/", ProsMap.getStrPro("sso.cookie.domain"));
		}
		render("qqInfo.jsp");
	}
}