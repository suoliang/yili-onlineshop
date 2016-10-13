/**
 * 
 */
package com.aladingshop.sku.cms.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class EngineSynThredRun extends BaseThredRun implements Runnable {
	private static final Log logger = LogFactory.getLog(EngineSynThredRun.class);

	private Map<String, Map<String, Object>> conMap;

	public EngineSynThredRun(Map<String, Map<String, Object>> map) {
		this.conMap = map;
	}

	public void run() {
		HttpSolrServer server = getServer();
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		try {
			for (Entry<String, Map<String, Object>> entry : conMap.entrySet()) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", entry.getKey());
				for (Entry<String, Object> entryElement : entry.getValue()
						.entrySet()) {
					doc.addField(entryElement.getKey(), entryElement.getValue());
				}
				docs.add(doc);
//				server.add(doc);
//				server.commit();
			}
			if(docs.size() > 0){
				server.add(docs);
				server.commit();
			}
		} catch (Exception e) {
			logger.error("ͬ" + e);
			e.printStackTrace();
			try {
				server.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("" + e1);
			}
			return;
		} finally {
			server.shutdown();
		}
	}

}
