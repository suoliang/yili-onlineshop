package com.fushionbaby.member.service;

import java.util.List;

import com.fushionbaby.member.model.MemberSign;


public interface MemberSignService {
	
	public void add(MemberSign memberSign);
	
	public void deleteById(Long id);
	
	public List<MemberSign> findByMemberId(Long memberId);
	
	public MemberSign findLastMemberSignByMemberId(Long memberId);
}
