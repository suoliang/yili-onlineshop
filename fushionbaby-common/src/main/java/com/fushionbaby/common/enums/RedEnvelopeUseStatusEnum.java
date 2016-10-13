/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月19日下午3:22:50
 */
package com.fushionbaby.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 红包使用状态
 * @author 孟少博
 * @date 2015年10月19日下午3:22:50
 */
public enum RedEnvelopeUseStatusEnum {

	
	MARCH("进行中","0"),
	SUCCESS("交易成功","1"),
	FAIL("交易取消","2"),
	REFUND("退款","3");
	
	
	private String msg;
	/** code */
	private String code;

	/** 构造方法 */
	private RedEnvelopeUseStatusEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	/** 通过编号获取标题 */
	public static String getTitle(String code) {
		for (RedEnvelopeUseStatusEnum s : RedEnvelopeUseStatusEnum.values()) {
			if (s.code.equals(code)) {
				return s.msg;
			}
		}
		return null;
	}
	
	/** 查询全部*/
	public static List<Map<String, Object>> getAll(){
		
		
		List<Map<String, Object>> statusMaps = new ArrayList<Map<String,Object>>();
		for (RedEnvelopeUseStatusEnum  status: RedEnvelopeUseStatusEnum.values()) {
			Map<String, Object> statusMap = new HashMap<String, Object>();
			statusMap.put("value", status.getCode());
			statusMap.put("key", status.getMsg());
			statusMaps.add(statusMap);
		}
		
		return statusMaps;
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
