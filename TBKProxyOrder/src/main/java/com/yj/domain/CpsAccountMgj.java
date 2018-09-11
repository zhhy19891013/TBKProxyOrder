package com.yj.domain;

import com.yj.domain.base.BasicModel;

/**
 * 系统绑定的蘑菇街联盟账号
 * 
 * @author 周晓斌
 * 
 */
public class CpsAccountMgj extends BasicModel {

	public static final String ACCOUNT_TYPE_CAIJI = "采集";
	public static final String ACCOUNT_TYPE_ZHUANLIAN = "转链";

	private String uid;// 蘑菇街账号的uid
	private String userName;// 蘑菇街用户名
	private String type;// 用户类型

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
