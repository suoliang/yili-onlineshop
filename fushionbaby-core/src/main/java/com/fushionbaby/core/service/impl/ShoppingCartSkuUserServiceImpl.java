package com.fushionbaby.core.service.impl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.PageConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.dao.ShoppingCartSkuUserDao;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.SkuService;

/**
 * @author 张明亮 购物车行
 */
@Service
public class ShoppingCartSkuUserServiceImpl implements ShoppingCartSkuUserService<ShoppingCartSku> {

	@Autowired
	private ShoppingCartSkuUserDao soShoppingCartSkuWebDao;
	@Autowired
	private SkuService skuService;
	 

	/**
	 * 为购物车添加一件需要购买的商品
	 * 
	 * @param shoppingCartSku
	 */
	public void add(ShoppingCartSku entity) throws DataAccessException {

		soShoppingCartSkuWebDao.add(entity);
	}

	/**
	 * 通过会员id查询购物车中已经勾选的商品行记录
	 * @param memberId
	 * @return List<SoShoppingCartSkuUser>
	 */
	public List<ShoppingCartSku> findSelectedCartSkuByMemberId(Long memberId) {
		return soShoppingCartSkuWebDao.findSelectedCartSkuByMemberId(memberId);
	}

	/**
	 * 通过会员id查询购物车中已经勾选的商品行记录中的sku_id列表
	 * @param memberId
	 * @return List<String>
	 */
	public List<String> findCartSkuSelectedSkuCode(ShoppingCartQueryCondition queryCondition){
		return soShoppingCartSkuWebDao.findCartSkuSelectedSkuCode(queryCondition);
	}
	
	/**
	 * 根据购物车id,查询会员购物车所有商品,分页查询
	 * 
	 * @param shoppingCardId
	 * @throws
	 */
	public List<ShoppingCartSku> getListPage(Long memberId, Integer pageIndex, Integer pageSize) {
		int index = (pageIndex == null || pageIndex < 1) ? PageConstant.APP_PAGE_INDEX : pageIndex;
		int size = (pageSize == null || pageSize < 1) ? PageConstant.APP_PAGE_SIZE : pageSize;
		
		List<ShoppingCartSku> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("start", (index - 1) * size);
		map.put("limit", size);
		list = soShoppingCartSkuWebDao.getListPage(map);
		return list;
	}
	
	public List<ShoppingCartSku> findByMemberId(Long memberId) {
		
		return soShoppingCartSkuWebDao.findByMemberId(memberId);
	}
	

	/**
	 * 根据购物车行记录id,查询购物车下一件商品
	 * 
	 * @param id
	 * @return
	 */
	public ShoppingCartSku findById(Long id) {
		return soShoppingCartSkuWebDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.core.service.SoShoppingCartSkuWebUserService#getAllSkuPrice(java.lang.Long)
	 */
	public BigDecimal getAllSkuPrice(Long memberId) {
		List<ShoppingCartSku> cartSkuUserList = this.getListPage(memberId, WebConstant.START_INDEX, CartConstant.CARTSIZE);
		BigDecimal totalPrice = new BigDecimal(0);
		for(ShoppingCartSku cartSkuUser : cartSkuUserList){
			BigDecimal lineTotalPrice = cartSkuUser.getCurrentPrice().multiply(new BigDecimal(cartSkuUser.getQuantity()));
			totalPrice = totalPrice.add(lineTotalPrice);
		}
		return totalPrice;
	}
	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.core.service.ShoppingCartSkuUserService#update(com.fushionbaby.core.model.ShoppingCartSkuUser)
	 */
	public void update(ShoppingCartSku soShoppingCartSku) {
		
		soShoppingCartSkuWebDao.update(soShoppingCartSku);
	}

	public List<ShoppingCartSku> findByQueryCondition(
			ShoppingCartQueryCondition queryCondition) {
		return soShoppingCartSkuWebDao.findByQueryCondition(queryCondition);
	}

	public ShoppingCartSku findBySkuCodeAndMemberId(String skuCode,
			Long memberId) {
		
		Sku sku = skuService.queryBySkuCode(skuCode);
		if(sku==null){
			return null;
		}
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setSkuCode(skuCode);
		queryCondition.setMemberId(memberId);
		queryCondition.setStoreCode(sku.getStoreCode());
		
		List<ShoppingCartSku> results = this.findByQueryCondition(queryCondition);
		if(results!=null && results.size() > 0){
			return results.get(0);
		}
		
		return null;
	}

	public void deleteById(Long id) {
		soShoppingCartSkuWebDao.deleteById(id);		
	}


	
}
