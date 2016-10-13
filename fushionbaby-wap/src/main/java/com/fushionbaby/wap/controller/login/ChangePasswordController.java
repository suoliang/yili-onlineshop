package com.fushionbaby.wap.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;


/***
 * web页面修改密码
 * @author glc
 * 2014-11-19
 */
@Controller
@RequestMapping("/changePassword")
public class ChangePasswordController {

	@Autowired
	private MemberService<Member> memberService;
	

	@RequestMapping("preChangePassword")
	public String  preChangePassword(HttpServletRequest request){
			return "change-passward";
	}
	
	/***
	 * 
	 * @param oldPwd
	 * @param password
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("confirmChangePassword")
	public Object changePassword(
			@RequestParam(value="oldPwd",defaultValue="")String oldPwd,
			@RequestParam(value="password",defaultValue="") String password,HttpServletRequest request){
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			if (StringUtils.isBlank(sid)) {
				return Jsonp.noLoginError("您还未登录");
			}
			UserDto userDto = (UserDto) SessionCache.get(sid);
			if(ObjectUtils.equals(null, userDto)){
				return Jsonp.noLoginError("您还未登录");
			}
			Long member_id=userDto.getMember_id();
			Member member = memberService.findById(member_id);
			if (member != null) {
				if (!StringUtils.equals(member.getPassword(), MD5Util.MD5(oldPwd))) {
					return Jsonp.error("当前密码输入有误");
				}
				member.setId(member_id);
				member.setPassword(MD5Util.MD5(password));
				memberService.update(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Jsonp.error("密码修改失败");
		}
		return Jsonp.success();
	}
	
}
