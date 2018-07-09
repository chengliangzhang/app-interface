package com.maoding.project.service.impl;

import com.maoding.conllaboration.SyncCmd;
import com.maoding.conllaboration.service.CollaborationService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.ProjectMemberType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.dao.ZInfoDAO;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.message.dto.SendMessageDTO;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dao.ProjectProcessNodeDao;
import com.maoding.project.dto.ProjectDesignUserList;
import com.maoding.project.dto.ProjectProcessDTO;
import com.maoding.project.dto.ProjectProcessNodeDTO;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.entity.ProjectProcessEntity;
import com.maoding.project.entity.ProjectProcessNodeEntity;
import com.maoding.project.service.ProjectProcessService;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dto.SaveProjectTaskDTO;
import com.maoding.task.dto.TaskWithFullNameDTO;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.task.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wuwq on 2016/10/27.
 */
@Service("projectProcessService")
public class ProjectProcessServiceImpl implements ProjectProcessService {

    @Autowired
    private ProjectProcessNodeDao projectProcessNodeDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private MyTaskService myTaskService;

    @Autowired
    private CollaborationService collaborationService;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private ZInfoDAO zInfoDAO;

    @Autowired
    private ProjectDao projectDao;


    /**
     * 方法描述：根据taskId删除流程
     * 作者：MaoSF
     * 日期：2016/10/31
     * @param:
     * @return:
     */
    @Override
    public ResponseBean deleteProcessByTaskId(String taskId) throws Exception {
        //后期优化此问题
        Map<String, Object> map = new HashMap();
        map.put("taskManageId", taskId);
        List<ProjectProcessNodeEntity> list = this.projectProcessNodeDao.getProcessNodeByParam(map);
        for (ProjectProcessNodeEntity dto : list) {
            deleteProcessNodeById(dto.getId());
        }
        return ResponseBean.responseSuccess("删除成功");
    }

    /**
     * 方法描述：新建或更新流程
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    public ResponseBean saveOrUpdateProcess_publish(ProjectProcessDTO dto) throws Exception {
        String taskId = dto.getTaskId();
        ProjectTaskEntity taskEntity = this.projectTaskDao.selectById(taskId);
        if (StringUtil.isNullOrEmpty(taskId) || taskEntity == null) {
            return ResponseBean.responseError("操作失败");
        }
        if (taskEntity != null && taskEntity.getTaskType() != SystemParameters.TASK_PRODUCT_TYPE_MODIFY) {//则新增一条被修改的记录
            taskId = projectTaskService.copyProjectTask(new SaveProjectTaskDTO(), taskEntity);
        }
        List<ProjectMemberEntity> list = this.projectMemberService.listProjectMember(null, null, null, taskId);
        //流程节点
        int nodeSeq = 1;
        StringBuffer ids = new StringBuffer();
        for (ProjectProcessNodeDTO nodeDTO : dto.getNodes()) {
            ProjectMemberEntity memberEntity = this.projectMemberService.getProjectMember(nodeDTO.getCompanyUserId(), nodeDTO.getSeq() + 3, taskId);
            if (null == memberEntity) {
                memberEntity = this.projectMemberService.saveProjectMember(dto.getProjectId(), dto.getCompanyId(), nodeDTO.getCompanyUserId(), taskId, nodeDTO.getSeq() + 3, nodeSeq, dto.getAccountId(),dto.getAppOrgId());
            }
            nodeSeq++;
            ids.append(memberEntity.getId() + ",");
        }
        String nodeIds = ids.toString();
        for (ProjectMemberEntity member : list) {
            if (member.getMemberType() != ProjectMemberType.PROJECT_TASK_RESPONSIBLE) {
                if (nodeIds.indexOf(member.getId()) < 0) {
                    this.projectMemberService.deleteProjectMember(member.getId());
                }
            }
        }
        this.projectTaskService.updateByTaskIdStatus(taskId, SystemParameters.TASK_STATUS_MODIFIED);
        return ResponseBean.responseSuccess("保存成功");
    }


    /**
     * 方法描述：新建或更新流程
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    @Override
    public ResponseBean saveOrUpdateProcess(ProjectProcessDTO dto) throws Exception {
        TaskWithFullNameDTO origin = zInfoDAO.getTaskByTaskId(dto.getTaskId());//保留原有数据
        String taskId = dto.getTaskId();
        ProjectTaskEntity taskEntity = this.projectTaskDao.selectById(taskId);
        if (StringUtil.isNullOrEmpty(taskId) || taskEntity == null) {
            return ResponseBean.responseError("操作失败");
        }
        List<ProjectMemberEntity> list = this.projectMemberService.listProjectMember(null, null, null, taskId);
        //流程节点
        String companyId = dto.getAppOrgId();
        StringBuffer ids = new StringBuffer();
        List<ProjectProcessNodeDTO> addNodes = new ArrayList<ProjectProcessNodeDTO>();
        for (ProjectProcessNodeDTO nodeDTO : dto.getNodes()) {
            ProjectMemberEntity memberEntity = this.projectMemberService.getProjectMember(nodeDTO.getCompanyUserId(), nodeDTO.getSeq() + 3, taskId);
            if (null == memberEntity) {
                ProjectProcessNodeEntity node = new ProjectProcessNodeEntity();
                nodeDTO.setId(StringUtil.buildUUID());
                nodeDTO.setProcessId(taskId);
                BaseDTO.copyFields(nodeDTO, node);
                node.setCreateBy(dto.getAccountId());
                projectProcessNodeDao.insert(node);
                addNodes.add(nodeDTO);
            } else {
                ids.append(memberEntity.getId() + ",");
            }

        }
        //  保存任务并推送消息
        String newIds = this.saveMyTask(addNodes, companyId, dto.getProjectId(), dto.getAccountId(), dto.getTaskId());

        String nodeIds = ids.toString() + "," + newIds;
        for (ProjectMemberEntity member : list) {
            if (member.getMemberType() != ProjectMemberType.PROJECT_TASK_RESPONSIBLE) {
                if (nodeIds.indexOf(member.getId()) < 0) {
                    deleteProcessNodeById(member.getTargetId());
                    this.projectMemberService.deleteProjectMember(member.getProjectId(), null, member.getMemberType(), member.getTargetId());
                }
            }
        }
        //添加项目动态
        TaskWithFullNameDTO target = zInfoDAO.getTaskByTaskId(dto.getTaskId()); //获取新任务;
        dynamicService.addDynamic(target, target, dto.getCompanyId(), dto.getAccountId());

        //给乙方任务负责人推送消息
//        String msg = (origin != null) ? origin.getMembers() : "";
//        msg += ";";
//        msg += (target != null) ? target.getMembers() : "";
//        this.sendMessageToPartBDesigner(dto.getProjectId(), dto.getTaskId(), msg);

        return ResponseBean.responseSuccess("操作成功");
    }

    private void sendMessageToPartBDesigner(String projectId, String taskId, String msgContent) throws Exception {

        ProjectEntity projectEntity = this.projectDao.selectById(projectId);
        if (projectEntity != null) {
            if (!StringUtil.isNullOrEmpty(projectEntity.getCompanyBid()) && !projectEntity.getCompanyBid().equals(projectEntity.getCompanyId())) {

                ProjectMemberEntity designManager = this.projectMemberService.getProjectMember(projectId, projectEntity.getCompanyBid(), ProjectMemberType.PROJECT_TASK_RESPONSIBLE, taskId);
                if (designManager != null) {
                    this.messageService.sendMessage(projectId, projectEntity.getCompanyBid(), taskId, SystemParameters.MESSAGE_TYPE_8, null, designManager.getAccountId(), null, msgContent);
                }
            }
        }
    }

    private String saveMyTask(List<ProjectProcessNodeDTO> addNodes, String companyId, String projectId, String accountId, String taskId) throws Exception {
        String ids = "";
        CompanyUserEntity companyUser = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, companyId);
        for (ProjectProcessNodeDTO node : addNodes) {
            boolean isSendMessage = true;
            if (companyUser != null && companyUser.getId().equals(node.getCompanyUserId())) {
                isSendMessage = false;
            }else {
                companyUser = this.companyUserDao.selectById(node.getCompanyUserId());
            }
            ProjectMemberEntity m = this.projectMemberService.saveProjectMember(projectId, companyId, node.getCompanyUserId(), companyUser.getUserId(), new Integer(3 + node.getSeq()), node.getId(), taskId, node.getSeq(), accountId, false,companyId);
            if(isSendMessage){
                this.messageService.sendMessageForDesigner(new SendMessageDTO(projectId,companyId,companyUser.getUserId(),accountId,companyId,SystemParameters.MESSAGE_TYPE_501,SystemParameters.MESSAGE_TYPE_502,taskId,taskId,node.getId(),node.getNodeName()));
            }
            ids += m.getId();
        }
        return ids;
    }

    private void deleteProjectProcessNode(String projectId, ProjectProcessNodeEntity nodeEntity) throws Exception {
        //删除
        projectProcessNodeDao.deleteById(nodeEntity.getId());
        //忽略任务
        myTaskService.ignoreMyTask(nodeEntity.getId());

        this.projectMemberService.deleteProjectMember(projectId, null, (3 + nodeEntity.getSeq()), nodeEntity.getId());
    }

    /**
     * 方法描述：处理流程完成
     * 作者：MaoSF
     * 日期：2017/3/12
     */
    @Override
    public ResponseBean completeProjectProcessNode(String projectId, String companyId, String nodeId, String taskId, String accountId) throws Exception {
        ProjectProcessNodeEntity originNode = projectProcessNodeDao.selectById(nodeId); //保留原有数据
        int i = this.projectProcessNodeDao.updateProcessNodeComplete(nodeId);
        ProjectProcessNodeEntity targetNode = projectProcessNodeDao.selectById(nodeId); //创建修改后数据
        //保存项目动态
        dynamicService.addDynamic(originNode, targetNode, companyId, accountId);

        //通知协同
        ProjectTaskEntity pTask = projectTaskDao.selectById(taskId);
        this.collaborationService.pushSyncCMD_PT(pTask.getProjectId(), pTask.getTaskPath(), SyncCmd.PT2);

        //推送设计--》校对--》审核
        this.sendMessage(projectId,companyId,nodeId,taskId,accountId,originNode.getSeq());

//        //判断是否存在设校审，并且是是否完成，如果全部完成，则给任务负责人推送消息
//        Map<String, Object> map = new HashMap<>();
//        map.put("taskManageId", taskId);//param1中保存了任务的id
//        map.put("notComplete", "1");//查询未完成的
//        List<ProjectProcessNodeEntity> processNodeList = this.projectProcessNodeDao.getProcessNodeByParam(map);
//        if (CollectionUtils.isEmpty(processNodeList)) {//如果全部完成，则给任务负责人推送消息
//            //通知任务负责人projectTaskEntity
//            //加上推送信息代码
//            ProjectMemberEntity taskResponsible = this.projectMemberService.getProjectMember(projectId, companyId, ProjectMemberType.PROJECT_TASK_RESPONSIBLE, taskId);
//            if (taskResponsible != null) {
//                this.messageService.sendMessage(projectId, companyId, taskId, SystemParameters.MESSAGE_TYPE_9, null, taskResponsible.getAccountId(), accountId, null);
//            }
//        }
        if (i > 0) {
            return ResponseBean.responseSuccess("操作成功");
        }
        return ResponseBean.responseError("操作失败");
    }

    /**
     * 方法描述：删除节点
     * 作者：MaoSF
     * 日期：2017/6/22
     */
    @Override
    public void deleteProcessNodeById(String id) throws Exception {
        //删除
        projectProcessNodeDao.deleteById(id);
        this.myTaskService.ignoreMyTask(id);//忽略任务
    }

    /**
     * 方法描述：保存流程节点
     * 作者：MaoSF
     * 日期：2017/6/22
     */
    @Override
    public String saveProjectProcessNode(String projectId, int seq, int nodeSeq, String taskId, String companyUserId) throws Exception {
        int seq1 = seq - 3;//因为对应memberType的值减3
        ProjectProcessNodeEntity node = new ProjectProcessNodeEntity();
        node.setId(StringUtil.buildUUID());
        node.setNodeSeq(nodeSeq);
        node.setSeq(seq1);
        node.setStatus(0);
        node.setNodeName(seq1 == 1 ? "设计" : seq1 == 2 ? "校对" : "审核");
        node.setProcessId(taskId);
        node.setCompanyUserId(companyUserId);
        this.projectProcessNodeDao.insert(node);
        return node.getId();
    }

    private void sendMessage(String projectId, String companyId, String nodeId, String taskId, String accountId,int seq) throws Exception{
        //推送设计--》校对--》审核
        ProjectDesignUserList members = null;
        if(seq==1) {//设计
            members = projectMemberService.listDesignMemberList(taskId);
            if (!CollectionUtils.isEmpty(members.getCheckUser().getUserList())) {
                for(CompanyUserAppDTO u :members.getCheckUser().getUserList()){
                    this.messageService.sendMessageForProcess(new SendMessageDTO(projectId,companyId,u.getUserId(),accountId,companyId,SystemParameters.MESSAGE_TYPE_503,taskId,u.getTargetId(),"设计","校对"));
                }

            }else if(!CollectionUtils.isEmpty(members.getExamineUser().getUserList())){
                for(CompanyUserAppDTO u :members.getExamineUser().getUserList()){
                    this.messageService.sendMessageForProcess(new SendMessageDTO(projectId,companyId,u.getUserId(),accountId,companyId,SystemParameters.MESSAGE_TYPE_503,taskId,u.getTargetId(),"设计","审核"));
                }
            }else {
                //任务负责人
                ProjectMemberEntity u = this.projectMemberService.getProjectMember(projectId, companyId, ProjectMemberType.PROJECT_TASK_RESPONSIBLE, taskId);
                if(u!=null){
                    this.messageService.sendMessageForProcess(new SendMessageDTO(projectId,companyId,u.getAccountId(),accountId,companyId,SystemParameters.MESSAGE_TYPE_405,taskId,nodeId,"设计",null));
                }
            }
        }
        if(seq==2){
            members = projectMemberService.listDesignMemberList(taskId);
            if (!CollectionUtils.isEmpty(members.getCheckUser().getUserList())) {
                for(CompanyUserAppDTO u :members.getExamineUser().getUserList()){
                    this.messageService.sendMessageForProcess(new SendMessageDTO(projectId,companyId,u.getUserId(),accountId,companyId,SystemParameters.MESSAGE_TYPE_503,taskId,u.getTargetId(),"校对","审核"));
                }
            }else {
                //任务负责人
                //任务负责人
                ProjectMemberEntity u = this.projectMemberService.getProjectMember(projectId, companyId, ProjectMemberType.PROJECT_TASK_RESPONSIBLE, taskId);
                if(u!=null){
                    this.messageService.sendMessageForProcess(new SendMessageDTO(projectId,companyId,u.getAccountId(),accountId,companyId,SystemParameters.MESSAGE_TYPE_405,taskId,nodeId,"校对",null));
                }
            }
        }
        if(seq==3){
            //给任务负责人
            //任务负责人
            ProjectMemberEntity u = this.projectMemberService.getProjectMember(projectId, companyId, ProjectMemberType.PROJECT_TASK_RESPONSIBLE, taskId);
            if(u!=null){
                this.messageService.sendMessageForProcess(new SendMessageDTO(projectId,companyId,u.getAccountId(),accountId,companyId,SystemParameters.MESSAGE_TYPE_405,taskId,nodeId,"审核",null));
            }
        }
    }
}
