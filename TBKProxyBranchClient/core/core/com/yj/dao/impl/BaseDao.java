package com.yj.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yj.dao.IBASEDao;

public class BaseDao implements IBASEDao {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required = true)
	protected SqlSessionTemplate sqlSessionTemplate;

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public Object searchSingleObject(String sqlName, Object param) {
		return sqlSessionTemplate.selectOne(sqlName, param);
	}

	@Override
	public boolean saveSingleObject(String sqlName, Object param) {
		try {
			sqlSessionTemplate.insert(sqlName, param);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public List searchObjects(String sqlName, Object param) {	
		try {
			return sqlSessionTemplate.selectList(sqlName,param);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 删除
	 */
	@Override
	public boolean deleteObjects(String sqlName, List l) {
		try {
			sqlSessionTemplate.delete(sqlName, l);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean updateSingleObject(String sqlName, Object param) {
		try {
			sqlSessionTemplate.update(sqlName, param);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return false;
	}


	@Override
	public boolean deleteSingleObject(String sqlName, Object o) {
		try {
			sqlSessionTemplate.delete(sqlName, o);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

}
