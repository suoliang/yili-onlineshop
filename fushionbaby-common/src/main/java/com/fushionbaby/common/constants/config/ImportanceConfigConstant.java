package com.fushionbaby.common.constants.config;
/**
 * 重要的配置常量
 * @author King 索亮
 *
 */
public class ImportanceConfigConstant {
	/**系统发布环境*/
	public static final String SYSTEMENVIRONMENT = "SYSTEM_ENVIRONMENT";
	/**发布版本*/
	public static final String DEPLOYVERSION = "DEPLOY_VERSION";
	/**图片文件路径*/
	public static final String IMAGEPATH = "IMAGE_PATH";
	/**图片服务根路径*/
	public static final String IMAGESERVERROOTPATH = "IMAGE_SERVERROOT_PATH";
	/**memcached服务地址*/
	public static final String MEMCACHEDSSERVER = "MEMCACHEDS_SERVER";
	/**redis服务地址*/
	public static final String REDISSERVER = "REDIS_SERVER";
	/**公用访问前缀*/
	public static final String HTTPPREFIX = "HTTP_PREFIX";
	/**支付宝即时到账异步通知路径*/
	public static final String JSDZNOTIFYURL = "JSDZ_NOTIFY_URL";
	/**支付宝即时到账同步通知路径*/
	public static final String JSDZRETURNURL = "JSDZ_RETURN_URL";
	/**支付宝担保交易异步通知路径*/
	public static final String DBJYNOTIFYURL = "DBJY_NOTIFY_URL";
	/**支付宝担保交易同步通知路径*/
	public static final String DBJYRETURNURL = "DBJY_RETURN_URL";	
	/**银联交易前台通知路径*/
	public static final String YLFRONTURL = "YL_FRONT_URL";
	/**银联交易后台通知路径*/
	public static final String YLBACKURL = "YL_BACK_URL";
	/**手机微信支付接收财付通通知的URL*/
	public static final String APPWXNOTIFYURL = "APP_WX_NOTIFY_URL";
	/**手机微信支付--如意宝ALABAO回调URL*/
	public static final String ALABAOAPPWXNOTIFYURL = "ALABAO_APP_WX_NOTIFY_URL";
	/**手机支付宝支付回调URL*/
	public static final String APPZFBNOTIFYURL = "APP_ZFB_NOTIFY_URL";
	/**手机支付宝支付-如意宝ALABAO回调URL*/
	public static final String ALABAOAPPZFBNOTIFYURL = "ALABAO_APP_ZFB_NOTIFY_URL";
	/**手机支付宝支付-益多宝yiduobao回调URL*/
	public static final String YIDUOBAO_APPZFBNOTIFYURL = "YIDUOBAO_APP_ZFB_NOTIFY_URL";
	/**手机微信支付-益多宝yiduobao回调URL*/
	public static final String YIDUOBAO_APP_WX_NOTIFY_URL = "YIDUOBAO_APP_WX_NOTIFY_URL";
	/**使用如意宝支付折扣值-打折*/
	public static final String ALABAO_PAY_DISCOUNT = "ALABAO_PAY_DISCOUNT";
	/**银联支付请求路径*/
	public static final String PAYYLURL = "PAY_YL_URL";
	/**支付宝支付请求路径*/
	public static final String PAYZFBJSDZURL = "PAY_ZFB_JSDZ_URL";
	/**WEB微信支付请求路径*/
	public static final String PAYWEBWXURL = "WEB_WX_URL";
	/**WEB微信支付后台通知路径*/
	public static final String WEBWXNOTIFYURL = "WEB_WX_NOTIFY_URL";
	/**手机银联交易前台通知路径*/
	public static final String APPUNIONBACKURL = "APP_UNION_BACK_URL";
	/** 支付宝退款路径*/
	public static final String ZFBREFUNDNOTIFYURL = "ZFB_REFUND_NOTIFY_URL";
	/** cms银联退款后台路径*/
	public static final String CMSYLREFUNDBACKNOTIFYURL = "CMS_YL_REFUND_BACK_NOTIFY_URL";
	/**手机银联支付-如意宝ALABAO回调URL*/
	public static final String ALABAOAPPUNIONBACKURL = "ALABAO_APP_UNION_BACK_URL";
	
	/**手机银联支付-阿拉丁订单 银联支付 回调URL*/
	public static final String YIDUOBAO_APP_UNION_NOTIFY_URL = "YIDUOBAO_APP_UNION_NOTIFY_URL";
	
	/**WAP网站访问域名*/
	public static final String WAPHTTPURL = "WAP_HTTP_URL";
	/**WAP支付宝支付请求路径*/
	public static final String WAPZFBURL = "WAP_ZFB_URL";
	/**WAP支付宝支付前台同步通知路径*/
	public static final String WAPZFBRETURNURL = "WAP_ZFB_RETURN_URL";
	/**WAP支付宝支付后台异步通知路径*/
	public static final String WAPZFBNOTIFYURL = "WAP_ZFB_NOTIFY_URL";
	
	/**微信公众号支付请求路径*/
	public static final String WXGZHPAYURL = "WX_GZH_PAY_URL";
	/**微信公众号支付异步回调路径*/
	public static final String WXGZHPAYNOTIFYURL = "WX_GZH_PAY_NOTIFY_URL";
	
	/** 订单满多少之后发送短信通知 现在是100*/
	public static final String APP_ORDER_LOWEST_MONEY= "APP_ORDER_LOWEST_MONEY";
	
	/** 是否开启订单审核(y开启,n不开启) */
	public static final String IS_OPEN_ORDER_AUDIT = "IS_OPEN_ORDER_AUDIT";
	
	
	/** 支付宝支付的手续费率，服务费率 */
	public static final String ZFBCOMMISSIONRATE = "ZFB_COMMISSION_RATE";
	/** 微信支付的手续费率，服务费率 */
	public static final String WXCOMMISSIONRATE = "WX_COMMISSION_RATE";
	/** 银联支付的手续费率，服务费率 */
	public static final String YLCOMMISSIONRATE = "YL_COMMISSION_RATE";
	
	/** 使用如意宝支付-如意宝账户金额小于2w(不包括)折扣值-打折 */
	public static final String ALABAO_SMALL_DISCOUNT = "ALABAO_SMALL_DISCOUNT";
	/** 使用如意宝支付-如意宝账户金额2w到10w(不包括)折扣值-打折 */
	public static final String ALABAO_MIDDLE_DISCOUNT = "ALABAO_MIDDLE_DISCOUNT";
	/** 使用如意宝支付-如意宝账户金额10w(包括)以上折扣值-打折 */
	public static final String ALABAO_BIG_DISCOUNT = "ALABAO_BIG_DISCOUNT";
	
	
}
