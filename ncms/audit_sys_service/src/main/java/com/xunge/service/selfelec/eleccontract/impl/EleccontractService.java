package com.xunge.service.selfelec.eleccontract.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xunge.comm.ContractCodeComm;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.basedata.collection.ContractComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.enums.ResourceEnum.AuditStateEnum;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.basedata.DatAttachmentMapper;
import com.xunge.dao.basedata.DatContractVOMapper;
import com.xunge.dao.selfelec.EleContractMapper;
import com.xunge.dao.selfelec.VEleContractMapper;
import com.xunge.dao.selfrent.contract.IDatPaymentPeriodDao;
import com.xunge.dao.system.parameter.ISysParameterDao;
import com.xunge.model.activity.Act;
import com.xunge.model.basedata.DatAttachment;
import com.xunge.model.basedata.DatAttachmentExample;
import com.xunge.model.basedata.DatContractVO;
import com.xunge.model.selfelec.EleContract;
import com.xunge.model.selfelec.EleContractExample;
import com.xunge.model.selfelec.VEleContract;
import com.xunge.model.selfelec.VEleContractCuring;
import com.xunge.model.selfelec.VEleContractExample;
import com.xunge.model.selfelec.VEleContractExample.Criteria;
import com.xunge.model.selfelec.vo.ElecContractQueryVO;
import com.xunge.model.system.parameter.SysParameterVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.basedata.util.ExcelExportHelper;
import com.xunge.service.selfelec.billaccount.IElecBillAccountService;
import com.xunge.service.selfelec.eleccontract.IEleccontractService;
import com.xunge.service.system.role.ISysRoleService;

@Service
public class EleccontractService implements IEleccontractService {

	@Resource
	private EleContractMapper eleContractMapper;
	@Resource
	private DatContractVOMapper datContractVOMapper;
	@Resource
	private DatAttachmentMapper datAttachmentMapper;
	@Resource
	private VEleContractMapper vEleContractMapper;
	@Autowired
	private IActTaskService actTaskService;
	@Autowired
	private ISysRoleService iSysRoleService;
	// 各省份预警期
	@Resource
	private ISysParameterDao sysParameterDao;
	// 缴费周期
	@Resource
	private IDatPaymentPeriodDao datPaymentPeriodDao;
	
	@Autowired
	private IElecBillAccountService elecReimburseInfoService;

	@Override
	public int add(VEleContract record,UserLoginInfo loginUser) {
		int back = SelfelecComm.NUMBER_1;
		// 添加主合同信息
		DatContractVO contract = new DatContractVO();
		String contractCode = record.getContractCode();
		if(StrUtil.isEmpty(contractCode)){
			contractCode = elecReimburseInfoService.queryMaxCode(loginUser,ContractCodeComm.ZDGH_NAME ,ContractCodeComm.ZDGH);
		}
		BeanUtils.copyProperties(record, contract);
		// 填充属性
		contract.setAuditingState(AuditStateEnum.PREAUDIT);
		contract.setOldContractId(contract.getContractId());
		contract.setContractCode(contractCode);
		contract.setContractYearquantity(mathYearquantity(
				contract.getContractStartdate(), contract.getContractEnddate()));
		back = datContractVOMapper.insert(contract);
		if (back != SelfelecComm.NUMBER_1) {
			return back;
		}
		// 添加附件信息
		DatAttachment attachment = new DatAttachment();
		String attachmentId = PromptMessageComm.ATTACHMENT_ID + System.currentTimeMillis();
		attachment.setAttachmentId(attachmentId);
		attachment.setBusinessId(record.getElecontractId());
		back = datAttachmentMapper.insert(attachment);
		if (back != SelfelecComm.NUMBER_1) {
			datContractVOMapper.deleteByPrimaryKey(contract.getContractId());
			return back;
		}
		// 添加电费合同
		EleContract eleContract = new EleContract();
		BeanUtils.copyProperties(record, eleContract);
		back = eleContractMapper.insert(eleContract);
		if (back != SelfelecComm.NUMBER_1) {
			datContractVOMapper.deleteByPrimaryKey(contract.getContractId());
			datAttachmentMapper.deleteByPrimaryKey(attachmentId);
		}
		return back;
	}

	@Override
	public int modify(VEleContract record) {
		int back = SelfelecComm.NUMBER_1;
		// 修改主合同信息
		DatContractVO contract = new DatContractVO();
		BeanUtils.copyProperties(record, contract);
		if (StringUtils.isEmpty(contract.getOldContractId())) {
			contract.setOldContractId(contract.getContractId());
		}
		contract.setAuditingState(ActivityStateComm.STATE_UNCOMMITTED);
		contract.setContractYearquantity(mathYearquantity(
				contract.getContractStartdate(), contract.getContractEnddate()));
		back = datContractVOMapper.updateByPrimaryKey(contract);
		if (back != SelfelecComm.NUMBER_1) {
			return back;
		}
		// 添加电费合同
		EleContract eleContract = new EleContract();
		BeanUtils.copyProperties(record, eleContract);
		back = eleContractMapper.updateByPrimaryKey(eleContract);
		return back;
	}

	@Override
	public int remove(VEleContract elecontract) {
		int stat = Integer.parseInt(SelfelecComm.NUMBER_0);
		// 删除电费合同
		EleContractExample eleExample = new EleContractExample();
		com.xunge.model.selfelec.EleContractExample.Criteria criteria = eleExample
				.createCriteria();
		criteria.andElecontractIdEqualTo(elecontract.getElecontractId());
		stat = eleContractMapper.deleteByExample(eleExample);
		if (stat != SelfelecComm.NUMBER_1) {
			return stat;
		}
		// 删除主合同
		stat = datContractVOMapper.deleteByPrimaryKey(elecontract
				.getContractId());
		if (stat != SelfelecComm.NUMBER_1) {
			return stat;
		}
		// 删除附件
		DatAttachmentExample attExample = new DatAttachmentExample();
		com.xunge.model.basedata.DatAttachmentExample.Criteria attCriteria = attExample
				.createCriteria();
		attCriteria.andBusinessIdEqualTo(elecontract.getElecontractId());
		stat = datAttachmentMapper.deleteByExample(attExample);
		if (stat != SelfelecComm.NUMBER_1) {
			return stat;
		}
		return stat;
	}

	@Override
	public int submitAudit(List<Map<String, Object>> list,
			UserLoginInfo loginUser) {
		for (Map<String, Object> map : list) {
			DatContractVO contract = datContractVOMapper.selectByPrimaryKey(map
					.get("id").toString());
			if (contract != null
					&& contract.getAuditingState() == ActivityStateComm.STATE_UNCOMMITTED) {
				actTaskService.startProcess(ActUtils.PD_SELFELEC_AUDIT[0],
						loginUser.getPrv_code(), ActUtils.PD_SELFELEC_AUDIT[1],
						map.get("id").toString(),
						ActUtils.PD_SELFELEC_AUDIT[2], map,
						loginUser.getUser_loginname());
				contract.setAuditingState(AuditStateEnum.AUDITING);
				datContractVOMapper.updateByPrimaryKey(contract);
			}
		}
		return list.size();
	}

	@Override
	public int audit(List<Map<String, Object>> list, UserLoginInfo loginInfo) {
		for (Map<String, Object> map : list) {
			String taskid = map.get("taskId").toString();
			Task t = actTaskService.getTask(taskid);
			DatContractVO contract = datContractVOMapper.selectByPrimaryKey(map
					.get("id").toString());
			// 增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if (t != null && contract != null) {
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NAME,
						map.get("auditState").toString());
				if (StrUtil.isNotBlank(map.get("nextAuditUserId").toString())) {
					vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NEXTUSER, map
							.get("nextAuditUserId").toString());// 指定下一环节审核人
				}
				if (map.get("maxPrice")!=null&&StrUtil.isNotBlank(map.get("maxPrice").toString())) {
					vars.put("maxPrice", map.get("maxPrice").toString());// 指定最大电费单价
				}
				String user_loginname = loginInfo.getUser_loginname();
				if (StrUtil.isNotBlank(user_loginname)) {
					vars.put(ActivityStateComm.ACTIVITY_VARIABLE_CURRUSER,
							user_loginname);// 指定当前审核人
				}
				actTaskService.completeByBusiness(
						ActUtils.PD_SELFELEC_AUDIT[1],
						contract.getContractId(), map.get("auditNote")
								.toString(), ActUtils.PD_SELFELEC_AUDIT[2],
						vars);
				Task newtask = actTaskService
						.getTask(ActUtils.PD_SELFELEC_AUDIT[1],
								contract.getContractId());

				if (newtask == null) {
					contract.setAuditingDate(new Date());
					contract.setAuditingUserId(loginInfo.getUser_id());
					if (map.get("auditState") != null
							&& map.get("auditState")
									.toString()
									.equals(ActivityStateComm.STATE_UNAPPROVE
											+ "")) {
						contract.setAuditingState(AuditStateEnum.AUDITFAIL);
					} else if (map.get("auditState") != null
							&& map.get("auditState")
									.toString()
									.equals(ActivityStateComm.STATE_NORMAL + "")) {
						contract.setAuditingState(AuditStateEnum.AUDITSUCC);
					}
					int i = datContractVOMapper.updateByPrimaryKey(contract);
				}
			}
		}
		return list.size();
	}

	public String export(ElecContractQueryVO vo, String path,
			HttpServletRequest request, HttpServletResponse response,
			UserLoginInfo loginUser, Integer activityFlag) {
		String fileName = DateDisposeComm.ELEC_CONTRACT_INFO_EXPORT + System.currentTimeMillis() + DateDisposeComm.SUFF_XLS;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		Object[] sheetObj = ExcelExportHelper.createElecontractExcelSheet(
				DateDisposeComm.ELEC_CONTRACT_INFO, vo.getOpType());
		if (sheetObj != null && sheetObj.length == SelfelecComm.NUMBER_2) {
			wb = (HSSFWorkbook) sheetObj[0];
			sheet = (HSSFSheet) sheetObj[1];
		}
		if (wb != null && sheet != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("prvId", vo.getPrvId());
			map.put("regId", vo.getRegion());
			map.put("pregId", vo.getCity());
			map.put("regIds", vo.getRegIds());
			map.put("pregIds", vo.getPregIds());
			map.put("contractItem", vo.getReg());
			map.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
			if(vo.getAuditStatus() != null){
				map.put("auditState", vo.getAuditStatus()+"");
			}
			if(vo.getStatus() != null){
				map.put("contractState", vo.getStatus()+"");
			}
			// 系统录入及导入数据
			int dataFrom = vo.getDataFrom();
			if (DataFromComm.STATE_0 == dataFrom) {
				map.put("dataFrom", dataFrom);
				map.put("dataFrom1", DataFromComm.STATE_1);
				// 接口采集数据
			} else if (DataFromComm.STATE_2 == dataFrom) {
				map.put("dataFrom", dataFrom);
			}
			//只有审核页面  并且选择的是审核中 才走流程
			if (activityFlag != null && ActivityStateComm.STATE_AUDIT == vo.getAuditStatus()) {
				if(ActivityStateComm.STATE_NORMAL == vo.getAuditStatus()){
					//mapper中 使用的 userid 值传给auditing_user_id
					map.put("userId", loginUser.getUser_id());
				}
				List<String> list = loginUser.getRole_ids();
				List<String> assigneeNameGroup = iSysRoleService
						.queryRoleNameById(list);
				Act act = new Act();
				act.setProcDefKey(ActUtils.PD_SELFELEC_AUDIT[0]);
				act.setBusinessTable(ActUtils.PD_SELFELEC_AUDIT[1]);
				act.setAssignee(loginUser.getUser_loginname());
				act.setAssigneeNameGroup(assigneeNameGroup);
				if (map.get("taskDefKey") != null) {
					act.setTaskDefKey(map.get("taskDefKey").toString());// 设置根据环节Key过滤
				}
				act.setRegCode(loginUser.getPrv_code());// 区分省份
				List<Act> idsList = actTaskService.todoList(act);
				if (idsList != null && idsList.size() > Integer.parseInt(SelfelecComm.NUMBER_0)) {
					map.put("ids", idsList);
				}
			}
			List<VEleContract> datas = queryAllEleContract(map);
			ExcelExportHelper.setElecontractExcelProperty(sheet, datas,
					vo.getOpType());
			// vo.setCur_page_num(1);
			// vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
			// PageInfo<VEleContract> firstPage = findAllVEleContract(vo,null);
			// int pages=firstPage.getPages();
			// for(int i=0;i<pages;i++){
			// if(0==i){
			// ExcelExportHelper.setElecontractExcelProperty(sheet,
			// firstPage.getList(), i, vo.getOpType());
			// }else{
			// vo.setCur_page_num(i++);
			// vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
			// PageInfo<VEleContract> page = findAllVEleContract(vo,null);
			// ExcelExportHelper.setElecontractExcelProperty(sheet,
			// page.getList(), i, vo.getOpType());
			// }
			// }
		}
		FileUtils.downFile(wb, fileName, request, response);
		// boolean exportResult = ExcelExportHelper.exportFile(wb, path,
		// fileName);
		// if(!exportResult){
		// fileName = "";
		// }
		return fileName;
	}

	@Override
	public PageInfo<VEleContract> findAllVEleContract(ElecContractQueryVO vo,
			Map<String, Object> map) {
		VEleContractExample example = new VEleContractExample();
		Criteria criteria1 = example.createCriteria();
		Criteria criteria2 = example.createCriteria();
		if (!StringUtils.isEmpty(vo.getCity()) && !vo.getCity().equals(PromptMessageComm.PLEASE_CHOOSE)) {
			criteria1.andPregIdEqualTo(vo.getCity());
			criteria2.andPregIdEqualTo(vo.getCity());
		}
		if (!StringUtils.isEmpty(vo.getRegion())
				&& !vo.getRegion().equals(PromptMessageComm.PLEASE_CHOOSE)) {
			criteria1.andRegIdEqualTo(vo.getRegion());
			criteria2.andRegIdEqualTo(vo.getRegion());
		}
		if (!StringUtils.isEmpty(vo.getReg())) {
			criteria1.andContractCodeLike("%" + vo.getReg() + "%");
			criteria2.andContractNameLike("%" + vo.getReg() + "%");
		}
		if (!StringUtils.isEmpty(vo.getStatus())) {
			criteria1.andContractStateEqualTo(vo.getStatus());
			criteria2.andContractStateEqualTo(vo.getStatus());
		}
		if (!StringUtils.isEmpty(vo.getAuditStatus())) {
			criteria1.andAuditingStateEqualTo(vo.getAuditStatus());
			criteria2.andAuditingStateEqualTo(vo.getAuditStatus());
		}
		if (!StringUtils.isEmpty(vo.getElecontractId())) {
			criteria1.andElecontractIdEqualTo(vo.getElecontractId());
			criteria2.andElecontractIdEqualTo(vo.getElecontractId());
		}
		if (!StringUtils.isEmpty(vo.getContractId())) {
			String contractId = vo.getContractId();
			criteria1.andContractIdEqualTo(contractId);
			criteria2.andContractIdEqualTo(contractId);
		}
		if (!StringUtils.isEmpty(vo.getDataFrom())) {
			// 接口采集
			if (DataFromComm.STATE_2 == vo.getDataFrom()) {
				criteria1.andDataFromEqualTo(DataFromComm.STATE_2);
				criteria2.andDataFromEqualTo(DataFromComm.STATE_2);
				// 系统录入 和导入
			} else if (DataFromComm.STATE_0 == vo.getDataFrom()) {
				criteria1.andDataFromEqualTo(DataFromComm.STATE_0);
				criteria2.andDataFromEqualTo(DataFromComm.STATE_1);
			} else {
				criteria1.andDataFromEqualTo(vo.getDataFrom());
				criteria2.andDataFromEqualTo(vo.getDataFrom());
			}
		} else {
			criteria1.andDataFromIsNotNull();
		}
		example.or(criteria2);
		PageHelper.startPage(vo.getCur_page_num(), vo.getPage_count());
		map.put("example", example);
		map.put("prvId", vo.getPrvId());
		map.put("regIds", vo.getRegIds());
		map.put("pregIds", vo.getPregIds());
		map.put("isDownShare", vo.getIsDownShare());
		PageInfo<VEleContract> page = new PageInfo<VEleContract>(
				vEleContractMapper.selectByExample(map));
		return page;
	}

	public PageInfo<DatAttachment> selectByBusiness(DatAttachment record) {
		DatAttachmentExample example = new DatAttachmentExample();

		com.xunge.model.basedata.DatAttachmentExample.Criteria criteria1 = example
				.createCriteria();
		criteria1.andBusinessIdEqualTo(record.getBusinessId());
		criteria1.andBusinessTypeEqualTo(record.getBusinessType());

		PageInfo<DatAttachment> page = new PageInfo<DatAttachment>(
				datAttachmentMapper.selectByExample(example));
		return page;
	}

	public String addAttach(DatAttachment record) {
		if (StringUtils.isEmpty(record.getAttachmentId())) {
			record.setAttachmentId(PromptMessageComm.ATTACHMENT_ID + System.currentTimeMillis());
		}
		String id = record.getAttachmentId();
		datAttachmentMapper.insert(record);
		return id;
	}

	public void delAttach(String url) {
		DatAttachmentExample example = new DatAttachmentExample();
		com.xunge.model.basedata.DatAttachmentExample.Criteria criteria = example
				.createCriteria();
		criteria.andAttachmentUrlEqualTo(url);
		datAttachmentMapper.deleteByExample(example);
	}

	private static BigDecimal mathYearquantity(Date st, Date et) {
		long diff = et.getTime() - st.getTime();
		double diffDay = diff / SelfelecComm.NUMBER_3600000 / SelfelecComm.NUMBER_24;
		BigDecimal bd = new BigDecimal(diffDay / SelfelecComm.NUMBER_365);
		return bd.setScale(SelfelecComm.NUMBER_1, BigDecimal.ROUND_UP);
	}

	@Override
	public PageInfo<VEleContract> queryAllContract(Map<String, Object> paramMap) {
		PageInfo<VEleContract> page = new PageInfo<VEleContract>(
				vEleContractMapper.queryAllElecContract(paramMap));
		return page;
	}

	@Override
	public List<VEleContract> queryAllEleContract(Map<String, Object> paramMap) {
		return vEleContractMapper.queryAllElecContract(paramMap);
	}

	/**
	 * @description 查询固化信息信息
	 * @author yuefy
	 * @date 创建时间：2017年9月8日
	 */
	@Override
	public PageInfo<VEleContractCuring> queryAllEleContractCuring(
			Map<String, Object> paramMap) {
		//周期可用
		paramMap.put("paymentState", StateComm.STATE_1);
		// 当前省份合同预警期
		// 各省份租费 电费 合同 预警期 常量值
		SysParameterVO parameter = sysParameterDao.queryParameter(paramMap);
		String warningDate = "";
		if(parameter != null){
			warningDate = parameter.getParaValue();
		}
		paramMap.put("warningDate", warningDate);
		Date nowDateTime = new Date();
		paramMap.put("nowDateTime", nowDateTime);
		paramMap.put("paymentState", StateComm.STATE_1);
		if (paramMap.get("exportFlag") == null) {
			Integer pageNumber = Integer.parseInt((String) paramMap
					.get("pageNumber"));
			Integer pageSize = Integer.parseInt((String) paramMap
					.get("pageSize"));
			if (pageNumber != null && pageSize != null) {
				PageHelper.startPage(pageNumber, pageSize);
			}
		}
		PageInfo<VEleContractCuring> page = new PageInfo<VEleContractCuring>(vEleContractMapper.queryAllElecContractCuring(paramMap));
		List<VEleContractCuring> lstvo2 = page.getList();
		if (null != lstvo2 && lstvo2.size() > Integer.parseInt(SelfelecComm.NUMBER_0)) {
			Iterator<VEleContractCuring> it = lstvo2.iterator();
			while (it.hasNext()) {
				VEleContractCuring vEleContract = (VEleContractCuring) it.next();
				// 给合同添加总月数，剩余天数
				Date start = vEleContract.getContractStartdate();
				Date end = vEleContract.getContractEnddate();
				Date now = new Date();
				double contractAllMounth =(end.getTime() - start
						.getTime()) / (double)(DateUtil.DAY_MS * SelfelecComm.NUMBER_30);
				long contractResidueDays = (long) ((end.getTime() - now
						.getTime()) / (DateUtil.DAY_MS));
				if (contractResidueDays < Integer.parseInt(SelfelecComm.NUMBER_0)) {
					contractResidueDays = Integer.parseInt(SelfelecComm.NUMBER_0);
				}
				BigDecimal bg1 = new BigDecimal(contractAllMounth);  
		        double finalNum = bg1.setScale(SelfelecComm.NUMBER_1, BigDecimal.ROUND_HALF_UP).doubleValue();  
				vEleContract.setContractAllMounth(finalNum);
				vEleContract.setContractAllMounth(contractAllMounth);
				vEleContract.setContractResidueDays(contractResidueDays);
			}
		}
		return page;
	}

	@Override
	public VEleContract queryByPrimaryKey(String elecContractId) {
		return vEleContractMapper.queryVEleContractVOById(elecContractId);
	}

	@Override
	public int updateContractVO(Map<String, Object> paramMap) {
		return vEleContractMapper.updateContractVO(paramMap);
	}

	@Override
	public VEleContract queryOneElecContractById(String elecContractId) {
		return vEleContractMapper.queryOneElecContractById(elecContractId);
	}

	/**
	 * @description 分页查询电费合同到期预警
	 * @author yuefy
	 * @date 创建时间：2017年9月7日
	 */
	@Override
	public PageInfo<VEleContract> queryEleccontractWarningList(
			Map<String, Object> paramMap) {
		//周期可用
		paramMap.put("paymentState", StateComm.STATE_1);
		// 当前省份合同预警期
		// 各省份租费 电费 合同 预警期 常量值
		SysParameterVO parameter = sysParameterDao.queryParameter(paramMap);
		String warningDate = "";
		if(parameter != null){
			warningDate = parameter.getParaValue();
		}
		paramMap.put("warningDate", warningDate);
		Date nowDateTime = new Date();
		paramMap.put("nowDateTime", nowDateTime);
		paramMap.put("paymentState", StateComm.STATE_1);
		if (paramMap.get("exportFlag") == null) {
			Integer pageNumber = Integer.parseInt((String) paramMap
					.get("pageNumber"));
			Integer pageSize = Integer.parseInt((String) paramMap
					.get("pageSize"));
			if (pageNumber != null && pageSize != null) {
				PageHelper.startPage(pageNumber, pageSize);
			}
		}
		PageInfo<VEleContract> page = new PageInfo<VEleContract>(vEleContractMapper.queryAllElecContract(paramMap));
		List<VEleContract> lstvo2 = page.getList();
		if (null != lstvo2 && lstvo2.size() > Integer.parseInt(SelfelecComm.NUMBER_0)) {
			Iterator<VEleContract> it = lstvo2.iterator();
			while (it.hasNext()) {
				VEleContract vEleContract = (VEleContract) it.next();
				// 给合同添加总月数，剩余天数
				Date start = vEleContract.getContractStartdate();
				Date end = vEleContract.getContractEnddate();
				Date now = new Date();
				double contractAllMounth = (end.getTime() - start.getTime()) / (double)(DateUtil.DAY_MS * SelfelecComm.NUMBER_30);
				long contractResidueDays = (long) ((end.getTime() - now.getTime()) / (DateUtil.DAY_MS));
				if (contractResidueDays < Integer.parseInt(SelfelecComm.NUMBER_0)) {
					contractResidueDays = Integer.parseInt(SelfelecComm.NUMBER_0);
				}
				BigDecimal bg1 = new BigDecimal(contractAllMounth);  
		        double finalNum = bg1.setScale(SelfelecComm.NUMBER_1, BigDecimal.ROUND_HALF_UP).doubleValue();  
				vEleContract.setContractAllMounth(finalNum);
				vEleContract.setContractResidueDays(contractResidueDays);
			}
		}
		return page;
	}
	
	/**
	 * @description 查询报账点和合同关联表信息
	 * @author yuefy
	 * @date 创建时间：2017年9月8日
	 */
	@Override
	public List<String> queryBillaccountContract(Map<String, Object> map) {
		return vEleContractMapper.queryBillaccountContract(map);
	};

	@Override
	public List<Map<String, String>> queryAllRegionContractNum(
			Map<String, Object> paramMap) {
		// 拼接为"崇明区：92"
		List<Map<String, String>> list = eleContractMapper
				.selectContractNumByCondition(paramMap);
		return list;
	}

	@Override
	public Map<String,Object> queryNoLinkContract(Map<String, Object> paramMap) {
		int nolinkedres = eleContractMapper.selectNolinkCont(paramMap);//已关联合同数
		int allres = eleContractMapper.selectAllContract(paramMap);//所有合同数
		Map<String, Object> resmap = new HashMap<>();
		resmap.put("nolinkedres", nolinkedres);
		resmap.put("allres", allres);
		return resmap;
	}
	
	/**
	 * @description 批量修改合同状态
	 * @author yuefy
	 * @date 创建时间：2017年10月19日
	 */
	@Override
	public boolean updateContractState(Map<String, Object> paramMap){
		return vEleContractMapper.updateContractState(paramMap);
	}
}
