package com.yj.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.SystemCommonConfig;

/**
 * 系统配置
 * 
 * @author Administrator
 *
 */
@Mapper
public interface SystemCommonConfigDao {
	/**
	 * 更新配置
	 * 
	 * @param config
	 */
	void updateSystemCommonConfig(SystemCommonConfig config);

	/**
	 * 根据配置的名字去获取配置
	 * 
	 * @param name
	 * @return
	 */
	SystemCommonConfig searchSystemCommonConfigByName(String name);
}
