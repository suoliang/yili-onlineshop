package com.fushionbaby.facade.biz.common.member;

import java.util.List;

import com.fushionbaby.common.dto.MemberAddressDto;

/**
 * 
 * @description  用户地址
 * @author 孟少博
 * @date 2015年8月24日下午7:37:25
 */
public interface MemberAddressFacade {
	/**
	 * 获得地址列表
	 * @param request
	 * @return
	 */
	List<MemberAddressDto> addressList(String sid);
}
