package com.yj.db.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yj.db.model.reuse.BasicModel;

/**
 * 手机端管理员查询数据专用
 */
public class JDBind extends BasicModel { 
	
	
	
	
	private String jdName;
	private String unionID;
	

	public String getUnionID() {
		return unionID;
	}

	public void setUnionID(String unionID) {
		this.unionID = unionID;
	}

	public String getJdName() {
		return jdName;
	}

	public void setJdName(String jdName) {
		this.jdName = jdName;
	}


	
	 
}
