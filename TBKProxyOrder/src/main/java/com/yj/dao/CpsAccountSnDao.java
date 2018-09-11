package com.yj.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.CpsAccountSn;

/**
 * 苏宁账号
 * 
 * @author Administrator
 *
 */
@Mapper
public interface CpsAccountSnDao {

	/**
	 * 查询苏宁账号
	 * 
	 * @return
	 */
	CpsAccountSn searchCpsAccountSn();
}
