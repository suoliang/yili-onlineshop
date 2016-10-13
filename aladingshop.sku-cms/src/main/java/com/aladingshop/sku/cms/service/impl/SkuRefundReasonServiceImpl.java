package com.aladingshop.sku.cms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuRefundReasonDao;
import com.aladingshop.sku.cms.model.SkuRefundReason;
import com.aladingshop.sku.cms.service.SkuRefundReasonService;

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
