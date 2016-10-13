package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dao.MemberSkuCommentImageDao;
import com.fushionbaby.member.model.MemberSkuCommentImage;
import com.fushionbaby.member.service.MemberSkuCommentImageService;

@Service
public class MemberSkuCommentImageServiceImpl implements MemberSkuCommentImageService<MemberSkuCommentImage> {

	@Autowired
	private MemberSkuCommentImageDao memberCommentImageDao;
	
	public void add(MemberSkuCommentImage memberCommentImage) throws DataAccessException {
		memberCommentImageDao.add(memberCommentImage);
	}

	public void deleteById(Long id) throws DataAccessException {
		memberCommentImageDao.deleteById(id);
		
	}

	public void update(MemberSkuCommentImage memberCommentImage) throws DataAccessException {
		memberCommentImageDao.update(memberCommentImage);
		
	}

	public MemberSkuCommentImage findById(Long id) throws DataAccessException {
		return memberCommentImageDao.findById(id);
	}

	public List<MemberSkuCommentImage> findAll() {
		return memberCommentImageDao.findAll();
	}
	
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = memberCommentImageDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberSkuCommentImage> list = memberCommentImageDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberSkuCommentImage>());
		}
		return page;
	}

	public List<MemberSkuCommentImage> findByCommentId(Long commentId) {
		return memberCommentImageDao.findByCommentId(commentId);
	}





}
