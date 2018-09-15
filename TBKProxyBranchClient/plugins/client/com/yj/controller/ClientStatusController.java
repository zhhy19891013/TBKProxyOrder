package com.yj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yj.service.IClientStateService;

@Controller
@RequestMapping(value = "client/state")
public class ClientStatusController extends BaseController {

	@Autowired
	private IClientStateService clientStateService;

	@RequestMapping(value = "getClientState")
	public String getClientState(HttpServletRequest request,
			HttpServletResponse response, String number, String state) {
		clientStateService.changeState(number, state);
		String str = null;
		if (state.equals("1")) {
			str = "no_tkl";
		}
		if (state.equals("4")) {
			str = "invalid_session";
		}
		if (str != null) {
			return "redirect:/judgeStartSuccess.do?state=" + str;
		} else {
			return null;
		}
	}

	/**
	 * 获取客户端vps内存状况
	 * 
	 * @param request
	 * @param response
	 * @param number
	 * @param vpsMemory
	 */
	@RequestMapping(value = "getClientVpsMemory")
	public void getClientVps(HttpServletRequest request,
			HttpServletResponse response, String number, String vpsMemory) {
		renderStr(SUCCESS, response);
	}

}
