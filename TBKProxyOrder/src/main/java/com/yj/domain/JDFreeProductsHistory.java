package com.yj.domain;

import java.util.Date;

import com.yj.domain.base.BasicModel;

public class JDFreeProductsHistory extends BasicModel {

	private String skuid;
	private Date inputTime;
	
	
	public String getSkuid() {
		return skuid;
	}
	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	
}
