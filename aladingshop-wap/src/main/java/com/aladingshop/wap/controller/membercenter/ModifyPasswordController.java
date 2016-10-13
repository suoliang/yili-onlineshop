package com.aladingshop.wap.controller.membercenter;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

/***
 * 修改密码
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/member")
public class ModifyPasswordController {
	/**注入日志*/
	private  static final Log LOGGER = LogFactory.getLog(ModifyPasswordController.class);
	/**注入会员*/
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private UserFacade userFacade;	
	
	/**
	 * 跳转到重置密码页面
	 * @return
	 */
	@RequestMapping("toModifyPassword")
	public String toModifyPassword(){
		return "membercenter/modify-password";
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("modifyPassword")
	public Object modifyPassword(HttpServletRequest request,@RequestParam(value ="password",defaultValue ="")String password){
		try{	
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = userFacade.getLatestUserBySid(sid);
			if(user == null || user.getMemberId() == null){
				return Jsonp.noLoginError("请先登录");
			}
			Member member=new Member();
			member.setId(user.getMemberId());
			member.setPassword(MD5Util.MD5(password));
			memberService.update(member);
		} catch (DataAccessException e) {
			e.printStackTrace();
			LOGGER.error("modifyPasswordController修改密码-设置新密码有误" + e);
			return Jsonp.error();
		}
		return Jsonp.success();
	}
}
