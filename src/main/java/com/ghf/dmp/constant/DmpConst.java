package com.ghf.dmp.constant;

/**
 * @author ghf
 *
 */
public class DmpConst {
	public interface ConfigKey {
		public static final String UserName = "userName";
		public static final String PassWord = "password";
		public static final String IsCheckStaff = "is_check_staff";
		public static final String IsShowFixedBug = "is_show_fixed_bug";
		public static final String IsShowReq = "is_show_req_list";
		public static final String IsShowToFixBug = "is_show_to_fix_bug";

		public static final String CaptchaUrl = "CaptchaUrl";
		public static final String LoginUrl = "LoginUrl";
		public static final String SearchUrl = "SearchUrl";
		public static final String StaffUrl = "StaffUrl";
		public static final String ComonQryUrl = "ComonQryUrl";

		public static final String DiffDay = "DiffDay";

		public static final String StaffList = "staffList";
		public static final String SkipStaffList = "skip_staff_list";
	}

	/** 产品项目 */
	public interface ProductId {
		/** CRM_广研 */
		public static final String CRM_GuangZhou = "8289408";
	}
	
	public enum BizType{
		//DB变更／客户需求／测试缺陷
		DB("DB变更",8),REQ("客户需求",7),BUG("测试缺陷",3);
		
		public int bid;
		public String name;
		
		BizType(String name, int bid) {
			this.name =name;
			this.bid = bid;
		}
	}
	
	/* 所属节点 */
	public interface Node {
		public static final String DB_REVIEW = "变更评审";
		public static final String DB_SUBMIT = "变更提出";
		
		public static final String BUG_REPAIR = "缺陷修复";
		
		public static final String REQ_DEVELOP = "需求开发";
		public static final String REQ_PM = "需求分析";
		public static final String REQ_SCCB = "后台需求接受";
	}

	public interface Spilt {
		public static final String SPILT_1 = ";";
		public static final String SPILT_2 = "@";
		public static final String SPILT_3 = ",";
	}

	public enum Staff {
		xiongxy3, 
		lizq10, 
		heal, 
/*		denghz, 
*/		chenjiao3, 
		zhangliang8, 
/*		wangyx, 
*/		wanghl9, 
/*		wangxd6, 
*/		yuxp3, 
		/*zhaohj3, 
		houxi, 
		xiazm, 
		laiwj, */
		licy_gy, 
		licy3, 
		zhaoyu, 
		hehx,
		yandz,
		zhuxy6,
		denglx,
		hetong,
		weisg,
		chenfang6,
		shenhao3,
		pangao,
		tenggang;
	}

	public enum StaffFullEnum {
		xiongxy3(4468884, "熊新宇"), 
		lizq10(44419251, "李忠强"), 
		heal(35500257, "贺爱莲"), 
		/*denghz(55386142, "邓慧智"), */
		chenjiao3(40287403, "陈郊"), 
		//zhangliang8(34348510, "张良"), 
		/*wangyx(41684029, "王育鑫"), */
		//wanghl9(41551430, "王华林"), 
		/*wangxd6(7567918, "王晓东"), */
		yuxp3(52118149, "于晓鹏"), 
		/*zhaohj3(31944510, "赵弘杰"), */
		zhaoyu(8606738, "赵羽"), 
		hehx(52326405, "何红星"), 
		licy3(57807529, "李超元"),
		//denglx(57807529, "邓磊鑫"),
		hetong(69513922, "何仝"),
		tenggang(4163438, "滕刚"),
		weisg(40351900, "魏书光"),
		yandz(67633782, "晏迪智"),
		chenfang6(44384366, "陈方"),
		pangao(57122566, "潘高"),
		shenhao3(61661798, "沈浩"),
		zhuxy6(67787180, "朱薪宇");
		
		public int value;
		public String cname;

		StaffFullEnum(int value, String cname) {
			this.value = value;
			this.cname = cname;
		}
	}

	public interface BugStatus {
		public static final String Repair = "bug修复";
	}
}
