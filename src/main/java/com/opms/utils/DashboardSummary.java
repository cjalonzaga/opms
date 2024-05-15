package com.opms.utils;

public class DashboardSummary {
	private String name;
	private Double totalChecked;
	private Double totalUnchecked;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getTotalUnchecked() {
		return totalUnchecked;
	}
	public void setTotalUnchecked(Double totalUnchecked) {
		this.totalUnchecked = totalUnchecked;
	}
	public Double getTotalChecked() {
		return totalChecked;
	}
	public void setTotalChecked(Double totalChecked) {
		this.totalChecked = totalChecked;
	}
	
	@Override
	public String toString() {
		return "{"+
				"\"name\" : " + "\""+name+"\"" +","+
				"\"totalUnchecked\" : " + totalUnchecked +","+
				"\"totalChecked\" : " + totalChecked +
				"}";
	}
}
