package com.yj.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.dao.JDBindDao;
import com.yj.dao.SystemConfig2Dao;
import com.yj.dao.TbSessionKeyDao;
import com.yj.db.bean.ClientXbBean;
import com.yj.db.model.CloudProduct;
import com.yj.db.model.CloudProductXbTj;
import com.yj.db.model.JDBind;
import com.yj.db.model.JDCloudProduct;
import com.yj.db.model.SystemConfig2;
import com.yj.db.model.SystemUser;
import com.yj.db.model.TbSessionKey;
import com.yj.db.model.reuse.BaseProduct;
import com.yj.service.BaseService;
import com.yj.service.IClientCloudProductXbTjService;
import com.yj.util.JDUtils;
import com.yj.util.RedisCachUtil;
import com.yj.util.StringUtil;
import com.yj.util.TKLUtil;
import com.zhhy.bean.TemplateBean;

public class ClientCloudProductXbTjService extends BaseService implements
		IClientCloudProductXbTjService {

	/**
	 * 客户端查询字符淘口令
	 */
	@Override
	public ClientXbBean searchNewCloudProductEmoji(CloudProductXbTj tj,
			String pid, Long uid) {
		ClientXbBean result = new ClientXbBean();
		// 获取下是否停止
		if (null == uid) {
			result.setStatus("error");
			result.setError("error_no_uid");
			result.setMax(0);
			result.setTotal(0);
			return result;
		}
		try {
			SystemUser user = RedisCachUtil.getUserByUid(uid, baseDao,
					baseRedisDao);// 获取下缓存的用户信息
			if (null == user) {
				result.setStatus("error");
				result.setError("error_no_uid");
				result.setMax(0);
				result.setTotal(0);
				return result;
			}
			if (null != pid && pid.contains("_")) {
				List listAll = new ArrayList();
				List rows = new ArrayList();
				long max = 0l;
				Double d = RedisCachUtil.getRateByName(baseDao, baseRedisDao,
						"xbts");
				System.out.println("小编是否发送:" + d);
				if (d > 0) {
					// 小编推荐的
					List list = searchXb(tj);
					// 京东产品
					if (!list.isEmpty()) {
						listAll.addAll(list);
					}
					// 如果有产品
					if (!listAll.isEmpty()) {
						Collections.sort(listAll, new TimeComparator());
						if (listAll.get(0) instanceof CloudProductXbTj) {
							CloudProductXbTj product = (CloudProductXbTj) listAll
									.get(0);
							// 获取sessionKey
							max = product.getUpdateTime().getTime();
							convertTaobaoEmoji(product, pid, rows, result, user);
						} else {
							// 京东的产品
							SystemUser admin = RedisCachUtil.getUserByUid(1l,
									baseDao, baseRedisDao);// 获取下管理员的信息
							JDCloudProduct p = (JDCloudProduct) listAll.get(0);
							max = p.getUpdateTime().getTime();
							// 转换链接
							String url = convertJdUrl(p, user);
							if (null != url) {
								p.setClickUrl(url);
								rows.add(createJdContent(p, null,
										user.getUserName()));
							}// 京东的转换失败
						}
					}
				}
				// 查询京东有券商品 如果有京东的优先发京东的
				result.setMax(max);
				result.setTotal(rows.size());
				result.setRows(rows);
			} else {
				result.setStatus("error");
				result.setError("pid_error");
				result.setMax(0);
				result.setTotal(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
			result.setError("error_unkonw");
			result.setMax(0);
			result.setTotal(0);
		}
		return result;
	}

	private List searchXb(CloudProductXbTj tj) {
		List result = new ArrayList();
		// 先从redis查
		Object obj = baseRedisDao.searchObject("xbs");
		if (null != obj) {
			List<CloudProductXbTj> tjs = JSONArray.parseArray(obj.toString(),
					CloudProductXbTj.class);
			if (!tjs.isEmpty()) {// 有最新的
				for (CloudProductXbTj t : tjs) {
					if (null != t.getUpdateTime()
							&& t.getUpdateTime().getTime() >= tj
									.getUpdateTime().getTime()) {
						result.add(t);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 云挂机返回产品，7.28
	 * 
	 * @author tcw
	 */
	@Override
	public ClientXbBean searchNewCloudProductXbTj2(CloudProductXbTj tj,
			String pid, Long uid, String url2) {
		url2 = "http://" + getZhiBoWeb() + "/#itemId#txxbs2.do?userId=" + uid;
		System.out.println(url2);
		ClientXbBean result = new ClientXbBean();
		// 获取下是否停止
		if (null == uid) {
			result.setStatus("error");
			result.setError("error_no_uid");
			result.setMax(0);
			result.setTotal(0);
			return result;
		}
		try {
			SystemUser user = RedisCachUtil.getUserByUid(uid, baseDao,
					baseRedisDao);// 获取下缓存的用户信息
			if (null == user) {
				result.setStatus("error");
				result.setError("error_no_uid");
				result.setMax(0);
				result.setTotal(0);
				return result;
			}
			if (null != pid && pid.contains("_")) {
				List listAll = new ArrayList();
				List rows = new ArrayList();
				long max = 0l;
				Double d = RedisCachUtil.getRateByName(baseDao, baseRedisDao,
						"xbts");
				System.out.println("小编是否发送:" + d);
				if (d > 0) {
					Double type = RedisCachUtil.getRateByName(baseDao,
							baseRedisDao, "cloudConfig");
					if (type != null) {
						if (0.0 == type) {
							// 不发送小编
						} else {
							// 小编推荐的
							List list = searchXb(tj);
							// 京东产品
							/*
							 * List jdList = baseDao .searchObjects(
							 * JDCloudProductSendDao
							 * .SEARCH_NEED_JD_CLOUD_PRODUCT_SEND_SQL_NAME, tj);
							 */
							if (!list.isEmpty()) {
								listAll.addAll(list);
							}
							/*
							 * if (!jdList.isEmpty()) { listAll.addAll(jdList);
							 * }
							 */
							// 如果有产品
							if (!listAll.isEmpty()) {
								Collections.sort(listAll, new TimeComparator());
								if (listAll.get(0) instanceof CloudProductXbTj) {
									CloudProductXbTj product = (CloudProductXbTj) listAll
											.get(0);
									// 获取sessionKey
									max = product.getUpdateTime().getTime();
									convertTaobao2(product, pid, rows, result,
											user, url2);
								} else {
									// 京东的产品
									SystemUser admin = RedisCachUtil
											.getUserByUid(1l, baseDao,
													baseRedisDao);// 获取下管理员的信息
									JDCloudProduct p = (JDCloudProduct) listAll
											.get(0);
									max = p.getUpdateTime().getTime();
									// 转换链接
									String url = convertJdUrl(p, user);
									if (null != url) {
										p.setClickUrl(url);
										rows.add(createJdContent(p, null,
												user.getUserName()));
									}
								}
							}
						}
					}
				}
				// 查询京东有券商品 如果有京东的优先发京东的
				result.setMax(max);
				result.setTotal(rows.size());
				result.setRows(rows);
			} else {
				result.setStatus("error");
				result.setError("pid_error");
				result.setMax(0);
				result.setTotal(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
			result.setError("error_unkonw");
			result.setMax(0);
			result.setTotal(0);
		}
		return result;
	}

	/**
	 * 客户端查询小编的内容 6.30 周修改优先个人推送的商品
	 */
	@Override
	public ClientXbBean searchNewCloudProductXbTj(CloudProductXbTj tj,
			String pid, Long uid) {
		ClientXbBean result = new ClientXbBean();
		// 获取下是否停止
		if (null == uid) {
			result.setStatus("error");
			result.setError("error_no_uid");
			result.setMax(0);
			result.setTotal(0);
			return result;
		}
		try {
			SystemUser user = RedisCachUtil.getUserByUid(uid, baseDao,
					baseRedisDao);// 获取下缓存的用户信息
			if (null == user) {
				result.setStatus("error");
				result.setError("error_no_uid");
				result.setMax(0);
				result.setTotal(0);
				return result;
			}
			if (null != pid && pid.contains("_")) {
				List listAll = new ArrayList();
				List rows = new ArrayList();
				long max = 0l;
				Double d = RedisCachUtil.getRateByName(baseDao, baseRedisDao,
						"xbts");
				System.out.println("小编是否发送:" + d);
				if (d > 0) {
					// 小编推荐的
					List list = searchXb(tj);
					// 京东产品
					if (!list.isEmpty()) {
						listAll.addAll(list);
					}
					// 如果有产品
					if (!listAll.isEmpty()) {
						Collections.sort(listAll, new TimeComparator());
						if (listAll.get(0) instanceof CloudProductXbTj) {
							CloudProductXbTj product = (CloudProductXbTj) listAll
									.get(0);
							// 获取sessionKey
							max = product.getUpdateTime().getTime();
							convertTaobao(product, pid, rows, result, user);
						} else {
							// 京东的产品
							SystemUser admin = RedisCachUtil.getUserByUid(1l,
									baseDao, baseRedisDao);// 获取下管理员的信息
							JDCloudProduct p = (JDCloudProduct) listAll.get(0);
							max = p.getUpdateTime().getTime();
							// 转换链接
							String url = convertJdUrl(p, user);
							if (null != url) {
								p.setClickUrl(url);
								rows.add(createJdContent(p, null,
										user.getUserName()));
							}// 京东的转换失败
						}
					}
				}
				// 查询京东有券商品 如果有京东的优先发京东的
				result.setMax(max);
				result.setTotal(rows.size());
				result.setRows(rows);
			} else {
				result.setStatus("error");
				result.setError("pid_error");
				result.setMax(0);
				result.setTotal(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
			result.setError("error_unkonw");
			result.setMax(0);
			result.setTotal(0);
		}
		return result;
	}

	/**
	 * 转换淘宝链接
	 * 
	 * @param product
	 * @param pid
	 * @param rows
	 * @param result
	 * @param user
	 */
	private void convertTaobao(CloudProduct product, String pid, List rows,
			ClientXbBean result, SystemUser user) {
		String sessionKey = getSessionKey(product.getTbName());
		product.createNewUrl2(pid, sessionKey);
		if (null != product.getNewUrl()) {
			if (!product.getNewUrl().contains("error")) {
				String tkl = TKLUtil.getByIp(product.getItemTitle(),
						product.getNewUrl(), product.getPictUrl(), this);
				if (tkl.equals("error")) {
					result.setStatus("error");
					result.setError("error_tkl");
				} else {
					result.setStatus("success");
				}
				rows.add(createContent(product, tkl, user.getUserName()));
			} else {
				result.setStatus("error");
				result.setError(product.getNewUrl());
				result.setMax(product.getUpdateTime().getTime());// 返回出问题的商品的时间戳
			}
		} else {
			result.setStatus("error_no_session_key");
			result.setError("error");
		}
	}

	private String convertJdUrl(JDCloudProduct p, SystemUser user) {
		JDBind jb = new JDBind();
		List jbList = baseDao.searchObjects(
				JDBindDao.SEARCH_RECORD_BY_CONDITION, jb);
		JDBind jb1 = new JDBind();
		if (!jbList.isEmpty() && jbList != null) {
			jb1 = (JDBind) jbList.get(0);
		}
		String unionId = jb1.getUnionID();
		return JDUtils.transferJDLinkNew(user.getJdPid(), unionId,
				p.getSkuid(), p.getCouponlink());
	}

	/**
	 * 创建京东推广文案
	 * 
	 * @param xb
	 * @param template
	 * @param weiba
	 * @param tkl
	 * @param userName
	 * @return
	 */
	private Map createJdContent(JDCloudProduct xb, String tkl, String userName) {
		Map map = new HashMap();
		String content = null;
		String shortLink = xb.getClickUrl();
		if (null != xb.getContent()) {
			content = xb.getContent().replaceAll("#clickUrl#", shortLink);
		} else {
			content = "<IMG src=\"" + xb.getPictrue() + "" + "\">" + "【包邮】"
					+ xb.getPfullname().replaceAll("\r\n", "<br>")
					+ "<br>--------<br>" + "京东价:" + xb.getPrice() + " <br>券后价:"
					+ xb.getAfterQuan() + "<br>" + "领券下单:" + shortLink
					+ "<br>--------<br>" + xb.getGuidearticle();
		}
		map.put("type", "jd");
		map.put("content", content);
		map.put("pictUrl", xb.getPictrue());
		map.put("pictUrl2", xb.getPictrue());
		map.put("afterQuan", xb.getAfterQuan());
		map.put("itemTitle", xb.getPfullname());
		// 上行参数
		map.put("account", userName);
		map.put("itemId", "528293413364");
		map.put("originUrl", "https://item.taobao.com/item.htm?id=528293413364");
		map.put("couponUrl", "https://item.taobao.com/item.htm?id=528293413364");
		map.put("tbkUrl", "https://item.taobao.com/item.htm?id=528293413364");
		map.put("itemTitle", "天天特价大黄蜂男童凉鞋2017夏小童中大童小孩童鞋男孩儿童沙滩鞋 ");
		map.put("itemDescription", "天天特价大黄蜂男童凉鞋2017夏小童中大童小孩童鞋男孩儿童沙滩鞋 ");
		map.put("tbCommand", "￥LjrVZGzgQTZ￥");
		return map;
	}

	/**
	 * 创建淘宝文案
	 * 
	 * @param xb
	 * @param template
	 * @param tkl
	 * @param userName
	 * @return
	 */
	private Map createContent(CloudProduct xb, String tkl, String userName) {
		// 存到redis
		baseRedisDao.saveObject(tkl.replaceAll("《", "").replaceAll("￥", ""),
				JSONObject.toJSONString(xb));
		Map map = new HashMap();
		String content = null;
		if (null != xb.getContent()) {
			String t = " #tkl# <br>";
			if (xb.getContent().contains("#tkl#")) {
				content = "<IMG src=\""
						+ xb.getPictUrl()
						+ ""
						+ "\">"
						+ xb.getContent().replaceAll("\r\n", "<br>")
								.replaceAll("#tkl#", "");
			} else {
				content = "<IMG src=\"" + xb.getPictUrl() + "" + "\">"
						+ xb.getContent().replaceAll("\r\n", "<br>") + "<br>"
						+ t.replaceAll("#tkl#", "") + "<br>";
			}
		} else {
			SystemConfig2 sc = RedisCachUtil.getSystemCofingByName(
					SystemConfig2.CONFIG_NAME_TEMPLATE, baseDao, baseRedisDao);
			String template = "";// 群发模板
			if (null != sc) {
				TemplateBean bean = JSON.parseObject(sc.getConfigValue(),
						TemplateBean.class);
				template = bean.getContent();
			}
			content = template
					.replace("#itemTitle#", xb.getItemTitle())
					.replace("#picturl#",
							"<IMG src=\"" + xb.getPictUrl() + "" + "\">")
					.replace("#tkl#", "")
					.replace("#discountPrice#", xb.getDiscountPrice() + "")
					.replace("#afterQuan#", xb.getAfterQuan() + "")
					.replace("#shortTitle#", xb.getShortTitle())
					.replace("#br#", "<br>");
		}
		map.put("type", "taobao");
		map.put("itemId", xb.getItemId());
		map.put("content", content);
		map.put("pictUrl", xb.getPictUrl());
		map.put("pictUrl2", xb.getPictUrl2());
		map.put("afterQuan", xb.getAfterQuan());
		map.put("quanPrice", xb.getQuanPrice());
		map.put("discountPrice", xb.getDiscountPrice());
		map.put("itemTitle", xb.getItemTitle());
		// 获取下直播间域名
		String zhiBoWeb = getZhiBoWeb();
		if (null != getZhiBoWeb() && null != tkl) {
			map.put("go", "http://" + getZhiBoWeb() + "/go.do?tkl="
					+ tkl.replace("《", "").replace("￥", ""));
		}
		System.out.println(zhiBoWeb);
		// 上行参数
		map.put("account", userName);
		map.put("itemId", xb.getItemId());
		map.put("originUrl", xb.getHref());
		map.put("couponUrl", xb.getMobileCoupon());
		map.put("tbkUrl", xb.getNewUrl());
		map.put("itemTitle", xb.getItemTitle());
		map.put("itemDescription", content);
		map.put("tbCommand", tkl);
		return map;
	}

	private String getZhiBoWeb() {
		SystemConfig2 sc = RedisCachUtil.getSystemCofingByName(
				SystemConfig2.CONFIG_NAME_ZHIBO_WEB, baseDao, baseRedisDao);
		if (null != sc) {
			List l = StringUtil.split(sc.getConfigValue(), ".");
			if (l.size() == 2) {
				return StringUtil.append(StringUtil.getRandomString(), ".",
						sc.getConfigValue());
			} else {
				return sc.getConfigValue();
			}

		}
		return null;
	}

	/**
	 * 获取sessionKey
	 */
	private String getSessionKey(String tbName) {
		TbSessionKey param = new TbSessionKey();
		param.setTbName(tbName);
		Object obj = baseRedisDao.searchObject(tbName + "_sessionKey");
		if (null != obj) {
			TbSessionKey key = JSONObject.parseObject(obj.toString(),
					TbSessionKey.class);
			if (null != key) {
				return key.getSessionKey();
			} else {
				System.out.println(tbName + ":未授权");
			}
		}
		TbSessionKey key = (TbSessionKey) baseDao.searchSingleObject(
				TbSessionKeyDao.SEARCH_SESSIONKEY_BY_TB_NAME, param);
		baseRedisDao.saveObject(tbName + "_sessionKey",
				JSONObject.toJSONString(key));
		if (null != key) {
			return key.getSessionKey();
		} else {
			System.out.println(tbName + ":未授权");
		}
		return null;
	}

	private class TimeComparator implements Comparator<BaseProduct> {
		@Override
		public int compare(BaseProduct o1, BaseProduct o2) {
			return o1.getUpdateTime().compareTo(o2.getUpdateTime());
		}
	}

	/**
	 * 转换淘宝链接
	 * 
	 * @param product
	 * @param pid
	 * @param rows
	 * @param result
	 * @param user
	 */
	private void convertTaobao2(CloudProduct product, String pid, List rows,
			ClientXbBean result, SystemUser user, String url) {
		String sessionKey = getSessionKey(product.getTbName());
		product.createNewUrl2(pid, sessionKey);
		if (null != product.getNewUrl()) {
			if (!product.getNewUrl().contains("error")) {
				rows.add(createContent2(product, url, user.getUserName()));
			} else {
				result.setStatus("error");
				result.setError(product.getNewUrl());
				result.setMax(product.getUpdateTime().getTime());// 返回出问题的商品的时间戳
			}
		} else {
			baseRedisDao.deleteObject(product.getTbName() + "_sessionKey");
			result.setStatus("error_no_session_key");
			result.setError("error");
		}
	}

	/**
	 * 创建淘宝文案 链接模式
	 * 
	 * @param xb
	 * @param template
	 * @param tkl
	 * @param userName
	 * @return
	 */
	private Map createContent2(CloudProduct xb, String tkl, String userName) {
		Map map = new HashMap();
		// 存到redis
		String content = null;
		tkl = tkl.replaceAll("#itemId#", xb.getItemId());
		if (null != xb.getContent()) {
			String t = "下單 :   #tkl#  <br>";
			if (xb.getContent().contains("#tkl#")) {
				content = "<IMG src=\""
						+ xb.getPictUrl()
						+ ""
						+ "\">"
						+ xb.getContent().replaceAll("\r\n", "<br>")
								.replaceAll("#tkl#", tkl);
			} else {
				content = "<IMG src=\"" + xb.getPictUrl() + "" + "\">"
						+ xb.getContent().replaceAll("\r\n", "<br>") + "<br>"
						+ t.replaceAll("#tkl#", tkl) + "<br>";
			}
		} else {
			SystemConfig2 sc = RedisCachUtil.getSystemCofingByName(
					SystemConfig2.CONFIG_NAME_TEMPLATE, baseDao, baseRedisDao);
			String template = "";// 群发模板
			if (null != sc) {
				TemplateBean bean = JSON.parseObject(sc.getConfigValue(),
						TemplateBean.class);
				template = bean.getContent();
			}
			content = template
					.replace("#itemTitle#", xb.getItemTitle())
					.replace("#picturl#",
							"<IMG src=\"" + xb.getPictUrl() + "" + "\">")
					.replace("#tkl#", tkl)
					.replace("#discountPrice#", xb.getDiscountPrice() + "")
					.replace("#afterQuan#", xb.getAfterQuan() + "")
					.replace("#shortTitle#", xb.getShortTitle())
					.replace("#br#", "<br>");
		}
		map.put("type", "taobao");
		map.put("itemId", xb.getItemId());
		map.put("content", content);
		map.put("pictUrl", xb.getPictUrl());
		map.put("pictUrl2", xb.getPictUrl2());
		map.put("afterQuan", xb.getAfterQuan());
		map.put("quanPrice", xb.getQuanPrice());
		map.put("discountPrice", xb.getDiscountPrice());
		map.put("itemTitle", xb.getItemTitle());
		// 上行参数
		map.put("account", userName);
		map.put("itemId", xb.getItemId());
		map.put("originUrl", xb.getHref());
		map.put("couponUrl", xb.getMobileCoupon());
		map.put("tbkUrl", xb.getNewUrl());
		map.put("itemTitle", xb.getItemTitle());
		map.put("itemDescription", content);
		map.put("tbCommand", tkl);
		return map;
	}

	/**
	 * 转换淘宝链接
	 * 
	 * @param product
	 * @param pid
	 * @param rows
	 * @param result
	 * @param user
	 */
	private void convertTaobaoEmoji(CloudProduct product, String pid,
			List rows, ClientXbBean result, SystemUser user) {
		String sessionKey = getSessionKey(product.getTbName());
		product.createNewUrl2(pid, sessionKey);
		if (null != product.getNewUrl()) {
			if (!product.getNewUrl().contains("error")) {
				String tkl = TKLUtil.getByIp(product.getItemTitle(),
						product.getNewUrl(), product.getPictUrl(), this);
				if (tkl.equals("error")) {
					result.setStatus("error");
					result.setError("error_tkl");
				} else {
					result.setStatus("success");
				}
				rows.add(createContentEmoji(product, tkl, user.getUserName()));
			} else {
				result.setStatus("error");
				result.setError(product.getNewUrl());
				result.setMax(product.getUpdateTime().getTime());// 返回出问题的商品的时间戳
			}
		} else {
			result.setStatus("error_no_session_key");
			result.setError("error");
		}
	}

	/**
	 * 创建淘宝文案 淘口令模式
	 * 
	 * @param xb
	 * @param template
	 * @param tkl
	 * @param userName
	 * @return
	 */
	private Map createContentEmoji(CloudProduct xb, String tkl, String userName) {
		Map map = new HashMap();
		String content = null;
		String tklEmoji = TKLUtil.getRandomTklEmoji();
		String tklEmoji2 = TKLUtil.getRandomTklEmoji();
		tkl = tkl.replaceAll("￥", "").replaceAll("《", "");
		StringBuffer buffer = new StringBuffer(tklEmoji);
		tkl = buffer.append(tkl).append(tklEmoji2).toString();
		if (null != xb.getContent()) {
			String t = "复制这条信息 #tkl# 打开「手机淘宝」下单<br>";
			if (xb.getContent().contains("#tkl#")) {
				content = xb.getContent().replaceAll("\r\n", "<br>")
						.replaceAll("#tkl#", tkl);
			} else {
				content = xb.getContent().replaceAll("\r\n", "<br>") + "<br>"
						+ t.replaceAll("#tkl#", tkl);
			}
		} else {
			SystemConfig2 sc = (SystemConfig2) baseDao.searchSingleObject(
					SystemConfig2Dao.SQL_NAME_SEARCH_SYSTEM_CONFIG2,
					SystemConfig2.CONFIG_NAME_TEMPLATE);
			String template = "";// 群发模板
			if (null != sc) {
				TemplateBean bean = JSON.parseObject(sc.getConfigValue(),
						TemplateBean.class);
				template = bean.getContent();
			}
			content = template.replace("#itemTitle#", xb.getItemTitle())
					.replace("#picturl#", "").replace("#tkl#", tkl)
					.replace("#discountPrice#", xb.getDiscountPrice() + "")
					.replace("#afterQuan#", xb.getAfterQuan() + "")
					.replace("#shortTitle#", xb.getShortTitle())
					.replace("#br#", "<br>");
		}
		map.put("type", "taobao");
		map.put("itemId", xb.getItemId());
		map.put("content", content);
		map.put("pictUrl", xb.getPictUrl().replaceAll("////", "//"));
		map.put("pictUrl2", xb.getPictUrl2());
		map.put("afterQuan", xb.getAfterQuan());
		map.put("quanPrice", xb.getQuanPrice());
		map.put("discountPrice", xb.getDiscountPrice());
		map.put("itemTitle", xb.getItemTitle());
		// 获取下直播间域名
		String zhiBoWeb = getZhiBoWeb();
		if (null != zhiBoWeb && null != tkl) {
			map.put("go", "http://" + zhiBoWeb + "/go.do?tkl=" + tkl);
		}
		System.out.println(zhiBoWeb);
		// 上行参数
		map.put("account", userName);
		map.put("itemId", xb.getItemId());
		map.put("originUrl", xb.getHref());
		map.put("couponUrl", xb.getMobileCoupon());
		map.put("tbkUrl", xb.getNewUrl());
		map.put("itemTitle", xb.getItemTitle());
		map.put("tbCommand", tkl);
		return map;
	}

}
