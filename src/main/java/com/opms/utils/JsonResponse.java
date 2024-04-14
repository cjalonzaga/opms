package com.opms.utils;

public class JsonResponse {
	private String message;
	private String callBackUri;
	private Boolean success;
	private Boolean error;
	
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
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
}
