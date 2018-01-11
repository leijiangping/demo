package com.xunge.model.selfrent.billAccount;
import java.io.Serializable;
import java.util.Date;
/**
 * 资源点实体类
 *@author changwq
 *
 */
public class DatBaseresourceVO implements Serializable  {
/**
	 * 
	 */
	private static final long serialVersionUID = -4480031821724611662L;
//	资源编码
    private String baseresourceId;
//	所属站点编码
    private String basesiteId;
//	所属区域编码
    private String regId;
//	资源类别（0：机房  1：资源点  2：热点）
    private Integer baseresourceType;
/*	根据资源类别填值（0：机房  1：资源点  2：热点）判断
    机房：
    1、交换机房  2、数据机房    3、动力机房
    4、传输机房    5、无线机房    6、综合机房 
    资源类别
    1.传输网络资源点、    2.无线网络资源点、
    3.全业务网络资源点、    4.综合网络资源点*/
    private Integer baseresourceCategory;
//	第三方系统资源编码（综资系统数据唯一标识）
    private String baseresourceCuid;
//	资源代码
    private String baseresourceCode;
//	资源名称
    private String baseresourceName;
//	资源地址
    private String baseresourceAddress;
//	资源面积（机房独有属性）资源点无此属性
    private Long baseresourceArea;
//	入网日期
    private Date baseresourceOpendate;
//	退网日期
    private Date baseresourceStopdate;
	/*机房特有属性
   1-移动资产    2-铁塔资产*/
    private Integer roomOwner;
/*机房特有属性
 1-自建  2-共建  3-租用  4-购买
 */           
    private Integer roomProperty;
/*机房特有属性
 1-联通   2-电信   3-联通电信
*/   
    private Integer roomShare;
//	
    private Long baseresourceLongitude;
//
    private Long baseresourceLatitude;
/*	空调总额定功率
    单位kw；
    动环专业下所属该机房所有空调的额定功率之和*/
    
    private Long airconditionerPower;
   /* 全生命周期状态
    1、在网    2、工程    3、退网*/
    private Integer baseresourceState;
//	资源备注
    private String baseresourceNote;
//   数据来源（0：系统录入  1:系统导入   2：接口采集）
    private Integer dataFrom;
    
    /**
     * 房屋缴费明细
     * */
    private RentPaymentdetailVO rentPaymentdetailVO;
    
    public String getBaseresourceId() {
        return baseresourceId;
    }

    public void setBaseresourceId(String baseresourceId) {
        this.baseresourceId = baseresourceId == null ? null : baseresourceId.trim();
    }

    public String getBasesiteId() {
        return basesiteId;
    }

    public void setBasesiteId(String basesiteId) {
        this.basesiteId = basesiteId == null ? null : basesiteId.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public Integer getBaseresourceType() {
        return baseresourceType;
    }

    public void setBaseresourceType(Integer baseresourceType) {
        this.baseresourceType = baseresourceType;
    }

    public Integer getBaseresourceCategory() {
        return baseresourceCategory;
    }

    public void setBaseresourceCategory(Integer baseresourceCategory) {
        this.baseresourceCategory = baseresourceCategory;
    }

    public String getBaseresourceCuid() {
        return baseresourceCuid;
    }

    public void setBaseresourceCuid(String baseresourceCuid) {
        this.baseresourceCuid = baseresourceCuid == null ? null : baseresourceCuid.trim();
    }

    public String getBaseresourceCode() {
        return baseresourceCode;
    }

    public void setBaseresourceCode(String baseresourceCode) {
        this.baseresourceCode = baseresourceCode == null ? null : baseresourceCode.trim();
    }

    public String getBaseresourceName() {
        return baseresourceName;
    }

    public void setBaseresourceName(String baseresourceName) {
        this.baseresourceName = baseresourceName == null ? null : baseresourceName.trim();
    }

    public String getBaseresourceAddress() {
        return baseresourceAddress;
    }

    public void setBaseresourceAddress(String baseresourceAddress) {
        this.baseresourceAddress = baseresourceAddress == null ? null : baseresourceAddress.trim();
    }

    public Long getBaseresourceArea() {
        return baseresourceArea;
    }

    public void setBaseresourceArea(Long baseresourceArea) {
        this.baseresourceArea = baseresourceArea;
    }

    public Date getBaseresourceOpendate() {
        return baseresourceOpendate;
    }

    public void setBaseresourceOpendate(Date baseresourceOpendate) {
        this.baseresourceOpendate = baseresourceOpendate;
    }

    public Date getBaseresourceStopdate() {
        return baseresourceStopdate;
    }

    public void setBaseresourceStopdate(Date baseresourceStopdate) {
        this.baseresourceStopdate = baseresourceStopdate;
    }

    public Integer getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(Integer roomOwner) {
        this.roomOwner = roomOwner;
    }

    public Integer getRoomProperty() {
        return roomProperty;
    }

    public void setRoomProperty(Integer roomProperty) {
        this.roomProperty = roomProperty;
    }

    public Integer getRoomShare() {
        return roomShare;
    }

    public void setRoomShare(Integer roomShare) {
        this.roomShare = roomShare;
    }

    public Long getBaseresourceLongitude() {
        return baseresourceLongitude;
    }

    public void setBaseresourceLongitude(Long baseresourceLongitude) {
        this.baseresourceLongitude = baseresourceLongitude;
    }

    public Long getBaseresourceLatitude() {
        return baseresourceLatitude;
    }

    public void setBaseresourceLatitude(Long baseresourceLatitude) {
        this.baseresourceLatitude = baseresourceLatitude;
    }

    public Long getAirconditionerPower() {
        return airconditionerPower;
    }

    public void setAirconditionerPower(Long airconditionerPower) {
        this.airconditionerPower = airconditionerPower;
    }

    public Integer getBaseresourceState() {
        return baseresourceState;
    }

    public void setBaseresourceState(Integer baseresourceState) {
        this.baseresourceState = baseresourceState;
    }

    public String getBaseresourceNote() {
        return baseresourceNote;
    }

    public void setBaseresourceNote(String baseresourceNote) {
        this.baseresourceNote = baseresourceNote == null ? null : baseresourceNote.trim();
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
    }

	public RentPaymentdetailVO getRentPaymentdetailVO() {
		return rentPaymentdetailVO;
	}

	public void setRentPaymentdetailVO(RentPaymentdetailVO rentPaymentdetailVO) {
		this.rentPaymentdetailVO = rentPaymentdetailVO;
	}
}