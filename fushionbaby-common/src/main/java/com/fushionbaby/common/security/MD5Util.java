package com.fushionbaby.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * MD5加密工具类
 * 
 * @author suntao
 * 
 *         2015年1月26日
 */
public class MD5Util {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	
	/**md5校验*/
	private final static String key = "1234567890";

	/**密码加字符串--勿动*/
	private final static String passwordKey = "1234567890";
	
	/**
	 * MD5 摘要计算(byte[]).
	 * 
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] md5Digest(byte[] src) {
		MessageDigest alg = null;
		try {
			alg = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} // MD5 is 16 bit message digest

		return alg.digest(src);
	}

	/**
	 * MD5 摘要计算(String).
	 * 
	 * @param src
	 *            String
	 * @throws Exception
	 * @return String
	 */
	public static String MD5(String src){
		return byteArrayToHexString(md5Digest(src.getBytes()));
	}

	/**
	 * 验证mac
	 * 
	 * @param data
	 * @param key
	 * @param mac
	 * @return
	 * @throws Exception
	 */
	public static boolean validMd5(String data, String key, String mac) {
		String b = MD5(data + key);

		return Objects.equals(b, mac);
	}

	/**
	 * MD5 解密
	 * 
	 * @param src
	 *            String
	 * @throws Exception
	 * @return String
	 */
	public static String md5Enc(String src) {
		return byteArrayToHexString(md5Digest(src.getBytes()));
	}

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString().toUpperCase();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String getKey() {
		return key;
	}

	public static String getPasswordkey() {
		return passwordKey;
	}

	/***
	 * 专门为身份校验使用的加密
	 * @param s
	 * @return
	 */
	public static String md5Verification(String s){
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	
	
	 public static void main(String[] args) {
		 String jarry = "{'sid':'n23Xq8340J28OAC5664a2f48304F668K','password':'123456'}1234567890";
		 String mac = "5AAA9E2414A75470B284299166DD5BC8";
		 String b = "";
		 try {
			 b = MD5(jarry);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 System.out.println(b);
		 System.out.println(b.equals(mac));
	}

}
