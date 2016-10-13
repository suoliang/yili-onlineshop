package com.fushionbaby.common.constants;

import com.fushionbaby.common.enums.AdvertisementConfigEnum;

/***
 * 广告配置的常量类
 * 
 * @author xupeijun
 * 
 */
public class AdvertisementConfigConstant {
	/** 商品分类 */
	public static AdvertisementConfigEnum WEB_CATEGORY;
	/** 手机app端的首页焦点 */
	public static final String APP_START_FOCUS = "app_start_focus";
	/** 手机app端的home页面焦点 */
	public static String APP_HOME_FOCUS = "app_home_focus";
	/** 手机APP门店home焦点图*/
	public static String APP_STORE_HOME_FOCUS = "app_store_home_focus";
	/** 手机首页弹框*/
	public static  String APP_HOME_BOMB_BOX = "APP_HOME_BOMB_BOX";
	
	/** web端的首页焦点 */
	public static String WEB_INDEX_FOCUS = "web_index_focus";
	/** wap端的首页焦点 */
	public static String WAP_INDEX_FOCUS = "wap_index_focus";
	/** wap端主题模块 */
	public static String WAP_INDEX_SEMINAR = "wap_index_seminar";
	/** web端的首页焦点缩略图 */
	public static String WEB_INDEX_BRIEF = "web_index_brief";
	/** app分享赚红包首页*/
	public static String APP_SHARE_INDEX = "app_share_index";
	/** app分享赚红包规则*/
	public static String APP_SHARE_RULE  =  "app_share_rule";
	/** 广告来源 （手机端） */
	public static final String AD_SOURCE_PHONE = "1";
	/** 广告来源（web端） */
	public static final String AD_SOURCE_WEB = "3";
	/** 登陆页面图片 */
	public static final String WEB_LOGIN_AD = "web_login";

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		System.out.println(AdvertisementConfigConstant.WEB_CATEGORY.getApCode("F001"));
		System.out.println(AdvertisementConfigConstant.WEB_CATEGORY.WEB_CATAGORY_CLOTHES.getApCode());
	}
}
