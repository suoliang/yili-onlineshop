package com.fushionbaby.solr.service;

import java.util.List;
import java.util.Map;

/**
 * 搜索引擎接口
 * 
 * @author sun tao 2015年7月20日
 */
public interface SearchEngineService {
	/**
	 * 同步信息到搜索引擎[其中key为当前元素唯一标识,要作为id使用，且要有content字段，值为当前节点搜索的一些关键字、词]
	 * 
	 * @param engineName
	 * @param conMap
	 */
	public void synEngineDocuments(Map<String, Map<String, String>> conMap);

	/**
	 * 根据id删除相关DOUMENTS[如果list为null，则清空全部]
	 * 
	 * @param engineName
	 * @param ids
	 */
	public void clearByIds(List<String> ids);

	/**
	 * 根据id获取相关DOCUMENTS[如果list为null，则获取全部]
	 * 
	 * @param ids
	 * @return
	 */
	public Map<String, Map<String, String>> getEngineDocuments(List<String> ids);

	/**
	 * 关键字搜索
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, Map<String, String>> getEngineDocumentsByKeyWord(
			String key);

}
