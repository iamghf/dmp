package com.ghf.dmp.bean;

public class ThreadCommonQryResp {
	private DmpCommQryResp[] resp;
	private String threadName;
	private StaffInfoDo staff;

	public DmpCommQryResp[] getResp() {
		return resp;
	}

	public void setResp(DmpCommQryResp[] resp) {
		this.resp = resp;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public StaffInfoDo getStaff() {
		return staff;
	}

	public void setStaff(StaffInfoDo staff) {
		this.staff = staff;
	}
}
