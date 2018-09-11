package com.yj.domain;

import com.yj.domain.base.BasicModel;
/**
 * 系统通用配置
 * @author Administrator
 */
public class SystemCommonConfig extends BasicModel{
	//京东免单相关
	public static final String CONFIG_NAME_JD_FREE_BEGIN="freeBeginDate";
	public static final String CONFIG_NAME_JD_FREE_END="";
	
	//订单抓取相关
	public static final String CONFIG_NAME_ORDER_GRAP_TB_MODEL="order_grap_tb_model";//淘宝订单抓取模式
	public static final String CONFIG_NAME_ORDER_GRAP_TB_SLEEP="order_grap_tb_sleep";//淘宝订单延时秒数
	public static final String CONFIG_NAME_ORDER_GRAP_TB_FK_DAY="order_grap_tb_fk_day";//淘宝订单付款抓取天数
	public static final String CONFIG_NAME_ORDER_GRAP_TB_TK_DAY="order_grap_tb_tk_day";//淘宝订单退款抓取天数
	public static final String CONFIG_NAME_ORDER_GRAP_TB_JS_DAY="order_grap_tb_js_day";//淘宝订单结算天数
	//系统相关
	public static final String CONFIG_NAME_THREE_ALIMAMA="three_alimama_rate";//阿里妈妈费用10%
	public static final String CONFIG_NAME_THREEE_YUNYING="three_yunyin_rate";//运营商费用23%
	public static final String CONFIG_NAME_THREEE_PROXY1_RATE="three_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_THREE_PROXY2_RATE="three_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_THREE_PROXY3_RATE="three_proxy3_rate";//三级分销三级代理比例
	public static final String CONFIG_NAME_THREE_QUDAO_RATE="three_qudao_rate";//三级分销渠道比例
	
	//拼多多积分配比
	public static final String CONFIG_NAME_PDD_YUNYING="pdd_yunyin_rate";//运营商费用
	public static final String CONFIG_NAME_PDD_PROXY1_RATE="pdd_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_PDD_PROXY2_RATE="pdd_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_PDD_PROXY3_RATE="pdd_proxy3_rate";//三级分销三级代理比例
	public static final String CONFIG_NAME_PDD_QUDAO_RATE="pdd_qudao_rate";//三级分销渠道比例
	
	//苏宁积分配比
	public static final String CONFIG_NAME_SUNING_TECH_YUNYING="suning_tech_rate";//技术支持费用
	public static final String CONFIG_NAME_SUNING_YUNYING="suning_yunyin_rate";//运营商费用
	public static final String CONFIG_NAME_SUNING_PROXY1_RATE="suning_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_SUNING_PROXY2_RATE="suning_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_SUNING_PROXY3_RATE="suning_proxy3_rate";//三级分销三级代理比例
	public static final String CONFIG_NAME_SUNING_QUDAO_RATE="suning_qudao_rate";//三级分销渠道比例
	
	
	//---自营商城积分配比
	public static final String CONFIG_NAME_TECH_YUNYING="fhg_tech_rate";//技术支持费用
	public static final String CONFIG_NAME_FHG_YUNYING="fhg_yunyin_rate";//运营商费用
	public static final String CONFIG_NAME_FHG_PROXY1_RATE="fhg_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_FHG_PROXY2_RATE="fhg_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_FHG_PROXY3_RATE="fhg_proxy3_rate";//三级分销三级代理比例
	public static final String CONFIG_NAME_FHG_QUDAO_RATE="fhg_qudao_rate";//三级分销渠道比例
	
	//京东积分配比
	public static final String CONFIG_NAME_JD_YUNYING="jd_yunyin_rate";//运营商费用
	public static final String CONFIG_NAME_JD_PROXY1_RATE="jd_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_JD_PROXY2_RATE="jd_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_JD_PROXY3_RATE="jd_proxy3_rate";//三级分销三级代理比例
	public static final String CONFIG_NAME_JD_QUDAO_RATE="jd_qudao_rate";//三级分销渠道比例
	
	//蘑菇街积分配比
	public static final String CONFIG_NAME_MGJ_YUNYING="mgj_yunyin_rate";//运营商费用
	public static final String CONFIG_NAME_MGJ_PROXY1_RATE="mgj_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_MGJ_PROXY2_RATE="mgj_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_MGJ_PROXY3_RATE="mgj_proxy3_rate";//三级分销三级代理比例
	public static final String CONFIG_NAME_MGJ_QUDAO_RATE="mgj_qudao_rate";//三级分销渠道比例
	
	public static final String CONFIG_NAME_THREE_MIN_RATE="three_min_rate";//最小佣金比例
	public static final String CONFIG_NAME_THREE_MIN_NUM="three_min_num";//最小佣金
	public static final String CONFIG_NAME_AUTO_CREATE_SITE="auto_create_site";//自动创建推广位
	public static final String CONFIG_NAME_RANK_OPEN="rank_open";//代理排行开关
	public static final String CONFIG_NAME_SIGN_IN="sign_in";//签到功能开关
	public static final String CONFIG_NAME_REGISTER_OPEN="register_open";//注册验证码功能开关
	public static final String CONFIG_NAME_SYSTEM_THEME="system_theme";//系统主题 0=老的 1=新的
	public static final String CONFIG_NAME_TWO_PROXY2_DEFAULT_RATE="two_default_rate";//二级代理模式默认二级比例
	//群发相关
	public static final String CONFIG_NAME_XB_TS="xbts";//是否开启群发
	public static final String CONFIG_NAME_CLOUD_XB_TS="cloudConfig";//云发单是否开启群发
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
	//短信平台类型
	public static final String CONFIG_NAME_SMS_TYPE="sms_type";
	public static final String CONFIG_NAME_SMS_OPEN="sms_open";
	public static final String CONFIG_NAME_SMS_TYPE_BXT="bxt";
	public static final String CONFIG_NAME_SMS_TYPE_YP="yp";
	public static final String CONFIG_NAME_SMS_TYPE_CJDZ="cjdz";
	
	//管理员手机号
	public static final String CONFIG_NAME_ADMIN_PHONE="admin_phone";
	//全局检查
	public static final String CONFIG_NAME_CHECK_ALL="check_all";
	//最后降级月
	public static final String CONFIG_NAME_LAST_DOWN_MONTH="lastDownMonth";
	//最后兑换金币
	public static final String CONFIG_NAME_LAST_CHANGE_GOLD="lastChangeGold";
	//京东免单活动起止时间
	public static final String CONFIG_NAME_FREE_BEGIN_DATE="freeBeginDate";
	public static final String CONFIG_NAME_FREE_END_DATE="freeEndDate";
	public static final String CONFIG_NAME_ACTIVITY_OPEN="activity_open";
	public static final String CONFIG_NAME_FREE_OPEN ="free_open";
	public static final String CONFIG_NAME_FREE_CONDITIONS="free_conditions";
	//JD返利
	public static final String CONFIG_NAME_JD_RETURN_JF_="jd_return_jf";
	public static final String CONFIG_NAME_JD_RETURN_OPEN="jd_return_open";
	public static final String CONFIG_NAME_JD_ORDER_LIMIT="jd_order_limit";
	public static final String CONFIG_NAME_JD_INTEGRAL_LIMIT="jd_integral_limit";//京东返积分上限
	public static final String CONFIG_NAME_JD_TXRQ_INTERVAL="jd_txrq_interval";//京东提现日期间隔
	public static final String CONFIG_NAME_MOBILE_JDFREE_OPEN="mobile_jdfree_open";
	
	//系统版本号
	public static final String CONFIG_WEB_VERSION="web_version";
	//蘑菇街
	public static final String CONFIG_NAME_MGJ_FREE_MODEL="mgj_free_model";//全额还是固定 蘑菇街免单送积分模式
	public static final String CONFIG_NAME_MGJ_FREE_INTEGER="mgj_free_integral";//蘑菇姐免单固定模式送积分数
	public static final String CONFIG_NAME_MGJ_FREE_LEVEL_PRICE="mgj_free_level_price";//蘑菇街阶梯免单订单金额
	//双十一
	public static final String CONFIG_NAME_CJ_HB_SWITCH="cj_hb_switch";//超级红包开关
	public static final String CONFIG_NAME_FLJ_HB_SWITCH="flj_hb_switch";//福利金红包开关
	public static final String CONFIG_NAME_FL_HB_SWITCH="fl_hb_switch";//福利红包开关
	
	//quartz任务执行状态
	public static final String CONFIG_NAME_QZ_SWITCH = "qz_switch";
	
	//个人IP
	public static final String CONFIG_NAME_OWN_WEB_IP = "own_web_ip";
	
	//app审核模式
	public static final String CONFIG_NAME_APP_VERIFY ="app_verify";
	//是否是线下小程序
	
 
	
	//新模式开关
	public static final String CONFIG_NAME_NEW_SYSTEM_OPEN="new_system_open";
	public static final String CONFIG_NAME_NEW_SYSTEM_START_DATE="new_system_start_date";
	
	//新模式淘宝联盟积分比例
	public static final String CONFIG_NAME_NEW_TAOBAO_PLATFORM_RATE="new_taobao_platform_rate";//平台费10%
	public static final String CONFIG_NAME_NEW_TAOBAO_SERVICE_RATE="new_taobao_service_rate";//服务费3%
	public static final String CONFIG_NAME_NEW_TAOBAO_BRANCH_RATE="new_taobao_branch_rate";//三级分销分公司比例
	public static final String CONFIG_NAME_NEW_TAOBAO_MANAGER_RATE="new_taobao_manager_rate";//三级分销经理比例
	public static final String CONFIG_NAME_NEW_TAOBAO_PROXY1_RATE="new_taobao_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_NEW_TAOBAO_PROXY2_RATE="new_taobao_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_NEW_TAOBAO_PROXY3_RATE="new_taobao_proxy3_rate";//三级分销三级代理比例
	
	//新模式自营商城积分比例
	public static final String CONFIG_NAME_NEW_FHG_PLATFORM_RATE="new_fhg_platform_rate";//平台费10%
	public static final String CONFIG_NAME_NEW_FHG_SERVICE_RATE="new_fhg_service_rate";//服务费3%
	public static final String CONFIG_NAME_NEW_FHG_TEAM_RATE="new_fhg_team_rate";//团队利润10%
	public static final String CONFIG_NAME_NEW_FHG_BRANCH_RATE="new_fhg_branch_rate";//三级分销分公司比例
	public static final String CONFIG_NAME_NEW_FHG_MANAGER_RATE="new_fhg_manager_rate";//三级分销经理比例
	public static final String CONFIG_NAME_NEW_FHG_PROXY1_RATE="new_fhg_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_NEW_FHG_PROXY2_RATE="new_fhg_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_NEW_FHG_PROXY3_RATE="new_fhg_proxy3_rate";//三级分销三级代理比例
	
	//其他平台积分比例
	public static final String CONFIG_NAME_NEW_OTHER_PLATFORM_RATE="new_other_platform_rate";//平台费10%
	public static final String CONFIG_NAME_NEW_OTHER_SERVICE_RATE="new_other_service_rate";//服务费3%
	public static final String CONFIG_NAME_NEW_OTHER_TEAM_RATE="new_other_team_rate";//团队利润10%
	public static final String CONFIG_NAME_NEW_OTHER_BRANCH_RATE="new_other_branch_rate";//三级分销分公司比例
	public static final String CONFIG_NAME_NEW_OTHER_MANAGER_RATE="new_other_manager_rate";//三级分销经理比例
	public static final String CONFIG_NAME_NEW_OTHER_PROXY1_RATE="new_other_proxy1_rate";//三级分销一级代理比例
	public static final String CONFIG_NAME_NEW_OTHER_PROXY2_RATE="new_other_proxy2_rate";//三级分销二级代理比例
	public static final String CONFIG_NAME_NEW_OTHER_PROXY3_RATE="new_other_proxy3_rate";//三级分销三级代理比例
 
	
	
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
