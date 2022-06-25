package com.corporativos_smartfit.dto;

import java.util.Date;

public class ResponseCode {
	private String message;
	private String code;


	public ResponseCode() {
		this.message = "";
		this.code = "";
	}

	public ResponseCode(String code, String message) {
	this.message = message;
	this.code = code;
	}

	public String getMessage() {
	return message;
	}

	public void setMessage(String message) {
	this.message = message;
	}

	public String getCode() {
	return code;
	}

	public void setCode(String code) {
	this.code = code;
	}
}
