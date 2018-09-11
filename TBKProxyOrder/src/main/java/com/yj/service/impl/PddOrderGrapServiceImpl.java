package com.yj.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yj.dao.CloudOrderPddDao;
import com.yj.domain.CloudOrderPdd;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.service.PddOrderGrapService;
import com.yj.service.impl.base.BaseCpsOrderServiceImpl;
import com.yj.util.DateUtil;
import com.yj.util.JSoupUtil;
import com.yj.util.StringUtil;

/**
 * 拼多多订单更新业务
 * 
 * @author Administrator
 *
 */
@Service
public class PddOrderGrapServiceImpl extends BaseCpsOrderServiceImpl implements PddOrderGrapService {
	@Autowired
	private CloudOrderPddDao cloudOrderPddDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 同步订单
	 */
	@Override
	public void grap() {
		String ip = getIp();
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_PDD, "开始抓取");
		int page = 1;
		while (true) {
			List<CloudOrderPdd> list = getListFromYf(page);
			if (null == list || list.isEmpty()) {
				break;
			}
			// 保存到数据库
			// 先把订单删了
			cloudOrderPddDao.deletePddOrders(list);
			// 再把订单插进去
			cloudOrderPddDao.addCloudOrderPddBatch(list);
			// 更新下订单的用户名
			cloudOrderPddDao.updatePddOrderUserNameAll();
			sendMessage(JSON.toJSONString(list), orderConfig.getTypePdd() + ":" + ip);
			page++;
		}
	}

	/**
	 * 从亿峰动力同步订单
	 * 
	 * @param page
	 * @return
	 */
	private List<CloudOrderPdd> getListFromYf(int page) {
		int max = isNeedGrapLong() ? 150 : 1;
		String url = StringUtil.append("http://www.yifengdongli.com/remote/pdd/searchPddOrder.do?createTime1=",
				DateUtil.lastXDays(max), "&createTime2=", DateUtil.lastXDays(0), "&page=" + page);
		String str = JSoupUtil.getRequest(url, null, 30);
		logger.info(url);
		logger.info(str);
		if (null != str) {
			try {
				List<CloudOrderPdd> results = JSONArray.parseArray(str, CloudOrderPdd.class);
				return results;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
