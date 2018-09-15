package com.yj.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yj.dao.SystemCommonConfigDao;
import com.yj.domain.SystemCommonConfig;
import com.yj.service.JdOrderCreateIntegralService;
import com.yj.service.JdOrderGrapService;
import com.yj.service.MgjOrderCreateIntegralService;
import com.yj.service.MgjOrderGrapService;
import com.yj.service.PddOrderCreateIntegralService;
import com.yj.service.PddOrderGrapService;
import com.yj.service.SnOrderCreateIntegralService;
import com.yj.service.SnOrderGrapService;
import com.yj.service.TbOrderCreateIntegralService;
import com.yj.service.TbOrderGrapService;
import com.yj.util.DateUtil;

/**
 * 同步订单业务
 * 
 * @author Administrator
 *
 */
@Component
public class OrderRefreshTask {
	@Autowired
	private SystemCommonConfigDao systemCommonConfigDao;
	@Autowired
	private TbOrderGrapService tbOrderGrapServiceImpl;
	@Autowired
	private TbOrderCreateIntegralService tbCreateIntegralServiceImpl;
	@Autowired
	private PddOrderGrapService pddOrderGrapServiceImpl;
	@Autowired
	private PddOrderCreateIntegralService pddOrderCreateIntegralServiceImpl;
	@Autowired
	private SnOrderGrapService snOrderGrapServiceImpl;
	@Autowired
	private SnOrderCreateIntegralService snOrderCreateIntegralServiceImpl;
	@Autowired
	private MgjOrderGrapService mgjOrderGrapServiceImpl;
	@Autowired
	private MgjOrderCreateIntegralService mgjOrderCreateIntegralServiceImpl;
	@Autowired
	private JdOrderGrapService jdOrderGrapServiceImpl;
	@Autowired
	private JdOrderCreateIntegralService JdOrderCreateIntegralServiceImpl;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 执行方法
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void execute() {
		logger.info("全部订单开始:" + DateUtil.lastXDaysSFM(0));
		if (!getQzLock()) {
			logger.info("订单抓取已在服务器执行");
			return;
		}
		changeQzLock("1");
		// 淘宝同步订单和计算积分'
		try {
			tbOrderGrapServiceImpl.grap();
			tbCreateIntegralServiceImpl.calIntegral();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拼多多同步和计算积分
		try {
			pddOrderGrapServiceImpl.grap();
			pddOrderCreateIntegralServiceImpl.calIntegral();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 京东同步和计算积分
		try {
			jdOrderGrapServiceImpl.grap();
			JdOrderCreateIntegralServiceImpl.calIntegral();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 蘑菇街同步和计算积分
		try {
			mgjOrderGrapServiceImpl.grap();
			mgjOrderCreateIntegralServiceImpl.calIntegral();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 苏宁同步和计算积分
		try {
			snOrderGrapServiceImpl.grap();
			snOrderCreateIntegralServiceImpl.calIntegral();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 自营商城同步和计算积分
		logger.info("全部订单结束:" + DateUtil.lastXDaysSFM(0));
		changeQzLock("0");
	}

	/**
	 * 获取qz任务的执行状态
	 * 
	 * @return
	 */
	public boolean getQzLock() {
		boolean result = true;
		SystemCommonConfig bean = systemCommonConfigDao
				.searchSystemCommonConfigByName(SystemCommonConfig.CONFIG_NAME_QZ_SWITCH);
		if (bean != null && "1".equals(bean.getConfigValue())) {
			result = false;
		}
		return result;
	}

	/**
	 * 改qz执行状态
	 */
	public void changeQzLock(String flag) {
		SystemCommonConfig config = new SystemCommonConfig();
		config.setConfigName(SystemCommonConfig.CONFIG_NAME_QZ_SWITCH);
		config.setConfigValue(flag);
		systemCommonConfigDao.updateSystemCommonConfig(config);
	}

}
