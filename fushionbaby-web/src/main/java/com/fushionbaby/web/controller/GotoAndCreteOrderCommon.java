package com.fushionbaby.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.core.model.ShoppingCartSkuUser;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;

/**
 * 购物车和创建订单公共方法
 * @author King 索亮
 *
 */
@Controller
public abstract class GotoAndCreteOrderCommon {
	
	@Autowired
	protected UserFacade userFacade;
	/**
	 * 购物车,行记录service
	 */
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSkuUser> soShoppingCartSkuWebService;
	
	protected Object checkUserLogin(HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (user == null) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		return null;
	}
	
	
	protected Object checkCart(UserDto user){
		List<ShoppingCartSkuUser> cartItemList = getCartItemList(user.getMember_id());
		if (CollectionUtils.isEmpty(cartItemList)) {
			return Jsonp.paramError("亲!请选择您要购买的商品!在结算!");
		}
		return null;
	}
	
	protected List<ShoppingCartSkuUser> getCartItemList(Long  memberId) {
		List<ShoppingCartSkuUser> cartItemList = soShoppingCartSkuWebService.findSelectedCartSkuByMemberId(memberId);
		return cartItemList;
	}

}
