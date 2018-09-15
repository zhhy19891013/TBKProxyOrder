package com.yj.db.model.bean.page;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.yj.db.model.reuse.BasicModel;

public class SystemUserClientConfigCountBean extends BasicModel{

	private String userName;
	private Long proxyNum;//代理数量
	private Long qunNum;//群数量
	private Long nextLevelProxyNum;//下级代理数量
	
	private Long firstProxyNum;
	private Long secondProxyNum;
	private String quDaoName;
	
	private String proxyName;
	private String wName;
	
	
	private Date createTime1;
	private Date createTime2;
	
	
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public String getwName() {
		return wName;
	}
	public void setwName(String wName) {
		this.wName = wName;
	}
	public String getQuDaoName() {
		return quDaoName;
	}
	public void setQuDaoName(String quDaoName) {
		this.quDaoName = quDaoName;
	}
	public Long getFirstProxyNum() {
		return firstProxyNum;
	}
	public void setFirstProxyNum(Long firstProxyNum) {
		this.firstProxyNum = firstProxyNum;
	}
	public Long getSecondProxyNum() {
		return secondProxyNum;
	}
	public void setSecondProxyNum(Long secondProxyNum) {
		this.secondProxyNum = secondProxyNum;
	}
	public Long getNextLevelProxyNum() {
		return nextLevelProxyNum;
	}
	public void setNextLevelProxyNum(Long nextLevelProxyNum) {
		this.nextLevelProxyNum = nextLevelProxyNum;
	}
	public Date getCreateTime1() {
		return createTime1;
	}
	public void setCreateTime1(Date createTime1) {
		this.createTime1 = createTime1;
	}
	public Date getCreateTime2() {
		return createTime2;
	}
	public void setCreateTime2(Date createTime2) {
		this.createTime2 = createTime2;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getProxyNum() {
		return proxyNum;
	}
	public void setProxyNum(Long proxyNum) {
		this.proxyNum = proxyNum;
	}
	public Long getQunNum() {
		return qunNum;
	}
	public void setQunNum(Long qunNum) {
		this.qunNum = qunNum;
	}
	public Map getExcelData() {
		Map map = new LinkedHashMap();
		map.put("代理人", getUserName());
		map.put("代理数量", getProxyNum());
		map.put("代理开群数量", getQunNum());
		
		return map;
	}
	
}
