package com.yj.dao;

import com.yj.dao.impl.BaseDao;

public class SystemUserDao extends BaseDao {

	public static final String SQL_NAME_SEARCH_ALL_SYSTEM_USER_COUNT = "searchAllSystemUserCount";
	public static final String SQL_NAME_SEARCH_ALL_SYSTEM_USER = "searchAllSystemUser";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_LOGIN_BY_PHONE ="searchSystemUserLoginByPhone";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_BY_USER_NAME = "searchSystemUserByUserName";
	public static final String SQL_NAME_ADD_USER = "addSystemUser";
	public static final String SQL_NAME_SEARCH_SYSTEMUSER_COUNT = "searchSystemUserCount";
	public static final String SQL_NAME_SEARCH_SYSTEMUSER = "searchSystemUser";
	public static final String SQL_NAME_DELETE_SYSTEMUSER="deleteSystemUser";
	public static final String SQL_NAME_UPDATE_SYSTEMUSER="updateSystemUser";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_BY_DATABASE_ID="searchSysemUserByDataBaseId";
	public static final String SQL_NAME_UPDATE_SYSTEMUSER_QRCODE="updateSystemUserQrCode";
	public static final String SQL_NAME_UPDATE_SYSEMUSER_INFO="updateSystemUserInfo";
	public static final String SQL_NAME_UPDATE_SYSEMUSER_INFO2="updateSystemUserInfo2";
	public static final String SQL_NAME_UPDATE_SYSTEMUSER_PROXY_RATE="updateSystemUserProxyRate";
	public static final String SQL_NAME_UPDATE_SYSTEMUSER_INTEGRAL_RATE="updateSystemUserIntegralRate";
	public static final String SQL_NAME_SEARCH_ALL_ADMIN_USER="searchAllAdminUser";
	public static final String SQL_NAME_UPDATE_SYSTEM_PID="updateSystemUserPid";
	public static final String SQL_NAME_UPDATE_SYSTEM_USER_TYPE="updateSystemUserType";
	public static final String SQL_NAME_FIND_ALL_USER ="findAllUser";
	//代理查询下级代理
	public static final String SQL_NAME_SEARCH_MY_SYSTEMUSER="searchMyProxy";
	public static final String SQL_NAME_SEARCH_MY_SYSTEMUSER_COUNT="searchMyProxyCount";
	//验证支付宝
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_BY_ZFB="searchSystemUserByZfb";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_BY_ZFB_AND_USER_NAME="searchSystemUserByZfbAndUserName";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_BY_PHONE_AND_USER_NAME="searchSystemUserByPhoneAndUserName";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_BY_PHONE="searchSystemUserByPhone";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_BY_EMAIL_AND_USER_NAME="searchSystemUserByemailAndUserName";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_BY_QQ_AND_USER_NAME="searchSystemUserByQQAndUserName";
	//修改用户头像
	public static final String SQL_NAME_UPLOAD_SYSTEM_USER_PICT  ="updateSystemPict";
	
	//修改用户信息
	public static final String SQL_NAME_UPDATE_SYSTEM_GROUP="updateSystemUserGroup";
	//查询所有的一级代理
	public static final String SQL_NAME_SEARCH_ALL_PROXY_USER="searchAllProxyUser";
	public static final String SQL_NAME_SEARCH_ALL_NOT_ADMIN_USER="searchAllNotAdminUser";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_PID="searchSystemUserByPid";
	public static final String SQL_NAME_SEARCH_SYSTEM_USER="searchSystemConfigByUserName";
	public static final String SQL_NAME_SEARCH_QUDAO_PROXY_USER_COUNT="searchMyQudaoProxyCount";
	public static final String SQL_NAME_SEARCH_QUDAO_PROXY_USER="searchMyQuDaoProxy";
	
	public static final String SQL_NAME_SEARCH_ALL_USER="searchAllQrcodeUser";
	//根据PID查询下用户
	public static final String SQL_NAME_SEARCH_SYSTEM_USER_BY_PID="searchSystemUserByProductId";
	public static final String SQL_NAME_UPDATE_SYSTEM_USER_PWD_BY_PHONE = "updatePwdByPhone";
	//查询所有的用户
	public static final String SQL_NAME_SEARCH_ALL_USER_FOR_PID_COUNT="searchAllUserCount";
	public static final String SQL_NAME_SEARCH_ALL_USER_FOR_PID="searchAllUser";
	public static final String SQL_NAME_UPDATE_SYSTEM_PID_INFO="updateSystemUserPidInfo";
	public static final String SQL_NAME_SEARCH_USER_GROUP = "searchUserGroup";
	public static final String SQL_NAME_SEARCH_FATHER_NAME_BY_USER_NAME = "searchFatherNameByUserName";
	public static final String SQL_NAME_SEARCH_USER_BY_FATHER_NAME = "searchByFatherName";
	public static final String SQL_NAME_UPDATE_SYSEMUSER_QUDAO = "updateQudao";
	public static final String SQL_NAME_UPDATE_SYSEMUSER_FATHER = "updateFather";
	public static final String SQL_NAME_UPDATE_SYSEMUSER_QUDAO_AND_FATHER = "updateQudaoAndFather";
	public static final String SQL_NAME_SEARCH_BY_FATHER_COUNT = "searchByFatherNameCount";
	public static final String SQL_NAME_SEARCH_EMP_COUNT = "searchEmpCount";
	public static final String SQL_NAME_SEARCH_USERNAME_COUNT = "searchUsernameCount";
	public static final String SQL_NAME_SEARCH_USER_BY_CONDITION = "searchUserByCondition";
	public static final String SQL_NAME_UPGRADE_USER = "upgradeUser";
	public static final String SQL_NAME_UPDATE_FATHER = "updateFatherName";
	
	
	public static final String SQL_NAME_SEARCH_ALL_USER_FOR_JD_PID_COUNT="searchAllUserJdCount";
	public static final String SQL_NAME_SEARCH_ALL_USER_FOR_JD_PID="searchAllUserJd";
	
	//京东根据jdPid查询用户名
	public static final String SQL_NAEM_SEARCH_USET_BY_JDPID="searchUserByJdPid";
	public static final String SQL_NAME_UPDATE_USER_JD_PID="updateSystemUserJdPid";
	public static final String SQL_NAME_UPDATE_BAN_TIME = "updateBanTime";
	public static final String SQL_NAME_UPDATE_QUDAO_NAME_WHEN_QUDAO_NAME_CHANGED = "updateQudaoNameWhenQudaoNameChanged";
	
}
