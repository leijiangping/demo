package com.xunge.controller.selfelec.billamount;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.alibaba.fastjson.JSON;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.siemens.ct.its.util.encoding.Base64;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.selfelec.VEleBillamount;
import com.xunge.model.selfelec.VEleBillamountPayment;
import com.xunge.service.selfelec.billamount.IElecBillamountService;

/**
 * 自维电费报账汇总Controller
 * 
 * @author DanielYang
 *
 */
@Controller
@RequestMapping(value = "/asserts/tpl/selfelec/billamount")
public class BillamountController extends BaseException {

	@Autowired
	private IElecBillamountService elecBillamountService;

	@RequestMapping("/exportPdf")
	public void exportPdf(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		FeedBackObject feedbackObj = new FeedBackObject();
		
		
		
		String base64String = request.getParameter("imgUrl");
		String title = request.getParameter("title");
		String requestUrl = request.getRequestURL().toString();
		String url = requestUrl.substring(Integer.parseInt(SelfelecComm.NUMBER_0), requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
		//更改路径
		
		
		String path = request.getSession().getServletContext().getRealPath("/") + File.separator + PromptMessageComm.WAY_OF_WEB_INF_ASSERTS_FILES_DISK_TEMP;
		
		String filename = title + DateDisposeComm.FORMAT_PDF;
		String filePath = path+ File.separator+filename;
		System.out.println(filePath);
		String imagePath = path+ File.separator + DateDisposeComm.IMG_BMP;
		
		Document document = new Document();
		try {
//			Map getMap = request.getFileMap();
//			MultipartFile mfile = (MultipartFile) getMap.get("imgData"); // 获取数据
//			InputStream file = mfile.getInputStream();
//			byte[] fileByte = FileCopyUtils.copyToByteArray(file);
		
			base64String = base64String.replace(DateDisposeComm.BASE64STRING_REPLACE, "");
			byte[] fileByte = Base64.decode(base64String);
			
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(imagePath));// 打开输入流
			imageOutput.write(fileByte, Integer.parseInt(SelfelecComm.NUMBER_0), fileByte.length);// 生成本地图片文件
			imageOutput.close();
			
			
			PdfWriter.getInstance(document, new FileOutputStream(filePath)); // itextpdf文件
			// document.setPageSize(PageSize.A2);
			document.open();
			document.add(new Paragraph(title));
			Image image = Image.getInstance(imagePath); // itext-pdf-image
			float heigth = image.getHeight();
			float width = image.getWidth();
			int percent = getPercent2(heigth, width); // 按比例缩小图片
			image.setAlignment(Image.MIDDLE);
			image.scalePercent(percent + 3);
			document.add(image);
			document.close();
		
			//文件下载完删除
			FileUtils.downloadByFilePath(filePath, null, request, response);
			FileUtils.deleteFile(filePath);
			FileUtils.deleteFile(imagePath);
			
//			feedbackObj.success = RESULT.SUCCESS_1;
//			feedbackObj.msg = "导出成功：成功导出简报Pdf";
//			feedbackObj.Obj = url + "/asserts/files-disk/temp/" + filename;
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
//			feedbackObj.success = RESULT.FAIL_0;
//			feedbackObj.msg = "导出失败：服务器异常";
		}
//		response.getWriter().print(JSON.toJSONString(feedbackObj));
	}

	private static int getPercent2(float h, float w) {
		int p = Integer.parseInt(SelfelecComm.NUMBER_0);
		float p2 = 0.0f;
		p2 = 530 / w * 100;
		p = Math.round(p2);
		return p;
	}

	@RequestMapping(value = "/queryVEleBillamount", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryVEleBillamountPage(VEleBillamount obj, int pageNumber, int pageSize,
			HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;

		// 获取当前用户所属地区
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		feedbackObj.Obj = elecBillamountService.queryVEleBillamountPage(loginInfo, obj, pageNumber, pageSize);
		return feedbackObj;
	}

	@RequestMapping(value = "/queryVEleBillamountPayment", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryVEleBillamountPaymentPage(VEleBillamountPayment obj, int pageNumber, int pageSize,
			HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;

		// 获取当前用户所属地区
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		feedbackObj.Obj = elecBillamountService.queryVEleBillamountPaymentPage(loginInfo, obj, pageNumber, pageSize);
		return feedbackObj;
	}

	@RequestMapping(value = "/queryPaymentDetail", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryPaymentDetailPage(VEleBillamountPayment obj, int pageNumber, int pageSize,
			HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;

		// 获取当前用户所属地区
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		feedbackObj.Obj = elecBillamountService.queryEleBillamountdetailPage(loginInfo, obj, pageNumber, pageSize);
		return feedbackObj;
	}

	@RequestMapping(value = "/saveAmountInfo", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject saveAmountInfo(HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;

		String jsonStr = request.getParameter("params");
		UserLoginInfo userInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(userInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		List<String> ids = JSON.parseArray(jsonStr, String.class);

		feedbackObj.Obj = elecBillamountService.saveAmountInfo(ids,userInfo);
		return feedbackObj;
	}

	@RequestMapping(value = "/pushBillamount", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject pushBillamount(HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;

		String jsonStr = request.getParameter("params");

		// 获取当前用户所属地区
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}

		List<String> ids = JSON.parseArray(jsonStr, String.class);

		feedbackObj.msg = elecBillamountService.pushBillamount(loginInfo, ids);
		return feedbackObj;
	}

	/**
	 * 根据自维电费报账汇总Id查询自维电费报账汇总信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryElecBillamountById", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryElecBillamountById(String billamountId, HttpServletRequest request) {

		FeedBackObject feedbackObj = new FeedBackObject();

		feedbackObj.success = RESULT.SUCCESS_1;

		feedbackObj.msg = PromptMessageComm.SEARCH_SUCCESS;//"查询自维电费报账汇总信息成功";
		feedbackObj.Obj = elecBillamountService.queryVEleBillamountById(billamountId);

		return feedbackObj;
	}
	
	
	
	
	
	@RequestMapping("/exportPaymentDatil")
	public void exportPaymentDatil(HttpServletRequest request, HttpServletResponse response){
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		String billAmountId = request.getParameter("billamountId");
		List<VEleBillamountPayment> list = elecBillamountService.queryEleBillamountdetail(loginInfo, billAmountId);
		ExportParams params = new ExportParams(DateDisposeComm.PAY_DETAIL, DateDisposeComm.PAY_DETAIL, ExcelType.XSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, VEleBillamountPayment.class, list);
        FileUtils.downFile(workBook, DateDisposeComm.PAY_DETAIL_XLS, request, response);
	}
	
	
	
	//删除未推送汇总
	@RequestMapping(value = "/deleteBillamount", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject deleteBillamount(HttpServletRequest request) {
		String jsonStr = request.getParameter("params");
		List<String> ids = JSON.parseArray(jsonStr, String.class);
		int count = elecBillamountService.deleteBillamountById(ids);
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg = PromptMessageComm.BILL_DELETE_WORDS1 + count + PromptMessageComm.BILL_DELETE_WORDS2;
		return feedbackObj;
		
	}
		
}