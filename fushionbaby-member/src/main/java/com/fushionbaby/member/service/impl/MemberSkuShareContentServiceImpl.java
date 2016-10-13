/**
 * 
 */
package com.fushionbaby.member.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.member.dao.MemberSkuShareContentDao;
import com.fushionbaby.member.model.MemberSkuShareContent;
import com.fushionbaby.member.service.MemberSkuShareContentService;


/**
 * @author mengshaobo
 * 
 */
@Service
public class MemberSkuShareContentServiceImpl implements MemberSkuShareContentService {

	@Autowired
	private MemberSkuShareContentDao skuShareContentDao;

	public void add(MemberSkuShareContent entity) throws DataAccessException {
		skuShareContentDao.add(entity);

	}


	public void deleteById(Long id) throws DataAccessException {
		skuShareContentDao.deleteById(id);

	}




}
