package com.maoding.v2.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.project.dto.ProjectDesignRangeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：AddProjectDTO
 * 类描述：项目立项DTO
 * 作    者：MaoSF
 * 日    期：2016年12月07日-下午4:35:50
 */
public class AddProjectDTO extends BaseDTO {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     *建设单位的id
     */
    private String constructId;

    /**
     * 建设单位名
     */
    private String constructName;

    /**
     *项目经营人（也可以移交给其他人）
     */
    private String managerId;

    /**
     *甲方公司名称
     */
    private String constructCompany;


    /**
     * 经营人
     */
    private String projectManagerId;

    /**
     * 设计阶段列表
     */
    private List<ProjectDesignContentInterfaceDTO> projectDesignContentList = new ArrayList<ProjectDesignContentInterfaceDTO>();

    /**
     * 设计范围列表
     */
    private List<ProjectDesignRangeDTO> projectDesignRangeList = new ArrayList<ProjectDesignRangeDTO>();

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectDesignRangeDTO> getProjectDesignRangeList() {
        return projectDesignRangeList;
    }

    public void setProjectDesignRangeList(List<ProjectDesignRangeDTO> projectDesignRangeList) {
        this.projectDesignRangeList = projectDesignRangeList;
    }

    public String getConstructId() {
        return constructId;
    }

    public void setConstructId(String constructId) {
        this.constructId = constructId;
    }

    public String getConstructName() {
        return constructName;
    }

    public void setConstructName(String constructName) {
        this.constructName = constructName;
    }

    public List<ProjectDesignContentInterfaceDTO> getProjectDesignContentList() {
        return projectDesignContentList;
    }

    public void setProjectDesignContentList(List<ProjectDesignContentInterfaceDTO> projectDesignContentList) {
        this.projectDesignContentList = projectDesignContentList;
    }

    public String getConstructCompany() {
        return constructCompany;
    }

    public void setConstructCompany(String constructCompany) {
        this.constructCompany = constructCompany;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }
}
