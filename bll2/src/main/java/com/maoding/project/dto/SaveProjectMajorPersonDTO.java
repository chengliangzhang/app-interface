package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：SaveProjectMajorPersonDTO
 * 类描述：保存专业技术人DTO
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午4:04:50
 */
public class SaveProjectMajorPersonDTO extends BaseDTO {


    private String projectId;

    private String companyId;

    private String taskManagerId;
    //需要保存的对象
    List<ProjectMajorPersonDTO> saveMajorPersonList = new ArrayList<ProjectMajorPersonDTO>();

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<ProjectMajorPersonDTO> getSaveMajorPersonList() {
        return saveMajorPersonList;
    }

    public void setSaveMajorPersonList(List<ProjectMajorPersonDTO> saveMajorPersonList) {
        this.saveMajorPersonList = saveMajorPersonList;
    }

    public String getTaskManagerId() {
        return taskManagerId;
    }

    public void setTaskManagerId(String taskManagerId) {
        this.taskManagerId = taskManagerId;
    }
}
