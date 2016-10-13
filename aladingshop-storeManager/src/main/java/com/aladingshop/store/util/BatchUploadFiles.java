package com.aladingshop.store.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aladingshop.store.util.bean.FileInfo;
import com.aladingshop.store.util.bean.UploadResult;
import com.aladingshop.store.util.constant.Constant;

/**
 * 批量上传文件(图片、txt、excel)
 * 
 * @author sun tao 2015年6月25日
 */
public class BatchUploadFiles {
	private static final Log LOGGER = LogFactory.getLog(BatchUploadFiles.class);

	private static String[] IMAGE_ARRAY = new String[] { "jpg", "png", "docx", "xlsx", "gif" };

	public static String getDicAbsPath(String[] pathArray) {
		String resultPath = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		String dateDic = dateFormat.format(new Date());
		for (String element : pathArray) {
			resultPath += "/" + element;
		}

		resultPath += "/" + dateDic;

		return resultPath;
	}

	public static String getFileBeforDic(String path) {
		path = path.replace("\\", "/");
		String[] allStr = path.split("/");
		return allStr[allStr.length - 2];
	}

	public static String getFileName(String path) {
		path = path.replace("\\", "/");
		String[] allStr = path.split("/");
		return allStr[allStr.length - 1];
	}

	private static void getParamMap(Map<String, String> paramMap, UploadResult result, String absPath, String remotePath) {
		File file = new File(absPath);
		// 判断上传类型
		List<String> types = Arrays.asList(IMAGE_ARRAY);
		String fi = file.getName();
		String name = fi.substring(fi.indexOf(".") == -1 ? 0 : fi.indexOf(".") + 1, fi.length());
		// 存储上传文件本地名
		result.setFileNameBefore(fi);
		// 存储上传文件本地全路径
		result.setLoaclRootPath(file.getAbsolutePath().replace(fi, ""));

		if (types.contains(name))
			paramMap.put("filetype", "IMG");
		else
			paramMap.put("filetype", "FILE");

		paramMap.put("filename", fi);
		paramMap.put("localdir", file.getPath());

		paramMap.put("remotedir", remotePath); // 目标目录
	}
	
	
	

	public static List<UploadResult> BatchUpload(FileInfo info) throws Exception {
		List<UploadResult> results = new ArrayList<UploadResult>();
		List<String> allFiles = new ArrayList<String>();

		getAllFiles(allFiles, info);

		Map<String, String> paramMap = FTPUtils.getParamMap();
		for (String absPath : allFiles) {
			UploadResult result = new UploadResult();
			getParamMap(paramMap, result, absPath, info.getRemotePath());
			// 上传ftp
			FTPUtils.uploadFile(paramMap, result);

			results.add(result);
		}

		return results;
	}

	public static List<UploadResult> BatchUpload(List<String> allFileAbs, String remotePath) {
		List<UploadResult> results = new ArrayList<UploadResult>();
		Map<String, String> paramMap = FTPUtils.getParamMap();
		for (String absPath : allFileAbs) {
			UploadResult result = new UploadResult();
			getParamMap(paramMap, result, absPath, remotePath);
			// 上传ftp
			try {
				FTPUtils.uploadFile(paramMap, result);
			} catch (Exception e) {
				LOGGER.error("FTP上传异常：" + e);
				result.setIsSuccess(Constant.UPLOAD_FAILURE);
				result.setErrorDesc(Constant.FTP_ERROR);
			}

			results.add(result);
		}

		return results;
	}

	public static UploadResult upload(FileInfo info) throws Exception {
		UploadResult result = new UploadResult();
		// 校验文件是否存在
		File file = new File(info.getRootPath());
		if (!file.exists()) {
			result.setIsSuccess(Constant.UPLOAD_FAILURE);
			result.setErrorDesc(Constant.UPLOAD_IMG_EXIST);
			result.setLoaclRootPath(info.getRootPath());
			return result;
		}

		Map<String, String> paramMap = FTPUtils.getParamMap();

		getParamMap(paramMap, result, info.getRootPath(), info.getRemotePath());
		// 上传ftp
		FTPUtils.uploadFile(paramMap, result);

		return result;
	}

	public static void getAllFiles(List<String> results, FileInfo info) {
		File dir = new File(info.getRootPath());
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			String fi = fs[i].getName();
			String name = fi.substring(fi.indexOf(".") == -1 ? 0 : fi.indexOf(".") + 1, fi.length());

			if (info.getTypes().indexOf(name.toLowerCase()) > -1) {
				results.add(fs[i].getAbsolutePath());
			}

			if (fs[i].isDirectory()) {
				try {
					info.setRootPath(fs[i].getAbsolutePath());
					getAllFiles(results, info);
				} catch (Exception e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		// FileInfo info = new FileInfo("E:\\image\\",
		// "/userfiles/images/sku/products", "jpgpng");
		//
		// try {
		// List<UploadResult> results = BatchUpload(info);
		// for (UploadResult result : results) {
		// System.out.println(result.getFileNameBefore());
		// System.out.println(result.getFileNameAfter());
		// System.out.println(result.getLoaclRootPath());
		// System.out.println(result.getFtpRootPath());
		// System.out.println(result.getIsSuccess());
		// }

		// UploadResult result = upload(info);
		// System.out.println(result.getFileNameBefore());
		// System.out.println(result.getFileNameAfter());
		// System.out.println(result.getLoaclRootPath());
		// System.out.println(result.getFtpRootPath());
		// System.out.println(result.getIsSuccess());
		// } catch (Exception e) {
		// }
		// System.out.println(getDicAbsPath(new String[] { "sku", "product" }));
	}
}
