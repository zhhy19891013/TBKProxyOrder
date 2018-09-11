package com.yj.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.CpsOrderConfig;

@Mapper
public interface CpsOrderConfigDao {
	/**
	 * 根据账号名称查看上次同步时间
	 * 
	 * @param name
	 * @return
	 */
	CpsOrderConfig searchCpsOrderConfigByName(String name);

	/**
	 * 新增订单配置
	 * 
	 * @param config
	 */
	void addCpsOrderConfig(CpsOrderConfig config);

	/**
	 * 更新下订单配置
	 * 
	 * @param config
	 */
	void updateCpsOrderConfig(CpsOrderConfig config);
}
