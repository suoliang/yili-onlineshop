package com.fushionbaby.wap.controller.cart;

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

import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartRemoveFacade;

@Controller
@RequestMapping("/cart")
public class ShoppingCartRemoveController {
	/**记录日志*/
	private static final Log logger = LogFactory.getLog(ShoppingCartRemoveController.class);
	
	@Autowired
	private ShoppingCartRemoveFacade removeFacade;

	/**
	 * 批量删除购物车中的行
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("removeBath")
	public Object removeBath(@RequestParam("codes") String codes, HttpServletRequest request) {
		Object obj = this.checkRemoveSkuCode(codes);
		if(!ObjectUtils.equals(null,obj)){
			return obj;
		}
		CartDto cartDto = new CartDto();
		try {
			String sid = CartUtils.getClientSid(request);
			String visitKey = CartUtils.getClientVisitKey(request);
			if (StringUtils.isBlank(sid) && StringUtils.isBlank(visitKey)) {
				return Jsonp.paramError();
			}
			String[] codesArrStr = CartUtils.getSkuCodes(codes);
			UserDto user = CartUtils.getUser(sid);
			String shoppingCartSid = CookieUtil.getCookieValue(request, CookieConstant.COOKIESHOPPINGCARTSID);
			if (ObjectUtils.notEqual(null, user)) {
				cartDto = removeFacade.hasLoginRemoveBath(user,codesArrStr,shoppingCartSid);
			} else {
				cartDto = removeFacade.noLoginRemoveBath(visitKey, codesArrStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ShoppingCartRemoveController.removeBath购物车删除商品,系统出现异常" + e);
			return Jsonp.error();
		}

		
		// 这里好像没有做完。记录(全部)商品购买总数量、已选择的商品总金额
		return Jsonp_data.success(cartDto);
	}

	private Object checkRemoveSkuCode(String codes) {
		if (StringUtils.isBlank(codes)) {
			return Jsonp.paramError("请至少选中一件商品!");
		}
		String codesArr[] = codes.split(",");
		try {
			for (String code : codesArr) {
				Long.valueOf(code);
			}
		} catch (NumberFormatException ne) {
			logger.error("ShoppingCartRemoveController.removeBath购物车删除商品传入参数有误" + ne);
			return Jsonp.paramError();
		}
		return null;
	}

	
}