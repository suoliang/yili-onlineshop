/**
 * 
 */
package com.aladingshop.refund.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.refund.dao.RefundDao;
import com.aladingshop.refund.model.Refund;
import com.aladingshop.refund.service.RefundService;
import com.fushionbaby.common.util.BasePagination;


/**
 * @description 类描述...
 * @author 徐鹏飞
 * @date 2015年8月18日下午3:01:23
 */
@Service
@Transactional
public class RefundServiceImpl implements RefundService{
	@Autowired
	private RefundDao refundDao;

	public void add(Refund refund) {
		refundDao.add(refund);
		
	}

	public void updateByBatchNo(Refund refund) {
		refundDao.updateByBatchNo(refund);
		
	}

	public void deleteById(Long id) {
		refundDao.deleteById(id);
		
	}
	
	public Refund findByBatchNo(String batchNo) {
		return refundDao.findByBatchNo(batchNo);
	}

	public Refund findByMemberIdAndOrderCode(Long memberId, String orderCode) {
		return refundDao.findByMemberIdAndOrderCode(memberId, orderCode);
	}

	public void updateByMemberIdAndOrderCode(Refund refund) {
		refundDao.updateByMemberIdAndOrderCode(refund);
		
	}
	
	public BasePagination getListPage(BasePagination page) {
		int total = refundDao.getTotal(page.getSearchParamsMap());
		List<Refund> result = new ArrayList<Refund>();
		if(total != 0) {
			result = refundDao.getListPage(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);		
		return page;
	}

	public int getTotal(Map<String, Object> searchParamsMap) {
		return refundDao.getTotal(searchParamsMap);
	}

	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		refundDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
	}
	
}
