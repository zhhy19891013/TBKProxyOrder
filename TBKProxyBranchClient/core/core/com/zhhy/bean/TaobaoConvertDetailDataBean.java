package com.zhhy.bean;

import java.util.Date;

public class TaobaoConvertDetailDataBean {
	private String category_id;//分类id
	private String coupon_click_url;//二合一链接
	private Date coupon_end_time;
	private String coupon_info;
	private Integer coupon_remain_count;
	private Date coupon_start_time;
	private Integer coupon_total_count;
	private String item_id;
	private Double max_commission_rate;
	
	
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCoupon_click_url() {
		return coupon_click_url;
	}
	public void setCoupon_click_url(String coupon_click_url) {
		this.coupon_click_url = coupon_click_url;
	}
	public Date getCoupon_end_time() {
		return coupon_end_time;
	}
	public void setCoupon_end_time(Date coupon_end_time) {
		this.coupon_end_time = coupon_end_time;
	}
	public String getCoupon_info() {
		return coupon_info;
	}
	public void setCoupon_info(String coupon_info) {
		this.coupon_info = coupon_info;
	}
	public Integer getCoupon_remain_count() {
		return coupon_remain_count;
	}
	public void setCoupon_remain_count(Integer coupon_remain_count) {
		this.coupon_remain_count = coupon_remain_count;
	}
	public Date getCoupon_start_time() {
		return coupon_start_time;
	}
	public void setCoupon_start_time(Date coupon_start_time) {
		this.coupon_start_time = coupon_start_time;
	}
	public Integer getCoupon_total_count() {
		return coupon_total_count;
	}
	public void setCoupon_total_count(Integer coupon_total_count) {
		this.coupon_total_count = coupon_total_count;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public Double getMax_commission_rate() {
		return max_commission_rate;
	}
	public void setMax_commission_rate(Double max_commission_rate) {
		this.max_commission_rate = max_commission_rate;
	}
	
	
}
