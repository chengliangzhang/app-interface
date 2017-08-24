package com.maoding.project.dto;
import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wuwq on 2016/10/27.
 */
public class ProjectProcessDTO extends BaseDTO {

    private String processName;

    private String companyId;

    private String projectId;

    private String taskId;

    private String status;

    private int seq;

    private List<ProjectProcessNodeDTO> nodes=new ArrayList<ProjectProcessNodeDTO>();

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<ProjectProcessNodeDTO> getNodes() {
        return nodes;
    }

    public void setNodes(List<ProjectProcessNodeDTO> nodes) {
        this.nodes = nodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

}
