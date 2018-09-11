package com.yj.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.SystemOrderGrapStatus;

/**
 * 订单更新状态
 * 
 * @author Administrator
 *
 */
@Mapper
public interface SystemOrderGrapStatusDao {
	/**
	 * 更新下订单抓取的状态
	 * 
	 * @param status
	 */
	void updateSystemOrderGrapStatus(SystemOrderGrapStatus status);

	/**
	 * 新增订单抓取状态
	 * 
	 * @param status
	 */
	void addSystemOrderGrapStatus(SystemOrderGrapStatus status);

	/**
	 * 根据类型查询状态
	 * 
	 * @param type
	 * @return
	 */
	SystemOrderGrapStatus searchSystemOrderGrapStatusByType(String type);
}
