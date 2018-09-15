package com.yj.service.impl;

import com.yj.db.model.SystemConfig2;
import com.yj.service.BaseService;
import com.yj.service.ISystemConfigService;
import com.yj.util.RedisCachUtil;

public class SystemConfigService extends BaseService implements
		ISystemConfigService {

	@Override
	public String searchSystemConfig() {
		// 获取下直播间域名
		SystemConfig2 sc = RedisCachUtil.getSystemCofingByName(
				SystemConfig2.CONFIG_NAME_ZHIBO_WEB, baseDao, baseRedisDao);
		return sc.getConfigValue();
	}


}
