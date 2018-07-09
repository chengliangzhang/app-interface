package com.maoding.task.dto;

import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：任务数据
 * 作    者：MaoSF
 * 日    期：2016年12月31日-上午10:13:28
 */
public class ProjectTaskDTO{


    /**
     * 任务id
     */
    private String id;

    /**
     *项目id
     */
    private String projectId;

    /**
     *任务所属公司id
     */
    private String companyId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 备注
     */
    private String taskRemark;

    /**
     * 任务父Id
     */
    private String taskPid;


    /**
     * 任务路径
     */
    private String taskPath;

    /**
     * 状态(1:正常进行，2：超时进行，3：正常完成，4.超时完成,5:未开始，6，剩余多少天，10,无状态)
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
     *负责人（可以是人，可以是公司）
     */
    private String companyName;

    /**
     *负责人id
     */
    private String managerId;

    /**
     *任务负责人
     */
    private String taskResponsibleName;

    /**
     * 是否是签发给公司为 0：自己做，1:签发给其他公司
     */
    private int flag;

    /**
     *状态字符串
     */
    private String stateHtml;

    /**
     * 层级
     */
    private int taskLevel;

    /**
     *完成时间
     */
    private String completeDate;

    /**
     *共多少天
     */
    private int allDay;


    /**
     * 子任务个数
     */
    private int childCount;

    /**
     * 是否是纯经营任务（1：是，0：否）
     */
    private int isOperaterTask;

    private String orgId;

    private String departName;

    /**
     * 暂无作用
     */
    private int notCompleteCount;

    private int isRootTask;

    /**
     * 关联发布数据的id
     */
    private String beModifyId;

    /**
     * 任务数据状态（0：正式版本，1：失效，2，未发布，）
     */
    private String taskStatus;

    private String nodeName;//我的任务中，设校审（项目名称+父集名称）

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

    public String getStateHtml() {
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

    public int getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(int taskLevel) {
        this.taskLevel = taskLevel;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
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

    public String getTaskPath() {
        return taskPath;
    }

    public void setTaskPath(String taskPath) {
        this.taskPath = taskPath;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getIsOperaterTask() {
        return isOperaterTask;
    }

    public void setIsOperaterTask(int isOperaterTask) {
        this.isOperaterTask = isOperaterTask;
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

    public int getNotCompleteCount() {
        return notCompleteCount;
    }

    public void setNotCompleteCount(int notCompleteCount) {
        this.notCompleteCount = notCompleteCount;
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

    public String getTaskResponsibleName() {
        return taskResponsibleName;
    }

    public void setTaskResponsibleName(String taskResponsibleName) {
        this.taskResponsibleName = taskResponsibleName;
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

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}