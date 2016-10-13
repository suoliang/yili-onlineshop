package com.fushionbaby.app.controller.other;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.member.model.MemberFeedbackInfo;
import com.fushionbaby.member.service.MemberFeedbackInfoService;

/**
 * 
 * @author cyla
 * <p>处理用户意见反馈的功能</p>
 * @version 1.0.0
 */
@Controller
@RequestMapping("/setting")
public class MemberFeedbackContorller {
	/**注入日志*/
	private static final Log LOGGER = LogFactory.getLog(MemberFeedbackContorller.class);
	@Autowired
	private MemberFeedbackInfoService<MemberFeedbackInfo> memberFeedbackInfoService;
	
	/**
	 * <p>保存用户反馈信息</p>
	 * @param sid 用户id
	 * @param content 反馈内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/feedback")
	public Object addFeedbackInfo(
			@RequestParam("sid")String sid, @RequestParam("content")String content,
			@RequestParam("sourceCode")String sourceCode,@RequestParam("contactInformation")String contactInformation) {
		try {
			if (CheckIsEmpty.isEmpty(sid, sourceCode, content)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(sid);
			if (user == null) {
				return Jsonp.noLoginError("请先登录");
			}
			MemberFeedbackInfo memberFeedbackInfo = new MemberFeedbackInfo();
			memberFeedbackInfo.setMemberId(user.getMemberId());
			memberFeedbackInfo.setContent(content);
			memberFeedbackInfo.setSourceCode(sourceCode);
			if (!CheckIsEmpty.isEmpty(contactInformation)) {
				memberFeedbackInfo.setContactInformation(contactInformation);
			}
			memberFeedbackInfoService.add(memberFeedbackInfo);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("MemberFeedbackContorller反馈失败"+e);
			return Jsonp.error("反馈失败");
		}
		return Jsonp.success();
	}
}
