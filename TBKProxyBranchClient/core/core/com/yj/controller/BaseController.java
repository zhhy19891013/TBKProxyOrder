package com.yj.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;

@Controller
public class BaseController implements IMessage {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected Integer LIMIT_CONVERT = 30;
	protected long LIMIT_PER_TIME = 60000;

	protected static HashMap rndMap = new HashMap();// key tbName
	protected static HashMap rndMap2 = new HashMap();// key tbkName


	/**
	 * 向前台发送list数据
	 * 
	 * @param list
	 * @param response
	 */
	protected void renderJson(Object o, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(o);
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		pw.write(jsonStr);
		pw.flush();
		pw.close();
	}

	/**
	 * 向前台发送list数据
	 * 
	 * @param list
	 * @param response
	 */
	protected void renderJson(List<?> list, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(list);
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		pw.write(jsonStr);
		pw.flush();
		pw.close();
	}

	/**
	 * 向前台发送list数据
	 * 
	 * @param list
	 * @param response
	 */
	protected void renderMapJson(Map<String, ?> map,
			HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(map);
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		pw.write(jsonStr);
		pw.flush();
		pw.close();
	}

	/**
	 * 向前台发送字符串
	 * 
	 * @param param
	 * @param response
	 */
	protected void renderStr(String param, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		pw.write(param);
		pw.flush();
		pw.close();
	}

	/**
	 * 向前台发送字符串
	 * 
	 * @param param
	 * @param response
	 */
	protected void renderHtmlStr(String param, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		pw.write(param);
		pw.flush();
		pw.close();
	}

	/**
	 * @param param
	 * @param response
	 */
	protected void renderState(boolean param, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		pw.write(Boolean.toString(param));
		pw.flush();
		pw.close();
	}

	/**
	 * 获取项目根目录
	 * 
	 * @param request
	 * @return
	 */
	protected String getRootPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}


	/**
	 * 获取客户端随机数
	 * 
	 * @param request
	 * @return
	 */
	protected String getUserRnd(HttpServletRequest request, String tbkName) {
		if (null != rndMap.get(tbkName)) {
			return rndMap.get(tbkName).toString();
		}
		if (null != rndMap2.get(tbkName)) {
			return rndMap2.get(tbkName).toString();
		}
		return null;
	}
}
