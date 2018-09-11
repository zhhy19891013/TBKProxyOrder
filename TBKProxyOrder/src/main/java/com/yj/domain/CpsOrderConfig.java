package com.yj.domain;

import java.sql.Timestamp;

import com.yj.domain.base.BasicModel;

/**
 * 订单抓取配置
 * 
 * @author Administrator
 *
 */
public class CpsOrderConfig extends BasicModel {

	private String cpsAccountName;// 账号名称
	private Timestamp lastUpdateDate;// 最后更新日期

	public String getCpsAccountName() {
		return cpsAccountName;
	}

	public void setCpsAccountName(String cpsAccountName) {
		this.cpsAccountName = cpsAccountName;
	}

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
