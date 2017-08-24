package com.maoding.task.dto;

import com.maoding.task.entity.ProjectProcessTimeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：任务数据
 * 作    者：MaoSF
 * 日    期：2016年12月31日-上午10:13:28
 */
public class ProjectTaskForEditDTO {


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
     *设计组织
     */
    private String companyName;


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
     *任务类型
     */
    private int taskType;


    /**
     *经营负责人id
     */
    private String managerId;

    /**
     * 经营负责人
     */
    private String managerName;

    /**
     *任务负责人
     */
    private String taskResponsibleName;

    /**
     * 层级
     */
    private int taskLevel;

    /**
     * 签发次数
     */
    private Integer issueLevel;  //如果issueLevel>1则是二次签发


    private String orgId;

    private String departName;

    /**
     * 是否已经生产
     */
    private int isHasProduct;

    private List<ProjectProcessTimeEntity> projectProcessTimeList = new ArrayList<>();

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public List<ProjectProcessTimeEntity> getProjectProcessTimeList() {
        return projectProcessTimeList;
    }

    public void setProjectProcessTimeList(List<ProjectProcessTimeEntity> projectProcessTimeList) {
        this.projectProcessTimeList = projectProcessTimeList;
    }

    public int getIsHasProduct() {
        return isHasProduct;
    }

    public void setIsHasProduct(int isHasProduct) {
        this.isHasProduct = isHasProduct;
    }

    public Integer getIssueLevel() {
        return this.taskLevel-1;
    }

    public void setIssueLevel(Integer issueLevel) {
        this.issueLevel = issueLevel;
    }
}