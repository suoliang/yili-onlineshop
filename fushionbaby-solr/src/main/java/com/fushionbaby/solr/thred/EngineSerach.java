/**
 * 
 */
package com.fushionbaby.solr.thred;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

/**
 * @description 搜索引擎搜索[一般搜索都是立即响应，故不单独走线程]
 * @author 孙涛
 * @date 2015年7月20日下午1:51:31
 */
@SuppressWarnings({ "unused", "deprecation" })
@Service
public class EngineSerach extends BaseThredRun {
	private static final Log logger = LogFactory.getLog(EngineSerach.class);

	public Map<String, Map<String, String>> getDocuments(List<String> ids) {
		Map<String, Map<String, String>> results = new HashMap<String, Map<String, String>>();
		HttpSolrServer server = getServer();
		String queryStr = "*:*";

		if (ids != null) {
			for (String id : ids) {
				queryStr += "id:" + id + "\tOR\t";
			}
			queryStr = queryStr.substring(0, queryStr.length() - 3);
		}

		try {
			SolrQuery query = new SolrQuery();
			query.setQuery(queryStr);
			QueryResponse res = server.query(query);
			SolrDocumentList documentList = res.getResults();
			for (SolrDocument document : documentList) {
				Map<String, String> fields = new HashMap<String, String>();
				String id = "";
				for (Entry<String, Object> entry : document.entrySet()) {
					if (!Objects.equal(entry.getKey(), "id"))
						fields.put(entry.getKey(),
								String.valueOf(entry.getValue()));
					else
						id = String.valueOf(entry.getValue());
				}
				results.put(id, fields);
			}

		} catch (Exception e) {
			logger.error("搜索引擎查询失败：" + e);
			return null;
		}

		return results;
	}

	public Map<String, Map<String, String>> getDocumentsByKeyWord(String key) {
		Map<String, Map<String, String>> results = new HashMap<String, Map<String, String>>();
		HttpSolrServer server = getServer();

		try {
			SolrQuery query = new SolrQuery();
			query.setQuery(key);
			QueryResponse res = server.query(query);
			SolrDocumentList documentList = res.getResults();
			for (SolrDocument document : documentList) {
				Map<String, String> fields = new HashMap<String, String>();
				String id = "";
				for (Entry<String, Object> entry : document.entrySet()) {
					if (!Objects.equal(entry.getKey(), "id"))
						fields.put(entry.getKey(),
								String.valueOf(entry.getValue()));
					else
						id = String.valueOf(entry.getValue());
				}
				results.put(id, fields);
			}
		} catch (Exception e) {
			logger.error("搜索引擎查询失败：" + e);
			return null;
		}

		return results;
	}
}
