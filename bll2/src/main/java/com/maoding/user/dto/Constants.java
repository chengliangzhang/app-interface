package com.maoding.user.dto;


import okhttp3.MediaType;

/**
 * Created by taojm on 2016/3/25.
 */
public class Constants {

    public static final String TAG = "IDCCAPP";
    /**********
     * example:2017.10.12 发布的版本更新版本号为"1" 后台服务最新版本号为“1”
     *
     *********/
    public static final String INTERFACE_VERSION = "1";//<!--每次更改必须修改版本号-->

    /**
     * 错误日志文件名称
     */
    public static final String LOG_NAME = "/crash.txt";
    /***  阿里云OSS地址Start****************************/
    public static final String OSS_END_POINT  = "http://oss-cn-shenzhen.aliyuncs.com";

    /***  阿里云OSS地址End****************************/

    /**
     * 应用存放文件目录
     */
    public static final String ROOT = "";//FileManager.getSDPath() + "/imaoding/";
    /**
     * 应用存放下载的附件
     */
    public static final String DOWNLOAD_PATH = ROOT + "download/";

    /**
     * 应用存放图片目录
     */
    public static final String CAMERA ="";// FileManager.getSDPath() + "/DCIM/Camera/";

    /**
     * 应用图片缓存目录
     */
    public static final String CACHE_IMG = ROOT + "cache/images/";

    /**
     * 应用sharedpreference缓存目录
     */
    public static final String CACHE_SHARE = ROOT + "cache/share/";
    /**
     * 应用日志目录文件
     */
    public static String APP_LOG_PATH = ROOT + "log/";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String CHARSET_UPLOAD = "ISO-8859-1";
    /**
     * 引导页Shreadpreferences字段，此为文件名
     */
    public static final String GUIDE_SHAREDPREF_NAME = "guide_pref";
    /**
     * 引导页Shreadpreferences字段，此key值为判断是否为第一次打开此应用
     */
    public static final String GUIDE_IS_FIRST_IN = "isFirstIn";
    /**
     * 标题栏状态1：只有返回按钮，背景为紫绿色
     */
    public static final int TOPBAR_TYPE_ONE = 1;
    /**
     * 标题栏状态2：只有返回按钮，背景为白色
     */
    public static final int TOPBAR_TYPE_TWO = 2;
    /**
     * 标题栏状态3：有返回按钮和有保存按钮，背景为白色
     */
    public static final int TOPBAR_TYPE_THREE = 3;
    /**
     * 标题栏状态4：只有通知按钮，背景为白色
     */
    public static final int TOPBAR_TYPE_FOUR = 4;
    /**
     * 标题栏状态5：只有取消按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_FIVE = 5;
    /**
     * 标题栏状态6：有返回和更多按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_SIX = 6;
    /**
     * 标题栏状态7：有取消和完成按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_SEVEN = 7;
    /**
     * 标题栏状态8：有返回和有导入通讯录按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_EIGHT = 8;
    /**
     * 标题栏状态9：只有返回图标变为X,标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_NINE = 9;
    /**
     * 标题栏状态10：有返回和有完成按钮,标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_TEN = 10;
    /**
     * 标题栏状态11：有返回和有添加按钮,标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_ELEVEN = 11;
    /**
     * 标题栏状态12：有取消和确定按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_TWELVE = 12;
    /**
     * 标题栏状态13：有返回和提交按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_THIRTEEN = 13;
    /**
     * 标题栏状态14：有返回和三点图标按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_FOURTEEN = 14;
    /**
     * 标题栏状态15：有返回按钮和跳过按钮，背景为紫绿色
     */
    public static final int TOPBAR_TYPE_FIFTEEN = 15;
    /**
     * 标题栏状态16：有返回按钮和修改按钮，背景为白色
     */
    public static final int TOPBAR_TYPE_SIXTEEN = 16;
    /**
     * 标题栏状态17：有返回和筛选图标按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_SEVENTEEN = 17;
    /**
     * 标题栏状态18：有返回和确定按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_EIGHTEEN = 18;
    /**
     * 标题栏状态19：有返回和我要报销按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_NINETEEN = 19;
    /**
     * 标题栏状态20：有返回和项目立项按钮，标题不能点击，背景为白色
     */
    public static final int TOPBAR_TYPE_TWENTY = 20;
    /**
     * 标题栏状态21：有返回和保存按钮，标题不能点击，背景为#84A0A1
     */
    public static final int TOPBAR_TYPE_TWENTYONE = 21;
    /**
     * 标题栏状态22：有返回和+按钮
     */
    public static final int TOPBAR_TYPE_TWENTTWO = 22;
    /**
     * 标题栏状态23：有返回和删除、筛选按钮
     */
    public static final int TOPBAR_TYPE_TWENTYTHREE = 23;
    /**
     * 标题栏状态24：有返回和删除、筛选按钮,删除按钮不可点击状态
     */
    public static final int TOPBAR_TYPE_TWENTYFOUR = 24;

    /**
     * 筛选页面数据类型:月份
     */
    public static final int SELECT_TYPE_MONTH = 2;
    /**
     * 筛选页面数据类型:设计团队筛选条件
     */
    public static final int SELECT_TYPE_DESIGNTEAM = 3;
    /**
     * 筛选页面数据类型:我的组织
     */
    public static final int SELECT_TYPE_GROUP = 4;

    /**
     * 保存组织数量（拥有系统管理员权限的组织）
     */
    public static int hasManagerTeamNums = 0;

    /**
     * 工作页面数据刷新广播action
     */
    public static final String WORK_RELOAD_ACTION = "android.intent.action.WORKRELOAD";
    /**
     * 联系人页面数据刷新广播action
     */
    public static final String TEAM_RELOAD_ACTION = "android.intent.action.TEAMRELOAD";
    /**
     * 消息页面数据刷新广播action
     */
    public static final String CONVERSATION_RELOAD_ACTION = "android.intent.action.CONVERSATION";


    public static final String MAODING_ASSISTANT_ID = "123f9ef123c140fd9b6c7a5123c68e1a";
    public static final String MAODING_NOTICE_ID = "75317ab0e0a011e782f8f8db88fcba36";
    public static final String MAODING_SYSTEM_ID = "75380bfce0a011e782f8f8db88fcba36";

    /**
     * Activity Result 标示
     */
    public static final int CREATE_TEAM_TAG = 201;
    public static final int DELETE_TEAM_TAG = 202;
    public final static int DISSOLUTIONTEAM = 203;
    public final static int ADD_DEPARTMENT = 205;
    public final static int SET_DEPARTMENT = 206;
    public final static int ADD_EMPLOYEE = 207;

    //单个添加员工
    public final static int MORE_ADD_EMPLOYEE = 208;
    //批量导入员工
    public final static int IMPORT_MODE = 211;
    //选择角色
    public final static int SELECT_ROLES = 220;
    //选择部门
    public final static int SELECT_DEPARTMENT = 222;
    //移交管理员
    public final static int TRANSFER_MANAGER = 224;
    //移交管理员1
    public final static int TRANSFER_MANAGER1 = 226;
    //选择建筑功能
    public static final int BUILDING_FUNCTION_CODE = 0x0088;
    //选择设计范围
    public static final int DESIGN_RANGE_CODE = 0x0089;
    //选择设计依据
    public static final int DESIGN_BASIS_CODE = 0x0090;
    //选择设计阶段
    public static final int DESIGN_PHASE_CODE = 0x0091;
    //选择设计阶段时间
    public static final int DESIGN_PHASE_TIME_CODE = 0x0291;
    //上传附件
    public static final int UPLOAD_FILE_CODE = 0x0092;
    //选择经营负责人
    public static final int SELECT_OPERATE_MANAGER_CODE = 0x0106;
    //选择项目负责人
    public static final int SELECT_PROJECT_MANAGER_CODE = 0x0107;
    //选择合作团队
    public static final int FIND_TEAM_CODE = 0x0117;
    //作品详情点赞后回调到列表刷新
    public static final int CLICK_LIKES = 0x0119;
    //选择责任人或者设计人员
    public static final int SELECT_DESIGN_PEOPLE = 0x0120;
    //编辑任务
    public static final int EDIT_TASK_CODE = 0x0123;
    //分解任务
    public static final int DECOMPOSE_TASK_CODE = 0x0127;
    //编辑员工信息
    public static final int EDIT_EMPLOYEE_INFO = 0x0125;
    //选择群管理员
    public static final int SELECT_GROUP_MANAGER = 0x0129;
    //选择报销类别
    public static final int SELECT_EXP_TYPE = 0x0130;
    //选择关联项目
    public static final int SELECT_ASSOCIATED_PROJECT = 0x0131;
    //发送消息
    public final static int SEND_NOTICE = 0x0157;
    //选择发送范围
    public final static int TO_SELECT_SEND_PEOPLE = 0x0158;
    //结果回调code
    public final static int RESULT_CODE = 0x0141;
    //编辑客户详情
    public static final int EDIT_CUSTOMER_INFO = 0x0142;

    public static final int EVENT_BUS_APPROVAL_LIST = 0x0156;           //eventBus回调刷新我审批待审批列表
    public static final int EVENT_BUS_PROJECT_LIST = 0x0160;            //eventBus回调刷新项目列表
    public static final int EVENT_BUS_PROJECT_INFO = 0x0161;            //eventBus回调刷新项目信息
    public static final int EVENT_BUS_PROJECT_TASK_INFO = 0x0161;       //eventBus回调刷新任务信息
    public static final int EVENT_BUS_TASK_DETAIL = 0x0162;             //eventBus回调刷新任务详情
    public static final int EVENT_BUS_EXP_MY_APPLY = 0x0163;             //eventBus回调刷新我报销的列表
    public static final int EVENT_BUS_EXP_MY_AUDIT = 0x0164;             //eventBus回调刷新我审核的列表
    public static final int EVENT_BUS_SWITCH_TEAM_REFRESH = 0x0170;      //eventBus 首页切换团队刷新
    public static final int EVENT_BUS_GREAT_TEAM_REFRESH = 0x0171;       //eventBus 首页创建团队刷新
    public static final int EVENT_BUS_MY_TASKS_OPERATOR = 0x0173;        //eventBus 我的任务操作完成刷新
    public static final int EVENT_BUS_DELETE_TEAM_REFRESH = 0x0172;       //eventBus 首页删除团队刷新
    public static final int EVENT_BUS_MAINDATA_REFRESH = 0x0173;     //eventBus 首页平台展示刷新
    public static final int EVENT_BUS_GETALLPERSON_IM_REFRESH = 0x0174;   //eventBus 首页IM人员信息刷新
    public static final int EVENT_BUS_ROLE_PERMISSION_REFRESH = 0x0179;   //eventBus 刷新团队列表获取最新权限
    public static final int EVENT_BUS_EDIT_PROJECT_REFRESH = 0x0180;       //eventBus 编辑项目刷新项目列表及任务列表
    public static final int EVENT_BUS_ROLE_PERMISSION_FOR_WORKANDMAINPAGE_REFRESH = 0x0181;       //eventBus 刷新工作和主页的权限
    public static final int EVENT_BUS_APPLY_TO_JOIN = 0x0183;       //eventBus 申请加入事业合伙人完成后刷新
    public static final int EVENT_BUS_TECH_LIST_INTO = 0x0184;       //eventBus 刷新技术审查费列表界面数据
    public static final int EVENT_BUS_COOP_LIST_INTO = 0x0188;       //eventBus 刷新合作设计费列表界面数据
    public static final int EVENT_BUS_PERSONINFO_REFRESH = 0x0185;       //eventBus 刷新个人信息
    public static final int EVENT_BUS_OTHER_FEE_PAYMETN = 0x0186;       //eventBus 刷新其他收支款项付款列表
    public static final int EVENT_BUS_OTHER_FEE_RECEIVABLES = 0x0187;       //eventBus 刷新其他收支款项收款款列表
    public static final int EVENT_BUS_DELETE_PROJECT = 0x0191;       //eventBus  删除项目,刷新群组信息
    public static final int EVENT_BUS_PROJECT_MEMBER = 0x0192;                     //eventBus 刷新项目成员
    public static final int EVENT_BUS_OTHER_PAYMENT_REFLESH = 0x0193;                     //eventBus 刷新其他款项
    public static final int EVENT_BUS_TASK_COUNT_BY_MAODINGASSITANT_REFRESH = 0x0194;       //eventBus 通过卯丁秘书刷新任务数
    public static final int EVENT_BUS_RIGHT_NATIVE_REFRESH = 0x0195;          //eventBus 刷新右侧导航栏
    public static final int EVENT_BUS_NATIVE_REFRESH = 0x0196;                //eventBus 刷新右侧导航栏
    public static final int EVENT_BUS_PROJECT_DOCUMENT_REFRESH = 0x0197;       //eventBus项目文档库修改刷新UI
    public static final int EVENT_BUS_OTHER_RPEOPLE_JOIN_TEAM_REFRESH = 0x0199;       //eventBus 其他成员加入到群中刷新
    public static final int EVENT_BUS_MY_TASK_FOR_TASK_ISSUE_REFRESH = 0x0120;              //eventBus 任务签发列表界面刷新
    public static final int EVENT_BUS_MY_TASK_FOR_PRODUCTION_TASK_REFRESH = 0x0121;        //eventBus 生产任务子任务和参与人员列表刷新
    public static final int EVENT_BUS_IM_PICTURE_REFRESH = 0x0122;        //eventBus 刷新聊天会话图片
    public static final int EVENT_BUS_REMIN_FILTER_REFRESH = 0x0123;        //eventBus 刷新报销筛选
    public static final int EVENT_BUS_REMIN_FILTER_RETURN_REFRESH = 0x0124;        //eventBus 刷新报销筛选
    public static final int EVENT_BUS_REMIN_FILTER_BYSCROLL_REFRESH = 0x0125;        //eventBus 刷新报销筛选
    public static final int EVENT_BUS_REMIN_FILTER_HOME_REFRESH = 0x0126;        //在home页面传递状态进入报销页面
    public static final int EVENT_BUS_SEARCH_MESSAGE_HOME_REFRESH = 0x0127;        //在home页面传递状态进入消息页面
    public static final int EVENT_BUS_CUSTOM_GROUP_DETAIL_REFRESH = 0x0128;        //eventBus回调刷新自定义群详情界面
    public static final int EVENT_BUS_CREATE_CUSTOM_GROUP_REFRESH = 0x0129;        //eventBus回调刷新自定义群列表界面
    public static final int EVENT_BUS_MAIN_NOTICE_REFRESH = 0x0130;        //eventBus回调刷新通知公告提示界面
    public static final int EVENT_BUS_REIMBURSE_REDIT_REFRESH = 0x0131;        //eventBus回调关闭报销详情界面
    public static final int EVENT_BUS_REIMBURSE_SUBMIT_SUCCESS_REFRESH = 0x0132;        //eventBus 提交报销成功，刷新报销列表页面
    public static final int EVENT_BUS_REIMBURSE_UPDATE_PIC_SUCCESS_REFRESH = 0x0133;        //eventBus 修改图片成功后刷新报销详情页面
    public static final int EVENT_BUS_LIGHTWEIGHT_TASK_REFRESH = 0x0134;        //eventBus 新增修改删除轻量任务刷新
    public static final int EVENT_BUS_ADD_LINKMAN_REFRESH = 0x0135;        //eventBus 新增客户联系人刷新


    //角色权限修改返回
    public static final int ROLE_CONFIG_MODIFY = 0x0161;
    //扫描回调
    public static final int SCAN_RESULT = 0x0165;
    //删除消息回调
    public final static int DELETE_NOTICE = 0x0166;
    //绑定邮箱回调
    public final static int BIND_MAIL_RESULT = 0x0167;
    //修改个人信息
    public final static int MODIFY_PERSONAL_INFO_RESULT = 0x0168;
    //发送通知公告回调
    public final static int SEND_NOTICE_RESULT = 0x0169;
    //我要报销回调
    public final static int I_WANT_REIMBURSEMENT_RESULT = 0x0170;

    /**
     * 汝彬IP
     */
    // private final static String BASE_URL = "http://192.168.33.107:8083/";

    /**
     * 汤毅IP 0727更新
     */
    // private final static String BASE_URL = "http://192.168.33.105:8083/";

    /**
     * 郭志彬 IP
     */
    // private final static String BASE_URL = "http://192.168.51.101:8083/";

    /**
     * 张工 IP
     */
    //private final static String BASE_URL = "http://192.168.33.140:8083/";

    /**
     * 双凤IP
     */
    private final static String BASE_URL = "http://192.168.13.105:8083/";

    /**
     * 双凤IP文件服务器
     */
    private final static String BASE_FILE_URL = "http://192.168.13.105:8071/";

    /**
     * 伟强
     */
    //private final static String BASE_URL = "http://192.168.17.168:8083/";

    /**
     * 打印机
     */
    //private final static String BASE_URL = "http://192.168.75.102:8084/idcc-ws/ws/";

    /**
     * 73服务器
     */
   // private final static String BASE_URL = "http://172.16.6.73:8888/";

    /**
     * 服务器
     */
    //private final static String BASE_URL = "http://112.74.211.53:8880/";

    /**
     * 云服务器
     * */
    //private final static String BASE_URL = "http://interface.designplus.com/";

    /**
     * v2 获取验证码URL
     */
    public final static String V2_GET_CODE_URL = BASE_URL + "/v2/sys/getCode.do";
    /**
     * V2_获取验证码URL（忘记密码专用）
     */
    public final static String V2_FORGET_PSW_GET_CODE_URL = BASE_URL + "/v2/sys/forgetPassWordGetCode.do";
    /**
     * V2 校验验证码URL
     */
    public final static String V2_CHECK_CODE_URL = BASE_URL + "/v2/sys/checkCode.do";
    /**
     * V2 注册账户URL
     */
    public final static String V2_REGISTER_URL = BASE_URL + "/v2/sys/register.do";
    /**
     * V2 登录URL
     */
    public final static String V2_LOGIN_URL = BASE_URL + "/v2/sys/login.do";
    /**
     * V2 忘记密码/找回密码URL
     */
    public final static String V2_FORGET_PSW_URL = BASE_URL + "/v2/sys/forgotPassword.do";
    /**
     * 重置密码
     */
    public static final String V2_CHANGE_PSW = BASE_URL + "/v2/user/changePassword.do";
    /**
     * 验证原密码
     */
    public static final String VALIDATION_PSW = BASE_URL + "v2/user/checkPassword";
    /**
     * 修改账户（手机号）
     */
    public static final String MODIFY_PHONE = BASE_URL + "v2/user/modifyPhone";
    /**
     * 团队服务类型
     */
    public static final String SERVER_TYPE = BASE_URL + "v2/org/getServerTypes";

    /**
     * 创建  分公司
     */
    public static final String CREATE_SUBCOMPANY = BASE_URL + "v2/org/createSubCompany";
    /**
     * 创建 事业合伙人
     */
    public static final String CREATE_BUSINESSPARTNER = BASE_URL + "v2/org/createBusinessPartner";
    /**
     * 团队列表(新接口2016-11-16)
     */
    public static final String GET_COMPANY_DATA = BASE_URL + "v2/org/getcompanyData";
    /**
     * 部门列表
     */
    public static final String DEPARTS = BASE_URL + "v2/org/getDepartByCompanyId";
    /**
     * 角色列表
     */
    public static final String ROLES = BASE_URL + "v2/role/getCompanyRole";
    /**
     * V2 添加/编辑员工信息
     */
    public static final String V2_SAVE_COMPANYUSER = BASE_URL + "v2/org/saveCompanyUser";
    /**
     * 设置员工为离职状态
     */
    public static final String PERSONAL_LEAVE = BASE_URL + "v2/org/quit";
    /**
     * 删除部门
     */
    public static final String DELETE_DEPART = BASE_URL + "v2/org/deleteDepart";

    /**
     * /解散团队
     */
    public static final String DISBAND_COMPANY = BASE_URL + "v2/org/disbandCompany";
    /**
     * 绑定个推
     */
    public static final String BIND_CID = BASE_URL + "v2/sys/bindCID";
    /**
     * 解绑个推
     */
    public static final String UNBIND_CID = BASE_URL + "v2/sys/unbindCID";
    /**
     * v2 系统更新
     */
    public static final String V2_SYSTEN_UPDATE = BASE_URL + "/v2/sys/helpCenterUpdate.json";
    /**
     * V2 获取邀请链接
     */
    public static final String V2_INVITATION_LINK = BASE_URL + "/v2/user/invitationLink.do";
    /**
     * 申请成员列表
     */
    public static final String PERSONAL_APPLY_LIST = BASE_URL + "v2/org/getPendingAudiOrgUser";
    /**
     * 团队模块-申请成员同意操作
     */
    public static final String PERSONAL_APPLY_OPERATION = BASE_URL + "v2/org/audiOrgUser";
    /**
     * 解除分公司、事业合伙人关系
     */
    public static final String DELETE_SUBCOMPANY = BASE_URL + "v2/org/deleteSubCompany";
    /**
     * 工作模块-项目立项-上传项目备案附件
     */
    public static final String UPLOAD_FILE_URL = BASE_URL + "project/upload_contractfile.do";

    /**
     * 获取IM用户基本资料接口
     */
    public static final String IM_USER_INFO = BASE_URL + "v2/im/imUserInfo.json";

    /**
     * 财务管理 - 获取报销类别列表数据
     */
    public static final String GET_EXP_TYPE_URL = BASE_URL + "v2/finance/getExpTypeList";
    /**
     * 财务管理 - 删除报销操作
     */
    public static final String DELETE_EXP_MAIN_URL = BASE_URL + "v2/finance/deleteExpMain.do";
    /**
     * 财务管理 - 同意报销操作
     */
    public static final String AGREE_EXP_MAIN_URL = BASE_URL + "v2/finance/agreeExpMain.do";
    /**
     * 财务管理 - 退回报销操作
     */
    public static final String RECALL_EXP_MAIN_URL = BASE_URL + "v2/finance/recallExpMain.do";
    /**
     * 财务管理 - 同意并转移审批人报销操作
     */
    public static final String AGREE_TRANSFER_URL = BASE_URL + "v2/finance/agreeAndTransAuditPerExpMain";
    /**
     * 获取IM群组基本资料接口
     */
    public static final String IM_GROUP_INFO = BASE_URL + "v2/im/imGroupInfo.do";
    /**
     * 编辑更新群组信息
     */
    public static final String UPDATE_GROUP_INFO = BASE_URL + "v2/im/updateImgroup.do";
    /**
     * 事业合伙人、分公司审核操作
     */
    public static final String PROCESSING_APPLICATION_OR_INVITATION = BASE_URL + "v2/org/processingApplicationOrInvitation";
    /**
     * 邀请合作伙伴操作
     */
    public static final String INVITE_PARTNER = BASE_URL + "v2/org/invateBusinessPartner";
    /**
     * 邀请分公司操作
     */
    public static final String INVATE_SBUCOMPANY = BASE_URL + "v2/org/invateSubCompany";
    /**
     * 移交管理员
     */
    public static final String TRANSFER_MANAGER_OPERATOR = BASE_URL + "v2/org/transfersys.json";

    /**
     * 查询分公司或事业合伙人待审核列表
     */
    public static final String SELECT_INVITEDDEPARTNER = BASE_URL + "v2/org/selectInvitedPartner";

    /**
     * 发送通知公告(161208)
     */
    public static final String SAVE_NOTICE = BASE_URL + "v2/notice/saveNotice";
    /**
     * 获取通知公告(161208)
     */
    public static final String GET_NOTICE = BASE_URL + "v2/notice/getNoticeList.do";

    /**
     * 删除通知公告(161208)
     */
    public static final String DELETE_NOTICE_URL = BASE_URL + "v2/notice/deleteNotice";


    /**********************新UI接口**********************************/

    //********************项目相关***********************************
    /**
     * 获取项目列表数据
     */
    public static final String GET_PROJECT_LIST_NEW_URL = BASE_URL + "/v2/project/v2GetProjectList.do";
    /**
     * 删除项目
     */
    public static final String DELETE_PROJECT_URL = BASE_URL + "v2/project/deleteProject.do";
    /**
     * 项目立项 - 提交项目立项数据
     */
    public static final String SAVE_NEW_PROJECT_URL = BASE_URL + "/v2/project/saveNewProject.do";
    /**
     * 获取设计阶段列表
     */
    public static final String GET_DESIGN_CONTENT_LIST_URL = BASE_URL + "v2/project/getDesignContentList.do";
    /**
     * 获取设计范围列表
     */
    public static final String GET_DESIGN_RANGE_LIST_URL = BASE_URL + "v2/project/getDesignRangeList.do";
    /**
     * 获取项目相关信息
     */
    public static final String GET_PROJECT_ABOUT_INFO_URL = BASE_URL + "v2/project/getProjectInfo.do";
    /**
     * 获取项目右侧导航栏判断权限
     */
    public static final String GET_PROJECT_NAVIGATION_ROLE_URL = BASE_URL + "v2/project/projectNavigationRoleInterface.do";
    /**
     * 完善项目信息 - 获取项目信息
     */
    public static final String GET_PROJECT_INFO_URL = BASE_URL + "/v2/project/getProject.do";
    /**
     * 完善项目信息 - 保存我关注的项目
     */
    public static final String SAVE_PROJECT_ATTENTION_INFO_URL = BASE_URL + "/v2/attention/saveAttention.do";
    /**
     * 完善项目信息 - 取消我关注的项目
     */
    public static final String CANCEL_PROJECT_ATTENTION_INFO_URL = BASE_URL + "/v2/attention/cancelAttention.do";
    /**
     * 获取项目类型（建筑功能）
     */
    public static final String GET_PROJECT_TYPE_URL = BASE_URL + "/v2/project/getProjectType.do";
    /**
     * 获取设计阶段变更数据
     */
    public static final String GET_DESIGN_CHANGE_TIME_URL = BASE_URL + "v2/task/getProjectDesignChangeTime.do";
    /**
     * 保存任务进度（阶段变更）
     */
    public static final String SAVE_PROCESS_TIME_URL = BASE_URL + "v2/task/saveProjectProcessTime.do";
    /**
     * 删除设计阶段变更
     */
    public static final String DELETE_PROCESS_TIME_URL = BASE_URL + "v2/task/deleteProjectProcessTime.do";
    /**
     * 获取经营任务信息
     */
    public static final String GET_PROJECT_TASK_FOR_OPERATOR_URL = BASE_URL + "v2/task/getProjectTaskDataForOperater.do";
    /**
     * 获取项目签发任务列表数据
     */
    public static final String GET_PROJECT_ISSUE_TASK_URL = BASE_URL + "v2/task/getProjectIssueTask.do";
    /**
     * 获取项目生产任务列表数据
     */
    public static final String GET_PROJECT_PRODUCT_TASK_URL = BASE_URL + "v2/task/getProjectProductTask.do";
    /**
     * 获取任务参与人员数据
     */
    public static final String GET_PROJECT_MEMBER_BY_TASK_URL = BASE_URL + "v2/task/getProjectMemberByTaskId.do";
    /**
     * 获取项目任务信息
     */
    public static final String GET_PROJECT_TASK_URL = BASE_URL + "/v2/task/getProjectTask.do";
    /**
     * 获取项目生产任务详情信息
     */
    public static final String GET_PROJECT_TASK_BY_ID_URL = BASE_URL + "/v2/task/getProjectTaskById.do";
    /**
     * 获取项目经营任务详情信息
     */
    public static final String GET_PROJECT_TASK_BY_ID_FOR_OPERATE_URL = BASE_URL + "v2/task/getProjectTaskByIdForOperater.do";
    /**
     * 任务分配 - 分解任务
     */
    public static final String DECOMPOSE_TASK_URL = BASE_URL + "v2/task/saveProductionTask.do";
    /**
     * 任务签发 - 发布任务
     */
    public static final String PUBLISH_ISSUE_TASK_URL = BASE_URL + "/v2/task/publishIssueTask.do";
    /**
     * 任务生产 - 发布任务
     */
    public static final String PUBLISH_PRODUCT_TASK_URL = BASE_URL + "/v2/task/publishProductTask.do";
    /**
     * 任务分配 - 添加任务负责人
     */
    public static final String SAVE_TASK_RESPONSIBLE_URL = BASE_URL + "v2/task/saveTaskResponsible.do";
    /**
     * 任务分配 - 设置任务参与人员
     */
    public static final String SAVE_TASK_PROCESS_URL = BASE_URL + "v2/projectProcess/saveTaskProcess.do";
    /**
     * 任务分配 - 移交任务负责人
     */
    public static final String TRANSFER_TASK_MANAGER_URL = BASE_URL + "v2/task/transferManager.do";
    /**
     * 任务分配 - 移交设计负责人
     */
    public static final String TRANSFER_TASK_DESIGNER_URL = BASE_URL + "v2/task/transferTaskDesigner.do";
    /**
     * 任务分配 - 移交的设计任务列表
     */
    public static final String GET_PROJECT_TASK_FOR_TRANSFER_DESIGNER_URL = BASE_URL + "v2/task/getProjectTaskForChangeDesigner";
    /**
     * 任务分配 - 获取合作团队数据
     */
    public static final String GET_OTHER_PARTNER_URL = BASE_URL + "v2/task/getOtherPartner.do";
    /**
     * 任务分配 - 获取组织部门数据
     */
    public static final String GET_DEPARTMENT_LIST_URL = BASE_URL + "v2/org/getDepartForOperator.do";
    /**
     * 任务分配 - 根据任务类型获取任务列表
     */
    public static final String GET_TASK_BY_TYPE_URL = BASE_URL + "v2/task/getTaskByType.do";
    /**
     * 任务分配 - 删除任务
     */
    public static final String DELETE_PROJECT_TASK_URL = BASE_URL + "v2/task/deleteProjectTask.do";
    /**
     * 获取参与项目参与人员列表
     */
    public static final String GET_PROJECT_PARTICIPANTS_URL = BASE_URL + "v2/project/listProjectAllMember.do";
    /**
     * 获取我的任务列表
     */
    public static final String GET_MY_TASK_LIST_URL = BASE_URL + "v2/mytask/getMyTask.do";
    /**
     * 获取我的任务列表
     */
    public static final String GET_MY_TASK_LIST_NEW_URL = BASE_URL + "v2/mytask/getMyTaskList.do";
    /**
     * 获取我的任务列表子列表
     */
    public static final String GET_MY_TASK_BY_PROJECT_ID_URL = BASE_URL + "v2/mytask/getMyTaskByProjectId";
    /**
     * 获取我发布的任务列表子列表
     */
    public static final String V2_MYTASK_GETMYSUBMITTASK = BASE_URL + "/v2/mytask/getMySubmitTask";
    /**
     * 获取待我审批的列表
     */
    public static final String V2_FINANCE_GET_AUDIT_DATA = BASE_URL + "v2/finance/getAuditData";
    /**
     * 获取我的任务详情
     */
    public static final String GET_MY_TASK_DETAIL_URL = BASE_URL + "v2/mytask/getMyTaskDetailNew.do";
    /**
     * 获取设计任务详情（编辑任务）
     */
    public static final String GET_TASK_DETAIL_FOR_EDIT_URL = BASE_URL + "v2/task/getProjectTaskDetailForEdit.do";
    /**
     * 我的任务 - 任务操作
     */
    public static final String HANDLE_MY_TASK_URL = BASE_URL + "v2/mytask/handleMyTask.do";
    /**
     * 项目费用 - 获取项目费用汇总数据
     */
    public static final String GET_PROJECT_FEE_SUMMARY_URL = BASE_URL + "/v2/projectCost/getProjectFeeSummary.do";
    /**
     * 合同回款 - 获取合同回款列表相关信息
     */
    public static final String GET_CONTRACT_PAYMENT_INFO_URL = BASE_URL + "/v2/projectCost/getContractInfo.do";
    /**
     * 设置或修改费用总金额（合同总金额，技术审查费,合作设计费付款）
     */
    public static final String SAVE_OR_UPDATE_TOTAL_AMOUNT_URL = BASE_URL + "/v2/projectCost/saveOrUpdateContract.do";
    /**
     * 合同回款 - 添加或修改合同回款节点
     */
    public static final String SAVE_OR_UPDATE_PAYMENT_URL = BASE_URL + "/v2/projectCost/saveOrUpdateCostPoint.do";
    /**
     * 合同回款 - 发起收款(新增),确认到款(即编辑)
     */
    public static final String SAVE_OR_UPDATE_COST_DETAIL_URL = BASE_URL + "/v2/projectCost/saveOrUpdateCostDetail.do";
    /**
     * 合同回款 - 删除合同回款明细节点
     */
    public static final String DELETE_COST_DETAIL_BY_ID_URL = BASE_URL + "/v2/projectCost/deleteCostDetailById.do";
    /**
     * 合同回款 - 删除合同回款节点
     */
    public static final String DELETE_PAYMENT_POINT_BY_ID_URL = BASE_URL + "/v2/projectCost/deletePointById.do";
    /**
     * 技术审查费- 获取技术审查费列表数据
     */
    public static final String GET_TECHNICAL_REVIEW_FEE_INFO_URL = BASE_URL + "/v2/projectCost/techicalReviewFeeInfo.do";
    /**
     * 技术审查费- 获取技术审查费详情
     */
    public static final String GET_TECHNICAL_REVIEW_FEE_DETAIL_URL = BASE_URL + "/v2/projectCost/getProjectCostPointDetail.do";
    /**
     * 合作设计费 - 获取合作设计费总览列表数据
     */
    public static final String GET_COOPERATIVE_DESIGN_FEE_URL = BASE_URL + "v2/projectCost/getProjectCooperatorFee.do";
    /**
     * 合作设计费 - 获取合作设计费列表数据
     */
    public static final String GET_COOPERATIVE_DESIGN_FEE_INFO_URL = BASE_URL + "/v2/projectCost/cooperativeDesignFeeInfo.do";
    /**
     * 合作设计费 - 根据costId获取合作设计费数据
     */
    public static final String GET_COOPERATIVE_DESIGN_FEE_BY_COSTID_URL = BASE_URL + "v2/projectCost/cooperativeDesignFeeInfoByCostId.do";
    /**
     * 其他收支款项 - 获取其他收支款项列表数据
     */
    public static final String GET_OTHER_FEE_INFO_URL = BASE_URL + "/v2/projectCost/getOtherFee.do";


    //**********************IM相关*****************************************

    /**
     * IM - 获取IM人员信息
     */
    public static final String GET_NEW_IM_USER_INFO_URL = BASE_URL + "v2/im/newImUserInfo.do";

    /**
     * 获取我的团队列表（切换团队）及所在团队对应的权限
     */
    public static final String GET_COMPANY_AND_ROLE_URL = BASE_URL + "v2/org/getMyCompanyAndRoleList.do";
    /**
     * 获取（选择人员）人员列表
     */
    public static final String GET_COMPANY_USER_URL = BASE_URL + "v2/org/getCompanyUserForSelect.do";
    /**
     * 获取（选择团队）团队列表
     */
    public static final String GET_COMPANY_FOR_SELECT_URL = BASE_URL + "/v2/task/getIssueTaskCompany.do";
    /**
     * 获取（自定义群选择人员）团队联系人列表
     */
    public static final String GET_COMPANY_USER_FOR_CUSTOM_GROUP_URL = BASE_URL + "v2/org/getCompanyUserForCustomGroup.do";
    /**
     * 获取自定义群群成员
     */
    public static final String GET_CUSTOM_GROUP_MEMBER_URL = BASE_URL + "v2/im/listCustomerImGroup.do";
    /**
     * 创建自定义群
     */
    public static final String SAVE_IM_CUSTOM_GROUP_URL = BASE_URL + "v2/im/saveImgroup.do";
    /**
     * 自定义群添加群成员
     */
    public static final String ADD_USER_TO_CUSTOM_GROUP_URL = BASE_URL + "v2/im/addUserListToCustomGroup.do";
    /**
     * 退出自定义群、删除群成员
     */
    public static final String EXIT_CUSTOM_GROUP_URL = BASE_URL + "v2/im/exitGroup.do";
    /**
     * 选择签发组织，判断是否已经签发给该公司了。返回的是数据是经营负责人和设计负责人。如果没有签发，则无返回数据
     */
    public static final String VALIDATE_ISSUE_TASK_COMPANY_URL = BASE_URL + "/v2/task/validateIssueTaskCompany.do";
    /**
     * 获取我的群组列表(2016.12.19)
     */
    public static final String V2_ORG_IMGROUPLIST = BASE_URL + "/v2/org/imGroupListNew.do";
    /**
     * 用户接口(v2新加)-修改用户信息(一并提交修改)
     */
    public static final String V2_SAVE_OR_UPDATE_USER = BASE_URL + "/v2/user/v2SaveOrupdateUser.do";
    /**
     * 项目(v2新加)-获取项目文档库列表
     */
    public static final String V2_GET_PROJECT_SKY_DRIVE = BASE_URL + "/v2/projectskydrive/getProjectSkyDrive.do";
    /**
     * 项目(v2新加)-创建文件夹或者修改文件夹名称
     */
    public static final String V2_SAVE_OR_UPDTAE_FILEMASTER = BASE_URL + "/v2/projectskydrive/saveOrUpdateFileMaster.do";
    /**
     * 项目(v2新加)-删除文件或者文件夹(文档库)
     */
    public static final String V2_DELETE_PROJECTSKYDRIVE = BASE_URL + "/v2/projectskydrive/deleteProjectSkyDrive.do";
    /**
     * 上传团队LOGO
     */
    public static final String UPLOAD_COMPANY_LOGO = BASE_URL + "org/upload_companylogo.do";
    /**
     * V2 获取文件上传地址和文件服务器地址
     */
    public static final String V2_SYS_FILE_SERVER_PATH = BASE_URL + "/v2/sys/fileServerPath";
    /**
     * V2 提交报销或修改报销
     */
    public static final String V2_FINANCE_SAVE_EXPMAINANDDETAILS = BASE_URL + "/v2/finance/v2SaveOrUpdateExpMainAndDetail.do";
    /**
     * V2 提交请假申请
     */
    public static final String V2_LEAVE_SAVE_LEAVE = BASE_URL + "v2/leave/saveLeave";
    /**
     * V2 提交轻量任务
     */
    public static final String V2_MYTASK_SAVE_MYTASK = BASE_URL + "v2/mytask/saveMyTask";
    /**
     * V2 删除轻量任务
     */
    public static final String V2_MYTASK_DELETE_MYTASK = BASE_URL + "v2/mytask/deleteMyTask";
    /**
     * V2 删除报销条目
     */
    public static final String V2_FINANCE_DELETE_EXP_MAIN = BASE_URL + "v2/finance/deleteExpMain";
    /**
     * V2 查询报销详情
     */
    public static final String V2_FINANCE_GET_EXPMAIN_DETAILS = BASE_URL + "/v2/finance/v2GetExpMainDetail.do";
    /**
     * V2 查询请假详情
     */
    public static final String V2_LEAVE_GET_LEAVE_DETAISL_DETAILS = BASE_URL + "v2/leave/getLeaveDetail";
    /**
     * V2 查询 轻量任务详情
     */
    public static final String V2_MYTASK_GETMYTASKDETAILSNEW = BASE_URL + "v2/mytask/getMyTaskDetailNew";
    /**
     * V2 移交管理员验证原密码
     */
    public static final String V2_ORG_VERIFY_SYS_PASSWORD = BASE_URL + "/v2/org/verifySysPassword.do";
    /**
     * V2 获取联系人及团队列表
     */
    public static final String V2_GET_LINK_PEOPLEANDGROUP = BASE_URL + "/v2/org/getLinkPeopleAndGroup.do";
    /**
     * V2 创建团队
     */
    public static final String V2_CREATE_COMPANY = BASE_URL + "/v2/org/createCompany.do";
    /**
     * V2 获取团队基本信息
     */
    public static final String V2_GET_COMPANY_BY_ID = BASE_URL + "/v2/org/getCompanyById.do";
    /**
     * V2 编辑团队基本信息
     */
    public static final String V2_SAVE_OR_UPDATE_COMPANY = BASE_URL + "/v2/org/saveOrUpdateCompany.do";
    /**
     * V2 获取用户基本信息
     */
    public static final String V2_USER_INFO = BASE_URL + "/v2/user/userInfo.do";

    /**
     * V2员工信息
     */
    public static final String V2_PERSONAL_INFO = BASE_URL + "/v2/org/getCompanyUser.do";
    /**
     * V2 保存平台展示数据
     */
    public static final String V2_SAVE_COMPANY_FILE_LIST = BASE_URL + "v2/org/saveCompanyFileList";
    /**
     * V2 获取平台展示数据
     */
    public static final String V2_GET_COMPANYIMG = BASE_URL + "v2/org/getCompanyImg";
    /**
     * V2 获取首页数据
     */
    public static final String V2_GET_COMPANYNOTICEPROJECTDYNAMIC = BASE_URL + "/v2/org/getCompanyNoticeProjectDynamic.do";
    /**
     * V2 获取首页数据(新的)
     */
    public static final String V2_GET_HOME_DATA = BASE_URL + "v2/task/getHomeData.do";

    /**
     * V2 获取发送公告的选择范围(团队)
     */
    public static final String V2_NOTICE_GETORGTREE = BASE_URL + "/v2/notice/getOrgTree.do";
    /**
     * V2 所在团队及权限
     */
    public static final String V2_ROLE_GET_COMPANY_DEPART_AND_PERMISSION = BASE_URL + "/v2/role/getCompanyDepartAndPermission.do";
    /**
     * V2 获取验证码
     */
    public static final String V2_SYS_GET_CODE = BASE_URL + "/v2/sys/getCode.do";
    /**
     * V2 绑定邮箱
     */
    public static final String V2_USER_BINDMAIL = BASE_URL + "/v2/user/bindEmail.do";
    /**
     * V2 报销 获取关联项目接口
     */
    public static final String V2_FINANCE_GET_PROJECTLIST = BASE_URL + "/v2/finance/getProjectList.do";
    /**
     * V2 报销列表 我报销的接口
     */
    public static final String V2_FINANCE_GET_MYEXPMAINPAGE = BASE_URL + "/v2/finance/v2MyExpMainPage.do";
    /**
     * V2 报销列表 我审核的接口
     */
    public static final String V2_FINANCE_GET_MYAUDITEXPMAINPAGE = BASE_URL + "/v2/finance/v2MyAuditExpMainPage.do";
    /**
     * v2查询分公司或事业合伙人待审核列表
     */
    public static final String V2_SELECT_INVITEDDEPARTNER = BASE_URL + "/v2/org/selectInvitedPartner.do";
    /**
     * v2 新增或修改部门
     */
    public static final String V2_ORG_SAVE_OR_UPDATEDEPART = BASE_URL + "v2/org/saveOrUpdateDepart";
    /**
     * v2 获取当前部门下的子部门以及成员
     */
    public static final String V2_ORG_GET_DEPART_AND_USER = BASE_URL + "/v2/org/getDepartAndGroup.do";
    /**
     * v2 获取没有挂靠的组织
     */
    public static final String V2_ORG_GET_FILTERCOMPANY = BASE_URL + "/v2/org/getFilterCompany.do";
    /**
     * V2_获取所有成员的列表数据(用于IM列表显示)。
     */
    public static final String V2_GET_ALL_PERSON = BASE_URL + "/v2/org/v2GetAllPersons.do";
    /**
     * 获取用户权限
     */
    public static final String GET_ROLE_USER_PERMISSION_URL = BASE_URL + "v2/role/getRoleUserPermission.do";
    /**
     * 保存用户权限
     */
    public static final String SAVE_ROLE_USER_PERMISSION_URL = BASE_URL + "v2/role/saveUserPermissionList.do";
    /**
     * 获取邀请信息
     */
    public static final String V2_GET_INVITEABOUT = BASE_URL + "v2/org/getInviteAbout.do";
    /**
     * 获取当前用户拥有的组织
     */
    public static final String V2_GET_COMPANYPRINCIPAL = BASE_URL + "v2/org/getCompanyPrincipal.do";
    /**
     * 通过Code获取组织信息
     */
    public static final String V2_GET_COMPANY_BY_CODE = BASE_URL + "v2/org/getCompanyByCode.do";
    /**
     * 申请成为事业合伙人
     */
    public static final String V2_APPLY_BUSINESS_COMPANY = BASE_URL + "v2/org/applyBusinessCompany.do";
    /**
     * 获取组织动态,所有动态
     */
    public static final String V2_DYNAMIC_GET_ALL_ORG_DYNAMIC = BASE_URL + "v2/dynamic/getOrgDynamicList";
    /**
     * 获取通知公告详情
     */
    public static final String V2_NOTICE_GET_NOTICEBYIDFORDYNAMICS = BASE_URL + "v2/notice/getNoticeByIdForDynamics";
    /**
     * 获取项目动态
     */
    public static final String V2_DYNAMIC_GET_PROJEXT_DYNAMIC = BASE_URL + "/v2/dynamic/getProjectDynamicByPage";
    /**
     * 获取获取报销单号
     */
    public static final String V2_FINANCE_GET_MAX_EXP_NO = BASE_URL + "/v2/finance/getMaxExpNo.do";
    /**
     * 获取卯丁助手数据
     */
    public static final String V2_MESSAGE_GETMESSAGE = BASE_URL + "v2/message/getMessage.do";
    /**
     * 获取组织架构数据
     */
    public static final String V2_ORG_GET_LINK_PEOPLELIST = BASE_URL + "/v2/org/getLinkPeopleList.do";
    /**
     * 获取组织架构群组数据
     */
    public static final String V2_ORG_GET_ALL_GROUP_LIST = BASE_URL + "/v2/org/getAllGroupList.do";
    /**
     * 获取组织架构群组数据
     */
    public static final String GET_ALL_GROUP_LIST = BASE_URL + "v2/im/getUserIdAllImGroup.do";
    /**
     * 邀请事业合伙人发送短信
     */
    public static final String V2_ORG_INVITEPARENT = BASE_URL + "v2/org/inviteParent";
    /**
     * 设置事业合伙人别名
     */
    public static final String V2_ORG_SET_BUSSINESSPARTNERNICKNAME = BASE_URL + "v2/org/setBusinessPartnerNickName";
    /**
     * 个人中心，获取专业数据的接口
     */
    public static final String V2_USER_GET_MAJOR = BASE_URL + "v2/user/getMajor";
    /**
     * 审批，获取请假类型的接口
     */
    public static final String V2_GET_LEAVE_TYPE = BASE_URL + "v2/leave/getLeaveType";
    /**
     * 项目群成员
     */
    public static final String V2_IM_GET_PROJECT_GROUP_USER = BASE_URL + "v2/im/getProjectGroupUser ";
    /**
     * 用户分享名片
     */
    public static final String V2_USER_CARD_LINK = BASE_URL + "v2/user/userCardLink";
    /**
     * 创建自定义群
     */
    public static final String V2_IM_SAVEIMGROUP = BASE_URL + "/v2/im/saveImgroup";
    /**
     * 解散自定义群
     */
    public static final String V2_IM_DELETEIMGROUP = BASE_URL + "/v2/im/deleteImgroup";
    /**
     * 离开自定义群
     */
    public static final String V2_IM_EXITIMGROUP = BASE_URL + "/v2/im/exitGroup";
    /**
     * 获取报销审批任务申请人列表
     */
    public static final String V2_ORG_GET_EXP_TASK_MEMBERS = BASE_URL + "/v2/org/getExpTaskMembers";
    /**
     * 反馈
     */
    public static final String V2_SAVE_FEEDBACK = BASE_URL + "v2/feedback/saveFeedback";
    /**
     * OSS 获取STS凭证
     */
    public static final String GET_STS = BASE_FILE_URL + "ossFileCenter/getAppOssSignature";
    /**
     *  获取甲方信息
     */
    public static final String GET_SERACH_FIRST_PARTY = "http://192.168.13.105:8079/yongyoucloud/enterpriseSearch/queryAutoComplete";
    /**
     *  获取甲方列表
     */
    public static final String GET_FIRST_PARTY_LIST = "http://192.168.13.105:8079/yongyoucloud/enterpriseSearch/getEnterprise";
    /**
     *  获取甲方详情
     */
    public static final String GET_FIRST_PARTY_DETAILS = "http://192.168.13.105:8079/yongyoucloud/enterpriseSearch/queryEnterpriseDetail";
    /**
     *  保存联系人
     */
    public static final String SAVE_LINKMAN = "http://192.168.13.105:8079/yongyoucloud/enterpriseLinkman/saveLinkman";
    /**
     *  删除联系人
     */
    public static final String DELETE_LINKMAN = "http://192.168.13.105:8079/yongyoucloud/enterpriseLinkman/deleteLinkman";
}