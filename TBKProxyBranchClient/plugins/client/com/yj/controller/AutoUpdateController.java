package com.yj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 

@Controller
public class AutoUpdateController extends BaseController {
	
	@RequestMapping("judgeStartSuccess")
	public void judgeStartSuccess(HttpServletRequest request, HttpServletResponse response,String state){
		if(state==null){
			renderStr("success", response);
		}else{
			renderStr(state,response);
		}
	}

}
