package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.CpsAccountJd;

/**
 * 京东账号数据库
 * 
 * @author Administrator
 *
 */
@Mapper
public interface CpsAccountJdDao {
	/**
	 * 查询所有的京东账号
	 * 
	 * @return
	 */
	List<CpsAccountJd> searchAccount();

	/**
	 * 根据类型查询账号
	 * 
	 * @param type
	 * @return
	 */
	CpsAccountJd searchCpsAccountJdByType(String type);

}
