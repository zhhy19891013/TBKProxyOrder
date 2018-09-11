package com.yj.domain.base;

/**
 * 所有实体类的父类
 * 
 * @author Administrator
 *
 */
public class BasicModel {
	/**
	 * 数据库编号
	 */
	private long databaseID;

	public long getDatabaseID() {
		return databaseID;
	}

	public void setDatabaseID(long databaseID) {
		this.databaseID = databaseID;
	}


}
