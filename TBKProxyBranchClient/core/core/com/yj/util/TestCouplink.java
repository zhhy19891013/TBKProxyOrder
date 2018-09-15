package com.yj.util;

import java.net.URLEncoder;

public class TestCouplink {
	static String str="https://wqs.jd.com/promote/2016/getcoupon/index.html?rurl=wqs.jd.com&keyid=c1650431bfb74ef9ac07ddcba6e77d25&roleid=6600479";
	static String str1="http://coupon.m.jd.com/coupons/show.action?to=item.jd.com/11240132786.html&key=107f71c4da7142c392735bebc3188942&roleId=6675588";
		
	static	String str2="https://coupon.jd.com/ilink/couponSendFront/send_index.action?to=ncwarrior.jd.com&key=1d010e1f573745e98fd3e4edae5047de&roleId=6628602";
	public static String convertCouponLink(String couponlink,String cpslink){
		try{
			cpslink=URLEncoder.encode(cpslink,"utf-8");
			if(couponlink.contains("wqs.jd.com")){
				if(couponlink.contains("&rurl=")  ){
					String suburl1=getReapaStr("&rurl=",couponlink);
					couponlink=couponlink.replace("&rurl="+suburl1, "&rurl="+cpslink);
				}else if(couponlink.contains("?rurl=")){
					String suburl1=getReapaStr("?rurl=",couponlink);
					couponlink=couponlink.replace("?rurl="+suburl1, "?rurl="+cpslink);
				}else{
					couponlink=couponlink+"&rurl="+cpslink;
				}
			}else if(couponlink.contains("coupon.m.jd.com") || couponlink.contains("coupon.jd.com")){
				if(couponlink.contains("&to=") ){
					String suburl1=getReapaStr("&to=",couponlink);
					couponlink=couponlink.replace("&to="+suburl1, "&to="+cpslink);
				}else if(couponlink.contains("?to=")){
					String suburl1=getReapaStr("?to=",couponlink);
					couponlink=couponlink.replace("?to="+suburl1, "?to="+cpslink);
				}else{
					couponlink=couponlink+"&to="+cpslink;
				}
			}else{
				return  couponlink;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  couponlink;
	}
	public static String getReapaStr(String str,String couponlink){
		int index=couponlink.indexOf(str);
		String suburl1=couponlink.substring(index+str.length());
		if(suburl1.contains("&")){
			suburl1=suburl1.substring(0, suburl1.indexOf("&"));
		}
		return suburl1;
	}
	//&to=
   public static void main(String[] args) {
	  System.out.println(convertCouponLink(str1,"http://wwww.baidu.com?id=1"));
}
}
