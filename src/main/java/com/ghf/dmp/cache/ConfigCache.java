package com.ghf.dmp.cache;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghf.boot.service.DmpService;
import com.ghf.dmp.bean.SysParaDo;
import com.ghf.dmp.constant.DmpConst;

public class ConfigCache {
	private static final Logger log = LoggerFactory.getLogger(ConfigCache.class);
	private static final Map<String, Object> cacheData = new HashMap<String, Object>();
	private static final String TRUE = "true";

	static {
		try {
			init();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private static void initFromDb()throws Exception {
		DmpService sv = new DmpService();
		SysParaDo[] paras = sv.getAllSysPara();
		for(SysParaDo para :paras) {
			cacheData.put(para.getCode(), para.getValue());
		}
	}
	
	private static void initFromFile()throws Exception {
		// InputStream in = ConfigCache.class.getClassLoader().getResourceAsStream("acct.properties");
		Properties p = getProps("config/acct.properties");
		
		cacheData.put(DmpConst.ConfigKey.UserName, p.getProperty(DmpConst.ConfigKey.UserName));
		cacheData.put(DmpConst.ConfigKey.PassWord, p.getProperty(DmpConst.ConfigKey.PassWord));
		
		cacheData.put(DmpConst.ConfigKey.CaptchaUrl, p.getProperty(DmpConst.ConfigKey.CaptchaUrl));
		cacheData.put(DmpConst.ConfigKey.LoginUrl, p.getProperty(DmpConst.ConfigKey.LoginUrl));
		cacheData.put(DmpConst.ConfigKey.SearchUrl, p.getProperty(DmpConst.ConfigKey.SearchUrl));
		cacheData.put(DmpConst.ConfigKey.StaffUrl, p.getProperty(DmpConst.ConfigKey.StaffUrl));
		cacheData.put(DmpConst.ConfigKey.ComonQryUrl, p.getProperty(DmpConst.ConfigKey.ComonQryUrl));
		
		cacheData.put(DmpConst.ConfigKey.DiffDay, p.getProperty(DmpConst.ConfigKey.DiffDay));

		cacheData.put(DmpConst.ConfigKey.IsCheckStaff, p.getProperty(DmpConst.ConfigKey.IsCheckStaff, TRUE));
		cacheData.put(DmpConst.ConfigKey.IsShowFixedBug, p.getProperty(DmpConst.ConfigKey.IsShowFixedBug, TRUE));
		cacheData.put(DmpConst.ConfigKey.IsShowReq, p.getProperty(DmpConst.ConfigKey.IsShowReq, TRUE));
		cacheData.put(DmpConst.ConfigKey.IsShowToFixBug, p.getProperty(DmpConst.ConfigKey.IsShowToFixBug, TRUE));
		
		//cacheData.put(DmpConst.ConfigKey.StaffList, initList(p.getProperty(DmpConst.ConfigKey.StaffList, ""),"config/stafflist.properties"));
		//cacheData.put(DmpConst.ConfigKey.SkipStaffList, initList(p.getProperty(DmpConst.ConfigKey.SkipStaffList, ""),"config/skipstafflist.properties"));
	}

	private static void init() throws Exception {
		Properties p = getProps("config/acct.properties");
		if(StringUtils.equalsIgnoreCase(TRUE, p.getProperty("is_user_db"))) {
			initFromDb();
		}else {
			initFromFile();
		}
	}

	private static List<Map<String, String>> initList(String str, String fileName) {
		if (StringUtils.isBlank(str)) {
			str = initListFromFile(fileName);
		}
		return parseList(str);
	}

	private static String initListFromFile(String fileName) {
		Properties p = getProps(fileName);
		Enumeration<?> keys = p.propertyNames();
		StringBuilder sb = new StringBuilder();
		while(keys.hasMoreElements()){
		    sb.append(keys.nextElement()).append(DmpConst.Spilt.SPILT_1);
		}
		return sb.length()>0 ? sb.substring(0, sb.length()-1) : sb.toString();
	}
	
	private static Properties getProps(String fileName) {
		Properties p = new Properties();
		try {
			p.load(new InputStreamReader(ConfigCache.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return p;
	}

	private static List<Map<String, String>> parseList(String staffStrs) {
		String[] staffs = staffStrs.split(DmpConst.Spilt.SPILT_1);
		List<Map<String, String>> staffList = new ArrayList<Map<String, String>>();
		if (StringUtils.isBlank(staffStrs)) {
			return staffList;
		}
		for (String staff : staffs) {
			String[] staffValue = staff.split(DmpConst.Spilt.SPILT_2);
			Map<String, String> staffMap = new HashMap<String, String>();
			staffMap.put(staffValue[0], staffValue[1]);
			staffList.add(staffMap);
		}
		return staffList;
	}

	public static boolean getBoolean(String key) {
		return StringUtils.equalsIgnoreCase(TRUE, getStrValue(key));
	}

	public static String getStrValue(String key) {
		return cacheData.get(key) + "";
	}
	
	public static int getIntValue(String key) {
		return getIntValue(key,0);
	}
	
	public static int getIntValue(String key , int defaultValue) {
		return StringUtils.isBlank(getStrValue(key))?defaultValue:Integer.parseInt(getStrValue(key));
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getList(String key) {
		return (List<Map<String, String>>) cacheData.get(key);
	}

	public static String getStaffNames() {
		return getNames(DmpConst.ConfigKey.StaffList);
	}
	
	public static String getSkipStaffNames() {
		return getNames(DmpConst.ConfigKey.SkipStaffList);
	}
	
	private static String getNames(String key) {
		List<Map<String, String>> staffList = getList(key);
		StringBuilder sb = new StringBuilder(1024);
		for (Map<String, String> staff : staffList) {
			for (String staffName : staff.values()) {
				sb.append(staffName).append(DmpConst.Spilt.SPILT_3);
			}
		}
		return sb.toString();
	}
}
