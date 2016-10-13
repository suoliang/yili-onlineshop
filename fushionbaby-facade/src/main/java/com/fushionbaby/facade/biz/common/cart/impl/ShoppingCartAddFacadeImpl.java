package com.fushionbaby.facade.biz.common.cart.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartAddFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sku.service.SkuService;
import com.google.gson.Gson;
/**
 * 
 * @author King 索亮
 *
 */
@Service
public class ShoppingCartAddFacadeImpl  implements ShoppingCartAddFacade {
	
	@Autowired
	private SkuService skuService;

	/**
	 * 商品库存操作service
	 */
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	/**
	 * 购物车,行记录service
	 */
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> soShoppingCartSkuWebService;
	
	/**
	 * Redis操作购物车,未登录会员使用的购物车
	 */
	@Autowired
	private CartRedisUserService<ShoppingCartSku> cartRedisService;
	/** 商品价格信息*/
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	/** 商品额外信息*/
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	@Autowired
	private SkuFacade skuFacade;
	
	
	/***
	 * 检查商品是否下架
	 */
	public String checkSkuStatus(String skuCode) {
		Sku skuEntry = skuService.queryBySkuCode(skuCode);
		if(skuEntry == null){
			return ShoppingCartCaptChaEnum.SKU_NO.getCode();
		}
		if (!StringUtils.equals(skuEntry.getStatus(), SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
			//return Jsonp.error("该商品已下架!");
			return ShoppingCartCaptChaEnum.SKU_STATUS_NO_PUT.getCode();
		}
		return ShoppingCartCaptChaEnum.SUCCESS.getCode();
	}
	/**
	 * 检查商品库存
	 */
	public String checkSkuInventoryNum(int quantityNum, String skuCode) {
		Sku skuEntry = skuService.queryBySkuCode(skuCode);
		if(skuEntry == null){
			return ShoppingCartCaptChaEnum.SKU_NO.getCode();
		}
		if (!StringUtils.equals(skuEntry.getStatus(), SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
			//return Jsonp.error("该商品已下架!");
			return ShoppingCartCaptChaEnum.SKU_STATUS_NO_PUT.getCode();
		}
		SkuInventory skuInventory = skuInventoryService.findBySkuCode(skuCode);
		Integer availableQty = skuInventory == null ? 0 : skuInventory.getAvailableQty();
		
		if (availableQty == null || availableQty < quantityNum) {
			//return Jsonp.error("该商品库存不足!");\
			return ShoppingCartCaptChaEnum.INVENTORY_NOT_ENOUGH.getCode();
		}
		return ShoppingCartCaptChaEnum.SUCCESS.getCode();
	}
	/***
	 * 获取已经登陆的购物车商品数量
	 */
	public Jsonp_data checkHasLoginCartSize(ShoppingCartBo shoppingCartBo) {
		
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setMemberId(shoppingCartBo.getUser().getMemberId());
		queryCondition.setStoreCode(shoppingCartBo.getStoreCode());
		List<ShoppingCartSku> cartSkuList = soShoppingCartSkuWebService.findByQueryCondition(queryCondition);
		int cartLineCount =cartSkuList!=null?cartSkuList.size():0;
		if (cartLineCount > CartConstant.CARTSIZE) {
			String code = ShoppingCartCaptChaEnum.SHOPPING_CART_FULL.getCode();
			return Jsonp_data.newInstance(code, cartLineCount, ShoppingCartCaptChaEnum.getTitle(code));
		}
		return Jsonp_data.success(cartLineCount);
	}
	/***
	 * 已经登陆的购物车添加
	 */
	public Jsonp hasLoginCartAdd(ShoppingCartBo shoppingCartBo) {
		Sku skuEntry = skuService.queryBySkuCode(shoppingCartBo.getSkuCode());
		SkuInventory skuInventory = skuInventoryService.findBySkuCode(shoppingCartBo.getSkuCode());
		if (skuEntry != null && skuInventory != null){
			
			return this.modifyCartSummary(skuEntry,shoppingCartBo);
			
		}
		
		
		return Jsonp.success();
	}
	/**
	 * 操作购物车
	 * 
	 * @param skuEntry
	 *            商品信息
	 * @param selectedColor
	 *            选择的颜色
	 * @param selectedSize
	 *            选择的尺寸
	 */
	private Jsonp modifyCartSummary(Sku skuEntry,ShoppingCartBo shoppingCartBo) {
		
		if(skuEntry.getQuotaNum() !=null && skuEntry.getQuotaNum() != 0  && shoppingCartBo.getQuantity()>=skuEntry.getQuotaNum()){
			shoppingCartBo.setQuantity(skuEntry.getQuotaNum());
		}
		Long memberId = shoppingCartBo.getUser().getMemberId();
		ShoppingCartSku cartRows = soShoppingCartSkuWebService.findBySkuCodeAndMemberId(skuEntry.getUniqueCode(), memberId);
		
		if (cartRows != null) {
			//当前行购买数量,原有数量+客户再次请求购买的数量
			int cartRowsQuantity = (cartRows.getQuantity() == null || cartRows.getQuantity() < 1) ? 1 : (cartRows.getQuantity() + shoppingCartBo.getQuantity());
			if(cartRowsQuantity > CartConstant.CARTSIZE){
				cartRowsQuantity = CartConstant.CARTSIZE;
			}
			SkuExtraInfo  skuExtraInfo = skuExtraInfoService.findBySkuCode(cartRows.getSkuCode());
			if(memberId!=null && StringUtils.isBlank(shoppingCartBo.getStoreCode()) || 
					StringUtils.equals(shoppingCartBo.getStoreCode(),CommonConstant.STORE_CODE_SHOP)){
				
				BigDecimal skuCurrentPrice = skuFacade.getCurrentSkuPrice(cartRows.getSkuCode(), memberId, skuExtraInfo.getIsMemberSku());
				
				cartRows.setCurrentPrice(skuCurrentPrice);
				cartRows.setLineTotalPrice(skuCurrentPrice.multiply(BigDecimal.valueOf(cartRowsQuantity)));
				
			}
			
			cartRows.setQuantity(cartRowsQuantity);
			soShoppingCartSkuWebService.update(cartRows);
		} else {
			
			shoppingCartBo.setQuantity( shoppingCartBo.getQuantity() >  CartConstant.CARTSIZE ? CartConstant.CARTSIZE : shoppingCartBo.getQuantity());
			//添加购物车行记录
			ShoppingCartSku cartSku = this.assembleCartRows(skuEntry,shoppingCartBo);
			if(ObjectUtils.equals(null, cartSku)){
				return Jsonp.error();
			}
			cartSku.setMemberId(memberId);
			
			soShoppingCartSkuWebService.add(cartSku);
		}
		return Jsonp.success();
	}
	/**
	 * 组装购物车商品信息
	 * 
	 * @param skuEntry
	 * @param selectedColor
	 * @param selectedSize
	 * @param quantityNumber
	 * @param cart
	 */
	private ShoppingCartSku assembleCartRows(Sku skuEntry,ShoppingCartBo shoppingCartBo) {		
		Long memberId=shoppingCartBo.getUser() !=null ? shoppingCartBo.getUser().getMemberId():null;
		String skuCode = skuEntry.getUniqueCode();
		ShoppingCartSku cartSku = new ShoppingCartSku();
		cartSku.setSkuCode(skuCode);
		
		
		SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(skuCode);
		cartSku.setCurrentPrice(skuFacade.getCurrentSkuPrice(skuCode, memberId, skuExtraInfo.getIsMemberSku()));
		//cartSku.setRetailPrice(skuPrice.getRetailPrice());
		cartSku.setQuantity(shoppingCartBo.getQuantity());
		cartSku.setSkuName(skuEntry.getName());
		cartSku.setColor(StringUtils.trimToEmpty(skuEntry.getColor()));
		cartSku.setSize(StringUtils.trimToEmpty(skuEntry.getSize()));
		cartSku.setBrandCode(skuEntry.getBrandCode());
		cartSku.setCategoryCode(skuEntry.getCategoryCode());
		cartSku.setIsGift(skuExtraInfo.getIsGift());
		cartSku.setIsSelected(CommonConstant.YES);
		cartSku.setStoreCode(shoppingCartBo.getStoreCode());
		return cartSku;
	}
	/**
	 * 得到选中的购物车总数
	 * @param memberId
	 * @return
	 */
	public Integer getSelectedCartSkuCountByMemberId(ShoppingCartQueryCondition cartQueryCondition) {
		
		List<ShoppingCartSku> cartSkus = soShoppingCartSkuWebService.findByQueryCondition(cartQueryCondition);
		if (CollectionUtils.isEmpty(cartSkus)) {
			return 0;
		}
		Integer quantity = 0;
		for (ShoppingCartSku soShoppingCartSku : cartSkus) {
			quantity += soShoppingCartSku.getQuantity();
		}
		return quantity;
	}
	/***
	 * 检查没有登陆的
	 */
	public Jsonp_data checkNoLoginCartSize(ShoppingCartBo shoppingCartBo) {
		//long cartLineCount = cartRedisService.getCartSize(visitKey);
		int cartLineCount = 0;
		List<ShoppingCartSku> cartSkuList =  cartRedisService.getListPage(shoppingCartBo.getVisitKey(), 0, -1);
		for (ShoppingCartSku shoppingCartSku : cartSkuList) {
			if(StringUtils.equals(shoppingCartSku.getStoreCode(),shoppingCartBo.getStoreCode())){
				cartLineCount++;
			}
		}
		
	//	long cartLineCount = cartSkuList!=null ? cartSkuList.size() : 0;
		if (cartLineCount > CartConstant.CARTSIZE) {
			
			String code = ShoppingCartCaptChaEnum.SHOPPING_CART_FULL.getCode();
			return Jsonp_data.newInstance(code, cartLineCount, ShoppingCartCaptChaEnum.getTitle(code));
		}
		return Jsonp_data.success(cartLineCount);
	}
	/***
	 * 没有登陆的购物车添加
	 */
	public Jsonp noLoginCartAdd(ShoppingCartBo shoppingCartBo) {
		Sku skuEntry = skuService.queryBySkuCode(shoppingCartBo.getSkuCode());
		SkuInventory skuInventory = skuInventoryService.findBySkuCode(shoppingCartBo.getSkuCode());
		if(skuEntry == null || skuInventory ==null){
			return  Jsonp.newInstance(ShoppingCartCaptChaEnum.SKU_NO.getCode(), ShoppingCartCaptChaEnum.SKU_NO.getMsg());
		}
		Integer quotaNum = skuEntry.getQuotaNum();//限购数量
		if(quotaNum !=null && quotaNum>0){
			shoppingCartBo.setQuantity( shoppingCartBo.getQuantity() > quotaNum ? quotaNum : shoppingCartBo.getQuantity());
		}
		ShoppingCartSku cartRowsTmp = this.assembleCartRows(skuEntry,shoppingCartBo);
		String itemJson = new Gson().toJson(cartRowsTmp);
		String visitKey = shoppingCartBo.getVisitKey();
		ShoppingCartSku cartItem = null;
		if(!ObjectUtils.equals(null, cartRowsTmp) && StringUtils.isNotBlank(cartRowsTmp.getSkuCode())){
			 cartItem = cartRedisService.getCartItemBySkuCode(visitKey, cartRowsTmp.getSkuCode());
		}
		if (cartItem != null) {// 存在则原有数量 加 quantityNumber
			// 当前行购买数量,原有数量+客户再次请求购买的数量
			int cartRowsQuantity = (cartItem.getQuantity() == null || cartItem.getQuantity() < 1) ? 1 : cartItem.getQuantity() + shoppingCartBo.getQuantity();
			if(cartRowsQuantity > CartConstant.CARTSIZE){
				cartRowsQuantity = CartConstant.CARTSIZE;
			}
			cartRedisService.updateCartItemQuantity(visitKey, cartItem.getId(), cartRowsQuantity);
		} else {
			cartRedisService.lpush(visitKey, itemJson);
		}
		return Jsonp.success();
	}
	/**	
	 * 得到未登录的购物车总数
	 */
	public Integer getRedisCount(ShoppingCartBo shoppingCartBo) {
		int sumCartSkuQuantity = cartRedisService.getSumCartSkuQuantity(shoppingCartBo.getVisitKey());
		return sumCartSkuQuantity;
	}

}
