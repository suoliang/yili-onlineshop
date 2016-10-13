package com.fushionbaby.wap.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

/***
 *   wap使用  图片的存放根目录
 * 
 * @author xupeijun
 * 
 */
public class ImageConstantWap{
	/** 读取配置文件 image.properties */
	protected final static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("image.properties");
			PATH = properties.getProperty("image.path");
			IMAGE_SERVER_ROOT_PATH = properties.getProperty("image.server_root.path");

		} catch (IOException e) {
			log.error("init image.path error.", e);
		}

	}
	/** 后台上传的路径 */
	public static String PATH;

	/** 服务器端的图片根路径 */
	public static String IMAGE_SERVER_ROOT_PATH;
	
	/** 页面显示 亲子课程的图片根路径 */
	public static final String SYS_ACTIVITY_FAMILY_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/"
			+ "family";
	/** 页面显示 活动的介绍图片 （包括banner和introduce） */
	public static final String SYS_ACTIVITY_INTRO_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/"
			+ "activity" + "/" + "activityIntro";
	
	/** 页面显示 风尚宝贝图片的根路径 */
	public static final String SYS_ACTIVITY_FUSHIONBABY_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/"
			+ "fushionbaby";
	/** 页面显示 广告的上传图片 */
	public static final String SYS_AD_PICTURE_SERVER = IMAGE_SERVER_ROOT_PATH + "/" + "ad";
	
	/** 上传 会员头像 的存放根目录 */
	public static final String MEMBER_IMAGE_PATH = PATH + "/" + "member" + "/" + "image";
	
	/** 页面取用的 商品图片 */
	public static final String SKU_IMAGE_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/" + "sku";
	
	public static void main(String[] args) {
		System.out.println(PATH);
		System.out.println(IMAGE_SERVER_ROOT_PATH);
	}
}
