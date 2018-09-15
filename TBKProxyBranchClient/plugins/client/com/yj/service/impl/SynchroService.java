package com.yj.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.dao.CloudProductXbTjDao;
import com.yj.dao.SystemUserClientConfigDao;
import com.yj.dao.TbSessionKeyDao;
import com.yj.db.model.CloudProductXbTj;
import com.yj.db.model.SystemUserClientConfig;
import com.yj.db.model.TbSessionKey;
import com.yj.service.BaseService;
import com.yj.service.ISynchroService;

public class SynchroService extends BaseService implements ISynchroService {

	public void synchro() {
		while (true) {
			System.out.println("开始同步下商品" + new Date());
			List all = baseDao.searchObjects(
					CloudProductXbTjDao.SEARCH_ALL_XB_TJ, null);
			// 保存到redis
			baseRedisDao.deleteObject("xbs");
			baseRedisDao.saveObject("xbs", JSONArray.toJSONString(all));
			System.out.println("同步小编产品:"+all.size());
			SystemUserClientConfig config = new SystemUserClientConfig();
			config.setUserName("admin");
			List configs = baseDao
					.searchObjects(
							SystemUserClientConfigDao.SQL_NAME_SEARCH_SYSTEM_USER_CLIENT_CONFIG_BY_USER_NAME,
							config);
			baseRedisDao.deleteObject("admin_config");
			baseRedisDao.saveObject("admin_config",
					JSONArray.toJSONString(configs));
			System.out.println("同步完配置");
			baseRedisDao.deleteObject("system_conifg2_zhiboWeb");
			//刷新下sessionkey
			if(null!=all&&!all.isEmpty()){
				CloudProductXbTj xb = (CloudProductXbTj) all.get(0);
				baseRedisDao.deleteObject(xb.getTbName()+"_sessionKey");
				TbSessionKey param = new TbSessionKey();
				param.setTbName(xb.getTbName());
				TbSessionKey key = (TbSessionKey) baseDao.searchSingleObject(
						TbSessionKeyDao.SEARCH_SESSIONKEY_BY_TB_NAME, param);
				if(null!=key){
					System.out.println("同步完sessionkey:"+xb.getTbName()+"_sessionKey"+"="+key.getSessionKey());
					baseRedisDao.saveObject(xb.getTbName()+"_sessionKey",JSONObject.toJSONString(key));
				}
			}
			baseRedisDao.deleteObject("system_common_config_xbts");
			System.out.println("同步结束，休息一分钟");
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String searchXBbyTkl(String tkl) {
		Object o = baseRedisDao.searchObject(tkl);
		if (null != o) {
			return o.toString();
		}
		return "error";
	}
	
	
}
