package com.yj.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yj.domain.base.BasicModel;

public class CloudOrderPdd extends BasicModel {
	private String userName;
	private String order_sn;// 推广订单编号
	private String goods_id;// 商品ID
	private String goods_name;// 商品名称
	private String goods_thumbnail_url;// 商品缩略图
	private int goods_quantity;// 购买的商品数量
	private int goods_price;// 商品价格(分)
	private int order_amount;// 实际支付金额(分)
	private Timestamp order_create_time;// 订单生成时间，UNIX时间戳
	private Double promotion_rate;// 佣金比例
	private int promotion_amount;// 佣金金额(分)
	private int order_status;// 订单状态：0-已支付；1-已成团；2-确认收货；3-审核成功；4-审核失败（不可提现）；5-已提现
	private String p_id;// 推广位ID
	private Date createDate;

	// 显示字段
	private double price;// 商品价格(元)
	private double realAmount;// 实际支付金额(元)
	private double commission;// 佣金金额(元)
	private String createTime1;
	private String createTime2;
	// 功能性字段
	private double jf;// 代理手机端查询专用
	private String orderTimeStr;
	private String status;// 代理手机端查询专用

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderTimeStr() {
		if (null != order_create_time) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(order_create_time);
		}
		return null;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_thumbnail_url() {
		return goods_thumbnail_url;
	}

	public void setGoods_thumbnail_url(String goods_thumbnail_url) {
		this.goods_thumbnail_url = goods_thumbnail_url;
	}

	public int getGoods_quantity() {
		return goods_quantity;
	}

	public void setGoods_quantity(int goods_quantity) {
		this.goods_quantity = goods_quantity;
	}

	public Timestamp getOrder_create_time() {
		return order_create_time;
	}

	public void setOrder_create_time(Timestamp order_create_time) {
		this.order_create_time = order_create_time;
		this.createDate = order_create_time;
	}

	public Double getPromotion_rate() {
		return promotion_rate;
	}

	public void setPromotion_rate(Double promotion_rate) {
		this.promotion_rate = promotion_rate;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public int getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(int goods_price) {
		this.goods_price = goods_price;
	}

	public int getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(int order_amount) {
		this.order_amount = order_amount;
	}

	public int getPromotion_amount() {
		return promotion_amount;
	}

	public void setPromotion_amount(int promotion_amount) {
		this.promotion_amount = promotion_amount;
	}

	public double getPrice() {
		return (new BigDecimal(goods_price).divide(new BigDecimal(100))).doubleValue();
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRealAmount() {
		return (new BigDecimal(order_amount).divide(new BigDecimal(100))).doubleValue();
	}

	public void setRealAmount(double realAmount) {
		this.realAmount = realAmount;
	}

	public double getCommission() {
		return (new BigDecimal(promotion_amount).divide(new BigDecimal(100))).doubleValue();
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime1() {
		return createTime1;
	}

	public void setCreateTime1(String createTime1) {
		this.createTime1 = createTime1;
	}

	public String getCreateTime2() {
		return createTime2;
	}

	public void setCreateTime2(String createTime2) {
		this.createTime2 = createTime2;
	}

	public double getJf() {
		return jf;
	}

	public void setJf(double jf) {
		this.jf = jf;
	}

	@Override
	public String toString() {
		return "order_sn:" + order_sn + ",goods_id" + goods_id + ",goods_name" + goods_name + ",goods_quantity"
				+ goods_quantity + ",goods_price" + goods_price + ",order_amount" + order_amount + ",order_create_time"
				+ order_create_time

				+ ",promotion_rate" + promotion_rate + ",promotion_amount" + promotion_amount + ",order_status"
				+ order_status + ",p_id" + p_id + ",createDate" + createDate;
	}

}
