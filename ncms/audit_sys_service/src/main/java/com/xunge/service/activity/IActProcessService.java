package com.xunge.service.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xunge.core.page.Page;
import com.xunge.model.activity.ActProcessVO;

/**
 * @author zhujj
 * @date 2017年6月15日 下午3:38:27 
 * @version 1.0.0 
 */
public interface IActProcessService {
	/**
	 * 流程定义列表
	 */
	public Page<ActProcessVO> processList(Page<ActProcessVO> page, String category) ;

	/**
	 * 流程定义列表
	 */
	public Page<ProcessInstance> runningList(Page<ProcessInstance> page, String procInsId, String procDefKey);
	
	/**
	 * 读取资源，通过部署ID
	 * @param processDefinitionId  流程定义ID
	 * @param processInstanceId 流程实例ID
	 * @param resourceType 资源类型(xml|image)
	 */
	public InputStream resourceRead(String procDefId, String proInsId, String resType) throws Exception;
	
	/**
	 * 部署流程 - 保存
	 * @param file
	 * @return
	 */
	@Transactional(readOnly = false)
	public String deploy(String exportDir, String category, MultipartFile file) ;
	
	/**
	 * 设置流程分类
	 */;
	public void updateCategory(String procDefId, String category) ;

	/**
	 * 挂起、激活流程实例
	 */;
	public String updateState(String state, String procDefId);
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */;
	public org.activiti.engine.repository.Model convertToModel(String procDefId) throws UnsupportedEncodingException, XMLStreamException ;
	
	/**
	 * 导出图片文件到硬盘
	 */
	public List<String> exportDiagrams(String exportDir) throws IOException;

	/**
	 * 删除部署的流程，级联删除流程实例
	 * @param deploymentId 流程部署ID
	 */;
	public void deleteDeployment(String deploymentId) ;
	
	/**
	 * 删除部署的流程实例
	 * @param procInsId 流程实例ID
	 * @param deleteReason 删除原因，可为空
	 */
	public void deleteProcIns(String procInsId, String deleteReason);
	
}
