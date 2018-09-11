package com.yj.dto.tb;

/**
 * 用户淘宝pid对应的结构
 * 
 * @author yiju-zhhy
 * 
 */
public class SystemTbPidBean {

	private String tbName;// 淘宝名称
	private String pid;// 对应的pid
	private String type;// 类型 采集 小编

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
