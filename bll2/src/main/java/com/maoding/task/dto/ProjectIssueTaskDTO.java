package com.maoding.task.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：签发DTO
 * 作    者：MaoSF
 * 日    期：2015年8月15日-上午10:13:28
 */
public class ProjectIssueTaskDTO {

    /**
     * id
     */
    private String id;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 被修改记录的id，用于修改任务，新增一条未被发布的数据，该字段记录被修改记录的id
     */
    private String beModifyId;


    /**
     * 状态(1:正常进行，2：超时进行，3：正常完成，4.超时完成,5.未开始，10,无状态)
     */
    private int taskState;

    /**
     * 状态html
     */
    private String stateHtml;

    /**
     * 任务状态(0生效，1不生效,2:未发布)
     */
    private String taskStatus;

    /**
     * 任务类型（0：生产，1：设计阶段，2：签发并发布，3：未发布的签发数据）
     */
    private int taskType;

    /**
     * 设计内容的开始时间
     */
    private String startTime;

    /**
     * 设计内容的结束时间
     */
    private String endTime;

    private String completeDate;

    /**
     * 设计组织id
     */
    private String companyId;

    /**
     * 设计组织名
     */
    private String companyName;

    /**
     * 部门名称
     */
    private String departId;

    /**
     * 部门名
     */
    private String departName;

    /**
     * 子任务的个数
     */
    private int isHasChild;

    /**
     * 未完成任务的个数
     */
    private int notCompleteCount;

    /**
     * 是否是签发的任务
     */
    private int isRootTask;

    /**
     *经营负责人名
     */
    private String managerName;

    /**
     *负责人id
     */
    private String managerId;

    /**
     * 合作组织id
     */
    private String fromCompanyId;

    private String fromCompanyName;

    /**
     * 是否是立项方（0：是，1：否(合作方)）
     */
    private int isCooperator;//是否是立项方

    /**
     *经营负责人
     */
    private String operatorManagerName;

    /**
     * 签发次数
     */
    private Integer issueLevel;
    /**
     * 设计任务
     */
    List<ProjectIssueTaskDTO> childTaskList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getBeModifyId() {
        return beModifyId;
    }

    public void setBeModifyId(String beModifyId) {
        this.beModifyId = beModifyId;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
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

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getIsHasChild() {
        return isHasChild;
    }

    public void setIsHasChild(int isHasChild) {
        this.isHasChild = isHasChild;
    }

    public int getNotCompleteCount() {
        return notCompleteCount;
    }

    public void setNotCompleteCount(int notCompleteCount) {
        this.notCompleteCount = notCompleteCount;
    }

    public List<ProjectIssueTaskDTO> getChildTaskList() {
        return childTaskList;
    }

    public void setChildTaskList(List<ProjectIssueTaskDTO> childTaskList) {
        this.childTaskList = childTaskList;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getStateHtml() {
        return stateHtml;
    }

    public void setStateHtml(String stateHtml) {
        this.stateHtml = stateHtml;
    }

    public int getIsRootTask() {
        return isRootTask;
    }

    public void setIsRootTask(int isRootTask) {
        this.isRootTask = isRootTask;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getFromCompanyId() {
        return fromCompanyId;
    }

    public void setFromCompanyId(String fromCompanyId) {
        this.fromCompanyId = fromCompanyId;
    }

    public String getFromCompanyName() {
        return fromCompanyName;
    }

    public void setFromCompanyName(String fromCompanyName) {
        this.fromCompanyName = fromCompanyName;
    }

    public int getIsCooperator() {
        return isCooperator;
    }

    public void setIsCooperator(int isCooperator) {
        this.isCooperator = isCooperator;
    }

    public String getOperatorManagerName() {
        return operatorManagerName;
    }

    public void setOperatorManagerName(String operatorManagerName) {
        this.operatorManagerName = operatorManagerName;
    }

    public Integer getIssueLevel() {
        return issueLevel;
    }

    public void setIssueLevel(Integer issueLevel) {
        this.issueLevel = issueLevel;
    }
}