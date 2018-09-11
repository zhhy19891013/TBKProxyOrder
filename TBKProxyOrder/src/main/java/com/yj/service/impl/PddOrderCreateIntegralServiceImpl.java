package com.yj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.CloudOrderPddDao;
import com.yj.domain.CloudOrderPdd;
import com.yj.domain.SystemCommonConfig;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.domain.SystemUser;
import com.yj.domain.SystemUserIntegral;
import com.yj.service.PddOrderCreateIntegralService;
import com.yj.service.impl.base.BaseIntegralServiceImpl;

/**
 * 拼多多创建积分
 * 
 * @author Administrator
 *
 */
@Service
public class PddOrderCreateIntegralServiceImpl extends BaseIntegralServiceImpl
		implements PddOrderCreateIntegralService {
	@Autowired
	private CloudOrderPddDao cloudOrderPddDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 计算积分
	 */
	@Override
	public void calIntegral() {
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_PDD, "计算积分");
		Double qudaoRate = searchRateByName(SystemCommonConfig.CONFIG_NAME_PDD_QUDAO_RATE);// 渠道分成
		Double proxy1Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_PDD_PROXY1_RATE);// 一级代理分成
		Double proxy2Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_PDD_PROXY2_RATE);// 二级代理分成
		Double proxy3Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_PDD_PROXY3_RATE);// 三级代理分成
		// 查询所有没有计算积分的订单
		List<CloudOrderPdd> paymList = cloudOrderPddDao.searchNotCalPddOrders();
		logger.info("共有" + paymList.size() + "条拼多多订单需要计算");
		if (null != paymList && !paymList.isEmpty()) {
			List<SystemUserIntegral> needSaveIntegral = new ArrayList<SystemUserIntegral>();
			for (CloudOrderPdd record : paymList) {
				SystemUser user = systemUserDao.searchSystemUserByPddPid(record.getP_id());
				if (null == user || isUserBan(user)) {// 有没有被ban
					continue;
				}
				createIntegral("拼多多订单", user, qudaoRate, proxy1Rate, proxy2Rate, proxy3Rate, record.getCommission(),
						record.getOrder_create_time(), record.getOrder_sn(), record.getGoods_id(),
						record.getGoods_name(), record.getGoods_thumbnail_url(), record.getRealAmount(),
						record.getPromotion_rate() / 100, needSaveIntegral);
			}
			saveIntegral(needSaveIntegral);
		}
		// 计算手动提交的积分
		// 开始计算手动提交的积分
		List<SystemUserIntegral> integrals = systemUserIntegralDao.searchAllCommitSystemUserIntegral();
		if (null != integrals && !integrals.isEmpty()) {
			List<SystemUserIntegral> needSaveIntegral = new ArrayList<SystemUserIntegral>();
			for (SystemUserIntegral integral : integrals) {
				CloudOrderPdd record = cloudOrderPddDao.searchPddOrderById(integral.getOrder_number());
				if (null == record) {
					continue;
				}
				// 找到相关的用户
				SystemUser user = systemUserDao.searchSystemUserByUserName(integral.getUser_name());
				if (null == user || isUserBan(user)) {
					continue;
				}
				if (user.getGroup_name().equals(SystemUser.GROUP_NAME_QUDAO)) {
					continue;
				}
				createIntegral("拼多多订单", user, qudaoRate, proxy1Rate, proxy2Rate, proxy3Rate, record.getCommission(),
						record.getOrder_create_time(), record.getOrder_sn(), record.getGoods_id(),
						record.getGoods_name(), record.getGoods_thumbnail_url(), record.getRealAmount(),
						record.getPromotion_rate() / 100, needSaveIntegral);
			}
			saveIntegral(needSaveIntegral);
		}
		// 更新下订单的用户名
		cloudOrderPddDao.updatePddOrderUserNameAll();
		// 更新下拼多多退款积分
		systemUserIntegralDao.updateSystemUserIntegralBckByPdd();
		// 预估变有效
		systemUserIntegralDao.updateSystemUserIntegralPddRealIntegral();
		// 更新下管理员下单的拼多多订单的用户名
		cloudOrderPddDao.updateAdminPddOrder();
		// 删除不是这个平台的拼多多订单
		cloudOrderPddDao.deleteNotThisPlatform();
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_PDD, "抓取结束");
		logger.info("拼多多同步结束");
	}

}
