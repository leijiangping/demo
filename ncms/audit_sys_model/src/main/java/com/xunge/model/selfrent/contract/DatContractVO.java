package com.xunge.model.selfrent.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import com.xunge.core.util.DateTime;
import com.xunge.core.util.DateUtil;
import com.xunge.model.system.region.SysRegionVO;

@ExcelTarget(value = "datContract")
public class DatContractVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8214100254707993297L;
	//合同编号
    private String contractId;
    //合同系统中合同流水号
    private String contractFlow;
    //区域编码
    @Excel(name="所属区县",orderNum="2")
    private String regId;
    //管理部门
    @Excel(name="所属部门",orderNum="25")
    private String managerDepartment;
    //管理负责人
    private String managerUser;
    //合同代码
    @Excel(name="合同代码",orderNum="4")
    private String contractCode;
    //合同名称
    @Excel(name="合同名称",orderNum="5")
    private String contractName;
    //合同类型
    @Excel(name="合同分类",orderNum="10",replace={"房租合同_0","电费合同_1"})
    private Integer contractType;
    //合同期始
    @Excel(name="合同期始",orderNum="12")
    private String contractStartdate;
    //合同期终
    @Excel(name="合同期终",orderNum="14")
    private String contractEnddate;
    //签约日期
    @Excel(name="签约日期",orderNum="15")
    private String contractSigndate;
    //合同年限
    @Excel(name="合同年限",orderNum="16")
    private double contractYearquantity;
    //我方签约名称
    @Excel(name="我方联系人",orderNum="17")
    private String contractCheckname1;
    //对方签约名称
    @Excel(name="对方联系人",orderNum="18")
    private String contractCheckname2;
    //合同状态（0：正常   9：停用）
    @Excel(name="合同状态",orderNum="6",replace={"正常_0","终止_9"})
    private int contractState;
    //合同备注
    private String contractNote;
    //数据来源（0：系统录入  1:系统导入   2：接口采集）
    private Integer dataFrom;
    //区域信息对象
    private SysRegionVO sysRegionVO;
    //总月数
    @Excel(name="合同总月数",orderNum="6")
    private String sumMouth;
    //剩余天数
    @Excel(name="剩余天数",orderNum="6")
    private String surplusDay;
    //是否向下共享 1-是 ，0-否
    @Excel(name="是否向下共享",orderNum="3",replace={"否_0","是_1"})
    private Integer isDownShare;
    //区县名称
    private String regName;
    //地市Id
    private String pregId;
    //地市名称
    @Excel(name="地市",orderNum="1")
    private String pregName;
    //省简称
    @Excel(name="省份",orderNum="0")
    private String prvSName;
    //省份ID
    private String prvId;
    //审核状态(状态：0-审核通过，8- 审核不通过 ，9-审核中，-1-未提交)
    @Excel(name="审核状态",orderNum="6",replace={"审核通过_0","审核不通过_8","审核中_9","未提交_-1"})
    private Integer auditingState;

    public Integer getAuditingState() {
		return auditingState;
	}

	public void setAuditingState(Integer auditingState) {
		this.auditingState = auditingState;
	}

	public String getPrvId() {
		return prvId;
	}

	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getPregId() {
		return pregId;
	}

	public void setPregId(String pregId) {
		this.pregId = pregId;
	}

	public String getPregName() {
		return pregName;
	}

	public void setPregName(String pregName) {
		this.pregName = pregName;
	}

	public String getPrvSName() {
		return prvSName;
	}

	public void setPrvSName(String prvSName) {
		this.prvSName = prvSName;
	}

	public Integer getIsDownShare() {
		return isDownShare;
	}

	public void setIsDownShare(Integer isDownShare) {
		this.isDownShare = isDownShare;
	}

	public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getContractFlow() {
		return contractFlow;
	}

	public void setContractFlow(String contractFlow) {
		this.contractFlow = contractFlow;
	}

	public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getManagerDepartment() {
        return managerDepartment;
    }

    public void setManagerDepartment(String managerDepartment) {
        this.managerDepartment = managerDepartment == null ? null : managerDepartment.trim();
    }

    public String getManagerUser() {
        return managerUser;
    }

    public void setManagerUser(String managerUser) {
        this.managerUser = managerUser == null ? null : managerUser.trim();
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.replaceAll(" ", "");
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.replaceAll(" ", "");
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public String getContractStartdate() {
        return contractStartdate;
    }

    public void setContractStartdate(String contractStartdate) {
        this.contractStartdate = contractStartdate;
    }

    public String getContractEnddate() {
        return contractEnddate;
    }

    public void setContractEnddate(String contractEnddate) {
        this.contractEnddate = contractEnddate;
    }

    public String getContractSigndate() {
        return contractSigndate;
    }

    public void setContractSigndate(String contractSigndate) {
        this.contractSigndate = contractSigndate;
    }

    public double getContractYearquantity() {
		return contractYearquantity;
	}

	public void setContractYearquantity(double contractYearquantity) {
		this.contractYearquantity = contractYearquantity;
	}

	public String getContractCheckname1() {
        return contractCheckname1;
    }

    public void setContractCheckname1(String contractCheckname1) {
        this.contractCheckname1 = contractCheckname1 == null ? null : contractCheckname1.trim();
    }

    public String getContractCheckname2() {
        return contractCheckname2;
    }

    public void setContractCheckname2(String contractCheckname2) {
        this.contractCheckname2 = contractCheckname2 == null ? null : contractCheckname2.trim();
    }

    public int getContractState() {
		return contractState;
	}

	public void setContractState(int contractState) {
		this.contractState = contractState;
	}

	public String getContractNote() {
        return contractNote;
    }

    public void setContractNote(String contractNote) {
        this.contractNote = contractNote == null ? null : contractNote.trim();
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
    }

	public SysRegionVO getSysRegionVO() {
		return sysRegionVO;
	}

	public void setSysRegionVO(SysRegionVO sysRegionVO) {
		this.sysRegionVO = sysRegionVO;
	}

	public String getSumMouth() {
		if(getContractEnddate() != null && getContractStartdate() != null){
			DateTime endDate = DateUtil.parse(getContractEnddate(), "yyyy-MM-dd");
			DateTime startDate = DateUtil.parse(getContractStartdate(), "yyyy-MM-dd");
			long time = (endDate.getTime() - startDate.getTime());
			long data = (DateUtil.DAY_MS * 30);
			double mounth = time/(double)data;
			sumMouth = String.format("%.1f\n", mounth);
		}
		return sumMouth;
	}

	public void setSumMouth(String sumMouth) {
		this.sumMouth = sumMouth;
	}

	public String getSurplusDay() {
		if(getContractEnddate() != null && getContractStartdate() != null){
			DateTime endDate = DateUtil.parse(getContractEnddate(), "yyyy-MM-dd");
			DateTime nowDate = new DateTime();
	    	surplusDay = ((endDate.getTime() - nowDate.getTime())/DateUtil.DAY_MS) + " 天";
	    	if(((endDate.getTime() - nowDate.getTime())/DateUtil.DAY_MS) < 0){
	    		surplusDay = "已超期";
	    	}
		}
		return surplusDay;
	}

	public void setSurplusDay(String surplusDay) {
		this.surplusDay = surplusDay;
	}
}