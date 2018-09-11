package com.yj.service.impl.base;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yj.dao.CpsOrderConfigDao;
import com.yj.dao.SystemCommonConfigDao;
import com.yj.dao.SystemOrderGrapStatusDao;
import com.yj.dao.TbSessionKeyDao;
import com.yj.domain.CpsOrderConfig;
import com.yj.domain.SystemCommonConfig;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.domain.config.OrderConfig;
import com.yj.mq.OrderProducer;
import com.yj.util.DateUtil;

/**
 * 订单抓取的父类
 * 
 * @author Administrator
 *
 */
public class BaseCpsOrderServiceImpl {
	@Autowired
	private SystemOrderGrapStatusDao systemOrderGrapStatusDao;
	@Autowired
	private SystemCommonConfigDao systemCommonConfigDao;
	@Autowired
	protected TbSessionKeyDao tbSessionKeyDao;
	@Autowired
	protected CpsOrderConfigDao cpsOrderConfigDao;
	@Autowired
	protected OrderConfig orderConfig;
	@Autowired
	private OrderProducer orderProducer;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 将抓到的订单信息发送到亿峰服务器
	 * 
	 * @param msg
	 */
	protected void sendMessage(String msg, String tag) {
		try {
			Message message = new Message("allOrder", tag, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
			SendResult sd = orderProducer.getProducer().send(message);
			logger.info(sd.getMsgId() + "->" + sd.getSendStatus());
		} catch (Exception e) {
		}
	}

	/**
	 * 根据名称查询对应的配置
	 * 
	 * @param name
	 * @return
	 */
	protected String getConfigByName(String name) {
		SystemCommonConfig config = systemCommonConfigDao.searchSystemCommonConfigByName(name);
		return config.getConfigValue();
	}

	/**
	 * 获取服务器的ip
	 * 
	 * @return
	 */
	protected String getIp() {
		return getConfigByName("own_web_ip");
	}

	/**
	 * 查询比例
	 * 
	 * @param name
	 * @return
	 */
	protected double searchRateByName(String name) {
		SystemCommonConfig config = systemCommonConfigDao.searchSystemCommonConfigByName(name);
		return Double.valueOf(config.getConfigValue());
	}

	/**
	 * 检查是否同步长时间范围的订单（过去一个月的单子等） 凌晨2点到早上7点时间内
	 * 
	 * @return trur=需要 false=不需要
	 */
	protected boolean isNeedGrapLong() {
		Date d = new Date();
		return (DateUtil.getHour(d) < orderConfig.getEndHour() && DateUtil.getHour(d) > orderConfig.getBeginHour());
	}

	/**
	 * 更新下配置
	 * 
	 * @param name
	 * @param value
	 */
	protected void updateConfig(String name, String value) {
		SystemCommonConfig config = new SystemCommonConfig();
		config.setConfigName(name);
		config.setConfigValue(value);
		systemCommonConfigDao.updateSystemCommonConfig(config);
	}

	/**
	 * 获取上次更新时间
	 * 
	 * @param name
	 * @return
	 */
	protected Timestamp getLastUpdate(String name) {
		CpsOrderConfig config = cpsOrderConfigDao.searchCpsOrderConfigByName(name);
		if (null == config) {
			return new Timestamp(System.currentTimeMillis() - 1200000L);
		} else {
			return config.getLastUpdateDate();
		}
	}

	/**
	 * 更新上次订单更新时间
	 * 
	 * @param config
	 */
	protected void updateLastUpdate(String name, Timestamp dateTime) {
		CpsOrderConfig config = new CpsOrderConfig();
		config.setCpsAccountName(name);
		config.setLastUpdateDate(dateTime);
		if (null == cpsOrderConfigDao.searchCpsOrderConfigByName(name)) {
			cpsOrderConfigDao.addCpsOrderConfig(config);
		} else {
			cpsOrderConfigDao.updateCpsOrderConfig(config);
		}
	}

	/**
	 * 更新下抓取状态
	 * 
	 * @param type
	 * @param status
	 * @param baseDao
	 */
	protected void updateOrderGrapStatus(String type, String status) {
		// 先查询下数据库有没有
		SystemOrderGrapStatus sogs = systemOrderGrapStatusDao.searchSystemOrderGrapStatusByType(type);
		if (null == sogs) {// 如果没有
			SystemOrderGrapStatus current = new SystemOrderGrapStatus();
			current.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			current.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			current.setOrderType(type);
			current.setOrderStatus(status);
			systemOrderGrapStatusDao.addSystemOrderGrapStatus(current);
		} else {
			if ("抓取结束".equals(status)) {
				sogs.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			}
			sogs.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			sogs.setOrderType(type);
			sogs.setOrderStatus(status);
			systemOrderGrapStatusDao.updateSystemOrderGrapStatus(sogs);
		}
	}

}
