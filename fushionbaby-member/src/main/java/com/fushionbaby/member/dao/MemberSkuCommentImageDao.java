package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.member.model.MemberSkuCommentImage;
/**
 * 
 * @author King
 *
 */
public interface MemberSkuCommentImageDao {

	void add(MemberSkuCommentImage memberCommentImage);

	void deleteById(Long id);

	void update(MemberSkuCommentImage memberCommentImage);
	
	MemberSkuCommentImage findById(Long id);
	
	List<MemberSkuCommentImage> findAll();
	
	List<MemberSkuCommentImage> findByCommentId(Long commentId);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List<MemberSkuCommentImage> getListPage(Map<String, Object> map);
	
	/**
	 *  分页查询的总记录数
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);

}
