package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.SkuBrandQueryCondition;
import com.fushionbaby.common.condition.sku.SkuSearchQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.dao.SkuBrandDao;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuBrand;
import com.fushionbaby.sku.service.SkuBrandService;
import com.fushionbaby.sku.service.SkuService;
import com.fushionbaby.sku.service.search.thread.EngineSerach;

@Service
public class SkuBrandServiceImpl implements SkuBrandService<SkuBrand> {

	@Autowired
	private SkuBrandDao brandDao;
	@Autowired
	private SkuService skuService;

	public void add(SkuBrand maBrand) throws DataAccessException {
		brandDao.add(maBrand);
	}

	public SkuBrand findById(Long id) throws DataAccessException {
		return brandDao.findById(id);
	}

	public List<SkuBrand> findAll() throws DataAccessException {
		return brandDao.findAll();
	}

	public void update(SkuBrand maBrand) throws DataAccessException {
		brandDao.update(maBrand);
	}

	public void deleteById(Long id) throws DataAccessException {
		brandDao.deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.core.service.ma.MaBrandService#getListPage(com.fushionbaby
	 * .core.util.page.BasePagination)
	 */
	public BasePagination getListPage(BasePagination pageParams)
			throws DataAccessException {

		Integer total = brandDao.getTotal(pageParams.getSearchParamsMap());
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			List<SkuBrand> list = brandDao.getListPage(pageParams
					.getSearchParamsMap());
			pageParams.setResult(list);
		} else {
			pageParams.setResult(new ArrayList<SkuBrand>());
		}
		return pageParams;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuBrandService#findAllApp(java.lang.Integer,
	 * java.lang.Integer)
	 */
	public List<SkuBrand> findAllApp(Integer offset, Integer size)
			throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isShow", CommonConstant.YES);
		map.put("offset", offset);
		map.put("size", size);
		return brandDao.findAllApp(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.sku.service.SkuBrandService#findAllWeb()
	 */
	public List<SkuBrand> findAllWeb(Integer offset, Integer size)
			throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isShow", CommonConstant.YES);
		if (offset != null)
			map.put("offset", offset);
		if (size != null)
			map.put("size", size);
		return brandDao.findAllWeb(map);
	}
	
	public SkuBrand findByBrandCode(String brandCode) {
			return brandDao.findByBrandCode(brandCode);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuBrandService#findBySkuId(java.lang.Long)
	 */
	public SkuBrand findBySkuCode(String skuCode) {
		Sku sku = skuService.queryBySkuCode(skuCode);
		if(sku==null){
			return null;
		}
		String brandCode = sku.getBrandCode();
		if (brandCode != null) {
			return brandDao.findByBrandCode(brandCode);
		}
		return null;
	}
/*
 * (non-Javadoc)
 * @see com.fushionbaby.sku.service.SkuBrandService#findByCondition(com.fushionbaby.common.condition.sku.SkuBrandQueryCondition)
 */
	public List<SkuBrand> findByCondition(SkuBrandQueryCondition queryCondition)
			throws DataAccessException {
		return brandDao.findByCondition(queryCondition);
	}
	
	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.search.SkuSearchService#queryBrandBySkuSearchKey(java.lang.String)
	 */
	public List<SkuBrand> queryBrandBySkuSearchKey(String searchKey) {
		
		SkuSearchQueryCondition queryCondition = new SkuSearchQueryCondition();

		queryCondition.setSearchKey(searchKey);
		
		List<Map<String, String>> results = new EngineSerach().getDocumentsByKeyWord(queryCondition);
		
		
		Set<String> skuBrands = new HashSet<String>();
		for (Map<String, String> result : results) {
			String skuBrandCode = result.get("skuBrandCode");
			 if(StringUtils.isNotBlank(skuBrandCode)){
				 skuBrands.add(skuBrandCode);
			 }
		}
		
		if(skuBrands ==null || skuBrands.size() < 1){
			return new ArrayList<SkuBrand>();
		}
		List<String> skuBrandList = new ArrayList<String>(skuBrands);
		return brandDao.findListByBrandCodes(skuBrandList);
	}

	
	public String getNameByCode(String code) {
		String name="";
	    SkuBrand brand=this.brandDao.findByBrandCode(code);
	    if(brand!=null){
	    	name=brand.getBrandName();
	    }
		return name;
	}
}