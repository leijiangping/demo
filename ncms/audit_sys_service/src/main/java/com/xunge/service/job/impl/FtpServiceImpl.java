package com.xunge.service.job.impl;

import org.springframework.stereotype.Service;

import com.xunge.comm.job.FtpUtils;
import com.xunge.comm.job.FtpsUtils;
import com.xunge.service.job.IFtpService;

@Service
public class FtpServiceImpl implements IFtpService{

	private FtpUtils ftpUtils;
	
	private FtpsUtils ftpsUtils;
	
	@Override
	public void setFtpInfo(String server, int port, String userName, String userPassword) {
		if(ftpUtils==null){
			ftpUtils=new FtpUtils(server,port,userName,userPassword);
		}
		else{
			ftpUtils.setServer(server);
			ftpUtils.setPort(port);
			ftpUtils.setUserName(userName);
			ftpUtils.setUserPassword(userPassword);
		}
		ftpUtils.open();
	}

	
	@Override
	public String[] getFileList(String path) {
		return ftpUtils.getFileNameList(path);
	}
	
	@Override
	public void downloadFile(String ftpDirectoryAndFileName, String localDirectoryAndFileName) {
		
		ftpUtils.get(ftpDirectoryAndFileName, localDirectoryAndFileName);
	}


	@Override
	public void close() {
		// TODO Auto-generated method stub
		ftpUtils.close();
	}
	
	
	@Override
	public void setFtpsInfo(String server, int port, String userName, String userPassword) {
		if(ftpsUtils==null){
			ftpsUtils=new FtpsUtils(server,port,userName,userPassword);
		}
		else{
			ftpsUtils.setServer(server);
			ftpsUtils.setPort(port);
			ftpsUtils.setUserName(userName);
			ftpsUtils.setUserPassword(userPassword);
		}
		ftpsUtils.open();
	}

	
	@Override
	public String[] getFtpsFileList(String path) {
		return ftpsUtils.getFileNameList(path);
	}
	
	@Override
	public void downloadFileFormFtps(String ftpDirectoryAndFileName, String localDirectoryAndFileName) {
		
		ftpsUtils.get(ftpDirectoryAndFileName, localDirectoryAndFileName);
	}

}
