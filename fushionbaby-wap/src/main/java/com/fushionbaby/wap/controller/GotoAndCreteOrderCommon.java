package com.fushionbaby.wap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.core.model.SoShoppingCartSkuUser;
import com.fushionbaby.core.service.SoShoppingCartSkuWebUserService;

/**
 * 购物车和创建订单公共方法
 * @author King 索亮
 *
 */
@Controller
public abstract class GotoAndCreteOrderCommon {
	
	
	/**
	 * 购物车,行记录service
	 */
	@Autowired
	private SoShoppingCartSkuWebUserService<SoShoppingCartSkuUser> soShoppingCartSkuWebService;
	
	protected Object checkUserLogin(HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError("请先登录!");
		}
		UserDto user = getUser(request);
		if (user == null) {
			return Jsonp.noLoginError("请先登录!");
		}
		return null;
	}
	
	protected UserDto getUser(HttpServletRequest request){
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto user = (UserDto) SessionCache.get(sid);
		return user;
	}
	
	protected Object checkCart(UserDto user){
		List<SoShoppingCartSkuUser> cartItemList = getCartItemList(user.getMember_id());
		if (CollectionUtils.isEmpty(cartItemList)) {
			return Jsonp.paramError("亲!请选择您要购买的商品!在结算!");
		}
		return null;
	}
	
	protected List<SoShoppingCartSkuUser> getCartItemList(Long  memberId) {
		List<SoShoppingCartSkuUser> cartItemList = soShoppingCartSkuWebService.findSelectedCartSkuByMemberId(memberId);
		return cartItemList;
	}

}
