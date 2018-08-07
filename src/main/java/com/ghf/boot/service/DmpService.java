package com.ghf.boot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghf.boot.SpringUtils;
import com.ghf.boot.domain.bean.SumInfo;
import com.ghf.dmp.bean.CommonQryReq;
import com.ghf.dmp.bean.DmpCommQryResp;
import com.ghf.dmp.bean.StaffInfoDo;
import com.ghf.dmp.bean.SysParaDo;
import com.ghf.dmp.bean.ThreadCommonQryResp;
import com.ghf.dmp.concurrent.work.CommonQryWorker;
import com.ghf.dmp.concurrent.work.FixBugQryWorker;
import com.ghf.dmp.constant.DmpConst;
import com.ghf.dmp.dao.StaffInfoDao;
import com.ghf.dmp.dao.SysParaInfoDao;

public class DmpService {
	private static final Logger log = LoggerFactory.getLogger(DmpService.class);

	public SumInfo[] initDmp() throws Exception {
		return this.getSumInfo(false);
	}
	
	public SumInfo[] getFixBugInfo(String realEndDate1,String realEndDate2) throws Exception{
		StaffInfoDo[] staffs = this.getStaffs();
		int initLen =	staffs.length;
		BlockingQueue<StaffInfoDo> que = new ArrayBlockingQueue<StaffInfoDo>(initLen);
		for (StaffInfoDo staff : staffs) {
			que.add(staff);
		}
		ExecutorService pool = Executors.newScheduledThreadPool(6);
		StaffInfoDo staff = null;
		List<Future<ThreadCommonQryResp>> results = new ArrayList<Future<ThreadCommonQryResp>>();
		while ((staff = que.poll()) != null) {
			CommonQryReq reqData = new CommonQryReq.CommonQryReqBuilder().index(0).size(9999)
					.productID(DmpConst.ProductId.CRM_GuangZhou).includeChildrenModule("true")
					.type(DmpConst.ProductId.CRM_GuangZhou).res(staff.getQid() + "").delayTaskNode("false")
					.realEndDate1(realEndDate1).realEndDate2(realEndDate2)/*.bizType(DmpConst.BizType.BUG.bid+"")*/.taskNodeStatus("2")
					.showCloseTag("true").cearteCommonQryReq();
			FixBugQryWorker myWork = new FixBugQryWorker(staff,reqData);
			results.add(pool.submit(myWork));
		}

		List<SumInfo> allSumList = new ArrayList<SumInfo>();

		for (Future<ThreadCommonQryResp> future : results) {
			ThreadCommonQryResp resp = future.get();
			allSumList.add(this.buildSumInfo(resp,true));
		}
		pool.shutdown();

		return allSumList.toArray(new SumInfo[0]);
	}

	private SumInfo[] getSumInfo(boolean needShowNoCurrentTask) throws Exception {
		StaffInfoDo[] staffs = this.getStaffs();
		int initLen =	staffs.length;
		BlockingQueue<StaffInfoDo> que = new ArrayBlockingQueue<StaffInfoDo>(initLen);
		for (StaffInfoDo staff : staffs) {
			que.add(staff);
		}
		ExecutorService pool = Executors.newScheduledThreadPool(6);
		StaffInfoDo staff = null;
		List<Future<ThreadCommonQryResp>> results = new ArrayList<Future<ThreadCommonQryResp>>();
		while ((staff = que.poll()) != null) {
			CommonQryWorker myWork = new CommonQryWorker(staff);
			results.add(pool.submit(myWork));
		}

		List<SumInfo> allSumList = new ArrayList<SumInfo>();

		for (Future<ThreadCommonQryResp> future : results) {
			ThreadCommonQryResp resp = future.get();
			allSumList.add(this.buildSumInfo(resp,needShowNoCurrentTask));
		}
		pool.shutdown();

		return allSumList.toArray(new SumInfo[0]);
	}
	
	private SumInfo buildSumInfo(ThreadCommonQryResp resp,boolean needShowNoCurrentTask) {
		SumInfo single = new SumInfo(); // 个人情况

		DmpCommQryResp[] reqs = resp.getResp();
		int len = ArrayUtils.isEmpty(reqs) ? 0 : reqs.length;
		int count = 0;
		int reqCount = 0;
		int bugCount = 0;
		int dbCount = 0;
		for (int i = 0; i < len; i++) {
			if (!needShowNoCurrentTask && StringUtils.equalsIgnoreCase("否", reqs[i].getCurrentTask())) {
				continue;
			}
			log.info(reqs[i].getReqMatrixListNum() + "    " + reqs[i].getTaskNodeName());
			count++;
			if (StringUtils.equalsIgnoreCase(DmpConst.BizType.DB.name, reqs[i].getBizType())) {
				dbCount++;
				single.addDbDetail(reqs[i]);
			} else if (StringUtils.equalsIgnoreCase(DmpConst.BizType.REQ.name, reqs[i].getBizType())) {
				reqCount++;
				single.addReqDetail(reqs[i]);
			} else if (StringUtils.equalsIgnoreCase(DmpConst.BizType.BUG.name, reqs[i].getBizType())) {
				bugCount++;
				single.addBugDetail(reqs[i]);
			} else {
				single.addOtherDetail(reqs[i]);
			}
		}
		log.info(resp.getStaff().getCode() + " dmp total is : " + count);
		log.info("------------------------------\r\n");

		single.setName(resp.getStaff().getName());
		single.setReqNum(reqCount);
		single.setBugNum(bugCount);

		return single;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> queryAllStaff()throws Exception {
		List<Map> list = new ArrayList<Map>();
		StaffInfoDo[] values = this.getStaffs();
		for(StaffInfoDo value : values) {
			Map map = new HashMap();
			map.put("id", value.getId());
			map.put("code", value.getCode());
			map.put("name", value.getName());
			map.put("email", value.getEmail());
			list.add(map);
		}
		return list;
	}
	
	private StaffInfoDo[] getStaffs()throws Exception {
		StaffInfoDao dao = (StaffInfoDao) SpringUtils.getBean(StaffInfoDao.class);
		List<StaffInfoDo> values = dao.getStaffByCode(null);
		if(null != values && !values.isEmpty()){
			return values.toArray(new StaffInfoDo[0]);
		}
		return new StaffInfoDo[0];
	}
	
	public SysParaDo[] getAllSysPara()throws Exception {
		SysParaInfoDao dao = (SysParaInfoDao) SpringUtils.getBean(SysParaInfoDao.class);
		List<SysParaDo> values = dao.getSysPara();
		if(null != values && !values.isEmpty()){
			return values.toArray(new SysParaDo[0]);
		}
		return new SysParaDo[0];
	}
}
