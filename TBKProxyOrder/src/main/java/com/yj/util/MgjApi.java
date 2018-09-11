package com.yj.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.mogujie.openapi.MogujieClient;
import com.yj.domain.MogujieOrderDetails;
import com.yj.dto.mgj.MgjOrderBean;
import com.yj.dto.mgj.MgjOrderDataOrder;
import com.yj.dto.mgj.MgjOrderDataOrderDetail;

public class MgjApi {

	





	/**
	 * 获取蘑菇街订单
	 * 
	 * @param token
	 * @param start
	 * @param end
	 * @param page
	 */
	public static List<MogujieOrderDetails> getMgjOrder(String token,
			String start, String end, int page, String mgjName) {
		List<MogujieOrderDetails> resultList = new ArrayList<MogujieOrderDetails>();
		MogujieClient mogujieClient = new MogujieClient("100583",
				"458B4AA1DC43CED71171004A369DAA30",
				"https://openapi.mogujie.com/invoke");
		Map<String, String> param = new HashMap<String, String>();
		mogujieClient.setNeedEnableLogger(false);
		mogujieClient.setIgnoreSSLCheck(true);
		Map<String, String> orderParam = new HashMap<String, String>();
		orderParam.put("start", start);
		orderParam.put("end", end);
		orderParam.put("page", page + "");
		orderParam.put("pagesize", "200");
		param.put("orderInfoQuery",
				JSONObject.toJSONString(JSONObject.toJSON(orderParam)));
		try {
			String result = mogujieClient.executeStr(
					"xiaodian.cpsdata.order.list.get", param, token);
			MgjOrderBean bean = JSONObject.parseObject(result,
					MgjOrderBean.class);
			List<MgjOrderDataOrder> orders = bean.getResult().getData()
					.getOrders();
			if (null != orders && !orders.isEmpty()) {
				for (MgjOrderDataOrder or : orders) {
					if (null != or.getProducts() && !or.getProducts().isEmpty()) {
						for (MgjOrderDataOrderDetail detail : or.getProducts()) {
							MogujieOrderDetails product = new MogujieOrderDetails();
							product.setOrderNo(or.getOrderNo());
							product.setGroupId(or.getGroupId());
							product.setChannel(or.getChannel());
							product.setUpdateTime(new Date(
									or.getUpdateTime() * 1000));
							product.setExpense(or.getExpense());
							product.setPaymentType("0");
							product.setAmount(detail.getAmount());
							product.setPrice(detail.getPrice());
							product.setName(detail.getName());
							product.setCommission(detail.getCommission());
							product.setProductUrl(detail.getProductUrl());
							product.setOrderTime(new Date(
									or.getOrderTime() * 1000));
							product.setMogujieName(mgjName);
							product.setProductNo(detail.getProductNo());
							product.setCreatedDate(new Date(
									or.getOrderTime() * 1000));
							product.setPaymentStatus(or.getPaymentStatus());
							resultList.add(product);
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return resultList;
	}

	

}
