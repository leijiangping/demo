package com.xunge.model.system.region;

import java.io.Serializable;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import com.xunge.model.system.province.SysProvinceVO;

@ExcelTarget(value = "sysRegion")
public class SysRegionVO implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5036030851214739590L;

	private String regId;

    private String prvId;

    private String regCode;

    private String regName;

    private String pregId;
    
    private Integer regOrder;

    private Integer level;

    private Integer isValid;

    private Integer regState;
    //省份名称
    @Excel(name="省份",orderNum="0")
    private String prvName;
    
    private String prvCode;
    
    //省份上线状态 0 未上线 9 已上线
    private Integer prvState;
    //省份标识
    private String prvFlag;
    
    private String pregName;
  	//行政区编码
  	private String  rCode;
  	//行政区名称	
  	private String  rName;
  	//上级行政区编码
  	private String  pRegCode;
  	//上级行政区名称
  	@Excel(name="地市",orderNum="1")
  	private String  pRegName;
  	private String prvSname;
  	//排序号
    
  	private Boolean isAjaxing;
    private Boolean  isFirstNode;
    private Boolean  isLastNode;
    private Boolean  isParent;
    private Boolean open;
    private String parentTId;
    private String tId;
    private Boolean zAsync;
    //子部门集合
    private List<SysRegionVO> children;
  	
    private SysProvinceVO province;
    
    
    public String getPrvName() {
		return prvName;
	}

	public void setPrvName(String prvName) {
		this.prvName = prvName;
	}

	public Boolean getIsAjaxing() {
		return isAjaxing;
	}

	public void setIsAjaxing(Boolean isAjaxing) {
		this.isAjaxing = isAjaxing;
	}

	public Boolean getIsFirstNode() {
		return isFirstNode;
	}

	public void setIsFirstNode(Boolean isFirstNode) {
		this.isFirstNode = isFirstNode;
	}

	public Boolean getIsLastNode() {
		return isLastNode;
	}

	public void setIsLastNode(Boolean isLastNode) {
		this.isLastNode = isLastNode;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getParentTId() {
		return parentTId;
	}

	public void setParentTId(String parentTId) {
		this.parentTId = parentTId;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public Boolean getzAsync() {
		return zAsync;
	}

	public void setzAsync(Boolean zAsync) {
		this.zAsync = zAsync;
	}

	public List<SysRegionVO> getChildren() {
		return children;
	}

	public void setChildren(List<SysRegionVO> children) {
		this.children = children;
	}


	public String getrCode() {
		return rCode;
	}

	public void setrCode(String rCode) {
		this.rCode = rCode;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public String getpRegCode() {
		return pRegCode;
	}

	public void setpRegCode(String pRegCode) {
		this.pRegCode = pRegCode;
	}

	public String getpRegName() {
		return pRegName;
	}

	public void setpRegName(String pRegName) {
		this.pRegName = pRegName;
	}


	public SysRegionVO() {
		super();
	}

	public SysRegionVO(String regId, String prvId, String regCode,
			String regName, String pregId, Integer regOrder, Integer level,
			Integer isValid, Integer regState) {
		super();
		this.regId = regId;
		this.prvId = prvId;
		this.regCode = regCode;
		this.regName = regName;
		this.pregId = pregId;
		this.regOrder = regOrder;
		this.level = level;
		this.isValid = isValid;
		this.regState = regState;
	}

	public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode == null ? null : regCode.trim();
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName == null ? null : regName.trim();
    }

    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId == null ? null : pregId.trim();
    }

    public Integer getRegOrder() {
        return regOrder;
    }

    public void setRegOrder(Integer regOrder) {
        this.regOrder = regOrder;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getRegState() {
        return regState;
    }

    public void setRegState(Integer regState) {
        this.regState = regState;
    }

	public SysProvinceVO getProvince() {
		return province;
	}

	public void setProvince(SysProvinceVO province) {
		this.province = province;
	}

	public String getPregName() {
		return pregName;
	}

	public void setPregName(String pregName) {
		this.pregName = pregName;
	}

	public String getPrvCode() {
		return prvCode;
	}

	public void setPrvCode(String prvCode) {
		this.prvCode = prvCode;
	}

	public String getPrvFlag() {
		return prvFlag;
	}

	public void setPrvFlag(String prvFlag) {
		this.prvFlag = prvFlag;
	}

	public String getPrvSname() {
		return prvSname;
	}

	public void setPrvSname(String prvSname) {
		this.prvSname = prvSname;
	}

	public Integer getPrvState() {
		return prvState;
	}

	public void setPrvState(Integer prvState) {
		this.prvState = prvState;
	}
	
}