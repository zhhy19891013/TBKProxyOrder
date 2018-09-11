package com.yj.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.cps.UnionServiceQueryOrderListWithKeyRequest;
import com.jd.open.api.sdk.response.cps.UnionServiceQueryOrderListWithKeyResponse;
import com.yj.domain.CpsAccountJd;
import com.yj.domain.JDOrderDetails;

public class JDapi {
	private JDapi() {
	}

	/**
	 * 获取京东client
	 * 
	 * @return
	 */
	private static JdClient getJdClient() {
		return new DefaultJdClient("https://api.jd.com/routerjson", "35a05c9e-a7ad-4a7a-a108-c8ffc529b50a",
				"ACBC550789560820ECAFF12A2A24721E", "0e5b964d8f6e47fa9c0fcc46c6712e1f", 10000, 10000);
	}

	/**
	 * 检验授权key对不对
	 * 
	 * @param key
	 * @return
	 */
	public static boolean checkKey(String key) {
		JdClient client = getJdClient();
		UnionServiceQueryOrderListWithKeyRequest request = new UnionServiceQueryOrderListWithKeyRequest();
		request.setUnionId(1000610323);
		request.setKey(key);
		request.setTime("20180609");
		request.setPageIndex(1);
		request.setPageSize(500);
		try {
			UnionServiceQueryOrderListWithKeyResponse response = client.execute(request);
			String str = response.getMsg();
			if (null == str || str.contains("Illegal Argument")) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 获取下京东免单订单
	 * 
	 * @return
	 */
	public static Map queryImportOrdersByPage(String time, String key, int page, List<Map<String, Long>> timeHistorys,
			List histroys) {
		Map resultMap = new HashMap();
		if (null == key) {
			return resultMap;
		}
		List<JDOrderDetails> orderDetails = new ArrayList<JDOrderDetails>();
		List<JDOrderDetails> freeDetails = new ArrayList<JDOrderDetails>();
		JdClient client = getJdClient();
		UnionServiceQueryOrderListWithKeyRequest request = new UnionServiceQueryOrderListWithKeyRequest();
		request.setUnionId(1000610323);
		request.setKey(key);
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
				if (queryImportOrdersWithKey_resultJson.getIntValue("success") == 0) {
					return resultMap;
				}
				if (queryImportOrdersWithKey_resultJson.getIntValue("success") == 1) {
					JSONArray jsonArray = queryImportOrdersWithKey_resultJson.getJSONArray("data");
					if (null == jsonArray) {
						return resultMap;
					}
					for (Object object : jsonArray) {
						JSONObject jobj = (JSONObject) object;
						String orderFrom = jobj.getString("orderEmt").equals("2") ? "无线" : "PC";
						String orderId = jobj.getString("orderId");
						Date orderTime = new Date(Long.valueOf(jobj.getString("orderTime")).longValue());
						JSONArray jar = JSONArray.parseArray(jobj.getString("skuList"));
						for (Object object2 : jar) {
							JSONObject jobjDetails = (JSONObject) object2;
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
								// 判断是京东免单还是正常单子
								boolean inTime = false;
								for (Map<String, Long> m : timeHistorys) {
									long begin = m.get("begin");
									long end = m.get("end");
									if (jd.getOrdertime().getTime() >= begin && jd.getOrdertime().getTime() < end) {
										inTime = true;
										break;
									}
								}
								if (inTime && histroys.contains(jd.getItemId())) {
									freeDetails.add(jd);
								} else {
									orderDetails.add(jd);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			return resultMap;
		}
		resultMap.put("orderDetails", orderDetails);
		resultMap.put("freeDetails", freeDetails);
		return resultMap;
	}

	/**
	 * 获取下京东免单订单
	 * 
	 * @return
	 */
	public static Map queryFreeImportOrders(String time, CpsAccountJd bind, long begin, long end, List products,
			int page) {
		Map resultMap = new HashMap();
		if (null == bind.getEmpowerKey()) {
			return resultMap;
		}
		List<JDOrderDetails> orderDetails = new ArrayList<JDOrderDetails>();
		List<JDOrderDetails> outOfLine = new ArrayList<>();// 违规订单
		JdClient client = getJdClient();
		UnionServiceQueryOrderListWithKeyRequest request = new UnionServiceQueryOrderListWithKeyRequest();
		request.setUnionId(1000610323);
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
				if (queryImportOrdersWithKey_resultJson.getIntValue("success") == 0) {
					return resultMap;
				}
				if (queryImportOrdersWithKey_resultJson.getIntValue("success") == 1) {
					JSONArray jsonArray = queryImportOrdersWithKey_resultJson.getJSONArray("data");
					if (null == jsonArray) {
						return resultMap;
					}
					for (Object object : jsonArray) {
						JSONObject jobj = (JSONObject) object;
						String orderFrom = jobj.getString("orderEmt").equals("2") ? "无线" : "PC";
						String orderId = jobj.getString("orderId");
						Date orderTime = new Date(Long.valueOf(jobj.getString("orderTime")).longValue());
						JSONArray jar = JSONArray.parseArray(jobj.getString("skuList"));
						for (Object object2 : jar) {
							JSONObject jobjDetails = (JSONObject) object2;
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
							if (jd.getOrdertime().getTime() >= begin && jd.getOrdertime().getTime() < end
									&& products.contains(jd.getItemId())) {
								if (null != jd.getCosts() && jd.getCosts() > 0 && !orderDetails.contains(jd)) {
									orderDetails.add(jd);
									if (jd.getStatus().equals("Y") && jd.getCommissionRate() >= 50) {
										outOfLine.add(jd);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			return resultMap;
		}
		resultMap.put("orderDetails", orderDetails);
		resultMap.put("outOfLine", outOfLine);
		return resultMap;
	}

}
