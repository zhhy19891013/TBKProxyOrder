package com.yj.dao;

import com.yj.dao.impl.BaseDao;

public class JDCloudProductSendDao extends BaseDao{

	public final static String ADD_JD_CLOUD_PRODUCT_SEND_SQL_NAME="uploadJDProductSendBatch";
	public final static String DELETE_JD_CLOUD_PRODUCT_SEND_SQL_NAME="deleteJDProductSendBySkuids";
	
	public final static String SEARCH_NEED_JD_CLOUD_PRODUCT_SEND_SQL_NAME="searchJdCloudProductNeedSend";
	public final static String SEARCH_JD_CLOUD_PRODUCT_SEND_SQL_NAME_COUNT="searchJdCloudProductSendListCount";
	public final static String SEARCH_JD_CLOUD_PRODUCT_SEND_SQL_NAME="searchJdCloudProductSendList";
	
	public final static String DELETE_EXPIRE_JD_CLOUD_PRODUCT_SEND_SQL_NAME="deleteAllExpireJdCloudSend";
	
}
