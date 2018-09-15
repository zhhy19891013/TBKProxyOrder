package com.yj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yj.service.ISynchroService;

@Controller
public class SychroProductController extends BaseController {
	@Autowired
	private ISynchroService synchroService;

	/**
	 * 群发客户端请求产品
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/synchro")
	public void down(HttpServletResponse response, HttpServletRequest request) {
		synchroService.synchro();
	}

	/**
	 * 百度翻译需要查询商品详情
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/searchByTkl")
	public void searchByTkl(HttpServletResponse response,
			HttpServletRequest request, String tkl) {
		renderStr(synchroService.searchXBbyTkl(tkl), response);
	}

}
