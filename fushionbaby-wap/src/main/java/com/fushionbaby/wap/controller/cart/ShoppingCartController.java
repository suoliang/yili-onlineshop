package com.fushionbaby.wap.controller.cart;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.CartUtils;

import com.fushionbaby.common.condition.ShoppingCartQueryCondition;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.SoShoppingCartSkuUser;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.core.service.SoShoppingCartSkuWebUserService;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartModifyFacade;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.FindSkuService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.wap.controller.AbstractMainController;

/**
 * @author 张明亮 购物车controller
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartController extends AbstractMainController {
	/**记录日志*/
	private static final Log logger = LogFactory.getLog(ShoppingCartController.class);

	/**
	 * 购物车,行记录service
	 */
	@Autowired
	private SoShoppingCartSkuWebUserService<SoShoppingCartSkuUser> soShoppingCartSkuWebService;

	/**
	 * 商品service
	 */
	@Autowired
	private FindSkuService findSkuService;

	/**
	 * 商品库存操作service
	 */
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;

	/**
	 * Reds操作购物车,未登录会员使用的购物车
	 */
	@Autowired
	private CartRedisUserService<SoShoppingCartSkuUser> cartRedisService;
	@Autowired
	private ShoppingCartModifyFacade modifyFacade;

	/**
	 * 修改购物车的商品购买数量
	 * 
	 * @param cart_rows_id
	 * @param quantity
	 */
	@ResponseBody
	@RequestMapping("modify_item_quantity")
	public Object updateCartItemQuantity(@RequestParam("skuCode") String skuCode,@RequestParam("quantity") String quantity,
			HttpServletRequest request) {
		
		CartDto cartDto = new CartDto();
		try {
			Object obj = null;
			obj = CartUtils.checkSidAndVisitKey(request);
			if(obj != null){
				return obj;
			}
			obj = CartUtils.checkSkuCode(skuCode);
			if(obj != null){
				return obj;
			}
			obj = CartUtils.checkQuantity(quantity);
			if(obj != null){
				return obj;
			}
			String sid = CartUtils.getClientSid(request);
			UserDto user = CartUtils.getUser(sid);
			
			ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
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
				return Jsonp.error("该商品库存不足!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ShoppingCartController.updateCartItemQuantity购物车更新商品购买数量,系统出现异常" + e);
			return Jsonp.error();
		}
		return Jsonp_data.success(cartDto);
	}
	
	
	/**
	 * 商品选中接口 修改购物车行选中状态
	 * 
	 * @param skuIds
	 * @param is_selected
	 * @return
	 */
	@ResponseBody
	@RequestMapping("modify_item_selected")
	public Object updateSelect(@RequestParam("skuCodes") String skuCodes, 
			@RequestParam("is_selected") String isSelected, HttpServletRequest request) {
		CartDto cartDto = new CartDto();
		try {
			Object obj = null;
			obj = CartUtils.checkSidAndVisitKey(request);
			if (obj != null) {
				return obj;
			}
			obj = CartUtils.checkSkuCodes(skuCodes);
			if(obj != null){
				return obj;
			}
			obj = CartUtils.checkIsSelected(isSelected);
			if(obj != null){
				return obj;
			}
			String sid = CartUtils.getClientSid(request);
			UserDto user = CartUtils.getUser(sid);
			String[] skuCodesArrNum = CartUtils.getSkuCodes(skuCodes);
			ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
			queryCondition.setSkuCodeArray(skuCodesArrNum);		
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
			logger.error("ShoppingCartController.updateSelect购物车更新商品选中状态,系统出现异常" + e);
			return Jsonp.error();
		}
		return Jsonp_data.success(cartDto);
	}
}