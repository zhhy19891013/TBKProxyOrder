package com.yj.service;

import java.util.Map;

public interface IClientSystemUserService extends IBaseService {
	public Map login(String tbkName, String tbkPwd, String tt, String rnd);

	public String testLogin(String tbkName, String tbkPwd, String tt, String rnd);
}
