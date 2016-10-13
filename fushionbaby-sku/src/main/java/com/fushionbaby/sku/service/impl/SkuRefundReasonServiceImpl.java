package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuRefundReasonDao;
import com.fushionbaby.sku.model.SkuRefundReason;
import com.fushionbaby.sku.service.SkuRefundReasonService;

/**
 * 
 * @author cyla
 *
 */
@Service
public class SkuRefundReasonServiceImpl implements SkuRefundReasonService {

	private SkuRefundReasonDao skuRefundReasonDao;
	public List<SkuRefundReason> findAll() {
		return skuRefundReasonDao.findAll();
	}

}
