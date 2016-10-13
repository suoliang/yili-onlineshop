package com.fushionbaby.facade.biz.common.cart.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ShoppingCartConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartModifyAfterDto;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartModifyFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuService;
@Service
public class ShoppingCartModifyFacadeImpl implements ShoppingCartModifyFacade {
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> soShoppingCartSkuWebService;
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private CartRedisUserService<ShoppingCartSku> cartRedisService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.cart.ShoppingCartModifyFacade#hasLoginOperation(com.fushionbaby.common.condition.ShoppingCartQueryCondition)
	 */
	public CartModifyAfterDto hasLoginOperation(ShoppingCartBo shoppingCartBo) {
		CartModifyAfterDto cartModifyAfterDto = new CartModifyAfterDto();
		Long memberId = shoppingCartBo.getUser().getMemberId();
		String skuCode = shoppingCartBo.getSkuCode();
		Sku sku = skuService.queryBySkuCode(skuCode);
		if(sku==null){
			return cartModifyAfterDto;
		}
		int quantityNumber = shoppingCartBo.getQuantity();
		if(sku.getQuotaNum() !=null && sku.getQuotaNum() != 0  && quantityNumber >=sku.getQuotaNum()){
			quantityNumber = sku.getQuotaNum();
		}
		ShoppingCartSku cartItem = soShoppingCartSkuWebService.findBySkuCodeAndMemberId(skuCode, memberId);
		if(cartItem == null){
			return cartModifyAfterDto;
		}
		
		boolean inventoryResult = skuInventoryService.verifyInventory(cartItem.getSkuCode(), quantityNumber);
		if(!inventoryResult){
			return null;
		}
		/**先获取商品行原有数量(旧的)为后续购物车总数量进行操作,必须写在setQuantity之前*/
		//int itemQuantityOld = cartItem.getQuantity();
		cartItem.setQuantity(quantityNumber);
		
		SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(skuCode);
		
		cartItem.setLineTotalPrice(skuFacade.getCurrentSkuPrice(skuCode, memberId, skuExtraInfo.getIsMemberSku()).multiply(BigDecimal.valueOf(quantityNumber)));
		soShoppingCartSkuWebService.update(cartItem);
		
		String shoppingCartSid = shoppingCartBo.getShoppingCartSid();
		Integer pnumTotal = (Integer) DataCache.get(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPNUMTOTAL);
		int itemQuantityOld = cartItem.getQuantity();
		//(购物车原有数量-商品行旧数量) + 新购买数量
		pnumTotal =pnumTotal - itemQuantityOld + quantityNumber;
		BigDecimal selectedTotalPrice = this.getSelectedCartItemPrice(memberId,sku.getStoreCode(), skuExtraInfo.getIsMemberSku());
		DataCache.put(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPNUMTOTAL, pnumTotal);
		DataCache.put(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPRICETOTAL, selectedTotalPrice);
		cartModifyAfterDto.setPnumTotal(pnumTotal);
		cartModifyAfterDto.setPriceTotal(NumberFormatUtil.numberFormat(selectedTotalPrice));
		cartModifyAfterDto.setSkuCode(skuCode);
		cartModifyAfterDto.setCurrentCartPrice(NumberFormatUtil.numberFormat(cartItem.getLineTotalPrice()));
		return cartModifyAfterDto;
	}
	
	
	private BigDecimal getSelectedCartItemPrice(Long memberId,String storeCode,String isMemberSku){
		
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setMemberId(memberId);
		queryCondition.setStoreCode(storeCode);
		queryCondition.setIsSelected(CommonConstant.YES);
		BigDecimal cartItemPriceTotal = new BigDecimal(0);
		List<ShoppingCartSku> selectedCartSkuUserList = soShoppingCartSkuWebService.findByQueryCondition(queryCondition);
		for (ShoppingCartSku s : selectedCartSkuUserList) {
			cartItemPriceTotal = cartItemPriceTotal.add(skuFacade.getCurrentSkuPrice(s.getSkuCode(), memberId, isMemberSku) .multiply(BigDecimal.valueOf(s.getQuantity())));
		}
		return cartItemPriceTotal;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.cart.ShoppingCartModifyFacade#noLoginOpertation(com.fushionbaby.common.condition.ShoppingCartQueryCondition)
	 */
	public CartModifyAfterDto noLoginOpertation(ShoppingCartBo queryCondition) {
		CartModifyAfterDto cartModifyAfter = new CartModifyAfterDto();
		String visitKey = queryCondition.getVisitKey();
		if(StringUtils.isBlank(visitKey)){
			return cartModifyAfter;
		}
		String skuCode = queryCondition.getSkuCode();
		
		Sku sku = skuService.queryBySkuCode(skuCode);
		if(sku==null){
			return cartModifyAfter;
		}
		int quantityNumber = queryCondition.getQuantity();
		if(sku.getQuotaNum()>0){
			quantityNumber = sku.getQuotaNum();
		}
		ShoppingCartSku cartItem = cartRedisService.getCartItemBySkuCode(visitKey, skuCode);
		if(cartItem == null){
			return cartModifyAfter;
		}
		boolean inventoryResult = skuInventoryService.verifyInventory(cartItem.getSkuCode(), quantityNumber);
		if(!inventoryResult){
			return null;
		}
		
		cartRedisService.updateCartItemQuantityBySkuCode(visitKey, skuCode, quantityNumber);;
		cartModifyAfter = cartRedisService.getSumCartSku(visitKey);
		String shoppingCartSid = queryCondition.getShoppingCartSid();
		DataCache.put(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPNUMTOTAL, cartModifyAfter.getPnumTotal());
		DataCache.put(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPRICETOTAL, cartModifyAfter.getPriceTotal());
		String currentCartPrice = NumberFormatUtil.numberFormat(cartItem.getCurrentPrice().multiply(BigDecimal.valueOf(quantityNumber)));
		cartModifyAfter.setCurrentCartPrice(currentCartPrice);
		cartModifyAfter.setSkuCode(skuCode);
			
		return cartModifyAfter;
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.cart.ShoppingCartModifyFacade#hasLoginModifySelect(com.fushionbaby.common.condition.ShoppingCartQueryCondition)
	 */
	public CartModifyAfterDto hasLoginModifySelect(ShoppingCartBo shoppingCartBo) {
		CartModifyAfterDto cartModifyAfterDto = new CartModifyAfterDto();		
		final String[] skuCodes = shoppingCartBo.getSkuCodeArray();
		final UserDto user = shoppingCartBo.getUser();
		final String isSelected = shoppingCartBo.getIsSelected();
		BigDecimal selectedTotalPrice = new BigDecimal(0);
		//Integer selectedPnumTotal = 0;//TODO
		for(String skuCode : skuCodes){
			ShoppingCartSku cartSku = soShoppingCartSkuWebService.findBySkuCodeAndMemberId(skuCode, user.getMemberId());
			if(cartSku==null){
				continue;
			}
			final Integer quantity = cartSku.getQuantity();
			//selectedPnumTotal += quantity;
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(skuCode);
			BigDecimal selectedPrice =  skuFacade.getCurrentSkuPrice(skuCode, user.getMemberId(), skuExtraInfo.getIsMemberSku()).multiply(new BigDecimal(quantity));	
			selectedTotalPrice = selectedTotalPrice.add(selectedPrice);
		}
		this.hasLoginUpdateCartItemSelected(user, skuCodes, isSelected);		
		String shoppingCartSid = shoppingCartBo.getShoppingCartSid();
		
		BigDecimal totalPrice = (BigDecimal) DataCache.get(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPRICETOTAL);
		Integer pnumTotal = (Integer) DataCache.get(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPNUMTOTAL);				
		if (isSelected.equals(CommonConstant.YES)) {
			totalPrice = totalPrice.add(selectedTotalPrice);
			//pnumTotal = pnumTotal + selectedPnumTotal;
		} else{
			totalPrice = totalPrice.subtract(selectedTotalPrice);
			//pnumTotal = pnumTotal - selectedPnumTotal;
			//if (pnumTotal < 0) {
			//	pnumTotal = 0;
			//}
			if(totalPrice.doubleValue() < 0){
				totalPrice = new BigDecimal(0);
			}
		}
		DataCache.put(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPNUMTOTAL, pnumTotal);
		DataCache.put(shoppingCartSid+ShoppingCartConstant.SHOPPINGCARTPRICETOTAL, totalPrice);
		cartModifyAfterDto.setPnumTotal(pnumTotal);
		cartModifyAfterDto.setPriceTotal(NumberFormatUtil.numberFormat(totalPrice));
		cartModifyAfterDto.setSkuCode(ArrayUtils.toString(skuCodes));
		return cartModifyAfterDto;
	}
	
	private void hasLoginUpdateCartItemSelected(UserDto user,String skuCodes[],String isSelected){
		Long memberId = user.getMemberId();
			for (int i = 0; i < skuCodes.length; i++){
				String skuCode = skuCodes[i];
				
				ShoppingCartSku cartItem = soShoppingCartSkuWebService.findBySkuCodeAndMemberId(skuCode, memberId);
				if(cartItem==null){
					continue ;
				}
				cartItem.setIsSelected(isSelected);
				soShoppingCartSkuWebService.update(cartItem);
				
			}
	}
	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.cart.ShoppingCartModifyFacade#noLoginModifySelect(com.fushionbaby.common.condition.ShoppingCartQueryCondition)
	 */
	public CartModifyAfterDto noLoginModifySelect(ShoppingCartBo shoppingCartBo) {
		final String[] skuCodes = shoppingCartBo.getSkuCodeArray();
		final String visitKey = shoppingCartBo.getVisitKey();
		final String isSelected = shoppingCartBo.getIsSelected();
		
		this.noLoginUpdateCartItemSelected(visitKey,  skuCodes, isSelected);		
		return cartRedisService.getSumCartSku(visitKey);
	}

	
	private void noLoginUpdateCartItemSelected(String visitKey,String skuCodes[],String isSelected){
		for (int i = 0; i < skuCodes.length; i++) {
			String skuCode = skuCodes[i];
			ShoppingCartSku cartItem = cartRedisService.getCartItemBySkuCode(visitKey, skuCode);
			if (ObjectUtils.notEqual(null, cartItem)) {
				cartRedisService.updateCartItemSelected(visitKey, skuCode, isSelected);
			}
		}
	}
}
