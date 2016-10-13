package com.fushionbaby.common.constants;

public class WebConstant {

	public static final Integer UNIQUE_CODE = 32;// 生成的32位唯一标识码
	/** 域名 */
	public static final String DOMAIN_URL = "http://www.aldingshop.com";

	/** 邮件发送模板常量 -- start */
	public static final String SENDER_EMAIL = "yili@aldmt.com";// 发送验证码的邮箱

	public static final int EMAIL_TYPE = 1;// 邮件类型1代表忘记密码操作
	/** 邮件类型2代表邮件推送 */
	public static final int EMAIL_PUSH_TYPE = 2;

	public static final String EMAIL_SUBJECT = "忘记密码提示(aldingshop.com)";// 邮件主题

	public static final String EMAIL_CODE = "[code]";// 邮件的模板[code]

	public static final String EMAIL_CONTENT = "<strong>【阿拉丁玛特】尊敬的用户，您好:</strong><br />"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 感谢您关注‘阿拉丁玛特’(www.aldingshop.com)，系统自动为您发送了这封邮件。"
			+ "您本次操作的验证码为：[code]。此验证码有效期为三十分钟，请在有效期内使用验证码进行操作。" ;// 发送的邮件内容
	/** 邮件发送模板常量 -- end */
	// ---------------------------------------------------
	/** 限时抢购专区首页限时数量 */
	public static final int SKU_TIMELIMIT_SIZE = 999;
	/** 特惠标签编码 */
	public static final String Preferential_SKU_LABEL_CODE = "preferential";
	/** 海外专区标签编码 */
	public static final String GLOBAL_LABEL_CODE = "global";
	/** 首页热销商品显示数量 */
	public static final String WEB_SKU_HOT_COUNT_INDEX = "WEB_SKU_HOT_COUNT_INDEX";
	/** 品牌页热销商品显示数量 */
	public static final String WEB_SKU_HOT_COUNT_BRAND = "WEB_SKU_HOT_COUNT_BRAND";
	/** 首页品牌显示数量 */
	public static final String WEB_SKU_BRAND_COUNT = "WEB_SKU_BRAND_COUNT";
	/** 首页海外专区显示数量 */
	public static final String WEB_SKU_GLOBAL_COUNT = "WEB_SKU_GLOBAL_COUNT";
	/** 首页特惠商品显示数量 */
	public static final String WEB_SKU_PREFERENCE_COUNT = "WEB_SKU_PREFERENCE_COUNT";

	/** 起始索引 */
	public static final int START_INDEX = 1;
	/** 默认起始页数 */
	public static final int DEFAULT_PAGE_INDEX = 1;
	/** 默认每页显示条数 */
	public static final int DEFAULT_PAGE_SIZE = 20;
	/** 商品列表每页显示数量（50） */
	public static final int SKU_LIST_SIZE = 25;
	/** 猜你喜欢的最大显示数量 */
	public static final int LINK_SKU_SIZE = 8;
	/** 商品评论最大分数 */
	public static final int SKU_COMMENT_SCORE = 5;
	/** 商品评论每页显示数量 */
	public static final int SKU_COMMENT_SIZE = 5;

	/** 收藏的商品每页显示数量 */
	public static final int COLLECT_SIZE = 6;
	/** 商品详情显示图片数量 */
	public static final int SKU_DETAIL_IMG_COUNT = 5;
	/** 亲子活动 评论每页显示数量 */
	public static final int CHIDREN_COMMENT_SIZE = 4;

	/** 使用优惠券的默认提示语 */
	public static final String WEB_CARD_DEFAULT_HINT_MESSAGE = "代金券不存在或已被使用";

	/** 分类图片类型 */
	public static final String CATEGORY_LOGO = "CATEGORY_LOGO";
	public static final String CATEGORY_BANNER = "CATEGORY_BANNER";
	public static final String CATEGORY_TITLE = "CATEGORY_TITLE";

	/** 提示信息 */
	public static final Integer NO_LOGIN = 1; /**未登录 */
	public static final Integer LOGIN_SUCCESS = 2; /**登录成功*/
	public static final Integer LOGIN_FAILURE = 3; /**登录失败 */ 
	
	public static final Integer SUCCESS = 2 ; /**操作成功 */ 
	public static final Integer FAILURE = 3; /** 操作失败 */
	
	
	/**操作信息 */
	public static final String OPEATION_ADD = "0"; /** 添加操作标识 */
	public static final String OPEATION_DEL  = "1"; /** 删除操作标识 */
	
	/**错误信息 */
	public static final String LOGIN_FAILURE_FIRST = "您输入的密码和用户名不匹配，请重新输入!";
	public static final String LOGIN_FAILURE_SECOND="请确认您的当前操作是否合法!";
}
