package com.yj.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 订单固定配置
 * 
 * @author Administrator
 *
 */
@Component
@PropertySource({ "classpath:resources.properties" })
@ConfigurationProperties(prefix = "order")
public class OrderConfig {
	/**
	 * 同步长时间订单的开始时间
	 */
	private int beginHour;
	/**
	 * 同步长时间订单的结束时间
	 */
	private int endHour;
	/**
	 * 订单类型:淘宝
	 */
	private String typeTb;
	/**
	 * 订单类型:京东
	 */
	private String typeJd;
	/**
	 * 订单类型:拼多多
	 */
	private String typePdd;
	/**
	 * 订单类型:苏宁
	 */
	private String typeSn;
	/**
	 * 订单类型:蘑菇街
	 */
	private String typeMgj;
	/**
	 * 订单类型:自营
	 */
	private String typeZy;

	public String getTypeTb() {
		return typeTb;
	}

	public void setTypeTb(String typeTb) {
		this.typeTb = typeTb;
	}

	public String getTypeJd() {
		return typeJd;
	}

	public void setTypeJd(String typeJd) {
		this.typeJd = typeJd;
	}

	public String getTypePdd() {
		return typePdd;
	}

	public void setTypePdd(String typePdd) {
		this.typePdd = typePdd;
	}

	public String getTypeSn() {
		return typeSn;
	}

	public void setTypeSn(String typeSn) {
		this.typeSn = typeSn;
	}

	public String getTypeMgj() {
		return typeMgj;
	}

	public void setTypeMgj(String typeMgj) {
		this.typeMgj = typeMgj;
	}

	public String getTypeZy() {
		return typeZy;
	}

	public void setTypeZy(String typeZy) {
		this.typeZy = typeZy;
	}

	public void setBeginHour(int beginHour) {
		this.beginHour = beginHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getBeginHour() {
		return beginHour;
	}

	public int getEndHour() {
		return endHour;
	}

}
