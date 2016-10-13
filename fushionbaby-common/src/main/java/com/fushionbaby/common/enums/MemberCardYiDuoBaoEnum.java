package com.fushionbaby.common.enums;
/***
 * 益多宝
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月17日下午2:01:20
 */
public enum MemberCardYiDuoBaoEnum {
	
	/**季卡*/
	YIDUOBAO_JI_KA("季卡","1"),
	/**双季卡*/
	YIDUOBAO_SHUANGJI_KA("双季卡","2"),
	/**年卡*/
	YIDUOBAO_NIAN_KA("年卡","3");
	
	/**name */
	private String typeName;
	/** code */
	private String typeCode;

	/** 构造方法 */
	private MemberCardYiDuoBaoEnum(String name, String code) {
		this.typeName = name;
		this.typeCode = code;
	}

	/** 通过name 得到 code */
	public static String getTypeCode(String name) {
		for (MemberCardYiDuoBaoEnum c : MemberCardYiDuoBaoEnum.values()) {
			if (c.typeName.equals(name)) {
				return c.typeCode;
			}
		}
		return null;
	}
	/** 通过code 得到 name */
	public static String getTypeName(String code) {
		for (MemberCardYiDuoBaoEnum c : MemberCardYiDuoBaoEnum.values()) {
			if (c.typeCode.equals(code)) {
				return c.typeName;
			}
		}
		return null;
	}
	
	public String getTypeCode() {
		return typeCode;
	}

	public String getTypeName() {
		return typeName;
	}


	public static void main(String[] args) {
		System.out.println(MemberCardYiDuoBaoEnum.YIDUOBAO_JI_KA.typeCode);
		System.out.println(MemberCardYiDuoBaoEnum.getTypeName("1"));
		System.out.println(MemberCardYiDuoBaoEnum.getTypeCode("年卡"));
	}
	
}
