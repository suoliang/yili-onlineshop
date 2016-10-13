/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月10日下午5:38:48
 */
package com.fushionbaby.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月10日下午5:38:48
 */
public enum RedEnvelopeUseTypeEnum {
	
	
	CONSUME("消费","1"),
	TRANSFER_ACCOUNTS("转账","2");
	
	
	private String msg;
	/** code */
	private String code;

	/** 构造方法 */
	private RedEnvelopeUseTypeEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	/** 通过编号获取标题 */
	public static String getTitle(String code) {
		for (RedEnvelopeUseTypeEnum s : RedEnvelopeUseTypeEnum.values()) {
			if (s.code.equals(code)) {
				return s.msg;
			}
		}
		return null;
	}
	
	
	/** 查询全部*/
	public static List<Map<String, Object>> getAll(){
		
		
		List<Map<String, Object>> typeMaps = new ArrayList<Map<String,Object>>();
		for(RedEnvelopeUseTypeEnum type : RedEnvelopeUseTypeEnum.values()){
			Map<String, Object> typeMap = new HashMap<String, Object>();
			typeMap.put("value", type.getCode());
			typeMap.put("key", type.getMsg());
			typeMaps.add(typeMap);
		}
		return typeMaps;
		
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
