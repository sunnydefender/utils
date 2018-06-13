package com.sky.framework.common.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.iharder.Base64;

public class HttpUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private static int timeout = 5000;

	public static String getRequest(String url, String params, String charset) throws Exception {
		String result = "";
		logger.info("REQUEST URL=[{}]", url);
		logger.info("接口请求报文: {}" , params);
		String request = url;
		if (!StringUtils.isBlank(params)) {
			request = url + "?" + params;
		}
		CloseableHttpResponse response = null;
		try {
			RequestConfig globalConfig = RequestConfig.custom()
					.setCookieSpec(CookieSpecs.IGNORE_COOKIES)
					.build();
			CloseableHttpClient httpclient = HttpClients.custom()
					.setDefaultRequestConfig(globalConfig)
					.build();
			HttpGet httpGet = new HttpGet(request);
			httpGet.setConfig(globalConfig);
			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity, charset);
				// logger.info("response: result={}" , result);
			} else {
				HttpEntity resEntity = response.getEntity();
				String error = EntityUtils.toString(resEntity, charset);
				logger.error("response: error={}" , error);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e);
		}finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}

	public static String postRequest(String url, String params, String charset)throws Exception {
		logger.info("request, url={}, params={}", url, params);
		String result = "";
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(params,charset);
			stringEntity.setContentType("application/x-www-form-urlencoded");
			Integer timeout = 150000;
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
					.setConnectionRequestTimeout(timeout).build();

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.setEntity(stringEntity);
			post.setConfig(requestConfig);
			response = httpclient.execute(post);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity, charset);
			} else {
				HttpEntity resEntity = response.getEntity();
				String error = EntityUtils.toString(resEntity, charset);
				logger.error("response: error={}" , error);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e);
		}
		finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	public static String postRequest2(String url, String params, String charset)throws Exception {
		logger.info("request, url={}, params={}", url, params);
		String result = "";
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(params);
			stringEntity.setContentType("application/json");
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
					.setConnectionRequestTimeout(timeout).build();

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.setEntity(stringEntity);
			post.setConfig(requestConfig);
			response = httpclient.execute(post);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity, charset);
				logger.info("response: result={}" , result);
			} else {
				HttpEntity resEntity = response.getEntity();
				String errorLog = EntityUtils.toString(resEntity, charset);
				logger.error("response: error={}" , errorLog);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e);
		}finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}

	public static String postRequest2(String url, String params, String charset, String userName, String password)throws Exception {
		logger.info("request, url={}, params={}", url, params);
		String result = "";
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(params);
			stringEntity.setContentType("application/json");

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
					.setConnectionRequestTimeout(timeout).build();

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.setEntity(stringEntity);
			post.setConfig(requestConfig);

			String cred = Base64.encodeBytes((userName + ":" + password).getBytes());
			//身份认证
			post.setHeader("Authorization", "Basic "+cred);

			response = httpclient.execute(post);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity, charset);
				logger.info("response: result={}" , result);
			} else {
				HttpEntity resEntity = response.getEntity();
				String errorLog = EntityUtils.toString(resEntity, charset);
				logger.error("response: error={}" , errorLog);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e);
		}finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
}
