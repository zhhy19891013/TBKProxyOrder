package com.yj.service.impl;

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

import com.alibaba.fastjson.JSON;
import com.yj.dao.CpsAccountJdDao;
import com.yj.dao.JDFreeOrderDetailsDao;
import com.yj.dao.JDFreeOrderDetailsTemporaryDao;
import com.yj.dao.JDFreeProductsHistoryDao;
import com.yj.dao.JDFreeReturnIntegralDao;
import com.yj.dao.JDFreeTimeHistoryDao;
import com.yj.dao.JDOrderDetailsDao;
import com.yj.dao.SystemUserDao;
import com.yj.domain.CpsAccountJd;
import com.yj.domain.JDFreeOrderDetails;
import com.yj.domain.JDFreeOrderDetailsTemporary;
import com.yj.domain.JDFreeProductsHistory;
import com.yj.domain.JDFreeReturnIntegral;
import com.yj.domain.JDFreeTimeHistory;
import com.yj.domain.JDOrderDetails;
import com.yj.domain.SystemOrderGrapStatus;
import com.yj.domain.SystemUser;
import com.yj.service.JdOrderGrapService;
import com.yj.service.impl.base.BaseCpsOrderServiceImpl;
import com.yj.util.DateUtil;
import com.yj.util.JDFreeApi;
import com.yj.util.JDapi;
import com.yj.util.ListUtil;
import com.yj.util.StringUtil;

/**
 * 京东订单抓取
 * 
 * @author Administrator
 *
 */
@Service
public class JdOrderGrapServiceImpl extends BaseCpsOrderServiceImpl implements JdOrderGrapService {
	@Autowired
	private CpsAccountJdDao cpsAccountJdDao;
	@Autowired
	private JDOrderDetailsDao jDOrderDetailsDao;
	@Autowired
	private JDFreeProductsHistoryDao jDFreeProductsHistoryDao;
	@Autowired
	private JDFreeTimeHistoryDao jDFreeTimeHistoryDao;
	@Autowired
	private JDFreeOrderDetailsDao jDFreeOrderDetailsDao;
	@Autowired
	private JDFreeOrderDetailsTemporaryDao jDFreeOrderDetailsTemporaryDao;
	@Autowired
	private JDFreeReturnIntegralDao jDFreeReturnIntegralDao;

	@Autowired
	private SystemUserDao systemUserDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 同步订单
	 */
	@Override
	public void grap() {
		updateOrderGrapStatus(SystemOrderGrapStatus.ORDER_GRAP_STATUS_JD, "开始抓取");
		logger.info("开始抓取京东订单");
		grapNormal();
		grapFree();
		logger.info("京东订单抓取结束");
	}

	/************************
	 * 私有方法
	 ************************/
	/**
	 * 抓取免单
	 */
	private void grapFree() {
		List<JDOrderDetails> freeDetails = new ArrayList<JDOrderDetails>();// 京东免单订单明细
		List<JDOrderDetails> outOfLine = new ArrayList<>();// 违规订单
		List<JDFreeReturnIntegral> integralList = new ArrayList<>();
		CpsAccountJd bind = cpsAccountJdDao.searchCpsAccountJdByType(CpsAccountJd.ACCOUNT_TYPE_MD);
		if (null == bind || null == bind.getEmpowerKey() || "".equals(bind.getEmpowerKey().trim())) {
			logger.info("未设置授权码");
			return;
		}
		// 设置时间区间
		int timeStart = Integer.valueOf(DateUtil.lastXDays2(0));// 开始时间
		int timeEnd = timeStart;
		if (isNeedGrapLong()) {
			timeEnd = Integer.valueOf(DateUtil.lastXDays2(90));
		}
		// 查出符合条件的京东免单订单详情
		// 符合条件：免单活动时间内上传的免单产品
		Long begin = Long.valueOf(getConfigByName("freeBeginDate"));
		Long end = Long.valueOf(getConfigByName("freeEndDate"));
		double limit = searchRateByName("jd_integral_limit");
		JDFreeOrderDetailsTemporary t = new JDFreeOrderDetailsTemporary();
		if (begin != null && end != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				t.setCreateTime1(sdf.format(new Date(begin)));
				t.setCreateTime2(sdf.format(new Date(end)));
				t.setPrice(limit);
			} catch (Exception e) {
				logger.info("后台京东免单活动时间设置有误");
				return;
			}
		} else {
			logger.info("后台京东免单未设置免单活动时间");
			return;
		}
		for (int i = timeStart; i <= timeEnd; i++) {
			List<JDOrderDetails> orderDetails = JDFreeApi.queryImportOrders(i + "", bind);
			if (null == orderDetails || orderDetails.isEmpty()) {
				continue;
			}
			logger.info("共抓取到 【" + orderDetails.size() + "】 条京东免单订单数据");
			if (!orderDetails.isEmpty()) {
				List all = ListUtil.makeSubList(orderDetails, 200);
				// 清空临时表
				jDFreeOrderDetailsTemporaryDao.deleteTemporaryJDOrderDetails();
				for (Object o : all) {
					List l = (List) o;
					// 放入数据库临时表中
					jDFreeOrderDetailsTemporaryDao.addJDFreeOrderDetailsTemporary(l);
				}
				freeDetails = jDFreeOrderDetailsTemporaryDao.searchJDFreeOrderDetailsInConditions(t);
				outOfLine = jDFreeOrderDetailsTemporaryDao.searchJDFreeOrderDetailsStatusY(t);
				if (!outOfLine.isEmpty()) {
					logger.info("共有【 " + outOfLine.size() + " 】条京东免单违规订单");
					for (Object o : outOfLine) {
						JDFreeOrderDetails temp = (JDFreeOrderDetails) o;
						SystemUser user = systemUserDao.searchSystemUserByJdFreePid(temp.getSubunionid());
						if (user != null) {
							JDFreeReturnIntegral integral = creatJdUserIntegral(temp, Double.valueOf(100),
									user.getUserName(), user.getUserName(), user.getGroup_name());
							integral.setRealIntegral(integral.getIntegral());
							// 存入list
							integralList.add(integral);
						}
					}
					if (!integralList.isEmpty()) {
						List l = ListUtil.makeSubList(integralList, 200);
						for (Object o : l) {
							List list = (List) o;
							logger.info("新增违规订单积分【 " + list.size() + " 】条");
							jDFreeReturnIntegralDao.deleteJDFreeIntegralByNumOutOfLine(list);
							jDFreeReturnIntegralDao.addJDFreeIntegralBatch(list);
						}
					}
				}
				// 将符合条件的订单详情存入京东免单订单详情表
				logger.info("订单有" + freeDetails.size() + "条京东免单数据");
				if (!freeDetails.isEmpty()) {
					List free = ListUtil.makeSubList(freeDetails, 200);
					for (Object o : free) {
						List l = (List) o;
						// 删
						jDFreeOrderDetailsDao.deleteJdFreeOrderDetailsByOrderIds(l);
						jDFreeOrderDetailsDao.addJDFreeOrderDetailsBatch(l);
						jDFreeOrderDetailsDao.updateJdFreeOrderDetailUserName(l);
					}
				}
			}
		}
	}

	/**
	 * 抓取普通账号的订单
	 */
	private void grapNormal() {
		String ip = getIp();
		List<JDOrderDetails> normalOrderDetails = new ArrayList<JDOrderDetails>();// 正常订单明细
		List<JDOrderDetails> freeDetails = new ArrayList<JDOrderDetails>();// 京东免单订单明细
		List<String> mdOrderIds = new ArrayList<String>();// 免单单号
		// 查询所有的京东账号
		List<CpsAccountJd> binds = cpsAccountJdDao.searchAccount();
		// 查询所有的免单记录
		List<String> history = findJdFreeHistory();
		if (null == binds || binds.isEmpty()) {
			logger.info("未绑定京东账号");
			return;
		}
		// 获取下免单的记录时间
		List<Map<String, Long>> times = searchAllJdFreemTimeHistory();
		if (null == times || times.isEmpty()) {
			logger.info("免单时间范围为空");
			return;
		}
		Map<String, CpsAccountJd> accounts = new HashMap<String, CpsAccountJd>();
		for (CpsAccountJd a : binds) {
			if (!a.getAccountType().equals(CpsAccountJd.ACCOUNT_TYPE_MD)) {
				accounts.put(a.getJdName(), a);
			}
		}
		for (String key : accounts.keySet()) {
			normalOrderDetails.clear();
			freeDetails.clear();
			mdOrderIds.clear();
			CpsAccountJd bind = (CpsAccountJd) accounts.get(key);
			if (null == bind.getEmpowerKey()) {
				logger.info("未设置授权码");
				continue;
			}
			// 设置时间区间
			int timeStart = Integer.valueOf(DateUtil.lastXDays2(0));// 开始时间
			int timeEnd = timeStart;
			if (isNeedGrapLong()) {
				timeEnd = Integer.valueOf(DateUtil.lastXDays2(90));
			}
			for (int i = timeStart; i <= timeEnd; i++) {
				// 先检验下京东授权key是不是有效的
				if (!JDapi.checkKey(bind.getEmpowerKey())) {
					logger.info(StringUtil.append(bind.getJdName(), "授权key已经过期"));
					break;
				}
				// 开始调用京东订单接口
				int page = 1;
				while (true) {
					Map map = JDapi.queryImportOrdersByPage(i + "", bind.getEmpowerKey(), page, times, history);
					if (null == map || map.isEmpty() || null == map.get("orderDetails")
							|| null == map.get("freeDetails")) {
						break;
					}
					normalOrderDetails = (List) map.get("orderDetails");// 原始订单明细数据
					freeDetails = (List) map.get("freeDetails");// 京东免单订单明细
					if (normalOrderDetails.isEmpty() && freeDetails.isEmpty()) {
						break;
					}
					if (!normalOrderDetails.isEmpty()) {
						logger.info("有" + normalOrderDetails.size() + "条正常京东数据");
						// 订单详情模块
						jDOrderDetailsDao.deleteJdOrderDetailsByOrderIds(normalOrderDetails);
						jDOrderDetailsDao.addJDOrderDetailsBatch(normalOrderDetails);
						jDOrderDetailsDao.updateJdOrderDetailUserName(normalOrderDetails);
						sendMessage(JSON.toJSONString(normalOrderDetails), orderConfig.getTypeJd()+":"+ip);
					}
					if (!freeDetails.isEmpty()) {
						logger.info("有" + freeDetails.size() + "条免单京东数据");
						// 删
						jDFreeOrderDetailsDao.deleteJdFreeOrderDetailsByOrderIds(freeDetails);
						jDFreeOrderDetailsDao.addJDFreeOrderDetailsBatch(freeDetails);
						jDFreeOrderDetailsDao.updateJdFreeOrderDetailUserName(freeDetails);
					}
					page++;
				}
			}
		}
	}

	/**
	 * 查看所有的免单区间
	 * 
	 * @return
	 */
	private List<Map<String, Long>> searchAllJdFreemTimeHistory() {
		List<Map<String, Long>> results = new ArrayList<Map<String, Long>>();
		List<JDFreeTimeHistory> list = jDFreeTimeHistoryDao.searchAllJdFreeTimeHistroy();
		if (null != list && !list.isEmpty()) {
			for (JDFreeTimeHistory hi : list) {
				Map<String, Long> m = new HashMap<String, Long>();
				List<String> time = StringUtil.split(hi.getFreeTimeRange(), "-");
				m.put("begin", Long.valueOf(time.get(0)));
				m.put("end", Long.valueOf(time.get(1)));
				results.add(m);
			}
		}
		return results;
	}

	/**
	 * 查京东免单产品历史清单
	 */
	private List<String> findJdFreeHistory() {
		List<String> result = new ArrayList<String>();
		List list = jDFreeProductsHistoryDao.searchAllJdFreeHistoryProducts();
		for (Object o : list) {
			JDFreeProductsHistory jph = (JDFreeProductsHistory) o;
			result.add(jph.getSkuid());
		}
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
