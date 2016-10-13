package com.fushionbaby.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberSkuComment;

/***
 * 
 * 会员评论
 * 
 * @author xupeijun
 *
 * 
 */
public interface MemberSkuCommentService<T extends MemberSkuComment> extends
		BaseService<T> {

	/**
	 *
	 * @param page
	 *            分页
	 * @return 结果
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	void updateDisable(Long id, String disable);

	void updateStatus(Long id, String status);

	/**
	 * 通过商品号查询商品评论(只显示状态为正常的评论信息)
	 * 
	 *
	 * @param skuId
	 *            商品号
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<MemberSkuComment> queryBySkuUnCode(String skuUnCode,
			Integer pageIndex, Integer pageSize);

	/**
	 * 获得商品评论总数
	 * 
	 * @param skuId
	 *            商品号
	 * @return
	 */
	Integer getPagetotal(String skuUnCode);

	List<MemberSkuComment> queryByCondition(
			MemberCommentQueryCondition queryCondition);

	Integer getTotalByCondition(MemberCommentQueryCondition queryCondition);

	void addPraiseCount(Long commentId);

	void subPraiseCount(Long commentId);

	/***
	 * 我的评价 已评价商品
	 * 
	 * @param condition
	 * @return
	 */
	List<MemberSkuComment> getMyCommentList(
			MemberCommentQueryCondition condition);

	/** 获取当天更新过评论的商品code */
	List<String> getTodayComment();
}
