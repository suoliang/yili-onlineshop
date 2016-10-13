package com.aladingshop.store.util.bean;

/**
 * 上传结果
 * 
 * @author sun tao 2015年6月30日
 */
public class UploadResult {
	private String fileNameBefore; // 文件上传之前名称
	private String fileNameAfter; // 文件上传之后名称
	private String isSuccess; // 是否成功 (0: 成功 1:失败)
	private String errorDesc; // 错误描述
	private String loaclRootPath; // 本地上传全路径
	private String ftpRootPath; // 上传至ftp全路径

	public UploadResult() {
	}

	public UploadResult(String isSuccess, String errorDesc,
			String loaclRootPath, String fileNameBefore) {
		this.isSuccess = isSuccess;
		this.errorDesc = errorDesc;
		this.loaclRootPath = loaclRootPath;
		this.fileNameBefore = fileNameBefore;
	}

	public String getFileNameBefore() {
		return fileNameBefore;
	}

	public void setFileNameBefore(String fileNameBefore) {
		this.fileNameBefore = fileNameBefore;
	}

	public String getFileNameAfter() {
		return fileNameAfter;
	}

	public void setFileNameAfter(String fileNameAfter) {
		this.fileNameAfter = fileNameAfter;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getLoaclRootPath() {
		return loaclRootPath;
	}

	public void setLoaclRootPath(String loaclRootPath) {
		this.loaclRootPath = loaclRootPath;
	}

	public String getFtpRootPath() {
		return ftpRootPath;
	}

	public void setFtpRootPath(String ftpRootPath) {
		this.ftpRootPath = ftpRootPath;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
