package com.xunge.comm.enums;

public class ResourceEnum {

	public static final AuditStateEnum auditStateEnum = new AuditStateEnum();
	public static final ResStateEnum resStateEnum = new ResStateEnum();
	public static final ResBelongEnum resBelongEnum = new ResBelongEnum();
	public static final DataFromEnum datafromEnum = new DataFromEnum();
	public static final ContractStateEnum contractStateEnum = new ContractStateEnum();
	/**
	 * 枚举 审核状态
	* 
	* Title: AuditStateEnum
	* @author Rob
	 */
	public static class AuditStateEnum extends BaseEnum<Object>{

		private static final long serialVersionUID = 1L;
		/**
		 * 待审核
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
		 * 审核不通过
		 */
		public static final int AUDITFAIL = 8;

		private AuditStateEnum(){
			super.putEnum(PREAUDIT, "待审核");
			super.putEnum(AUDITING, "审核中");
			super.putEnum(AUDITSUCC, "审核通过");
			super.putEnum(AUDITFAIL, "审核不通过");
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
		 * 入网
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
			super.putEnum(ENTERNET, "入网");
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
		/**
		 * 移动
		 */
		public static final int YIDONG = 1;
		/**
		 * 铁塔
		 */
		public static final int TIETA = 2;

		private ResBelongEnum(){
			super.putEnum(YIDONG, "移动维护");
			super.putEnum(TIETA, "铁塔维护");
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
	
}
