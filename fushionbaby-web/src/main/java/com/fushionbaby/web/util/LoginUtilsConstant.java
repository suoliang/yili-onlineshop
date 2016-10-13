package com.fushionbaby.web.util;

import com.qq.connect.utils.URLEncodeUtils;

public class LoginUtilsConstant {
	/** qq的申请信息 */
	public static final String QQ_CLIENT_ID = "101179105";
	public static final String QQ_CLIENT_SECRET = "c722257cb127fb9d01a89b4fefd6c223";
	/** 第一次的回调地址 */
	public static final String QQ_REDIRECT_URL_FIRST = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/callBackQQ.do");
	/** 第二次的回调地址 */
	public static final String QQ_REDIRECT_URL_SECOND = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/callBackQQ2.do");

	/** 微信的申请信息 */
	public static final String WEIXIN_CLIENT_ID = "wxdcb2470548eef8c0";
	public static final String WEIXIN_CLIENT_SECRET = "1d5c6304d3d4c292ce6af34cc92d3ebf";
	/** 线上 */
	public static final String WEIXIN_REDIRECT_URL = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/WeixinLogin.do");
	

	/** sina的申请信息 */
	public static final String SINA_CLIENT_ID = "1184907740";
	public static final String SINA_CLIENT_SECRET = "753a2771a78a4243bdc2c31aff581f78";
	/** 第一次的回调地址 */
	public static final String SINA_REDIRECT_URL = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/callBackSina.do");
	/** 第二次的回调地址 */
	public static final String SINA_REDIRECT_URL2 = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/callBackSina2.do");
	/** 支付宝的回调地址*/
	public static final String ZFB_REDIRECT_URL ="http://www.fushionbaby.com/login/callBackZFB.do";
    
	/** 预发布环境回调地址  支付宝可以在本地测试*/
	//public static final String ZFB_REDIRECT_URL ="http://wwwtest.fushionbaby.com/login/callBackZFB.do";
	/**本地调试 回调地址 支付宝可以在本地测试*/
	//  public static final String ZFB_REDIRECT_URL = "http://localhost:8080/fushionbaby-web/login/callBackZFB.do";
	/** 商户账号*/
	public static String ZFB_PARTNER = "2088711896780205";
	/** 商户的私钥*/
	public static String ZFB_KEY = "omzth80a7k4gue3411y6krpkcv0ypp8a";

}
