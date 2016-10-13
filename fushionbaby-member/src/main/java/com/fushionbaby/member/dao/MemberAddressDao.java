package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.member.model.MemberAddress;
/**
 * 
 * @author King
 *
 */
public interface MemberAddressDao {

	public void add(MemberAddress maMemberAddress);
    
    public void deleteById(Long id);
    
    public void delete(MemberAddress maMemberAddress);

    public void update(MemberAddress maMemberAddress);

    public MemberAddress findById(Long id);
    
    public List<MemberAddress> findAll();

    public long count(Map<String,Object> map);
 	
	public List<MemberAddress> getListPage(Map<String,Object> map);

	 int getTotal(Map<String, Object> searchParamsMap);

	 List<MemberAddress> getPageList(Map<String, Object> searchParamsMap);

	 public List<MemberAddress> getAddressListByMemberId(@Param("memberId") Long memberId);
	
}