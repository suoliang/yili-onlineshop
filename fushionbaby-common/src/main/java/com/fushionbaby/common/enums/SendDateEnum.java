/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月20日下午4:12:32
 */
package com.fushionbaby.common.enums;

/**
 * @description 配送时间
 * @author 孟少博
 * @date 2015年10月20日下午4:12:32
 */
public enum SendDateEnum {

	
	RANDOM_TIME("周一到周日(任意时间)","1"),
	WORK_TIME("周一到周五(工作日)","2"),
	REST_TIME("周六周日(休息日)","3"),
	HALF_HOUR("半小时内","11"),
	ONE_HOUR("一小时内","12"),
	TWO_HOUR("两小时内","13");
	
	
	
	/** 标题*/
	private String msg;
	/** 广告配置ap_code */
	private String code;

	/** 构造方法 */
	private SendDateEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	/** 通过分类编号获取标题 */
	public static String getTitle(String code) {
		for (SendDateEnum s : SendDateEnum.values()) {
			if (s.code.equals(code)) {
				return s.msg;
			}
		}
		return null;
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
