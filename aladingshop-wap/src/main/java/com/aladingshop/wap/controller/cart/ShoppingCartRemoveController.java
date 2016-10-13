package com.aladingshop.wap.controller.cart;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.CartUtils;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartDto;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartRemoveFacade;

/**
 * 
 * @description 购物车移除商品
 * @author 孟少博
 * @date 2015年8月14日上午10:04:21
 */
@RequestMapping("/cart")
@Controller
public class ShoppingCartRemoveController {

	/**记录日志*/
	private static final Log logger = LogFactory.getLog(ShoppingCartRemoveController.class);
	
	@Autowired
	private ShoppingCartRemoveFacade removeFacade;

	/**
	 * 批量删除购物车中的行
	 * 
	 * @param skuCodes 商品编号集合
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("removeBath")
	public Object removeBath(@RequestParam("skuCodes") String skuCodes, HttpServletRequest request) {
		if (StringUtils.isBlank(skuCodes)) {
			String resultCode = ShoppingCartCaptChaEnum.SKU_CODE_ISNULL.getCode();
			return  Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		CartDto cartDto = new CartDto();
		try {
			String sid = CartUtils.getClientSid(request);
			String visitKey = CartUtils.getClientVisitKey(request);
			if (StringUtils.isBlank(sid) && StringUtils.isBlank(visitKey)) {
				String resultCode = ShoppingCartCaptChaEnum.SID_AND_VISITKEY_ISNULL.getCode();
				return  Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			UserDto user = CartUtils.getUser(sid);
			String shoppingCartSid = CookieUtil.getCookieValue(request, CookieConstant.COOKIESHOPPINGCARTSID);
			
			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			String[] codes = new String[]{skuCodes};
			shoppingCartBo.setUser(user);
			shoppingCartBo.setVisitKey(visitKey);
			shoppingCartBo.setSkuCodeArray(codes);
			shoppingCartBo.setShoppingCartSid(shoppingCartSid);
			
			if (ObjectUtils.notEqual(null, user)) {
				cartDto = removeFacade.hasLoginRemoveBath(shoppingCartBo);
			} else {
				cartDto = removeFacade.noLoginRemoveBath(shoppingCartBo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ShoppingCartRemoveController.removeBath购物车删除商品,系统出现异常" + e);
			return Jsonp.error();
		}
		return Jsonp_data.success(cartDto);
	}
}
