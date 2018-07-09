package com.maoding.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：签发DTO
 * 作    者：MaoSF
 * 日    期：2015年8月15日-上午10:13:28
 */
public class ProjectIssueTaskDTO extends TaskBaseDTO{

    /**
     * id
     */
    private String id;

    /** 排序序号 */
    private Integer seq;

    private String taskPid;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 任务名称
     */
    private String taskName;

    private String taskRemark;

    /**
     * 被修改记录的id，用于修改任务，新增一条未被发布的数据，该字段记录被修改记录的id
     */
    private String beModifyId;


    /**
     * 发布版本的id
     */
    private String publishId;

    /**
     * 任务状态(0生效，1不生效,2:未发布)
     */
    private String taskStatus;

    /**
     * 任务类型（0：生产，1：设计阶段，2：签发并发布，3：未发布的签发数据）
     */
    private int taskType;

    /**
     * 签发组织id
     */
    private String fromCompanyId;

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
     * 是否是签发的任务
     */
    private int isRootTask;

    private int taskLevel;

    /**
     * 是否是第一个
     */
    private boolean isFirst;

    /**
     * 是否是最后一个
     */
    private boolean isLast;

    /**
     * 设计任务
     */
    List<ProjectIssueTaskDTO> childTaskList = new ArrayList<>();

    /**
     * 签发次数
     */
    Integer issueLevel;

    /**
     * 是否能被删除
     */
    Boolean canBeDelete;

    private String currentCompanyId;

    /**
     * 子任务数
     */
    private Integer childCount;


    private Integer memberCount;

    /**
     * 任务负责人
     */
    private String taskResponsibleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public int getIsRootTask() {
        return isRootTask;
    }

    public void setIsRootTask(int isRootTask) {
        this.isRootTask = isRootTask;
    }

    public Integer getIssueLevel() {
        return issueLevel;
    }

    public void setIssueLevel(Integer issueLevel) {
        this.issueLevel = issueLevel;
    }

    public Boolean getCanBeDelete() {
        return canBeDelete;
    }

    public void setCanBeDelete(Boolean canBeDelete) {
        this.canBeDelete = canBeDelete;
    }

    public String getTaskPid() {
        return taskPid;
    }

    public void setTaskPid(String taskPid) {
        this.taskPid = taskPid;
    }

    public String getFromCompanyId() {
        return fromCompanyId;
    }

    public void setFromCompanyId(String fromCompanyId) {
        this.fromCompanyId = fromCompanyId;
    }

    public String getCurrentCompanyId() {
        return currentCompanyId;
    }

    public void setCurrentCompanyId(String currentCompanyId) {
        this.currentCompanyId = currentCompanyId;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public int getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(int taskLevel) {
        this.taskLevel = taskLevel;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public String getTaskResponsibleName() {
        return taskResponsibleName;
    }

    public void setTaskResponsibleName(String taskResponsibleName) {
        this.taskResponsibleName = taskResponsibleName;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }
}