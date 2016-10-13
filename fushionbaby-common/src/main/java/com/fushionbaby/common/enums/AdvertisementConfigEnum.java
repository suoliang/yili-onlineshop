package com.fushionbaby.common.enums;

public enum AdvertisementConfigEnum {

	/** 安全座椅 */
	WEB_CATEGORY_SAFETY_CHAIR("web_safety_chair", "F001"),
	/** 儿童车 */
	WEB_CATEGORY_CHILD_CAR("web_chidren_car", "F002"),
	/** 洗护用品 */
	WEB_CATEGORY_WASH_GOODS("web_wash_goods", "F003"),
	/** 喂辅用品 */
	WEB_CATAGORY_FEED_GOODS("web_feed_goods", "F004"),
	/** 玩具商品 */
	WEB_CATAGORY_TOY_GOODS("web_toy_goods", "F005"),
	/** 主题家居 */
	WEB_CATAGORY_HOUSE_GOODS("web_house_goods", "F006"),
	/** 服装 */
	WEB_CATAGORY_CLOTHES("web_clothes", "F007");

	/** 商品分类code */
	private String apCode;
	/** 广告配置ap_code */
	private String categoryCode;

	/** 构造方法 */
	private AdvertisementConfigEnum(String apCode, String categoryCode) {
		this.apCode = apCode;
		this.categoryCode = categoryCode;
	}

	/** 通过分类编号获取广告编号 */
	public static String getApCode(String categoryCode) {
		for (AdvertisementConfigEnum c : AdvertisementConfigEnum.values()) {
			if (c.categoryCode.equals(categoryCode)) {
				return c.apCode;
			}
		}
		return null;
	}

	/** 通过广告编号获取分类编号 */
	public static String getCategoryCode(String apCode) {
		for (AdvertisementConfigEnum c : AdvertisementConfigEnum.values()) {
			if (c.apCode.equals(apCode)) {
				return c.categoryCode;
			}
		}
		return null;

	}

	public String getApCode() {
		return apCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

}
