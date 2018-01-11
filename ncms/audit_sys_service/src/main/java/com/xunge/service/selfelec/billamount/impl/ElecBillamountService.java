package com.xunge.service.selfelec.billamount.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.rent.warning.WarningComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.selfelec.EleBillaccountExpMapper;
import com.xunge.dao.selfelec.EleBillamountMapper;
import com.xunge.dao.selfelec.EleBillamountdetailMapper;
import com.xunge.dao.selfelec.ElePaymentdetailMapper;
import com.xunge.dao.selfelec.VDatElectricmeterExpMapper;
import com.xunge.dao.selfelec.VEleBillaccountPaymentInfoMapper;
import com.xunge.dao.selfelec.VEleBillaccountbaseresourceMapper;
import com.xunge.dao.selfelec.VEleBillamountMapper;
import com.xunge.dao.selfelec.VEleBillamountPaymentMapper;
import com.xunge.dao.selfelec.VEleContractMapper;
import com.xunge.dao.system.parameter.ISysParameterDao;
import com.xunge.model.elecbill.BillReportEntityVO;
import com.xunge.model.elecbill.BillReportVO;
import com.xunge.model.elecbill.ElecBillResult;
import com.xunge.model.selfelec.EleBillamount;
import com.xunge.model.selfelec.EleBillamountExample;
import com.xunge.model.selfelec.EleBillamountdetail;
import com.xunge.model.selfelec.EleBillamountdetailExample;
import com.xunge.model.selfelec.ElePaymentBenchmark;
import com.xunge.model.selfelec.VEleBillaccountPaymentInfo;
import com.xunge.model.selfelec.VEleBillaccountbaseresource;
import com.xunge.model.selfelec.VEleBillaccountbaseresourceExample;
import com.xunge.model.selfelec.VEleBillamount;
import com.xunge.model.selfelec.VEleBillamountExample;
import com.xunge.model.selfelec.VEleBillamountPayment;
import com.xunge.model.selfelec.VEleBillamountPaymentExample;
import com.xunge.model.selfelec.VEleContract;
import com.xunge.model.system.parameter.SysParameterVO;
import com.xunge.service.elecbill.report.IElecBillReport;
import com.xunge.service.selfelec.billamount.IElecBillamountService;
import com.xunge.service.selfelec.elecaudit.IElePaymentBenchmarkService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.util.CodeGeneratorUtil;

/**
 * 自维电费报账汇总Service
 * @author DanielYang
 *
 */
@Service
public class ElecBillamountService implements IElecBillamountService  {
	@Resource
	private EleBillamountMapper eleBillamountMapper;
	@Resource
	private EleBillamountdetailMapper eleBillamountdetailMapper;
	@Resource
	private EleBillaccountExpMapper eleBillaccountExpMapper;
	@Resource
	private VEleBillamountMapper vEleBillamountMapper;
	@Resource
	private VEleBillamountPaymentMapper vEleBillamountPaymentMapper;
	@Resource
	private VEleBillaccountPaymentInfoMapper vEleBillaccountPaymentInfoMapper;
	@Resource
	private ElePaymentdetailMapper elePaymentdetailMapper;
	
	@Resource
	private VDatElectricmeterExpMapper vDatElectricmeterExpMapper;
	@Autowired
	private IElecBillReport elecBillReportImpl;
	@Resource
	private IElePaymentBenchmarkService elePaymentBenchmarkService;
	@Resource
	private VEleBillaccountbaseresourceMapper vEleBillaccountbaseresourceMapper;
	

	@Resource
	private VEleContractMapper vEleContractMapper;
	

	@Autowired
	private ILogService log;
	
	// 各省份预警期
	@Resource
	private ISysParameterDao sysParameterDao;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 查询 汇总信息
	 * @param loginInfo
	 * @param obj
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillamount> queryVEleBillamountPage(UserLoginInfo loginInfo ,VEleBillamount obj,int pageNumber, int pageSize){
		PageHelper.startPage(pageNumber , pageSize);
		VEleBillamountExample example = new VEleBillamountExample();
		
		if(obj!=null){
			VEleBillamountExample.Criteria criteria= example.createCriteria();
			VEleBillamountExample.Criteria criteria2= example.createCriteria();
			
			if(StringUtils.isNoneBlank(obj.getBillamountCode())){
				criteria.andBillamountCodeLike("%" + obj.getBillamountCode()+"%");
				criteria2.andBillamountCodeLike("%" + obj.getBillamountCode()+"%");
			}
			
			if(StringUtils.isNoneBlank(obj.getPregId())){
				if(StringUtils.isNotBlank(obj.getRegId())){
					criteria.andRegIdEqualTo(obj.getRegId());
					criteria2.andRegIdEqualTo(obj.getRegId());
				}else{
					criteria.andPregIdEqualTo(obj.getPregId());
					criteria2.andPregIdEqualTo(obj.getPregId());
				}
			}
			
			if(obj.getBillamountDateStart()!=null && obj.getBillamountDateEnd()!=null){
				criteria.andBillamountDateBetween(obj.getBillamountDateStart(), obj.getBillamountDateEnd());
				criteria2.andBillamountDateBetween(obj.getBillamountDateStart(), obj.getBillamountDateEnd());
			}else if(obj.getBillamountDateStart()!=null){
				criteria.andBillamountDateGreaterThanOrEqualTo(obj.getBillamountDateStart());
				criteria2.andBillamountDateGreaterThanOrEqualTo(obj.getBillamountDateStart());
			}else if(obj.getBillamountDateEnd()!=null){
				criteria.andBillamountDateLessThanOrEqualTo(obj.getBillamountDateEnd());
				criteria2.andBillamountDateLessThanOrEqualTo(obj.getBillamountDateEnd());
			}
			
			if(obj.getBillamountState()!=null){
				criteria.andBillamountStateEqualTo(obj.getBillamountState());
				criteria2.andBillamountStateEqualTo(obj.getBillamountState());
			}
			
			if(StringUtils.isNoneBlank(obj.getContractCode())){
				criteria.andContractCodeLike("%" + obj.getContractCode()+"%");
				criteria2.andContractNameLike("%" + obj.getContractCode()+"%");
			}
			
			if(StringUtils.isNoneBlank(obj.getSupplierCode())){
				criteria.andSupplierCodeLike("%" + obj.getSupplierCode()+"%");
				criteria2.andSupplierNameLike("%" + obj.getSupplierCode()+"%");
			}
			
			if(StringUtils.isNoneBlank(obj.getBillaccountCode())){
				criteria.andBillaccountCodeLike("%" + obj.getBillaccountCode()+"%");
				criteria2.andBillaccountNameLike("%" + obj.getBillaccountCode()+"%");
			}
			
			if(loginInfo.getReg_ids() != null && loginInfo.getReg_ids().size() > Integer.parseInt(SelfelecComm.NUMBER_0)){
				criteria.andRegIdIn(loginInfo.getReg_ids());
				criteria2.andRegIdIn(loginInfo.getReg_ids());
			}
			example.or(criteria2);
		}
		
		
		List<VEleBillamount> datas = vEleBillamountMapper.selectByExample(example);
		
		return new PageInfo<VEleBillamount>(datas);
	}
	
	/**
	 * 查询 具体汇总详情列表
	 * @param loginInfo
	 * @param obj
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillamountPayment> queryEleBillamountdetailPage(UserLoginInfo loginInfo ,VEleBillamountPayment obj,int pageNumber, int pageSize){
		PageHelper.startPage(pageNumber , pageSize);
		VEleBillamountPaymentExample example = new VEleBillamountPaymentExample();
		
		if(obj!=null){
			VEleBillamountPaymentExample.Criteria criteria= example.createCriteria();
			
			if(StringUtils.isNoneBlank(obj.getBillamountId())){
				criteria.andBillamountIdEqualTo(obj.getBillamountId());
			}
		}
		
		
		List<VEleBillamountPayment> datas = vEleBillamountPaymentMapper.selectByExample(example);
		
		return new PageInfo<VEleBillamountPayment>(datas);
	}
	
	@Override
	public VEleBillamount queryVEleBillamountById(String billamountId) {
		VEleBillamount result = null;
		VEleBillamountExample example = new VEleBillamountExample();
		VEleBillamountExample.Criteria criteria = example.createCriteria();
		
		criteria.andBillamountIdEqualTo(billamountId);
		
		List<VEleBillamount> list = vEleBillamountMapper.selectByExample(example);
		if(list!=null && list.size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
			result = list.get(Integer.parseInt(SelfelecComm.NUMBER_0));
		}
		return result;
	}
	
	/**
	 * 查询 待汇总缴费信息
	 * @param loginInfo
	 * @param obj
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillamountPayment> queryVEleBillamountPaymentPage(UserLoginInfo loginInfo ,VEleBillamountPayment obj,int pageNumber, int pageSize){
		PageHelper.startPage(pageNumber , pageSize);
		VEleBillamountPaymentExample example = new VEleBillamountPaymentExample();

		VEleBillamountPaymentExample.Criteria criteria = example.createCriteria();
		if(obj!=null){
			if(loginInfo.getReg_ids() != null && loginInfo.getReg_ids().size() > Integer.parseInt(SelfelecComm.NUMBER_0)){
				criteria.andRegIdIn(loginInfo.getReg_ids());
			}
			if(StringUtils.isNoneBlank(obj.getSupplierId())){
				criteria.andSupplierIdEqualTo(obj.getSupplierId());
			}
			if(StringUtils.isNoneBlank(obj.getContractCode())){
				criteria.andContractCodeLike("%" + obj.getContractCode()+ "%");
			}
			if(obj.getAmountType()!=null){
				criteria.andAmountTypeEqualTo(obj.getAmountType());
			}
			if(obj.getPaymentMethod()!=null){
				criteria.andPaymentMethodEqualTo(obj.getPaymentMethod());
			}
			if(obj.getBillamountDate()!=null){
				criteria.andBillamountDateEqualTo(obj.getBillamountDate());
			}
			if(obj.getAuditingState()!=null){
				criteria.andAuditingStateEqualTo(obj.getAuditingState());
			}
			if(StringUtils.isNoneBlank(obj.getPregId())){
				if(StringUtils.isNotBlank(obj.getRegId())){
					criteria.andRegIdEqualTo(obj.getRegId());
				}else{
					criteria.andPregIdEqualTo(obj.getPregId());
				}
			}
		}
		criteria.andBillamountIdIsNull();//待汇总的汇总id为空
		List<VEleBillamountPayment> datas = vEleBillamountPaymentMapper.selectByExample(example);
		
		return new PageInfo<VEleBillamountPayment>(datas);
	}
	
	/**
	 * 推送汇总单
	 * @param loginInfo
	 * @param ids
	 * @return
	 */
	public String pushBillamount(UserLoginInfo loginInfo, List<String> ids){
		
		String result = "" ;
		
		EleBillamountExample example = new EleBillamountExample();
		
		EleBillamountExample.Criteria criteria = example.createCriteria();
		criteria.andBillamountIdIn(ids);
				
		List<EleBillamount> list = eleBillamountMapper.selectByExample(example);
		
		for(EleBillamount amount:list){
			logger.info(PromptMessageComm.READY_TO_PUSH1 + JSONObject.toJSONString(amount));
			
			if(amount.getBillamountState()==SelfelecComm.NUMBER_1){
				logger.info(PromptMessageComm.PUSHED_SUMMARY + amount.getBillamountCode());
				//已推送
				continue;
			}
			
			try{
				logger.info(PromptMessageComm.READY_TO_PUSH2+JSONObject.toJSONString(amount));
				//调用推送接口，然后更新推送状态
				BillReportVO report = new BillReportVO();
				
				//TODO 补充 推送内容
				String sNote=amount.getSupplierName()+"-"+DateFormatUtils.format(amount.getBillamountDate(), SelfelecComm.FORMAT_yyyyMMdd) + PromptMessageComm.PAYMENT;
				report.setAbstract_content(sNote);
				//含税金额
				report.setApply_amount(amount.getBillamountWithtax());
				//汇总单号
				report.setCollect_num(amount.getBillamountCode());
				
				VEleContract ec=vEleContractMapper.queryOneElecContractById(amount.getElecontractId());
				if(ec!=null&&ec.getContractCode()!=null){
					report.setContract_code(ec.getContractCode());
				}
				//不含税金额
				report.setDocument_amount(amount.getBillamountNotax());
				Map<String,Object> param = new HashMap<>();
				param.put("billamount_id", amount.getBillamountId());
				report.setEntity_expend(new ArrayList<BillReportEntityVO>());
				report.setPayment_type(SelfelecComm.NUMBER_1);
				//备注
				report.setRemark(sNote);
//				report.setSystem_id(system_id);
				// 税金
				report.setTax_amount(amount.getBillamountTaxamount());
				//传动当前点击生成汇总单的人员
				report.setUser_name(amount.getUserName());//生成汇总单人员信息amount.getUserName()
				//传动当前点击生成汇总单的人员工号
				report.setUser_number(amount.getUserNumber());//工号
				
				report.setVendor_code(amount.getSupplierCode());
				report.setVendor_name(amount.getSupplierName());
				//如果地址为空，则默认传“无”
				report.setVendor_site(StrUtil.isNotBlank(amount.getSupplierAddress())?amount.getSupplierAddress() : PromptMessageComm.SUPPLIER_SERVICE_CATEGORY);
				
				/*
				 * 报账行实体
				 */
				EleBillamountdetailExample exampleDetail = new EleBillamountdetailExample();
				EleBillamountdetailExample.Criteria criteriaDetail = exampleDetail.createCriteria();
				criteriaDetail.andBillamountIdEqualTo(amount.getBillamountId());
						
				List<EleBillamountdetail> listDetail = eleBillamountdetailMapper.selectByExample(exampleDetail);
				List<BillReportEntityVO> entity_expend=Lists.newArrayList();
				for(EleBillamountdetail amountDetail:listDetail){
					BillReportEntityVO reportEntity=new BillReportEntityVO();
					reportEntity.setPrice(amountDetail.getBillamountPrice());//单价
					reportEntity.setCount(amountDetail.getBillamountNumber()); //数量
					reportEntity.setTax_rate(amountDetail.getBillamountTaxratio()==BigDecimal.ZERO?BigDecimal.ZERO:(amountDetail.getBillamountTaxratio().divide(new BigDecimal(Double.toString(100.00d))))); //税率
					reportEntity.setLine_amount(amountDetail.getBillamountWithtax()); //报账金额--含税金额
					reportEntity.setDocument_line_amount(amountDetail.getBillamountNotax());//价款--不含税金额
					reportEntity.setTax_line_amount(amountDetail.getBillamountTaxamount()); //税额
					
					VEleBillamountPaymentExample examplePayment = new VEleBillamountPaymentExample();
					VEleBillamountPaymentExample.Criteria criteriaPayment = examplePayment.createCriteria();
					criteriaPayment.andBillamountDetailIdEqualTo(amountDetail.getBillamountdetailId());
					
					List<VEleBillamountPayment> list3 = vEleBillamountPaymentMapper.selectByExample(examplePayment);
					JSONArray checkResult = new JSONArray();
					if(list3.size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
						VEleBillamountPayment vebp=list3.get(Integer.parseInt(SelfelecComm.NUMBER_0));
						checkResult=this.showBenchmark(vebp.getBillaccountpaymentdetailId());
//						checkResult=this.getBenchmark(vebp.getTotalDegreeActual()!=null?vebp.getTotalDegreeActual().toString():"0",vebp.getBillamountStartdate(),vebp.getBillamountEnddate(),vebp.getBillaccountId());
						//关联机房
						VEleBillaccountbaseresourceExample exampleBB = new VEleBillaccountbaseresourceExample();
						com.xunge.model.selfelec.VEleBillaccountbaseresourceExample.Criteria criteriaBB = exampleBB.createCriteria();
						if(param!=null && StringUtils.isNoneBlank(vebp.getBillaccountId())){
							criteriaBB.andBillaccountIdEqualTo(vebp.getBillaccountId());
						}
						criteriaBB.andRelationStateEqualTo(Integer.parseInt(SelfelecComm.NUMBER_0));
//						criteria.andRelationEnddateIsNull();
						
						List<VEleBillaccountbaseresource> datas = vEleBillaccountbaseresourceMapper.selectByExample(exampleBB);
						if(datas!=null&&datas.size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
							String belongRoom=this.getBelongRoom(datas);
							reportEntity.setBelong_room(belongRoom);//明细所属机房/资源点/位置点信息
						}
					}
					
					//TODO 补充 稽核结果
					reportEntity.setCheck_result(checkResult!=null&&checkResult.size()>0?checkResult.toJSONString():PromptMessageComm.NO_BENCHMARK);//稽核结果
					
					
					
					reportEntity.setAmount_date_begin(DateUtil.formatDate(amountDetail.getBillamountStartdate()));//缴费起始
					reportEntity.setAmount_date_end(DateUtil.formatDate(amountDetail.getBillamountEnddate()));//缴费期终
					entity_expend.add(reportEntity);
				}
				
				report.setEntity_expend(entity_expend);
				String json_report = JSONObject.toJSONString(report);
				logger.info(PromptMessageComm.START_PUSHING_THE_BILLING_INTERFACE + json_report);

				JSONObject billReportVO = JSONObject.parseObject(json_report);
				ElecBillResult reportResult = elecBillReportImpl.reportToBillSystem(loginInfo.getPrv_code(),billReportVO);
				logger.info(PromptMessageComm.PUSH_COMPLETION1+JSONObject.toJSONString(reportResult));
				result=reportResult.toString();
				// 添加系统日志
				log.info(SysLogComm.LOG_Operate, PromptMessageComm.PUSH_COMPLETION2 + reportResult.getIs_succ());
				if(!PromptMessageComm.SUCCESS_NO_STR.equals(reportResult.getIs_succ())){
					//推送成功，则 更新 0为已推送状态
					amount.setBillamountState(Integer.parseInt(SelfelecComm.NUMBER_0));
					result += eleBillamountMapper.updateByPrimaryKey(amount);
				}
			}catch(Exception e){
				result = PromptMessageComm.NETWORK_INTERFACE_EXCEPTION;
				e.printStackTrace();
			}
		}
		
		return result;
	}
	private String getBelongRoom(List<VEleBillaccountbaseresource> datas){
		String s="";
		for(VEleBillaccountbaseresource vebb:datas){
			s+=vebb.getBaseresourceCuid()+"+"+vebb.getBaseresourceName()+"|";
		}
		return s.substring(Integer.parseInt(SelfelecComm.NUMBER_0), s.length()-SelfelecComm.NUMBER_1);
	}
	/**
	 * 查询标杆信息
	 * @param totalDegreeActualStr
	 * @param billamountStartdateStr
	 * @param billamountEnddateStr
	 * @param billaccountId
	 * @return
	 * @throws Exception
	 * @author xup
	 */
	@SuppressWarnings("unused")
	private JSONArray getBenchmark(String totalDegreeActualStr,Date billamountStartdateStr,Date billamountEnddateStr,String billaccountId) throws Exception{
			SimpleDateFormat sdf = new SimpleDateFormat(SelfelecComm.FORMAT_yyyyMMdd);
			Double totalDegreeActual = Double.parseDouble(totalDegreeActualStr);
			Map<String, Object> param = new HashMap<>();
			param.put("billaccountId", billaccountId);
			param.put("startdate", (billamountStartdateStr));
			param.put("enddate", (billamountEnddateStr));
			vDatElectricmeterExpMapper.getBenchmark(param);
			Map<String, Object> resultMap = param;
			Object errCodeObj = resultMap.get("errCode");
			JSONArray arr=new JSONArray();
			if (errCodeObj != null && (int) errCodeObj == Integer.parseInt(SelfelecComm.NUMBER_0)) {
				NumberFormat nFormat = NumberFormat.getNumberInstance();
				nFormat.setMaximumFractionDigits(SelfelecComm.NUMBER_2);// 设置小数点后面位数为

				// 智能电表标杆
				BigDecimal electricmeterBenchmark = (BigDecimal) resultMap.get("electricmeterBenchmark");
				// 动环负载标杆
				BigDecimal powerloadBenchmark = (BigDecimal) resultMap.get("powerloadBenchmark");
				// 额定功率标杆
				BigDecimal powerratingBenchmark = (BigDecimal) resultMap.get("powerratingBenchmark");
				// 环比历史电量标杆
				BigDecimal hisNowBenchmark = (BigDecimal) resultMap.get("hisNowBenchmark");
				// 同比历史电量标杆
				BigDecimal hisLastBenchmark = (BigDecimal) resultMap.get("hisLastBenchmark");

				// 同比历史电量标杆
				 if (hisLastBenchmark != null) {
					 JSONObject billReportVO =new JSONObject();
					 if (totalDegreeActual > hisLastBenchmark.doubleValue()) {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,PromptMessageComm.HISTORICAL_BENCHMARK_YEAR_ON_YEAR);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);

						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,nFormat.format((totalDegreeActual - hisLastBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
								/ hisLastBenchmark.doubleValue()));
					} else {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,PromptMessageComm.HISTORICAL_BENCHMARK_YEAR_ON_YEAR);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,PromptMessageComm.ZERO_PERCENT);
					}
					 arr.add(billReportVO);
				}

				// 环比历史电量标杆
				if (hisNowBenchmark != null){
					 JSONObject billReportVO =new JSONObject();
					 if (totalDegreeActual > hisNowBenchmark.doubleValue()) {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,PromptMessageComm.HISTORICAL_BENCHMARK_RING);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);

						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,nFormat.format((totalDegreeActual - hisNowBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
								/ hisNowBenchmark.doubleValue()));
					} else {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,PromptMessageComm.HISTORICAL_BENCHMARK_YEAR_ON_YEAR);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,PromptMessageComm.ZERO_PERCENT);
					}
					 arr.add(billReportVO);
				}

				// 额定功率标杆
				if (powerratingBenchmark != null)  {
					 JSONObject billReportVO =new JSONObject();
					 if (totalDegreeActual > powerratingBenchmark.doubleValue()) {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,DateDisposeComm.RATED_POWER_BENCHMARK);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);
						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE, nFormat.format((totalDegreeActual - powerratingBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
								/ powerratingBenchmark.doubleValue()));
					} else {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,DateDisposeComm.RATED_POWER_BENCHMARK);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,PromptMessageComm.ZERO_PERCENT);
					}
					 arr.add(billReportVO);
				}

				// 智能电表标杆
				 if (electricmeterBenchmark != null) {
					 JSONObject billReportVO =new JSONObject();
					 if (totalDegreeActual > electricmeterBenchmark.doubleValue()) {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,DateDisposeComm.SMARTMETER_BENCHMARK);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);
						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE, nFormat.format((totalDegreeActual - electricmeterBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
								/ electricmeterBenchmark.doubleValue()));
					} else {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,DateDisposeComm.SMARTMETER_BENCHMARK);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,PromptMessageComm.ZERO_PERCENT);
					}
					 arr.add(billReportVO);
				}

				// 动环负载标杆
				 if (powerloadBenchmark != null) {
					 JSONObject billReportVO =new JSONObject();
					 if (totalDegreeActual > powerloadBenchmark.doubleValue()) {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,DateDisposeComm.MOVABLE_RING_LOAD_BENCHMARK);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);
						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE, nFormat.format((totalDegreeActual - powerloadBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
								/ powerloadBenchmark.doubleValue()));
					} else {
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,DateDisposeComm.MOVABLE_RING_LOAD_BENCHMARK);
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,PromptMessageComm.ZERO_PERCENT);
					}
					 arr.add(billReportVO);
				}
			}
			return arr;
	}
	@SuppressWarnings("unused")
	private JSONArray showBenchmark(String billaccountpaymentdetailId) {
		Map<String, Object> param = new HashMap<>();
		param.put("billaccountpaymentdetailId", billaccountpaymentdetailId);
		List<ElePaymentBenchmark> elePaymentBenchmark = elePaymentBenchmarkService.queryAllByForginKey(param);
		JSONArray arr=new JSONArray();
		if (elePaymentBenchmark != null && elePaymentBenchmark.size()> Integer.parseInt(SelfelecComm.NUMBER_0)) {
			for(int i=Integer.parseInt(SelfelecComm.NUMBER_0);i<elePaymentBenchmark.size();i++){
				// 同比历史电量标杆
				if (elePaymentBenchmark.get(i).getPaybenchmarkType() == Integer.parseInt(SelfelecComm.NUMBER_0)) {
					JSONObject billReportVO =new JSONObject();
					billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,PromptMessageComm.HISTORICAL_BENCHMARK_YEAR_ON_YEAR);
					if(elePaymentBenchmark.get(i).getPaybenchmarkRate()==Integer.parseInt(SelfelecComm.NUMBER_0)){
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
					}else{
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);
					}
					billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,elePaymentBenchmark.get(i).getPaybenchmarkRate());
					arr.add(billReportVO);
				}
				// 环比历史电量标杆
				if (elePaymentBenchmark.get(i).getPaybenchmarkType() == SelfelecComm.NUMBER_1){
					JSONObject billReportVO =new JSONObject();
						billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,PromptMessageComm.HISTORICAL_BENCHMARK_RING);
						if(elePaymentBenchmark.get(i).getPaybenchmarkRate()==Integer.parseInt(SelfelecComm.NUMBER_0)){
							billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
						}else{
							billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);
						}
						billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,elePaymentBenchmark.get(i).getPaybenchmarkRate());
						arr.add(billReportVO);
				}
				// 额定功率标杆
				if (elePaymentBenchmark.get(i).getPaybenchmarkType() == SelfelecComm.NUMBER_2)  {
					JSONObject billReportVO =new JSONObject();
					billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,DateDisposeComm.RATED_POWER_BENCHMARK);
					if (elePaymentBenchmark.get(i).getPaybenchmarkRate()==Integer.parseInt(SelfelecComm.NUMBER_0)) {
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
					} else {
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);
					}
					billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,elePaymentBenchmark.get(i).getPaybenchmarkRate());
					arr.add(billReportVO);
				}
				
				// 智能电表标杆
				if (elePaymentBenchmark.get(i).getPaybenchmarkType() == SelfelecComm.NUMBER_3) {
					JSONObject billReportVO =new JSONObject();
					billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,DateDisposeComm.SMARTMETER_BENCHMARK);
					if (elePaymentBenchmark.get(i).getPaybenchmarkRate()==Integer.parseInt(SelfelecComm.NUMBER_0)) {
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
					} else {
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);
					}
					billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,elePaymentBenchmark.get(i).getPaybenchmarkRate());
					arr.add(billReportVO);
				}
				
				
				// 动环负载标杆
				if (elePaymentBenchmark.get(i).getPaybenchmarkType() == SelfelecComm.NUMBER_4) {
					JSONObject billReportVO =new JSONObject();
					billReportVO.put(PromptMessageComm.BENCHMARKING_TYPE,DateDisposeComm.MOVABLE_RING_LOAD_BENCHMARK);
					if (elePaymentBenchmark.get(i).getPaybenchmarkRate()==Integer.parseInt(SelfelecComm.NUMBER_0)) {
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD,PromptMessageComm.NO);
					} else {
						billReportVO.put(PromptMessageComm.IF_OVER_STANDARD, PromptMessageComm.YES);
					}
					billReportVO.put(PromptMessageComm.OVER_STANDARD_RATE,elePaymentBenchmark.get(i).getPaybenchmarkRate());
					arr.add(billReportVO);
				}
			}
		}
		return arr;
}
	
	/**
	 * 生成汇总单
	 * @param ids
	 * @return
	 */
	public String saveAmountInfo(List<String> ids,UserLoginInfo userInfo){
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		VEleBillamountPaymentExample example = new VEleBillamountPaymentExample();
		VEleBillamountPaymentExample.Criteria criteria = example.createCriteria();
		
		criteria.andBillaccountpaymentdetailIdIn(ids);
		
		List<VEleBillamountPayment> list = vEleBillamountPaymentMapper.selectByExample(example);
		
		EleBillamount eleBillamount=new EleBillamount();
		String billamountId=SysUUID.generator();
		BigDecimal billamountNotax= new BigDecimal(Integer.parseInt(SelfelecComm.NUMBER_0));
		BigDecimal billamountWithtax= new BigDecimal(Integer.parseInt(SelfelecComm.NUMBER_0));
		BigDecimal billamountTaxamount= new BigDecimal(Integer.parseInt(SelfelecComm.NUMBER_0));
		
		int index = Integer.parseInt(SelfelecComm.NUMBER_0);
		
		List<String> paymentIds = new ArrayList<String>();
		
		for(VEleBillamountPayment payment:list){
			EleBillamountdetail detail = new EleBillamountdetail();
			
			detail.setBillamountdetailId(SysUUID.generator());
			detail.setBillamountId(billamountId);
			detail.setBillamountEnddate(payment.getBillamountEnddate());
			detail.setBillamountStartdate(payment.getBillamountStartdate());
			detail.setBillamountNotax(payment.getBillamountNotax());
			if(payment.getDetailCnt()!=null){
				detail.setBillamountNumber(payment.getTotalDegreeActual());
			}
			
			detail.setBillamountPrice(payment.getBillamountPrice());
			detail.setBillamountTaxamount(payment.getBillamountTaxamount());
			detail.setBillamountTaxratio(payment.getTaxRate());
			detail.setBillamountWithtax(payment.getBillAmountActual());
			detail.setBillaccountpaymentdetailId(payment.getBillaccountpaymentdetailId());
//			UserLoginInfo userInfo=(UserLoginInfo)map.get("userInfo");
//			EleBillamount.setUserNumber(userInfo.getUser_code());//报账工号
//			EleBillamount.setUserName(userInfo.getUser_loginname());//报账用户名
			eleBillamount.addEleBillamountdetails(detail);//保存字表数据
			
			if(index == Integer.parseInt(SelfelecComm.NUMBER_0)){
				eleBillamount.setElecontractId(payment.getElecontractId());
				eleBillamount.setSupplierId(payment.getSupplierId());
				//通过触发器触发 填写其他供应商信息
//				eleBillamount.setSupplierCode(payment.getSupplierCode());
//				eleBillamount.setSupplierName(payment.getSupplierName());
//				eleBillamount.setSupplierAddress(payment.getsu);
//				eleBillamount.setSupplierContact(payment.getContractCode());
//				eleBillamount.setSupplierTelephone(payment.getSupplierTelephone());
				
				eleBillamount.setBankUser(payment.getBankUser());
				eleBillamount.setBankAccount(payment.getBankAccount());
				eleBillamount.setDepositBank(payment.getDepositBank());
			}
			if(null!=payment.getBillamountNotax()){//不含税金额 BigDecimal判断不等于空
				billamountNotax=billamountNotax.add(payment.getBillamountNotax());
			}	
			if(null!=payment.getBillAmountActual()){//实际报账金额 BigDecimal判断不等于空
				billamountWithtax=billamountWithtax.add(payment.getBillAmountActual());
			}
			if(null!=payment.getBillamountTaxamount()){//税额 BigDecimal判断不等于空
				billamountTaxamount=billamountTaxamount.add(payment.getBillamountTaxamount());
			}
			paymentIds.add(payment.getBillaccountpaymentdetailId());
			
			index++;
		}
		
		eleBillamount.setUserName(userInfo.getUser_loginname());
		eleBillamount.setUserNumber(userInfo.getUser_code());

		eleBillamount.setBillamountNotax(billamountNotax);
		//apply_amount
		eleBillamount.setBillamountWithtax(billamountWithtax);
		eleBillamount.setBillamountTaxamount(billamountTaxamount);
		
		eleBillamount.setBillamountState(StateComm.STATE__1);//报账状态-1：未推送0：已推送
		eleBillamount.setBillamountNote("");//汇总摘要
		eleBillamount.setBillamountId(billamountId);
		
		eleBillamount.setBillamountCode(CodeGeneratorUtil.BillElecAmountCodeGet(userInfo.getPrv_code()));//自动生成code
		eleBillamount.setBillamountDate(new Date());
			result += eleBillamountMapper.insert(eleBillamount);
		for(EleBillamountdetail detail:eleBillamount.getEleBillamountdetails()){
			result += eleBillamountdetailMapper.insert(detail);

			//更新 缴费表 汇总id字段
			Map<String,Object> map = new HashMap<String, Object>();
			List<String> lists=Lists.newArrayList();
			lists.add(detail.getBillaccountpaymentdetailId());
			map.put("paymentIds", paymentIds);
			map.put("billamountId", billamountId);
			map.put("billamountDetailId", detail.getBillamountdetailId());
			result +=eleBillaccountExpMapper.updateBillamountIdByPaymentId(map);
		}
		
		return billamountId;
	}
	
	public PageInfo<EleBillamount> queryElecBillamountPage(UserLoginInfo loginInfo ,EleBillamount obj,int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber , pageSize);
		EleBillamountExample example = new EleBillamountExample();
		
		List<EleBillamount> datas = eleBillamountMapper.selectByExample(example);
		
		return new PageInfo<EleBillamount>(datas);
	}
	public EleBillamount queryElecBillamountById(EleBillamount obj) {
		return eleBillamountMapper.selectByPrimaryKey(obj.getBillamountId());
	}
	
	public List<VEleBillamountPayment> queryEleBillamountdetail(UserLoginInfo loginInfo ,String billAmountId){
		VEleBillamountPaymentExample example = new VEleBillamountPaymentExample();
		VEleBillamountPaymentExample.Criteria criteria= example.createCriteria();
		if(StringUtils.isNoneBlank(billAmountId)){
			criteria.andBillamountIdEqualTo(billAmountId);
		}									
		List<VEleBillamountPayment> datas = vEleBillamountPaymentMapper.selectByExample(example);
		return datas;
	}

	@Override
	@Transactional
	public int deleteBillamountById(List<String> ids) {
		vEleBillamountPaymentMapper.updateBillamountidIsNull(ids);//更新报账点缴费，汇总单编码置空
		eleBillamountdetailMapper.deleteByBillamountIds(ids);//删除汇总缴费明细
		int count = eleBillamountMapper.deleteByPrimaryKeys(ids);//删除汇总缴费
		return count;
	}

	@Override
	public Map<String,Object> queryPushedBillLv(Map<String, Object> map) {
		int pushedBill = eleBillamountMapper.selectPushedBill(map);//已关联资源数
		int allres = eleBillamountMapper.selectPushBackBill(map);//所有资源数
		Map<String, Object> resmap = new HashMap<>();
		resmap.put("pushedBill", pushedBill);
		resmap.put("allpushbill", allres);
		return resmap;
	}
	
	
	/**
     * @description 查询报账点及报账点缴费详细信息
     * @author yuefy
     * @date 创建时间：2017年9月21日
     */
	public List<VEleBillaccountPaymentInfo> selectBillamountPaymentDetails(Map<String,Object> paramMap){
		//周期可用
		paramMap.put("paymentState", StateComm.STATE_1);
		String paraCode = WarningComm.ELEC_ALERT;
		paramMap.put("paraCode", paraCode);
		// 当前省份合同预警期
		// 各省份租费 电费 合同 预警期 常量值
		SysParameterVO parameter = sysParameterDao.queryParameter(paramMap);
		String warningDate = "";
		if(parameter != null){
			warningDate = parameter.getParaValue();
		}
		if(paramMap.get("exportFlag") == null){
			Integer pageNumber = Integer.parseInt((String) paramMap.get("pageNumber"));
			Integer pageSize = Integer.parseInt((String) paramMap.get("pageSize"));
			PageHelper.startPage(pageNumber, pageSize);
		}
		paramMap.put("warningDate", warningDate);
		paramMap.put("nowDateTime", new Date());
		paramMap.put("paymentState", StateComm.STATE_1);
		return vEleBillaccountPaymentInfoMapper.selectBillamountPaymentDetails(paramMap);
	};

}