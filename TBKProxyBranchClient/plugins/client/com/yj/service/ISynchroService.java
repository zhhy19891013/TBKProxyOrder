package com.yj.service;

import com.yj.db.model.CloudProductXbTj;

public interface ISynchroService extends IBaseService {
	
	public void synchro();
	
	public String searchXBbyTkl(String tkl);
}
