package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.member.model.MemberSkuCommentReply;
/**
 * 
 * @author King
 *
 */
public interface MemberSkuCommentReplyDao {

	void add(MemberSkuCommentReply memberCommentReply);

	void deleteById(Long id);

	void update(MemberSkuCommentReply memberCommentReply);
	
	MemberSkuCommentReply findById(Long id);
	
	List<MemberSkuCommentReply> findAll();
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List<MemberSkuCommentReply> getListPage(Map<String, Object> map);
	
	/**
	 *  分页查询的总记录数
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);
	
	List<MemberSkuCommentReply> findByCommentId(Long commentId);

}
