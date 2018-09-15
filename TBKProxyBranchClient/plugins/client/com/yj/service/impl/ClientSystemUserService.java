package com.yj.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yj.dao.SystemUserClientConfigDao;
import com.yj.dao.SystemUserDao;
import com.yj.db.model.SystemConfig2;
import com.yj.db.model.SystemUser;
import com.yj.db.model.SystemUserClientConfig;
import com.yj.service.BaseService;
import com.yj.service.IClientSystemUserService;
import com.yj.util.EncryptionUtil;
import com.yj.util.GZIPCompress;
import com.yj.util.RedisCachUtil;

public class ClientSystemUserService extends BaseService implements
		IClientSystemUserService {
	/**
	 * 客户端登录
	 */
	@Override
	public Map login(String tbkName, String tbkPwd, String tt, String rnd) {
		Map result = new HashMap();
		SystemUser param = new SystemUser();
		param.setUserName(tbkName.trim());
		List users = baseDao.searchObjects(
				SystemUserDao.SQL_NAME_SEARCH_SYSTEM_USER_BY_USER_NAME, param);
		if (null != users && !users.isEmpty()) {
			SystemUser user = (SystemUser) users.get(0);
			if (user.getPassword().equalsIgnoreCase(tbkPwd.trim())) {
				String str = tbkName + user.getPassword() + tt + "Salt";
				result.put("str",
						EncryptionUtil.toSha1(str) + ";" + user.getProductPid());
				SystemUserClientConfig config = new SystemUserClientConfig();
				config.setUserName(tbkName);
				if (user.getGroup_name().equals(SystemUser.GROUP_NAME_EMP)) {
					config.setUserName("admin");
				}
				// 小尾巴（small_tail）参数获取
				String weiba = "";
				SystemConfig2 sc2 = RedisCachUtil.getSystemCofingByName(
						"weiba", baseDao, baseRedisDao);
				// (SystemConfig2)baseDao.searchSingleObject(SystemConfig2Dao.SQL_NAME_SEARCH_SYSTEM_CONFIG2,
				// "weiba");
				if (sc2 != null) {
					weiba = sc2.getConfigValue();
				}
				result.put("xiaoweiba", weiba);
				List configs = new ArrayList();
				Object obj = baseRedisDao.searchObject("admin_config");
				if (null != obj) {
					System.out.println("使用redis成功!");
					configs = JSONArray.parseArray(obj.toString(),
							SystemUserClientConfig.class);
				}
				result.put("configs", configs);
				result.put("jdPid", user.getJdPid());
				result.put("t", new Date().getTime());
				return result;
			}
		}
		result.put("str", "0");
		return result;
	}

	/**
	 * 客户端登录测试压缩
	 */
	public String testLogin(String tbkName, String tbkPwd, String tt, String rnd) {
		Map result = new HashMap();
		SystemUser param = new SystemUser();
		param.setUserName(tbkName.trim());
		List users = baseDao.searchObjects(
				SystemUserDao.SQL_NAME_SEARCH_SYSTEM_USER_BY_USER_NAME, param);
		if (null != users && !users.isEmpty()) {
			SystemUser user = (SystemUser) users.get(0);
			if (user.getPassword().equalsIgnoreCase(tbkPwd.trim())) {
				String str = tbkName + user.getPassword() + tt + "Salt";
				result.put("str",
						EncryptionUtil.toSha1(str) + ";" + user.getProductPid());
				SystemUserClientConfig config = new SystemUserClientConfig();
				config.setUserName(tbkName);
				if (user.getGroup_name().equals(SystemUser.GROUP_NAME_EMP)) {
					config.setUserName("admin");
				}
				// 小尾巴（small_tail）参数获取
				String weiba = "";
				SystemConfig2 sc2 = RedisCachUtil.getSystemCofingByName(
						"weiba", baseDao, baseRedisDao);
				if (sc2 != null) {
					weiba = sc2.getConfigValue();
				}
				result.put("xiaoweiba", weiba);
				List configs = new ArrayList();
				Object obj = baseRedisDao.searchObject("admin_config");
				if (null != obj) {
					configs = JSONArray.parseArray(obj.toString(),
							SystemUserClientConfig.class);
					System.out.println("使用redis成功!");
				} else {
					System.out.println("-------连数据库------------------");
					configs = baseDao
							.searchObjects(
									SystemUserClientConfigDao.SQL_NAME_SEARCH_SYSTEM_USER_CLIENT_CONFIG_BY_USER_NAME,
									config);
					baseRedisDao.saveObject(tbkName + "_config",
							JSONArray.toJSONString(configs));
					System.out.println(tbkName + "_config");
				}
				result.put("configs", configs);
				result.put("jdPid", user.getJdPid());
				result.put("t", new Date().getTime());
				String jsonStr = JSON.toJSONString(result);
				String gzipStr = new String();
				try {
					byte[] sss = GZIPCompress.doCompress(jsonStr);
					gzipStr = GZIPCompress.encode(sss);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return gzipStr;
			}
		}
		result.put("str", "0");
		String jsonStr = JSON.toJSONString(result);
		String gzipStr = new String();
		try {
			byte[] sss = GZIPCompress.doCompress(jsonStr);
			gzipStr = GZIPCompress.encode(sss);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return gzipStr;
	}

}
