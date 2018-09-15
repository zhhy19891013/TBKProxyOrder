package com.yj.db.model.reuse;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 媒体效果 和 淘宝客活动推广的基类
 * @author Administrator
 *
 */
public class ReportBase extends BasicModel{
	private static final long serialVersionUID = 1L;
	private Date thedate;//日期
	private Integer alipayNum=0;//付款笔数
	private Integer mixClick=0;//点击数
	private Double alipayRec=0.0;//效果预估
	private Double rec=0.0;//预估收入
	private String reportId;//日期+渠道编号
	//以下字段阿里妈妈获取不知道含义 先保留
	private String mixPv;
	private String mixCtr;
	private String mixEcpm;
	private Double alipayAmt;
	private Double mixRphc;
	//以下字段为计算字段
	private Double clickConversion=0.0;//点击转化=付款笔数/点击数
	private Double clickWorth=0.0;//点击价值=预估收入/点击数
	private Double pct=0.0;//平均客单=效果预估/付款笔数
	//软件账号信息
	private String tbName;//淘宝名称
	private String tbkName;//蚂蚁联盟账号
	
	
	//
	private String thedate1;//开始
	private String thedate2;//结束
	
	
	

	public String getThedate1() {
		return thedate1;
	}

	public void setThedate1(String thedate1) {
		this.thedate1 = thedate1;
	}

	public String getThedate2() {
		return thedate2;
	}

	public void setThedate2(String thedate2) {
		this.thedate2 = thedate2;
	}

	/**
	 * 计算下几个计算字段
	 */
	public void calculation(){
		//点击转化=付款笔数/点击数
		if(alipayNum!=0&&mixClick!=0){
			BigDecimal bd1 = new BigDecimal(alipayNum+"");
			BigDecimal bd2 = new BigDecimal(mixClick+"");
			clickConversion =bd1.divide(bd2,2).doubleValue();
		}
		//点击价值=预估收入/点击数
		if(alipayRec!=0.0&&mixClick!=0){
			BigDecimal bd1 = new BigDecimal(alipayRec+"");
			BigDecimal bd2 = new BigDecimal(mixClick+"");
			clickWorth =bd1.divide(bd2,2).doubleValue();
		}
		//平均客单=效果预估/付款笔数
		if(alipayNum!=0.0&&alipayRec!=0){
			BigDecimal bd1 = new BigDecimal(alipayRec+"");
			BigDecimal bd2 = new BigDecimal(alipayNum+"");
			pct =bd1.divide(bd2,2).doubleValue();
		}
		generateReportId();
	}
	
	public void generateReportId(){
		
	}

	public Date getThedate() {
		return thedate;
	}

	public void setThedate(Date thedate) {
		this.thedate = thedate;
	}

	public Integer getAlipayNum() {
		return alipayNum;
	}

	public void setAlipayNum(Integer alipayNum) {
		this.alipayNum = alipayNum;
	}

	public Integer getMixClick() {
		return mixClick;
	}

	public void setMixClick(Integer mixClick) {
		this.mixClick = mixClick;
	}

	public Double getAlipayRec() {
		return alipayRec;
	}

	public void setAlipayRec(Double alipayRec) {
		this.alipayRec = alipayRec;
	}

	public Double getRec() {
		return rec;
	}

	public void setRec(Double rec) {
		this.rec = rec;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getMixPv() {
		return mixPv;
	}

	public void setMixPv(String mixPv) {
		this.mixPv = mixPv;
	}

	public String getMixCtr() {
		return mixCtr;
	}

	public void setMixCtr(String mixCtr) {
		this.mixCtr = mixCtr;
	}

	public String getMixEcpm() {
		return mixEcpm;
	}

	public void setMixEcpm(String mixEcpm) {
		this.mixEcpm = mixEcpm;
	}

	public Double getAlipayAmt() {
		return alipayAmt;
	}

	public void setAlipayAmt(Double alipayAmt) {
		this.alipayAmt = alipayAmt;
	}

	public Double getMixRphc() {
		return mixRphc;
	}

	public void setMixRphc(Double mixRphc) {
		this.mixRphc = mixRphc;
	}

	public Double getClickConversion() {
		return clickConversion;
	}

	public void setClickConversion(Double clickConversion) {
		this.clickConversion = clickConversion;
	}

	public Double getClickWorth() {
		return clickWorth;
	}

	public void setClickWorth(Double clickWorth) {
		this.clickWorth = clickWorth;
	}

	public Double getPct() {
		return pct;
	}

	public void setPct(Double pct) {
		this.pct = pct;
	}

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getTbkName() {
		return tbkName;
	}

	public void setTbkName(String tbkName) {
		this.tbkName = tbkName;
	}
	
	
}
