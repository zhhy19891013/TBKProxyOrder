package com.yj.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 抓取的所有链接
 * 
 * @author Administrator
 * 
 */
public class UrlUtil {

	public static String getPlanInfo(String itemId) {
		return "http://pub.alimama.com/pubauc/getCommonCampaignByItemId.json?itemId="
				+ itemId;
	}

	/**
	 * 获取推广位
	 * 
	 * @param token
	 * @return
	 */
	public static String newSelfAzone(String token) {
		return "http://pub.alimama.com/common/adzone/newSelfAdzone2.json?tag=29&blockId=&t="
				+ new Date().getTime()
				+ "&_tb_token_="
				+ token
				+ "&pvid=10_49.66.9.205_611_1481767394061";
	}

	/**
	 * 创建导购位
	 * 
	 * @return
	 */
	public static String seflAzoneCreate() {
		return "http://pub.alimama.com/common/adzone/selfAdzoneCreate.json";
	}

	/**
	 * 获取收入账户信息
	 * {"data":{"drawEnable":1,"lastMonthTotal":178.40,"curMonthTotal":119.30
	 * ,"yesterdayTotal":0,"isbound":"true"
	 * },"info":{"message":null,"ok":true},"ok":true,"invalidKey":null}
	 * 
	 * @param token
	 * @return
	 */
	public static String unionAccountInfo(String token) {
		return "http://pub.alimama.com/overview/unionaccountinfo.json?t="
				+ new Date().getTime() + "&pvid=&_tb_token_=" + token
				+ "&_input_charset=utf-8";
	}

	/**
	 * {"data":{"RefundReason":[0.11475409836065574],"thirtyPubNum":[13],
	 * "ServiceGap"
	 * :["45.12%"],"thirtyCmCommisionAmt":[2496.25],"SellerScore":[966
	 * ],"ConsignmentGapBottom"
	 * :[false],"Ind":["食品/保健"],"MerchandisAvgScore":["4.91"
	 * ],"MerchandisGapBottom"
	 * :[false],"thirtyCmSettleAmt":[3186.5],"thirtyCmSettleNum"
	 * :[40],"ServiceAvgScore"
	 * :["4.92"],"ServiceGapBottom":[false],"ConsignmentAvgScore"
	 * :["4.90"],"AvgRefundRate":[0.03220057044747254],"SellLevelPicture":[
	 * "http://pics.taobaocdn.com/newrank/s_blue_2.gif"
	 * ],"MerchandisGap":["49.20%"
	 * ],"ConsignmentGap":["47.70%"]},"info":{"message"
	 * :null,"ok":true},"ok":true}
	 * 
	 * @param id
	 * @return
	 */
	public static String getSellerInfo(String id, String token) {
		return "http://pub.alimama.com/pubauc/searchPromotionInfo.json?oriMemberId="
				+ id
				+ "&t=1459122339728&pvid=50_49.80.199.159_6639_1459122167931&_tb_token_="
				+ token + "&_input_charset=utf-8";
	}

	/**
	 * 优惠券链接
	 */
	public static String convertPCQuanToMobile(String yhq) {
		if (null != yhq) {
			return yhq.replace("taoquan.taobao.com/coupon/unify_apply.htm",
					"shop.m.taobao.com/shop/coupon.htm").replace(
					"taoquan.taobao.com/coupon/unify_apply_result.htm",
					"shop.m.taobao.com/shop/coupon.htm");
		}
		return null;
	}

	/************************************************************************
	 * 管方活动相关URL
	 ************************************************************************/
	/**
	 * 获取官方活动
	 * 
	 * @param page
	 * @param token
	 * @return
	 */
	public static String getOfficalEvent(int page, String token) {
		return "http://pub.alimama.com/event/newOfficialEventList.json?spm=a2320.7388781.a214tr8.d006.fmXbGi&toPage="
				+ page
				+ "&perPagesize=100&invokerType=1&periodType=0&platformType=0&orderWay=2&t="
				+ new Date().getTime()
				+ "&pvid=&_tb_token_="
				+ token
				+ "&_input_charset=utf-8";
	}

	/**
	 * 官方活动推广
	 * 
	 * @param url
	 * @param invokerId
	 * @param sid
	 *            第二段PID
	 * @param aid
	 *            第三段PID
	 * @param token
	 * @return
	 */
	public static String getOfficalEventTg(String url, String invokerId,
			String sid, String aid, String token) {
		return "http://pub.alimama.com/urltrans/urltransForOfficial.json?siteid="
				+ sid
				+ "&adzoneid="
				+ aid
				+ "&promotionURL="
				+ convert(url)
				+ "&eventId=2015012602&invokerId="
				+ invokerId
				+ "&t="
				+ new Date().getTime()
				+ "&pvid=&_tb_token_="
				+ token
				+ "&_input_charset=utf-8";
	}

	/**
	 * 转换链接工具
	 * 
	 * @param url
	 * @param invokerId
	 * @param sid
	 *            第二段PID
	 * @param aid
	 *            第三段PID
	 * @param token
	 * @return
	 */
	public static String getUrlTrans(String url, String sid, String aid,
			String token) {
		return "http://pub.alimama.com/urltrans/urltrans.json?siteid=" + sid
				+ "&adzoneid=" + aid + "&promotionURL=" + convert(url) + "&t="
				+ new Date().getTime()
				+ "&pvid=52_49.80.199.114_8167_1458388650007&_tb_token_="
				+ token + "&_input_charset=utf-8";
	}

	/**************************************************************************
	 * 计划相关URL
	 **************************************************************************/
	/**
	 * 计划推广链接
	 * */
	public static String getPlanExtendUrl(String userNumberId, String aid,
			String sid, String token) {
		return "http://pub.alimama.com/common/code/getShopCode.json?orimemberid="
				+ userNumberId
				+ "&adzoneid="
				+ aid
				+ "&siteid="
				+ sid
				+ "&t="
				+ new Date().getTime()
				+ "&_tb_token_="
				+ token
				+ "&_input_charset=utf-8";
	}

	/**
	 * 获取推广计划管理
	 * 
	 * @param page
	 * @param token
	 * @return
	 */
	public static String getSelfJoinedPlan(int page, String token) {
		return "http://pub.alimama.com/campaign/joinedCampaigns.json?spm=a2320.7388781.a214tr8.d006.4gaMVx&toPage="
				+ page
				+ "&perPageSize=40&t="
				+ new Date().getTime()
				+ "&_tb_token_=" + token + "&_input_charset=utf-8";
	}

	/****************************************************************
	 * 报表相关url
	 **************************************************************/
	/**
	 * 自助推广
	 */
	public static String getReportSelfUrl(String adzoneId, String startTime,
			String endTime) {
		return "http://pub.alimama.com/report/selfRpt.json?adzoneId="
				+ adzoneId
				+ "&startTime="
				+ startTime
				+ "&endTime="
				+ endTime
				+ "&t=1451203408755&_tb_token_=mPK3MuB966p&_input_charset=utf-8";
	}

	/**
	 * 自助推广 分页
	 */
	public static String getReportSelfPageUrl(String startTime, String endTime,
			int pageNo) {
		return "http://pub.alimama.com/report/selfRptByPaging.json?adzoneId=&startTime="
				+ startTime
				+ "&endTime="
				+ endTime
				+ "&pageNo="
				+ pageNo
				+ "&pageSize=20&t=1451352444770&_tb_token_=0Ii4B5rmQ6p&_input_charset=utf-8";
	}

	/**
	 * 媒体效果 分页
	 * 
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param gcId
	 *            媒体编号 网站0 app7 导购8 其他-1
	 * @return
	 */
	public static String getReportMediaUrl(String startTime, String endTime,
			int pageNo, String gcId) {
		return "http://pub.alimama.com/report/mediaRptByPaging.json?gcId="
				+ gcId
				+ "&siteId=&startTime="
				+ startTime
				+ "&endTime="
				+ endTime
				+ "&pageNo="
				+ pageNo
				+ "&pageSize=20&t=1451125667981&_tb_token_=M5o2E8EKv5p&_input_charset=utf-8";
	}

	/**
	 * 
	 淘宝客活动推广 分页
	 * 
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @return
	 */
	public static String getReportEventUrl(String startTime, String endTime,
			int pageNo, String token) {
		return "http://pub.alimama.com/report/linkEventRptByPaging.json?eventId=&adzoneId=&startTime="
				+ startTime
				+ "&endTime="
				+ endTime
				+ "&pageNo="
				+ pageNo
				+ "&pageSize=20&t=1451133319534&_tb_token_="
				+ token
				+ "&_input_charset=utf-8";
	}

	/**
	 * 淘宝客推广明细 订单结算
	 * 
	 * @param time1
	 * @param time2
	 * @param page
	 * @param t
	 * @param tbToken
	 * @return
	 */
	public static String getReportPaymentRecordUrl(String time1, String time2,
			int page, String t, String tbToken) {
		return "http://pub.alimama.com/report/getTbkPaymentDetails.json?startTime="
				+ time1
				+ "&endTime="
				+ time2
				+ "&payStatus=3&queryType=1&toPage="
				+ page
				+ "&perPageSize=20&total=&t="
				+ t
				+ "&_tb_token_="
				+ tbToken
				+ "&_input_charset=utf-8";
	}

	/**
	 * 抓取维权订单
	 * 
	 * @param time1
	 * @param time2
	 * @param page
	 * @param t
	 * @param tbToken
	 * @param payStatus
	 * @return
	 */
	public static String getRefundPaymentRecordUrl(String time1, String time2,
			int page, String tbToken) {
		StringBuffer sb = new StringBuffer(
				"http://pub.alimama.com/report/getNewTbkRefundPaymentDetails.json?startTime=")
				.append(time1)
				.append("&endTime=")
				.append(time2)
				.append("&refundType=1&searchType=1&toPage=1&perPageSize=20&t=1495185034560&pvid=&_tb_token_=")
				.append(tbToken).append("&_input_charset=utf-8");
		return sb.toString();
	}

	/**
	 * 淘宝客推广明细 订单失效
	 * 
	 * @param time1
	 * @param time2
	 * @param page
	 * @param t
	 * @param tbToken
	 * @return
	 */
	public static String getReportPaymentRecordUrl2(String time1, String time2,
			int page, String t, String tbToken, String payStatus) {
		StringBuffer buffer = new StringBuffer(
				"http://pub.alimama.com/report/getTbkPaymentDetails.json?startTime=")
				.append(time1).append("&endTime=").append(time2)
				.append("&payStatus=").append(payStatus)
				.append("&queryType=1&toPage=").append(page)
				.append("&perPageSize=999&total=999&t=").append(t)
				.append("&_tb_token_=").append(tbToken)
				.append("&_input_charset=utf-8");
		return buffer.toString();
	}

	/**
	 * 淘宝客推广明细 订单失效
	 * 
	 * @param time1
	 * @param time2
	 * @param page
	 * @param t
	 * @param tbToken
	 * @return
	 */
	public static String getReportPaymentRecordUrlJs(String time1,
			String time2, int page, String t, String tbToken, String payStatus) {
		StringBuffer buffer = new StringBuffer(
				"http://pub.alimama.com/report/getTbkPaymentDetails.json?startTime=")
				.append(time1).append("&endTime=").append(time2)
				.append("&payStatus=").append(payStatus)
				.append("&queryType=3&toPage=").append(page)
				.append("&perPageSize=999&total=999&t=").append(t)
				.append("&_tb_token_=").append(tbToken)
				.append("&_input_charset=utf-8");
		return buffer.toString();
	}

	/***********************************
	 * 调用淘爆款
	 ************************************/

	public static String getQuMaiCat(String cat) {
		return "http://www.qumai.org/cm/getcat.php?cat=" + cat;
	}

	/**
	 * 获取计划信息
	 * 
	 * @param itemid
	 * @return
	 */
	public static String getQuMaiPlan(String itemid) {
		return "http://www.qumai.org/getalimama.php?iid=" + itemid;
	}

	/**
	 * 获取鹊桥信息
	 * 
	 * @param itemid
	 * @return
	 */
	public static String getQuMailQueQiao(String itemid) {
		return "http://www.qumai.org/cm/getqj.php?iid=" + itemid;
	}

	/**
	 * 获取推广位
	 * 
	 * @param tag
	 * @param t
	 * @return
	 */
	public static String getAdzone(String tag, String t) {
		return "http://pub.alimama.com/common/adzone/newSelfAdzone2.json?tag="
				+ tag + "&t=" + t;
	}

	public static String getUnionPubContextInfo() {
		return "http://pub.alimama.com/common/getUnionPubContextInfo.json";
	}

	/**
	 * 9块9抓取链接
	 * 
	 * @return
	 */
	public static String get99Items(int page, String token, String t) {
		return "http://pub.alimama.com/group/searchGroupAuctionList.json?spm=a219t.7473494.1998155389.3.9Z7ryh&groupId=1375950175666&cat=&"
				+ "toPage="
				+ page
				+ "&perPagesize=40&t="
				+ t
				+ "&_tb_token_="
				+ token + "&_input_charset=utf-8";
	}

	public static String get99ItemsSuper(int page) {
		return "http://pub.alimama.com/items/channel/9k9.json?channel=9k9&_t=1470887350020&toPage="
				+ page
				+ "&perPageSize=40&shopTag=&t="
				+ new Date().getTime()
				+ "&_tb_token_=bzvGosGs4np&pvid=16_121.237.9.193_373_1470887339010";
	}

	/**
	 * 9块9推广链接 和单品链接唯一区别就是有一个groupid
	 * 
	 * @return
	 */
	public static String get99ItemsTuiGuang(String auctionid, String token,
			String sitid, String aid) {
		return "http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="
				+ auctionid
				+ "&adzoneid="
				+ aid
				+ "&siteid="
				+ sitid
				+ "&groupid=1375950175666&t=1447833041765&_tb_token_="
				+ token
				+ "&_input_charset=utf-8";
	}

	/**
	 * 根据链接搜索单品
	 * 
	 * @param link
	 * @param t
	 * @param tbToken
	 * @return
	 */
	public static String getSingleByLink(String link, String t, String tbToken) {
		return "http://pub.alimama.com/pubauc/searchAuctionList.json?spm=a219t.7473494.1998155389.3.04rHPL&q="
				+ convert(link)
				+ "&toPage=1&perPagesize=40&t="
				+ t
				+ "&_tb_token_=" + tbToken + "&_input_charset=utf-8";
	}

	/**
	 * 计划链接
	 * 
	 * @param camid
	 * @param keepid
	 * @param memberID
	 * @return
	 */
	public static String getPlanUrl(String camid, String keepid, String memberID) {
		return "http://pub.alimama.com/myunion.htm?spm=a219t.7473494.1998155387.6.blGbGI#!/promo/self/campaign?campaignId="
				+ camid
				+ "&shopkeeperId="
				+ keepid
				+ "&userNumberId="
				+ memberID;
	}

	/**
	 * 单品搜索相关的计划
	 * 
	 * @param memberid
	 * @return
	 */
	public static String getPlanByMemberID(String memberid, String t,
			String tbToken) {
		return "http://pub.alimama.com/pubauc/getCommonCampaignDetails.json?oriMemberid="
				+ memberid
				+ "&t="
				+ t
				+ "&_tb_token_="
				+ tbToken
				+ "&_input_charset=utf-8";
	}

	/**
	 * 查看计划明细
	 * 
	 * @param capid
	 * @param keepid
	 * @param page
	 * @param memberID
	 * @param t
	 * @param tbToken
	 * @return
	 */
	public static String getPlanDetail(String capid, String keepid, int page,
			String memberID, String t, String tbToken) {
		return "http://pub.alimama.com/campaign/merchandiseDetail.json?spm=a219t.7473494.1998155389.3.04rHPL&campaignId="
				+ capid
				+ "&shopkeeperId="
				+ keepid
				+ "&userNumberId="
				+ memberID
				+ "&tab=2&omid="
				+ memberID
				+ "&toPage="
				+ page
				+ "&perPagesize=10&t="
				+ t
				+ "&_tb_token_="
				+ tbToken
				+ "&_input_charset=utf-8";
	}

	/**
	 * 登录淘宝链接
	 * 
	 * @return
	 */
	public static String getLoginLMUrl() {
		return "https://login.taobao.com/member/login.jhtml?style=mini&from=alimama&redirectURL=http%3A%2F%2Flogin.taobao.com%2Fmember%2Ftaobaoke%2Flogin.htm%3Fis_login%3d1&full_redirect=true&disableQuickLogin=true";
	}

	/**
	 * 单品搜索链接
	 * 
	 * @return
	 */
	public static String getSingProductUrl(String key, int page) {
		String url = "http://pub.alimama.com/pubauc/searchAuctionList.json?spm=a2320.7388781.a214tr8.d006.ofnJ4a&q="
				+ convert(key)
				+ "&toPage="
				+ page
				+ "&perPagesize=40&t="
				+ new Date().getTime()
				+ "&_tb_token_=BdUI7uJ4xqo&_input_charset=utf-8";// 单品搜索链接
		return url;
	}

	/**
	 * 使用超级搜索接口
	 * 
	 * @param key
	 * @param page
	 * @param token
	 * @return
	 */
	public static String getSuperProductUrl(String key, int page, String token) {
		return "http://pub.alimama.com/items/search.json?q="
				+ convert(key)
				+ "&_t="
				+ new Date().getTime()
				+ "&toPage="
				+ page
				+ "&auctionTag=&perPageSize=50&shopTag=&t=1460186575858&_tb_token_="
				+ token;
	}

	/**
	 * 使用超级搜索接口来查链接
	 * 
	 * @param url
	 * @param token
	 * @return
	 */
	public static String getSuperProductByUrl(String url, String token) {
		return "http://pub.alimama.com/items/search.json?q=" + convert(url)
				+ "&_t=" + new Date().getTime()
				+ "&auctionTag=&perPageSize=50&shopTag=&t="
				+ new Date().getTime() + "&_tb_token_=" + token;
	}

	/**
	 * 使用超级搜索接口来查链接
	 * 
	 * @param url
	 * @param token
	 * @return
	 */
	public static String getSuperProductByUrl2(String url) {
		return "http://pub.alimama.com/items/search.json?q=" + convert(url)
				+ "&_t=" + new Date().getTime()
				+ "&auctionTag=&perPageSize=50&shopTag=&t="
				+ new Date().getTime();
	}

	/**
	 * 使用高佣搜索接口来查链接
	 * 
	 * @param url
	 * @param token
	 * @return
	 */
	public static String getSuperProductByUrl3(String url) {
		return "http://pub.alimama.com/items/channel/qqhd.json?q="
				+ convert(url)
				+ "8&channel=qqhd&_t="
				+ new Date().getTime()
				+ "&perPageSize=40&shopTag=&t="
				+ new Date().getTime()
				+ "&_tb_token_=tZbSjmDfQlp&pvid=19_49.80.197.120_354_1470132891070";
	}

	/**
	 * 单品推广转化 http://pub.alimama.com/common/code/getAuctionCode.json?auctionid=
	 * 520297474320
	 * &adzoneid=38806989&siteid=11154725&t=1446474588406&_tb_token_=
	 * oYBgLIuamvo&_input_charset=utf-8
	 * 
	 * @param auctionid
	 * @param token
	 * @return
	 */
	public static String getSingProduTuiGuangUrl(String auctionid,
			String token, String sitid, String aid) {
		return "http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="
				+ auctionid
				+ "&adzoneid="
				+ aid
				+ "&siteid="
				+ sitid
				+ "&t=1446474588406&_tb_token_="
				+ token
				+ "&_input_charset=utf-8";
	}

	/***
	 * 2016-4-13新增 鹊桥新版本转换接口
	 */
	public static String getQueqiaoTuiGuangUrl(String auctionid, String token,
			String sitid, String aid) {
		return "http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="
				+ auctionid
				+ "&adzoneid="
				+ aid
				+ "&siteid="
				+ sitid
				+ "&scenes=3&channel=tk_qqhd&t="
				+ new Date().getTime()
				+ "&_tb_token_=" + token;
	}

	/**
	 * 获取计划url
	 * 
	 * @param userID
	 * @param token
	 * @return
	 */
	public static String getPlanUrl(String userID, String token) {
		return "http://pub.alimama.com/pubauc/getCommonCampaignDetails.json?oriMemberid="
				+ userID
				+ "&t=1445002021101&_tb_token_="
				+ token
				+ "&_input_charset=utf-8";
	}

	/**
	 * _tb_token_ applyreason campId keeperid
	 * 
	 * @return
	 */
	public static String getApplyForCommonCampaign() {
		return "http://pub.alimama.com/pubauc/applyForCommonCampaign.json";
	}

	/**
	 * 获取鹊桥活动链接
	 * 
	 * @param token
	 * @param t
	 * @return
	 */
	public static String getQueQiaoUrl(String token, String t, int page) {
		return "http://pub.alimama.com/event/squareList.json?spm=a219t.7473494.1998155387.5.avpGIc&orderType=3&key=&toPage="
				+ page
				+ "&perPageSize=100&"
				+ "platformType=-1&catId=-1&commissionRangeType=-1&eventStatus=-1&highQuality=-1&"
				+ "promotionType=-1&t="
				+ t
				+ "&_tb_token_="
				+ token
				+ "&_input_charset=utf-8";
	}

	/**
	 * 获取活动商品相应的链接
	 * 
	 * @return
	 */
	public static String getQueQiaoItems(int page, String pid1, String pid2,
			String pid3, String eventID) {
		return "http://temai.taobao.com/event/items.json?toPage=" + page
				+ "&perPageSize=20&catId=&tagId=&pid=mm_" + pid1 + "_" + pid2
				+ "_" + pid3 + "&unid=&platformType=&isPreview=0&id=" + eventID;
	}

	/**
	 * 获取活动商品相应的链接
	 * 
	 * @return
	 */
	public static String getQueQiaoItemsByCat(int page, String pid1,
			String pid2, String pid3, String eventID, String catID) {
		return "http://temai.taobao.com/event/items.json?toPage=" + page
				+ "&perPageSize=20&catId=" + catID + "&tagId=&pid=mm_" + pid1
				+ "_" + pid2 + "_" + pid3
				+ "&unid=&platformType=&isPreview=0&id=" + eventID;
	}

	/**
	 * 获取活动商品相应的链接
	 * 
	 * @return
	 */
	public static String getQueQiaoItems2(int page, String pid, String eventID) {
		return "http://temai.taobao.com/event/items.json?toPage=" + page
				+ "&perPageSize=20&catId=&tagId=&pid=mm_" + pid
				+ "&unid=&platformType=&isPreview=0&id=" + eventID;
	}

	// https://detail.tmall.com/item.htm?id=39029487868 天猫
	// https://item.taobao.com/item.htm?id=523196499768

	public static String getQueQiaoItemTuiGuang(String url, String token,
			String t) {
		return "http://pub.alimama.com/urltrans/urltrans.json?promotionURL="
				+ convert(url) + "&t=" + t + "&_tb_token_=" + token
				+ "&_input_charset=utf-8";
	}

	/**
	 * 获取下计划的明细
	 * 
	 * @param campid
	 * @param sellerId
	 * @param token
	 * @return
	 */
	public static String campaignDetai(String campid, String sellerId,
			String token) {
		return "http://pub.alimama.com/campaign/campaignDetail.json?campaignId="
				+ campid
				+ "&shopkeeperId="
				+ sellerId
				+ "&t="
				+ new Date().getTime()
				+ "&pvid=&_tb_token_="
				+ token
				+ "&_input_charset=utf-8";
	}

	public static String campaigns(String sellerid, String token) {
		return "http://pub.alimama.com/shopdetail/campaigns.json?oriMemberId="
				+ sellerid + "&t=" + new Date().getTime() + "&_tb_token_="
				+ token + "&_input_charset=utf-8";
	}

	/**
	 * {"data":{"RefundReason":[0.11475409836065574],"thirtyPubNum":[13],
	 * "ServiceGap"
	 * :["45.12%"],"thirtyCmCommisionAmt":[2496.25],"SellerScore":[966
	 * ],"ConsignmentGapBottom"
	 * :[false],"Ind":["食品/保健"],"MerchandisAvgScore":["4.91"
	 * ],"MerchandisGapBottom"
	 * :[false],"thirtyCmSettleAmt":[3186.5],"thirtyCmSettleNum"
	 * :[40],"ServiceAvgScore"
	 * :["4.92"],"ServiceGapBottom":[false],"ConsignmentAvgScore"
	 * :["4.90"],"AvgRefundRate":[0.03220057044747254],"SellLevelPicture":[
	 * "http://pics.taobaocdn.com/newrank/s_blue_2.gif"
	 * ],"MerchandisGap":["49.20%"
	 * ],"ConsignmentGap":["47.70%"]},"info":{"message"
	 * :null,"ok":true},"ok":true}
	 * 
	 * @param id
	 * @return
	 */
	public static String getSellerInfo(String id) {
		return "http://pub.alimama.com/pubauc/searchPromotionInfo.json?oriMemberId="
				+ id
				+ "&t=1459122339728&pvid=50_49.80.199.159_6639_1459122167931&_tb_token_=ROH1SVUPONp&_input_charset=utf-8";
	}

	public static String convert(String str) {
		String eStr = null;
		try {
			eStr = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			System.out.println("客户端登录有错误");
		}
		return eStr;
	}

	public static String deConvert(String str) {
		String eStr = null;
		try {
			eStr = URLDecoder.decode(str, "utf-8");
		} catch (Exception e) {
			try {
				eStr = URLDecoder.decode(str.replaceAll("%", "%25"), "utf-8");
				return eStr;
			} catch (Exception e2) {
				System.out.println(str);
				e2.printStackTrace();
				System.out.println("解码错误");
			}
		}
		return eStr;
	}

	public static String deConvertGbk(String str) {
		String eStr = null;
		try {
			eStr = URLDecoder.decode(str, "gbk");
		} catch (Exception e) {
			try {
				eStr = URLDecoder.decode(str.replaceAll("%", "%25"), "gbk");
				return eStr;
			} catch (Exception e2) {
				System.out.println("解码错误");
			}
		}
		return eStr;
	}

	public static String deConvert2(String str) {
		String eStr = null;
		try {
			eStr = new String(str.getBytes("GBK"), "UTF-8");
		} catch (Exception e) {
		}
		return eStr;
	}

	public static String deConvert3(String str) {
		String eStr = null;
		try {
			eStr = new String(str.getBytes("ISO-8859-1"), "gbk");
		} catch (Exception e) {
		}
		return eStr;
	}

	public static void main(String[] args) {
		System.out.println(convert("木流锦"));
	}
}
