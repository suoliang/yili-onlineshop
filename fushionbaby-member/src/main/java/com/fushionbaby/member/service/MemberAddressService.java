package com.fushionbaby.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberAddress;

/**
 * 
 * @author King
 *
 */
public interface MemberAddressService<T extends MemberAddress> extends
		BaseService<T> {

	public void delete(MemberAddress maMemberAddress)
			throws DataAccessException;

	/**
	 * 查询总记录数
	 * 
	 * @param member_id
	 * @return
	 * @throws DataAccessException
	 */
	public long count(long memberId) throws DataAccessException;

	/**
	 * 分页获取列表
	 * 
	 * @param member_id
	 * @param page_index
	 * @param page_size
	 * @return
	 * @throws DataAccessException
	 */
	public List<MemberAddress> getListPage(long memberId, int pageIndex,
			int pageSize) throws DataAccessException;
	/**
	 * 根据会员地址id和会员id删除
	 * @param addressId
	 * @param memberId
	 */
	void delete(Long addressId, Long memberId);

	BasePagination findByContactor(BasePagination page);

	/**
	 * 根据会员id查询该会员的收货地址
	 * 
	 * @param memberId
	 * @return
	 */
	public List<MemberAddress> getAddressListByMemberId(Long memberId);

}
