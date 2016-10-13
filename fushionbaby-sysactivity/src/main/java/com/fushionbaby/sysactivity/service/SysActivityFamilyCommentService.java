package com.fushionbaby.sysactivity.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.condition.SysActivityFamilyCommentQueryCondition;
import com.fushionbaby.sysactivity.model.SysActivityFamilyComment;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysActivityFamilyCommentService<T extends SysActivityFamilyComment>
		extends BaseService<T> {
	List<SysActivityFamilyComment> findAll();
	/**
	 * 条件分页查询
	 * 
	 * @param queryCondition
	 * @return
	 */
	BasePagination getListPage(
			SysActivityFamilyCommentQueryCondition queryCondition);

	/***
	 * 索亮 APP 亲子课程 - 评论列表
	 * 
	 * @param map
	 * @return
	 */
	List<SysActivityFamilyComment> getListPageType(Long article_id,
			int page_index, int page_size);

	/***
	 * 通过文章的id删除相关的评论
	 * 
	 * @param id
	 */
	void deleteByArticleId(Long id);

}
