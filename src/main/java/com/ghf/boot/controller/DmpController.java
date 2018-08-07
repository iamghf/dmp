package com.ghf.boot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ghf.boot.constant.BootConst;
import com.ghf.boot.domain.bean.SumInfo;
import com.ghf.boot.service.DmpService;
import com.ghf.dmp.bean.DmpCommQryResp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class DmpController {
	@RequestMapping(value = "/dmp", method = { RequestMethod.GET })
	public ModelAndView dmp(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

	@RequestMapping(value = "/doDmpQuery", method = { RequestMethod.GET })
	public ModelAndView doDmpQuery(HttpServletRequest request) {
		String freshFlag = request.getParameter("flag"); // 0-不刷新
		ModelAndView mv = new ModelAndView("dmp");

		Map ret = this.buildResp(this.getDmpToDoInfo(freshFlag, request.getSession()));

		mv.addObject("categories", ret.get("categories"));
		mv.addObject("reqdata", ret.get("reqdata"));
		mv.addObject("bugdata", ret.get("bugdata"));
		mv.addObject("Funnel", ret.get("Funnel"));
		mv.addObject("name", "ffff");
		return mv;
	}

	@RequestMapping(value = "/doDmpQueryDetail", method = { RequestMethod.GET })
	public ModelAndView doDmpQueryDetail(HttpServletRequest request) {
		String freshFlag = request.getParameter("flag"); // 0-不刷新
		ModelAndView mv = new ModelAndView("dmpdtl");
		
		Map ret = this.buildResp(this.getDmpToDoInfo(freshFlag, request.getSession()));
		
		mv.addObject("reqList", ret.get("reqList"));
		return mv;
	}
	
	@RequestMapping(value = "/menuFixBug", method = { RequestMethod.GET })
	public ModelAndView menuFixBug(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index2");
		return mv;
	}
	
	@RequestMapping(value = "/doDmpQueryFixBug", method = { RequestMethod.GET })
	public ModelAndView doDmpQueryFixBug(HttpServletRequest request) {
		String freshFlag = request.getParameter("flag"); // 0-不刷新
		String realEndDate1 = request.getParameter("date1");
		String realEndDate2 = request.getParameter("date2");
		
		Map ret = this.buildResp(this.getFixBug(freshFlag,realEndDate1,realEndDate2, request.getSession()));
		
		ModelAndView mv = new ModelAndView("dmp");
		mv.addObject("categories", ret.get("categories"));
		mv.addObject("reqdata", ret.get("reqdata"));
		mv.addObject("bugdata", ret.get("bugdata"));
		mv.addObject("Funnel", ret.get("Funnel"));
		mv.addObject("reqList", ret.get("reqList"));
		return mv;
	} 
	
	private SumInfo[] getFixBug(String freshFlag,String realEndDate1,String realEndDate2,HttpSession session) {
		SumInfo[] allDatas = null;
		if (StringUtils.equalsAnyIgnoreCase(BootConst.NEED_FRESH, freshFlag)) {
			allDatas = (SumInfo[]) this.getDataFromSession(BootConst.SessionKey.DMP_FIX_BUG, session);
		}
		if (ArrayUtils.isEmpty(allDatas)) {
			try {
				DmpService sv = new DmpService();
				allDatas = sv.getFixBugInfo(realEndDate1, realEndDate2);
			} catch (Exception ex) {

			}
			this.addDataToSession(BootConst.SessionKey.DMP_FIX_BUG, allDatas, session);
		}
		return allDatas;
	}
	
	private SumInfo[] getDmpToDoInfo(String freshFlag,HttpSession session) {
		SumInfo[] allDatas = null;
		if (StringUtils.equalsAnyIgnoreCase(BootConst.NEED_FRESH, freshFlag)) {
			allDatas = (SumInfo[]) this.getDataFromSession(BootConst.SessionKey.DMP_REQ, session);
		}
		if (ArrayUtils.isEmpty(allDatas)) {
			try {
				DmpService sv = new DmpService();
				allDatas = sv.initDmp();
			} catch (Exception ex) {

			}
			this.addDataToSession(BootConst.SessionKey.DMP_REQ, allDatas, session);
		}
		return allDatas;
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private Map buildResp(SumInfo[] allDatas) {
		if (ArrayUtils.isEmpty(allDatas)) {
			return new HashMap();
		}
		JSONArray cates = new JSONArray();
		JSONArray datas = new JSONArray();
		JSONArray datas2 = new JSONArray();
		JSONArray data3 = new JSONArray(); // 金字塔

		List<Map> detailList = new ArrayList<Map>();

		for (SumInfo allData : allDatas) {
			cates.put(allData.getName());
			datas.put(allData.getReqNum());
			datas2.put(allData.getBugNum());
			JSONObject tmp = new JSONObject();
			tmp.put("name", allData.getName());
			tmp.put("value", allData.getReqNum());
			data3.put(tmp);

			for (DmpCommQryResp resp : allData.getReqDetail()) {
				Map detail = new HashMap();
				detail.put("name", allData.getName());
				detail.put("projectName", resp.getProjectName());
				detail.put("taskNo", resp.getReqMatrixListNum());
				// detail.put("taskDesc", resp.getTaskNodeName());
				detail.put("taskName", resp.getReqMatrixListName());
				detail.put("bizType", resp.getBizType());
				detailList.add(detail);
			}

			for (DmpCommQryResp resp : allData.getBugDetail()) {
				Map detail = new HashMap();
				detail.put("name", allData.getName());
				detail.put("projectName", resp.getProjectName());
				detail.put("taskNo", resp.getReqMatrixListNum());
				// detail.put("taskDesc", resp.getTaskNodeName());
				detail.put("taskName", resp.getReqMatrixListName());
				detail.put("bizType", resp.getBizType());
				detailList.add(detail);
			}
		}

		Map ret = new HashMap();
		ret.put("categories", cates.toString());
		ret.put("reqdata", datas.toString());
		ret.put("bugdata", datas2.toString());
		ret.put("Funnel", data3);
		ret.put("reqList", detailList);
		return ret;
	}

	private Object getDataFromSession(String key, HttpSession session) {
		if (null == session) {
			return null;
		}
		return session.getAttribute(key);
	}

	private void addDataToSession(String key, Object data, HttpSession session) {
		session.setAttribute(key, data);
	}
	
	@RequestMapping(value = "/queryStaff", method = { RequestMethod.GET })
	public ModelAndView queryStaff(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("staffdtl");
		DmpService service = new DmpService();
		List<Map> list = service.queryAllStaff();
		mv.addObject("staffList", list);
		return mv;
	} 
}
