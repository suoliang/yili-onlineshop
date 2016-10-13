package com.fushionbaby.facade.biz.common.cart.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ShoppingCartConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.cart.CartDto;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartRemoveFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.service.SkuExtraInfoService;
@Service
public class ShoppingCartRemoveFacadeImpl implements ShoppingCartRemoveFacade {
	@Autowired
	private CartRedisUserService<ShoppingCartSku> cartRedisService;
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> shoppingCartSkuService;
	
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	
	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.cart.ShoppingCartRemoveFacade#hasLoginRemoveBath(com.fushionbaby.common.dto.UserDto, com.fushionbaby.common.dto.cart.CartDto, java.lang.Long[])
	 */
	public CartDto hasLoginRemoveBath( ShoppingCartBo shoppingCartBo) {
		Long memberId = shoppingCartBo.getUser().getMemberId();
		List<String> codes =  Arrays.asList(shoppingCartBo.getSkuCodeArray());
		for (int i = 0; i < codes.size(); i++) {
			String skuCode = codes.get(0);
			//通过memberId，skuCode查找
			ShoppingCartSku cartItem = shoppingCartSkuService.findBySkuCodeAndMemberId(skuCode,memberId);
			if (cartItem != null) {
				//通过memberId，skuCode删除					
				shoppingCartSkuService.deleteById(cartItem.getId());
			}
		}
		
		return this.getHasLoginCartDto(shoppingCartBo);
		
	}
	
	
	private CartDto getHasLoginCartDto(ShoppingCartBo shoppingCartBo){
		Long memberId = shoppingCartBo.getUser().getMemberId();
		String shoppingCartSid = shoppingCartBo.getShoppingCartSid();
		
		CartDto cartDto = new CartDto();
		cartDto.setPriceTotal(this.getPriceTotal(memberId, shoppingCartBo.getStoreCode()));
		List<ShoppingCartSku> cartSkuUserList  = shoppingCartSkuService.getListPage(memberId, WebConstant.START_INDEX, CartConstant.CARTSIZE);
		Integer pnum = new Integer(0);
		for (ShoppingCartSku cartSku : cartSkuUserList) {
			pnum +=cartSku.getQuantity();
		}
		cartDto.setPnumTotal(pnum);
		cartDto.setShoppingCartSid(shoppingCartSid);
		
		DataCache.put(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPRICETOTAL, new BigDecimal(cartDto.getPriceTotal()));
		
		return cartDto;
	}
	
	private String getPriceTotal(Long memberId,String storeCode){
		
		BigDecimal priceTotal = new BigDecimal(0);
		
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setStoreCode(storeCode);
		queryCondition.setMemberId(memberId);
		
		List<ShoppingCartSku> cartSkuUsers = shoppingCartSkuService.findByQueryCondition(queryCondition);
		for (ShoppingCartSku shoppingCartSku : cartSkuUsers) {
			
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(shoppingCartSku.getSkuCode());
			
			priceTotal.add(skuFacade.getCurrentSkuPrice(skuExtraInfo.getSkuCode(), memberId, skuExtraInfo.getIsMemberSku()).multiply(BigDecimal.valueOf(shoppingCartSku.getQuantity())));
		}
		
		return NumberFormatUtil.numberFormat(priceTotal);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.cart.ShoppingCartRemoveFacade#noLoginRemoveBath(java.lang.String, com.fushionbaby.common.dto.cart.CartDto, java.lang.Long[])
	 */
	public CartDto noLoginRemoveBath( ShoppingCartBo shoppingCartBo) {
		
		String visitKey = shoppingCartBo.getVisitKey();
		List<String> codes =  Arrays.asList(shoppingCartBo.getSkuCodeArray());
		
		for (int i = codes.size() - 1; i >= 0; i--) {
			String skuCode = codes.get(i);
			ShoppingCartSku cartItem = cartRedisService.getCartItemBySkuCode(visitKey, skuCode);
			if (cartItem != null) {
				cartRedisService.delCartItem(visitKey, cartItem.getId());
			}
		}
		return this.getNoLoginCartDto(visitKey);
	}
	
	private CartDto getNoLoginCartDto(String visitKey){
		CartDto cartDto = new CartDto();
		BigDecimal priceTotal = cartRedisService.getSelectedSkuPrice(visitKey);
		cartDto.setPriceTotal(NumberFormatUtil.numberFormat(priceTotal));
		cartDto.setPnumTotal(cartRedisService.getQuantityTotal(visitKey));
		return cartDto;
	}
}
