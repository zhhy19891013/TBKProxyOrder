package com.yj.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.JDFreeOrderDetailsDao;
import com.yj.dao.JDFreeReturnIntegralDao;
import com.yj.dao.JDOrderDetailsDao;
import com.yj.domain.JDFreeOrderDetails;
import com.yj.domain.JDFreeReturnIntegral;
import com.yj.domain.JDOrderDetails;
import com.yj.domain.SystemCommonConfig;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.domain.SystemUser;
import com.yj.domain.SystemUserIntegral;
import com.yj.service.JdOrderCreateIntegralService;
import com.yj.service.impl.base.BaseIntegralServiceImpl;
import com.yj.util.DateUtil;
import com.yj.util.ListUtil;

/**
 * 京东订单创建积分
 * 
 * @author Administrator
 *
 */
@Service
public class JdOrderCreateIntegralServiceImpl extends BaseIntegralServiceImpl implements JdOrderCreateIntegralService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JDOrderDetailsDao jDOrderDetailsDao;
	@Autowired
	private JDFreeOrderDetailsDao jDFreeOrderDetailsDao;
	@Autowired
	private JDFreeReturnIntegralDao jDFreeReturnIntegralDao;

	/**
	 * 创建积分
	 */
	@Override
	public void calIntegral() {
		logger.info("开始计算京东积分");
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_JD, "计算积分");
		// 先算正常积分
		calNormal();
		// 再算免单积分
		calFree();
		// 更新积分状态
		updateJdIntegral();
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_JD, "抓取结束");
		logger.info("计算京东积分结束");
	}

	/************************
	 * 私有方法
	 *************************/
	/**
	 * 计算正常积分
	 */
	private void calNormal() {
		Double qudaoRate = searchRateByName(SystemCommonConfig.CONFIG_NAME_JD_QUDAO_RATE);// 渠道分成
		Double proxy1Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_JD_PROXY1_RATE);// 一级代理分成
		Double proxy2Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_JD_PROXY2_RATE);// 二级代理分成
		Double proxy3Rate = searchRateByName(SystemCommonConfig.CONFIG_NAME_JD_PROXY3_RATE);// 三级代理分成
		JDOrderDetails param = new JDOrderDetails();
		param.setOrdertime(DateUtil.formateTodayDate(30));
		List<JDOrderDetails> paymList = jDOrderDetailsDao.searchAllNotCalJdOrder(param);
		if (null != paymList && !paymList.isEmpty()) {
			logger.info("共有" + paymList.size() + "条京东订单需要计算");
			List needSaveIntegral = new ArrayList();
			for (JDOrderDetails record : paymList) {
				SystemUser user = systemUserDao.searchSystemUserByJdPid(record.getSubunionid());
				if (null == user || isUserBan(user)) {// 有没有被ban
					continue;
				}
				createIntegral("京东订单", user, qudaoRate, proxy1Rate, proxy2Rate, proxy3Rate, record.getCommision(),
						new Timestamp(record.getOrdertime().getTime()), record.getOrderid(), record.getItemId(),
						record.getProductname(), record.getProdpic(), record.getCosts(), record.getCommissionRate(),
						needSaveIntegral);
			}
			saveIntegral(needSaveIntegral);
		}
		// 处理手动提交的积分
		// 开始计算手动提交的积分
		List<SystemUserIntegral> integrals = systemUserIntegralDao.searchAllCommitSystemUserIntegral();
		if (null != integrals && !integrals.isEmpty()) {
			List<SystemUserIntegral> needSaveIntegral = new ArrayList<SystemUserIntegral>();
			for (SystemUserIntegral integral : integrals) {
				JDOrderDetails record = jDOrderDetailsDao.searchOrderDetailsByOrderId(integral.getOrder_number());
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
				createIntegral("京东订单", user, qudaoRate, proxy1Rate, proxy2Rate, proxy3Rate, record.getCommision(),
						new Timestamp(record.getOrdertime().getTime()), record.getOrderid(), record.getItemId(),
						record.getProductname(), record.getProdpic(), record.getCosts(), record.getCommissionRate(),
						needSaveIntegral);
			}
			saveIntegral(needSaveIntegral);
		}
	}

	/**
	 * 计算免单积分
	 */
	private void calFree() {
		// 查免单活动的时间
		JDFreeReturnIntegral integral = new JDFreeReturnIntegral();
		String freeBeginDate = getConfigByName("freeBeginDate");
		String freeEndDate = getConfigByName("freeEndDate");
		Long begin = null;
		Long end = null;
		if (freeBeginDate != null) {
			begin = Long.valueOf(freeBeginDate);
		}
		if (freeEndDate != null) {
			end = Long.valueOf(freeEndDate);
		}
		Date beginDate;
		Date endDate;
		if (begin != null && end != null) {
			beginDate = new Date(begin);
			endDate = new Date(end);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			integral.setCreateTime1(sdf.format(beginDate));
			integral.setCreateTime2(sdf.format(endDate));
		} else {
			logger.info("京东免单活动时间未设置，无法计算积分");
			return;
		}
		// 待计算订单
		Map<String, Long> orderNumMap = new HashMap<>();
		// 剩余订单
		Map<String, Long> orderNumLeft = new HashMap<>();
		// 活动时间内完成的免单订单量
		Map<String, Long> orderNumFinish = new HashMap<>();
		JDFreeOrderDetails param = new JDFreeOrderDetails();
		param.setOrdertime(DateUtil.formateTodayDate(90));
		// 查询出所有需要计算的京东免单订单
		List<JDFreeOrderDetails> paymList = jDFreeOrderDetailsDao.searchAllNotCalJdFreeOrderDetails(param);
		logger.info("共有" + paymList.size() + "条【京东免单】订单需要计算");
		List<JDFreeReturnIntegral> needSaveIntegral = new ArrayList<JDFreeReturnIntegral>();
		// 查京东免单限制单数
		Long limitNum = Long.valueOf(getConfigByName(SystemCommonConfig.CONFIG_NAME_JD_ORDER_LIMIT));
		if (null != paymList && !paymList.isEmpty()) {
			for (JDFreeOrderDetails record : paymList) {
				// 是否在免单订单时间内，如果不在就不需要生成积分
				if (!isInFreeTime(record, begin, end)) {
					continue;
				}
				SystemUser user = systemUserDao.searchSystemUserByJdFreePid(record.getSubunionid());
				if (null == user) {
					continue;
				}
				// 查这个用户活动时间内的免单订单量
				// 查活动时间内数据库存在单数
				integral.setUser_name(user.getUserName());
				Long orderNum = orderNumFinish.get(user.getUserName());
				if (orderNum == null) {
					orderNum = jDFreeReturnIntegralDao.searchMobileJdFreeProxyOrderCount(integral);
					if (orderNum == null) {
						orderNum = Long.valueOf(0);
					} else {
						orderNumFinish.put(user.getUserName(), orderNum);
					}
				}
				// 本次抓取的、已列入待计算list的订单量
				Long num = orderNumMap.get(user.getUserName());
				if (num == null) {
					num = Long.valueOf("0");
				}
				// 先看map里有没有剩余单量记录
				// 剩余的免单订单量
				Long left = orderNumLeft.get(user.getUserName());
				if (left == null) {
					left = Long.valueOf("0");
					// 待计算的订单量和数据库的订单量的总和
					Long all = new BigDecimal(orderNum).add(new BigDecimal(num)).longValue();
					if (all < limitNum) {
						// 剩余单量 = 限制单量 - 待计算单量和数据库单量的和
						left = new BigDecimal(limitNum).subtract(new BigDecimal(all)).longValue();
					}
				}
				// 如果数据库单数小于限制单数，计算出剩余单数，限制抓取的list长度
				if (left > 0) {
					// 先判断下是否已经生成过了 查询非提交状态的积分
					List notCommit = jDFreeReturnIntegralDao.searchNotCommitJdFreeIntegral(record.getOrderid());
					if (null != notCommit && !notCommit.isEmpty()) {// 如果之前已经处理过了那么就跳过下面的步骤
						logger.info("该免单订单已经存在" + record.getOrderid());
						continue;
					}
					// 开始算积分
					// 根据orderid查询出订单明细
					// 计算积分
					JDFreeReturnIntegral in = creatJdUserIntegral(record, Double.valueOf(100), user.getUserName(),
							user.getUserName(), user.getGroup_name());
					if (null != in) {
						needSaveIntegral.add(in);
						// 待计算订单+1
						// 剩余订单-1
						orderNumMap.put(user.getUserName(), new BigDecimal(num).add(new BigDecimal("1")).longValue());
						orderNumLeft.put(user.getUserName(),
								new BigDecimal(left).subtract(new BigDecimal("1")).longValue());
					}
				}
			}
		}
		logger.info("共有" + needSaveIntegral.size() + "条免单积分数据需要更新");
		if (!needSaveIntegral.isEmpty()) {
			List all = ListUtil.makeSubList(needSaveIntegral, 500);
			for (Object o : all) {
				List list = (List) o;
				logger.info("新增积分:" + list.size());
				jDFreeReturnIntegralDao.deleteJDFreeIntegralByNumOutOfLine(list);
				jDFreeReturnIntegralDao.addJDFreeIntegralBatch(list);
			}
		}

	}

	/**
	 * 判断是否在免单区间内
	 * 
	 * @param baseDao
	 * @param record
	 * @return
	 */
	private boolean isInFreeTime(JDOrderDetails record, long begin, long end) {
		if (record.getOrdertime().getTime() < begin || record.getOrdertime().getTime() >= end) {
			return false;
		}
		return true;
	}

	/**
	 * 更新下京东退款的积分 以及有效的积分
	 */
	private void updateJdIntegral() {
		logger.info("更新过期京东积分开始");
		// 更新退款订单
		systemUserIntegralDao.updateSystemUserIntegralJdExpireIntegral();
		// 预估变有效
		systemUserIntegralDao.updateSystemUserIntegralJdRealIntegral();
		// 更新京东订单的用户名
		jDOrderDetailsDao.updateJdOrderDetailUserNameFromIntegral();
		logger.info("更新过期京东积分结束");
		logger.info("更新京东免单积分开始");
		jDFreeReturnIntegralDao.updateJDFreeIntegralRealIntegral();
		logger.info("转化免单积分结束，开始更新退款积分");
		jDFreeReturnIntegralDao.updateJDFreeIntegralExpireIntegralByJDFree();
		jDFreeOrderDetailsDao.updateJdFreeOrderDetailUserNameBatch();
		logger.info("更新京东免单积分结束");
	}

	/**
	 * 创建京东免单积分
	 * 
	 * @param record
	 * @param rate
	 * @param userName
	 * @param proxyName
	 * @return
	 */
	private JDFreeReturnIntegral creatJdUserIntegral(JDFreeOrderDetails record, double rate, String userName,
			String proxyName, String groupName) {
		JDFreeReturnIntegral intgral = new JDFreeReturnIntegral();
		// 查询返积分限制上限
		Double limit = searchRateByName("jd_integral_limit");
		Double integral = record.getCosts();
		if (limit >= integral) {
			// 如果实际积分小于等于上限，则全返
			intgral.setIntegral(integral);
		} else {
			// 如果实际积分大于上限，则返上限积分
			intgral.setIntegral(limit);
			return null;
		}
		// 乘以分成
		intgral.setUser_name(userName);
		intgral.setAuctionTitle(record.getProductname());
		intgral.setCreate_time(new Timestamp(System.currentTimeMillis()));
		intgral.setOrderDate(new Timestamp(record.getOrdertime().getTime()));
		intgral.setOrderStatus(JDFreeReturnIntegral.INTEGRAL_STATUS_NORMAL);
		intgral.setIntegralOrign("订单提交");
		intgral.setIntegralPrividor(proxyName);// 积分提供人（代理名称）
		if (null != record.getPlus() && record.getPlus().equals("1")) {
			intgral.setIntegralNote("plush会员，无效订单");
			intgral.setOrderStatus(JDFreeReturnIntegral.INTEGRAL_STATUS_INVALID);
		} else if (record.getCommissionRate() < 40) {
			intgral.setIntegralNote("比例不对，无效订单");
			intgral.setOrderStatus(JDFreeReturnIntegral.INTEGRAL_STATUS_INVALID);
		} else {
			intgral.setIntegralNote(JDFreeReturnIntegral.INTEGRAL_STATUS_NORMAL);
		}
		intgral.setTbName("京东订单");
		intgral.setTotalAlipayFeeString(integral);// 佣金
		intgral.setIntegralRate(rate);
		intgral.setOrder_number(record.getOrderid());
		intgral.setGroupName(groupName);
		intgral.setRealPay(record.getCosts());
		return intgral;
	}
}
