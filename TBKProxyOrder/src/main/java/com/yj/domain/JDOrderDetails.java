package com.yj.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yj.domain.base.BasicModel;

/**
 * 京东订单明细模块
 */
public class JDOrderDetails extends BasicModel {

	private String itemId;// 商品ID
	private Double commision;// 订单佣金
	private String orderfrom;// 下单平台
	private String orderid;// 订单编号
	private Date ordertime;// 订单日期
	private String status;// 订单状态 //D 确认收货 M 等待收货 S已经结算 X 退款 R 售后 Y违规 N 订单未付款
	private String subunionid;// 下线ID
	private String username;// 用户名
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private Integer amount;// 数量
	private Double price;// 单价
	private String prodpic;// 图片地址
	private String productname;// 商品名称
	private String produrl;// 商品链接
	private String createTime1;// 创建时间（最小）
	private String createTime2;// 创建时间（最大）
	// 新增字段
	private Integer returnamount;// 退货数量
	private Double costs;// 计算佣金商品金额，不含优惠和运费
	private Double money;// 商品总金额
	private Double subsidyRate;// 补贴比例
	private String plus;// 是否是plus会员
	private Double commissionRate;// 佣金比例

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	private String orderTimeStr;

	public String getPlus() {
		return plus;
	}

	public void setPlus(String plus) {
		this.plus = plus;
	}

	public Double getSubsidyRate() {
		return subsidyRate;
	}

	public void setSubsidyRate(Double subsidyRate) {
		this.subsidyRate = subsidyRate;
	}

	public String getOrderTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null != ordertime) {
			return sdf.format(ordertime);
		}
		return null;
	}

	public Integer getReturnamount() {
		return returnamount;
	}

	public void setReturnamount(Integer returnamount) {
		this.returnamount = returnamount;
	}

	public Double getCosts() {
		return costs;
	}

	public void setCosts(Double costs) {
		this.costs = costs;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Double getCommision() {
		return commision;
	}

	public void setCommision(Double commision) {
		this.commision = commision;
	}

	public String getOrderfrom() {
		return orderfrom;
	}

	public void setOrderfrom(String orderfrom) {
		this.orderfrom = orderfrom;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubunionid() {
		return subunionid;
	}

	public void setSubunionid(String subunionid) {
		this.subunionid = subunionid;
	}

	public String getUsername() {
		if (null != username) {
			username = username.replaceAll("\t", "");
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getProdpic() {
		return prodpic;
	}

	public void setProdpic(String prodpic) {
		this.prodpic = prodpic;
	}

	public String getProductname() {
		if (null != productname) {
			productname = productname.replaceAll("\t", "");
		}
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProdurl() {
		return produrl;
	}

	public void setProdurl(String produrl) {
		this.produrl = produrl;
	}

	@Override
	public String toString() {
		return orderid;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JDOrderDetails) {
			JDOrderDetails d = (JDOrderDetails) obj;
			return d.getOrderid().equals(orderid);
		}
		return super.equals(obj);
	}
}
