/**
 * 
 */
package com.fushionbaby.cms.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author mengshaobo
 * 
 */
public class FileUploadTools {

	/** 添加图片到上传路径 */
	public static File addFile(MultipartFile file, String path) throws IllegalStateException,
			IOException {
		File targetFile = null;
		if (file != null) {
//			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//			Matcher m = p.matcher(file.getOriginalFilename());
//			String fileName = UUID.randomUUID() + m.replaceAll("");
			String orifile = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String fileName = UUID.randomUUID() + orifile;
			targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			file.transferTo(targetFile);
		}
		return targetFile;
	}

	/** 添加图片到上传路径 */
//	 public static File addFile(MultipartFile file, String type,
//	 HttpServletRequest request) throws IllegalStateException,
//	 IOException {
//	 File targetFile = null;
//	 if (file != null) {
//	 String path = "e:/upload/" + type;
//	 String fileName = UUID.randomUUID() + file.getOriginalFilename();
//	
//	 targetFile = new File(path, fileName);
//	 if (!targetFile.exists()) {
//	 targetFile.mkdirs();
//	 }
//	 file.transferTo(targetFile);
//	 }
//	 return targetFile;
//	 }
	public static void main(String[] args) {
		
		String name="123.213.jpg";
		String orifile = name.substring(name.lastIndexOf("."));
		System.out.println(orifile);
	}
}
