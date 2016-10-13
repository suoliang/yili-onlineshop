package com.fushionbaby.common.constants;

/***
 * cookie相关的常量类
 * 
 * @author King 索亮
 *
 */
public class CookieConstant {
	public static final String DOMAIN = "www.aladingshop.com";// 网站域名,cookie保存使用,记录cookie是属于哪个域名的

	/**
	 * 设置cookie有效期，根据需要自定义[本系统设置为30天]
	 */
	public final static int COOKIE_MAX_AGE = 1000 * 60 * 60 * 24 * 30;
	/**
	 * 设置cookie有效期，登陆默认7天
	 */
	public final static int COOKIE_LOGIN_AGE = 60 * 60 * 24 * 7;
	/**
	 * 设置cookie有效期，验证码 10分钟
	 */
	public final static int COOKIE_REGISTER_AGE = 10 * 60;

	/**
	 * 设置cookie有效期，最后浏览页15分钟
	 */
	public final static int COOKIE_URL_AGE = 15 * 60;

	public static final String COOKIESHOPPINGCARTSID = "shoppingCartSid";// 购物车标识id

	public static final String COOKIE_SID = "sid";// 放置用户的登录信息标识码
	
	public static final String ALABAO_SID = "alabao_sid"; //放置阿拉宝用户的登录信息标识码

	public static final String REGISTER_COOKIE = "register_code";// 用户注册时放置注册标识码

	public static final String FORGETPASSWORD_COOKIE = "forgetPassword_code";// 用户点击忘记密码时设置标识码

	public static final String FORGETPASSWORD_COOKIE_LOGINNAME = "forgetPassword_loginname";// 用户点击忘记密码时放置登陆名的标识码
	

	/**
	 * 当前用户最后一次浏览页
	 */
	public static final String COOKIE_CUURENT_URL = "current_url";
    /**请求发送短信的标志*/
	public static final String LAST_SEND_CODE_TIME = "LAST_SEND_CODE_TIME";
	
}
