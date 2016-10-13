package com.fushionbaby.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sysmgr.dao.SysmgrRefundReasonDao;
import com.fushionbaby.sysmgr.model.SysmgrRefundReason;
import com.fushionbaby.sysmgr.service.SysmgrRefundReasonService;
/**
 * 
 * @author cyla
 *
 */
@Service
public class SysmgrRefundReasonServiceImpl implements SysmgrRefundReasonService {

	@Autowired
	private SysmgrRefundReasonDao sysmgrRefundReasondao;
	
	public List<SysmgrRefundReason> findAll() {
		return sysmgrRefundReasondao.findAll();
	}

}
