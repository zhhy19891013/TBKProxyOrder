package com.jeefw.controller.sys;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.Attachment;
import com.jeefw.model.sys.Authority;
import com.jeefw.model.sys.Role;
import com.jeefw.model.sys.SysUser;
import com.jeefw.service.sys.AttachmentService;
import com.jeefw.service.sys.AuthorityService;
import com.jeefw.service.sys.RoleService;
import com.jeefw.service.sys.SysUserService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;

/**
 * 用户的控制层
 * @框架唯一的升级和技术支持地址：http://shop111863449.taobao.com
 */
@Controller
@RequestMapping("/sys/sysuser")
public class SysUserController extends JavaEEFrameworkBaseController<SysUser> implements Constant {

	private static final Log log = LogFactory.getLog(SysUserController.class);
	@Resource
	private SysUserService sysUserService;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private AuthorityService authorityService;
	@Resource
	private RoleService roleService;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	// 登录
	@RequestMapping("/login")
	public void login(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser sysUser = sysUserService.getByProerties("email", sysUserModel.getEmail());
		if (sysUser == null || sysUser.getStatus() == true) { // 用户名有误或已被禁用
			result.put("result", -1);
			writeJSON(response, result);
			return;
		}
		if (!sysUser.getPassword().equals(new Sha256Hash(sysUserModel.getPassword()).toHex())) { // 密码错误
			result.put("result", -2);
			writeJSON(response, result);
			return;
		}
		sysUser.setLastLoginTime(new Date());
		sysUserService.merge(sysUser);
		Subject subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(sysUserModel.getEmail(), sysUserModel.getPassword(), sysUserModel.isRememberMe()));
		Session session = subject.getSession();
		session.setAttribute(SESSION_SYS_USER, sysUser);
		session.setAttribute("ROLE_KEY", sysUser.getRoles().iterator().next().getRoleKey());
		result.put("result", 1);
		writeJSON(response, result);
	}

	// 跳转到主页，获取菜单并授权
	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if (session.getAttribute(SESSION_SYS_USER) == null) {
			return new ModelAndView();
		} else {
			SysUser sysUser = (SysUser) session.getAttribute(SESSION_SYS_USER);
			String globalRoleKey = sysUser.getRoles().iterator().next().getRoleKey();
			try {
				List<Authority> allMenuList = authorityService.queryAllMenuList(globalRoleKey);
				return new ModelAndView("back/index", "authorityList", allMenuList);
			} catch (Exception e) {
				log.error(e.toString());
				return new ModelAndView();
			}
		}
	}

	// 注册
	@RequestMapping("/register")
	public void register(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser emailSysUser = sysUserService.getByProerties("email", sysUserModel.getEmail());
		if (emailSysUser != null) {
			result.put("result", -1);
			writeJSON(response, result);
			return;
		}
		SysUser sysUser = new SysUser();
		sysUser.setUserName(sysUserModel.getUserName());
		sysUser.setSex(sysUserModel.getSex());
		sysUser.setEmail(sysUserModel.getEmail());
		sysUser.setPhone(sysUserModel.getPhone());
		sysUser.setBirthday(sysUserModel.getBirthday());
		// sysUser.setPassword(MD5.crypt(sysUserModel.getPassword()));
		sysUser.setPassword(new Sha256Hash(sysUserModel.getPassword()).toHex());
		sysUser.setStatus(false);
		sysUser.setLastLoginTime(new Date());

		Set<Role> roles = new HashSet<Role>();
		Role role = roleService.getByProerties("roleKey", "ROLE_USER");
		roles.add(role);
		sysUser.setRoles(roles);

		sysUserService.persist(sysUser);
		// sysUserService.saveSysuserAndRole(sysUser.getId(), 3);

		Subject subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(sysUserModel.getEmail(), sysUserModel.getPassword()));
		Session session = subject.getSession();
		session.setAttribute(SESSION_SYS_USER, sysUser);
		session.setAttribute("ROLE_KEY", sysUser.getRoles().iterator().next().getRoleKey());

		result.put("result", 1);
		writeJSON(response, result);
	}

	// 获取个人资料信息
	@RequestMapping("/sysuserprofile")
	public ModelAndView sysuserprofile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SysUser sysuser = sysUserService.get(((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId());
		SysUser sysUserWithAvatar = sysUserService.getSysUserWithAvatar(sysuser);
		return new ModelAndView("back/sysuserprofile", "sysuser", sysUserWithAvatar);
	}

	// 登出
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SecurityUtils.getSubject().logout();
		response.sendRedirect("/jeefw/login.jsp");
	}

	// 发送邮件找回密码
	@RequestMapping("/retrievePassword")
	public void retrievePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		String email = request.getParameter("email");
		SysUser sysUser = sysUserService.getByProerties("email", email);
		if (sysUser == null || sysUser.getStatus() == true) { // 用户名有误或已被禁用
			result.put("result", -1);
			writeJSON(response, result);
			return;
		}
		SimpleEmail emailUtil = new SimpleEmail();
		emailUtil.setCharset("utf-8");
		emailUtil.setHostName("smtp.163.com");
		try {
			emailUtil.addTo(email, sysUser.getUserName());
			emailUtil.setAuthenticator(new DefaultAuthenticator("javaeeframework@163.com", "abcd123456"));// 参数是您的真实邮箱和密码
			emailUtil.setFrom("javaeeframework@163.com", "研发中心");
			emailUtil.setSubject("研发中心密码找回");
			emailUtil.setMsg("本邮件发送仅提供例子，需要您二次开发。" + sysUser.getUserName() + "，您的登录密码是：" + sysUser.getPassword());
			emailUtil.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("result", 1);
		writeJSON(response, result);
	}

	// 更改密码
	@RequestMapping("/resetPassword")
	public void resetPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String password = request.getParameter("password");
		Long id = ((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId();
		// sysUserService.updateByProperties("id", id, "password", MD5.crypt(password));
		sysUserService.updateByProperties("id", id, "password", new Sha256Hash(password).toHex());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		writeJSON(response, result);
	}

	// 查询用户的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getSysUser", method = { RequestMethod.POST, RequestMethod.GET })
	public void getSysUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		SysUser sysUser = new SysUser();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("email") && result.getString("op").equals("eq")) {
					sysUser.set$eq_email(result.getString("data"));
				}
				if (result.getString("field").equals("userName") && result.getString("op").equals("cn")) {
					sysUser.set$like_userName(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				sysUser.setFlag("OR");
			} else {
				sysUser.setFlag("AND");
			}
		}
		sysUser.setFirstResult((firstResult - 1) * maxResults);
		sysUser.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		sysUser.setSortedConditions(sortedCondition);
		QueryResult<SysUser> queryResult = sysUserService.doPaginationQuery(sysUser);
		JqGridPageView<SysUser> sysUserListView = new JqGridPageView<SysUser>();
		sysUserListView.setMaxResults(maxResults);
		List<SysUser> sysUserCnList = sysUserService.querySysUserCnList(queryResult.getResultList());
		sysUserListView.setRows(sysUserCnList);
		sysUserListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, sysUserListView);
	}

	// 保存用户的实体Bean
	@RequestMapping(value = "/saveSysUser", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(SysUser entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			SysUser sysUser = sysUserService.get(entity.getId());
			entity.setPassword(sysUser.getPassword());
			entity.setLastLoginTime(sysUser.getLastLoginTime());
			sysUserService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			// entity.setPassword(MD5.crypt("123456")); // 初始化密码为123456
			entity.setPassword(new Sha256Hash("123456").toHex()); // 初始化密码为123456
			sysUserService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作用户的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateSysUser", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateSysUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteSysUser(request, response, (Long[]) ConvertUtils.convert(ids, Long.class));
		} else if (oper.equals("excel")) {
			response.setContentType("application/msexcel;charset=UTF-8");
			try {
				response.addHeader("Content-Disposition", "attachment;filename=file.xls");
				OutputStream out = response.getOutputStream();
				out.write(URLDecoder.decode(request.getParameter("csvBuffer"), "UTF-8").getBytes());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Map<String, Object> result = new HashMap<String, Object>();
			String userName = request.getParameter("userName");
			String email = request.getParameter("email");
			SysUser sysUser = null;
			if (oper.equals("edit")) {
				sysUser = sysUserService.get(Long.valueOf(id));
			}
			SysUser emailSysUser = sysUserService.getByProerties("email", email);
			if (StringUtils.isBlank(userName) || StringUtils.isBlank(email)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写姓名和邮箱");
				writeJSON(response, result);
			} else if (null != emailSysUser && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此邮箱已存在，请重新输入");
				writeJSON(response, result);
			} else if (null != emailSysUser && !sysUser.getEmail().equalsIgnoreCase(email) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此邮箱已存在，请重新输入");
				writeJSON(response, result);
			} else {
				SysUser entity = new SysUser();
				entity.setUserName(userName);
				entity.setSex(Short.valueOf(request.getParameter("sexCn")));
				entity.setEmail(email);
				entity.setPhone(request.getParameter("phone"));
				if (StringUtils.isNotBlank(request.getParameter("birthday"))) {
					entity.setBirthday(dateFormat.parse(request.getParameter("birthday")));
				}
				entity.setDepartmentKey(request.getParameter("departmentValue"));
				entity.setStatusCn(request.getParameter("statusCn"));
				if (entity.getStatusCn().equals("是")) {
					entity.setStatus(true);
				} else {
					entity.setStatus(false);
				}

				Set<Role> roles = new HashSet<Role>();
				Role role = roleService.getByProerties("roleKey", request.getParameter("roleCn"));
				roles.add(role);
				entity.setRoles(roles);

				if (oper.equals("edit")) {
					entity.setId(Long.valueOf(id));
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else if (oper.equals("add")) {
					entity.setCmd("new");
					doSave(entity, request, response);
				}
			}
		}
	}

	// 保存个人资料
	@RequestMapping(value = "/saveSysUserProfile", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveSysUserProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long sysUserId = ((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId();
		SysUser sysUser = sysUserService.get(sysUserId);
		SysUser entity = new SysUser();
		entity.setId(sysUserId);
		entity.setUserName(request.getParameter("userName"));
		entity.setSex(Short.valueOf(request.getParameter("sex")));
		entity.setEmail(request.getParameter("email"));
		entity.setPhone(request.getParameter("phone"));
		if (null != request.getParameter("birthday")) {
			entity.setBirthday(dateFormat.parse(request.getParameter("birthday")));
		}
		entity.setStatus(sysUser.getStatus());
		entity.setPassword(sysUser.getPassword());
		entity.setLastLoginTime(sysUser.getLastLoginTime());
		entity.setDepartmentKey(sysUser.getDepartmentKey());
		entity.setRoles(sysUser.getRoles());
		sysUserService.merge(entity);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		writeJSON(response, result);
	}

	// 删除用户
	@RequestMapping("/deleteSysUser")
	public void deleteSysUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		if (Arrays.asList(ids).contains(Long.valueOf("1"))) {
			writeJSON(response, "{success:false,message:'删除项包含超级管理员，超级管理员不能删除！'}");
		} else {
			boolean flag = sysUserService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
	}

	// 即时更新个人资料的字段
	@RequestMapping(value = "/updateSysUserField", method = { RequestMethod.POST, RequestMethod.GET })
	public void updateSysUserField(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(request.getParameter("pk"));
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		if (name.equals("userName")) {
			sysUserService.updateByProperties("id", id, "userName", value);
		} else if (name.equals("sex")) {
			sysUserService.updateByProperties("id", id, "sex", Short.valueOf(value));
		} else if (name.equals("email")) {
			sysUserService.updateByProperties("id", id, "email", value);
		} else if (name.equals("phone")) {
			sysUserService.updateByProperties("id", id, "phone", value);
		} else if (name.equals("birthday")) {
			if (null != value) {
				sysUserService.updateByProperties("id", id, "birthday", dateFormat.parse(value));
			}
		}
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	// 上传个人资料的头像
	@RequestMapping(value = "/uploadAttachement", method = RequestMethod.POST)
	public void uploadAttachement(@RequestParam(value = "avatar", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		JSONObject json = new JSONObject();
		if (!file.isEmpty()) {
			if (file.getSize() > 2097152) {
				json.put("message", requestContext.getMessage("g_fileTooLarge"));
			} else {
				try {
					String originalFilename = file.getOriginalFilename();
					String fileName = sdf.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3) + originalFilename.substring(originalFilename.lastIndexOf("."));
					File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/img/" + DateFormatUtils.format(new Date(), "yyyyMM")));
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
					String destinationFilePath = "/static/upload/img/" + DateFormatUtils.format(new Date(), "yyyyMM") + "/" + fileName;
					Long sysUserId = ((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId();
					attachmentService.deleteByProperties(new String[] { "type", "typeId" }, new Object[] { (short) 1, sysUserId });
					Attachment attachment = new Attachment();
					attachment.setFileName(originalFilename);
					attachment.setFilePath(destinationFilePath);
					attachment.setType((short) 1);
					attachment.setTypeId(sysUserId);
					attachmentService.persist(attachment);
					json.put("status", "OK");
					json.put("url", request.getContextPath() + destinationFilePath);
					json.put("message", requestContext.getMessage("g_uploadSuccess"));
				} catch (Exception e) {
					e.printStackTrace();
					json.put("message", requestContext.getMessage("g_uploadFailure"));
				}
			}
		} else {
			json.put("message", requestContext.getMessage("g_uploadNotExists"));
		}
		writeJSON(response, json.toString());
	}

	/** 以下方法是根据路径跳转到页面 **/

	@RequestMapping("/sysuser")
	public String sysuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/systemmanage/sysuser";
	}

	@RequestMapping("/homepage")
	public String homepage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/homepage";
	}

	@RequestMapping("/dict")
	public String dict(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/systemmanage/dict";
	}

	@RequestMapping("/role")
	public String role(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/systemmanage/role";
	}

	@RequestMapping("/department")
	public String department(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/systemmanage/department";
	}

	@RequestMapping("/mail")
	public String mail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/systemmanage/mail";
	}

	@RequestMapping("/information")
	public String information(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/infomanage/information";
	}

	@RequestMapping("/authority")
	public String authority(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/systemmanage/authority";
	}

	@RequestMapping("/typography")
	public String typography(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/typography";
	}

	@RequestMapping("/uielements")
	public String uielements(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/uielements";
	}

	@RequestMapping("/buttonsicon")
	public String buttonsicon(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/buttonsicon";
	}

	@RequestMapping("/contentslider")
	public String contentslider(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/contentslider";
	}

	@RequestMapping("/nestablelist")
	public String nestablelist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/nestablelist";
	}

	@RequestMapping("/jquerydatatables")
	public String jquerydatatables(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/jquerydatatables";
	}

	@RequestMapping("/formelements")
	public String formelements(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/formelements";
	}

	@RequestMapping("/formelements2")
	public String formelements2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/formelements2";
	}

	@RequestMapping("/wizardvalidation")
	public String wizardvalidation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/wizardvalidation";
	}

	@RequestMapping("/uiwidgets")
	public String uiwidgets(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/uiwidgets";
	}

	@RequestMapping("/calendar")
	public String calendar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/calendar";
	}

	@RequestMapping("/gallery")
	public String gallery(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/gallery";
	}

	@RequestMapping("/pricingtables")
	public String pricingtables(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/pricingtables";
	}

	@RequestMapping("/invoice")
	public String invoice(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/invoice";
	}

	@RequestMapping("/timeline")
	public String timeline(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/timeline";
	}

	@RequestMapping("/faq")
	public String faq(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/faq";
	}

	@RequestMapping("/grid")
	public String grid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/bootstrapexample/grid";
	}

	@RequestMapping("/charts")
	public String charts(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/chartandreport/charts";
	}

	@RequestMapping("/callError404")
	public String callError404(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "redirect:/sys/sysuser/error404";
	}

	@RequestMapping("/error404")
	public String error404(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/error404";
	}

	@RequestMapping("/callError500")
	public String callError500(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "redirect:/sys/sysuser/error500";
	}

	@RequestMapping("/error500")
	public String error500(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/error500";
	}

	@RequestMapping("/callUnauthorized")
	public String callUnauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "redirect:/sys/sysuser/unauthorized";
	}

	@RequestMapping("/unauthorized")
	public String unauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/unauthorized";
	}

	@RequestMapping("/druid")
	public String druid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "back/druid";
	}

}
