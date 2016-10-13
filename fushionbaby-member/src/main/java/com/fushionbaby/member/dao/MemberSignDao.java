package com.fushionbaby.member.dao;

import java.util.List;

import com.fushionbaby.member.model.MemberSign;


public interface MemberSignDao {
	
	public void add(MemberSign memberSign);
	
	public List<MemberSign> findByMemberId(Long memberId);
	
	public void deleteById(Long id);
	
}
