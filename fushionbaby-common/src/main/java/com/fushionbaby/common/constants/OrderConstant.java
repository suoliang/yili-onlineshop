package com.fushionbaby.common.constants;

import java.math.BigDecimal;

/**
 * 订单常量类
 * @author 张明亮
 */
public interface OrderConstant {
	/** 税率  以6%计算*/
	public static final BigDecimal TAX_RATE=new BigDecimal(0.06) ;
	
	/**优惠券竖线进行分割*/
	public static final String COUPONS_LINE = "|";
	
	/**订单收货地址 -- 未修改*/
	public static final int UNMODIFIED_ADDRESS = 0;
	
	/**物流状态:1未发货*/
	public static final int LOGISTICS_STATUS_NO=1;
	
	/**物流状态:2已发货*/
	public static final int LOGISTICS_STATUS_YES=2;
	
	/**物流状态:3已签收*/
	public static final int LOGISTICS_STATUS_SIGN=3;
	
	/** 购物车结算*/
	public static final String GOTO_CART_PAYMENT = "0";
	/** 立即购买*/
	public static final String GOTO_IMMEDIATE_PAYMENT = "1";
	/** 商品组合*/
	public static final String GOTO_COMBINATION_PAYMENT = "2";


}
