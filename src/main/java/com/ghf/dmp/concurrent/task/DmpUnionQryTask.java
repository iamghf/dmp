package com.ghf.dmp.concurrent.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghf.dmp.bean.DmpCommQryResp;
import com.ghf.dmp.bean.ThreadCommonQryResp;
import com.ghf.dmp.concurrent.work.CommonQryWorker;
import com.ghf.dmp.constant.DmpConst;

/**
 * @author ghf
 *
 */
public class DmpUnionQryTask {
	private static final Logger log = LoggerFactory.getLogger(DmpUnionQryTask.class);
	/*public void getAllReqInfoByCommon() throws InterruptedException, ExecutionException {
		int initLen = DmpConst.StaffFull.values().length;
		BlockingQueue<DmpConst.StaffFull> que = new ArrayBlockingQueue<DmpConst.StaffFull>(initLen);
		for (DmpConst.StaffFull staff : DmpConst.StaffFull.values()) {
			if (StringUtils.equalsIgnoreCase("xiongxy3", staff.name())) {
				//continue;
			}
			que.add(staff);
		}
		ExecutorService pool = Executors.newScheduledThreadPool(6);
		//ExecutorService pool = Executors.newCachedThreadPool();
		DmpConst.StaffFull staff =null;
		List<Future<ThreadCommonQryResp>> results = new ArrayList<Future<ThreadCommonQryResp>>();
		while((staff = que.poll())!=null) {
			CommonQryWorker myWork = new CommonQryWorker(staff);
			results.add(pool.submit(myWork));
		}
		for(Future<ThreadCommonQryResp> future: results) {
			ThreadCommonQryResp resp = future.get();
			DmpCommQryResp[] reqs = resp.getResp();
			int len = ArrayUtils.isEmpty(reqs)?0:reqs.length;
			int count =0;
			int reqCount = 0;
			int bugCount = 0;
			int dbCount = 0;
			List<DmpCommQryResp> reqList = new ArrayList<DmpCommQryResp>();
			List<DmpCommQryResp> bugList = new ArrayList<DmpCommQryResp>();
			List<DmpCommQryResp> dbList = new ArrayList<DmpCommQryResp>();
			List<DmpCommQryResp> otherList = new ArrayList<DmpCommQryResp>();
			for(int i=0;i<len;i++) {
				if(StringUtils.equalsIgnoreCase("否", reqs[i].getCurrentTask())) {
					continue;
				}
				log.info(reqs[i].getReqMatrixListNum() + "    " + reqs[i].getTaskNodeName());
				count++;
				if(StringUtils.equalsIgnoreCase(DmpConst.BizType.DB, reqs[i].getBizType())) {
					dbCount++;
					dbList.add(reqs[i]);
				}else if(StringUtils.equalsIgnoreCase(DmpConst.BizType.REQ, reqs[i].getBizType())) {
					reqCount++;
					reqList.add(reqs[i]);
				}else if(StringUtils.equalsIgnoreCase(DmpConst.BizType.BUG, reqs[i].getBizType())) {
					bugCount++;
					bugList.add(reqs[i]);
				}else {
					otherList.add(reqs[i]);
				}
			}
			log.info(resp.getStaff().name() + " dmp total is : " + count);
			log.info(resp.getStaff().name() + " db total is : " + dbCount);
			for(DmpCommQryResp tmp : dbList) {
				log.info(tmp.getReqMatrixListNum() + "    " + tmp.getTaskNodeName());
			}
			log.info(resp.getStaff().name() + " req total is : " + reqCount);
			for(DmpCommQryResp tmp : reqList) {
				log.info(tmp.getReqMatrixListNum() + "    " + tmp.getTaskNodeName());
			}
			log.info(resp.getStaff().name() + " bug total is : " + bugCount);
			for(DmpCommQryResp tmp : bugList) {
				log.info(tmp.getReqMatrixListNum() + "    " + tmp.getTaskNodeName());
			}
			log.info(resp.getStaff().name() + " other total is : " + (count-bugCount-reqCount-dbCount));
			for(DmpCommQryResp tmp : otherList) {
				log.info(tmp.getReqMatrixListNum() + "    " + tmp.getTaskNodeName());
			}
			log.info("------------------------------\r\n");
		}
		
		pool.shutdown();
	}*/
}
