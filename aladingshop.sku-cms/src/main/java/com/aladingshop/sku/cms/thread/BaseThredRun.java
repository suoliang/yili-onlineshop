/**
 * 
 */
package com.aladingshop.sku.cms.thread;

import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * @description 搜索引擎操作父类
 * @author 孙涛
 * @date 2015年7月20日下午1:28:38
 */
public class BaseThredRun {

	public HttpSolrServer getServer() {
		return new HttpSolrServer(PropertiesUitls.getValByKey("solr-url"));
	}
}
