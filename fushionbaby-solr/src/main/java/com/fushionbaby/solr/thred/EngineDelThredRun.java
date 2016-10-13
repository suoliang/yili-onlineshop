/**
 * 
 */
package com.fushionbaby.solr.thred;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * @description 搜索引擎删除操作
 * @author 孙涛
 * @date 2015年7月20日下午1:13:43
 */
@SuppressWarnings("deprecation")
public class EngineDelThredRun extends BaseThredRun implements Runnable {
	private static final Log logger = LogFactory
			.getLog(EngineDelThredRun.class);

	private List<String> ids;

	public EngineDelThredRun(List<String> list) {
		this.ids = list;
	}

	public void run() {
		/**
		 * 删除DOCUMENT内容
		 */
		HttpSolrServer server = getServer();

		try {
			if (ids == null)
				server.deleteByQuery("*:*"); // 如果集合为null删全部
			else
				server.deleteById(ids);

			server.commit();
		} catch (Exception e) {
			logger.error("同步信息到搜索引擎异常：" + e);
			try {
				server.rollback();
			} catch (Exception e1) {
				logger.error("搜索引擎回滚删除操作异常：" + e1);
			}
			return;
		} finally {
			server.shutdown();
		}

	}

}
