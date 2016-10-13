package com.fushionbaby.member.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberDevice;

public interface MemberDeviceService<T extends MemberDevice> extends
		BaseService<T> {
		
	BasePagination getListPage(BasePagination page);
	
	List<MemberDevice> findByMac(String mac);
	
	String getMemDevRegisterInfo(String mac);
}
