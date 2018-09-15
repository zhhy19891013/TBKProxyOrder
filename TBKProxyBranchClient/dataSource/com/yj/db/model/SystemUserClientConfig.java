package com.yj.db.model;

import com.yj.db.model.reuse.BasicModel;
/**
 * 客户端群发配置
 * @author Administrator
 */
public class  SystemUserClientConfig extends BasicModel {
	private static final long serialVersionUID = 1L;
	
	private String wName;//群名称
	private String pid;//pid设置
	private String userName;//用户名
	private String proxyName;//代理名称
	private long uid;
	private String groupBy;
	
	
	
	public String getwName() {
		return wName;
	}
	public void setwName(String wName) {
		this.wName = wName;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public String getWName() {
		if(null!=wName){
			wName =  wName.trim();
		}
		return wName;
	}
	public void setWName(String wName) {
		this.wName = wName;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
