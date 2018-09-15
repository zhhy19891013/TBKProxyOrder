package com.yj.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * jsoup工具类
 * 
 * @author Administrator
 * 
 */
public class JSoupUtil {

	/**
	 * JSOUP get
	 */
	public static String getJSONStrFromWebByJsoup(String url, Map cookies) {
		try {
			return getJSon(url, cookies);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
		return null;
	}

	private static String getJSon(String url, Map cookies) throws IOException {
		Document doc;
		if (null == cookies) {
			doc = Jsoup.connect(url).ignoreContentType(true).timeout(6000)
					.get();
		} else {
			doc = Jsoup.connect(url).ignoreContentType(true).cookies(cookies)
					.timeout(6000).get();
		}
		Elements ele = doc.getElementsByTag("body");
		return ele.text();
	}

	/**
	 * JSOUP POST
	 */
	public static String postJSONStrFromWebByJsoup(String url, Map data) {
		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true)
					.timeout(300000).data(data).post();
			Elements ele = doc.getElementsByTag("body");
			return ele.text();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * JSOUP POST
	 */
	public static String postJSONStrFromWebByJsoupShort(String url, Map data) {
		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true)
					.timeout(20000).data(data).post();
			Elements ele = doc.getElementsByTag("body");
			return ele.text();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * JSOUP POST
	 */
	public static String postJSONStrFromWebByJsoup(String url, Map data,
			Map cookies) {
		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true)
					.timeout(12000).cookies(cookies).data(data).post();
			Elements ele = doc.getElementsByTag("body");
			return ele.text();
		} catch (Exception e) {
			if (e instanceof UnknownHostException
					|| e instanceof SocketTimeoutException) {
				try {
					Thread.sleep(1000);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * JSOUP get
	 */
	public static String getJSONStr(String url) {

		try {
			Document s;
			s = Jsoup.connect(url).ignoreContentType(true).get();
			Elements ele = s.getElementsByTag("body");
			return ele.text();

		} catch (Exception e) {

		}
		return null;

	}

	public static String getJSONStrUpdate(String url) {

		try {
			Document s;
			s = Jsoup.connect(url).ignoreContentType(true).timeout(100).get();
			Elements ele = s.getElementsByTag("body");
			return ele.text();

		} catch (Exception e) {

		}
		return null;

	}

	/**
	 * JSOUP get
	 */
	public static Elements getJSONStrFromWebByJsoup(String url) {
		try {
			Document doc;
			doc = Jsoup.connect(url).ignoreContentType(true).timeout(12000)
					.get();
			Elements ele = doc.getElementsByTag("body");
			return ele;
		} catch (Exception e) {
			if (e instanceof UnknownHostException
					|| e instanceof SocketTimeoutException) {
				try {
					Thread.sleep(1000);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				return getJSONStrFromWebByJsoup2(url);
			}
		}
		return null;
	}

	/**
	 * JSOUP get
	 */
	public static Elements getJSONStrFromWebByJsoup2(String url) {
		try {
			Document doc;
			doc = Jsoup.connect(url).ignoreContentType(true).timeout(12000)
					.get();
			Elements ele = doc.getElementsByTag("body");
			return ele;
		} catch (Exception e) {
			if (e instanceof UnknownHostException
					|| e instanceof SocketTimeoutException) {
				try {
					Thread.sleep(1000);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * JSOUP get
	 */
	public static String getJSONStrFromAlimamaByJsoup(String url, Map cookies) {
		try {
			Document doc;
			if (null == cookies) {
				doc = Jsoup.connect(url).ignoreContentType(true).timeout(12000)
						.get();
			} else {
				doc = Jsoup
						.connect(url)
						.header("Host", "pub.alimama.com")
						.header("Referer",
								"http://pub.alimama.com/myunion.htm?spm=a2320.7388781.a214tr8.d006.UqmIPN")
						.header("Accept",
								"application/json, text/javascript, */*; q=0.01")
						.header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language",
								"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
						.header("Connection", "keep-alive")
						.header("Content-Type",
								"application/x-www-form-urlencoded; charset=UTF-8")
						.header("User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
						.header("X-Requested-With", "XMLHttpRequest")
						.header("Cache-Control", "max-age=0")
						.cookies(cookies)
						.userAgent(
								"Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko")
						.ignoreContentType(true).cookies(cookies)
						.timeout(12000).get();
			}
			Elements ele = doc.getElementsByTag("body");
			return ele.text();
		} catch (Exception e) {
			if (e instanceof UnknownHostException
					|| e instanceof SocketTimeoutException) {
				try {
					Thread.sleep(1000);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String postJSONStrFromWebByJsoupShort1(String url, Map data) {
		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true)
					.timeout(30000).data(data).post();
			Elements ele = doc.getElementsByTag("body");
			return ele.text();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// String param
		// ="[{\"campId\":\"41274211\",\"keeperId\":\"1667693961\",\"cat\":\"50015221\",\"discountPrice\":89,\"href\":\"https://item.taobao.com/item.htm?id=543236225716\",\"isMall\":0,\"itemId\":\"543236225716\",\"itemTitle\":\"古阿源阿胶糕即食补气血500g礼盒装山东固元滋补ejiao原味阿胶膏\",\"shortTitle\":\"古阿源阿胶糕即食补气血500g礼盒装山东固元滋补ejiao原味阿胶膏\",\"pictUrl\":\"http://img04.taobaocdn.com/bao/uploaded/i4/1667693961/TB29NCVbNtmpuFjSZFqXXbHFpXa_!!1667693961.jpg\",\"pictUrl2\":\"http://gd1.alicdn.com/imgextra/i1/1667693961/TB2AWZSemCI.eBjy1XbXXbUBFXa_!!1667693961.jpg\",\"pictUrl3\":\"http://gd2.alicdn.com/imgextra/i2/1667693961/TB2g0SFbhaK.eBjSZFAXXczFXXa_!!1667693961.jpg\",\"pictUrl4\":\"http://gd1.alicdn.com/imgextra/i1/1667693961/TB2Wei1aY1J.eBjSszcXXbFzVXa_!!1667693961.jpg\",\"pictUrl5\":\"http://gd4.alicdn.com/imgextra/i4/1667693961/TB29NCVbNtmpuFjSZFqXXbHFpXa_!!1667693961.jpg\",\"productType\":\"3\",\"soldQuantity\":77,\"realCommission\":11.7,\"realCommissionRate\":30,\"ind\":\"食品/保健\",\"refundReason\":0.062,\"sellerScore\":10180,\"serviceAvgScore\":\"4.87\",\"serviceGap\":\"26.51\",\"consignmentAvgScore\":\"4.86\",\"consignmentGap\":\"30.92\",\"avgRefundRate\":0.038,\"merchandisAvgScore\":\"4.84\",\"merchandisGap\":\"25.45\",\"commentCount\":0,\"shopName\":\"???????\",\"mobileCoupon\":\"http://shop.m.taobao.com/shop/coupon.htm?seller_id=1667693961&activity_id=5cf6ffa1185f442b951167992ffdf104\",\"pcCoupon\":\"http://shop.m.taobao.com/shop/coupon.htm?seller_id=1667693961&activity_id=5cf6ffa1185f442b951167992ffdf104\",\"quanPrice\":50,\"used\":663,\"require\":89,\"rest\":9337,\"expired\":1483113600000}]";
		Map data = new HashMap();
		data.put("pid", "1_1_1");
		data.put("updateTime", "1");
		data.put("page", "1");
		System.out.println(postJSONStrFromWebByJsoup(
				"http://www.longtong58.com/client/cloud/down.do", data));
	}

}
