/**
 * 
 */
package com.aladingshop.sku.cms.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.SkuSearchQueryCondition;
import com.fushionbaby.common.enums.SkuStatusEnum;

/**
 * @description 搜索引擎搜索[一般搜索都是立即响应，故不单独走线程]
 * @author 孙涛
 * @date 2015年7月20日下午1:51:31
 */
@Service
public class EngineSerach extends BaseThredRun {
	private static final Log logger = LogFactory.getLog(EngineSerach.class);

	public Map<String, Map<String, String>> getDocuments(List<String> ids,Integer pageSize,Integer curPage) {
		Map<String, Map<String, String>> results = new HashMap<String, Map<String, String>>();
		HttpSolrServer server = getServer();
		String queryStr = "*:*";
		int rows = pageSize==null?10:pageSize;
		int index = curPage==null?1:curPage;
		
		if (ids != null) {
			for (String id : ids) {
				queryStr += "id:" + id + "\tOR\t";
			}
			queryStr = queryStr.substring(0, queryStr.length() - 3);
		}

		try {
			SolrQuery query = new SolrQuery();
			
			//分页设置
			query.setStart(rows * (index - 1));
			query.setRows(rows);
			query.setQuery(queryStr);
			
			// 设置高亮参数
			query.setHighlight(true); // 开启高亮组件
			query.addHighlightField("skuName");// 高亮字段
			query.setHighlightSimplePre("<span color='red'>");// 前缀标记
			query.setHighlightSimplePost("</span>");// 后缀标记

			QueryResponse res = server.query(query);
			SolrDocumentList documentList = res.getResults();
			for (SolrDocument document : documentList) {
				Map<String, String> fields = new HashMap<String, String>();
				String id = "";
				for (Entry<String, Object> entry : document.entrySet()) {
					if (!StringUtils.equals(entry.getKey(), "id")){
						fields.put(entry.getKey(),
								String.valueOf(entry.getValue()));
					}
					else{
						id = String.valueOf(entry.getValue());
					}
				}
				results.put(id, fields);
			}

		} catch (Exception e) {
			logger.error("搜索引擎查询失败：" + e);
			return null;
		}

		return results;
	}

	public List<Map<String, String>> getDocumentsByKeyWord(SkuSearchQueryCondition queryCondition) {
		
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		HttpSolrServer server = getServer();

		try {
			SolrQuery query = new SolrQuery();
			if(queryCondition !=null ){
				StringBuffer strQuery = new StringBuffer();
				String searchKey = queryCondition.getSearchKey();
				if(StringUtils.isNotBlank(searchKey)){
					strQuery.append(searchKey);
				}
				if(StringUtils.isNotBlank(queryCondition.getCategoryCode())){
					if(StringUtils.isNotBlank(strQuery.toString())){
						strQuery.append(" AND ");
					}
					strQuery.append("skuCategoryCode:"+queryCondition.getCategoryCode()+"*");
				}
				if(StringUtils.isNotBlank(queryCondition.getBrandCode())){
					if(StringUtils.isNotBlank(strQuery.toString())){
						strQuery.append(" AND ");
					}
					strQuery.append("skuBrandCode:"+queryCondition.getBrandCode());
				}
				
				query.setQuery(strQuery.toString());
				//分页设置
				if(queryCondition.getStart() !=null){
					query.setStart((queryCondition.getStart() - 1 ) * queryCondition.getLimit());
				}
				if(queryCondition.getLimit() !=null){
					query.setRows(queryCondition.getLimit());
				}
				
				// 设置价格范围
				//query.set("fq", "skuPrice:[1 TO 20]");
				if(StringUtils.isNotBlank(queryCondition.getPrice())){
					String price = queryCondition.getPrice();
					String beginPrice = price.split("-")[0];
					beginPrice = StringUtils.isBlank(beginPrice) ? "0" : beginPrice;
					String endPrice = price.split("-")[1];
					endPrice = StringUtils.isBlank(endPrice) ? Integer.MAX_VALUE+"" : endPrice;
					query.set("fq", "skuPrice:["+beginPrice+" TO "+endPrice+"]");
				}
				
			
				if(StringUtils.equals("price", queryCondition.getSortParam())){
					if(StringUtils.equals("desc", queryCondition.getSortType())){
						query.addSort("skuPrice",ORDER.desc);
					}else{
						query.addSort("skuPrice",ORDER.asc);
					}
				}
				if(StringUtils.equals("sales",queryCondition.getSortParam() )){
//					query.addSort("salesVolume",ORDER.desc);
//					query.addSort("actualSalesVolume",ORDER.desc);
					query.set("sort", "salesVolume desc,actualSalesVolume desc");
				}
			}
			query.set("skuStatus", SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
			// 设置高亮参数
			query.setHighlight(true); // 开启高亮组件
			query.addHighlightField("skuName");// 高亮字段
			query.setHighlightSimplePre("<span color='red'>");// 前缀标记
			query.setHighlightSimplePost("</span>");// 后缀标记
			
			QueryResponse res = server.query(query);
			
			SolrDocumentList documentList = res.getResults();
			
			for (SolrDocument document : documentList) {
				Map<String, String> fields = new HashMap<String, String>();
				for (Entry<String, Object> entry : document.entrySet()) {
					fields.put(entry.getKey(),
							String.valueOf(entry.getValue()));
				}
				results.add(fields);
			}
			Map<String, String> numFound = new HashMap<String, String>();
			numFound.put("numFound", documentList.getNumFound()+"");
			results.add(numFound);
		} catch (Exception e) {
			logger.error("搜索引擎查询失败：" + e);
			return null;
		}

		return results;
	}
	
}
