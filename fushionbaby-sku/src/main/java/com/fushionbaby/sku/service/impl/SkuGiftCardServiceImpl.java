package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.dao.SkuGiftCardDao;
import com.fushionbaby.sku.model.SkuGiftCard;
import com.fushionbaby.sku.service.SkuGiftCardService;

/***
 * 礼品卡的实现
 * @author xupeijun
 *
 */
@Service
public class SkuGiftCardServiceImpl implements SkuGiftCardService<SkuGiftCard> {

	
	@Autowired
	private SkuGiftCardDao giftCardDao;
	
	
	public void add(SkuGiftCard entity) {
		giftCardDao.add(entity);
		
	}

	public void deleteById(Long id) {
		giftCardDao.deleteById(id);
	}

	public void update(SkuGiftCard entity) {
		giftCardDao.update(entity);
	}

	public SkuGiftCard findById(Long id)  {
		return giftCardDao.findById(id);
	}

	public List<SkuGiftCard> findAll() {
		return giftCardDao.findAll();
	}

	public void updateStatus(Map<String, Object> map) {
		giftCardDao.updateStatus(map);
	}

	public List<SkuGiftCard> listUnSaleSkuGiftCard() {
		return giftCardDao.listUnSaleSkuGiftCard();
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.giftCardDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuGiftCard> list = this.giftCardDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuGiftCard>());
		}
		return page;
	}
	
	public String[] getNewCode(String dateStr, int count) {
		String curCode = giftCardDao.getCurrentCode(dateStr);
		String[] codes = new String[count];
		int tmpIndex = 0;
		if(curCode != null) {
			tmpIndex = Integer.valueOf(curCode.split(dateStr)[1]);
		}
		StringBuilder newCode;
		for(int i=0; i<codes.length; i++) {
			tmpIndex += 1;
			newCode = new StringBuilder().append(dateStr);
			for(int j=0; j<6-String.valueOf(tmpIndex).length(); j++) {
				newCode.append("0");
			}
			codes[i] = newCode.append(String.valueOf(tmpIndex)).toString();
		}
		return codes;
	}
	
	public SkuGiftCard useCard(String cardNo) {
		return giftCardDao.findByCardNo(cardNo);
	}
	
}
