/**
 * 
 */
package com.fushionbaby.solr.thred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fushionbaby.solr.service.impl.SearchEngineServiceImpl;

/**
 * @description 搜索引擎同步操作
 * @author 孙涛
 * @date 2015年7月20日上午11:20:09
 */
@SuppressWarnings({ "unused", "deprecation" })
public class EngineSynThredRun extends BaseThredRun implements Runnable {
	private static final Log logger = LogFactory
			.getLog(EngineSynThredRun.class);

	private Map<String, Map<String, String>> conMap;

	public EngineSynThredRun(Map<String, Map<String, String>> map) {
		this.conMap = map;
	}

	public void run() {
		/**
		 * 同步DOCUMENT内容
		 */
		HttpSolrServer server = getServer();
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		try {
			for (Entry<String, Map<String, String>> entry : conMap.entrySet()) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", entry.getKey());
				for (Entry<String, String> entryElement : entry.getValue()
						.entrySet()) {
					doc.addField(entryElement.getKey(), entryElement.getValue());
				}
				server.add(doc);
				server.commit();
			}
		} catch (Exception e) {
			logger.error("同步信息到搜索引擎异常：" + e);
			try {
				server.rollback();
			} catch (Exception e1) {
				logger.error("搜索引擎回滚同步操作异常：" + e1);
			}
			return;
		} finally {
			server.shutdown();
		}
	}

}
