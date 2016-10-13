package com.aladingshop.store.util.bean;

/**
 * User: suntao Date: 15-6-25 Time: 下午13:24
 */
public class FtpInfo {

	private String host; // 主机IP

	private String port; // 主机端口

	private String userName;// 用户名

	private String password;// 密码

	private String localDir;// 本地目录，最后结束要带'/'

	private String remoteDir;// 远程目录，最后结束要带'/'

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocalDir() {
		return localDir;
	}

	public void setLocalDir(String localDir) {
		this.localDir = localDir;
	}

	public String getRemoteDir() {
		return remoteDir;
	}

	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}

}
