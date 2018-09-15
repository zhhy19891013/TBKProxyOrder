package com.yj.db.model;

import com.yj.db.model.reuse.BasicModel;
/**
 * 系统配置(短信群发，群发模板设置)
 * @author Administrator
 */
public class SystemConfig2 extends BasicModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String CONFIG_NAME_SMS="sms";
	public static final String CONFIG_NAME_TEMPLATE="template";
	public static final String CONFIG_NAME_WEIBA="weiba";
	public static final String CONFIG_NAME_ZHIBO_WEB="zhiboWeb";
	
	public static final String DEFAULT_TEMPLATE_HTML="<div class=\"am-form-group item\"><label class=\"am-form-label mylable am-u-sm-2\"><a class=\"fl up\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-up\"></i></a> <a class=\"fl down\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-down\"></i></a> 图片</label><div class=\"am-u-sm-10\"><input disabled class=info value=\"#picturl#\" type=\"text\"></div></div><div class=\"am-form-group item\"><label class=\"am-form-label mylable am-u-sm-2\"><a class=\"fl up\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-up\"></i></a> <a class=\"fl down\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-down\"></i></a> 标题</label><div class=\"am-u-sm-10\"><input id=\"userName\" class=\"info\" value=\"#itemTitle#\" type=\"text\"></div></div><div class=\"am-form-group item\"><label class=\"am-form-label mylable am-u-sm-2\"><a class=\"fl up\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-up\"></i></a> <a class=\"fl down\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-down\"></i></a> 价格</label><div class=\"am-u-sm-10\"><textarea rows=\"10\" cols=\"10\" class=\"info\">原价#discountPrice#,券后价#afterQuan#</textarea></div></div><div class=\"am-form-group item\"><label class=\"am-form-label mylable am-u-sm-2\"><a class=\"fl up\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-up\"></i></a> <a class=\"fl down\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-down\"></i></a> 下单</label><div class=\"am-u-sm-10\"><textarea rows=\"10\" cols=\"10\" class=\"info\">下单地址： 复制这条信息 #tkl# 打开「手机淘宝」领券下单</textarea></div></div><div class=\"am-form-group item\"><label class=\"am-form-label mylable am-u-sm-2\"><a class=\"fl up\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-up\"></i></a> <a class=\"fl down\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-down\"></i></a> 推荐</label><div class=\"am-u-sm-10\"><input id=\"plateForm\" class=\"info\" value=\"#shortTitle#\" type=\"text\"></div></div><div class=\"am-form-group item\"><label class=\"am-form-label mylable am-u-sm-2\"><a class=\"fl up\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-up\"></i></a> <a class=\"fl down\" href=\"javascript:void(0)\"><i class=\"am-icon-chevron-down\"></i></a> 尾巴</label><div class=\"am-u-sm-10\"><input id=\"plateForm\" class=\"info\" value=\"便宜实惠\" type=\"text\"></div></div>";
	
	private String configName;//配置名称
	private String configValue;//配置内容 json字符串
	
	
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
	
}
