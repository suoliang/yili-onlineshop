package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dao.MemberSkuCommentReplyDao;
import com.fushionbaby.member.model.MemberSkuCommentReply;
import com.fushionbaby.member.service.MemberSkuCommentReplyService;

@Service
public class MemberSkuCommentReplyServiceImpl implements MemberSkuCommentReplyService<MemberSkuCommentReply> {

	@Autowired
	private MemberSkuCommentReplyDao memberCommentReplyDao;
	
	public void add(MemberSkuCommentReply memberCommentReply) throws DataAccessException {
		memberCommentReplyDao.add(memberCommentReply);
	}

	public void deleteById(Long id) throws DataAccessException {
		memberCommentReplyDao.deleteById(id);
		
	}

	public void update(MemberSkuCommentReply memberCommentReply) throws DataAccessException {
		memberCommentReplyDao.update(memberCommentReply);
		
	}

	public MemberSkuCommentReply findById(Long id) throws DataAccessException {
		return memberCommentReplyDao.findById(id);
	}

	public List<MemberSkuCommentReply> findAll() {
		return memberCommentReplyDao.findAll();
	}
	
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = memberCommentReplyDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberSkuCommentReply> list = memberCommentReplyDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberSkuCommentReply>());
		}
		return page;
	}

	public List<MemberSkuCommentReply> findByCommentId(Long commentId) {
		return memberCommentReplyDao.findByCommentId(commentId);
	}

	



}
