package com.xunge.model.basedata.ring;

import java.io.Serializable;
import java.util.Date;

public class PowerPerfVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1872949848836501922L;

	private String powerId;

    private String prvId;

    private String prvSname;

    private String pregId;

    private String pregName;

    private String regId;

    private String regName;

    private Integer resourceType;

    private String resourceCode;

    private Integer codeType;

    private String resourceName;

    private Date startTime;

    private Date stopTime;

    private Float smpsLoss;

    private Float v1;

    private Float a1;

    private Float v2;

    private Float a2;

    private Float v3;

    private Float a3;

    private Float v4;

    private Float a4;

    private Float v5;

    private Float a5;

    private Float v6;

    private Float a6;

    private Float v7;

    private Float a7;

    private Float v8;

    private Float a8;

    private Float v9;

    private Float a9;

    private Float v10;

    private Float a10;

    private Float v11;

    private Float a11;

    private Float v12;

    private Float a12;

    private Float v13;

    private Float a13;

    private Float v14;

    private Float a14;

    private Float v15;

    private Float a15;

    private Float v16;

    private Float a16;

    private Float v17;

    private Float a17;

    private Float v18;

    private Float a18;

    private Float v19;

    private Float a19;

    private Float v20;

    private Float a20;

    private Float v21;

    private Float a21;

    private Float v22;

    private Float a22;

    private Float v23;

    private Float a23;

    private Float v24;

    private Float a24;
    
    private String queryDate;
    
    private String userId;
    
    private String create_user;
    private String create_ip;
    private Date create_time;
    private String update_user;
    private String update_ip;
    private Date update_time;

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId == null ? null : powerId.trim();
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

    public String getPrvSname() {
        return prvSname;
    }

    public void setPrvSname(String prvSname) {
        this.prvSname = prvSname == null ? null : prvSname.trim();
    }

    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId == null ? null : pregId.trim();
    }

    public String getPregName() {
        return pregName;
    }

    public void setPregName(String pregName) {
        this.pregName = pregName == null ? null : pregName.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName == null ? null : regName.trim();
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode == null ? null : resourceCode.trim();
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.replaceAll(" ", "");
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Float getSmpsLoss() {
    	if(smpsLoss==null){
    		smpsLoss=new Float("0");
    	}
        return smpsLoss;
    }

    public void setSmpsLoss(Float smpsLoss) {
        this.smpsLoss = smpsLoss;
    }

    public Float getV1() {
    	if(v1==null){
    		v1=new Float("0");
    	}
        return v1;
    }

    public void setV1(Float v1) {
        this.v1 = v1;
    }

    public Float getA1() {
    	if(a1==null){
    		a1=new Float("0");
    	}
        return a1;
    }

    public void setA1(Float a1) {
        this.a1 = a1;
    }

    public Float getV2() {
    	
    	if(v2==null){
    		v2=new Float("0");
    	}
        return v2;
    }

    public void setV2(Float v2) {
        this.v2 = v2;
    }

    public Float getA2() {
    	if(a2==null){
    		a2=new Float("0");
    	}
        return a2;
    }

    public void setA2(Float a2) {
        this.a2 = a2;
    }

    public Float getV3() {
    	if(v3==null){
    		v3=new Float("0");
    	}
        return v3;
    }

    public void setV3(Float v3) {
        this.v3 = v3;
    }

    public Float getA3() {
    	
    	if(a3==null){
    		a3=new Float("0");
    	}
        return a3;
    }

    public void setA3(Float a3) {
        this.a3 = a3;
    }

    public Float getV4() {
    	
    	if(v4==null){
    		v4=new Float("0");
    	}
        return v4;
    }

    public void setV4(Float v4) {
        this.v4 = v4;
    }

    public Float getA4() {
    	
    	if(a4==null){
    		a4=new Float("0");
    	}
        return a4;
    }

    public void setA4(Float a4) {
        this.a4 = a4;
    }

    public Float getV5() {
    	
    	if(v5==null){
    		v5=new Float("0");
    	}
        return v5;
    }

    public void setV5(Float v5) {
        this.v5 = v5;
    }

    public Float getA5() {
    	
    	if(a5==null){
    		a5=new Float("0");
    	}
        return a5;
    }

    public void setA5(Float a5) {
        this.a5 = a5;
    }

    public Float getV6() {
    	if(v6==null){
    		v6=new Float("0");
    	}
        return v6;
    }

    public void setV6(Float v6) {
        this.v6 = v6;
    }

    public Float getA6() {
    	if(a6==null){
    		a6=new Float("0");
    	}
        return a6;
    }

    public void setA6(Float a6) {
        this.a6 = a6;
    }

    public Float getV7() {
    	if(v7==null){
    		v7=new Float("0");
    	}
        return v7;
    }

    public void setV7(Float v7) {
        this.v7 = v7;
    }

    public Float getA7() {
    	if(a7==null){
    		a7=new Float("0");
    	}
        return a7;
    }

    public void setA7(Float a7) {
        this.a7 = a7;
    }

    public Float getV8() {
    	if(v8==null){
    		v8=new Float("0");
    	}
        return v8;
    }

    public void setV8(Float v8) {
        this.v8 = v8;
    }

    public Float getA8() {
    	if(a8==null){
    		a8=new Float("0");
    	}
        return a8;
    }

    public void setA8(Float a8) {
        this.a8 = a8;
    }

    public Float getV9() {
    	if(v9==null){
    		v9=new Float("0");
    	}
        return v9;
    }

    public void setV9(Float v9) {
        this.v9 = v9;
    }

    public Float getA9() {
    	
    	if(a9==null){
    		a9=new Float("0");
    	}
        return a9;
    }

    public void setA9(Float a9) {
        this.a9 = a9;
    }

    public Float getV10() {
    	if(v10==null){
    		v10=new Float("0");
    	}
        return v10;
    }

    public void setV10(Float v10) {
        this.v10 = v10;
    }

    public Float getA10() {
    	if(a10==null){
    		a10=new Float("0");
    	}
        return a10;
    }

    public void setA10(Float a10) {
        this.a10 = a10;
    }

    public Float getV11() {
    	
    	if(v11==null){
    		v11=new Float("0");
    	}
        return v11;
    }

    public void setV11(Float v11) {
        this.v11 = v11;
    }

    public Float getA11() {
    	
    	if(a11==null){
    		a11=new Float("0");
    	}
        return a11;
    }

    public void setA11(Float a11) {
        this.a11 = a11;
    }

    public Float getV12() {
    	
    	if(v12==null){
    		v12=new Float("0");
    	}
        return v12;
    }

    public void setV12(Float v12) {
        this.v12 = v12;
    }

    public Float getA12() {
    	
    	if(a12==null){
    		a12=new Float("0");
    	}
        return a12;
    }

    public void setA12(Float a12) {
        this.a12 = a12;
    }

    public Float getV13() {
    	
    	if(v13==null){
    		v13=new Float("0");
    	}
        return v13;
    }

    public void setV13(Float v13) {
        this.v13 = v13;
    }

    public Float getA13() {
    	
    	if(a13==null){
    		a13=new Float("0");
    	}
        return a13;
    }

    public void setA13(Float a13) {
        this.a13 = a13;
    }

    public Float getV14() {
    	
    	if(v14==null){
    		v14=new Float("0");
    	}
        return v14;
    }

    public void setV14(Float v14) {
        this.v14 = v14;
    }

    public Float getA14() {
    	
    	if(a14==null){
    		a14=new Float("0");
    	}
        return a14;
    }

    public void setA14(Float a14) {
        this.a14 = a14;
    }

    public Float getV15() {
    	
    	if(v15==null){
    		v15=new Float("0");
    	}
        return v15;
    }

    public void setV15(Float v15) {
        this.v15 = v15;
    }

    public Float getA15() {
    	
    	if(a15==null){
    		a15=new Float("0");
    	}
        return a15;
    }

    public void setA15(Float a15) {
        this.a15 = a15;
    }

    public Float getV16() {
    	
    	if(v16==null){
    		v16=new Float("0");
    	}
        return v16;
    }

    public void setV16(Float v16) {
        this.v16 = v16;
    }

    public Float getA16() {
    	
    	if(a16==null){
    		a16=new Float("0");
    	}
        return a16;
    }

    public void setA16(Float a16) {
        this.a16 = a16;
    }

    public Float getV17() {
    	
    	if(v17==null){
    		v17=new Float("0");
    	}
        return v17;
    }

    public void setV17(Float v17) {
        this.v17 = v17;
    }

    public Float getA17() {
    	
    	if(a17==null){
    		a17=new Float("0");
    	}
        return a17;
    }

    public void setA17(Float a17) {
        this.a17 = a17;
    }

    public Float getV18() {
    	
    	if(v18==null){
    		v18=new Float("0");
    	}
        return v18;
    }

    public void setV18(Float v18) {
        this.v18 = v18;
    }

    public Float getA18() {
    	
    	if(a18==null){
    		a18=new Float("0");
    	}
        return a18;
    }

    public void setA18(Float a18) {
        this.a18 = a18;
    }

    public Float getV19() {
    	
    	if(v19==null){
    		v19=new Float("0");
    	}
        return v19;
    }

    public void setV19(Float v19) {
        this.v19 = v19;
    }

    public Float getA19() {
    	
    	if(a19==null){
    		a19=new Float("0");
    	}
        return a19;
    }

    public void setA19(Float a19) {
        this.a19 = a19;
    }

    public Float getV20() {
    	
    	if(v20==null){
    		v20=new Float("0");
    	}
        return v20;
    }

    public void setV20(Float v20) {
        this.v20 = v20;
    }

    public Float getA20() {
    	
    	if(a20==null){
    		a20=new Float("0");
    	}
        return a20;
    }

    public void setA20(Float a20) {
        this.a20 = a20;
    }

    public Float getV21() {
    	
    	if(v21==null){
    		v21=new Float("0");
    	}
        return v21;
    }

    public void setV21(Float v21) {
        this.v21 = v21;
    }

    public Float getA21() {
    	if(a21==null){
    		a21=new Float("0");
    	}
        return a21;
    }

    public void setA21(Float a21) {
        this.a21 = a21;
    }

    public Float getV22() {
    	
    	if(v22==null){
    		v22=new Float("0");
    	}
        return v22;
    }

    public void setV22(Float v22) {
        this.v22 = v22;
    }

    public Float getA22() {
    	
    	if(a22==null){
    		a22=new Float("0");
    	}
        return a22;
    }

    public void setA22(Float a22) {
        this.a22 = a22;
    }

    public Float getV23() {
    	
    	if(v23==null){
    		v23=new Float("0");
    	}
        return v23;
    }

    public void setV23(Float v23) {
        this.v23 = v23;
    }

    public Float getA23() {
    	
    	if(a23==null){
    		a23=new Float("0");
    	}
        return a23;
    }

    public void setA23(Float a23) {
        this.a23 = a23;
    }

    public Float getV24() {
    	
    	if(v24==null){
    		v24=new Float("0");
    	}
        return v24;
    }

    public void setV24(Float v24) {
        this.v24 = v24;
    }

    public Float getA24() {
    	
    	if(a24==null){
    		a24=new Float("0");
    	}
        return a24;
    }

    public void setA24(Float a24) {
        this.a24 = a24;
    }

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCreate_ip() {
		return create_ip;
	}

	public void setCreate_ip(String create_ip) {
		this.create_ip = create_ip;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getUpdate_ip() {
		return update_ip;
	}

	public void setUpdate_ip(String update_ip) {
		this.update_ip = update_ip;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
    
}