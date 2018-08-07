package com.ghf.dmp.http.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghf.bean.util.XmlBeanUtil;
import com.ghf.dmp.bean.BaseReq;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpExcute {
	private static Logger log = LoggerFactory.getLogger(HttpExcute.class);
	@SuppressWarnings("unchecked")
	public static <T,Q> List<T> doRequest(BaseReq<Q> req, Class<T> respClass) throws Exception{
		CloseableHttpClient client = null!= req.getHttpclient()? req.getHttpclient(): (CloseableHttpClient) new HttpDmpClient(false).getIntance();
		//step 1
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		if(null!= req.getUrlParams()) {
			for(Entry<String,String> param : req.getUrlParams().entrySet()) {
				pairList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
		}
		if(req.getData() != null) {
			Q data = req.getData();
			Class<?> clazz = data.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String tagName = XmlBeanUtil.getTagName(field); // 标签名
				Object value = field.get(data); // 标签值
				if(!XmlBeanUtil.isEmpty(value) && XmlBeanUtil.isPojo(value.getClass())) {
					pairList.add(new BasicNameValuePair(tagName, JSONObject.fromBean(value).toString()));
				}else {
					pairList.add(new BasicNameValuePair(tagName,XmlBeanUtil.isEmpty(value)?StringUtils.EMPTY: value+""));
				}
			}
		}
		
		NameValuePair[] params = pairList.toArray(new NameValuePair[] {});
		//RequestBuilder.post(req.getUrl()).addParameters(params).build();
		//String url = req.getUrl()+URLEncoder.encode("?paraMap="+JSONObject.fromBean(req.getData()).toString());

		String url = req.getUrl()+"?paraMap="+JSONObject.fromBean(req.getData()).toString();
		url = url.replaceAll(" ", "%20");
		url = url.replaceAll("\\?", "%3F");
		url = url.replaceAll("=", "%3D");
		url = url.replaceAll("\\{", "%7B");
		url = url.replaceAll("\\}", "%7D");
		
		log.debug(url);
		HttpUriRequest query = RequestBuilder.post(req.getUrl()).addParameter("paraMap",JSONObject.fromBean(req.getData()).toString()).build();
		query.setHeaders(HttpUtils.getLoginHeader());
		
		//step 2
		CloseableHttpResponse queryResp = client.execute(query);
		//step 3
		JSONObject json = JSONObject.fromString(HttpUtils.paraseResp(queryResp));
		if(null!=client) {
			client.close();
		}
		log.debug(url);
		JSONArray list = json.getJSONArray("result");
		List<T> reqList = new ArrayList<T>();
		for (int i = 0; i < list.length(); i++) {
			JSONObject subJson = list.getJSONObject(i);
			T reqInfo = (T) JSONObject.toBean(subJson, respClass);
			reqList.add(reqInfo);
		} 
		return reqList;
	}
}
