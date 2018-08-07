package com.ghf.dmp.cache;

import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.ghf.dmp.http.common.HttpUtils;

public class CookieCache {
	private BasicCookieStore cookieStore = new BasicCookieStore();
	
	public void init() {
		/*String jessionId = HttpUtils.login();
		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",jessionId);
		cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
	    cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "58.213.156.202");
	    cookie.setAttribute(ClientCookie.PORT_ATTR, "58080");
	    cookie.setAttribute(ClientCookie.PATH_ATTR, "/OnlineServer");
		cookieStore.addCookie(cookie);*/
		cookieStore = HttpUtils.login();
	}
	
	public BasicCookieStore getCookie() {
		if(cookieStore.getCookies().isEmpty()) {
			init();
		}
		return this.cookieStore;
	}
}
