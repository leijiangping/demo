package com.xunge.service.activity.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.page.Page;
import com.xunge.core.util.StrUtil;
import com.xunge.service.activity.IActModelService;
import com.xunge.service.activity.editor.language.json.converter.BpmnJsonConverter;

/**
 * @author 朱俊杰
 * @date 2017年6月13日 下午2:54:35
 * @describe
 */
@Service
@Transactional(readOnly = true)
public class ActModelServiceImpl implements IActModelService{
	@Autowired
	private RepositoryService repositoryService;
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private ObjectMapper objectMapper;
	protected ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 流程模型列表
	 */
	public Page<org.activiti.engine.repository.Model> modelList(Page<org.activiti.engine.repository.Model> page, String category) {

		ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
		
		if (StringUtils.isNotEmpty(category)){
			modelQuery.modelCategory(category);
		}
		page.setTotal(modelQuery.count());
		page.setResult(modelQuery.listPage(page.getFirstResult(),page.getPageSize()));

		return page;
	}

	/**
	 * 创建模型
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional(readOnly = false)
	public Model create(String name, String key, String description, String category) throws UnsupportedEncodingException {
		if(StrUtil.isNotBlank(category)){
			key=key+"_"+category;
		}
		
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", PromptMessageComm.CANVAS);
		editorNode.put("resourceId", PromptMessageComm.CANVAS);
		
		ObjectNode properties = objectMapper.createObjectNode();
		properties.put("process_author", "");
		properties.put("process_id",key);
		properties.put("name",name);
		properties.put("documentation",description);
		editorNode.put("properties", properties);
		
		ObjectNode stencilset = objectMapper.createObjectNode();
		stencilset.put("namespace", PromptMessageComm.NAMESPACE);
		editorNode.put("stencilset", stencilset);

		Model modelData = repositoryService.newModel();
		description = StringUtils.defaultString(description);
		modelData.setKey(StringUtils.defaultString(key));
		modelData.setName(name);
		modelData.setCategory(category);
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));
		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelData.setMetaInfo(modelObjectNode.toString());
		
		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes(PromptMessageComm.UTF_8));
		
		return modelData;
	}
	/**
	 * 创建模型
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional(readOnly = false)
	public Model update(String name, String key, String description, String category,String modelId) throws UnsupportedEncodingException {
		if(StrUtil.isNotBlank(key)){
			key=key+"_"+category;
		}
	
		Model modelData = repositoryService.getModel(modelId);
		description = StringUtils.defaultString(description);
		modelData.setKey(StringUtils.defaultString(key));
		modelData.setName(name);
		modelData.setCategory(category);
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));
		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelData.setMetaInfo(modelObjectNode.toString());
		
		repositoryService.saveModel(modelData);	
		try {
			ObjectNode editorNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			editorNode.put("id",PromptMessageComm.CANVAS);
			editorNode.put("resourceId", PromptMessageComm.CANVAS);
			
			ObjectNode properties = objectMapper.createObjectNode();
			properties.put("process_author", "");
			properties.put("process_id",key);
			properties.put("name",name);
			properties.put("documentation",description);
			editorNode.put("properties", properties);
			
			ObjectNode stencilset = objectMapper.createObjectNode();
			stencilset.put("namespace", PromptMessageComm.NAMESPACE);
			editorNode.put("stencilset", stencilset);

			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes(PromptMessageComm.UTF_8));	
		} catch (IOException e) {
			// TODO Auto-generated catch block
    		throw new BusinessException(PromptMessageComm.COPY_MODEL_FAIL);
		}
         
		return modelData;
	}
	/**
	 * 创建模型
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional(readOnly = false)
	public Model insertCloneModel(String name, String key, String description, String category,String modelId) throws UnsupportedEncodingException {
		if(StrUtil.isNotBlank(key)&&StrUtil.isNotBlank(category)){
			key=key.split("_")[0]+"_"+category;
		}
		
		Model modelData = repositoryService.newModel();
		description = StringUtils.defaultString(description);
		modelData.setKey(StringUtils.defaultString(key));
		modelData.setName(name);
		modelData.setCategory(category);
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));
		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelData.setMetaInfo(modelObjectNode.toString());
		
		repositoryService.saveModel(modelData);	
		try {
			ObjectNode editorNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelId));
			editorNode.put("id", PromptMessageComm.CANVAS);
			editorNode.put("resourceId", PromptMessageComm.CANVAS);
			
			ObjectNode properties = objectMapper.createObjectNode();
			properties.put("process_author", "");
			properties.put("process_id",key);
			properties.put("name",name);
			properties.put("documentation",description);
			editorNode.put("properties", properties);
			
			ObjectNode stencilset = objectMapper.createObjectNode();
			stencilset.put("namespace", PromptMessageComm.NAMESPACE);
			editorNode.put("stencilset", stencilset);

		
			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes(PromptMessageComm.UTF_8));	
		} catch (IOException e) {
			// TODO Auto-generated catch block
    		throw new BusinessException(PromptMessageComm.COPY_MODEL_FAIL);
		}
         
		return modelData;
	}
	/**
	 * 根据Model部署流程
	 */
	@Transactional(readOnly = false)
	public String deploy(String id) {
		String message = "";
		try {
			org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
			
			String processName = modelData.getName();
			if (!StringUtils.endsWith(processName, PromptMessageComm.PROCESS_NAME_END_WITH)){
				processName += PromptMessageComm.PROCESS_NAME_END_WITH;
			}
//			System.out.println("========="+processName+"============"+modelData.getName());
			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
					.addInputStream(processName, in).deploy();
//					.addString(processName, new String(bpmnBytes)).deploy();
			
			// 设置流程分类
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
			for (ProcessDefinition processDefinition : list) {
				repositoryService.setProcessDefinitionCategory(processDefinition.getId(), modelData.getCategory());
				message = PromptMessageComm.DEPLOYMENT_SUCCESS + processDefinition.getId();
			}
			if (list.size() == 0){
				message = PromptMessageComm.DEPLOYMENT_FAIL;
			}
		} catch (Exception e) {
			throw new ActivitiException(PromptMessageComm.DESIGN_MODEL_ERROR+id, e);
		}
		return message;
	}
	
	/**
	 * 导出model的xml文件
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public void export(String id, HttpServletResponse response) {
		try {
			org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId() + PromptMessageComm.PROCESS_NAME_END_WITH;
			response.setHeader(PromptMessageComm.CONTENT_DISPOSITION, PromptMessageComm.ATTACHMENT + filename);
			response.flushBuffer();
		} catch (Exception e) {
			throw new ActivitiException(PromptMessageComm.EXP_MODEL_XML_FAIL+id, e);
		}
		
	}

	/**
	 * 更新Model分类
	 */
	@Transactional(readOnly = false)
	public void updateCategory(String id, String category) {
		org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
		modelData.setCategory(category);
		repositoryService.saveModel(modelData);
	}
	
	/**
	 * 删除模型
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		repositoryService.deleteModel(id);
	}
}
