package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskManagerDTO
 * 类 描 述：任务分解DTO
 * 作    者：MaoSF
 * 日    期：2016年8月3日-下午1:47:23
 */
public class ProjectTaskManagerTableDTO extends BaseDTO {

    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 任务名称
     */
    private String taskName;

    private List<ProjectTaskManagerTableDetailDTO> detailList = new ArrayList<ProjectTaskManagerTableDetailDTO>();

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }



    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<ProjectTaskManagerTableDetailDTO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ProjectTaskManagerTableDetailDTO> detailList) {
        this.detailList = detailList;
    }
}