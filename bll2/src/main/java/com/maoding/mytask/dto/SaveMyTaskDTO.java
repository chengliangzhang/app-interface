package com.maoding.mytask.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;

import java.util.Date;

public class SaveMyTaskDTO extends BaseDTO {


    private String uuid; //前端传递过来的id

    private String taskTitle;

    /**
     *
     任务类型(1.签发：经营负责人,2.生产安排（项目设计负责人）12.生产安排，13.生产安排（任务负责人。）
     3.设计（设计，校对，审核），
     4.付款（技术审查费-确认付款款（经营负责人）），5.付款（技术审查费-确认付款款（企业负责人）），6.付款（合作设计费-付款确认（经营负责人）），7.付款（合作设计费-付款确认（企业负责人）），
     8.到款（技术审查费-确认到款），9.到款（合作设计费-到款确认）10.到款（合同回款-到款确认）
     11.报销单审核,
     ****14,设置设计负责人，15，设置任务负责人 (目前没有这几种任务)****
     16.技术审查费-财务-付款，17.技术审查费-财务-到款 ，18.合作设计费-财务-付款，19.合作设计费-财务-到款，20.其他费-财务-付款，21.其他费-财务-到款,
     22.设计任务完成后，给设计负责人推送完成任务
     100:轻量型任务类型
     */
    private Integer taskType;

    private String handlerId;

    private String companyId;

    private String projectId;

    private String taskContent;

    /**
     * 任务发送公司id
     */
    private String sendCompanyId;

    /**
     * 截止日期
     */
    private String deadlineStr;

    /**
     * 截止日期
     */
    private Date deadline;

    /**
     * 截止日期
     */
    private Date startDate;

    /**
     * 截止日期
     */
    private String startDateStr;

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getSendCompanyId() {
        return sendCompanyId;
    }

    public void setSendCompanyId(String sendCompanyId) {
        this.sendCompanyId = sendCompanyId;
    }

    public Date getDeadline() {
        if(!StringUtil.isNullOrEmpty(deadlineStr)){
            deadline = DateUtils.str2Date(deadlineStr);
        }
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDeadlineStr() {
        return deadlineStr;
    }

    public void setDeadlineStr(String deadlineStr) {
        this.deadlineStr = deadlineStr;
    }

    public Date getStartDate() {
        if(!StringUtil.isNullOrEmpty(startDateStr)){
            startDate = DateUtils.str2Date(startDateStr);
        }
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }
}
