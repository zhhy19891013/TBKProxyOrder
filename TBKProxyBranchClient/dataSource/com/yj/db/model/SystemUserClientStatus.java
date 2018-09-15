package com.yj.db.model;

import com.yj.db.model.reuse.BasicModel;

public class SystemUserClientStatus extends BasicModel{

	private static final long serialVersionUID = 1L;
	
	private String groupBy;
	private String clientState;
	private String status;
	private String url;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public String getClientState() {
		return clientState;
	}
	public void setClientState(String clientState) {
		this.clientState = clientState;
	}
	
	
	
}
