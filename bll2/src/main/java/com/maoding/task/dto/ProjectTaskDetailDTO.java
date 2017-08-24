package com.maoding.task.dto;

import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.project.dto.ProjectTaskProcessNodeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：任务数据
 * 作    者：MaoSF
 * 日    期：2016年12月31日-上午10:13:28
 */
public class ProjectTaskDetailDTO {
    /**
     * 任务id
     */
    private String id;

    /**
     *任务所属公司id
     */
    private String companyId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 备注
     */
    private String taskRemark;

    /**
     *父级任务名称
     */
    private String taskParentName;

    /**
     * 任务父Id
     */
    private String taskPid;

    /**
     * 状态(1:正常进行，2：超时进行，3：正常完成，4.超时完成,10,无状态)
     */
    private int taskState;

    /**
     *任务类型
     */
    private int taskType;

    /**
     *开始时间
     */
    private String startTime;

    /**
     *结束时间
     */
    private String endTime;

    /**
     *约定的开始时间
     */
    private String appointedStartTime;


    /**
     *约定的结束时间
     */
    private String appointedEndTime;

    /**
     *负责人（可以是人，可以是公司）
     */
    private String companyName;

    /**
     *当前任务所属公司的经营负责人id
     */
    private String managerId;

    /**
     *当前任务所属公司的经营负责人
     */
    private String managerName;

    /**
     * 当前任务所属公司的设计负责人id
     */
    private String designerId;

    /**
     * 当前任务所属公司的设计负责人
     */
    private String designerName;

    /**
     * 是否是任务的负责人or流程操作人
     */
    private int flag;//1:是，0，否

    /**
     * 权限标示(flag1：任务签发，flag2：任务负责人，flag3：设计人员，flag4：计划进度，flag5：编辑任务，flag6：删除任务)
     */
    private Map<String,String> roleFlag = new HashMap<String,String>();

    /**
     *状态字符串
     */
    private String stateHtml;

    /**
     * 是否可以转发,0:只能内部下发，1：可以下发or外包
     */
    private int issueCount;

    /**
     *是否显示约定进度时间(是：1，否：0)
     */
    private int isShowAppointmentTime;

    private int isOperaterTask;

    /**
     *共多少天
     */
    private int allDay;

    private String orgId;

    private String departName;

    private String processId;//如果存在设计任务，则为设计任务的id

    private int isProduct;//是否已经生产

    private int childCount;

    private int isShowProcessTime;//是否显示进度时间（计划进度+合同约定时间）


    private int isRootTask;//是否是根任务（或许是签发的任务）

    private String completeDate;

    private String myTaskId;//对与我的任务中的id

    private String beModifyId;//被修改记录的ID

    private String taskStatus;
    /**
     *子任务
     */
    private List<ProjectTaskDetailDTO> childTaskList = new ArrayList<ProjectTaskDetailDTO>();
//
//    /**
//     *子任务
//     */
//    private List<ProjectProcessShowDTO> processList = new ArrayList<ProjectProcessShowDTO>();

    private List<ProjectTaskProcessNodeDTO> designUserList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskPid() {
        return taskPid;
    }

    public void setTaskPid(String taskPid) {
        this.taskPid = taskPid;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public String getTaskParentName() {
        return taskParentName;
    }

    public void setTaskParentName(String taskParentName) {
        this.taskParentName = taskParentName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<ProjectTaskDetailDTO> getChildTaskList() {
        return childTaskList;
    }

    public void setChildTaskList(List<ProjectTaskDetailDTO> childTaskList) {
        this.childTaskList = childTaskList;
    }

    public String getStateHtml() {
//        if(!StringUtil.isNullOrEmpty(this.getStartTime()) && !StringUtil.isNullOrEmpty(this.getEndTime())){
//            stateHtml = "共"+ DateUtils.getDutyDays(this.getStartTime(),this.getEndTime())+"工作日";
//        }
        return stateHtml;
    }

    public void setStateHtml(String stateHtml) {
        this.stateHtml = stateHtml;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public List<ProjectTaskProcessNodeDTO> getDesignUserList() {
        return designUserList;
    }

    public void setDesignUserList(List<ProjectTaskProcessNodeDTO> designUserList) {
        this.designUserList = designUserList;
    }

    public String getAppointedStartTime() {
        return appointedStartTime;
    }

    public void setAppointedStartTime(String appointedStartTime) {
        this.appointedStartTime = appointedStartTime;
    }

    public String getAppointedEndTime() {
        return appointedEndTime;
    }

    public void setAppointedEndTime(String appointedEndTime) {
        this.appointedEndTime = appointedEndTime;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public Map<String, String> getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(Map<String, String> roleFlag) {
        this.roleFlag = roleFlag;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }

    public int getIsShowAppointmentTime() {
        return isShowAppointmentTime;
    }

    public void setIsShowAppointmentTime(int isShowAppointmentTime) {
        this.isShowAppointmentTime = isShowAppointmentTime;
    }

    public int getAllDay() {
        if(!StringUtil.isNullOrEmpty(this.startTime) && !StringUtil.isNullOrEmpty(this.endTime)){
            allDay = DateUtils.daysOfTwo(endTime, this.startTime)+1;
        }
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public int getIsOperaterTask() {
        return isOperaterTask;
    }

    public void setIsOperaterTask(int isOperaterTask) {
        this.isOperaterTask = isOperaterTask;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }


    public int getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(int isProduct) {
        this.isProduct = isProduct;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getIsShowProcessTime() {
        return isShowProcessTime;
    }

    public void setIsShowProcessTime(int isShowProcessTime) {
        this.isShowProcessTime = isShowProcessTime;
    }

    public int getIsRootTask() {
        return isRootTask;
    }

    public void setIsRootTask(int isRootTask) {
        this.isRootTask = isRootTask;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getMyTaskId() {
        return myTaskId;
    }

    public void setMyTaskId(String myTaskId) {
        this.myTaskId = myTaskId;
    }

    public String getBeModifyId() {
        return beModifyId;
    }

    public void setBeModifyId(String beModifyId) {
        this.beModifyId = beModifyId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}