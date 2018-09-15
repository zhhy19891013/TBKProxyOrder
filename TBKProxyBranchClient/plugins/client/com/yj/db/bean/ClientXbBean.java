package com.yj.db.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 小编推荐接口的bean
 * @author Administrator
 */
public class ClientXbBean {
	private String status;//状态
	private Date time;//休息的时间
	private String error;//错误码
	private List rows=new ArrayList();//群发内容
	private long max;//最后的时间戳
	private long total;//总记录
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
}
