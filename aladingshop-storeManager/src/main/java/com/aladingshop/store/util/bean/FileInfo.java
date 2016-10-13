package com.aladingshop.store.util.bean;

public class FileInfo {
	private String rootPath; // 根目录

	private String remotePath; // 目标目录

	private String types; // 限制上传文件类型列表(例如jpgpngtxt ,每个格式之间无需加分隔符)

	public FileInfo() {
	}

	public FileInfo(String rootPath, String remotePath, String types) {
		this.rootPath = rootPath;
		this.remotePath = remotePath;
		this.types = types;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}
}
