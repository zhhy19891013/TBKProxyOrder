package com.yj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * 字符串工具
 * 
 * @author yiju-zhhy
 * 
 */
public class StringUtil {
	
	public static String getRandomString(){
	     Random random2 = new Random();
		int lenth =random2.nextInt(8)+1;
	    //定义一个字符串（A-Z，a-z，0-9）即62位；
	    String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
	    //由Random生成随机数
	        Random random=new Random();  
	        StringBuffer sb=new StringBuffer();
	        //长度为几就循环几次
	        for(int i=0; i<lenth; ++i){
	          //产生0-61的数字
	          int number=random.nextInt(62);
	          //将产生的数字通过length次承载到sb中
	          sb.append(str.charAt(number));
	        }
	        //将承载的字符转换成字符串
	        return sb.toString();
	  }
	
	/**
	 * 替换特殊字符
	 * @param str
	 * @return
	 */
	public static String replaceSpecialCharacter(String str){
		if(null==str||str.isEmpty()){
			return str;
		}
		if(str.contains("\t")){
			str =str.replaceAll("\t", "").trim();
		}
		if(str.contains("\\")){
			str=str.replaceAll("\\\\", "");
		}
		return str;
	}
	
	/**
	 * 分割字符串
	 * 
	 * @param str
	 * @param part
	 * @return
	 */
	public static List<String> split(String str, String part) {
		try {
			List<String> result = new ArrayList<String>();
			StringTokenizer token = new StringTokenizer(str, part);
			while (token.hasMoreElements()) {
				result.add(token.nextToken() + "");
			}
			return result;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 拼接字符串
	 * 
	 * @param strFirst
	 * @param strs
	 * @return
	 */
	public static String append(String strFirst, String... strs) {
		StringBuffer buffer = new StringBuffer(strFirst);
		if (strs.length != 0) {
			for (String str : strs) {
				buffer.append(str);
			}
		}
		return buffer.toString();
	}
	public static void main(String[] args) {
		System.out.println(getRandomString());
	}
	
}
