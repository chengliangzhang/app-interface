package com.maoding.process.service.impl;

import com.maoding.activiti.dto.FlowTaskDTO;
import com.maoding.activiti.dto.ProcessDetailPrepareDTO;
import com.maoding.activiti.dto.UserTaskDTO;
import com.maoding.activiti.dto.WorkActionDTO;
import com.maoding.activiti.service.WorkflowService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.NewBaseService;
import com.maoding.core.constant.ProcessTypeConst;
import com.maoding.core.constant.ProjectCostConst;
import com.maoding.core.util.StringUtil;
import com.maoding.financial.dto.AuditDTO;
import com.maoding.financial.dto.AuditEditDTO;
import com.maoding.financial.dto.SaveExpMainDTO;
import com.maoding.financial.entity.ExpMainEntity;
import com.maoding.financial.service.ExpAuditService;
import com.maoding.financial.service.ExpMainService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.process.dao.ProcessInstanceRelationDao;
import com.maoding.process.dao.ProcessTypeDao;
import com.maoding.process.dto.ActivitiDTO;
import com.maoding.process.dto.TaskDTO;
import com.maoding.process.dto.UserTaskNodeDTO;
import com.maoding.process.entity.ProcessInstanceRelationEntity;
import com.maoding.process.entity.ProcessTypeEntity;
import com.maoding.process.service.ProcessService;
import com.maoding.projectcost.dto.ProjectCostPointDetailDTO;
import com.maoding.projectcost.service.ProjectCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * activiti Service
 * 流程定义，部署，启动,任务 处理接口实现
 */
@Service("processService")
public class ProcessServiceImpl extends NewBaseService implements ProcessService {

    @Autowired
    private ProcessInstanceRelationDao processInstanceRelationDao;

    @Autowired
    private ExpAuditService expAuditService;

    @Autowired
    private ExpMainService expMainService;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProcessTypeDao processTypeDao;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private ProjectCostService projectCostService;

    private static final String auditIdKey = "auditId";

    @Override
    public void startProcessInstance(ActivitiDTO dto) throws Exception {
        String processInstanceId = null;
        //获取流程的key值，确定启动哪个流程
        String processKey = this.getProcessKey(dto.getTargetType(),dto.getCurrentCompanyId());
        if(processKey != null){
            //启动流程
            WorkActionDTO workAction = new WorkActionDTO();
            workAction.setResultMap(dto.getParam());
            workAction.setKey(processKey);
            workAction.setBusinessKey(dto.getBusinessKey());
            workAction.setCompanyUserId(dto.getCompanyUserId());
            workAction.setResultMap(dto.getParam());
            workAction.setCurrentCompanyId(dto.getCurrentCompanyId());
            processInstanceId = workflowService.startProcess(workAction).getId();
            //保存审核记录
            saveAudit(dto.getBusinessKey(),dto,true);
            //保存流程实例与业务表的关系
            ProcessInstanceRelationEntity instanceRelation = new ProcessInstanceRelationEntity(dto.getBusinessKey(),processInstanceId,dto.getTargetType());
            processInstanceRelationDao.insert(instanceRelation);
        }
    }

    @Override
    public boolean isNeedStartProcess(ActivitiDTO dto) throws Exception {
        //获取流程的key值，确定启动哪个流程
        String processKey = this.getProcessKey(dto.getTargetType(),dto.getCurrentCompanyId());
        if(ProcessTypeConst.PROCESS_TYPE_FREE.equals(processKey)
                && (!dto.getParam().containsKey("approveUser") || StringUtil.isNullOrEmpty(dto.getParam().get("approveUser")))){
            return false;
        }
        return true;
    }

    /**
     *
     * @param businessKey:主业务id（比如报销单id）
     * @param dto：从前端传递过来的基础参数
     * @param isPass ：当前审批是否通过
     * @description ：完成该任务后，如果不是结束流程，则存在下一个任务，为下一个审批人增加一条审批记录(为了兼容原有的做法，并且不改动原来的查询)
     * 如果审核通过，并且已经结束流程，则把审批的主记录设置为审批通过状态
     * 如果没有审批通过，则把当前记录设置为退回状态
     */
    private void saveAudit(String businessKey,BaseDTO dto,boolean isPass) throws Exception{
        List<FlowTaskDTO> taskList = null;
        if(isPass){
            taskList = workflowService.listWorkTask(businessKey);
            //处理 getVariables ，创建下一个人要处理的任务，并且返回任务的id，可能是多个任务的id。把返回的任务id 放入 dto.getVariables() 中进行保存
            for(FlowTaskDTO task:taskList){
                // todo 处理审核记录
                SaveExpMainDTO saveExpMain = new SaveExpMainDTO();
                saveExpMain.setCompanyUserId(dto.getCurrentCompanyUserId());
                saveExpMain.setAuditPerson(task.getAssignee().getId());
                saveExpMain.setAppOrgId(dto.getAppOrgId());
                saveExpMain.setAccountId(dto.getAccountId());
                saveExpMain.setId(businessKey);
                String auditId = this.expAuditService.saveAudit(saveExpMain);
                Map<String,Object> map = new HashMap<>();
                map.put(auditIdKey,auditId);
                workflowService.setTaskVariables(task.getId(),map);
            }
        }

        //没有任务，说明流程已经结束
        if(isPass && CollectionUtils.isEmpty(taskList)){
            ExpMainEntity exp = this.expMainService.selectById(businessKey);
            exp.setId(businessKey);
            exp.setApproveStatus("1");//审批通过的状态
            this.expMainService.updateById(exp);
            if(exp.getType()==5){
                //推送任务
                ProjectCostPointDetailDTO projectCostPointDetailDTO = new ProjectCostPointDetailDTO();
                projectCostPointDetailDTO.setMainId(businessKey);
                projectCostPointDetailDTO.setFeeStatus(ProjectCostConst.FEE_STATUS_APPROVE);
                projectCostService.completeProjectFeeApply(projectCostPointDetailDTO);
            }
        }
        if(!isPass){
            ExpMainEntity exp = this.expMainService.selectById(businessKey);
            exp.setId(businessKey);
            exp.setApproveStatus("2");//退回的状态
            this.expMainService.updateById(exp);
            if(exp.getType()==5){
                //推送任务
                ProjectCostPointDetailDTO projectCostPointDetailDTO = new ProjectCostPointDetailDTO();
                projectCostPointDetailDTO.setMainId(businessKey);
                projectCostPointDetailDTO.setFeeStatus(ProjectCostConst.FEE_STATUS_NOT_APPROVE);
                projectCostService.completeProjectFeeApply(projectCostPointDetailDTO);
            }
        }
    }

    /**
     * 根据类型获取流程的Key值
     */
    private String getProcessKey(String targetType,String companyId){
        //如果是自由流程，则启动系统中的默认的流程
        ProcessTypeEntity processType = this.processTypeDao.getCurrentProcessType(companyId,targetType);
        if(processType==null || ProcessTypeConst.TYPE_FREE == processType.getType()){
            return ProcessTypeConst.PROCESS_TYPE_FREE;
        }
        return "p_"+processType.getCompanyId()+"_"+processType.getTargetType()+"_"+processType.getType();
        // return processType.getCompanyId()+"_"+processType.getTargetType()+"_"+processType.getType();
    }

    /**
     * 签收任务
     */
    @Override
    public void claimTask(TaskDTO dto) throws Exception {
        WorkActionDTO actionDTO = new WorkActionDTO();
        actionDTO.setCompanyUserId(dto.getCompanyUserId());
        actionDTO.setId(dto.getTaskId());
        workflowService.claimWorkTask(actionDTO);

    }

    /**
     * 完成任务
     */
    @Override
    public void completeTask(TaskDTO dto) throws Exception {
      //  claimTask(dto);
        Map<String, Object> variable =  workflowService.getTaskVariables(dto.getTaskId());
        //处理当前任务 对应 myTask 表中的任务 先获取，再处理，myTask表中的id保存在activiti对应的任务的variable对应的myTaskId中
        if(variable.containsKey(auditIdKey) && !StringUtil.isNullOrEmpty(auditIdKey)){
            SaveExpMainDTO saveExpMain = new SaveExpMainDTO();
            saveExpMain.setId(dto.getBusinessKey());
            saveExpMain.setCompanyUserId(dto.getCompanyUserId());
            saveExpMain.setVersionNum(dto.getVersionNum());
            expAuditService.completeAudit(saveExpMain);
        }
        //以便在我的任务列表中，能获取到activiti中的任务。
        WorkActionDTO workAction = new WorkActionDTO();
        workAction.setId(dto.getTaskId());
        workAction.setResultMap(dto.getVariables());
        workflowService.completeWorkTask(workAction);
        //保存我的任务（当前任务完成，如果下一个节点不是结束节点，系统会自动为下一个节点产生一个任务，系统需要保存一条相应的审核记录中）
        this.saveAudit(dto.getBusinessKey(),dto,false);

    }


    /**
     * 完成任务
     */
    @Override
    public void completeTask2(TaskDTO dto) throws Exception {
        //  claimTask(dto);

        List<FlowTaskDTO> list = workflowService.listWorkTaskVariableValueEquals(auditIdKey,dto.getId());
        for(FlowTaskDTO task:list){
            //查询当前流程是否是自由流程
            String processKey = workflowService.getProcessKeyByTaskId(task.getId());
            WorkActionDTO workAction = new WorkActionDTO();
            workAction.setId(task.getId());
            //如果是自由流程，如果前端没有传递审批人，则直接结束流程
            if(ProcessTypeConst.PROCESS_TYPE_FREE.equals(processKey) && StringUtil.isNullOrEmpty(dto.getNextCompanyUserId())){
                workAction.setIsPass(ProcessTypeConst.NOT_PASS);
            }else if(StringUtil.isNullOrEmpty(dto.getApproveStatus()) || ProcessTypeConst.PASS.equals(dto.getApproveStatus())){
                workAction.setIsPass(ProcessTypeConst.PASS);
            }else {
                workAction.setIsPass(ProcessTypeConst.NOT_PASS);
            }
            dto.getVariables().put("approveUser",dto.getNextCompanyUserId());
            workAction.setResultMap(dto.getVariables());
            workAction.setCompanyUserId(dto.getCurrentCompanyUserId());
            if(task.getAssignee()==null || task.getAssignee().getId()==null){
                workAction.getResultMap().put("isNotSign","1");
            }
            workflowService.completeWorkTask(workAction);
            //保存我的任务（当前任务完成，如果下一个节点不是结束节点，系统会自动为下一个节点产生一个任务，系统需要保存一条相应的审核记录中）
            this.saveAudit(dto.getBusinessKey(),dto,ProcessTypeConst.PASS.equals(workAction.getIsPass()));
        }
    }

    /**
     *
     * @param dto:mainId:审批主记录的id
     * @param dto:targetType 审批类型
     * @return 返回到前端的标识，1：代表是自由流程，需要前端传递审批人 0：代表不是自由流程，不需要前端传递审批人
     */
    @Override
    public  Map<String,Object>  getCurrentProcess(AuditEditDTO dto) {
        String processDefineId = null;
        Map<String,Object> result = new HashMap<>();
        result.put("processFlag","1");//返回到前端的标识，1：代表是自由流程，需要前端传递审批人
        result.put("conditionList",new ArrayList<>());//首先默认返回个空数组
        result.put("processType",ProcessTypeConst.TYPE_FREE);
        if(StringUtil.isNullOrEmpty(dto.getMainId())){
            ProcessTypeEntity processType = this.processTypeDao.getCurrentProcessType(dto.getAppOrgId(),dto.getAuditType());
            if(!(processType==null || ProcessTypeConst.TYPE_FREE == processType.getType())){
                processDefineId = workflowService.getProcessDefineIdByProcessKey(this.getProcessKey(dto.getAuditType(),dto.getAppOrgId()));
            }
        }else {
            ProcessInstanceRelationEntity instanceRelation = processInstanceRelationDao.getProcessInstanceRelation(dto.getMainId());
            if(instanceRelation!=null){
                processDefineId = workflowService.getProcessDefineIdByProcessInstanceId(instanceRelation.getProcessInstanceId());
            }
        }
        if(!StringUtil.isNullOrEmpty(processDefineId) && !processDefineId.contains(ProcessTypeConst.PROCESS_TYPE_FREE)){
            List<UserTaskNodeDTO> userList = new ArrayList<>();
            ProcessDetailPrepareDTO detailPrepareDTO = new ProcessDetailPrepareDTO();
            detailPrepareDTO.setSrcProcessDefineId(processDefineId);
            List<UserTaskDTO> userTaskList = this.workflowService.listFlowTaskUser(detailPrepareDTO);
            //todo 重新封装
            userList = this.getUserList(userTaskList);
            result.put("processFlag","0");//代表不是自由流程，不需要前端传递审批人
            result.put("conditionList",userList);
            String key = processDefineId.split(":")[0];
            result.put("processType",key.substring(key.lastIndexOf("_")+1));
        }
        return result;
    }

    @Override
    public Map<String,Object> getCurrentTaskUser(AuditEditDTO dto, List<AuditDTO> auditList,String value) throws Exception{
        Map<String,Object>  result = getCurrentProcess(dto);
        String processType = result.get("processType").toString();
        if("0".equals(result.get("processFlag")) && !StringUtil.isNullOrEmpty(value)){
            List<UserTaskNodeDTO> taskList = (List<UserTaskNodeDTO>)result.get("conditionList");
            List<CompanyUserAppDTO> userList = this.getUserList(taskList,value,processType);
            int index = 0;
            if(!CollectionUtils.isEmpty(auditList)){//理论上是不会为空的
                index = auditList.size()-1;
            }else {
                auditList = new ArrayList<>();
            }
            for(int i = index;i<userList.size();i++){
                AuditDTO audit = new AuditDTO();
                BaseDTO.copyFields(userList.get(i),audit);
                audit.setApproveStatus("0");//待审批状态
                auditList.add(audit);
            }
        }
        return result;
    }


   private List<CompanyUserAppDTO> getUserList(List<UserTaskNodeDTO> taskList,String value,String processType){
       if(!CollectionUtils.isEmpty(taskList)) {
           if(ProcessTypeConst.TYPE_FIXED== Integer.parseInt(processType)){//固定流程的情况下
               return taskList.get(0).getUserList();
           }else {//是条件流程的情况下
               for(UserTaskNodeDTO t:taskList){
                   if(isCurrentBrunchProcess(t,value)){
                       return t.getUserList();
                   }
               }
           }
       }
       return null;
   }

   private boolean isCurrentBrunchProcess(UserTaskNodeDTO t,String value){
       double v = Double.parseDouble(value);
       double max = StringUtil.isNullOrEmpty(t.getMax())?-1:Double.parseDouble(t.getMax());
       double min = StringUtil.isNullOrEmpty(t.getMin())?0:Double.parseDouble(t.getMin());
       if (max == -1) {
           //这里处理最大的值的情况下
           if (v >= min) {
               return true;
           }
       } else {
           if (v >= min && v < max) {
               return true;
           }
       }
       return false;
   }

    @Override
    public List<UserTaskNodeDTO> getUserListForAudit(AuditEditDTO dto) {
        List<UserTaskNodeDTO> userList = new ArrayList<>();
        String processDefineId = null;
        if(StringUtil.isNullOrEmpty(dto.getMainId())){
            ProcessTypeEntity processType = this.processTypeDao.getCurrentProcessType(dto.getAppOrgId(),dto.getAuditType());
            if(processType==null || ProcessTypeConst.TYPE_FREE == processType.getType()){
                return userList;
            }else {
                processDefineId = workflowService.getProcessDefineIdByProcessKey(this.getProcessKey(dto.getAuditType(),dto.getAppOrgId()));
            }
        }else {
            ProcessInstanceRelationEntity instanceRelation = processInstanceRelationDao.getProcessInstanceRelation(dto.getMainId());
            if(instanceRelation!=null){
                processDefineId = workflowService.getProcessDefineIdByProcessInstanceId(instanceRelation.getProcessInstanceId());
            }
        }
        if(!StringUtil.isNullOrEmpty(processDefineId) && !processDefineId.equals(ProcessTypeConst.PROCESS_TYPE_FREE)){
            ProcessDetailPrepareDTO detailPrepareDTO = new ProcessDetailPrepareDTO();
            detailPrepareDTO.setSrcProcessDefineId(processDefineId);
            List<UserTaskDTO> userTaskList = this.workflowService.listFlowTaskUser(detailPrepareDTO);
            //todo 重新封装
            userList = this.getUserList(userTaskList);
        }
        return userList;
    }

    @Override
    public int suspendProcess(SaveExpMainDTO dto) {
        //查询流程实例与审核单的关联关系
        ProcessInstanceRelationEntity instanceRelation = processInstanceRelationDao.getProcessInstanceRelation(dto.getId());
        if(instanceRelation!=null){
            workflowService.suspendProcessInstanceById(instanceRelation.getProcessInstanceId());
        }
        //对报审核单据进行状态设置
        ExpMainEntity entity = new ExpMainEntity();
        entity.setId(dto.getId());
        entity.setApproveStatus(ProjectCostConst.FEE_STATUS_REPEAL.toString());
        return this.expMainService.updateById(entity);
    }


    /**
     * 重新组织数据，根据id，把用户的信息获取，返回到前端
     */
    private List<UserTaskNodeDTO> getUserList(List<UserTaskDTO> userTaskList){
        List<UserTaskNodeDTO> list = new ArrayList<>();
        userTaskList.stream().forEach(u->{
            UserTaskNodeDTO taskNode = new UserTaskNodeDTO();
            taskNode.setMax(u.getMax());
            taskNode.setMin(u.getMin()==null?"0":u.getMin());
            u.getAssignList().stream().forEach(assign->{
                taskNode.getUserList().add(companyUserDao.getCompanyUserDataById(assign));
            });
            list.add(taskNode);
        });
        return list;
    }

}
