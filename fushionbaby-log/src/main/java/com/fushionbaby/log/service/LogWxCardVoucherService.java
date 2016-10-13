/**
 * 
 */
package com.fushionbaby.log.service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.log.model.LogWxCardVoucher;

/**
 * @author mengshaobo
 *
 */
public interface LogWxCardVoucherService<T extends LogWxCardVoucher> {
	void addCardVoucher(LogWxCardVoucher cardVoucher);
	
	BasePagination getPageList(BasePagination page);
}
