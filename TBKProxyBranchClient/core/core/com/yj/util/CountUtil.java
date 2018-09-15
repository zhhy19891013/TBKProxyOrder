package com.yj.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 访问计数
 * 
 * @author Administrator
 */
public class CountUtil {
	private static final Logger logger = Logger.getLogger(CountUtil.class);

	private static Map countMap = new HashMap();// 访问次数
	private static Map timeMap = new HashMap();// 时间间隔
	private static Map countMap2 = new HashMap();// 访问次数
	private static Map timeMap2 = new HashMap();// 时间间隔
	
	private static Map countMap3 = new HashMap();// 访问次数
	private static Map timeMap3 = new HashMap();// 时间间隔

	/**
	 * ip限制
	 * 
	 * @param request
	 * @param maxcount
	 * @param limit
	 * @return
	 */
	public static boolean requestIpLimit(HttpServletRequest request,
			int maxcount, long limit) {
		try {
			String ip = getIpAddress(request);
			String url = request.getRequestURL().toString();
			String key = "req_limit_".concat(ip);
			Integer count = 0;
			if (null != countMap2.get(key)) {
				count = (Integer) countMap2.get(key);
			}
			count++;
			// 检验时间
			long time = 0;
			if (null != timeMap2.get(key)) {
				Date now = new Date();
				Date last = (Date) timeMap2.get(key);
				time = now.getTime() - last.getTime();
			} else {
				timeMap2.put(key, new Date());// 更新下时间
			}
			// 如果现在的时间和之前的时间间隔已经超过规定的时间间隔 那么就重新统计
			if (time > limit) {
				count = 1;
				timeMap2.put(key, new Date());// 更新下时间
				countMap2.put(key, 0);
			} else {
				countMap2.put(key, count);
			}
			// 在时间间隔内累加下 并缓存下
			if (count > maxcount) {
				logger.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数["
						+ maxcount + "]");
				return false;
			}
		} catch (Exception e) {
			logger.error("发生异常: ", e);
		}
		return true;
	}

	public static boolean requestLimit(HttpServletRequest request,
			int maxcount, long limit) {
		try {
			String ip = getIpAddress(request);
			String url = request.getRequestURL().toString();
			String key = "req_limit_".concat(url).concat(ip);
			Integer count = 0;
			if (null != countMap.get(key)) {
				count = (Integer) countMap.get(key);
			}
			count++;
			// 检验时间
			long time = 0;
			if (null != timeMap.get(key)) {
				Date now = new Date();
				Date last = (Date) timeMap.get(key);
				time = now.getTime() - last.getTime();
			} else {
				timeMap.put(key, new Date());// 更新下时间
			}
			// 如果现在的时间和之前的时间间隔已经超过规定的时间间隔 那么就重新统计
			if (time > limit) {
				count = 1;
				timeMap.put(key, new Date());// 更新下时间
				countMap.put(key, 0);
			} else {
				countMap.put(key, count);
			}
			// 在时间间隔内累加下 并缓存下
			if (count > maxcount) {
				return false;
			}
		} catch (Exception e) {
			logger.error("发生异常: ", e);
		}
		return true;
	}

	public static boolean openIdLimit(String openId, int maxcount, long limit) {
		try {
			String key = "req_limit_".concat(openId);
			Integer count = 0;
			if (null != countMap3.get(key)) {
				count = (Integer) countMap3.get(key);
			}
			count++;
			// 检验时间
			long time = 0;
			if (null != timeMap3.get(key)) {
				Date now = new Date();
				Date last = (Date) timeMap3.get(key);
				time = now.getTime() - last.getTime();
			} else {
				timeMap3.put(key, new Date());// 更新下时间
			}
			// 如果现在的时间和之前的时间间隔已经超过规定的时间间隔 那么就重新统计
			if (time > limit) {
				count = 1;
				timeMap3.put(key, new Date());// 更新下时间
				countMap3.put(key, 0);
			} else {
				countMap3.put(key, count);
			}
			// 在时间间隔内累加下 并缓存下
			if (count > maxcount) {
				logger.info("用户IP[" + openId + "]访问地址[ 发送短信 ]超过了限定的次数["
						+ maxcount + "]");
				return false;
			}
		} catch (Exception e) {
			logger.error("发生异常: ", e);
		}
		return true;
	}

	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getIpAddress(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

}
