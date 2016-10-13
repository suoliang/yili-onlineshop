/**
 * 
 */
package com.fushionbaby.log.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.log.dao.LogWxCardVoucherDao;
import com.fushionbaby.log.model.LogWxCardVoucher;
import com.fushionbaby.log.service.LogWxCardVoucherService;

/**
 * @author mengshaobo
 *
 */
@Service
public class LogWxCardVoucherServiceImpl implements LogWxCardVoucherService<LogWxCardVoucher> {

	@Autowired
	private LogWxCardVoucherDao wxCardVoucherDao;
	/* (non-Javadoc)
	 * @see com.fushionbaby.payment.service.WxCardVoucherService#addCardVoucher(com.fushionbaby.payment.model.WxCardVoucher)
	 */
	public void addCardVoucher(LogWxCardVoucher cardVoucher) {
		wxCardVoucherDao.add(cardVoucher);

	}
	public BasePagination getPageList(BasePagination page) {
		
		
		Integer total = wxCardVoucherDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<LogWxCardVoucher> list = wxCardVoucherDao.getPageList(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<LogWxCardVoucher>());
		}
		return page;
		
	}

}
