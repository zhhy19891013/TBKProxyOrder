package com.yj.domain;

import java.util.Date;

import com.yj.domain.base.BasicModel;

/**
 * 系统绑定的京东联盟账号
 */
public class CpsAccountJd extends BasicModel {
	private static final long serialVersionUID = 1L;
	public static final String ACCOUNT_TYPE_CAIJI = "采集";
	public static final String ACCOUNT_TYPE_ZHUANLIAN = "转链";
	public static final String ACCOUNT_TYPE_MD = "免单";

	private String jdName;// 京东账号名称
	private String unionID;// 京东unionid
	private String accountType;// 账号类型
	private String siteID;// 推广位id
	private String empowerKey;// 授权key
	private Date stime;// key领取时间
	private Date etime;// key过期时间

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getUnionID() {
		return unionID;
	}

	public void setUnionID(String unionID) {
		this.unionID = unionID;
	}

	public String getJdName() {
		return jdName;
	}

	public void setJdName(String jdName) {
		this.jdName = jdName;
	}

	public String getEmpowerKey() {
		if (null != empowerKey) {
			empowerKey = empowerKey.replaceAll("\t", "");
		}
		return empowerKey;
	}

	public void setEmpowerKey(String empowerKey) {
		this.empowerKey = empowerKey;
	}

	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	public Date getEtime() {
		return etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

}
