package com.maoding.task.dto;

import com.maoding.financial.dto.ExpAmountDTO;
import com.maoding.mytask.dto.MyTaskCountDTO;
import com.maoding.mytask.dto.MyTaskDTO;
import com.maoding.project.dto.ProjectCountDTO;
import com.maoding.project.dto.ProjectProgressDTO;
import com.maoding.project.entity.ProjectSkyDriveEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandy on 2017/9/8.
 */
public class HomeDataDTO {

    /**
     * 权限标识
     */
    private int permissionFlag;

    /**
     * 企业Banner
     */
    private List<String> companyBanner = new ArrayList<>();

    /**
     * 我的任务统计数据
     */
    private MyTaskCountDTO myTaskCount = new MyTaskCountDTO();

    /**
     * 即将到期的任务（距离结束时期一个星期）
     */
    private List<MyTaskDTO> dueTaskList = new ArrayList<>();

    /**
     * 超时进行的任务
     */
    private List<MyTaskDTO> overtimeTaskList = new ArrayList<>();

    /**
     * 项目统计数据
     */
    private ProjectCountDTO projectCount = new ProjectCountDTO();

    /**
     * 项目进度数据
     */
    private List<ProjectProgressDTO> projectList = new ArrayList<>();


    /**
     * 报销板块数据
     */
    private ExpAmountDTO expAmount = new ExpAmountDTO();

    public int getPermissionFlag() {
        return permissionFlag;
    }

    public void setPermissionFlag(int permissionFlag) {
        this.permissionFlag = permissionFlag;
    }

    public List<String> getCompanyBanner() {
        return companyBanner;
    }

    public void setCompanyBanner(List<String> companyBanner) {
        this.companyBanner = companyBanner;
    }

    public MyTaskCountDTO getMyTaskCount() {
        return myTaskCount;
    }

    public void setMyTaskCount(MyTaskCountDTO myTaskCount) {
        this.myTaskCount = myTaskCount;
    }

    public List<MyTaskDTO> getDueTaskList() {
        return dueTaskList;
    }

    public void setDueTaskList(List<MyTaskDTO> dueTaskList) {
        this.dueTaskList = dueTaskList;
    }

    public List<MyTaskDTO> getOvertimeTaskList() {
        return overtimeTaskList;
    }

    public void setOvertimeTaskList(List<MyTaskDTO> overtimeTaskList) {
        this.overtimeTaskList = overtimeTaskList;
    }

    public ProjectCountDTO getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(ProjectCountDTO projectCount) {
        this.projectCount = projectCount;
    }

    public List<ProjectProgressDTO> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectProgressDTO> projectList) {
        this.projectList = projectList;
    }

    public ExpAmountDTO getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(ExpAmountDTO expAmount) {
        this.expAmount = expAmount;
    }
}
