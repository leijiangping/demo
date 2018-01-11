package com.xunge.comm.tower;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @descript ftp访问封装类
 * @author wangz
 * @date 2017-09-26 11:15:49
 */
public class FtpUtil {

	public FTPClient client;
	public ArrayList<String> fileList;

	/**
	 * 重载构造函数
	 * 
	 * @param isPrintCommmand
	 *            是否打印与FTPServer的交互命令
	 */
	public FtpUtil(boolean isPrintCommmand) {
		client = new FTPClient();
		fileList = new ArrayList<String>();
		if (isPrintCommmand) {
			client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		}
	}

	/**
	 * 登陆FTP服务器
	 * 
	 * @param host
	 *            FTPServer IP地址
	 * @param port
	 *            FTPServer 端口
	 * @param username
	 *            FTPServer 登陆用户名
	 * @param password
	 *            FTPServer 登陆密码
	 * @return 是否登录成功
	 * @throws IOException
	 */
	public boolean login(FtpVO ftp)	throws IOException {
		this.client.connect(ftp.getIpAddr(),ftp.getPort());
		if (FTPReply.isPositiveCompletion(this.client.getReplyCode())) {
			if (this.client.login(ftp.getUser(), ftp.getPwd())) {
				//每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据
				this.client.enterLocalPassiveMode();// FTP本地被动模式
				//允许禁用/启用远程主机链接验证
				this.client.setRemoteVerificationEnabled(false);
				this.client.setControlEncoding("GBK");
				return true;
			}
		}
		disConnection();
		return false;
	}

	/**
	 * 关闭数据链接
	 * 
	 * @throws IOException
	 */
	public void disConnection() throws IOException {
		if (this.client.isConnected()) {
			this.client.disconnect();
		}
	}

	/**
	 * 递归遍历出目录下面所有文件
	 * 
	 * @param pathName
	 *            需要遍历的目录，必须以"/"开始和结束
	 * @throws IOException
	 */
	public void List(String pathName) throws IOException {
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			String directory = pathName;
			// 更换目录到当前目录
			this.client.changeWorkingDirectory(directory);
			FTPFile[] files = this.client.listFiles();
			for (FTPFile file : files) {
				if (file.isFile()) {
					fileList.add(directory + file.getName());
				} else if (file.isDirectory()) {
					List(directory + file.getName() + "/");
				}
			}
		}
	}

	/**
	 * 递归遍历目录下面指定的文件名
	 * 
	 * @param pathName
	 *            需要遍历的目录，必须以"/"开始和结束
	 * @param ext
	 *            文件的扩展名
	 * @throws IOException
	 */
	public void List(String pathName, String ext) throws IOException {
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			String directory = pathName;
			// 更换目录到当前目录
			this.client.changeWorkingDirectory(directory);
			FTPFile[] files = this.client.listFiles();
			for (FTPFile file : files) {
				if (file.isFile()) {
					if (file.getName().endsWith(ext)) {
						fileList.add(directory + file.getName());
					}
				} else if (file.isDirectory()) {
					List(directory + file.getName() + "/", ext);
				}
			}
		}
	}
	
	/**
     * 从FTP服务器上下载文件
     * @param ftpDirectoryAndFileName ftp服务器文件路径，以/dir形式开始
     * @param localDirectoryAndFileName 保存到本地的目录
     * @return
     */
    public boolean downLoadFile(String ftpDirectoryAndFileName, String localDirectoryAndFileName) {
        if (!this.client.isConnected()) {
            return false;
        }
        try {
            // 将路径中的斜杠统一
            char[] chars = ftpDirectoryAndFileName.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpDirectoryAndFileName = sbStr.toString();
            String filePath = ftpDirectoryAndFileName.substring(0, ftpDirectoryAndFileName.lastIndexOf("/"));
            String fileName = ftpDirectoryAndFileName.substring(ftpDirectoryAndFileName.lastIndexOf("/") + 1);
            this.changeDir(filePath);
            FileOutputStream fos=new FileOutputStream(localDirectoryAndFileName);
            this.client.retrieveFile(new String(fileName.getBytes(), "iso-8859-1"),fos); // download
            fos.close();
            this.client.sendCommand("pwd"); // 重置超时时间
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 层层切换工作目录
     * @param ftpPath 目的目录
     * @return 切换结果
     */
    public boolean changeDir(String ftpPath) {
        if (!this.client.isConnected()) {
            return false;
        }
        try {
            // 将路径中的斜杠统一
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            if (ftpPath.indexOf('/') == -1) {
                // 只有一层目录
            	this.client.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                	this.client.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}