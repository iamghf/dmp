package com.ghf.dmp.bean;

import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;

public abstract class BaseReq<T> {
	private String url;
	private Map<String,String> urlParams;
	private T data;
	private CloseableHttpClient httpclient;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String,String> getUrlParams() {
		return urlParams;
	}
	public void setUrlParam(Map<String,String> urlParams) {
		this.urlParams = urlParams;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public CloseableHttpClient getHttpclient() {
		return httpclient;
	}
	public void setHttpclient(CloseableHttpClient httpclient) {
		this.httpclient = httpclient;
	}
	public void setUrlParams(Map<String, String> urlParams) {
		this.urlParams = urlParams;
	}

}
