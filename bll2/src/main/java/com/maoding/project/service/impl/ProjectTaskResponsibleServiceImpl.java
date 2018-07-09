package com.maoding.project.service.impl;

import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.ProjectMemberType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.BeanUtilsEx;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.message.dto.QueryMessageDTO;
import com.maoding.message.dto.SendMessageDTO;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.project.dto.ProjectTaskResponsibleDTO;
import com.maoding.project.service.ProjectTaskResponsibleService;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dto.SaveProjectTaskDTO;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.task.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectService
 * 类描述：项目Service
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:28:54
 */
@Service("projectTaskResponsibleService")
public class ProjectTaskResponsibleServiceImpl  implements ProjectTaskResponsibleService {

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private MessageService messageService;

    /**
     * 方法描述：保存设计负责人（新增）
     * 作者：MaoSF
     * 日期：2017/5/20
     */
    @Override
    public ResponseBean insertTaskResponsible(String projectId,String companyId,String targetId,String taskId,String accountId,String currentCompanyId) throws Exception {
        boolean isSendMessage = true;
        if(companyId.equals(currentCompanyId)){
            CompanyUserEntity user = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,currentCompanyId);
            if(user!=null && user.getId().equals(targetId)){
                isSendMessage = false;//如果当前操作指定自己为任务负责人，则不发送消息
            }
        }
        this.projectMemberService.deleteProjectMember(ProjectMemberType.PROJECT_TASK_RESPONSIBLE,taskId);
        this.projectMemberService.saveProjectMember(projectId,companyId,targetId,null, ProjectMemberType.PROJECT_TASK_RESPONSIBLE,taskId,accountId,isSendMessage,currentCompanyId);

        return ResponseBean.responseSuccess();
    }

    /**
     * 方法描述：保存设计负责人（新增）(用于处理未发布版本)
     * 作者：MaoSF
     * 日期：2017/5/20
     */
    @Override
    public ResponseBean insertTaskResponsible(String projectId, String companyId, String companyUserId, String taskId, String accountId) throws Exception {
        this.projectMemberService.deleteProjectMember(ProjectMemberType.PROJECT_TASK_RESPONSIBLE,taskId);
        this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,taskId, ProjectMemberType.PROJECT_TASK_RESPONSIBLE,0,accountId,companyId);
        return ResponseBean.responseSuccess();
    }

    /**
     * 方法描述：保存任务负责人（新增）-- 发布任务给本组织，默认设计负责人为任务负责人
     * 作者：MaoSF
     * 日期：2017/5/20
     */
    @Override
    public ResponseBean insertTaskResponsibleForPublishTask(String projectId, String companyId, String taskId, String accountId,String currentCompanyId) throws Exception {
        this.projectMemberService.deleteProjectMember(ProjectMemberType.PROJECT_TASK_RESPONSIBLE,taskId);
        ProjectMemberEntity member = this.projectMemberService.getDesignManager(projectId, companyId);
        if (member != null) {
            //如果不为空，则设置任务负责人，默认为设计负责人
            this.projectMemberService.saveProjectMember(projectId,companyId,member.getCompanyUserId(),member.getAccountId(), ProjectMemberType.PROJECT_TASK_RESPONSIBLE,taskId,accountId,false,currentCompanyId);
            //如果是第一次，则推送给设计负责人
            //是否是第一次发布给本团队
            Map<String, Object> map = new HashMap<>();
            map.put("permissionId", "50");//经营总监权限id
            map.put("companyId", currentCompanyId);
            List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
            if(!CollectionUtils.isEmpty(companyUserList)){
                QueryMessageDTO query = new QueryMessageDTO();
                query.setProjectId(projectId);
                query.setCompanyId(currentCompanyId);
                query.setMessageType(SystemParameters.MESSAGE_TYPE_401);
                query.setUserId(companyUserList.get(0).getUserId());
                if(CollectionUtils.isEmpty(messageService.getMessageByParam(query))){
                    this.sendMessage(projectId,companyId,member.getAccountId(),accountId,currentCompanyId,SystemParameters.MESSAGE_TYPE_401,null);
                }else {
                    this.sendMessage(projectId,companyId,member.getAccountId(),accountId,currentCompanyId,SystemParameters.MESSAGE_TYPE_402,null);
                }
            }
        }
        return null;
    }

    @Override
    public ResponseBean saveTaskResponsible(ProjectTaskResponsibleDTO dto) throws Exception {
        ProjectMemberEntity projectMember = this.projectMemberService.getProjectMember(dto.getProjectId(), dto.getCompanyId(),ProjectMemberType.PROJECT_TASK_RESPONSIBLE,dto.getTaskId());
        //保留原有的数据
        ProjectMemberEntity oldProjectTaskResponsible = null;
        if (projectMember != null) {
            oldProjectTaskResponsible = new ProjectMemberEntity();
            BeanUtilsEx.copyProperties(projectMember, oldProjectTaskResponsible);
        }
        CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getCompanyId());
        boolean isSendMessage = true;
        if(companyUserEntity!=null && companyUserEntity.getId().equals(dto.getTargetId())){
            isSendMessage = false;
        }
        if(null!=projectMember) {
            this.projectMemberService.updateProjectMember(projectMember,dto.getTargetId(),null,dto.getAccountId(),false);
        }else {
            projectMember = this.projectMemberService.saveProjectMember(dto.getProjectId(),dto.getCompanyId(),dto.getTargetId(),null,ProjectMemberType.PROJECT_TASK_RESPONSIBLE,dto.getTaskId(),dto.getAccountId(),false,dto.getAppOrgId());
        }
        if(isSendMessage){
            this.messageService.sendMessageForDesigner(new SendMessageDTO(dto.getProjectId(), dto.getCompanyId(),projectMember.getAccountId(),dto.getAccountId(),dto.getAppOrgId(),SystemParameters.MESSAGE_TYPE_403,SystemParameters.MESSAGE_TYPE_404,dto.getTaskId(),dto.getTaskId(),null));
        }
        // 添加项目动态
        dynamicService.addDynamic(oldProjectTaskResponsible,projectMember,dto.getAppOrgId(),dto.getAccountId());
        return  ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 任务负责人移交(未发布状态)
     */
    @Override
    public ResponseBean transferTaskResponse(ProjectTaskResponsibleDTO dto, boolean isPublish) throws Exception {
        String taskId = dto.getTaskId();
        String beModifyId = dto.getTaskId();
        ProjectTaskEntity taskEntity = this.projectTaskDao.selectById(taskId);
        if(StringUtil.isNullOrEmpty(taskId) || taskEntity==null){
            return ResponseBean.responseError("操作失败");
        }
        if(taskEntity!=null && taskEntity.getTaskType()!= SystemParameters.TASK_PRODUCT_TYPE_MODIFY){//则新增一条被修改的记录
            taskId = projectTaskService.copyProjectTask(new SaveProjectTaskDTO(),taskEntity);
        }else {
            beModifyId = taskEntity.getBeModifyId();
        }
        if(isPublish){//需要发布
            //保存对象
            this.projectTaskService.updateByTaskIdStatus(taskId,SystemParameters.TASK_STATUS_MODIFIED);
        }else {//不需要发布
            ProjectMemberEntity projectMember = this.projectMemberService.getProjectMember(dto.getProjectId(), dto.getCompanyId(),ProjectMemberType.PROJECT_TASK_RESPONSIBLE,beModifyId);
            CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getCompanyId());
            boolean isSendMessage = true;
            if(companyUserEntity!=null && companyUserEntity.getId().equals(dto.getTargetId())){
                isSendMessage = false;
            }
            if(null!=projectMember) {
                this.projectMemberService.updateProjectMember(projectMember,dto.getTargetId(),null,dto.getAccountId(),isSendMessage);
            }else {
                this.projectMemberService.saveProjectMember(dto.getProjectId(),dto.getCompanyId(),dto.getTargetId(),null,ProjectMemberType.PROJECT_TASK_RESPONSIBLE,dto.getTaskId(),dto.getAccountId(),isSendMessage,dto.getAppOrgId());
            }
        }
        //无论何种情况，任务类型=4的，都需要新增记录（如果存在原来的记录，则删除，再新增）
        this.insertTaskResponsible(taskEntity.getProjectId(),taskEntity.getCompanyId(),dto.getTargetId(),taskId,dto.getTargetId());
        return  ResponseBean.responseSuccess("操作成功");
    }

    private void sendMessage(String projectId,String companyId,String userId,String accountId,String currentCompanyId,Integer messageType,String taskId) throws Exception{
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setCompanyId(companyId);
        messageEntity.setProjectId(projectId);
        messageEntity.setMessageType(messageType);
        messageEntity.setUserId(userId);
        messageEntity.setCreateBy(accountId);
        messageEntity.setSendCompanyId(currentCompanyId);
        messageEntity.setTargetId(taskId);
        messageService.sendMessage(messageEntity);
    }
}
