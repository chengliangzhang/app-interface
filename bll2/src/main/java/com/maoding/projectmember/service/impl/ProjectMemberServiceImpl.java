package com.maoding.projectmember.service.impl;

import com.maoding.conllaboration.service.CollaborationService;
import com.maoding.core.constant.ProjectMemberType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.org.dto.CompanyUserGroupDTO;
import com.maoding.org.dto.CompanyUserTableDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dto.ProjectDesignUserList;
import com.maoding.project.dto.ProjectTaskProcessNodeDTO;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.service.ProjectProcessService;
import com.maoding.projectmember.dao.ProjectMemberDao;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.dto.ProjectMemberGroupDTO;
import com.maoding.projectmember.dto.UserPositionDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.entity.ProjectTaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Idccapp22 on 2017/6/6.
 */
@Service("projectMemberService")
public class ProjectMemberServiceImpl implements ProjectMemberService{

    @Autowired
    private ProjectMemberDao projectMemberDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private MyTaskService myTaskService;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private MyTaskDao myTaskDao;

    @Autowired
    private ProjectProcessService projectProcessService;

    @Value("${fastdfs.url}")
    protected String fastdfsUrl;

    /**
     * 方法描述：保存项目成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public ProjectMemberEntity saveProjectMember(String projectId, String companyId, String companyUserId, String accountId,Integer memberType, String targetId, String nodeId, Integer seq,String createBy,boolean isSendMessage,String currentCompanyId)  throws Exception{
        ProjectMemberEntity projectMember = new ProjectMemberEntity();
        projectMember.setId(StringUtil.buildUUID());
        projectMember.setProjectId(projectId);
        projectMember.setCompanyId(companyId);
        projectMember.setCompanyUserId(companyUserId);
        projectMember.setAccountId(accountId);
        projectMember.setTargetId(targetId);
        projectMember.setNodeId(nodeId);
        projectMember.setMemberType(memberType);
        projectMember.setStatus(ProjectMemberType.FORMAL_STATUS);
        projectMember.setDeleted(0);
        projectMember.setSeq(seq==null?0:seq);
        projectMember.setCreateBy(createBy);
        projectMember.setCreateDate(new Date());
        syncCompanyUserIdToAccountId(projectMember,companyUserId,accountId);
        int i= projectMemberDao.insert(projectMember);

        //TODO 推送任务
        this.sendTaskToUser(projectId,companyId,companyUserId,memberType,targetId,nodeId,isSendMessage,createBy,currentCompanyId);

        //TODO 把人员添加到项目群组中
        //this.imGroupService.addUserToProjectGroup(projectId,companyUserId,accountId,targetId,memberType);

        return projectMember;
    }

    /**
     * 方法描述：保存项目成员（未发布版本的数据：status=2）
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public ProjectMemberEntity saveProjectMember(String projectId, String companyId, String companyUserId, String targetId, Integer memberType,Integer seq,String createBy,String currentCompanyId)  throws Exception{
        ProjectMemberEntity projectMember = new ProjectMemberEntity();
        projectMember.setId(StringUtil.buildUUID());
        projectMember.setProjectId(projectId);
        projectMember.setCompanyId(companyId);
        projectMember.setCompanyUserId(companyUserId);
        if(memberType==ProjectMemberType.PROJECT_TASK_RESPONSIBLE){
            projectMember.setTargetId(targetId);
        }else {
            projectMember.setNodeId(targetId);
        }
        //projectMember.setTargetId(targetId);
        projectMember.setMemberType(memberType);
        projectMember.setStatus(ProjectMemberType.NOT_PUBLISH_STATUS);
        projectMember.setDeleted(0);
        projectMember.setSeq(seq==null?0:seq);
        projectMember.setCreateBy(createBy);
        projectMember.setCreateDate(new Date());
        syncCompanyUserIdToAccountId(projectMember,companyUserId,null);
        int i= projectMemberDao.insert(projectMember);
        return projectMember;
    }

    /**
     * 方法描述：保存项目成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public ProjectMemberEntity saveProjectMember(String projectId, String companyId, String companyUserId, String accountId, Integer memberType,String createBy,String currentCompanyId)  throws Exception{
        return this.saveProjectMember(projectId,companyId,companyUserId,accountId,memberType,null,null,null,createBy,true,currentCompanyId);
    }

    /**
     * 方法描述：保存项目成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public ProjectMemberEntity saveProjectMember(String projectId, String companyId, String companyUserId, String accountId, Integer memberType,String targetId, String createBy,boolean isSendMessage,String currentCompanyId) throws Exception {
        return this.saveProjectMember(projectId,companyId,companyUserId,accountId,memberType,targetId,null,null,createBy,isSendMessage,currentCompanyId);
    }

    /**
     * 方法描述：保存项目成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public ProjectMemberEntity saveProjectMember(String projectId, String companyId, String companyUserId, String accountId, Integer memberType, String createBy, boolean isSendMessage,String currentCompanyId)  throws Exception{
        return this.saveProjectMember(projectId,companyId,companyUserId,accountId,memberType,null,null,null,createBy,isSendMessage,currentCompanyId);
    }

    /**
     * 方法描述：保存项目成员(只推送消息，不发送任务)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public ProjectMemberEntity saveProjectMember(String projectId, String companyId, String companyUserId, Integer memberType, String createBy,boolean isSendMessage,String currentCompanyId) throws Exception {

        ProjectMemberEntity projectMember = new ProjectMemberEntity();
        projectMember.setId(StringUtil.buildUUID());
        projectMember.setProjectId(projectId);
        projectMember.setCompanyId(companyId);
        projectMember.setCompanyUserId(companyUserId);
        projectMember.setMemberType(memberType);
        projectMember.setStatus(ProjectMemberType.FORMAL_STATUS);
        projectMember.setDeleted(0);
        projectMember.setCreateBy(createBy);
        projectMember.setCreateDate(new Date());
        if(memberType == ProjectMemberType.PROJECT_CREATOR){
            //立项人的accountId= createBy
            syncCompanyUserIdToAccountId(projectMember,companyUserId,createBy);
        }else {
            syncCompanyUserIdToAccountId(projectMember,companyUserId,null);
        }

        projectMemberDao.insert(projectMember);
        //推送消息
        if(isSendMessage){
            sendMessageToUser(projectId,companyId,companyUserId,memberType);
        }
        //如果是添加立项人，则不处理添加到项目成员中，因为，立项人就是创建项目群组的管理员
//        if(memberType != ProjectMemberType.PROJECT_CREATOR){
//            //TODO 把人员添加到项目群组中
//            imGroupService.addUserToProjectGroup(projectId,companyUserId,null,null,memberType);
//        }
        return projectMember;
    }


    /**
     * 方法描述：更新成员(交换操作)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public void updateProjectMember(ProjectMemberEntity projectMember,String companyUserId,String accountId,String updateBy,boolean isSendMessage) throws Exception{
       String oldCompanyUserId = projectMember.getCompanyUserId();
        projectMember.setCompanyUserId(companyUserId);
        projectMember.setAccountId(accountId);
        syncCompanyUserIdToAccountId(projectMember,companyUserId,accountId);
        this.projectMemberDao.updateById(projectMember);

        //处理任务
        if(ProjectMemberType.PROJECT_OPERATOR_MANAGER==projectMember.getMemberType()){
            transferOperatorManagerTask(oldCompanyUserId,companyUserId,projectMember.getCompanyId(),projectMember.getProjectId(),isSendMessage);
        }
        if(ProjectMemberType.PROJECT_DESIGNER_MANAGER==projectMember.getMemberType()){
            sendMessageTaskDesigner(projectMember.getProjectId(),companyUserId,projectMember.getCompanyId(),null);
        }
        if(ProjectMemberType.PROJECT_TASK_RESPONSIBLE==projectMember.getMemberType()){
            sendTaskForChangeMember(projectMember.getProjectId(),projectMember.getCompanyId(),companyUserId,oldCompanyUserId,projectMember.getMemberType(),projectMember.getTargetId(),isSendMessage,updateBy);
        }
        //把人员添加到项目群组中
        //imGroupService.addUserToProjectGroup(projectMember.getProjectId(),companyUserId,accountId,projectMember.getTargetId(),projectMember.getMemberType());

        //移除旧人员
        //this.imGroupService.removeNotContentProject(projectMember.getProjectId(),oldCompanyUserId,null,projectMember.getTargetId(),projectMember.getMemberType());

    }

    /**
     * 方法描述：更新成员状态
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public void updateProjectMember(String projectId, String companyId, String companyUserId, Integer memberType, String targetId) throws Exception {
        ProjectMemberEntity projectMember = new ProjectMemberEntity();
        projectMember.setProjectId(projectId);
        projectMember.setCompanyId(companyId);
        projectMember.setCompanyUserId(companyUserId);
        projectMember.setTargetId(targetId);
        projectMember.setMemberType(memberType);
        this.projectMemberDao.updateProjectMemberStatus(projectMember);
    }

    /**
     * 方法描述：删除成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public void deleteProjectMember(String projectId, String companyId,Integer memberType, String targetId) throws Exception{
       // List<ProjectMemberEntity> list = this.listProjectMember(projectId,companyId,memberType,targetId);
        ProjectMemberEntity projectMember = new ProjectMemberEntity();
        projectMember.setProjectId(projectId);
        projectMember.setCompanyId(companyId);
        projectMember.setTargetId(targetId);
        projectMember.setMemberType(memberType);
        //TODO 删除操作
        this.projectMemberDao.deleteProjectMember(projectMember);
        //TODO 从项目群组移除成员
//        for(ProjectMemberEntity member:list){
//            this.imGroupService.removeNotContentProject(member.getProjectId(),member.getCompanyUserId(),null,member.getTargetId(),memberType);
//        }
    }

    /**
     * 方法描述：删除成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public void deleteProjectMember(Integer memberType,String targetId) throws Exception{
        this.deleteProjectMember(null,null,memberType,targetId);
    }

    /**
     * 方法描述：删除成员(删除修改版本的数据，物理删除)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public void deleteProjectMember(String id) throws Exception {
        this.projectMemberDao.deleteById(id);
    }

    /**
     * 方法描述：发布任务，处理当前任务下所有的人员
     * 作者：MaoSF
     * 日期：2017/6/21
     * @param taskId（正式记录的ID）
     * @param modifyTaskId（正式记录被关联用于修改记录的ID）
     */
    @Override
    public void publishProjectMember(String projectId,String companyId,String taskId,String modifyTaskId,String accountId) throws Exception {
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
        if(userEntity==null){
            return;
        }
        List<ProjectMemberEntity> memberList = this.listProjectMember(null,null,null,taskId);
        List<ProjectMemberEntity> modifyMemberList = this.listProjectMember(null,null,null,modifyTaskId);
        //处理任务负责人
        publishTaskResponsible(projectId,companyId,taskId,userEntity.getId(),accountId,memberList,modifyMemberList);
        //处理设计人员
        publishTaskDesign(projectId,companyId,taskId,userEntity.getId(),accountId,memberList,modifyMemberList);
    }

    /**
     * 方法描述：copy 任务下所有的人员
     * 作者：MaoSF
     * 日期：2017/6/21
     */
    @Override
    public void copyProjectMember(String taskId, String newTaskId) throws Exception {
        List<ProjectMemberEntity> list = this.projectMemberDao.listProjectMemberByTaskId(taskId);
        for(ProjectMemberEntity entity:list){
            entity.setId(StringUtil.buildUUID());
            entity.setStatus(ProjectMemberType.BE_MODIFY_STATUS);
            if(entity.getMemberType()==ProjectMemberType.PROJECT_TASK_RESPONSIBLE){
                entity.setTargetId(newTaskId);
            }else {
                entity.setNodeId(newTaskId);
            }
            this.projectMemberDao.insert(entity);
        }
    }
    /**
     * 方法描述：获取成员信息
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public List<ProjectMemberEntity> listProjectMember(String projectId, String companyId, Integer memberType, String targetId) {
        ProjectMemberEntity projectMember = new ProjectMemberEntity();
        projectMember.setProjectId(projectId);
        projectMember.setCompanyId(companyId);
        projectMember.setMemberType(memberType);
        projectMember.setTargetId(targetId);
        return this.projectMemberDao.listProjectMember(projectMember);
    }

    /**
     * 方法描述：获取成员信息
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public ProjectMemberEntity getProjectMember(String projectId, String companyId, Integer memberType, String targetId) {
        List<ProjectMemberEntity> list = this.listProjectMember(projectId,companyId,memberType,targetId);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 方法描述：获取成员信息(带有个人资料信息)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public List<ProjectMemberDTO> listProjectMemberByParam(String projectId, String companyId, Integer memberType, String targetId) {
        ProjectMemberEntity projectMember = new ProjectMemberEntity();
        projectMember.setProjectId(projectId);
        projectMember.setCompanyId(companyId);
        projectMember.setMemberType(memberType);
        projectMember.setTargetId(targetId);
        List<ProjectMemberDTO> list = this.projectMemberDao.listProjectMemberByParam(projectMember);
//        for(ProjectMemberDTO dto:list){
//            if(!StringUtil.isNullOrEmpty(dto.getImgUrl())){
//                dto.setImgUrl(fastdfsUrl+dto.getImgUrl());
//            }
//        }
        return list;
    }

    @Override
    public ProjectMemberDTO getProjectMemberByParam(String projectId, String companyId, Integer memberType, String targetId) {
        List<ProjectMemberDTO> list = this.listProjectMemberByParam(projectId,companyId,memberType,targetId);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<ProjectMemberGroupDTO> listProjectMember(String projectId, String currentCompanyId, String accountId) {
        List<ProjectMemberGroupDTO> groupList = new ArrayList<>();
        ProjectEntity project = projectDao.selectById(projectId);
        if(project==null){
            return groupList;
        }
        List<CompanyEntity> companyList = this.companyDao.getCompanyByProjectId(projectId);
        for(CompanyEntity c : companyList){
            ProjectMemberEntity projectMember = new ProjectMemberEntity();
            projectMember.setProjectId(projectId);
            projectMember.setCompanyId(c.getId());
            List<ProjectTaskProcessNodeDTO> list= this.projectMemberDao.listProjectMemberGroupByParam(projectMember);
            setPermission(list,c.getId(), currentCompanyId, accountId);

            ProjectMemberGroupDTO dto = new ProjectMemberGroupDTO();
            dto.setCompanyId(c.getId());
            dto.setCompanyName(c.getCompanyName());
            dto.setProjectCompanyId(project.getCompanyId());
            dto.setProjectCreateBy(project.getCreateBy());
            dto.setNodeList(list);
            groupList.add(dto);
        }
        return groupList;
    }

    /**
     * 方法描述：经营负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    @Override
    public ProjectMemberEntity getOperatorManager(String projectId, String companyId) {
        return this.getProjectMember(projectId,companyId,ProjectMemberType.PROJECT_OPERATOR_MANAGER,null);
    }

    /**
     * 方法描述：经营助理
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    @Override
    public ProjectMemberEntity getOperatorAssistant(String projectId, String companyId)  {
        return this.getProjectMember(projectId, companyId, ProjectMemberType.PROJECT_OPERATOR_MANAGER_ASSISTANT, null);
    }

    /**
     * 方法描述：设计负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    @Override
    public ProjectMemberEntity getDesignManager(String projectId, String companyId) {
        return this.getProjectMember(projectId,companyId,ProjectMemberType.PROJECT_DESIGNER_MANAGER,null);
    }

    @Override
    public List<ProjectMemberEntity> listDesignManagerAndAssist(String projectId, String companyId){
        List<ProjectMemberEntity> list = new ArrayList<>();
        ProjectMemberEntity member = this.getDesignManager(projectId, companyId);
        if (member != null ) {
            list.add(member);
        }
        ProjectMemberEntity assistant = this.getDesignManagerAssistant(projectId, companyId);//设计助理
        if (assistant != null) {
            list.add(assistant);
        }
        return list;
    }

    @Override
    public ProjectMemberEntity getDesignManagerAssistant(String projectId, String companyId)  {
        return this.getProjectMember(projectId, companyId, ProjectMemberType.PROJECT_DESIGNER_MANAGER_ASSISTANT, null);
    }

    /**
     * 方法描述：设计负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    @Override
    public ProjectMemberEntity getTaskDesigner(String taskId) {
        return this.getProjectMember(null,null,ProjectMemberType.PROJECT_TASK_RESPONSIBLE,taskId);
    }

    /**
     * 方法描述：获取成员信息(社校审)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public ProjectMemberEntity getProjectMember(String companyUserId, Integer memberType, String targetId) {
        ProjectMemberEntity projectMember = new ProjectMemberEntity();
        projectMember.setCompanyUserId(companyUserId);
        projectMember.setMemberType(memberType);
        projectMember.setTargetId(targetId);
        List<ProjectMemberEntity> list=  this.projectMemberDao.listProjectMember(projectMember);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }


    /**
     * 方法描述：立项人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    @Override
    public ProjectMemberDTO getProjectCreatorDTO(String projectId, String companyId) {
        return this.getProjectMemberByParam(projectId,companyId,ProjectMemberType.PROJECT_CREATOR,null);
    }

    /**
     * 方法描述：经营负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    @Override
    public ProjectMemberDTO getOperatorManagerDTO(String projectId, String companyId)  {
        return this.getProjectMemberByParam(projectId,companyId,ProjectMemberType.PROJECT_OPERATOR_MANAGER,null);
    }

    /**
     * 方法描述：设计负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    @Override
    public ProjectMemberDTO getDesignManagerDTO(String projectId, String companyId) {
        return this.getProjectMemberByParam(projectId,companyId,ProjectMemberType.PROJECT_DESIGNER_MANAGER,null);
    }

    /**
     * 方法描述：设计负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    @Override
    public ProjectMemberDTO getTaskDesignerDTO(String taskId) {
        return this.getProjectMemberByParam(null,null,ProjectMemberType.PROJECT_TASK_RESPONSIBLE,taskId);
    }

    /**
     * 方法描述：经营负责人+设计负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     *
     * @param projectId
     * @param companyId
     */
    @Override
    public List<ProjectMemberDTO> listProjectManager(String projectId, String companyId) {
        List<ProjectMemberDTO> list = this.listProjectMemberByParam(projectId,companyId,null,null);
        List<ProjectMemberDTO> result = new ArrayList<>();
        for(ProjectMemberDTO dto:list){
            if(dto.getMemberType() == ProjectMemberType.PROJECT_OPERATOR_MANAGER || dto.getMemberType()==ProjectMemberType.PROJECT_DESIGNER_MANAGER){
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * 方法描述：成员角色
     * 作者：MaoSF
     * 日期：2017/6/13
     */
    @Override
    public List<UserPositionDTO> listUserPositionForProject(String projectId, String companyId, String accountId) {
        return this.projectMemberDao.getUserPositionForProject(projectId,companyId,accountId);
    }

    /**
     * 方法描述：
     * 作者：MaoSF
     * 日期：2017/6/24
     *
     * @param taskId
     */
    @Override
    public List<ProjectTaskProcessNodeDTO> listDesignUser(String taskId) {
        List<ProjectTaskProcessNodeDTO> list = this.projectMemberDao.listProjectDesignMember(taskId,fastdfsUrl);
        return list;
    }

    @Override
    public ProjectDesignUserList listDesignMemberList(String taskId) {
        ProjectDesignUserList dto = new ProjectDesignUserList();
        List<ProjectTaskProcessNodeDTO> list = listDesignUser(taskId);
        for (ProjectTaskProcessNodeDTO design :list) {
            if (design.getMemberType() == ProjectMemberType.PROJECT_DESIGNER) {
                dto.setDesignUser(design);

            }
            if (design.getMemberType() == ProjectMemberType.PROJECT_PROOFREADER) {
                dto.setCheckUser(design);
            }
            if (design.getMemberType() == ProjectMemberType.PROJECT_AUDITOR) {
                dto.setExamineUser(design);
            }
        }
        return dto;
    }

    @Override
    public List<ProjectTaskProcessNodeDTO> listDesignMember(String taskId) {
        ProjectTaskEntity task = this.projectTaskDao.selectById(taskId);
        List<ProjectTaskProcessNodeDTO> list = new ArrayList<>();
        if(task == null){
            return list;
        }
        int taskState = this.projectTaskDao.getTaskState(taskId,task.getProjectId());

        ProjectMemberDTO design = getTaskDesignerDTO(taskId);
        if(design!=null){
            ProjectTaskProcessNodeDTO designUser = new ProjectTaskProcessNodeDTO();
            designUser.setMemberType(ProjectMemberType.PROJECT_TASK_RESPONSIBLE);
            CompanyUserAppDTO userAppDTO = new CompanyUserAppDTO();
            userAppDTO.setId(design.getCompanyUserId());
            userAppDTO.setUserName(design.getCompanyUserName());
            userAppDTO.setAccountName(design.getAccountName());
            userAppDTO.setCellphone(design.getCellphone());
            userAppDTO.setUserId(design.getUserId());
            userAppDTO.setFileFullPath(design.getFileFullPath());
            userAppDTO.setTaskState(taskState);
            userAppDTO.setEmail(design.getEmail());
            designUser.getUserList().add(userAppDTO);
            list.add(designUser);
        }
        List<ProjectTaskProcessNodeDTO> designUserList = listDesignUser(taskId);
        //设置状态
        for(ProjectTaskProcessNodeDTO dto:designUserList){
            for(CompanyUserAppDTO userDTO:dto.getUserList()){
                userDTO.setTaskState(taskState);
                if(!StringUtil.isNullOrEmpty(userDTO.getCompleteTime())){
                    userDTO.setTaskState(3);//已完成
                    if(!StringUtil.isNullOrEmpty(task.getEndTime())){
                        if(DateUtils.datecompareDate(userDTO.getCompleteTime(),task.getEndTime())>0){
                            userDTO.setTaskState(4);//超时完成
                        }
                    }
                }
            }
        }
        //
        list.addAll(designUserList);
        return list;
    }

    /**
     * 方法描述：获取设计人员
     * 作者：MaoSF
     * 日期：2017/6/24
     *
     * @param taskId
     */
    @Override
    public String getDesignUserByTaskId(String taskId) {
        return this.projectMemberDao.getDesignUserByTaskId(taskId);
    }

    @Override
    public int getMemberCount(String projectId) {
        ProjectMemberEntity entity = new ProjectMemberEntity();
        entity.setProjectId(projectId);
        return this.projectMemberDao.getMemberCount(entity);
    }

    @Override
    public List<CompanyUserGroupDTO> listProjectAllMember(Map<String, Object> map) {
        ProjectEntity project = projectDao.selectById(map.get("projectId"));
        if(project==null){
            return new ArrayList<>();
        }
        map.put("projectCompanyId",project.getCompanyId());//为了排序，把立项方排放在第一位
        map.put("fastdfsUrl",this.fastdfsUrl);
        return projectMemberDao.listProjectAllMember(map);
    }

    @Override
    public List<CompanyUserAppDTO> listProjectMember(String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("fastdfsUrl",this.fastdfsUrl);
        map.put("projectId",projectId);
        return projectMemberDao.listProjectMember(map);
    }

    private void setPermission(List<ProjectTaskProcessNodeDTO> list,String companyId, String currentCompanyId,String accountId){
        if(companyId.equals(currentCompanyId)) {//如果是当前公司的记录，则处理是否具有修改经营负责人和设计负责人的权限
            for (ProjectTaskProcessNodeDTO dto : list) {
                for (CompanyUserAppDTO user : dto.getUserList()) {
                    if(dto.getMemberType()==ProjectMemberType.PROJECT_OPERATOR_MANAGER || dto.getMemberType()==ProjectMemberType.PROJECT_DESIGNER_MANAGER){
                        setPermission(dto.getMemberType(), user, companyId, currentCompanyId, accountId);
                    }
                }
            }
        }
    }
    private void setPermission(int type ,CompanyUserAppDTO user,String companyId, String currentCompanyId,String accountId){

        /******************设置权限代码*******************/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId",companyId);
        if (1==type) {
            map.put("permissionId", "51");//经营负责人
        }
        if(2==type ){
            map.put("permissionId", "52");//经营负责人
        }
        map.put("companyId",currentCompanyId);
        map.put("userId", accountId);
        List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
        if(!CollectionUtils.isEmpty(companyUserList)){
            user.setIsUpdateOperator(1);
        }
        /*************************************/
    }

    /**
     * 方法描述：同步插入companyUserId，accountId（防止接口中没有同时、传入CompanyUserId，accountId）
     * 作者：MaoSF
     * 日期：2017/6/6
     * @param:
     * @return:
     */
    private void syncCompanyUserIdToAccountId(ProjectMemberEntity projectMember,String companyUserId,String accountId){
        if(companyUserId==null && accountId!=null){
            CompanyUserEntity companyUser = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,projectMember.getCompanyId());
            if(companyUser!=null){
                projectMember.setCompanyUserId(companyUser.getId());
                projectMember.setAccountId(companyUser.getUserId());
            }
        }
        if(companyUserId!=null && accountId==null){
            CompanyUserEntity companyUser = companyUserDao.selectById(companyUserId);
            if(companyUser!=null){
                projectMember.setAccountId(companyUser.getUserId());
            }
        }
    }

    /**
     * 方法描述：给项目成员推送任务
     * 作者：MaoSF
     * 日期：2017/6/7
     */
    private void sendTaskToUser(String projectId, String companyId, String companyUserId, Integer memberType, String targetId, String nodeId,boolean isSendMessage,String accountId,String currentCompanyId) throws Exception{
        switch (memberType){
            case 1:
                myTaskService.saveMyTask(projectId, SystemParameters.ISSUE_TASK, companyId, companyUserId, isSendMessage,accountId,currentCompanyId);
                break;
            case 3:
                //给负责人发送任务负责人
                this.myTaskService.saveMyTask(targetId, SystemParameters.PRODUCT_TASK_RESPONSE,companyId,companyUserId,isSendMessage,accountId,currentCompanyId);
                break;
            case 4:
            case 5:
            case 6:
                this.myTaskService.saveMyTask(targetId, SystemParameters.PROCESS_DESIGN, companyId, companyUserId,isSendMessage,accountId,currentCompanyId);
                break;
            default:
        }


    }

    /**
     * 方法描述：乙方和设计负责人，只推送消息
     * 作者：MaoSF
     * 日期：2017/6/7
     */
    private void sendMessageToUser(String projectId, String companyId, String companyUserId,Integer memberType) throws Exception{
        if(memberType== ProjectMemberType.PROJECT_OPERATOR_MANAGER ){//此处代表乙方
            sendMessageToPartBManager( projectId,  companyId,  companyUserId, SystemParameters.ISSUE_TASK,null);
        }
        if(memberType==ProjectMemberType.PROJECT_DESIGNER_MANAGER ){
            sendMessageTaskDesigner(projectId,companyUserId,companyId,null);
        }
    }

    private void sendMessageToPartBManager(String projectId, String companyId, String managerId, int type, String content) throws Exception {

        CompanyUserEntity user = this.companyUserDao.selectById(managerId);
        if(content==null){
            ProjectEntity project = this.projectDao.selectById(projectId);
            if(project!=null){
                content = project.getProjectName();
            }
        }
        if (user != null) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessageType(type);
            messageEntity.setProjectId(projectId);
            messageEntity.setCompanyId(companyId);
            messageEntity.setMessageContent(content);
            messageEntity.setTargetId(projectId);
            messageEntity.setUserId(user.getUserId());
            this.messageService.sendMessage(messageEntity);
        }
    }

    /**
     * 方法描述：更换经营负责人，把经营负责人的所有任务移交给新的经营负责人
     * 作者：MaoSF
     * 日期：2017/5/9
     * @param:
     * @return:
     */
    private void transferOperatorManagerTask(String oldCompanyUserId,String newCompanyUserId,String companyId,String projectId,boolean isSendMessage) throws Exception{

        //查询当前项目中经营负责人处理费用的任务
        Map<String,Object> param = new HashMap<>();
        param.put("status","0");
        List<String> typeList = new ArrayList<>();
        typeList.add(""+SystemParameters.ISSUE_TASK);
        typeList.add(""+SystemParameters.ARRANGE_TASK_DESIGN);
        typeList.add(""+SystemParameters.TECHNICAL_REVIEW_FEE_OPERATOR_MANAGER);
        typeList.add(""+SystemParameters.COOPERATIVE_DESIGN_FEE_ORG_MANAGER);
        param.put("typeList",typeList);
        param.put("companyId",companyId);
        param.put("projectId",projectId);
        param.put("handlerId",oldCompanyUserId);
        List<MyTaskEntity> myTaskList = this.myTaskService.getMyTaskByParam(param);
        if(!CollectionUtils.isEmpty(myTaskList)){
            for(MyTaskEntity myTask:myTaskList){
                myTask.setHandlerId(newCompanyUserId);
                this.myTaskService.saveMyTask(myTask,isSendMessage);
            }
            //把所有的任务设置为无效
            param.put("param4","1");
            this.myTaskDao.updateStatesByTargetId(param);
        }else {//如果是乙方，没有任务的时候，则只推送消息
            if(isSendMessage){
                CompanyUserEntity user = this.companyUserDao.selectById(newCompanyUserId);
                if(user==null){
                    return;
                }
                ProjectEntity project = this.projectDao.selectById(projectId);
                if(project==null){
                    return;
                }
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_1);
                messageEntity.setProjectId(projectId);
                messageEntity.setCompanyId(companyId);
                messageEntity.setMessageContent(project.getProjectName());
                messageEntity.setTargetId(projectId);
                messageEntity.setUserId(user.getUserId());
                this.messageService.sendMessage(messageEntity);
            }
        }

    }

    public void sendMessageTaskDesigner(String projectId,String companyUserId,String companyId,String taskId) throws Exception{
        ProjectEntity projectEntity = this.projectDao.selectById(projectId);
        boolean isPartB = false;
        CompanyUserEntity companyUserEntity = this.companyUserDao.selectById(companyUserId);
        if(projectEntity!=null && companyUserEntity!=null) {
            isPartB = !StringUtil.isNullOrEmpty(projectEntity.getCompanyBid()) && !projectEntity.getCompanyId().equals(projectEntity.getCompanyBid());
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setProjectId(projectId);
            messageEntity.setTargetId(projectId);
            messageEntity.setCompanyId(companyId);
            messageEntity.setUserId(companyUserEntity.getUserId());
            messageEntity.setMessageType(isPartB?SystemParameters.MESSAGE_TYPE_2:SystemParameters.MESSAGE_TYPE_4);
            if (taskId == null) {
                List<String> taskList = projectTaskDao.getProjectTaskOfToCompany(projectId, companyId);
                if ((taskList == null) || (taskList.size() == 0)) {
                    messageEntity.setMessageContent(projectEntity.getProjectName());
                    this.messageService.sendMessage(messageEntity);
                } else {
                    for (String t : taskList) {
                        messageEntity.setMessageContent(projectEntity.getProjectName() + " - " + projectTaskDao.getTaskParentName(t));
                        this.messageService.sendMessage(messageEntity);
                    }
                }
            } else {
                messageEntity.setMessageContent(projectEntity.getProjectName() + " - " + projectTaskDao.getTaskParentName(taskId));
                this.messageService.sendMessage(messageEntity);
            }
        }
    }

    /**
     * 方法描述：更换任务负责人任务处理
     * 作者：MaoSF
     * 日期：2017/6/7
     */
    private void sendTaskForChangeMember(String projectId, String companyId, String companyUserId, String oldCompanyUserId,Integer memberType,String targetId,boolean isSendMessage,String accountId) throws Exception{

        int taskType = 0;
        if(memberType == ProjectMemberType.PROJECT_OPERATOR_MANAGER){
            taskType = SystemParameters.ISSUE_TASK;
        }
        if(memberType == ProjectMemberType.PROJECT_TASK_RESPONSIBLE){
            taskType = SystemParameters.PRODUCT_TASK_RESPONSE;
        }

        if(taskType>0){
            if(oldCompanyUserId!=null){
                //忽略原来负责人的任务
                myTaskService.ignoreMyTask(targetId,taskType,oldCompanyUserId);
            }
            //给新的任务负责人推送任务
            this.myTaskService.saveMyTask(targetId, taskType,companyId,companyUserId,isSendMessage,accountId,companyId);
        }
    }

    /**
     * 方法描述：发布任务，处理任务负责人
     * 作者：MaoSF
     * 日期：2017/6/22
     */
    private void publishTaskResponsible(String projectId,String companyId,String targetId,String currentCompanyUserId,String accountId,List<ProjectMemberEntity> memberList,List<ProjectMemberEntity> modifyMemberList) throws Exception{
        List<ProjectMemberEntity> oldList = getByMemberType(memberList,ProjectMemberType.PROJECT_TASK_RESPONSIBLE);
        List<ProjectMemberEntity> modifyList = getByMemberType(modifyMemberList,ProjectMemberType.PROJECT_TASK_RESPONSIBLE);
        ProjectMemberEntity oldMember =  (CollectionUtils.isEmpty(oldList)?null:oldList.get(0));
        String oldMemberId = (CollectionUtils.isEmpty(oldList)?null:oldList.get(0).getCompanyUserId());
        String modifyMemberId = (CollectionUtils.isEmpty(modifyList)?null:modifyList.get(0).getCompanyUserId());
        if((StringUtil.isNullOrEmpty(oldMemberId) && StringUtil.isNullOrEmpty(modifyMemberId)) || (!StringUtil.isNullOrEmpty(oldMemberId) && !StringUtil.isNullOrEmpty(modifyMemberId) && oldMemberId.equals(modifyMemberId))){
            return;
        }
        boolean isSendMessage = false;
        if(!StringUtil.isNullOrEmpty(modifyMemberId) && !currentCompanyUserId.equals(modifyMemberId)){
            isSendMessage = true;
        }
        if(oldMember==null){
            //新增
            this.saveProjectMember(projectId,companyId,modifyMemberId,null,ProjectMemberType.PROJECT_TASK_RESPONSIBLE,targetId,accountId,isSendMessage,companyId);
        }else {
            //更新
            updateProjectMember(oldMember,modifyMemberId,null,accountId,isSendMessage);
        }
    }

    /**
     * 方法描述：发布任务，处理设计人员
     * 作者：MaoSF
     * 日期：2017/6/22
     */
    private void publishTaskDesign(String projectId,String companyId,String targetId,String companyUserId,String accountId,List<ProjectMemberEntity> memberList,List<ProjectMemberEntity> modifyMemberList) throws Exception{
        //设计
        List<ProjectMemberEntity> oldList = getByMemberType(memberList,ProjectMemberType.PROJECT_DESIGNER);
        List<ProjectMemberEntity> modifyList = getByMemberType(modifyMemberList,ProjectMemberType.PROJECT_DESIGNER);
        publishTaskDesignByMemberType(projectId,companyId,targetId,ProjectMemberType.PROJECT_DESIGNER,companyUserId,accountId,oldList,modifyList);
        //校对
        oldList = getByMemberType(memberList,ProjectMemberType.PROJECT_PROOFREADER);
        modifyList = getByMemberType(modifyMemberList,ProjectMemberType.PROJECT_PROOFREADER);
        publishTaskDesignByMemberType(projectId,companyId,targetId,ProjectMemberType.PROJECT_PROOFREADER,companyUserId,accountId,oldList,modifyList);
        //审核
        oldList = getByMemberType(memberList,ProjectMemberType.PROJECT_AUDITOR);
        modifyList = getByMemberType(modifyMemberList,ProjectMemberType.PROJECT_AUDITOR);
        publishTaskDesignByMemberType(projectId,companyId,targetId,ProjectMemberType.PROJECT_AUDITOR,companyUserId,accountId,oldList,modifyList);
    }

    private List<ProjectMemberEntity> getByMemberType(List<ProjectMemberEntity> memberList,Integer memberType) {
        List<ProjectMemberEntity> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(memberList)) {
            for (ProjectMemberEntity member : memberList) {
                if (member.getMemberType() == memberType) {
                    list.add(member);
                }
            }
        }
        return list;
    }

    private void publishTaskDesignByMemberType(String projectId,String companyId,String targetId,Integer memberType,String currentCompanyUserId,String accountId,List<ProjectMemberEntity> oldList,List<ProjectMemberEntity> modifyList) throws Exception{
        List<ProjectMemberEntity> deleteList = new ArrayList<>();
        List<ProjectMemberEntity> addList = new ArrayList<>();
        if(CollectionUtils.isEmpty(oldList) && CollectionUtils.isEmpty(modifyList)){//不做处理
            return;
        }
        if(CollectionUtils.isEmpty(oldList) && !CollectionUtils.isEmpty(modifyList)) {//全部添加
            addList.addAll(modifyList);
        }
        if(!CollectionUtils.isEmpty(oldList) && CollectionUtils.isEmpty(modifyList)){//全部移除
            deleteList.addAll(oldList);
        }
        if(!CollectionUtils.isEmpty(oldList) && !CollectionUtils.isEmpty(modifyList)) {//两者对比
            for(ProjectMemberEntity m:oldList){
                boolean isDelete = true;
                for(ProjectMemberEntity m2:modifyList){
                    if(m.getCompanyUserId().equals(m2.getCompanyUserId())){
                        isDelete = false;
                        break;
                    }
                }
                if(isDelete){
                    deleteList.add(m);
                }
            }
            for(ProjectMemberEntity m:modifyList){
                boolean isAdd = true;
                for(ProjectMemberEntity m2:oldList){
                    if(m.getCompanyUserId().equals(m2.getCompanyUserId())){
                        isAdd = false;
                        break;
                    }
                }
                if(isAdd){
                    addList.add(m);
                }
            }
        }

        for(ProjectMemberEntity m:deleteList){
            this.projectProcessService.deleteProcessNodeById(m.getTargetId());
            //忽略任务
            myTaskService.ignoreMyTask(m.getTargetId());
            this.deleteProjectMember(projectId,null,memberType,m.getTargetId());
        }
        for(ProjectMemberEntity m:addList){
            boolean isSendMessage = true;
            if(currentCompanyUserId.equals(m.getCompanyUserId())){
                isSendMessage = false;
            }
            //保存设计流程
            String nodeId = this.projectProcessService.saveProjectProcessNode(projectId,memberType,m.getSeq(),targetId,m.getCompanyUserId());
            //保存成员
            this.saveProjectMember(projectId,companyId,m.getCompanyUserId(),null, memberType,nodeId,targetId,m.getSeq(),accountId,isSendMessage,companyId);
        }
    }
}
