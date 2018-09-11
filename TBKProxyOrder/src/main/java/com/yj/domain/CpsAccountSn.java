package com.yj.domain;

import com.yj.domain.base.BasicModel;

/**
 * 系统绑定的苏宁联盟账号
 * 
 * @author Administrator
 * 
 */
public class CpsAccountSn extends BasicModel {

	private String userName;// 苏宁账号对应的账号名称
	private String uid;// 苏宁账号的id
	private String appKey;// 苏宁账号的appkey
	private String appSecret;// 苏宁账号的appsecret

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		if (null != userName) {
			userName = userName.trim();
		}
		this.userName = userName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		if (null != uid) {
			uid = uid.trim();
		}
		this.uid = uid;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		if (null != appKey) {
			appKey = appKey.trim();
		}
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		if (null != appSecret) {
			appSecret = appSecret.trim();
		}
		this.appSecret = appSecret;
	}

}
