package com.yj.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.request.TbkPrivilegeGetRequest;
import com.taobao.api.request.TbkScOrderGetRequest;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.taobao.api.response.TbkPrivilegeGetResponse;
import com.taobao.api.response.TbkScOrderGetResponse;
import com.yj.common.constants.RedisConstans;
import com.yj.domain.PaymentRecord;
import com.yj.dto.tb.TbOrderDto;
import com.yj.dto.tb.TbkScOrderGetDetail;
import com.yj.dto.tb.TbkScOrderGetDto;
import com.yj.util.redis.RedisClient;

/**
 * 淘宝接口工具类
 * 
 * @author Administrator
 *
 */
public class TbApi {

	private static final String TB_URL = "http://gw.api.taobao.com/router/rest";// 淘宝接口地址
	private static final String TB_APPKEY = "23383504";// appkey
	private static final String TB_APPSECRET = "11bf981493db0778b4a2ab99011c5dc3";// appsecret

	private static final String TB_APPKEY_UP = "23446779";
	private static final String TB_APPSECRET_UP = "c99f4ed6cfbdeb63c73c888bfb27738e";

	private static TaobaoClient client = new DefaultTaobaoClient(TB_URL, TB_APPKEY, TB_APPSECRET);
	private static TaobaoClient clientUp = new DefaultTaobaoClient(TB_URL, TB_APPKEY_UP, TB_APPSECRET_UP);

	/**
	 * 根据id获取图片地址
	 * @param itemID
	 * @return
	 */
	public static String getProductInfo(String itemID) {
		TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
		req.setPlatform(1L);
		req.setNumIids(itemID);
		TbkItemInfoGetResponse rsp;
		try {
			rsp = clientUp.execute(req);
			if (null != rsp.getBody() && rsp.getBody().contains("n_tbk_item")) {
				JSONObject jo = JSONObject.parseObject(rsp.getBody());
				JSONObject jo2 = JSONObject.parseObject(jo.getString("tbk_item_info_get_response"));
				JSONObject jo3 = JSONObject.parseObject(jo2.getString("results"));
				JSONArray jo4 = JSONObject.parseArray(jo3.getString("n_tbk_item"));
				return JSONObject.parseObject(jo4.get(0).toString()).getString("pict_url");
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检验key是否有效
	 * 
	 * @param sessionKey
	 * @return
	 */
	public static boolean checkKey(String sessionKey) {
		if (null == sessionKey || sessionKey.isEmpty()) {
			return false;
		}
		try {
			TbkPrivilegeGetRequest req = new TbkPrivilegeGetRequest();
			req.setItemId(Long.parseLong("111"));
			req.setAdzoneId(Long.parseLong("1"));
			req.setPlatform(2L);
			req.setSiteId(Long.parseLong("1"));
			TbkPrivilegeGetResponse rsp = client.execute(req, sessionKey);
			if (null != rsp && rsp.getBody().contains("invalid-sessionkey")) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 获取淘宝订单
	 * 
	 * @param tbName
	 * @param sessionKey
	 * @param startTime
	 *            开始时间
	 * @param page
	 *            页码
	 * @param tkStatus
	 *            订单状态1: 全部订单，3：订单结算，12：订单付款， 13：订单失效，14：订单成功
	 * @return
	 */
	public static TbOrderDto grapOrder(String tbName, String sessionKey, Date startTime, long page, long tkStatus,
			long span, RedisClient redisClient) {
		TbOrderDto dto = new TbOrderDto();
		TbkScOrderGetRequest req = new TbkScOrderGetRequest();
		req.setFields(
				"tb_trade_parent_id,tb_trade_id,num_iid,item_title,item_num,price,pay_price,seller_nick,seller_shop_title,commission,commission_rate,unid,create_time,earning_time,tk3rd_pub_id,tk3rd_site_id,tk3rd_adzone_id,relation_id,tk_status");
		req.setStartTime(startTime);
		req.setSpan(span);
		req.setPageNo(page);
		req.setPageSize(100L);
		req.setTkStatus(tkStatus);
		req.setOrderQueryType("create_time");
		try {
			TbkScOrderGetResponse rsp = client.execute(req, sessionKey);
			TbkScOrderGetDto re = JSONObject.parseObject(rsp.getBody(), TbkScOrderGetDto.class);
			if (null != re && null != re.getTbk_sc_order_get_response()
					&& null != re.getTbk_sc_order_get_response().getResults()
					&& null != re.getTbk_sc_order_get_response().getResults().getN_tbk_order()) {
				List<TbkScOrderGetDetail> list = re.getTbk_sc_order_get_response().getResults().getN_tbk_order();
				if (list.size() >= 100) {
					dto.setHasMore(1);
				}
				List<PaymentRecord> orders = new ArrayList<PaymentRecord>();
				List<String> ids = new ArrayList<String>();
				for (TbkScOrderGetDetail detai : list) {
					String id = detai.createRepeatId();
					if (null != id) {
						ids.add(id);
					}
					PaymentRecord record = detai.createPayMentRecord(tbName);
					if (record.getAuctionTitle().contains("赠品") && record.getFinalDiscountToString() < 3) {
					} else {
						// 获取下主图
						String key = RedisConstans.CACHE_NAME_TB_PICT_URL + detai.getNum_iid();
						String pic = redisClient.get(key);
						if (null == pic) {
							pic = getProductInfo(detai.getNum_iid());
							if (null != pic) {
								redisClient.set(key, pic);
								redisClient.expire(key, RedisConstans.DEFAULT_EXPIRE_TIME);
							} else {
								record.setPictUrl(
										"https://img.alicdn.com/imgextra/i1/1936457393/O1CN0124U2ZLaBHfCHfYh_!!1936457393.jpg");
							}
						}
						record.setPictUrl(pic);
						orders.add(record);
					}
				}
				dto.setRecords(orders);
				dto.setIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public static void main(String[] args) {
		System.out.println(getProductInfo("573261638162"));
	}

	private TbApi() {
	}
}
