package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.JDFreeReturnIntegral;

/**
 * 京东免单积分
 * 
 * @author Administrator
 *
 */
@Mapper
public interface JDFreeReturnIntegralDao {
	/**
	 * 更新退款积分
	 */
	void updateJDFreeIntegralExpireIntegralByJDFree();

	/**
	 * 将订单状态为结算或者收货状态的订单积分从预估转为有效
	 */
	void updateJDFreeIntegralRealIntegral();

	/**
	 * 查询所有非提交的订单
	 * 
	 * @param orderId
	 * @return
	 */
	List<JDFreeReturnIntegral> searchNotCommitJdFreeIntegral(String orderId);

	/**
	 * 根据单号批量删除
	 * 
	 * @param integrals
	 */
	void deleteJDFreeIntegralByNumOutOfLine(List<JDFreeReturnIntegral> integrals);

	/**
	 * 批量新增
	 * 
	 * @param integrals
	 */
	void addJDFreeIntegralBatch(List<JDFreeReturnIntegral> integrals);

	/**
	 * 查询代理的免单总数
	 * 
	 * @param param
	 * @return
	 */
	Long searchMobileJdFreeProxyOrderCount(JDFreeReturnIntegral param);
}
