package com.yj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yj.service.IClientSystemUserService;

@Controller
@RequestMapping("/client/systemUser")
public class ClientSystemController extends BaseController {
	@Autowired
	private IClientSystemUserService clientSystemUserService;

	/**
	 * 客户端登录
	 */
	@RequestMapping(value = "login")
	public void login(HttpServletResponse response, HttpServletRequest request,
			String tbkName, String tbkPwd, String tt, String rnd) {
		renderMapJson(clientSystemUserService.login(tbkName, tbkPwd, tt, rnd),
				response);
	}

	/**
	 * 客户端登录测试压缩
	 */
	@RequestMapping(value = "testLogin")
	public void testLogin(HttpServletResponse response,
			HttpServletRequest request, String tbkName, String tbkPwd,
			String tt, String rnd) {
		renderStr(clientSystemUserService.testLogin(tbkName, tbkPwd, tt, rnd),
				response);
	}

}
