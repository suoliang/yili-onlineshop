package com.fushionbaby.common.constants;

/**
 * @author 张明亮 购物车常量
 */
public interface CartConstant {

	/**
	 * 购物车行大小,一个购物车最多放多少件商品行记录
	 */
	public static final int CARTSIZE = 58;

	/**
	 * 购物车主表存储前缀,Redis缓存key拼接使用
	 */
	public static final String CART_PREFIX = "cart_";

	/**
	 * 购物车唯一访问key,未登录用户浏览器要缓存这个
	 */
	public static final String COOKIE_VISIT_KEY = "visit_key";
}
