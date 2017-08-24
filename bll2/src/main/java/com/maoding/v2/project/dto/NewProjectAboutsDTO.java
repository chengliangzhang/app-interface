package com.maoding.v2.project.dto;

import com.maoding.project.dto.ProjectDesignRangeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：NewProjectAboutsDTO
 * 类描述：NewProjectAboutsDTO
 * 作    者：ChenZJ
 * 日    期：2016年12月06日-下午4:35:50
 */
public class NewProjectAboutsDTO{//不继承基类
    private String id;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     *项目所在组织
     */
    private String companyId;

    /**
     * 甲方
     */
    private String ConstructCompanyName;

    /**
     *立项人
     */
    private String addProjectPerson;

    /**
     * 项目完成度
     */
    private String percentageCompletion;

    /**
     * 设计范围列表
     */
    private List<ProjectDesignRangeDTO> projectDesignRangeList = new ArrayList<ProjectDesignRangeDTO>();

    /**
     * 设计阶段列表
     */
    private String projectDesignContent;

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

    public String getConstructCompanyName() {
        return ConstructCompanyName;
    }

    public void setConstructCompanyName(String constructCompanyName) {
        ConstructCompanyName = constructCompanyName;
    }

    public List<ProjectDesignRangeDTO> getProjectDesignRangeList() {
        return projectDesignRangeList;
    }

    public void setProjectDesignRangeList(List<ProjectDesignRangeDTO> projectDesignRangeList) {
        this.projectDesignRangeList = projectDesignRangeList;
    }

    public String getProjectDesignContent() {
        return projectDesignContent;
    }

    public void setProjectDesignContent(String projectDesignContent) {
        this.projectDesignContent = projectDesignContent;
    }

    public String getAddProjectPerson() {
        return addProjectPerson;
    }

    public void setAddProjectPerson(String addProjectPerson) {
        this.addProjectPerson = addProjectPerson;
    }

    public String getPercentageCompletion() {
        return percentageCompletion;
    }

    public void setPercentageCompletion(String percentageCompletion) {
        this.percentageCompletion = percentageCompletion;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
