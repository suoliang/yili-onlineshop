package com.fushionbaby.web.controller.membercenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.model.MemberSkuCommentReply;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberSkuCommentReplyService;
import com.fushionbaby.member.service.MemberSkuCommentService;

/***
 * 会员评论回复
 * @author xupengfei
 *
 */
@Controller
@RequestMapping("/member")
public class MemberSkuCommentReplyController {

	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;

	/** 会员评论回复*/
	@Autowired
	private MemberSkuCommentReplyService<MemberSkuCommentReply> commentReplyService;
	
	
	@Autowired
	private MemberSkuCommentService<MemberSkuComment> commentService;
	
	private static final Log logg=LogFactory.getLog(MemberSkuCommentReplyController.class);

	

	/***
	 * @param request
	 * @return
	 */
	@RequestMapping("/commentReplySubmit")
	@ResponseBody
	public Object commentReplySubmit(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="commentId",defaultValue="") Long commentId
			
			,@RequestParam(value="isAnonymous",defaultValue="") String isAnonymous
			,@RequestParam(value="replyContent",defaultValue="") String replyContent
			){
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError("用户未登录");
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		if(ObjectUtils.equals(null, user)){
			return Jsonp.noLoginError("用户未登录");
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		Long memberId=userDto.getMember_id();
		
			MemberSkuCommentReply commentReply=new MemberSkuCommentReply();
			commentReply.setCommentId(commentId);
			commentReply.setMemberId(memberId);;
			commentReply.setIsAnonymous(isAnonymous);
			String ipAddress=GetIpAddress.getIpAddr(request); 
			commentReply.setIpAddress(ipAddress);
			commentReply.setMemberName(memberExtraInfoService.findByMemberId(memberId).getNickname());
			commentReply.setReplyContent(replyContent);
			commentReply.setStatus(1);
			commentReply.setSourceCode(SourceConstant.WEB_CODE);
			commentReplyService.add(commentReply);	
			String memberNickname=memberExtraInfoService.findByMemberId(memberId).getNickname();
			return Jsonp_data.success(memberNickname);
	}
	
	/***
	 * @param request
	 * @return
	 */
	@RequestMapping("/commentPraise")
	@ResponseBody
	public Object commentPraise(HttpServletRequest request,@RequestParam(value="commentId",defaultValue="") Long commentId
			,@RequestParam(value="isZaned",defaultValue="") String isZaned){
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			if (StringUtils.isBlank(sid)) {
				return Jsonp.noLoginError("用户未登录");
			}
			UserDto user = (UserDto) SessionCache.get(sid);
			if(ObjectUtils.equals(null, user)){
				return Jsonp.noLoginError("用户未登录");
			}
			if("y".equals(isZaned)){
				commentService.subPraiseCount(commentId);
				return Jsonp_data.success("取消点赞成功");
			}else{
				commentService.addPraiseCount(commentId);
				return Jsonp_data.success("点赞成功");
			}
			
			
		} catch (Exception e) {
			logg.error("点赞失败",e);
			return Jsonp.error();
		}
	}
}			
	
		
		
		
	

	

	
	

