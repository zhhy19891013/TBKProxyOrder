package com.yj.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.PaymentRecordDao;
import com.yj.domain.PaymentRecord;
import com.yj.domain.SystemCommonConfig;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.domain.SystemUser;
import com.yj.domain.SystemUserIntegral;
import com.yj.service.TbOrderCreateIntegralService;
import com.yj.service.impl.base.BaseIntegralServiceImpl;
import com.yj.util.DateUtil;

/**
 * 淘宝订单计算积分业务
 * 
 * @author Administrator
 *
 */
@Service
public class TbOrderCreateIntegralServiceImpl extends BaseIntegralServiceImpl implements TbOrderCreateIntegralService {

	@Autowired
	private PaymentRecordDao paymentRecordDao;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 计算积分
	 */
	@Override
	public void calIntegral() {
		logger.info("开始计算淘宝订单的积分");
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_TB, "计算积分");
		// 开始计算积分
		PaymentRecord param = new PaymentRecord();
		param.setCreateDate(DateUtil.formateTodayDate(40));
		List<PaymentRecord> paymList = paymentRecordDao.searchAllNotCalPayment(param);
		Double qudaoRate = searchRateByName(SystemCommonConfig.CONFIG_NAME_THREE_QUDAO_RATE);// 渠道分成
		Double proxy1Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_THREEE_PROXY1_RATE);// 一级代理分成
		Double proxy2Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_THREE_PROXY2_RATE);// 二级代理分成
		Double proxy3Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_THREE_PROXY3_RATE);// 三级代理分成
		if (null != paymList && !paymList.isEmpty()) {
			Map<String, SystemUser> pidMapUser = getSystemInfo();
			logger.info("共有" + paymList.size() + "条订单需要计算");
			List<SystemUserIntegral> needSaveIntegral = new ArrayList<SystemUserIntegral>();
			for (PaymentRecord record : paymList) {
				SystemUser user = pidMapUser.get(record.getPid());
				if (null == user || isUserBan(user)) {
					continue;
				}
				createIntegral(record.getTbName(), user, qudaoRate, proxy1Rate, proxy2Rate, proxy3Rate,
						record.getFeeString(), record.getCreateTime(), record.getTaobaoTradeParentId(),
						record.getAuctionId(), record.getAuctionTitle(), record.getAuctionUrl(),
						record.getTotalAlipayFeeString(), record.getFinalDiscountToString(), needSaveIntegral);
			}
			saveIntegral(needSaveIntegral);
		}
		// 开始计算手动提交的积分
		List<SystemUserIntegral> integrals = systemUserIntegralDao.searchAllCommitSystemUserIntegral();
		if (null != integrals && !integrals.isEmpty()) {
			List<SystemUserIntegral> needSaveIntegral = new ArrayList<SystemUserIntegral>();
			for (SystemUserIntegral integral : integrals) {
				List<PaymentRecord> list = paymentRecordDao
						.searchPaymentBytaobaoTradeParentId(integral.getOrder_number());
				if (null == list || list.isEmpty()) {
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
				PaymentRecord record = list.get(0);
				createIntegral(record.getTbName(), user, qudaoRate, proxy1Rate, proxy2Rate, proxy3Rate,
						record.getFeeString(), record.getCreateTime(), record.getTaobaoTradeParentId(),
						record.getAuctionId(), record.getAuctionTitle(), record.getAuctionUrl(),
						record.getTotalAlipayFeeString(), record.getFinalDiscountToString(), needSaveIntegral);
			}
			saveIntegral(needSaveIntegral);
		}
		// 算好积分后更新下订单的用户名
		paymentRecordDao.updatePaymentRecordUserNameByIntegral();
		// 更新退款积分信息
		SystemUserIntegral inte = new SystemUserIntegral();
		inte.setOrderDate(new Timestamp(DateUtil.formateTodayDate(90).getTime()));
		// 删除超过90天的退款积分
		systemUserIntegralDao.deleteBackOrder(inte);
		// 把更新下退款积分
		systemUserIntegralDao.updateSystemUserIntegralExpireIntegral();
		// 预估变为有效
		systemUserIntegralDao.updateSystemUserIntegralRealIntegral();
		logger.info("转换阿里积分");
		systemUserIntegralDao.updateAliIntegral();
		logger.info("淘宝同步结束");
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_TB, "抓取结束");
	}

}
