package com.yj.db.model;

import com.yj.db.model.reuse.BasicModel;
/**
 * 系统通用配置
 * @author Administrator
 */
public class SystemCommonConfig extends BasicModel{
	private static final long serialVersionUID = 1L;
	public static final String CONFIG_NAME_THREE_ALIMAMA="three_alimama_rate";//阿里妈妈费用10%
	public static final String CONFIG_NAME_THREEE_YUNYING="three_yunyin_rate";//运营商费用23%
	public static final String CONFIG_NAME_THREEE_PROXY1_RATE="three_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_THREE_PROXY2_RATE="three_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_THREE_PROXY3_RATE="three_proxy3_rate";//三级分销三级代理比例
	public static final String CONFIG_NAME_THREE_QUDAO_RATE="three_qudao_rate";//三级分销渠道比例
	public static final String CONFIG_NAME_THREE_MIN_RATE="three_min_rate";//最小佣金比例
	public static final String CONFIG_NAME_THREE_MIN_NUM="three_min_num";//最小佣金
	public static final String CONFIG_NAME_AUTO_CREATE_SITE="auto_create_site";//自动创建推广位
	public static final String CONFIG_NAME_RANK_OPEN="rank_open";//代理排行开关
	public static final String CONFIG_NAME_SIGN_IN="sign_in";//签到功能开关
	public static final String CONFIG_NAME_REGISTER_OPEN="register_open";//注册验证码功能开关
	public static final String CONFIG_NAME_SYSTEM_THEME="system_theme";//系统主题 0=老的 1=新的
	public static final String CONFIG_NAME_TWO_PROXY2_DEFAULT_RATE="two_default_rate";//二级代理模式默认二级比例
	//积分相关
	public static final String CONFIG_NAME_MIN_EXCHANGE_NUM="min_exchange_num";//最小提现金额
	public static final String CONFIG_NAME_LAST_IN_DAY="last_in_day";//延迟入账天数
	public static final String CONFIG_NAME_LAST_IN_NUM="last_in_num";//延迟入账金额
	public static final String CONFIG_NAME_IN_DAY="in_day";//入账天数
	//聚石塔相关
	public static final String CONFIG_NAME_USE_JST="use_jst";//是否使用聚石塔
	public static final String CONFIG_NAME_USE_TS="ts_switch";//是否使用推送
	public static final String CONFIG_NAME_TS_COUNT ="ts_count";//推送数目
	
	public static final String CONFIG_NAME_INTEGRAL_CONTROLLER ="integral_controller";//积分倍数
	
	private String configName;//配置名称
	private String configValue;//配置值
	private String configNote;//配置备注
	
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getConfigNote() {
		return configNote;
	}
	public void setConfigNote(String configNote) {
		this.configNote = configNote;
	}
	
}
