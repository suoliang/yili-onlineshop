package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.member.model.MemberSkuComment;

/***
 * 会员评论
 * 
 * @author xupeijun
 *
 */
public interface MemberSkuCommentDao {

	void add(MemberSkuComment MemberComment);

	void update(MemberSkuComment MemberComment);

	void updateDisable(MemberSkuComment MemberComment);

	void updateStatus(MemberSkuComment MemberComment);

	MemberSkuComment findById(Long id);

	// List<MemberComment> findAll();

	void deleteById(Long id);

	/**
	 * 分页查询
	 * 
	 * @author
	 */
	List<MemberSkuComment> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);

	/***
	 * 通过条件查询会员评论
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<MemberSkuComment> queryByCondition(
			MemberCommentQueryCondition queryCondition);

	/**
	 * 通过条件查询数量
	 * 
	 * @param queryCondition
	 * @return
	 */
	Integer getTotalByCondition(MemberCommentQueryCondition queryCondition);

	/**
	 * 增加点赞
	 */
	void addPraiseCount(Long id);

	/**
	 * 取消点赞
	 */
	void subPraiseCount(Long id);

	/** 定时任务 */
	List<String> findTodayComment();
}
