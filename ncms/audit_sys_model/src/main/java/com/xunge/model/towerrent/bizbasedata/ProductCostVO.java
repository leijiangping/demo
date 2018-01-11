package com.xunge.model.towerrent.bizbasedata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jcy
 * @date 2017年7月6日
 * @description 建造成本库
 */
public class ProductCostVO implements Serializable{
	/**
	 * 
	 */
	  private static final long serialVersionUID = -187600985857878L;
	  //建造成本库编码
	  private String productcostId; 
	  // 铁塔种类编码  
	  private String producttypeId;
	  // 铁塔种类名称
	  private String producttypeName;
	  //挂高范围编码
	  private String highupId; 
	  //挂高范围名称
	  private String highupName;
	  //风压范围编码
	  private String windpressureId;
	  //风压范围名称
	  private String windpressureName;
	  //机房类型
	  private String roomtypeId;
	  //配套类型
	  private String supttypeId;
	  //机房名称
	  private String roomtypeName;
	  //配套名称
	  private String supttypeName;
	  //建造成本库价格（万元）
	  private BigDecimal productcostPrice;
	  //建造成本库状态
	  private int productcostState;
	  //建造成本库备注
	  private String productcostNote;
		  
		public String getProductcostId() {
			return productcostId;
		}
		public void setProductcostId(String productcostId) {
			this.productcostId = productcostId;
		}
		public String getProducttypeId() {
			return producttypeId;
		}
		public void setProducttypeId(String producttypeId) {
			this.producttypeId = producttypeId;
		}
		public String getProducttypeName() {
			return producttypeName;
		}
		public void setProducttypeName(String producttypeName) {
			this.producttypeName = producttypeName;
		}
		public String getHighupId() {
			return highupId;
		}
		public void setHighupId(String highupId) {
			this.highupId = highupId;
		}
		public String getHighupName() {
			return highupName;
		}
		public void setHighupName(String highupName) {
			this.highupName = highupName;
		}
		public String getWindpressureId() {
			return windpressureId;
		}
		public void setWindpressureId(String windpressureId) {
			this.windpressureId = windpressureId;
		}
		public String getWindpressureName() {
			return windpressureName;
		}
		public void setWindpressureName(String windpressureName) {
			this.windpressureName = windpressureName;
		}
		public BigDecimal getProductcostPrice() {
			return productcostPrice;
		}
		public void setProductcostPrice(BigDecimal productcostPrice) {
			this.productcostPrice = productcostPrice;
		}
		public int getProductcostState() {
			return productcostState;
		}
		public void setProductcostState(int productcostState) {
			this.productcostState = productcostState;
		}
		public String getProductcostNote() {
			return productcostNote;
		}
		public void setProductcostNote(String productcostNote) {
			this.productcostNote = productcostNote;
		}
		public String getRoomtypeId() {
			return roomtypeId;
		}
		public void setRoomtypeId(String roomtypeId) {
			this.roomtypeId = roomtypeId;
		}
		public String getSupttypeId() {
			return supttypeId;
		}
		public void setSupttypeId(String supttypeId) {
			this.supttypeId = supttypeId;
		}
	  public String getRoomtypeName() {
			return roomtypeName;
		}
		public void setRoomtypeName(String roomtypeName) {
			this.roomtypeName = roomtypeName;
		}
		public String getSupttypeName() {
			return supttypeName;
		}
		public void setSupttypeName(String supttypeName) {
			this.supttypeName = supttypeName;
		}
}
