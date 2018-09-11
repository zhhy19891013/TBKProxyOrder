package com.yj.domain;

import java.sql.Timestamp;
import java.util.Date;

import com.yj.domain.base.BasicModel;

/**
 * 用户积分
 * 
 * @author yiju-zhhy
 */
public class SystemUserIntegral extends BasicModel {

	public final static String INTEGRAL_STATUS_NORMAL = "订单有效";
	public final static String INTEGRAL_STATUS_NO = "订单退回";
	public final static String INTEGRAL_STATUS_COMMIT = "订单提交";
	public final static String INTEGRAL_STATUS_INVALID = "订单退款";
	public final static String EXCHANGE_ORDER_NUMBER = "exchange";

	private String order_number;// 订单号
	private Timestamp create_time;// 创建时间
	private Date create_date;// 创建日期
	private String user_name;// 用户名
	private Double realIntegral;// 实际积分
	private Timestamp orderDate;// 订单日期
	private String orderStatus;// 订单状态 订单有效 订单退款 无效订单
	private String integralOrign;// 来源 代理分成 提交订单 积分兑换
	private String integralPrividor;// 积分提供者
	private String integralNote;// 积分备注
	private Double totalAlipayFeeString;// 佣金
	private Double integralRate;// 积分比例
	private String groupName;// 用户角色
	private String auctionId;// 商品id
	private String auctionTitle;// 商品标题
	private String auctionUrl;// 商品链接
	private String tbName;// 淘宝账号 淘宝的为淘宝账号 其他平台的为 拼多多订单 京东订单 蘑菇街订单 苏宁订单
	private Double realPay;// 付款金额
	private Double aliIntegral;// 阿里积分
	private double integral;// 预估积分
	private Double finalDiscountToString;// 佣金比例

	private String createTime1;
	private String createTime2;
	
	
	
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

	public Double getFinalDiscountToString() {
		return finalDiscountToString;
	}

	public void setFinalDiscountToString(Double finalDiscountToString) {
		this.finalDiscountToString = finalDiscountToString;
	}

	public Double getAliIntegral() {
		return aliIntegral;
	}

	public void setAliIntegral(Double aliIntegral) {
		this.aliIntegral = aliIntegral;
	}

	public Double getRealPay() {
		return realPay;
	}

	public void setRealPay(Double realPay) {
		this.realPay = realPay;
	}

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getAuctionTitle() {
		return auctionTitle;
	}

	public void setAuctionTitle(String auctionTitle) {
		this.auctionTitle = auctionTitle;
	}

	public String getAuctionUrl() {
		return auctionUrl;
	}

	public void setAuctionUrl(String auctionUrl) {
		this.auctionUrl = auctionUrl;
	}

	public String getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(String auctionId) {
		this.auctionId = auctionId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Double getIntegralRate() {
		return integralRate;
	}

	public void setIntegralRate(Double integralRate) {
		this.integralRate = integralRate;
	}

	public Double getTotalAlipayFeeString() {
		return totalAlipayFeeString;
	}

	public void setTotalAlipayFeeString(Double totalAlipayFeeString) {
		this.totalAlipayFeeString = totalAlipayFeeString;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getIntegralNote() {
		if (null == integralNote) {
			integralNote = "订单有效";
		}
		return integralNote;
	}

	public void setIntegralNote(String integralNote) {
		this.integralNote = integralNote;
	}

	public String getIntegralPrividor() {
		return integralPrividor;
	}

	public void setIntegralPrividor(String integralPrividor) {
		this.integralPrividor = integralPrividor;
	}

	public String getIntegralOrign() {
		return integralOrign;
	}

	public void setIntegralOrign(String integralOrign) {
		this.integralOrign = integralOrign;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public Double getRealIntegral() {
		return realIntegral;
	}

	public void setRealIntegral(Double realIntegral) {
		this.realIntegral = realIntegral;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String orderNumber) {
		order_number = orderNumber;
	}

	public double getIntegral() {
		return integral;
	}

	public void setIntegral(double integral) {
		this.integral = integral;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp createTime) {
		setCreate_date(createTime);
		create_time = createTime;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String userName) {
		user_name = userName;
	}

}
