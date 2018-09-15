package com.yj.dao;

import java.util.List;

/**
 * 所有dao的父类 提供了一些共有的方法
 * 
 * @author Administrator
 */
public interface IBASEDao  {
	/**
	 * 单独查询
	 * 
	 * @param sqlName
	 * @param param
	 * @return
	 */
	public Object searchSingleObject(String sqlName, Object param);

	/**
	 * 查询列表
	 * 
	 * @param sqlName
	 * @param param
	 * @return
	 */
	public List searchObjects(String sqlName, Object param);


	/**
	 * 删除多个
	 * 
	 * @param sqlName
	 * @param l
	 * @return
	 */
	public boolean deleteObjects(String sqlName, List l);

	/***
	 * 删除单个
	 */
	public boolean deleteSingleObject(String sqlName, Object o);

	/**
	 * 保存单个
	 * 
	 * @param sqlName
	 * @param param
	 * @return
	 */
	public boolean saveSingleObject(String sqlName, Object param);

	/**
	 * 修改单个
	 * 
	 * @param sqlName
	 * @param param
	 * @return
	 */
	public boolean updateSingleObject(String sqlName, Object param);

}
