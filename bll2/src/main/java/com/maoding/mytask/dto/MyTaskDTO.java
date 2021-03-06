package com.maoding.mytask.dto;


import java.math.BigDecimal;
import java.util.Date;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：MyTaskDTO
 * 类描述：我的任务列表
 * 作    者：MaoSF
 * 日    期：2017年01月06日-下午3:10:45
 */
public class MyTaskDTO{

    private String id;

    private String taskTitle;

    /* 任务类型(1.签发：经营负责人,2.生产安排（项目设计负责人）.13.生产安排（任务负责人。）
         3.设计（设计，校对，审核），
         4.付款（技术审查费-确认付款款（经营负责人）），5.付款（技术审查费-确认付款款（企业负责人）），6.付款（合作设计费-付款确认（经营负责人）），7.付款（合作设计费-付款确认（企业负责人）），
         8.到款（技术审查费-确认到款），9.到款（合作设计费-到款确认）10.到款（合同回款-到款确认）
         11.报销单审核,12.同意邀请合作伙伴) */
    private int taskType;

    private String targetId;

    private String taskContent;

    private String taskMemo;

    /**
     * 状态(1:正常进行，2：超时进行，3：正常完成，4.超时完成,10,无状态)
     */
    private int taskState;

    private String projectId;


    /**
     * 项目相关的项目名称
     */
    private String projectName;

    /**
     * 是否可以转发,0:只能内部下发，1：可以下发or外包
     */
    private int issueCount;

    private String description;//描述标示，当前只有taskType==3的时候，才使用

    private String taskRemark;//任务描述，当前只有taskType==3的时候，才使用

    private Date createDate;

    private String role;//角色

    private String status;//1:完成，其他未完成（0,3）

    private String money;//报销审批 金额

    private String expNo;//报销审批 单号

    private String versionNum;//报销审批 版本控制

    private String endDate;//结束时间

    private String overTime;//超时时间

    /*****************财务******************/
    private BigDecimal unPayFee; //应收未收、应付未付

    private BigDecimal planFee ;// 计划金额(计划收款，或许付款)

    /**
     * 截止日期
     */
    private Date deadline;

    private String handlerName;

    private Date completeDate;

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle == null ? null : taskTitle.trim();
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent == null ? null : taskContent.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskMemo() {
        return taskMemo;
    }

    public void setTaskMemo(String taskMemo) {
        this.taskMemo = taskMemo;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getExpNo() {
        return expNo;
    }

    public void setExpNo(String expNo) {
        this.expNo = expNo;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getUnPayFee() {
        return unPayFee;
    }

    public void setUnPayFee(BigDecimal unPayFee) {
        this.unPayFee = unPayFee;
    }

    public BigDecimal getPlanFee() {
        return planFee;
    }

    public void setPlanFee(BigDecimal planFee) {
        this.planFee = planFee;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }
}