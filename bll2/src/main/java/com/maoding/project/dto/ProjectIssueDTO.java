package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * 深圳市设计同道技术有限公司（2.0）
 * 类    名：ProjectDesignContentDetailEntity
 * 类描述：项目任务签发模块DTO
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午4:11:50
 */
public class ProjectIssueDTO extends BaseDTO {

    private String projectName;//项目名称

    /**
     *设计阶段信息
     */
    private List<ProjectDesignPeriodDTO> designContentList = new ArrayList<ProjectDesignPeriodDTO>();


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectDesignPeriodDTO> getDesignContentList() {
        return designContentList;
    }

    public void setDesignContentList(List<ProjectDesignPeriodDTO> designContentList) {
        this.designContentList = designContentList;
    }

}
