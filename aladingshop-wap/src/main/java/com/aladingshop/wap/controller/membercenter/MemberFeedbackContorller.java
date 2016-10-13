package com.aladingshop.wap.controller.membercenter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CookieUtil;
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
@RequestMapping("/member")
public class MemberFeedbackContorller {
	/**注入日志*/
	private static final Log LOGGER = LogFactory.getLog(MemberFeedbackContorller.class);
	@Autowired
	private MemberFeedbackInfoService<MemberFeedbackInfo> memberFeedbackInfoService;
	
	
	@RequestMapping("toFeedback")
	public String toAddAddress(){
		return "membercenter/suggest";
	}
	/**
	 * <p>保存用户反馈信息</p>
	 * @param sid 用户id
	 * @param content 反馈内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/feedback")
	public Object addFeedbackInfo(HttpServletRequest request,Model model,
			@RequestParam("content")String content,
			@RequestParam("contactInformation")String contactInformation) {
		try {
			String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user == null || user.getMemberId() == null){
				return Jsonp.noLoginError("请先登录");
			}
			MemberFeedbackInfo memberFeedbackInfo = new MemberFeedbackInfo();
			memberFeedbackInfo.setMemberId(user.getMemberId());
			memberFeedbackInfo.setContent(content);
			memberFeedbackInfo.setSourceCode(SourceConstant.WAP_CODE);
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
