package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.Authority;

import core.service.Service;

/**
 * 菜单的业务逻辑层的接口
 * @框架唯一的升级和技术支持地址：http://shop111863449.taobao.com
 */
public interface AuthorityService extends Service<Authority> {

	// 获取多级菜单
	List<Authority> queryAllMenuList(String globalRoleKey);

	// 获取按钮权限数据
	String getAuthorityButtonList(String menuCode);

	// 保存角色权限
	void saveAuthorityButtonList(String checkboxList, String menuCode);

}
