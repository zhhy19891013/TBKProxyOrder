package com.jeefw.dao.sys.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.SysUserDao;
import com.jeefw.model.sys.SysUser;

import core.dao.BaseDao;

/**
 * 用户的数据持久层的实现类
 * @框架唯一的升级和技术支持地址：http://shop111863449.taobao.com
 */
@Repository
public class SysUserDaoImpl extends BaseDao<SysUser> implements SysUserDao {

	public SysUserDaoImpl() {
		super(SysUser.class);
	}

	@Override
	public String getRoleValueBySysUserId(Long sysUserId) {
		Query query = this.getSession().createSQLQuery("select role_value from sysuser_role,role where sysuser_role.role_id = role.id and sysuser_id = :sysUserId");
		query.setParameter("sysUserId", sysUserId);
		String roleValue = (String) query.uniqueResult() == null ? "" : (String) query.uniqueResult();
		return roleValue;
	}

}
