package com.xunge.controller.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.xunge.comm.job.RestServerUtils;



@Controller
@RequestMapping("/asserts/tpl/basedata/collection")
public class JobController {
	private static final Logger LOGGER = Logger.getLogger(JobController.class);
	private String tomcatPath = System.getProperty("catalina.home").replaceAll("\\\\", "/") + File.separator;
	/**
     * 接收job推送过来的文件
     * @param fileRequest
     */
    @RequestMapping("/postFile")
    public void postFile(HttpServletRequest request, HttpServletResponse response) {
        String result = "error";
        Map<String, Object> map = new HashMap<String, Object>();
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
        	LOGGER.error("----------------has file upload!");
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile multipartFile = multiRequest.getFile(iter.next());
                if (multipartFile != null) {
                    // 取得当前上传文件的文件名称
                    String fileName = multipartFile.getOriginalFilename();
                  //获取文件路径
                    LOGGER.error("----------------file name is!"+fileName);
                    if (fileName.trim() != null && fileName.trim().length() > 0) {
                        CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
                        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
                        File tempFile = fi.getStoreLocation();
                        // 拿到文件，存储
                        result = "success";
                    }
                    try {
                    	String localPath=RestServerUtils.getTomcatRootPath()+File.separator+"resource";
                    	File tomcatResource = new File(localPath);
                    	if (!tomcatResource.exists()){
                    		tomcatResource.mkdir();
                    	}
                    	String[] fileParam = fileName.split("_");
                    	String absPath = "";
                    	if (fileName.contains("1440")){
                    		//类似GZ_MACHROOM_METER_1440_201708220000_201708230000_201708230152_01.zip这种格式的
                    		absPath = localPath + File.separator + fileParam[0] + File.separator + fileParam[1]+"_"+fileParam[2] + File.separator
                    				+ fileParam[4].substring(0, 8)+File.separator;
                    	}else if(fileParam.length == 3){
                    		//类似GZ_OLT_170821011005.zip这种格式
                    		absPath = localPath + File.separator + fileParam[0] + File.separator + fileParam[1] + File.separator
                    				+ fileParam[2].substring(0, 8)+File.separator;
                    	}
                    	File desFile = new File(absPath);
                    	if(!desFile.exists()){
                    		desFile.mkdirs();
                    	}
						multipartFile.transferTo(new File(absPath + fileName));
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        }
        map.put("result", result);
    }
    
    @RequestMapping("/download")
    public HttpServletResponse download(String fileKey,HttpServletRequest request, HttpServletResponse response) {
        try {
        	String fileType = fileKey.contains("CSV") ? "CSV":"LOG";
        	fileKey = fileKey.replace("CSV", "").replace("LOG", "");
        	String[] fileParam = fileKey.split("_");
        	String prvCode = fileParam[0];
        	String prefix = fileKey.contains("1440") ? fileParam[1] + "_" +fileParam[2] : fileParam[1];
        	String timeSmap = fileKey.contains("1440") ? fileParam[4].substring(0, 8) : fileParam[2].substring(0, 8);
        	String filePath = tomcatPath+"resource"+File.separator+prvCode+
        			File.separator+prefix+File.separator+timeSmap+File.separator;
            // path是指欲下载的文件的路径。
            File file = new File(filePath);
            if (file.exists()){
            	File[] listFile = file.listFiles();
            	for(File eachFile: listFile){
            		if(eachFile.getName().toUpperCase().contains(fileType)){
            			if (fileType.equals("LOG")){
            				if (eachFile.getName().contains(fileKey)){
            					file = eachFile;
            				}
            			}else{
            				file = eachFile;
            			}
            		}
            	}
            	// 取得文件名。
            	String filename = file.getName();
            	// 取得文件的后缀名。
            	String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            	// 以流的形式下载文件。
            	InputStream fis = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
            	byte[] buffer = new byte[fis.available()];
            	fis.read(buffer);
            	fis.close();
            	// 清空response
            	response.reset();
            	// 设置response的Header
            	response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            	response.addHeader("Content-Length", "" + file.length());
            	OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            	response.setContentType("application/octet-stream");
            	response.setCharacterEncoding("UTF-8");
            	toClient.write(buffer);
            	toClient.flush();
            	toClient.close();
            }else{
            	//文件不存在！
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
}

