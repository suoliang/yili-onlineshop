package com.aladingshop.store.config;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;
/**
 * 全局配置
 * @author 孟少博
 * @version 2015-06-08
 */
public class Global {
	protected final static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("envi.properties");
			Properties propertieImg = PropertiesLoaderUtils.loadAllProperties("image.properties");
			ADMIN_PATH = properties.getProperty("cms.adminPath");
			IMAGE_PATH = propertieImg.getProperty("image.server_root.path");
			CKFINDER_BASEURL = propertieImg.getProperty("ckfinder.baseUrl");
			CKFINDER_BASEDIR = propertieImg.getProperty("ckfinder.baseDir");

		} catch (IOException e) {
			log.error("init cms.adminPath error.", e);
		}
	}
	/**项目访问路径*/
	private static String ADMIN_PATH;
	/**图片路径*/
	private static String IMAGE_PATH;
	/**CKFINDER获取图片的网络路径*/
	private static String CKFINDER_BASEURL;
	/**CKFINDER存放图片的物理地址*/
	private static String CKFINDER_BASEDIR;
	/** 
	 * 获取当前用户的地址
	 * @return
	 */
	public static String getAdminPath(){
		return ADMIN_PATH;
	}
	
	/**
	 * 获取当前图片地址
	 * */
	public static String getImagePath(){
		return IMAGE_PATH;
	}
	/**
	 * CKFINDER获取图片的网络路径
	 * @return
	 */
	public static String getBaseUrl(){
		return CKFINDER_BASEURL;
	}
	/**
	 * CKFINDER存放图片的物理地址
	 * @return
	 */
	public static String getBaseDir(){
		return CKFINDER_BASEDIR;
	}
	
	public static void main(String[] args) {
		System.out.println(ADMIN_PATH);
		System.out.println(IMAGE_PATH);
		System.out.println(CKFINDER_BASEURL);
		System.out.println(CKFINDER_BASEDIR);
	}
	
}
