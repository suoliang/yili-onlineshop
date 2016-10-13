package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dao.MemberEmailDao;
import com.fushionbaby.member.model.MemberEmail;
import com.fushionbaby.member.service.MemberEmailService;

@Service
public class MemberEmailServiceImpl implements MemberEmailService<MemberEmail> {

	@Autowired
	private MemberEmailDao maMemberEmailDao;
	
	public void add(MemberEmail maMemberEmail) throws DataAccessException {
		maMemberEmailDao.add(maMemberEmail);
	}

	public void deleteById(Long id) throws DataAccessException {
		maMemberEmailDao.deleteById(id);
	}

	public void update(MemberEmail maMemberEmail) throws DataAccessException {
		maMemberEmailDao.update(maMemberEmail);
	}

	public MemberEmail findById(Long id) throws DataAccessException {
		return maMemberEmailDao.findById(id);
	}

	public List<MemberEmail> findAll() throws DataAccessException {
		return maMemberEmailDao.findAll();
	}
	
	/**
	 * 邮箱注册时添加到邮箱表
	 * @param memberId
	 * @param email
	 */
	public void addMemberEmail(Long memberId,String email) {
		MemberEmail memberEmail = new MemberEmail();
		memberEmail.setMemberId(memberId);
		memberEmail.setEmail(email);
		maMemberEmailDao.add(memberEmail);
	}
	
	// 分页
	public BasePagination getListPage(BasePagination page) throws DataAccessException {
		Integer total = maMemberEmailDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberEmail> list = maMemberEmailDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberEmail>());
		}
		return page;
	}
	
	/**
	 * 重载--查询出不分页的数据
	 * @param map
	 * @return
	 */
	public List<MemberEmail> getListPage(Map<String,Object> map){
		return maMemberEmailDao.getListPage(map);
	}
	
	public void deleteByMemberId(Long memberId){
		maMemberEmailDao.deleteByMemberId(memberId);
	}
}
