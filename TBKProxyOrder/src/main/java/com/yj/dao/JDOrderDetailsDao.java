package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.JDOrderDetails;

/**
 * 京东订单明细
 * 
 * @author Administrator
 *
 */
@Mapper
public interface JDOrderDetailsDao {
	/**
	 * 根据id批量删除
	 * 
	 * @param orders
	 */
	void deleteJdOrderDetailsByOrderIds(List<JDOrderDetails> orders);

	/**
	 * 批量新增
	 * 
	 * @param orders
	 */
	void addJDOrderDetailsBatch(List<JDOrderDetails> orders);

	/**
	 * 根据单号批量更新订单的用户名
	 * 
	 * @param orders
	 */
	void updateJdOrderDetailUserName(List<JDOrderDetails> orders);

	/**
	 * 查询没有计算的京东订单
	 * 
	 * @return
	 */
	List<JDOrderDetails> searchAllNotCalJdOrder(JDOrderDetails detail);

	/**
	 * 根据单号查询订单
	 * 
	 * @param orderId
	 * @return
	 */
	JDOrderDetails searchOrderDetailsByOrderId(String orderId);

	/**
	 * 更新订单的用户名
	 */
	void updateJdOrderDetailUserNameFromIntegral();

}
