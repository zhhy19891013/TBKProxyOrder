package com.yj.db.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yj.db.model.reuse.BasicModel;
import com.yj.util.EncryptionUtil;

public class SystemUser extends BasicModel {
	private static final long serialVersionUID = 1L;
	// 二级代理模式权限
	public static final String GROUP_NAME_ADMIN = "admin";//管理员
	public static final String GROUP_NAME_PROXY = "proxy";//一级代理
	public static final String GROUP_NAME_PROXY2 = "proxy2";//二级代理
	// 三级分销模式权限
	public static final String GROUP_NAME_PROXY3 = "proxy3";//三级代理
	public static final String GROUP_NAME_QUDAO = "qudao";//渠道权限
	// 企业分类
	public static final String USER_NAME_COMMPANY = "企业用户";
	public static final String USER_NAME_PERSONAL = "个人用户";
	
	public static final String GROUP_NAME_EMP = "emp";

	// 密码盐
	public static final String SALT = "s4lt";

	private String userName;
	private String password;
	private String group_name;
	private Timestamp reg_time;
	private Date regDate;
	private Timestamp expire_time;
	private String qq;
	private String phone;
	private String email;
	private String pictUrl;// 二维码名称
	private String fatherName;// 一级代理名称
	private Double proxyRate;// 分成比例
	private Double integralRate;// 积分比例
	private String userType;// 企业用户||个人用户
	private String fatherWx;
	private String groupAdminName;
	private String databaseFlag;
	private String zfb;
	private String productPid;// 对应商品pid 三段式
	private String pid;// 后两位pid
	private String lastPid;// 最后一位pid（广告位pid）
	private String qudaoName;// 渠道名称
	private String realName;//真实姓名
	private String openId;//微信识别标志
	private String pidInfo;//pid信息
	private int proxyLevel;
	private String jdPid;//京东PID
	private Date BanTime;
	
	
	
	public Date getBanTime() {
		return BanTime;
	}

	public void setBanTime(Date banTime) {
		BanTime = banTime;
	}

	public String getJdPid() {
		return jdPid;
	}

	public void setJdPid(String jdPid) {
		this.jdPid = jdPid;
	}

	public int getProxyLevel() {
		return proxyLevel;
	}

	public void setProxyLevel(int proxyLevel) {
		this.proxyLevel = proxyLevel;
	}

	// 显示字段
	private String regTimeStr;
	private  String proxyType;//代理名称
	// 传值字段
	private transient String webSite;

	private String regDate1;
	private String regDate2;
	public String getPidInfo() {
		return pidInfo;
	}

	public void setPidInfo(String pidInfo) {
		this.pidInfo = pidInfo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}

	public String getRegDate1() {
		return regDate1;
	}

	public void setRegDate1(String regDate1) {
		this.regDate1 = regDate1;
	}

	public String getRegDate2() {
		return regDate2;
	}

	public void setRegDate2(String regDate2) {
		this.regDate2 = regDate2;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getQudaoName() {
		return qudaoName;
	}

	public void setQudaoName(String qudaoName) {
		this.qudaoName = qudaoName;
	}

	public String getLastPid() {
		return lastPid;
	}

	public void setLastPid(String lastPid) {
		this.lastPid = lastPid;
	}

	public String getProductPid() {
		return productPid;
	}

	public void setProductPid(String productPid) {
		this.productPid = productPid;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getZfb() {
		return zfb;
	}

	public void setZfb(String zfb) {
		this.zfb = zfb;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDatabaseFlag() {
		return databaseFlag;
	}

	public void setDatabaseFlag(String databaseFlag) {
		this.databaseFlag = databaseFlag;
	}

	public String getGroupAdminName() {
		return groupAdminName;
	}

	public void setGroupAdminName(String groupAdminName) {
		this.groupAdminName = groupAdminName;
	}

	public String showProxyName() {
		if (getGroup_name().equals("proxy")) {
			return "一级代理";
		}
		if (getGroup_name().equals("proxy2")) {
			return "二级代理";
		}
		if(getGroup_name().equals("qudao")){
			return "渠道";
		}
		if(getGroup_name().equals("emp")){
			return "员工";
		}
		return "管理员";
	}

	public String getFatherWx() {
		return fatherWx;
	}

	public void setFatherWx(String fatherWx) {
		this.fatherWx = fatherWx;
	}

	public Double getIntegralRate() {
		return integralRate;
	}

	public void setIntegralRate(Double integralRate) {
		this.integralRate = integralRate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Double getProxyRate() {
		return proxyRate;
	}

	public void setProxyRate(Double proxyRate) {
		this.proxyRate = proxyRate;
	}

	public String getRegTimeStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (null != getReg_time()) {
			return format.format(getReg_time());
		}
		return "";
	}

	public void setRegTimeStr(String regTimeStr) {
		this.regTimeStr = regTimeStr;
	}

	public String getPictUrl() {
		return pictUrl;
	}

	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	/**
	 * 转换密码 明文+s4lt 转sha1
	 * 
	 * @param pwd
	 */
	public void convertPWD(String pwd) {
		pwd = pwd + SystemUser.SALT;
		setPassword(new EncryptionUtil().getDigestOfString(pwd.getBytes()));
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String groupName) {
		group_name = groupName;
	}

	public Timestamp getReg_time() {
		return reg_time;
	}

	public void setReg_time(Timestamp regTime) {
		setRegDate(regTime);
		reg_time = regTime;
	}

	public Timestamp getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(Timestamp expireTime) {
		expire_time = expireTime;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
