package com.maoding.activiti.service.impl;

import com.maoding.activiti.dto.*;
import com.maoding.activiti.service.WorkflowService;
import com.maoding.core.base.dto.CoreDTO;
import com.maoding.core.base.dto.CoreEditDTO;
import com.maoding.core.base.dto.CorePageDTO;
import com.maoding.core.base.service.NewBaseService;
import com.maoding.core.constant.ProcessTypeConst;
import com.maoding.core.util.DigitUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.core.util.StringUtils;
import org.activiti.bpmn.model.*;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * @date : 2018/7/26
 * @description :
 */
@Service("workflowServiceImpl")
public class WorkflowServiceImpl extends NewBaseService implements WorkflowService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FormService formService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private  ProcessEngine processEngine;

    /**
     * @param deployment            指定的流程，可以为空
     * @param deploymentEditRequest 包含数字条件、修改任务的流程编辑信息，可以为空
     * @return 流程编辑信息
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个流程的编辑器
     **/
    @Deprecated
    @Override
    public DeploymentEditDTO createDeploymentEdit(DeploymentDTO deployment, DeploymentEditDTO deploymentEditRequest) {
        return null;
    }

    /**
     * @author  张成亮
     * @date    2018/7/30
     * @description     创建或修改一个流程
     * @param   deploymentEditRequest 包含名称、数字条件、修改任务的流程编辑信息，除名称外，各属性可以为空
     *                                如果id为空，则是创建流程，如果srcDeployId不为空，需要从模板流程复制流程到新流程中
     *                                否则，如果id不为空，则是修改流程，srcDeployId无效
     * @return  新建或修改后的流程信息
     **/
    @Override
    public DeploymentDTO changeDeployment(DeploymentEditDTO deploymentEditRequest) {
        return null;
    }

    /**
     * @return 流程查询信息
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个流程查询器
     **/
    @Override
    public DeploymentQueryDTO createDeploymentQuery() {
        return null;
    }

    /**
     * @param deploymentEdit 编辑内容
     * @author 张成亮
     * @date 2018/7/30
     * @description 保存流程
     **/
    @Override
    public void saveDeploy(DeploymentEditDTO deploymentEdit) {

    }

    /**
     * @param deploymentEdit 删除内容
     * @author 张成亮
     * @date 2018/7/30
     * @description 删除流程
     **/
    @Deprecated
    @Override
    public void deleteDeploy(DeploymentEditDTO deploymentEdit) {

    }

    /**
     * @param deleteRequest 删除申请
     * @author 张成亮
     * @date 2018/7/30
     * @description 删除流程
     **/
    @Override
    public void deleteDeploy(CoreEditDTO deleteRequest) {

    }

    /**
     * 流程挂起
     * 用于单据撤销
     */
    @Override
    public void suspendProcessInstanceById(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    /**
     * @param query 流程查询器
     * @return 流程列表
     * @author 张成亮
     * @date 2018/7/30
     * @description 查询流程
     **/
    @Override
    public List<DeploymentDTO> listDeployment(DeploymentQueryDTO query) {
        return null;
    }

    /**
     * @param query 流程查询器
     * @return 流程个数
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取流程总个数
     **/
    @Override
    public int countDeployment(DeploymentQueryDTO query) {
        return 0;
    }

    /**
     * @param query 流程查询器
     * @return 流程分页数据
     * @author 张成亮
     * @date 2018/7/30
     * @description 分页获取流程列表
     **/
    @Override
    public CorePageDTO<DeploymentDTO> listPageDeployment(DeploymentQueryDTO query) {
        return null;
    }

    /**
     * @param deploymentEdit 指定的流程编辑器，不能为空
     * @param flowTask       指定的流程任务，可以为空
     * @return 流程任务编辑器
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个流程任务的编辑器
     **/
    @Override
    public FlowTaskEditDTO createFlowTaskEdit(DeploymentEditDTO deploymentEdit, FlowTaskDTO flowTask) {
        return null;
    }

    /**
     * @return 流程任务查询信息
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个流程任务的查询器
     **/
    @Override
    public FlowTaskQueryDTO createFlowTaskQuery() {
        return null;
    }

    /**
     * @param query 流程任务查询器
     * @return 流程任务列表
     * @author 张成亮
     * @date 2018/7/30
     * @description 查询流程任务
     **/
    @Override
    public List<FlowTaskDTO> listFlowTask(FlowTaskQueryDTO query) {
        return null;
    }

    /**
     * @param deploymentEdit
     * @param sequence       指定的流程路径，可以为空  @return  流程路径编辑器
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个流程路径编辑器
     **/
    @Override
    public FlowSequenceEditDTO createFlowSequenceEdit(DeploymentEditDTO deploymentEdit, FlowSequenceDTO sequence) {
        return null;
    }

    /**
     * @return 流程路径查询器
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个流程路径查询器
     **/
    @Override
    public FlowSequenceQueryDTO createFlowSequenceQuery() {
        return null;
    }

    /**
     * @param query 流程路径查询器
     * @return 流程路径列表
     * @author 张成亮
     * @date 2018/7/30
     * @description 查询流程路径
     **/
    @Override
    public List<FlowSequenceDTO> listFlowSequence(FlowSequenceQueryDTO query) {
        return null;
    }

    /**
     * @param deploymentEdit
     * @param gateWay        指定的流程网关，可以为空  @return  流程路径编辑器
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个流程网关编辑器
     **/
    @Override
    public FlowGateWayEditDTO createFlowGateWayEdit(DeploymentEditDTO deploymentEdit, FlowGateWayDTO gateWay) {
        return null;
    }


   /**
     * @return 流程网关查询器
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个流程网关查询器
     **/
    @Override
    public FlowGateWayQueryDTO createFlowGateWayQuery() {
        return null;
    }

    /**
     * @param query 流程网关查询器
     * @return 流程网关列表
     * @author 张成亮
     * @date 2018/7/30
     * @description 查询流程网关
     **/
    @Override
    public List<FlowGateWayDTO> listFlowGateWay(FlowGateWayQueryDTO query) {
        return null;
    }

    /**
     * @return 流程用户查询器
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个流程用户查询器
     **/
    @Override
    public UserQueryDTO createUserQuery() {
        return null;
    }

    /**
     * @param query 用户查询器
     * @return 用户列表
     * @author 张成亮
     * @date 2018/7/30
     * @description 查询用户
     **/
//    @Override
//    public List<UserDTO> listUser(UserQueryDTO query) {
//        return null;
//    }

    /**
     * @return 当前流程任务查询器
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取一个正在当前流程任务查询器
     **/
    @Override
    public WorkTaskQueryDTO createWorkTaskQuery() {
        return null;
    }

    /**
     * @param query 当前任务查询器
     * @return 当前任务列表
     * @author 张成亮
     * @date 2018/7/30
     * @description 查询当前任务
     **/
    @Override
    public List<WorkTaskDTO> listWorkTask(WorkTaskQueryDTO query) {



        return null;
    }

    @Override
    public List<FlowTaskDTO> listWorkTask(String businessKey) {
        List<FlowTaskDTO> taskList = new ArrayList<>();
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .processInstanceBusinessKey(businessKey)
                .list();

        //处理 getVariables ，创建下一个人要处理的任务，并且返回任务的id，可能是多个任务的id。把返回的任务id 放入 dto.getVariables() 中进行保存
        list.stream().forEach(task->{
            List<IdentityLink> userList = taskService.getIdentityLinksForTask(task.getId());
            userList.stream().forEach(u->{
                String userIdStr = null;
                if("assignee".equals(u.getType())){
                    userIdStr = u.getUserId();
                }
                if("candidate".equals(u.getType())){
                    userIdStr = u.getUserId();
                }
                String[] userIds = userIdStr.split(",");
                for(String userId:userIds){
                    FlowTaskDTO flowTask = new FlowTaskDTO();
                    flowTask.setId(task.getId());
                    flowTask.setName(task.getName());
                    CoreDTO assignee = new CoreDTO();
                    assignee.setId(userId);
                    flowTask.setAssignee(assignee);
                    taskList.add(flowTask);
                }
            });
        });
        return taskList;
    }

    @Override
    public List<FlowTaskDTO> listWorkTaskVariableValueEquals(String name, String value) {
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .processVariableValueEquals(name,value)
                .list();
        List<FlowTaskDTO> taskList = new ArrayList<>();
        list.stream().forEach(task->{
            FlowTaskDTO flowTask = new FlowTaskDTO();
            flowTask.setId(task.getId());
            flowTask.setName(task.getName());
            CoreDTO assignee = new CoreDTO();
            assignee.setId(task.getAssignee());
            flowTask.setAssignee(assignee);
            taskList.add(flowTask);
        });
        return taskList;

    }

    /**
     * @param query 当前任务查询器
     * @return 当前任务个数
     * @author 张成亮
     * @date 2018/7/30
     * @description 获取当前任务总个数
     **/
    @Override
    public int countWorkTask(WorkTaskQueryDTO query) {
        return 0;
    }

    /**
     * @param query 当前任务查询器
     * @return 当前任务分页数据
     * @author 张成亮
     * @date 2018/7/30
     * @description 分页获取当前任务列表
     **/
    @Override
    public CorePageDTO<WorkTaskDTO> listPageWorkTask(WorkTaskQueryDTO query) {
        return null;
    }

    /**
     * @param deployment 流程
     * @return 当前流程任务
     * @author 张成亮
     * @date 2018/7/30
     * @description 启动流程
     **/
    @Deprecated
    @Override
    public WorkTaskDTO startDeployment(DeploymentDTO deployment) {

        return null;

    }

    /**
     * 描述       启动流程
     * 日期       2018/8/1
     *
     * @param workTask
     * @author 张成亮
     */
    @Override
    public WorkTaskDTO startProcess(WorkActionDTO workTask) {
        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(workTask.getCompanyUserId());
            if(ProcessTypeConst.PROCESS_TYPE_FREE.equals(workTask.getKey())){
                processInstance = runtimeService.startProcessInstanceByKey(workTask.getKey(),workTask.getBusinessKey(), workTask.getResultMap());
            }else {
                processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(workTask.getKey(),workTask.getBusinessKey(), workTask.getResultMap(),workTask.getCurrentCompanyId());
            }
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        WorkTaskDTO workTaskDTO = new WorkTaskDTO();
        workTaskDTO.setId(processInstance.getId());
        return workTaskDTO;
    }

    /**
     * @param workTask 当前任务
     * @author 张成亮
     * @date 2018/7/30
     * @description 结束当前任务
     **/
    @Override
    public void completeWorkTask(WorkActionDTO workTask) {
        //如果还未认领，则直接认领，并完成
        if(workTask.getResultMap().containsKey("isNotSign")){
            this.claimWorkTask(workTask);
        }
       //处理完成的动作
        Map<String,Object> variables = new HashMap<>();
        variables.put("isPass",workTask.getIsPass());
        variables.putAll(workTask.getResultMap());
        taskService.complete(workTask.getId(), variables);
    }

    /**
     * 获取任务的参数
     * @param taskId 任务id
     */
    @Override
    public Map<String, Object> getTaskVariables(String taskId) {
        return taskService.getVariables(taskId);
    }

    /**
     * 设置任务的参数
     * @param taskId 任务id
     */
    @Override
    public void setTaskVariables(String taskId, Map<String, Object> variables) {
        taskService.setVariables(taskId,variables);
    }

    /**
     * @param workTask 当前任务
     * @author 张成亮
     * @date 2018/7/30
     * @description 认领当前任务, 如果任务不是会签的话，认领任务将会使任务从其他人任务列表内消失
     **/
    @Override
    public void claimWorkTask(WorkActionDTO workTask) {
        taskService.claim(workTask.getId(), workTask.getCompanyUserId());
    }

    /**
     * @param taskId 当前任务Id
     * @author MaoSF
     * @date 2018/8/02
     * @description 根据当前任务的id获取流程的key
     **/
    @Override
    public String getProcessKeyByTaskId(String taskId) {
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        return getProcessKeyByProcessInstanceId(processInstanceId);
    }

    /**
     * @param processInstanceId 当前流程实例id
     * @author MaoSF
     * @date 2018/8/02
     * @description 根据当前流程实例id获取流程的key
     **/
    @Override
    public String getProcessKeyByProcessInstanceId(String processInstanceId) {
        return getProcessByProcessInstanceId(processInstanceId).getKey();
    }

    @Override
    public String getProcessDefineIdByProcessInstanceId(String processInstanceId) {
        return getProcessByProcessInstanceId(processInstanceId).getId();
    }

    @Override
    public String getProcessDefineIdByProcessKey(String processKey,String companyId) {
        ProcessDefinitionQuery processDefinitionQuery = this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .processDefinitionTenantId(companyId);
        if(processDefinitionQuery!=null){
            List<ProcessDefinition> processDefinitionList = processDefinitionQuery.active().orderByProcessDefinitionVersion().desc().list();//latestVersion().singleResult();
            if(!CollectionUtils.isEmpty(processDefinitionList)){
                return processDefinitionList.get(0).getId();
            }
        }
        return null;
    }


    private ProcessDefDTO getProcessByProcessInstanceId(String processInstanceId){
        ProcessDefDTO def = new ProcessDefDTO();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(processInstance!=null){
            def.setId(processInstance.getProcessDefinitionId());
            def.setKey(processInstance.getProcessDefinitionKey());
        }else {
            HistoricProcessInstance processInstance2 = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            if(processInstance2!=null){
                def.setId(processInstance2.getProcessDefinitionId());
                def.setKey(processInstance2.getProcessDefinitionKey());
            }
        }
        return def;
    }

    @Override
    public  List<UserTaskDTO> listFlowTaskUser(ProcessDetailPrepareDTO query) {
        List<UserTaskDTO> list = new ArrayList<>();
        BpmnModel model = repositoryService.getBpmnModel(query.getSrcProcessDefineId());
        if(model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            StartEvent start = null;
            for(FlowElement e : flowElements) {
                if(e instanceof StartEvent){
                    //接下来获取分支
                    start = (StartEvent)e;
                    break;
                }
            }
            List<SequenceFlow> sequenceFlows =  start.getOutgoingFlows();
            sequenceFlows.stream().forEach(s->{
                UserTaskDTO userTask = new UserTaskDTO();
                Map<String,Double> condition = getPointFromCondition(s.getConditionExpression());
                if(condition!=null){
                    userTask.setMax(condition.get("max")+"");
                    userTask.setMin(condition.get("min")+"");
                }
                getUserTask(flowElements,s.getTargetRef(),userTask);
                list.add(userTask);
            });
        }
        return list;
    }

    public void  getUserTask( Collection<FlowElement> flowElements,String refId,UserTaskDTO userTask){
        flowElements.stream().forEach(f->{
            if(refId.equals(f.getId())){
                if(f instanceof UserTask){
                    UserTask t = (UserTask)f;
                    if(!StringUtil.isNullOrEmpty(t.getAssignee())){
                        userTask.getAssignList().add(t.getAssignee());
                    }else if(!CollectionUtils.isEmpty(t.getCandidateUsers())){
                        userTask.getAssignList().addAll(t.getCandidateUsers());
                    }
                    List<SequenceFlow> sequenceFlows =  t.getOutgoingFlows();
                    sequenceFlows.stream().forEach(s->{
                        getUserTask(flowElements,s.getTargetRef(),userTask);
                    });
                }
            }
        });
    }

    //根据表达式获取数字条件节点
    private Map<String,Double> getPointFromCondition(String conditionStr){
        Map<String,Double> result = new HashMap<>();
        if(StringUtils.isNotEmpty(conditionStr)){
        //    conditionStr= conditionStr.replaceAll(" || ", " or ").replaceAll(" && ", " and ");
            String split = null;
            if(conditionStr.contains("and")){
                split = "and";
            }
            if(conditionStr.contains("or")){
                split = "or";
            }
            String[] conditions = new String[1];
            if(StringUtil.isNullOrEmpty(split)){
                conditions[0] = conditionStr;
            }else {
                conditions = conditionStr.split(split);
            }

            for(String condition:conditions){
                String s = getCondition(condition);
                if(s.contains(">=")){
                    result.put("min",DigitUtils.parseDouble(StringUtils.lastRight(s,">=").trim()));
                }else   if(s.contains(">")){
                    result.put("max",DigitUtils.parseDouble(StringUtils.left(s,">").trim()));
                }else {
                    result.put("max",DigitUtils.parseDouble(StringUtils.lastRight(s,">").trim()));
                }
            }
        }
        return result;
    }

    private static String getCondition( String condition ){
        String s = condition;
        if(condition.contains("${")){
            s = StringUtils.right(condition,"${");
        }
        if(condition.contains("}")){
            s  = StringUtils.left(s,"}");
        }
        return s;
    }
}
