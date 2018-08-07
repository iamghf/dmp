package com.ghf.dmp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghf.dmp.bean.CommonQryReq;
import com.ghf.dmp.bean.DmpCommQryResp;
import com.ghf.dmp.bean.StaffInfoDo;
import com.ghf.dmp.bean.UnionQueryBean;
import com.ghf.dmp.cache.ConfigCache;
import com.ghf.dmp.constant.DmpConst;
import com.ghf.dmp.http.common.HttpExcute;

public class DmpReqService {
	private static Logger log = LoggerFactory.getLogger(DmpReqService.class);

	/**
	 * 查询当前（未完成）所有任务单（bug，需求，db等）
	 * @param staff
	 * @return
	 * @throws Exception
	 */
	public DmpCommQryResp[] queryReqByCommon(StaffInfoDo staff) throws Exception {
		CommonQryReq reqData = new CommonQryReq.CommonQryReqBuilder().index(0).size(9999)
				.productID(DmpConst.ProductId.CRM_GuangZhou).includeChildrenModule("true")
				.type(DmpConst.ProductId.CRM_GuangZhou).res(staff.getQid()+"").delayTaskNode("false").taskNodeStatus("1")
				.showCloseTag("false").cearteCommonQryReq();
		return this.query(staff,reqData);
	}
	
	public DmpCommQryResp[] query(StaffInfoDo staff,CommonQryReq reqData) throws Exception {
		return this.doQuery(reqData);
	}
	
	public DmpCommQryResp[] doQuery(CommonQryReq reqData) throws Exception {
		UnionQueryBean reqBean = new UnionQueryBean();
		reqBean.setData(reqData);
		reqBean.setUrl(ConfigCache.getStrValue(DmpConst.ConfigKey.ComonQryUrl));
		return this.doUnionQuery(reqBean);
	}
	
	public DmpCommQryResp[] doUnionQuery(UnionQueryBean reqBean) throws Exception {
		List<DmpCommQryResp> resps =  HttpExcute.doRequest(reqBean, DmpCommQryResp.class);
		return resps.toArray(new DmpCommQryResp[0]);
	}

	public static void main(String[] args) throws Exception {
		//DmpReqService sv = new DmpReqService();
		//DmpCommQryResp[] resps = sv.queryReqByCommon(DmpConst.StaffFull.chenjiao3);
	}
}
