package com.xunge.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author changwq
 * @Description 参数计算类
 */
public class DownZipUtil {
	private static PropertiesLoader loader = new PropertiesLoader("properties/sysConfig.properties");
	public static final String basePath = loader.getProperty("UploadUrls");
	/**
	 * 压缩并导出文件
	 * 
	 * @param zipPath
	 *            压缩文件临时路径 路径最后不要有 /
	 * @param zipName
	 *            压缩为文件名 **.zip
	 * @param createFilesPath
	 *            需要压缩的文件列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static boolean downloadZip(String zipPath, String zipName,
			List<String> createFilesPath, HttpServletRequest request,
			HttpServletResponse response) {
		// String tmpFileName = "report.zip";
		byte[] buffer = new byte[1024];
		// String strZipPath =
		// COM_REPORT_PATH+"/"+user.getOid()+"/"+report.getOid()+"/"+tmpFileName;

		String strZipPath = zipPath + "/" + zipName;
		try {
			File tmpZip = new File(zipPath);
			if (!tmpZip.exists())
				tmpZip.mkdirs();
			File tmpZipFile = new File(strZipPath);
			if (!tmpZipFile.exists())
				tmpZipFile.createNewFile();
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					strZipPath));
			if(createFilesPath.size()>0){
				for (int i = 0; i < createFilesPath.size(); i++) {
					//数据库文件路径
					String menuPath = createFilesPath.get(i);
					//下载文件路径
					String path = basePath + menuPath;
					//去掉文件夹目录结构的/
					String menu = menuPath.substring(1, menuPath.length());
					String[] m = menu.split("/");
					int a = i+1;
					String finmenu = m[0]+"/"+m[1]+"/"+m[2]+"/"+a+","+m[3];
					FileInputStream fis = new FileInputStream(new File(path));
					//创建目录层级
					ZipEntry entry = new ZipEntry(finmenu);//在ZIP文件中的目录结构，最前面不要有斜杠。  
					out.putNextEntry(entry);
					// 设置压缩文件内的字符编码，不然会变成乱码
					//out.setEncoding("UTF-8");
					int len;
					// 读入需要下载的文件的内容，打包到zip文件
					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					out.closeEntry();
					fis.close();
				}
			}
			out.close();
			downloadFile(strZipPath,zipPath, zipName, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 以压缩文件导出
	 * 
	 * @param fileName
	 * @param filePath
	 * @param response
	 */
	public static void downloadFile(String menu,String filePath, String fileName,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		// response.setContentType("application/octet-stream");

		try {
			File file = new File(filePath, fileName);
			// 以流的形式下载文件。
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			File tmpZipFile = new File(menu);
			tmpZipFile.delete();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}