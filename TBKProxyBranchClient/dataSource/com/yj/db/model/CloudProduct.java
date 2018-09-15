package com.yj.db.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.AbstractField;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.yj.db.model.reuse.BaseProduct;
import com.yj.db.model.reuse.BasicModel;
import com.yj.util.DateUtil;
import com.yj.util.StringUtil;
import com.yj.util.TaoBaoApi;

/**
 * 云产品库数据
 * 
 * @author Administrator
 * 
 */
public class CloudProduct extends BaseProduct {
	private static final long serialVersionUID = 1L;
	private String productType;// 类型 优惠券,普通 1 通用 2 鹊桥 3 定向
	private String cat;// 分类
	private Integer isMall;// 1=天猫 0=淘宝
	private String itemTitle;// 宝贝标题
	private String shortTitle;// 副标题
	private Integer score = 100;// 评分
	private Double discountPrice;// 特价
	private Integer soldQuantity;// 销量
	private Double realCommissionRate;// 佣金率
	private String content;// 文案
	private String pictUrl;// 图片链接
	private String pictUrl2;// 图片链接
	private String pictUrl3;// 图片链接
	private String pictUrl4;// 图片链接
	private String pictUrl5;// 图片链接
	private String itemId;// 宝贝编号
	private String eventId;// 鹊桥编号
	// 4.5新增字段
	private Double quanPrice;// 优惠券价格
	private Integer rest;// 优惠券剩余数量
	private Integer used;// 优惠券使用数量
	private Date expired;// 优惠券到期日期
	private Double require;// 满多少
	private Double refundReason;// 30天退款率
	private Integer commentCount = 0;// 累计评价
	private Double avgRefundRate;// 行业均值
	private Integer sellerScore;// 信誉
	private String ind;// 主营行业
	private String merchandisAvgScore;// 描述相符
	private String merchandisGap;// 描述相符高于
	private String serviceAvgScore;// 服务态度
	private String serviceGap;// 服务态度高于
	private String consignmentAvgScore;// 发货速度
	private String consignmentGap;// 发货速度高于
	private String pcCoupon;//
	private String mobileCoupon;//
	private String wxContent;// 微信文案
	private String tkl;// 淘口令
	private String tbName;// 产品对应的采集账号
	// 8.26新增字段
	private Integer todayQuantity = 0;// 今日销量

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	private Integer currentQuantity = 0;// 近两小时销量
	private Timestamp lastQuantityUpdateTime;// 上次更新销量时间
	private Integer lastQuantity = 0;// 上次更新销量
	private Double cost;// 裂变成本

	private Timestamp lastQuanUpdateTime;
	private Integer currentQuan = 0;// 近两小时领券量
	private Integer lastQuan = 0;

	// 新增字段
	private String campId;// 定向计划id
	private String keeperId;// 店铺id
	private String orig = "采";// 来源/置顶字段 zd表示置顶的

	// 非传字段
	private String href;// 宝贝链接
	private Timestamp updateTime;// 更新时间
	private String indexFlag = "new";// 索引标志
	private String auditFlag = "0";// 审核标志 0未审核 1审核通过 2审核不过
	private Double realCommission;// 佣金
	private Double jf;// 三级模式返积分
	// 店铺信息
	private Double merchandisAvgScoreNum;// 描述相符
	private Double merchandisGapNum;// 描述相符高于
	private Double serviceAvgScoreNum;// 服务态度
	private Double serviceGapNum;// 服务态度高于
	private Double consignmentAvgScoreNum;// 发货速度
	private Double consignmentGapNum;// 发货速度高于

	// 以下为查询字段
	private transient Double cost1;// 成本上限
	private transient Double discountPrice2;// 特价上限
	private transient Double realCommission2;// 佣金上限
	private transient Double realCommissionRate2;// 佣金率上限
	private transient Integer score1;// 评分下限
	private transient Integer score2;// 评分上限
	private transient Integer soldQuantity2;// 销量上限
	private transient String seller;// 卖家信用查询字段
	private String shopType;// 店铺标签
	private String shopName;// 店铺类型名称
	private String updateTimeStr;
	private String yhqNum;// 优惠券信息
	private String expiredStr;// 优惠券到期
	private Double afterQuan;// 券后价格
	private String dpPic;

	private transient String searchFlag;// 查询标识 hasQuan noQuan lb pd top

	private Timestamp updateTime2;// 更新时间
	private String content2;

	private String localPictUrl;// 图片链接
	private String localPictUrl2;// 图片链接
	private String localPictUrl3;// 图片链接
	private String localPictUrl4;// 图片链接
	private String localPictUrl5;// 图片链接

	// 详情页图片1

	private Timestamp updateTime3;// 更新时间

	private String pid;

	private String newUrl;

	// 发送标志
	public static final String SEND_FLGA_NO = "1";
	public static final String SEND_FLAG_SEND = "2";
	private String sendFlag;// 发送标志

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getNewUrl() {
		return newUrl;
	}

	public Double getJf() {
		if (null != jf && null != productType && productType.equals("2")) {
			jf = calIntegral(95, jf);
		}
		return jf;
	}

	public void setJf(Double jf) {
		this.jf = jf;
	}

	public void createNewUrl(String pid, String sessionKey) {
		String src = "xshe_xqtz";
		if (null != getMobileCoupon()) {
			String url2 = getMobileCoupon()
					.replaceAll("http://shop.m.taobao.com/shop/coupon.htm", "")
					.replace("https://shop.m.taobao.com/shop/coupon.htm", "")
					.replace(
							"https://market.m.taobao.com/apps/aliyx/coupon/detail.html",
							"")
					.replace("http://uland.taobao.com/cp/coupon", "")
					.replace("?", "");
			List<String> strs = StringUtil.split(url2, "&");
			String activityId = "";
			for (String str : strs) {
				if (str.contains("activity_id")) {
					activityId = str.replace("activity_id=", "");
				}
				if (str.contains("activityId")) {
					activityId = str.replace("activityId=", "");
				}
			}
			List<String> pidStrArray = StringUtil.split(pid, "_");
			String pid2 = pidStrArray.get(1);
			String pid3 = pidStrArray.get(2);
			if (null == getProductType() || getProductType().equals("4")
					|| getProductType().isEmpty()) {// 营销计划
				String itemid = getItemId();
				newUrl = TaoBaoApi.convert(pid2, pid3, itemid, sessionKey,
						realCommissionRate);
				if (null != newUrl && !newUrl.contains("error")) {
					StringBuffer buffer = new StringBuffer(newUrl).append(
							"&activityId=").append(activityId);
					newUrl = buffer.toString();
				}
			} else {
				newUrl = TaoBaoApi.convert(pid2, pid3, getItemId(), sessionKey,
						realCommissionRate);// 调用高佣api
				if (null != newUrl && !newUrl.contains("error")) {
					StringBuffer buffer = new StringBuffer(newUrl).append(
							"&activityId=").append(activityId);
					newUrl = buffer.toString();
				} else {// 失败了就使用拼链接的形式
					StringBuffer buffer = new StringBuffer(
							"https://uland.taobao.com/coupon/edetail?activityId=")
							.append(activityId).append("&pid=mm_").append(pid)
							.append("&itemId=").append(getItemId())
							.append("&src=").append(src);
					if (productType.equals("3") || productType.equals("1")) {
						buffer.append("&dx=1");
					}
					newUrl = buffer.toString();
				}
			}
		}
	}

	public void createNewUrl2(String pid, String sessionKey) {
		List<String> pidStrArray = StringUtil.split(pid, "_");
		String pid2 = pidStrArray.get(1);
		String pid3 = pidStrArray.get(2);
		String itemid = getItemId();
		newUrl = TaoBaoApi.convert(pid2, pid3, itemid, sessionKey,
				realCommissionRate);
		if (null != getMobileCoupon()) {
			String url2 = getMobileCoupon()
					.replaceAll("http://shop.m.taobao.com/shop/coupon.htm", "")
					.replace("https://shop.m.taobao.com/shop/coupon.htm", "")
					.replace(
							"https://market.m.taobao.com/apps/aliyx/coupon/detail.html",
							"")
					.replace("http://uland.taobao.com/cp/coupon", "")
					.replace("?", "");
			List<String> strs = StringUtil.split(url2, "&");
			String activityId = "";
			for (String str : strs) {
				if (str.contains("activity_id")) {
					activityId = str.replace("activity_id=", "");
				}
				if (str.contains("activityId")) {
					activityId = str.replace("activityId=", "");
				}
			}

			if (null != newUrl && !newUrl.contains("error")) {
				StringBuffer buffer = new StringBuffer(newUrl).append(
						"&activityId=").append(activityId);
				newUrl = buffer.toString();
			}

		}
	}

	public void setNewUrl(String newUrl) {
		this.newUrl = newUrl;
	}

	public String getLocalPictUrl() {
		if (null != getPictUrl()) {
			localPictUrl = "product/pic/" + getItemId() + "/pict.jpg";
		}
		return localPictUrl;
	}

	public void setLocalPictUrl(String localPictUrl) {
		this.localPictUrl = localPictUrl;
	}

	public String getLocalPictUrl2() {
		if (null != getPictUrl2()) {
			localPictUrl2 = "product/pic/" + getItemId() + "/pict2.jpg";
		}
		return localPictUrl2;
	}

	public void setLocalPictUrl2(String localPictUrl2) {
		this.localPictUrl2 = localPictUrl2;
	}

	public String getLocalPictUrl3() {
		if (null != getPictUrl3()) {
			localPictUrl3 = "product/pic/" + getItemId() + "/pict3.jpg";
		}
		return localPictUrl3;
	}

	public void setLocalPictUrl3(String localPictUrl3) {
		this.localPictUrl3 = localPictUrl3;
	}

	public String getLocalPictUrl4() {
		if (null != getPictUrl4()) {
			localPictUrl4 = "product/pic/" + getItemId() + "/pict4.jpg";
		}
		return localPictUrl4;
	}

	public void setLocalPictUrl4(String localPictUrl4) {
		this.localPictUrl4 = localPictUrl4;
	}

	public String getLocalPictUrl5() {
		if (null != getPictUrl5()) {
			localPictUrl5 = "product/pic/" + getItemId() + "/pict5.jpg";
		}
		return localPictUrl5;
	}

	public void setLocalPictUrl5(String localPictUrl5) {
		this.localPictUrl5 = localPictUrl5;
	}

	public String getWxContent() {
		return wxContent;
	}

	public void setWxContent(String wxContent) {
		this.wxContent = wxContent;
	}

	public String getTkl() {
		return tkl;
	}

	public void setTkl(String tkl) {
		this.tkl = tkl;
	}

	public String getPictUrl2() {
		return pictUrl2;
	}

	public void setPictUrl2(String pictUrl2) {
		this.pictUrl2 = pictUrl2;
	}

	public String getPictUrl3() {
		return pictUrl3;
	}

	public void setPictUrl3(String pictUrl3) {
		this.pictUrl3 = pictUrl3;
	}

	public String getPictUrl4() {
		return pictUrl4;
	}

	public void setPictUrl4(String pictUrl4) {
		this.pictUrl4 = pictUrl4;
	}

	public String getPictUrl5() {
		return pictUrl5;
	}

	public void setPictUrl5(String pictUrl5) {
		this.pictUrl5 = pictUrl5;
	}

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

	public Timestamp getLastQuanUpdateTime() {
		return lastQuanUpdateTime;
	}

	public void setLastQuanUpdateTime(Timestamp lastQuanUpdateTime) {
		this.lastQuanUpdateTime = lastQuanUpdateTime;
	}

	public Integer getCurrentQuan() {
		return currentQuan;
	}

	public void setCurrentQuan(Integer currentQuan) {
		this.currentQuan = currentQuan;
	}

	public Integer getLastQuan() {
		return lastQuan;
	}

	public void setLastQuan(Integer lastQuan) {
		this.lastQuan = lastQuan;
	}

	public Double getCost1() {
		return cost1;
	}

	public void setCost1(Double cost1) {
		this.cost1 = cost1;
	}

	// 券后价减去佣金
	public Double getCost() {
		if (null != getAfterQuan() && null != getRealCommission()) {
			BigDecimal b1 = new BigDecimal("" + getAfterQuan());
			BigDecimal b2 = new BigDecimal("" + getRealCommission());
			return b1.subtract(b2).doubleValue();
		}
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Timestamp getLastQuantityUpdateTime() {
		return lastQuantityUpdateTime;
	}

	public void setLastQuantityUpdateTime(Timestamp lastQuantityUpdateTime) {
		this.lastQuantityUpdateTime = lastQuantityUpdateTime;
	}

	public Integer getLastQuantity() {
		return lastQuantity;
	}

	public void setLastQuantity(Integer lastQuantity) {
		this.lastQuantity = lastQuantity;
	}

	public Integer getTodayQuantity() {
		return todayQuantity;
	}

	public void setTodayQuantity(Integer todayQuantity) {
		this.todayQuantity = todayQuantity;
	}

	public Integer getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(Integer currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public String getOrig() {
		return orig;
	}

	public void setOrig(String orig) {
		this.orig = orig;
	}

	public String getShortTitle() {
		if (null == shortTitle) {
			return itemTitle;
		}
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	// 区分商家产品
	private String applyFlag;

	public String getApplyFlag() {
		return applyFlag;
	}

	public void setApplyFlag(String applyFlag) {
		this.applyFlag = applyFlag;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public Timestamp getUpdateTime2() {
		return updateTime2;
	}

	public void setUpdateTime2(Timestamp updateTime2) {
		this.updateTime2 = updateTime2;
	}

	public Double getRequire() {
		return require;
	}

	public void setRequire(Double require) {
		this.require = require;
	}

	public CloudProduct() {
		setSord("desc");
	}

	public String getDpPic() {
		if (null == sellerScore) {
			return dpPic;
		}
		if (sellerScore < 10) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_red_1.gif";
		} else if (sellerScore < 40) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_red_2.gif";
		} else if (sellerScore < 90) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_red_3.gif";
		} else if (sellerScore < 150) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_red_4.gif";
		} else if (sellerScore < 250) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_red_5.gif";
		} else if (sellerScore < 500) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_blue_1.gif";
		} else if (sellerScore < 1000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_blue_2.gif";
		} else if (sellerScore < 2000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_blue_3.gif";
		} else if (sellerScore < 5000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_blue_4.gif";
		} else if (sellerScore < 10000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_blue_5.gif";
		} else if (sellerScore < 20000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_cap_1.gif";
		} else if (sellerScore < 50000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_cap_2.gif";
		} else if (sellerScore < 100000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_cap_3.gif";
		} else if (sellerScore < 200000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_cap_4.gif";
		} else if (sellerScore < 500000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_cap_5.gif";
		} else if (sellerScore < 1000000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_crown_1.gif";
		} else if (sellerScore < 2000000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_crown_2.gif";
		} else if (sellerScore < 5000000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_crown_3.gif";
		} else if (sellerScore < 10000000) {
			dpPic = "http://pics.taobaocdn.com/newrank/s_crown_4.gif";
		} else {
			dpPic = "http://pics.taobaocdn.com/newrank/s_crown_5.gif";
		}
		return dpPic;
	}

	public void setDpPic(String dpPic) {
		this.dpPic = dpPic;
	}

	public Double getAfterQuan() {
		if (null != getDiscountPrice()) {
			afterQuan = getDiscountPrice();
			if (null != getQuanPrice()) {
				BigDecimal b1 = new BigDecimal("" + getQuanPrice());
				BigDecimal b2 = new BigDecimal("" + getDiscountPrice());
				afterQuan = b2.subtract(b1).doubleValue();
			}
			if (null != getRequire() && getRequire() > getDiscountPrice()) {
				afterQuan = getDiscountPrice();
			}
		}
		return afterQuan;
	}

	public void setAfterQuan(Double afterQuan) {
		this.afterQuan = afterQuan;
	}

	public String getExpiredStr() {
		if (null != getExpired()) {
			SimpleDateFormat fomat = new SimpleDateFormat("yy-MM-dd");
			expiredStr = fomat.format(getExpired());
			if (getExpired().before(DateUtil.formateTodayDate(0))) {
				expiredStr += "<font color='red'>过期</font>";
			}
		} else {
			expiredStr = "无";
		}
		return expiredStr;
	}

	public void setExpiredStr(String expiredStr) {
		this.expiredStr = expiredStr;
	}

	public String getYhqNum() {
		if (null != rest && null != used && used > 0) {
			yhqNum = (int) quanPrice.doubleValue() + "元  剩余 <font color=red>"
					+ rest + "</font>/" + (rest + used) + "";
		} else {
			yhqNum = "无";
		}
		return yhqNum;
	}

	public void setYhqNum(String yhqNum) {
		this.yhqNum = yhqNum;
	}

	public String getUpdateTimeStr() {
		if (null != getProductType()) {
			if (null != getUpdateTime()) {
				SimpleDateFormat fomat = new SimpleDateFormat("yy-MM-dd  HH:mm");
				updateTimeStr = fomat.format(getUpdateTime());
			}
			String str = "<font class='color-brand'> 通用最高</font>";
			if (productType.equals("2")) {
				str = "<font class='color-brand'> 鹊桥最高</font>";
			}
			if (productType.equals("3")) {
				str = "<font class='color-brand'> 定向最高</font>";
			}
		}
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getShopType() {
		if (null != getIsMall()) {
			shopType = isMall == 1 ? "tag-tmall" : "tag-tb";
		}
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getShopName() {
		if (null != getIsMall()) {
			shopName = isMall == 1 ? "天猫店铺" : "淘宝店铺";
		}
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getScore1() {
		return score1;
	}

	public void setScore1(Integer score1) {
		this.score1 = score1;
	}

	public Double getMerchandisAvgScoreNum() {
		if (null != getMerchandisAvgScore()
				&& !getMerchandisAvgScore().trim().isEmpty()) {
			merchandisAvgScoreNum = Double.valueOf(getMerchandisAvgScore());
		}
		return merchandisAvgScoreNum;
	}

	public Double getMerchandisGapNum() {
		if (null != getMerchandisGap() && !getMerchandisGap().trim().isEmpty()) {
			merchandisGapNum = Double.valueOf(getMerchandisGap().replace("%",
					"").trim());
		}
		return merchandisGapNum;
	}

	public Double getServiceAvgScoreNum() {
		if (null != getServiceAvgScore()
				&& !getServiceAvgScore().trim().isEmpty()) {
			serviceAvgScoreNum = Double.valueOf(getServiceAvgScore());
		}
		return serviceAvgScoreNum;
	}

	public Double getServiceGapNum() {
		if (null != getServiceGap() && !getServiceGap().trim().isEmpty()) {
			serviceGapNum = Double.valueOf(getServiceGap().replace("%", "")
					.trim());
		}
		return serviceGapNum;
	}

	public Double getConsignmentAvgScoreNum() {
		if (null != getConsignmentAvgScore()
				&& !getConsignmentAvgScore().isEmpty()) {
			consignmentAvgScoreNum = Double.valueOf(getConsignmentAvgScore());
		}
		return consignmentAvgScoreNum;
	}

	public Double getConsignmentGapNum() {
		if (null != getConsignmentGap()
				&& !getConsignmentGap().trim().isEmpty()) {
			consignmentGapNum = Double.valueOf(getConsignmentGap().replace("%",
					"").trim());
		}
		return consignmentGapNum;
	}

	public void setMerchandisAvgScoreNum(Double merchandisAvgScoreNum) {
		this.merchandisAvgScoreNum = merchandisAvgScoreNum;
	}

	public void setMerchandisGapNum(Double merchandisGapNum) {
		this.merchandisGapNum = merchandisGapNum;
	}

	public void setServiceAvgScoreNum(Double serviceAvgScoreNum) {
		this.serviceAvgScoreNum = serviceAvgScoreNum;
	}

	public void setServiceGapNum(Double serviceGapNum) {
		this.serviceGapNum = serviceGapNum;
	}

	public void setConsignmentAvgScoreNum(Double consignmentAvgScoreNum) {
		this.consignmentAvgScoreNum = consignmentAvgScoreNum;
	}

	public void setConsignmentGapNum(Double consignmentGapNum) {
		this.consignmentGapNum = consignmentGapNum;
	}

	public Integer getSoldQuantity2() {
		return soldQuantity2;
	}

	public void setSoldQuantity2(Integer soldQuantity2) {
		this.soldQuantity2 = soldQuantity2;
	}

	public Integer getScore2() {
		return score2;
	}

	public void setScore2(Integer score2) {
		this.score2 = score2;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public Integer getIsMall() {
		return isMall;
	}

	public void setIsMall(Integer isMall) {
		this.isMall = isMall;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(Integer soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public Double getRealCommissionRate() {
		return realCommissionRate;
	}

	public void setRealCommissionRate(Double realCommissionRate) {
		this.realCommissionRate = realCommissionRate;
	}

	public String getContent() {
		if (null != content) {
			content = content.trim().replace("\t", "");
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPictUrl() {
		if (null != pictUrl) {

		}
		return pictUrl;
	}

	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Double getQuanPrice() {
		return quanPrice;
	}

	public void setQuanPrice(Double quanPrice) {
		this.quanPrice = quanPrice;
	}

	public Integer getRest() {
		return rest;
	}

	public void setRest(Integer rest) {
		this.rest = rest;
	}

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public Double getRefundReason() {
		if (null == refundReason) {
			refundReason = 0.0;
		}
		return refundReason;
	}

	public void setRefundReason(Double refundReason) {
		this.refundReason = refundReason;
	}

	public Integer getCommentCount() {
		if (null == commentCount) {
			commentCount = 0;
		}
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Double getAvgRefundRate() {
		if (null == avgRefundRate) {
			avgRefundRate = 0.0;
		}
		return avgRefundRate;
	}

	public void setAvgRefundRate(Double avgRefundRate) {
		this.avgRefundRate = avgRefundRate;
	}

	public Integer getSellerScore() {
		if (null == sellerScore) {
			sellerScore = 100000;
		}
		return sellerScore;
	}

	public void setSellerScore(Integer sellerScore) {
		this.sellerScore = sellerScore;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public String getMerchandisAvgScore() {
		return merchandisAvgScore;
	}

	public void setMerchandisAvgScore(String merchandisAvgScore) {
		this.merchandisAvgScore = merchandisAvgScore;
	}

	public String getMerchandisGap() {
		return merchandisGap;
	}

	public void setMerchandisGap(String merchandisGap) {
		this.merchandisGap = merchandisGap;
	}

	public String getServiceAvgScore() {
		return serviceAvgScore;
	}

	public void setServiceAvgScore(String serviceAvgScore) {
		this.serviceAvgScore = serviceAvgScore;
	}

	public String getServiceGap() {
		return serviceGap;
	}

	public void setServiceGap(String serviceGap) {
		this.serviceGap = serviceGap;
	}

	public String getConsignmentAvgScore() {
		return consignmentAvgScore;
	}

	public void setConsignmentAvgScore(String consignmentAvgScore) {
		this.consignmentAvgScore = consignmentAvgScore;
	}

	public String getConsignmentGap() {
		return consignmentGap;
	}

	public void setConsignmentGap(String consignmentGap) {
		this.consignmentGap = consignmentGap;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getIndexFlag() {
		return indexFlag;
	}

	public void setIndexFlag(String indexFlag) {
		this.indexFlag = indexFlag;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public Double getRealCommission() {
		return realCommission;
	}

	public void setRealCommission(Double realCommission) {
		this.realCommission = realCommission;
	}

	public Double getDiscountPrice2() {
		return discountPrice2;
	}

	public void setDiscountPrice2(Double discountPrice2) {
		this.discountPrice2 = discountPrice2;
	}

	public Double getRealCommission2() {
		return realCommission2;
	}

	public void setRealCommission2(Double realCommission2) {
		this.realCommission2 = realCommission2;
	}

	public Double getRealCommissionRate2() {
		return realCommissionRate2;
	}

	public void setRealCommissionRate2(Double realCommissionRate2) {
		this.realCommissionRate2 = realCommissionRate2;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHref() {
		if (null != getItemId() && null != getIsMall()) {
			href = getIsMall() == 1 ? "https://detail.tmall.com/item.htm?id="
					+ getItemId() : "https://item.taobao.com/item.htm?id="
					+ getItemId();
		}
		return href;
	}

	/***
	 * lucene相关字段
	 */

	public void loadValueFromLuncene(Document doc, List items) {
		for (Object o : items) {
			if (o.equals("itemId")) {
				setItemId(doc.get("itemId"));
			}
			if (o.equals("mobileCoupon")) {
				setMobileCoupon(doc.get("mobileCoupon"));
			}
		}
	}

	/**
	 * 从luncene加载下内容
	 */
	@Override
	public void loadValueFromLuncene(Document doc) {
	}

	/**
	 * 获取下luncene的字段
	 */
	@Override
	public List<AbstractField> createtLuceneField() {
		return null;
	}

	@Override
	public Sort createSort() {
		return super.createSort();
	}

	/**
	 * 获取luncene查询语句
	 */
	@Override
	public Query createQuery() {
		return null;
	}

	public String getCampId() {
		return campId;
	}

	public void setCampId(String campId) {
		this.campId = campId;
	}

	public String getKeeperId() {
		return keeperId;
	}

	public void setKeeperId(String keeperId) {
		this.keeperId = keeperId;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	/**
	 * 特价-优惠券=实际价格 实际价格在90以上的话 Q群是很难推动的 所以价格评分为0分 然后下面的话就是10块以内是30分 每加10块减3分
	 * 如果优惠券到期 或者数量到0 那么实际价格=特价 佣金的话 90%为满分30分 每少10%减3分 销量评分高于1000笔 满分10分 其他情况
	 * 每100笔销量加1分 然后如果评价数目不足销量的1/10 那么销量评分减半 关于信誉是 天猫店或者2皇冠以上 10分满分
	 * 其他情况2000笔信誉加1分 退款率低于平均就是满分10分 高于平均2倍就是0分 每高出平均的10% 减1分
	 */
	public void calProductScore() {
		score = 0;
		// 计算价格评分
		Double afterQuan2 = null != getAfterQuan() ? getAfterQuan() : 0;
		if (afterQuan2 < 10) {
			score = 30;
		} else if (afterQuan2 < 90) {
			int num = (int) ((afterQuan2 - 10) / 10);
			score = 30 - num * 3;
		}
		// 30
		// 计算佣金评分
		Double rlRate = null != getRealCommissionRate() ? getRealCommissionRate()
				: 0;
		if (rlRate > 90) {
			score += 30;
		} else {
			int num = (int) ((90 - rlRate) / 10);
			score += 30 - num * 3;
		}
		// 21
		// 计算销量评分
		int xlSocre = 10;
		Integer soldQuantityN = null != getSoldQuantity() ? getSoldQuantity()
				: 0;
		if (soldQuantityN > 1000) {
			xlSocre = 10;
		} else {
			xlSocre = soldQuantityN / 100;
		}
		if (xlSocre > 10) {
			xlSocre = 10;
		}
		// 如果评价数目不足销量的1/10 那么销量评分减半
		int comm = null != getCommentCount() ? getCommentCount() : 0;
		if (comm < soldQuantityN / 10) {
			xlSocre = xlSocre / 2;
		}
		score += xlSocre;
		// 10
		// 计算信誉评分
		int sell = null != getSellerScore() ? getSellerScore() : 0;
		if (isMall == 1 || sell >= 50000) {
			score += 10;
		} else {
			score += sell / 2000;
		}

		// 24
		// 退款率
		Double tkl = null != getRefundReason() ? getRefundReason() : 0.0;
		Double avg = null != getAvgRefundRate() ? getAvgRefundRate() : 0.0;
		if (tkl < avg) {
			score += 10;
		} else if (tkl < avg * 2) {
			score += 10 - (int) ((avg - tkl) / (0.1));
		}
		// 描述相符
		Double d1 = null != getMerchandisGapNum() ? getMerchandisGapNum() : 0;
		Double d2 = null != getServiceGapNum() ? getServiceGapNum() : 0;
		Double d3 = null != getConsignmentGapNum() ? getConsignmentGapNum() : 0;
		int num = 0;
		if (d1 > 0) {
			num++;
		}
		if (d2 > 0) {
			num++;
		}
		if (d3 > 0) {
			num++;
		}
		if (num == 3) {
			score += 10;
		} else {
			score += num * 3;
		}
	}

	public String getPcCoupon() {
		return pcCoupon;
	}

	public void setPcCoupon(String pcCoupon) {
		this.pcCoupon = pcCoupon;
	}

	public String getMobileCoupon() {
		return mobileCoupon;
	}

	public void setMobileCoupon(String mobileCoupon) {
		this.mobileCoupon = mobileCoupon;
	}

	public Timestamp getUpdateTime3() {
		return updateTime3;
	}

	public void setUpdateTime3(Timestamp updateTime3) {
		this.updateTime3 = updateTime3;
	}

	/**
	 * 积分=成交金额*积分率*分成 保留两位小数
	 * 
	 * @param integralRate
	 * @param money
	 * @param shareRate
	 * @return
	 */
	public double calIntegral(double integralRate, double money) {
		BigDecimal b1 = new BigDecimal("" + integralRate);
		BigDecimal b2 = new BigDecimal("100");
		BigDecimal b3 = new BigDecimal("" + money);
		return b1.divide(b2).multiply(b3)
				.setScale(2, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	@Override
	public String toString() {
		return getItemTitle();
	}

	public static void main(String[] args) {
		String url = "https://s.click.taobao.com/sDsyApw?pid=mm_0_0_0";
		String orig = url.substring(0, url.indexOf("?"));
		String param = url.substring(url.indexOf("?") + 1);
		String[] strs = param.split("&");
		Map ps = new HashMap();
		for (String str : strs) {
			String[] ss = str.split("=");
			if (ss.length >= 2) {
				ps.put(ss[0], ss[1]);
			}
		}
		System.out.println(ps);
	}
}
