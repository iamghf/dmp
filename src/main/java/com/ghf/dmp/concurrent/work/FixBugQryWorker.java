package com.ghf.dmp.concurrent.work;

import java.util.concurrent.Callable;

import com.ghf.dmp.bean.CommonQryReq;
import com.ghf.dmp.bean.StaffInfoDo;
import com.ghf.dmp.bean.ThreadCommonQryResp;
import com.ghf.dmp.service.DmpReqService;

public class FixBugQryWorker implements Callable<ThreadCommonQryResp> {
	private StaffInfoDo staff;
	private CommonQryReq reqData;

	public FixBugQryWorker(StaffInfoDo staff,CommonQryReq reqData) {
		this.staff = staff;
		this.reqData = reqData;
	}
	@Override
	public ThreadCommonQryResp call() throws Exception {
		ThreadCommonQryResp resp = new ThreadCommonQryResp();
		DmpReqService service = new DmpReqService();
		resp.setStaff(this.staff);
		resp.setResp(service.query(this.staff,this.reqData));
		return resp;
	}

}
