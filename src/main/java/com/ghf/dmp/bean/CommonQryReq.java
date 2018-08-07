package com.ghf.dmp.bean;

/**
 * @author ghf 统一查询 条件参数
 *
 */
public class CommonQryReq {
	private int index;
	private int size;
	private String productID;
	private String reqMatrixId;
	private String isIncludeChildrenModule;
	private String type;
	private String res; // 查询工号
	private String delayTaskNode;
	private String planEndDate1;
	private String planEndDate2;
	private String realEndDate1;
	private String realEndDate2;
	private String taskNodeStatus;
	private String bizType;
	private String bizNum;
	private String showCloseTag;

	private CommonQryReq(int index, int size, String productID, String reqMatrixId, String isIncludeChildrenModule,
			String type, String res, String delayTaskNode, String planEndDate1, String planEndDate2,
			String realEndDate1, String realEndDate2, String taskNodeStatus, String bizType, String bizNum,
			String showCloseTag) {
		this.index = index;
		this.size = size;
		this.productID = productID;
		this.reqMatrixId = reqMatrixId;
		this.isIncludeChildrenModule = isIncludeChildrenModule;
		this.type = type;
		this.res = res;
		this.planEndDate1 = planEndDate1;
		this.planEndDate2 = planEndDate2;
		this.realEndDate1 = realEndDate1;
		this.realEndDate2 = realEndDate2;
		this.delayTaskNode = delayTaskNode;
		this.taskNodeStatus = taskNodeStatus;
		this.bizType = bizType;
		this.bizNum = bizNum;
		this.showCloseTag = showCloseTag;
	}

	public static class CommonQryReqBuilder {
		private int index;
		private int size;
		private String productID;
		private String reqMatrixId;
		private String isIncludeChildrenModule;
		private String type;
		private String res; // 查询工号
		private String delayTaskNode;
		private String taskNodeStatus;
		private String planEndDate1;
		private String planEndDate2;
		private String realEndDate1;
		private String realEndDate2;
		private String bizType;
		private String bizNum;
		private String showCloseTag;

		public CommonQryReqBuilder index(int index) {
			this.index = index;
			return this;
		}

		public CommonQryReqBuilder size(int size) {
			this.size = size;
			return this;
		}

		public CommonQryReqBuilder productID(String productID) {
			this.productID = productID;
			return this;
		}

		public CommonQryReqBuilder reqMatrixId(String reqMatrixId) {
			this.reqMatrixId = reqMatrixId;
			return this;
		}

		public CommonQryReqBuilder includeChildrenModule(String isIncludeChildrenModule) {
			this.isIncludeChildrenModule = isIncludeChildrenModule;
			return this;
		}

		public CommonQryReqBuilder type(String type) {
			this.type = type;
			return this;
		}

		public CommonQryReqBuilder res(String res) {
			this.res = res;
			return this;
		}

		public CommonQryReqBuilder delayTaskNode(String delayTaskNode) {
			this.delayTaskNode = delayTaskNode;
			return this;
		}

		public CommonQryReqBuilder taskNodeStatus(String taskNodeStatus) {
			this.taskNodeStatus = taskNodeStatus;
			return this;
		}

		public CommonQryReqBuilder bizType(String bizType) {
			this.bizType = bizType;
			return this;
		}

		public CommonQryReqBuilder bizNum(String bizNum) {
			this.bizNum = bizNum;
			return this;
		}

		public CommonQryReqBuilder showCloseTag(String showCloseTag) {
			this.showCloseTag = showCloseTag;
			return this;
		}

		public CommonQryReqBuilder planEndDate1(String planEndDate1) {
			this.planEndDate1 = planEndDate1;
			return this;
		}

		public CommonQryReqBuilder planEndDate2(String planEndDate2) {
			this.planEndDate2 = planEndDate2;
			return this;
		}

		public CommonQryReqBuilder realEndDate1(String realEndDate1) {
			this.realEndDate1 = realEndDate1;
			return this;
		}

		public CommonQryReqBuilder realEndDate2(String realEndDate2) {
			this.realEndDate2 = realEndDate2;
			return this;
		}

		public CommonQryReq cearteCommonQryReq() {
			return new CommonQryReq(this.index, this.size, this.productID, this.reqMatrixId,
					this.isIncludeChildrenModule, this.type, this.res, this.delayTaskNode, this.planEndDate1,
					this.planEndDate2, this.realEndDate1, this.realEndDate2, this.taskNodeStatus, this.bizType,
					this.bizNum, this.showCloseTag);
		}
	}

	public int getIndex() {
		return index;
	}

	public int getSize() {
		return size;
	}

	public String getProductID() {
		return productID;
	}

	public String getReqMatrixId() {
		return reqMatrixId;
	}

	public String getIncludeChildrenModule() {
		return isIncludeChildrenModule;
	}

	public String getType() {
		return type;
	}

	public String getRes() {
		return res;
	}

	public String getDelayTaskNode() {
		return delayTaskNode;
	}

	public String getTaskNodeStatus() {
		return taskNodeStatus;
	}

	public String getBizType() {
		return bizType;
	}

	public String getBizNum() {
		return bizNum;
	}

	public String getShowCloseTag() {
		return showCloseTag;
	}

	public String getPlanEndDate1() {
		return planEndDate1;
	}

	public String getPlanEndDate2() {
		return planEndDate2;
	}

	public String getRealEndDate1() {
		return realEndDate1;
	}

	public String getRealEndDate2() {
		return realEndDate2;
	}
}
