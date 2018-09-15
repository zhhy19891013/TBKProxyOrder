package com.yj.service;

import com.yj.db.bean.ClientXbBean;
import com.yj.db.model.CloudProductXbTj;

/**
 * 小编推荐接口
 * 
 * @author Administrator
 */
public interface IClientCloudProductXbTjService extends IBaseService {

	public ClientXbBean searchNewCloudProductEmoji(CloudProductXbTj tj,
			String pid, Long uid);

	/**
	 * 客户端查询
	 */
	public ClientXbBean searchNewCloudProductXbTj(CloudProductXbTj tj,
			String pid, Long uid);

	public ClientXbBean searchNewCloudProductXbTj2(CloudProductXbTj tj,
			String pid, Long uid, String url2);

}
