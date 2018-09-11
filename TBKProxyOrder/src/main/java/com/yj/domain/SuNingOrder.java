package com.yj.domain;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import com.yj.domain.base.BasicModel;
import com.yj.util.NumCalUtil;

/**
 * 苏宁订单 tx
 * 
 * @author Administrator
 *
 */
public class SuNingOrder extends BasicModel {
	private String userName;
	private String orderCode;

	private Timestamp payTime;// 支付时间，格式：yyyy-MM-dd HH:mm:ss
	private Timestamp orderSubmitTime;// 下单时间，格式：yyyy-MM-dd HH:mm:ss
	private String orderLineNumber;// 订单行项目号
	private String orderLineStatusDesc;// 订单行项目状态
	private Timestamp orderLineStatusChangeTime;// 行项目状态更新时间，格式：yyyy-MM-dd
												// HH:mm:ss
	private String orderLineOrigin;// 订单行来源（PC端、无线端）
	private String productName;// 商品名称
	private int saleNum;// 商品数量
	private double payAmount;// 实付金额
	private String orderLineFlag;// 订单行标记
	private String childAccountId;// 子推广账号ID
	private String sellerName;// 商户名称
	private String sellerCode;// 商户编码;
	private String goodsNum;// 商品编码
	private double commissionRatio;// 佣金比例
	private double prePayCommission;// 预估佣金
	private String productFirstCatalog;// 一级目录
	private String productSecondCatalog;// 二级目录
	private String productThirdCatalog;// 三级目录
	private String orderType;// 商品归属
	private String positionId;// 推广位ID
	private String goodsGroupCatalog;// 商品组目录编码

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public Timestamp getOrderSubmitTime() {
		return orderSubmitTime;
	}

	public void setOrderSubmitTime(Timestamp orderSubmitTime) {
		this.orderSubmitTime = orderSubmitTime;
	}

	public String getOrderLineNumber() {
		return orderLineNumber;
	}

	public void setOrderLineNumber(String orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}

	public String getOrderLineStatusDesc() {
		return orderLineStatusDesc;
	}

	public void setOrderLineStatusDesc(String orderLineStatusDesc) {
		this.orderLineStatusDesc = orderLineStatusDesc;
	}

	public Timestamp getOrderLineStatusChangeTime() {
		return orderLineStatusChangeTime;
	}

	public void setOrderLineStatusChangeTime(Timestamp orderLineStatusChangeTime) {
		this.orderLineStatusChangeTime = orderLineStatusChangeTime;
	}

	public String getOrderLineOrigin() {
		return orderLineOrigin;
	}

	public void setOrderLineOrigin(String orderLineOrigin) {
		this.orderLineOrigin = orderLineOrigin;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public String getOrderLineFlag() {
		return orderLineFlag;
	}

	public void setOrderLineFlag(String orderLineFlag) {
		this.orderLineFlag = orderLineFlag;
	}

	public String getChildAccountId() {
		return childAccountId;
	}

	public void setChildAccountId(String childAccountId) {
		this.childAccountId = childAccountId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public double getCommissionRatio() {
		return commissionRatio;
	}

	public void setCommissionRatio(double commissionRatio) {
		this.commissionRatio = commissionRatio;
	}

	public double getPrePayCommission() {
		prePayCommission = NumCalUtil.chenDouble(commissionRatio, payAmount);
		return prePayCommission;
	}

	public void setPrePayCommission(double prePayCommission) {
		this.prePayCommission = prePayCommission;
	}

	public String getProductFirstCatalog() {
		return productFirstCatalog;
	}

	public void setProductFirstCatalog(String productFirstCatalog) {
		this.productFirstCatalog = productFirstCatalog;
	}

	public String getProductSecondCatalog() {
		return productSecondCatalog;
	}

	public void setProductSecondCatalog(String productSecondCatalog) {
		this.productSecondCatalog = productSecondCatalog;
	}

	public String getProductThirdCatalog() {
		return productThirdCatalog;
	}

	public void setProductThirdCatalog(String productThirdCatalog) {
		this.productThirdCatalog = productThirdCatalog;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getGoodsGroupCatalog() {
		return goodsGroupCatalog;
	}

	public void setGoodsGroupCatalog(String goodsGroupCatalog) {
		this.goodsGroupCatalog = goodsGroupCatalog;
	}

	public Object getExcelData() {
		Map map = new LinkedHashMap();
		map.put("订单日期", getPayTime());
		map.put("商品ID", getGoodsNum());
		map.put("商品名称", getProductName());
		map.put("价格", getPayAmount());
		map.put("数量", getSaleNum());
		map.put("订单佣金", getPrePayCommission());
		map.put("实际支付金额", getPayAmount());
		map.put("订单编号", getOrderLineNumber());
		map.put("订单状态", getOrderLineStatusDesc());
		map.put("推广位ID", getPositionId());
		map.put("用户名", getUserName());
		return map;
	}

}
