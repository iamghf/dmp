package com.ghf.dmp.http.common;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import com.ghf.dmp.cache.CookieCache;

public class HttpDmpClient {
	private HttpClient httpClient;
	
	public HttpDmpClient(boolean isLogin) {
		
	}
	
	public HttpClient getIntance() {
		if(null == this.httpClient) {
			this.httpClient = HttpClients.custom().setDefaultCookieStore(new CookieCache().getCookie()).build();
		}
		return this.httpClient;
	}
}
