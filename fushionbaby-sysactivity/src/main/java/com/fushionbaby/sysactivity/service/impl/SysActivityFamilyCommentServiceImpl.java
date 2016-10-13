package com.fushionbaby.sysactivity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.condition.SysActivityFamilyCommentQueryCondition;
import com.fushionbaby.sysactivity.dao.SysActivityFamilyCommentDao;
import com.fushionbaby.sysactivity.model.SysActivityFamilyComment;
import com.fushionbaby.sysactivity.service.SysActivityFamilyCommentService;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class SysActivityFamilyCommentServiceImpl implements
		SysActivityFamilyCommentService<SysActivityFamilyComment> {

	@Autowired
	private SysActivityFamilyCommentDao sysActivityFamilyCommentDao;

	public void add(SysActivityFamilyComment sysActivityFamilyComment)
			throws DataAccessException {
		sysActivityFamilyCommentDao.add(sysActivityFamilyComment);
	}

	public void deleteById(Long id) throws DataAccessException {
		sysActivityFamilyCommentDao.deleteById(id);
	}

	public void update(SysActivityFamilyComment sysActivityFamilyComment)
			throws DataAccessException {
		sysActivityFamilyCommentDao.update(sysActivityFamilyComment);
	}

	public SysActivityFamilyComment findById(Long id)
			throws DataAccessException {
		return sysActivityFamilyCommentDao.findById(id);
	}

	public List<SysActivityFamilyComment> findAll() throws DataAccessException {
		return sysActivityFamilyCommentDao.findAll();
	}

	public BasePagination getListPage(
			SysActivityFamilyCommentQueryCondition queryCondition) {
		BasePagination page = new BasePagination();

		Integer total = this.sysActivityFamilyCommentDao
				.getTotal(queryCondition);
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysActivityFamilyComment> list = this.sysActivityFamilyCommentDao
					.queryByCondition(queryCondition);
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysActivityFamilyComment>());
		}
		return page;
	}

	/***
	 * 索亮 APP 亲子课程—评论列表
	 */
	public List<SysActivityFamilyComment> getListPageType(Long article_id,
			int page_index, int page_size) {
		List<SysActivityFamilyComment> list = null;
		try {

			SysActivityFamilyCommentQueryCondition queryCondition = new SysActivityFamilyCommentQueryCondition();
			queryCondition.setArticleId(article_id);
			queryCondition.setStart((page_index - 1) * page_size);
			queryCondition.setLimit(page_size);
			list = sysActivityFamilyCommentDao.queryByCondition(queryCondition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void deleteByArticleId(Long id) {
		this.sysActivityFamilyCommentDao.deleteByArticleId(id);
	}

}
