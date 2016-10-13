/**
 * 
 */
package com.fushionbaby.sku.service.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.condition.sku.SkuSearchQueryCondition;
import com.fushionbaby.sku.dao.SkuBrandDao;
import com.fushionbaby.sku.dto.SkuSearchDto;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuBrand;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuService;
import com.fushionbaby.sku.service.search.thread.BaseThredRun;
import com.fushionbaby.sku.service.search.thread.EngineSerach;
import com.fushionbaby.sku.service.search.thread.EngineSynThredRun;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月24日上午10:00:14
 */
@Service
public class SkuSearchServiceImpl implements SkuSearchService {
	
	@Autowired
	private SkuService skuService;
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	@Autowired
	private SkuBrandDao brandDao;
	
	public void addSkuDocument() {
		List<SkuSearchDto> skuList = skuService.queryBySameDayOperateing();
		Map<String, Map<String, Object>> data = new HashMap<String, Map<String, Object>>();
		Map<String,Object> map = null;
		for (SkuSearchDto sku : skuList) {
			map = new HashMap<String, Object>();
			map.put("skuCode",sku.getSkuCode());
			map.put("skuName", sku.getSkuName());
			map.put("skuKeyWords",sku.getKeyWords().toArray());
			map.put("skuLabel", sku.getSkuLabel());
			map.put("skuDescription", sku.getSkuDescription());
			map.put("skuBrand", sku.getSkuBrand());
			map.put("skuBrandCode",sku.getSkuBrandCode());
			map.put("skuCategoryCode",sku.getSkuCategoryCode());
			map.put("skuCategory", sku.getSkuCategory());
			map.put("skuPrice", sku.getSkuPrice());
			map.put("salesVolume", sku.getSalesVolume());
			map.put("actualSalesVolume", sku.getActualSalesVolume());
			map.put("onshelvestime", sku.getOnshelvestime());
			map.put("skuStatus", sku.getSkuStatus());
			List<String> content = new ArrayList<String>();
			content.addAll(sku.getKeyWords());
			content.add(sku.getSkuBrand());
			content.add(sku.getSkuCategory());
			
			map.put("content", content);
			data.put("sku"+sku.getSkuCode(), map);
		}
				
		Thread thread = new Thread(new EngineSynThredRun(data), "synEngineThread");
		thread.start();
		
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.search.SkuSearchService#querySkuDtoList(com.fushionbaby.common.condition.sku.SkuEntryQueryCondition)
	 */
	public List<Sku> querySkuList(
			SkuSearchQueryCondition queryCondition) {
		
		List<Map<String, String>> results = new EngineSerach().getDocumentsByKeyWord(queryCondition);
		
		List<String> skuCodes = new ArrayList<String>();
		for (Map<String, String> result : results) {
			String skuCode = result.get("skuCode");
			
			skuCodes.add(skuCode);
		}
		if(CollectionUtils.isEmpty(skuCodes) || skuCodes.size()<1){
			return new ArrayList<Sku>();
		}
		List<Sku> skuList = new ArrayList<Sku>();
		for (String skuCode : skuCodes) {
			Sku sku = skuService.queryBySkuCode(skuCode);
			skuList.add(sku);
		}
		return skuList;
	}

	
	
	public  List<Map<String,Object>> autoComplete(String prefix, int min) {

		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();

        SolrQuery query = new SolrQuery();

        QueryResponse rsp= new QueryResponse();
        
        //Facet为solr中的层次分类查询
        try {
        	
           SolrServer server = new BaseThredRun().getServer();  
        	
           query.setFacet(true);
           query.setQuery(prefix);
           query.setFacetPrefix(prefix);
           query.addFacetField("skuKeyWords");
           rsp = server.query(query);

        } catch (Exception e) {
               e.printStackTrace();
               return null;
        }
        
        List<FacetField> facets= rsp.getFacetFields();
        
        
        for (FacetField facet : facets) {
        	List<Count> counts = facet.getValues();
        	for (Count count : counts) {
        		Map<String, Object> map = new HashMap<String, Object>(); 
        		map.put("count", count.getCount()) ;
        		map.put("name", count.getName());
        		result.add(map);
			}
		}

		return result;

    }
	
	
	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuService#queryBySkuSearchKey(java.lang.String)
	 */
	public SkuSearchDto queryResByCondition(SkuSearchQueryCondition queryCondition) {
		
		SkuSearchDto skuSearch = new SkuSearchDto();
		
		List<Map<String, String>> results = new EngineSerach().getDocumentsByKeyWord(queryCondition);
		
		Set<String> skuBrands = new HashSet<String>();
		Set<String> skuCategorys = new HashSet<String>();
		Long numFound = 0L;
		for (Map<String, String> result : results) {
			 String skuBrandCode = result.get("skuBrandCode");
			 String skuCategoryCode = result.get("skuCategoryCode");
			 if(StringUtils.isNotBlank(skuBrandCode)){
				 skuBrands.add(skuBrandCode);
			 }
			 if(StringUtils.isNotBlank(skuCategoryCode)){
				 skuCategorys.add(skuCategoryCode);
			 }
			String numFoundStr = result.get("numFound");
			if(numFoundStr!=null){
				numFound = Long.valueOf(numFoundStr);
			}
			 
		}
		skuSearch.setNumFound(numFound);
		
		List<String> skuBrandCodes = new ArrayList<String>(skuBrands);
		List<SkuBrand> brandList = new ArrayList<SkuBrand>();
		if(!CollectionUtils.isEmpty(skuBrandCodes)){
			brandList = brandDao.findListByBrandCodes(skuBrandCodes);
		}
		
		if(!CollectionUtils.isEmpty(skuBrandCodes)){
			skuSearch.setBrandList(brandList);
		}
		List<SkuCategory> categoryList = this.getCategoryList(
				new ArrayList<String>(skuCategorys),
				queryCondition.getCategoryCode());
		skuSearch.setCategoryList(categoryList);
		return skuSearch;
	}
	
	/**
	 * 获取分类集合
	 * @param skuCategoryCodes 从solr拿出的分类集合
	 * @param categoryCode 查询条件中的分类
	 * @return
	 */
	private List<SkuCategory> getCategoryList(List<String> skuCategoryCodes,String categoryCode){
		
		Set<SkuCategory> categoryList = new HashSet<SkuCategory>();
		if(CollectionUtils.isEmpty(skuCategoryCodes) && StringUtils.isNotBlank(categoryCode)){
			return skuCategoryService.getIdenticalCategory(StringUtils.EMPTY,categoryCode);
		}
		
		for (String code : skuCategoryCodes) {
			if(StringUtils.isBlank(code)){
				continue;
			}
			List<SkuCategory> identicalCategory = skuCategoryService.getIdenticalCategory(StringUtils.EMPTY,code);
			categoryList.addAll(identicalCategory);
		}
		
		
		return this.removeRepeatData(categoryList);
		
	}

	/**
	 * 去除重复的数据
	 * @param categoryList
	 * @return
	 */
	private List<SkuCategory> removeRepeatData(Set<SkuCategory> categoryList){
		
		Map<String, SkuCategory> map = new HashMap<String, SkuCategory>();
		for(SkuCategory result : categoryList){
			map.put(result.getCode(), result);
		}
		List<SkuCategory> results = new ArrayList<SkuCategory>();
		Set<String> set  = map.keySet();
		for (String code : set) {
			results.add(map.get(code));
		}
		
		return results;
	}

	
}
