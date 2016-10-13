package com.fushionbaby.other.wxCard;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.other.util.json.JSONException;
import com.fushionbaby.other.util.json.JSONObject;

/***
 * 处理微信卡券
 * 
 * 1、得到access_token 2、通过卡的code得到card_id 3、由card_id得到该卡券的详细信息（折扣，优惠金额等）
 * 4、记录保存使用信息 5、销毁已经是用过的card
 * 
 * @author xupeijun
 * 
 */
public class UseWXCrad {

	public static  final Log log=LogFactory.getLog(UseWXCrad.class);
	/** 微信公众号的appid */
	public static final String APP_ID = "wx09fd81d5483102c6";
	/** 微信公众号的appsecret */
	public static final String SECRET = "72f599b6d03233146cf4180203ec8b00";
    /** 得到access_token*/
	private static String ACCESS_TOKEN = getAccessToken();
	/***
	 * 得到access_token
	 * 
	 * @return
	 */
	public static String getAccessToken() {
		String access_token = "";
		/** 得到access_token */
		String access_token_json = HttpRequest.sendPost(
				"https://api.weixin.qq.com/cgi-bin/token",
				"grant_type=client_credential&appid=" + APP_ID + "&secret="
						+ SECRET);
		try {
			JSONObject jo = new JSONObject(access_token_json);
			access_token = jo.getString("access_token");
		} catch (JSONException e) {
			log.error("微信卡券得到accesstoken失敗", e);
		}
		return access_token;
	}

	/***
	 * 根据code得到card_id
	 * 
	 * @param code
	 * @return
	 */
	public static String getCardIdByCode(String code) {
		
		String card_id = "";
		String json = "{\"code\":\"" + code + "\"}";
		String card_message_json=HttpRequest.sendPost("https://api.weixin.qq.com/card/code/get?access_token="
						+ ACCESS_TOKEN, json);
		//System.out.println(card_message_json);
		try {
			JSONObject jo = new JSONObject(card_message_json);
			String errmsg = jo.getString("errmsg");
			if(!StringUtils.equals("ok", errmsg)){
				return StringUtils.EMPTY;
			}
			JSONObject card = jo.getJSONObject("card");
			card_id = card.getString("card_id");
		} catch (JSONException e) {
			log.error("微信卡券通过卡号获得卡的类型失败", e);
		}
		return card_id;
	}

	/***
	 * 通过card_id得到卡的详细信息
	 * 
	 * @param card_id
	 * @return
	 */
	public static String getCardInfo(String card_id) {
		String json = "{\"card_id\":\"" + card_id + "\"}";
		String message = HttpRequest.sendPost(
				"https://api.weixin.qq.com/card/get?access_token="
						+ ACCESS_TOKEN, json);
		try {
			JSONObject msg = new JSONObject(message);
			String errmsg = msg.getString("errmsg");
			if(! StringUtils.equals(errmsg, "ok")){
				return null;
			}
		} catch (JSONException e) {
			log.error("获得卡的具体信息失败", e);
			return null;
		}
		return message;
	}
	
	
	/***
	 * 用户使用过的话，销毁该卡
	 * 
	 * @param code
	 * @param       card_id
	 */
	public static String destoryCard(String code, String card_id) {
		String json = "{\"code\":\"" + code + "\",\"card_id\":\"" + card_id
				+ "\"}";
		String message = HttpRequest.sendPost(
				"https://api.weixin.qq.com/card/code/consume?access_token="
						+ ACCESS_TOKEN, json);
		try {
			JSONObject jo = new JSONObject(message);
			String errmsg = jo.getString("errmsg");
			if(!StringUtils.equals("ok", errmsg)){
				return StringUtils.EMPTY;
			}
			JSONObject card = jo.getJSONObject("card");
			card_id = card.getString("card_id");
		} catch (JSONException e) {
			log.error("微信卡券销毁失败", e);
		}
		return card_id;
	}
	
	
	public static void main(String[] args) {
		String code = "287399274456";
		System.out.println(ACCESS_TOKEN);
		String card_id = getCardIdByCode(code);
		System.out.println( "card:" +card_id);
		System.out.println("getCardInfo(card_id):" + getCardInfo(card_id));
		if(StringUtils.isNotBlank(card_id)){
//			System.out.println("card_id-----" + card_id);
//			WxCardVoucher wxCard = getCardInfo(card_id);
//			System.out.println("detail-----" + wxCard);
			//destoryCard(code, card_id);
			
		}
	}
	
}
