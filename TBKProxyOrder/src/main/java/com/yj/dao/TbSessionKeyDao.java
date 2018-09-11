package com.yj.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.TbSessionKey;

@Mapper
public interface TbSessionKeyDao {
	/**
	 * 根据淘宝名称查询授权key
	 * 
	 * @param name
	 * @return
	 */
	TbSessionKey searchSessionKeyByTbName(String name);
}
