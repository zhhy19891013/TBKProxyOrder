package com.yj.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkItemCouponGetRequest;
import com.taobao.api.request.TbkPrivilegeGetRequest;
import com.taobao.api.response.TbkItemCouponGetResponse;
import com.taobao.api.response.TbkPrivilegeGetResponse;
import com.zhhy.bean.TaobaoConvertBean;
/**
 * 淘宝api相关封装类
 * 
 * @author Administrator
 * 
 */
public class TaoBaoApi {
	private TaoBaoApi() {
	}

	/**
	 * 转链api error_no_session_key 没有授权 error_item_ban 宝贝下架
	 * error_session_key_invalid 授权过期 error_site 推广位不一致
	 * 
	 * @return
	 */
	public static String convert(String pid2, String pid3, String itemId,
			String sessionKey,double rate) {
		try {
			if (null == sessionKey) {
				return "error_no_session_key";
			}
			TaobaoClient client = new DefaultTaobaoClient(
					"http://gw.api.taobao.com/router/rest", "23383504",
					"11bf981493db0778b4a2ab99011c5dc3");
			TbkPrivilegeGetRequest req = new TbkPrivilegeGetRequest();
			req.setItemId(Long.parseLong(itemId));
			req.setAdzoneId(Long.parseLong(pid3));
			req.setPlatform(2L);
			req.setSiteId(Long.parseLong(pid2));
			TbkPrivilegeGetResponse rsp = client.execute(req, sessionKey);
			if (rsp.getBody().contains("coupon_click_url")) {
				TaobaoConvertBean bean = JSONObject.parseObject(rsp.getBody(),
						TaobaoConvertBean.class);
				double realRate =bean.getTbk_privilege_get_response().getResult().getData().getMax_commission_rate();
				if(realRate<rate&&(rate-realRate>1)){
					System.out.println("真实比例:"+realRate+",产品库比例:"+rate);
					return "error_item_ban";
				}
				return bean.getTbk_privilege_get_response().getResult()
						.getData().getCoupon_click_url();
			}
			if (rsp.getBody().contains("该item_id对应宝贝已下架或非淘客宝贝")) {
				return "error_item_ban";
			}
			if (rsp.getBody().contains("invalid-sessionkey")) {
				return "error_session_key_invalid";
			}
			if (rsp.getBody().contains("请检查site_id和adzone_id是否匹配")) {
				return "error_site";
			}
			PrintUtil.print(rsp.getBody());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

	public static void main(String[] args) {
		System.out.println(20.1-20);
	}

}
