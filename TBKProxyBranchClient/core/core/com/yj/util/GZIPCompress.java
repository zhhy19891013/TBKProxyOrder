package com.yj.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.yj.db.model.SystemUser;

public class GZIPCompress {
	/**
	 * 字符串gzip压缩为byte[]
	 *  
	 * @param str
	 * @return
	 * @throws IOException
	 */
    public static byte[] doCompress(String str) throws IOException {  
	    if (str == null || str.length() == 0) {  
	        return null;  
	    }  
	    ByteArrayOutputStream out = new ByteArrayOutputStream();  
	    GZIPOutputStream gzip = new GZIPOutputStream(out);  
	    gzip.write(str.getBytes("UTF-8"));  
	    gzip.close();  
	    return out.toByteArray();   
    }  
     
    /**
     * byte[] gzip解压 为byte[] 
     * @param str
     * @return
     * @throws IOException
     */
    public static byte[] doUncompress(byte[] str) throws IOException {  
	    if (str == null || str.length == 0) {  
	    	return null;  
	    }  
	    ByteArrayOutputStream out = new ByteArrayOutputStream();  
	    ByteArrayInputStream in = new ByteArrayInputStream(str);  
	    GZIPInputStream gunzip = new GZIPInputStream(in);  
	    byte[] buffer = new byte[256];  
	    int n;  
	    while ((n = gunzip.read(buffer)) >= 0) {  
	    	out.write(buffer, 0, n);  
	    }  
	    return out.toByteArray();  
    }  
	
    /**
     * 字符串gzip压缩为字符串
     * @param str
     * @return
     * @throws IOException
     */
    public static String compress(String str) throws IOException {   
    	if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString("ISO-8859-1");  
     }   
     
     /**
      * 字符串gzip解压缩为字符串   
      * @param str
      * @return
      * @throws IOException
      */
    public static String uncompress(String str) throws IOException {   
    	if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
				str.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
		return out.toString();   
     }  
    /**
     * 读取本地文件
     * @param filePath
     * @return
     */
    public static String readTxtFile(String filePath){
    	String s = new String();
    	String lineTxt = new String();
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    while((lineTxt = bufferedReader.readLine()) != null){
                        System.out.println(lineTxt);
                    }
                    s=lineTxt;
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     return s;
    }
    /**
     * 字符串写入本地txt文件
     * @param filePath
     * @param s
     */
    public static void WriteStringToFile(String filePath,String s) {
        try {
            File file = new File(filePath);
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(s);// 往文件里写入字符串
            ps.append(s);// 在已有的基础上添加字符串
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * byte[]经base64编码转字符串
     * @param bstr
     * @return
     */
    public static String encode(byte[] bstr){    
    	return new sun.misc.BASE64Encoder().encode(bstr);    
    }
    
    //------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {
    	
    }
}
