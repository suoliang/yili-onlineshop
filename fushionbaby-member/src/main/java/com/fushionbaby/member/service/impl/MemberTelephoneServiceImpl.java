package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dao.MemberTelephoneDao;
import com.fushionbaby.member.dto.MemberTelephoneDto;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberTelephoneService;

/**
 * 
 * @author King
 * 
 */
@Service
public class MemberTelephoneServiceImpl implements MemberTelephoneService<MemberTelephone> {

	@Autowired
	private MemberTelephoneDao maMemberTelephoneDao;

	public void add(MemberTelephone maMemberTelephone) throws DataAccessException {
		maMemberTelephoneDao.add(maMemberTelephone);
	}

	public void deleteById(Long id) throws DataAccessException {
		maMemberTelephoneDao.deleteById(id);
	}

	public void update(MemberTelephone maMemberTelephone) throws DataAccessException {
		maMemberTelephoneDao.update(maMemberTelephone);
	}

	public MemberTelephone findById(Long id) throws DataAccessException {
		return maMemberTelephoneDao.findById(id);
	}

	public List<MemberTelephone> findAll() throws DataAccessException {
		return maMemberTelephoneDao.findAll();
	}
	/***
	 * app和web公用的添加会员手机信息
	 * @param memberId
	 * @param phone 
	 */
	public void addMemberPhone(Long memberId, String phone) {
		MemberTelephone memberTelephone = new MemberTelephone();
		memberTelephone.setMemberId(memberId);
		memberTelephone.setTelephone(phone);
		maMemberTelephoneDao.add(memberTelephone);
	}

	// 分页
	public BasePagination getListPage(BasePagination page) throws DataAccessException {
		Integer total = maMemberTelephoneDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberTelephone> list = maMemberTelephoneDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberTelephone>());
		}
		return page;
	}
	
	/***根据条件查询返回的数据集合*/
	public List<MemberTelephone> findByCondition(MemberTelephoneDto telephoneDto){
		return maMemberTelephoneDao.findByCondition(telephoneDto);
	}
	
	public void deleteByMemberId(Long memberId){
		maMemberTelephoneDao.deleteByMemberId(memberId);
	}
}
