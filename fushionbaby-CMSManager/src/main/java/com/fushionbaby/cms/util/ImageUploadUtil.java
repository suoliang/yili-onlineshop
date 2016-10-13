
package com.fushionbaby.cms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 图片ftp上传接口
 * 
 * @Class Name ImageUploadUtil
 * @Author 孟少博
 * @version 2015-6-12
 */

public class ImageUploadUtil {
	/**基本路径*/
	private static final String FTP_SERVICE_BASE_URL = "/userfiles";
	/** URL*/
	private static final String URL = "127.0.0.1";
	/** port*/
	private static final Integer PORT=2121;
	/** username*/
	private static final String USER_NAME="admin";
	/** password*/
	private static final String PASSWORD="admin";
	/** dir*/
	private static final String DIR="/opt/pic";

	/**
	 * 传入图片在tomcat应用中的相对路径（基于userfiles）,上传到ftp服务器
	 * 例如 图片已上传到webapps/userfiles/a/b/a.jpg,则传入参数为：a/b/a.jpg
	 * 
	 * @Methods Name uploadImage
	 * @Create In Mar 10, 2015 By wangyu
	 * @param relativeUrl 图片相对路径
	 * @return boolean
	 */
	public static   boolean uploadImage( String relativeUrl) {
		
		if (StringUtils.isBlank(relativeUrl)) {
			// 传入路径为空
			return false;
		}
		if (!relativeUrl.startsWith("/")) {
			relativeUrl = "/" + relativeUrl;
		}
		File imgFile = new File(FTP_SERVICE_BASE_URL + relativeUrl);

		int index = relativeUrl.lastIndexOf("/");

		String foldenPath = relativeUrl.substring(0, index);
		String imageName = relativeUrl.substring(index + 1);

		try {
			FTPUtilsBak fu = new FTPUtilsBak(URL, PORT,USER_NAME, PASSWORD);
			fu.connectFTPServer();
			fu.getFtp().setControlEncoding("UTF-8");
			fu.getFtp().changeWorkingDirectory(DIR);
			if (!StringUtils.isBlank(relativeUrl)) {
				String[] foldens = foldenPath.split("/");
				String path = DIR;
				for (String folden : foldens) {
					if (!StringUtils.isBlank(folden)) {
						fu.getFtp().makeDirectory(folden);
						path = path + "/" + folden;
						fu.getFtp().changeWorkingDirectory(path);
					}
				}
			}
			InputStream inputStream = new FileInputStream(imgFile);
			imageName = new String(imageName.getBytes("UTF-8"),"ISO-8859-1");
			fu.uploadFile(inputStream, imageName);

			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 获得相同的图片
	 * @param beforeImages
	 * @param newImages
	 * @return
	 */
	public static List<String> getEqImages(List<String> beforeImages,List<String> newImages){
		List<String> eqImages = new ArrayList<String>();
		for (String beforeImage : beforeImages) {
			if(newImages.contains(beforeImage)){
				eqImages.add(beforeImage);
			}
		}
		return eqImages;
	}
	/**
	 * 获得添加数据库的新图片
	 * @param newImages 新图片集合
	 * @param eqImages 与数据库存在相同的图片
	 * @return
	 */
	public static Set<String> getNewImageSet(List<String> newImages,List<String> eqImages){
		Set<String> newImageSet = new HashSet<String>();/*添加到数据库的新图片*/
		if(CollectionUtils.isEmpty(eqImages)){
			for (String n : newImages) {
				newImageSet.add(n);
			}
			return newImageSet;
		}
		for(String newImage :newImages){
			if(!eqImages.contains(newImage)){
				newImageSet.add(newImage);
			}
			
		}
		return newImageSet;
	}
	/**
	 * 添加需要移除的图片
	 * @param beforeImages 之前的图片
	 * @param eqImages 相同的图片
	 */
	public static List<String> removeImages(List<String> beforeImageUrls,List<String> eqImages){
		List<String> removeImage = new ArrayList<String>();
		if(CollectionUtils.isEmpty(eqImages)){
			return removeImage;
		}
		for (String bi : beforeImageUrls) {  
			  if(!eqImages.contains(bi)){
				  removeImage.add(bi);
			  }
		 }  
		return removeImage;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(ImageUploadUtil.uploadImage("E:/image"));
	}
}
