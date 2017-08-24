package com.maoding.task.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.ProjectMemberType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.BeanUtilsEx;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dao.ProjectTaskResponsibleDao;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.service.ProjectService;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.task.dao.ProjectManagerDao;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dto.ProjectTaskDTO;
import com.maoding.task.dto.TransferTaskDesignerDTO;
import com.maoding.task.entity.ProjectManagerEntity;
import com.maoding.task.service.ProjectManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjecManagerServiceImpl
 * 类描述：项目经营人、负责人
 * 作    者：MaoSF
 * 日    期：2016年12月28日-下午5:28:54
 */
@Service("projectManagerService")
public class ProjectManagerServiceImpl extends GenericService<ProjectManagerEntity>  implements ProjectManagerService {

    @Autowired
    private ProjectManagerDao projectManagerDao;

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private MyTaskService myTaskService;

    @Autowired
    private ProjectTaskResponsibleDao projectTaskResponsibleDao;

    @Autowired
    private MyTaskDao myTaskDao;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProjectMemberService projectMemberService;

//    @Autowired
//    private DynamicService dynamicService;
    /**
     * 方法描述：移交经营负责人，项目负责人
     * 作者：MaoSF
     * 日期：2017/1/5
     *
     * @param map
     * @param:
     * @return:
     */
    @Override
    public ResponseBean transferManager(Map<String,Object> map) throws Exception {

        String companyUserId = (String)map.get("companyUserId");
        String projectId = (String)map.get("projectId");
        String companyId = (String)map.get("appOrgId");
        String accountId = (String)map.get("accountId");
        String type = (String)map.get("type");
        ProjectMemberEntity projectMember = null;
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
        boolean isSendMessage = true;
        if(userEntity!=null && userEntity.getId().equals(companyUserId)){
            isSendMessage = false;
        }

        if("1".equals(type)){
            projectMember = this.projectMemberService.getProjectMember(projectId,companyId, ProjectMemberType.PROJECT_OPERATOR_MANAGER,null);
        }else {
            projectMember = this.projectMemberService.getProjectMember(projectId,companyId,ProjectMemberType.PROJECT_DESIGNER_MANAGER,null);
        }
        String oldCompanyUserId = null;
        if(projectMember!=null){
            oldCompanyUserId = projectMember.getCompanyUserId();
            if(oldCompanyUserId.equals(companyUserId)){
                return ResponseBean.responseError("请移交给其他人");
            }
            //添加项目动态
            ProjectMemberEntity pmOld = new ProjectMemberEntity();
            BeanUtilsEx.copyProperties(projectMember,pmOld);
            this.projectMemberService.updateProjectMember(projectMember,companyUserId,null,accountId,isSendMessage);
            //添加项目动态
            projectMember.setCompanyUserId(companyUserId);
            //dynamicService.addDynamic(dynamicService.createDynamicFrom(pmOld,projectMember));

        }else {
            //添加设计负责人
            ProjectMemberEntity memberEntity = this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,Integer.parseInt(type),accountId,isSendMessage);
//            //添加项目动态
            //dynamicService.addDynamic(dynamicService.createDynamicFrom(memberEntity));
        }


        return ResponseBean.responseSuccess("处理成功");
    }


    /**
     * 方法描述：移交设计负责人
     * 作者：MaoSF
     * 日期：2017/3/22
     *
     * @param dto
     * @param:
     * @return:
     */
    @Override
    public ResponseBean transferTaskDesinger(TransferTaskDesignerDTO dto) throws Exception {
        ProjectMemberEntity projectMember = this.projectMemberService.getProjectMember(dto.getProjectId(),dto.getCurrentCompanyId(),ProjectMemberType.PROJECT_DESIGNER_MANAGER,null);

        String oldCompanyUserId = null;
        if(projectMember==null || StringUtil.isNullOrEmpty(dto.getCompanyUserId())){
            return ResponseBean.responseError("移交失败");
        }
        oldCompanyUserId = projectMember.getCompanyUserId();
        if(oldCompanyUserId.equals(dto.getCompanyUserId())){
            return ResponseBean.responseError("请移交给其他人");
        }

        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getCurrentCompanyId());
        boolean isSendMessage = true;
        if(userEntity!=null && userEntity.getId().equals(dto.getCompanyUserId())){
            isSendMessage = false;
        }

        ProjectMemberEntity pmOld = null;
        if (projectMember != null) {
            pmOld = new ProjectMemberEntity();
            BeanUtilsEx.copyProperties(projectMember,pmOld);
        }

        this.projectMemberService.updateProjectMember(projectMember,dto.getCompanyUserId(),null,dto.getAccountId(),isSendMessage);
        //添加项目动态
        dynamicService.addDynamic(pmOld,projectMember,dto.getCurrentCompanyId(),dto.getAccountId());

        for(String taskId:dto.getTaskList()){
            projectMember = this.projectMemberService.getProjectMember(dto.getProjectId(),dto.getCurrentCompanyId(),ProjectMemberType.PROJECT_DESIGNER_MANAGER,taskId);
            this.projectMemberService.updateProjectMember(projectMember,dto.getCompanyUserId(),null,dto.getAccountId(),isSendMessage);
        }
        return ResponseBean.responseSuccess("移交成功");
    }

    /**
     * 方法描述：删除经营负责人和设计负责人
     * 作者：MaoSF
     * 日期：2017/4/12
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public ResponseBean deleteProjectManage(String projectId, String companyId) throws Exception {
        this.projectMemberService.deleteProjectMember(projectId,companyId, ProjectMemberType.PROJECT_OPERATOR_MANAGER,null);
        this.projectMemberService.deleteProjectMember(projectId,companyId,ProjectMemberType.PROJECT_DESIGNER_MANAGER,null);
        return null;
    }


    /**
     * 方法描述：给companyUser发送成为设计负责人消息
     * 作者：ZCL
     * 日期：2017/5/20
     */
    public void sendMessageTaskDesigner(String projectId,String companyUserId,String companyId) throws Exception{
        ProjectEntity projectEntity = this.projectDao.selectById(projectId);
        CompanyUserEntity companyUserEntity = this.companyUserDao.selectById(companyUserId);
        if(projectEntity!=null && companyUserEntity!=null) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setProjectId(projectId);
            messageEntity.setTargetId(projectId);
            messageEntity.setCompanyId(companyId);
            messageEntity.setUserId(companyUserEntity.getUserId());
            messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_4);
            Map<String,Object> map = new HashMap<>();
            map.put("projectId",projectId);
            map.put("companyId",companyId);
            List<ProjectTaskDTO> taskList = projectTaskDao.getProjectTaskByCompanyId(map);
            if ((taskList == null) || (taskList.size() == 0)) {
                messageEntity.setMessageContent(projectEntity.getProjectName());
                this.messageService.sendMessage(messageEntity);
            } else {
                for (ProjectTaskDTO task : taskList){
                    messageEntity.setMessageContent(projectEntity.getProjectName() + " - " + projectTaskDao.getTaskParentName(task.getId()));
                    this.messageService.sendMessage(messageEntity);
                }
            }
        }

//        ProjectEntity projectEntity = this.projectDao.selectById(projectId);
//        CompanyUserEntity companyUserEntity = this.companyUserDao.selectById(companyUserId);
//        if(projectEntity!=null && companyUserEntity!=null) {
//            MessageEntity messageEntity = new MessageEntity();
//            messageEntity.setProjectId(projectId);
//            messageEntity.setTargetId(projectId);
//            messageEntity.setCompanyId(companyId);
//            messageEntity.setMessageContent(projectEntity.getProjectName());
//            messageEntity.setUserId(companyUserEntity.getUserId());
//            messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_4);
//            this.messageService.sendMessage(messageEntity);
//        }
    }


}
