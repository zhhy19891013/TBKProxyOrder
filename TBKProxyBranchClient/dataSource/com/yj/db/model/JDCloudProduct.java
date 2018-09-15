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
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.yj.db.model.reuse.BaseProduct;
import com.yj.util.DateUtil;

/**
 * 京东产品模块
 */
public class JDCloudProduct extends BaseProduct {
	private static final long serialVersionUID = 1L;

	//新增字段
	private String content;//文案
	private String wxContent;
	private String skuid;// 商品ID
	private String cateId;// 产品分类ID
	private Double commisionrate;// 佣金比率
	private Double commision;// 佣金
	private double couponmoney;// 优惠券金额 0 说明没有优惠券，当有优惠券的时候，会多个优惠券链接 couponlink
								// 属性
	private Date endtime;// 优惠券活动结束时间
	private String couponlink;// 优惠券链接
	private String freepost;// 是否包邮 0不包邮 1包邮
	private Double oprice;// 原价
	private String pictrue;// 商品图片
	private Double price;// 现价
	private String prodname;// 商品短名称
	private Date starttime;// 优惠券活动开始时间
	private String indexFlag = "new";// 索引标志

	private String cat;// 产品分类显示
	private double jf;// 积分
	// 新增字段
	private String pfullname;// 产品标题
	private long ordernum;// 30天销量
	private String guidearticle;// 推荐理由
	private int sortsid;// 评分
	private int jdself = 0;// 京东自营
	private String promotionpic;// 长图

	// 查询字段
	private Double price2;// 现价
	private Double commision2;// 佣金
	private Double commisionrate2;// 佣金比率
	private Date updateTime2;// 更新时间

	private String clickUrl;// 转换好的链接
	private String updateTimeStr;

	// 计算字段
	private Double relRate;
	private double afterQuan;
	// 发送标记
	private String sendFlag;

	
	public String getWxContent() {
		return wxContent;
	}

	public void setWxContent(String wxContent) {
		this.wxContent = wxContent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPromotionpic() {
		return promotionpic;
	}

	public void setPromotionpic(String promotionpic) {
		this.promotionpic = promotionpic;
	}

	public int getJdself() {
		return jdself;
	}

	public void setJdself(int jdself) {
		this.jdself = jdself;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public double getCouponmoney() {
		return couponmoney;
	}

	public void setCouponmoney(double couponmoney) {
		this.couponmoney = couponmoney;
	}

	public double getAfterQuan() {
		if (couponmoney > 0) {
			BigDecimal b1 = new BigDecimal("" + getCouponmoney());
			BigDecimal b2 = new BigDecimal("" + getPrice());
			afterQuan = b2.subtract(b1).doubleValue();
		}
		return afterQuan;
	}

	public void setAfterQuan(double afterQuan) {
		this.afterQuan = afterQuan;
	}

	public int getSortsid() {
		return sortsid;
	}

	public void setSortsid(int sortsid) {
		this.sortsid = sortsid;
	}

	public String getPfullname() {
		return pfullname;
	}

	public void setPfullname(String pfullname) {
		this.pfullname = pfullname;
	}

	public long getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(long ordernum) {
		this.ordernum = ordernum;
	}

	public String getGuidearticle() {
		if(null==guidearticle){
			guidearticle="";
		}
		return guidearticle;
	}

	public void setGuidearticle(String guidearticle) {
		this.guidearticle = guidearticle;
	}

	public Double getRelRate() {
		BigDecimal b1 = new BigDecimal("" + getCommisionrate());
		BigDecimal b2 = new BigDecimal("100");
		return b1.multiply(b2).setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	public void setRelRate(Double relRate) {
		this.relRate = relRate;
	}

	public String getUpdateTimeStr() {
		if (null != getUpdateTime()) {
			SimpleDateFormat fomat = new SimpleDateFormat("yy-MM-dd  HH:mm");
			updateTimeStr = fomat.format(getUpdateTime());
		}
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public Date getUpdateTime2() {
		return updateTime2;
	}

	public void setUpdateTime2(Date updateTime2) {
		this.updateTime2 = updateTime2;
	}

	public double getJf() {
		return jf;
	}

	public void setJf(double jf) {
		this.jf = jf;
	}

	public String getCat() {
		return cat;
	}

	private String link;// 商品链接

	/********************
	 * lucene相关字段
	 ********************/

	/**
	 * 获取luncene查询语句
	 */
	@Override
	public Query createQuery() {
		BooleanQuery q = new BooleanQuery();
		
		return q;
	}

	@Override
	public Sort createSort() {
		Map<String, Sort> sortMap = new HashMap<String, Sort>();
		boolean reverse = null != getSord()
				&& getSord().equalsIgnoreCase("desc");
		sortMap.put("sortsid", new Sort(new SortField("sortsid", SortField.INT,
				reverse)));
		sortMap.put("updateTime", new Sort(new SortField("updateTime",
				SortField.LONG, reverse)));
		sortMap.put("price", new Sort(new SortField("price", SortField.DOUBLE,
				reverse)));
		sortMap.put("commisionrate", new Sort(new SortField("commisionrate",
				SortField.DOUBLE, reverse)));
		sortMap.put("commision", new Sort(new SortField("commision",
				SortField.DOUBLE, reverse)));
		if (null != getSidx() && null != sortMap.get(getSidx())) {
			return sortMap.get(getSidx());
		}
		return super.createSort();
	}

	/***
	 * lucene相关字段
	 */

	/**
	 * 从luncene加载下内容
	 */
	@Override
	public void loadValueFromLuncene(Document doc) {
		// 新加字段
		if (null != doc.get("jdself")) {
			try {
				setJdself(Integer.valueOf("jdself"));
			} catch (Exception e) {
			}
		}
		if (null != doc.get("promotionpic")) {
			setPromotionpic(doc.get("promotionpic"));
		}
		if (null != doc.get("ordernum")) {
			setOrdernum(Long.valueOf(doc.get("ordernum")));
		}
		if (null != doc.get("pfullname")) {
			setPfullname(doc.get("pfullname"));
		}
		if (null != doc.get("guidearticle")) {
			setGuidearticle(doc.get("guidearticle"));
		}
		if (null != doc.get("sortsid")) {
			setSortsid(Integer.valueOf(doc.get("sortsid")));
		}
		if (null != doc.get("databaseID")
				&& !doc.get("databaseID").equals("null")) {
			setDatabaseID(Long.parseLong(doc.get("databaseID")));
		}
		setCat(doc.get("cat"));
		if (null != doc.get("commisionrate")) {
			setCommisionrate(Double.valueOf(doc.get("commisionrate")));
		}
		if (null != doc.get("commision")) {
			setCommision(Double.valueOf(doc.get("commision")));
		}
		if (null != doc.get("endtime")) {
			setEndtime(new Timestamp(Long.parseLong(doc.get("endtime"))));
		}
		if (null != doc.get("couponmoney")) {
			setCouponmoney(Double.valueOf(doc.get("couponmoney")));
		}
		if (null != doc.get("couponlink")) {
			setCouponlink(doc.get("couponlink"));
		}
		if (null != doc.get("freepost")) {
			setFreepost(doc.get("freepost"));
		}
		if (null != doc.get("pictrue")) {
			setPictrue(doc.get("pictrue"));
		}
		if (null != doc.get("oprice")) {
			setOprice(Double.valueOf(doc.get("oprice")));
		}
		if (null != doc.get("price")) {
			setPrice(Double.valueOf(doc.get("price")));
		}
		if (null != doc.get("prodname")) {
			setProdname(doc.get("prodname"));
		}
		if (null != doc.get("starttime")) {
			setStarttime(new Timestamp(Long.parseLong(doc.get("starttime"))));
		}
		if (null != doc.get("updateTime")) {
			setUpdateTime(new Timestamp(Long.parseLong(doc.get("updateTime"))));
		}
		if (null != doc.get("cat")) {
			setCat(doc.get("cat"));
		}
		if (null != doc.get("skuid")) {
			setSkuid(doc.get("skuid"));
		}
		if (null != doc.get("link")) {
			setLink(doc.get("link"));
		}

	}

	/**
	 * 获取下luncene的字段
	 */
	@Override
	public List<AbstractField> createtLuceneField() {
		List<AbstractField> result = new ArrayList<AbstractField>();
		// 新增字段
		Field itemTitleField1 = new Field("pfullname", getPfullname(),
				Field.Store.YES, Field.Index.ANALYZED);
		result.add(itemTitleField1);
		Field ordernumField = new Field("ordernum", getOrdernum() + "",
				Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		result.add(ordernumField);
		if (null != getGuidearticle()) {
			Field guidearticleField1 = new Field("guidearticle",
					getGuidearticle(), Field.Store.YES, Field.Index.ANALYZED);
			result.add(guidearticleField1);
		}
		Field sortsidField1 = new Field("sortsid", getSortsid() + "",
				Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		result.add(sortsidField1);
		// id
		Field databaseIDField = new Field("databaseID", getDatabaseID() + "",
				Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		result.add(databaseIDField);
		Field skuidField = new Field("skuid", getSkuid(), Field.Store.YES,
				Field.Index.NOT_ANALYZED_NO_NORMS);
		result.add(skuidField);
		Field catField = new Field("cat", getCat(), Field.Store.YES,
				Field.Index.NOT_ANALYZED_NO_NORMS);
		result.add(catField);
		Field linkField = new Field("link", getLink(), Field.Store.YES,
				Field.Index.NOT_ANALYZED_NO_NORMS);
		result.add(linkField);
		// 标题
		Field itemTitleField = new Field("prodname", getProdname(),
				Field.Store.YES, Field.Index.ANALYZED);
		result.add(itemTitleField);
		// 京东自营
		NumericField jdselfField = new NumericField("jdself", Field.Store.YES,
				true).setIntValue(getJdself());
		result.add(jdselfField);
		if (null != getPromotionpic()) {
			Field promotionpicField = new Field("promotionpic",
					getPromotionpic(), Field.Store.YES,
					Field.Index.NOT_ANALYZED_NO_NORMS);
			result.add(promotionpicField);
		}
		// 佣金率
		NumericField discountPriceField = new NumericField("commisionrate",
				Field.Store.YES, true).setDoubleValue(getCommisionrate());
		result.add(discountPriceField);
		// 佣金
		NumericField afterQuanField = new NumericField("commision",
				Field.Store.YES, true).setDoubleValue(getCommision());
		result.add(afterQuanField);
		// 优惠券到期
		NumericField expiredField = new NumericField("endtime",
				Field.Store.YES, true).setLongValue(getEndtime().getTime());
		result.add(expiredField);
		NumericField starttimeField = new NumericField("starttime",
				Field.Store.YES, true).setLongValue(getStarttime().getTime());
		result.add(starttimeField);
		NumericField updateTimeField = new NumericField("updateTime",
				Field.Store.YES, true).setLongValue(getUpdateTime().getTime());
		result.add(updateTimeField);
		Field freepostField = new Field("freepost", getFreepost(),
				Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		result.add(freepostField);
		// 原价
		if (null != getOprice()) {
			NumericField opriceField = new NumericField("oprice",
					Field.Store.YES, true).setDoubleValue(getOprice());
			result.add(opriceField);
		}
		Field pictrueField = new Field("pictrue", getPictrue(),
				Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		result.add(pictrueField);
		NumericField priceField = new NumericField("price", Field.Store.YES,
				true).setDoubleValue(getPrice());
		result.add(priceField);
		if (null != getCouponlink()) {
			Field picUrlField2 = new Field("couponlink", getCouponlink(),
					Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
			result.add(picUrlField2);
		}
		// 优惠券价格
		NumericField quanField = new NumericField("couponmoney",
				Field.Store.YES, true).setDoubleValue(getCouponmoney());
		result.add(quanField);
		return result;
	}

	public Double getPrice2() {
		return price2;
	}

	public void setPrice2(Double price2) {
		this.price2 = price2;
	}

	public Double getCommision2() {
		return commision2;
	}

	public void setCommision2(Double commision2) {
		this.commision2 = commision2;
	}

	public Double getCommisionrate2() {
		return commisionrate2;
	}

	public void setCommisionrate2(Double commisionrate2) {
		this.commisionrate2 = commisionrate2;
	}

	public void setCommision(Double commision) {
		this.commision = commision;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public Double getCommisionrate() {
		return commisionrate;
	}

	public void setCommisionrate(Double commisionrate) {
		this.commisionrate = commisionrate;
	}

	public Double getCommision() {
		return commision;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getCouponlink() {
		return couponlink;
	}

	public void setCouponlink(String couponlink) {
		this.couponlink = couponlink;
	}

	public String getFreepost() {
		return freepost;
	}

	public void setFreepost(String freepost) {
		this.freepost = freepost;
	}

	public Double getOprice() {
		return oprice;
	}

	public void setOprice(Double oprice) {
		this.oprice = oprice;
	}

	public String getPictrue() {
		return pictrue;
	}

	public void setPictrue(String pictrue) {
		this.pictrue = pictrue;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public String getIndexFlag() {
		return indexFlag;
	}

	public void setIndexFlag(String indexFlag) {
		this.indexFlag = indexFlag;
	}


	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getCat(String cateId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("0", "其他");
		map.put("1", "女装");
		map.put("2", "男装");
		map.put("3", "内衣");
		map.put("4", "母婴");
		map.put("5", "儿童");
		map.put("6", "美妆");
		map.put("7", "居家");
		map.put("8", "鞋包配饰");
		map.put("9", "美食");
		map.put("10", "文体车品");
		map.put("11", "数码家电");
		map.put("12", "其他");
		if (null == map.get(cateId)) {
			return "其他";
		}
		return map.get(cateId);
	}

}
