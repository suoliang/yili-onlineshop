package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.enums.MemberCommentStatusEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dao.MemberSkuCommentDao;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.service.MemberSkuCommentService;

/**
 * 
 * @author King
 * 
 */
@Service
public class MemberSkuCommentServiceImpl implements
		MemberSkuCommentService<MemberSkuComment> {

	@Autowired
	private MemberSkuCommentDao memberCommentDao;

	private static final Log logg = LogFactory
			.getLog(MemberSkuCommentServiceImpl.class);

	public void add(MemberSkuComment maComment) throws DataAccessException {
		memberCommentDao.add(maComment);
	}

	public void update(MemberSkuComment maComment) throws DataAccessException {
		memberCommentDao.update(maComment);
	}

	public void updateDisable(Long id, String disable)
			throws DataAccessException {
		MemberSkuComment MemberComment = new MemberSkuComment();
		disable = CommonConstant.YES.equals(disable) ? CommonConstant.NO
				: CommonConstant.YES;
		MemberComment.setId(id);
		MemberComment.setDisable(disable);
		memberCommentDao.updateDisable(MemberComment);
	}

	public MemberSkuComment findById(Long id) throws DataAccessException {
		return memberCommentDao.findById(id);
	}

	public void deleteById(Long id) throws DataAccessException {
		memberCommentDao.deleteById(id);
	}

	// 分页
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = memberCommentDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberSkuComment> list = memberCommentDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberSkuComment>());
		}
		return page;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.MemberCommentService#queryBySkuId(java.lang.
	 * Long, java.lang.Integer, java.lang.Integer)
	 */
	public List<MemberSkuComment> queryBySkuUnCode(String skuUnCode,
			Integer pageIndex, Integer pageSize) {

		List<MemberSkuComment> entryList = null;

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("skuUnCode", skuUnCode);
			map.put("status", MemberCommentStatusEnum.PASS.getVlue());
			map.put("disable", CommonConstant.YES);
			if (pageIndex != null && pageSize != null) {
				map.put("start", (pageIndex - 1) * pageSize);
				map.put("limit", pageSize);
			}
			entryList = memberCommentDao.getListPage(map);
		} catch (Exception e) {
			logg.error("通过id查询出错", e);
		}

		return entryList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.MemberCommentService#getPagetotal(java.lang.
	 * Long)
	 */
	public Integer getPagetotal(String skuUnCode) {
		Integer total = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("skuUnCode", skuUnCode);
			total = memberCommentDao.getTotal(map);
		} catch (Exception e) {
			logg.error("查询总数失败", e);
		}
		return total;
	}

	public List<MemberSkuComment> queryByCondition(
			MemberCommentQueryCondition queryCondition) {
		return memberCommentDao.queryByCondition(queryCondition);

	}

	public void addPraiseCount(Long commentId) {
		memberCommentDao.addPraiseCount(commentId);
	}

	public void subPraiseCount(Long commentId) {
		memberCommentDao.subPraiseCount(commentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.member.service.MemberSkuCommentService#updateStatus(java
	 * .lang.Long, java.lang.String)
	 */
	public void updateStatus(Long id, String status) {
		MemberSkuComment MemberComment = new MemberSkuComment();
		status = CommonConstant.YES.equals(status) ? CommonConstant.NO
				: CommonConstant.YES;
		MemberComment.setId(id);
		MemberComment.setStatus(status);
		memberCommentDao.updateStatus(MemberComment);

	}

	public List<MemberSkuComment> getMyCommentList(
			MemberCommentQueryCondition condition) {
		return this.memberCommentDao.queryByCondition(condition);
	}

	public Integer getTotalByCondition(
			MemberCommentQueryCondition queryCondition) {
		return memberCommentDao.getTotalByCondition(queryCondition);
	}

	public List<String> getTodayComment() {
		return memberCommentDao.findTodayComment();
	}

}
