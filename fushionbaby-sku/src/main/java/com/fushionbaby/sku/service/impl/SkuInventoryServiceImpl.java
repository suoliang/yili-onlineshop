package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.SkuInventoryQueryCondition;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.dao.SkuInventoryDao;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuInventoryService;

/**
 * @author mengshaobo
 * 
 *         索亮20141101更新
 */
@Service
public class SkuInventoryServiceImpl implements SkuInventoryService<SkuInventory> {

	@Autowired
	private SkuInventoryDao skuInventoryDao;

	public void add(SkuInventory skuInventory) throws DataAccessException {
		skuInventoryDao.add(skuInventory);
	}

	public void deleteById(Long id) throws DataAccessException {
		skuInventoryDao.deleteById(id);
	}

	public void update(SkuInventory skuInventory) throws DataAccessException {
		skuInventoryDao.update(skuInventory);
	}

	/** 查询库存信息，修改时加载数据 */
	public SkuInventory findById(Long id) throws DataAccessException {
		return skuInventoryDao.findById(id);
	}

	/** 暂时未使用到，空实现 */
	public List<SkuInventory> findAll() throws DataAccessException {
		return null;
	}

	/** 分页 */
	public BasePagination getListPage(BasePagination page) throws DataAccessException {
		Integer total = skuInventoryDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuInventory> list = skuInventoryDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuInventory>());
		}
		return page;
	}

	/**
	 * 根据商品的skuCode,购买数量quantity,<br/>
	 * 验证系统是否还有剩余库存,存在余货,返回true,库存剩余不足或者没有了返回false
	 * 
	 * @param sku_code
	 * @param quantity
	 * @return boolean
	 * @author 张明亮
	 */
	public boolean verifyInventory(String skuCode, int quantity) {
		boolean answer = true;// 回答库存是否充足,默认库存充足
		Integer quantityNumber = skuInventoryDao.getInventoryQuantity(skuCode);
		if (quantityNumber != null && quantityNumber >= quantity) {
			answer = true;// 货物储备够,或者刚刚好
		} else {
			answer = false;// 货物储备不够
		}

		return answer;
	}

	/**
	 * 根据商品Id得到该商品的库存信息
	 * 
	 * @param skuId
	 * @return
	 * @author 张明亮
	 */
	public SkuInventory findBySkuCode(String skuCode) {
		return skuInventoryDao.findBySkuCode(skuCode);
	}
	
	/**
	 * 增加库存数量
	 * @param skuId
	 * @param quantity
	 */
	public void addInventoryQuantity(String skuCode, int quantity){
		SkuInventory skuInventory = skuInventoryDao.findBySkuCode(skuCode);
		if(skuInventory==null){
			return ;
		}
		skuInventory.setAvailableQty(skuInventory.getAvailableQty() + quantity);
		skuInventoryDao.update(skuInventory);
	}

	/**
	 * 更新库存数量
	 * @param skuId
	 * @param quantity
	 */
	public void updateInventoryQuantity(String skuCode, int quantity){
		SkuInventory skuInventory = skuInventoryDao.findBySkuCode(skuCode);
		if(skuInventory==null){
			return ;
		}
		skuInventory.setAvailableQty(quantity);
		skuInventoryDao.update(skuInventory);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuInventoryService#queryByCondition(com.
	 * fushionbaby.sku.condition.SkuInventoryQueryCondition)
	 */
	public List<SkuInventory> queryByCondition(SkuInventoryQueryCondition queryCondition) {
		return skuInventoryDao.queryByCondition(queryCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuInventoryService#querySkuventory(java.
	 * lang.String, java.lang.String, java.lang.String)
	 */
	public SkuInventory querySkuIventory(String productCode, String color, String size) {
		return skuInventoryDao.querySkuIventory(productCode, color, size);
	}

	public void addOrUpdate(SkuInventory skuInventory) {
		if(skuInventory.getId()==null){
			this.add(skuInventory);
			return;
		}
		this.update(skuInventory);
	}
}
