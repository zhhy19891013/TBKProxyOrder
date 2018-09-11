package com.yj.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.CpsAccountTb;

@Mapper
public interface CpsAccountTbDao {
	/**
	 * 查询绑定的淘宝账号
	 * 
	 * @return
	 */
	CpsAccountTb searchTbAccount();

}
