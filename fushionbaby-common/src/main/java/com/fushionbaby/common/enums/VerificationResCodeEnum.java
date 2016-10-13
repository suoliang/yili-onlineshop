package com.fushionbaby.common.enums;

import java.util.HashMap;
import java.util.Map;

/***
 * 认证服务返回数据的枚举
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年1月19日下午2:30:36
 */
public enum VerificationResCodeEnum {
	
     /**验证一致*/
	AGREED("一致","1000"),
	/**验证不一致*/
	DIS_AGREED("不一致","1001"),
	/**验证库中无此号*/
	THERE_IS_NO_DATA("库中无此号","1002"),
	/**验证 核验失败，请稍候再试*/
	CHECK_FAIL_AND_TRY_AGAIN("核验失败，请稍候再试","1110"),
	/**验证  商家 ID不合法*/
	ID_IS_ILLEGAL("商家 ID不合法","1101"),
	/**验证  身份证姓名不合法*/
	ID_NAME_IS_ILLEGAL("身份证姓名不合法","1102"),
	/**验证  身份证号码不合法*/
	ID_NO_IS_ILLEGAL("身份证号码不合法","1103"),
	/**验证  签名不合法*/
	SIGN_IS_ILLEGAL("签名不合法","1104"),
	/**验证  第三方服务器异常*/
	SERVICE_IS_EXCEPTION("第三方服务器异常","1105"),
	/**验证  账户余额不足*/
	NOT_ENOUGH_MONEY("账户余额不足","1106"),
	/**验证  tm 不合法*/
	TM_IS_ILLEGAL("tm 不合法","1107"),
	/**验证  其他异常*/
	OTHER_EXCEPTION("其他异常","1108"),
	/**验证   账号被暂停*/
	ACCOUNT_IS_ILLEGAL("账号被暂停","1109"),
	/**验证  请稍后再试*/
	PLEASE_TRY_AGAIN("请稍后再试 ","1003");
	
	
	/** 标题*/
	private String msg;
	/** code */
	private String code;

	/** 构造方法 */
	private VerificationResCodeEnum(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	/** 通过编号获取标题 */
	public static String getMsg(String code) {
		for (VerificationResCodeEnum s : VerificationResCodeEnum.values()) {
			if (s.code.equals(code)) {
				return s.msg;
			}
		}
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	/***
	 * 得到该枚举类的map集合
	 * @return
	 */
	public static Map<String, String> getVerificationResCodeEnumMap(){
		Map<String, String> map=new HashMap<String, String>();
		for(VerificationResCodeEnum o:VerificationResCodeEnum.values()){
			map.put(o.getCode(), o.getMsg());
		}
		return map;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getVerificationResCodeEnumMap().size());
		System.out.println(VerificationResCodeEnum.getMsg("1000"));
		System.out.println(VerificationResCodeEnum.CHECK_FAIL_AND_TRY_AGAIN.getCode());
		
	}
	
	
}
