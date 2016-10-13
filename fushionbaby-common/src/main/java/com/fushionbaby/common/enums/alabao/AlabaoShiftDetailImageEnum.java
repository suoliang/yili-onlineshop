package com.fushionbaby.common.enums.alabao;

/**
 * @description 如意消费卡转入账单详情--图片
 * @author 索亮
 * @date 2015年12月3日上午10:49:07
 */
public enum AlabaoShiftDetailImageEnum {
	/**
	 * 1,如意消费卡转入
	 */
	ALABAO("1","bill/201512/ruyixiaofeika_icon.png"),
	/**
	 * 2,银联转入
	 */
	ALABAO_APP_UNION("2","bill/201512/unionpay_icon.png"),
	/**
	 * 3,微信转入
	 */
	ALABAO_APP_WX("3","bill/201512/weixinpay_icon.png"),
	/**
	 * 4,支付宝转入
	 */
	ALABAO_APP_ZFB("4","bill/201512/alipay_icon.png"),
	/**
	 * 5,阿拉丁卡转入
	 */
	ALABAO_APP_YDB("5","bill/201512/aladingcard_icon.png"),
	/**
	 * 6,红包转入
	 */
	ALABAO_APP_RED("6","bill/201512/red_icon.png"),
	/**
	 * 7,退款转入
	 */
	ALABAO_APP_REFUND("7","bill/201512/refund_ruyixiaofeika_icon.png"),
	/**
	 * 8,实体卡转入
	 */
	ALABAO_APP_ENTITY_CARD("8","bill/201512/recharge_icon.png");
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoShiftDetailImageEnum(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/** 根据枚举代码得到对应的汉字 */
	public static String parseCode(String code) {
		for (AlabaoShiftDetailImageEnum c : AlabaoShiftDetailImageEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
}
