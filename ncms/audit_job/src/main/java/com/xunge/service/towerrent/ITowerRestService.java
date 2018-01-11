package com.xunge.service.towerrent;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.colletion.TaskInfoVO;

public interface ITowerRestService {
	/**
	 * 处理数据
	 * @param taskinfo 任务对象
	 * @param file ftp获取的文件
	 * @param fileType 文件类型,参考常量类
	 * @param listreg 省市区数据
	 * @param dataMap 数据库已存在的数据
	 * @param dicNameMap 数据字典    key:(枚举值标题code+#+枚举值描述) 哈希码  value:枚举值数值
	 * @param dicValueMap 数据字典 key:(枚举值标题code+#+枚举值数值) 哈希码   value:枚举值描述
	 * @param userName 流程发起人
	 */
	public void processData(TaskInfoVO taskInfo,File file,String fileType,List<SysRegionVO> listreg,Map<Integer, Object> dataMap,Map<Integer, String> dicNameMap,Map<Integer, String> dicValueMap, String userName);
}
