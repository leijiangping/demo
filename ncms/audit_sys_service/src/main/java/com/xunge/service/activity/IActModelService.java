package com.xunge.service.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Model;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xunge.core.page.Page;

/**
 * @author 朱俊杰
 * @date 2017年6月13日 下午2:52:47
 * @describe
 */
public interface IActModelService {
	/**
	 * 流程模型列表
	 */
	public Page<org.activiti.engine.repository.Model> modelList(Page<org.activiti.engine.repository.Model> page, String category);
	
	
	/**
	 * 创建模型
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional(readOnly = false)
	public Model create(String name, String key, String description, String category) throws UnsupportedEncodingException ;
	/**
	 * 修改模型
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional(readOnly = false)
	public Model update(String name, String key, String description, String category,String modelId) throws UnsupportedEncodingException ;
	/**
	 * 克隆模型
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional(readOnly = false)
	public Model insertCloneModel(String name, String key, String description, String category,String modelId) throws UnsupportedEncodingException ;
	
	
	/**
	 * 根据Model部署流程
	 */
	@Transactional(readOnly = false)
	public String deploy(String id);
	
	/**
	 * 导出model的xml文件
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public void export(String id, HttpServletResponse response);
	
	/**
	 * 更新Model分类
	 */
	@Transactional(readOnly = false)
	public void updateCategory(String id, String category);
	
	/**
	 * 删除模型
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public void delete(String id);
}
