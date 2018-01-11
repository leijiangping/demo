package com.xunge.controller.activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.page.Page;
import com.xunge.model.FeedBackObject;
import com.xunge.service.activity.IActModelService;


/**
 * 流程模型相关Controller
 * @author zhujj
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "/asserts/tpl/system/activity")
public class ActModelController extends BaseException{

	@Autowired
	private IActModelService actModelService;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 流程模型列表
	 */
	@RequestMapping(value = { "modelList"}, method = RequestMethod.POST) 
	public @ResponseBody FeedBackObject modelList(int cur_page_num,int page_count,String category) {
		Page<org.activiti.engine.repository.Model> page = actModelService.modelList(
				new Page<org.activiti.engine.repository.Model>(cur_page_num, page_count), category);
	
		FeedBackObject backObj = new FeedBackObject();
		backObj.success=RESULT.SUCCESS_1;
		backObj.Obj=(page);
		return backObj;
	}

	/**
	 * 创建模型
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		System.out.println(PromptMessageComm.CREATE_GET);
		return PromptMessageComm.WAY_ACT_ACTMODELCREATE;
	}
	
	/**
	 * 创建模型
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ModelAndView create(String name, String key, String description, String category,String oper,
			HttpServletRequest request, HttpServletResponse response) {
		//ModelAndView mv=new ModelAndView("/asserts/tpl/system/activity/act/process-editor/modeler");
		ModelAndView mv=new ModelAndView(PromptMessageComm.WAY_MODELER_OPEN_WITH_BLANK);
		try {
			
			org.activiti.engine.repository.Model modelData = actModelService.create(name, key, description, category);
			
			mv.getModel().put("modelId", modelData.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(PromptMessageComm.CREATE_MODEL_FAIL, e);
		}
		return mv;
	}
	/**
	 * 创建模型
	 */
	@RequestMapping(value = "createByAjax", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject createByAjax(String name, String key, String description, String category,
			String oper,String modelId,HttpServletRequest request, HttpServletResponse response) {
		FeedBackObject backObject = new FeedBackObject();
		try {
			if(oper.equals(PromptMessageComm.INSERT)){
				org.activiti.engine.repository.Model modelData = actModelService.create(name, key, description, category);
				backObject.Obj = modelData.getId();
			}else if(oper.equals(PromptMessageComm.UPDATE)){
				org.activiti.engine.repository.Model modelData = actModelService.update(name, key, description, category,modelId);
				backObject.Obj = modelData.getId();
			}else if(oper.equals(PromptMessageComm.CLONE)){
				org.activiti.engine.repository.Model modelData = actModelService.insertCloneModel(name, key, description, category,modelId);
				backObject.Obj = modelData.getId();
			}

			backObject.success = RESULT.SUCCESS_1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(PromptMessageComm.CREATE_MODEL_FAIL, e);
			backObject.success = RESULT.FAIL_0;
		}
		return backObject;
	}
	/**
	 * 创建模型
	 */
	@RequestMapping(value = "cloneAct", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject cloneAct(String name, String key, String description, String category,String oper,
			HttpServletRequest request, HttpServletResponse response) {
		FeedBackObject backObject = new FeedBackObject();
		backObject.success = RESULT.SUCCESS_1;
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(PromptMessageComm.CREATE_MODEL_FAIL, e);
		}
		return backObject;
	}
	/**
	 * 根据Model部署流程
	 */
	@RequestMapping(value = "deploy", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject deploy( String id) {
		FeedBackObject fbo=new FeedBackObject();
		String message = actModelService.deploy(id);
		fbo.setMsg(message);
		return fbo;
	}
	
	/**
	 * 导出model的xml文件
	 */
	@RequestMapping(value = "export")
	public void export(@RequestParam("id") String id, HttpServletResponse response) {
		actModelService.export(id, response);
	}

	/**
	 * 更新Model分类
	 */
	@RequestMapping(value = "updateCategory", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateCategory( String id,  String category) {
		FeedBackObject fbo=new FeedBackObject();
		actModelService.updateCategory(id, category);
		fbo.setMsg(PromptMessageComm.SET_SUCCESS + id);
		return fbo;
	}
	
	/**
	 * 删除Model
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete")
	public @ResponseBody
	FeedBackObject delete(String modelId) {
		FeedBackObject fbo=new FeedBackObject();
		actModelService.delete(modelId);
		fbo.setMsg(PromptMessageComm.MODEL_DELETE_SUCCESS);
		return fbo;
	}
}
