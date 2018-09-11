package com.yj.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yj.dao.CpsAccountTbDao;
import com.yj.dao.PaymentRecordDao;
import com.yj.dao.SystemUserIntegralDao;
import com.yj.domain.CpsAccountTb;
import com.yj.domain.PaymentRecord;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.domain.TbSessionKey;
import com.yj.dto.tb.TbOrderDto;
import com.yj.service.TbOrderGrapService;
import com.yj.service.impl.base.BaseCpsOrderServiceImpl;
import com.yj.util.DateUtil;
import com.yj.util.TbApi;
import com.yj.util.redis.RedisClient;

/**
 * 淘宝订单业务
 * 
 * @author Administrator
 *
 */
@Service
public class TbOrderGrapServiceImpl extends BaseCpsOrderServiceImpl implements TbOrderGrapService {
	@Autowired
	private PaymentRecordDao paymentRecordDao;
	@Autowired
	private CpsAccountTbDao cpsAccountTbDao;
	@Autowired
	protected SystemUserIntegralDao systemUserIntegralDao;
	@Autowired
	private RedisClient client;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 同步订单 白天同步当天的单子 晚上(凌晨2点到凌晨7点)同步一个月的单子
	 */
	@Override
	public void grap() {
		String ip = getIp();
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_TB, "开始抓取");
		logger.info("开始抓取淘宝订单");
		CpsAccountTb account = cpsAccountTbDao.searchTbAccount();
		if (null == account) {
			return;
		}
		List<String> tbNames = new ArrayList<String>();
		if (null != account.getXbTbName()) {
			if (!tbNames.contains(account.getXbTbName())) {
				tbNames.add(account.getXbTbName());
			}
		}
		if (null != account.getZlTbName()) {
			if (!tbNames.contains(account.getZlTbName())) {
				tbNames.add(account.getZlTbName());
			}
		}
		tbNames.addAll(account.getProductTbNames());
		String lastLangDay = getConfigByName("order_grap_lang_date");
		String currentDay = DateUtil.getDay(new Date()) + "";
		if (null != tbNames && !tbNames.isEmpty()) {
			for (String tbName : tbNames) {
				// 检验授权key是否在线
				TbSessionKey key = tbSessionKeyDao.searchSessionKeyByTbName(tbName);
				if (null == key || null == key.getSessionKey() || !TbApi.checkKey(key.getSessionKey())) {
					logger.info(tbName + ":授权key不在线");
					continue;
				}
				Timestamp last = getLastUpdate(tbName);
				if (isNeedGrapLong() && !lastLangDay.equals(currentDay)) {// 同步过去一个月的
					last = new Timestamp(DateUtil.lastXDaysDate(30).getTime());
					updateConfig("order_grap_lang_date", currentDay);
				} else {
					last = new Timestamp(last.getTime() - 2400000);
					// last = new
					// Timestamp(DateUtil.lastXDaysDate(32).getTime());
				}
				while (true) {
					if (last.getTime() > System.currentTimeMillis()) {
						break;
					} else {
						updateLastUpdate(tbName, last);
					}
					int page = 1;
					while (true) {
						logger.info("开始同步:" + tbName + ":" + last + ":淘宝订单");
						// 计算下span
						long span = System.currentTimeMillis() - last.getTime();
						if (span > 1200000) {
							span = 1200;
						} else {
							span = span / 1000;
						}
						TbOrderDto dto = TbApi.grapOrder(tbName, key.getSessionKey(), last, page, 1, span, client);
						/*
						 * if (null != dto && null != dto.getIds() &&
						 * !dto.getIds().isEmpty()) { logger.info("共有重复单子:" +
						 * dto.getIds().size());
						 * paymentRecordDao.deleteRepeatPay(dto.getIds());
						 * systemUserIntegralDao.updtateRepeat(dto.getIds()); }
						 */
						if (null == dto || null == dto.getRecords() || dto.getRecords().isEmpty()) {
							break;
						}
						// 保存到数据库
						List<PaymentRecord> records = dto.getRecords();
						logger.info("共有" + records.size() + "条淘宝订单需要更新");
						sendMessage(JSON.toJSONString(records), orderConfig.getTypeTb() + ":" + ip);
						paymentRecordDao.deletePaymentRecordByIds(records);
						paymentRecordDao.addPayementRecord(records);
						paymentRecordDao.updatePaymentRecordUserNameByIntegralAndOrderNumber(records);
						if (dto.getHasMore() == 0) {
							break;
						}
						page++;
					}
					last = new Timestamp((last.getTime() + 1200000));
				}
			}
		}
	}

}
