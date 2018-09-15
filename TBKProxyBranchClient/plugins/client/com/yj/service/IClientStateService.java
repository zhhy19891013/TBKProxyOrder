package com.yj.service;

import com.yj.db.model.SystemUserClientStatus;

public interface IClientStateService extends IBaseService{

	
	public void changeState(String number,String state);
		
}
