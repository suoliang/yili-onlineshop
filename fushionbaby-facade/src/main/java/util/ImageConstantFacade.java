package util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

/***
 *  facade项目使用  图片的存放根目录
 * 
 * @author xupeijun
 * 
 */
public class ImageConstantFacade{
	/** 读取配置文件 image.properties */
	protected final static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("image.properties");
			IMAGE_SERVER_ROOT_PATH = properties.getProperty("image.server_root.path");
			PATH = properties.getProperty("image.path");
		} catch (IOException e) {
			log.error("init image.path error.", e);
		}

	}
	/** 上传的路径 */
	public static String PATH;
	/** 下载路径 */
	public static String IMAGE_SERVER_ROOT_PATH;
	
	/** 页面取用 会员头像 的存放根目录 */
	public static final String MEMBER_IMAGE_SERVER_PATH = IMAGE_SERVER_ROOT_PATH + "/" + "member"	+ "/" + "image";
	/** 评价商品图片 上传路径*/
	public static final String MEMBER_COMMENT_PICTURE_PATH=PATH + "/" + "commentPic";
	/** 下载路径*/
	public static final String MEMBER_COMMENT_PICTURE_SERVER_PATH=IMAGE_SERVER_ROOT_PATH  +  "/"  + "commentPic";
	
	/**益多宝卡的图片存放位置*/
	public static final String YIDUOBAO_PICTURE_SERVER_PATH=IMAGE_SERVER_ROOT_PATH  +  "/"  + "yiduobao";
	
	
	
public static void main(String[] args) {
	System.out.println(PATH);
	System.out.println(IMAGE_SERVER_ROOT_PATH);
}
}
