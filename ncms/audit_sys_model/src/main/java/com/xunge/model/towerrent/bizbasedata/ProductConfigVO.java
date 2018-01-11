package com.xunge.model.towerrent.bizbasedata;

import java.io.Serializable;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 产品配置库
 */
public class ProductConfigVO implements Serializable{
			/**
			 * 
			 */
			private static final long serialVersionUID = -562165656232349451L;
			 //产品配置库ID
			 private String  productconfigId;
			 //铁塔类型ID
			 private String  producttypeId;
			 //铁塔类型名称
			 private String  producttypeName;
			 //机房类型ID
			 private String  roomtypeId;
			//机房类型名称
			 private String  roomtypeName;
			 //配套类型ID
			 private String  supttypeId;
			 //配套类型名称
			 private String  supttypeName;
			 //产品配置库名车功能
			 private String  productconfigName;
			 //产品配置库状态
			 private int  productconfigState;
			public String getProductconfigId() {
				return productconfigId;
			}
			public void setProductconfigId(String productconfigId) {
				this.productconfigId = productconfigId;
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
			public String getRoomtypeId() {
				return roomtypeId;
			}
			public void setRoomtypeId(String roomtypeId) {
				this.roomtypeId = roomtypeId;
			}
			public String getRoomtypeName() {
				return roomtypeName;
			}
			public void setRoomtypeName(String roomtypeName) {
				this.roomtypeName = roomtypeName;
			}
			public String getSupttypeId() {
				return supttypeId;
			}
			public void setSupttypeId(String supttypeId) {
				this.supttypeId = supttypeId;
			}
			public String getSupttypeName() {
				return supttypeName;
			}
			public void setSupttypeName(String supttypeName) {
				this.supttypeName = supttypeName;
			}
			public String getProductconfigName() {
				return productconfigName;
			}
			public void setProductconfigName(String productconfigName) {
				this.productconfigName = productconfigName;
			}
			public int getProductconfigState() {
				return productconfigState;
			}
			public void setProductconfigState(int productconfigState) {
				this.productconfigState = productconfigState;
			}
}
