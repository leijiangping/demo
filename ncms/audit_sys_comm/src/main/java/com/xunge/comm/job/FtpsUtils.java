package com.xunge.comm.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

/**
 * FTPS工具类
 */
public class FtpsUtils {
    private FTPSClient ftpsClient = null;
    private String server;
    private int port;
    private String userName;
    private String userPassword;
    public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
    public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public FtpsUtils(){
		
	}
	public FtpsUtils(String server, int port, String userName, String userPassword) {
        this.server = server;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * 连接服务器
     * @return 连接成功与否 true:成功， false:失败
     */
    public boolean open() {
        if (ftpsClient != null && ftpsClient.isConnected()) {
            return true;
        }
        try {
        	ftpsClient = new FTPSClient();
            // 连接
        	ftpsClient.connect(this.server, this.port);
        	ftpsClient.login(this.userName, this.userPassword);
            setftpsClient(ftpsClient);
            // 检测连接是否成功
            int reply = ftpsClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.close();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
           /* System.out.println("open FTP success:" + this.server + ";port:" + this.port + ";name:" + this.userName
                    + ";pwd:" + this.userPassword);*/
            ftpsClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置上传模式.binally  or ascii
            return true;
        } catch (Exception ex) {
            this.close();
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 切换到父目录
     * @return 切换结果 true：成功， false：失败
     */
    private boolean changeToParentDir() {
        try {
            return ftpsClient.changeToParentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 改变当前目录到指定目录
     * @param dir 目的目录
     * @return 切换结果 true：成功，false：失败
     */
    private boolean cd(String dir) {
        try {
            return ftpsClient.changeWorkingDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取目录下所有的文件名称
     * 
     * @param filePath 指定的目录
     * @return 文件列表,或者null
     */
    private FTPFile[] getFileList(String filePath) {
        try {
            return ftpsClient.listFiles(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 层层切换工作目录
     * @param ftpPath 目的目录
     * @return 切换结果
     */
    public boolean changeDir(String ftpPath) {
        if (!ftpsClient.isConnected()) {
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
                ftpsClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpsClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 循环创建目录，并且创建完目录后，设置工作目录为当前创建的目录下
     * @param ftpPath 需要创建的目录
     * @return
     */
    public boolean mkDir(String ftpPath) {
        if (!ftpsClient.isConnected()) {
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
            System.out.println("ftpPath:" + ftpPath);
            if (ftpPath.indexOf('/') == -1) {
                // 只有一层目录
                ftpsClient.makeDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
                ftpsClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpsClient.makeDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                    ftpsClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 上传文件到FTP服务器
     * @param localDirectoryAndFileName 本地文件目录和文件名
     * @param ftpFileName 上传到服务器的文件名
     * @param ftpDirectory FTP目录如:/path1/pathb2/,如果目录不存在会自动创建目录
     * @return
     */
    public boolean upload(String localDirectoryAndFileName, String ftpFileName, String ftpDirectory) {
        if (!ftpsClient.isConnected()) {
            return false;
        }
        boolean flag = false;
        if (ftpsClient != null) {
            File srcFile = new File(localDirectoryAndFileName);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(srcFile);
                // 创建目录
                this.mkDir(ftpDirectory);
                ftpsClient.setBufferSize(100000);
                ftpsClient.setControlEncoding("UTF-8");
                // 设置文件类型（二进制）
                ftpsClient.setFileType(ftpsClient.BINARY_FILE_TYPE);
                // 上传
                flag = ftpsClient.storeFile(new String(ftpFileName.getBytes(), "iso-8859-1"), fis);
            } catch (Exception e) {
                this.close();
                e.printStackTrace();
                return false;
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println("上传文件成功，本地文件名： " + localDirectoryAndFileName + "，上传到目录：" + ftpDirectory + "/" + ftpFileName);
        return flag;
    }

    /**
     * 从FTP服务器上下载文件
     * @param ftpDirectoryAndFileName ftp服务器文件路径，以/dir形式开始
     * @param localDirectoryAndFileName 保存到本地的目录
     * @return
     */
    public boolean get(String ftpDirectoryAndFileName, String localDirectoryAndFileName) {
        if (!ftpsClient.isConnected()) {
            return false;
        }
        ftpsClient.enterLocalPassiveMode(); // Use passive mode as default
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
            ftpsClient.retrieveFile(new String(fileName.getBytes(), "iso-8859-1"),fos); // download
            fos.close();
           // System.out.println(ftpsClient.getReplyString()); // check result
           // System.out.println("从ftp服务器上下载文件：" + ftpDirectoryAndFileName + "， 保存到：" + localDirectoryAndFileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回FTP目录下的文件列表
     * @param pathName
     * @return
     */
    public String[] getFileNameList(String pathName) {
        try {
            return ftpsClient.listNames(pathName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除FTP上的文件
     * @param ftpDirAndFileName 路径开头不能加/，比如应该是test/filename1
     * @return
     */
    public boolean deleteFile(String ftpDirAndFileName) {
        if (!ftpsClient.isConnected()) {
            return false;
        }
        try {
            return ftpsClient.deleteFile(ftpDirAndFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除FTP目录
     * @param ftpDirectory
     * @return
     */
    public boolean deleteDirectory(String ftpDirectory) {
        if (!ftpsClient.isConnected()) {
            return false;
        }
        try {
            return ftpsClient.removeDirectory(ftpDirectory);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 关闭链接
     */
    public void close() {
        try {
            if (ftpsClient != null && ftpsClient.isConnected()) {
                ftpsClient.disconnect();
            }
           // System.out.println("成功关闭连接，服务器ip:" + this.server + ", 端口:" + this.port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FTPSClient getftpsClient() {
        return ftpsClient;
    }

    public void setftpsClient(FTPSClient ftpsClient) {
        this.ftpsClient = ftpsClient;
    }

    public static void main(String[] args) {
        FtpsUtils f = new FtpsUtils("127.0.0.1", 21, "test", "123456");
        try {
            if(f.open()) {
                String fileName = "测试2.txt";
                //上传
                f.upload("d:/1.txt", fileName, "test1");

                //遍历
                FTPFile[] list = f.getFileList("/test1");
                for(FTPFile file : list) {
                    String name = file.getName();
                   // System.out.println("--" + new String(name.getBytes("iso-8859-1"), "GB2312"));
                }

                //只遍历指定目录下的文件名
                String[] names = f.getFileNameList("/test1");
                for(String name : names) {
                   // System.out.println(new String(name.getBytes("iso-8859-1"), "GB2312"));
                }

                //下载
                boolean b = f.get("/test1/测试2.txt", "d:/text.txt");
               // System.out.println(b);

                //删除
                String ftpDirAndFileName = "test1/测试.txt";
                boolean be = f.deleteFile(new String(ftpDirAndFileName.getBytes(), "iso-8859-1"));
              //  System.out.println(be);

                //删除目录
                boolean delf = f.deleteDirectory("test1");
               // System.out.println(delf);

                f.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}