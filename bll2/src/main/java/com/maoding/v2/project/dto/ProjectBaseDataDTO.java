package com.maoding.v2.project.dto;

import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.project.dto.ProjectConstructDTO;
import com.maoding.system.dto.DataDictionaryDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectBaseDataDTO
 * 类描述：项目立项基础数据DTO
 * 作    者：MaoSF
 * 日    期：2016年12月07日-下午4:35:50
 */
public class ProjectBaseDataDTO{

    /**
     * 设计阶段列表
     */
    private List<DataDictionaryDTO> projectDesignContentList = new ArrayList<DataDictionaryDTO>();

    /**
     * 设计范围列表
     */
    private List<DataDictionaryDTO> projectDesignRangeList = new ArrayList<DataDictionaryDTO>();

    /**
     * 甲方
     */
    private List<ProjectConstructDTO> projectConstructList = new ArrayList<ProjectConstructDTO>();

    /**
     * 经营负责人
     */
    private List<CompanyUserDataDTO> companyUserList = new ArrayList<>();

    public List<CompanyUserDataDTO> getCompanyUserList() {
        return companyUserList;
    }

    public void setCompanyUserList(List<CompanyUserDataDTO> companyUserList) {
        this.companyUserList = companyUserList;
    }

    public List<DataDictionaryDTO> getProjectDesignContentList() {
        return projectDesignContentList;
    }

    public void setProjectDesignContentList(List<DataDictionaryDTO> projectDesignContentList) {
        this.projectDesignContentList = projectDesignContentList;
    }

    public List<DataDictionaryDTO> getProjectDesignRangeList() {
        return projectDesignRangeList;
    }

    public void setProjectDesignRangeList(List<DataDictionaryDTO> projectDesignRangeList) {
        this.projectDesignRangeList = projectDesignRangeList;
    }

    public List<ProjectConstructDTO> getProjectConstructList() {
        return projectConstructList;
    }

    public void setProjectConstructList(List<ProjectConstructDTO> projectConstructList) {
        this.projectConstructList = projectConstructList;
    }
}
