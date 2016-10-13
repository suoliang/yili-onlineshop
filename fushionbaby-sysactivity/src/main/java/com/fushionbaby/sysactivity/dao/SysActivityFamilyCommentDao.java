package com.fushionbaby.sysactivity.dao;

import java.util.List;

import com.fushionbaby.sysactivity.condition.SysActivityFamilyCommentQueryCondition;
import com.fushionbaby.sysactivity.model.SysActivityFamilyComment;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysActivityFamilyCommentDao {

	void add(SysActivityFamilyComment sysActivityFamilyComment);

	void deleteById(Long id);

	void update(SysActivityFamilyComment sysActivityFamilyComment);

	SysActivityFamilyComment findById(Long id);

	List<SysActivityFamilyComment> findAll();

	/***
	 * 通过文章的id删除 相关评论
	 * 
	 * @param id
	 */
	void deleteByArticleId(Long id);

	/**
	 * 通过条件查询评论
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<SysActivityFamilyComment> queryByCondition(
			SysActivityFamilyCommentQueryCondition queryCondition);

	/**
	 * 条件查询评论条数
	 * 
	 * @param queryCondition
	 * @return
	 */
	Integer getTotal(SysActivityFamilyCommentQueryCondition queryCondition);

}