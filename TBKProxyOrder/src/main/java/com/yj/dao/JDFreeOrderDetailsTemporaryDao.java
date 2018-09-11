package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.JDFreeOrderDetailsTemporary;
import com.yj.domain.JDOrderDetails;

/**
 * 京东免单临时表
 * 
 * @author Administrator
 *
 */
@Mapper
public interface JDFreeOrderDetailsTemporaryDao {
	/**
	 * 清空京东免单订单临时表
	 */
	void deleteTemporaryJDOrderDetails();

	/**
	 * 批量新增
	 * 
	 * @param orders
	 */
	void addJDFreeOrderDetailsTemporary(List<JDOrderDetails> orders);

	/**
	 * 查出免单订单
	 * 
	 * @param t
	 * @return
	 */
	List<JDOrderDetails> searchJDFreeOrderDetailsInConditions(JDFreeOrderDetailsTemporary t);

	/**
	 * 查出免单违规订单
	 * 
	 * @param t
	 * @return
	 */
	List<JDOrderDetails> searchJDFreeOrderDetailsStatusY(JDFreeOrderDetailsTemporary t);

}
