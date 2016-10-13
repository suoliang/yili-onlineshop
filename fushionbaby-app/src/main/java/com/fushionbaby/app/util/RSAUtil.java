package com.fushionbaby.app.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.StringUtils;

/***
 * app支付宝登陆 私钥签名
 * 
 * @author xupeijun
 *
 */
public class RSAUtil {
	public static final String ZFB_PRIKEY = 
			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANE8qBZMAMVMMslJ"
			+ "tk2IdiUyQU8QX0+t0xQtm+lpXvijtOCvgVg+EYQofVo6k3imEwymSqTR99jXHZu0"
			+ "Ja52Y/mA7K5SneQv908+u8xmZpAKwUwN3iHCQQc4ovPzmu7UJ5eE9idowyQjkxg3"
			+ "dDArCqKl6iAbshEUR9bLA4znbPHjAgMBAAECgYAGVL6FRjFzxtV2j7trZyikAXxG"
			+ "ZylrIMhVQPgJvP15qoJrlgPk5qPjCtKYK1OQ//1tkY6KpCvKrN+heXb2a3Ck6vgX"
			+ "meXJ61ZhcTH0DmJV3kjoc9YsRNZQKRo/s4cXRPdH87OrxRnJ8uZcZBtvOmbobf8F"
			+ "tpfQhVN7SaxQNXr9KQJBAP20LMDUA0yAr1zVYyvX8nA5kn0ClNnEaSrRGqmTlMOT"
			+ "48UPgFrY42RQCOTkEih/BlN01plozdzJFdahi6vhBqcCQQDTIXQD/UhohiUJWk7Z"
			+ "bON+i4Cqubaa9AhgY5//sgtv6KimQmE9/wf8BtzqBELz8JYjR36/JuhxdaM+BRHp"
			+ "rF5lAkEAjOAKZbyUZFukhJD883CC76wYWpAfZVi56QuZmYYYeg1Z0afM5yU+Bwqm"
			+ "FatLJe967MaZDzBx92I1XSvw6a0XSwJAXTFnBFvImlqb/oTeSRrHUBRDtxhCgiM1"
			+ "j1ZqSXNNmRgZWUnzomnwHE7rNGCtLQAFRY1JZRbHg01pwPZ78akkAQJBAPtTQCIq"
			+ "hRBD2eVqQsn5boeXWznFdkp9HLVOToJBTqvvDB5MeKNq3O4TuotmJP1oGzD4BWT1"
			+ "2uGRqJMK+BT/ivM=";
			
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	
	public static String sign(String content) {
		String charset = "UTF-8";
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(ZFB_PRIKEY));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(charset));
			byte[] signed = signature.sign();
			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static String ZFBTrustLogin(String appUserId) {
		StringBuilder sb = new StringBuilder();
		sb.append("app_name=\"mc\"&biz_type=\"trust_login\"&partner=\"");
		sb.append("2088711896780205");
		if (!StringUtils.isEmpty(appUserId)) {
			appUserId = appUserId.replace("\"", "");
			sb.append("\"&app_id=\"");
			sb.append(appUserId);
		}
		sb.append("\"");
		String info = sb.toString();
		// 请求信息签名
		String sign = RSAUtil.sign(info);
		try {
			if (sign != null) {
				sign = URLEncoder.encode(sign, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		info += "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";
		return info;
	}
	
	
}
