package com.yj.domain;

import java.sql.Timestamp;

import com.yj.domain.base.BasicModel;

/**
 * 系统订单抓取状态
 * 
 * @author Administrator
 */
public class SystemOrderGrapStatus extends BasicModel {

	public static final String ORDER_GRAP_STATUS_TB = "淘宝";
	public static final String ORDER_GRAP_STATUS_JD = "京东";
	public static final String ORDER_GRAP_STATUS_MGJ = "蘑菇街";
	public static final String ORDER_GRAP_STATUS_ZY = "自营";
	public static final String ORDER_GRAP_STATUS_PDD = "拼多多";
	public static final String ORDER_GRAP_STATUS_SN = "苏宁";
	private Timestamp updateTime;// 同步时间
	private String orderType;// 订单类型
	private String orderStatus;// 抓取状态
	private Timestamp lastUpdateTime;// 上次同步时间

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
