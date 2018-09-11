package com.yj.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yj.dao.CpsAccountSnDao;
import com.yj.dao.SuNingOrderDao;
import com.yj.domain.CpsAccountSn;
import com.yj.domain.SuNingOrder;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.dto.tb.SNCloudOrderParam;
import com.yj.service.SnOrderGrapService;
import com.yj.service.impl.base.BaseCpsOrderServiceImpl;
import com.yj.util.DateUtil;
import com.yj.util.SnApi;

/**
 * 苏宁订单同步
 * 
 * @author Administrator
 *
 */
@Service
public class SnOrderGrapServiceImpl extends BaseCpsOrderServiceImpl implements SnOrderGrapService {
	@Autowired
	private CpsAccountSnDao cpsAccountSnDao;
	@Autowired
	private SuNingOrderDao suNingOrderDao;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 同步订单
	 */
	@Override
	public void grap() {
		String ip = getIp();
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_SN, "开始抓取");
		CpsAccountSn snBind = cpsAccountSnDao.searchCpsAccountSn();
		if (null == snBind) {
			logger.info("未绑定苏宁账号!");
			return;
		}
		int day = isNeedGrapLong() ? 30 : 1;
		List<SuNingOrder> listForUse = new ArrayList<SuNingOrder>();
		while (true) {
			int page = 1;
			if (day > 30) {
				break;
			}
			while (true) {
				Date d = DateUtil.lastXDaysDate(day);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String formatTime = sdf.format(d);
				String start = formatTime + " 00:00:00";
				String end = formatTime + " 24:00:00";
				List<SNCloudOrderParam> list = SnApi.searchAllOrder(page, snBind, end, start);
				listForUse.clear();
				// 更新下用户名
				if (null == list || list.isEmpty()) {
					break;
				}
				for (SNCloudOrderParam snCloudOrderParam : list) {
					String orderCode = snCloudOrderParam.getOrderCode();
					List<SuNingOrder> detailList = snCloudOrderParam.getOrderDetail();
					for (SuNingOrder snOrderDetailsParam : detailList) {
						snOrderDetailsParam.setOrderCode(orderCode);
						listForUse.add(snOrderDetailsParam);
					}
				}
				// 从数据库删除
				suNingOrderDao.deleteSnOrders(listForUse);
				// 往数据库保存
				suNingOrderDao.addSuningOrdersBatch(listForUse);
				// 从积分表更新下名字
				suNingOrderDao.updateSuningOrderUserName(listForUse);
				sendMessage(JSON.toJSONString(listForUse), orderConfig.getTypeSn() + ":" + ip);
				page++;
			}
			day++;
		}

	}

}
