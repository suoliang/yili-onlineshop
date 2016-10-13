package com.fushionbaby.common.enums;

/**
 * 
 * @description 使用优惠券错误码
 * @author 孟少博
 * @date 2015年8月18日下午1:45:00
 */
public enum UseCouponEnum {

	
	COUPON_ISNULL("优惠券不存在或已失效","1"),
	FULL_USE_COUPON("元才可以使用该优惠券!","2"),
	COUPON_EXCEPTION("优惠券金额异常","3"),
    COUPON_NOT_BRAND("该优惠券不能使用在某些品牌下","4"),
    COUPON_BRAND_ISNULL("购买商品列表、商品品牌为空,不允许使用优惠券","14"),
    COUPON_NOT_CATEGORY("该优惠券不能使用在某些分类下","5"),
    COUPON_CATEGORY_ISNULL("购买商品列表、商品分类为空,不允许使用优惠券","15"),
    COUPON_NOT_LABEL("该优惠券不能使用在某些标签下","6"),
    COUPON_LABEL_ISNULL("购买商品列表、商品标签为空,不允许使用优惠券","16") ,
    COUPON_NOT_SKUS("该优惠券不能使用在某些商品下","7");
	
	/** 标题*/
	private String msg;
	/** code */
	private String code;

	/** 构造方法 */
	private UseCouponEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	/** 通过编号获取标题 */
	public static String getTitle(String code) {
		for (UseCouponEnum s : UseCouponEnum.values()) {
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
