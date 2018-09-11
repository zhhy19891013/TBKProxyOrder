package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yj.domain.SystemUserIntegral;

/**
 * 用户积分
 * 
 * @author Administrator
 *
 */
@Mapper
public interface SystemUserIntegralDao {
	/**
	 * 转换阿里积分
	 */
	void updateAliIntegral();

	/**
	 * 京东预估变有效
	 */
	void updateSystemUserIntegralJdRealIntegral();

	/**
	 * 更新京东退款积分
	 */
	void updateSystemUserIntegralJdExpireIntegral();

	/**
	 * 拼多多预估变有效
	 */
	void updateSystemUserIntegralPddRealIntegral();

	/**
	 * 更新拼多多退款积分
	 */
	void updateSystemUserIntegralBckByPdd();

	/**
	 * 淘宝退款的转换为订单退款
	 */
	void updateSystemUserIntegralExpireIntegral();

	/**
	 * 超过15天预估积分转换为有效积分 京东和拼多多的积分不按这个规则
	 */
	void updateSystemUserIntegralRealIntegral();

	/**
	 * 清理下退款单子
	 * 
	 * @param integral
	 */
	void deleteBackOrder(SystemUserIntegral integral);

	/**
	 * 批量新增积分
	 * 
	 * @param records
	 */
	void addSystemUserIntegralBatch(List<SystemUserIntegral> records);

	/**
	 * 查询非代理提交的订单
	 * 
	 * @param orderNumber
	 * @return
	 */
	SystemUserIntegral searchNotCommitIntegral(String orderNumber);

	/**
	 * 根据单号批量删除积分
	 * 
	 * @param records
	 */
	void deleteSystemUserIntegralByOrderNumbers(List<SystemUserIntegral> records);

	/**
	 * 查询所有提交的订单号
	 * 
	 * @return
	 */
	List<SystemUserIntegral> searchAllCommitSystemUserIntegral();

	/**
	 * 删除重复积分
	 * 
	 * @param ids
	 */
	void updtateRepeat(List<String> ids);

	void deleteRepeat(List<String> ids);
}
