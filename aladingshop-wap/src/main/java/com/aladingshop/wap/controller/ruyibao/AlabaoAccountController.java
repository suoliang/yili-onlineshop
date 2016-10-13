package com.aladingshop.wap.controller.ruyibao;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.Cookie;
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

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;

/**
 * @description 阿拉宝账户信息
 * @author 索亮
 * @date 2015年9月9日上午11:54:15
 */
@Controller
@RequestMapping("/ruyibao")
public class AlabaoAccountController {
	private static final Log LOGGER = LogFactory.getLog(AlabaoAccountController.class);
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private SmsService<Sms> smsService;
	
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> shoppingCartSkuUserService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;

	
	/***
	 * 如意宝-注册--请求短信验证码
	 * @param userPhone
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAlabaoSmsCode")
	public Object registerGetSmsCode(
			@RequestParam(value="userPhone",defaultValue="")String userPhone,
			HttpServletRequest request){
		try {
			//拿到cookie，判断用户是否进行注册操作
			String registerCode = CookieUtil.getCookieValue(request, CookieConstant.REGISTER_COOKIE);
			if (ObjectUtils.equals(registerCode, null)) {
				return Jsonp.error("请返回登录页重新注册");
			}
			if (CheckIsEmpty.isEmpty(userPhone)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto userDto = (UserDto) SessionCache.get(sid);
			if(ObjectUtils.equals(null, userDto)){
				return Jsonp.noLoginError("请先登录或重登阿拉丁");
			}
			/**此处限制一个APP用户只能绑一个账户*/
			if (ObjectUtils.notEqual(alabaoAccountService.findByMemberId(userDto.getMemberId()), null)) {
				return Jsonp.error("此用户已绑定");
			}
			if (ObjectUtils.notEqual(alabaoAccountService.findByAccount(userPhone), null)) {
				return Jsonp.error("此手机号已注册,您可直接登录!");
			}
			
			/**获取纯数字的验证码*/
			String registerRandomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
			DataCache.put(registerCode, registerRandomNum);
			try {
				//注册发送的短信验证码
				smsService.sendSmsCode(registerRandomNum,userPhone,SourceConstant.WAP_CODE,SmsConstant.SMS_TYPE_REGISTER_ID);
			} catch (Exception e) {
				e.printStackTrace();
				return Jsonp.error("短信服务器响应超时");
			}
			return Jsonp_data.success(registerCode);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("请求出错" + e);
			return Jsonp.error();
		}
	}
	
	/**
	 * 开通我的如意宝
	 * 需要验证用户是否登录阿拉丁账户
	 * @param userPhone
	 * @param loginPassword
	 * @param payPassword
	 * @param userName
	 * @param userCardId
	 * @param registerNum
	 * @param sourceCode
	 * @param key -- 短信验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("openAlabao")
	public Object openAlabao(@RequestParam("userPhone") String userPhone,
			@RequestParam("loginPassword") String loginPassword,
			@RequestParam("payPassword") String payPassword,
			@RequestParam("userName") String userName,
			@RequestParam("userCardId") String userCardId,
			@RequestParam("registerNum") String registerNum,
			@RequestParam("key") String key,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			/**获得DataCache里的验证码的值*/
			String randomSmsCode = (String) DataCache.get(registerNum);
			if (!StringUtils.equals(key,randomSmsCode)) {
				return Jsonp.error("验证码输入有误!");
			}
			if (ObjectUtils.notEqual(alabaoAccountService.findByAccount(userPhone), null)) {
				return Jsonp.error("此手机号已注册,您可直接登录!");
			}
			if (ObjectUtils.notEqual(alabaoAccountService.findByIDCard(userCardId), null)) {
				return Jsonp.error("身份证号已绑定");
			}
			AlabaoAccount alabaoAccount = new AlabaoAccount();
			alabaoAccount.setAccount(userPhone);
			alabaoAccount.setLoginPassword(MD5Util.MD5(loginPassword+MD5Util.getPasswordkey()));
			alabaoAccount.setPayPassword(MD5Util.MD5(payPassword+MD5Util.getPasswordkey()));
			alabaoAccount.setMemberId(user.getMemberId());
			alabaoAccount.setMobilePhone(userPhone);
			alabaoAccount.setTrueName(userName);
			alabaoAccount.setIdentityCardNo(userCardId);
			alabaoAccount.setSourceCode(SourceConstant.WAP_CODE);
			alabaoAccount.setLockedBalance(BigDecimal.ZERO);
			alabaoAccount.setBalance(BigDecimal.ZERO);
			alabaoAccount.setYesterdayIncome(BigDecimal.ZERO);
			alabaoAccount.setTotalIncome(BigDecimal.ZERO);
			alabaoAccount.setStatus("2");//此处默认审核通过
			alabaoAccountService.add(alabaoAccount);
			this.modifyCartSkuPriceByVip(user.getMemberId());
			/**销毁注册码*/
			Cookie cookie = CookieUtil.getCookie(request, CookieConstant.REGISTER_COOKIE);
			CookieUtil.removeCookie(response, cookie);
			DataCache.remove(registerNum);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("开通我的如意宝出错", e);
			return Jsonp.error("开通出错");
		}
	}
	
	
	/*到购物车中 修改VIP会员商品的价格*/
	private void modifyCartSkuPriceByVip(Long memberId){
		
		AlabaoAccount alabaoAccount= alabaoAccountService.findByMemberId(memberId);
		if(alabaoAccount==null){
			return;
		}
		ShoppingCartQueryCondition cartQueryCondition = new ShoppingCartQueryCondition();
		cartQueryCondition.setMemberId(memberId);
		List<ShoppingCartSku>  cartList = shoppingCartSkuUserService.findByQueryCondition(cartQueryCondition);
		if(cartList==null){
			return ;
		}
		for (ShoppingCartSku cartSku : cartList) {
			
			SkuExtraInfo extraInfo = skuExtraInfoService.findBySkuCode(cartSku.getSkuCode());
			if(extraInfo==null || !StringUtils.equals(extraInfo.getIsMemberSku(),CommonConstant.YES)){
				continue;
			}
			SkuPrice skuPrice = skuPriceService.findBySkuCode(cartSku.getSkuCode());
			if(skuPrice==null || skuPrice.getAladingPrice()==null){
				continue;
			}
			cartSku.setCurrentPrice(skuPrice.getAladingPrice());
			cartSku.setLineTotalPrice(skuPrice.getAladingPrice().multiply(BigDecimal.valueOf(cartSku.getQuantity())));
			shoppingCartSkuUserService.update(cartSku);
		}
		
	}
	
}
