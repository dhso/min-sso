/**
 * 
 */
/**
 * @author hadong
 *
 */
package com.minws.sso.frame.util;

import java.util.Map;

import com.alibaba.appengine.api.fetchurl.FetchUrlService;
import com.alibaba.appengine.api.fetchurl.FetchUrlServiceFactory;

public class AceUtils {

	/**
	 * ACE内置的HTTPclient
	 * 
	 * @param type
	 * @param url
	 * @param parameters
	 * @param charset
	 * @return
	 */
	public static String httpGet(String url) {
		FetchUrlService fetchUrlService = FetchUrlServiceFactory.getFetchUrlService();
		return fetchUrlService.get(url);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String httpPost(String url, Map parameters) {
		FetchUrlService fetchUrlService = FetchUrlServiceFactory.getFetchUrlService();
		return fetchUrlService.post(url, parameters, "utf-8");
	}
}