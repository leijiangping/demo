package com.xunge.service.job.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.aspectj.util.FileUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.sys.util.AccessTokenGenerator;

import com.cmcc.iot.ncms.twrwscli.TwrWsCli;
import com.cmcc.iot.ncms.twrwscli.bean.ReqDownload;
import com.cmcc.iot.ncms.twrwscli.bean.ResDownload;
import com.cmcc.iot.ncms.twrwscli.utils.XStreamUtil;
import com.xunge.comm.BatchControlComm;
import com.xunge.comm.job.FtpUtils;
import com.xunge.comm.job.RestServerUtils;
import com.xunge.comm.tower.CommonData;
import com.xunge.model.basedata.colletion.FtpFileConfigVO;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.model.job.TaskHistoryInfoVO;
import com.xunge.model.system.SysUserVO;
import com.xunge.model.system.region.DictionaryVO;
import com.xunge.service.basedata.IBaseDataService;
import com.xunge.service.basedata.ring.IRingService;
import com.xunge.service.job.ICompareService;
import com.xunge.service.job.IContractJobService;
import com.xunge.service.job.IFileService;
import com.xunge.service.job.IFtpService;
import com.xunge.service.job.IIronTowerService;
import com.xunge.service.job.IJobSyncService;
import com.xunge.service.job.IProvinceService;
import com.xunge.service.job.IResourceService;
import com.xunge.service.job.ITaskHistoryInfoService;
import com.xunge.service.job.ITaskInfoService;
import com.xunge.service.system.user.ISysUserService;
import com.xunge.util.DateConverter;
import com.xunge.util.DateUtil;
import com.xunge.util.PropertiesLoader;
import com.xunge.util.StrUtil;
import com.xunge.util.TableType;

@Service
public class JobSyncServiceImpl implements IJobSyncService{

	private static final Logger LOGGER = Logger.getLogger(JobSyncServiceImpl.class);
	
	@Resource
	private ITaskInfoService taskInfoService;
	
	@Resource
	private IFtpService ftpService;
	
	@Resource
	private IFileService fileService;
	@Resource
	private ICompareService compareService;
	
	@Resource
	private IProvinceService provinceService;
	
	@Resource
	private IResourceService resourceService;
	
	@Resource
	private ITaskHistoryInfoService taskHistoryInfoService;

	@Resource
	private IBaseDataService contractService;
	
	@Resource
	private IRingService ringService;
	
	@Resource
	private IContractJobService contractJobService;
	
	@Resource
	private IIronTowerService ironTowerService;
	
	@Resource
	private ISysUserService sysUserService;

	@Override
	@Async
	public void syncContractData(TaskInfoVO taskInfo,String type) {
		StringBuffer loginfoBF = new StringBuffer(); 
		long startTime=System.currentTimeMillis();   //获取开始时间
		
		IBaseDataService ibds = null;
		if(type.equalsIgnoreCase("contract")){
			 ibds = contractService;
		}else if(type.equalsIgnoreCase("ring")){
			 ibds = ringService;
		}else{
			ibds = resourceService;
		}
		
		
		//文件导入错误信息
		String errorInfo="";
		
		String prvId=taskInfo.getPrvId();
		LOGGER.info("-taskInfo---prvId: "+prvId);
		
		SysProvinceVO sysProvinceVO=provinceService.getProvinceByPk(prvId);
		String prvCode=sysProvinceVO.getPrvCode();
		LOGGER.info("-taskInfo---prvCode: "+prvCode);
		
		try{
			String taskId= taskInfo.getTaskId();
			
			// 获取远程ftp路径及权限配置
			String server=taskInfo.getFtpUrl();
			int port=taskInfo.getFtpPort();
			String userName=taskInfo.getFtpUser();
			String userPassword=taskInfo.getFtpPassword();
			String filePath=taskInfo.getFtpFilepath();
			LOGGER.info("----taskInfo.filePath: "+filePath);
			
			ftpService.setFtpInfo(server, port, userName, userPassword);
			FtpUtils ftpUtil = new FtpUtils(server,port,userName,userPassword);
			// 拼接本地存放路径
			if(StringUtils.isNoneBlank(filePath)&&!filePath.startsWith("/")){
				filePath="/"+filePath;
			}
			Date newDate=DateConverter.getDate(0);
			char delimiter = BatchControlComm.contDelimiter;
			String delimiterstr = BatchControlComm.contDelimiterStr;
			if(type.equalsIgnoreCase("ring")){
				newDate=DateConverter.getDate(-1);
				delimiter = BatchControlComm.ringDelimiter;
				delimiterstr = BatchControlComm.ringDelimiterStr;
			}
			long nowDateTimeStr=(new Date()).getTime();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd"); 
			String newDateStr=dateFm.format(newDate);
			String localPath=RestServerUtils.getTomcatRootPath()+File.separator+"resource";
			
			File file=new File(localPath);
			if(!file.isDirectory()){
				file.mkdirs();
			}
			LOGGER.info("---ftp文件取到本地，存放路径---localPath: "+localPath);
			
			//删除10天前的文件， 同时获取比对用的前天的文件名称
			HashMap<String,String> fileNameHash=new HashMap<String,String>();
			String[] localFileNames=file.list();
			for(String fileName:localFileNames){
				
				boolean isDel=delfiles(type, localPath, fileName);
				if(isDel){
					continue;
				}
//				int index=fileName.indexOf(oldDateStr);
				if(type.equalsIgnoreCase("ring")){
					if(fileName.toUpperCase().lastIndexOf(".ZIP")<0 && fileName.contains("_1440_")){
						int index=fileName.indexOf("_1440_");
						String fileFlag = fileName.substring(0, index);
						if (fileNameHash.containsKey(fileFlag)){
							String oldIncomeDate = fileNameHash.get(fileFlag).split("_1440_")[1].split("_")[0];
							String newIncomeDate = fileName.split("_1440_")[1].split("_")[0];
							int result = oldIncomeDate.compareTo(newIncomeDate);
							if (result < 0 && !fileName.contains(newDateStr)){
								fileNameHash.put(fileFlag, fileName);
							}
						}else{
							fileNameHash.put(fileFlag, fileName);
						}
					}
				}else{
					if(fileName.toUpperCase().lastIndexOf(".ZIP")<0 && !fileName.contains("log")){
						String fileFlag = fileName.split("_")[0] +"_"+ fileName.split("_")[1];
						if (fileNameHash.containsKey(fileFlag)){
							String oldIncomeDate = fileNameHash.get(fileFlag).split("_")[2].replace(".csv", "").replace(".CSV", "");
							String newIncomeDate = fileName.split("_")[2].replace(".csv", "").replace(".CSV", "");
							int result = oldIncomeDate.compareTo(newIncomeDate);
							if (result < 0 && !fileName.contains(newDateStr)){
								fileNameHash.put(fileFlag, fileName);
							}
						}else{
							fileNameHash.put(fileFlag, fileName);
						}
					}
				}
				
			}
			
			// 根据taskInfo获取需要同步的FTP文件列表
			List<FtpFileConfigVO> ftpFileConfigVOList=taskInfoService.getFtpFileConfigByTaskId(taskId);
			if(ftpFileConfigVOList.size()<=0){
				LOGGER.error("FTP采集文件配置列表为空:"+filePath);
				//记录信息
				String dataTimeStr = type.equalsIgnoreCase("ring")?getRingDataTimeStr():getCurrentDataTimeStr();
				fileNoContentOrFtpNoFile(prvId, prvCode+"_"+dataTimeStr, taskId,
						nowDateTimeStr,"FTP采集文件配置列表为空:"+filePath);
				return;
			}
			boolean hasValiedFile = false;// 是否有需要导入的文件
			if(type.equalsIgnoreCase("res")){
				ftpFileConfigVOList=sortResFtpFileConfig(ftpFileConfigVOList);//鎺掑簭
			}
			for(FtpFileConfigVO ftpFileConfigVO :ftpFileConfigVOList){
				String perfix=ftpFileConfigVO.getFilePerfix();
				String perfixForLog = ftpFileConfigVO.getFilePerfix();
				String[] fileNames=ftpService.getFileList(filePath);//FTP目录下的文件列表
				//链接异常后重新获取5次
				if(fileNames==null){
					for(int i=0;i<5;i++){
						fileNames=ftpService.getFileList(filePath);
						if(fileNames!=null){
							break;
						}
					}
				}
				if(fileNames==null){
					LOGGER.error("FTP连接异常");
					//记录信息
					String dataTimeStr = type.equalsIgnoreCase("ring")?getRingDataTimeStr():getCurrentDataTimeStr();
					fileNoContentOrFtpNoFile(prvId, prvCode+"_"+perfix+"_"+dataTimeStr, taskId,
							nowDateTimeStr,"FTP连接异常");
					return;
				}else if(fileNames.length<=0){
					LOGGER.error("获取FTP目录下无文件");
					//记录信息
					String dataTimeStr = type.equalsIgnoreCase("ring")?getRingDataTimeStr():getCurrentDataTimeStr();
					fileNoContentOrFtpNoFile(prvId, prvCode+"_"+perfix+"_"+dataTimeStr, taskId,
							nowDateTimeStr,"获取FTP目录下无文件");
					return;
				}
				
				
				String[] result=ibds.getDBparms(perfix);
				String pkName=result[0];
				String typeName=result[1];
				String tableName=result[2];
				String fixFilename = type.equalsIgnoreCase("ring")?prvCode+"_"+perfix+"_1440_"+newDateStr:prvCode+"_"+perfix+"_"+newDateStr;
				Arrays.sort(fileNames);
				reverseArray(fileNames);
				for(String fileName:fileNames){
					
					// SH_Contract_20170718122000.zip
					if(fileName.toUpperCase().indexOf(fixFilename.toUpperCase())>=0 && !fileName.endsWith(".log")){
						long endTime_ready=System.currentTimeMillis();
						loginfoBF.append("---------拿到文件，准备下载并解析：准备阶段耗时"+(endTime_ready-startTime)/60000+"min "+(endTime_ready-startTime)%60000+"ms\r\n");
						LOGGER.info(loginfoBF.toString());
						hasValiedFile = true;
						String localFileName=fileNameHash.get(prvCode+"_"+perfix);
						String realFileName = fileName.substring(fileName.lastIndexOf("/") + 1);
						String newDirectoryAndFileName = localPath+ File.separator+realFileName;
						String localDirectoryAndFileName=localPath+File.separator+localFileName;
						
						// zip文件解压
						ftpService.downloadFile(fileName, newDirectoryAndFileName);
						LOGGER.info("ftp下载："+newDirectoryAndFileName);

						if(fileName.toUpperCase().indexOf(".ZIP")>=0){
							String csvName=fileService.unzipFile(newDirectoryAndFileName,localPath,realFileName);
							if(StringUtils.isBlank(csvName)){
								continue;
							}
							// tomcat/resource/新文件名.csv
							newDirectoryAndFileName=localPath+File.separator+csvName;
							LOGGER.info("ftp下载后新文件名："+fileName);
						}
						
						File newFile=new File(newDirectoryAndFileName);
						File oldFile=new File(localDirectoryAndFileName);
						
						if(newFile.exists()){
							FileInputStream newFis = new FileInputStream(newFile);
							List<String> columns=new ArrayList<String>();
							Map<String,String> newContents=fileService.parseFile(newFis,pkName,columns,delimiter);
							LOGGER.info("--------------------------新内容行数："+newContents.keySet().size());
							Map<String,String> oldContents=null;
							if(oldFile.exists()){
								FileInputStream oldFis = new FileInputStream(oldFile);
								oldContents=fileService.parseFile(oldFis,pkName,new ArrayList<String>(),delimiter);
							}
							long endTime_parseFile=System.currentTimeMillis();
							loginfoBF.append("---------下载并解析为Map集合完成，耗时"+(endTime_parseFile-endTime_ready)/60000+"min "+(endTime_parseFile-endTime_ready)%60000+"ms\r\n");
							LOGGER.info(loginfoBF.toString());
							if(newContents!=null && newContents.size()>0){
								
								Map<String, String> analysisInfoMap = new HashMap<>();
								if(oldContents!=null&&oldContents.size()>0){
									long start_compare=System.currentTimeMillis();
									compareService.startCompare(newContents, oldContents);
									Map<String, String> insertRes=compareService.getInsertResults();
									Map<String, String> delRes=compareService.getDelResults();
									long end_compare=System.currentTimeMillis();
									loginfoBF.append("---------昨日与上次文件对比完成，新增、更新："+insertRes.size()+"条，删除："+delRes.size()+"条，"
											+ "耗时"+(end_compare-start_compare)/60000+"min "+(end_compare-start_compare)%60000+"ms\r\n");
									LOGGER.info(loginfoBF.toString());
									
									//文件分析
									analysisInfoMap = fileAnalysis(newFile, insertRes, null, columns, perfix, errorInfo,delimiter,sysProvinceVO);
									loginfoBF.append(analysisInfoMap.get("comment"));
									if(!delRes.isEmpty()){
										ibds.deleteFromDB(pkName,typeName,columns,delRes,sysProvinceVO,BatchControlComm.contDelimiterStr,tableName);
									}
									if(!insertRes.isEmpty()){
										StringBuffer logInfo = ibds.insertIntoDB(typeName,columns,insertRes,sysProvinceVO,taskInfo,delimiterstr,tableName);
										loginfoBF.append(logInfo);
									}
								}
								else{
									//文件分析
									analysisInfoMap = fileAnalysis(newFile, newContents, null, columns, perfix, errorInfo,delimiter,sysProvinceVO);
									loginfoBF.append(analysisInfoMap.get("comment"));
									StringBuffer logInfo = ibds.insertIntoDB(typeName,columns,newContents,sysProvinceVO,taskInfo,delimiterstr,tableName);
									loginfoBF.append(logInfo);
								}
								String dataTimeStr = type.equalsIgnoreCase("ring")?getRingDataTimeStr():getCurrentDataTimeStr();
								String forlogStr = type.equalsIgnoreCase("ring")?prvCode+"_"+perfix+"_1440_"+dataTimeStr:prvCode+"_"+perfixForLog+"_"+dataTimeStr;
								//文件导入错误日志信息入库
								persistenceErrorInfo(taskId, prvId, forlogStr,loginfoBF);
								//回传日志文件到FTP服务器
								uploadErrorLogToServer(ftpUtil,filePath,localPath,forlogStr,analysisInfoMap.get("errorInfo"),newFile);
							}
							else{
								LOGGER.error(fileName+":文件无内容或编码格式不正确");
								String dataTimeStr = type.equalsIgnoreCase("ring")?getRingDataTimeStr():getCurrentDataTimeStr();
								fileNoContentOrFtpNoFile(prvId, prvCode+"_"+perfix+"_"+dataTimeStr, taskId,
										nowDateTimeStr,fileName+":文件无内容或编码格式不正确");
							}
						}
						if(!type.equalsIgnoreCase("ring")){
							break;
						}
					}	
				}
				if (!hasValiedFile){
					//如果未找到要导入的文件
					String dataTimeStr = type.equalsIgnoreCase("ring")?getRingDataTimeStr():getCurrentDataTimeStr();
					fileNoContentOrFtpNoFile(prvId, prvCode+"_"+perfix+"_"+dataTimeStr, taskId,
							nowDateTimeStr,"未找到需要导入的文件!前缀校验："+fixFilename);
				}
			}
			LOGGER.info("资源同步完成!");
		}
		catch(Exception e ){
			LOGGER.error("资源同步失败!",e);
			StringBuffer sbErrorInfo = new StringBuffer();
			sbErrorInfo.append(e.getMessage());
			persistenceErrorInfo(taskInfo.getTaskId(), taskInfo.getPrvId(), prvCode+"_"+getCurrentDataTimeStr(),sbErrorInfo);
		}
		finally{
			ftpService.close();
		}
	}

	private String getCurrentDataTimeStr() {
		long l = System.currentTimeMillis();
		//new日期对象
		Date date = new Date(l);
		//转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String dataTimeStr = dateFormat.format(date);
		return dataTimeStr;
	}
	
	private String getRingDataTimeStr() {
		Date date = DateConverter.getDate(-1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String dataTimeStr = dateFormat.format(date);
		return dataTimeStr;
	}

	private void persistenceErrorInfo(String taskId, String prvId, String sss,
			StringBuffer infoBF) {
		TaskHistoryInfoVO taskHistory = new TaskHistoryInfoVO();
		String runninginfo = infoBF.toString();
		taskHistory.setPrvId(prvId);
		taskHistory.setStartDatetime(new Date());
		taskHistory.setTaskInfoId(taskId);
		taskHistory.setComment(runninginfo);
//		taskHistory.setErrorInfo(errorInfo.getBytes());
		taskHistory.setTaskHistoryId(sss);
		//日志信息入库
		taskHistoryInfoService.insert(taskHistory);
	}

	private int uploadErrorLogToServer(FtpUtils ftpUtil,String ftpDirectory,String serverPath,String fileName,String logInfo,File csvFile){
		File localPath = new File(serverPath);
		LOGGER.info("-----------------uploadErrorLogToServer");
		if (localPath.exists() && localPath.isDirectory()){
			String localLogPath = serverPath + File.separator + "log";
			File localFile = new File(localLogPath);
			String LogFileName = localLogPath + File.separator + fileName + ".Cmcc.NCMS.handled.log";
				try {
					String ftpFileName = fileName + ".Cmcc.NCMS.handled.log";
					if (localFile.exists()){
						//如果log文件夹存在，清理之前残留的文件
						FileUtil.deleteContents(localFile);
						File logFile = new File(LogFileName);
						logFile.createNewFile();
						FileUtil.writeAsString(logFile, logInfo);
						if (ftpUtil.open()){
							ftpUtil.upload(LogFileName, ftpFileName, ftpDirectory);
							ftpUtil.close();
						}
					//如果文件夹存在则直接进行存储
					} else{
						//文件夹不存在进行创建
						boolean createLogFolder = localFile.mkdir();
						if (createLogFolder){
							FileUtil.writeAsString(new File(LogFileName), logInfo);
							if (ftpUtil.open()){
								ftpUtil.upload(LogFileName, ftpFileName, ftpDirectory);
								ftpUtil.close();
							}
						}
					}
					//将日志从job服务器上传到web服务器
					LOGGER.info("------------------------------------upload file to web");
					PropertiesLoader prop = new PropertiesLoader("\\properties\\sysConfig.properties");
					LOGGER.info("------------------------------------upload file to web：prop-"+(prop!=null));
					String url = prop.getProperty("NCMS_WebUrl");
					LOGGER.info("------------------------------------web url is :" + url);
					String filePath = localLogPath + File.separator;
					LOGGER.info("----------------------------------localFilePath is :" + filePath);
					uploadFileToWebServer(url,filePath,ftpFileName,csvFile);
				} catch (IOException e) {
					LOGGER.info("------------------------------------catchException :");
					e.printStackTrace();
				}finally{
					LOGGER.info("------------------------------------delete tmp file");
					FileUtil.deleteContents(localFile);
					localFile.delete();
				}
		}
		return 0;
	}
	
	
	public void uploadFileToWebServer(String url,String filepath,String logFile,File csvFile){
		if(url==null){//未配置
			LOGGER.error("未配置job文件推送web目录地址");
			return;
		}
		HttpClient httpclient = new DefaultHttpClient();  
        try {  
        	if (StrUtil.isNotBlank(logFile)){
        		//修改原csv名称
        		String timeStamp = "";
        		if (logFile.contains("1440")){
        			String[] paraArray = logFile.split("_");
        			timeStamp = paraArray[4];
        			timeStamp = timeStamp.substring(0, timeStamp.indexOf(".Cmcc"));
        			String csvPath = csvFile.getAbsolutePath().replace(".csv", "").replace(".CSV", "");
        			String csvNewPath = csvPath + "_" + timeStamp +".csv";
        			File newFile = new File(csvNewPath);
        			csvFile.renameTo(newFile);
        			csvFile = newFile;
        		}else{
        			String[] paraArray = logFile.split("_");
        			timeStamp = paraArray[2];
        			timeStamp = timeStamp.substring(0, timeStamp.indexOf(".Cmcc"));
        			String csvPath = csvFile.getAbsolutePath().replace(".csv", "").replace(".CSV", "");
        			String csvNewPath = csvPath + "_" + timeStamp +".csv";
        			File newFile = new File(csvNewPath);
        			csvFile.renameTo(newFile);
        			csvFile = newFile;
        		}
        	}
            HttpPost httppost = new HttpPost(url);  
            FileBody loggerFile = new FileBody(new File(filepath + File.separator + logFile));  
            FileBody csvFileBody = new FileBody(csvFile);  
            StringBody comment = new StringBody(logFile);  
            //设置请求头
            LOGGER.info("-------------------------Log File path is :" + filepath + File.separator + logFile);
            LOGGER.info("-------------------------CSV File path is :" + filepath + File.separator + csvFile.getAbsolutePath());
            MultipartEntity reqEntity = new MultipartEntity();  
            reqEntity.addPart("logFile", loggerFile);    
            reqEntity.addPart("csvFile", csvFileBody);    
            reqEntity.addPart("filename1", comment);
            httppost.setEntity(reqEntity);  
        	HttpResponse response = httpclient.execute(httppost);  
        	int statusCode = response.getStatusLine().getStatusCode();  
        	if(statusCode == HttpStatus.SC_OK){  
        		LOGGER.info("服务器正常响应.....");  
        		HttpEntity resEntity = response.getEntity();  
        		EntityUtils.consume(resEntity);  
        	}  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {   
                httpclient.getConnectionManager().shutdown();   
            } catch (Exception ignore) {  
                  
            }  
        }  
    }  

	private void fileNoContentOrFtpNoFile(String prvId, String prvCode,
			String taskId, long nowDateTimeStr,String errorInfo) {
		persistenceErrorInfo(taskId, prvId, prvCode,new StringBuffer(errorInfo));
	}
	
	private Map<String,String> executeResInsert(String pkName,String typeName,String tableName,List<String> columns,Map<String, String> map,
			SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo){
		
		Map<String,String> result = new HashMap<String,String>();
//		switch(tableName.toLowerCase()){
		
	/*	case TableType.RESOURCE_SYS_REGION:
			result=resourceService.InsertSysRegion(typeName,columns,map,sysProvinceVO,String.valueOf(BatchControlComm.resDelimiter));
			break;*/
//		case TableType.RESOURCE_DAT_BASESITE:
//			
//			result=resourceService.InsertBasesite(typeName,columns,map,sysProvinceVO, taskInfo,BatchControlComm.resDelimiterStr);
//			break;
//		case TableType.RESOURCE_DAT_BASERESOURCE:
//		
//			result=resourceService.InsertBaseresource(typeName,columns,map,sysProvinceVO, taskInfo,BatchControlComm.resDelimiterStr);
//			
//			break;
//		case TableType.RESOURCE_DAT_BASESTATION:
//			
//			result=resourceService.InsertBaseStation(typeName,columns,map,sysProvinceVO, taskInfo,BatchControlComm.resDelimiterStr);
//			break;
//		}
		return result; 
	} 
	
	private boolean executeResUpdate(String pkName,String typeName,String tableName,List<String> columns,Map<String, String> map,
			SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo){
		
		boolean result=false;
		switch(tableName.toLowerCase()){
		
		/*case TableType.RESOURCE_SYS_REGION:
			result=resourceService.updateSysRegion(typeName,columns,map,sysProvinceVO,String.valueOf(BatchControlComm.resDelimiter));
			break;*/
		case TableType.RESOURCE_DAT_BASESITE:
			
			result=resourceService.updateBasesite(typeName,columns,map,sysProvinceVO, taskInfo,BatchControlComm.resDelimiterStr);
			break;
		case TableType.RESOURCE_DAT_BASERESOURCE:
		
			result=resourceService.updateBaseresource(typeName,columns,map,sysProvinceVO, taskInfo,BatchControlComm.resDelimiterStr);
			
			break;
		case TableType.RESOURCE_DAT_BASESTATION:
			
			result=resourceService.updateBaseStation(typeName,columns,map,sysProvinceVO, taskInfo,BatchControlComm.resDelimiterStr);
			break;
		}
		
		return result;
	} 
	
	private boolean executeResDel(String pkName,String typeName,String tableName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO){
			
		boolean result=false;
		switch(tableName.toLowerCase()){
		
		/*case TableType.RESOURCE_SYS_REGION:
			result=resourceService.delSysRegion(typeName,columns,map,sysProvinceVO);
			break;*/
		case TableType.RESOURCE_DAT_BASESITE:
			
			result=resourceService.delBasesite(pkName,typeName,columns,map,sysProvinceVO);
			break;
		case TableType.RESOURCE_DAT_BASERESOURCE:
		
			result=resourceService.delBaseresource(pkName,typeName,columns,map,sysProvinceVO);
			
			break;
		case TableType.RESOURCE_DAT_BASESTATION:
			
			result=resourceService.delBaseStation(pkName,typeName,columns,map,sysProvinceVO);
			break;
		}
		
		return result;
	} 
	
	private boolean delResFile(String localPath,String fileName){
		
		boolean isDel=false;
		String[] fileNameArray=fileName.split("_");//用"_"分隔文件名
		if(fileNameArray.length==3){//判断当是下划线最后一段时 （业务：文件名命名方式：省网标识_数据分类标识_文件生成时间）
			String datetime=fileNameArray[2];//将最后一个文件名存到datetime中
			if(StringUtils.isNoneBlank(datetime)){//如果datetime不为空
				datetime=datetime.substring(0, 8);//截取下标0-8的字符，并放入datetime中
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd"); // 格式化当前系统日期
				try {
					Date d = dateFm.parse(datetime);//将截取到的字符串转化为Date对象
					Date oldDate=DateConverter.getDate(-10);//获取到前10天的日期
					if(d.before(oldDate)){//如果获取到文件的时间在10天之前
						isDel=true;
						File file=new File(localPath+File.separator+fileName);//获取到文件名
						if(file.exists()){//如果存在 ，删除。（删除10天前的文件）
							file.delete();
							isDel=true;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return isDel;
	}

	private List<FtpFileConfigVO> sortResFtpFileConfig(List<FtpFileConfigVO> ftpFileConfigVOs) {

		List<FtpFileConfigVO> result = new ArrayList<FtpFileConfigVO>();
		List<FtpFileConfigVO> first = new ArrayList<FtpFileConfigVO>();
		List<FtpFileConfigVO> second = new ArrayList<FtpFileConfigVO>();
		List<FtpFileConfigVO> third = new ArrayList<FtpFileConfigVO>();
		List<FtpFileConfigVO> four = new ArrayList<FtpFileConfigVO>();
		for (FtpFileConfigVO ftpFileConfigVO : ftpFileConfigVOs) {
			String perfix=ftpFileConfigVO.getFilePerfix();
			if(perfix.equalsIgnoreCase("City")||perfix.equalsIgnoreCase("Region")){
				first.add(ftpFileConfigVO);
			}
	        else if(perfix.equalsIgnoreCase("Site")){
	        	second.add(ftpFileConfigVO);
			}
	        else if(perfix.equalsIgnoreCase("Room")||perfix.equalsIgnoreCase("ResourceSpot")||perfix.equalsIgnoreCase("Hotspot")){
	        	third.add(ftpFileConfigVO);
	        }
	        else{
	        	four.add(ftpFileConfigVO);
	        }
		}
		result.addAll(first);
		result.addAll(second);
		result.addAll(third);
		result.addAll(four);
		return result;
	}
	
    private boolean delRingFile(String localPath,String fileName){
		
		boolean isDel=false;
		String[] fileNameArray=fileName.split("_1440_");
		if(fileNameArray.length==2){
			String datetime=fileNameArray[1];
			if(StringUtils.isNoneBlank(datetime)){
				int index=datetime.indexOf("_");
				if(index>=0){
					datetime=datetime.substring(0, 8);
					SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd"); // 格式化当前系统日期
					try {
						Date d = dateFm.parse(datetime);
						Date oldDate=DateConverter.getDate(-10);
						if(d.before(oldDate)){
							isDel=true;
							File file=new File(localPath+File.separator+fileName);
							if(file.exists()){
								file.delete();
								isDel=true;
							}
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return isDel;
	}
	
	private boolean executeRingInsert(String pkName,String typeName,String tableName,List<String> columns,Map<String, String> map,
			SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo){
		
		boolean result=false;
		switch(tableName.toLowerCase()){
		
		case TableType.RING_METER:
		
			ringService.InsertMeter(typeName,columns,map,sysProvinceVO,taskInfo,BatchControlComm.ringDelimiterStr);
			
			break;
		case TableType.RING_POWER:
			
			ringService.InsertPower(typeName,columns,map,sysProvinceVO,taskInfo,BatchControlComm.ringDelimiterStr);
			break;
		}
		return result; 
	} 
	
	private boolean executeRingUpdate(String pkName,String typeName,String tableName,List<String> columns,Map<String, String> map,
			SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo){
		
		boolean result=false;
		switch(tableName.toLowerCase()){
		
		case TableType.RING_METER:
		
			result=ringService.updateMeter(typeName,columns,map,sysProvinceVO,taskInfo,BatchControlComm.ringDelimiterStr);
			
			break;
		case TableType.RING_POWER:
			
			result=ringService.updatePower(typeName,columns,map,sysProvinceVO,taskInfo,BatchControlComm.ringDelimiterStr);
			break;
		}
		
		return result;
	} 
	
	private boolean executeRingDel(String pkName,String typeName,String tableName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO){
			
		boolean result=false;
		switch(tableName.toLowerCase()){
		
		case TableType.RING_METER:
		
			result=ringService.delMeter(pkName,typeName,columns,map,sysProvinceVO);
			
			break;
		case TableType.RING_POWER:
			
			result=ringService.delPower(pkName,typeName,columns,map,sysProvinceVO);
			break;
		}
		
		return result;
	}

    private boolean delContractFile(String localPath,String fileName){
		
		boolean isDel=false;
		String[] fileNameArray=fileName.split("_");
		if(fileNameArray.length==3){
			String datetime=fileNameArray[2];
			if(StringUtils.isNoneBlank(datetime)){
				datetime=datetime.substring(0, 8);
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd"); // 格式化当前系统日期
				try {
					Date d = dateFm.parse(datetime);
					Date oldDate=DateConverter.getDate(-10);
					if(d.before(oldDate)){
						isDel=true;
						File file=new File(localPath+File.separator+fileName);
						if(file.exists()){
							file.delete();
							isDel=true;
						}
					}
				} catch (Exception e) {
				}
			}
		}
		return isDel;
	}
	
	/**
	 * 文件分析
	 * errorContents:错误行[pk-行value]
	 * errorInfo:错误信息\r\n错误信息\r\n
	 * errorContents、errorInfo记录数据库，并写入analysis.log，传入提供方ftp目录下
	 * @return
	 * @throws Exception 
	 */
	private Map<String, String> fileAnalysis(File files, Map<String,String> insertRes, Map<String,String> updateRes, 
			List<String> columns, String perfix, String errorInfo,char divideFlag,SysProvinceVO sysProvinceVO) throws Exception {
		StringBuffer loginfoBF = new StringBuffer();
		long start_compare=System.currentTimeMillis();
		
		//文件合法性验证
		Map<String,String> errorContents = new HashMap<>();
		Map<String,String> errorInsertContents=fileService.analysis(files,insertRes, columns, perfix, errorInfo,divideFlag,sysProvinceVO);
		errorContents.putAll(errorInsertContents);
		
		if(updateRes!=null){
			Map<String,String> errorUpdateContents=fileService.analysis(files,updateRes, columns, perfix, errorInfo,divideFlag,sysProvinceVO);
			errorContents.putAll(errorUpdateContents);
		}
		
		long end_compare=System.currentTimeMillis();
		loginfoBF.append("---------文件分析耗时"+(end_compare-start_compare)/60000+"min "+(end_compare-start_compare)%60000+"ms\r\n");
		errorContents.put("loginfoBF", loginfoBF.toString());
		LOGGER.info(loginfoBF.toString());
		
		return errorContents;
	}
	
	private static void reverseArray(String[] Array) {  
        ArrayList<String> array_list = new ArrayList<String>();  
        for (int i = 0; i < Array.length; i++) {  
            array_list.add(Array[Array.length - i - 1]);  
        }  
        Array = array_list.toArray(Array);  
    }

	@Override
	@Async
	public void syncTowerData(TaskInfoVO taskInfo) {
		String prvId = taskInfo.getPrvId();
		LOGGER.info("-taskInfo---prvId: "+prvId);
		
		SysProvinceVO sysProvinceVO = provinceService.getProvinceByPk(prvId);
		String prvCode = sysProvinceVO.getPrvCode();
		LOGGER.info("-taskInfo---prvCode: "+prvCode);
		
		List<SysUserVO> users = sysUserService.queryAllAdminUser();
		Map<Integer, SysUserVO> map = new HashMap<Integer, SysUserVO>();
		for (SysUserVO vo : users) {
			map.put(vo.getUserLoginname().hashCode(), vo);
		}
		int ukey = (prvCode.toLowerCase() + CommonData.ADMIN_USER).hashCode();
		String userName = map.containsKey(ukey) ? map.get(ukey).getUserLoginname() : CommonData.DEFAULT_USER;
		LOGGER.info("-flow---user: " + userName);
		
		String taskId= taskInfo.getTaskId();
		// 根据taskInfo获取需要同步的FTP文件列表
		List<FtpFileConfigVO> ftpFileConfigVOList = taskInfoService.getFtpFileConfigByTaskId(taskId);
		if(ftpFileConfigVOList.size() <= 0){
			LOGGER.error("FTP文件列表为空!");
			//记录信息
			taskHistoryInfoService.recordLog(taskInfo, "FTP文件列表为空!", prvId);
			return;
		}
		
		// 获取数据字典文件类型信息
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("groupId", CommonData.DIC_FILE_TYPE);
		List<DictionaryVO> dicList = provinceService.querySysDictionary(param);
		Map<Integer, String> dicMap = new HashMap<Integer, String>();
		for (DictionaryVO vo : dicList) {
			int dnkey = vo.getDictName().hashCode();
			dicMap.put(dnkey, vo.getDictValue());
		}
		
		try {
			for (FtpFileConfigVO vo : ftpFileConfigVOList) {
				//文件类型（批量起租单）
				int key = vo.getFileDataType().hashCode();
				if (dicMap.containsKey(key)) {
					String fileType = dicMap.get(key);// PLQZ
					String serverCode = vo.getFilePerfix();// 服务类型（T03_OT_004）
					String accountPeriod = StringUtils.isBlank(taskInfo.getTaskTime()) ? DateUtil.format(new Date(), "yyyyMM") : taskInfo.getTaskTime();
					String reqParam = getReqestXml(serverCode, fileType, accountPeriod, prvId);
					
					// 接口请求参数处理
					ReqDownload download =  XStreamUtil.xml2Bean(reqParam, ReqDownload.class);
					
					//根据运营商编码和服务编码获取令牌
					String accessToken = AccessTokenGenerator.getAccessToken(download.getCustCompany(), download.getServiceCode());
					if (StringUtils.isNotBlank(accessToken)) {
						download.setAccessToken(accessToken);
						download.setHandleType(CommonData.DOWNLOAD_REGISTER);
						download.setRequestTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
						// 请求下载
						ResDownload resDownLoad = TwrWsCli.request(download);
						// 响应成功
						if (CommonData.RESPONSE_CODE.equalsIgnoreCase(resDownLoad.getResponseCode())) {
							// 处理数据
							ironTowerService.processData(taskInfo ,download, resDownLoad, userName);
							// 通知下载成功
							ReqDownload req = download;
							req.setHandleType(CommonData.DOWNLOAD_FINISH);// 下载完成
							req.setStatus(CommonData.STATUS_SUCCESS);// 下载成功
							req.setServiceCode(resDownLoad.getServiceCode());
							req.setFlowId(resDownLoad.getFlowId());
							ResDownload finishResult = TwrWsCli.request(req);
							LOGGER.info(finishResult.toString());
						} else {
							LOGGER.error("铁塔下载请求失败,原因:" + resDownLoad.getResponseMsg());
						}
					} else {
						LOGGER.error("铁塔令牌获取失败!");
					}
				} else {
					LOGGER.error("找不到对应的文件类型:" + vo.getFileDataType());
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
	} 
	
	
	private String getReqestXml(String serverCode,String fileType,String accountPeriod,String provinceId){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<PACKET><HEAD>");
		sb.append("<HANDLE_TYPE/>");
		sb.append("<CUST_COMPANY>").append("1001").append("</CUST_COMPANY>");
		sb.append("<SERVICE_CODE>").append(serverCode).append("</SERVICE_CODE>");
		sb.append("<ACCESS_TOKEN/>");
		sb.append("<FILE_TYPE>").append(fileType).append("</FILE_TYPE>");
		sb.append("<REQUEST_TIME/>");
		sb.append("<ACCOUNT_PERIOD>").append(accountPeriod).append("</ACCOUNT_PERIOD>");
		sb.append("<PROVINCE_ID>").append(provinceId).append("</PROVINCE_ID>");
		sb.append("<CITY_ID>").append(provinceId).append("</CITY_ID>");
		sb.append("<FLOW_ID/>");
		sb.append("<STATUS/>");
		sb.append("<SYS_COMPANY/>");  
		sb.append("</PACKET></HEAD>");
		return sb.toString();
	}
	
	private boolean delfiles(String type,String localPath,String fileName){
		boolean b = false;
		switch (type.toLowerCase()) {
		case "ring":
			b= delRingFile(localPath, fileName);
			break;
		case "res":
			b= delResFile(localPath, fileName);
			break;
		case "contract":
			b= delContractFile(localPath, fileName);
			break;
		}
		return b;
	}
}
