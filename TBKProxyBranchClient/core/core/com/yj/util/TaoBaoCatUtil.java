package com.yj.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



/**
 * 淘宝类别转换工具
 * @author Administrator
 */
public class TaoBaoCatUtil {
	
	private TaoBaoCatUtil(){
		
	}
	
	public static 	Properties prop =new Properties();
	public static 	Properties prop2 =new Properties();
	static {
		try {
			prop.load(TaoBaoCatUtil.class.getResourceAsStream("cat.properties"));
			List kl = new ArrayList();
			for(Object o:prop.keySet()){
				Map m = new HashMap();
				m.put("cat", prop.getProperty(o.toString()));
				kl.add(m);
			}
		} catch (Exception e) {
		}
		
		try {
			prop2.load(TaoBaoCatUtil.class.getResourceAsStream("cat2.properties"));
			List kl = new ArrayList();
			for(Object o:prop2.keySet()){
				Map m = new HashMap();
				m.put("cat", prop2.getProperty(o.toString()));
				kl.add(m);
			}
		} catch (Exception e) {
		}
	}
	
	
	public static List getAllCatName(){
		return new ArrayList(prop.keySet());
	}
	
	public static String getCatByID(String id){
		return null!=prop.getProperty(id)?prop.getProperty(id).toString():"其他";
	}
	
	public static String getCatByID2(String id){
		return null!=prop2.getProperty(id)?prop2.getProperty(id).toString():"其他";
	}
	
	/**
	 * 鹊桥特殊字符转化
	 * @param str
	 * @return
	 */
	public static String convertActivityItemTitle(String str){
		try {
			String[] strs = str.split("itemTitle");
			for (String o : strs) {
				if (o.contains("sellerId")) {
					String info1 = o.substring(o.indexOf(":") + 1,
							o.indexOf("sellerId"));
					String info2 = info1.substring(info1.indexOf("\""),
							info1.lastIndexOf(","));
					String info3 = info2.substring(info2.indexOf("\"") + 1,
							info2.lastIndexOf("\""));
					if(!info3.contains("\"")){
						continue;
					}
					String info4 = info3.replaceAll("\"", " ")
							.replaceAll("“", " ").replaceAll("”", "");
					str = str.replaceAll(info3, info4);
				}
			}
		} catch (Exception e) {
		}
		return str;
	}
	
	/**
	 * 单品字符转化
	 * @param str
	 * @return
	 */
	public static String convertSingleTitle(String str){
		try {
			String[] strs = str.split("title");
			for (String o : strs) {
				if (o.contains("groupRate")) {
					String info1 = o.substring(o.indexOf(":") + 1,
							o.indexOf("groupRate"));
					String info2 = info1.substring(info1.indexOf("\""),
							info1.lastIndexOf(","));
					String info3 = info2.substring(info2.indexOf("\"") + 1,
							info2.lastIndexOf("\""));
					if(!info3.contains("\"")){
						continue;
					}
					String info4 = info3.replaceAll("\"", " ")
							.replaceAll("“", " ").replaceAll("”", "");
					str = str.replaceAll(info3, info4);
				}
			}
		} catch (Exception e) {
		}
		return str;
	}
	public static void main(String[] args) {
		String info = "\"title\":\"趣歌风尚★2016春装新款系带领口长袖印花修身真丝连衣裙121A059\",\"groupRate\":0.0";
		System.out.println(		convertSingleTitle(info));
	}
}
