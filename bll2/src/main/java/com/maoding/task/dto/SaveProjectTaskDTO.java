package com.maoding.task.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：任务分解数据
 * 作    者：MaoSF
 * 日    期：2016年12月31日-上午10:13:28
 */
public class SaveProjectTaskDTO extends BaseDTO{

    /**
     * 项目id
     */
    private String projectId;

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
    private String managerId;

    /**
     * 技术负责人
     */
    private String designerId;

    /**
     *任务所属公司id
     */
    private String companyId;

    /**
     *任务类型（经营方：2，生产方：0（默认不传递））
     */
    private int taskType;

    /**
     * 是否发布
     */
    private int isPublish;

    private int flag;//1:代表根任务

    private String orgId;

    private String memo;//变更原因

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
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

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public int getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(int isPublish) {
        this.isPublish = isPublish;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}