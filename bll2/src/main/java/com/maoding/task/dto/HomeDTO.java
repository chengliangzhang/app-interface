package com.maoding.task.dto;

import com.maoding.financial.dto.ExpAmountDTO;
import com.maoding.mytask.dto.MyTaskCountDTO;
import com.maoding.mytask.dto.MyTaskDTO;
import com.maoding.project.dto.ProjectCountDTO;
import com.maoding.project.dto.ProjectProgressDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandy on 2017/9/8.
 */
public class HomeDTO {

    private int permissionFlag;//看见项目总览的权限1：有权限，其他：没有权限
    /**
     * 企业Banner
     */
    private List<String> companyBanner = new ArrayList<>();

    /**********我的项目---项目总览************/

    private CountDTO projectCount = new CountDTO();

    /**********我的项目---我参与的******************/

    private CountDTO myProjectCount = new CountDTO();

    /**************我的任务******************************/

    private CountDTO myTaskCount = new CountDTO();

    /***************审批--待我审批*********************/
    private ApproveCount approveCount = new ApproveCount();


    /***********审批--我提交的*********************/
    private ApproveCount mySubmitCount = new ApproveCount();

    /***********审批--我完成审批的*********************/
    private ApproveCount myApprovedCount = new ApproveCount();

    private List<MyTaskDTO> taskList = new ArrayList<>();

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

    public CountDTO getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(CountDTO projectCount) {
        this.projectCount = projectCount;
    }

    public CountDTO getMyProjectCount() {
        return myProjectCount;
    }

    public void setMyProjectCount(CountDTO myProjectCount) {
        this.myProjectCount = myProjectCount;
    }

    public CountDTO getMyTaskCount() {
        return myTaskCount;
    }

    public void setMyTaskCount(CountDTO myTaskCount) {
        this.myTaskCount = myTaskCount;
    }

    public ApproveCount getApproveCount() {
        return approveCount;
    }

    public void setApproveCount(ApproveCount approveCount) {
        this.approveCount = approveCount;
    }

    public ApproveCount getMySubmitCount() {
        return mySubmitCount;
    }

    public void setMySubmitCount(ApproveCount mySubmitCount) {
        this.mySubmitCount = mySubmitCount;
    }

    public List<MyTaskDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<MyTaskDTO> taskList) {
        this.taskList = taskList;
    }

    public ApproveCount getMyApprovedCount() {
        return myApprovedCount;
    }

    public void setMyApprovedCount(ApproveCount myApprovedCount) {
        this.myApprovedCount = myApprovedCount;
    }
}
