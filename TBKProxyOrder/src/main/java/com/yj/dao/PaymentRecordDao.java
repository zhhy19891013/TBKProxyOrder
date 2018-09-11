package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.PaymentRecord;

/**
 * 淘宝订单数据库处理
 * 
 * @author Administrator
 *
 */
@Mapper
public interface PaymentRecordDao {

	List<PaymentRecord> searchTest();

	/**
	 * 查询还没有计算积分的订单
	 * 
	 * @param param
	 * @return
	 */
	List<PaymentRecord> searchAllNotCalPayment(PaymentRecord param);

	/**
	 * 根据单号找到相关的订单
	 * 
	 * @param id
	 * @return
	 */
	List<PaymentRecord> searchPaymentBytaobaoTradeParentId(String id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	void deletePaymentRecordByIds(List<PaymentRecord> ids);

	/**
	 * 批量新增
	 * 
	 * @param ids
	 */
	void addPayementRecord(List<PaymentRecord> ids);

	/**
	 * 更新下订单的username 根据积分表
	 * 
	 * @param ids
	 */
	void updatePaymentRecordUserNameByIntegralAndOrderNumber(List<PaymentRecord> ids);

	/**
	 * 更新下订单的username 根据积分表
	 * 
	 */
	void updatePaymentRecordUserNameByIntegral();

	void deleteRepeatPay(List<String> ids);
}
