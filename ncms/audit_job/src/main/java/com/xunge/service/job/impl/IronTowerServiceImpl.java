package com.xunge.service.job.impl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.iot.ncms.twrwscli.bean.ReqDownload;
import com.cmcc.iot.ncms.twrwscli.bean.ResDownload;
import com.xunge.comm.job.RestServerUtils;
import com.xunge.comm.tower.CommonData;
import com.xunge.comm.tower.FtpUtil;
import com.xunge.comm.tower.FtpVO;
import com.xunge.dao.basedata.system.SysDictionaryMapper;
import com.xunge.dao.basedata.system.SysRegionVOMapper;
import com.xunge.dao.towerrent.TowerBillbalanceMapper;
import com.xunge.dao.towerrent.TowerRentInformationTowerMapper;
import com.xunge.dao.towerrent.TowerRentinformationBizchangeMapper;
import com.xunge.dao.towerrent.TowerStopServerMapper;
import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.system.region.DictionaryVO;
import com.xunge.model.towerrent.TowerBillbalanceVO;
import com.xunge.model.towerrent.TowerRentInformationTowerVO;
import com.xunge.model.towerrent.TowerRentinformationBizchangeVO;
import com.xunge.model.towerrent.TowerStopServerVO;
import com.xunge.service.job.IIronTowerService;
import com.xunge.service.job.ITaskHistoryInfoService;
import com.xunge.service.job.util.irontower.IronTowerFileType;
import com.xunge.service.towerrent.ITowerRestService;
import com.xunge.util.DateConverter;
import com.xunge.util.PropertiesLoader;
import com.xunge.util.XmlTool;

@Service
public class IronTowerServiceImpl implements IIronTowerService{

	private static final Logger LOGGER = Logger.getLogger(IronTowerServiceImpl.class);
	
	@Resource
	private SysRegionVOMapper regionVOMapper;
	
	@Resource
	private SysDictionaryMapper dictionaryMapper;
	
	@Resource
	TowerBillbalanceMapper billbalanceMapper;
	
	@Resource
	TowerRentinformationBizchangeMapper bizchangeMapper;
	
	@Resource
	TowerRentInformationTowerMapper informationTowerMapper;
	
	@Resource
	TowerStopServerMapper stopServerMapper;
	
	@Resource
	private ITowerRestService restService;
	
	@Resource
	private ITaskHistoryInfoService taskHistoryInfoService;
	
	@Override
 	public JSONObject requestDownloadFile(String requestXml) {
		
		//String ironTowerUrl =  PropertiesUtil.getProperty("IronTowerUrl");
		String ironTowerUrl = new PropertiesLoader("\\properties\\sysConfig.properties").getProperty("ironTowerUrl");
		JSONObject json=null;
		String xml=callWebService(ironTowerUrl,requestXml);
		try {
			json=XmlTool.documentToJSONObject(xml);
		} catch (DocumentException e) {
			LOGGER.error(e.getMessage());
		}
		
		return json;
	}

	@Override
	public JSONObject noticeFileDownloadFinish(String noticeXml) {
		
		//String ironTowerUrl =  PropertiesUtil.getProperty("IronTowerUrl");
		String ironTowerUrl = new PropertiesLoader("\\properties\\sysConfig.properties").getProperty("ironTowerUrl");
		JSONObject json=null;
		String xml=callWebService(ironTowerUrl,noticeXml);
		try {
			json=XmlTool.documentToJSONObject(xml);
		} catch (DocumentException e) {
			LOGGER.error(e.getMessage());
		}
		
		return json;
	}
	
	private String callWebService(String url,String requestStr){
		
		HttpURLConnection conn=null;
		OutputStream os=null;
		InputStream is=null;
		 StringBuffer sb=new StringBuffer();
		try {
			 //服务的地址
	        URL wsUrl = new URL(url);
	        conn = (HttpURLConnection) wsUrl.openConnection(); 
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
	        
	        os = conn.getOutputStream();
	        os.write(requestStr.getBytes());
	        
	        is = conn.getInputStream();
	        byte[] b = new byte[1024];
	        int len = 0;
	        while((len = is.read(b)) != -1){
	            String ss = new String(b,0,len,"UTF-8");
	            sb.append(ss);
	        }
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		finally{
			
			try {
				if(is!=null){
					is.close();
				}
				if(os!=null){
					os.close();
				}
				if(conn!=null){
					conn.disconnect();
				}
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return sb.toString();
	}

	@Override
	public void processData(TaskInfoVO taskInfo,ReqDownload download, ResDownload resDownLoad, String userName) throws Exception {
		
		String filePath = resDownLoad.getFilePath();
		URL url = new URL(filePath);
		
		FtpUtil f = new FtpUtil(false); 
		FtpVO ftp = new FtpVO(url.getHost(), url.getPort(), resDownLoad.getFtpUser(), resDownLoad.getFtpPwd());
		if(f.login(ftp)){  
//            f.List("/","xlsx");  
            f.List("/");
        }  
		// 本地文件路径
		String localPath = RestServerUtils.getTomcatRootPath() + File.separator + "tower/" +download.getFileType().trim().toLowerCase();
		LOGGER.info("====="+localPath);
		// 判断是否存在本地路径，不存在新建文件路径
		File file = new File(localPath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		
        List<String> list = f.fileList;
        if (list.isEmpty()) {
        	LOGGER.error("FTP目录下无文件");
			//记录信息
        	if (null != taskInfo) {
        		String historyId = "";
        		taskHistoryInfoService.recordLog(taskInfo, "FTP目录下无文件", historyId);
			}
		} else {
			// 测试阶段删除文件操作注释,正式上线打开
			/*String[] localFileNames = file.list();
			// 删除10天前的文件， 同时获取比对用的前天的文件名称
			for (String fileName : localFileNames) {
				boolean isDel = delTowerFile(localPath, fileName);
				if (isDel) {
					continue;
				}
			}*/
			
			// 获取数据字典信息
			List<DictionaryVO> dicList = dictionaryMapper.querySysDictionary(null);
			Map<Integer, String> dicNameMap = new HashMap<Integer, String>();
			Map<Integer, String> dicValueMap = new HashMap<Integer, String>();
			for (DictionaryVO vo : dicList) {
				int dnkey = (vo.getDictgroupCode() + "#" + vo.getDictName()).hashCode();
				dicNameMap.put(dnkey, vo.getDictValue());
				int dvkey = (vo.getDictgroupCode() + "#" + vo.getDictValue()).hashCode();
				dicValueMap.put(dvkey, vo.getDictName());
			}
			
			// 获取省市区信息
			SysRegionVO region = new SysRegionVO();
			region.setRegState(CommonData.REGION_STATE);
			region.setPrvId(taskInfo.getPrvId());
			List<SysRegionVO> listreg = regionVOMapper.getAddress(region);
			
			String prvId = download.getProvinceId();
			String cityId = download.getCityId();
			String fileType = download.getFileType();
			// 数据过滤条件
			Map<String,Object> queryParam = new HashMap<String,Object>();
			queryParam.put("prvId", prvId);// 省ID
			if (!prvId.equals(cityId)) {
				queryParam.put("pregId", cityId);// 市ID
			}
			// 请求参数无区县ID数据过滤到地市
		    // queryParam.put("regId", "");// 区ID
			Map<Integer, Object> dataMap = getDataMap(fileType,queryParam);
			
			// 下载并处理excel文件
			for (String fileName : list) {
				boolean flag = f.downLoadFile(fileName, localPath+fileName);
				LOGGER.info("download " + fileName + ( flag ? " success" : " failed"));
				File newFile = new File(localPath + fileName);
				
				// 处理铁塔类型资源
				String[] strs = fileName.split("-");
				if (strs.length == 6) {
					if (strs[5].contains("01")){// 塔类产品
						restService.processData(taskInfo, newFile, download.getFileType(), listreg, dataMap, dicNameMap, dicValueMap, userName);
					}
				} else {
					restService.processData(taskInfo, newFile, download.getFileType(), listreg, dataMap, dicNameMap, dicValueMap, userName);
				}
			}
		}
		f.disConnection(); // 关闭FTP链接
	}
	
	/**
	 * 根据不同文件类型和查询参数查询数据库数据
	 * @param fileType 文件类型  IronTowerFileType类中的文件类型
	 * @param queryParam 查询数据参数
	 * @return map
	 */
	private Map<Integer, Object> getDataMap(String fileType, Map<String, Object> queryParam) {
		Map<Integer, Object> dataMap = new HashMap<Integer, Object>();
		// 铁塔起租信息
		if (fileType.equalsIgnoreCase(IronTowerFileType.PLQZ.toString())) {
			// 根据条件查询数据
			List<TowerRentInformationTowerVO> datas = informationTowerMapper.queryDataByCondition(queryParam);
			// 将查询出来的数据封装map
			for (TowerRentInformationTowerVO vo : datas) {
				int key = (vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode()).hashCode();
				dataMap.put(key, vo);
			}
		}
		// 铁塔业务变更信息
		if (fileType.equalsIgnoreCase(IronTowerFileType.YWBG.toString())) {
			// 根据条件查询数据
			List<TowerRentinformationBizchangeVO> datas = bizchangeMapper.queryDataByCondition(queryParam);

			// 将查询出来的数据封装map
			for (TowerRentinformationBizchangeVO vo : datas) {
				int key = (vo.getBusinessConfirmNumber() + "#" +vo.getTowerStationCode()+"#"+vo.getChangeItem()).hashCode();
				dataMap.put(key, vo);
			}
		}
		// 铁塔服务终止信息
		if (fileType.equalsIgnoreCase(IronTowerFileType.FWZZ.toString())) {
			// 根据条件查询数据
			List<TowerStopServerVO> datas = stopServerMapper.queryDataByCondition(queryParam);

			// 将查询出来的数据封装map
			for (TowerStopServerVO vo : datas) {
				int key = (vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode()).hashCode();
				dataMap.put(key, vo);
			}
		}
		// 对账确认单 
		if (fileType.equalsIgnoreCase(IronTowerFileType.DZQR.toString())) {
			
		}
		// 结算详单|确认费用清单
		if (fileType.equalsIgnoreCase(IronTowerFileType.JSXD.toString())
			|| fileType.equalsIgnoreCase(IronTowerFileType.QRFY.toString())) {
			
			// 根据条件查询数据
			List<TowerBillbalanceVO> datas = billbalanceMapper.queryDataByCondition(queryParam);
			
			// 将查询出来的数据封装map
			for (TowerBillbalanceVO vo : datas) {
				int key = (vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode()).hashCode();
				dataMap.put(key, vo);
			}
		}
		// 电费清单
		if (fileType.equalsIgnoreCase(IronTowerFileType.DFQD.toString())) {
			
		}
		return dataMap;
	}
	
	/**
	 * 删除本地文件
	 * @param localPath
	 * @param fileName
	 * @return
	 */
	private boolean delTowerFile(String localPath, String fileName) {
		boolean isDel = false;
		String[] fileNameArray = fileName.split("-");
		String datetime = fileNameArray[4];
		if (StringUtils.isNoneBlank(datetime)) {
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMM"); // 格式化当前系统日期
			try {
				Date d = dateFm.parse(datetime);
				Date oldDate = DateConverter.getDate(-10);
				if (d.before(oldDate)) {
					isDel = true;
					File file = new File(localPath + File.separator + fileName);
					if (file.exists()) {
						file.delete();
						isDel = true;
					}
				}
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
				e.printStackTrace();
			}
		}
		return isDel;
	}
}
