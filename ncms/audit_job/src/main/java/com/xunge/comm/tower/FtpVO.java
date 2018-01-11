package com.xunge.comm.tower;

public class FtpVO {

	private String ipAddr;// ip地址

	private int port;// 端口号

	private String user;// 用户名

	private String pwd;// 密码

	public FtpVO(String ipAddr, int port, String user, String pwd) {
		this.ipAddr = ipAddr;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}