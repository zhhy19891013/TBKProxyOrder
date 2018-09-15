package com.jeefw.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jeefw.model.sys.SysUser;

import core.controller.ExtJSBaseController;
import core.support.ExtJSBaseParameter;

/**
 * @框架唯一的升级和技术支持地址：http://shop111863449.taobao.com
 */
public abstract class JavaEEFrameworkBaseController<E extends ExtJSBaseParameter> extends ExtJSBaseController<E> implements Constant {

	public SysUser getCurrentSysUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return (SysUser) request.getSession().getAttribute(SESSION_SYS_USER);
	}

}
