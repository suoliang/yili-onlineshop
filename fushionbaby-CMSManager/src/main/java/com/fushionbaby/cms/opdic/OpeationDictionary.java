package com.fushionbaby.cms.opdic;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.config.model.SysmgrDictConfig;
import com.fushionbaby.config.service.SysmgrDictConfigService;

/**
 * 操作字典表
 * 
 * @author sun tao 2015年6月29日
 */
public class OpeationDictionary {
	@SuppressWarnings("unchecked")
	private final static SysmgrDictConfigService<SysmgrDictConfig> dictConfigService = SpringContextHolder
			.getBean(SysmgrDictConfigService.class);

	private static final RedisUtil REDIS_UTIL = new RedisUtil();

	public static String getDictLabel(String value, String type,
			String defaultValue) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
			for (SysmgrDictConfig dict : getDictionaryByType(type)) {
				if (type.equals(dict.getType())
						&& value.equals(dict.getValue())) {
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictDesc(String value, String type,
			String defaultValue) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
			for (SysmgrDictConfig dict : getDictionaryByType(type)) {
				if (type.equals(dict.getType())
						&& value.equals(dict.getValue())) {
					return dict.getDescription();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type,
			String defaultLabel) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
			for (SysmgrDictConfig dict : getDictionaryByType(type)) {
				if (type.equals(dict.getType())
						&& label.equals(dict.getLabel())) {
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}

	public static List<SysmgrDictConfig> getDictionaryByType(String type) {
//		Gson gson = new Gson();
//		String disAbleKey = type + "_KEY".toUpperCase();
//		String jsonMap = REDIS_UTIL.hget("DICTIONARY", disAbleKey);
		// 判断当前key是否存在于缓存池
//		if (StringUtils.isNotBlank(jsonMap)) {
//			// 存在直接取值并转换(获取的值为JSON字符串)
//			List<SysmgrDictConfig> dicList = gson.fromJson(jsonMap,
//					new TypeToken<List<SysmgrDictConfig>>() {
//					}.getType());
//
//			return dicList;
//		}

		// 存在则需查询数据库后并存储到REDIS缓存池
		List<SysmgrDictConfig> dicList = dictConfigService
				.findByLabelValueType(null, null, type);
		if (dicList == null || dicList.size() == 0) {
			return null;
		}

		// map转换为JSON字符串
//		REDIS_UTIL.hset("DICTIONARY", disAbleKey, gson.toJson(dicList));

		return dicList;
	}
}
