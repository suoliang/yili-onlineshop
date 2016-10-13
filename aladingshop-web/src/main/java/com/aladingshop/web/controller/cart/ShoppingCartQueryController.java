/**
 * 
 */
package com.aladingshop.web.controller.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import util.CartUtils;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartDto;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartQueryFacade;

/**
 * @description 购物车查询
 * @author 孟少博
 * @date 2015年8月13日下午3:27:42
 */

@Controller
@RequestMapping("/cart")
public class ShoppingCartQueryController {
	/**记录日志*/
	private static final Log LOGGER = LogFactory.getLog(ShoppingCartQueryController.class);
	
	@Autowired
	private ShoppingCartQueryFacade queryFacade;
	
	
	/**
	 * 返回到客户端一个唯一key值
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getVisitKey")
	public Object getVisitKey() {
		String uuid = RandomNumUtil.getUUIDString() + RandomNumUtil.getUUIDString();
		return Jsonp_data.success(uuid);
	}
	
	/**
	 * 迷你购物车查询
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="miniCart",method=RequestMethod.POST)
	public Object miniCartList(HttpServletRequest request,HttpServletResponse response) {
		
		CartDto cartDto = new CartDto();
		try {
			String checkResultCode = CartUtils.checkSidAndVisitKey(request);
			if(!StringUtils.equals(checkResultCode, ShoppingCartCaptChaEnum.SUCCESS.getCode()))
			{
				return Jsonp.newInstance(checkResultCode, ShoppingCartCaptChaEnum.getTitle(checkResultCode));
			}
			
			ShoppingCartBo queryCondition = new ShoppingCartBo();
			UserDto user = CartUtils.getUser(CartUtils.getClientSid(request));
			
			queryCondition.setUser(user);
			String visitKey = CartUtils.getClientVisitKey(request);
			
			queryCondition.setVisitKey(visitKey);
			queryCondition.setStart(WebConstant.START_INDEX);
			queryCondition.setLimit(CartConstant.CARTSIZE);
			queryCondition.setImageType(ImageStandardConstant.IMAGE_STANDARD_PC_54);
			
			String shoppingCartSid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
			CookieUtil.setCookie(response, CookieConstant.COOKIESHOPPINGCARTSID, shoppingCartSid);
			queryCondition.setShoppingCartSid(shoppingCartSid);
			
			cartDto = queryFacade.queryCartDto(queryCondition);
			if(user !=null && user.getMemberId() !=null){
				cartDto.setLoginStatus(CommonConstant.YES);
			}else{
				cartDto.setLoginStatus(CommonConstant.NO);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			LOGGER.error("查询购物车是出现异常"+e);
			return Jsonp.error();
		}
		
		return Jsonp_data.success(cartDto);
		
	}
	
	
	/**
	 * 查看浏览,到购物车,跳转到view页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	public String cartList(ModelMap model, HttpServletRequest request,HttpServletResponse response) {	
		
		CartDto cartDto = new CartDto();
		
		try {
			UserDto user = CartUtils.getUser(CartUtils.getClientSid(request));
			ShoppingCartBo queryCondition = new ShoppingCartBo();
			queryCondition.setUser(user);
			queryCondition.setVisitKey(CartUtils.getClientVisitKey(request));
			queryCondition.setStart(WebConstant.START_INDEX);
			queryCondition.setLimit(CartConstant.CARTSIZE);
			queryCondition.setImageType(ImageStandardConstant.IMAGE_STANDARD_PC_183);
			
			String shoppingCartSid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
			CookieUtil.setCookie(response, CookieConstant.COOKIESHOPPINGCARTSID, shoppingCartSid);
			queryCondition.setShoppingCartSid(shoppingCartSid);
			cartDto = queryFacade.queryCartDto(queryCondition);
			
			model.put("shoppingCartSid",shoppingCartSid);
			model.put("cart", cartDto);
			model.put("user", user);//判断是否登录--索亮
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询购物车是出现异常"+e);
		}
		
		return "cart/cart";
	}
}
