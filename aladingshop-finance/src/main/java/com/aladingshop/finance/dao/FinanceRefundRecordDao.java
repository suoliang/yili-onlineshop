package com.aladingshop.finance.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.finance.model.FinanceRefundRecord;

/***
 * 退款成功的记录
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日下午5:41:36
 */
public interface FinanceRefundRecordDao {

	void add(FinanceRefundRecord financeRefundRecord);
	List<FinanceRefundRecord> findAll();
	Integer getTotal(Map<String, Object> map);
}
