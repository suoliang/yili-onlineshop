package com.aladingshop.sku.cms.service;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuGiftCard;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/***
 * 礼品卡
 * @author xupeijun
 *
 */
public interface SkuGiftCardService <T extends SkuGiftCard> extends BaseService<T> {
	
	List<SkuGiftCard> findAll();
	
	/***
	 * 改变礼品卡的状态
	 * @param map
	 */
   void updateStatus(Map<String, Object> map);
	  /**
	   * 得到可作为商品的礼品卡（未售出的礼品卡）
	   * @return list
	   */
	List<SkuGiftCard> listUnSaleSkuGiftCard();
	
	BasePagination getListPage(BasePagination page);
	
	String[] getNewCode(String dateStr, int count);
	
	SkuGiftCard useCard(String cardNo);
	
}
