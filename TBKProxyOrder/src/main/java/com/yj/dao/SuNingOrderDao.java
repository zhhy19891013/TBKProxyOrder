package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.SuNingOrder;

/**
 * 苏宁订单
 * 
 * @author Administrator
 *
 */
@Mapper
public interface SuNingOrderDao {

	/**
	 * 根据单号批量删除
	 * 
	 * @param orders
	 */
	void deleteSnOrders(List<SuNingOrder> orders);

	/**
	 * 批量新增苏宁订单
	 * 
	 * @param orders
	 */
	void addSuningOrdersBatch(List<SuNingOrder> orders);

	/**
	 * 从积分表更新下订单的用户名
	 * 
	 * @param orders
	 */
	void updateSuningOrderUserName(List<SuNingOrder> orders);

	/**
	 * 查询需要计算积分的订单
	 * 
	 * @return
	 */
	List<SuNingOrder> searchNotCalSnOrders();
}
