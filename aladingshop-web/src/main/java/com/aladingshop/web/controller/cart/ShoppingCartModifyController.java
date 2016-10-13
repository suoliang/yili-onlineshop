package com.aladingshop.web.controller.cart;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.CartUtils;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartModifyAfterDto;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartModifyFacade;
/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2015年8月13日下午7:27:00
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartModifyController {
	/**记录日志*/
	private static final Log LOGGER = LogFactory.getLog(ShoppingCartModifyController.class);
	@Autowired
	private ShoppingCartModifyFacade modifyFacade;
	
	@ResponseBody
	@RequestMapping(value="modifyItemQuantity",method=RequestMethod.POST)
	public Object modifyItemQuantity(String skuCode,String quantity,HttpServletRequest request){
		
		CartModifyAfterDto cartDto = new CartModifyAfterDto();
		
		try {
			String resultCode = CommonCode.SUCCESS_CODE;
			if(!StringUtils.equals(resultCode = CartUtils.checkSidAndVisitKey(request), CommonCode.SUCCESS_CODE)){
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			if(StringUtils.isBlank(skuCode) ){
				resultCode = ShoppingCartCaptChaEnum.SKU_CODE_ISNULL.getCode();
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			if(!StringUtils.equals(resultCode = CartUtils.checkQuantity(quantity), CommonCode.SUCCESS_CODE)){
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = CartUtils.getUser(sid);
			
			ShoppingCartBo queryCondition = new ShoppingCartBo();
			queryCondition.setQuantity(CartUtils.getQuantity(quantity));
			queryCondition.setSkuCode(skuCode);	
			String shoppingCartSid = CookieUtil.getCookieValue(request, CookieConstant.COOKIESHOPPINGCARTSID);
			queryCondition.setShoppingCartSid(shoppingCartSid);
			
			
			if (ObjectUtils.notEqual(null, user)) {
				queryCondition.setUser(user);
				cartDto = modifyFacade.hasLoginOperation(queryCondition);
			} else {
				queryCondition.setVisitKey(CartUtils.getClientVisitKey(request));
				cartDto = modifyFacade.noLoginOpertation(queryCondition);
			}
			if(ObjectUtils.equals(null, cartDto)){
				resultCode = ShoppingCartCaptChaEnum.INVENTORY_NOT_ENOUGH.getCode();
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("ShoppingCartRemoveController.removeBath购物车删除商品,系统出现异常" + e);
			return Jsonp.error();
		}
		
		return 	Jsonp_data.success(cartDto);
	}
	/**
	 * 
	 * @param skuCodes
	 * @param isSelected
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value="modifyItemSelected",method=RequestMethod.POST)
	public Object updateSelect(@RequestParam("skuCodes") String skuCode, 
			@RequestParam("isSelected") String isSelected, HttpServletRequest request) {
		CartModifyAfterDto cartDto = new CartModifyAfterDto();
		try {
			String resultCode = CommonCode.SUCCESS_CODE;
			if(!StringUtils.equals(resultCode = CartUtils.checkSidAndVisitKey(request), CommonCode.SUCCESS_CODE)){
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			if(StringUtils.isBlank(skuCode) ){
				resultCode = ShoppingCartCaptChaEnum.SKU_CODE_ISNULL.getCode();
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			if(!StringUtils.equals(resultCode = CartUtils.checkIsSelected(isSelected),  CommonCode.SUCCESS_CODE)){
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = CartUtils.getUser(sid);
			
			ShoppingCartBo queryCondition = new ShoppingCartBo();
			queryCondition.setSkuCodeArray(skuCode.split(","));		
			queryCondition.setIsSelected(isSelected);
			String shoppingCartSid = CookieUtil.getCookieValue(request, CookieConstant.COOKIESHOPPINGCARTSID);
			queryCondition.setShoppingCartSid(shoppingCartSid);
			if (ObjectUtils.notEqual(null, user)) {
				queryCondition.setUser(user);
				cartDto =	modifyFacade.hasLoginModifySelect(queryCondition);
			} else {
				queryCondition.setVisitKey(CartUtils.getClientVisitKey(request));
				cartDto =	modifyFacade.noLoginModifySelect(queryCondition);
			}
		} catch (Exception e) {
			LOGGER.error("ShoppingCartController.updateSelect购物车更新商品选中状态,系统出现异常" + e);
			e.printStackTrace();
			return Jsonp.error();
		}
		return Jsonp_data.success(cartDto);
	}
}
