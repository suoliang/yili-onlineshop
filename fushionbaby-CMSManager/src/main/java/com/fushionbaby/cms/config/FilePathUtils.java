package com.fushionbaby.cms.config;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

public class FilePathUtils {

	protected final static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("image.properties");
			IMAGE_PREFIX = properties.getProperty("ftp.imagePrefix");
			RELATIVE_PREFIX = properties.getProperty("image.relativePath");
		} catch (IOException e) {
			log.error("init image.path error.", e);
		}
	}
	/** 图片前缀*/
	private static String IMAGE_PREFIX;
	/** 图片相对路径前缀*/
	private static String RELATIVE_PREFIX;
	
	/**
	 * 将图片的相对路径转换为绝对路径
	 * 
	 * @param uri
	 * @param request
	 * @return
	 */
	public static String uri2url(String uri, HttpServletRequest request) {
			String url = uri.replaceAll(RELATIVE_PREFIX.trim(), IMAGE_PREFIX.trim());
			return url.replaceAll("/_thumbs/", "/");
	}
}
