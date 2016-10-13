package com.fushionbaby.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberSkuCommentReply;

/**
 * 
 * @author King
 * 
 */
public interface MemberSkuCommentReplyService<T extends MemberSkuCommentReply> extends BaseService<T> {

	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	
	List<MemberSkuCommentReply> findByCommentId(Long commentId);
}

