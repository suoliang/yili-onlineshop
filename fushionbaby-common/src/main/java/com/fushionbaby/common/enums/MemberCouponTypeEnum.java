package com.fushionbaby.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/***
 * 优惠券和规则类型枚举
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月31日下午3:27:19
 */
public enum MemberCouponTypeEnum {
	
	/**注册时赠送的优惠券  券号为  mr开头*/
	MEMEBER_REGISTER_GIVE("MEMEBER_REGISTER_GIVE","mr"),
	/**注册时赠送的优惠券  券号为  mr开头*/
	REGISTER_SEND("REGISTER_SEND","rs"),
	/**订单满一百时 赠送 券号开头为 fo*/
	ORDER_FULL_100_GIVE("ORDER_FULL_100_GIVE","fo"),
	/**订单满五十时 赠送 券号开头为 ff*/
	ORDER_FULL_50_GIVE("ORDER_FULL_50_GIVE","ff");
	
	/**订单满金额*/
	private String type;
	/**优惠券赠送金额*/
	private String code;

	/** 构造方法 */
	private MemberCouponTypeEnum(String type, String code) {
		this.type = type;
		this.code = code;
	}

	/** 通过type 得到 code */
	public static String getCode(String type) {
		for (MemberCouponTypeEnum c : MemberCouponTypeEnum.values()) {
			if (c.type.equals(type)) {
				return c.code;
			}
		}
		return null;
	}
	/** 通过code得到type */
	public static String getType(String code) {
		for (MemberCouponTypeEnum c : MemberCouponTypeEnum.values()) {
			if (c.code.equals(code)) {
				return c.type;
			}
		}
		return null;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static Map<String, String> getMemberCouponTypeMap(){
		Map<String, String> map=new HashMap<String, String>();
		for(MemberCouponTypeEnum m:MemberCouponTypeEnum.values()){
			map.put(m.getType(), m.getCode());
		}
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(MemberCouponTypeEnum.MEMEBER_REGISTER_GIVE.getCode());
		System.out.println(MemberCouponTypeEnum.getType("fo"));
		System.out.println(MemberCouponTypeEnum.getCode("MEMEBER_REGISTER_GIVE"));
		System.out.println(MemberCouponTypeEnum.IsMemberCoupon("ff10245454"));
		System.out.println(MemberCouponTypeEnum.IsMemberCoupon("10245454"));
	}
	
	
	/***
	 * 判断一个优惠券号，是否和用户绑定
	 * @param couponCode
	 * @return
	 */
	public static boolean  IsMemberCoupon(String couponCode){
		boolean flag=false;
		if(StringUtils.isBlank(couponCode)){
			return flag;
		}
		for(MemberCouponTypeEnum m:MemberCouponTypeEnum.values()){
			if(couponCode.contains(m.getCode())){
				flag=true;
				break;
			}
		}
		return flag;
	}
	
}
