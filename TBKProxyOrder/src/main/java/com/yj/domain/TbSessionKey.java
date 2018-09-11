package com.yj.domain;

import com.yj.domain.base.BasicModel;

/**
 * 通过客户端上传的淘宝/蘑菇街的授权key
 * 
 * @author Administrator
 * 
 */
public class TbSessionKey extends BasicModel {
	private String tbName; // 淘宝名
	private String sessionKey; // 登录返回的sessionkey

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
