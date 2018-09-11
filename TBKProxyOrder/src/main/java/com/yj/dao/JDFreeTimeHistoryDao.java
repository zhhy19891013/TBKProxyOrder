package com.yj.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yj.domain.JDFreeTimeHistory;

/**
 * 京东免单时间记录
 * 
 * @author Administrator
 *
 */
@Service
public interface JDFreeTimeHistoryDao {

	/**
	 * 查询所有的免单时间
	 * 
	 * @return
	 */
	List<JDFreeTimeHistory> searchAllJdFreeTimeHistroy();
}
