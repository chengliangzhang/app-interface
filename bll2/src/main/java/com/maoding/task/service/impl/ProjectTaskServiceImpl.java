package com.maoding.task.service.impl;

import com.maoding.conllaboration.SyncCmd;
import com.maoding.conllaboration.service.CollaborationService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.ProjectMemberType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.BeanUtilsEx;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.dao.ZInfoDAO;
import com.maoding.dynamic.dto.ZTaskDTO;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.dynamic.service.OrgDynamicService;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyDataDTO;
import com.maoding.org.dto.CompanyUserTableDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dao.ProjectProcessDao;
import com.maoding.project.dao.ProjectProcessNodeDao;
import com.maoding.project.dto.ProjectTaskProcessNodeDTO;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.entity.ProjectProcessNodeEntity;
import com.maoding.project.entity.ProjectSkyDriveEntity;
import com.maoding.project.service.*;
import com.maoding.projectcost.dao.ProjectCostDao;
import com.maoding.projectcost.dto.ProjectCostDTO;
import com.maoding.projectcost.entity.ProjectCostEntity;
import com.maoding.projectcost.service.ProjectCostService;
import com.maoding.projectmember.dao.ProjectMemberDao;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.task.dao.ProjectProcessTimeDao;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dao.ProjectTaskRelationDao;
import com.maoding.task.dto.*;
import com.maoding.task.entity.ProjectManagerEntity;
import com.maoding.task.entity.ProjectProcessTimeEntity;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.task.entity.ProjectTaskRelationEntity;
import com.maoding.task.service.ProjectManagerService;
import com.maoding.task.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjecManagerServiceImpl
 * 类描述：项目任务
 * 作    者：MaoSF
 * 日    期：2016年12月28日-下午5:28:54
 */
@Service("projectTaskService")
public class ProjectTaskServiceImpl extends GenericService<ProjectTaskEntity> implements ProjectTaskService {

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private ZInfoDAO zInfoDAO;

    @Autowired
    private ProjectMemberDao projectMemberDao;

    @Autowired
    private ProjectProcessTimeDao projectProcessTimeDao;

    @Autowired
    private ProjectTaskResponsibleService projectTaskResponsibleService;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProjectProcessDao projectProcessDao;

    @Autowired
    private ProjectTaskRelationDao projectTaskRelationDao;

    @Autowired
    private CollaborationService collaborationService;

    @Autowired
    private ProjectProcessService projectProcessService;

    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    @Autowired
    private MyTaskService myTaskService;

    @Autowired
    private MyTaskDao myTaskDao;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private ProjectManagerService projectManagerService;

    @Autowired
    private ProjectProcessNodeDao projectProcessNodeDao;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectCostDao projectCostDao;

    @Autowired
    private ProjectCostService projectCostService;

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private OrgDynamicService orgDynamicService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MessageService messageService;


    @Value("${fastdfs.url}")
    protected String fastdfsUrl;


    @Override
    public int insert(ProjectTaskEntity entity) {
        if (entity.getSeq() == 0) {
            entity.setSeq(this.projectTaskDao.getProjectTaskMaxSeq(entity.getProjectId(), entity.getTaskPid()));
        }
        int i = this.projectTaskDao.insert(entity);
        //处理文件
        if (entity.getTaskType() != 0) {
            projectSkyDriverService.createFileMasterForTask(entity);
        }

        return i;
    }

    @Override
    public int updateById(ProjectTaskEntity entity) {
        int i = this.projectTaskDao.updateById(entity);
        this.updateSkyDriver(entity);
        return i;
    }

    private void updateSkyDriver(ProjectTaskEntity projectTaskEntity) {
        //处理文件
        if (!StringUtil.isNullOrEmpty(projectTaskEntity.getTaskName())) {
            ProjectSkyDriveEntity skyDriveEntity = this.projectSkyDriverService.getSkyDriveByTaskId(projectTaskEntity.getId());
            if (skyDriveEntity != null) {
                skyDriveEntity.setFileName(projectTaskEntity.getTaskName());
                if (!StringUtil.isNullOrEmpty(projectTaskEntity.getCompanyId())) {
                    skyDriveEntity.setCompanyId(projectTaskEntity.getCompanyId());
                }
                this.projectSkyDriverService.updateById(skyDriveEntity);
            } else {
                //为了获取完整的数据。以防创建的文件不准确
                projectTaskEntity = this.projectTaskDao.selectById(projectTaskEntity.getId());
                if (projectTaskEntity.getTaskType() != 0) {
                    this.projectSkyDriverService.createFileMasterForTask(projectTaskEntity);
                }
            }
        }

    }

    /**
     * 方法描述：发布当前记录后，修改该任务下的子集的taskPid，taskPath(重新设置为正式记录的taskPid)
     * param：publishId:被发布记录的ID，taskPid：新的taskPid,新的taskPath=parentPath+id
     * 作者：MaoSF
     * 日期：2017/6/23
     */
    private void updateModifyTaskPid(String publishId, String taskPid, String parentPath) {

        this.projectTaskDao.updateModifyTaskPid(publishId, taskPid, parentPath);
    }

    /**
     * 方法描述：设计阶段数据
     * 作者：MaoSF
     * 日期：2017/6/16
     *
     * @param projectId
     */
    @Override
    public List<ProjectTaskEntity> listProjectTaskContent(String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("taskType", SystemParameters.TASK_TYPE_PHASE);
        return this.projectTaskDao.selectByParam(map);
    }

    /**
     * 方法描述：项目签发数据（项目详情界面，签发组织板块数据）
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectIssueDataDTO> getTaskIssueData(String projectId) throws Exception {
        return projectTaskDao.getTaskIssueData(projectId);
    }

    /**
     * 方法描述：项目任务列表（任务分配界面）
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param:
     * @return:
     */
    @Override
    public ProjectTaskDataDTO getProjectTask(Map<String, Object> map) throws Exception {

        ProjectTaskDataDTO dto = new ProjectTaskDataDTO();
        String projectId = (String) map.get("projectId");
        String companyId = (String) map.get("appOrgId");
        String accountId = (String) map.get("accountId");

        ProjectEntity project = this.projectDao.selectById(projectId);
        //获取当前项目在当前团队的经营负责人和项目负责人
        List<ProjectMemberDTO> managerList = this.projectMemberService.listProjectManager(projectId, companyId);
        for (ProjectMemberDTO managerDTO : managerList) {
            managerDTO.setFlag(this.isProjectManagerEditRole(projectId, companyId, accountId, managerDTO.getMemberType()));
        }

        //其他组织个数
        List<String> companyList = this.projectTaskDao.getOtherPartnerCompany(projectId, companyId);
        if (!CollectionUtils.isEmpty(companyList)) {
            dto.setOtherPartnerCount(companyList.size());
        }

        //获取任务列表
        List<ProjectTaskDTO> taskList = this.getProjectTaskRootData(projectId, companyId);
//        if(companyId.equals(project.getCompanyId())){//如果是项目立项放进来。则查询所有任务的顶级任务数据
//            taskList = this.getProjectTaskRootData(projectId,companyId);
//        }else {//展示签发节点的数据
//            taskList = this.getProjectTaskByCompanyId(projectId,companyId);
//        }

        List<ProjectTaskDTO> taskList2 = new ArrayList<>();//重新组装，过来没有子节点
        for (ProjectTaskDTO dto1 : taskList) {
            List<ProjectTaskEntity> taskEntities = this.projectTaskDao.getProjectTaskByPid2(dto1.getId());
            if (!CollectionUtils.isEmpty(taskEntities)) {
                setTaskState(dto1);
                taskList2.add(dto1);
            }
        }
        dto.setId(projectId);
        dto.setProjectName(project != null ? project.getProjectName() : null);
        dto.setTaskList(taskList2);
        dto.setManagerList(managerList);
        return dto;
    }

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，签发组织板块数据））
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param map
     * @param:
     * @return:
     */
    @Override
    public ProjectTaskDataDTO getProjectTaskDataForOperater(Map<String, Object> map) throws Exception {
        ProjectTaskDataDTO dto = new ProjectTaskDataDTO();
        String projectId = (String) map.get("projectId");
        String companyId = (String) map.get("appOrgId");
        String accountId = (String) map.get("accountId");

        ProjectEntity project = this.projectDao.selectById(projectId);

        //获取当前项目在当前团队的经营负责人和项目负责人
        List<ProjectMemberDTO> managerList = this.projectMemberService.listProjectManager(projectId, companyId);
        for (ProjectMemberDTO managerDTO : managerList) {
            managerDTO.setFlag(this.isProjectManagerEditRole(projectId, companyId, accountId, managerDTO.getMemberType()));
        }

        //其他组织个数
        List<String> companyList = this.projectTaskDao.getOtherPartnerCompany(projectId, companyId);
        if (!CollectionUtils.isEmpty(companyList)) {
            dto.setOtherPartnerCount(companyList.size());
        }

        //获取任务列表
        List<ProjectTaskDTO> taskList = this.getProjectTaskForOperater(projectId, companyId);
//        for(ProjectTaskDTO dto1:taskList){
//            setTaskState(dto1);
//            //设置别名
//            String nikeName = this.companyService.getNickName(dto1.getCompanyId(),companyId);
//            if(!StringUtil.isNullOrEmpty(nikeName)){
//                dto1.setCompanyName(nikeName);
//            }
//        }
        dto.setId(projectId);
        dto.setProjectName(project != null ? project.getProjectName() : null);
        dto.setTaskList(taskList);
        dto.setManagerList(managerList);
        return dto;
    }


//
//    /**
//     * 方法描述：项目编辑权限
//     * 作者：MaoSF
//     * 日期：2017/3/22
//     * @param:
//     * @return:
//     */
//    @Override
//    public int isProjectManagerEditRole(String projectId, String companyId,String accountId, int type) throws Exception{
//
////        if(accountId.equals(project.getCreateBy()) && project.getCompanyId().equals(companyId)){
////            return 1;
////        }
//        ProjectManagerEntity managerEntity = this.projectManagerDao.getProjectOperaterManager(project.getId(), companyId);
//        if (managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)) {
//            return 1;
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        List<CompanyUserTableDTO> companyUserList = null;
//
//        //项目基本信息编辑
//        map.put("permissionId", "50");//企业负责人
//        map.put("companyId", companyId);
//        companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
//        if (!CollectionUtils.isEmpty(companyUserList)) {
//            for(CompanyUserTableDTO dto:companyUserList){
//                if(dto.getUserId().equals(accountId)){
//                    return 1;
//                }
//            }
//        }
//
//        return 0;
//    }

    /**
     * 方法描述：是否可以设计经营负责人和设计负责人权限
     * 作者：MaoSF
     * 日期：2017/4/20
     *
     * @param:type=1:经营负责人权限，type=2:设计负责人权限
     * @return:
     */
    @Override
    public int isProjectManagerEditRole(String projectId, String companyId, String accountId, int type) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (type == 1) {
            map.put("permissionId", "51");//经营负责人
        } else {
            map.put("permissionId", "52");//设计负责人
        }
        map.put("companyId", companyId);
        map.put("userId", accountId);
        List<CompanyUserTableDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
        if (!CollectionUtils.isEmpty(companyUserList)) {
            return 1;
        }
        return 0;
    }

    /**
     * 方法描述：查询项目根任务
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskRootData(String projectId, String companyId) throws Exception {
        List<ProjectTaskDTO> list = this.projectTaskDao.getProjectTaskRootData(projectId);
        //设置负责人
        if (!CollectionUtils.isEmpty(list)) {
            for (ProjectTaskDTO dto : list) {
                ProjectMemberDTO projectMember = this.projectMemberService.getTaskDesignerDTO(dto.getId());
                if (projectMember != null) {
                    dto.setCompanyName(dto.getCompanyName() + "(" + projectMember.getCompanyUserName() + ")");
                }

                this.setTaskState(dto);
            }
        }
        return list;
    }


    /**
     * 方法描述：经营界面任务查询
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskForOperater(String projectId, String companyId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("companyId", companyId);
        ProjectEntity project = this.projectDao.selectById(projectId);
        if (project != null) {
            if (companyId.equals(project.getCompanyBid())) {
                map.put("companyId", project.getCompanyId());//如果是乙方，则展示立项方的数据
            }
        }

        List<ProjectTaskDTO> list = new ArrayList<ProjectTaskDTO>();
        ProjectEntity projectEntity = this.projectDao.selectById(projectId);
        if (projectEntity != null && projectEntity.getCompanyId().equals(companyId)) {
            list = this.projectTaskDao.getProjectTaskRootData(projectId);
        } else {
            list = this.projectTaskDao.getProjectTaskRootOfOperater(map);
        }

        for (ProjectTaskDTO dto1 : list) {
            setTaskState(dto1);
//            String nikeName = this.companyService.getNickName(dto1.getCompanyId(),companyId);
//            if(!StringUtil.isNullOrEmpty(nikeName)){
//                dto1.setCompanyName(nikeName);
//            }
        }

        return list;
    }

    /**
     * 方法描述：经营界面任务详情
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param id
     * @param companyId
     * @param:accountId
     * @return:
     */
    @Override
    public ProjectTaskDetailDTO getProjectTaskDetailForOperater(String id, String companyId, String accountId) throws Exception {
        ProjectTaskDetailDTO detailDTO = new ProjectTaskDetailDTO();
        ProjectTaskDTO dto = this.projectTaskDao.getProjectTaskByIdForOperator(id, companyId);


        if (dto != null) {
            ProjectEntity projectEntity = this.projectDao.selectById(dto.getProjectId());
            //是否是乙方
            if (projectEntity != null && companyId.equals(projectEntity.getCompanyBid())) {
                //查询计划进度时间
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("targetId", dto.getId());
                map.put("type", "2");
                List<ProjectProcessTimeEntity> timeList2 = projectProcessTimeDao.getProjectProcessTime(map);
                if (!CollectionUtils.isEmpty(timeList2)) {
                    dto.setStartTime(timeList2.get(0).getStartTime());
                    dto.setEndTime(timeList2.get(0).getEndTime());
                }
            }

            this.setTaskState(dto);
            BaseDTO.copyFields(dto, detailDTO);
            if (projectEntity != null) {
                detailDTO.setProjectName(projectEntity.getProjectName());
            }
            if (!StringUtil.isNullOrEmpty(dto.getTaskPid())) {
                detailDTO.setTaskParentName(this.projectTaskDao.getTaskParentName(dto.getTaskPid()));
            }


            //设定约定时间
            List<ProjectProcessTimeEntity> timeList = null;
            if (dto.getTaskType() == 1) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("targetId", dto.getId());
                map.put("type", "1");
                timeList = projectProcessTimeDao.getProjectProcessTime(map);

            } else {
                if (null != projectTaskRelationDao.getProjectTaskRelationByTaskId(dto.getId())) {//如果存在转发的记录
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("targetId", dto.getId());
                    map.put("type", "2");
                    map.put("notCompanyId", companyId);
                    timeList = projectProcessTimeDao.getProjectProcessTime(map);
                }
            }
            if (!CollectionUtils.isEmpty(timeList)) {
                detailDTO.setAppointedStartTime(timeList.get(0).getStartTime());
                detailDTO.setAppointedEndTime(timeList.get(0).getEndTime());
            }

            List<ProjectMemberDTO> managerList = null;
            //查询当前任务所属公司的经营负责人
            if (dto.getTaskType() == 1) {
                managerList = this.projectMemberService.listProjectManager(dto.getProjectId(), projectEntity.getCompanyId());
            } else {
                managerList = this.projectMemberService.listProjectManager(dto.getProjectId(), dto.getCompanyId());
            }
            if (!CollectionUtils.isEmpty(managerList)) {
                for (ProjectMemberDTO managerDTO : managerList) {
                    if (managerDTO.getMemberType() == ProjectMemberType.PROJECT_OPERATOR_MANAGER) {
                        detailDTO.setManagerId(managerDTO.getCompanyUserId());
                        detailDTO.setManagerName(managerDTO.getUserName());
                    } else {
                        detailDTO.setDesignerId(managerDTO.getCompanyUserId());
                        detailDTO.setDesignerName(managerDTO.getUserName());
                    }
                }
            }

            //判断是否已经生产
            //查询是否存在流程
            List<ProjectProcessNodeEntity> projectProcessList = projectProcessNodeDao.selectNodeByTaskManageId(id);
            if (!CollectionUtils.isEmpty(projectProcessList)) {
                detailDTO.setIsProduct(1);
            } else {
                //1.查询子任务（生产的任务）
                List<ProjectTaskEntity> taskLeafList = this.projectTaskDao.getProjectTaskByPidOfProductTask(id);
                if (!CollectionUtils.isEmpty(taskLeafList)) {
                    detailDTO.setIsProduct(1);
                }
            }

            List<ProjectTaskEntity> childList = this.projectTaskDao.getProjectTaskByPid2(detailDTO.getId());

            //处理权限
            this.handleTaskDetailRoleFlagForOperate(detailDTO, projectEntity.getCompanyId(), companyId, accountId, dto.getProjectId(), dto.getTaskPath(), childList.size());

            //查询自己相关的所有任务
            List<ProjectTaskDTO> childTask = this.projectTaskDao.getRelationProjectTaskByCompanyOfOperater(dto.getProjectId(), companyId);

            if (!CollectionUtils.isEmpty(childTask)) {
                List<String> taskIdList = new ArrayList<String>();
                for (ProjectTaskDTO child : childTask) {
                    taskIdList.addAll(Arrays.asList(child.getTaskPath().split("-")));
                }
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("taskIdList", taskIdList);
                param.put("taskPid", id);
                param.put("companyId", companyId);
                List<ProjectTaskDTO> childTasks = this.projectTaskDao.getProjectTaskChildOfOperater(param);
                for (ProjectTaskDTO dto1 : childTasks) {
                    this.setTaskState(dto1);
                }
                List<ProjectTaskDetailDTO> list = new ArrayList<>();
                for (ProjectTaskDTO childDTO : childTasks) {
                    this.setTaskState(childDTO);
                    ProjectTaskDetailDTO taskDetailDTO = new ProjectTaskDetailDTO();
                    BaseDTO.copyFields(childDTO, taskDetailDTO);
                    if (!companyId.equals(childDTO.getCompanyId())) {
                        taskDetailDTO.setDepartName("");
                        childDTO.setDepartName("");
                    }
                    //设置别名
//                    String nikeName2 = this.companyService.getNickName(taskDetailDTO.getCompanyId(),companyId);
//                    if(!StringUtil.isNullOrEmpty(nikeName2)){
//                        taskDetailDTO.setCompanyName(nikeName2);
//                    }
                    list.add(taskDetailDTO);
                }
                detailDTO.setChildTaskList(list);
            }
        }
        return detailDTO;
    }


    /**
     * 方法描述：非立项方（项目任务数据）
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskByCompanyId(String projectId, String companyId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("companyId", companyId);
        List<ProjectTaskDTO> list = this.projectTaskDao.getProjectTaskByCompanyId(map);
        //设置负责人
        if (!CollectionUtils.isEmpty(list)) {
            for (ProjectTaskDTO dto : list) {
                ProjectMemberDTO projectMember = this.projectMemberService.getTaskDesignerDTO(dto.getId());
                if (projectMember != null) {
                    dto.setCompanyName(dto.getCompanyName() + "(" + projectMember.getCompanyUserName() + ")");
                }
                this.setTaskState(dto);
            }
        }
        return list;
    }

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，我的任务，设，校，审，定四种任务所在的任务））
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectProcessTaskByCompanyId(String projectId, String companyId, String accountId) throws Exception {
        CompanyUserEntity companyUser = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, companyId);
        if (companyUser != null) {
            List<ProjectTaskDTO> list = this.projectTaskDao.getProjectProcessTaskByCompanyUserId(projectId, companyUser.getId());
            for (ProjectTaskDTO dto : list) {
                ProjectMemberDTO projectMember = this.projectMemberService.getTaskDesignerDTO(dto.getId());
                if (projectMember != null) {
                    dto.setCompanyName(dto.getCompanyName() + "(" + projectMember.getCompanyUserName() + ")");
                }

                this.setTaskState(dto);
            }
            return list;
        }
        return null;
    }

    /**
     * 方法描述：项目任务列表（任务分配界面），1：全部任务，2，我团队的任务，3，我的任务
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:map
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskByType(Map<String, Object> map) throws Exception {
        String projectId = (String) map.get("projectId");
        String companyId = (String) map.get("appOrgId");
        String accountId = (String) map.get("accountId");
        int type = Integer.parseInt((String) map.get("type"));
        ProjectEntity projectEntity = this.projectDao.selectById(projectId);
        if (projectEntity == null) {
            return null;
        }
        if (type == 1) {
            return this.getProjectTaskRootData(projectId, companyId);
        }
        if (type == 2) {//我团队的任务
            if (!companyId.equals(projectEntity.getCompanyId()))//如果是合作团队，则查询自己的
            {
                return this.getProjectTaskByCompanyId(projectId, companyId);
            } else {
                return this.getProjectTaskRootData(projectId, companyId);//如果是立项方，则查询全部
            }
        }
        if (type == 3) {
            return this.getProjectProcessTaskByCompanyId(projectId, companyId, accountId);
        }
        return null;
    }

    @Override
    public ResponseBean getProjectPartner(Map<String, Object> map) throws Exception {
        String companyId = (String) map.get("appOrgId");
        String projectId = (String) map.get("projectId");
        String type = (String) map.get("type");//1.经营界面，2：生产界面
        //返回的数组
        ProjectManagerListDTO dto = new ProjectManagerListDTO();
        ProjectEntity projectEntity = this.projectDao.selectById(projectId);
        if (projectEntity != null) {
            //首先查询立项方
            dto = getProjectManager(projectId, projectEntity.getCompanyId(), companyId);
            dto.setType(1);
            //查询立项方签发给其他组织
            List<ProjectTaskRelationEntity> taskRelationEntityList = this.projectTaskRelationDao.getProjectTaskRelationByFromCompanyId(projectId, projectEntity.getCompanyId());
            if ("2".equals(type)) {
                if (!CollectionUtils.isEmpty(taskRelationEntityList)) {
                    for (ProjectTaskRelationEntity relationEntity : taskRelationEntityList) {
                        ProjectManagerListDTO dto1 = this.getProjectManager(projectId, relationEntity.getToCompanyId(), companyId);
                        dto.getChildList().add(dto1);
                        //查询立项方签发给其他组织
                        List<ProjectTaskRelationEntity> taskRelationEntityList2 = this.projectTaskRelationDao.getProjectTaskRelationByFromCompanyId(projectId, relationEntity.getToCompanyId());
                        if (!CollectionUtils.isEmpty(taskRelationEntityList2)) {
                            for (ProjectTaskRelationEntity relationEntity2 : taskRelationEntityList2) {
                                ProjectManagerListDTO dto2 = this.getProjectManager(projectId, relationEntity2.getToCompanyId(), companyId);
                                dto1.getChildList().add(dto2);
                            }
                        }
                    }
                }
            } else {
                dto = getOperatorCompany(taskRelationEntityList, companyId, projectId, dto);
            }
        }

        return ResponseBean.responseSuccess().addData("partnerList", dto);
    }

    private ProjectManagerListDTO getOperatorCompany(List<ProjectTaskRelationEntity> taskRelationEntityList, String companyId, String projectId, ProjectManagerListDTO dto) throws Exception {
        if (!CollectionUtils.isEmpty(taskRelationEntityList)) {
            for (ProjectTaskRelationEntity relationEntity : taskRelationEntityList) {
                boolean isAdd = false;
                if (relationEntity.getFromCompanyId().equals(companyId) || relationEntity.getToCompanyId().equals(companyId)) {
                    isAdd = true;
                }
                ProjectManagerListDTO dto1 = this.getProjectManager(projectId, relationEntity.getToCompanyId(), companyId);
                //查询立项方签发给其他组织
                List<ProjectTaskRelationEntity> taskRelationEntityList2 = this.projectTaskRelationDao.getProjectTaskRelationByFromCompanyId(projectId, relationEntity.getToCompanyId());
                if (!CollectionUtils.isEmpty(taskRelationEntityList2)) {
                    for (ProjectTaskRelationEntity relationEntity1 : taskRelationEntityList2)
                        if (relationEntity1.getFromCompanyId().equals(companyId) || relationEntity1.getToCompanyId().equals(companyId)) {
                            isAdd = true;
                            break;
                        }
                }
                if (isAdd) {
                    dto.getChildList().add(dto1);
                }
                if (!CollectionUtils.isEmpty(taskRelationEntityList2)) {
                    for (ProjectTaskRelationEntity relationEntity2 : taskRelationEntityList2) {
                        if (relationEntity2.getFromCompanyId().equals(companyId) || relationEntity2.getToCompanyId().equals(companyId)) {
                            ProjectManagerListDTO dto2 = this.getProjectManager(projectId, relationEntity2.getToCompanyId(), companyId);
                            dto1.getChildList().add(dto2);
                        }
                    }
                }
            }
        }
        return dto;
    }


    private ProjectManagerListDTO getProjectManager(String projectId, String companyId, String currentCompanyId) throws Exception {
        ProjectManagerListDTO dto = new ProjectManagerListDTO();
        dto.setCompanyId(companyId);
        //查询立项方人员
        CompanyEntity companyEntity = this.companyDao.selectById(companyId);
        if (companyEntity != null) {
            //获取当前项目在当前团队的经营负责人和项目负责人
            List<ProjectMemberDTO> managerList = this.projectMemberService.listProjectManager(projectId, companyId);
            dto.setCompanyName(companyEntity.getAliasName());
            dto.setManagerList(managerList);
        }
        return dto;
    }

    private ResponseBean validateSaveProjectTask(SaveProjectTaskDTO dto) {
        if (StringUtil.isNullOrEmpty(dto.getTaskName())) {
            return ResponseBean.responseError("任务名称不能为空");
        }

        if (dto.getTaskType() != 1) {//如果是跟任务，怎不验证pid
            if (StringUtil.isNullOrEmpty(dto.getTaskPid())) {
                return ResponseBean.responseError("父任务id不能为空");
            }
        }

        //去除重名的问题
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            ProjectTaskEntity projectTaskEntity = this.projectTaskDao.getProjectTaskByPidAndTaskName(dto.getProjectId(), dto.getTaskPid(), dto.getTaskName());
            if (projectTaskEntity != null) {
                return ResponseBean.responseError(dto.getTaskName() + "已存在");
            }
        } else {
            if (!StringUtil.isNullOrEmpty(dto.getTaskName())) {
                ProjectTaskEntity projectTask = this.projectTaskDao.selectById(dto.getId());
                if (projectTask != null) {
                    ProjectTaskEntity projectTaskEntity = this.projectTaskDao.getProjectTaskByPidAndTaskName(projectTask.getProjectId(), projectTask.getTaskPid(), dto.getTaskName());
                    if (projectTaskEntity != null && !projectTask.getId().equals(projectTaskEntity.getId()) && !projectTaskEntity.getId().equals(projectTask.getBeModifyId())) {
                        return ResponseBean.responseError(dto.getTaskName() + "已存在");
                    }
                }
            }
        }

        return null;
    }

    /**
     * 方法描述：生产任务新增、修改
     * 作者：MaoSF
     * 日期：2017/2/24
     */
    public ResponseBean saveNotPublishProductionTask(SaveProjectTaskDTO dto) throws Exception {
        //验证
        ResponseBean responseBean = this.validateSaveProjectTask(dto);
        if (responseBean != null) {
            return responseBean;
        }
        ProjectTaskEntity entity = new ProjectTaskEntity();
        BaseDTO.copyFields(dto, entity);
        //新增
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            entity.setId(StringUtil.buildUUID());
            entity.setTaskStatus("0");
            entity.setCreateBy(dto.getAccountId());

            //查询父任务
            ProjectTaskEntity parentTask = this.projectTaskDao.selectById(dto.getTaskPid());
            if (parentTask != null) {
                //如果是被修改的记录的ID，并且存在正式数据。则新增的数据的父ID应该为正式记录的ID
                if (parentTask.getTaskType() == SystemParameters.TASK_PRODUCT_TYPE_MODIFY && !StringUtil.isNullOrEmpty(parentTask.getBeModifyId())) {
                    ProjectTaskEntity beModifyTask = this.projectTaskDao.selectById(parentTask.getBeModifyId());
                    entity.setTaskPid(beModifyTask.getId());
                    entity.setTaskPath(beModifyTask.getTaskPath() + entity.getId());
                } else {
                    entity.setTaskPath(parentTask.getTaskPath() + "-" + entity.getId());
                }
                entity.setTaskLevel(parentTask.getTaskLevel() + 1);

                //自己组织签发给部门后 生产安排分解任务需要带部门
                if (StringUtil.isNullOrEmpty(dto.getOrgId()) && !StringUtil.isNullOrEmpty(parentTask.getOrgId())) {
                    entity.setOrgId(parentTask.getOrgId());
                }
            } else {
                entity.setTaskLevel(1);
                entity.setTaskPath(entity.getId());
            }

            //负责人
            String targetId = dto.getDesignerId();
            //保存任务负责人
            if (!StringUtil.isNullOrEmpty(targetId)) {
                this.saveProjectTaskResponsibler(dto, entity.getId(), targetId, true);
            }
            //处理时间
            ProjectProcessTimeEntity time = this.saveProjectProcessTime(dto, entity.getId(), false);
            if(time!=null){
                entity.setStartTime(time.getStartTime());
                entity.setEndTime(time.getEndTime());
            }
            entity.setTaskType(SystemParameters.TASK_PRODUCT_TYPE_MODIFY);
            entity.setTaskStatus(SystemParameters.TASK_STATUS_MODIFIED);
            entity.setCompanyId(dto.getAppOrgId());
            this.insert(entity);
        } else {
            //此处需要修改
            this.updateProjectTaskForNotPublish(dto);
        }
        //返回信息
        return ResponseBean.responseSuccess("保存成功");
    }

    /**
     * 方法描述：生产任务分解
     * 作者：CHENZHUJIE
     * 日期：2017/2/24
     */
    private ResponseBean saveProductionTask(SaveProjectTaskDTO dto) throws Exception {
        //验证
        ResponseBean responseBean = this.validateSaveProjectTask(dto);
        if (responseBean != null) {
            return responseBean;
        }
        ProjectTaskEntity entity = new ProjectTaskEntity();
        BaseDTO.copyFields(dto, entity);
        //新增
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            entity.setId(StringUtil.buildUUID());
            entity.setTaskStatus("0");
            entity.setCreateBy(dto.getAccountId());

            //处理父节点上的流程
            //this.handleProcessForTaskProduct(dto.getTaskPid(),dto.getId());
            //查询父任务
            ProjectTaskEntity parentTask = this.projectTaskDao.selectById(dto.getTaskPid());
            if (parentTask != null) {
                entity.setTaskLevel(parentTask.getTaskLevel() + 1);
                entity.setTaskPath(parentTask.getTaskPath() + "-" + entity.getId());
                //自己组织签T发给部门后 生产安排分解任务需要带部门
                if (StringUtil.isNullOrEmpty(dto.getOrgId()) && !StringUtil.isNullOrEmpty(parentTask.getOrgId())) {
                    entity.setOrgId(parentTask.getOrgId());
                }
            } else {
                entity.setTaskLevel(1);
                entity.setTaskPath(entity.getId());
            }

            entity.setCompanyId(dto.getAppOrgId());
            this.insert(entity);

            //添加项目动态
            dynamicService.addDynamic(null, entity, dto.getCurrentCompanyId(), dto.getAccountId());

            //负责人
            String targetId = dto.getDesignerId();
            //任务负责人,不存在，则默认是父任务的负责人
            if (StringUtil.isNullOrEmpty(dto.getDesignerId())) {
                ProjectMemberDTO projectMember = this.projectMemberService.getTaskDesignerDTO(dto.getTaskPid());
                if (projectMember != null) {
                    targetId = projectMember.getCompanyUserId();
                }
            }
            //保存任务负责人
            if (!StringUtil.isNullOrEmpty(targetId)) {
                this.saveProjectTaskResponsibler(dto, entity.getId(), targetId, false);
            }
            //处理时间
            this.saveProjectProcessTime(dto, entity.getId(), true);
        } else {
            this.updateProjectTask(dto);
        }
        //返回信息
        return ResponseBean.responseSuccess("保存成功").addData("projectTask", dto);
    }


    private ResponseBean saveOperaterTask(SaveProjectTaskDTO dto) throws Exception {
        //验证
        ResponseBean responseBean = this.validateSaveProjectTask(dto);
        if (responseBean != null) {
            return responseBean;
        }
        ProjectTaskEntity entity = new ProjectTaskEntity();
        BaseDTO.copyFields(dto, entity);
        //新增
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            String rootId = null;
            entity.setId(StringUtil.buildUUID());
            entity.setCreateBy(dto.getAccountId());

            //查询父任务
            ProjectTaskEntity parentTask = this.projectTaskDao.selectById(dto.getTaskPid());
            if (parentTask != null) {//此处任务必须不为null
                entity.setTaskLevel(parentTask.getTaskLevel() + 1);
                entity.setTaskPath(parentTask.getTaskPath() + "-" + entity.getId());
            } else {
                return ResponseBean.responseError("任务分解失败");
            }

            //保存分解的任务
            this.insert(entity);

            //处理签发关系
            this.handleProjectManager(entity, dto.getAppOrgId(), dto.getManagerId(), dto.getDesignerId(), dto.getAccountId());

            //处理时间
            this.saveProjectProcessTime(dto, entity.getId(), true);
        } else {
            this.updateProjectTask(dto);
        }
        //返回信息
        return ResponseBean.responseSuccess("保存成功").addData("projectTask", dto);
    }


    private void handleProjectManager(ProjectTaskEntity task, String currentCompanyId, String managerId, String designerId, String accountId) throws Exception {
        String appOrgId = currentCompanyId;
        if (appOrgId.equals(task.getCompanyId())) {//如果签发的是给自己的团队
            //给设计负责人发送安排任务负责人的任务
            this.sendTaskToDesigner(task.getProjectId(), task.getId(), task.getCompanyId(), currentCompanyId, accountId);
        } else {//如果签发给其他团队
            //查询是否存在已签发给此公司
            //设计经营负责人
            this.saveProjectManager(task.getProjectId(), task.getId(), task.getCompanyId(), managerId, designerId, accountId);
            //保存
            this.saveProjectTaskRelation2(task, currentCompanyId, accountId);
        }
    }

    private void saveProjectManager(String projectId, String taskId, String companyId, String managerId, String designerId, String accountId) throws Exception {

        ProjectManagerEntity entity = new ProjectManagerEntity();
        entity.setCompanyId(companyId);
        entity.setProjectId(projectId);
        entity.setCreateBy(accountId);

        ProjectMemberEntity managerEntity = this.projectMemberService.getOperatorManager(projectId, companyId);
        if (managerEntity == null) {
            this.projectMemberService.saveProjectMember(projectId, companyId, managerId, ProjectMemberType.PROJECT_OPERATOR_MANAGER, accountId, false);
        } else {
            MessageEntity msg = getMessage(projectId, taskId, companyId, managerId);
            messageService.sendMessage(msg);
        }

        //查询签发给的公司是否存在签发的记录
        List<ProjectTaskRelationEntity> taskRelationList = this.projectTaskRelationDao.getProjectTaskRelationByCompanyId(projectId, companyId);
        if (CollectionUtils.isEmpty(taskRelationList)) {//若果不存在签发的记录，则推送签发的任务
            // 发送任务
            this.myTaskService.saveMyTask(taskId, SystemParameters.ISSUE_TASK, companyId, managerId);
        }
    }

    /**
     * 方法描述：给设计负责人推送任务
     * 作者：MaoSF
     * 日期：2017/4/20
     *
     * @param:
     * @return:
     */
    private void sendTaskToDesigner(String projectId, String taskId, String companyId, String currentCompanyId, String accountId) throws Exception {
        ProjectMemberEntity designerEntity = this.projectMemberService.getDesignManager(projectId, companyId);
        if (designerEntity != null) {
            //如果不为空，则设置任务负责人，默认为设计负责人
            this.projectTaskResponsibleService.insertTaskResponsible(projectId, companyId, designerEntity.getCompanyUserId(), taskId, accountId, currentCompanyId);
        }
    }

    /**
     * 方法描述：在签发任务时设置设计负责人并通知设计负责人
     * 作者：ZCL
     */
    private void notifyIssuedDesigner(String projectId, String taskId, String companyId) throws Exception {
        ProjectMemberEntity designerEntity = this.projectMemberService.getDesignManager(projectId, companyId);
        if (designerEntity == null) {
            //签发到的公司中选择具备经营权限的人员中选择第一个填入项目经营负责人位置
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("permissionId", "52");//设计总监权限id
            map.put("companyId", companyId);
            List<CompanyUserTableDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
            if (companyUserList.size() > 0) {
                CompanyUserTableDTO u = companyUserList.get(0);
                this.projectMemberService.saveProjectMember(projectId, companyId, u.getId(), ProjectMemberType.PROJECT_DESIGNER_MANAGER, null, true);
            }
        }
    }

    private MessageEntity getMessage(String projectId, String taskId, String companyId, String managerId) {
        MessageEntity msg = new MessageEntity();
        msg.setProjectId(projectId);
        msg.setCompanyId(companyId);
        msg.setTargetId(taskId);
        msg.setUserId(managerId);
        msg.setMessageType(SystemParameters.MESSAGE_TYPE_5);
        return msg;
    }


    /**
     * 方法描述：保存费用
     * 作者：MaoSF
     * 日期：2017/3/7
     *
     * @param:
     * @return:
     */
    private void saveProjectCost(ProjectTaskEntity task, String currentCompanyId) throws Exception {

        //查询是否存在总金额

        //查询总费用
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", task.getProjectId());
        map.put("fromCompanyId", currentCompanyId);
        map.put("toCompanyId", task.getCompanyId());
        map.put("type", "3");
        map.put("flag", "1");
        List<ProjectCostDTO> totalCost = this.projectCostDao.selectByParam(map);//理论上只会存在一条
        String costId = null;
        if (!CollectionUtils.isEmpty(totalCost)) {
            costId = totalCost.get(0).getId();
        } else {
            costId = StringUtil.buildUUID();
            ProjectCostEntity costEntity = new ProjectCostEntity();
            costEntity.setId(costId);
            costEntity.setProjectId(task.getProjectId());
            costEntity.setFromCompanyId(currentCompanyId);
            costEntity.setToCompanyId(task.getCompanyId());
            costEntity.setType("3");
            costEntity.setFlag("1");
            this.projectCostDao.insert(costEntity);
        }
    }

    private void updateProjectTask(SaveProjectTaskDTO dto) throws Exception {

        ProjectTaskEntity taskEntity = projectTaskDao.selectById(dto.getId());
        //保存修改前的对象
        ProjectTaskEntity oldTask = new ProjectTaskEntity();
        BeanUtilsEx.copyProperties(taskEntity, oldTask);

        if (StringUtil.isNullOrEmpty(dto.getTaskName())) {
            dto.setTaskName(taskEntity.getTaskName());
        }

        if (!taskEntity.getCompanyId().equals(dto.getCompanyId())) {//如果更改了组织
            //删除任务（包含子任务）
            this.deleteProjectTask(taskEntity, dto.getAccountId());

            //把部门设置为null
            this.projectTaskDao.updateTaskOrgId(taskEntity.getId());
            //更新
            BaseDTO.copyFields(dto, taskEntity);
            taskEntity.setTaskStatus("0"); //再更新（由于上面taskStatus设置为1了，所以，此处设置为0.更新为有效）
            taskEntity.setCompanyId(dto.getCompanyId());
            this.updateById(taskEntity);

            //保存项目动态
            dynamicService.addDynamic(oldTask, taskEntity, dto.getCompanyId(), dto.getAccountId());

            if (!dto.getCompanyId().equals(dto.getAppOrgId())) {
                if (!StringUtil.isNullOrEmpty(dto.getManagerId()))
                //设计经营负责人
                {
                    this.saveProjectManager(dto.getProjectId(), dto.getId(), dto.getCompanyId(), dto.getManagerId(), dto.getDesignerId(), dto.getAccountId());
                }
                this.saveProjectTaskRelation2(taskEntity, dto.getAppOrgId(), dto.getAccountId());
            } else {

                this.sendTaskToDesigner(dto.getProjectId(), dto.getId(), dto.getCompanyId(), dto.getAppOrgId(), dto.getAccountId());
            }

        } else {
            ProjectMemberDTO projectMember = this.projectMemberService.getTaskDesignerDTO(dto.getId());
            if (projectMember != null) {
                if (!projectMember.getCompanyUserId().equals(dto.getDesignerId())) {//说明更改了任务负责人
                    //保存任务负责人
                    this.saveProjectTaskResponsibler(dto, dto.getId(), dto.getDesignerId(), false);
                    //忽略原有任务负责人的任务
                    this.myTaskService.ignoreMyTask(taskEntity.getId(), SystemParameters.PRODUCT_TASK_RESPONSE, projectMember.getCompanyUserId());
                }
            }
            //更新
            BaseDTO.copyFields(dto, taskEntity);
            taskEntity.setTaskStatus("0"); //再更新（由于上面taskStatus设置为1了，所以，此处设置为0.更新为有效）
            taskEntity.setCompanyId(dto.getCompanyId());
            taskEntity.setTaskName(dto.getTaskName());
            this.updateById(taskEntity);

            //保存项目动态
            dynamicService.addDynamic(oldTask, taskEntity, dto.getCompanyId(), dto.getAccountId());
        }

        //处理时间
        if (!StringUtil.isNullOrEmpty(dto.getStartTime())) {
            this.saveProjectProcessTime(dto, dto.getId(), true);
        }

    }

    private ResponseBean updateProjectTaskForNotPublish(SaveProjectTaskDTO dto) throws Exception {
        ProjectTaskEntity taskEntity = projectTaskDao.selectById(dto.getId());
        if(taskEntity==null){
            return ResponseBean.responseError("操作失败");
        }
        String taskId = dto.getId();
        String beModifyId =StringUtil.isNullOrEmpty(taskEntity.getBeModifyId())?taskEntity.getId():taskEntity.getBeModifyId();
        if(taskEntity.getTaskType()!= SystemParameters.TASK_PRODUCT_TYPE_MODIFY){//则新增一条被修改的记录
            taskId = this.copyProjectTask(new SaveProjectTaskDTO(),taskEntity);
        }

        ProjectMemberDTO projectMember = this.projectMemberService.getTaskDesignerDTO(dto.getId());
        if (projectMember != null) {
            if (!projectMember.getCompanyUserId().equals(dto.getDesignerId())) {//说明更改了任务负责人
                //保存任务负责人
                this.saveProjectTaskResponsibler(dto,taskId, dto.getDesignerId(), false);
                //忽略原有任务负责人的任务
                this.myTaskService.ignoreMyTask(beModifyId, SystemParameters.PRODUCT_TASK_RESPONSE, projectMember.getCompanyUserId());
            }
        }

        if(!(StringUtil.isNullOrEmpty(dto.getTaskName()) || dto.getTaskName().equals(taskEntity.getTaskName()))){
            taskEntity.setTaskName(dto.getTaskName());
            this.updateById(taskEntity);
        }
        //更新
        ProjectTaskEntity beModityEntity = new ProjectTaskEntity();
        BaseDTO.copyFields(dto, beModityEntity);
        taskEntity.setId(taskId);
        taskEntity.setTaskPid(null);
        taskEntity.setTaskPath(null);
        if(StringUtil.isNullOrEmpty(dto.getStartTime())){
            taskEntity.setTaskStatus(SystemParameters.TASK_STATUS_VALID); //如果没有更新时间，则设置为已发布
        }else {
            taskEntity.setTaskStatus(SystemParameters.TASK_STATUS_MODIFIED); //如果没有更新时间，则设置为已发布
        }
        this.updateById(taskEntity);

        //保存项目动态
     //   dynamicService.addDynamic(oldTask, taskEntity, dto.getCompanyId(), dto.getAccountId());

        //处理时间
        if (!StringUtil.isNullOrEmpty(dto.getStartTime())) {
            this.saveProjectProcessTime(dto, taskId, false);
        }

        return ResponseBean.responseSuccess("操作成功");
    }


    private void saveProjectTaskResponsibler(SaveProjectTaskDTO dto, String id, String targetId, boolean isModify) throws Exception {
        if (isModify) {
            this.projectTaskResponsibleService.insertTaskResponsible(dto.getProjectId(), dto.getCompanyId(), targetId, id, dto.getAccountId());
        } else {
            this.projectTaskResponsibleService.insertTaskResponsible(dto.getProjectId(), dto.getCompanyId(), targetId, id, dto.getAccountId(), dto.getAppOrgId());
        }
    }

    /**
     * 方法描述：保存签发团队关系
     * 作者：MaoSF
     * 日期：2017/1/6
     *
     * @param:
     * @return:
     */
    private void saveProjectTaskRelation(SaveProjectTaskDTO dto, String id) throws Exception {
        ProjectTaskRelationEntity relationEntity = new ProjectTaskRelationEntity();
        relationEntity.setId(StringUtil.buildUUID());
        relationEntity.setFromCompanyId(dto.getAppOrgId());
        relationEntity.setToCompanyId(dto.getManagerId());
        relationEntity.setTaskId(id);
        relationEntity.setCreateBy(dto.getAccountId());
        this.projectTaskRelationDao.insert(relationEntity);

        //发送任务
        //则发送给所有具有经营权限的人员
        //this.myTaskService.saveMyTask(id, SystemParameters.CLAIM_TASK,dto.getManagerId());

        //发送通知
        List<String> companyList = new ArrayList<String>();
        companyList.add(dto.getManagerId());
        this.projectService.sendMsgToRelationCompanyUser(companyList);
    }

    /**
     * 方法描述：保存任务计划时间
     * 作者：MaoSF
     * 日期：2017/1/6
     *
     * @param:
     * @return:
     */
    private ProjectProcessTimeEntity saveProjectProcessTime(SaveProjectTaskDTO dto, String id, boolean isPublish) throws Exception {
        if (!StringUtil.isNullOrEmpty(dto.getStartTime())) {
            ProjectProcessTimeEntity projectProcessTimeEntity = new ProjectProcessTimeEntity();
            projectProcessTimeEntity.setId(StringUtil.buildUUID());
            projectProcessTimeEntity.setTargetId(id);
            projectProcessTimeEntity.setCompanyId(dto.getAppOrgId());//存储当前公司id
            projectProcessTimeEntity.setType(isPublish ? SystemParameters.PROCESS_TYPE_PLAN : SystemParameters.PROCESS_TYPE_NOT_PUBLISH);
            projectProcessTimeEntity.setStartTime(dto.getStartTime());
            projectProcessTimeEntity.setEndTime(dto.getEndTime());
            projectProcessTimeEntity.setCreateBy(dto.getAccountId());
            this.projectProcessTimeDao.insert(projectProcessTimeEntity);
            return projectProcessTimeEntity;
        } else {
            //把父级的默认过来,复制一份给子任务
            if (!StringUtil.isNullOrEmpty(dto.getTaskPid())) {
                Map<String, Object> map = new HashMap<>();
                map.put("targetId", dto.getTaskPid());
                List<ProjectProcessTimeEntity> processTimeList = this.projectProcessTimeDao.getProjectProcessTime(map);
                if (!CollectionUtils.isEmpty(processTimeList)) {
                    ProjectProcessTimeEntity processTime = processTimeList.get(0);
                    processTime.setId(StringUtil.buildUUID());
                    processTime.setTargetId(id);
                    processTime.setType(isPublish ? SystemParameters.PROCESS_TYPE_PLAN : SystemParameters.PROCESS_TYPE_NOT_PUBLISH);
                    this.projectProcessTimeDao.insert(processTime);
                    return processTime;
                }
            }
        }
        return null;
    }

    private ResponseBean saveIssueTask(SaveProjectTaskDTO dto) throws Exception {
        //验证
        ResponseBean result = validateSaveProjectTask(dto);
        if (result != null) return result;
        int rd = 0;
        if (dto.getId() == null) {
            rd = saveNewIssueTask(dto, null);
        } else {
            ProjectTaskEntity entity = projectTaskDao.selectById(dto.getId());
            String dataCompanyId = entity.getCompanyId();//先保存到dataCompanyId，防止copy对象的时候，设置为null了
            if (entity == null) return ResponseBean.responseError("操作失败");
            if (entity.getTaskType() == SystemParameters.TASK_TYPE_ISSUE) {
                rd = saveNewIssueTask(dto, entity);
            } else {
                String tempCompanyId = entity.getCompanyId();
                BaseDTO.copyFields(dto, entity);
                if (!StringUtil.isNullOrEmpty(dto.getCompanyId()) && !dto.getCompanyId().equals(tempCompanyId)) {
                    //把部门设置为null
                    this.projectTaskDao.updateTaskOrgId(entity.getId());
                }
                entity.setTaskStatus(SystemParameters.TASK_STATUS_MODIFIED);
                rd = projectTaskDao.updateById(entity);

                if ((dto.getStartTime() != null) || (dto.getEndTime() != null)) {
                    if (StringUtil.isNullOrEmpty(entity.getCompanyId())) {//防止前端没有传递companyId
                        entity.setCompanyId(dataCompanyId);
                    }
                    rd = saveTaskTime(entity, dto.getStartTime(), dto.getEndTime(), dto.getMemo(), SystemParameters.PROCESS_TYPE_NOT_PUBLISH);
                }
            }
        }

        if (dto.getIsPublish() == 1) {//如果是发布
            List<String> idList = new ArrayList<>();
            idList.add(dto.getId());
            return this.publishIssueTask(idList, dto.getAccountId(), dto.getAppOrgId());
        }

        return ResponseBean.responseSuccess("操作成功");
    }

    private int saveNewIssueTask(SaveProjectTaskDTO dto, ProjectTaskEntity issuedEntity) throws Exception {
        int level = 0;
        String path = "";

        ProjectTaskEntity entity = new ProjectTaskEntity();
        BeanUtilsEx.copyProperties(dto, entity);
        entity.setCompanyId(dto.getCompanyId());
        entity.setId(StringUtil.buildUUID());
        dto.setId(entity.getId());//用于保存并发布，返回id
        if (issuedEntity != null) {
            entity.setBeModifyId(issuedEntity.getId());
            entity.setTaskName(StringUtil.isNullOrEmpty(dto.getTaskName()) ? issuedEntity.getTaskName() : dto.getTaskName());
            entity.setTaskRemark(StringUtil.isNullOrEmpty(dto.getTaskRemark()) ? issuedEntity.getTaskRemark() : dto.getTaskRemark());
            entity.setOrgId(StringUtil.isNullOrEmpty(dto.getOrgId()) ? issuedEntity.getOrgId() : dto.getOrgId());
            entity.setCompanyId(StringUtil.isNullOrEmpty(dto.getCompanyId()) ? issuedEntity.getCompanyId() : dto.getCompanyId());
            entity.setProjectId(StringUtil.isNullOrEmpty(dto.getProjectId()) ? issuedEntity.getProjectId() : dto.getProjectId());
            entity.setTaskPid(StringUtil.isNullOrEmpty(dto.getTaskPid()) ? issuedEntity.getTaskPid() : dto.getTaskPid());
            entity.setStartTime(StringUtil.isNullOrEmpty(dto.getStartTime())?issuedEntity.getStartTime():dto.getStartTime());
            entity.setEndTime(StringUtil.isNullOrEmpty(dto.getEndTime())?issuedEntity.getEndTime():dto.getEndTime());
            //复制及添加时间
            Map<String, Object> query = new HashMap<>();
            query.put("targetId", issuedEntity.getId());
            List<ProjectProcessTimeEntity> tps = projectProcessTimeDao.getProjectProcessTime(query);
            for (ProjectProcessTimeEntity t : tps) {
                saveTaskTime(entity, t.getStartTime(), t.getEndTime(), t.getMemo(), SystemParameters.PROCESS_TYPE_PLAN);
            }
            if ((dto.getStartTime() != null) || (dto.getEndTime() != null)) {
                int rd = saveTaskTime(entity, dto.getStartTime(), dto.getEndTime(), dto.getMemo(), SystemParameters.PROCESS_TYPE_NOT_PUBLISH);
                if (rd < 1) return -1;
            }
        } else if ((dto.getStartTime() == null) && (dto.getEndTime() == null)) {
            //添加默认时间
            Map<String, Object> query = new HashMap<>();
            query.put("targetId", entity.getTaskPid());
            List<ProjectProcessTimeEntity> processList = projectProcessTimeDao.getProjectProcessTime(query);
            if (CollectionUtils.isEmpty(processList)) {
                ProjectProcessTimeEntity tp = processList.get(0);//获取最新记录，设置未默认值
                saveTaskTime(entity, tp.getStartTime(), tp.getEndTime(), tp.getMemo(), SystemParameters.PROCESS_TYPE_NOT_PUBLISH);
                entity.setStartTime(tp.getStartTime());
                entity.setEndTime(tp.getEndTime());
            }
        } else {
            //添加指定的时间
            saveTaskTime(entity, dto.getStartTime(), dto.getEndTime(), dto.getMemo(), SystemParameters.PROCESS_TYPE_NOT_PUBLISH);
            entity.setStartTime(dto.getStartTime());
            entity.setEndTime(dto.getEndTime());
        }

        //添加任务
        ProjectTaskEntity parentTask = projectTaskDao.selectById(entity.getTaskPid());
        if (parentTask != null) {
            level = parentTask.getTaskLevel();
            path = parentTask.getTaskPath() + "-";
        }

        entity.setCreateBy(dto.getAccountId());
        entity.setTaskLevel(level + 1);
        entity.setTaskPath(path + entity.getId());
        entity.setTaskStatus(SystemParameters.TASK_STATUS_MODIFIED);
        entity.setTaskType(SystemParameters.TASK_TYPE_MODIFY);
        if (dto.getTaskType() == SystemParameters.TASK_PRODUCT_TYPE_MODIFY) {
            entity.setTaskType(SystemParameters.TASK_PRODUCT_TYPE_MODIFY);
        } //返回新增实体的id，用于调用方法（copyProjectTask）中使用
        dto.setId(entity.getId());
        return projectTaskDao.insert(entity);
    }

    private int saveTaskTime(ProjectTaskEntity task, String startTime, String endTime, String memo, Integer timeType) throws Exception {
        ProjectProcessTimeEntity entity = new ProjectProcessTimeEntity();
        entity.setId(StringUtil.buildUUID());
        entity.setTargetId(task.getId());
        entity.setCompanyId(task.getCompanyId());
        entity.setType(timeType);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setCreateBy(task.getCreateBy());
        entity.setMemo(memo);
        return projectProcessTimeDao.insert(entity);
    }

    /**
     * 方法描述：删除任务
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param task
     * @param accountId
     * @param:
     * @return:
     */
    public ResponseBean deleteProjectTask(ProjectTaskEntity task, String accountId) throws Exception {
        String id = task.getId();
        if (task.getBeModifyId() != null) {
            id = task.getBeModifyId();
        }
        //更改签发的数据
        List<ProjectTaskEntity> list = this.projectTaskDao.getProjectTaskByTaskPath(id);//查询当前节点下面所有的子任务（包含自己）

        //把设计阶段下面的所以任务都设置为无效
        ProjectTaskEntity projectTaskEntity = new ProjectTaskEntity();
        projectTaskEntity.setTaskPath(id);
        projectTaskEntity.setTaskStatus("1");
        projectTaskEntity.setUpdateBy(accountId);
        projectTaskDao.updateProjectTaskStatus(projectTaskEntity);

        //删除计划时间
        this.projectProcessTimeDao.deleteByTargetId(id);

        //删除负责人
        this.projectMemberService.deleteProjectMember(ProjectMemberType.PROJECT_TASK_RESPONSIBLE, id);

        List<String> taskIdList = new ArrayList<String>();
        List<String> companyList = new ArrayList<String>();//记录公司id
        //签发的记录（用来保存被删除记录的签发记录，最后匹配是否还存在其他签发（同项目，同A--B的签发记录，用于判断是否删除合作设计费））
        List<ProjectTaskRelationEntity> taskRelationList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (ProjectTaskEntity taskEntity : list) {
                taskIdList.add(taskEntity.getId());
                companyList.add(taskEntity.getCompanyId());
                //忽略任务
                this.myTaskService.ignoreMyTask(taskEntity.getId());
                //删除流程
                this.projectProcessService.deleteProcessByTaskId(taskEntity.getId());

                //查询签发数据
                ProjectTaskRelationEntity taskRelation = this.projectTaskRelationDao.getProjectTaskRelationByTaskId(taskEntity.getId());
                if (taskRelation != null) {
                    taskRelationList.add(taskRelation);
                }

                //负责人移除群
                if (!id.equals(taskEntity.getId())) {
                    this.projectMemberService.deleteProjectMember(ProjectMemberType.PROJECT_TASK_RESPONSIBLE, taskEntity.getId());
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("relationStatus", "1");
            map.put("taskIdList", taskIdList);
            this.projectTaskRelationDao.updateTaskRelationStatus(map);


            ProjectEntity projectEntity = projectDao.selectById(task.getProjectId());
            if (projectEntity != null && !StringUtil.isNullOrEmpty(task.getCompanyId()) && !projectEntity.getCompanyId().equals(task.getCompanyId()) && !task.getCompanyId().equals(projectEntity.getCompanyBid())) {
                //处理经营负责人和设计负责人
                List<ProjectTaskRelationEntity> relationEntities = this.projectTaskRelationDao.getProjectTaskRelationByCompanyId(task.getProjectId(), task.getCompanyId());
                if (CollectionUtils.isEmpty(relationEntities)) {
                    this.projectManagerService.deleteProjectManage(task.getProjectId(), task.getCompanyId());
                }
            }

            //删除费用节点
            this.handleProjectTaskRelationForCooperatorFee(projectEntity, taskRelationList, accountId);
        }

        //删除文档库
        this.projectSkyDriverService.updateSkyDriveStatus(id, accountId);

        //删除消息
        this.messageService.deleteMessage(id);

        if (!CollectionUtils.isEmpty(companyList)) {
            //发送通知
            this.projectService.sendMsgToRelationCompanyUser(companyList);
        }

        if (!task.getId().equals(id)) {
            deleteIssueEditTask(task.getId(), accountId);
        }
        //返回信息
        return ResponseBean.responseSuccess("删除成功");
    }

    private void deleteIssueEditTask(String id, String accountId) {
        //删除任务
        ProjectTaskEntity projectTaskEntity = new ProjectTaskEntity();
        projectTaskEntity.setTaskPath(id);
        projectTaskEntity.setTaskStatus("1");
        projectTaskEntity.setUpdateBy(accountId);
        projectTaskDao.updateProjectTaskStatus(projectTaskEntity);

        //删除计划时间
        this.projectProcessTimeDao.deleteByTargetId(id);
    }


    /**
     * 方法描述：删除任务的时候，处理合作设计费
     * 作者：MaoSF
     * 日期：2017/5/3
     *
     * @param:
     * @return:
     */
    private void handleProjectTaskRelationForCooperatorFee(ProjectEntity projectEntity, List<ProjectTaskRelationEntity> taskRelationList, String accountId) throws Exception {

        for (ProjectTaskRelationEntity taskRelationEntity : taskRelationList) {
            //查询签发记录
            Map<String, Object> stringMap = new HashMap<>();
            stringMap.put("fromCompanyId", taskRelationEntity.getFromCompanyId());
            stringMap.put("toCompanyId", taskRelationEntity.getToCompanyId());
            stringMap.put("projectId", projectEntity.getId());
            //处理经营负责人和设计负责人
            List<ProjectTaskRelationEntity> relationEntities = this.projectTaskRelationDao.getTaskRelationParam(stringMap);
            if (CollectionUtils.isEmpty(relationEntities)) {//如果为空，则删除合作设计费
                stringMap.put("type", "3"); //合作设计费的标示
                List<ProjectCostDTO> costList = this.projectCostDao.selectByParam(stringMap);
                for (ProjectCostDTO costDTO : costList) {
                    this.projectCostService.deleteProjectCost(costDTO.getId(), accountId);
                }
            }
        }
    }

    /**
     * 方法描述：删除任务
     * 作者：MaoSF
     * 日期：2017/1/3
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResponseBean deleteProjectTaskForDesignContent(ProjectTaskEntity task, String accountId) throws Exception {
        String id = task.getId();
        //删除设计阶段
//        projectDesignContentDao.deleteById(id);
        //更改签发的数据
        List<ProjectTaskEntity> list = this.projectTaskDao.getProjectTaskByTaskPath(id);//查询当前节点下面所有的子任务（包含自己）

        //把设计阶段下面的所以任务都设置为无效
        ProjectTaskEntity projectTaskEntity = new ProjectTaskEntity();
        projectTaskEntity.setTaskPath(id);
        projectTaskEntity.setTaskStatus("1");
        projectTaskEntity.setUpdateBy(accountId);
        projectTaskDao.updateProjectTaskStatus(projectTaskEntity);

        //删除计划时间
        this.projectProcessTimeDao.deleteByTargetId(id);

        //删除负责人
        this.projectMemberService.deleteProjectMember(ProjectMemberType.PROJECT_TASK_RESPONSIBLE, id);

        List<String> taskIdList = new ArrayList<String>();
        List<String> companyList = new ArrayList<String>();//记录公司id
        //签发的记录（用来保存被删除记录的签发记录，最后匹配是否还存在其他签发（同项目，同A--B的签发记录，用于判断是否删除合作设计费））
        List<ProjectTaskRelationEntity> taskRelationList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (ProjectTaskEntity taskEntity : list) {
                if (taskEntity.getTaskType() != 1) {
                    taskIdList.add(taskEntity.getId());
                }
                companyList.add(taskEntity.getCompanyId());
                //忽略任务
                this.myTaskService.ignoreMyTask(taskEntity.getId());
                //删除流程
                this.projectProcessService.deleteProcessByTaskId(taskEntity.getId());
                //查询签发记录
                ProjectTaskRelationEntity taskRelation = this.projectTaskRelationDao.getProjectTaskRelationByTaskId(taskEntity.getId());
                if (taskRelation != null) {
                    taskRelationList.add(taskRelation);
                }


                //负责人移除群
                if (!id.equals(taskEntity.getId())) {
                    this.projectMemberService.deleteProjectMember(ProjectMemberType.PROJECT_TASK_RESPONSIBLE, taskEntity.getId());
                }
            }

            if (!CollectionUtils.isEmpty(taskIdList)) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("relationStatus", "1");
                map.put("taskIdList", taskIdList);
                this.projectTaskRelationDao.updateTaskRelationStatus(map);
            }

            ProjectEntity projectEntity = projectDao.selectById(task.getProjectId());
            if (projectEntity != null && !StringUtil.isNullOrEmpty(task.getCompanyId()) && !projectEntity.getCompanyId().equals(task.getCompanyId()) && !task.getCompanyId().equals(projectEntity.getCompanyBid())) {
                //处理经营负责人和设计负责人
                List<ProjectTaskRelationEntity> relationEntities = this.projectTaskRelationDao.getProjectTaskRelationByCompanyId(task.getProjectId(), task.getCompanyId());
                if (CollectionUtils.isEmpty(relationEntities)) {
                    this.projectManagerService.deleteProjectManage(task.getProjectId(), task.getCompanyId());
                }
            }

            //删除费用节点
            this.handleProjectTaskRelationForCooperatorFee(projectEntity, taskRelationList, accountId);
        }

        //删除文档库
        this.projectSkyDriverService.updateSkyDriveStatus(id, accountId);

        //删除消息
        this.messageService.deleteMessage(id);

        if (!CollectionUtils.isEmpty(companyList)) {
            //发送通知
            this.projectService.sendMsgToRelationCompanyUser(companyList);
        }
        //返回信息
        return ResponseBean.responseSuccess("删除成功");
    }


    /**
     * 方法描述：删除任务
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param id
     * @param accountId
     * @param:
     * @return:
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResponseBean deleteProjectTaskById(String id, String accountId) throws Exception {
        ResponseBean responseBean;
        ProjectTaskEntity pTask = this.projectTaskDao.selectById(id);
        if (StringUtil.isNullOrEmpty(pTask.getTaskPid())) {//如果是根任务，调用删除设计阶段的接口
            responseBean = deleteProjectTaskByIdForDesignContent(id, accountId);
        } else {
            //删除任务(要删除的人员项目群)
            responseBean = this.deleteProjectTask(pTask, accountId);
            //添加项目动态
            dynamicService.addDynamic(pTask, null, null, accountId);
            if (!StringUtil.isNullOrEmpty(pTask.getTaskPid())) {
                ProjectTaskEntity projectTaskParent = this.projectTaskDao.selectById(pTask.getTaskPid());
                if (projectTaskParent != null && projectTaskParent.getTaskType() != 0) {
                    List<ProjectTaskDTO> list = this.projectTaskDao.getProjectTaskByPid(pTask.getTaskPid(), pTask.getCompanyId());
                    if (CollectionUtils.isEmpty(list)) {
                        if (projectTaskParent.getIsOperaterTask() == 1) {
                            projectTaskParent.setIsOperaterTask(0);
                            this.updateById(projectTaskParent);
                        }
                    }
                }
            }
        }

        //通知协同
        if(pTask.getTaskType()==SystemParameters.TASK_TYPE_PHASE)
            collaborationService.pushSyncCMD_PT(pTask.getProjectId(),pTask.getTaskPath(),SyncCmd.PT0);
        else if(pTask.getTaskType()==SystemParameters.TASK_TYPE_ISSUE)
            collaborationService.pushSyncCMD_PT(pTask.getProjectId(),pTask.getTaskPath(),SyncCmd.PT1);
        else
            collaborationService.pushSyncCMD_PT(pTask.getProjectId(),pTask.getTaskPath(),SyncCmd.PT2);

        return responseBean;
    }

    /**
     * 方法描述：删除设计阶段的时候调用
     * 作者：MaoSF
     * 日期：2017/4/8
     *
     * @param id
     * @param accountId
     * @param:
     * @return:
     */
    @Override
    public ResponseBean deleteProjectTaskByIdForDesignContent(String id, String accountId) throws Exception {
        ProjectTaskEntity projectTaskEntity = this.projectTaskDao.selectById(id);
        //添加项目动态
        dynamicService.addDynamic(projectTaskEntity, null, null, accountId);
        //删除任务(要删除的人员项目群)
        ResponseBean ResponseBean = this.deleteProjectTaskForDesignContent(projectTaskEntity, accountId);
        return ResponseBean;
    }


    /**
     * 方法描述：获取任务详情及子任务
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param id
     * @param:
     * @return:
     */
    @Override
    public ProjectTaskDetailDTO getProjectTaskById(String id, String companyId, String accountId) throws Exception {
        ProjectTaskDetailDTO detailDTO = new ProjectTaskDetailDTO();
        ProjectTaskDTO dto = this.projectTaskDao.getProjectTaskById(id, companyId);
        if (dto != null) {
            this.setTaskState(dto);
            BaseDTO.copyFields(dto, detailDTO);
            //负责人处理
            ProjectMemberDTO manager = this.projectMemberService.getTaskDesignerDTO(dto.getId());
            if (manager != null) {
                detailDTO.setManagerId(manager.getCompanyUserId());
                detailDTO.setManagerName(manager.getCompanyUserName());
            }

            if (!StringUtil.isNullOrEmpty(dto.getTaskPid())) {
                detailDTO.setTaskParentName(this.projectTaskDao.getTaskParentName(dto.getTaskPid()));
            }

            ProjectEntity projectEntity = this.projectDao.selectById(dto.getProjectId());
            if (projectEntity != null) {
                detailDTO.setProjectName(projectEntity.getProjectName());
            }


            //查询子任务
            List<ProjectTaskDTO> childList = this.projectTaskDao.getProjectTaskByPid(id,companyId);
            List<ProjectTaskDetailDTO> list = new ArrayList<>();
            for (ProjectTaskDTO child : childList) {
                if (child.getTaskType() == 3) {
                    continue;//过滤未发布的版本数据
                }
                this.setTaskState(child);
                ProjectTaskDetailDTO detailDTO1 = new ProjectTaskDetailDTO();
                BaseDTO.copyFields(child, detailDTO1);
                //负责人处理
                ProjectMemberDTO manager1 = this.projectMemberService.getTaskDesignerDTO(detailDTO1.getId());
                if (manager1 != null) {
                    detailDTO1.setManagerId(manager1.getCompanyUserId());
                    detailDTO1.setManagerName(manager1.getCompanyUserName());
                }

                //查询对与的我的任务的id
                Map<String, Object> map = new HashMap<>();
                map.put("taskType", SystemParameters.PRODUCT_TASK_RESPONSE);
                map.put("targetId", detailDTO1.getId());
                List<MyTaskEntity> taskEntityList = this.myTaskDao.getMyTaskByParam(map);
                if (!CollectionUtils.isEmpty(taskEntityList)) {
                    detailDTO1.setMyTaskId(taskEntityList.get(0).getId());
                }
                list.add(detailDTO1);
            }
            detailDTO.setChildTaskList(list);
            /**************需要APP端修改前端**************/
            List<ProjectTaskProcessNodeDTO> designUserList = this.projectMemberService.listDesignUser(id);
            detailDTO.setDesignUserList(designUserList);
            //处理权限
            this.handleTaskDetailRoleFlag(detailDTO, companyId, accountId, dto.getProjectId(), dto.getTaskPath(), projectEntity.getCompanyBid(), list.size());

        }
        return detailDTO;
    }

    /**
     * 方法描述：获取任务详情及子任务
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    @Override
    public ProjectTaskDetailDTO getProductTaskForEdit(String id, String companyId, String accountId) throws Exception {
        ProjectTaskDetailDTO detailDTO = new ProjectTaskDetailDTO();
        ProjectTaskDTO dto = this.projectTaskDao.getProductTaskForEdit(id);
        if (dto != null) {
            this.setTaskState(dto);
            BaseDTO.copyFields(dto, detailDTO);
            //负责人处理
            ProjectMemberDTO manager = this.projectMemberService.getTaskDesignerDTO(dto.getId());
            if (manager != null) {
                detailDTO.setManagerId(manager.getCompanyUserId());
                detailDTO.setManagerName(manager.getCompanyUserName());
            }

            if (!StringUtil.isNullOrEmpty(dto.getTaskPid())) {
                detailDTO.setTaskParentName(this.projectTaskDao.getTaskParentName(dto.getTaskPid()));
            }

            ProjectEntity projectEntity = this.projectDao.selectById(dto.getProjectId());
            if (projectEntity != null) {
                detailDTO.setProjectName(projectEntity.getProjectName());
            }


            //查询子任务
            List<ProjectTaskDTO> childList = this.projectTaskDao.getProjectTaskByPidForProduct(id);
            List<ProjectTaskDetailDTO> list = new ArrayList<>();
            for (ProjectTaskDTO child : childList) {
                if (child.getTaskType() == 3) {
                    continue;//过滤未发布的版本数据
                }
                this.setTaskState(child);
                ProjectTaskDetailDTO detailDTO1 = new ProjectTaskDetailDTO();
                BaseDTO.copyFields(child, detailDTO1);
                //负责人处理
                ProjectMemberDTO manager1 = this.projectMemberService.getTaskDesignerDTO(detailDTO1.getId());
                if (manager1 != null) {
                    detailDTO1.setManagerId(manager1.getCompanyUserId());
                    detailDTO1.setManagerName(manager1.getCompanyUserName());
                }

                //查询对与的我的任务的id
                Map<String, Object> map = new HashMap<>();
                map.put("taskType", SystemParameters.PRODUCT_TASK_RESPONSE);
                map.put("targetId", detailDTO1.getId());
                List<MyTaskEntity> taskEntityList = this.myTaskDao.getMyTaskByParam(map);
                if (!CollectionUtils.isEmpty(taskEntityList)) {
                    detailDTO1.setMyTaskId(taskEntityList.get(0).getId());
                }
                list.add(detailDTO1);
            }
            detailDTO.setChildTaskList(list);
            List<ProjectTaskProcessNodeDTO> designUserList = this.projectMemberService.listDesignUser(dto.getId());
            detailDTO.setDesignUserList(designUserList);
            //处理权限
            this.handleTaskDetailRoleFlag(detailDTO, companyId, accountId, dto.getProjectId(), dto.getTaskPath(), projectEntity.getCompanyBid(), list.size());

        }
        return detailDTO;
    }


    /**
     * 方法描述：处理当前任务权限标示（roleFlag）
     * 作者：MaoSF
     * 日期：2017/1/18
     *
     * @param:
     * @return:
     */
    public void handleTaskDetailRoleFlag(ProjectTaskDetailDTO dto, String companyId, String accountId, String projectId, String taskPath, String companyBid, int isHasChild) throws Exception {
        //设计负责人
        ProjectMemberEntity managerEntity = this.projectMemberService.getDesignManager(projectId, companyId);
        Map<String, String> map = new HashMap<String, String>();
        if (!StringUtil.isNullOrEmpty(accountId)) {//accountId可能传递为null,则不做任何处理
            CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, companyId);
            if (userEntity == null) {
                return;
            }
            String companyUserId = userEntity.getId();
            ProjectMemberDTO responsiblerMap = this.projectMemberService.getTaskDesignerDTO(dto.getId());
            if (dto.getCompanyId().equals(companyId)) {//如果是本团队的任务

                ProjectMemberDTO responsiblerParentTaskMap = null;
                if (!StringUtil.isNullOrEmpty(dto.getTaskPid())) {
                    responsiblerParentTaskMap = this.projectMemberService.getTaskDesignerDTO(dto.getTaskPid());
                }

                Map<String, Object> param2 = new HashMap<String, Object>();
                param2.put("taskId", dto.getId());
                ProjectTaskRelationEntity relationEntity = this.projectTaskRelationDao.getProjectTaskRelationByTaskId(dto.getId());

                if (relationEntity == null && responsiblerMap != null) {//如果是转发的来的任务（类似根任务）
                    //如果是设计负责人
                    if ((managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId))) {
                        if (StringUtil.isNullOrEmpty(dto.getCompleteDate())) {
                            map.put("flag1", "1");
                            map.put("flag3", "1");
                        }
                        map.put("flag2", "1");
                        if (dto.getTaskType() == 0) {
                            map.put("flag4", "1");
                            map.put("flag5", "1");
                            List<ProjectTaskEntity> projectTaskEntityList = this.projectTaskDao.getProjectTaskByPid2(dto.getId());
                            if (CollectionUtils.isEmpty(projectTaskEntityList)) {
                                map.put("flag6", "1");
                            }
                        }
                    }

                    //如果是父级任务负责人
                    if (responsiblerParentTaskMap != null && companyUserId.equals(responsiblerParentTaskMap.getCompanyUserId())) {
                        //如果不是设计负责人，才走下面，因为如果是设计负责人，则已经具有所有的权限了
                        if (!(managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId))) {
                            map.put("flag2", "1");
                            if (dto.getTaskType() == 0) {
                                map.put("flag4", "1");
                                map.put("flag5", "1");
                                List<ProjectTaskEntity> projectTaskEntityList = this.projectTaskDao.getProjectTaskByPid2(dto.getId());
                                if (CollectionUtils.isEmpty(projectTaskEntityList)) {
                                    map.put("flag6", "1");
                                }
                            }
                        }
                    }

                    //如果是任务负责人
                    if (responsiblerMap.getCompanyUserId().equals(companyUserId) && StringUtil.isNullOrEmpty(dto.getCompleteDate())) {//如果是任务负责人
                        map.put("flag1", "1");
                        map.put("flag3", "1");
                    }
                }
            }
            //如果是乙方经营负责人且存在任务负责人，则可以操作
            if (managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)
                    && companyId.equals(companyBid) && StringUtil.isNullOrEmpty(dto.getCompleteDate())) {
                map.put("flag3", "1");
            }
        }
        dto.setRoleFlag(map);
    }


    /**
     * 方法描述：处理当前任务权限标示（roleFlag）
     * 作者：MaoSF
     * 日期：2017/1/18
     */
    public void handleTaskDetailRoleFlag2(ProjectTaskDetailDTO dto, String companyId, String taskPath, String companyBid,String responsibleId,CompanyUserEntity  userEntity ,ProjectMemberEntity designManager) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (userEntity!=null) {
            String companyUserId = userEntity.getId();
            if (dto.getCompanyId().equals(companyId)) {//如果是本团队的任务

                ProjectMemberDTO responsiblerParentTaskMap = null;
                if (!StringUtil.isNullOrEmpty(dto.getTaskPid())) {
                    responsiblerParentTaskMap = this.projectMemberService.getTaskDesignerDTO(dto.getTaskPid());
                }
                if (responsibleId != null) {
                    //如果是设计负责人
                    if ((designManager != null && designManager.getCompanyUserId().equals(companyUserId))) {
                        if (StringUtil.isNullOrEmpty(dto.getCompleteDate())) {
                            map.put("flag1", "1");
                            map.put("flag3", "1");
                        }
                        map.put("flag2", "1");
                        if (dto.getTaskType() == 0) {
                            map.put("flag4", "1");
                            map.put("flag5", "1");
                            List<ProjectTaskEntity> projectTaskEntityList = this.projectTaskDao.getProjectTaskByPid2(dto.getId());
                            if (CollectionUtils.isEmpty(projectTaskEntityList)) {
                                map.put("flag6", "1");
                            }
                        }
                    }
                    //如果是父级任务负责人
                    if (responsiblerParentTaskMap != null && companyUserId.equals(responsiblerParentTaskMap.getCompanyUserId())) {
                        //如果不是设计负责人，才走下面，因为如果是设计负责人，则已经具有所有的权限了
                        if (!(designManager != null && designManager.getCompanyUserId().equals(companyUserId))) {
                            map.put("flag2", "1");
                            if (dto.getTaskType() == 0) {
                                map.put("flag4", "1");
                                map.put("flag5", "1");
                                List<ProjectTaskEntity> projectTaskEntityList = this.projectTaskDao.getProjectTaskByPid2(dto.getId());
                                if (CollectionUtils.isEmpty(projectTaskEntityList)) {
                                    map.put("flag6", "1");
                                }
                            }
                        }
                    }
                    //如果是任务负责人
                    if (responsibleId.equals(companyUserId) && StringUtil.isNullOrEmpty(dto.getCompleteDate())) {//如果是任务负责人
                        map.put("flag1", "1");
                        map.put("flag3", "1");
                    }
                }
            }
            //如果是乙方经营负责人且存在任务负责人，则可以操作
//            if (designManager != null && designManager.getCompanyUserId().equals(companyUserId)
//                    && companyId.equals(companyBid) && StringUtil.isNullOrEmpty(dto.getCompleteDate())) {
//                map.put("flag3", "1");
//            }
        }
        dto.setRoleFlag(map);
    }

    /**
     * 方法描述：处理当前任务权限标示（roleFlag）
     * 作者：MaoSF
     * 日期：2017/1/18
     *
     * @param:
     * @return:
     */
    public void handleTaskDetailRoleFlagForOperate(ProjectTaskDetailDTO dto, String projectCompanyId, String companyId, String accountId, String projectId, String taskPath, int isHasChild) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (!StringUtil.isNullOrEmpty(accountId)) {//accountId可能传递为null,则不做任何处理
            CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, companyId);
            if (userEntity == null) {
                return;
            }
            String companyUserId = userEntity.getId();
            ProjectMemberEntity managerEntity = this.projectMemberService.getOperatorManager(projectId, companyId);
            if (StringUtil.isNullOrEmpty(dto.getTaskPid())) {//如果是根任务
                ProjectEntity project = this.projectDao.selectById(projectId);
                if (projectCompanyId.equals(companyId) || (project != null && projectCompanyId.equals(project.getCompanyBid()))) {
                    dto.setIsShowAppointmentTime(1);//展示约定时间
                    dto.setIsShowProcessTime(1);
                }
                if (projectCompanyId.equals(companyId) && managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)) {
                    if (managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)) {
                        map.put("flag1", "1");
                        map.put("flag4", "1");
                        map.put("flag5", "1");//可以编辑名称
                        if (isHasChild == 0) {
                            map.put("flag6", "1");//删除
                        }
                    }
                }
            } else {//如果是子任务
                ProjectTaskRelationEntity relationEntity = this.projectTaskRelationDao.getProjectTaskRelationByTaskId(dto.getId());
                if (dto.getCompanyId().equals(companyId)) {//如果是本团队的任务
                    if (relationEntity != null) {//如果是转发的来的任务（类似根任务）
                        dto.setIsShowAppointmentTime(1);//展示约定时间
                        dto.setIsShowProcessTime(1);
                        if (managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)) {
                            map.put("flag1", "1");
                            map.put("flag4", "1");
                        }
                    } else {

                        if (managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)) {
                            // map.put("flag1","1");
                            map.put("flag5", "1");
                            List<ProjectTaskDTO> projectTaskEntityList = this.projectTaskDao.getProjectTaskByPid(dto.getId(), null);
                            if (CollectionUtils.isEmpty(projectTaskEntityList)) {
                                map.put("flag6", "1");
                            }
                        }
                    }

                } else {//-----------------处理完-----------------//
                    //查询是否是当前公司签发出去的
                    if (relationEntity != null && relationEntity.getFromCompanyId().equals(companyId)) {//说明是签发的公司
                        dto.setIsShowProcessTime(1);
                        if (managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)) {
                            map.put("flag5", "1");
                            //判断是否存在子节点
                            List<ProjectTaskDTO> projectTaskEntityList = this.projectTaskDao.getProjectTaskByPid(dto.getId(), null);
                            if (CollectionUtils.isEmpty(projectTaskEntityList)) {
                                map.put("flag6", "1");
                            }
                        }
                    }
                }
            }
            //设置转发的次数
            if (!StringUtil.isNullOrEmpty(taskPath)) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                String[] taskIdList = taskPath.split("-");
                map2.put("taskIdList", taskIdList);
                int issueCount = this.projectTaskRelationDao.getTaskIssueCount(map2);
                dto.setIssueCount(0);
                if (issueCount < 2) {
                    dto.setIssueCount(1);
                }
            }
        }
        dto.setRoleFlag(map);
    }


    /**
     * 方法描述：经营任务分解
     * 作者：CHENZHUJIE
     * 日期：2017/2/24
     *
     * @param dto
     */
    @Override
    public ResponseBean saveProjectTask2(SaveProjectTaskDTO dto) throws Exception {
        ResponseBean responseBean = null;
        if (dto.getTaskType() == 0) {
          //  responseBean = this.saveNotPublishProductionTask(dto);
            responseBean = this.saveProductionTask(dto);
        } else {
            // responseBean = this.saveOperaterTask(dto);
            responseBean = saveIssueTask(dto);
        }
        return responseBean;
    }


    /**
     * 方法描述：处理任务完成时间
     * 作者：MaoSF
     * 日期：2017/3/12
     */
    @Override
    public ResponseBean handleProjectTaskCompleteDate(String taskId, String companyUserId) throws Exception {
        ProjectTaskEntity task = this.projectTaskDao.selectById(taskId);
        //查询设计负责人
        ProjectMemberEntity designerManager = this.projectMemberService.getDesignManager(task.getProjectId(), task.getCompanyId());
        if (!StringUtil.isNullOrEmpty(task.getTaskPid())) {
            //查询子任务是否全部完成
            Map<String, Object> map = new HashMap<>();
            map.put("taskPid", task.getTaskPid());
            map.put("notComplete", "1");//查询未完成的
            List<ProjectTaskEntity> taskList = this.projectTaskDao.selectByParam(map);
            if (CollectionUtils.isEmpty(taskList)) {//如果全部完成，则推送任务
                ProjectTaskEntity parentTask = this.projectTaskDao.selectById(task.getTaskPid());
                ProjectMemberEntity paramDesignerManager = null;
                if (!task.getCompanyId().equals(parentTask.getCompanyId())) {
                    paramDesignerManager = this.projectMemberService.getDesignManager(parentTask.getProjectId(), parentTask.getCompanyId());
                } else {
                    paramDesignerManager = designerManager;
                }
                if (paramDesignerManager != null) {
                    myTaskService.saveMyTask(parentTask.getId(), SystemParameters.TASK_COMPLETE, parentTask.getCompanyId(), paramDesignerManager.getCompanyUserId(), false);
                }
            }
        }
        //只要生产根任务都完成，都需要推送任务
        if ((task != null) && (designerManager != null)) {
            CompanyUserEntity userEntity = this.companyUserDao.selectById(designerManager.getCompanyUserId());
            if (userEntity == null) return null;
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setProjectId(task.getProjectId());
            messageEntity.setCompanyId(task.getCompanyId());
            messageEntity.setTargetId(taskId);
            messageEntity.setUserId(userEntity.getUserId());
            messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_35);
            this.messageService.sendMessage(messageEntity);
        }
        return null;
    }

    /**
     * 方法描述：移交设计负责人请求数据
     * 作者：MaoSF
     * 日期：2017/3/22
     *
     * @param map
     * @param:
     * @return:
     */
    @Override
    public ResponseBean getProjectTaskForChangeDesigner(Map<String, Object> map) throws Exception {
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId((String) map.get("accountId"), (String) map.get("appOrgId"));
        if (userEntity == null) {
            return ResponseBean.responseError("查询失败");
        }
        map.put("targetId", userEntity.getId());
        List<ProejctTaskForDesignerDTO> taskList = this.projectTaskDao.getMyProjectTask(map);
        return ResponseBean.responseSuccess().addData("taskList", taskList);
    }

    /**
     * 方法描述：
     * 作者：MaoSF
     * 日期：2017/3/17
     *
     * @param map
     * @param:map(projectId,toCompanyId(签发给这个组织的id)
     * @return:
     */
    @Override
    public ResponseBean validateIssueTaskCompany(Map<String, Object> map) throws Exception {
        String currentCompanyId = (String) map.get("appOrgId");
        String toCompanyId = (String) map.get("toCompanyId");
        String projectId = (String) map.get("projectId");
//        if(currentCompanyId.equals(toCompanyId)){
//            return ResponseBean.responseSuccess();
//        }
        //如果不是签发给自己，则查询是否已经签过发给toCompanyId

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("projectId", projectId);
        map1.put("companyId", toCompanyId);
        List<ProjectMemberDTO> list = this.projectMemberService.listProjectManager(projectId, toCompanyId);
        if (!CollectionUtils.isEmpty(list)) {
            map1.clear();
            for (ProjectMemberDTO dto : list) {
                if (ProjectMemberType.PROJECT_OPERATOR_MANAGER == dto.getMemberType()) {
                    map1.put("managerId", dto.getCompanyUserId());
                    map1.put("managerName", dto.getUserName());
                }
                /*****************************/
                if (ProjectMemberType.PROJECT_DESIGNER_MANAGER == dto.getMemberType()) {
                    map1.put("designerId", dto.getCompanyUserId());
                    map1.put("designerName", dto.getUserName());
                }
            }
            return ResponseBean.responseSuccess().addData(map1);
        }
        return ResponseBean.responseSuccess();
    }


    /**
     * 方法描述:获取任务签发组织
     * 作者：MaoSF
     * 日期：2017/3/17
     *
     * @param id
     * @param:
     * @return:
     */
    @Override
    public ResponseBean getIssueTaskCompany(String id, String companyId) throws Exception {
        ProjectTaskEntity projectTask = this.projectTaskDao.selectById(id);
        Map<String, Object> map = new HashMap<String, Object>();

        //判断是否是二次签发
        Map<String, Object> map2 = new HashMap<String, Object>();
        String[] taskIdList = projectTask.getTaskPath().split("-");
        map2.put("taskIdList", taskIdList);
        int issueCount = this.projectTaskRelationDao.getTaskIssueCount(map2);
        if (issueCount == 2) {//已经存在二次转发。
            CompanyEntity companyEntity = companyDao.selectById(companyId);
            CompanyDataDTO companyDataDTO = new CompanyDataDTO();
            BaseDTO.copyFields(companyEntity, companyDataDTO);
            List<CompanyDataDTO> dataDTOS = new ArrayList<CompanyDataDTO>();
            dataDTOS.add(companyDataDTO);
            map.put("allCompanyList", dataDTOS);
        } else {
            Map<String, Object> allCompany = this.companyService.getCompanyForSelect(companyId, projectTask.getProjectId());
            List<CompanyDataDTO> allCompanyList = (List<CompanyDataDTO>) allCompany.get("allCompanyList");
            List<CompanyDataDTO> subCompanyList = (List<CompanyDataDTO>) allCompany.get("subCompanyList");
            List<CompanyDataDTO> partnerCompanyList = (List<CompanyDataDTO>) allCompany.get("partnerCompanyList");
            List<CompanyDataDTO> outerCooperatorCompanyList = (List<CompanyDataDTO>) allCompany.get("outerCooperatorCompanyList");

            List<CompanyDataDTO> allCompanyList2 = new ArrayList<>();
            List<CompanyDataDTO> subCompanyList2 = new ArrayList<>();
            List<CompanyDataDTO> partnerCompanyList2 = new ArrayList<>();
            List<CompanyDataDTO> outerCooperatorCompanyList2 = new ArrayList<>();

            String companyIdList = this.projectTaskDao.getParentTaskCompanyId(map2);
            if (!StringUtil.isNullOrEmpty(companyIdList)) {

                for (CompanyDataDTO dataDTO : allCompanyList) {
                    if (companyId.equals(dataDTO.getId()) || companyIdList.indexOf(dataDTO.getId()) < 0) {
                        allCompanyList2.add(dataDTO);
                    }
                }

                for (CompanyDataDTO dataDTO : subCompanyList) {
                    if (companyId.equals(dataDTO.getId()) || companyIdList.indexOf(dataDTO.getId()) < 0) {
                        subCompanyList2.add(dataDTO);
                    }
                }
                for (CompanyDataDTO dataDTO : partnerCompanyList) {
                    if (companyId.equals(dataDTO.getId()) || companyIdList.indexOf(dataDTO.getId()) < 0) {
                        partnerCompanyList2.add(dataDTO);
                    }
                }
                for (CompanyDataDTO dataDTO : outerCooperatorCompanyList) {
                    if (companyId.equals(dataDTO.getId()) || companyIdList.indexOf(dataDTO.getId()) < 0) {
                        outerCooperatorCompanyList2.add(dataDTO);
                    }
                }

                //  setNikeName(allCompanyList2,companyId);
                map.put("allCompanyList", allCompanyList2);
                //  setNikeName(subCompanyList2,companyId);
                map.put("subCompanyList", subCompanyList2);
                // setNikeName(partnerCompanyList2,companyId);
                map.put("partnerCompanyList", partnerCompanyList2);
                //  setNikeName(outerCooperatorCompanyList2,companyId);
                map.put("outerCooperatorCompanyList", outerCooperatorCompanyList2);
            } else {
                map.put("allCompanyList", allCompany);
            }
        }
        return ResponseBean.responseSuccess("查询成功").setData(map);
    }

//    private void setNikeName(List<CompanyDataDTO> list,String companyId){
//        for(CompanyDataDTO dto:list){
//            dto.setRealName(dto.getCompanyName());
//            String nikeName = this.companyService.getNickName(dto.getId(),companyId);
//            if(!StringUtil.isNullOrEmpty(nikeName)){
//                dto.setCompanyName(nikeName);
//            }
//        }
//    }

    /**
     * 方法描述：设置任务状态
     * 作者：MaoSF
     * 日期：2017/4/6
     *
     * @param dto
     * @param:
     * @return:
     */
    @Override
    public void setTaskState(ProjectTaskDTO dto) throws Exception {
        if ((dto.getBeModifyId() == null
                && (dto.getTaskType()==SystemParameters.TASK_PRODUCT_TYPE_MODIFY|| dto.getTaskType()==SystemParameters.TASK_TYPE_MODIFY))
                || ("2".equals(dto.getTaskStatus())) && dto.getId().equals(dto.getBeModifyId())) {
            dto.setTaskState(7);
            dto.setStateHtml("未发布");
            return;
        }
        //设置状态
        int taskState = getTaskState(StringUtil.isNullOrEmpty(dto.getBeModifyId())?dto.getId():dto.getBeModifyId());
        dto.setTaskState(taskState);
        String stateHtml = getStateHtml(dto.getEndTime(), dto.getStartTime(), dto.getCompleteDate(), taskState);
        dto.setStateHtml(stateHtml);
    }

    public int getTaskState(String id) {
        return this.projectTaskDao.getTaskState(id);
    }

    public String getStateHtml(String endTime, String startTime, String completeDate, int taskState) {

        return this.projectTaskDao.getStateText(taskState, startTime, endTime, completeDate);
    }


    @Override
    public ResponseBean getIssueTaskForMyTask(String projectId, String companyId) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("companyId", companyId);

        List<ProjectTaskDTO> list = new ArrayList<>();
        ProjectEntity projectEntity = this.projectDao.selectById(projectId);
        if (projectEntity != null && projectEntity.getCompanyId().equals(companyId)) {
            list = this.projectTaskDao.getProjectTaskRootData(projectId);
        } else {
            list = this.projectTaskDao.getProjectTaskByCompanyId(map);
        }
        List<ProjectTaskDetailDTO> taskList = new ArrayList<>();
        ProjectMemberDTO managerDTO = this.projectMemberService.getOperatorManagerDTO(projectId, companyId);

        for (ProjectTaskDTO dto1 : list) {
            setTaskState(dto1);
            ProjectTaskDetailDTO detailDTO = new ProjectTaskDetailDTO();
            BaseDTO.copyFields(dto1, detailDTO);
            //负责人处理
            if (managerDTO != null) {
                detailDTO.setManagerId(managerDTO.getCompanyUserId());
                detailDTO.setManagerName(managerDTO.getUserName());
            }
            if (!StringUtil.isNullOrEmpty(dto1.getTaskPid())) {
                detailDTO.setTaskParentName(this.projectTaskDao.getTaskParentName(dto1.getTaskPid()));
            }
            //设定约定时间
            List<ProjectProcessTimeEntity> timeList = null;
            if (dto1.getTaskType() == 1) {
                map.clear();
                map.put("targetId", dto1.getId());
                map.put("type", "1");
                timeList = projectProcessTimeDao.getProjectProcessTime(map);

            } else {
                if (null != projectTaskRelationDao.getProjectTaskRelationByTaskId(dto1.getId())) {//如果存在转发的记录
                    map.clear();
                    map.put("targetId", dto1.getId());
                    map.put("type", "2");
                    map.put("notCompanyId", companyId);
                    timeList = projectProcessTimeDao.getProjectProcessTime(map);
                }
            }

            if (!CollectionUtils.isEmpty(timeList)) {
                detailDTO.setAppointedStartTime(timeList.get(0).getStartTime());
                detailDTO.setAppointedEndTime(timeList.get(0).getEndTime());
            }
            //查询子任务
            List<ProjectTaskDTO> childList = this.projectTaskDao.getProjectTaskByPid(detailDTO.getId(), companyId);
            List<ProjectTaskDetailDTO> childDetailList = new ArrayList<>();
            for (ProjectTaskDTO child : childList) {
                this.setTaskState(child);
                ProjectTaskDetailDTO detailDTO1 = new ProjectTaskDetailDTO();
                BaseDTO.copyFields(child, detailDTO1);
                childDetailList.add(detailDTO1);
            }
            detailDTO.setChildTaskList(childDetailList);
            taskList.add(detailDTO);
        }
        return ResponseBean.responseSuccess().addData("taskList", taskList);
    }

    /**
     * 方法描述：设置设计负责人（我的任务）获取的数据
     * 作者：MaoSF
     * 日期：2017/5/5
     *
     * @param:map(projectId,companyId,)
     * @return:
     */

    public ResponseBean getArrangeDesignManagerForMyTask(String projectId, String companyId) throws Exception {

        ProjectTaskDataDTO dto = new ProjectTaskDataDTO();
        ProjectEntity project = this.projectDao.selectById(projectId);
        //获取当前项目在当前团队的经营负责人和项目负责人
        List<ProjectMemberDTO> managerList = this.projectMemberService.listProjectManager(projectId, companyId);
        //获取任务列表
        List<ProjectTaskDTO> taskList = this.projectTaskDao.getResponsibleTaskForMyTask(projectId, companyId);
        dto.setId(projectId);
        dto.setProjectName(project != null ? project.getProjectName() : null);
        dto.setTaskList(taskList);
        dto.setManagerList(managerList);
        // return dto;
        return ResponseBean.responseSuccess().addData("projectTask", dto);
    }


    /**
     * 方法描述：设置任务负责人（我的任务）获取的数据
     * 作者：MaoSF
     * 日期：2017/5/5
     *
     * @param:map(projectId,companyId,)
     * @return:
     */

    public ResponseBean getArrangeTaskResponseForMyTask(String projectId, String taskId, String companyId) throws Exception {

        //获取当前项目在当前团队的经营负责人和项目负责人
        List<ProjectMemberDTO> managerList = this.projectMemberService.listProjectManager(projectId, companyId);
        //获取任务
        ProjectTaskDTO task = this.projectTaskDao.getProjectTaskById(taskId, companyId);
        if (task != null) {
            this.setTaskState(task);
        }
        //查询任务负责人
        ProjectMemberDTO taskResponsible = this.projectMemberService.getTaskDesignerDTO(taskId);
        task.setTaskResponsibleName("");
        if (taskResponsible != null) {
            task.setTaskResponsibleName(taskResponsible.getCompanyUserName());
        }
        return ResponseBean.responseSuccess().addData("task", task).addData("managerList", managerList);
    }

    /**
     * 方法描述：处理设校审（我的任务）获取的数据
     * 作者：MaoSF
     * 日期：2017/5/5
     *
     * @param:map(projectId,taskId，companyId,)
     * @return:
     */
    @Override
    public ResponseBean getHandProcessDataForMyTask(String projectId, String taskId, String companyId) throws Exception {

        //获取任务
        ProjectTaskDTO task = this.projectTaskDao.getProjectTaskById(taskId, companyId);
        if (task != null) {
            this.setTaskState(task);
        }
        //查询任务负责人
        ProjectMemberDTO taskResponsible = this.projectMemberService.getTaskDesignerDTO(taskId);
        if (taskResponsible != null) {
            task.setTaskResponsibleName(taskResponsible.getCompanyUserName());
        }

        List<ProjectTaskProcessNodeDTO> designUserList = this.projectMemberService.listDesignUser(taskId);
        return ResponseBean.responseSuccess().addData("projectTask", task).addData("designUserList", designUserList);
    }

    /**
     * 进行签发
     *
     * @param idList        要发布的签发任务的ID列表
     * @param currentUserId 操作者标识
     */
    @Override
    public ResponseBean publishIssueTask(List<String> idList, String currentUserId, String currentCompanyId) throws Exception {
        List<ProjectTaskEntity> list = projectTaskDao.getTaskByIdList(idList);
        for (ProjectTaskEntity e : list) {
            e.setUpdateBy(currentUserId);
            e.setUpdateDate(new Date());
            TaskWithFullNameDTO origin = zInfoDAO.getTaskByTaskId(e.getBeModifyId());
            ProjectTaskEntity entity;
            if (e.getBeModifyId() == null) {
                entity = new ProjectTaskEntity();
                BeanUtilsEx.copyProperties(e, entity);
                entity.setId(StringUtil.buildUUID());
                //taskPath不能直接从未发布的数据中直接copy过来，因为id不一样。taskPath是由id-id连接起来的
                //查询父任务,设置taskPath
                ProjectTaskEntity parentTask = this.projectTaskDao.selectById(e.getTaskPid());
                if (parentTask != null) {
                    entity.setTaskLevel(parentTask.getTaskLevel() + 1);
                    entity.setTaskPath(parentTask.getTaskPath() + "-" + entity.getId());
                } else {
                    entity.setTaskLevel(1);
                    entity.setTaskPath(entity.getId());
                }
                entity.setBeModifyId(null);
                entity.setTaskStatus(SystemParameters.TASK_STATUS_VALID);
                entity.setTaskType(SystemParameters.TASK_TYPE_ISSUE);
                projectTaskDao.insert(entity);

                //处理签发关系
                this.handleProjectTaskRelation(entity, currentCompanyId, currentUserId);

                //复制时间
                handleProcessTime(entity, e, false);
                //保存项目动态（签发任务）
                dynamicService.addDynamic(null, entity, currentCompanyId, currentUserId);
                //判断是否是二次签发的记录，如果是二次签发，则自动产生一条生产的根任务
                if (!currentCompanyId.equals(entity.getCompanyId())) {
                    //判断是否是二次签发
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    String[] taskIdList = entity.getTaskPath().split("-");
                    map2.put("taskIdList", taskIdList);
                    int issueCount = this.projectTaskRelationDao.getTaskIssueCount(map2);
                    if (issueCount == 2) {//如果是二次转发
                        autoGenerationProductTask(entity);
                    }
                }

                e.setTaskStatus(SystemParameters.TASK_STATUS_VALID);
                e.setBeModifyId(entity.getId());
                projectTaskDao.updateById(e);
            } else {
                entity = projectTaskDao.selectById(e.getBeModifyId());
                updateProjectTaskForPublish(entity, e, currentCompanyId, currentUserId);
            }

            TaskWithFullNameDTO target = zInfoDAO.getTaskByTaskId(e.getBeModifyId());
            if ((origin != null) && (origin.getTaskType() == SystemParameters.TASK_TYPE_PRODUCT)) origin.setTaskType(SystemParameters.TASK_TYPE_ISSUE); //生产根任务作为签发任务处理
            if ((target != null) && (target.getTaskType() == SystemParameters.TASK_TYPE_PRODUCT)) target.setTaskType(SystemParameters.TASK_TYPE_ISSUE); //生产根任务作为签发任务处理
            messageService.sendMessage(origin,target,null,e.getProjectId(),currentCompanyId,currentUserId);
            //通知协同
            this.collaborationService.pushSyncCMD_PT(e.getProjectId(),e.getTaskPath(),SyncCmd.PT1);
        }
        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 进行生产任务发布
     */
    @Override
    public ResponseBean publishProductTask(List<String> idList, String currentUserId, String currentCompanyId) throws Exception {
        List<ProjectTaskEntity> list = projectTaskDao.getTaskByIdList(idList);
        for (ProjectTaskEntity e : list) {
            e.setUpdateBy(currentUserId);
            e.setUpdateDate(new Date());
            ProjectTaskEntity entity;
            if (e.getBeModifyId() == null) {
                entity = new ProjectTaskEntity();
                BeanUtilsEx.copyProperties(e, entity);
                entity.setId(StringUtil.buildUUID());
                //taskPath不能直接从未发布的数据中直接copy过来，因为id不一样。taskPath是由id-id连接起来的
                //查询父任务,设置taskPath
                ProjectTaskEntity parentTask = this.projectTaskDao.selectById(e.getTaskPid());
                if (parentTask != null && parentTask.getTaskType() == SystemParameters.TASK_PRODUCT_TYPE_MODIFY) {
                    if (StringUtil.isNullOrEmpty(parentTask.getBeModifyId())) {
                        return ResponseBean.responseError("父任务“" + parentTask.getTaskName() + "”未发布，该任务不可发布");
                    }
                    parentTask = this.projectTaskDao.selectById(parentTask.getBeModifyId());
                }
                if (parentTask != null) {
                    entity.setTaskLevel(parentTask.getTaskLevel() + 1);
                    entity.setTaskPath(parentTask.getTaskPath() + "-" + entity.getId());
                    entity.setTaskPid(parentTask.getId());
                } else {
                    entity.setTaskLevel(1);
                    entity.setTaskPath(entity.getId());
                }
                entity.setBeModifyId(null);
                entity.setTaskStatus(SystemParameters.TASK_STATUS_VALID);
                entity.setTaskType(SystemParameters.TASK_TYPE_PRODUCT);
                this.insert(entity);
                //更新子集的父ID（没有发布过记录的父ID，并且记录的父ID的为被修改记得的ID的情况）
                this.updateModifyTaskPid(e.getId(), entity.getId(), entity.getTaskPath());
                //保存项目动态（生产任务）
                dynamicService.addDynamic(null, entity, currentCompanyId, currentUserId);
            } else {
                entity = projectTaskDao.selectById(e.getBeModifyId());
                //保存原有数据
                TaskWithFullNameDTO origin = zInfoDAO.getTaskByTask(entity);

                entity.setTaskName(e.getTaskName());
                entity.setTaskRemark(e.getTaskRemark());
                entity.setOrgId(e.getOrgId());
                this.updateById(entity);

                //保存项目动态（生产任务）
                TaskWithFullNameDTO target = zInfoDAO.getTaskByTask(entity);
                dynamicService.addDynamic(origin, target, currentCompanyId, currentUserId);
            }

            //复制时间
            handleProcessTime(entity, e, false);
            //保存项目动态（签发任务）
            dynamicService.addDynamic(null, entity, currentCompanyId, currentUserId);
            //处理人员变动
            this.projectMemberService.publishProjectMember(e.getProjectId(), e.getCompanyId(), entity.getId(), e.getId(), currentUserId);
            //更改状态
            e.setTaskStatus(SystemParameters.TASK_STATUS_VALID);
            e.setBeModifyId(entity.getId());
            projectTaskDao.updateById(e);

            //通知协同
            this.collaborationService.pushSyncCMD_PT(e.getProjectId(),e.getTaskPath(),SyncCmd.PT2);

//            String taskPid = e.getId();
//            if(!StringUtil.isNullOrEmpty(e.getBeModifyId())){
//                taskPid = e.getBeModifyId();
//            }
//            List<ProjectTaskEntity> taskList = projectTaskDao.listChildTaskForPublish(taskPid);
//            if (!CollectionUtils.isEmpty(taskList)) {
//                for (ProjectTaskEntity entity1 : taskList) {
//                    if (entity1.getTaskType() == SystemParameters.TASK_PRODUCT_TYPE_MODIFY && "2".equals(entity1.getTaskStatus())) {
//                        List<String> list1 = new ArrayList();
//                        list1.add(entity1.getId());
//                        publishProductTask(list1, currentUserId, currentCompanyId);
//                    }
//                }
//            }
        }

        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 方法描述：二次转包，发布的时候，自动产生一个生产的根任务
     * 作者：MaoSF
     * 日期：2017/6/2
     *
     * @param:
     * @return:
     */
    private void autoGenerationProductTask(ProjectTaskEntity parentTask) throws Exception {
        ProjectTaskEntity entity = new ProjectTaskEntity();
        BeanUtilsEx.copyProperties(parentTask,entity);
        entity.setId(StringUtil.buildUUID());
        //taskPath不能直接从未发布的数据中直接copy过来，因为id不一样。taskPath是由id-id连接起来的
        //查询父任务,设置taskPath
        entity.setTaskLevel(parentTask.getTaskLevel()+1);
        entity.setTaskPath(parentTask.getTaskPath()+"-"+entity.getId());
        entity.setTaskPid(parentTask.getId());
        entity.setBeModifyId(null);
        entity.setTaskStatus(SystemParameters.TASK_STATUS_MODIFIED);
        entity.setTaskType(SystemParameters.TASK_TYPE_MODIFY);
        insert(entity);
        //复制时间
        copyProcessTime(entity,parentTask,false);//此处为false，全部复制，并且为未发布
    }

    /**
     * 方法描述：时间全部复制
     * 作者：MaoSF
     * 日期：2017/6/21
     * @param:isPublish(true:发布，false:未发布）
     */
    private void copyProcessTime(ProjectTaskEntity taskEntity,ProjectTaskEntity publishTask,boolean isPublish) throws Exception{
        int timeType =isPublish?SystemParameters.PROCESS_TYPE_PLAN:SystemParameters.PROCESS_TYPE_NOT_PUBLISH;
        //复制及添加时间
        Map<String,Object> query = new HashMap<>();
        query.put("targetId",publishTask.getId());
        List<ProjectProcessTimeEntity> tps = projectProcessTimeDao.getProjectProcessTime(query);
        for (ProjectProcessTimeEntity t : tps) {
            saveTaskTime(taskEntity, t.getStartTime(), t.getEndTime(), t.getMemo(),timeType);
        }
    }
    /**
     * 方法描述：任务发布--出来任务关系
     * 作者：MaoSF
     * 日期：2017/5/16
     *
     * @param:
     * @return:
     */
    private void handleProjectTaskRelation(ProjectTaskEntity task, String currentCompanyId, String accountId) throws Exception {

        if (currentCompanyId.equals(task.getCompanyId())) {//如果签发的是给自己的团队
            //给设计负责人发送安排任务负责人的任务
            this.sendTaskToDesigner(task.getProjectId(), task.getId(), task.getCompanyId(), currentCompanyId, accountId);
        } else {//如果签发给其他团队
            //查询是否存在已签发给此公司
            //设置并通知对方公司经营负责人
            this.sendTaskToProjectManager(task.getProjectId(), task.getId(), task.getCompanyId());
            //设置并通知对方公司设计负责人
            this.notifyIssuedDesigner(task.getProjectId(), task.getId(), task.getCompanyId());
            //保存签发关系
            this.saveProjectTaskRelation2(task, currentCompanyId, accountId);
        }
    }

    /**
     * 方法描述：保存签发团队关系
     * 作者：MaoSF
     * 日期：2017/1/6
     *
     * @param:
     * @return:
     */
    private void saveProjectTaskRelation2(ProjectTaskEntity task, String currentCompanyId, String accountId) throws Exception {
        ProjectTaskRelationEntity relationEntity = new ProjectTaskRelationEntity();
        relationEntity.setId(StringUtil.buildUUID());
        relationEntity.setFromCompanyId(currentCompanyId);
        relationEntity.setToCompanyId(task.getCompanyId());
        relationEntity.setTaskId(task.getId());
        relationEntity.setCreateBy(accountId);
        this.projectTaskRelationDao.insert(relationEntity);

        this.saveProjectCost(task, currentCompanyId);//保存管理的费用

        //产生动态
      //  this.orgDynamicService.combinationDynamicForPartner(task.getProjectId(), currentCompanyId, task.getCompanyId(), task.getId(), accountId);
        //发送通知
        List<String> companyList = new ArrayList<String>();
        companyList.add(task.getCompanyId());
        this.projectService.sendMsgToRelationCompanyUser(companyList);
    }


    /**
     * 方法描述：任务发布后，给经营负责人推送任务
     * 作者：MaoSF
     * 日期：2017/5/16
     *
     * @param:
     * @return:
     */
    private void sendTaskToProjectManager(String projectId, String taskId, String companyId) throws Exception {
        ProjectMemberEntity projectManager = this.projectMemberService.getOperatorManager(projectId, companyId);
        if (projectManager == null) {
            //签发到的公司中选择具备经营权限的人员中选择第一个填入项目经营负责人位置
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("permissionId", "51");//经营总监权限id
            map.put("companyId", companyId);
            List<CompanyUserTableDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
            if (companyUserList.size() > 0) {
                CompanyUserTableDTO u = companyUserList.get(0);
                projectManager = this.projectMemberService.saveProjectMember(projectId, companyId, u.getId(), ProjectMemberType.PROJECT_OPERATOR_MANAGER, null, false);
            }
        }

        if (projectManager != null) {
            String managerId = projectManager.getCompanyUserId();
            //查询签发给的公司是否存在签发的记录
            List<ProjectTaskRelationEntity> taskRelationList = this.projectTaskRelationDao.getProjectTaskRelationByCompanyId(projectId, companyId);
            if (CollectionUtils.isEmpty(taskRelationList)) {//若果不存在签发的记录，则推送签发的任务
                // 发送任务
                this.myTaskService.saveMyTask(taskId, SystemParameters.ISSUE_TASK, companyId, managerId);

            } else {
                MessageEntity msg = getMessage(projectId, taskId, companyId, managerId);
                messageService.sendMessage(msg);
            }
        }
    }

    private void updateProjectTaskForPublish(ProjectTaskEntity taskEntity, ProjectTaskEntity publishTask, String currentCompanyId, String accountId) throws Exception {
        //保存原有数据
        TaskWithFullNameDTO origin = zInfoDAO.getTaskByTask(taskEntity);
        String companyId = taskEntity.getCompanyId();
        if (!companyId.equals(publishTask.getCompanyId())) {//如果更改了组织
            //删除任务（包含子任务）
            this.deleteProjectTask(taskEntity, accountId);

            //把部门设置为null
            this.projectTaskDao.updateTaskOrgId(taskEntity.getId());

            taskEntity.setCompanyId(publishTask.getCompanyId());//此处需要设置为发布后的组织id
            if (!publishTask.getCompanyId().equals(currentCompanyId)) {
                this.handleProjectTaskRelation(taskEntity, currentCompanyId, accountId);
            } else {
                this.sendTaskToDesigner(taskEntity.getProjectId(), taskEntity.getId(), taskEntity.getCompanyId(), currentCompanyId, accountId);
            }
        }
        //处理计划进度时间
        this.handleProcessTime(taskEntity, publishTask, !companyId.equals(publishTask.getCompanyId()));

        //更新
        String id = taskEntity.getId();

        String taskPath = taskEntity.getTaskPath();
        BaseDTO.copyFields(publishTask, taskEntity);
        taskEntity.setId(id);
        taskEntity.setTaskPath(taskPath);
        taskEntity.setTaskStatus(SystemParameters.TASK_STATUS_VALID); //再更新（由于上面taskStatus设置为1了，所以，此处设置为0.更新为有效）
        taskEntity.setTaskType(SystemParameters.TASK_TYPE_ISSUE);
        taskEntity.setCompleteDate(null);
        taskEntity.setBeModifyId(null);//此语句必须要设置为null,因为前面copy把beModifyId复制到taskEntity中了
        this.updateById(taskEntity);
        //获取修改后的数据
        TaskWithFullNameDTO target = zInfoDAO.getTaskByTask(taskEntity);
        //保存项目动态
        dynamicService.addDynamic(origin, target, currentCompanyId, accountId);
        //把状态更改为发布的状态
        publishTask.setTaskStatus(SystemParameters.TASK_STATUS_VALID);
        projectTaskDao.updateById(publishTask);
    }


    private void handleProcessTime(ProjectTaskEntity taskEntity, ProjectTaskEntity publishTask, boolean isChangeOrg) throws Exception {
        if(isChangeOrg){
            this.copyProcessTime(taskEntity,publishTask,true);
            return;
        }
//        if (isChangeOrg) {
//            //复制及添加时间
//            Map<String, Object> query = new HashMap<>();
//            query.put("targetId", publishTask.getId());
//            List<ProjectProcessTimeEntity> tps = projectProcessTimeDao.getProjectProcessTime(query);
//            for (ProjectProcessTimeEntity t : tps) {
//                saveTaskTime(taskEntity, t.getStartTime(), t.getEndTime(), t.getMemo(), SystemParameters.PROCESS_TYPE_PLAN);
//            }
//            return;
//        }
        //把计划进度时间全部复制到新的
        Map<String, Object> map = new HashMap<>();
        map.put("targetId", publishTask.getId());
        map.put("type", SystemParameters.PROCESS_TYPE_NOT_PUBLISH);
        List<ProjectProcessTimeEntity> processList = this.projectProcessTimeDao.getProjectProcessTime(map);
        if (!CollectionUtils.isEmpty(processList)) {
            for (ProjectProcessTimeEntity processTimeEntity : processList) {
                //1.把类型改为计划时间（未发布--计划)
                processTimeEntity.setType(SystemParameters.PROCESS_TYPE_PLAN);
                this.projectProcessTimeDao.updateById(processTimeEntity);
                //2.复制到
                processTimeEntity.setId(StringUtil.buildUUID());
                processTimeEntity.setTargetId(taskEntity.getId());
                this.projectProcessTimeDao.insert(processTimeEntity);
            }

            ProjectProcessTimeEntity processTime = processList.get(0);//只取第1条数据，因为是倒序排列，第一条数据是最新记录
            //查询签发的子任务，由于存在时间变更，需要通知子任务
            List<ProjectIssueTaskDTO> taskList = this.projectTaskDao.getTaskByTaskPidForChangeProcessTime(taskEntity.getId());
            for (ProjectIssueTaskDTO child : taskList) {
                if (StringUtil.isNullOrEmpty(child.getStartTime()) || !(DateUtils.datecompareDate(processTime.getStartTime(), child.getStartTime()) <= 0
                        && DateUtils.datecompareDate(processTime.getEndTime(), child.getEndTime()) >= 0)) {
                    //启动未发布状态
                    ProjectTaskEntity notPublishTask = new ProjectTaskEntity();
                    if (StringUtil.isNullOrEmpty(child.getId())) {//如果没有未发布数据
                        BaseDTO.copyFields(child, notPublishTask);//child中的数据是从发布版中获取的。
                        //则生成一条记录
                        notPublishTask.setId(StringUtil.buildUUID());
                        notPublishTask.setTaskStatus(SystemParameters.TASK_STATUS_MODIFIED);//未发布状态
                        notPublishTask.setTaskLevel(taskEntity.getTaskLevel() + 1);
                        notPublishTask.setTaskPath(taskEntity.getTaskPath() + "-" + notPublishTask.getId());
                        notPublishTask.setOrgId(child.getDepartId());
                        notPublishTask.setTaskType(SystemParameters.TASK_TYPE_MODIFY);
                        this.projectTaskDao.insert(notPublishTask);
                    } else {
                        notPublishTask.setId(child.getId());
                        notPublishTask.setTaskStatus(SystemParameters.TASK_STATUS_MODIFIED);//未发布状态
                        this.projectTaskDao.updateById(notPublishTask);
                    }
                    //2.复制到修改任务中，并且设置未未发布的版本
                    processTime.setId(StringUtil.buildUUID());
                    processTime.setTargetId(notPublishTask.getId());
                    processTime.setType(SystemParameters.PROCESS_TYPE_NOT_PUBLISH);//设置为未发布版本
                    this.projectProcessTimeDao.insert(processTime);
                }
            }

            //处理当前节点下生产任务的时间
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("taskPath", taskEntity.getId() + "-");
            objectMap.put("taskType", 0);
            List<ProjectTaskEntity> childTaskList = this.projectTaskDao.selectByParam(objectMap);
            for (ProjectTaskEntity child : childTaskList) {
                //2.复制到修改任务中，并且设置未未发布的版本
                processTime.setId(StringUtil.buildUUID());
                processTime.setTargetId(child.getId());
                processTime.setType(SystemParameters.PROCESS_TYPE_PLAN);//设置为计划进度
                this.projectProcessTimeDao.insert(processTime);
            }
        }
    }

    /**
     * 方法描述：经营板块的数据
     * 作者：MaoSF
     * 日期：2017/5/15
     */
    @Override
    public ResponseBean getOperatorTaskList(String projectId, String companyId) throws Exception {
        ProjectEntity projectEntity = this.projectDao.selectById(projectId);
        ProjectMemberDTO managerDTO = null;
        QueryProjectTaskDTO query = new QueryProjectTaskDTO();
        if (projectEntity != null) {
            //如果是立项方，则设置isCreator=1
            if (!projectEntity.getCompanyId().equals(companyId)) {
                query.setIsCooperator(1);
            }

            query.setProjectId(projectId);
            query.setCompanyId(companyId);
            List<ProjectIssueTaskDTO> taskList = this.projectTaskDao.getOperatorTaskList(query);
            //设置状态
            this.getIssueTaskState(taskList, companyId);
            //返回数据
            return ResponseBean.responseSuccess().addData("taskList", taskList);
        }
        return ResponseBean.responseError("查询失败");
    }


    private void getIssueTaskState(List<ProjectIssueTaskDTO> contentTaskList, String companyId) throws Exception {
        for (ProjectIssueTaskDTO issueTaskDTO : contentTaskList) {
            for (ProjectIssueTaskDTO dto : issueTaskDTO.getChildTaskList()) {
                if ("0".equals(dto.getTaskStatus())) {//已发布的数据
                    //设置状态
                    int taskState = getTaskState(dto.getBeModifyId());
                    dto.setTaskState(taskState);
                    String stateHtml = getStateHtml(dto.getEndTime(), dto.getStartTime(), dto.getCompleteDate(), taskState);
                    dto.setStateHtml(stateHtml);
                } else if ("2".equals(dto.getTaskStatus())) {//未发布状态
                    if (dto.getTaskType() == 3 && dto.getId().equals(dto.getBeModifyId())) {
                        dto.setStateHtml("未发布");
                        dto.setTaskState(7);
                    } else {//计算原来已经发布的数据
                        Map<String, Object> map = new HashMap<>();
                        map.put("targetId", dto.getBeModifyId());
                        List<ProjectProcessTimeEntity> processTimeList = this.projectProcessTimeDao.getProjectProcessTime(map);
                        String planStartTime = null;
                        String planEndTime = null;
                        if (!CollectionUtils.isEmpty(processTimeList)) {
                            planStartTime = processTimeList.get(0).getStartTime();
                            planEndTime = processTimeList.get(0).getEndTime();
                        }
                        int taskState = getTaskState(dto.getBeModifyId());
                        dto.setTaskState(taskState);
                        String stateHtml = getStateHtml(planEndTime, planStartTime, dto.getCompleteDate(), taskState);
                        dto.setStateHtml(stateHtml);
                    }
                }
            }
        }
    }


    /**
     * 方法描述：经营界面任务详情
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param id
     * @param companyId
     * @param:accountId
     * @return:
     */
    @Override
    public ProjectTaskForEditDTO getProjectTaskDetailForEdit(String id, String companyId, String accountId) throws Exception {
        ProjectTaskForEditDTO detailDTO = new ProjectTaskForEditDTO();
        Map<String, Object> map = new HashMap<>();
        ProjectTaskDTO projectTask = this.projectTaskDao.getProjectTaskByIdForEdit(id, 1);
        if (projectTask == null) return detailDTO;

        BaseDTO.copyFields(projectTask, detailDTO);
        ProjectMemberDTO managerDTO = this.projectMemberService.getOperatorManagerDTO(projectTask.getProjectId(), projectTask.getCompanyId());
        detailDTO.setManagerId(managerDTO.getCompanyUserId());
        detailDTO.setManagerName(managerDTO.getUserName());

        //设定约定时间
        map.clear();
        map.put("targetId", id);
        List<ProjectProcessTimeEntity> timeList = this.projectProcessTimeDao.getProjectProcessTime(map);
        detailDTO.setProjectProcessTimeList(timeList);

        //设置setIsHasProduct值
        String taskId = null;
        if (projectTask.getTaskType() == SystemParameters.TASK_TYPE_MODIFY) {
            if (!StringUtil.isNullOrEmpty(projectTask.getBeModifyId())) {
                taskId = projectTask.getBeModifyId();
            }
        } else {
            taskId = id;
        }
        if (!StringUtil.isNullOrEmpty(taskId)) {
            if (!CollectionUtils.isEmpty(this.projectTaskDao.getProjectTaskByPid2(taskId))
                    || !CollectionUtils.isEmpty(this.projectProcessNodeDao.selectNodeByTaskManageId(taskId))) {
                detailDTO.setIsHasProduct(1);
            }
        }
        return detailDTO;
    }

    /**
     * 方法描述：完成生产任务
     * 作者：MaoSF
     * 日期：2017/3/12
     *
     * @param projectId
     * @param taskId
     * @param companyId
     * @param accountId
     */
    @Override
    public ResponseBean completeProductTask(String projectId, String taskId, String companyId, String accountId) throws Exception {
        ProjectTaskEntity projectTaskEntity = this.projectTaskDao.selectById(taskId);
        if (projectTaskEntity == null) {
            return ResponseBean.responseError("操作失败");
        }
        //如果是处理完成
        //1.判断是否存在设校审，并且是是否完成
        Map<String, Object> map = new HashMap<>();
        map.put("taskManageId", taskId);
        map.put("notComplete", "1");//查询未完成的
        List<ProjectProcessNodeEntity> processNodeList = this.projectProcessNodeDao.getProcessNodeByParam(map);
        if (!CollectionUtils.isEmpty(processNodeList)) {
            String taskNames = projectTaskEntity.getTaskName() + "中";
            for (ProjectProcessNodeEntity entity : processNodeList) {
                taskNames += entity.getNodeName() + "、";
            }
            taskNames = taskNames.substring(0, taskNames.length() - 1);
            return ResponseBean.responseError(taskNames + "工作还未完成，该任务不能标记成完成状态。");
        }
        //2.判断子任务是否完成
        map.put("taskPid", taskId);
        List<ProjectTaskEntity> taskList = this.projectTaskDao.selectByParam(map);
        if (!CollectionUtils.isEmpty(taskList)) {
            String taskNames = "";
            for (ProjectTaskEntity entity : taskList) {
                taskNames += entity.getTaskName() + "、";
            }
            taskNames = taskNames.substring(0, taskNames.length() - 1);
            return ResponseBean.responseError("此任务中" + taskNames + "还未完成，该任务不能标记成完成状态");
        }
        //把任务设置为完成
        if (StringUtil.isNullOrEmpty(projectTaskEntity.getCompleteDate())) {
            //保存原有数据
            ProjectTaskEntity originTask = new ProjectTaskEntity();
            BeanUtilsEx.copyProperties(projectTaskEntity, originTask);

            projectTaskEntity.setCompleteDate(DateUtils.getDate());
            this.projectTaskDao.updateById(projectTaskEntity);
            //生成项目动态
            dynamicService.addDynamic(originTask, projectTaskEntity, companyId, accountId);
            /**************下面代码需要处理****************/
            if (!StringUtil.isNullOrEmpty(projectTaskEntity.getTaskPid())) {
                CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, companyId);
                this.handleProjectTaskCompleteDate(projectTaskEntity.getId(), companyUserEntity.getId());
            }
            /******************************/
            //通知协同
            this.collaborationService.pushSyncCMD_PT(projectTaskEntity.getProjectId(),projectTaskEntity.getTaskPath(),SyncCmd.PT2);
        }
        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 方法描述：对象信息复制（用于数据记录更新的时候，不存在被修改的记录，则产生一条永不修改的记录数据）
     * 作者：MaoSF
     * 日期：2017/6/21
     */
    @Override
    public String copyProjectTask(SaveProjectTaskDTO dto, ProjectTaskEntity issuedEntity) throws Exception {
        if (dto == null) {
            dto = new SaveProjectTaskDTO();
        }
        dto.setTaskType(4);
        saveNewIssueTask(dto, issuedEntity);
        this.projectMemberService.copyProjectMember(issuedEntity.getId(), dto.getId());
        return dto.getId();
    }

    @Override
    public ResponseBean updateByTaskIdStatus(String id, String taskStatus) throws Exception {
        ProjectTaskEntity taskEntity = new ProjectTaskEntity();
        taskEntity.setId(id);
        taskEntity.setTaskStatus(taskStatus);
        this.projectTaskDao.updateById(taskEntity);
        return ResponseBean.responseSuccess();
    }
}
