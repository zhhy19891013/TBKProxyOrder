package com.yj.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yj.dao.ClientStateDao;
import com.yj.db.model.SystemUserClientStatus;
import com.yj.service.BaseService;
import com.yj.service.IClientStateService;

public class ClientStateService extends BaseService implements IClientStateService{


	@Override
	public void changeState(String number,String state) {
		// TODO Auto-generated method stub
		if(number.trim().equals("")){
			number = null;
		}
		if(number!=null){
			SystemUserClientStatus scs = new SystemUserClientStatus();
			scs.setGroupBy(number);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy年 MM月 dd日  hh时 mm分 ss秒");
			StringBuffer sb = new StringBuffer();
			if(state.equals("1")){
				sb.append("没有淘口令 ！   时间：").append(sdf.format(new Date())).append(" <br>");
			}
			if(state.equals("2")){
				sb.append("未授权 ！   时间：").append(sdf.format(new Date())).append(" <br>");
			}
			if(state.equals("4")){
				sb.append("sessionKey过期 ！   时间：").append(sdf.format(new Date())).append(" <br>");
			}
			if(state.equals("5")){
				sb.append("推广位不一致！   时间：").append(sdf.format(new Date())).append(" <br>");
			}
			if(state.equals("6")){
				sb.append("pid错误 ！   时间：").append(sdf.format(new Date())).append(" <br>");
			}
			if(state.equals("7")){
				sb.append("未知错误！   时间：").append(sdf.format(new Date())).append(" <br>");
			}	
			if(state.equals("8")){
				sb.append("uid对应的用户没找到  ！   时间：").append(sdf.format(new Date())).append(" <br>");
			}
			if(state.equals("9")){
				sb.append("获取网站回显异常 ！   时间：").append(sdf.format(new Date())).append(" <br>");
			}
			if(state.equals("10")){
				sb.append("剪贴板被占用 ！   时间：").append(sdf.format(new Date())).append(" <br>");
			}
			if(state.equals("11")){
				sb.append("网站后台一键停止  ！   时间：").append(sdf.format(new Date())).append(" <br>");
			}
			String status = "异常";
			if(state.equals("success")){
				sb.append("开始群发 ！   时间：").append(sdf.format(new Date())).append(" <br>");
				status ="正常";
				baseDao.deleteSingleObject(ClientStateDao.SQL_NAME_DELETE_CLIENT_STATE, scs);
			}
			scs.setClientState(sb.toString());
			scs.setStatus(status);
			SystemUserClientStatus s = (SystemUserClientStatus) baseDao.searchSingleObject(ClientStateDao.SQL_NAME_SEARCH_FORS_CLIENT_STATE, scs);
			if(s==null){
				baseDao.saveSingleObject(ClientStateDao.SQL_NAME_CHANGE_CLIENT_STATE, scs);
			}else{
				if(s.getClientState().length()>200){
					scs.setClientState(sb.toString());
				}else{
					scs.setClientState(s.getClientState()+sb.toString());
				}
				baseDao.updateSingleObject(ClientStateDao.SQL_NAME_UPDATE_CLIENT_STATE, scs);
			}
			
		}
	}

}
