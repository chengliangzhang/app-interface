package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：DesignBasisDTO
 * 类描述：设计依据DTO
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午4:48:50
 */
public class ProjectTaskTreeDTO extends BaseDTO {

    private String projectId;
    /**
     * 设计阶段名称
     */
    private String contentName;

    /**
     * 计划进度开始时间
     */
    private String planProgressStarttime;
    /**
     * 计划进度结束时间
     */
    private String planProgressEndtime;

    /**
     * 合同进度开始时间
     */
    private String contractProgressStarttime;
    /**
     * 合同进度结束时间
     */
    private String contractProgressEndtime;


    private int allDay;//共多少天

    private int overDay;//剩余多少天

    /**
     * 任务分解状态
     */
    private String taskStatus;

    private int relationStatus;

    private String endTime;


    private List<ProjectServerContentDTO> projectServerContenList = new ArrayList<ProjectServerContentDTO>();

    private List<ProjectTaskManagerDTO> taskList = new ArrayList<ProjectTaskManagerDTO>();

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getPlanProgressStarttime() {
        return planProgressStarttime;
    }

    public void setPlanProgressStarttime(String planProgressStarttime) {
        this.planProgressStarttime = planProgressStarttime;
    }

    public String getPlanProgressEndtime() {
        return planProgressEndtime;
    }

    public void setPlanProgressEndtime(String planProgressEndtime) {
        this.planProgressEndtime = planProgressEndtime;
    }

    public int getAllDay() {
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    public int getOverDay() {
        return overDay;
    }

    public void setOverDay(int overDay) {
        this.overDay = overDay;
    }

    public List<ProjectTaskManagerDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ProjectTaskManagerDTO> taskList) {
        this.taskList = taskList;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getContractProgressStarttime() {
        return contractProgressStarttime;
    }

    public void setContractProgressStarttime(String contractProgressStarttime) {
        this.contractProgressStarttime = contractProgressStarttime;
    }

    public String getContractProgressEndtime() {
        return contractProgressEndtime;
    }

    public void setContractProgressEndtime(String contractProgressEndtime) {
        this.contractProgressEndtime = contractProgressEndtime;
    }

    public List<ProjectServerContentDTO> getProjectServerContenList() {
        return projectServerContenList;
    }

    public void setProjectServerContenList(List<ProjectServerContentDTO> projectServerContenList) {
        this.projectServerContenList = projectServerContenList;
    }

    public int getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 处理设计阶段状态
     */
    public void HandleTaskStatus(){
        String taskStatus = null;
        int relationStatus = this.getRelationStatus();
        String taskScheduleEndtime = null;
        String taskScheduleStatime = null;

        if(!StringUtil.isNullOrEmpty(this.planProgressEndtime) || !StringUtil.isNullOrEmpty(this.planProgressStarttime)){
            taskScheduleEndtime = this.planProgressEndtime;
            taskScheduleStatime=this.planProgressStarttime;
        }else {
            taskScheduleEndtime= this.contractProgressEndtime;
            taskScheduleStatime=this.contractProgressStarttime;
        }

        String endTime = this.getEndTime();
        //taskStatus计算
        if (relationStatus==2){//如果已经完成
            if(StringUtil.isNullOrEmpty(taskScheduleEndtime)){
                taskStatus = "9";//完成
            }else {//如果结束时间不为空
                if (DateUtils.datecompareDate(endTime,taskScheduleEndtime)<=0){//正常完成
                    taskStatus  = "7";//正常完成
                }else {//超时完成
                    taskStatus  = "8";//超时完成
                }
            }
        }else {//如果没完成

            //1.开始为空&&结束时间为空
            if (StringUtil.isNullOrEmpty(taskScheduleStatime) && StringUtil.isNullOrEmpty(taskScheduleEndtime)) {
                taskStatus = "10";//空状态
            }

            //2.开始为空&&结束时间不为空
            if (StringUtil.isNullOrEmpty(taskScheduleStatime) && !StringUtil.isNullOrEmpty(taskScheduleEndtime)) {
                Date today = new Date();
                if (DateUtils.datecompareDate(DateUtils.date2Str(today, DateUtils.date_sdf), taskScheduleEndtime) <= 0) {//进行中
                    taskStatus = "5";//进行中
                } else {
                    taskStatus = "6";//超时进行
                }
            }

            //3.开始不为空&&结束时间为空
            if (!StringUtil.isNullOrEmpty(taskScheduleStatime) && StringUtil.isNullOrEmpty(taskScheduleEndtime)) {
                Date today = new Date();
                if (DateUtils.datecompareDate(DateUtils.date2Str(today, DateUtils.date_sdf), taskScheduleStatime) >= 0) {//进行中
                    taskStatus = "5";//进行中
                } else {
                    taskStatus = "4";//未开始
                }
            }

            //4.开始不为空&&结束时间为不空
            if (!StringUtil.isNullOrEmpty(taskScheduleStatime) && !StringUtil.isNullOrEmpty(taskScheduleEndtime)) {
                Date today = new Date();
                if (DateUtils.datecompareDate(DateUtils.date2Str(today, DateUtils.date_sdf), taskScheduleStatime) >= 0) {//进行中
                    if (DateUtils.datecompareDate(DateUtils.date2Str(today, DateUtils.date_sdf), taskScheduleEndtime) <= 0) {//正常进行
                        taskStatus = "3";//正常进行
                    } else {//超时进行
                        taskStatus = "6";//超时进行
                    }
                } else {
                    taskStatus = "4";//未开始
                }
            }
        }
        this.setTaskStatus(taskStatus);
    }
}
