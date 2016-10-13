package com.aladingshop.wap.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/***
 * 
 * @description 类描述... 第三方登陆账号的信息
 * @author 徐培峻
 * @date 2015年8月13日下午4:04:57
 */
public class OtherLoginConstant {

	/**yili 支付宝相关  begin*/
	//public static final String ZFB_REDIRECT_URL = "http://localhost:8080/aladingshop-web/otherLogin/callBackZFB.htm";
	public static final String ZFB_REDIRECT_URL = "http://wwwtest.aladingshop.com/otherLogin/callBackZFB.htm";
	//public static final String ZFB_REDIRECT_URL = "http://www.aladingshop.com/otherLogin/callBackZFB.htm";
	public static String ZFB_PARTNER = "2088911777868192";
	public static String ZFB_KEY = "ile3h1czgu2h5k390ukvgpx7hubuzptg";
	/**yili 支付宝相关   end*/
	
	/**yili 微信相关     begin*/
	public static final String WX_CLIENT_ID="wxdbb5b8b26a262e6f";
	public static final String WX_CLIENT_SECRET="419397c1afe3cdaedaae3394dee6428c";
	//public static final String WX_REDIRECT_URL=getEncodeURL("http://www.aladingshop.com/otherLogin/WeixinLogin.do");
	public static final String WX_REDIRECT_URL=getEncodeURL("http://wwwtest.aladingshop.com/otherLogin/WeixinLogin.do");
	/**yili 微信相关     end*/
	
	
	/**yili  qq信息   begin*/
	public static final String QQ_CLIENT_ID = "101225031";
	public static final String QQ_CLIENT_SECRET = "0fddf6e8500e957d13c301df88e70eb7";
	//public static final String QQ_REDIRECT_URL_FIRST = getEncodeURL("http://www.aladingshop.com/otherLogin/callBackQQ.do");
	public static final String QQ_REDIRECT_URL_FIRST = getEncodeURL("http://wwwtest.aladingshop.com/login/callBackQQ.do");
	//public static final String QQ_REDIRECT_URL_SECOND = getEncodeURL("http://www.aladingshop.com/otherLogin/callBackQQ2.do");
	public static final String QQ_REDIRECT_URL_SECOND = getEncodeURL("http://wwwtest.aladingshop.com/login/callBackQQ2.do");
	/**yili  qq信息   end */
	
	
	/**yili sina信息  begin*/
	public static final String SINA_CLIENT_ID = "1245639200";
	public static final String SINA_CLIENT_SECRET = "939ac8e9d1f9cd9edc4c5f53cd80dffb";
	//public static final String SINA_REDIRECT_URL = getEncodeURL("http://www.aladingshop.com/otherLogin/callBackSina.do");
	public static final String SINA_REDIRECT_URL = getEncodeURL("http://wwwtest.aladingshop.com/login/callBackSina.do");
	//public static final String SINA_REDIRECT_URL2 =getEncodeURL("http://www.aladingshop.com/otherLogin/callBackSina2.do");
	public static final String SINA_REDIRECT_URL2 =getEncodeURL("http://wwwtest.aladingshop.com/login/callBackSina2.do");
	/**yili sina信息  end*/
	
	private static String getEncodeURL(String url) {
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}  
		return url;
	}
}
