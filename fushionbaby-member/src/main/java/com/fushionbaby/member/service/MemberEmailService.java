package com.fushionbaby.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberEmail;
/**
 * 
 * @author King
 *
 */
public interface MemberEmailService<T extends MemberEmail> extends BaseService<T> {
	/**
	 * 邮箱注册时添加到邮箱表
	 * @param memberId
	 * @param email
	 */
	void addMemberEmail(Long memberId,String email);
	/**
	 * 
	 * @param page
	 *            分页
	 * @return 结果
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	/**重载--查询出不分页的数据*/
	public List<MemberEmail> getListPage(Map<String,Object> map);
	
	void deleteByMemberId(Long memberId);
}
