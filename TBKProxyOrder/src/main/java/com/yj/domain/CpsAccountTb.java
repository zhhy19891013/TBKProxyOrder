package com.yj.domain;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yj.domain.base.BasicModel;

/**
 * 系统绑定的淘宝联盟账号
 * 
 * @author yiju-zhhy
 * 
 */
public class CpsAccountTb extends BasicModel {
	private String xbTbName;// 小编淘宝名称
	private String productTbName;// 采集淘宝名称
	private String zlTbName;// 转链淘宝名称
	private String flTbName;// 返利淘宝名称
	// 非持久化字段
	private List<String> productTbNames;

	public String getFlTbName() {
		return flTbName;
	}

	public void setFlTbName(String flTbName) {
		this.flTbName = flTbName;
	}

	public String getXbTbName() {
		return xbTbName;
	}

	public void setXbTbName(String xbTbName) {
		this.xbTbName = xbTbName;
	}

	public String getProductTbName() {
		return productTbName;
	}

	public void setProductTbName(String productTbName) {
		this.productTbName = productTbName;
	}

	public String getZlTbName() {
		return zlTbName;
	}

	public void setZlTbName(String zlTbName) {
		this.zlTbName = zlTbName;
	}

	public List<String> getProductTbNames() {
		if (null != productTbName) {
			productTbNames = JSON.parseArray(productTbName, String.class);
		} else {
			productTbNames = new ArrayList<String>();
		}
		return productTbNames;
	}

	public void setProductTbNames(List<String> productTbNames) {
		this.productTbNames = productTbNames;
	}

}
