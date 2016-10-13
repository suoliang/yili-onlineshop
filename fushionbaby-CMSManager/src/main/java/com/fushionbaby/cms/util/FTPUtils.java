package com.fushionbaby.cms.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.fushionbaby.cms.util.bean.FtpInfo;
import com.fushionbaby.cms.util.bean.UploadResult;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.util.RandomNumUtil;
import com.google.common.io.Files;

/**
 * User: suntao Date: 15-6-25 Time: 下午4:56
 */
public class FTPUtils {

	private static Logger log = Logger.getLogger(FTPUtils.class);

	/**
	 * @Description: 连接ftp服务器
	 * @param ip
	 *            商户FTP地址
	 * @param port
	 *            商户FTP端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return FtpClient 返回类型
	 */
	private static FTPClient connectServer(String ip, String port, String username, String password, String fileType) {
		FTPClient ftp = null;
		try {
			ftp = new FTPClient();
			ftp.setRemoteHost(ip);
			ftp.setRemotePort(Integer.valueOf(port));
			ftp.connect();
			/** 登陆 */
			ftp.login(username, password);
			// 1:Standard : PORT方式，主动方式 2:Passive(也就是PASV，被动方式
			FTPTransferType type = fileType.equals("IMG") ? FTPTransferType.BINARY : FTPTransferType.ASCII;
			ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.setType(type);
		} catch (Exception e) {
			log.error("ftp服务器登录异常", e);
		}
		return ftp;
	}

	/**
	 * @Description: ftp文件上传
	 * @param ftpInfo
	 *            FTP信息
	 * @param localFileName
	 *            本地要上传的文件名
	 * @param remoteFileName
	 *            上传到FTP服务器上的文件名，不包括目录
	 * @return boolean
	 */
	public static boolean upLoadFile(FtpInfo ftpInfo, String localFileName, String remoteFileName, String fileType) {
		FTPClient ftp = null;
		try {
			ftp = connectServer(ftpInfo.getHost(), ftpInfo.getPort(), ftpInfo.getUserName(), ftpInfo.getPassword(),
					fileType);
			if (ftp != null) {
				if (StringUtils.isNotEmpty(ftpInfo.getRemoteDir())) {
					if (!ftp.existsDirectory(ftpInfo.getRemoteDir())) {// 如果目录不存在，则创建
						ftp.mkdir(ftpInfo.getRemoteDir());
					}

					ftp.chdir(ftpInfo.getRemoteDir());
				}
				ftp.put(ftpInfo.getLocalDir(), remoteFileName);
				log.debug("FTP文件上传成功!");
				return true;
			}
		} catch (Exception e) {
			log.error("FTP文件上传异常", e);
		} finally {
			close(ftp);
		}
		return false;
	}

	/**
	 * @Description: ftp文件下载
	 * @param ftpInfo
	 *            商户/机构ftp信息
	 * @param remoteFileName
	 *            服务器文件名，不包括目录
	 * @return boolean
	 */
	public static boolean downLoadFile(FtpInfo ftpInfo, String remoteFileName, String fileType) {
		try {
			// ftp信息的本地相对目录没有精确到文件格式,取文件的后缀名为文件夹名
			FTPClient ftp = connectServer(ftpInfo.getHost(), ftpInfo.getPort(), ftpInfo.getUserName(),
					ftpInfo.getPassword(), fileType);
			if (ftp == null) {
				log.info("ftp is null");
				return false;
			}
			if (StringUtils.isNotEmpty(ftpInfo.getRemoteDir())) {
				ftp.chdir(ftpInfo.getRemoteDir());
			}
			// 下载文件之前判断文件在本地是否已存在，存在则重新命名
			if (Files.isFile().apply(new File(ftpInfo.getLocalDir() + remoteFileName))) {
				log.info("本地文件已存在");
				return false;
			}
			// //FTP下载过程中临时文件名
			String tmpFile = Files.getNameWithoutExtension(remoteFileName) + ".downing";
			String tmpFilePath = ftpInfo.getLocalDir() + tmpFile;
			// //从FTP下载对账文件到本地路径目录
			ftp.get(tmpFilePath, remoteFileName);
			// //下载成功后，修改为正常文件名
			FileUtils.copyFile(new File(tmpFilePath), new File(ftpInfo.getLocalDir() + remoteFileName));
			FileUtils.deleteQuietly(new File(tmpFilePath));
			return true;
		} catch (Exception e) {
			log.error("文件下载异常", e);
		}
		return false;
	}

	public static boolean deleteFile(Map<String, String> param) {
		FtpInfo ftpInfo = getFileInfo(param);

		try {
			// ftp信息的本地相对目录没有精确到文件格式,取文件的后缀名为文件夹名
			FTPClient ftp = connectServer(ftpInfo.getHost(), ftpInfo.getPort(), ftpInfo.getUserName(),
					ftpInfo.getPassword(), param.get("filetype"));
			if (ftp == null) {
				return false;
			}
			if (StringUtils.isNotEmpty(ftpInfo.getRemoteDir())) {
				ftp.chdir(param.get("remotedir"));
			}
			// 下载文件之前判断当前文件是否存在于ftp目录
			if (ftp.existsFile(param.get("filename"))) {
				ftp.delete(param.get("filename"));
			}

			return true;
		} catch (Exception e) {
			log.error("删除文件异常", e);
		}
		return false;
	}

	/**
	 * @Description: ftp文件下载
	 * @param ftpInfo
	 *            商户/机构ftp信息
	 * @param remoteFileName
	 *            服务器文件名，不包括目录
	 * @param localFileName
	 *            下载之后本地文件名，不包括目录
	 * @return boolean
	 */
	public static boolean downLoadFile(FtpInfo ftpInfo, String remoteFileName, String localFileName, String fileType) {
		try {
			// ftp信息的本地相对目录没有精确到文件格式,取文件的后缀名为文件夹名
			FTPClient ftp = connectServer(ftpInfo.getHost(), ftpInfo.getPort(), ftpInfo.getUserName(),
					ftpInfo.getPassword(), fileType);
			if (ftp == null) {
				return false;
			}
			if (StringUtils.isNotEmpty(ftpInfo.getRemoteDir())) {
				ftp.chdir(ftpInfo.getRemoteDir());
			}
			// 下载文件之前判断文件在本地是否已存在，存在则重新命名
			if (Files.isFile().apply(new File(ftpInfo.getLocalDir() + remoteFileName))) {
				return false;
			}
			// //FTP下载过程中临时文件名
			String tmpFile = Files.getNameWithoutExtension(remoteFileName) + ".downing";
			String tmpFilePath = ftpInfo.getLocalDir() + tmpFile;
			// //从FTP下载对账文件到本地路径目录
			ftp.get(tmpFilePath, remoteFileName);
			// //下载成功后，修改为正常文件名
			FileUtils.copyFile(new File(tmpFilePath), new File(ftpInfo.getLocalDir() + localFileName));
			FileUtils.deleteQuietly(new File(tmpFilePath));
			return true;
		} catch (Exception e) {
			log.error("文件下载异常", e);
		}
		return false;
	}

	/**
	 * 关闭
	 * 
	 * @param ftp
	 */
	public static void close(FTPClient ftp) {
		if (ftp != null && ftp.connected()) {
			try {
				ftp.quit();
			} catch (IOException e) {
				log.error("IO异常", e);
			} catch (FTPException e) {
				log.error("ftp异常", e);
			}
		}
	}

	/**
	 * 封装ftp上传文件基本参数(在线申请信用卡)
	 * 
	 * @return
	 */
	public static Map<String, String> getParamMap() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("host", Constant.UPLOAD_IMG_HOST); // IP
		paramMap.put("username", Constant.UPLOAD_IMG_USERNAME); // 用户名
		paramMap.put("password", Constant.UPLOAD_IMG_PASSWORD); // 密码
		paramMap.put("port", Constant.UPLOAD_IMG_PORT); // 端口
		return paramMap;
	}

	private static FtpInfo getFileInfo(Map<String, String> param) {
		FtpInfo ftpInfo = new FtpInfo();
		ftpInfo.setHost(param.get("host"));
		ftpInfo.setUserName(param.get("username"));
		ftpInfo.setPassword(param.get("password"));
		ftpInfo.setPort(param.get("port"));
		ftpInfo.setRemoteDir(param.get("remotedir"));
		ftpInfo.setLocalDir(param.get("localdir"));

		return ftpInfo;
	}

	/**
	 * ftp文件上传
	 * 
	 */
	public static void uploadFile(Map<String, String> param, UploadResult result) throws Exception {
		String[] nameArray = param.get("filename").replace(" ", "").split("\\.");
		String remoteName = DateUtil.getCurrentDateStr(DateUtil.FULL_PATTERN)+RandomNumUtil.getNumber(3)  //时间戳加随机数加文件名
				+ com.fushionbaby.cms.util.StringUtils.upFileNameFilter(nameArray[0]) + "." + nameArray[1]; // 名字加时间戳
		// 如果有中文则直接替换
		remoteName = RegUtils.repChinese(remoteName);
		// 存储上传之后的文件名
		result.setFileNameAfter(remoteName);
		// 获取日期字符串
		String yearMonth = DateUtil.getCurrentDateStr(DateUtil.FULL_YEAR_MONTH);
		String removePath = param.get("remotedir") + "/" + yearMonth;
		param.put("remotedir", removePath);
		// 存储上传至ftp的全路径
		result.setFtpRootPath(param.get("remotedir"));
		// 默认上传结果失败
		result.setIsSuccess(Constant.UPLOAD_FAILURE);
		result.setErrorDesc(Constant.UPLOAD_IMG_EXIST);
		String type = param.get("filetype");
		File file = new File(param.get("localdir"));
		if (file.exists()) {
			if (!upLoadFile(getFileInfo(param), param.get("filename"), remoteName, type)) {
				log.error("ftp 异常");
				result.setErrorDesc(Constant.FTP_ERROR);
			} else {
				result.setIsSuccess(Constant.UPLOAD_SUCCESS); // 上传成功
			}
		}
	}

	// public static void main(String[] args) {
	// Map<String, String> paramMap = getParamMap();
	//
	// try {
	// FTPUtils.uploadFile(paramMap);
	// } catch (Exception e) {
	// log.error("调用registerQECode接口上传文件时程序异常：" + e.getMessage());
	// }
	// }

}
