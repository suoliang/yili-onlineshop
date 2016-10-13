package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuBrandDao;
import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuBrand;
import com.aladingshop.sku.cms.service.SkuBrandService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.fushionbaby.common.condition.sku.SkuBrandQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;

@Service
public class SkuBrandServiceImpl implements SkuBrandService<SkuBrand> {

	@Autowired
	private SkuBrandDao brandDao;
	@Autowired
	private SkuInfoService skuService;

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
	public SkuBrand findBySkuUnCode(String skuUnCode) {
		Sku sku = skuService.queryByUniqueCode(skuUnCode);
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
}