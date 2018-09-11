package com.yj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.SuNingOrderDao;
import com.yj.domain.SuNingOrder;
import com.yj.domain.SystemCommonConfig;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.domain.SystemUser;
import com.yj.domain.SystemUserIntegral;
import com.yj.service.SnOrderCreateIntegralService;
import com.yj.service.impl.base.BaseIntegralServiceImpl;

/**
 * 苏宁订单计算积分业务
 * 
 * @author Administrator
 *
 */
@Service
public class SnOrderCreateIntegralServiceImpl extends BaseIntegralServiceImpl implements SnOrderCreateIntegralService {
	@Autowired
	private SuNingOrderDao suNingOrderDao;

	/**
	 * 计算积分
	 */
	@Override
	public void calIntegral() {
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_SN, "计算积分");
		List<SuNingOrder> records = suNingOrderDao.searchNotCalSnOrders();
		if (null != records && !records.isEmpty()) {
			List<SystemUserIntegral> needSaveIntegral = new ArrayList<SystemUserIntegral>();
			Double qudaoRate = searchRateByName(SystemCommonConfig.CONFIG_NAME_SUNING_QUDAO_RATE);// 渠道分成
			Double proxy1Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_SUNING_PROXY1_RATE);// 一级代理分成
			Double proxy2Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_SUNING_PROXY2_RATE);// 二级代理分成
			Double proxy3Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_SUNING_PROXY3_RATE);// 三级代理分成
			for (SuNingOrder record : records) {
				if (null == record.getChildAccountId() || record.getChildAccountId().isEmpty()) {
					continue;
				}
				SystemUser user = systemUserDao.searchSysemUserByDataBaseId(Long.valueOf(record.getChildAccountId()));
				if (null == user || isUserBan(user)) {// 有没有被ban
					continue;
				}
				createIntegral("苏宁订单", user, qudaoRate, proxy1Rate, proxy2Rate, proxy3Rate,
						record.getPrePayCommission(), record.getOrderSubmitTime(), record.getOrderLineNumber(),
						record.getGoodsNum(), record.getProductName(), record.getGoodsNum(), record.getPayAmount(),
						record.getCommissionRatio(), needSaveIntegral);
			}
			saveIntegral(needSaveIntegral);
		}
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_SN, "抓取结束");
	}

}
