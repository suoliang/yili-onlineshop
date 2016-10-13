package com.fushionbaby.facade.biz.common.cart.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ShoppingCartConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartDto;
import com.fushionbaby.common.dto.cart.CartItemDto;
import com.fushionbaby.common.dto.cart.CartModifyAfterDto;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartQueryFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuService;
/**
 * 
 * @author mengshaobo
 *
 */
@Service
public class ShoppingCartQueryFacadeImpl  implements ShoppingCartQueryFacade {

	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> soShoppingCartSkuWebService;
	@Autowired
	private CartRedisUserService<ShoppingCartSku> cartRedisService;
	@Autowired
	private SkuService skuService;
	
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired 
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	
	@Autowired
	private ImageProcess imageProcess;

	
	@Autowired
	private SkuFacade skuFacade;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.cart.ShoppingCartQueryFacade#getCartDto
	 * (java.lang.String, java.lang.String,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public CartDto queryCartDto(ShoppingCartBo shoppingCartBo) {
		List<ShoppingCartSku> list = null;
		String visitKey = shoppingCartBo.getVisitKey();
		UserDto user = shoppingCartBo.getUser();
		
		String loginStatus = null;
		if (StringUtils.isNotBlank(visitKey) && ObjectUtils.equals(null, user)) {
			
			loginStatus = CommonConstant.NO;
			list = cartRedisService.getListPage( visitKey, shoppingCartBo.getStart(), shoppingCartBo.getLimit());
			list = this.mofifySkuSelected(list, null, visitKey);
		} else if (ObjectUtils.notEqual(null, user)) {
			loginStatus = CommonConstant.YES;
			// 通过memberId查购物车中的信息
			//list = soShoppingCartSkuWebService.getListPage(user.getMemberId(), shoppingCartBo.getStart(), shoppingCartBo.getLimit());
			ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
			queryCondition.setStoreCode(shoppingCartBo.getStoreCode());
			queryCondition.setMemberId(shoppingCartBo.getUser().getMemberId());
			queryCondition.setStart((shoppingCartBo.getStart()-1)*shoppingCartBo.getLimit());
			queryCondition.setLimit(shoppingCartBo.getLimit());
			list = soShoppingCartSkuWebService.findByQueryCondition(queryCondition);
			
			list = this.mofifySkuSelected(list, user.getMemberId(), visitKey);
		}
		if (CollectionUtils.isEmpty(list)) {
			return this.getCartDtoByNull(loginStatus);
		}
		Long memberId = user!=null ? user.getMemberId() : null;
		
		CartDto cartDto = this.getCartItemDtoList(memberId,list, shoppingCartBo.getShoppingCartSid(),shoppingCartBo.getImageType());
		if (ObjectUtils.notEqual(null, cartDto)) {
			cartDto.setLoginStatus(loginStatus);
			BigDecimal priceTotal = this.getCartPrice(cartDto, user, visitKey,shoppingCartBo.getStoreCode());
			cartDto.setPriceTotal(NumberFormatUtil.numberFormat(priceTotal));
			return cartDto;
		}
		return this.getCartDtoByNull(loginStatus);
	}
	

	

	/**
	 * 获取购物车中选中的商品总价格
	 * @param cartDto
	 * @param user
	 * @param visitKey
	 * @return
	 */
	private BigDecimal getCartPrice(CartDto cartDto, UserDto user, String visitKey,String storeCode) {
		if (ObjectUtils.notEqual(null, user)) {
			BigDecimal cartItemPriceTotal = new BigDecimal(0);
			ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
			queryCondition.setMemberId(user.getMemberId());
			queryCondition.setStoreCode(storeCode);
			queryCondition.setIsSelected(CommonConstant.YES);
			List<ShoppingCartSku> selectedCartSkuUserList = soShoppingCartSkuWebService.findByQueryCondition(queryCondition);
			if(selectedCartSkuUserList == null){
				return cartItemPriceTotal;
			}
			for (ShoppingCartSku s : selectedCartSkuUserList) {
				
				cartItemPriceTotal = cartItemPriceTotal.add(s.getCurrentPrice().multiply(BigDecimal.valueOf(s.getQuantity())));
			}
			
			return cartItemPriceTotal;
		}
		// 从redis中拿数量和金额
		CartModifyAfterDto sumCartSku = cartRedisService.getSumCartSku(visitKey);
		return new BigDecimal(sumCartSku.getPriceTotal());
	}

	/**
	 * 通过购物车商品列表获得页面显示的购物车商品列表
	 * 
	 * @param list
	 * @param shoppingCartSid 
	 * @return
	 */
	private CartDto getCartItemDtoList(Long memberId,List<ShoppingCartSku> list, String shoppingCartSid,String imageStandardType) {
		BigDecimal selectedPrice = new BigDecimal(0);
		CartDto cartDto = new CartDto();
		List<CartItemDto> cartList = new ArrayList<CartItemDto>();
		for (ShoppingCartSku cartSku : list) {
			CartItemDto cartItemDto = new CartItemDto();
			
			String skuCode = cartSku.getSkuCode();
			cartItemDto.setSkuCode(skuCode);
			cartItemDto.setCartRowsId(String.valueOf(cartSku.getId()));
			cartItemDto.setImgPath(imageProcess.getThumImagePath(cartSku.getSkuCode(),imageStandardType));
			cartItemDto.setName(cartSku.getSkuName());
			
			SkuExtraInfo skuExtraInfo	= skuExtraInfoService.findBySkuCode(skuCode);
			BigDecimal currentPrice = skuFacade.getCurrentSkuPrice(skuCode, memberId, skuExtraInfo.getIsMemberSku());
			
			cartItemDto.setPrice(NumberFormatUtil.numberFormat(currentPrice));
			cartItemDto.setPnum(cartSku.getQuantity());
			cartItemDto.setColor(StringUtils.isBlank(cartSku.getColor()) ? StringUtils.EMPTY : cartSku.getColor());
			cartItemDto.setSize(StringUtils.isBlank(cartSku.getSize()) ? StringUtils.EMPTY : cartSku.getSize());
			BigDecimal rowPriceTotal = currentPrice.multiply(new BigDecimal(cartSku.getQuantity()));
			cartItemDto.setRowPriceTotal(NumberFormatUtil.numberFormat(rowPriceTotal));

			cartItemDto.setIsSelected(StringUtils.isBlank(cartSku.getIsSelected()) ? CommonConstant.NO : cartSku.getIsSelected() );
			int availableQty = this.getAvailableQtyBy(skuCode);
			int quotaNum = this.getQuotaNum(skuCode);
//			if(availableQty==0 || availableQty<quotaNum){
//				cartItemDto.setIsSelected(this.mofifySkuSelected(skuCode, memberId));
//				cartItemDto.setIsSelected(CommonConstant.NO);
//			}
			cartItemDto.setIsMemberSku(this.getIsMemberSku(skuCode));
			cartItemDto.setAvailableQty(availableQty);
			cartItemDto.setQuotaNum(quotaNum);
			cartList.add(cartItemDto);
			Integer pnumTotal = cartDto.getPnumTotal() == null ? 0 : cartDto.getPnumTotal();
			BigDecimal priceTotal = cartDto.getPriceTotal() == null ? new BigDecimal(0) : new BigDecimal(cartDto.getPriceTotal());
			cartDto.setPnumTotal(pnumTotal + cartSku.getQuantity());
			BigDecimal formatPriceTotal = priceTotal.add(currentPrice.multiply(new BigDecimal(cartSku.getQuantity())));
			cartDto.setPriceTotal(NumberFormatUtil.numberFormat(formatPriceTotal));
			if(StringUtils.equals(CommonConstant.YES, cartItemDto.getIsSelected())){
				selectedPrice = selectedPrice.add(currentPrice.multiply(new BigDecimal(cartSku.getQuantity())));
			}
		}
		cartDto.setShoppingCartSid(shoppingCartSid);
		DataCache.put(shoppingCartSid + ShoppingCartConstant.SHOPPINGCARTPNUMTOTAL,cartDto.getPnumTotal());
		DataCache.put(shoppingCartSid + ShoppingCartConstant.SHOPPINGCARTPRICETOTAL,selectedPrice);
		cartDto.setItems(cartList);
		return cartDto;
	}
	
	/**
	 * 修改选中的商品选中状态改为 不选中
	 * @param skuCode
	 * @param memberId
	 * @param visitKey
	 * @return
	 */
	private List<ShoppingCartSku> mofifySkuSelected(List<ShoppingCartSku> cartSkuList,Long memberId,String visitKey){
		
		List<ShoppingCartSku> newCartSkuList = cartSkuList;
		
		for (ShoppingCartSku shoppingCartSku : newCartSkuList) {
			String skuCode = shoppingCartSku.getSkuCode();
			
			int availableQty = this.getAvailableQtyBy(skuCode);
			int quotaNum = this.getQuotaNum(skuCode);
			if(availableQty==0 || availableQty<quotaNum){
				shoppingCartSku.setIsSelected(CommonConstant.NO);
				if(memberId!=null){
					ShoppingCartSku cartSku = soShoppingCartSkuWebService.findBySkuCodeAndMemberId(skuCode, memberId);
					cartSku.setIsSelected(CommonConstant.NO);
					soShoppingCartSkuWebService.update(cartSku);
				}else{
					cartRedisService.updateCartItemSelected(visitKey, skuCode, CommonConstant.NO);
				}
			}
		}
		return newCartSkuList;
		
	}
	
	
	/**
	 * 获取商品库存数量
	 * @param skuCode 商品编号
	 * @return
	 */
	private Integer getAvailableQtyBy(String skuCode){
		
		SkuInventory skuInventory = skuInventoryService.findBySkuCode(skuCode);
		
		if(ObjectUtils.equals(null, skuInventory)){
			return new Integer(0);
		}
		return skuInventory.getAvailableQty();
	}
	/**
	 * 获取商品限购数量
	 * @param skuCode 商品编号
	 * @return
	 */
	private Integer getQuotaNum(String skuCode){
		
		Sku sku = skuService.queryBySkuCode(skuCode);
		
		if(ObjectUtils.equals(null, sku)){
			return new Integer(0);
		}
		
		return sku.getQuotaNum()==null ? new Integer(0) : sku.getQuotaNum();
		
	}
	
	private String getIsMemberSku(String skuCode){
		
		SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(skuCode);
		if(skuExtraInfo!=null){
			return skuExtraInfo.getIsMemberSku();
		}
		return CommonConstant.NO;
		
	}
	

	/**
	 * 当购物车列表为空时页面的默认值
	 * 
	 * @return
	 */
	private CartDto getCartDtoByNull(String loginStatus) {
		CartDto cartDto = new CartDto();
		cartDto.setPnumTotal(0);
		cartDto.setPriceTotal("0.00");
		cartDto.setItems(Collections.<CartItemDto> emptyList());
		cartDto.setLoginStatus(loginStatus);
		return cartDto;
	}

	public List<CartItemDto> findSelectedCartSkuByMemberId(ShoppingCartQueryCondition queryCondition) {
		
		List<ShoppingCartSku>  cartList = soShoppingCartSkuWebService.findByQueryCondition(queryCondition);
		if(CollectionUtils.isEmpty(cartList)){
			return null;
		}
		List<CartItemDto> itemList = new ArrayList<CartItemDto>();
		for (ShoppingCartSku cart : cartList) {
			CartItemDto item = new CartItemDto();
			item.setSkuCode(cart.getSkuCode());
			item.setName(cart.getSkuName());
			itemList.add(item);
		}
		
		return itemList;
	}

}
