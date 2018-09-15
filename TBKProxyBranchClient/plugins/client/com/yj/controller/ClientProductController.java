package com.yj.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yj.db.bean.ClientXbBean;
import com.yj.db.model.CloudProductXbTj;
import com.yj.service.IClientCloudProductXbTjService;

@Controller
@RequestMapping("/client/cloud")
public class ClientProductController extends BaseController {
	@Autowired
	private IClientCloudProductXbTjService clientCloudProductXbTjService;

	/**
	 * 群发客户端请求产品
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "down", method = RequestMethod.POST)
	public void down(HttpServletResponse response, HttpServletRequest request,
			long updateTime, String pid, Long uid) {
		CloudProductXbTj xb = new CloudProductXbTj();
		xb.setUpdateTime(new Timestamp(updateTime));
		renderJson(clientCloudProductXbTjService.searchNewCloudProductXbTj(xb,
				pid, uid), response);
	}

	/**
	 * 群发客户端请求产品 发单服务器暂时没加
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "downText")
	public void dowText(HttpServletResponse response,
			HttpServletRequest request, long updateTime) {
		ClientXbBean result = new ClientXbBean();
		long max = 0;
		List rows = new ArrayList();
		result.setStatus("success");
		result.setMax(max);
		result.setTotal(rows.size());
		result.setRows(rows);
		renderJson(result, response);
	}

	/**
	 * 云群发客户端请求产品
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "cloudDown", method = RequestMethod.POST)
	public void cloudDown(HttpServletResponse response,
			HttpServletRequest request, long updateTime, String pid, Long uid) {
		CloudProductXbTj xb = new CloudProductXbTj();
		xb.setUpdateTime(new Timestamp(updateTime));
		renderJson(clientCloudProductXbTjService.searchNewCloudProductXbTj2(xb,
				pid, uid, null), response);
	}

	/**
	 * 群发客户端请求产品
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "downEmoji", method = RequestMethod.POST)
	public void downEmoji(HttpServletResponse response,
			HttpServletRequest request, long updateTime, String pid, Long uid) {
		CloudProductXbTj xb = new CloudProductXbTj();
		xb.setUpdateTime(new Timestamp(updateTime));
		renderJson(clientCloudProductXbTjService.searchNewCloudProductEmoji(xb,
				pid, uid), response);
	}

}
