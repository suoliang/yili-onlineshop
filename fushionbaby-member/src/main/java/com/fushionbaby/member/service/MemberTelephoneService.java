package com.fushionbaby.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dto.MemberTelephoneDto;
import com.fushionbaby.member.model.MemberTelephone;
/**
 * 
 * @author King
 *
 * @param <T>
 */
public interface MemberTelephoneService<T extends MemberTelephone> extends BaseService<T> {
	
	/***
	 * app和web公用的添加会员手机信息
	 * @param memberId
	 * @param phone
	 */
	void addMemberPhone(Long memberId, String phone);
	
	/**
	 * 
	 * @param page
	 *            分页
	 * @return 结果
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	/**根据条件查询出所有结果数据*/
	public List<MemberTelephone> findByCondition(MemberTelephoneDto telephoneDto);
	
	void deleteByMemberId(Long memberId);
		
}
