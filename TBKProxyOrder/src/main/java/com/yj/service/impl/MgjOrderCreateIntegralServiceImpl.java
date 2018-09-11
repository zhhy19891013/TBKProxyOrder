package com.yj.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.MogujieOrderDetailsDao;
import com.yj.domain.MogujieOrderDetails;
import com.yj.domain.SystemCommonConfig;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.domain.SystemUser;
import com.yj.service.MgjOrderCreateIntegralService;
import com.yj.service.impl.base.BaseIntegralServiceImpl;
import com.yj.util.DateUtil;

/**
 * 蘑菇街订单创建积分业务
 * 
 * @author Administrator
 *
 */
@Service
public class MgjOrderCreateIntegralServiceImpl extends BaseIntegralServiceImpl
		implements MgjOrderCreateIntegralService {

	@Autowired
	private MogujieOrderDetailsDao mogujieOrderDetailsDao;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 计算积分
	 */
	@Override
	public void calIntegral() {
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_MGJ, "计算积分");
		MogujieOrderDetails param = new MogujieOrderDetails();
		param.setOrderTime(DateUtil.formateTodayDate(90));
		List<MogujieOrderDetails> paymList = mogujieOrderDetailsDao.searchAllNotCalMgjOrder(param);
		if (null != paymList && !paymList.isEmpty()) {
			logger.info("共有" + paymList.size() + "条蘑菇街订单需要计算");
			List needSaveIntegral = new ArrayList();
			Double qudaoRate = searchRateByName(SystemCommonConfig.CONFIG_NAME_MGJ_QUDAO_RATE);// 渠道分成
			Double proxy1Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_MGJ_PROXY1_RATE);// 一级代理分成
			Double proxy2Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_MGJ_PROXY2_RATE);// 二级代理分成
			Double proxy3Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_MGJ_PROXY3_RATE);// 三级代理分成
			for (MogujieOrderDetails record : paymList) {
				SystemUser user = systemUserDao.searchSystemUserByMgjPid(record.getGroupId());
				if (null == user || isUserBan(user)) {// 有没有被ban
					continue;
				}
				createIntegral("蘑菇街订单", user, qudaoRate, proxy1Rate, proxy2Rate, proxy3Rate, record.getExpense(),
						new Timestamp(record.getOrderTime().getTime()), record.getOrderNo(), record.getProductNo(),
						record.getName(), record.getProductUrl(), record.getPrice(), record.getRate(),
						needSaveIntegral);
			}
			saveIntegral(needSaveIntegral);
		}
		logger.info("更新蘑菇街积分开始");
		// 更新蘑菇街订单用户名
		mogujieOrderDetailsDao.updateMgjOrderDetailUserNameFromIntegralAll();
		// 更新蘑菇街退款订单
		mogujieOrderDetailsDao.updateSystemUserIntegralExpireIntegralByMgj();
		logger.info("更新蘑菇街积分结束");
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_MGJ, "抓取结束");
	}

}
