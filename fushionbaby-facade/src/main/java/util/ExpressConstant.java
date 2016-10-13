package util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

/**
 * 快递100API查询配置
 * 
 * @author suntao
 *
 */
public class ExpressConstant {
	/** 读取配置文件 express.properties */
	private final static Logger log = LoggerFactory.getLogger(ConfigUtil.class);

	static {
		try {
			Properties properties = PropertiesLoaderUtils
					.loadAllProperties("express.properties");
			searchKey = properties.getProperty("search.key");
		} catch (IOException e) {
			log.error("init image.path error.", e);
		}

	}
	/** 查询授权秘钥 */
	public static String searchKey;

}
