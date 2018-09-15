package com.yj.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yj.dao.IBASEDao;
import com.yj.dao.IBASEReidsDao;
import com.yj.dao.SystemCommonConfigDao;
import com.yj.dao.SystemConfig2Dao;
import com.yj.dao.SystemUserDao;
import com.yj.dao.TKLAppIpDao;
import com.yj.db.model.SystemCommonConfig;
import com.yj.db.model.SystemConfig2;
import com.yj.db.model.SystemUser;
import com.yj.db.model.TKLAppIp;

/**
 * redis缓存
 * 
 * @author Administrator
 */
public class RedisCachUtil {
	public final static String KEY_NAME_SYSTEM_USER_PID = "system_user_";// 用户redis缓存前缀
	public final static String KEY_NAME_SEND_NUMBER = "cloud_product_send_list_num_";// 推送上限
	public final static String KEY_NAME_SYSTEM_CONFIG2 = "system_conifg2_";// 系统配置
	public final static String KEY_NAME_IP = "ip";// 聚石塔ip
	public final static String KEY_NAME_SYSTEM_COMMON_CONFIG = "system_common_config_";//数值配置信息
	public final static Map validUser = new HashMap();

	/**
	 * 根据uid获取下用户信息
	 * 
	 * @param uid
	 * @param baseDao
	 * @return
	 */
	public static SystemUser getUserByUid(Long uid, IBASEDao baseDao,
			IBASEReidsDao baseRedisDao) {
		String key = KEY_NAME_SYSTEM_USER_PID + uid;
		SystemUser user = null;
		Object obj = baseRedisDao.searchObject(key);
		if(validUser.keySet().contains(uid)){
			return null;
		}
		if (null == obj) {
			SystemUser old = new SystemUser();
			old.setDatabaseID(uid);
			user = (SystemUser) baseDao.searchSingleObject(
					SystemUserDao.SQL_NAME_SEARCH_SYSTEM_USER_BY_DATABASE_ID,
					old);
			// 存到redis
			if (null != user) {
				String str = JSONObject.toJSONString(user);
				baseRedisDao.saveObject(key, str);
			}else{
				validUser.put(uid, user);
			}
		} else {
			user = JSONObject.parseObject(obj.toString(), SystemUser.class);
		}
		return user;
	}

	/**
	 * 获取下群发配置
	 * 
	 * @param name
	 * @param baseDao
	 * @return
	 */
	public static SystemConfig2 getSystemCofingByName(String name,
			IBASEDao baseDao, IBASEReidsDao baseRedisDao) {
		String key = KEY_NAME_SYSTEM_CONFIG2 + name;
		SystemConfig2 sc = null;
		Object obj = baseRedisDao.searchObject(key);
		if (null == obj) {
			sc = (SystemConfig2) baseDao.searchSingleObject(
					SystemConfig2Dao.SQL_NAME_SEARCH_SYSTEM_CONFIG2, name);
			// 存到redis
			String str = JSONObject.toJSONString(sc);
			baseRedisDao.saveObject(key, str);
		} else {
			sc = JSONObject.parseObject(obj.toString(), SystemConfig2.class);
		}
		return sc;
	}

	/**
	 * 刷新下群发配置
	 * 
	 * @param name
	 * @param sc
	 */
	public static void setSystemConfigByName(String name, SystemConfig2 sc,
			IBASEReidsDao baseRedisDao) {
		String key = KEY_NAME_SYSTEM_CONFIG2 + name;
		String str = JSONObject.toJSONString(sc);
		baseRedisDao.updateObject(key, str);
	}

	/**
	 * 获取淘口令ip
	 * 
	 * @param baseService
	 * @return
	 */
	public static String getIp(IBASEDao baseDao, IBASEReidsDao baseRedisDao) {
		String result = null;
		Object obj = baseRedisDao.searchObject(KEY_NAME_IP);
		if (null != obj) {
			result = obj.toString();
		} else {
			TKLAppIp ip = (TKLAppIp) baseDao.searchSingleObject(
					TKLAppIpDao.SQL_NAME_SEARCH_TKL_APP_IP, null);
			if (null != ip) {
				result = ip.getIp();
			} else {
				result = "121.41.178.167";
			}
			baseRedisDao.saveObject(KEY_NAME_IP, result);
		}
		return result;
	}

	/**
	 * 刷新下ip
	 * 
	 * @param ip
	 */
	public static void setIp(IBASEReidsDao baseRedisDao, String ip) {
		baseRedisDao.updateObject(KEY_NAME_IP, ip);
	}

	/**
	 * 获取下数值配置信息
	 * 
	 * @param baseService
	 * @param name
	 * @return
	 */
	public static Double getRateByName(IBASEDao baseDao,
			IBASEReidsDao baseRedisDao, String name) {
		String key = KEY_NAME_SYSTEM_COMMON_CONFIG + name;
		String resultStr = null;
		Object obj = baseRedisDao.searchObject(key);
		if (null != obj) {
			resultStr = obj.toString();
		} else {
			SystemCommonConfig config = new SystemCommonConfig();
			config.setConfigName(name);
			SystemCommonConfig r = (SystemCommonConfig) baseDao
					.searchSingleObject(
							SystemCommonConfigDao.SQL_NAME_SEARCH_SYSTEM_COMMON_CONFIG_BY_NAME,
							config);
			if (null != r) {
				resultStr = r.getConfigValue();
			}
			// 保存到redis中
			baseRedisDao.saveObject(key, resultStr);
		}
		if (null != resultStr) {
			return Double.valueOf(resultStr);
		}
		return null;
	}

	/**
	 * 刷新下数值配置信息
	 * 
	 * @param ip
	 */
	public static void setRate(String name, Double rate,
			IBASEReidsDao baseReidsDao) {
		String key = KEY_NAME_SYSTEM_COMMON_CONFIG + name;
		baseReidsDao.updateObject(key, rate+"");
	}

}
