package com.yj.db.model;

import java.util.Date;

import com.yj.db.model.reuse.BasicModel;

public class ClientSleepConfig  extends BasicModel {
	
	private int sleepSwitch;
	private int sleepTime;
	private Date wakeUpTime;
	
	
	public Date getWakeUpTime() {
		return wakeUpTime;
	}
	public void setWakeUpTime(Date wakeUpTime) {
		this.wakeUpTime = wakeUpTime;
	}
	public int getSleepSwitch() {
		return sleepSwitch;
	}
	public void setSleepSwitch(int sleepSwitch) {
		this.sleepSwitch = sleepSwitch;
	}
	public int getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	
	
	
}
