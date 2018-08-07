package com.ghf.boot.domain.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ghf.dmp.bean.DmpCommQryResp;

public class SumInfo implements Serializable{
	private static final long serialVersionUID = -8639658305249362908L;
	
	private String name;
	private int reqNum;
	private int bugNum;
	
	private List<DmpCommQryResp> reqDetail = new ArrayList<DmpCommQryResp>();
	
	private List<DmpCommQryResp> bugDetail = new ArrayList<DmpCommQryResp>();
	
	private List<DmpCommQryResp> dbDetail = new ArrayList<DmpCommQryResp>();
	
	private List<DmpCommQryResp> ohterDetail = new ArrayList<DmpCommQryResp>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getReqNum() {
		return reqNum;
	}
	public void setReqNum(int reqNum) {
		this.reqNum = reqNum;
	}
	public int getBugNum() {
		return bugNum;
	}
	public void setBugNum(int bugNum) {
		this.bugNum = bugNum;
	}
	public List<DmpCommQryResp> getReqDetail() {
		return Collections.unmodifiableList(reqDetail);
	}
	public void addReqDetail(DmpCommQryResp req) {
		this.reqDetail.add(req);
	}
	
	public List<DmpCommQryResp> getDbDetail() {
		return Collections.unmodifiableList(dbDetail);
	}
	public void addDbDetail(DmpCommQryResp db) {
		this.dbDetail.add(db);
	}
	
	public List<DmpCommQryResp> getBugDetail() {
		return Collections.unmodifiableList(bugDetail);
	}
	public void addBugDetail(DmpCommQryResp bug) {
		this.bugDetail.add(bug);
	}
	
	public List<DmpCommQryResp> getOtherDetail() {
		return Collections.unmodifiableList(ohterDetail);
	}
	public void addOtherDetail(DmpCommQryResp ohter) {
		this.ohterDetail.add(ohter);
	}
	
}
