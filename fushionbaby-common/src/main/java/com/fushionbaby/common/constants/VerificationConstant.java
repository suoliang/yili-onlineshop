package com.fushionbaby.common.constants;
/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月30日下午7:27:24
 */
public class VerificationConstant {
	/**认证相关的商户id*/
	public static final String MALL_ID="110204";
	/** 认证相关的key*/
	public static final String API_KEY="6150d00ec8997d6a43b7f6dced4bb5f0";
	/**认证状态 1代表未验证*/
	public static final String IS_VALIDATE_STATUS_NULL="1";
	/**认证状态 2代表验证失败*/
	public static final String IS_VALIDATE_STATUS_FAIL="2";
	/**认证状态 3代表验证成功*/
	public static final String IS_VALIDATE_STATUS_SUCCESS="3";
	/**认证成功的返回标志  status=1000*/
	public static final String VALIDATE_SUCCESS_RESPONSECODE="1000";
	/**全局配置   是否进行身份认证的标志控制    y代表开启认证  n代表不开启认证 */
	public static final String OPEN_OR_CLOSE_VERIFICATION_ID="OPEN_OR_CLOSE_VERIFICATION_ID";
	/**全局配置   是否进行银行卡认证的标志控制    y代表开启认证  n代表不开启认证 */
	public static final String OPEN_OR_CLOSE_VERIFICATION_CARD="OPEN_OR_CLOSE_VERIFICATION_CARD";
    /**验证的类型   1、身份证号码真实姓名 */
	public static final String VERIFICATION_TYPE_ID="1";
	/**验证的类型  2、银行卡号真实姓名 */
	public static final String VERIFICATION_TYPE_BANK="2";
    /**每天最多的验证次数 3次一天*/
	public static final int VERIFICATION_MAX_TIMES_ONEDAY=3;
	
	
	
	
	
	

}
