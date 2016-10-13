package com.fushionbaby.wap.controller.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.CartUtils;

import com.fushionbaby.common.condition.ShoppingCartQueryCondition;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.SoShoppingCartSkuUser;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.core.service.SoShoppingCartSkuWebUserService;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartQueryFacade;
import com.fushionbaby.wap.controller.AbstractMainController;

/**
 * @author 张明亮
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartByQueryController extends AbstractMainController{
	
	/**记录日志*/
	private static final Log LOGGER = LogFactory.getLog(ShoppingCartByQueryController.class);
	
	/**
	 * 购物车,行记录service
	 */
	@Autowired
	private SoShoppingCartSkuWebUserService<SoShoppingCartSkuUser> soShoppingCartSkuWebService;
	
	/**
	 * Redis操作购物车,未登录会员使用的购物车
	 */
	@Autowired
	private CartRedisUserService<SoShoppingCartSkuUser> cartRedisService;
	
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
	 * 查询购物车列表mini_cart购物车
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("miniCartList")
	public Object miniCartList(HttpServletRequest request,HttpServletResponse response) {
		try {
			Object obj = null;
			obj = CartUtils.checkSidAndVisitKey(request);
			if(obj!=null){
				return obj;
			}
			ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
			UserDto user = CartUtils.getUser(CartUtils.getClientSid(request));
			queryCondition.setUser(user);
			String visitKey = CartUtils.getClientVisitKey(request);
			queryCondition.setVisitKey(visitKey);
			queryCondition.setStart(WebConstant.START_INDEX);
			queryCondition.setLimit(CartConstant.CARTSIZE);
			queryCondition.setImageType(ImageStandardConstant.IMAGE_STANDARD_WEB_75);
			String shoppingCartSid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
			CookieUtil.setCookie(response, CookieConstant.COOKIESHOPPINGCARTSID, shoppingCartSid);
			queryCondition.setShoppingCartSid(shoppingCartSid);
			CartDto cartDto = queryFacade.queryCartDto(queryCondition);
			if(ObjectUtils.notEqual(null, user)){
				cartDto.setPriceTotal(soShoppingCartSkuWebService.getAllSkuPrice(user.getMember_id()));
			}else{
				cartDto.setPriceTotal(cartRedisService.getAllSkuPrice(visitKey));
			}
			return Jsonp_data.success(cartDto);
		} catch (Exception e) {
			LOGGER.error("ShoppingCartByQueryController.miniCartList购物车查询,系统出现异常" + e);
			e.printStackTrace();
			return Jsonp.error();
		}

	}
	
	/**
	 * 查看浏览,到购物车,跳转到view页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("cartList")
	public String cartList(ModelMap model, HttpServletRequest request,HttpServletResponse response) {	
		UserDto user =null;
		CartDto cartDto = new CartDto();
		try {
			user = CartUtils.getUser(CartUtils.getClientSid(request));
			ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
			queryCondition.setUser(user);
			queryCondition.setVisitKey(CartUtils.getClientVisitKey(request));
			queryCondition.setStart(WebConstant.START_INDEX);
			queryCondition.setLimit(CartConstant.CARTSIZE);
			queryCondition.setImageType(ImageStandardConstant.IMAGE_STANDARD_WEB_75);
			//获取购物车行数据
			String shoppingCartSid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
			CookieUtil.setCookie(response, CookieConstant.COOKIESHOPPINGCARTSID, shoppingCartSid);
			queryCondition.setShoppingCartSid(shoppingCartSid);
			cartDto = queryFacade.queryCartDto(queryCondition);
		} catch (Exception e) {
			LOGGER.error("ShoppingCartByQueryController.cartList购物车查询,系统出现异常" + e);
			e.printStackTrace();
		}

		model.put("cart", cartDto);
		model.put("cartList", cartDto.getItems());
		model.put("user", user);//判断是否登录--索亮
//		if (cartDto == null || cartDto.getItems() == null || cartDto.getItems().size() <= 0) {
//			return "cart-none";
//		}
		return "car";
	}
	

	
}
