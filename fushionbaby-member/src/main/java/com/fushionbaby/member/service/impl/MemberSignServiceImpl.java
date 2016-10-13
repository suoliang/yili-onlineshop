package com.fushionbaby.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.member.dao.MemberSignDao;
import com.fushionbaby.member.model.MemberSign;
import com.fushionbaby.member.service.MemberSignService;


@Service
public class MemberSignServiceImpl implements MemberSignService{
	@Autowired
	private MemberSignDao memberSignDao;
	
	public void add(MemberSign memberSign) {
		memberSignDao.add(memberSign);		
	}

	public List<MemberSign> findByMemberId(Long memberId) {
		return memberSignDao.findByMemberId(memberId);
	}

	public MemberSign findLastMemberSignByMemberId(Long memberId) {
		return memberSignDao.findByMemberId(memberId).get(0);
	}

	public void deleteById(Long id) {
		memberSignDao.deleteById(id);		
	}
	
}
