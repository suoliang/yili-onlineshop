package com.aladingshop.wap.controller.ruyibao;

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
import org.springframework.web.servlet.ModelAndView;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.google.gson.Gson;

/**
 * @description 阿拉宝登录
 * @author 索亮
 * @date 2015年9月9日下午3:20:26
 */
@Controller
@RequestMapping("/ruyibao")
public class AlabaoLoginController {
	private static final Log LOGGER = LogFactory
			.getLog(AlabaoLoginController.class);

	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private MemberService<Member> memberService;

	/***
	 * 首页点击如意消费卡请求
	 * 
	 * @return
	 */
	@RequestMapping("/home")
	public ModelAndView homeRequest(HttpServletRequest request) {
		/** 校验是否已登录如意宝 */
		String alabaoSid = CookieUtil.getCookieValue(request,
				CookieConstant.ALABAO_SID);
		if (StringUtils.isBlank(alabaoSid)) {
			return new ModelAndView("redirect:/ruyibao/welcome");
		}

		return new ModelAndView("redirect:/ruyibao/mainShow");
	}

	/***
	 * 跳转到如意宝首页
	 * 
	 * @return
	 */
	@RequestMapping("/welcome")
	public String welcomePage() {
		return "ruyibao/spe-ruyi";
	}

	/***
	 * 跳转到登录页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "ruyibao/spe-ruyi-login";
	}

	/**
	 * 如意宝--登录
	 * 
	 * @param sid
	 * @param account
	 * @param loginPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("login")
	public Object alabaoLogin(@RequestParam("acount") String acount,
			@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String sid = CookieUtil.getCookieValue(request,
					CookieConstant.COOKIE_SID);
			if (ObjectUtils.equals(SessionCache.get(sid), null)) {
				return Jsonp.noLoginError("请先登录或重登阿拉丁账户");
			}
			if (alabaoAccountService.findByAccount(acount) == null) {
				return Jsonp.error("您输入的用户名或密码错误!");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService
					.loginByAccAndPwd(acount,
							MD5Util.MD5(password + MD5Util.getPasswordkey()));
			if (ObjectUtils.equals(alabaoAccount, null)) {
				return Jsonp.error("您输入的用户名或密码错误!");
			}

			String alabaoSid = RandomNumUtil
					.getCharacterAndNumber(AppConstant.UNIQUE_CODE);
			AlabaoUserDto alabaoUserDto = new AlabaoUserDto();
			alabaoUserDto.setAccount(alabaoAccount.getAccount());
			alabaoUserDto.setAlabaoId(alabaoAccount.getId());
			alabaoUserDto.setMemberId(alabaoAccount.getMemberId());
			alabaoUserDto.setAlabaoSid(alabaoSid);
			Member member = memberService.findById(alabaoAccount.getMemberId());
			String memberName = "";
			if (ObjectUtils.notEqual(member, null)) {
				memberName = member.getLoginName();
			}
			alabaoUserDto.setMemberName(memberName);
			CookieUtil.setCookie(response, CookieConstant.ALABAO_SID,
					alabaoSid, CookieConstant.COOKIE_LOGIN_AGE);
			AlabaoSessionCache.put(alabaoSid, new Gson().toJson(alabaoUserDto));
			BeanNullConverUtil.nullConver(alabaoUserDto);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝登录出错", e);
			return Jsonp.error("用户登录出错");
		}
	}

	/*** 点击注册时跳转到注册页面 */
	@RequestMapping("/toRuyiRegister")
	public String toRegister() {
		return "ruyibao/spe-ruyi-registering";
	}

}
