package com.aladingshop.card.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.card.model.MemberCard;

/**
 * @author xinlangtao
 * 
 */
public interface MemberCardDao {

	void deleteById(Long id);

	void add(MemberCard card);

	MemberCard findById(Long id);

	List<MemberCard> findAll();

	void update(MemberCard card);

	Integer getTotal(Map<String, Object> searchParamsMap);

	List<MemberCard> getListPage(Map<String, Object> searchParamsMap);

	List<MemberCard> findByMemberId(Long id);

	List<MemberCard> findAllByMemberId(Long id);

	MemberCard findByMemberIdCardNo(MemberCard card);

	List<MemberCard> findTaskCardList();

	List<MemberCard> findTaskExpire();

	MemberCard findByCardCode(String cardCode);

	/***
	 * 修改赠券金额
	 * 
	 * @param memberCard
	 */
	void updateRebate(MemberCard memberCard);
}