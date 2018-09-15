package com.yj.service;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yj.dao.IBASEDao;
import com.yj.dao.IBASEReidsDao;
import com.yj.dao.SystemUserDao;
import com.yj.db.model.CloudProduct;
import com.yj.db.model.SystemUser;
import com.yj.db.model.bean.PidBean;
import com.yj.db.model.reuse.BasicModel;
import com.yj.util.RedisCachUtil;

/**
 * 业务的基类
 * 
 * @author Administrator
 */
public class BaseService implements IBaseService {
	@Autowired
	protected IBASEDao baseDao;

	@Autowired
	protected IBASEReidsDao baseRedisDao;

	protected static final Logger log = Logger.getLogger(BaseService.class);
	public transient final static String ZD_FLAG = "zd";
	public static final String ERROR_CODE_REPEAT = "200";// 重复
	public static final String ERROR_CODE_ERRPR = "500";// 系统错误
	public static final String ERROR_CODE_VRCODE_ERROR = "300";// 验证码错误
	public static final String SUCCESS_CODE_SUCCESS = "0";// 成功
	public static final String ERROR_CODE_LIMIT = "100";// 限制
	public static final String xh_ip = "106.75.91.94";



	/**
	 * JSOUP get
	 */
	@Override
	public String getJSONStrFromWebByJsoup(String url, Map cookies) {
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
			log.error(e);
			if (e instanceof UnknownHostException
					|| e instanceof SocketTimeoutException) {
				log.info("网络断开稍候再试");
				try {
					Thread.sleep(1000);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				return getJSONStrFromWebByJsoup(url, cookies);
			}
		}
		return null;
	}

	/**
	 * JSOUP POST
	 */
	@Override
	public String postJSONStrFromWebByJsoup(String url, Map cookies, Map data) {
		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true)
					.cookies(cookies).timeout(12000).data(data).post();
			Elements ele = doc.getElementsByTag("body");
			return ele.text();
		} catch (Exception e) {
			log.error(e);
			if (e instanceof UnknownHostException
					|| e instanceof SocketTimeoutException) {
				log.info("网络断开稍候再试");
				try {
					Thread.sleep(1000);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				return postJSONStrFromWebByJsoup(url, cookies, data);
			}
		}
		return null;
	}

	/**
	 * 转换相关的文案模板信息 现在支持以下几个字段 1.price价格 2.title标题 3.url链接地址 5.dl 淘宝短链 6. bd百度短链
	 * 4.yhq优惠券
	 * 
	 * @param item
	 * @param template
	 * @return
	 */
	protected String convertContentFromTemplate(String template, String url,
			String bd, String dl, Double zkPrice, String title, String yhq) {
		// 如果没有优惠券就把优惠券链接给去掉
		if (null == template || template.contains("diy_hang")
				|| template.trim().isEmpty()) {
			return "";
		}
		if (!template.contains("mb_yhq")) {
			return template;
		}
		if (null == yhq) {
			// 把原文给截取出来
			String str1 = template.substring(template
					.indexOf("<div class=\"mb_yhq\">"));
			String yhTemplate = str1.substring(0, str1.indexOf("</div>")
					+ "</div>".length());
			template = template.replace(yhTemplate, "").trim();
		}
		template = template.replace("<div class=\"mb_title\">", "")
				.replace("#title#", title).replace("</div>", "</br>");
		template = template.replace("<div class=\"mb_sp\">", "");
		template = template.replace("<div class=\"mb_url\">", "");
		template = template.replace("<div class=\"mb_url\">", "").replace(
				"#bd#", bd);
		template = template.replace("<div class=\"mb_url\">", "").replace(
				"#dl#", dl);
		template = template.replace("<div class=\"mb_url\">", "").replace(
				"#url#", dl);
		template = template.replace("<div class=\"mb_price\">", "").replace(
				"#price#", zkPrice + "");
		template = template.replace("<section>", "").replace("</section>", "")
				.replace("<div class=\"mb_other\">", "");
		return template;
	}

	/**
	 */
	@Override
	public Map searchByCondition(String countSQL, String searchSQL, int page,
			int perPageRow, BasicModel sp) {
		Map result = new HashMap();
		// 先count下
		Long total = (Long) baseDao.searchSingleObject(countSQL, sp);
		int begin = (page - 1) * perPageRow;
		int end = page * perPageRow;
		sp.setBeginPage(begin);
		sp.setEndPage(end);
		sp.setPerPage(perPageRow);
		List row = baseDao.searchObjects(searchSQL, sp);
		result.put("total", total);
		result.put("rows", row);
		result.put("records", row.size());
		return result;
	}


	@Override
	public String getMyPid(CloudProduct product, String pidInfo) {
		if (null == pidInfo || null == product.getTbName()) {
			return null;
		} else {
			List<PidBean> pidBeans = JSON.parseArray(pidInfo, PidBean.class);
			for (PidBean bean : pidBeans) {
				if (bean.getTbName().equals(product.getTbName())) {
					return bean.getPid();
				}
			}
		}
		return null;
	}

	/**
	 * 1.先查用户 2.再找相应的pid
	 */
	@Override
	public String getMyPidByUID(CloudProduct product, long uid) {
		SystemUser user = new SystemUser();
		user.setDatabaseID(uid);
		SystemUser old = (SystemUser) baseDao.searchSingleObject(
				SystemUserDao.SQL_NAME_SEARCH_SYSTEM_USER_BY_DATABASE_ID, user);
		if (null != old) {
			return getMyPid(product, old.getPidInfo());
		}
		return null;
	}

	@Override
	public String getMyTklIp() {
		return RedisCachUtil.getIp(baseDao, baseRedisDao);
	}


	@Override
	public List searchRows(String sql, BasicModel param) {
		return baseDao.searchObjects(sql, param);
	}

}
