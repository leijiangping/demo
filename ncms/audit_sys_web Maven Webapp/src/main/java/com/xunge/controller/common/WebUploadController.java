package com.xunge.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.model.FeedBackObject;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.UploadUtils;

/**
 * @author zhujj
 * @date 2017年8月15日 上午9:57:25 
 * @version 1.0.0 
 */

@RestController
@RequestMapping("/asserts/tpl/common/webupload")
public class WebUploadController extends BaseException {
	/**
	 * 上传文件
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/uploadFile")
	public @ResponseBody FeedBackObject uploader(String newName,String path,HttpServletRequest request){
		FeedBackObject feedbk = new FeedBackObject();
		UploadUtils upload=new UploadUtils();
		upload.setDirName(PromptMessageComm.UPLOAD_FILES);
		Map<String,Object> map=upload.uploadFile(request,newName,path);
		if(map!=null&&map.get(PromptMessageComm.UPLOAD_ERR)!=null&&!map.get(PromptMessageComm.UPLOAD_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)){//文件验证失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = map.get(PromptMessageComm.UPLOAD_ERR).toString();
			return feedbk;
		}
		if(map!=null&&map.get(PromptMessageComm.UPLOAD_SAVE_ERR)!=null&&!map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)){//文件上传保存失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString();

			return feedbk;
		}
		
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj=map;
		return feedbk;
	}
	/**
	 * 下载模板
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value="/downLoad")
	public void downLoad(String path,String name,HttpServletRequest request,HttpServletResponse response){
		FileUtils.download(path,name, request, response);
	}
}
