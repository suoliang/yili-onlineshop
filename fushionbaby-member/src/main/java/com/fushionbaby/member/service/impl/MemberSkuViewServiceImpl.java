package com.fushionbaby.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.member.dao.MemberSkuViewDao;
import com.fushionbaby.member.model.MemberSkuView;
import com.fushionbaby.member.service.MemberSkuViewService;
/**
 * 
 * @author King suoliang
 *
 */
@Service
public class MemberSkuViewServiceImpl implements MemberSkuViewService<MemberSkuView> {

	@Autowired
	private MemberSkuViewDao skuViewDao;
	
	public void add(MemberSkuView skuView) throws DataAccessException {
		skuViewDao.add(skuView);
	}

	public void deleteById(Long id) throws DataAccessException {
		skuViewDao.deleteById(id);
	}

	public void update(MemberSkuView skuView) throws DataAccessException {
		skuViewDao.update(skuView);
	}

	public MemberSkuView findById(Long id) throws DataAccessException {
		return skuViewDao.findById(id);
	}

	public List<MemberSkuView> findAll() throws DataAccessException {
		return skuViewDao.findAll();
	}

}
