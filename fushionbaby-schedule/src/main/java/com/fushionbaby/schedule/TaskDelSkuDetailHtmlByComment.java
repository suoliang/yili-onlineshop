/**
 * 
 */
package com.fushionbaby.schedule;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.service.MemberSkuCommentService;

/**
 * 根据更新的评论删除
 * 
 * @author suntao
 *
 */
@Service
public class TaskDelSkuDetailHtmlByComment {
	private static boolean runFlag = false;
	private static final Log LOGGER = LogFactory
			.getLog(TaskDelSkuDetailHtmlByComment.class);

	/** 评论接口 */
	@Autowired
	private MemberSkuCommentService<MemberSkuComment> memberSkuCommentService;

	public void excute() {
		if (runFlag) {
			LOGGER.info("TaskDelSkuDetailHtmlByComment正在运行");
			return;
		}
		runFlag = true;

		LOGGER.info("TaskDelSkuDetailHtmlByComment开始运行");
		run();
		LOGGER.info("TaskDelSkuDetailHtmlByComment运行结束");
		runFlag = false;
	}

	@Transactional
	private void run() {
		try {
			List<String> codes = memberSkuCommentService.getTodayComment();
			/** 获取当天更新的评论相关的商品code集合 */
			String url = "http://www.aladingshop.com/webtest/delSkuDetailStatic";
			for (String code : codes) {
				HttpRequest.sendGet(url, "skuCode=" + code);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(
					"定时任务项目中：TaskDelSkuDetailHtmlByComment.java删除有评论更新的商品详情静态页异常",
					e);
		}
	}
}
