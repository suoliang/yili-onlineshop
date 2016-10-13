package com.aladingshop.finance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.finance.dao.FinanceRefundRecordDao;
import com.aladingshop.finance.model.FinanceRefundRecord;
import com.aladingshop.finance.service.FinanceRefundRecordService;

/***
 * 退款成功的记录
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日下午5:41:36
 */
@Service
public class FinanceRefundRecordServiceImpl implements FinanceRefundRecordService {

	@Autowired
	private FinanceRefundRecordDao financeRefundRecordDao;
	
	public List<FinanceRefundRecord> findAll() {
		return financeRefundRecordDao.findAll();
	}

	public void add(FinanceRefundRecord financeRefundRecord) {
		this.financeRefundRecordDao.add(financeRefundRecord);
	}

	
}
