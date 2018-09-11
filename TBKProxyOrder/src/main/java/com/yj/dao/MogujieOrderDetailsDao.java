package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.MogujieOrderDetails;

/**
 * 蘑菇街数据库
 * 
 * @author Administrator
 *
 */
@Mapper
public interface MogujieOrderDetailsDao {
	/**
	 * 菇街退款的转换为订单退款
	 */
	void updateSystemUserIntegralExpireIntegralByMgj();
	/**
	 * 根据单号批量删除蘑菇街订单
	 * 
	 * @param orders
	 */
	void deleteMogujieOrdersBatch(List<MogujieOrderDetails> orders);

	/**
	 * 批量新增蘑菇街订单
	 * 
	 * @param orders
	 */
	void addMogujieOrdersBatch(List<MogujieOrderDetails> orders);

	/**
	 * 更新蘑菇街订单用户名
	 */
	void updateMgjOrderDetailUserName(List<MogujieOrderDetails> orders);

	/**
	 * 查询需要计算积分的蘑菇街订单
	 * 
	 * @param param
	 * @return
	 */
	List<MogujieOrderDetails> searchAllNotCalMgjOrder(MogujieOrderDetails param);
	
	/**
	 * 更新蘑菇街订单用户名
	 */
	void updateMgjOrderDetailUserNameFromIntegralAll();

}
