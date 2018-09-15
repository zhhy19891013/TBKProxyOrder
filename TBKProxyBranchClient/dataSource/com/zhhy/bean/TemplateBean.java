package com.zhhy.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yj.db.model.SystemConfig2;

public class TemplateBean {
	private String html;
	private String content;
	
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public static void main(String[] args) {
		TemplateBean bean = new TemplateBean();
		bean.setHtml(SystemConfig2.DEFAULT_TEMPLATE_HTML);
		bean.setContent("#picturl#<br>#itemTitle#<br>原价#discountPrice#,券后价#afterQuan#<br>#shortTitle#<br>便宜实惠<br>");
		System.out.println(JSON.toJSONString(bean));
	}
	
}
