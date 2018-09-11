package com.yj.service.impl.base;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yj.dao.SystemUserDao;
import com.yj.dao.SystemUserIntegralDao;
import com.yj.domain.SystemUser;
import com.yj.domain.SystemUserIntegral;
import com.yj.dto.tb.SystemTbPidBean;

/**
 * 创建积分业务的父类
 * 
 * @author Administrator
 *
 */
public class BaseIntegralServiceImpl extends BaseCpsOrderServiceImpl {
	@Autowired
	protected SystemUserDao systemUserDao;
	@Autowired
	protected SystemUserIntegralDao systemUserIntegralDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 创建积分
	 */
	protected void createIntegral(String tbName, SystemUser user, double qudaoRate, double proxy1Rate,
			double proxy2Rate, double proxy3Rate, double commission, Timestamp createTime, String orderId,
			String itemId, String itemTitle, String href, double realPay, double commissionRate,
			List<SystemUserIntegral> needSave) {
		// 计算积分
		if (null != systemUserIntegralDao.searchNotCommitIntegral(orderId)) {
			logger.info("该订单的积分已经存在" + orderId);
			return;
		}
		needSave.addAll(generate(tbName, user, qudaoRate, proxy1Rate, proxy2Rate, proxy3Rate, commission, createTime,
				orderId, itemId, itemTitle, href, realPay, commissionRate));
	}

	/**
	 * 保存积分
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void saveIntegral(List<SystemUserIntegral> needSaveIntegral) {
		logger.info("共有" + needSaveIntegral.size() + "条积分数据需要更新");
		if (!needSaveIntegral.isEmpty()) {
			// 先删掉预提交的
			List all = makeSubList(needSaveIntegral, 500);
			for (Object o : all) {
				List list = (List) o;
				logger.info("新增积分:" + list.size());
				systemUserIntegralDao.deleteSystemUserIntegralByOrderNumbers(list);
				systemUserIntegralDao.addSystemUserIntegralBatch(list);
			}
		}
	}

	/**
	 * 实时获取用户信息
	 * 
	 * @return
	 */
	protected Map<String, SystemUser> getSystemInfo() {
		List<SystemUser> allProxyUser = systemUserDao.searchAllNotAdminUser();
		Map<String, SystemUser> pidMapUser = new HashMap<String, SystemUser>();
		if (!allProxyUser.isEmpty()) {
			for (Object o : allProxyUser) {
				SystemUser su = (SystemUser) o;
				if (null != su.getPidInfo() && !su.getGroup_name().equals(SystemUser.GROUP_NAME_QUDAO)
						&& !su.getGroup_name().equals(SystemUser.GROUP_NAME_EMP)) {
					List<SystemTbPidBean> pidBeans = JSON.parseArray(su.getPidInfo(), SystemTbPidBean.class);
					for (SystemTbPidBean bean : pidBeans) {
						String pid = bean.getPid();
						String[] str = pid.split("_");
						pidMapUser.put(str[2], su);
					}
				}
			}
		}
		return pidMapUser;
	}

	/**
	 * 判断用户是否被禁用
	 * 
	 * @param user
	 * @return
	 */
	protected boolean isUserBan(SystemUser user) {
		if (null == user.getBanTime() || new Date().after(user.getBanTime())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 订单生成积分
	 */
	public List<SystemUserIntegral> generate(String tbName, SystemUser user, double qudaoRate, double proxy1Rate,
			double proxy2Rate, double proxy3Rate, double commission, Timestamp createTime, String orderId,
			String itemId, String itemTitle, String href, double realPay, double commissionRate) {
		List<SystemUserIntegral> result = new ArrayList<SystemUserIntegral>();
		// 先计算渠道积分
		SystemUserIntegral qudaoIntegral = creatUserIntegral(tbName, qudaoRate, user.getQudaoName(), user.getUserName(),
				SystemUser.GROUP_NAME_QUDAO, commission, createTime, orderId, itemId, itemTitle, href, realPay,
				commissionRate);
		result.add(qudaoIntegral);
		// 找两次父类
		// 一层关系
		SystemUser father1 = systemUserDao.searchSystemUserByUserName(user.getFatherName());
		if (null == father1 || father1.getGroup_name().equals(SystemUser.GROUP_NAME_QUDAO)) {// 上面没有代理了
			result.add(
					creatUserIntegral(tbName, proxy3Rate, user.getUserName(), user.getUserName(), user.getGroup_name(),
							commission, createTime, orderId, itemId, itemTitle, href, realPay, commissionRate));
			return result;
		}
		// 上级有代理，再找下上级的上级
		SystemUser father2 = systemUserDao.searchSystemUserByUserName(father1.getFatherName());
		// 两层关系
		if (null == father2 || father2.getGroup_name().equals(SystemUser.GROUP_NAME_QUDAO)) {
			result.add(
					creatUserIntegral(tbName, proxy3Rate, user.getUserName(), user.getUserName(), user.getGroup_name(),
							commission, createTime, orderId, itemId, itemTitle, href, realPay, commissionRate));
			result.add(creatUserIntegral(tbName, proxy2Rate, father1.getUserName(), user.getUserName(),
					father1.getGroup_name(), commission, createTime, orderId, itemId, itemTitle, href, realPay,
					commissionRate));
			return result;
		}
		// 有三层关系按三层计算
		result.add(creatUserIntegral(tbName, proxy3Rate, user.getUserName(), user.getUserName(), user.getGroup_name(),
				commission, createTime, orderId, itemId, itemTitle, href, realPay, commissionRate));
		result.add(creatUserIntegral(tbName, proxy2Rate, father1.getUserName(), user.getUserName(),
				father1.getGroup_name(), commission, createTime, orderId, itemId, itemTitle, href, realPay,
				commissionRate));
		result.add(creatUserIntegral(tbName, proxy1Rate, father2.getUserName(), user.getUserName(),
				father2.getGroup_name(), commission, createTime, orderId, itemId, itemTitle, href, realPay,
				commissionRate));
		return result;
	}

	/**
	 * 创建有效的积分
	 * 
	 * @param record
	 * @param rate
	 * @param userName
	 * @param proxyName
	 * @return
	 */
	protected SystemUserIntegral creatUserIntegral(String tbName, double rate, String userName, String proxyName,
			String groupName, double commission, Timestamp createTime, String orderId, String itemId, String itemTitle,
			String href, double realPay, double commissionRate) {
		SystemUserIntegral intgral = new SystemUserIntegral();
		intgral.setIntegral(calIntegral(rate, commission));
		// 乘以分成
		intgral.setUser_name(userName);// 积分所属人
		intgral.setIntegralPrividor(proxyName);// 积分提供人（代理名称）
		intgral.setGroupName(groupName);
		intgral.setOrderDate(createTime);
		intgral.setTotalAlipayFeeString(commission);// 佣金
		intgral.setIntegralRate(rate);// 积分比
		intgral.setOrder_number(orderId);// 订单号
		intgral.setAuctionId(itemId);// 商品id
		intgral.setAuctionTitle(itemTitle);// 商品标题
		intgral.setAuctionUrl(href);// 商品链接
		intgral.setRealPay(realPay);// 实际付款金额
		intgral.setFinalDiscountToString(commissionRate);// 佣金率
		intgral.setCreate_time(new Timestamp(System.currentTimeMillis()));
		intgral.setOrderStatus(SystemUserIntegral.INTEGRAL_STATUS_NORMAL);
		intgral.setIntegralOrign("订单提交");
		intgral.setIntegralNote(SystemUserIntegral.INTEGRAL_STATUS_NORMAL);
		intgral.setTbName(tbName);
		return intgral;
	}

	/**
	 * 积分=成交金额*积分率*分成 保留两位小数
	 * 
	 * @param integralRate
	 * @param money
	 * @param shareRate
	 * @return
	 */
	private double calIntegral(double integralRate, double money) {
		BigDecimal b1 = new BigDecimal("" + integralRate);
		BigDecimal b2 = new BigDecimal("100");
		BigDecimal b3 = new BigDecimal("" + money);
		return b1.divide(b2).multiply(b3).setScale(2, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 分页
	 **/
	protected List makeSubList(List allList, int pageSize) {
		List list = new ArrayList();
		int pageNum = 0;
		List pageList = new ArrayList();
		for (int i = 0; i < allList.size(); i++) {
			if (pageNum >= pageSize) {
				list.add(pageList);
				pageList = new ArrayList();
				pageNum = 0;
			}
			pageList.add(allList.get(i));
			pageNum++;
		}
		if (pageList.size() > 0) {
			list.add(pageList);
		}
		return list;
	}
}
