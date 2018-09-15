package com.yj.util;

public class LinkObject {
	private String tinyurl;// 腾讯短链接
	private String short_url;// 百度短链接
	private String key;//淘宝短链接
	
	private String url_short;//新浪短链接
	
	
	
	
	
	public String getUrl_short() {
		return url_short;
	}

	public void setUrl_short(String url_short) {
		this.url_short = url_short;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTinyurl() {
		return tinyurl;
	}

	public void setTinyurl(String tinyurl) {
		this.tinyurl = tinyurl;
	}

	public String getShort_url() {
		return short_url;
	}

	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}

}
