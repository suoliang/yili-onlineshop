package com.fushionbaby.app.controller.login;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

/***
 * 修改密码
 * @author xupeijun
 *
 */
@Controller
@RequestMapping("/user")
public class ChangePasswordController {
	private static final Log LOGGER = LogFactory.getLog(ChangePasswordController.class);
	@Autowired
	private MemberService<Member> memberService;
	/***
	 * 修改密码
	 * @param member_id 用户id
	 * @param password 新密码
	 * @throws UnsupportedEncodingException
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("changePassword")
	public Object changePassword(
			String sid,
			String password) {		
		UserDto userDto= (UserDto)SessionCache.get(sid);
		if(userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		Long memberId = userDto.getMemberId();
		
		if(CheckIsEmpty.isEmpty(password)) {
			return Jsonp.paramError("密码不能为空");
		}
		
		try {
			Member member = new Member();
			member.setId(memberId);
			member.setPassword(MD5Util.MD5(password));
			memberService.update(member);
			return Jsonp.success();
		} catch(DataAccessException e) {
			e.printStackTrace();
			LOGGER.error("ChangePasswordController修改密码失败"+e);
			return Jsonp.error("修改密码失败！");
		}		
	}
}
