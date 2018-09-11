package com.yj.dto.tb;

import java.sql.Timestamp;
import java.util.Date;

import com.yj.domain.PaymentRecord;

public class TbkScOrderGetDetail {

	public String createRepeatId() {
		if (!trade_parent_id.equals(trade_id)) {
			System.out.println(trade_parent_id + ":" + trade_id);
			return trade_id;
		}
		return null;
	}

	public PaymentRecord createPayMentRecord(String tbName) {
		PaymentRecord record = new PaymentRecord();
		record.setTbName(tbName);
		record.setExNickName(seller_nick);
		record.setExShopTitle(seller_shop_title);
		record.setCreateTime(create_time);
		record.setEarningTime(earning_time);
		record.setLastUpdate(new Date());
		record.setCreateDate(create_time);
		record.setAuctionId(num_iid);
		record.setAuctionTitle(item_title);
		record.setAuctionUrl("https://detail.tmall.com/item.htm?id=" + num_iid);
		if (null != subsidy_rate) {
			record.setTkShareRate(Double.valueOf(subsidy_rate));
			record.setTk3rdPubShareFee(Double.valueOf(subsidy_rate));
		}
		if (null != alipay_total_price) {
			record.setRealPayFeeString(Double.valueOf(alipay_total_price));
			record.setTotalAlipayFeeString(Double.valueOf(alipay_total_price));
		}
		record.setAuctionNum(item_num);
		if (null != price) {
			record.setPayPrice(Double.valueOf(price));
		}
		record.setTaobaoTradeParentId(trade_parent_id);
		record.setTerminalType(terminal_type);
		if (null != pub_share_pre_fee) {
			record.setFeeString(Double.valueOf(pub_share_pre_fee));
			record.setTkPubShareFeeString(Double.valueOf(pub_share_pre_fee));
		}
		if (null != total_commission_rate) {
			record.setFinalDiscountToString(Double.valueOf(total_commission_rate) * 100);
		}
		record.setPayStatus(tk_status + "");
		record.setPid(adzone_id);
		record.setBizType(terminal_type);
		record.setTkBizTag(terminal_type);
		return record;
	}

	private String terminal_type;// 成交平台，PC:1，无线:2
	private String adzone_id;// 推广位id
	private String alipay_total_price;// 付款金额
	private String price;// 单价
	private String num_iid;// 商品ID
	private double item_num;// 商品数量
	private String seller_shop_title;// 卖家店铺名称
	private String seller_nick;// 卖家昵称
	private String trade_id;// 淘宝订单号
	private String trade_parent_id;// 淘宝父订单编号
	private String auction_category;// 类目名称
	private String item_title;// 商品标题
	private String order_type;// 订单类型，如天猫，淘宝
	private Timestamp create_time;// 创建时间
	private String pub_share_pre_fee;// 效果预估，付款金额*(佣金比率+补贴比率)*分成比率 系统使用的佣金字段
	private String total_commission_rate;// 佣金比率 0.03
	private int tk_status;// 淘客订单状态，3：订单结算，12：订单付款， 13：订单失效，14：订单成功
	private String site_id;// 来源媒体ID(第二段)
	private Timestamp earning_time;// 结算时间
	// 以下数据在结算后才会有
	private String pay_price;// 实际支付金额
	private String total_commission_fee;// 佣金金额
	private String subsidy_rate;// 补贴比例
	private String commission;// 推广者获得的收入金额，对应联盟后台报表“预估收入”
	private String commission_rate;// 推广者获得的分成比率，对应联盟后台报表“分成比率”

	public double getItem_num() {
		return item_num;
	}

	public void setItem_num(double item_num) {
		this.item_num = item_num;
	}

	public Timestamp getEarning_time() {
		return earning_time;
	}

	public void setEarning_time(Timestamp earning_time) {
		this.earning_time = earning_time;
	}

	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}

	public String getAdzone_id() {
		return adzone_id;
	}

	public void setAdzone_id(String adzone_id) {
		this.adzone_id = adzone_id;
	}

	public String getAlipay_total_price() {
		return alipay_total_price;
	}

	public void setAlipay_total_price(String alipay_total_price) {
		this.alipay_total_price = alipay_total_price;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNum_iid() {
		return num_iid;
	}

	public void setNum_iid(String num_iid) {
		this.num_iid = num_iid;
	}

	public String getSeller_shop_title() {
		return seller_shop_title;
	}

	public void setSeller_shop_title(String seller_shop_title) {
		this.seller_shop_title = seller_shop_title;
	}

	public String getSeller_nick() {
		return seller_nick;
	}

	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}

	public String getTrade_id() {
		return trade_id;
	}

	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}

	public String getTrade_parent_id() {
		return trade_parent_id;
	}

	public void setTrade_parent_id(String trade_parent_id) {
		this.trade_parent_id = trade_parent_id;
	}

	public String getAuction_category() {
		return auction_category;
	}

	public void setAuction_category(String auction_category) {
		this.auction_category = auction_category;
	}

	public String getItem_title() {
		return item_title;
	}

	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public String getPub_share_pre_fee() {
		return pub_share_pre_fee;
	}

	public void setPub_share_pre_fee(String pub_share_pre_fee) {
		this.pub_share_pre_fee = pub_share_pre_fee;
	}

	public String getTotal_commission_rate() {
		return total_commission_rate;
	}

	public void setTotal_commission_rate(String total_commission_rate) {
		this.total_commission_rate = total_commission_rate;
	}

	public int getTk_status() {
		return tk_status;
	}

	public void setTk_status(int tk_status) {
		this.tk_status = tk_status;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getPay_price() {
		return pay_price;
	}

	public void setPay_price(String pay_price) {
		this.pay_price = pay_price;
	}

	public String getTotal_commission_fee() {
		return total_commission_fee;
	}

	public void setTotal_commission_fee(String total_commission_fee) {
		this.total_commission_fee = total_commission_fee;
	}

	public String getSubsidy_rate() {
		return subsidy_rate;
	}

	public void setSubsidy_rate(String subsidy_rate) {
		this.subsidy_rate = subsidy_rate;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getCommission_rate() {
		return commission_rate;
	}

	public void setCommission_rate(String commission_rate) {
		this.commission_rate = commission_rate;
	}

}
