package com.yj.db.model;

import com.yj.db.model.reuse.BasicModel;

public class TKLApp extends BasicModel{
	private static final long serialVersionUID = 1L;
	private String appkey;
	private String appsecret;
	
	
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
}
