package com.yj.dao;

import com.yj.dao.impl.BaseDao;
/**
 * 小编推荐
 * @author Administrator
 */
public class CloudProductXbTjDao extends BaseDao{
	//删除过期的产品
	public static final String SQL_NAME_DELETE_ALL_EXPIRE_CLOUD_PRODUCT_XB_TJ="deleteAllExpireCloudProductXbTj";
	//删除券过期的产品
	public static final String SQL_NAME_DELETE_ALL_QUAN_EXPIRE_CLOUD_PRODUCT_XB_TJ="deleteAllQuanExpireCloudProductXbTj";
	//新增小编推荐
	public static final String SQL_NAME_ADD_CLOUD_PRODUCT_XB_TJ="addColudProductXbTjBatch";
	//删除小编推荐
	public static final String SQL_NAME_DELETE_CLOUD_PRODUCT_XB_TJ_BY_ITEM_IDS="deleteColudProductXbTjByItemIds";
	//查询小编推荐
	public static final String SQL_NAME_SEARCH_CLOUD_PRODUCT_XB_TJ_COUNT="searchCloudXbTjCount";
	public static final String SQL_NAME_SEARCH_CLOUD_PRODUCT_XB_TJ="searchCloudXbTj";
	
	public static final String SQL_NAME_SEARCH_CLOUD_PRODUCT_XB_TJ_ALL_COUNT="searchCloudXbTjAllCount";
	public static final String SQL_NAME_SEARCH_CLOUD_PRODUCT_XB_TJ_ALL="searchCloudXbTjAll";
	public static final String SQL_NAME_SEARCH_CLOUD_PRODUCT_XB_TJ_LIST="searchNewCloudProductXbTjList";
	
	//查询最新的小编推荐
	public static final String SQL_NAME_SEARCH_NEW_CLOUD_PRODUCT_XB_TJ_COUNT="searchNewCloudProductXbTjCount";
	public static final String SQL_NAME_SEARCH_NEW_CLOUD_PRODUCT_XB_TJ="searchNewCloudProductXbTj";
	
	public static final String SQL_NAME_SEARCH_CLOUD_PRODUCT_XB_TJ_BY_ITEM_ID="searchCloudXbTjByItemID";
	
	public static final String SQL_NAME_UPDATE_CLOUD_PRODUCT_XB_TJ_SEND_FLAG="updateCloudXbTjSend";
	//查询需要删除的小编推荐
	public static final String SEARCH_NEED_DELETE_CLOUD_PRODUCT_XB_TJ="searchNeedDeleteCloudProductXbTj";
	
	public static final String SEARCH_ALL_XB_TJ="searchXBTJAll";
	
}
