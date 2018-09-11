package com.yj.domain;

import com.yj.domain.base.BasicModel;

/**
 * 京东免单历史时间
 * 
 * @author yiju-zhhy
 * 
 */
public class JDFreeTimeHistory extends BasicModel {

	private String freeTimeRange;

	public String getFreeTimeRange() {
		return freeTimeRange;
	}

	public void setFreeTimeRange(String freeTimeRange) {
		this.freeTimeRange = freeTimeRange;
	}

}
