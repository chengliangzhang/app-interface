package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;

import java.util.Date;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskManagerDTO
 * 类 描 述：任务分解DTO
 * 作    者：MaoSF
 * 日    期：2016年8月3日-下午1:47:23
 */
public class ProjectTaskManagerUserDTO extends BaseDTO {

    private String projectId;

    private String companyUserId;

    private String taskName;

    private String taskManageName;

    private String currentNodeId;

    private String processInstanceId;

    private int status;

    private String processId;

    private String projectName;

    private String planTime;

    private String myNodeId;

    private int endSatus;//0,4：无操作，1.提交，2.打回，通过，3.打回，完成

    private String currentNodeName;

    /**
     * 进度开始时间
     */
    private String taskScheduleStatime;

    /**
     * 进度结束时间
     */
    private String taskScheduleEndtime;

    private int allDay;

    private String completeDate;//此任务的完成时间

    public String getTaskScheduleStatime() {
        return taskScheduleStatime;
    }

    public void setTaskScheduleStatime(String taskScheduleStatime) {
        this.taskScheduleStatime = taskScheduleStatime;
    }

    public String getTaskScheduleEndtime() {
        return taskScheduleEndtime;
    }

    public void setTaskScheduleEndtime(String taskScheduleEndtime) {
        this.taskScheduleEndtime = taskScheduleEndtime;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public String getCurrentNodeName() {
        return currentNodeName;
    }

    public void setCurrentNodeName(String currentNodeName) {
        this.currentNodeName = currentNodeName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskManageName() {
        return taskManageName;
    }

    public void setTaskManageName(String taskManageName) {
        this.taskManageName = taskManageName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /**
     * 服务内容初设计划时间的总天数
     * @return
     */
    public int getAllDay() {
        String startTime=this.taskScheduleStatime;
        String endTime=this.taskScheduleEndtime;
        if(StringUtil.isNullOrEmpty(startTime) || StringUtil.isNullOrEmpty(endTime)){
            allDay=0;
        }else {
            Date startDate = DateUtils.str2Date(startTime,DateUtils.date_sdf);
            Date endDate = DateUtils.str2Date(endTime,DateUtils.date_sdf);
            allDay=DateUtils.daysOfTwo(endDate,startDate);
        }
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getMyNodeId() {
        return myNodeId;
    }

    public void setMyNodeId(String myNodeId) {
        this.myNodeId = myNodeId;
    }


    public int getEndSatus() {
        return endSatus;
    }

    public void setEndSatus(int endSatus) {
        this.endSatus = endSatus;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }
}