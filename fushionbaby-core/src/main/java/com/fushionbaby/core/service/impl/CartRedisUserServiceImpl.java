package com.fushionbaby.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartModifyAfterDto;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.dao.ShoppingCartSkuUserDao;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuPriceService;
import com.google.gson.Gson;

/**
 * 购物车Redis操作实现类,购物常见操作
 * 
 * @author 张明亮
 */
@Service
public class CartRedisUserServiceImpl implements CartRedisUserService<ShoppingCartSku> {

	/**
	 * 购物车行记录dao
	 */
	@Autowired
	private ShoppingCartSkuUserDao soShoppingCartSkuWebDao;
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	/**
	 * 静态的,类加载的时候初始化
	 */
	private static RedisUtil redisUtil = new RedisUtil();

	/**
	 * 购物车添加商品行记录,如果新添加的商品,在购物车存在则原有数量+1,否则新增到购物车
	 * 
	 * @param visitKey
	 * @param item
	 * @param quantity
	 */
	public void addCartItem(String visitKey, ShoppingCartSku item, int quantity) {
		
		String itemJson = new Gson().toJson(item);
		ShoppingCartSku cartItem  = null;
		if(ObjectUtils.notEqual(null, item) && StringUtils.isNotBlank(item.getSkuCode())){
			cartItem = this.getCartItemBySkuCode(visitKey, item.getSkuCode());
		}
		if (cartItem != null) {// 存在则原有数量加1
			int quantityTmp = (cartItem.getQuantity() == null || cartItem.getQuantity() < 1) ? 1 : cartItem.getQuantity() + quantity;
			this.updateCartItemQuantity(visitKey, cartItem.getId(), quantityTmp);
		} else {// 购物车不存在该商品添加
			redisUtil.lpush(visitKey, itemJson);
		}
	}

	/**
	 * 更新购物车商品行记录的购买数量
	 * 
	 * @param visitKey
	 * @param index
	 * @param number
	 */
	public void updateCartItemQuantity(String visitKey, long index, int number) {
		Gson gson = new Gson();
		String itemJson = redisUtil.lindex(visitKey, index);
		if (StringUtils.isNotBlank(itemJson)) {
			ShoppingCartSku item = gson.fromJson(itemJson, ShoppingCartSku.class);

			// 数量默认为1
			int quantity = (item.getQuantity() == null || item.getQuantity() < 1) ? 1 : number;
			item.setQuantity(quantity);
			itemJson = gson.toJson(item);
			redisUtil.lset(visitKey, index, itemJson);
		}
	}
	
	/**
	 * 修改购物车购买的商品数量
	 * 
	 * @param visitKey
	 * @param skuId
	 * @param number
	 */

	public void updateCartItemQuantityBySkuCode(String visitKey,String skuCode, int number) {
		ShoppingCartSku item = this.getCartItemBySkuCode(visitKey, skuCode);

		if (item != null) {
			this.updateCartItemQuantity(visitKey, item.getId(), number);
		}
	}

	/**
	 * 根据购物车id和购物车行商品skuId修改购物车行记录是否被选中
	 * 
	 * @param shoppingCartSummaryId
	 * @param skuId
	 * @param isSelected
	 */
	public void updateCartItemSelected(String visitKey, String skuCode, String isSelected) {
		ShoppingCartSku item = this.getCartItemBySkuCode(visitKey, skuCode);

		if (item != null) {
			Gson gson = new Gson();
			// isSelected是否选中,如果不是y/n默认设置为y
			isSelected = !(CommonConstant.YES.equals(isSelected) || CommonConstant.NO.equals(isSelected)) ? CommonConstant.YES : isSelected;
			item.setIsSelected(isSelected);
			String itemJson = gson.toJson(item);
			redisUtil.lset(visitKey,item.getId(), itemJson);
		}
	}

	/**
	 * 更新购物车所有商品行记录的,isSelected
	 * 
	 * @param visitKey
	 * @param isSelected
	 */
	public void updateCartItemSelectedAll(String visitKey, String isSelected) {
		//isSelected是否选中,如果不是y/n默认设置为y
		isSelected = !(CommonConstant.YES.equals(isSelected) || CommonConstant.NO.equals(isSelected)) ? CommonConstant.YES : isSelected;
		Gson gson = new Gson();
		List<ShoppingCartSku> list = this.getListPage(visitKey, 0, -1);
		for (int i = 0; i < list.size(); i++) {
			ShoppingCartSku item = list.get(i);
			item.setIsSelected(isSelected);
			String itemJson = gson.toJson(item);
			redisUtil.lset(visitKey, i, itemJson);
		}
	}

	/**
	 * 根据购物车的visitKey,删除购物车制定index的商品行记录,从购物车删除一件商品
	 * 
	 * @param visitKey
	 * @param index
	 */
	public void delCartItem(String visitKey, long index) {
		
		
		ShoppingCartSku cartSku = this.getCartItemById(visitKey, index);
		if(cartSku==null){
			return ;
		}
		
		redisUtil.delIndex(visitKey, index);
	}

	/**
	 * 删除整个购物车
	 * 
	 * @param visitKey
	 */
	public void delCart(String visitKey) {
		redisUtil.del(visitKey);
		String cartKey = CartConstant.CART_PREFIX + visitKey;
		redisUtil.del(cartKey);
	}

	/**
	 * 购物车购买商品行,分页查询
	 * 
	 * @param visitKey
	 * @param start
	 * @param end
	 * @return List<SoShoppingCartSku>
	 */
	public List<ShoppingCartSku> getListPage(String visitKey, int start, int end) {
		Gson gson = new Gson();
		List<ShoppingCartSku> list = new ArrayList<ShoppingCartSku>();
		if (start <= 0) {
			start = 1;
		}
		start = (start-1)*end;
		List<String> redisCartList = redisUtil.lrange(visitKey, start, end);
		if (CollectionUtils.isEmpty(redisCartList)) {
			return list;
		}
		for (int i = 0; i < redisCartList.size(); i++) {
			String itemJson = redisCartList.get(i);
			if (StringUtils.isBlank(itemJson)) {
				continue;
			}
			ShoppingCartSku item = gson.fromJson(itemJson, ShoppingCartSku.class);
			item.setId(Long.valueOf(i));// 为查询结果设置id
			list.add(item);
		}
		return list;
	}
	
	
	
	public List<ShoppingCartSku> getSkuListPageByStoreCode(String storeCode,
			String visitKey, int start, int end) {
		
		List<ShoppingCartSku> skuList = new ArrayList<ShoppingCartSku>();
		List<ShoppingCartSku> allSkuList = this.getListPage(visitKey, 0, -1);
		if(allSkuList==null ){
			return skuList;
		}
		
		for (ShoppingCartSku shoppingCartSku : allSkuList) {
			
			if(StringUtils.isBlank(storeCode)  && StringUtils.isBlank(shoppingCartSku.getStoreCode())){
				skuList.add(shoppingCartSku);
			}
			else if(StringUtils.equals(shoppingCartSku.getStoreCode(),storeCode)){
				skuList.add(shoppingCartSku);
			}
		}
		
		if(skuList.size()<start){
			return  new ArrayList<ShoppingCartSku>();
		}
		
		if(end==-1){
			end = skuList.size();
		}
		
		return skuList.subList(start, end);
	}
	

	

	/**
	 * 查询购物车中已经勾选的商品行记录
	 * 
	 * @param visitKey
	 * @return List<SoShoppingCartSku>
	 */
	public List<ShoppingCartSku> findCartSkuSelected(String visitKey) {
		List<ShoppingCartSku> list = new ArrayList<ShoppingCartSku>();
		List<ShoppingCartSku> listTmp = this.getListPage(visitKey, 0, -1);
		if (!CollectionUtils.isEmpty(listTmp)) {
			for (ShoppingCartSku item : listTmp) {
				if (CommonConstant.YES.equals(item.getIsSelected())) {
					list.add(item);
				}
			}
		}
		return list;
	}

	/**
	 * 根据购物车id得到购物车isSelected状态的数量
	 * @param shoppingCartSummaryId
	 * @param isSelected
	 * @return int
	 */
	public int findCartItemSelectedCount(String visitKey,String isSelected){
		int count = 0;
		if(StringUtils.isBlank(visitKey) || StringUtils.isBlank(isSelected)){
			return count;
		}
		List<ShoppingCartSku> listTmp = this.getListPage(visitKey, 0, -1);
		if (!CollectionUtils.isEmpty(listTmp)) {
			for (ShoppingCartSku item : listTmp) {
				if (isSelected.equals(item.getIsSelected())) {
					count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * 根据购物车唯一key,和购物车item的id获取购物车中一件商品行的信息
	 * 
	 * @param visitKey
	 * @param index
	 * @return
	 */
	public ShoppingCartSku getCartItemById(String visitKey, long index) {
		Gson gson = new Gson();
		String itemJson = redisUtil.lindex(visitKey, index);
		ShoppingCartSku item = null;
		if (StringUtils.isNotBlank(itemJson) && !("nil".equals(itemJson))) {
			item = gson.fromJson(itemJson, ShoppingCartSku.class);
		}
		return item;
	}

	/**
	 * 根据购物车唯一key,和购物车item的商品编号获取购物车中一件商品行的信息
	 * 
	 * @param visitKey
	 * @param skuId
	 * @return
	 */

	public ShoppingCartSku getCartItemBySkuCode(String visitKey, String skuCode) {

		if (StringUtils.isBlank(visitKey)) {
			return null;
		}
		List<String> itemList = redisUtil.lrange(visitKey, 0, -1);
		ShoppingCartSku cartItem = null;
		for (int i = 0; i < itemList.size(); i++) {
			String itemJson = itemList.get(i);
			if (StringUtils.isBlank(itemJson)) {
				continue;
			}
			cartItem = (ShoppingCartSku) new Gson().fromJson(itemJson, ShoppingCartSku.class);
			if (cartItem != null && StringUtils.isNotBlank(cartItem.getSkuCode())) {
				if(StringUtils.equals(cartItem.getSkuCode(),skuCode)){
					cartItem.setId(Long.valueOf(i));
					break;
				}
			}
			cartItem = null;
		}
		return cartItem;

	}

	
	/**
	 * 会员登录,购物车操作;登录成功时调用
	 * 
	 * @param visitKey
	 * @param memberId
	 */
	public void loginCart(String visitKey, String storeVisitKey,UserDto userDto) {
		List<ShoppingCartSku> list = this.getListPage(visitKey, 0, -1);
		if (!CollectionUtils.isEmpty(list)){ 
			hasLoginSynchronizeCartSku(userDto, list);
			this.delCart(visitKey);
		}
		if(StringUtils.isBlank(storeVisitKey)){
			return ;
		}
		List<ShoppingCartSku> storeList = this.getListPage(storeVisitKey, 0, -1);
		if (!CollectionUtils.isEmpty(storeList)){ 
			hasLoginSynchronizeCartSku(userDto, storeList);
			this.delCart(storeVisitKey);
		}

	}



	private void hasLoginSynchronizeCartSku(UserDto userDto, List<ShoppingCartSku> list ) {
		Long memberId = userDto.getMemberId();
		for (ShoppingCartSku cartSku : list) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("memberId", memberId);
//			map.put("skuCode", cartSku.getSkuCode());
			
			ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
			queryCondition.setMemberId(memberId);
			queryCondition.setSkuCode(cartSku.getSkuCode());
			queryCondition.setStoreCode(cartSku.getStoreCode());
			
			List<ShoppingCartSku> cartRowList = soShoppingCartSkuWebDao.findByQueryCondition(queryCondition);
			if (cartRowList != null && cartRowList.size()>0 && cartRowList.get(0)!=null) {
				ShoppingCartSku cartRow = cartRowList.get(0);
				int skuQuantity = (cartRow.getQuantity() == null || cartRow.getQuantity() < 1) ? 1 : cartRow.getQuantity() + cartSku.getQuantity();
				cartRow.setQuantity(skuQuantity);
				soShoppingCartSkuWebDao.update(cartRow);
				cartSku.setQuantity(skuQuantity);//同步购物车
			} else {
				cartSku.setCurrentPrice(this.getVipPrice(userDto, cartSku.getSkuCode()));
				cartSku.setId(null);// 清空原有id,采用mysql数据库的id生成规则,产生新的id
				cartSku.setMemberId(memberId);
				soShoppingCartSkuWebDao.add(cartSku);
			}
		}
	}
	
	private BigDecimal getVipPrice(UserDto userDto,String skuCode){
		
		SkuPrice skuPrice = skuPriceService.findBySkuCode(skuCode);
		if(StringUtils.isNotBlank(userDto.getAlabaoSid())){
			
			if(skuPrice !=null && skuPrice.getAladingPrice()!=null && skuPrice.getAladingPrice().compareTo(BigDecimal.ZERO)==1){
			
				return skuPrice.getAladingPrice();
			}
		}
		
		return skuPrice.getCurrentPrice();
		
	}

	

	/**
	 * 获取购物车总大小
	 * 
	 * @param visitKey
	 * @return long
	 */
	public long getCartSize(String visitKey) {
		Long size = redisUtil.llen(visitKey);
		size = size == null ? 0 : size;
		return size;
	}

	/**
	 * 获取购物车商品总数量
	 * 
	 * @param visitKey
	 * @return long
	 */
	public int getPnumSzie(String visitKey) {
		int num = 0;
		List<ShoppingCartSku> listTmp = this.getListPage(visitKey, 0, -1);
		if (!CollectionUtils.isEmpty(listTmp)) {
			for (ShoppingCartSku item : listTmp) {
				num += item.getQuantity();
			}
		}
		return num;
	}

	/**
	 * 向list左边追加加元素
	 * 
	 * @param visitKey
	 * @param item
	 */
	public void lpush(String visitKey, String item) {
		redisUtil.lpush(visitKey, item);
	}

	/**
	 * 判断key是否在Redis存在,存在返回ture,否则返回false
	 * 
	 * @param key
	 * @return
	 */
	public boolean isExists(String key) {
		return redisUtil.isExists(key);
	}

	
	/**
	 * 返回visitKey对应的购物车购买总数量
	 */
	public int getSumCartSkuQuantity(String visitKey){
		int quantityTotal =0;
		if(StringUtils.isBlank(visitKey)){
			return quantityTotal;
		}
		List<ShoppingCartSku> cartSkuList = this.getListPage(visitKey, 0, -1);
		if(CollectionUtils.isEmpty(cartSkuList)){
			return quantityTotal;
		}
		for (ShoppingCartSku item : cartSkuList) {
				quantityTotal = quantityTotal + item.getQuantity();
		}
		return quantityTotal;
	}

	public BigDecimal getSelectedSkuPrice(String visitKey) {
		BigDecimal currentPriceTotal = new BigDecimal(0);
		if(StringUtils.isBlank(visitKey)){
			return currentPriceTotal;
		}
		List<ShoppingCartSku> listTmp = this.getListPage(visitKey, 0, -1);
		if (!CollectionUtils.isEmpty(listTmp)) {
			for (ShoppingCartSku item : listTmp) {
				if (CommonConstant.YES.equals(item.getIsSelected())) {
					BigDecimal rowCurrentPriceTotal = item.getCurrentPrice().multiply(new BigDecimal(item.getQuantity()));
					currentPriceTotal = currentPriceTotal.add(rowCurrentPriceTotal);
				}
			}
		}
		return currentPriceTotal;
	}

	public BigDecimal getAllSkuPrice(String visitKey) {
		BigDecimal allPriceTotal = new BigDecimal(0);
		if(StringUtils.isBlank(visitKey)){
			return allPriceTotal;
		}
		List<ShoppingCartSku> listTmp = this.getListPage(visitKey, 0, -1);
		if (!CollectionUtils.isEmpty(listTmp)) {
			for (ShoppingCartSku item : listTmp) {
				BigDecimal rowCurrentPriceTotal = item.getCurrentPrice().multiply(new BigDecimal(item.getQuantity()));
				allPriceTotal = allPriceTotal.add(rowCurrentPriceTotal);
			}
		}
		return allPriceTotal;
	}

	public int getQuantityTotal(String visitKey) {
		int quantityTotal = 0;
		if(StringUtils.isBlank(visitKey)){
			return quantityTotal;
		}
		
		List<ShoppingCartSku> listTmp = this.getListPage(visitKey, 0, -1);
		if (!CollectionUtils.isEmpty(listTmp)) {
			for (ShoppingCartSku item : listTmp) {
					quantityTotal = quantityTotal + item.getQuantity();
			}
		}
		return quantityTotal;
	}

	public CartModifyAfterDto getSumCartSku(String visitKey) {
		CartModifyAfterDto  cartModifyAfterDto = new CartModifyAfterDto();
	
		BigDecimal currentPriceTotal = new BigDecimal(0);
		int quantityTotal = 0;
		List<ShoppingCartSku> listTmp = this.getListPage(visitKey, 0, -1);
		if(CollectionUtils.isEmpty(listTmp)){
			return cartModifyAfterDto;
		}
		
		for (ShoppingCartSku item : listTmp) {
			if ( StringUtils.equals(CommonConstant.YES, item.getIsSelected())) {
				BigDecimal itemCurrentPriceTotal = item.getCurrentPrice().multiply( BigDecimal.valueOf(item.getQuantity()));
				currentPriceTotal = currentPriceTotal.add(itemCurrentPriceTotal);
				quantityTotal = quantityTotal + item.getQuantity();
			}
		}
		cartModifyAfterDto.setPnumTotal(quantityTotal);
		cartModifyAfterDto.setPriceTotal(NumberFormatUtil.numberFormat(currentPriceTotal));
		
		return cartModifyAfterDto;
	}
	
	/**
	 * 更新购物车主表到Redis
	 * 
	 * @param visitKey
	 * @param cart
	 */
	public void saveCart(String visitKey, ShoppingCartSku cart) {
		if (StringUtils.isBlank(visitKey)) {
			return;
		}
		if (cart == null) {
			return;
		}
		Gson gson = new Gson();
		String cartJson = gson.toJson(cart);

		redisUtil.set(visitKey, cartJson);
	}

}