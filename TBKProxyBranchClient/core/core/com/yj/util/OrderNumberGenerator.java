/*
 * Created on Jan 1, 2005
 *
 */
package com.yj.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * used to getnerator order sequence
 */
public class OrderNumberGenerator {

	static Logger m_logger = Logger.getLogger(OrderNumberGenerator.class);

	public static final String PRODUCT_ID_KEY = "SP";
	public static final String PRODUCT_PICTURE_ID_KEY = "TP";
	public static final String MATERIAL_PICTURE_ID_KEY = "YP";
	public static final String MATERIAL_ID_KEY="YL";
	public static final String COLOR_ID_KEY="YS";
	
	static public String getUniqueID() {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssS");
		String id =  format.format(new Date());
		return id;
	}

	static public String getUniqueID(String title) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssS");
		String id = title + "" + format.format(new Date());
		return id;
	}

	/**
	 * 可以避免运行过快导致的两个ID就是相等的问题
	 * 
	 * @param existOnes
	 * @return
	 */
	static public String getUniqueID(List existOnes) {
		String id = getUniqueID();
		while (existOnes.contains(id)) {
			id = getUniqueID();
		}
		existOnes.add(id);
		return id;
	}

	public static String getUniqueID(List existOnes, String title) {
		String id = getUniqueID(title);
		while (existOnes.contains(id)) {
			id = getUniqueID(title);
		}
		existOnes.add(id);
		return id;
	}

}