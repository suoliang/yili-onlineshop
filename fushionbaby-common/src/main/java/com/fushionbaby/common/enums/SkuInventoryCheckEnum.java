package com.fushionbaby.common.enums;
/**
 * 
 * @description 商品库存验证
 * @author 孟少博
 * @date 2015年8月30日下午3:33:46
 */
public enum SkuInventoryCheckEnum {
	
	
	SKU_OFF_SHElVES("该商品已下架!","1"),
	SKU_INVENTORY_FULL("该商品库存不足!","2");
	
	
	/** 标题*/
	private String msg;
	/** code */
	private String code;

	/** 构造方法 */
	private SkuInventoryCheckEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	/** 通过编号获取标题 */
	public static String getTitle(String code) {
		for (SkuInventoryCheckEnum s : SkuInventoryCheckEnum.values()) {
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
