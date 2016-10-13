package com.fushionbaby.sku.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sku.model.SkuGiftCard;

/***
 * 礼品券
 * @author xupeijun
 *
 */
public interface SkuGiftCardDao  {
	void add(SkuGiftCard giftCard);
	void deleteById(Long id);
	void update(SkuGiftCard giftCard);
	SkuGiftCard findById(Long id);
	List<SkuGiftCard> findAll();
    /***
     *改变礼品卡的状态
     * @param map
     */
	void updateStatus(Map<String, Object> map);
	/***
	 * 得到所有的未售出的礼品卡（商品展示）
	 * @return list
	 */
	List<SkuGiftCard> listUnSaleSkuGiftCard();
	
	Integer getTotal(Map<String, Object> searchParamsMap);
	
	List<SkuGiftCard> getListPage(Map<String, Object> searchParamsMap);
	/***
	 * 通过code得到礼品卡商品的库存
	 * @param code
	 * @return
	 */
	Integer getGiftCardInventoryByCode(String code);
	
	String getCurrentCode(String dateStr);
	
	SkuGiftCard findByCardNo(String cardNo); 
	
}
