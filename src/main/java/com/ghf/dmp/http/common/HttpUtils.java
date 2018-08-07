package com.ghf.dmp.http.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghf.dmp.cache.ConfigCache;
import com.ghf.dmp.constant.DmpConst;

import net.sf.json.JSONObject;

public class HttpUtils {
	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
	
	public static BasicCookieStore login() {
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		String jessionId = null;
		try {
			HttpGet httpget = new HttpGet(ConfigCache.getStrValue(DmpConst.ConfigKey.CaptchaUrl));
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			HttpEntity entity = response1.getEntity();
			EntityUtils.consume(entity);

			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI(ConfigCache.getStrValue(DmpConst.ConfigKey.LoginUrl)))
					.addParameter("userName", ConfigCache.getStrValue(DmpConst.ConfigKey.UserName))
					.addParameter("password", ConfigCache.getStrValue(DmpConst.ConfigKey.PassWord)).build();
			login.setHeaders(getLoginHeader());
			// login.addHeader("Host", "");
			CloseableHttpResponse response2 = httpclient.execute(login);
			String respStr = paraseResp(response2);
			JSONObject respJson = JSONObject.fromString(respStr);
			if (respJson.getInt("code") > 0) {
				throw new Exception(respJson.getString("errorInfo"));
			}
			String setCookie = response2.getFirstHeader("Set-Cookie").getValue();
			jessionId = setCookie.substring("JSESSIONID=".length(),setCookie.indexOf(";"));
			
			log.debug(jessionId);
			response2.close();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return cookieStore;
	}
	
	public static Header[] getLoginHeader() {
		List<Header> list = new ArrayList<Header>();
		list.add(new BasicHeader("Host", "58.213.156.202:58080"));
		list.add(new BasicHeader("Accept", "text/plain,*/*;q=0.01"));
		list.add(new BasicHeader("Accept-Language", "en-GB;q=0.5"));
		String url = "http://58.213.156.202:58080/OnlineServer/FrameAction_edoPackPage4Iframe.action?&&i=44688846907338656278321365&&p=D8Bd*zqgRYLbKcF7nm8U6lNwpnH45i23NOYYGcMNRhug91soG9Zm8w";
		list.add(new BasicHeader("Referer",
				"http://58.213.156.202:58080/OnlineServer/BizQueryPageAction/showPage.action?pageTag=defectQueryPage4CRMGY&productId=8289408&pageId=294001&reportId="));
		// list.add(new BasicHeader("Referer",url));
		list.add(new BasicHeader("Connection", "keep-alive"));
		list.add(new BasicHeader("X-Requested-With", "XMLHttpRequest"));
		list.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
		list.add(new BasicHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"));
		list.add(new BasicHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36"));
		return list.toArray(new Header[0]);
	}
	
	public static String paraseResp(CloseableHttpResponse resp) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
		String inputLine;
		StringBuilder sb = new StringBuilder();
		while ((inputLine = reader.readLine()) != null) {
			sb.append(inputLine);
		}
		reader.close();
		return sb.toString();
	}
	
	public static void main(String[] args)throws Exception{
		log.debug("starting...");
		login();
	}
}
