package com.hydsoft.springboot.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * Description: httpClient������
 * 
 * @author xushijie@inca.com.cn 2018��9��25��
 * @date Created on 2018��4��19��
 */
public class HttpClientUtils {

	// �����ʽ�����ͱ����ʽͳһ��UTF-8
	private static final String ENCODING = "UTF-8";
	
	// �������ӳ�ʱʱ�䣬��λ���롣
	private static final int CONNECT_TIMEOUT = 6000;
	
	// �����ȡ���ݵĳ�ʱʱ��(����Ӧʱ��)����λ���롣
	private static final int SOCKET_TIMEOUT = 6000;

	/**
	 * ����get���󣻲�������ͷ���������
	 * 
	 * @param url �����ַ
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url) throws Exception {
		return doGet(url, null, null);
	}
	
	/**
	 * ����get���󣻴��������
	 * 
	 * @param url �����ַ
	 * @param params �����������
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url, Map<String, String> params) throws Exception {
		return doGet(url, null, params);
	}

	/**
	 * ����get���󣻴�����ͷ���������
	 * 
	 * @param url �����ַ
	 * @param headers ����ͷ����
	 * @param params �����������
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		// ����httpClient����
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// �������ʵĵ�ַ
		URIBuilder uriBuilder = new URIBuilder(url);
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue());
			}
		}

		// ����http����
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		/**
		 * setConnectTimeout���������ӳ�ʱʱ�䣬��λ���롣
		 * setConnectionRequestTimeout�����ô�connect Manager(���ӳ�)��ȡConnection
		 * ��ʱʱ�䣬��λ���롣����������¼ӵ����ԣ���ΪĿǰ�汾�ǿ��Թ������ӳصġ�
		 * setSocketTimeout�������ȡ���ݵĳ�ʱʱ��(����Ӧʱ��)����λ���롣 �������һ���ӿڣ�����ʱ�����޷��������ݣ���ֱ�ӷ����˴ε��á�
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpGet.setConfig(requestConfig);
		
		// ��������ͷ
		packageHeader(headers, httpGet);

		// ����httpResponse����
		CloseableHttpResponse httpResponse = null;

		try {
			// ִ�����󲢻����Ӧ���
			return getHttpClientResult(httpResponse, httpClient, httpGet);
		} finally {
			// �ͷ���Դ
			release(httpResponse, httpClient);
		}
	}

	/**
	 * ����post���󣻲�������ͷ���������
	 * 
	 * @param url �����ַ
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url) throws Exception {
		return doPost(url, null, null);
	}
	
	/**
	 * ����post���󣻴��������
	 * 
	 * @param url �����ַ
	 * @param params ��������
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url, Map<String, String> params) throws Exception {
		return doPost(url, null, params);
	}

	/**
	 * ����post���󣻴�����ͷ���������
	 * 
	 * @param url �����ַ
	 * @param headers ����ͷ����
	 * @param params �����������
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		// ����httpClient����
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// ����http����
		HttpPost httpPost = new HttpPost(url);
		/**
		 * setConnectTimeout���������ӳ�ʱʱ�䣬��λ���롣
		 * setConnectionRequestTimeout�����ô�connect Manager(���ӳ�)��ȡConnection
		 * ��ʱʱ�䣬��λ���롣����������¼ӵ����ԣ���ΪĿǰ�汾�ǿ��Թ������ӳصġ�
		 * setSocketTimeout�������ȡ���ݵĳ�ʱʱ��(����Ӧʱ��)����λ���롣 �������һ���ӿڣ�����ʱ�����޷��������ݣ���ֱ�ӷ����˴ε��á�
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);
		// ��������ͷ
		/*httpPost.setHeader("Cookie", "");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");*/
		packageHeader(headers, httpPost);
		
		// ��װ�������
		packageParam(params, httpPost);

		// ����httpResponse����
		CloseableHttpResponse httpResponse = null;

		try {
			// ִ�����󲢻����Ӧ���
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} finally {
			// �ͷ���Դ
			release(httpResponse, httpClient);
		}
	}

	/**
	 * ����put���󣻲����������
	 * 
	 * @param url �����ַ
	 * @param  ��������
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url) throws Exception {
		return doPut(url);
	}

	/**
	 * ����put���󣻴��������
	 * 
	 * @param url �����ַ
	 * @param params ��������
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url, Map<String, String> params) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPut.setConfig(requestConfig);
		
		packageParam(params, httpPut);

		CloseableHttpResponse httpResponse = null;

		try {
			return getHttpClientResult(httpResponse, httpClient, httpPut);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * ����delete���󣻲����������
	 * 
	 * @param url �����ַ
	 * @param params ��������
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpDelete.setConfig(requestConfig);

		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult(httpResponse, httpClient, httpDelete);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * ����delete���󣻴��������
	 * 
	 * @param url �����ַ
	 * @param params ��������
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url, Map<String, String> params) throws Exception {
		if (params == null) {
			params = new HashMap<String, String>();
		}

		params.put("_method", "delete");
		return doPost(url, params);
	}
	
	/**
	 * Description: ��װ����ͷ
	 * @param params
	 * @param httpMethod
	 */
	public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
		// ��װ����ͷ
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				// ���õ�����ͷ��HttpRequestBase������
				httpMethod.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Description: ��װ�������
	 * 
	 * @param params
	 * @param httpMethod
	 * @throws UnsupportedEncodingException
	 */
	public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
			throws UnsupportedEncodingException {
		// ��װ�������
		if (params != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			// ���õ������http������
			httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
		}
	}

	/**
	 * Description: �����Ӧ���
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @param httpMethod
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse,
			CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
		// ִ������
		httpResponse = httpClient.execute(httpMethod);

		// ��ȡ���ؽ��
		if (httpResponse != null && httpResponse.getStatusLine() != null) {
			String content = "";
			if (httpResponse.getEntity() != null) {
				content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
			}
			return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
		}
		return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * Description: �ͷ���Դ
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @throws IOException
	 */
	public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
		// �ͷ���Դ
		if (httpResponse != null) {
			httpResponse.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}

}