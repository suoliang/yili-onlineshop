package com.fushionbaby.app.controller.unwanted.alabao;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.vo.LoginAlabaoDto;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.google.gson.Gson;

/**
 * @description 阿拉宝登录
 * @author 索亮
 * @date 2015年9月9日下午3:20:26
 */
@Controller
@RequestMapping("/alabao")
public class AlabaoLoginController {
	private static final Log LOGGER = LogFactory.getLog(AlabaoLoginController.class);
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private MemberService<Member> memberService;
	/**
	 * 如意宝--登录
	 * @param sid
	 * @param account
	 * @param loginPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("login")
	public Object alabaoLogin(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("登录如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			LoginAlabaoDto loginAlabaoDto = gson.fromJson(data, LoginAlabaoDto.class);
			if (ObjectUtils.equals(SessionCache.get(loginAlabaoDto.getSid()), null)) {
				return Jsonp.noLoginError("请先登录或重登APP账户");
			}
			if (alabaoAccountService.findByAccount(loginAlabaoDto.getAccount()) == null) {
				return Jsonp.error("您输入的用户名和密码不匹配!");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService.loginByAccAndPwd(loginAlabaoDto.getAccount(),MD5Util.MD5(loginAlabaoDto.getLoginPassword()+MD5Util.getPasswordkey()));
			if (ObjectUtils.equals(alabaoAccount, null)) {
				return Jsonp.error("您输入的用户名和密码不匹配!");
			}
			String sid = RandomNumUtil.getCharacterAndNumber(AppConstant.UNIQUE_CODE);
			AlabaoUserDto alabaoUserDto = new AlabaoUserDto();
			alabaoUserDto.setAccount(alabaoAccount.getAccount());
			alabaoUserDto.setAlabaoId(alabaoAccount.getId());
			alabaoUserDto.setMemberId(alabaoAccount.getMemberId());
			alabaoUserDto.setAlabaoSid(sid);
			alabaoUserDto.setMemberName(alabaoAccount.getTrueName());
			AlabaoSessionCache.put(sid, new Gson().toJson(alabaoUserDto));
			BeanNullConverUtil.nullConver(alabaoUserDto);
			return Jsonp_data.success(alabaoUserDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝登录出错", e);
			return Jsonp.error("用户登录出错");
		}
	}
	
}
