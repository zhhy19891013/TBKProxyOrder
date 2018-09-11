package com.yj.domain;

import java.sql.Timestamp;
import java.util.Date;

import com.yj.domain.base.BasicModel;

/**
 * 淘宝订单详情
 * 
 * @author Administrator
 * 
 */
public class PaymentRecord extends BasicModel {
	private String tbName;// 淘宝账号
	private String userName;// 用户名
	private String exNickName;// 店铺名称
	private String exShopTitle;// 店铺名称
	private Timestamp createTime;// 创建时间
	private Timestamp earningTime;// 结算时间
	private Date lastUpdate;// 最后更新日期
	private Date createDate;
	private String auctionId;// 宝贝id
	private String auctionTitle;
	private String auctionUrl;
	private Double tkShareRate;
	private Double realPayFeeString;// 付款金额
	private Double auctionNum;// 购买数量
	private Double payPrice;// 原价
	private String taobaoTradeParentId;// 淘宝交易单号
	private String terminalType;// 交易平台
	private Double feeString;// 预估收入（佣金）
	private Double tkPubShareFeeString;// 预计收入
	private Double tk3rdPubShareFee;// 分成比例
	private Double finalDiscountToString;// 收入比例
	private Double totalAlipayFeeString;// 结算金额
	private String payStatus;// 订单状态 3 订单结算 12 订单付款 13订单失效 14订单成功
	private String pid;// 订单对应的pid
	private String wqRemark;// 维权订单备注
	private String bizType;
	private String tkBizTag;
	private String pictUrl;// 图片地址
	
	

	public String getPictUrl() {
		return pictUrl;
	}

	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}

	public String getUserName() {
		if (null != userName) {
			userName = userName.replaceAll("\"", "").replaceAll(" ", "").replaceAll("【", "").replaceAll("】", "")
					.replaceAll(" ", "").trim();
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getFeeString() {
		return feeString;
	}

	public void setFeeString(Double feeString) {
		this.feeString = feeString;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Double getFinalDiscountToString() {
		return finalDiscountToString;
	}

	public Double getTotalAlipayFeeString() {
		return totalAlipayFeeString;
	}

	public void setTotalAlipayFeeString(Double totalAlipayFeeString) {
		this.totalAlipayFeeString = totalAlipayFeeString;
	}

	public String getTbName() {
		if (null != tbName) {
			tbName = tbName.replaceAll("\"", "").replaceAll(" ", "").replaceAll("【", "").replaceAll("】", "")
					.replaceAll(" ", "").trim();
		}
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		setCreateDate(createTime);
		this.createTime = createTime;
	}

	public String getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(String auctionId) {
		this.auctionId = auctionId;
	}

	public Timestamp getEarningTime() {
		return earningTime;
	}

	public void setEarningTime(Timestamp earningTime) {
		this.earningTime = earningTime;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getTkBizTag() {
		return tkBizTag;
	}

	public void setTkBizTag(String tkBizTag) {
		this.tkBizTag = tkBizTag;
	}

	public Double getTk3rdPubShareFee() {
		return tk3rdPubShareFee;
	}

	public void setTk3rdPubShareFee(Double tk3rdPubShareFee) {
		this.tk3rdPubShareFee = tk3rdPubShareFee;
	}

	public String getAuctionTitle() {
		if (null != auctionTitle) {
			auctionTitle = auctionTitle.replaceAll("\"", "").replaceAll(" ", "").replaceAll("【", "").replaceAll("】", "")
					.replaceAll(" ", "").replace("\t", "").trim();
		}
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

	public Double getTkShareRate() {
		return tkShareRate;
	}

	public void setTkShareRate(Double tkShareRate) {
		this.tkShareRate = tkShareRate;
	}

	public String getExShopTitle() {
		return exShopTitle;
	}

	public void setExShopTitle(String exShopTitle) {
		this.exShopTitle = exShopTitle;
	}

	public Double getRealPayFeeString() {
		return realPayFeeString;
	}

	public void setRealPayFeeString(Double realPayFeeString) {
		this.realPayFeeString = realPayFeeString;
	}

	public Double getAuctionNum() {
		return auctionNum;
	}

	public void setAuctionNum(Double auctionNum) {
		this.auctionNum = auctionNum;
	}

	public Double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}

	public String getTaobaoTradeParentId() {
		return taobaoTradeParentId;
	}

	public void setTaobaoTradeParentId(String taobaoTradeParentId) {
		this.taobaoTradeParentId = taobaoTradeParentId;
	}

	public String getExNickName() {
		return exNickName;
	}

	public void setExNickName(String exNickName) {
		this.exNickName = exNickName;
	}

	public Double getTkPubShareFeeString() {
		return tkPubShareFeeString;
	}

	public void setTkPubShareFeeString(Double tkPubShareFeeString) {
		this.tkPubShareFeeString = tkPubShareFeeString;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public void setFinalDiscountToString(Double finalDiscountToString) {
		this.finalDiscountToString = finalDiscountToString;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getWqRemark() {
		if (null == wqRemark) {
			wqRemark = "淘宝客推广";
		}
		return wqRemark;
	}

	public void setWqRemark(String wqRemark) {
		this.wqRemark = wqRemark;
	}

}
