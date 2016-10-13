/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月20日下午3:29:29
 */
package com.fushionbaby.common.enums;

/**
 * @description 评价等级
 * @author 孟少博
 * @date 2015年10月20日下午3:29:29
 */
public enum CommentLevelEnum {

	
	GOOD("好评","1"),
	SECONDARY("中评","2"),
	LOWEST("差评","3");
	
	
	/** 标题*/
	private String msg;
	/** code */
	private String code;

	/** 构造方法 */
	private CommentLevelEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	/** 通过编号获取标题 */
	public static String getTitle(String code) {
		for (CommentLevelEnum s : CommentLevelEnum.values()) {
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
