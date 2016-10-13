package com.fushionbaby.common.security;

import java.security.*;  

import javax.crypto.*;  

import com.fushionbaby.common.constants.SecurityConstant;

import sun.misc.*;

/**       
  * 使用DES加密与解密,可对byte[],String类型进行加密与解密  
  * 密文可使用String,byte[]存储.   
  * 方法:  
  * void getKey(String   strKey)从strKey的字条生成一个Key     
  * String getEncString(String strMing)对strMing进行加密,返回String密文  
  * String getDesString(String strMi)对strMin进行解密,返回String明文  
  * byte[] getEncCode(byte[] byteS)byte[]型的加密  
  * byte[] getDesCode(byte[] byteD)byte[]型的解密  
  */ 
public class Encrypt {

	private Key key;
	private byte[] byteMi = null;
	private byte[] byteMing = null;
	private String strMi= "";
	private String strM= ""; 
	  
	//根据参数生成KEY   
	public void setKey(String strKey) { 
	     try {  
	          KeyGenerator _generator = KeyGenerator.getInstance("DES");  
	          _generator.init(new SecureRandom(strKey.getBytes()));  
	          this.key = _generator.generateKey();  
	          _generator=null;
	     } catch(Exception e) {
	        e.printStackTrace();
	     }	   
	}  
	  
	//加密String明文输入,String密文输出  
	public void setEncString(String strMing) {
		BASE64Encoder base64en = new BASE64Encoder();  
	     try {
	         this.byteMing = strMing.getBytes("UTF8");  
	         this.byteMi = this.getEncCode(this.byteMing);  
	         this.strMi = base64en.encode(this.byteMi);
	     } catch(Exception e) {
	        e.printStackTrace();
	     } finally { 
	         this.byteMing = null;  
	         this.byteMi = null;
	      }
	}  
	  
	//加密以byte[]明文输入,byte[]密文输出    
	private byte[] getEncCode(byte[] byteS){
	    byte[] byteFina = null;  
	    Cipher cipher;  
	    try {
	        cipher = Cipher.getInstance("DES");  
	        cipher.init(Cipher.ENCRYPT_MODE,key);  
	        byteFina = cipher.doFinal(byteS);
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        cipher = null;
	    }   
	    return byteFina;
	} 
	  
	//解密:以String密文输入,String明文输出   
	public void setDesString(String strMi){  
	    BASE64Decoder base64De = new BASE64Decoder();   
	    try {
	        this.byteMi = base64De.decodeBuffer(strMi);  
	        this.byteMing = this.getDesCode(byteMi);  
	        this.strM = new String(byteMing,"UTF8");  
	    }  catch(Exception e) {
	      e.printStackTrace();
	    } finally {
	       base64De = null;  
	       byteMing = null;  
	       byteMi = null;
	    }  
	}
	 
	//解密以byte[]密文输入,以byte[]明文输出    
	private byte[] getDesCode(byte[] byteD){
	    Cipher cipher;  
	    byte[] byteFina=null;  
	    try {
	       cipher = Cipher.getInstance("DES");  
	       cipher.init(Cipher.DECRYPT_MODE,key);  
	       byteFina = cipher.doFinal(byteD);
	    } catch(Exception e) {
	       e.printStackTrace();
	    } finally {
	       cipher=null;
	    }  
	    return byteFina;
	} 
	 
	//返回加密后的密文strMi  
	public String getStrMi() {
	     return strMi;
	}
	//返回解密后的明文
	public String getStrM() {
	    return strM;
	}
	
	/**
	 * DES加密文本
	 * @param text
	 * @return 返回加密后的文本
	 */
	public static String encryption(String text){
		Encrypt des = new Encrypt();
		String key = SecurityConstant.SECURITY_DES_KEY; //初始化密钥。
		des.setKey(key);    //调用set函数设置密钥。
		des.setEncString(text);//将要加密的明文传送给Encrypt.java进行加密计算。
		String Mi=des.getStrMi();  //调用get函数获取加密后密文。
		return Mi;
	}
	
	/**
	 * DES解密 文本
	 * @param text
	 * @return 返回解密后的文本
	 */
	public static String decryption(String text){
		Encrypt des = new Encrypt();
		String key = SecurityConstant.SECURITY_DES_KEY; //初始化密钥。
		des.setKey(key);    //调用set函数设置密钥。
		des. setDesString(text);//将要解密的密文传送给Encrypt.java进行解密计算。
		String M=des.getStrM();  //调用get函数获取加密后密文。
		return M;
	}
	
	
	/**
	 * main测试
	 * @param args
	 */
    public static void main(String[] args) {  
        String testStr = "fushinbaby加密测试";
        System.out.println("testStr=========="+testStr);
        
        //加密
        String Mi = Encrypt.encryption(testStr);
    	System.out.println("加密后密文=========="+Mi);
    	
    	//密文解密：
    	String M = Encrypt.decryption(Mi);//调用get函数获取解密后明文。
    	System.out.println("解密后明文=========="+M);
    }  
}
