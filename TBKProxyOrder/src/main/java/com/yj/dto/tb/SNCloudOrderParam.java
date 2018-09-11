package com.yj.dto.tb;

import java.util.List;
/**
 * tx
 */

import com.yj.domain.SuNingOrder;
import com.yj.domain.base.BasicModel;

public class SNCloudOrderParam extends BasicModel {
	private String orderCode;// 订单号，限时打折活动ID
	private String userName;

	private List<SuNingOrder> orderDetail;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public List<SuNingOrder> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<SuNingOrder> orderDetail) {
		this.orderDetail = orderDetail;
	}

}
