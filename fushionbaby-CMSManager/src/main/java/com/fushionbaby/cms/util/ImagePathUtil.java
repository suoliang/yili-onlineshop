/**
 * 
 */
package com.fushionbaby.cms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mengshaobo
 *
 */
public class ImagePathUtil {
	
	private static final Log LOGGER = LogFactory.getLog(ImagePathUtil.class);
	
	/**
	 * 通过 图片的java地址获取图片名称集合
	 * @param imageJavePath 图片的java地址
	 * @return
	 */
	public static List<String> getImageNameList(String imageJavaPath){
		List<String> graphicDetailsList = new ArrayList<String>();
		String[] gd =StringUtils.split(imageJavaPath, "|");
		for(int i = 0 ; i < gd.length;i++){
			String pic = gd[i];
			String upPath = "";
			String fileAllName = "";
			if(StringUtils.isBlank(pic)){
				continue;
			}
			if(StringUtils.contains(pic, "connector.java?")){
				
				 String fileName = null;
				 try {
					fileName = pic.substring( pic.indexOf("FileName=") );
					upPath = conventUri(pic);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				 fileAllName = upPath + StringUtils.split(fileName, "=")[1];
			}else{
				 fileAllName = upPath + pic.substring(StringUtils.indexOf(pic, "mages/")+"mages/".length());
			}
			System.out.println(fileAllName);
			graphicDetailsList.add(fileAllName);
		}
		return graphicDetailsList;
	}
	/**
	 * 获取单个图片名称
	 * @param imageJavaPath 图片java地址
	 * @return
	 */
	
	public static String getImageName(String imageJavaPath){
		String fileName = null; 
		if(StringUtils.isNotBlank(imageJavaPath) && StringUtils.contains(imageJavaPath, "connector.java?")){
			 try {
				String filePath = imageJavaPath.substring(imageJavaPath.indexOf("FileName=") );
				fileName = StringUtils.split(filePath, "=")[1];
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("图片地址获取名称时出现异常！");
			}
		}else{
			fileName =  imageJavaPath.substring(StringUtils.indexOf(imageJavaPath, "mages/")+"mages/".length());
		}
		return fileName;
	}
	
	/**
	 * 转换图片地址
	 * @param imagePath
	 * @return
	 */
	public static String  conventUri(String imagePath){
		String startupPath = null;
		try {
			if(StringUtils.contains(imagePath, "connector.java?")){
				String upPath = imagePath.substring(imagePath.indexOf("startupPath="), imagePath.indexOf("FileName=")-1);
				
				String path = URLDecoder.decode(upPath, "utf-8");
				String p = path.substring(path.indexOf("=")+1);
				//startupPath = p.replaceAll(":", "");
				startupPath = p.substring(p.indexOf(":")+2);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("图片地址获取名称时出现异常！");
			e.printStackTrace();
		}
		return startupPath;
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String startupPath = conventUri("|http://localhost:8080/fushionbaby-CMSManager/userfiles/_thumbs/Images/sku/products/2015/06/2Lighthouse.jpg|http://localhost:8080/fushionbaby-CMSManager/static/ckfinder/core/connector/java/connector.java?command=Thumbnail&type=Images&currentFolder=%2Fsku%2Fproducts%2F2015%2F06%2F&langCode=zh-cn&hash=74bf5710aff4004&startupPath=images%3A%2Fsku%2Fproducts%2F2015%2F06%2F&FileName=1Koala.jpg");
//		String uls = "|http://localhost:8080/fushionbaby-CMSManager/userfiles/_thumbs/Images/sku/products/2015/06/2Lighthouse.jpg|http://localhost:8080/fushionbaby-CMSManager/static/ckfinder/core/connector/java/connector.java?command=Thumbnail&type=Images&currentFolder=%2Fsku%2Fproducts%2F2015%2F06%2F&langCode=zh-cn&hash=74bf5710aff4004&startupPath=images%3A%2Fsku%2Fproducts%2F2015%2F06%2F&FileName=1Koala.jpg";	
//		getImageNameList(uls);
		//System.out.println( startupPath);
		//String startupPath = "http://localhost:8080/fushionbaby-CMSManager/userfiles/_thumbs/Images/sku/products/2015/06/2Lighthouse.jpg";
//		String startupPath = "http://localhost:8080/fushionbaby-CMSManager/static/ckfinder/core/connector/java/connector.java?command=Thumbnail&type=Images&currentFolder=%2Fsku%2Fproducts%2F2015%2F06%2F&langCode=zh-cn&hash=74bf5710aff4004&startupPath=images%3A%2Fsku%2Fproducts%2F2015%2F06%2F&FileName=1Koala.jpg";
//		System.out.println( getImageName(startupPath));
		
//		String fileNameBefore = "core/connector/java/ddd";
//		String[] files = fileNameBefore.split("/");
//	
//		System.out.println(files[files.length - 2]    );
		
		String file = "E:\\product\\sku\\10001\\";
		//System.out.println(file.split("\\\\")[file.split("\\\\").length-1]);
		System.out.println(file.substring(file.lastIndexOf(file.lastIndexOf("\\\\")+1)));
		
		String ftpRootPath = "var/fff/201506";
		//System.out.println(ftpRootPath.substring(ftpRootPath.lastIndexOf("/")+1));
		
	}
}
