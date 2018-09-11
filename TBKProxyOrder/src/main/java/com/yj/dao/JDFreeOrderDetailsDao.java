package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.JDFreeOrderDetails;
import com.yj.domain.JDOrderDetails;

/**
 * 京东免单订单
 * 
 * @author Administrator
 *
 */
@Mapper
public interface JDFreeOrderDetailsDao {
	/**
	 * 更新免单订单的用户名
	 */
	void updateJdFreeOrderDetailUserNameBatch();
	/**
	 * 根据单号批量删除
	 * 
	 * @param orders
	 */
	void deleteJdFreeOrderDetailsByOrderIds(List<JDOrderDetails> orders);

	/**
	 * 批量新增免单订单
	 * 
	 * @param orders
	 */
	void addJDFreeOrderDetailsBatch(List<JDOrderDetails> orders);

	/**
	 * 批量更新免单订单的用户名
	 * 
	 * @param orders
	 */
	void updateJdFreeOrderDetailUserName(List<JDOrderDetails> orders);

	/**
	 * 查询需要计算的京东免单
	 * 
	 * @param param
	 * @return
	 */
	List<JDFreeOrderDetails> searchAllNotCalJdFreeOrderDetails(JDFreeOrderDetails param);

	/**
	 * 根据单号查询订单
	 * 
	 * @param orderId
	 * @return
	 */
	JDFreeOrderDetails searchJdFreeOrderDetailsByOrderId(String orderId);
}
