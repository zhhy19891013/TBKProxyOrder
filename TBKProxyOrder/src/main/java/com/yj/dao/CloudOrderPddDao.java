package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.CloudOrderPdd;

/**
 * 拼多多数据库
 * 
 * @author Administrator
 *
 */
@Mapper
public interface CloudOrderPddDao {
	/**
	 * 删除不是这个拼台的订单
	 */
	void deleteNotThisPlatform();

	/**
	 * 更新下管理员下单的拼多多订单的用户名
	 */
	void updateAdminPddOrder();

	/**
	 * 根据订单号批量删除拼多多订单
	 * 
	 * @param orders
	 */
	void deletePddOrders(List<CloudOrderPdd> orders);

	/**
	 * 新增拼多多订单
	 * 
	 * @param orders
	 */
	void addCloudOrderPddBatch(List<CloudOrderPdd> orders);

	/**
	 * 更新订单用户名
	 */
	void updatePddOrderUserNameAll();

	/**
	 * 查询没有计算的拼多多订单
	 * 
	 * @return
	 */
	List<CloudOrderPdd> searchNotCalPddOrders();

	/**
	 * 根据单号查询拼多多订单
	 * 
	 * @param id
	 * @return
	 */
	CloudOrderPdd searchPddOrderById(String id);
}
