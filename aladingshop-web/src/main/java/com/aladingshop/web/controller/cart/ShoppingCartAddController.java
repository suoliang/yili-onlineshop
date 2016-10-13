package com.aladingshop.web.controller.cart;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.CartUtils;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartAddFacade;

@Controller
@RequestMapping("/cart")
public class ShoppingCartAddController {
	/**记录日志*/
	private static final Log LOGGER = LogFactory.getLog(ShoppingCartAddController.class);
	
	
	
	@Autowired
	private ShoppingCartAddFacade addFacade;
	
	
	/**
	 * 添加购物车
	 * @param skuCode 商品编号
	 * @param quantity 数量
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addToCart")
	public Object addToCart(String skuCode,String quantity, 
			HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
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
			if(!StringUtils.equals(resultCode = addFacade.checkSkuStatus(skuCode) , CommonCode.SUCCESS_CODE)){
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			int quantityNum = CartUtils.getQuantity(quantity);
			if(!StringUtils.equals(resultCode = addFacade.checkSkuInventoryNum(quantityNum,skuCode),CommonCode.SUCCESS_CODE)){
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			
			UserDto user = CartUtils.getUser(CartUtils.getClientSid(request));
			int pnum = 0;
			Jsonp_data checkResult;
			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setSkuCode(skuCode);
			shoppingCartBo.setQuantity(quantityNum);
			if (user != null) {
				shoppingCartBo.setUser(user);
				checkResult = addFacade.checkHasLoginCartSize(shoppingCartBo);
				if(!StringUtils.equals(checkResult.getResponseCode(), CommonCode.SUCCESS_CODE)){
					return checkResult;
				}
				Jsonp addResult = addFacade.hasLoginCartAdd(shoppingCartBo);
				if(!StringUtils.equals(addResult.getResponseCode(), CommonCode.SUCCESS_CODE)){
					return Jsonp.error();
				}
				ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
				queryCondition.setIsSelected(CommonConstant.YES);
				queryCondition.setMemberId(user.getMemberId());
				pnum = addFacade.getSelectedCartSkuCountByMemberId(queryCondition);
			} else{
				String visitKey = CartUtils.getClientVisitKey(request);
				shoppingCartBo.setVisitKey(visitKey);
				checkResult =  addFacade.checkNoLoginCartSize(shoppingCartBo);
				if(!StringUtils.equals(checkResult.getResponseCode(), CommonCode.SUCCESS_CODE)){
					return checkResult;
				}
				addFacade.noLoginCartAdd(shoppingCartBo);
				pnum = addFacade.getRedisCount(shoppingCartBo);
			}
			resultMap.put("pnum", String.valueOf(pnum));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("添加购物车时，出现异常"+e.getMessage());
			return Jsonp.error();
		}
		resultMap.put("skuCode", String.valueOf(skuCode));
		resultMap.put("quantity", String.valueOf(quantity));
	
		return Jsonp_data.success(resultMap);
		
	}
	
	
	
	
}
