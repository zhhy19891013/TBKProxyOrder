package com.yj.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yj.dao.CpsAccountMgjDao;
import com.yj.dao.MogujieOrderDetailsDao;
import com.yj.domain.CpsAccountMgj;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.domain.TbSessionKey;
import com.yj.service.MgjOrderGrapService;
import com.yj.service.impl.base.BaseCpsOrderServiceImpl;
import com.yj.util.DateUtil;
import com.yj.util.MgjApi;

/**
 * 蘑菇街订单同步业务
 * 
 * @author Administrator
 *
 */
@Service
public class MgjOrderGrapServiceImpl extends BaseCpsOrderServiceImpl implements MgjOrderGrapService {
	@Autowired
	private CpsAccountMgjDao cpsAccountMgjDao;
	@Autowired
	private MogujieOrderDetailsDao mogujieOrderDetailsDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 同步订单
	 */
	@Override
	public void grap() {
		String ip = getIp();
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_MGJ, "开始抓取");
		CpsAccountMgj mgj = cpsAccountMgjDao.searchAccountMgjByType(CpsAccountMgj.ACCOUNT_TYPE_CAIJI);
		if (null == mgj) {
			logger.info("未绑定蘑菇街账号!");
			return;
		}
		// 获取下token
		TbSessionKey key = tbSessionKeyDao.searchSessionKeyByTbName(mgj.getUserName());
		if (null != key && null != key.getSessionKey()) {
			int page = 1;
			String start = DateUtil.lastXDays2(30);
			String end = DateUtil.lastXDays2(0);
			while (true) {
				List list = MgjApi.getMgjOrder(key.getSessionKey(), start, end, page, mgj.getUserName());
				if (null == list || list.isEmpty()) {
					break;
				}
				// 先根据单号删除
				mogujieOrderDetailsDao.deleteMogujieOrdersBatch(list);
				// 保存到数据库
				mogujieOrderDetailsDao.addMogujieOrdersBatch(list);
				// 更新订单的用户名
				mogujieOrderDetailsDao.updateMgjOrderDetailUserName(list);
				sendMessage(JSON.toJSONString(list), orderConfig.getTypeMgj() + ":" + ip);
				page++;
			}
		}

	}

}
