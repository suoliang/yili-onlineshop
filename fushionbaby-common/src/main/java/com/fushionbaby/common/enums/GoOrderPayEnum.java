package com.fushionbaby.common.enums;
/**
 * 
 * @description 去订单支付时的状态
 * @author 孟少博
 * @date 2015年8月18日上午11:11:26
 */
public enum GoOrderPayEnum {
	
	PAY_Off_FAILED_ERROR_MSG("订单结算序列失效!","1"),
	ADDRESS_ISNULL("收货地址不能为空","2"),
	ORDER_ISNULL("订单创建失败","3"),
	SELECTED_SKU_ISNULL("请选择您要购买的商品!在结算!","4"),
	YIDUOBAO_ERROR("益多宝收益额或赠券额金额不足!","5"),
	ORDER_EXCEPTION("创建订单异常","999");
	
	
	/** 标题*/
	private String msg;
	/** code */
	private String code;

	/** 构造方法 */
	private GoOrderPayEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	/** 通过编号获取标题 */
	public static String getTitle(String code) {
		for (GoOrderPayEnum s : GoOrderPayEnum.values()) {
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
