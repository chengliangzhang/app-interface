package com.maoding.mytask.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：MyTaskDTO
 * 类描述：我的任务列表
 * 作    者：MaoSF
 * 日    期：2017年01月06日-下午3:10:45
 */
public class TaskDataDTO{

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

    private Integer overTime;

    /**
     * 状态(1:正常进行，2：超时进行，3：完成)
     */
    private int taskState;


    private Date createDate;

    private String status;//1:完成，其他未完成（0,3）

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

    public Integer getOverTime() {
        return overTime;
    }

    public void setOverTime(Integer overTime) {
        this.overTime = overTime;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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