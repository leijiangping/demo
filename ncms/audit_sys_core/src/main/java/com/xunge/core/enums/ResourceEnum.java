package com.xunge.core.enums;

public class ResourceEnum {

	public static final AuditStateEnum auditStateEnum = new AuditStateEnum();
	public static final ResStateEnum resStateEnum = new ResStateEnum();
	public static final ResBelongEnum resBelongEnum = new ResBelongEnum();
	public static final DataFromEnum datafromEnum = new DataFromEnum();
	public static final ContractStateEnum contractStateEnum = new ContractStateEnum();
	public static final ResourceTypeEnum resourceTypeEnum = new ResourceTypeEnum();
	
	/**
	 * 枚举 审核状态
	* 
	* Title: AuditStateEnum
	* @author Rob
	 */
	public static class AuditStateEnum extends BaseEnum<Object>{

		private static final long serialVersionUID = 1L;
		/**
		 * 未提交
		 */
		public static final int PREAUDIT = -1;
		/**
		 * 审核中
		 */
		public static final int AUDITING = 9;
		/**
		 * 审核通过
		 */
		public static final int AUDITSUCC = 0;
		/**
		 * 审核未通过
		 */
		public static final int AUDITFAIL = 8;

		private AuditStateEnum(){
			super.putEnum(PREAUDIT, "未提交");
			super.putEnum(AUDITING, "审核中");
			super.putEnum(AUDITSUCC, "审核通过");
			super.putEnum(AUDITFAIL, "审核未通过");
		}
	}
	/**
	 * 枚举 资源状态
	* 
	* Title: ResStateEnum
	* @author Rob
	 */
	public static class ResStateEnum extends BaseEnum<Object>{

		private static final long serialVersionUID = 1L;
		/**
		 * 在网
		 */
		public static final int ENTERNET = 1;
		/**
		 * 工程
		 */
		public static final int ENGINEERING = 2;
		/**
		 * 退网
		 */
		public static final int BACKNET = 3;

		private ResStateEnum(){
			super.putEnum(ENTERNET, "在网");
			super.putEnum(ENGINEERING, "工程");
			super.putEnum(BACKNET, "退网");
		}
	}
	/**
	 * 枚举 资源归属
	* 
	* Title: ResBelongEnum
	* @author Rob
	 */
	public static class ResBelongEnum extends BaseEnum<Object>{

		private static final long serialVersionUID = 1L;
		//若是多个单位，之间用半角逗号分割，举例如下：1,2,4
		
		/**
		 * 中国移动
		 */
		public static final String CMCC = "1";
		/**
		 * 中国联通
		 */
		public static final String CUCC = "2";
		/**
		 * 中国电信
		 */
		public static final String CTCC = "3";
		/**
		 * 中国铁通
		 */
		public static final String CTT = "4";
		/**
		 * 广电
		 */
		public static final String SARFT = "5";
		/**
		 * 业主
		 */
		public static final String OWENR = "6";
		/**
		 * 其他
		 */
		public static final String ELSE = "7";
		/**
		 * 电力
		 */
		public static final String ELECTRICPOWER = "8";
		/**
		 * 铁塔
		 */		
		public static final String TOWER = "9";

		private ResBelongEnum(){
			super.putEnum(CMCC, "中国移动");
			super.putEnum(CUCC, "中国联通");
			super.putEnum(CTCC, "中国电信");
			super.putEnum(CTT, "中国铁通");
			super.putEnum(SARFT, "广电");
			super.putEnum(OWENR, "业主");
			super.putEnum(ELSE, "其他");
			super.putEnum(ELECTRICPOWER, "电力");
			super.putEnum(TOWER, "中国铁塔");
		}
	}
	/**
	 * 枚举 数据来源
	* 
	* Title: DataFromEnum
	* @author Rob
	 */
	public static class DataFromEnum extends BaseEnum<Object>{

		private static final long serialVersionUID = 1L;
		/**
		 * 系统录入
		 */
		public static final int SYSTEM = 0;
		/**
		 * 系统导入
		 */
		public static final int IMPORT = 1;
		/**
		 * 接口采集
		 */
		public static final int COLLECTION = 2;

		private DataFromEnum(){
			super.putEnum(SYSTEM, "系统录入");
			super.putEnum(IMPORT, "系统导入");
			super.putEnum(COLLECTION, "接口采集");
		}
	}
	/**
	 * 枚举 合同状态
	* 
	* Title: ContractStateEnum
	* @author Rob
	 */
	public static class ContractStateEnum extends BaseEnum<Object>{

		private static final long serialVersionUID = 1L;
		/**
		 * 正常
		 */
		public static final int NORMAL = 0;
		/**
		 * 暂停
		 */
		public static final int PAUSE = 1;
		/**
		 * 终止
		 */
		public static final int STOP = 9;

		private ContractStateEnum(){
			super.putEnum(NORMAL, "正常");
			super.putEnum(PAUSE, "暂停");
			super.putEnum(STOP, "终止");
		}
	}
	
	/**
	 * 枚举 资源类型
	* 
	* Title: ContractStateEnum
	* @author Rob
	 */
	public static class ResourceTypeEnum extends BaseEnum<Object>{

		private static final long serialVersionUID = 1L;
		/**
		 * 机房
		 */
		public static final int ROOM = 0;
		/**
		 * 资源点
		 */
		public static final int RESOURCE = 1;
		/**
		 * 热点
		 */
		public static final int HOT = 2;
		/**
		 * 位置点
		 */
		public static final int LOCATION = 3;
		

		private ResourceTypeEnum(){
			super.putEnum(ROOM, "机房");
			super.putEnum(RESOURCE, "资源点");
			super.putEnum(HOT, "热点");
			super.putEnum(LOCATION, "位置点");
		}
	}
	
}
