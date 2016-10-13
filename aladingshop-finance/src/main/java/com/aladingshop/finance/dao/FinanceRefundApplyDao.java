package com.aladingshop.finance.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.finance.model.FinanceRefundApply;

/***
 * 退款申请记录表
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日下午5:42:31
 */
public interface FinanceRefundApplyDao {
	void add(FinanceRefundApply financeRefundApply);
	void update(FinanceRefundApply financeRefundApply);
	List<FinanceRefundApply> findAll();
	Integer getTotal(Map<String, Object> map);
	List<FinanceRefundApply> getListPage(Map<String, Object> searchParamsMap);
	/***
	 * 查询 退款记录
	 * @param map
	 * @return
	 */
	FinanceRefundApply findByMemberIdAndOrderCode(Map<String, Object> map);
	void updateByMemberIdAndOrderCode(FinanceRefundApply financeRefundApply);
}
