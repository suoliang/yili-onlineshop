package com.fushionbaby.common.enums;
/**
 * 购物车验证码
 * @description 类描述...
 * @author 孟少博
 * @date 2015年8月12日上午11:54:10
 */
public enum ShoppingCartCaptChaEnum {
	
	SUCCESS("操作成功","0"),
	
	SID_AND_VISITKEY_ISNULL("SID和visitkey都为空", "1"),
	
	SKU_CODE_ISNULL("商品编号为空","2"),
	
	QUANTITY_ISNULL("购买数量quantity参数不能为空","3"),
	
	QUANTITY_FORMAT_EXCEPTION("购买数量quantity参数内容有误,必须是整数","4"),
	
	SELECT_PARAMS_ISNULL("isSelected参数不能为空","5"),
	
	SELECT_PARAMS_ERROR("isSelected的值只能是y/n两个值","6"),
	
	SID_AND_VISITKEY_COOKIE_ERROR("购物车获取cookie有误","7"),
	
	SHOPPING_CART_COOKIE_ERROR("购物车获取cookie有误","8"),
	
	SKU_STATUS_NO_PUT("该商品已下架!","9"),
	
	SHOPPING_CART_SKU_CODES_ISNULL("购物车编号不能为空","10"),
	
	SKU_NO("商品不存在","11"),
	
	INVENTORY_NOT_ENOUGH("该商品库存不足","12"),
	
	SHOPPING_CART_FULL("购物车已满","13"),
	
	SHOPPING_CART_SID_ISNULL("购物车SID为空","14"),
	
	SHOPPING_CART_SELECTED_FALSE("请至少选中一件商品!","15");
	


	/** 标题*/
	private String msg;
	/** 广告配置ap_code */
	private String code;

	/** 构造方法 */
	private ShoppingCartCaptChaEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	/** 通过分类编号获取标题 */
	public static String getTitle(String code) {
		for (ShoppingCartCaptChaEnum s : ShoppingCartCaptChaEnum.values()) {
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
