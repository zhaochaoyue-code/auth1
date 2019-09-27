package com.hydsoft.springboot.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Category;

/**
 * 
 * @HttpClientUtil.java ����@HttpClientUtil.javaʵ��
 * @author ������ �������ߣ� Create Date�� 2017��7��13�� ����4:15:26 Modified By�� ������ Modified
 *         Date: 2017��7��13������4:15:26 Why & What is modified <�޸�ԭ������>
 * @version V1.0
 */
public class HttpClientUtil {

	
	private static Category logger = Category.getInstance(HttpClientUtil.class);
	
	@SuppressWarnings({ "deprecation", "resource" })
	public static String postMethod(String smsRequestUrl, List<NameValuePair> params) {
		String str = "";
		try {
			// ����HttpClientʵ��
			HttpClient httpclient = new DefaultHttpClient();
			// ����һ��post����
			HttpPost httpPost = new HttpPost(smsRequestUrl);
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				str = convertStreamToString(instreams);
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * ����get����
	 * 
	 * @param url
	 *            ·��
	 * @return
	 */
	public static String httpGet(String url) {
		// get���󷵻ؽ��
		String strResult = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// ����get����
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/** �����ͳɹ������õ���Ӧ **/
			if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				/** ��ȡ���������ع�����json�ַ������� **/
				
				strResult = EntityUtils.toString(response.getEntity(),"UTF-8");
			} else {
			}
		} catch (IOException e) {
		}
		return strResult;
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String xlxtPost(String smsRequestUrl, List<NameValuePair> params) {
		try {
			// ����һ��httpclient����
			CloseableHttpClient client = HttpClients.createDefault();
			// ����һ��post����
			HttpPost post = new HttpPost(smsRequestUrl);
			// ��װ��һ��Entity����
			StringEntity entity = new UrlEncodedFormEntity(params, "utf-8");
			// �������������
			post.setEntity(entity);
			// ��������ı���ͷ���ı���
			post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
			// ������������˷��صı���
			post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
			// ִ��post����
			CloseableHttpResponse response = client.execute(post);
			// ��ȡ��Ӧ��
			int statusCode = response.getStatusLine().getStatusCode();
			// ���巵������
			String resStr = "";
			if (statusCode == 200) {
				// ��ȡ����
				resStr = EntityUtils.toString(response.getEntity());
				// ���
				System.out.println("����ɹ�,���󷵻�����Ϊ: " + resStr);
			} else {
				// ���
				System.out.println("����ʧ��,������Ϊ: " + statusCode);
				resStr = "����ʧ��";
			}
			// �ر�response��client
			response.close();
			client.close();
			return resStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "����ʧ��";
		}

	}

	/**
	 * post����
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static String postJosnContent(String url, String Json) throws Exception {
		HttpPost method = new HttpPost(url);
		method.addHeader("Content-Type", "application/json;charset=utf-8");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String result = null;
		try {
			StringEntity entity = new StringEntity(Json, "UTF-8");// ���������������
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			method.setEntity(entity);
			HttpResponse response = httpClient.execute(method);
			logger.info("���óɹ�");
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			method.releaseConnection();
		}
		return result;

	}	
	public static void main(String[] args) throws Exception{
		/*String xx = "http://192.9.200.63:8080/qyWechat/message/pullDataInfo?departmentId=10";
		String resultStr = HttpClientUtil.httpGet(xx);
		System.err.println(resultStr);
		JSONObject result = JSONObject.fromObject(resultStr);
		System.err.println(result);
		String code = result.getString("code");*/
		
		String url ="http://192.9.200.63:8080/qyWechat/message/remindMessage?";
		String Json =  "{\"btntxt\":\"����\",\"content\":\"����ʩ\",\"explainContent\":\"��Ŀ����\",\"title\":\"���Է���\",\"userArray\":[{\"qyUserId\":\"zhaochaoyue\",\"userId\":\"4431\"}]}";
		String a = postJosnContent(url, Json);
		System.err.println(a);
	}


}
