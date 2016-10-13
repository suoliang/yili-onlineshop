package com.fushionbaby.wap.controller.login;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.ChannelConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.core.model.SoShoppingCartSkuUser;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.wap.util.GetJsonFromResponse;
import com.fushionbaby.wap.util.LoginUtilsConstant;
import com.qq.connect.utils.json.JSONException;
import com.qq.connect.utils.json.JSONObject;

/***
 * 第三方登陆
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/login")
public class OtherLoginController {
	private static final Log logger = LogFactory.getLog(OtherLoginController.class);
	/** 注入会员 */
	@Autowired
	private MemberService<Member> memberService;
	/** 额外会员 */
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;
	/** 购物车 */
	@Autowired
	private CartRedisUserService<SoShoppingCartSkuUser> cartRedisService;
	/** access_token 的常量*/
	private static  final String   ACCESS_TOKEN="access_token";
	/**openid的常量*/
	private static  final String   OPEN_ID="openid";
	/** 404页面 常量*/
	private static final String PAGE_NOT_FOUND="common/404";
	/***
	 * 第三方之qq登录 成功之后返回到下面的callback中 。页面点击之后，会到该方法中
	 * 
	 * @return
	 */
	@RequestMapping("loginAsQQ")
	public void loginAsQQ(HttpServletResponse response) {
		try {
			/** 请求过去 获得code 在callback.do中 */
			response.sendRedirect("https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id="
					+ LoginUtilsConstant.QQ_CLIENT_ID
					+ "&redirect_uri="
					+ LoginUtilsConstant.QQ_REDIRECT_URL_FIRST
					+ "&state="
					+ new Date().getTime());
		} catch (IOException e) {
			logger.error("qq登陆回调失败", e);
		}
	}
	
	/**
	 * 第一次访问这个的时候
	 */
	@RequestMapping("callBackQQ")
	public void callBackQQFirst(HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");
			/** 第一次请求 */
				/** 通过code获得access_token */
				/** jojo为带上access_token 和refresh_token的字符串 */
				String jojo = GetJsonFromResponse.sendPost("https://graph.qq.com/oauth2.0/token",
						       "grant_type=authorization_code"
						        + "&client_id=" + LoginUtilsConstant.QQ_CLIENT_ID
								+ "&client_secret=" + LoginUtilsConstant.QQ_CLIENT_SECRET
								+ "&code="+ code
								+ "&redirect_uri=" + LoginUtilsConstant.QQ_REDIRECT_URL_SECOND);
		try {
			response.sendRedirect("http://www.fushionbaby.com/login/callBackQQ2.do?"+ jojo);
		} catch (IOException e) {
			logger.error("qq回调用code得到access_token失败", e);
		}
	}

	@RequestMapping("callBackQQ2")
	public String callBackQQSecond(HttpServletRequest request,HttpServletResponse response) {
		/** 生成sid */
		String sid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
		/** 未登录时的购物车信息 */
		String visitKey = CookieUtil.getCookieValue(request,CartConstant.COOKIE_VISIT_KEY);
		String accessToken = request.getParameter(ACCESS_TOKEN);
	    String openId=	getOpenIdByAccessTokenQQ(accessToken);
		String nickName=getNickNameByAccessTokenAndOpenIdQQ(accessToken,openId);
		   	   nickName=transCodingIntoUtf8(nickName);
			/** 检验该用户是不是已经登过web网站 */
			Member member = this.memberService.findByOpenId(openId);
			Long extraId=null;
			if (member == null) {
				/** 将登录信息保存在数据库 */
			 Long id = memberService.addLoginMember(openId,SourceConstant.WEB_CODE, ChannelConstant.QQ_CHANNEL,
					GetIpAddress.getIpAddr(request));
				/** 添加到额外会员表 */
				memberExtraInfoService.addMemberExtraInfo(id);
				extraId=id;
			} else {
				extraId=member.getId();
			}
			/** 将购物车里面的东西加入到 该用户中 */
			cartRedisService.loginCart(visitKey, extraId);
			/** 将登陆信息存入到缓存中 */
			this.createUserDto(nickName, sid, response, extraId);
		   return 	redirectToIndex(response);
	}

	/***
	 * qq登陆  通过accessToken和openId获得用户的昵称
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	private String getNickNameByAccessTokenAndOpenIdQQ(String accessToken,
			String openId) {
		String nickName="";
		String userInfoJson = GetJsonFromResponse.sendPost("https://graph.qq.com/user/get_user_info", 
				ACCESS_TOKEN+"="+ accessToken 
				+ "&oauth_consumer_key="+ LoginUtilsConstant.QQ_CLIENT_ID 
				+ "&openid="+ openId);
		JSONObject jo;
		try {
			jo = new JSONObject(userInfoJson);
			nickName = jo.getString("nickname");
		} catch (JSONException e) {
			logger.error("qq登陆获得用户昵称失败", e);
		}
		return nickName;
	}

	/***
	 * qq登陆的，通过accessToken获得用户的openId
	 * @param accessToken
	 * @return
	 */
	private String getOpenIdByAccessTokenQQ(String accessToken) {
		String openId="";
		/** 通过access_token 获得refresh_token */
		String openIdJson = GetJsonFromResponse.sendPost("https://graph.qq.com/oauth2.0/me", 
				ACCESS_TOKEN+"="+ accessToken);
		/** 将 openIdJson数据进行解析 得到openid */
		openIdJson = openIdJson.substring(openIdJson.indexOf("{"));
		openIdJson = openIdJson.substring(0,openIdJson.lastIndexOf(")"));
		JSONObject jo;
		try {
			jo = new JSONObject(openIdJson);
			 openId = jo.getString(OPEN_ID);
		} catch (JSONException e) {
			logger.error("qq登陆由accesstoken获取用户openId失败", e);
		}
		return openId;
	}

	/***
	 * 用户扫描之后会到这里并带上code参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("WeixinLogin")
	public String weixinLogin(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");// 请求带过来的参数
		/** 给登陆账号一个唯一标识 的sid */
		String sid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
		/** 设置未登录时的购物车信息 */
		String visitKey = CookieUtil.getCookieValue(request,CartConstant.COOKIE_VISIT_KEY);
		String refreshToken=getRefreshTokenByCodeWX(code);
		Map<String, String> openIdAndAccessTokenMap=getopenIdAndAccessTokenMapByRefreshTokenWX(refreshToken);
        Map<String, String>	nickNameAndOpenIdMap=getNickNameByOpenIdAndAccessTokenMapWX(openIdAndAccessTokenMap);
		 String openId=nickNameAndOpenIdMap.get("openId");
		 String nickname=nickNameAndOpenIdMap.get("nickname");
		       nickname=transCodingIntoUtf8(nickname);
		/** 检验该用户是不是已经登过web网站 */
		Member member = this.memberService.findByOpenId(openId);
		/** 存入数据库 */
		Long extraId=null;
		if (member == null) {
			Long id = memberService.addLoginMember(openId,
					SourceConstant.WEB_CODE, ChannelConstant.WX_CHANNEL,
					GetIpAddress.getIpAddr(request));
			/** 添加到额外会员表 */
			memberExtraInfoService.addMemberExtraInfo(id);
			extraId=id;
		} else {
			extraId=member.getId();
		}
		/** 将购物车里面的东西加入到 该用户中 */
		cartRedisService.loginCart(visitKey, extraId);
		/** 登录时的信息存入缓存中 */
		this.createUserDto(nickname, sid, response, extraId);
	 return redirectToIndex(response);
	}

	/***
	 * 通过openid和accessToken的map集合得到用户的昵称和用户的openid的map集合
	 * @param openIdAndAccessTokenMap
	 * @return
	 */
	private Map<String, String> getNickNameByOpenIdAndAccessTokenMapWX(Map<String, String> openIdAndAccessTokenMap) {
		Map<String, String> map=new HashMap<String, String>();
		String nickname="";
		String openId=openIdAndAccessTokenMap.get("openId");
		String accessToken=openIdAndAccessTokenMap.get("accessToken");
		/** end 获得 access_token和openid,通过refresh_token获得 */
		/** 获得用户的资料 通过openid和access_token */
		String userInfo = GetJsonFromResponse.sendPost("https://api.weixin.qq.com/sns/userinfo",
				"access_token="	+ accessToken + "&openid=" + openId);
		JSONObject jo;
		try {
			jo = new JSONObject(userInfo);
			openId = jo.getString(OPEN_ID);
			nickname = jo.getString("nickname");
		} catch (JSONException e) {
			logger.error("微信登陆得到用户的昵称和用户的openid的map集合失败", e);
		}
		map.put("openId", openId);
		map.put("nickname", nickname);
		return map;
	}

	/***
	 * 微信登陆，使用refreshToken得到openid和accessToken的map集合
	 * @param refreshToken
	 * @return
	 */
	private Map<String, String> getopenIdAndAccessTokenMapByRefreshTokenWX(String refreshToken) {
		Map<String, String> map=new HashMap<String, String>();
		String openId="";
		String accessToken="";
		String openIdJson = GetJsonFromResponse.sendPost("https://api.weixin.qq.com/sns/oauth2/refresh_token",
				"appid=" + LoginUtilsConstant.WEIXIN_CLIENT_ID 
				+ "&refresh_token=" + refreshToken
				+ "&grant_type=refresh_token");
		JSONObject jo;
		try {
			jo = new JSONObject(openIdJson);
			openId = jo.getString(OPEN_ID);
		    accessToken = jo.getString("access_token");
		} catch (JSONException e) {
			logger.error("微信登陆，通过refreshToken获取openid和accessToken失败", e);
		}
		map.put("openId", openId);
		map.put("accessToken", accessToken);
		return map;
	}

	/***
	 * 微信（WX）登陆  通过code换取refreshToken
	 * @param code
	 * @return
	 */
	private String getRefreshTokenByCodeWX(String code) {
		String refreshToken="";
		String codeJson = GetJsonFromResponse.sendPost("https://api.weixin.qq.com/sns/oauth2/access_token",
				 "appid=" + LoginUtilsConstant.WEIXIN_CLIENT_ID 
				+ "&secret=" + LoginUtilsConstant.WEIXIN_CLIENT_SECRET 
				+ "&code=" + code
				+ "&grant_type=authorization_code");
		JSONObject jo;
		try {
			jo = new JSONObject(codeJson);
		     refreshToken = jo.getString("refresh_token");
		} catch (JSONException e) {
			logger.error("微信登陆，用code换取refreshToken失败", e);
		}
		return refreshToken;
	}

	/***
	 * sina登陆
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("loginAsSina")
	public void loginAsSina(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			/** 获得code */
			response.sendRedirect("https://api.weibo.com/oauth2/authorize?client_id="
					+ LoginUtilsConstant.SINA_CLIENT_ID
					+ "&redirect_uri="
					+ LoginUtilsConstant.SINA_REDIRECT_URL
					+ "&response_type=code");
		} catch (IOException e) {
			logger.error("sina 登陆获取code失败", e);
		}
	}

	/***
	 * sina的获得code之后的回调方法
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("callBackSina")
	public String callBackSina(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
		/** 为该登录用户设置一个sid */
		String sid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
		/** 设置未登录时的购物车信息 */
		String visitKey = CookieUtil.getCookieValue(request,CartConstant.COOKIE_VISIT_KEY);
		    String accessToken=	getAccessTokenByCodeSina(code);
		    String uid=getUidByAccessTokenSina(accessToken);
			String name=getNameByUidAndAccessTokenSina(accessToken,uid);
			       name=transCodingIntoUtf8(name);
			/** 通过uid得到member对象 */
			Member member = this.memberService.findByOpenId(uid);
		    Long extraId=null;
			if (member == null) {
				Long id = memberService.addLoginMember(uid,SourceConstant.WEB_CODE, ChannelConstant.SINA_CHANNEL,
						GetIpAddress.getIpAddr(request));
				memberExtraInfoService.addMemberExtraInfo(id);
				extraId=id;
			} else {
				extraId=member.getId();
			}
			/** 将购物车里面的东西加入到 该用户中 */
			cartRedisService.loginCart(visitKey, extraId);
			/** 将用户登陆信息存入缓存 */
			this.createUserDto(name, sid, response, extraId);
			return redirectToIndex(response);
	}




	
	/***
	 * sina登陆的用code获得accessToken
	 * @param code
	 * @return
	 */
	private String getAccessTokenByCodeSina(String code){
		String accessToken="";
		/** 通过code获得access_token 和uid */
		String accessJson = GetJsonFromResponse.sendPost("https://api.weibo.com/oauth2/access_token", 
				   "client_id="+ LoginUtilsConstant.SINA_CLIENT_ID 
				+ "&client_secret=" + LoginUtilsConstant.SINA_CLIENT_SECRET
				+ "&grant_type=authorization_code"
				+ "&redirect_uri="+ LoginUtilsConstant.SINA_REDIRECT_URL
				+ "&code=" + code);
		JSONObject jo;
		try {
			jo = new JSONObject(accessJson);
		    accessToken = jo.getString(ACCESS_TOKEN);
		} catch (JSONException e) {
			logger.error("sina端登录的使用code获得accessToken失败", e);
		}
		
		return accessToken;
	}
	/***
	 * sina登陆的通过  accessToken得到用户的uid
	 * @param accessToken
	 * @return
	 */
	private String getUidByAccessTokenSina(String accessToken) {
		
		String uid="";
		String uidJson = GetJsonFromResponse.sendPost("https://api.weibo.com/oauth2/get_token_info",
				"access_token=" + accessToken);
		JSONObject jo2;
		try {
			jo2 = new JSONObject(uidJson);
		    uid = jo2.getString("uid");
		} catch (JSONException e) {
			logger.error("sina登陆使用 accessToken获得用户uid失败", e);
		}
		return uid;
	}
/***
 * sina登陆的  使用accessToken和uid得到用户的昵称，显示在页面中
 * @param accessToken
 * @param uid
 * @return
 */
	private String getNameByUidAndAccessTokenSina(String accessToken, String uid) {
     String name="";
     /** 未登陆过，就通过 UId获得用户的信息 */
		String userJson1 = GetJsonFromResponse.sendGet("https://api.weibo.com/2/users/show.json", 
				"access_token="	+ accessToken 
				+ "&uid=" + uid 
				+ "&client_id="+ LoginUtilsConstant.SINA_CLIENT_ID);
		JSONObject jo3;
		try {
			jo3 = new JSONObject(userJson1);
			name = jo3.getString("name");
		} catch (JSONException e) {
			logger.error("sina登录时获取用户昵称失败", e);
		}
		return name;  
	}

	/***
	 * 将登陆信息(nickName和sid)存入到 缓存中
	 * 
	 * @param member
	 * @param sid
	 * @param response
	 * @param member_id
	 */
	private void createUserDto(String nickname, String sid,
			HttpServletResponse response, Long member_id) {
		UserDto user = new UserDto();
		user.setLogin_name(nickname);
		user.setSid(sid);
		user.setMember_id(member_id);
		CookieUtil.setCookie(response, CookieConstant.COOKIE_SID, sid);
		SessionCache.put(sid, user);
	}
	
	
	/***
	 * 登陆完成后重定向到主页
	 * @param response
	 * @return
	 */
	private String redirectToIndex(HttpServletResponse response){
		try {
			response.sendRedirect("http://www.fushionbaby.com");
			return "index";
		} catch (IOException e) {
			logger.error("第三方登陆重定向到首页失败", e);
			return PAGE_NOT_FOUND;
		}
		
	}
	/***
	 * 对名字做转码的，转为utf-8
	 * @param nickName
	 * @return
	 */
		private String transCodingIntoUtf8(String nickName) {
		try {
			   byte[] b = nickName.getBytes("utf-8");
				nickName = new String(b, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				logger.error("用户名转码失败", e1);
			}
			return nickName;
		}
	
}
