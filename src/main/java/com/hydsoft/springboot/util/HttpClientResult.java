package com.hydsoft.springboot.util;

import java.io.Serializable;

/**
 * Description: ��װhttpClient��Ӧ���
 * 
 * @author JourWon
 * @date Created on 2018��4��19��
 */
public class HttpClientResult implements Serializable {

	private static final long serialVersionUID = 2168152194164783950L;

	/**
	 * ��Ӧ״̬��
	 */
	private int code;

	/**
	 * ��Ӧ����
	 */
	private String content;

	public HttpClientResult() {
	}

	public HttpClientResult(int code) {
		this.code = code;
	}

	public HttpClientResult(String content) {
		this.content = content;
	}

	public HttpClientResult(int code, String content) {
		this.code = code;
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "HttpClientResult [code=" + code + ", content=" + content + "]";
	}

}
