package com.aladingshop.finance.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.finance.dao.FinanceRefundApplyDao;
import com.aladingshop.finance.model.FinanceRefundApply;
import com.aladingshop.finance.service.FinanceRefundApplyService;
import com.fushionbaby.common.util.BasePagination;

/***
 * 退款申请记录表
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日下午5:42:31
 */
@Service
public class FinanceRefundApplyServiceImpl implements FinanceRefundApplyService{

	@Autowired
	private FinanceRefundApplyDao financeRefundApplyDao;
	
	public List<FinanceRefundApply> findAll() {
		return financeRefundApplyDao.findAll();
	}

	public void add(FinanceRefundApply financeRefundApply) {
		this.financeRefundApplyDao.add(financeRefundApply);
	}

	public BasePagination getListPage(BasePagination page) {
		
		Integer total = this.financeRefundApplyDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<FinanceRefundApply> list = this.financeRefundApplyDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<FinanceRefundApply>());
		}
		return page;
	}

	public FinanceRefundApply findByMemberIdAndOrderCode(Long memberId,	String orderCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("orderCode", orderCode);
		return this.financeRefundApplyDao.findByMemberIdAndOrderCode(map);
	}

	public void updateByMemberIdAndOrderCode(FinanceRefundApply financeRefundApply) {
		this.financeRefundApplyDao.updateByMemberIdAndOrderCode(financeRefundApply);
	}

	
	
}
