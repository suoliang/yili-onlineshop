package com.fushionbaby.common.util;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 分页自动计算limit上限下限 前提必须继承RequestBase类
 * 
 * @author sun tao 2015年7月14日
 */
public class CalcPageUtil {
	public static void setTotalPageByObject(Object obj,
			Map<String, Object> paramMap) {
		/** 对象转JSON **/
		Gson gson = new Gson();
		String jsonStr = gson.toJson(obj);
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(jsonStr);

		Integer totalPage = (json.getAsJsonObject().get("total").getAsInt() - 1)
				/ json.getAsJsonObject().get("pageSize").getAsInt() + 1;
		Integer size = json.getAsJsonObject().get("pageSize").getAsInt();

		/** 跳转页max、min校验 **/
		Integer targetPage = json.getAsJsonObject().get("pageIndex").getAsInt();
		targetPage = targetPage > totalPage ? totalPage : targetPage;
		targetPage = targetPage < 1 ? 1 : targetPage;

		paramMap.put("start", (targetPage - 1) * size);
		paramMap.put("limit", size);
	}
}
