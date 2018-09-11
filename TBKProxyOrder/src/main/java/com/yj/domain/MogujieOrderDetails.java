package com.yj.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.yj.domain.base.BasicModel;
/**
 * 
 * @author 蘑菇街订单详情
 *
 */
public class MogujieOrderDetails extends BasicModel {
	private String orderNo;//订单号
	private String groupId;//推广位ID
	private String channel;//渠道类型
	private String orderStatus;
	private Date updateTime;//
	private Double expense;//真实佣金
	private String paymentType;//付款类型
	private String tradeItemId;//商品ID
	private String shopInfo;//店铺信息
	private Integer amount;//商品数量
	private Double price;//商品价格
	private String name;//商品名字
	private String commission;//佣金比例
	private String productUrl;//商品链接
	private String category;// 类目id
	private String categoryName;// 类目名
	private Date orderTime;//订单创建时间
	private String MogujieName;//蘑菇街账户名
	private String paymentStatus;// 订单状态 注解：10000是订单未付款 20000是已付款  90000订单取消 40000 确认收货 30000退款
	private String productNo;//商品ID

	
	
	
	
	/**
	 * 功能性字段
	 */
	private String userName;//用户名字
	private Date createdDate;//创建时间
	private Double rate;// 佣金比例
	private String createTime1;// 创建时间（最小）
	private String createTime2;// 创建时间（最大）
	
	
	
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Double getExpense() {
		return expense;
	}
	public void setExpense(Double expense) {
		this.expense = expense;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getTradeItemId() {
		return tradeItemId;
	}
	public void setTradeItemId(String tradeItemId) {
		this.tradeItemId = tradeItemId;
	}
	public String getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(String shopInfo) {
		this.shopInfo = shopInfo;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Double getRate() {
		 BigDecimal b = new BigDecimal(Double.parseDouble(commission.replace("%", ""))/100);
		return  b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getMogujieName() {
		return MogujieName;
	}
	public void setMogujieName(String mogujieName) {
		MogujieName = mogujieName;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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
	public Object getExcelData() {
		Map map = new LinkedHashMap();
		map.put("订单日期", getOrderTime());
		map.put("订单号", getOrderNo());
		map.put("推广位ID",getGroupId());
		map.put("更新时间",getUpdateTime());
		map.put("订单佣金",getExpense());
		map.put("数量",getAmount());
		map.put("单价",getPrice());
		map.put("商品名称",getName());
		map.put("商品链接",getProductUrl());
		map.put("用户名",getUserName());
		map.put("商品ID",getProductNo());
		return map;
	}
		
	
}
