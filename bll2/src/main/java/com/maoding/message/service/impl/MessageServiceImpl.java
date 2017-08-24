package com.maoding.message.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.BeanUtilsEx;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.dao.ZInfoDAO;
import com.maoding.financial.dao.ExpMainDao;
import com.maoding.financial.dto.ExpMainDTO;
import com.maoding.financial.entity.ExpMainEntity;
import com.maoding.hxIm.dto.ImSendMessageDTO;
import com.maoding.hxIm.service.ImQueueProducer;
import com.maoding.message.dao.MessageDao;
import com.maoding.message.dto.MessageDTO;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.notice.constDefine.NotifyDestination;
import com.maoding.notice.service.NoticeService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dao.ProjectProcessNodeDao;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.entity.ProjectProcessNodeEntity;
import com.maoding.projectcost.dao.ProjectCostPaymentDetailDao;
import com.maoding.projectcost.dao.ProjectCostPointDao;
import com.maoding.projectcost.dao.ProjectCostPointDetailDao;
import com.maoding.projectcost.entity.ProjectCostPaymentDetailEntity;
import com.maoding.projectcost.entity.ProjectCostPointDetailEntity;
import com.maoding.projectcost.entity.ProjectCostPointEntity;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dto.TaskWithFullNameDTO;
import com.maoding.task.entity.ProjectTaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ImQueueProducer imQueueProducer;

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


    @Override
    public ResponseBean sendMessage(MessageEntity messageEntity) throws Exception {
        if (messageEntity == null) {
            return ResponseBean.responseSuccess();//如果为null.则直接返回。防止编辑的时候，有些数据未传递（无需发送消息的情况）
        }
        if (messageEntity.getMessageType() == 0) {
            return ResponseBean.responseSuccess();//如果为0，则直接返回，不推送消息
        }
        String projectId = messageEntity.getProjectId();
        String targetId = messageEntity.getTargetId();
        switch (messageEntity.getMessageType()) {
            case 5:
            case 6:
            case 7:
            case 9:
            case 21:
            case 35:
                messageEntity.setMessageContent(getProjectName(projectId) + SEPARATOR
                        + getTaskNameTree(targetId));
                break;
            case 8:
                messageEntity.setMessageContent(getProjectName(projectId) + SEPARATOR
                        + getTaskNameTree(targetId) + SEPARATOR
                        + messageEntity.getMessageContent());
                break;
            case 10:
                messageEntity.setMessageContent(getProjectName(projectId) + SEPARATOR
                        + getTaskNameTree(targetId) + SEPARATOR
                        + getNodeName(messageEntity.getParam1()));
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 31:
            case 32:
                ProjectCostPointDetailEntity detail = projectCostPointDetailDao.selectById(targetId);
                messageEntity.setMessageContent(getProjectName(projectId) + SEPARATOR
                        + getFeeDescription(detail) + SEPARATOR
                        + getFee(detail));
                break;
            case 19:
                ExpMainDTO dto = expMainDao.getExpMainDetail(targetId);
                messageEntity.setMessageContent(getUserName(targetId) + SEPARATOR
                        + getExpName(dto) + SEPARATOR
                        + getExpAmount(dto));
                break;
            case 20:
            case 22:
                ExpMainDTO dto2 = expMainDao.getExpMainDetail(targetId);
                messageEntity.setMessageContent(getExpName(targetId) + SEPARATOR
                        + getExpAmount(dto2));
                break;
            case 23:
            case 24:
            case 25:
            case 26:
                ProjectCostPaymentDetailEntity payment = projectCostPaymentDetailDao.selectById(targetId);
                messageEntity.setMessageContent(getProjectName(projectId) + SEPARATOR
                        + getCostPointName(messageEntity.getParam1()) + SEPARATOR
                        + getPaymentFee(payment));
                break;
            case 18:
            case 27:
            case 28:
            case 29:
            case 30:
            case 33:
            case 34:
                ProjectCostPaymentDetailEntity payment2 = projectCostPaymentDetailDao.selectById(targetId);
                messageEntity.setMessageContent(getProjectName(projectId) + SEPARATOR
                        + getCostPointName(messageEntity.getParam1()) + SEPARATOR
                        + getPaymentFee(payment2) + SEPARATOR
                        + getPaymentDate(payment2, messageEntity.getMessageType()));
                break;

            default:
                break;
        }

        messageEntity.setId(StringUtil.buildUUID());
        messageDao.insert(messageEntity);
        sendUserMessageToClient(messageEntity);
        return null;
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
                common.setMessageContent(taskName + SEPARATOR + userName + SEPARATOR + taskName + SEPARATOR + op + SEPARATOR + tp);

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

    private String getUserName(String targetId) {
        ExpMainEntity em = expMainDao.selectById(targetId);
        if (em == null) return "";
        CompanyUserEntity cue = companyUserDao.selectById(em.getUserId());
        return (cue != null) ? cue.getUserName() : "";
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


    /**
     * 方法描述：获取消息
     * 作者：MaoSF
     * 日期：2017/3/17
     * @param:
     * @return:
     */
    @Override
    public ResponseBean getMessage(Map<String, Object> paraMap) throws Exception {
        if (null != paraMap.get("pageIndex")) {
            int page = (Integer) paraMap.get("pageIndex");
            int pageSize = (Integer) paraMap.get("pageSize");
            paraMap.put("startPage", page * pageSize);
            paraMap.put("endPage", pageSize);
        }
        List<MessageDTO> list = this.messageDao.getMessage(paraMap);
        conversionToTemplate(list, (String) paraMap.get("appOrgId"));

        //标示为已读
        this.messageDao.updateRead((String) paraMap.get("accountId"));

        return ResponseBean.responseSuccess("查询成功").addData("list", list);
    }


    private void handleMessageStateForMyTask(MessageDTO dto) throws Exception {
        CompanyUserEntity companyUser = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getUserId(), dto.getCompanyId());
        if (companyUser != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", dto.getProjectId());
            map.put("companyId", dto.getCompanyId());
            map.put("handlerId", companyUser.getId());
            map.put("targetId", dto.getTargetId());
            if (dto.getMessageType() == 11) {
                map.put("taskType", "5");
            }
            if (dto.getMessageType() == 14) {
                map.put("taskType", "7");
            }
            if (dto.getMessageType() == 17) {
                map.put("taskType", "10");
            }
            List<MyTaskEntity> list = this.myTaskDao.getMyTaskByParam(map);
            if (!CollectionUtils.isEmpty(list)) {
                dto.setIsComplete(list.get(0).getStatus());
            }
        }

    }

    /**
     * 模板转换
     */
    private void conversionToTemplate(List<MessageDTO> list, String currentCompanyId) throws Exception {
        for (MessageDTO dto : list) {
            String[] params = dto.getMessageContent().split(SEPARATOR);

            if (!StringUtil.isNullOrEmpty(dto.getProjectId()) && params.length > 0) {
                dto.setProjectName(params[0]);
            }
            dto.setMessageTitle(getMessageTitle(dto.getMessageType()) + dealWithTitle(dto.getMessageType() + "", dto.getMessageContent()));
            dto.setMessageContent(StringUtil.format(SystemParameters.message.get(dto.getMessageType() + ""), params));
            if (dto.getMessageType() == 11 || dto.getMessageType() == 14 || dto.getMessageType() == 17) {
                handleMessageStateForMyTask(dto);
            }
        }
    }

    public String dealWithTitle(String type, String messageContent) {
        String result = "";
        int intType = Integer.parseInt(type);
        String[] strArr = messageContent.split(";");
        if (intType < 5) {
            result = messageContent;
        } else if (intType < 11 || intType == 21) {
            result = messageContent.replaceAll(";", "-");
        } else if (intType < 19 || intType > 22 && intType < 36) {

            String newStr = "";
            for (int i = 0; i < strArr.length - 1; i++) {
                if (i == 0) {
                    result = newStr + strArr[i];
                } else if (i == 1) {
                    result = result + "-" + strArr[i];
                }
            }
        } else if (intType < 20) {
            result = strArr[0] + "申请的报销";
        } else {
            if (intType == 20) {
                result = "报销不予以批准";
            }
            if (intType == 22) {
                result = "报销已经审批通过";
            }
        }
        return result;
    }


    private String getMessageTitle(int type) {
        String titleType = "";
        switch (type) {
            case 1:
            case 2:
                titleType = "【乙方】";
                break;
            case 3:
            case 5:
                titleType = "【经营负责人】";
                break;
            case 4:
            case 6:
            case 35:
                titleType = "【设计负责人】";
                break;
            case 7:
                titleType = "【任务负责人】";
                break;
            case 8:
            case 9:
            case 21:
            case 10:
                titleType = "【设计】";
                break;
            case 11:
            case 12:
            case 13:
            case 23:
            case 24:
            case 27:
            case 28:
                titleType = "【技术审查费】";
                break;

            case 17:
            case 18:
                titleType = "【合同回款】";
                break;
            case 19:
            case 20:
            case 22:
                titleType = "【费用报销】";
                break;

            case 14:
            case 15:
            case 16:
            case 25:
            case 26:
            case 29:
            case 30:
                titleType = "【合作设计费】";
                break;
            case 31:
            case 32:
            case 33:
            case 34:
                titleType = "【其他费用】";
                break;
            case 36:
            case 37:
                titleType = "【任务签发】 时间变更";
                break;
            case 38:
            case 39:
                titleType = "【生产安排】 时间变更";
                break;
            default:
                titleType = "";
                break;
        }
        return titleType;
    }

    /**
     * 方法描述：发送消息
     * 作者：MaoSF
     * 日期：2016/12/8
     * @param:
     * @return:
     */
    public void sendUserMessageToClient(MessageEntity entity) {
        try {
            String[] params = entity.getMessageContent().split(SEPARATOR);
            String msg = StringUtil.format(SystemParameters.message.get(entity.getMessageType() + ""), params);
            String receiver = entity.getUserId();


            Map<String, Object> map = new HashMap<>();
            map.put("msg", msg);
            map.put("receiver", receiver);
            map.put("messageType", SystemParameters.USER_MESSAGE);
            noticeService.notify(NotifyDestination.WEB, map);//推送给web

            Map<String, Object> msgMap = new HashMap<>();
            msgMap.put("type", "txt");
            msgMap.put("msg", msg);
            ImSendMessageDTO imSendMessageDTO = new ImSendMessageDTO();
            imSendMessageDTO.setFromUser(SystemParameters.MAODING_ID);//卯丁助手
            imSendMessageDTO.setToUsers(new String[]{receiver});
            imSendMessageDTO.setTargetType("users");//接收主体（users:个人，chatgroups：群组），如果为空，则默认为个人
            imSendMessageDTO.setExt(null);
            imSendMessageDTO.setMsgMap(msgMap);
            imQueueProducer.sendMessage(imSendMessageDTO);//推送给APP
        } catch (Exception e) {
            log.error("MessageServiceImpl失败 推送消息失败 app端", e);
        }

    }

    /**
     * 方法描述：根据关键字删除
     * 作者：MaoSF
     * 日期：2017/3/25
     * @param:
     * @return:
     */
    @Override
    public int deleteMessage(String field) throws Exception {
        return this.messageDao.deleteMessage(field);
    }
}
