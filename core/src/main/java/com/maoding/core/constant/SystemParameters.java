/**
 * 
 */
package com.maoding.core.constant;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**深圳市设计同道技术有限公司
 * 类    名：SystemParameters
 * 类描述：
 * 作    者：MaoSF
 * 日    期：2015年7月15日-上午10:43:55
 */

public interface SystemParameters {
	
	Locale DEFAULT_LOCALE = Locale.getDefault();
	String FILE_SEPARATOR = System.getProperty( "file.separator" );
	String FTP_FILE_SEPARATOR = "/";
	String UTF8="UTF-8";
	int DEFAULT_BUFFER=1024;
	/**验证码时效（2分钟）*/
	long SECURITY_CODE_MAX_LIVE_TIME=180000;
	/**验证码时效（10分钟）*/
	long SECURITY_CODE_10_MAX_LIVE_TIME=600000;
	/**令牌时效（一星期）*/
	long TOKEN_MAX_LIVE_TIME=604800000;
	/**验证码时效（2分钟）*/
	int SECURITY_CODE_MAX_LIVE_TIME_N=180000;
	/**验证码时效（10分钟）*/
	int SECURITY_CODE_10_MAX_LIVE_TIME_N=600000;
	/**令牌时效（一星期）*/
	int TOKEN_MAX_LIVE_TIME_N=604800000;

	/**
	 * session超时状态
	 */
	String SESSION_TIMEOUT_CODE = "401";

	/**
	 * 设计依据编码
	 */
	String PRO_DESIGNBASIC = "designBasic";
	/**
	 * 设计范围编码
	 */
	String PRO_DESIGNRANGE = "designRange";
	/**
	 * 设计阶段
	 */
	String PRO_DESIGNCONTENT = "designContent";

	/**
	 * 建筑功能
	 */
	String PRO_CONSTRUCTFUNCTION="zp-jzgn";
	/**
	 * 项目类别
	 */
	String PRO_Type="project-type";

	/**
	 * 专业
	 */
	String USER_MAJOR="zy";
	
	/**
	 * 任务管理
	 */
	String TASK_MANAGER="taskManager";
	

	//默认为建筑类型
	String PROJECT_TYPE_ID="1c6f48757e684b3cb059b94021e12baa";
	

	/**
	 * 系统管理员角色id
	 */
	String ADMIN_MANAGER_ROLE_ID ="2f84f20610314637a8d5113440c69bde";

	/**
	 * 企业负责人id
     */
	String ORG_MANAGER_ROLE_ID="23297de920f34785b7ad7f9f6f5fe9d1";
	

	/**----------------路径---------------------**/
	/**
	 * 富文本路径
	 */
	String PATHPREFIX = "file/upload/";
	
	/**
	 * 发送验证码短信模板
     */
	String SEND_CODE_MSG="【卯 丁】你的短信验证码为：?，将于10分钟后失效。";

	/**
	 * 添加组织人员（用户账号不存在）
	 */
	String ADD_COMPANY_USER_MSG_1 = "【卯 丁】?邀请你加入\"?\"，请访问 ? 注册使用。";

	/**
	 * 添加组织人员（用户账号已经存在）
     */
	String ADD_COMPANY_USER_MSG_2 = "【卯 丁】?邀请你加入\"?\"。";

	/**
	 * 分享邀请，组织同意加入，短信提示,（用户账号不存在）
     */
	String SHARE_INVITE_MSG_1 = "【卯 丁】\"?\"已审核通过你的加入申请，请访问 ? 注册使用。";

	/**
	 * 分享邀请，组织同意加入，短信提示,用户账号已经存在）
	 */
	String SHARE_INVITE_MSG_2 = "【卯 丁】\"?\"已审核通过你的加入申请。";

	/**
	 * 移交管理员，给新管理员发送短信模板
     */
	String TRANSFER_ADMIN_MSG="【卯 丁】你已成为\"?\"的管理员。";


	/**
	 * 创建分公司，合作伙伴，管理员没有账号的短信信息
     */
	//String CREATE_SUB_COMPANY_MSG_1 = "【卯 丁】?邀请你成为\"?\"-\"?\"的管理员，卯丁帐号：?，密码：?，组织管理密码：?。请访问 ? 注册使用。";

	String CREATE_SUB_COMPANY_MSG_1 = "【卯 丁】?邀请你成为\"?\"-\"?\"的管理员，卯丁帐号：?，密码：?。请访问 ? 注册使用。";


	String INVITE_PARENT_MSG = "【卯 丁】?邀请你的组织成为“?”的分支机构，请点击 ? 进行操作。";

	String INVITE_PARENT_MSG2 = "【卯 丁】?邀请你的组织成为“?”的事业合伙人，请点击 ? 进行操作。";
	/**
	 *创建分公司，合作伙伴，管理员有账号的短信信息
     */
	//String CREATE_SUB_COMPANY_MSG_2 = "【卯 丁】?邀请你成为\"?\"-\"?\"的管理员，组织管理密码：?。";
	String CREATE_SUB_COMPANY_MSG_2 = "【卯 丁】?邀请你成为\"?\"-\"?\"的管理员。";

	/**
	 * 移交管理员，给新管理员发送短信模板
	 */
    String TRANSFER_ORG_MSG="【卯 丁】你已被取消“?”企业负责人相关权限。如有疑问请联系?。";

	String[] DEPART_DEFAULT_IMG = {"common/img/depart/financial.png","common/img/depart/market.png","common/img/depart/operatingmanagement.png",
			"common/img/depart/product.png","common/img/depart/programcreation.png","common/img/depart/publicadministration.png","common/img/depart/technology.png"};


	/*****************任务类型******************/
	/*****************任务类型******************/
    int ISSUE_TASK = 1;//1.签发
	int PRODUCT_TASK_DESIGN = 2;//2.生产安排（技术负责人）
	int  PRODUCT_TASK= 12;//12.生产安排
	int PRODUCT_TASK_RESPONSE = 13;//生产安排（任务负责人）
	int PROCESS_DESIGN = 3;//3.设计，校对，审核
	int TECHNICAL_REVIEW_FEE_OPERATOR_MANAGER = 4;//4.技术审查费付款确认（经营负责人）
	int TECHNICAL_REVIEW_FEE_ORG_MANAGER = 5;//5.技术审查费付款确认（企业负责人）
	int COOPERATIVE_DESIGN_FEE_ORG_MANAGER = 6;//6.付款（合作设计费-付款确认（经营负责人））
	int COOPERATIVE_DESIGN_FEE_OPERATOR_MANAGER = 7;//7.合作设计费付款确认（企业负责人）
	int TECHNICAL_REVIEW_FEE_PAYMENT_CONFIRM = 8;//8.技术审查费到款确认
	int COOPERATIVE_DESIGN_FEE_PAYMENT_CONFIRM = 9;//9.合作设计费费到款确认
	int CONTRACT_FEE_PAYMENT_CONFIRM = 10;//7.合同回款到款确认
	int EXP_AUDIT= 11;//11.报销审核
	int ARRANGE_TASK_DESIGN = 14;//安排设计负责人
	int ARRANGE_TASK_RESPONSIBLE = 15;//安排任务负责人
	int TECHNICAL_REVIEW_FEE_FOR_PAY = 16;//技术审查费付款（财务）
	int TECHNICAL_REVIEW_FEE_FOR_PAID = 17;//技术审查费到款（财务）
	int COOPERATIVE_DESIGN_FEE_FOR_PAY = 18;//合作设计费付款（财务）
	int COOPERATIVE_DESIGN_FEE_FOR_PAID = 19;//合作设计费到款（财务）
	int OTHER_FEE_FOR_PAY = 20;//其他费付款（财务）
	int OTHER_FEE_FOR_PAID = 21;//其他费到款（财务）
    int TASK_COMPLETE = 22;//生产根任务已完成，给设计负责人推送任务

    /*****************消息推送类型**********************/
	String NOTICE_TYPE="notice";
	String ROLE_TYPE="role";
	String PROJECT_TYPE="project";
	String USER_MESSAGE = "userMessage";

	/*****************项目动态动态类型******************/
	//历史遗留项目动态
    int PROJECT_DYNAMIC_ADD_PROJECT = 1;//创建项目
	int PROJECT_DYNAMIC_CHANGE_PROJECT = 2;//更新项目基本信息
	int PROJECT_DYNAMIC_ADD_ISSUE_TASK = 3;//签发任务
	int PROJECT_DYNAMIC_ADD_TASK = 4;//新建子任务
	int PROJECT_DYNAMIC_TASKPERSON = 5;//安排生产任务参与人员
	int PROJECT_DYNAMIC_CONTRACTTOSECTION = 6;//合同到款确认
	int PROJECT_DYNAMIC_CONTRACTNODE = 7;//新增合同回款节点
	int PROJECT_DYNAMIC_INITIATEDSECTION = 8;//合同回款发起

	//新增项目动态
	//---------基本信息类----
    int PROJECT_DYNAMIC_DEL_PROJECT = 9;//删除项目
	//---------任务类----------
    int PROJECT_DYNAMIC_CHANGE_ISSUE_TASK = 10;//更改签发任务
	int PROJECT_DYNAMIC_CHANGE_TASK = 11;//更改生产任务
	int PROJECT_DYNAMIC_DEL_ISSUE_TASK = 12;//删除签发任务
	int PROJECT_DYNAMIC_DEL_TASK = 13;//删除生产任务
	//--------款项类-----------
    int PROJECT_DYNAMIC_CHANGE_FEE = 14;//修改总金额
	int PROJECT_DYNAMIC_ADD_CONTRACT_POINT = 15;//新增合同收款节点
	int PROJECT_DYNAMIC_ADD_DESIGN_POINT = 16;//新增技术审查费收款节点
	int PROJECT_DYNAMIC_ADD_COOPERATOR_POINT = 17;//新增合作设计费付款节点
	int PROJECT_DYNAMIC_ADD_OTHER_DEBIT_POINT = 18;//新增其他收支收款节点
	int PROJECT_DYNAMIC_ADD_OTHER_PAY_POINT = 19;//新增其他收支付款节点
	int PROJECT_DYNAMIC_CHANGE_CONTRACT_POINT = 20;//更改合同收款节点
	int PROJECT_DYNAMIC_CHANGE_DESIGN_POINT = 21;//更改技术审查费收款节点
	int PROJECT_DYNAMIC_CHANGE_COOPERATOR_POINT = 22;//更改合作设计费付款节点
	int PROJECT_DYNAMIC_CHANGE_OTHER_DEBIT_POINT = 23;//更改其他收支收款节点
	int PROJECT_DYNAMIC_CHANGE_OTHER_PAY_POINT = 24;//更改其他收支付款节点
	int PROJECT_DYNAMIC_DEL_CONTRACT_POINT = 25;//删除合同收款节点
	int PROJECT_DYNAMIC_DEL_DESIGN_POINT = 26;//删除技术审查费收款节点
	int PROJECT_DYNAMIC_DEL_COOPERATOR_POINT = 27;//删除合作设计费付款节点
	int PROJECT_DYNAMIC_DEL_OTHER_DEBIT_POINT = 28;//删除其他收支收款节点
	int PROJECT_DYNAMIC_DEL_OTHER_PAY_POINT = 29;//删除其他收支付款节点

	//-----文件类------
    int PROJECT_DYNAMIC_UPLOAD_FILE = 30;//上传文件
	int PROJECT_DYNAMIC_UPDATE_FILE = 31;//修改文件
	int PROJECT_DYNAMIC_DELETE_FILE = 32;//删除文件

	//----阶段类-------
    int PROJECT_DYNAMIC_ADD_PHASE_TASK = 33;//添加设计阶段
	int PROJECT_DYNAMIC_CHANGE_PHASE_TASK = 34;//更改设计阶段
	int PROJECT_DYNAMIC_DEL_PHASE_TASK = 35;//删除设计阶段

	/*****************团队动态类型******************/
	int DYNAMIC_PROJECT = 1;//1.立项动态
	int DYNAMIC_PARTYB= 2;//1.乙方动态
	int DYNAMIC_PARTNER = 3;//合作方动态
	int DYNAMIC_NOTICE = 4;//3.通知公告动态

	String sperater="<br/>";
	/*****************团队动态模板******************/
	Map<String,String> dynamic=new HashMap<String,String>(){
		{
			put("1","? 创建了项目：?" +sperater+
					"设计内容包括：?"
				//	+sperater+"经营负责人是：?、设计负责人是：?"
			);
			put("2","我们成为了 ? 项目的乙方，立项方为 ?" +sperater+
							"设计内容包括：?"
				//	"我们的经营负责人是：?、项目负责人是：?"
			);
			put("3","我们成为了 ? 项目的合作设计方，合作方为 ?" +sperater+
					"我们负责的部分是?。"
				//	+	"经营负责人是：?、设计负责人是：?"
			);
			put("4","?");
		}
	};



	/*****************团队动态模板******************/
	//1 创建项目 2，修改项目3，签发任务，4，分解任务5，安排设计人员，6，确认合同回款7，新增合同回款节点 8,发起合同回款
	Map<String,String> projectDynamic=new HashMap<String,String>(){
		{
			put(""+PROJECT_DYNAMIC_ADD_PROJECT,"创建了项目" );
			put(""+PROJECT_DYNAMIC_CHANGE_PROJECT,"修改了项目基本信息：“?”");
			put(""+PROJECT_DYNAMIC_ADD_ISSUE_TASK,"签发了新任务：“?” 给?");
			put(""+PROJECT_DYNAMIC_ADD_TASK,"在生产安排中分解出一项新任务：“?”");
			put(""+PROJECT_DYNAMIC_TASKPERSON,"在生产任务 ：“?” 中安排了参与人员 “?”");
			put(""+PROJECT_DYNAMIC_CONTRACTTOSECTION,"确认了一条合同到账：“?” 到账金额：?万元");
			put(""+PROJECT_DYNAMIC_CONTRACTNODE,"新增了一条合同回款节点：“?”");
			put(""+PROJECT_DYNAMIC_INITIATEDSECTION,"发起了合同回款：“?” 金额：?万元");
			put(""+PROJECT_DYNAMIC_DEL_PROJECT,"删除了项目");
			put(""+PROJECT_DYNAMIC_CHANGE_ISSUE_TASK,"修改了签发任务：“?”的?");
			put(""+PROJECT_DYNAMIC_CHANGE_TASK,"修改了生产任务：“?”的?");
			put(""+PROJECT_DYNAMIC_DEL_ISSUE_TASK,"删除了签发任务：“?”");
			put(""+PROJECT_DYNAMIC_DEL_TASK,"删除了生产任务：“?”");
			put(""+PROJECT_DYNAMIC_CHANGE_FEE,"修改了“?”的?");
			put(""+PROJECT_DYNAMIC_ADD_CONTRACT_POINT,"新增了合同回款的节点：“?”");
			put(""+PROJECT_DYNAMIC_ADD_DESIGN_POINT,"新增了技术审查费的收款节点：“?”");
			put(""+PROJECT_DYNAMIC_ADD_COOPERATOR_POINT,"新增了合作设计费的付款节点：“?”");
			put(""+PROJECT_DYNAMIC_ADD_OTHER_DEBIT_POINT,"新增了其他收支的收款节点：“?”");
			put(""+PROJECT_DYNAMIC_ADD_OTHER_PAY_POINT,"新增了其他收支的付款节点：“?”");
			put(""+PROJECT_DYNAMIC_CHANGE_CONTRACT_POINT,"修改了合同回款的节点：“?”的?");
			put(""+PROJECT_DYNAMIC_CHANGE_DESIGN_POINT,"修改了技术审查费的收款节点：“?”的?");
			put(""+PROJECT_DYNAMIC_CHANGE_COOPERATOR_POINT,"修改了合作设计费的付款节点：“?”的?");
			put(""+PROJECT_DYNAMIC_CHANGE_OTHER_DEBIT_POINT,"修改了其他收支的收款节点：“?”的?");
			put(""+PROJECT_DYNAMIC_CHANGE_OTHER_PAY_POINT,"修改了其他收支的付款款节点：“?”的?");
			put(""+PROJECT_DYNAMIC_DEL_CONTRACT_POINT,"删除了合同回款的收款节点：“?”");
			put(""+PROJECT_DYNAMIC_DEL_DESIGN_POINT,"删除了技术审查费的收款节点：“?”");
			put(""+PROJECT_DYNAMIC_DEL_COOPERATOR_POINT,"删除了合作设计费的付款节点：“?”");
			put(""+PROJECT_DYNAMIC_DEL_OTHER_DEBIT_POINT,"删除了其他收支的收款节点：“?”");
			put(""+PROJECT_DYNAMIC_DEL_OTHER_PAY_POINT,"删除了其他收支的付款节点：“?”");
			put(""+PROJECT_DYNAMIC_UPLOAD_FILE,"上传了文件：“?”");
			put(""+PROJECT_DYNAMIC_CHANGE_PHASE_TASK,"修改了文件：“?”");
			put(""+PROJECT_DYNAMIC_DELETE_FILE,"删除了文件：“?”");
			put(""+PROJECT_DYNAMIC_ADD_PHASE_TASK,"新增了阶段：“?”");
			put(""+PROJECT_DYNAMIC_CHANGE_PHASE_TASK,"把阶段：“?”修改为“?”");
			put(""+PROJECT_DYNAMIC_DEL_PHASE_TASK,"删除了阶段：“?”");
		}
	};


	/*****************消息模板******************/
	//1 乙方经营负责人
	// 2，乙方项目负责人，
	//1 立项方经营负责人
	// 2，立项项目负责人

	// 3.任务设计人设置给乙方项目负责人发送消息，
	// 4.任务完成父节点任务负责人，
	// 5.设计 校队 审核 人员
	// 6.立项方经营负责人（企业负责人 技术审查费）付款确认
	// 7.立项方企业负责人确认付款后，给乙方经营负责人和财务发送通知（已确认付款 合作设计费）
	// 8.乙方财务到款确认后，给乙方经营负责人和企业负责人发送通知（乙方经营负责人、企业负责人收到到款通知）

	// 9.付款方经营负责人（企业负责人）合作设计费（付款确认）
	// 10.付款方企业负责人确认付款后，给收款方经营负责人和财务发送通知（已确认付款 合作设计费）
	// 11.收款财务到款确认后，给收款方经营负责人和企业负责人发送通知（收款方经营负责人、企业负责人收到到款通知）

	// 12，立项方经营负责人发起合同回款收款，财务、企业负责人 收到通知（合同回款）
	// 12，立项方财务（合同回款）到款确认后，经营负责人、企业负责人 收到通知（合同回款）
	Map<String,String> message=new HashMap<String,String>(){
		{
			//设置乙方
			put("1","你成为了 “?” 乙方经营负责人" );//ok
			put("2","你成为了 “?” 乙方项目负责人");//ok
			//立项
			put("3","你成为了 “?” 经营负责人" );//ok
			put("4","你成为了 “?” 设计负责人" );//ok
			//任务签发
			put("5","你成为了“? - ?”的经营负责人" );//ok
			put("6","你成为了“? - ?”的设计负责人" );//ok
			//设置任务负责人
			put("7","你成为了“? - ?”的任务负责人");//ok

			//“卯丁科技大厦 - 方案设计 - 方案C”设计任务的校对人是：许佳迪、小练，审核人是：XX，XXX
			put("8","“? - ?”设计任务的?");//乙方项目负责人,当设置审核人员后，给乙方负责人发送消息 //ok

			put("9","“? - ?”设计任务已全部完成");//ok
			put("10","你成为了 “? - ?” 的?人"); //ok

			//技术审查费
			put("11","“? - 技术审查费 - ?” 金额：?万，需要你确认付款");
			put("12","“? - 技术审查费 - ?” 金额：?万，已确认付款");
			put("13","“? - 技术审查费 - ?” 金额：?万，已确认到账");

			//合作设计费
			put("14","“? - 合作设计费 - ?” 金额：?万，需要你确认付款");
			put("15","“? - 合作设计费 - ?” 金额：?万，已确认付款");
			put("16","“? - 合作设计费 - ?” 金额：?万，已确认到账");

			//合同回款
			put("17","“? - 合同回款 - ?” 金额：?万，开始收款了");
			put("18","“? - 合同回款 - ?” 金额：?万，已确认到账");//合同回款到账，通知经营负责人，企业负责人

			put("19","?申请报销“?”共计?元");//ok
			put("20","?共计?元的报销申请不予批准");//ok

			put("21","“? - ?”所有子任务已全部完成");//ok

			put("22","你申请“?”共计?元已审批通过");//报销单审批完成后发给报销人送消息

		//	put("23","?共计?元的报销申请不予批准");//事业合伙人同意后，给
			put("23","“? - 技术审查费 - ?” 金额：?万，已付款，请进行相关操作");//技术审查费财务人员付款操作
			put("24","“? - 技术审查费 - ?” 金额：?万，已到账，请进行相关操作");//技术审查费财务人员到账操作

			put("25","“? - 合作设计费 - ?” 金额：?万，已付款，请进行相关操作");//合作设计费财务人员付款操作
			put("26","“? - 合作设计费 - ?” 金额：?万，已到账，请进行相关操作");//合作设计费财务人员到账操作

			put("27","“? - 技术审查费 - ?” 金额：?万，已确认付款，付款日期为?");//技术审查费付款，通知经营负责人，企业负责人
			put("28","“? - 技术审查费 - ?” 金额：?万，已确认到账，到账日期为?");//技术审查费到账，通知经营负责人，企业负责人

			put("29","“? - 合作设计费 - ?” 金额：?万，已确认付款，付款日期为?");//合作设计费付款，通知经营负责人，企业负责人
			put("30","“? - 合作设计费 - ?” 金额：?万，已确认到账，到账日期为?");//合作设计费到账，通知经营负责人，企业负责人

			//其他收支
			put("31","“? - 其他收支 - ?” 金额：?万，开始付款了");
			put("32","“? - 其他收支 - ?” 金额：?万，开始收款了");
			put("33","“? - 其他收支 - ?” 金额：?万，已确认付款，付款日期为?");//合其他收支付款，通知经营负责人，企业负责人
			put("34","“? - 其他收支 - ?” 金额：?万，已确认收款，收款日期为?");//合其他收支付款，通知经营负责人，企业负责人

			put("35","“? - ?”的所有设计任务已完成");//生产根任务已完成，给设计负责人推送消息

			put(String.format("%d", MESSAGE_TYPE_PHASE_TASK_CHANGE),"立项人?通知您，?的时间进行了调整，由?变更为?"); //设计内容时间更改
			put(String.format("%d",MESSAGE_TYPE_ISSUE_TASK_CHANGE),"?经营负责人?通知您，?的计划进度进行了调整，由?变更为?"); //签发任务时间更改
			put(String.format("%d",MESSAGE_TYPE_PRODUCT_TASK_CHANGE),"?任务负责人?通知您，?的计划进度进行了调整，由?变更为?"); //生产任务时间更改
			put(String.format("%d",MESSAGE_TYPE_PRODUCT_TASK_FINISH),"?，?已完成，您看是否需要现在去处理相关项目费用的事宜？"); //生产任务完成
		}
	};


	/*****************任务类型******************/
	int MESSAGE_TYPE_1 = 1;//1.乙方经营负责人
	int MESSAGE_TYPE_2 = 2;//1.乙方经营负责人
	int MESSAGE_TYPE_3 = 3;//1.乙方经营负责人
	int MESSAGE_TYPE_4 = 4;//1.乙方经营负责人
	int MESSAGE_TYPE_5 = 5;//1.乙方经营负责人
	int MESSAGE_TYPE_6 = 6;//1.乙方经营负责人
	int MESSAGE_TYPE_7 = 7;//1.乙方经营负责人
	int MESSAGE_TYPE_8 = 8;//1.乙方经营负责人
	int MESSAGE_TYPE_9 = 9;//1.乙方经营负责人
	int MESSAGE_TYPE_10 = 10;//1.乙方经营负责人
	int MESSAGE_TYPE_11 = 11;//经营负责人，技术审查费付款
	int MESSAGE_TYPE_12 = 12;//1.乙方经营负责人
	int MESSAGE_TYPE_13 = 13;//1.乙方经营负责人
	int MESSAGE_TYPE_14 = 14;//1.乙方经营负责人
	int MESSAGE_TYPE_15 = 15;//1.乙方经营负责人
	int MESSAGE_TYPE_16 = 16;//1.乙方经营负责人
	int MESSAGE_TYPE_17 = 17;//1.乙方经营负责人
	int MESSAGE_TYPE_18 = 18;//合同回款财务人员到款操作
	int MESSAGE_TYPE_19 = 19;//1.乙方经营负责人
	int MESSAGE_TYPE_20 = 20;//1.乙方经营负责人
	int MESSAGE_TYPE_21 = 21;//流程审批完成，给负责人发送消息
	int MESSAGE_TYPE_22 = 22;//同意报销,给报销人推送消息
	int MESSAGE_TYPE_23 = 23;//技术审查费财务人员付款操作
	int MESSAGE_TYPE_24 = 24;//技术审查费财务人员到款操作
	int MESSAGE_TYPE_25 = 25;//合作设计费财务人员付款操作
	int MESSAGE_TYPE_26 = 26;//合作设计费财务人员到款操作
	int MESSAGE_TYPE_27 = 27;//技术审查费付款，通知经营负责人，企业负责人
	int MESSAGE_TYPE_28 = 28;//技术审查费到款，通知经营负责人，企业负责人
	int MESSAGE_TYPE_29 = 29;//合作设计费付款，通知经营负责人，企业负责人
	int MESSAGE_TYPE_30 = 30;//合作设计费到款，通知经营负责人，企业负责人
	int MESSAGE_TYPE_31 = 31;//其他费财务人员付款操作
	int MESSAGE_TYPE_32 = 32;//其他费财务人员到款操作
	int MESSAGE_TYPE_33 = 33;//其他费付款，通知经营负责人，企业负责人
	int MESSAGE_TYPE_34 = 34;//其他费到款，通知经营负责人，企业负责人
	int MESSAGE_TYPE_35 = 35;//生产根任务已完成，给设计负责人推送消息
	int MESSAGE_TYPE_PHASE_TASK_CHANGE = 36; //设计任务时间更改
	int MESSAGE_TYPE_ISSUE_TASK_CHANGE = 37; //签发任务时间更改
	int MESSAGE_TYPE_PRODUCT_TASK_CHANGE = 38; //生产任务时间更改
	int MESSAGE_TYPE_PRODUCT_TASK_FINISH = 39; //生产任务完成

	//*******任务类型************
	int TASK_TYPE_PRODUCT = 0;//生产任务
	int TASK_TYPE_ISSUE = 2;//签发任务
	int TASK_TYPE_PHASE = 1;//项目阶段
	int TASK_TYPE_MODIFY = 3;//签发任务的修改版本

	String TASK_STATUS_VALID = "0";
	String TASK_STATUS_INVALID = "1";
	String TASK_STATUS_MODIFIED = "2";

	//*******任务计划时间类型*******
	int PROCESS_TYPE_CONTACT = 1;//合同进度
	int PROCESS_TYPE_PLAN = 2;//计划进度
	int PROCESS_TYPE_NOT_PUBLISH = 3;//未发布
	int TASK_PRODUCT_TYPE_MODIFY = 4;//生产任务的修改版本


	/*************项目群成员加入的类型**************/
	int PROJECT_MANAGER = 1;//经营负责人
	int PROJECT_DESIGNER = 2;//任务负责人
	int TASK_RESPONSIBLE = 3;//任务负责人
	int PROCESS_USER = 4;//设校审人员
	int PROJECT_CREATOR = 5;//立项人

	/*************im 卯丁助手id *******************/
	String MAODING_ID = "123f9ef123c140fd9b6c7a5123c68e1a";
}

