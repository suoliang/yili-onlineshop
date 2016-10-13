package com.aladingshop.finance.service;

import java.util.List;

import com.aladingshop.finance.model.FinanceRefundRecord;

/***
 * 退款成功记录
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日下午5:46:42
 */
public interface FinanceRefundRecordService {

	List<FinanceRefundRecord> findAll();

	void add(FinanceRefundRecord financeRefundRecord);

}
