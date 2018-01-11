package com.xunge.service.job;

/*
 * ftp服务
 */
public interface IFtpService {
	

	public void setFtpInfo(String server, int port, String userName, String userPassword);
	
	
	public String[] getFileList(String path);
	
	public void downloadFile(String ftpDirectoryAndFileName, String localDirectoryAndFileName);
	
	public void close();
	
	public void setFtpsInfo(String server, int port, String userName, String userPassword);

	public String[] getFtpsFileList(String path); 

	public void downloadFileFormFtps(String ftpDirectoryAndFileName, String localDirectoryAndFileName);
}
