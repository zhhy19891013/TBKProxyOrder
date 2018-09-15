package com.yj.service;

import java.util.List;
import java.util.Map;


import com.yj.db.model.CloudProduct;
import com.yj.db.model.reuse.BasicModel;

public interface IBaseService {

	public String getMyTklIp();

	/**
	 * 使用jsoup get方法抓取
	 * 
	 * @param url
	 * @param cookies
	 * @return
	 */
	public String getJSONStrFromWebByJsoup(String url, Map cookies);

	/**
	 * 使用jsoup post方法抓取
	 * 
	 * @param url
	 * @param cookies
	 * @return
	 */
	public String postJSONStrFromWebByJsoup(String url, Map cookies, Map data);

	/**
	 * 分页查询数据库
	 * 
	 * @param countSQL
	 * @param searchSQL
	 * @param page
	 * @param perPageRow
	 * @param sp
	 * @return
	 */
	public Map searchByCondition(String countSQL, String searchSQL, int page,
			int perPageRow, BasicModel sp);

	/**
	 * 获取我的pid
	 * 
	 * @param product
	 * @param pidInfo
	 * @return
	 */
	public String getMyPid(CloudProduct product, String pidInfo);

	public String getMyPidByUID(CloudProduct product, long uid);

	public List searchRows(String sql, BasicModel param);

}
