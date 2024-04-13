package com.opms.utils;

public class JsonResponse {
	private String message;
	private String callBackUri;
	
	public String getCallBackUri() {
		return callBackUri;
	}
	public void setCallBackUri(String callBackUri) {
		this.callBackUri = callBackUri;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
