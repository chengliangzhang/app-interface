package com.maoding.project.service.impl;

import com.beust.jcommander.internal.Maps;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.BeanUtilsEx;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.dto.OrgDynamicDataDTO;
import com.maoding.dynamic.entity.OrgDynamicEntity;
import com.maoding.message.service.MessageService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.project.dao.*;
import com.maoding.project.dto.*;
import com.maoding.project.entity.*;
import com.maoding.project.service.ProjectDynamicsService;
import com.maoding.projectcost.dao.ProjectCostPaymentDetailDao;
import com.maoding.projectcost.dao.ProjectCostPointDao;
import com.maoding.projectcost.dao.ProjectCostPointDetailDao;
import com.maoding.projectcost.dto.ProjectCostDTO;
import com.maoding.projectcost.dto.ProjectCostPaymentDetailDTO;
import com.maoding.projectcost.dto.ProjectCostPointDTO;
import com.maoding.projectcost.dto.ProjectCostPointDetailDTO;
import com.maoding.projectcost.entity.*;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.task.dao.ProjectManagerDao;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dto.ProjectProcessTimeDTO;
import com.maoding.task.dto.SaveProjectTaskDTO;
import com.maoding.task.dto.TransferTaskDesignerDTO;
import com.maoding.task.entity.ProjectManagerEntity;
import com.maoding.task.entity.ProjectProcessTimeEntity;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.user.dao.UserDao;
import com.maoding.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Idccapp21 on 2017/3/2.
 */

@Service("projectDynamicsService")
public class ProjectDynamicsServiceImpl extends GenericService<ProjectDynamicsEntity> implements ProjectDynamicsService {
    @Autowired
    private ProjectDynamicsDao projectDynamicsDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ProjectCostPointDao projectCostPointDao;

    @Autowired
    private ProjectCostPaymentDetailDao projectCostPaymentDetailDao;

    @Autowired
    private ProjectProcessDao projectProcessDao;

    @Autowired
    private ProjectProcessNodeDao projectProcessNodeDao;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectCostPointDetailDao projectCostPointDetailDao;


    @Autowired
    private ProjectManagerDao projectManagerDao;

    @Autowired
    private MessageService messageService;

    private final String SEPARATOR = " ;";

    @Override
    public ResponseBean appAddProjectDynamic(InsertProjectDynamicDTO dto){
        return BeanUtilsEx.convertToResponseBean(addProjectDynamic(dto));
    }

    @Override
    public AjaxMessage addProjectDynamic(InsertProjectDynamicDTO dto){
        if ((dto == null) || (dto.getProjectId() == null) || (dto.getCompanyId() == null) || (dto.getCreateBy() == null))
            return AjaxMessage.failed("数据保存失败！");

        if (dto.getUserName() == null){
            dto.setUserName(getCompanyUserName(dto.getCompanyId(),dto.getCreateBy()));
        }

        ProjectDynamicsEntity entity = new ProjectDynamicsEntity();
        entity.setId(StringUtil.buildUUID());
        entity.setCompanyId(dto.getCompanyId());
        entity.setProjectId(dto.getProjectId());
        entity.setStatus(0);
        entity.setType(dto.getType());
        entity.setCompanyUserId(dto.getCompanyUserId());
        entity.setCreateBy(dto.getCreateBy());
        entity.setCreateDate(new Date());
        String content = dto.getUserName();
        content += SEPARATOR + dto.getNodeName();
        content += SEPARATOR + ((dto.getAction() != null) ? dto.getAction() : "");
        content += SEPARATOR + ((dto.getValue() != null) ? dto.getValue() : "");
        content += SEPARATOR + SEPARATOR + SEPARATOR +  dto.getType().toString();
        entity.setContent(content);
        int result = projectDynamicsDao.insert(entity);
        if (result > 0){
            return AjaxMessage.succeed("数据保存成功！");
        } else {
            return AjaxMessage.failed("数据保存失败！");
        }
    }

    @Override
    public ResponseBean getProjectDynamicByPage(Map<String, Object> paraMap)throws Exception{
        Object pageIndex=paraMap.get("pageIndex");
        if (null != pageIndex) {
            int page = (Integer) pageIndex;
            int pageSize = (Integer) paraMap.get("pageSize");
            paraMap.put("startPage", page * pageSize);
            paraMap.put("endPage", pageSize);
        }
        List<OrgDynamicDataDTO> data= projectDynamicsDao.getProjectDynamicsPage(paraMap);
        for(OrgDynamicDataDTO dto:data){
            convertDynamics(dto);
        }
        Map<String,Object> result= Maps.newHashMap();
        result.put("data",data);

        return  ResponseBean.responseSuccess("查询成功").setData(result);
    }

    private void convertDynamics(OrgDynamicDataDTO dto){
        List<OrgDynamicEntity> list = dto.getDynamicList();
        for(OrgDynamicEntity entity:list){
            if(SystemParameters.projectDynamic.get(entity.getDynamicType()+"")==null){
                continue;
            }

            String[] s = entity.getDynamicContent().split(SEPARATOR);
            entity.setDynamicTitle(s[0]);
            if(s.length>1)
            {
                List<String> list1 = Arrays.asList(s);
                List<String> list2 = new ArrayList<String>();
                for(int i=1;i<list1.size();i++){
                    list2.add(list1.get(i));
                }
                entity.setDynamicContent(StringUtil.format(SystemParameters.projectDynamic.get(entity.getDynamicType()+""),list2.toArray()));
//                if(entity.getDynamicType()==7){
//                    entity.setDynamicContent(StringUtil.format(SystemParameters.projectDynamic.get(entity.getDynamicType()+""),list1.toArray()));
//                }else {
//                    entity.setDynamicContent(StringUtil.format(SystemParameters.projectDynamic.get(entity.getDynamicType()+""),list2.toArray()));
//                }
            }else {
                entity.setDynamicContent(SystemParameters.projectDynamic.get(entity.getDynamicType()+""));
            }
        }
    }

    /**
     * 新建项目时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectDTO dto) {
        if (dto == null) return null;
        String pid = dto.getId();
        String cid = (dto.getCompanyId() != null) ? dto.getCompanyId() : dto.getCurrentCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_ADD_PROJECT);
        dyn.setNodeName(dto.getProjectName());
        return dyn;
    }

    /**
     * 更改项目基本信息时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectDTO dto, ProjectEntity entity) {
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        String pid = (dto.getId() != null) ? dto.getId() : entity.getId();
        String cid = (dto.getCompanyId() != null) ? dto.getCompanyId() :
                ((dto.getCurrentCompanyId() != null) ? dto.getCurrentCompanyId() : entity.getCompanyId());
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));
        String action = null;
        String value = null;
        if ((dto.getProjectName() != null) && !(dto.getProjectName().equals(entity.getProjectName()))) {
            action = "项目名";
            value = dto.getProjectName();
        }
        if ((dto.getInvestmentEstimation()!=null) && !(dto.getInvestmentEstimation().equals(entity.getInvestmentEstimation()))){
            action = "投资估算";
        }
        if ((dto.getConstructCompany()!=null) && !(dto.getConstructCompany().equals(entity.getConstructCompany()))){
            action = "甲方";
        }
        if ((dto.getCompanyBid()!=null) && !(dto.getCompanyBid().equals(entity.getCompanyBid()))){
            action = "乙方";
        }
        if ((dto.getProjectType() != null) && !(dto.getProjectType().equals(entity.getProjectType()))) {
            action = "设计范围";
        }
        if ((dto.getBaseArea()!=null) && !(dto.getBaseArea().equals(entity.getBaseArea()))){
            action = "基地面积";
        }
        if ((dto.getBuiltType()!=null) && !(dto.getBuiltType().equals(entity.getBuiltType()))){
            action = "功能分类";
        }
        if ((dto.getProjectNo()!=null) && !(dto.getProjectNo().equals(entity.getProjectNo()))){
            action = "项目编号";
        }
        if ((dto.getCapacityArea()!=null) && !(dto.getCapacityArea().equals(entity.getCapacityArea()))){
            action = "计容建筑面积";
        }
        if ((dto.getTotalConstructionArea()!=null) && !(dto.getTotalConstructionArea().equals(entity.getTotalConstructionArea()))){
            action = "总建筑面积";
        }
        if ((dto.getIncreasingArea()!=null) && !(dto.getIncreasingArea().equals(entity.getIncreasingArea()))){
            action = "核增面积";
        }
        if ((dto.getCoverage()!=null) && !(dto.getCoverage().equals(entity.getCoverage()))){
            action = "覆盖率";
        }
        if ((dto.getGreeningRate()!=null) && !(dto.getGreeningRate().equals(entity.getGreeningRate()))){
            action = "绿化率";
        }
        if ((dto.getBuiltHeight()!=null) && !(dto.getBuiltHeight().equals(entity.getBuiltHeight()))){
            action = "建筑高度";
        }
        if ((dto.getBuiltFloorUp()!=null) && !(dto.getBuiltFloorUp().equals(entity.getBuiltFloorUp()))){
            action = "建筑层数（地上）";
        }
        if ((dto.getBuiltFloorDown()!=null) && !(dto.getBuiltFloorDown().equals(entity.getBuiltFloorDown()))){
            action = "建筑层数（地下）";
        }
        if ((dto.getConstructCompany()!=null) && !(dto.getConstructCompany().equals(entity.getConstructCompany()))){
            action = "建设单位";
        }
        if ((dto.getTotalContractAmount()!=null) && !(dto.getTotalContractAmount().equals(entity.getTotalContractAmount()))){
            action = "合同总金额";
        }
        if ((dto.getStatus()!=null) && !(dto.getStatus().equals(entity.getStatus()))){
            action = "项目状态";
        }
        if (((dto.getProvince()!=null) && !(dto.getProvince().equals(entity.getProvince()))) ||
                (dto.getCity()!=null && !(dto.getCity().equals(entity.getCity()))) ||
                (dto.getCounty()!=null && !(dto.getCounty().equals(entity.getCounty()))) ||
                (dto.getDetailAddress()!=null && !(dto.getDetailAddress().equals(entity.getDetailAddress())))){
            action = "项目地点";
        }
        if ((dto.getVolumeRatio()!=null) && !(dto.getVolumeRatio().equals(entity.getVolumeRatio()))){
            action = "容积率";
        }
        if (!CollectionUtils.isEmpty(dto.getProjectDesignContentList())){
            action = "设计阶段";
        }
        if (!StringUtil.isNullOrEmpty(dto.getFilePath())){
            action = "合同扫描件";
        }
        if (!StringUtil.isNullOrEmpty(dto.getSignDate())){
            action = "合同签订";
        }
        if (!CollectionUtils.isEmpty(dto.getProjectDesignRangeList())){
            action = "设计范围";
        }
        if (action == null) return null;

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(entity.getProjectName());
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_PROJECT);
        dyn.setAction(action);
        dyn.setValue(value);
        return dyn;
    }

    /**
     * 添加设计人员时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(TransferTaskDesignerDTO dto){
        if (dto == null) return null;
        String pid = dto.getProjectId();
        String cid = dto.getCurrentCompanyId();
        String uid = dto.getAccountId();
        String rid = dto.getCompanyUserId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setCompanyUserId(rid);
        dyn.setNodeName(getProjectName(dyn.getProjectId()));
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_PROJECT);
        dyn.setAction("设计负责人");
        dyn.setValue(getCompanyUserName(dyn.getCompanyUserId()));
        return dyn;
    }

    /**
     * 修改设计人员时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(TransferTaskDesignerDTO dto, ProjectManagerEntity entity){
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        String pid = (dto.getProjectId() != null) ? dto.getProjectId() : entity.getProjectId();
        String cid = (dto.getCurrentCompanyId() != null) ? dto.getCurrentCompanyId() : entity.getCompanyId();
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));
        String rid = (dto.getCompanyUserId() != null) ? dto.getCompanyUserId() : entity.getCompanyUserId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setCompanyUserId(rid);
        dyn.setNodeName(getProjectName(dyn.getProjectId()));
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_PROJECT);
        if (entity.getType() == 1) {
            dyn.setAction("经营负责人");
        } else {
            dyn.setAction("设计负责人");
        }
        dyn.setValue(getCompanyUserName(dyn.getCompanyUserId()));
        return dyn;
    }

    private String getProjectName(String id){
        ProjectEntity pe = projectDao.selectById(id);
        return (pe != null) ? pe.getProjectName() : "未知";
    }

    private String getCompanyUserName(String rid, String cid, String uid){
        String un = "";
        if (!StringUtil.isNullOrEmpty(rid)){
            un = getCompanyUserName(rid);
        } else {
            un = getCompanyUserName(cid,uid);
        }
        return un;
    }
    private String getCompanyUserName(String cid, String uid){
        CompanyUserEntity cue = companyUserDao.getCompanyUserByUserIdAndCompanyId(uid,cid);
        return (cue != null) ? cue.getUserName() : getUserName(uid);
    }
    private String getCompanyUserName(String id){
        if (id == null) return "";
        CompanyUserEntity cue = companyUserDao.selectById(id);
        return (cue != null) ? cue.getUserName() : "";
    }
    private String getUserName(String id){
        if (id == null) return "";
        UserEntity ue = userDao.selectById(id);
        return (ue != null) ? ue.getUserName() : "";
    }
    /**
     * 删除项目时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectEntity entity, String accountId) {
        if ((entity == null) || (accountId == null)) return null;

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(entity.getId());
        dyn.setCompanyId(entity.getCompanyId());
        dyn.setCreateBy(accountId);
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_DEL_TASK);
        dyn.setNodeName(entity.getProjectName());
        return dyn;
    }
    /**
     * 分解签发任务，分解生产任务时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(SaveProjectTaskDTO dto){
        if (dto == null) return null;
        String pid = dto.getProjectId();
        String cid = dto.getCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);

        if (dto.getTaskType() == SystemParameters.TASK_TYPE_PHASE) return null;
        if (dto.getTaskType() == SystemParameters.TASK_TYPE_PRODUCT) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_ADD_TASK);
        } else {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_ADD_ISSUE_TASK);
            if (!StringUtil.isNullOrEmpty(dyn.getCompanyId())) {
                CompanyEntity ce = companyDao.selectById(dto.getCompanyId());
                if (ce != null) {
                    dyn.setAction(ce.getCompanyName());
                }
            }
        }
        dyn.setNodeName(getTaskTreeName(dto.getTaskPid(),dto.getTaskName()));
        return dyn;
    }
    /**
     * 更改任务名，更改设计组织时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(SaveProjectTaskDTO dto, ProjectTaskEntity entity){
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        String pid = (dto.getProjectId() != null) ? dto.getProjectId() : entity.getProjectId();
        String cid = (dto.getCompanyId() != null) ? dto.getCompanyId() : entity.getCompanyId();
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);

        if (entity.getTaskType() == SystemParameters.TASK_TYPE_PHASE) return null;
        if (entity.getTaskType() == SystemParameters.TASK_TYPE_PRODUCT) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_TASK);
        } else {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_ISSUE_TASK);
        }
        dyn.setNodeName(getTaskTreeName(entity.getId()));
        if (!StringUtil.isNullOrEmpty(dto.getTaskName())) {
            dyn.setAction("名称");
            dyn.setValue(getTaskTreeName(entity.getTaskPid(), dto.getTaskName()));
        } else if (!StringUtil.isNullOrEmpty(dto.getCompanyId())) {
            dyn.setAction("设计组织");
            dyn.setValue(getCompanyName(dto.getCompanyId()));
        }
        return dyn;
    }
    private String getCompanyName(String id){
        CompanyEntity ce = companyDao.selectById(id);
        return (ce != null) ? ce.getCompanyName() : "未知";
    }
    private String getTaskTreeName(String id){
        return projectTaskDao.getTaskParentName(id);
    }
    private String getTaskTreeName(String id, String sub){
        String tree = getTaskTreeName(id);
        if (!StringUtil.isNullOrEmpty(tree)){
            tree += " — ";
        }
        tree += (!StringUtil.isNullOrEmpty(sub)) ? sub : "子任务";
        return tree;
    }

    /**
     * 删除签发任务，删除生产任务时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectTaskEntity entity, String accountId,String currentCompanyId) {
        if ((entity == null) || (accountId == null)) return null;

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(entity.getProjectId());
        dyn.setCompanyId(currentCompanyId);
        dyn.setCreateBy(accountId);
        if (entity.getTaskPid() == null) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_DEL_PHASE_TASK);
            dyn.setNodeName(entity.getTaskName());
        } else {
            if (entity.getTaskType() == SystemParameters.TASK_TYPE_PRODUCT) {
                dyn.setType(SystemParameters.PROJECT_DYNAMIC_DEL_TASK);
            } else {
                dyn.setType(SystemParameters.PROJECT_DYNAMIC_DEL_ISSUE_TASK);
            }
            dyn.setNodeName(projectTaskDao.getTaskParentName(entity.getId()));
        }
        return dyn;
    }

    /**
     * 初始化任务时间时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectProcessTimeDTO dto) {
        if (dto == null)  return null;
        String tid = dto.getTargetId();
        ProjectTaskEntity task_entity = projectTaskDao.selectById(tid);
        if (task_entity == null) return null;
        String pid = task_entity.getProjectId();
        String cid = (dto.getCompanyId() != null) ? dto.getCompanyId() : dto.getCurrentCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        setDynamicByTaskType(dyn,task_entity.getTaskType(),pid,tid);
        dyn.setValue((dto.getMemo() != null) ? dto.getMemo() : "");
        return dyn;
    }
    /**
     * 更改任务时间时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectProcessTimeDTO dto, ProjectProcessTimeEntity entity) {
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        String tid = (dto.getTargetId() != null) ? dto.getTargetId() : entity.getTargetId();
        ProjectTaskEntity task_entity = projectTaskDao.selectById(tid);
        if (task_entity == null) return null;
        String pid = task_entity.getProjectId();
        String cid = ((dto.getCompanyId() != null) ? dto.getCompanyId() :
                ((dto.getCurrentCompanyId() != null) ? dto.getCurrentCompanyId() : entity.getCompanyId()));
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        setDynamicByTaskType(dyn,task_entity.getTaskType(),pid,tid);
        dyn.setValue((dto.getMemo() != null) ? dto.getMemo() : "");
        return dyn;
    }

    private void setDynamicByTaskType(InsertProjectDynamicDTO dyn, Integer taskType, String pid, String tid){
        if (taskType == SystemParameters.TASK_TYPE_PHASE ) { //项目阶段
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_PROJECT);
            dyn.setNodeName(getProjectName(pid));
            dyn.setAction("合同进度");
        } else {
            if (taskType == SystemParameters.TASK_TYPE_PRODUCT) { //生产任务
                dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_TASK);
            } else { //签发任务
                dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_ISSUE_TASK);
            }
            dyn.setNodeName(getTaskTreeName(tid));
            dyn.setAction("计划进度");
        }
    }

    /**
     * 添加任务负责人
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectTaskResponsibleDTO dto) {
        if (dto == null) return null;
        String tid = dto.getTaskId();
        ProjectTaskEntity task_entity = projectTaskDao.selectById(tid);
        if (task_entity == null) return null;
        String pid = (dto.getProjectId() != null) ? dto.getProjectId() : task_entity.getProjectId();
        String cid = (dto.getCompanyId() != null) ? dto.getCompanyId() : dto.getCurrentCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(getTaskTreeName(tid));
        if (task_entity.getTaskType() == SystemParameters.TASK_TYPE_PRODUCT) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_TASK);
        } else {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_ISSUE_TASK);
        }
        dyn.setAction("任务负责人");
        dyn.setValue(getResponsibleName(dto.getTaskId()));
        return dyn;
    }

    /**
     * 更改任务负责人
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectTaskResponsibleDTO dto, ProjectTaskResponsiblerEntity entity) {
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        String tid = (dto.getTaskId() != null) ? dto.getTaskId() : entity.getTaskId();
        ProjectTaskEntity task_entity = projectTaskDao.selectById(tid);
        if (task_entity == null) return null;
        String pid = (dto.getProjectId() != null) ? dto.getProjectId() : task_entity.getProjectId();
        String cid = ((dto.getCompanyId() != null) ? dto.getCompanyId() :
                ((dto.getCurrentCompanyId() != null) ? dto.getCurrentCompanyId() : entity.getCompanyId()));
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(getTaskTreeName(tid));
        if (task_entity.getTaskType() == 0) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_TASK);
        } else {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_ISSUE_TASK);
        }
        dyn.setAction("任务负责人");
        dyn.setValue(getResponsibleName(entity.getTaskId()));
        return dyn;
    }

    private String getResponsibleName(String taskId) {
        try {
            ProjectMemberDTO memberDTO = projectMemberService.getTaskDesignerDTO(taskId);
            if (memberDTO == null) return "";
            return memberDTO.getCompanyUserName();
        }catch (Exception e){

        }

        return "";
    }
    /**
     * 添加任务参与人时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectProcessDTO dto){
        if (dto == null) return null;
        String tid = dto.getTaskId();
        ProjectTaskEntity task_entity = projectTaskDao.selectById(tid);
        if (task_entity == null) return null;
        String pid = (dto.getProjectId() != null) ? dto.getProjectId() : task_entity.getProjectId();
        String cid = ((dto.getCurrentCompanyId() != null) ? dto.getCurrentCompanyId() : dto.getCompanyId());
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(getTaskTreeName(tid));
        if (task_entity.getTaskType() == 0) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_TASK);
        } else {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_ISSUE_TASK);
        }
        dyn.setAction("参与人员");
        dyn.setValue(getTeamPersons(tid));
        return dyn;
    }
    /**
     * 更改任务参与人时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectProcessDTO dto, ProjectProcessEntity entity){
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        String tid = (dto.getTaskId() != null) ? dto.getTaskId() : entity.getTaskManageId();
        ProjectTaskEntity task_entity = projectTaskDao.selectById(tid);
        if (task_entity == null) return null;
        String pid = (dto.getProjectId() != null) ? dto.getProjectId() : task_entity.getProjectId();
        String cid = ((dto.getCurrentCompanyId() != null) ? dto.getCurrentCompanyId() :
                ((dto.getCompanyId() != null) ? dto.getCompanyId() : entity.getCompanyId()));
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(getTaskTreeName(tid));
        if (task_entity.getTaskType() == SystemParameters.TASK_TYPE_PRODUCT) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_TASK);
        } else {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_ISSUE_TASK);
        }
        dyn.setAction("参与人员");
        dyn.setValue(getTeamPersons(tid));
        return dyn;
    }
    private String getTeamPersons(String taskManageId){
        return  projectMemberService.getDesignUserByTaskId(taskManageId);
//        List<ProjectProcessDataDTO> processList = projectProcessDao.selectByTaskManageId(taskManageId);
//        if (processList == null) return "";
//        String designPerson="";
//        String proofreading="";
//        String review="";
//        for (ProjectProcessDataDTO process : processList) {
//            List<ProjectProcessNodeEntity> processNodeList = projectProcessNodeDao.selectByProcessId(process.getId());
//            if (processNodeList == null) return "";
//            for (ProjectProcessNodeEntity node : processNodeList) {
//                if (!StringUtil.isNullOrEmpty(node.getCompanyUserId())) {
//                    if ("设计".equals(node.getNodeName())) {
//                        if (StringUtil.isNullOrEmpty(designPerson)) {
//                            designPerson = "设计人：" + getCompanyUserName(node.getCompanyUserId());
//                        } else {
//                            designPerson += "、" + getCompanyUserName(node.getCompanyUserId());
//                        }
//                    } else if ("校对".equals(node.getNodeName())) {
//                        if (StringUtil.isNullOrEmpty(proofreading)) {
//                            proofreading = "校对人：" + getCompanyUserName(node.getCompanyUserId());
//                        } else {
//                            proofreading += "、" + getCompanyUserName(node.getCompanyUserId());
//                        }
//                    } else if ("审核".equals(node.getNodeName())) {
//                        if (StringUtil.isNullOrEmpty(review)) {
//                            review = "审核人：" + getCompanyUserName(node.getCompanyUserId());
//                        } else {
//                            review += "、" + getCompanyUserName(node.getCompanyUserId());
//                        }
//                    }
//                }
//            }
//        }
//        String persons = "";
//        if (!StringUtil.isNullOrEmpty(designPerson)) {
//            if (!StringUtil.isNullOrEmpty(persons)) {
//                persons += "，";
//            }
//            persons += designPerson;
//        }
//        if (!StringUtil.isNullOrEmpty(proofreading)) {
//            if (!StringUtil.isNullOrEmpty(persons)) {
//                persons += "，";
//            }
//            persons += proofreading;
//        }
//        if (!StringUtil.isNullOrEmpty(review)) {
//            if (!StringUtil.isNullOrEmpty(persons)) {
//                persons += "，";
//            }
//            persons += review;
//        }
//        return persons;
    }
    /**
     * 增加项目费用时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostDTO dto) {
        if (dto == null) return null;
        String pid = dto.getProjectId();
        String cid = dto.getCurrentCompanyId();
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((dto.getUpdateBy() != null) ? dto.getUpdateBy() : dto.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(getFeeTypeString(dto.getType()));
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_FEE);
        dyn.setAction("总金额");
        dyn.setValue((dto.getFee() != null) ? dto.getFee().toString() : "");
        return dyn;
    }
    /**
     * 更改项目费用时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostDTO dto, ProjectCostEntity entity) {
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        String pid = (dto.getProjectId() != null) ? dto.getProjectId() : entity.getProjectId();
        String cid = dto.getCurrentCompanyId();
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((dto.getUpdateBy() != null) ? dto.getUpdateBy() :
                        ((dto.getCreateBy() != null) ? dto.getCreateBy() :
                                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()))));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName("总金额");
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_FEE);
        dyn.setAction(getFeeTypeString(dto.getType()));
        dyn.setValue((dto.getFee() != null) ? dto.getFee().toString() : "");
        return dyn;
    }
    private String getFeeTypeString(String type){
        if (Integer.parseInt(type) == 1) {
            return "合同回款";
        } else if (Integer.parseInt(type) == 2){
            return "技术审查费";
        } else if (Integer.parseInt(type) == 3){
            return "合作设计费";
        } else {
            return "其他";
        }
    }
    /**
     * 添加款项节点时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDTO dto) {
        if (dto == null) return null;
        String pid = dto.getProjectId();
        String cid = dto.getCurrentCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);

        if (Integer.parseInt(dto.getType()) == 1) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_ADD_CONTRACT_POINT);
        } else if (Integer.parseInt(dto.getType()) == 2) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_ADD_DESIGN_POINT);
        } else if (Integer.parseInt(dto.getType()) == 3) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_ADD_COOPERATOR_POINT);
        } else if (Integer.parseInt(dto.getType()) == 4) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_ADD_OTHER_PAY_POINT);
        } else {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_ADD_OTHER_DEBIT_POINT);
        }
        dyn.setNodeName(dto.getFeeDescription());
        return dyn;
    }
    /**
     * 更改款项节点时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDTO dto, ProjectCostPointEntity entity) {
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        String pid = (dto.getProjectId() != null) ? dto.getProjectId() : entity.getProjectId();
        String cid = dto.getCurrentCompanyId();
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);

        dyn.setNodeName(entity.getFeeDescription());
        dyn.setType(getPointChangeType(entity.getType()));
        if (!StringUtil.isNullOrEmpty(dto.getFeeDescription())){
            dyn.setAction("名称");
        }
        if (dto.getFee() != null){
            dyn.setAction("金额");
        }
        return dyn;
    }

    private int getPointChangeType(String type){
        if (Integer.parseInt(type) == 1) {
            return SystemParameters.PROJECT_DYNAMIC_CHANGE_CONTRACT_POINT;
        } else if (Integer.parseInt(type) == 2) {
            return SystemParameters.PROJECT_DYNAMIC_CHANGE_DESIGN_POINT;
        } else if (Integer.parseInt(type) == 3) {
            return SystemParameters.PROJECT_DYNAMIC_CHANGE_COOPERATOR_POINT;
        } else if (Integer.parseInt(type) == 4) {
            return SystemParameters.PROJECT_DYNAMIC_CHANGE_OTHER_PAY_POINT;
        } else {
            return SystemParameters.PROJECT_DYNAMIC_CHANGE_OTHER_DEBIT_POINT;
        }
    }

    /**
     * 删除款项节点时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointEntity entity, String accountId) {
        if ((entity == null) || (accountId == null)) return null;

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(entity.getProjectId());
        ProjectEntity project = projectDao.selectById(entity.getProjectId());
        if (project == null) return null;
        dyn.setCompanyId(project.getCompanyId());
        dyn.setCreateBy(accountId);
        if (Integer.parseInt(entity.getType()) == 1) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_DEL_CONTRACT_POINT);
        } else if (Integer.parseInt(entity.getType()) == 2) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_DEL_DESIGN_POINT);
        } else if (Integer.parseInt(entity.getType()) == 3) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_DEL_COOPERATOR_POINT);
        } else if (Integer.parseInt(entity.getType()) == 4) {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_DEL_OTHER_PAY_POINT);
        } else {
            dyn.setType(SystemParameters.PROJECT_DYNAMIC_DEL_OTHER_DEBIT_POINT);
        }
        dyn.setNodeName(entity.getFeeDescription());
        return dyn;
    }
    /**
     * 发起收款调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDetailDTO dto) {
        if (dto == null) return null;
        ProjectCostPointEntity point = projectCostPointDao.selectById(dto.getPointId());
        if (point == null) return null;
        ProjectEntity project = projectDao.selectById(point.getProjectId());
        if (project == null) return null;
        String cid = dto.getCurrentCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(project.getId());
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(point.getFeeDescription());
        dyn.setType(getPointChangeType(point.getType()));
        dyn.setAction(getCostType(point.getType(),cid,project.getCompanyBid(),null) +
                ((Integer.parseInt(point.getType())!=4)?"通知":"申请"));
        if (dto.getFee() != null) {
            dyn.setValue(dto.getFee().toString());
        }
        return dyn;
    }

    /**
     * 删除项目费用细项时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDetailEntity entity, String accountId) {
        if ((entity == null) || (accountId == null)) return null;
        ProjectCostPointDetailEntity pointDetail = projectCostPointDetailDao.selectById(entity.getId());
        if (pointDetail == null) return null;
        ProjectCostPointEntity point = projectCostPointDao.selectById(entity.getPointId());
        if (point == null) return null;
        ProjectEntity project = projectDao.selectById(pointDetail.getProjectId());
        if (project == null) return null;
        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(project.getId());
        dyn.setCompanyId(project.getCompanyId());
        dyn.setCreateBy(accountId);
        dyn.setNodeName(point.getFeeDescription());
        dyn.setType(getPointChangeType(point.getType()));
        dyn.setAction(getCostType(point.getType(),null,project.getCompanyBid(),null) +
                ((Integer.parseInt(point.getType())!=4)?"通知":"申请"));
        if (entity.getFee() != null){
            dyn.setValue(entity.getFee().toString());
        }
        return dyn;
    }
    /**
     * 更改发起收款时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDetailDTO dto, ProjectCostPointDetailEntity entity) {
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        ProjectCostPointEntity point = projectCostPointDao.selectById(entity.getPointId());
        if (point == null) return null;
        ProjectEntity project = projectDao.selectById(point.getProjectId());
        if (project == null) return null;
        String cid = dto.getCurrentCompanyId();
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(project.getId());
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(point.getFeeDescription());
        dyn.setType(getPointChangeType(point.getType()));
        dyn.setAction(getCostType(point.getType(),cid,project.getCompanyBid(),null) +
                ((Integer.parseInt(point.getType())!=4)?"通知":"申请"));
        if (entity.getFee() != null){
            dyn.setValue(entity.getFee().toString());
        }
        if (dto.getFee() != null){
            dyn.setValue(dto.getFee().toString());
        }
        return dyn;
    }

    /**
     * 添加项目到款信息时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPaymentDetailDTO dto) {
        if (dto == null) return null;
        ProjectCostPointDetailEntity detail = projectCostPointDetailDao.selectById(dto.getPointDetailId());
        if (detail == null) return null;
        ProjectCostPointEntity point = projectCostPointDao.selectById(detail.getPointId());
        if (point == null) return null;
        ProjectEntity project = projectDao.selectById(point.getProjectId());
        if (project == null) return null;
        String cid = dto.getCurrentCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(project.getId());
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(point.getFeeDescription());
        dyn.setType(getPointChangeType(point.getType()));
        dyn.setAction(getCostType(point.getType(),cid,project.getCompanyBid(),dto.getPaidDate()) + "明细");
        if (dto.getFee() != null) {
            dyn.setValue(dto.getFee().toString());
        }
        return dyn;
    }

    /**
     * 更改项目到款信息时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPaymentDetailDTO dto, ProjectCostPaymentDetailEntity entity) {
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        ProjectCostPointDetailEntity detail = projectCostPointDetailDao.selectById(entity.getPointDetailId());
        if (detail == null) return null;
        ProjectCostPointEntity point = projectCostPointDao.selectById(detail.getPointId());
        if (point == null) return null;
        ProjectEntity project = projectDao.selectById(point.getProjectId());
        if (project == null) return null;
        String cid = dto.getCurrentCompanyId();
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(project.getId());
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(point.getFeeDescription());
        dyn.setType(getPointChangeType(point.getType()));
        dyn.setAction(getCostType(point.getType(),cid,project.getCompanyBid(),dto.getPaidDate()) + "明细");
        if (dto.getFee() != null){
            dyn.setValue(dto.getFee().toString());
        }
        if (dto.getPaidDate() != null){
            dyn.setValue(dto.getPaidDate());
        }
        if (dto.getPayDate() != null){
            dyn.setValue(dto.getPayDate());
        }
        return dyn;
    }

    /**
     * 删除项目到款信息时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPaymentDetailEntity entity, String accountId) {
        if ((entity == null) || (accountId == null)) return null;
        ProjectCostPointDetailEntity detail = projectCostPointDetailDao.selectById(entity.getPointDetailId());
        if (detail == null) return null;
        ProjectCostPointEntity point = projectCostPointDao.selectById(detail.getPointId());
        if (point == null) return null;
        ProjectEntity project = projectDao.selectById(point.getProjectId());
        if (project == null) return null;

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(project.getId());
        dyn.setCompanyId(project.getCompanyId());
        dyn.setCreateBy(accountId);
        dyn.setNodeName(point.getFeeDescription());
        dyn.setType(getPointChangeType(point.getType()));
        dyn.setAction(getCostType(point.getType(),project.getCompanyId(),project.getCompanyBid(),entity.getPaidDate()) + "明细");
        if (entity.getFee() != null){
            dyn.setValue(entity.getFee().toString());
        }
        return dyn;
    }
    /**
     * 确认到款时调用
     */
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostOperaterEntity dto, String fee, String accountId){
        if ((dto == null) || (accountId == null)) return null;
        ProjectCostPaymentDetailEntity entity = projectCostPaymentDetailDao.selectById(dto.getCostDetailId());
        if (entity == null) return null;
        ProjectCostPointDetailEntity detail = projectCostPointDetailDao.selectById(entity.getPointDetailId());
        if (detail == null) return null;
        ProjectCostPointEntity point = projectCostPointDao.selectById(detail.getPointId());
        if (point == null) return null;

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(point.getProjectId());
        dyn.setCompanyUserId(dto.getCompanyUserId());
        dyn.setCreateBy(accountId);
        dyn.setType(getPointChangeType(point.getType()));
        dyn.setNodeName(point.getFeeDescription());
        dyn.setAction(getCostType(point.getType(),null,null,entity.getPaidDate()) + "明细");
        dyn.setValue(fee);
        return dyn;
    }

    /**
     * 获取费用类型
     * @param type 费用节点类型
     * @param cId 当前公司ID
     * @param bId 项目乙方ID
     * @param paidDate 到账日期
     * @return 费用类型
     */
    private String getCostType(String type, String cId, String bId, String paidDate){
        switch (Integer.parseInt(type)){
            case 1: //合同回款确认
                return "回款";
            case 2:
            case 3:
                if ((cId == null) || (cId.equals(bId) || (paidDate != null)))
                    return "收款"; //确认到账
                else
                    return "付款"; //支付
            case 4:
                return "付款"; //其他费用（付款）
            case 5:
                return "收款"; //其他费用（收款）
            default:
                return "";
        }
    }

    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectSkyDriveDTO dto){
        if (dto == null) return null;
        String pid = dto.getProjectId();
        String cid = dto.getCurrentCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_UPLOAD_FILE);
        dyn.setNodeName(dto.getFileName());
        return dyn;
    }
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectSkyDriveDTO dto, ProjectSkyDriveEntity entity){
        if (dto == null) return null;
        if (entity == null) return getInsertProjectDynamicDTO(dto);
        String pid = (entity.getProjectId() != null) ? entity.getProjectId() : dto.getProjectId();
        String cid = (entity.getCompanyId() != null) ? entity.getCompanyId() : dto.getCurrentCompanyId();
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(entity.getFileName());
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_UPDATE_FILE);
        dyn.setAction(dto.getFileName());
        return dyn;
    }
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectSkyDriveRenameDTO dto, ProjectSkyDriveEntity entity){
        if ((dto == null) || (entity == null)) return null;
        String pid = entity.getProjectId();
        String cid = (entity.getCompanyId() != null) ? entity.getCompanyId() : dto.getCurrentCompanyId();
        String uid = ((dto.getAccountId() != null) ? dto.getAccountId() :
                ((entity.getUpdateBy() != null) ? entity.getUpdateBy() : entity.getCreateBy()));

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setNodeName(entity.getFileName());
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_UPDATE_FILE);
        dyn.setAction(dto.getFileName());
        return dyn;
    }
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectSkyDriveEntity entity, String accountId) {
        if ((entity == null) || (accountId == null)) return null;

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(entity.getProjectId());
        dyn.setCompanyId(entity.getCompanyId());
        dyn.setCreateBy(accountId);
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_DELETE_FILE);
        dyn.setNodeName(entity.getFileName());
        return dyn;
    }
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectDesignContentDTO  dto){
        if (dto == null) return null;

        String pid = dto.getProjectId();
        String cid = dto.getCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_ADD_PHASE_TASK);
        dyn.setNodeName(dto.getContentName());
        return dyn;
    }
    @Override
    public InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectDesignContentDTO  dto, ProjectDesignContentEntity entity){
        if (dto == null) return null;

        String pid = dto.getProjectId();
        String cid = dto.getCompanyId();
        String uid = dto.getAccountId();

        InsertProjectDynamicDTO dyn = new InsertProjectDynamicDTO();
        dyn.setProjectId(pid);
        dyn.setCompanyId(cid);
        dyn.setCreateBy(uid);
        dyn.setType(SystemParameters.PROJECT_DYNAMIC_CHANGE_PHASE_TASK);
        dyn.setNodeName(entity.getContentName());
        dyn.setAction(dto.getContentName());
        return dyn;
    }
}
