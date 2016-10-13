package com.fushionbaby.common.enums;

public enum SkuStatusEnum {

	/** 商品初始化状态 */
	SKU_STATUS_INIT("1"),
	/** 商品上架 */
	SKU_STATUS_TOP("2"),
	/** 商品下架 */
	SKU_STATUS_DOWN("3"),
	/** 商品已删除*/
	SKU_STATUS_DELETED("9");

	private String strVlue;

	private SkuStatusEnum(String strVlue) {
		this.strVlue = strVlue;
	}

	public String getStrVlue() {
		return strVlue;
	}

	
	public static void main(String[] args) {
		System.out.println(SkuStatusEnum.SKU_STATUS_INIT.getStrVlue());
	}
}
