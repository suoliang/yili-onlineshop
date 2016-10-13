package com.fushionbaby.web.controller.cart;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.CartUtils;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.ShoppingCartSkuUser;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartAddFacade;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.FindSkuService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuService;

@Controller
@RequestMapping("/cart")
public class ShoppingCartByAddController {
	/**记录日志*/
	private static final Log logger = LogFactory.getLog(ShoppingCartByAddController.class);

	/**
	 * 购物车,行记录service
	 */
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSkuUser> soShoppingCartSkuWebService;

	/**
	 * 商品service
	 */
	@Autowired
	private FindSkuService findSkuService;
	@Autowired
	private SkuService<Sku> skuService;

	/**
	 * 商品库存操作service
	 */
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;

	/**
	 * Redis操作购物车,未登录会员使用的购物车
	 */
	@Autowired
	private CartRedisUserService<ShoppingCartSkuUser> cartRedisService;
	
	@Autowired
	private ShoppingCartAddFacade addFacade;

	@ResponseBody
	@RequestMapping("addToCart")
	public Object addToCart(@RequestParam("sku_code") String skuCode,
			@RequestParam("quantity")String quantity, HttpServletRequest request,HttpServletResponse response) {
		
		try {
			Object obj =  null;
			obj = CartUtils.checkSidAndVisitKey(request);
			if(obj!=null){
				return obj;
			}
			obj = CartUtils.checkSkuCode(skuCode);
			if(obj!=null){
				return obj;
			}
			obj = CartUtils.checkQuantity(quantity);
			if(obj!=null){
				return obj;
			}
			obj = addFacade.checkSkuStatus(skuCode);
			if(obj!=null){
				return obj;
			}
			int quantityNum = CartUtils.getQuantity(quantity);
			obj = addFacade.checkSkuInventoryNum(quantityNum,skuCode);
			if(obj!=null){
				return obj;
			}
			String sid = CartUtils.getClientSid(request);
			String visitKey = CartUtils.getClientVisitKey(request);
			UserDto user = CartUtils.getUser(sid);
	
			int pnum = 0;
			if (user != null) {
				obj = addFacade.checkHasLoginCartSize(user.getMember_id());
				if(obj!=null){
					return obj;
				}
				addFacade.hasLoginCartAdd(user.getMember_id(),skuCode, quantityNum);
				pnum = addFacade.getSelectedCartSkuCountByMemberId(user.getMember_id());
			} else {
				obj =  addFacade.checkNoLoginCartSize(visitKey);
				if(obj!=null){
					return obj;
				}	
				addFacade.noLoginCartAdd(visitKey, skuCode, quantityNum);
				pnum = addFacade.getRedisCount(visitKey);
			}
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("pnum", String.valueOf(pnum));
			resultMap.put("skuId", String.valueOf(skuCode));
			resultMap.put("quantity", String.valueOf(quantity));
			return Jsonp_data.success(resultMap);
		} catch (Exception e) {
			logger.error("ShoppingCartByAddController购物车添加系统异常!" + e);
			e.printStackTrace();
			return Jsonp.error();
		}
	}
	
	
}