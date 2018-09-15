package com.yj.dao;

import com.yj.dao.impl.BaseDao;

public class ClientStateDao extends BaseDao{
	
	//查询状态
	public static final String SQL_NAME_SEARCH_CLIENT_STATE_COUNT ="searchClientStateCount";
	public static final String SQL_NAME_SEARCH_CLIENT_STATE ="searchClientState";
	
	public static final String SQL_NAME_SEARCH_FORS_CLIENT_STATE ="searchForClientState";
	//修改状态
	public static final String SQL_NAME_CHANGE_CLIENT_STATE ="changeClientState";
	public static final String SQL_NAME_UPDATE_CLIENT_STATE ="updateClientState";
	public static final String SQL_NAME_DELETE_CLIENT_STATE ="deleteClientState";
	
}
