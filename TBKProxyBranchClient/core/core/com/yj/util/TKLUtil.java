package com.yj.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.yj.service.IBaseService;

public class TKLUtil {

	/**
	 * 表情淘口令符号
	 */
	private static List<String> tklStyleList = new ArrayList<String>();

	static {
		tklStyleList.add("🔑");
		tklStyleList.add("💲");
		tklStyleList.add("😺");
		tklStyleList.add("📲");
		tklStyleList.add("💰");
		tklStyleList.add("🎵");
		tklStyleList.add("🔐");
		tklStyleList.add("✔");
	}

	public static String reductionTKL(String tkl) {
		for (String str : tklStyleList) {
			tkl = tkl.replaceAll(str, "");
		}
		return tkl.replace("￥", "").replace("《", "");
	}

	public static String getRandomTklEmoji() {
		int random = (int) (Math.random() * tklStyleList.size());
		if (random > tklStyleList.size() || random < 0) {
			random = 0;
		}
		return tklStyleList.get(random);
	}

	public static String getByIp(String title, String url, String pictUrl,
			IBaseService baseService) {
		try {
			String doc = getByIp(title, url, pictUrl, "23383504",
					"11bf981493db0778b4a2ab99011c5dc3");
			if (doc.equals("error")) {
				doc = getByIp(title, url, pictUrl, "23708666",
						"157c9cd7c1a85db9ec3e35baa87053bf");
			}
			if (doc.equals("error")) {
				doc = getByIp(title, url, pictUrl, "23525730",
						"3488c37a11fa3a57eaece0e02fa23320");
			}
			if (doc.equals("error")) {
				doc = getByIp(title, url, pictUrl, "23525350",
						"33de48a7e729e9ed305d3de3315c6374");
			}
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	public static String getByIp(String title, String url, String pictUrl,
			String appkey, String appSecret) {
		TaobaoClient client = new DefaultTaobaoClient(
				"https://eco.taobao.com/router/rest", appkey, appSecret);
		TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
		req.setText(title);
		req.setUrl(url);
		String body = null;
		try {
			TbkTpwdCreateResponse rsp = client.execute(req);
			body = rsp.getBody();
			JSONObject o = JSONObject.parseObject(body);

			String tkl = o.getJSONObject("tbk_tpwd_create_response")
					.getJSONObject("data").get("model").toString();
			System.out.println(tkl);
			if (null != tkl) {
				if (tkl.contains("￥")) {
					return tkl.replaceAll("￥", "《");
				} else {
					return tkl;
				}
			}
		} catch (Exception e) {
			System.out.println(body);
			System.out.println(url);
			e.printStackTrace();
		}
		return "error";
	}

	private static Document remoteGetTkl(Map data, String ip) {
		try {
			return Jsoup
					.connect("http://" + ip + "/ConvertTkl/servlet/Convert")
					.ignoreContentType(true).timeout(30000).data(data).post();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Document remoteGetTklPersonal(Map data, String ip) {
		try {

			StringBuffer sb = new StringBuffer("http://").append(ip).append(
					"/ConvertTkl/servlet/ConvertPersonal");
			return Jsoup.connect(sb.toString()).ignoreContentType(true)
					.timeout(30000).data(data).post();
		} catch (IOException e) {
		}
		return null;
	}

	public static void main(String[] args) {
		getByIp("伯朗苹果Type-c数据线三合一华为p9手机多用多头一拖三多功能充电",
				"https://uland.taobao.com/coupon/edetail?e=Yy51e1wslLk8Clx5mXPEKjxhTvJewOpzjptcs0Wx9QR9qBDtc2%2FUytcLsOn7Yh1SIxsawsJqVO%2B%2BKTf7HempSJBh%2BsFgnewC%2B35WViOGkFQt%2FzOJQMDvlzLZyP%2BOsOFo&traceId=0b82a97515088968932732997e&activityId=061210e08cee43e5ae27f712f2bcd5dc",
				"http://img.alicdn.com/bao/uploaded/i4/TB1Trc.OXXXXXceXFXXXXXXXXXX_!!2-item_pic.png_220x220",
				"23525731", "68e6b800969663905dcff34cdbafa5df");
	}
}
