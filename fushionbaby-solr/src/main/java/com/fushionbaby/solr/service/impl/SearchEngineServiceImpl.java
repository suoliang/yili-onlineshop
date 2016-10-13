package com.fushionbaby.solr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fushionbaby.solr.service.SearchEngineService;
import com.fushionbaby.solr.thred.EngineDelThredRun;
import com.fushionbaby.solr.thred.EngineSerach;
import com.fushionbaby.solr.thred.EngineSynThredRun;

/**
 * @description 搜索引擎接口实现
 * @author 孙涛
 * @date 2015年7月20日上午11:07:24
 */
public class SearchEngineServiceImpl implements SearchEngineService {
	private static EngineSerach serach = new EngineSerach();

	public void synEngineDocuments(Map<String, Map<String, String>> conMap) {
		Thread thread = new Thread(new EngineSynThredRun(conMap), "synThredRun");
		thread.start();
	}

	public void clearByIds(List<String> ids) {
		Thread thread = new Thread(new EngineDelThredRun(ids), "delThredRun");
		thread.start();
	}

	public Map<String, Map<String, String>> getEngineDocuments(List<String> ids) {
		return serach.getDocuments(ids);
	}

	public Map<String, Map<String, String>> getEngineDocumentsByKeyWord(
			String key) {
		return serach.getDocumentsByKeyWord(key);
	}

//	public Map<String, Map<String, String>> getDate() {
//		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
//		for (int i = 5; i > 0; i--) {
//			Map<String, String> paramMap = null;
//
//			paramMap = new HashMap<String, String>();
//			paramMap.put("skuName", "name" + i);
//			paramMap.put("skuImage", "image" + i);
//			paramMap.put("skuOther", "other" + i);
//
//			if (i == 0)
//				paramMap.put("content", "耐克 美特斯邦威 联华超 市 锐步");
//			else if (i == 1)
//				paramMap.put("content", "匹克 班尼路 大张超 市 特步");
//			else
//				paramMap.put("content", "阿迪达斯 森马 国际莲 花 安踏");
//
//			data.put("skuId" + i, paramMap);
//		}
//
//		return data;
//	}

//	public static void main(String[] args) {
//		SearchEngineServiceImpl impl = new SearchEngineServiceImpl();
//		Map<String, Map<String, String>> results = new HashMap<String, Map<String, String>>();
//		 impl.synEngineDocuments(impl.getDate());
//		 List<String> ids = new ArrayList<String>();
//		 ids.add("skuId5");
//		 ids.add("skuId4");
//		results = impl.getEngineDocumentsByKeyWord("莲花");
//		for (Entry<String, Map<String, String>> entry : results.entrySet()) {
//			System.out.println("-------------------------------------");
//			System.out.println("key : " + entry.getKey());
//			int i = 1;
//			for (Entry<String, String> entryVal : entry.getValue().entrySet()) {
//				System.out.println("values[" + i + "] name : "
//						+ entryVal.getKey());
//				System.out.println("values[" + i + "] value : "
//						+ entryVal.getValue());
//				i++;
//			}
//			System.out.println("-------------------------------------");
//		}
//		 impl.clearByIds(null);
//	}
}
