package com.yj.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yj.domain.base.BasicModel;
import com.yj.util.EncryptionUtil;

/**
 * 系统用户
 * 
 * @author Administrator
 */
public class SystemUser extends BasicModel {
	// 二级代理模式权限
	public static final String GROUP_NAME_ADMIN = "admin";// 管理员
	public static final String GROUP_NAME_PROXY = "proxy";// 一级代理
	public static final String GROUP_NAME_PROXY2 = "proxy2";// 二级代理
	// 三级分销模式权限
	public static final String GROUP_NAME_PROXY3 = "proxy3";// 三级代理
	public static final String GROUP_NAME_QUDAO = "qudao";// 渠道权限
	public static final String GROUP_NAME_MANAGER = "manager";// jingli权限
	public static final String GROUP_NAME_FILIALE = "filiale";// fengongsi权限

	// 企业分类
	public static final String USER_NAME_COMMPANY = "企业用户";
	public static final String USER_NAME_PERSONAL = "个人用户";
	public static final String GROUP_NAME_EMP = "emp";
	// 密码盐
	public static final String SALT = "s4lt";
	// 用户信息
	private String userName;// 用户名
	private String password;// 密码
	private String qq;// qq
	private String phone;// 手机号
	private String email;// 微信
	private String pictUrl;// 头像路径
	private String zfb;// 支付宝
	private String realName;// 真实姓名
	private String openId;// 微信识别标志（小程序使用）
	// 时间信息
	private Date BanTime;// 冻结时间
	private Timestamp expire_time;// 暂无使用
	private Timestamp lastLoginTime;// 最后登录时间
	private Timestamp reg_time;// 注册时间
	private Date regDate;
	// 角色信息
	private String inviteCode;// 邀请码
	private String fatherName;// 一级代理名称
	private String gfName;// 上上级
	private String fatherWx;// 上级的微信号
	private String qudaoName;// 渠道名称
	private String groupAdminName;// 管理员组
	private String group_name;// 角色类型 proxy qudao
	private int proxyLevel = -1;// -1=无级别 0=初级 1=中级 2=高级I 3 =高级II 4=高级III 5=高级IV
								// 6=高级V 7=高级VI
	private String appWxOpenId;// app微信授权登录的openId(app使用)
	private long bid;// 商家id 线下使用
	// 推广位信息
	private String pidInfo;// 淘宝pid信息
	private String jdPid;// 京东pid信息
	private String mgjPid;// 蘑菇街pid信息
	private String cpsPid;// 粉惠购pid信息
	private String pddPid;// 拼多多PID
	private String suNingPid;// 苏宁pid
	private String jdFreePid;// 京东免单Pid
	// 过时/未使用的字段
	private String productPid;// 对应商品pid 三段式
	private String pid;// 后两位pid
	private String lastPid;// 最后一位pid（广告位pid）
	private String userType;// 企业用户||个人用户
	private Double proxyRate;// 分成比例（三级模式使用了后台设置的比例）
	private Double integralRate;// 积分比例（三级模式使用了后台设置的比例）
	private String databaseFlag;
	// 显示字段/传值字段（非持久化字段）
	private String regTimeStr;
	private String proxyType;// 代理名称
	private transient String webSite;
	private String regDate1;
	private String regDate2;
	// 新增显示字段
	private long sum; // 总卡券数量
	private long notUseCount;// 未使用数量
	private double notUseMoney;// 卡券未使用金额总和
	private double useMoney;// 卡券金额总和
	private String belongAgentName;
	private String proxyPath;
	private Double percent;
	private double totalWithdraw;// 累计提现

	/**
	 * 转换密码 明文+s4lt 转sha1
	 * 
	 * @param pwd
	 */
	public void convertPWD(String pwd) {
		pwd = pwd + SystemUser.SALT;
		setPassword(new EncryptionUtil().getDigestOfString(pwd.getBytes()));
	}

	public String getAppWxOpenId() {
		return appWxOpenId;
	}

	public void setAppWxOpenId(String appWxOpenId) {
		this.appWxOpenId = appWxOpenId;
	}

	public String getJdFreePid() {
		return jdFreePid;
	}

	public void setJdFreePid(String jdFreePid) {
		this.jdFreePid = jdFreePid;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public String getProxyPath() {
		return proxyPath;
	}

	public void setProxyPath(String proxyPath) {
		this.proxyPath = proxyPath;
	}

	public String getSuNingPid() {
		return suNingPid;
	}

	public void setSuNingPid(String suNingPid) {
		this.suNingPid = suNingPid;
	}

	public String getPddPid() {
		return pddPid;
	}

	public void setPddPid(String pddPid) {
		this.pddPid = pddPid;
	}

	public String getCpsPid() {
		return cpsPid;
	}

	public void setCpsPid(String cpsPid) {
		this.cpsPid = cpsPid;
	}

	public String getMgjPid() {
		return mgjPid;
	}

	public void setMgjPid(String mgjPid) {
		this.mgjPid = mgjPid;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getGfName() {
		return gfName;
	}

	public void setGfName(String gfName) {
		this.gfName = gfName;
	}

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

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public long getNotUseCount() {
		return notUseCount;
	}

	public void setNotUseCount(long notUseCount) {
		this.notUseCount = notUseCount;
	}

	public double getNotUseMoney() {
		return notUseMoney;
	}

	public void setNotUseMoney(double notUseMoney) {
		this.notUseMoney = notUseMoney;
	}

	public double getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(double useMoney) {
		this.useMoney = useMoney;
	}

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public String getBelongAgentName() {
		return belongAgentName;
	}

	public void setBelongAgentName(String belongAgentName) {
		this.belongAgentName = belongAgentName;
	}

	public double getTotalWithdraw() {
		return totalWithdraw;
	}

	public void setTotalWithdraw(double totalWithdraw) {
		this.totalWithdraw = totalWithdraw;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(getUserName());
		buffer.append("uid:").append(getDatabaseID() + "").append("手机号:").append(getPhone()).append("微信:")
				.append(getEmail()).append("支付宝:").append(getZfb());
		return buffer.toString();
	}

}
