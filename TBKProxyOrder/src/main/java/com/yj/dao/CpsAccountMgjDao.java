package com.yj.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.CpsAccountMgj;

/**
 * 蘑菇街数据库
 * 
 * @author Administrator
 *
 */
@Mapper
public interface CpsAccountMgjDao {

	/**
	 * 根据类型查询蘑菇街账号
	 * 
	 * @param type
	 * @return
	 */
	CpsAccountMgj searchAccountMgjByType(String type);
}
