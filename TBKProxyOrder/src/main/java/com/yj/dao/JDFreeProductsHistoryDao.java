package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.JDFreeProductsHistory;

/**
 * 免单商品
 * 
 * @author Administrator
 *
 */
@Mapper
public interface JDFreeProductsHistoryDao {

	/**
	 * 查询所有免单商品
	 * 
	 * @return
	 */
	List<JDFreeProductsHistory> searchAllJdFreeHistoryProducts();
}
