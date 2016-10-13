package com.aladingshop.finance.service;

import java.util.List;

import com.aladingshop.finance.model.FinanceRefundApply;
import com.fushionbaby.common.util.BasePagination;

/***
 * 退款申请记录表
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日下午5:42:31
 */
public interface FinanceRefundApplyService {

	List<FinanceRefundApply> findAll();

	void add(FinanceRefundApply financeRefundApply);

	BasePagination getListPage(BasePagination page);

	/***
	 * 通过memberId和orderCode查询
	 * @param memberId
	 * @param orderCode
	 * @return
	 */
	FinanceRefundApply findByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	void updateByMemberIdAndOrderCode(FinanceRefundApply financeRefundApply);

}
