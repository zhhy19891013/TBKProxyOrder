package com.yj.db.model.reuse;

import java.sql.Timestamp;

/**
 * 淘宝产品和京东产品的父类
 * @author Administrator
 *
 */
public class BaseProduct extends BasicModel{
	private Timestamp updateTime;// 更新时间

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
