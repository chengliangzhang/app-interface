package com.maoding.task.dto;

import com.maoding.projectmember.dto.ProjectMemberDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDataDTO
 * 类描述：任务分配界面数据
 * 作    者：MaoSF
 * 日    期：2015年8月15日-上午10:13:28
 */
public class ProjectTaskDataDTO {


    /**
     *项目id
     */
    private String id;

    /**
     *项目名称
     */
    private String projectName;

    /**
     *合作设计单位个数
     */
    private int otherPartnerCount;

    /**
     *项目经营人，项目负责人
     */
    List<ProjectMemberDTO> managerList = new ArrayList<>();

    /**
     * 任务列表
     */
    List<ProjectTaskDTO> taskList = new ArrayList<ProjectTaskDTO>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectMemberDTO> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<ProjectMemberDTO> managerList) {
        this.managerList = managerList;
    }

    public List<ProjectTaskDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ProjectTaskDTO> taskList) {
        this.taskList = taskList;
    }

    public int getOtherPartnerCount() {
        return otherPartnerCount;
    }

    public void setOtherPartnerCount(int otherPartnerCount) {
        this.otherPartnerCount = otherPartnerCount;
    }
}