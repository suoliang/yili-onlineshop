package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.member.model.MemberDevice;

public interface MemberDeviceDao {

	void add(MemberDevice memberDevice);
	
	void update(MemberDevice memberDevice);
	
	MemberDevice findById(Long id);
	
	void deleteById(Long id);
	
	Integer getTotal(Map<String, Object> searchParamsMap);
	
	List<MemberDevice> getPageList(Map<String, Object> searchParamsMap);

	List<MemberDevice> findByMac(String mac);
}
