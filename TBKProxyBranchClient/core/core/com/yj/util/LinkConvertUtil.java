package com.yj.util;

import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.zhhy.bean.LinkBean;
import com.zhhy.bean.LinkBeanObject;

/**
 * 链接转换工具类
 * 
 * @author Administrator
 * 
 */
public class LinkConvertUtil {

	private static final Logger log = Logger.getLogger(LinkConvertUtil.class);

	private LinkConvertUtil() {

	}

	/**
	 * 腾讯短链接
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String convertTXUrl(String url) throws Exception {
		String web = "http://www.qqurl.com/create/?url=" + url
				+ "&_=1446873630824";
		Document doc = Jsoup.connect(web).ignoreContentType(true).post();
		Elements ele = doc.getElementsByTag("body");
		LinkObject ob = JSON.parseObject(ele.text(), LinkObject.class);
		if (null != ob.getShort_url()) {
			return ob.getShort_url().replace("\\", "/");
		}
		return "转换失败";
	}

	/**
	 * 百度短链接
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String convertBDUrl(String url) {
		String baidudz = "http://dwz.cn/create.php";
		Map param = new HashMap();
		param.put("access_type", "web");
		param.put("url", url);
		try {
			String str = postJSONStrFromWebByJsoup(baidudz, null, param);
			LinkObject ob = JSON.parseObject(str, LinkObject.class);
			if (null != ob.getTinyurl()) {
				return ob.getTinyurl().replace("\\", "/");
			}
		} catch (Exception e) {
			log.error(e);
		}
		return "转换失败";
	}

	/**
	 * 淘宝短链接
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String convertTBUrl(String url) {
		String baidudz = "http://tao.bb/d/save";
		Map param = new HashMap();
		param.put("access_type", "web");
		param.put("url", url);
		String str = postJSONStrFromWebByJsoup(baidudz, null, param);
		LinkObject ob = JSON.parseObject(str, LinkObject.class);
		if (null != ob.getKey()) {
			return "http://tao.bb/" + ob.getKey();
		}
		return "转换失败";
	}

	/**
	 * 新浪短链接 569452181 2849184197 211160679 82966982 2440435914 2190063733
	 * 3442868347
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String convertXLUrl(String url) {
		try {
			String web = "http://api.weibo.com/2/short_url/shorten.json?source=5786724301&url_long="
					+URLEncoder.encode(url,"utf-8"); ;
			Document doc = Jsoup.connect(web).ignoreContentType(true)
					.timeout(2000).get();
			Elements ele = doc.getElementsByTag("body");
			LinkBean ob = JSON.parseObject(ele.text(),
					LinkBean.class);
			if (null != ob &&!ob.getUrls().isEmpty()) {
				LinkBeanObject lbp = ob.getUrls().get(0);
				if(null!=lbp.getUrl_short()){
					return lbp.getUrl_short();
				}
			}
		} catch (Exception e) {
			System.out.println("转换出错");
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			System.out
					.println(convertXLUrl("http://coupon.m.jd.com/coupons/show.action?key=35ef08a46cd44307896e146555dd4637&roleId=6676155&to=https%3a%2f%2funion-click.jd.com%2fjdc%3fd%3d3qUXMv"));
		} catch (Exception e) {
		}
	}

	/**
	 * JSOUP POST
	 */
	private static String postJSONStrFromWebByJsoup(String url, Map cookies,
			Map data) {
		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true)
					.timeout(12000).data(data).post();
			Elements ele = doc.getElementsByTag("body");
			return ele.text();
		} catch (Exception e) {
			log.error(e);
			if (e instanceof UnknownHostException
					|| e instanceof SocketTimeoutException) {
				log.info("网络断开稍候再试");
				try {
					Thread.sleep(2000);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				return postJSONStrFromWebByJsoup(url, cookies, data);
			}
		}
		return null;
	}
}
