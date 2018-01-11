package com.xunge.controller.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.core.util.StrUtil;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.ActProcessVO;
import com.xunge.service.activity.impl.ActProcessService;


/**
 * 流程定义相关Controller
 * @author zhujj
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "/asserts/tpl/system/activity")
public class ActProcessController{

	@Autowired
	private ActProcessService actProcessService;

	/**
	 * 流程定义列表
	 */
	@RequestMapping(value = "/process/list", method=RequestMethod.POST)
	@ResponseBody
	public FeedBackObject processList(String category,int cur_page_num,int page_count, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
	    Page<ActProcessVO> page = actProcessService.processList(new Page<ActProcessVO>(cur_page_num, page_count), category);
		System.out.println(PromptMessageComm.SYSO_PROCESS_DEF);
		FeedBackObject backObj = new FeedBackObject();
		backObj.success=RESULT.SUCCESS_1;
		backObj.Obj=page;
		return backObj;
	}
	
	/**
	 * 运行中的实例列表
	 */
	@RequestMapping(value = "/process/running")
	public String runningList(String procInsId, String procDefKey,int cur_page_num,int page_count, HttpServletRequest request, HttpServletResponse response, Model model) {
	    Page<ProcessInstance> page = actProcessService.runningList(new Page<ProcessInstance>(cur_page_num,page_count), procInsId, procDefKey);
		model.addAttribute("page", page);
		model.addAttribute("procInsId", procInsId);
		model.addAttribute("procDefKey", procDefKey);
		return PromptMessageComm.WAY_ACT_ACTPROCESSRUNNINGLIST;
	}

	/**
	 * 读取资源，通过部署ID
	 * @param processDefinitionId  流程定义ID
	 * @param processInstanceId 流程实例ID
	 * @param resourceType 资源类型(xml|image)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/process/resource/read")
	public void resourceRead(String procDefId, String proInsId, String resType, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = actProcessService.resourceRead(procDefId, proInsId, resType);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 部署流程
	 */
	@RequestMapping(value = "/process/deploy", method=RequestMethod.GET)
	public String deploy(Model model) {
		return PromptMessageComm.WAY_ACT_ACTPROCESSDEPLOY;
	}
	
	/**
	 * 部署流程 - 保存
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/process/deploy", method=RequestMethod.POST)
	public String deploy(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir, 
			String category, MultipartFile file, RedirectAttributes redirectAttributes) {

		String fileName = file.getOriginalFilename();
		if (StrUtil.isBlank(fileName)){
			redirectAttributes.addFlashAttribute("message", PromptMessageComm.SELECT_DEPLOYMENT_FILE);
		}else{
			String message = actProcessService.deploy(exportDir, category, file);
			redirectAttributes.addFlashAttribute("message", message);
		}

		return PromptMessageComm.REDIRECT_ACT_PROCESS;
	}
	
	/**
	 * 设置流程分类
	 */
	@RequestMapping(value = "/process/updateCategory")
	public String updateCategory(String procDefId, String category, RedirectAttributes redirectAttributes) {
		actProcessService.updateCategory(procDefId, category);
		return PromptMessageComm.REDIRECT_ACT_PROCESS;
	}

	/**
	 * 挂起、激活流程实例
	 */
	@RequestMapping(value = "/process/update/{state}")
	public @ResponseBody
	FeedBackObject updateState(@PathVariable("state") String state, String procDefId, RedirectAttributes redirectAttributes) {
		String message = actProcessService.updateState(state, procDefId);
		FeedBackObject fbo=new FeedBackObject();
		fbo.setMsg(message);
		return fbo;
	}
	
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @param redirectAttributes
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	@RequestMapping(value = "/process/convert/toModel")
	public @ResponseBody
	FeedBackObject convertToModel(String procDefId, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, XMLStreamException {
		org.activiti.engine.repository.Model modelData = actProcessService.convertToModel(procDefId);
		FeedBackObject fbo=new FeedBackObject();
		fbo.setMsg(PromptMessageComm.CONVERSION_MODEL_SUCCESS+modelData.getId());
		return fbo;
	}
	
	/**
	 * 导出图片文件到硬盘
	 */
	@RequestMapping(value = "/process/export/diagrams")
	@ResponseBody
	public List<String> exportDiagrams(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir) throws IOException {
		List<String> files = actProcessService.exportDiagrams(exportDir);;
		return files;
	}

	/**
	 * 删除部署的流程，级联删除流程实例
	 * @param deploymentId 流程部署ID
	 */
	@RequestMapping(value = "/process/delete")
	public @ResponseBody
	FeedBackObject delete(String deploymentId) {
		actProcessService.deleteDeployment(deploymentId);
		FeedBackObject fbo=new FeedBackObject();
		fbo.setMsg(PromptMessageComm.MODEL_DELETE_SUCCESS);
		return fbo;
	}
	
	/**
	 * 删除流程实例
	 * @param procInsId 流程实例ID
	 * @param reason 删除原因
	 */
	@RequestMapping(value = "/process/deleteProcIns")
	public String deleteProcIns(String procInsId, String reason, RedirectAttributes redirectAttributes) {
		if (StrUtil.isBlank(reason)){
		}else{
			actProcessService.deleteProcIns(procInsId, reason);
		}
		return PromptMessageComm.REDIRECT_ACT_PROCESS_RUNNING;
	}
	
}
