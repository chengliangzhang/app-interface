package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskManagerDTO
 * 类 描 述：任务分解DTO
 * 作    者：MaoSF
 * 日    期：2016年8月3日-下午1:47:23
 */
public class ProjectTaskManagerTableDetailDTO extends BaseDTO {

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 进度开始时间
     */
    private String taskScheduleStatime;

    /**
     * 进度结束时间
     */
    private String taskScheduleEndtime;

    /**
     * 设计人（多个以,隔开）
     */
    private String taskDesigner;

    /**
     * 负责人（多个以,隔开）
     */
    private String taskHead;

    /**
     * 设计人（多个以,隔开）
     */
    private String taskDesignerName;

    /**
     * 负责人（多个以,隔开）
     */
    private String taskHeadName;

    /**
     * 完成状态
     */
    private String taskStatus;


    private String taskStatusName;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

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

    public String getTaskDesigner() {
        return taskDesigner;
    }

    public void setTaskDesigner(String taskDesigner) {
        this.taskDesigner = taskDesigner;
    }

    public String getTaskHead() {
        return taskHead;
    }

    public void setTaskHead(String taskHead) {
        this.taskHead = taskHead;
    }

    public String getTaskDesignerName() {
        return taskDesignerName;
    }

    public void setTaskDesignerName(String taskDesignerName) {
        this.taskDesignerName = taskDesignerName;
    }

    public String getTaskHeadName() {
        return taskHeadName;
    }

    public void setTaskHeadName(String taskHeadName) {
        this.taskHeadName = taskHeadName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getType() {
        String type="";
        switch (taskStatus){
            case ("0"):
                type = "未开始";break;
            case ("1"):
                type = "已取消";break;
            case ("2"):
                type = "暂停中";break;
            case ("3"):
                type = "进行中";break;
            case ("4"):
                type = "已完成";break;
            default:
                ;
        }
        return type;
    }

    public String getTaskStatusName() {
        String taskStatusName="";
        switch (taskStatus){
            case ("0"):
                taskStatusName = "未开始";break;
            case ("1"):
                taskStatusName = "已取消";break;
            case ("2"):
                taskStatusName = "暂停中";break;
            case ("3"):
                taskStatusName = "进行中";break;
            case ("4"):
                taskStatusName = "已完成";break;
            default:
                ;
        }
        return taskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        this.taskStatusName = taskStatusName;
    }
}