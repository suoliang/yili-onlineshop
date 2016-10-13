/**
 * 
 */
package com.fushionbaby.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.SoOrderCombinationDao;
import com.fushionbaby.core.model.SoOrderCombination;
import com.fushionbaby.core.service.SoOrderCombinationService;

/**
 * @author mengshaobo
 *
 */
@Service
public class SoOrderCombinationServiceImpl implements SoOrderCombinationService {

	@Autowired
	private SoOrderCombinationDao soOrderCombinationDao;
	/* (non-Javadoc)
	 * @see com.fushionbaby.order.service.SoOrderCombinationService#add(com.fushionbaby.order.model.SoOrderCombination)
	 */
	public void add(SoOrderCombination soOrderCombination) {
		
		soOrderCombinationDao.add(soOrderCombination);

	}

}
