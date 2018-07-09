package com.maoding.task.dto;

import com.maoding.task.dto.ProjectIssueTaskDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * 深圳市设计同道技术有限公司（2.0）
 * 类描述：项目任务签发模块DTO
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午4:11:50
 */
public class TaskDataDTO {

    /**
     * 任务总数（设计任务）
     */
    private Integer taskCount;

    /**
     * 超时进行
     */
    private Integer timeOutTaskCount;


    /**
     * 完成个数
     */
    private Integer completeTaskCount;


    /**
     * 任务列表数据（本月到期）
     */
    private List<ProjectIssueTaskDTO> taskList = new ArrayList<>();


    /**
     * 超时任务
     */
    private List<ProjectIssueTaskDTO> overtimeTaskList = new ArrayList<>();


    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public Integer getTimeOutTaskCount() {
        return timeOutTaskCount;
    }

    public void setTimeOutTaskCount(Integer timeOutTaskCount) {
        this.timeOutTaskCount = timeOutTaskCount;
    }

    public Integer getCompleteTaskCount() {
        return completeTaskCount;
    }

    public void setCompleteTaskCount(Integer completeTaskCount) {
        this.completeTaskCount = completeTaskCount;
    }

    public List<ProjectIssueTaskDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ProjectIssueTaskDTO> taskList) {
        this.taskList = taskList;
    }

    public List<ProjectIssueTaskDTO> getOvertimeTaskList() {
        return overtimeTaskList;
    }

    public void setOvertimeTaskList(List<ProjectIssueTaskDTO> overtimeTaskList) {
        this.overtimeTaskList = overtimeTaskList;
    }
}
