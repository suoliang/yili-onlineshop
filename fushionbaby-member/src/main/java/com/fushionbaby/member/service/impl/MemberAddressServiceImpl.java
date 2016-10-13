package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dao.MemberAddressDao;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.service.MemberAddressService;
/**
 * 
 * @author King
 *
 */
@Service
public class MemberAddressServiceImpl implements MemberAddressService<MemberAddress> {
	private static final Log LOG = LogFactory.getLog(MemberAddressServiceImpl.class);
	@Autowired
	private MemberAddressDao maMemberAddressDao;
	
	public void add(MemberAddress maMemberAddress) throws DataAccessException {
		maMemberAddressDao.add(maMemberAddress);
	}

	public void deleteById(Long id) throws DataAccessException {
		maMemberAddressDao.deleteById(id);
	}
	
	public void delete(MemberAddress maMemberAddress) throws DataAccessException {
		maMemberAddressDao.delete(maMemberAddress);
	}

	public void update(MemberAddress maMemberAddress) throws DataAccessException {
		maMemberAddressDao.update(maMemberAddress);
	}

	public MemberAddress findById(Long id) throws DataAccessException {
		return 	maMemberAddressDao.findById(id);
	}

	public List<MemberAddress> findAll() throws DataAccessException {
		return maMemberAddressDao.findAll();
	}

	public long count(long memberId) throws DataAccessException {
		long count = 0L;
		try {
			  Map<String,Object> map = new HashMap<String,Object>();
			  map.put("memberId", memberId);
			  count = maMemberAddressDao.count(map);
		} catch(Exception e) {
		   LOG.error(e);
		   e.printStackTrace();
		}
		return count;
	}
	
	public List<MemberAddress> getListPage(long memberId,int pageIndex,int pageSize) throws DataAccessException {
		List<MemberAddress> list = null;
		try {
			  Map<String,Object> map = new HashMap<String,Object>();
			  map.put("memberId", memberId);
			  map.put("start", (pageIndex-1)*pageSize);
			  map.put("limit", pageSize);
			  list = maMemberAddressDao.getListPage(map);
		} catch(Exception e) {
		   LOG.error(e);
		   e.printStackTrace();
		}
		return list;
	}
	
	public void delete(Long addressId, Long memberId) {
		MemberAddress memberAddr = new MemberAddress();
		memberAddr.setId(addressId);
		memberAddr.setMemberId(memberId);
		maMemberAddressDao.delete(memberAddr);
	}

	public BasePagination findByContactor(BasePagination page) {
		int total = maMemberAddressDao.getTotal(page.getSearchParamsMap());
		List<MemberAddress> result = new ArrayList<MemberAddress>();
		if(total != 0) {
			result = maMemberAddressDao.getPageList(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);		
		return page;
	}
	
	public List<MemberAddress> getAddressListByMemberId(Long memberId){
		return maMemberAddressDao.getAddressListByMemberId(memberId);
	}

}
