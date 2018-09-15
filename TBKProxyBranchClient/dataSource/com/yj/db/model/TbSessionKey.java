package com.yj.db.model;

import com.yj.db.model.reuse.BasicModel;

/**
 * tb_session_key
 * @author Administrator
 *
 */
public class TbSessionKey extends BasicModel{

	private String tbName;  //淘宝名
	private String sessionKey; //登录返回的sessionkey
	
	
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
