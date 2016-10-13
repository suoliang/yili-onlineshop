package com.aladingshop.card.service;

import java.util.List;

import com.aladingshop.card.model.MemberCard;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author xinlangtao
 * 
 */
public interface MemberCardService<T extends MemberCard> extends BaseService<T> {
	List<MemberCard> findAll();

	BasePagination getListPage(BasePagination page);

	/**
	 * 查询某个用户下的可用储值卡信息
	 * 
	 * @param id
	 * @return
	 */
	List<MemberCard> findByMemberId(Long id);

	/**
	 * 查询某个用户下所有储值卡信息
	 * 
	 * @param id
	 * @return
	 */
	List<MemberCard> findAllByMemberId(Long id);

	/**
	 * 根据用户id和储值卡编号查询储值卡信息
	 * 
	 * @param searchParamsMap
	 */
	MemberCard findByMemberIdCardNo(MemberCard card);

	/***
	 * 定时任务处理 ，利息计算（利息部分和赠券）
	 * 
	 * @return
	 */
	List<MemberCard> findTaskCardList();

	/**
	 * 定时任务处理，到期返如意宝(本金)
	 * 
	 * @return
	 */
	List<MemberCard> findTaskExpire();

	MemberCard findByCardCode(String cardCode);

	/***
	 * 修改返券金额
	 * 
	 * @param memberCard
	 */
	void updateRebate(MemberCard memberCard);
}
