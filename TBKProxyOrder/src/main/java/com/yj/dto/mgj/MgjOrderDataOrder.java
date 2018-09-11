package com.yj.dto.mgj;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class MgjOrderDataOrder {
	private String orderNo;
	private String groupId;
	private String channel;
	private double expense;
	private long updateTime;
	private long orderTime;
	private String paymentStatus;
	private String paymentType;//付款类型
	
	
	
	
	private List<MgjOrderDataOrderDetail> products;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public double getExpense() {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}



	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(long orderTime) {
		this.orderTime = orderTime;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public List<MgjOrderDataOrderDetail> getProducts() {
		return products;
	}

	public void setProducts(List<MgjOrderDataOrderDetail> products) {
		this.products = products;
	}
	
 }
