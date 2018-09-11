package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.SystemUser;

/**
 * 用户信息数据库
 * 
 * @author Administrator
 *
 */
@Mapper
public interface SystemUserDao {

	/**
	 * 查询所有的用户
	 * 
	 * @return
	 */
	List<SystemUser> searchAllNotAdminUser();

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param name
	 * @return
	 */
	SystemUser searchSystemUserByUserName(String name);

	/**
	 * 根据拼多多pid查询用户
	 * 
	 * @param name
	 * @return
	 */
	SystemUser searchSystemUserByPddPid(String name);

	/**
	 * 根据用户id查询用户
	 * 
	 * @param id
	 * @return
	 */
	SystemUser searchSysemUserByDataBaseId(Long id);

	/**
	 * 根据蘑菇街pid查询用户信息
	 * 
	 * @param pid
	 * @return
	 */
	SystemUser searchSystemUserByMgjPid(String pid);

	/**
	 * 根据京东免单pid查询用户
	 * 
	 * @param pid
	 * @return
	 */
	SystemUser searchSystemUserByJdFreePid(String pid);

	/**
	 * 根据京东pid查询用户
	 * 
	 * @param pid
	 * @return
	 */
	SystemUser searchSystemUserByJdPid(String pid);

}
