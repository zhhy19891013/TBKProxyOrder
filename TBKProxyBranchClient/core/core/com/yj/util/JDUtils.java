package com.yj.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.util.JSoupUtil;

public class JDUtils {
	public static final String CATE_HHR = "1";
	public static final String CATE_XX = "2";

	public static void main(String[] args) {
		transferJDLinkNew("997706088", "1000357799", "15032886367","http://coupon.m.jd.com/coupons/show.action?key=a4857cb850104efaa3c53cf2954732c9&roleId=7824533");
	}
	

	// http://www.lanyincao.com/console/openapi/transfer?unionId=1000144672&pid=712114882&app-key=njlb&app-secret=de14fd83-0dec-427d-8d8b-c5ff01d531e9&sku=14543585001,14738384024
	// 京东转换链接(新) 2017-8-22
	// pid
	public static String transferJDLinkNew(String pid, String unionID,
			String itemID, String cLink) {
		if (pid != null && unionID != null && itemID != null) {
			StringBuffer buffer = new StringBuffer(
					"http://www.lanyincao.com/console/openapi/transfer");
			Document doc;
			Map data = new HashMap();
			data.put("unionId", unionID);
			data.put("pid", pid);
			data.put("app-key", "njlb");
			data.put("app-secret", "de14fd83-0dec-427d-8d8b-c5ff01d531e9");
			data.put("sku", itemID);
			if (null != cLink) {
				data.put("couponurl", UrlUtil.convert(cLink));
			}
			try {
				doc = Jsoup.connect(buffer.toString()).data(data).timeout(30000).post();
				JSONObject ob = JSON.parseObject(doc.text());
				String url = null;
				JSONObject json = JSONObject.parseObject(doc.text());
				JSONArray json2 = JSONArray.parseArray(json.get("list")
						.toString());
				for (Object object : json2) {
					JSONObject jo = (JSONObject) object;
					url = jo.get("shortUrl").toString();
				}
				return url;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;// "unionID错误或没有同步京东PID"
		}

	}

}
