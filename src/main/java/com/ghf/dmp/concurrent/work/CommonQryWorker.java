package com.ghf.dmp.concurrent.work;

import java.util.concurrent.Callable;

import com.ghf.dmp.bean.StaffInfoDo;
import com.ghf.dmp.bean.ThreadCommonQryResp;
import com.ghf.dmp.service.DmpReqService;

public class CommonQryWorker implements Callable<ThreadCommonQryResp> {
	private StaffInfoDo staff;

	public CommonQryWorker(StaffInfoDo staff) {
		this.staff = staff;
	}
	@Override
	public ThreadCommonQryResp call() throws Exception {
		ThreadCommonQryResp resp = new ThreadCommonQryResp();
		DmpReqService service = new DmpReqService();
		resp.setStaff(this.staff);
		resp.setResp(service.queryReqByCommon(this.staff));
		return resp;
	}

}
