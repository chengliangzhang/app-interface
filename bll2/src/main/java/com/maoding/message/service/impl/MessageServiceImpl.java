package com.maoding.message.service.impl;

import com.maoding.circle.dao.MaodingCircleDao;
import com.maoding.circle.dao.MaodingCircleReadDao;
import com.maoding.circle.dto.MaodingCircleCommentDataDTO;
import com.maoding.circle.dto.MaodingCircleReadDTO;
import com.maoding.circle.dto.QueryCircleDTO;
import com.maoding.circle.service.MaodingCircleService;
import com.maoding.commonModule.dto.QueryCopyRecordDTO;
import com.maoding.commonModule.entity.CopyRecordEntity;
import com.maoding.commonModule.service.CopyRecordService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.MsgType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.BeanUtilsEx;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.JsonUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.dao.ZInfoDAO;
import com.maoding.financial.dao.ExpAuditDao;
import com.maoding.financial.dao.ExpMainDao;
import com.maoding.financial.dao.LeaveDetailDao;
import com.maoding.financial.dto.ExpMainDTO;
import com.maoding.financial.entity.ExpAuditEntity;
import com.maoding.financial.entity.ExpMainEntity;
import com.maoding.financial.entity.LeaveDetailEntity;
import com.maoding.hxIm.dto.ImSendMessageDTO;
import com.maoding.message.dao.MessageDao;
import com.maoding.message.dto.*;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.notice.constDefine.NotifyDestination;
import com.maoding.notice.dao.NoticeDao;
import com.maoding.notice.dto.NoticeDTO;
import com.maoding.notice.service.NoticeService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dao.ProjectProcessNodeDao;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.entity.ProjectProcessNodeEntity;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.projectcost.dao.ProjectCostPaymentDetailDao;
import com.maoding.projectcost.dao.ProjectCostPointDao;
import com.maoding.projectcost.dao.ProjectCostPointDetailDao;
import com.maoding.projectcost.entity.ProjectCostPaymentDetailEntity;
import com.maoding.projectcost.entity.ProjectCostPointDetailEntity;
import com.maoding.projectcost.entity.ProjectCostPointEntity;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.role.service.PermissionService;
import com.maoding.schedule.dao.ScheduleDao;
import com.maoding.schedule.dao.ScheduleMemberDao;
import com.maoding.schedule.entity.ScheduleEntity;
import com.maoding.schedule.entity.ScheduleMemberEntity;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dto.TaskWithFullNameDTO;
import com.maoding.task.entity.ProjectTaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Idccapp21 on 2016/10/20.
 */
@Service("messageService")
public class MessageServiceImpl extends GenericService<MessageEntity> implements MessageService {

    protected final Logger log= LoggerFactory.getLogger(this.getClass());

    private final String SEPARATOR = " ;";

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private ProjectCostPointDetailDao projectCostPointDetailDao;

    @Autowired
    private ProjectCostPaymentDetailDao projectCostPaymentDetailDao;

    @Autowired
    private ProjectCostPointDao projectCostPointDao;

    @Autowired
    private ProjectProcessNodeDao projectProcessNodeDao;

    @Autowired
    private ExpMainDao expMainDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private MyTaskDao myTaskDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ZInfoDAO zInfoDAO;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private LeaveDetailDao leaveDetailDao;

    @Autowired
    private ExpAuditDao expAuditDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private ScheduleMemberDao scheduleMemberDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MaodingCircleReadDao maodingCircleReadDao;

    @Autowired
    private MaodingCircleDao maodingCircleDao;

    @Autowired
    private MaodingCircleService maodingCircleService;

    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    @Autowired
    private CopyRecordService copyRecordService;

    @Value("${server.url}")
    protected String serverUrl;

    @Override
    public ResponseBean sendMessage(MessageEntity messageEntity) {
        if ((messageEntity == null) || (messageEntity.getMessageType() == 0)) {
            return ResponseBean.responseSuccess(null);//如果为0，则直接返回，不推送消息
        }
        String projectId = messageEntity.getProjectId();
        String targetId = messageEntity.getTargetId();
        Map<String,String> para = new HashMap<>();
        String[] params = null;
        switch (messageEntity.getMessageType()) {
            case SystemParameters.MESSAGE_TYPE_1: //"你成为了 “?” 乙方经营负责人"
            case SystemParameters.MESSAGE_TYPE_2: //"你成为了 “?” 乙方设计负责人"
            case SystemParameters.MESSAGE_TYPE_4: //"你成为了 “?” 设计负责人"
                //已设置了所有的?
                if (messageEntity.getMessageContent() == null){
                    para.put("projectName", getProjectName(messageEntity.getProjectId()));
                }else {
                    para.put("projectName", getProjectName(messageEntity.getMessageContent()));
                }
                break;
            case SystemParameters.MESSAGE_TYPE_3: //"hi,?完成了“?”的立项，请你担任该项目的经营负责人，请在任务中查看并进行<a href=\"?\"［任务签发］</a>，谢谢！"
                //已设置了第二个?
                para.put("projectName",  messageEntity.getMessageContent());
                para.put("url", "/iWork/project/projectInformation/" + messageEntity.getProjectId() + "/2");
                break;
            case SystemParameters.MESSAGE_TYPE_301:
            case SystemParameters.MESSAGE_TYPE_302: //"hi,?组织，经营负责人?发布了“?”的设计任务，请你担任该项目的经营负责人，请在任务中查看并进行<a href=\"?\"［任务签发］</a>，谢谢！"
            case SystemParameters.MESSAGE_TYPE_303: //"hi,?组织，经营负责人?发布了“?”的设计任务，请在任务中查看并进行<a href=\"?\"［任务签发］</a>，谢谢！"
            case SystemParameters.MESSAGE_TYPE_305:
            case SystemParameters.MESSAGE_TYPE_306:
            case SystemParameters.MESSAGE_TYPE_410:
                para.put("projectName", getProjectName(messageEntity.getProjectId()));
                para.put("url", "/iWork/project/projectInformation/" + messageEntity.getProjectId() + "/2");
                break;

            case SystemParameters.MESSAGE_TYPE_401: //"hi,经营负责人?发布了“?”的设计任务，请你担任该项目的设计负责人，请在任务中查看并进行<a href=\"?\"［生产安排］</a>，谢谢！"
            case SystemParameters.MESSAGE_TYPE_402: //"hi,经营负责人?发布了“?”的设计任务，请在任务中查看并进行<a href=\"?\">［生产安排］</a>，谢谢！"
                para.put("projectName", getProjectName(messageEntity.getProjectId()));
                para.put("url", "/iWork/project/projectInformation/" + messageEntity.getProjectId() + "/3");

                break;
            case SystemParameters.MESSAGE_TYPE_403: //"hi，设计负责人?进行了“?-?”的生产安排，你被设定为任务负责人，详情点击<a href="?">［我的任务］</a>查看，谢谢！"
            case SystemParameters.MESSAGE_TYPE_404: //"hi,任务负责人?进行了“?-?”的生产安排，你被设定为任务负责人，详情点击<a href=\"?\">［我的任务］</a>查看，谢谢！"
                para.put("projectName", getProjectName(messageEntity.getProjectId()));
                para.put("taskName", getTaskNameTree(messageEntity.getTargetId()));
                para.put("url", "/iWork/myTask/taskList");
                break;
            case SystemParameters.MESSAGE_TYPE_405: //"hi，“?-?”的?任务已完成，请你确认，谢谢！"
                //已设置了第3个nodeName
                para.put("projectName", getProjectName(messageEntity.getProjectId()));
                para.put("taskName", getTaskNameTree(messageEntity.getTargetId()));
                para.put("nodeName",  messageEntity.getMessageContent());
                break;
            case SystemParameters.MESSAGE_TYPE_406: //"hi，“?-?”的设计任务已完成，请你确认，谢谢！"
                para.put("projectName",getProjectName(messageEntity.getProjectId()));
                para.put("taskName", getTaskNameTree(messageEntity.getTargetId()));
                break;
            case SystemParameters.MESSAGE_TYPE_407: //"hi，“?-?”的设计任务已完成，请你确认，谢谢！"
            case SystemParameters.MESSAGE_TYPE_408: //"hi，“?-?”所有生产任务已完成，请你确认，谢谢！"
            case SystemParameters.MESSAGE_TYPE_409: //"hi，“?-?”的设计任务已完成，请你跟进相关项目收支的经营工作，谢谢！"
                //假定第2个?已被填写
                para.put("projectName", getProjectName(messageEntity.getProjectId()));
                para.put("taskName", messageEntity.getMessageContent());
                break;
            case SystemParameters.MESSAGE_TYPE_601: //"hi，设计负责人?变更了“?-?”的计划进度【由?至?变更为?至?】，请查看并作相应调整，谢谢！"
            case SystemParameters.MESSAGE_TYPE_602: //"hi，任务负责人?变更了“?-?”的计划进度【由?至?变更为?至?】，请查看并作相应调整，谢谢！"
            case SystemParameters.MESSAGE_TYPE_603: //"hi，经营责人?变更了“?-?”的计划进度【由?至?变更为?至?】，请查看并作相应调整，谢谢！"
            case SystemParameters.MESSAGE_TYPE_604: //"hi，?组织经营负责人?变更了“?-?”的计划进度【由?至?变更为?至?】，请查看并作相应调整，谢谢！"
                //已设置了第2-end参数 (web端需要调整）
                params = messageEntity.getMessageContent().split(SEPARATOR);
                para.put("projectName", params[0] != null ? params[0] : "");
                para.put("taskName", params[1] != null ? params[1] : "");
                para.put("startTime1", params[2] != null ? params[2] : "");
                para.put("endTime1", params[3] != null ? params[3] : "");
                para.put("startTime2", params[4] != null ? params[4] : "");
                para.put("endTime2", params[5] != null ? params[5] : "");
                break;
            case SystemParameters.MESSAGE_TYPE_5: //"你成为了“? - ?”的经营负责人"
                //已设置了所有的?
                break;
            case SystemParameters.MESSAGE_TYPE_501: //"hi，设计负责人?进行了“?-?”的生产安排，你将参与该任务的?工作，详情请点击<a href=\"?\">［我的任务］</a>查看，谢谢！"
            case SystemParameters.MESSAGE_TYPE_502: //"hi，任务负责人?进行了“?-?”的生产安排，你将参与该任务的?工作，详情请点击<a href=\"?\">［我的任务］</a>查看，谢谢！"
                //已设置了倒数第2个?
                para.put("projectName", getProjectName(messageEntity.getProjectId()));
                para.put("taskName",  getTaskNameTree(messageEntity.getTargetId()));
                para.put("nodeName", messageEntity.getMessageContent());
                para.put("url", "/iWork/myTask/taskList");
                break;
            case SystemParameters.MESSAGE_TYPE_503: //"hi，?完成了“?-?”的?工作，请你?，谢谢！"
                //已设置了倒数第1-2个?
                params = messageEntity.getMessageContent().split(SEPARATOR);

                para.put("projectName", getProjectName(messageEntity.getProjectId()));
                para.put("taskName", getTaskNameTree(messageEntity.getTargetId()));
                para.put("nodeName", params[0] != null ? params[0] : "");
                para.put("toNodeName", params[1] != null ? params[1] : "");
                break;
            case SystemParameters.MESSAGE_TYPE_6: //"你成为了“? - ?”的设计负责人"
            case SystemParameters.MESSAGE_TYPE_7: //"你成为了“? - ?”的任务负责人"
                para.put("projectName", getProjectName(projectId));
                para.put("taskName", getTaskNameTree(targetId));
                break;
            case SystemParameters.MESSAGE_TYPE_8: //"“? - ?”设计任务的?"
                //已设置了倒数第1个?
                para.put("projectName", getProjectName(projectId));
                para.put("taskName", getTaskNameTree(targetId));
                para.put("nodeName", messageEntity.getMessageContent());
                break;
            case SystemParameters.MESSAGE_TYPE_9: //"“? - ?”设计任务已全部完成"
                para.put("projectName",getProjectName(projectId));
                para.put("taskName", getTaskNameTree(targetId));
                break;
            case SystemParameters.MESSAGE_TYPE_10: //"你成为了 “? - ?” 的?人"
                para.put("projectName", getProjectName(projectId));
                para.put("taskName", getTaskNameTree(targetId));
                para.put("nodeName", getNodeName(messageEntity.getParam1()));
                break;
            case SystemParameters.MESSAGE_TYPE_21: //"“? - ?”所有子任务已全部完成"
                ProjectCostPointDetailEntity detail2 = projectCostPointDetailDao.selectById(targetId);
                para.put("projectName", getProjectName(projectId));
                para.put("taskName", getFeeDescription(detail2));
                break;
            case SystemParameters.MESSAGE_TYPE_19: //"?申请报销“?”共计?元"
            case SystemParameters.MESSAGE_TYPE_20: //"hi，?拒绝了你申请的“?”报销金额?元"
            case SystemParameters.MESSAGE_TYPE_22: //"hi，?同意你“?”的报销金额为?元，谢谢！"
            case SystemParameters.MESSAGE_TYPE_222: //"hi，?同意你“?”的报销金额为?元，谢谢！"
            case SystemParameters.MESSAGE_TYPE_223: //"hi，?同意你“?”的报销金额为?元，谢谢！"
            case SystemParameters.MESSAGE_TYPE_224: //"hi，?同意你“?”的报销金额为?元，谢谢！"
            case SystemParameters.MESSAGE_TYPE_221: //"hi，?转交了?申请的“?”报销金额?元，请你审批，谢谢！"
            case SystemParameters.MESSAGE_TYPE_225: //"hi，?转交了?申请的“?”报销金额?元，请你审批，谢谢！"
            case SystemParameters.MESSAGE_TYPE_236:
            case SystemParameters.MESSAGE_TYPE_237:
                ExpMainDTO dto4 = expMainDao.getExpMainDetail(targetId);
                para.put("expUserName", getExpUserName(targetId));
                para.put("expName", getExpName(targetId));
                para.put("expAmount", getExpAmount(dto4));
                //查询审批原因
                if(messageEntity.getMessageType()==SystemParameters.MESSAGE_TYPE_20 || messageEntity.getMessageType()==SystemParameters.MESSAGE_TYPE_223){
                    ExpAuditEntity recallAudit = this.expAuditDao.selectLastRecallAudit(messageEntity.getTargetId());
                    if(recallAudit!=null){
                        para.put("reason",recallAudit.getAuditMessage());
                    }
                }
                break;
            /***********请假部分***********/
//            put("226","%sendUserName% 提交了请假申请，请假类型：%leaveTypeName%，请假时间：%startTime1%-%endTime1%，请您审批。");//ok
//            put("227","%sendUserName% 拒绝了你的请假申请，请假类型“%leaveTypeName%”，请假时间：%startTime1%-%endTime1%，退回原因：%reason%。");//ok
//            //您提交的请假申请，请假类型：事假，请假时间：2017/12/26 09:00-2017/12/27 18:00，已完成审批。
//            put("228","你提交的请假申请，请假类型：%leaveTypeName%，请假时间：%startTime1%-%endTime1%，已完成审批。");
//            put("229","%sendUserName%同意并转交了%expUserName%的请假申请，请假类型：%leaveTypeName%，请假时间：%startTime1%-%endTime1%给你，请你审批。");
//            /***********出差部分***********/
//            put("230","%sendUserName% 提交了出差申请，出差地：%address%，出差时间：%startTime1%-%endTime1%，请您审批。");
//            put("231","%sendUserName% 拒绝了你的出差申请，出差地“%address%”，出差时间：%startTime1%-%endTime1%，退回原因：%reason%。");//ok
//            put("232","你提交的出差申请，出差地“%address%”，出差时间：%startTime1%-%endTime1%，已完成审批。");
//            put("233","%sendUserName%同意并转交了%expUserName%的出差申请，出差地：%address%，出差时间：%startTime1%-%endTime1%，%sendUserName%给你，请你审批。");
            case SystemParameters.MESSAGE_TYPE_226:
            case SystemParameters.MESSAGE_TYPE_227:
            case SystemParameters.MESSAGE_TYPE_228:
            case SystemParameters.MESSAGE_TYPE_229:
            case SystemParameters.MESSAGE_TYPE_230:
            case SystemParameters.MESSAGE_TYPE_231:
            case SystemParameters.MESSAGE_TYPE_232:
            case SystemParameters.MESSAGE_TYPE_233:
            case SystemParameters.MESSAGE_TYPE_238:
            case SystemParameters.MESSAGE_TYPE_239:
                LeaveDetailEntity leaveDetail = leaveDetailDao.getLeaveDetailByMainId(targetId);
                if(leaveDetail!=null){
                    para.put("address", leaveDetail.getAddress());
                    para.put("leaveTypeName",this.getLeaveTypeName(leaveDetail.getLeaveType()));
                    para.put("startTime1", DateUtils.date2Str(leaveDetail.getStartTime(),DateUtils.time_sdf_slash));
                    para.put("endTime1", DateUtils.date2Str(leaveDetail.getEndTime(),DateUtils.time_sdf_slash));
                }
                para.put("expUserName", getExpUserName(targetId));
                //查询审批原因
                if(messageEntity.getMessageType()==SystemParameters.MESSAGE_TYPE_227 || messageEntity.getMessageType()==SystemParameters.MESSAGE_TYPE_231){
                    ExpAuditEntity recallAudit = this.expAuditDao.selectLastRecallAudit(messageEntity.getTargetId());
                    if(recallAudit!=null){
                        para.put("reason",recallAudit.getAuditMessage());
                    }
                }
                break;
//            //财务拨款（报销，费用）
//            put("234","你申请的报销“%expName%”共计%expAmount%元，财务已拨款。");
//            put("235","你申请的费用“%expName%”共计%expAmount%元，财务已拨款。");
            case SystemParameters.MESSAGE_TYPE_234:
            case SystemParameters.MESSAGE_TYPE_235:
                ExpMainDTO expMainDTO = expMainDao.getExpMainDetail(targetId);
                para.put("expUserName", getExpUserName(targetId));
                para.put("expName", expMainDTO.getExpName());
                para.put("expAmount", getExpAmount(expMainDTO));
                break;
            case SystemParameters.MESSAGE_TYPE_11: //"hi，?发起了“?：?”的技术审查费?万元，请确认付款金额，谢谢！"
            case SystemParameters.MESSAGE_TYPE_12: //"“? - 技术审查费 - ?” 金额：?万，已确认付款"
            case SystemParameters.MESSAGE_TYPE_13: //"“? - 技术审查费 - ?” 金额：?万，已确认到账"
            case SystemParameters.MESSAGE_TYPE_14: //"hi，?发起了“?：?”的合作设计费?万元，请确认付款金额，谢谢！"
            case SystemParameters.MESSAGE_TYPE_15: //"“? - 合作设计费 - ?” 金额：?万，已确认付款"
            case SystemParameters.MESSAGE_TYPE_16: //"“? - 合作设计费 - ?” 金额：?万，已确认到账"
            case SystemParameters.MESSAGE_TYPE_17: //"hi，?发起了“?：?”的合同回款?万元，请你跟进并确认实际到帐金额和日期，谢谢！"
            case SystemParameters.MESSAGE_TYPE_31: //"hi，?发起了“?：?”的其他支出?万元，请你跟进并确认实际付款金额和日期，谢谢！"
            case SystemParameters.MESSAGE_TYPE_32: //"hi，?发起了“?：?”的其他收入?万元，请你跟进并确认实际到帐金额和日期，谢谢！"
                ProjectCostPointDetailEntity detail6 = projectCostPointDetailDao.selectById(targetId);
                para.put("projectName", getProjectName(projectId));
                para.put("feeDescription",getFeeDescription(detail6));
                para.put("fee",  getFee(detail6));
                break;
            case SystemParameters.MESSAGE_TYPE_18: //"hi，?确认了“?：?”的实际到金额为?万元，到账日期为?，谢谢！"
            case SystemParameters.MESSAGE_TYPE_23: //"hi，?确认了“?：?”的技术审查费付款金额为?万元，请你跟进并确认实际付款日期，谢谢！"
            case SystemParameters.MESSAGE_TYPE_24: //"hi，?确认了“?：?”的技术审查费付款金额为?万元，请你跟进并确认实际到账日期，谢谢！"
            case SystemParameters.MESSAGE_TYPE_25: //"hi，?确认了“?：?”的合作设计费付款金额为?万元，请你跟进并确认实际付款日期，谢谢！"
            case SystemParameters.MESSAGE_TYPE_26: //"hi，?确认了“?：?”的合作设计费付款金额为?万元，请你跟进并确认实际到账日期，谢谢！"
            case SystemParameters.MESSAGE_TYPE_27: //"hi，?确认了“?：?”的技术审查费付款金额为?万元，实际付款日期为?，谢谢！"
            case SystemParameters.MESSAGE_TYPE_28: //"hi，?确认了“?：?”的技术审查费到账金额为?万元，实际到账日期为?，谢谢！"
            case SystemParameters.MESSAGE_TYPE_29: //"hi，?确认了“?：?”的合作设计费付款金额为?万元，实际付款日期为?，谢谢！"
            case SystemParameters.MESSAGE_TYPE_30: //"hi，?确认了“?：?”的合作设计费到账金额为?万元，实际到账日期为?，谢谢！"
            case SystemParameters.MESSAGE_TYPE_33: //"hi，?确认了“?：?”的实际付款金额为?万元，付款日期为?，谢谢！"
            case SystemParameters.MESSAGE_TYPE_34: //"hi，?确认了“?：?”的实际到账金额为?万元，到账日期为?，谢谢！"
                ProjectCostPaymentDetailEntity payment4 = projectCostPaymentDetailDao.selectById(targetId);
                para.put("projectName", getProjectName(projectId));
                para.put("feeDescription", getCostPointName(messageEntity.getParam1()));
                para.put("fee", getPaymentFee(payment4));
                para.put("paymentDate", getPaymentDate(payment4, messageEntity.getMessageType()));
                break;
            case SystemParameters.MESSAGE_TYPE_35: //"“? - ?”的所有设计任务已完成"
                para.put("projectName", getProjectName(projectId));
                para.put("taskName", getTaskNameTree(targetId));
                break;
            case SystemParameters.MESSAGE_TYPE_NEW_SYSTEM_MANAGER: //hi,你被设定为“圆正测试设计院”系统管理员，相应权限请点击［个人设置］查看。
            case SystemParameters.MESSAGE_TYPE_ROLE_CHANGE: //hi，系统管理员重新设置了你的权限，请点击［个人设置］查看。
            case SystemParameters.MESSAGE_TYPE_NEW_ORG_MANAGER: //hi,你被设定为“圆正测试设计院”企业负责人，相应权限请点击［个人设置］查看。
                para.put("url", "/iWork/personal/center");
                break;
            //////确定乙方:hi,“卯丁科技大厦一期”项目由“立项组织/XXX”完成立项，请你根据项目进度跟进相关工作，谢谢！
            case SystemParameters.MESSAGE_TYPE_PART_B:
                para.put("projectName", getProjectName(projectId));
                break;
            ////第一次发布任务，则给本团队的企业负责人推送消息
            case SystemParameters.MESSAGE_TYPE_PUBLISH_TASK_ORG_MANAGER: //hi,“%projectName%”项目已完成立项，该项目的经营负责人是：%projectManagerName%，设计负责人是：%designerName%，你可根据具体情况进行调整，谢谢！
                para.put("projectName", getProjectName(projectId));
                para.put("projectManagerName",this.getOperatorName(messageEntity.getProjectId(),messageEntity.getSendCompanyId()));
                para.put("designerName", getDesignerName(messageEntity.getProjectId(), messageEntity.getSendCompanyId()));
                break;
            //会议，日程消息
            case SystemParameters.MESSAGE_TYPE_701:
            case SystemParameters.MESSAGE_TYPE_702:
            case SystemParameters.MESSAGE_TYPE_703:
            case SystemParameters.MESSAGE_TYPE_704:
            case SystemParameters.MESSAGE_TYPE_705:
            case SystemParameters.MESSAGE_TYPE_706:
            case SystemParameters.MESSAGE_TYPE_707:
            case SystemParameters.MESSAGE_TYPE_708:
                ScheduleEntity scheduleEntity = this.scheduleDao.selectById(messageEntity.getTargetId());
                para.put("scheduleContent",scheduleEntity.getContent());
                para.put("startTime1",DateUtils.date2Str(scheduleEntity.getStartTime(),DateUtils.time_sdf_slash));
                para.put("endTime1",DateUtils.date2Str(scheduleEntity.getEndTime(),DateUtils.time_sdf_slash));
                para.put("reminderTime",scheduleEntity.getReminderTime()+"");
                if(!StringUtil.isNullOrEmpty(messageEntity.getParam1())){
                    ScheduleMemberEntity memberEntity = scheduleMemberDao.selectById(messageEntity.getParam1());
                    if(memberEntity!=null){
                        para.put("reason",memberEntity.getRefuseReason());
                    }
                }
                break;
            case SystemParameters.MESSAGE_TYPE_901:
            case SystemParameters.MESSAGE_TYPE_902:
                MyTaskEntity task = myTaskDao.selectById(messageEntity.getTargetId());
                para.put("taskName",task.getTaskTitle());
                para.put("scheduleContent",task.getTaskContent());
                para.put("startTime1",StringUtil.isNullOrEmpty(task.getStartDate())?"无":DateUtils.date2Str(task.getStartDate(),DateUtils.date_sdf2));
                para.put("endTime1",StringUtil.isNullOrEmpty(task.getDeadline())?"无":DateUtils.date2Str(task.getDeadline(),DateUtils.date_sdf2));
                break;
            default:
                break;
        }
        //统一设置发送者人的名字
        para.put("sendUserName", getUserName(messageEntity.getSendCompanyId(), messageEntity.getCreateBy()));
        para.put("sendCompanyName", this.getCompanyName(messageEntity.getSendCompanyId()));
        messageEntity.setId(StringUtil.buildUUID());
        try{
            messageEntity.setMessageContent(JsonUtils.obj2json(para));
        }catch (Exception e){
            log.error("MessageServiceImpl:messageEntity.setMessageContent(JsonUtils.obj2json(para)) 报错。");
        }
        messageDao.insert(messageEntity);
        sendMessageToClient(messageEntity);
        return ResponseBean.responseSuccess("发送成功");
    }

    /**
     * 方法描述：发送消息
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    @Override
    public ResponseBean sendMessage(String projectId, String companyId, String targetId, int messageType, String paramId,
                                    String userId, String accountId, String content) throws Exception {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setCompanyId(companyId);
        messageEntity.setProjectId(projectId);
        messageEntity.setTargetId(targetId);
        messageEntity.setMessageType(messageType);
        messageEntity.setParam1(paramId);
        messageEntity.setUserId(userId);
        messageEntity.setMessageContent(content);
        messageEntity.setCreateBy(accountId);
        return this.sendMessage(messageEntity);
    }
    @Override
    public void sendMessageForProcess(SendMessageDTO messageDTO) throws Exception{
        if ((messageDTO == null) || (messageDTO.getMessageType() == 0)) return;

        String messageContent = "";
        if (!StringUtil.isNullOrEmpty(messageDTO.getContent1())) {
            messageContent += messageDTO.getContent1();
        }
        if (!StringUtil.isNullOrEmpty(messageDTO.getContent2())) {
            messageContent += this.SEPARATOR + messageDTO.getContent2();
        }
        MessageEntity messageEntity = new MessageEntity();
        //设置通用属性
        BeanUtilsEx.copyProperties(messageDTO, messageEntity);
        //设置操作者
        messageEntity.setCreateBy(messageDTO.getAccountId());
        messageEntity.setMessageContent(messageContent);
        this.sendMessage(messageEntity);
    }

    @Override
    public void sendMessageForDesigner(SendMessageDTO messageDTO) throws Exception{
        if (messageDTO == null) return;

        MessageEntity messageEntity = new MessageEntity();
        //设置通用属性
        BeanUtilsEx.copyProperties(messageDTO, messageEntity);
        //设置操作者
        messageEntity.setCreateBy(messageDTO.getAccountId());
        //设置以任务负责人还是设计负责人发送消息
        ProjectMemberEntity designer = null;
        try {
            designer = this.projectMemberService.getTaskDesigner(messageDTO.getNodeId());//任务负责人
        } catch (Exception e) {
            designer = null;
        }
        if ((designer != null) && StringUtil.isSame(designer.getAccountId(), messageDTO.getAccountId())) {
            //任务负责人推送的信息
            messageEntity.setMessageType(messageDTO.getMessageType2());
        } else {
            //设计负责人推送的信息
            messageEntity.setMessageType(messageDTO.getMessageType1());
        }
        this.sendMessage(messageEntity);
    }

    @Override
    public void sendMessageForProjectManager(SendMessageDTO dto) {
        ProjectMemberEntity member = projectMemberService.getOperatorManager(dto.getProjectId(),dto.getCompanyId());
        String userId = "";
        MessageEntity m = new MessageEntity();
        m.setMessageType(dto.getMessageType());
        m.setProjectId(dto.getProjectId());
        m.setCompanyId(dto.getCompanyId());
        m.setCreateBy(dto.getAccountId());
        m.setSendCompanyId(dto.getSendCompanyId());
        if(member!=null && !dto.getAccountId().equals(member.getAccountId()) && !userId.contains(member.getAccountId())){
            m.setUserId(member.getAccountId());
            this.sendMessage(m);
            userId+=member.getAccountId();
        }
        member  = projectMemberService.getOperatorAssistant(dto.getProjectId(),dto.getCompanyId());
        if(member!=null && !dto.getAccountId().equals(member.getAccountId()) && !userId.contains(member.getAccountId())){
            m.setUserId(member.getAccountId());
            this.sendMessage(m);
        }
    }

    @Override
    public void sendMessageForCopy(SendMessageDTO messageDTO) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setTargetId(messageDTO.getTargetId());
        //把最新的审批记录的id关联上
        messageEntity.setParam1(null);
        messageEntity.setSendCompanyId(messageDTO.getCurrentCompanyId());
        messageEntity.setCreateBy(messageDTO.getAccountId());
        messageEntity.setMessageType(messageDTO.getMessageType());
        List<CopyRecordEntity> copyList = this.copyRecordService.selectCopyByCompanyUserId(new QueryCopyRecordDTO(messageDTO.getTargetId()));
        for(CopyRecordEntity c:copyList){
            CompanyUserEntity u = companyUserDao.selectById(c.getCompanyUserId());
            if(u!=null && "1".equals(u.getAuditStatus())){
                messageEntity.setUserId(u.getUserId());
                messageEntity.setCompanyId(u.getCompanyId());
                this.sendMessage(messageEntity);
            }
        }
    }

    /**
     * 通用的发送消息方法
     * @param toUserIdList 目标用户ID列表，如果为空则从origin,target数据生成
     * @param projectId    操作的项目ID
     * @param companyId    操作者的组织ID
     * @param userId       操作者的用户ID
     * @return: 发送失败原因，如果成功，返回null
     */
    @Override
    public <T> String sendMessage(T origin, T target, List<String> toUserIdList, String projectId, String companyId, String userId) throws Exception {
        if ((origin == null) && (target == null)) return "无信息需要发送";
        //补填缺失参数
        //项目编号
        if ((projectId == null) && (target != null)) projectId = (String) BeanUtilsEx.getProperty(target, "projectId");
        if ((projectId == null) && (origin != null)) projectId = (String) BeanUtilsEx.getProperty(origin, "projectId");

        //操作者公司编号和用户编号
        if ((companyId == null) && (target != null))
            companyId = (String) BeanUtilsEx.getProperty(target, "currentCompanyId");
        if ((companyId == null) && (origin != null))
            companyId = (String) BeanUtilsEx.getProperty(origin, "currentCompanyId");
        if ((companyId == null) && (target != null)) companyId = (String) BeanUtilsEx.getProperty(target, "companyId");
        if ((companyId == null) && (origin != null)) companyId = (String) BeanUtilsEx.getProperty(origin, "companyId");

        if ((userId == null) && (target != null)) userId = (String) BeanUtilsEx.getProperty(target, "accountId");
        if ((userId == null) && (target != null)) userId = (String) BeanUtilsEx.getProperty(target, "handlerId");
        if ((userId == null) && (target != null)) userId = (String) BeanUtilsEx.getProperty(target, "userId");
        if ((userId == null) && (target != null)) userId = (String) BeanUtilsEx.getProperty(target, "updateBy");
        if ((userId == null) && (target != null)) userId = (String) BeanUtilsEx.getProperty(target, "createBy");
        if ((userId == null) && (origin != null)) userId = (String) BeanUtilsEx.getProperty(origin, "accountId");
        if ((userId == null) && (origin != null)) userId = (String) BeanUtilsEx.getProperty(origin, "userId");
        if ((userId == null) && (origin != null)) userId = (String) BeanUtilsEx.getProperty(origin, "updateBy");
        if ((userId == null) && (origin != null)) userId = (String) BeanUtilsEx.getProperty(origin, "createBy");

        //调用相应创建日志方法
        ResponseBean ajax = null;
        if ((origin instanceof TaskWithFullNameDTO) || (target instanceof TaskWithFullNameDTO)) {
            ajax = sendMessage(createMessage((TaskWithFullNameDTO) origin, (TaskWithFullNameDTO) target, toUserIdList, projectId, companyId, userId));
        } else if ((origin instanceof MyTaskEntity) || (target instanceof MyTaskEntity)) {
            ajax = sendMessage(createMessage((MyTaskEntity) origin, (MyTaskEntity) target, toUserIdList, projectId, companyId, userId));
        }
        return (ajax == null || "0".equals(ajax.getError())) ? null : "发送失败";
    }


    @Override
    public ResponseBean sendMessage(List<MessageEntity> messageList) throws Exception {
        if (messageList != null) {
            for (MessageEntity entity : messageList) {
                this.sendMessage(entity);
            }
        }
        return ResponseBean.responseSuccess("发送成功");
    }

    //根据个人任务信息生成消息，用于设计负责人向经营负责人发送设计任务已完成消息
    private List<MessageEntity> createMessage(MyTaskEntity origin, MyTaskEntity target, List<String> toUserIdList, String projectId, String companyId, String userId) {
        if ((origin == null) && (target == null)) return null;

        //--------------设置消息共有字段---------------
        //设置通用字段
        MessageEntity common = new MessageEntity();

        String id = (target != null) ? target.getId() : null;
        if ((id == null) && (origin != null)) id = origin.getId();

        common.set4Base(userId, null, new Date(), null);
        common.setProjectId(projectId);
        common.setTargetId(id);
        common.setStatus("0");

        //设置特有字段
        Integer type = (target != null) ? target.getTaskType() : null;
        if ((type == null) && (origin != null)) type = origin.getTaskType();

        try {
            if (type == SystemParameters.TASK_COMPLETE) {
                common.setMessageType(SystemParameters.MESSAGE_TYPE_PRODUCT_TASK_FINISH);

                if (toUserIdList == null) {
                    toUserIdList = new ArrayList<>();
                    ProjectMemberEntity mgr = projectMemberService.getOperatorManager(projectId, companyId);
                    if ((mgr != null) && !StringUtil.isEmpty(mgr.getAccountId()) && !(StringUtil.isSame(mgr.getAccountId(), userId)) && !(toUserIdList.contains(mgr.getAccountId()))) {
                        //获取任务名称
                        String taskId = (target != null) ? target.getTargetId() : null;
                        if ((taskId == null) && (origin != null)) taskId = origin.getTargetId();
                        TaskWithFullNameDTO task = zInfoDAO.getTaskByTaskId(taskId);
                        String taskName = (task != null) ? task.getTaskName() : "";
                        //获取用户名称
                        String toUserName = companyUserDao.getUserNameByCompanyIdAndUserId(companyId, mgr.getAccountId());
                        //设置提示语
                        common.setMessageContent(toUserName + SEPARATOR + taskName);
                        //添加目标用户
                        toUserIdList.add(mgr.getAccountId());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

        //---------------生产发送信息列表，补充各消息特有字段
        return createMessageList(common, toUserIdList, companyId);
    }

    //根据任务信息生成消息，用于发送时间更改消息
    private List<MessageEntity> createMessage(TaskWithFullNameDTO origin, TaskWithFullNameDTO target, List<String> toUserIdList, String projectId, String companyId, String userId) {
        if ((origin == null) && (target == null)) return null;

        //--------------设置消息共有字段---------------
        //设置通用字段
        MessageEntity common = new MessageEntity();

        String id = (target != null) ? target.getId() : null;
        if ((id == null) && (origin != null)) id = origin.getId();

        common.set4Base(userId, null, new Date(), null);
        common.setProjectId(projectId);
        common.setTargetId(id);
        common.setStatus("0");

        //设置特有字段
        Integer type = (target != null) ? target.getTaskType() : null;
        if ((type == null) && (origin != null)) type = origin.getTaskType();

        String userName = "";
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(userId, companyId);
        if (user != null) {
            userName = user.getUserName();
        }
        String taskName = (target != null) ? target.getTaskName() : null;
        if ((taskName == null) && (origin != null)) taskName = origin.getTaskName();

        String toCompanyId = ((target != null) && !StringUtil.isEmpty(target.getToCompanyId())) ? target.getToCompanyId() : companyId;

        String op = ((origin != null) && !StringUtil.isEmpty(origin.getTaskPeriod())) ? origin.getTaskPeriod() : " 未设置 ";
        String tp = ((target != null) && !StringUtil.isEmpty(target.getTaskPeriod())) ? target.getTaskPeriod() : " 未设置 ";

        try {
            if (type == SystemParameters.TASK_TYPE_PHASE) {
                common.setMessageType(SystemParameters.MESSAGE_TYPE_PHASE_TASK_CHANGE);
                common.setMessageContent(userName + SEPARATOR + taskName + SEPARATOR + op + SEPARATOR + tp);
                if (toUserIdList == null) {
                    toUserIdList = new ArrayList<>();
                    ProjectMemberEntity mgr = projectMemberService.getOperatorManager(projectId, companyId);
                    if ((mgr != null) && !StringUtil.isEmpty(mgr.getAccountId()) && !(StringUtil.isSame(mgr.getAccountId(), userId)) && !(toUserIdList.contains(mgr.getAccountId()))) {
                        toUserIdList.add(mgr.getAccountId());
                    }
                }
            } else if (type == SystemParameters.TASK_TYPE_ISSUE) {
                common.setMessageType(SystemParameters.MESSAGE_TYPE_ISSUE_TASK_CHANGE);
               // common.setMessageContent(taskName + SEPARATOR + userName + SEPARATOR + taskName + SEPARATOR + op + SEPARATOR + tp);
                common.setMessageContent(companyDao.getCompanyName(companyId) + SEPARATOR + userName + SEPARATOR + taskName + SEPARATOR + op + SEPARATOR + tp);
                if (toUserIdList == null) {
                    toUserIdList = new ArrayList<>();
                    String targetToCompanyId = (target != null) ? target.getToCompanyId() : null;
                    String originToCompanyId = (origin != null) ? origin.getToCompanyId() : null;
                    if (!(StringUtil.isSame(originToCompanyId, companyId)) || !(StringUtil.isSame(targetToCompanyId, companyId))) {
                        common.setMessageContent(companyDao.getCompanyName(companyId) + SEPARATOR + common.getMessageContent());
                    }

                    //建立要发送到的组织列表
                    List<String> companyIdList = new ArrayList<>();
                    if (!(StringUtil.isEmpty(originToCompanyId)) && !(companyIdList.contains(originToCompanyId))) {
                        companyIdList.add(originToCompanyId);
                    }
                    if (!(StringUtil.isEmpty(targetToCompanyId)) && !(companyIdList.contains(targetToCompanyId))) {
                        companyIdList.add(targetToCompanyId);
                    }
                    //建立要发送的用户列表
                    for (String cid : companyIdList) {
                        if (!(StringUtil.isSame(cid, companyId))) { //签发给外部组织的获取外部组织本项目的经营负责人
                            ProjectMemberEntity mgr = projectMemberService.getOperatorManager(projectId, cid);
                            if ((mgr != null) && !StringUtil.isEmpty(mgr.getAccountId()) && !(StringUtil.isSame(mgr.getAccountId(), userId)) && !(toUserIdList.contains(mgr.getAccountId()))) {
                                toUserIdList.add(mgr.getAccountId());
                            }
                        } else { //签发给本组织的获取此任务的任务负责人和任务负责人
                            ProjectMemberEntity designer = projectMemberService.getDesignManager(projectId, cid);
                            if ((designer != null) && !StringUtil.isEmpty(designer.getAccountId()) && !(StringUtil.isSame(designer.getAccountId(), userId)) && !(toUserIdList.contains(designer.getAccountId()))) {
                                toUserIdList.add(designer.getAccountId());
                            }
                            ProjectMemberDTO leader = projectMemberService.getTaskDesignerDTO(id);
                            if ((leader != null) && !StringUtil.isEmpty(leader.getAccountId()) && !(StringUtil.isSame(leader.getAccountId(), userId)) && !(toUserIdList.contains(leader.getAccountId()))) {
                                toUserIdList.add(leader.getAccountId());
                            }
                        }
                    }
                }
            } else if (type == SystemParameters.TASK_TYPE_PRODUCT) {
                common.setMessageType(SystemParameters.MESSAGE_TYPE_PRODUCT_TASK_CHANGE);
                common.setMessageContent(taskName + SEPARATOR + userName + SEPARATOR + taskName + SEPARATOR + op + SEPARATOR + tp);

                if (toUserIdList == null) {
                    toUserIdList = new ArrayList<>();
                    //任务参与人
                    List<ProjectMemberEntity> memberList = projectMemberService.listProjectMember(projectId, companyId, null, id);
                    for (ProjectMemberEntity member : memberList) {
                        if ((member.getAccountId() != null) && !StringUtil.isEmpty(member.getAccountId()) && !(StringUtil.isSame(member.getAccountId(), userId)) && !(toUserIdList.contains(member.getAccountId()))) {
                            toUserIdList.add(member.getAccountId());
                        }
                    }
                    //子任务负责人
                    List<ProjectTaskEntity> taskList = projectTaskDao.listUnCompletedTask(id);
                    for (ProjectTaskEntity task : taskList) {
                        ProjectMemberDTO leader = projectMemberService.getTaskDesignerDTO(task.getId());
                        if ((leader != null) && !StringUtil.isEmpty(leader.getAccountId()) && !(StringUtil.isSame(leader.getAccountId(), userId)) && !(toUserIdList.contains(leader.getAccountId()))) {
                            toUserIdList.add(leader.getAccountId());
                        }
                    }
                    //设计负责人
                    ProjectMemberEntity designer = projectMemberService.getDesignManager(projectId, companyId);
                    if ((designer != null) && !StringUtil.isEmpty(designer.getAccountId()) && !(StringUtil.isSame(designer.getAccountId(), userId)) && !(toUserIdList.contains(designer.getAccountId()))) {
                        toUserIdList.add(designer.getAccountId());
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

        //---------------生产发送信息列表，补充各消息特有字段
        return createMessageList(common, toUserIdList, toCompanyId);
    }

    private List<MessageEntity> createMessageList(MessageEntity common, List<String> toUserIdList, String toCompanyId) {
        if ((common == null) || (CollectionUtils.isEmpty(toUserIdList))) return null;
        List<MessageEntity> list = new ArrayList<>();
        for (String uid : toUserIdList) {
            MessageEntity msg = new MessageEntity();
            BeanUtilsEx.copyProperties(common, msg);
            msg.setCompanyId(toCompanyId);
            msg.setUserId(uid);
            list.add(msg);
        }
        return list;
    }

    private String getProjectName(String projectId) {
        ProjectEntity prj = projectDao.selectById(projectId);
        return (prj != null) ? prj.getProjectName() : "";
    }

    private String getTaskNameTree(String targetId) {
        return projectTaskDao.getTaskParentName(targetId);
    }

    private String getFeeDescription(ProjectCostPointDetailEntity entity) {
        ProjectCostPointEntity cp = projectCostPointDao.selectById(entity.getPointId());
        return (cp != null) ? cp.getFeeDescription() : "";
    }

    private String getNodeName(String param1) {
        ProjectProcessNodeEntity pp = projectProcessNodeDao.selectById(param1);
        return (pp != null) ? pp.getNodeName() : "";
    }

    private String getUserName(String companyId, String userId) {
        return companyUserDao.getUserNameByCompanyIdAndUserId(companyId, userId);
    }

    private String getCostPointName(String param1) {
        ProjectCostPointEntity pp = projectCostPointDao.selectById(param1);
        return (pp != null) ? pp.getFeeDescription() : "";
    }

    private String getFee(ProjectCostPointDetailEntity entity) {
        return StringUtil.getRealData(entity.getFee());
    }

    private String getPaymentFee(ProjectCostPaymentDetailEntity entity) {
        return StringUtil.getRealData(entity.getFee());
    }

    private String getPaymentDate(ProjectCostPaymentDetailEntity entity, int messageType) {
        if (messageType == SystemParameters.MESSAGE_TYPE_27 || messageType == SystemParameters.MESSAGE_TYPE_29
                || messageType == SystemParameters.MESSAGE_TYPE_33) {
            return entity.getPayDate();
        }
        if (messageType == SystemParameters.MESSAGE_TYPE_18 || messageType == SystemParameters.MESSAGE_TYPE_28
                || messageType == SystemParameters.MESSAGE_TYPE_30 || messageType == SystemParameters.MESSAGE_TYPE_34) {
            return entity.getPaidDate();
        }
        return "";
    }

    private String getCompanyName(String companyId) {
        return companyDao.getCompanyName(companyId);
    }

    private String getExpName(String targetId) {
        return getExpName(expMainDao.getExpMainDetail(targetId));
    }

    private String getExpName(ExpMainDTO entity) {
        return entity.getExpName();
    }

    private String getExpAmount(ExpMainDTO entity) {
        return StringUtil.getRealData(entity.getExpSumAmount());
    }

    private String getExpUserName(String targetId) {
        ExpMainEntity em = expMainDao.selectById(targetId);
        if (em == null) return "";
        CompanyUserEntity cue = companyUserDao.selectById(em.getCompanyUserId());
        return (cue != null) ? cue.getUserName() : "";
    }

    private String getOperatorName(String projectId,String companyId){
        ProjectMemberDTO dto = null;
        try{
            dto  = this.projectMemberService.getOperatorManagerDTO(projectId,companyId);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(dto!=null){
            return dto.getCompanyUserName();
        }
        return "";
    }

    private String getDesignerName(String projectId,String companyId) {
        ProjectMemberDTO dto = null;
        try{
            dto  = this.projectMemberService.getDesignManagerDTO(projectId,companyId);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(dto!=null){
            return dto.getCompanyUserName();
        }
        return "";
    }

    public String getLeaveTypeName(String leaveType) {
        if(StringUtil.isNullOrEmpty(leaveType)){
            return "其他";
        }
        switch (leaveType){
            case "1":
                return "年假";
            case "2":
                return "事假";
            case "3":
                return "病假";
            case "4":
                return "调休假";
            case "5":
                return "婚假";
            case "6":
                return "产假";
            case "7":
                return "陪产假";
            case "8":
                return "丧假";
            case "9":
                return "其他";
            case "10":
                return "出差";
            default:
                return "其他";
        }
    }
    /**
     * 方法描述：获取消息
     * 作者：MaoSF
     * 日期：2017/3/17
     */
    @Override
    public ResponseBean getMessage(Map<String, Object> paraMap) throws Exception {
        boolean isPaging = false;
        int page = 0 ;
        int pageSize = 0 ;
        if (null != paraMap.get("pageIndex")) {
            page = (Integer) paraMap.get("pageIndex");
            pageSize = (Integer) paraMap.get("pageSize");
            paraMap.put("startPage", page * pageSize);
            paraMap.put("endPage", pageSize);
            isPaging = true;
        }
        if(!paraMap.containsKey("status")){
            paraMap.put("status","0");
        }
        int messageCount = this.messageDao.getMessageCount(paraMap);
        List<MessageDTO> resultList = getMessageList(isPaging,page,pageSize,messageCount,paraMap);
        Map<String,String> logos = new HashMap<>();
        //设置组织logo
        for(MessageDTO dto:resultList){
            if(resultList.contains(dto.getCompanyId())){
                dto.setFileFullPath(logos.get(dto.getCompanyId()));
            }else {
                dto.setFileFullPath(projectSkyDriverService.getCompanyLogo(dto.getCompanyId()));
                logos.put(dto.getCompanyId(),dto.getFileFullPath());
            }
        }
        //标示为已读
        this.messageDao.updateRead((String) paraMap.get("accountId"));
        Map<String,Object> otherMap = new HashMap<>();
        otherMap.put("pageIndex",paraMap.get("pageIndex"));
        otherMap.put("isLastPage",paraMap.get("isLastPage"));
        return ResponseBean.responseSuccess("查询成功").addData("list", resultList).addData(paraMap);
    }

    @Override
    public ResponseBean getLastMessage(Map<String, Object> map) throws Exception {
        List<LastMessageDTO> lastMessageList = new ArrayList<>();
        map.put("lastMessage","1");//获取最新一条
        List<MessageDTO> list = this.messageDao.getMessage(map);
        map.put("param2","0");
        int notReadCount = this.messageDao.getMessageCount(map);
        for(MessageDTO dto:list){
            LastMessageDTO msg = new LastMessageDTO();
            msg.setMessageTitle("卯丁秘书");
            if("2".equals(dto.getStatus())){
                msg.setMessageContent("");
            }else {
                msg.setMessageContent(getMessageContent(dto.getMessageContent(),dto.getMessageType(),dto.getId()));
            }
            msg.setMessageType(MsgType.MSG_TYPE_MAO_DING);//卯丁秘书类型
            msg.setNotReadCount(notReadCount);
            msg.setCreateDate(dto.getCreateDate());
            lastMessageList.add(msg);
        }

        //获取通知公告的未读数
        map.put("userId",map.get("accountId"));
        List<NoticeDTO> noticeList = this.noticeDao.getNoticeByParam(map);
        int notReadNoticeCount = this.noticeDao.getNotReadNoticeCount(map);
        if(!CollectionUtils.isEmpty(noticeList)){
            LastMessageDTO msg = new LastMessageDTO();
            msg.setMessageTitle("通知公告");
            msg.setMessageContent(noticeList.get(0).getNoticeTitle());
            msg.setMessageType(MsgType.MSG_TYPE_NOTICE);//通知公告类型
            msg.setNotReadCount(notReadNoticeCount);
            msg.setCreateDate(noticeList.get(0).getCreateDate());
            lastMessageList.add(msg);
        }

        //查询未读项目讨论的数目
        Map<String,LastMessageDTO> circleData = new HashMap<>();
        QueryCircleDTO query = new QueryCircleDTO();
        query.setAccountId((String) map.get("accountId"));
        List<MaodingCircleReadDTO> circleList = maodingCircleReadDao.getNotReadCircle(query);
        List<String> notNeedSearchProject = new ArrayList<>();
        for(MaodingCircleReadDTO read:circleList){
            if(read.getDeleted()!=null && read.getDeleted()==1){
                //由于查询是按照时间倒序排列的。如果前面的数据的的deleted = 1，
                // 则表示前端已经删除不想展示，如果最前面deleted = 0，后面的数据存在deleted = 1，仍然展示
                notNeedSearchProject.add(read.getProjectId());
                continue;
            }
            if(notNeedSearchProject.contains(read.getProjectId())){
                continue;
            }
            query.setProjectId(read.getProjectId());
            if(StringUtil.isNullOrEmpty(maodingCircleDao.getPartInCompany(query))){
                notNeedSearchProject.add(read.getProjectId());
                continue;
            }
            LastMessageDTO msg = null;
            if(!circleData.containsKey(read.getProjectId())){
                msg = new LastMessageDTO();
                circleData.put(read.getProjectId(),msg);
                msg.setProjectId(read.getProjectId());
                msg.setMessageTitle("讨论区·"+read.getProjectName());
                msg.setProjectName(read.getProjectName());
                msg.setCreateDate(read.getCreateDate());
                msg.setMessageType(MsgType.MSG_TYPE_CIRCLE);
                msg.setLastCircleReadId(read.getId());
                lastMessageList.add(msg);
                MaodingCircleCommentDataDTO lastComment = maodingCircleService.getComment(read,query.getAccountId());
                if(lastComment!=null){
                    //1：特别提醒（@），2：评论了你，4:评论了,3：谁谁谁评论谁
                    if(lastComment.getReplyType()==1){
                        msg.setMessageContent(lastComment.getCommentUser().getUserName()+"@了你："+lastComment.getComment());
                    }
                    if(lastComment.getReplyType()==2){
                        msg.setMessageContent(lastComment.getCommentUser().getUserName()+"评论了你："+lastComment.getComment());
                    }
                    if(lastComment.getReplyType()==3){
                        msg.setMessageContent(lastComment.getCommentUser().getUserName()+"评论了"+lastComment.getReplyUser().getUserName()+"："+lastComment.getComment());
                    }
                    if(lastComment.getReplyType()==4){
                        msg.setMessageContent(lastComment.getCommentUser().getUserName()+"评论了："+lastComment.getComment());
                    }
                }
            }else {
                msg = circleData.get(read.getProjectId());
            }
            if(read.getIsRead()==0){
                msg.addCount();
            }
        }
        return ResponseBean.responseSuccess("查询成功")
                .addData("list", lastMessageList);
    }

    private List<MessageDTO> getMessageList(boolean isPaging,int page,int pageSize,int messageCount,Map<String, Object> paraMap) throws Exception{
        int currentCount = 0 ;
        if(isPaging){
            paraMap.put("pageIndex",page+"");
            paraMap.put("startPage", page * pageSize);
            paraMap.put("endPage", pageSize);
            currentCount = (page+1) * pageSize;
            paraMap.put("isLastPage",currentCount<messageCount?0:1);
        }
        List<MessageDTO> list = this.messageDao.getMessage(paraMap);
        List<MessageDTO> resultList = conversionToTemplate(list);
        if(CollectionUtils.isEmpty(resultList) && currentCount<messageCount && isPaging){
            return getMessageList(isPaging,page++,pageSize,messageCount,paraMap);
        }
        return resultList;
    }

    private MessageDTO handleMessageStateForMyTask(MessageDTO dto) throws Exception{
        MessageEntity m = new MessageEntity();
        BaseDTO.copyFields(dto,m);
        MyTaskEntity task = handleMessageForMyTask(m);
        if(!"1".equals(m.getStatus())){
            if(task!=null){
                dto.setIsComplete(task.getStatus());
                dto.setTargetId(task.getId());
            }
            return dto;
        }
        return null;
    }

    private MyTaskEntity handleMessageForMyTask(MessageEntity m) throws Exception {
        CompanyUserEntity handle = companyUserDao.getCompanyUserByUserIdAndCompanyId(m.getUserId(),m.getCompanyId());
        if(handle==null){
            //标识当前消息对应的任务已经无效
            m.setStatus("1");
            m.setMessageContent(null);//防止m.MessageContent已经通过模板匹配好整句话导致数据错误
            messageDao.updateById(m);
        }else {
            Map<String,Object> map = new HashMap<>();
            map.put("handlerId",handle.getId());
            map.put("targetId",m.getTargetId());
            map.put("handlerIsNull","1");
            map.put("permissionCodes",permissionService.getPermissionCodeByUserId(m.getCompanyId(),m.getUserId()));

            List<MyTaskEntity> myTaskList = null;
            switch (m.getMessageType()) {
                case SystemParameters.MESSAGE_TYPE_403: //"hi，设计负责人?进行了“?-?”的生产安排，你被设定为任务负责人，详情点击<a href="?">［我的任务］</a>查看，谢谢！"
                case SystemParameters.MESSAGE_TYPE_404: //"hi,任务负责人?进行了“?-?”的生产安排，你被设定为任务负责人，详情点击<a href=\"?\">［我的任务］</a>查看，谢谢！"
                case 7: //"你成为了“? - ?”的任务负责人"
                    map.put("taskType",SystemParameters.PRODUCT_TASK_RESPONSE);
                    break;
                case SystemParameters.MESSAGE_TYPE_408: //"hi，“?-?”所有生产任务已完成，请你确认，谢谢！"
                    map.put("taskType",SystemParameters.TASK_COMPLETE);
                    break;
                case SystemParameters.MESSAGE_TYPE_501: //"hi，设计负责人?进行了“?-?”的生产安排，你将参与该任务的?工作，详情请点击<a href=\"?\">［我的任务］</a>查看，谢谢！"
                case SystemParameters.MESSAGE_TYPE_502: //"hi，任务负责人?进行了“?-?”的生产安排，你将参与该任务的?工作，详情请点击<a href=\"?\">［我的任务］</a>查看，谢谢！"
                case SystemParameters.MESSAGE_TYPE_503:
                    /**设校审的任务*/
                    map.put("taskType",SystemParameters.PROCESS_DESIGN);
                    map.put("targetId",m.getParam1());
                    break;
                case 11: //"hi，?发起了“?：?”的技术审查费?万元，请确认付款金额，谢谢！"
                    /**技术审核确认付款任务*/
                    map.put("taskType",SystemParameters.TECHNICAL_REVIEW_FEE_OPERATOR_MANAGER);
                    map.put("targetId",m.getTargetId());
                    break;
                case 14: //"hi，?发起了“?：?”的合作设计费?万元，请确认付款金额，谢谢！"
                    /**合作设计费确认付款任务*/
                    map.put("taskType",SystemParameters.COOPERATIVE_DESIGN_FEE_ORG_MANAGER);
                    break;
                case 17: //"hi，?发起了“?：?”的合同回款?万元，请你跟进并确认实际到帐金额和日期，谢谢！"
                    /**合同回款确认到账任务*/
                    map.put("taskType",SystemParameters.CONTRACT_FEE_PAYMENT_CONFIRM);
                    break;
                case 23: //"hi，?确认了“?：?”的技术审查费付款金额为?万元，请你跟进并确认实际付款日期，谢谢！"
                    /**技术审核费财务确认付款日期界面 任务*/
                    map.put("taskType",SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY);
                    break;
                case 24: //"hi，?确认了“?：?”的技术审查费付款金额为?万元，请你跟进并确认实际到账日期，谢谢！"
                    /**技术审核费财务到账界面 任务*/
                    map.put("taskType",SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID);
                    break;
                case 25: //"hi，?确认了“?：?”的合作设计费付款金额为?万元，请你跟进并确认实际付款日期，谢谢！"
                    /**合作设计费确认付款日期界面 任务*/
                    map.put("taskType",SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY);
                    break;
                case 26: //"hi，?确认了“?：?”的合作设计费付款金额为?万元，请你跟进并确认实际到账日期，谢谢！"
                    /**合作设计费确认到款日期界面 任务*/
                    map.put("taskType",SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID);
                    break;
                case 31: //"hi，?发起了“?：?”的其他支出?万元，请你跟进并确认实际付款金额和日期，谢谢！"
                    /**其他支出付款界面 任务*/
                    map.put("taskType",SystemParameters.OTHER_FEE_FOR_PAY);
                    break;
                case 32: //"hi，?发起了“?：?”的其他收入?万元，请你跟进并确认实际到帐金额和日期，谢谢！"
                    /**其他收入到账界面 任务*/
                    map.put("taskType",SystemParameters.OTHER_FEE_FOR_PAID);
                    break;
                case 901:
                    /**轻量 任务*/
                    map.put("taskType",SystemParameters.CUSTOM_TASK);
                    map.put("id",m.getTargetId());
                    map.remove("targetId");
                    break;
                case SystemParameters.MESSAGE_TYPE_236:
                case SystemParameters.MESSAGE_TYPE_237:
                case SystemParameters.MESSAGE_TYPE_238:
                case SystemParameters.MESSAGE_TYPE_239:
                    //查询copy记录里面是否存在
                    MyTaskEntity copy = new MyTaskEntity();
                    copy.setId(m.getTargetId());
                    if(copyRecordService.isExitCopyRecord(new QueryCopyRecordDTO(m.getTargetId(),handle.getId()))){
                        copy.setStatus("0");
                    }else {
                        copy.setStatus("1");
                        updateMessageStatus(m);
                    }
                    return copy;
                case 19:
                case 221:
                case 22:
                case 222:
                case 225:
                case 226:
                case 229:
                case 230:
                case 233:
                    MyTaskEntity audit = new MyTaskEntity();
                    audit.setId(m.getTargetId());
                    if(StringUtil.isNullOrEmpty(m.getParam1())){
                        audit.setStatus("1");
                    }else {
                        ExpAuditEntity auditEntity = expAuditDao.selectById(m.getParam1());
                        if(auditEntity==null){
                            audit.setStatus("1");
                            updateMessageStatus(m);
                        }else {
                            audit.setStatus("0".equals(auditEntity.getApproveStatus())?"0":"1");
                        }
                    }
                    return audit;
                default:
                    return null;
            }
            myTaskList = myTaskDao.getMyTaskByParam(map);
            if(!CollectionUtils.isEmpty(myTaskList)){
                return myTaskList.get(0);
            }else {
                if(map.containsKey("taskType")){
                    updateMessageStatus(m);
                }
            }
            return  null;
        }
        return null;
    }

    void updateMessageStatus(MessageEntity m){
        //标识当前消息对应的任务已经无效
        m.setMessageContent(null);//防止m.MessageContent已经通过模板匹配好整句话导致数据错误
        m.setStatus("1");
        messageDao.updateById(m);
    }

    /**
     * 模板转换
     */
    private List<MessageDTO> conversionToTemplate(List<MessageDTO> list) throws Exception {
        List<MessageDTO> resultList = new ArrayList<>();
        for (MessageDTO dto : list) {
            dto.setMessageTitle("");
            dto.setMessageContent(getMessageContent(dto.getMessageContent(),dto.getMessageType(),dto.getId(),dto));
            dto.setIsNotice(SystemParameters.message2.get(Integer.toString(dto.getMessageType())).getType());
            MessageDTO messageDTO =  handleMessageStateForMyTask(dto);
            if(messageDTO!=null){
                resultList.add(messageDTO);
            }
        }
        return resultList;
    }
    private String getMessageContent(String messageContent,Integer messageType,String id,MessageDTO messageDTO) throws Exception{

        MessageJsonDTO dto = new MessageJsonDTO();
        try{
            dto = JsonUtils.json2pojo(messageContent, MessageJsonDTO.class);
        }catch (Exception e){
            System.out.println("id==="+id);
        }

        if(messageDTO!=null){
            messageDTO.setProjectName(dto.getProjectName());
        }
        //生成content
        String msg = SystemParameters.message2.get(Integer.toString(messageType)).getContent()
                .replaceAll("%scheduleContent%",getValue(dto.getScheduleContent()))
                .replaceAll("%reminderTime%",getValue(dto.getReminderTime()))

                .replaceAll("%projectName%",getValue(dto.getProjectName()))
                .replaceAll("%taskName%", getValue(dto.getTaskName()))

                .replaceAll("%startTime1%", getValue(dto.getStartTime1()))
                .replaceAll("%endTime1%", getValue(dto.getEndTime1()))
                .replaceAll("%startTime2%", getValue(dto.getStartTime2()))
                .replaceAll("%endTime2%", getValue(dto.getEndTime2()))

                .replaceAll("%expAmount%", getValue(dto.getExpAmount()))
                .replaceAll("%expName%", getValue(dto.getExpName()))
                .replaceAll("%expUserName%", getValue(dto.getExpUserName()))

                .replaceAll("%fee%", getValue(dto.getFee()))
                .replaceAll("%feeDescription%", getValue(dto.getFeeDescription()))
                .replaceAll("%paymentDate%", getValue(dto.getPaymentDate()))

                .replaceAll("%nodeName%", getValue(dto.getNodeName()))
                .replaceAll("%toNodeName%", getValue(dto.getToNodeName()))

                .replaceAll("%sendUserName%", getValue(dto.getSendUserName()))
                .replaceAll("%sendCompanyName%", getValue(dto.getSendCompanyName()))

                .replaceAll("%projectManagerName%", getValue(dto.getProjectManagerName()))
                .replaceAll("%designerName%", getValue(dto.getDesignerName()))
                .replaceAll("%url%",(this.serverUrl+getValue(dto.getUrl())))

                .replaceAll("%address%",getValue(dto.getAddress()))
                .replaceAll("%reason%",getValue(dto.getReason()))
                .replaceAll("%leaveTypeName%",getValue(dto.getLeaveTypeName()))
                .replaceAll("%responseName%", getValue(dto.getResponseName()))
                .replaceAll("%deliverName%", getValue(dto.getDeliverName()))
                ;

        return msg;
    }
    private String getMessageContent(String messageContent,Integer messageType,String id) throws Exception{
        return getMessageContent(messageContent,messageType,id,null);
    }

    private String getValue(String m){
        if(StringUtil.isNullOrEmpty(m) || "null".equals(m)){
            return "";
        }
        return m;
    }

    /**
     * 方法描述：发送消息
     * 作者：MaoSF
     * 日期：2016/12/8
     */
    public void sendMessageToClient(MessageEntity entity) {
        try {
                String msg = getMessageContent(entity.getMessageContent(),entity.getMessageType(),entity.getId());
                String receiver = entity.getUserId();
                SendMessageDataDTO dto = new SendMessageDataDTO();
                dto.setContent(msg);
                dto.setReceiver(receiver);
                dto.setMessageType(SystemParameters.USER_MESSAGE);
                noticeService.notify(NotifyDestination.WEB, dto);//推送给web

                Map<String, Object> msgMap = new HashMap<>();
                msgMap.put("type", "txt");
                msgMap.put("msg", msg);
                ImSendMessageDTO imSendMessageDTO = new ImSendMessageDTO();
                imSendMessageDTO.setFromUser(SystemParameters.MAODING_ID);//卯丁助手
                imSendMessageDTO.setToUsers(new String[]{receiver});
                imSendMessageDTO.setTargetType("users");//接收主体（users:个人，chatgroups：群组），如果为空，则默认为个人
                imSendMessageDTO.setExt(null);
                imSendMessageDTO.setMsgMap(msgMap);

                SendMessageDataDTO notifyMsg = new SendMessageDataDTO();
                notifyMsg.setMessageType(SystemParameters.APP_MESSAGE);
                notifyMsg.setReceiverList(Arrays.asList(receiver));
                notifyMsg.setContent(msg);
                noticeService.notify(NotifyDestination.APP, notifyMsg);
           // imQueueProducer.sendMessage(imSendMessageDTO);//推送给APP
        } catch (Exception e) {
            log.error("MessageServiceImpl失败 推送消息失败 app端", e);
        }
    }

    /**
     * 方法描述：根据关键字删除
     * 作者：MaoSF
     * 日期：2017/3/25
     */
    @Override
    public int deleteMessage(String field) throws Exception {
        return this.messageDao.deleteMessage(field);
    }

    @Override
    public List<MessageEntity> getMessageByParam(QueryMessageDTO dto) {
        return this.messageDao.selectByParam(dto);
    }

    @Override
    public ResponseBean readMessage(QueryMessageDTO dto) {
        MessageEntity message = new MessageEntity();
        message.setId(dto.getId());
        message.setParam2("1");
        this.messageDao.updateById(message);
        return ResponseBean.responseSuccess();
    }

    @Override
    public ResponseBean clearMessage(QueryMessageDTO dto) {
        this.messageDao.updateMessageStatus(dto.getAccountId());
        return ResponseBean.responseSuccess();
    }
}
