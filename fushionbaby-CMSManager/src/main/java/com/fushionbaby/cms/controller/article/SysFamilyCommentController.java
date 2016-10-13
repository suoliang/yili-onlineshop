package com.fushionbaby.cms.controller.article;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.condition.SysActivityFamilyCommentQueryCondition;
import com.fushionbaby.sysactivity.model.SysActivityFamily;
import com.fushionbaby.sysactivity.model.SysActivityFamilyComment;
import com.fushionbaby.sysactivity.service.SysActivityFamilyCommentService;
import com.fushionbaby.sysactivity.service.SysActivityFamilyService;

/**
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/article_comment")
public class SysFamilyCommentController {

	@Autowired
	private SysActivityFamilyCommentService<SysActivityFamilyComment> sysArticleCommentService;

	@Autowired
	private SysActivityFamilyService<SysActivityFamily> articleService;

	private static final Log logger = LogFactory
			.getLog(SysFamilyCommentController.class);

	/***
	 * 查询出所有的亲子课程的评论列表
	 * 
	 * @param commentNickname
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("articleCommentList")
	public String findAll(
			@RequestParam(value = "commentNickname", defaultValue = "") String commentNickname,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}

			Integer current = page.getCurrentPage();
			SysActivityFamilyCommentQueryCondition queryCondition = new SysActivityFamilyCommentQueryCondition();
			queryCondition.setCommentNickname(commentNickname);
			queryCondition.setLimit(page.getLimit());
			queryCondition.setStart((page.getCurrentPage() - 1)
					* page.getLimit());

			// 分页对象
			page = this.sysArticleCommentService.getListPage(queryCondition);
			page.setCurrentPage(current);
			// 分页结果
			List<SysActivityFamilyComment> articleCommentList = (List<SysActivityFamilyComment>) page
					.getResult();
			for (SysActivityFamilyComment comment : articleCommentList) {
				SysActivityFamily articleobj = articleService.findById(comment
						.getArticleId());
				if (articleobj.getTitle() != null) {
					comment.setArticleTitle(articleobj.getTitle());
				}

			}

			model.addAttribute("articleCommentList", articleCommentList);
			model.addAttribute("page", page);
			model.addAttribute("commentNickname", commentNickname);

			return "article/article_comment/article_comment_list";
		} catch (Exception e) {
			logger.error("亲子课程评论列表查询失败", e);
			return "";
		}
	}

	@ResponseBody
	@RequestMapping("deleteArticleComment")
	public JsonResponseModel delete(@RequestParam("id") String id) {
		JsonResponseModel jsonMode = new JsonResponseModel();
		try {
			this.sysArticleCommentService.deleteById(Long.valueOf(id));
			jsonMode.Success("亲子课程文章评论删除成功");
		} catch (Exception e) {
			jsonMode.Fail("亲子课程文章评论删除失败");
			logger.error("亲子课程文章评论删除失败", e);
		}
		return jsonMode;
	}

	/**
	 * 
	 */

}
