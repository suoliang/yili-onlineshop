package com.fushionbaby.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.member.dao.MemberExtraInfoDao;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.service.MemberExtraInfoService;
/**
 * 
 * @author King
 *
 */
@Service
public class MemberExtraInfoServiceImpl implements MemberExtraInfoService<MemberExtraInfo> {

	@Autowired
	private MemberExtraInfoDao maMemberInfoDao;
	
	public void add(MemberExtraInfo maMemberInfo) throws DataAccessException {
		maMemberInfoDao.add(maMemberInfo);
	}

	public void deleteById(Long id) throws DataAccessException {
		maMemberInfoDao.deleteById(id);
	}

	public void update(MemberExtraInfo maMemberInfo) throws DataAccessException {
		maMemberInfoDao.update(maMemberInfo);
	}
	
	/***
	 * 通过会员ID修改额外信息
	 * @param maMemberInfo
	 * @throws DataAccessException
	 */
	public void updateByMemberId(MemberExtraInfo maMemberInfo) throws DataAccessException {
		maMemberInfoDao.updateByMemberId(maMemberInfo);
	}

	public MemberExtraInfo findById(Long id) throws DataAccessException {
		return maMemberInfoDao.findById(id);
	}
	
	/***
	 * 通过会员id拿到额外信息
	 */
	public MemberExtraInfo findByMemberId(Long member_id) throws DataAccessException {
		return maMemberInfoDao.findByMemberId(member_id);
	}
	public List<MemberExtraInfo> findAll() throws DataAccessException {
		return maMemberInfoDao.findAll();
	}
	/***
	 * app和web公用的用户注册时的额外信息添加
	 * @param memberId
	 */
	public void addMemberExtraInfo(Long memberId) {
		MemberExtraInfo memberExtraInfo = new MemberExtraInfo();
		memberExtraInfo.setMemberId(memberId);// 额外会员表添加一个id
		maMemberInfoDao.add(memberExtraInfo);
	}

	public void deleteByMemberId(Long memberId){
		maMemberInfoDao.deleteByMemberId(memberId);
	}
}
