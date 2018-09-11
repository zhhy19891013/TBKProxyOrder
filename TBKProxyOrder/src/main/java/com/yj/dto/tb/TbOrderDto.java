package com.yj.dto.tb;

import java.util.List;

import com.yj.domain.PaymentRecord;

/**
 * 淘宝订单转换类
 * 
 * @author Administrator
 *
 */
public class TbOrderDto {
	private int hasMore = 0;// 1=还有 0=没有了
	private List<PaymentRecord> records;// 订单记录
	private List<String> ids;
	
	
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public int getHasMore() {
		return hasMore;
	}

	public void setHasMore(int hasMore) {
		this.hasMore = hasMore;
	}

	public List<PaymentRecord> getRecords() {
		return records;
	}

	public void setRecords(List<PaymentRecord> records) {
		this.records = records;
	}

}
