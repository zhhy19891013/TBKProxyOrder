package com.yj.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.netalliance.OrderQueryRequest;
import com.suning.api.entity.netalliance.OrderQueryResponse;
import com.suning.api.exception.SuningApiException;
import com.yj.domain.CpsAccountSn;
import com.yj.dto.tb.SNCloudOrderParam;

/**
 * 苏宁的api接口
 * 
 * @author Administrator
 *
 */
public class SnApi {

	/**
	 * 抓订单接口
	 * 
	 * @param page
	 * @param suNingBind
	 * @param endTime
	 * @param startTime
	 * @return
	 */
	public static List<SNCloudOrderParam> searchAllOrder(int page, CpsAccountSn suNingBind, String endTime,
			String startTime) {
		List<SNCloudOrderParam> list = new ArrayList<SNCloudOrderParam>();
		OrderQueryRequest request = new OrderQueryRequest();
		request.setEndTime(endTime);
		request.setPageNo(page);
		request.setPageSize(10);
		request.setStartTime(startTime);
		String serverUrl = "https://open.suning.com/api/http/sopRequest";
		String appKey = suNingBind.getAppKey();
		String appSecret = suNingBind.getAppSecret();
		DefaultSuningClient client = new DefaultSuningClient(serverUrl, appKey, appSecret, "json");
		try {
			OrderQueryResponse response = client.excute(request);
			JSONObject obj = JSON.parseObject(response.getBody());
			if (response.getBody().contains("sn_head")) {
				String orderList = obj.getJSONObject("sn_responseContent").getJSONObject("sn_body")
						.getString("queryOrder");
				list = JSONArray.parseArray(orderList, SNCloudOrderParam.class);
			}
		} catch (SuningApiException e) {
			e.printStackTrace();
		}
		return list;
	}

}
