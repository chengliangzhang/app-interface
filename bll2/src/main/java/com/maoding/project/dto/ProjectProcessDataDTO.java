package com.maoding.project.dto;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wuwq on 2016/10/27.
 */
public class ProjectProcessDataDTO{

    private String id;

    private String companyId;

    /**
     *流程名
     */
    private String processName;

    private String projectId;

    /**
     *任务id
     */
    private String taskId;

    private String status;


    /**
     * 移动端使用
     */
    private int flag;//0:没有完成，2：完成

    /**
     * 移动端使用
     */
    private String currentNodeId;//处理到当前节点的id

    /**
     * 移动端使用
     */
    private int endSatus;//0,4：无操作，1.提交，2.打回，通过，3.打回，完成


    private String lastNodeId;

    private List<ProjectProcessNodeDataDTO> nodes=new ArrayList<ProjectProcessNodeDataDTO>();

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
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

    public List<ProjectProcessNodeDataDTO> getNodes() {
        return nodes;
    }

    public void setNodes(List<ProjectProcessNodeDataDTO> nodes) {
        this.nodes = nodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public int getEndSatus() {
        return endSatus;
    }

    public void setEndSatus(int endSatus) {
        this.endSatus = endSatus;
    }

    public String getLastNodeId() {
        return lastNodeId;
    }

    public void setLastNodeId(String lastNodeId) {
        this.lastNodeId = lastNodeId;
    }
}
