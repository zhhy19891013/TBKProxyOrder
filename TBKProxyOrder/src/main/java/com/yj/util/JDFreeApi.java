package com.yj.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.cps.UnionServiceQueryOrderListWithKeyRequest;
import com.jd.open.api.sdk.response.cps.UnionServiceQueryOrderListWithKeyResponse;
import com.yj.domain.CpsAccountJd;
import com.yj.domain.JDOrderDetails;

/**
 * 京东接口相应工具类
 * 
 * @author yiju-zhhy
 * 
 */
public class JDFreeApi {
	private static final Long UNION_ID = 1000610323L;

	/**
	 * 获取京东免单订单
	 * 
	 * @return
	 */
	public static List<JDOrderDetails> queryImportOrders(String time, CpsAccountJd bind) {
		if (null == bind.getEmpowerKey()) {
			return null;
		}
		List<JDOrderDetails> orderDetails = new ArrayList<JDOrderDetails>();
		JdClient client = getJdClient();
		UnionServiceQueryOrderListWithKeyRequest request = new UnionServiceQueryOrderListWithKeyRequest();
		int page = 1;
		while (true) {
			request.setUnionId(UNION_ID);
			request.setKey(bind.getEmpowerKey());
			request.setTime(time);
			request.setPageIndex(page);
			request.setPageSize(500);
			try {
				UnionServiceQueryOrderListWithKeyResponse response = client.execute(request);
				if (response.getCode().equals("0")) {
					JSONObject result = JSON.parseObject(response.getMsg());
					JSONObject jingdong_UnionService_queryImportOrdersWithKey_responce = result
							.getJSONObject("jingdong_UnionService_queryOrderListWithKey_responce");
					String queryImportOrdersWithKey_result = jingdong_UnionService_queryImportOrdersWithKey_responce
							.getString("result");
					JSONObject queryImportOrdersWithKey_resultJson = JSON.parseObject(queryImportOrdersWithKey_result);
					if (queryImportOrdersWithKey_resultJson.getIntValue("success") == 1) {
						JSONArray jsonArray = queryImportOrdersWithKey_resultJson.getJSONArray("data");
						for (Object object : jsonArray) {
							JSONObject jobj = (JSONObject) object;
							String orderFrom = jobj.getString("orderEmt").equals("2") ? "无线" : "PC";
							String orderId = jobj.getString("orderId");
							Date orderTime = new Date(Long.valueOf(jobj.getString("orderTime")).longValue());
							JSONArray jar = JSONArray.parseArray(jobj.getString("skuList"));
							for (Object object2 : jar) {
								JSONObject jobjDetails = (JSONObject) object2;
								String unionTag = jobjDetails.getString("unionTag");
								if (null == unionTag || !unionTag.equals("00000100")) {// 非拼购订单
									continue;
								}
								JDOrderDetails jd = new JDOrderDetails();
								jd.setPlus(jobj.getString("plus"));
								jd.setSubsidyRate(jobjDetails.getDouble("subsidyRate"));
								jd.setItemId(jobjDetails.getString("skuId"));
								jd.setCommision(jobjDetails.getDouble("estimateFee"));
								jd.setOrderfrom(orderFrom);
								jd.setOrderid(orderId);
								jd.setOrdertime(orderTime);
								jd.setSubunionid(jobjDetails.getString("spId"));
								jd.setCreateTime(new Date());
								jd.setUpdateTime(jd.getCreateTime());
								jd.setCommissionRate(jobjDetails.getDouble("commissionRate"));
								jd.setAmount(jobjDetails.getInteger("skuNum"));
								jd.setProdurl("http://item.jd.com/" + jd.getItemId() + ".html");
								jd.setPrice(jobjDetails.getDouble("estimateCosPrice"));
								jd.setProductname(jobjDetails.getString("skuName"));
								jd.setCosts(jobjDetails.getDouble("estimateCosPrice"));
								jd.setMoney(jobjDetails.getDouble("estimateCosPrice"));
								Integer num = jobjDetails.getInteger("validCode");
								Integer skuReturnNum = jobjDetails.getInteger("skuReturnNum");
								Integer frozenSkuNum = jobjDetails.getInteger("frozenSkuNum");
								switch (num) {
								case 3:
									jd.setStatus("X");// 退款
									break;
								case 18:
									jd.setStatus("S");// 结算
									break;
								case 16:
									jd.setStatus("M");// 等待收货
									break;
								case 17:
									jd.setStatus("D");// 确认收货
									break;
								case 15:
									jd.setStatus("N");// 未付款
									break;
								case 13:
									jd.setStatus("Y");// 违规
									break;
								default:
									jd.setStatus("X");// 取消
									break;
								}
								if (null != skuReturnNum && skuReturnNum > 0) {
									jd.setStatus("R");
								}
								if (null != frozenSkuNum && frozenSkuNum > 0) {
									jd.setStatus("R");
								}
								if (num < 15 && jd.getPrice() <= 0) {// 赠品单子不抓
								} else {
									orderDetails.add(jd);
								}
							}
						}
					} else {
						break;
					}
				} else {
					break;
				}
			} catch (Exception e) {
				break;
			}
			page++;
		}
		return orderDetails;
	}

	/***********************************************
	 * 私有方法
	 ***********************************************/
	/**
	 * 获取京东client
	 * 
	 * @return
	 */
	private static JdClient getJdClient() {
		return new DefaultJdClient("https://api.jd.com/routerjson", "35a05c9e-a7ad-4a7a-a108-c8ffc529b50a",
				"ACBC550789560820ECAFF12A2A24721E", "0e5b964d8f6e47fa9c0fcc46c6712e1f", 10000, 10000);
	}

}
