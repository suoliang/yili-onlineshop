package com.aladingshop.store.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

/***
 * cms使用 图片的存放根目录
 * 
 * @author xupeijun
 * 
 */
public class ImageConstantCms{
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
	/** html 后台上传 商品文件的存放位置 */
	public static final String SKU_HTML_PATH = PATH + File.separator + "sku_html";
	/** html 后台上传 活动文件的存放位置 */
	public static final String ACTIVITY_HTML_PATH = PATH + File.separator + "activity_html";
	/** 后台上传 活动的地点图片 */
	public static final String SYS_ACTIVITY_PLACE_PICTURE_PATH = PATH + "/" + "activity" + "/"
			+ "activityPlace";
	/** 后台上传 活动的介绍图片 （包括banner和introduce） */
	public static final String SYS_ACTIVITY_INTRO_PATH = PATH + "/" + "activity" + "/"
			+ "activityIntro";
	/** 页面显示 广告的上传图片 */
	public static final String SYS_AD_PICTURE_SERVER = IMAGE_SERVER_ROOT_PATH + "/" + "ad";
	/** 后台上传 广告的上传图片 */
	public static final String SYS_AD_PICTURE = PATH + "/" + "ad";
	/** 页面显示 风尚宝贝图片的根路径 */  
	public static final String SYS_ACTIVITY_FUSHIONBABY_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/"
			+ "fushionbaby";
	/** 后台上传 风尚宝贝图片的根路径 */
	public static final String SYS_ACTIVITY_FUSHIONBABY_PATH = PATH + File.separator
			+ "fushionbaby";
	/** html 后台上传 文章文件的存放位置 */
	public static final String ARTICLE_HTML_PATH = PATH + File.separator + "article_html";
	/** 后台上传 亲子课程的图片根路径 */
	public static final String SYS_ACTIVITY_FAMILY_PATH = PATH + File.separator + "family";
	/** 页面取用 手机品牌图片的存放根目录 */
	public static final String APP_BRAND_IMAGE_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/"
			+ "brand";
	/** 页面取用 网站品牌图片的存放根目录 */
	public static final String WEB_BRAND_IMAGE_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/"
			+ "brand";
	/** 后台上传 网站品牌图片的存放根目录 */
	public static final String WEB_BRAND_IMAGE_PATH = PATH + File.separator + "brand";
	/** 后台上传 手机品牌图片的存放根目录 */
	public static final String APP_BRAND_IMAGE_PATH = PATH + File.separator + "brand";
	/** 后台上传 banner 图片的存放根目录 */
	public static final String BANNER_IMAGE_PATH = PATH + File.separator + "banner";
	/** html 服务端取 商品文件的存放位置 */
	public static final String SKU_HTML_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/" + "sku_html";
	/** 后台上传路径 商品分类的图片 */
	public static final String SKU_CATEGORY_PATH = PATH + File.separator + "category";
	
	/** 页面（web）显示的商品详情图片位置 */
	public static final String SKU_DETAIL_PRODUCT_IMAGE_SERVICE_PATH = IMAGE_SERVER_ROOT_PATH
			+ "/" + "sku_detail";
	public static final String SKU_DETAIL_PRODUCT_IMAGE_PATH = PATH + File.separator
			+ "sku_detail";
	/** 页面取用的 商品图片 */
	public static final String SKU_IMAGE_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/" + "sku";
	/** 后台上传的 商品图片的存放路径 */
	public static final String SKU_IMAGE_PATH = PATH + File.separator + "sku";
	
	
	public static final String SYS_ACTIVITY_PLACE_PICTURE_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + 
		      "/" + "activity" + "/" + "activityPlace";
	public static final String SYS_ACTIVITY_INTRO_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/" + 
		      "activity" + "/" + "activityIntro";
	
	public static void main(String[] args) {
		System.out.println(PATH);
		System.out.println(IMAGE_SERVER_ROOT_PATH);
	}
	
	

	
	
	
	
	
	
	
	
}
