/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月15日下午8:02:30
 */
package util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月15日下午8:02:30
 */
public class MobileConstant {
	/** 读取配置文件 image.properties */
	protected final static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("mobile.properties");
			MOBILELIST = properties.getProperty("mobile.list");
		} catch (IOException e) {
			log.error("init image.path error.", e);
		}

	}
	/** 上传的路径 */
	public static String MOBILELIST;
}
