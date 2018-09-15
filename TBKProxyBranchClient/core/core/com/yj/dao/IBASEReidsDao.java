package com.yj.dao;

/**
 * redis数据库操作方法
 * 
 * @author Administrator
 */
public interface IBASEReidsDao {
	/**
	 * 保存
	 * 
	 * @param key
	 * @param value
	 */
	public void saveObject(String key, String value);

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public void deleteObject(String key);

	/**
	 * 查询
	 * 
	 * @param key
	 * @return
	 */
	public Object searchObject(String key);
	
	/**
	 * 
	 * @param k
	 * @param v
	 */
	public void updateObject(String k, String v);

}
