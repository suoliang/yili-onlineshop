package com.fushionbaby.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberSkuCommentImage;

/**
 * 
 * @author King
 * 
 */
public interface MemberSkuCommentImageService<T extends MemberSkuCommentImage> extends BaseService<T> {


	
	List<MemberSkuCommentImage> findAll();
	/***
	 * 通过评论id得到该评论下的图片
	 * @param commentId
	 * @return
	 */
	List<MemberSkuCommentImage> findByCommentId(Long commentId);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	
	
}

