package com.ghf.dmp;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghf.dmp.concurrent.task.DmpUnionQryTask;

public class MainFrame {
	private static Logger log = LoggerFactory.getLogger(MainFrame.class);
	public static void main(String[] args)throws Exception{
		invoke();
	}
	
	public static void invoke() throws InterruptedException, ExecutionException {
		DmpUnionQryTask task = new DmpUnionQryTask();
		long startTime = System.currentTimeMillis();
		//task.getAllReqInfoByCommon();
		log.info("总耗时："+(System.currentTimeMillis()-startTime));
	}
}
