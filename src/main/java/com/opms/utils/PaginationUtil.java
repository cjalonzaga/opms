package com.opms.utils;

public class PaginationUtil {
	
	private Integer offSet;
	
	private Integer limit;
	
	private String keyword;
	
	public PaginationUtil(Integer offSet , Integer limit , String keyword) {
		this.offSet = offSet;
		this.limit = limit;
		this.keyword = keyword;
	}
	
	public PaginationUtil() {}

	public Integer getOffSet() {
		return offSet;
	}

	public void setOffSet(Integer offSet) {
		this.offSet = offSet;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
