package com.yj.dao;

import com.yj.dao.impl.BaseDao;
/**
 * 系统群发配置
 * @author Administrator
 */
public class SystemUserClientConfigDao extends BaseDao{

	//查微信群是否存在
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_CLIENT_HAS_WNAME ="searchSystemUserClientHasWName";
	//查已设置群发设置的用户
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_CLIENT_CONFIG_FOR_PROXY_NAME="searchSystemUserClientConfigForProxyName";
	
	//查询所有的群发配置
	public static final String SQL_NAME_SEARCH_ALL_SYSTEM_USER_CLIENT_CONFIG="searchAllSystemUserClientConfig";
	
	//新增配置
	public  static final  String SQL_NAME_ADD_SYSTEM_USER_CLIENT_CONFIG="addSystemUserClientConfig";
	//删除配置
	public static final String SQL_NAME_DELETE_SYSTEM_USER_CLIENT_CONFIG="deleteSystemUserClinetConfig";
	
	
	//批量删除配置
	public static final String SQL_NAME_DELETE_SYSTEM_USER_CLIENT_CONFIGS="deleteSystemUserClinetConfigs";
	
	
	//查询
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_CLIENT_CONFIG_BY_USER_NAME="searchSystemUserClientConfigByUserName";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_CLIENT_CONFIG_BY_USER_NAME_AND_PROXY_NAME="searchSystemUserClientConfigByUserNameAndProxyName";

	//发单时间
	public static final String SQL_NAME_DELETE_SYSTEM_USER_CLIENT_CONFIG_T="deleteSystemUserClinetTConfig";
	public static final String SQL_NAME_ADD_SYSTEM_USER_CLIENT_CONFIG_T="addSystemUserClientConfigT";
	public static final String SQL_NANE_SEARCH_SYSTEM_USER_CLIENT_CONFIG_T="searchSystemUserClientConfigTByUserName";
	
	//更新
	public static final String SQL_NAME_UPDATE_SYSTEM_USER_CLIENT_CONFIG="updateSystemUserClientConfig";
	public static final String SQL_NAME_UPDATE_USER_CLIENT_CONFIG_USER_NAME = "updateUserClientConfigUserName";
	
}
