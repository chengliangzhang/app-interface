package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by Idccapp21 on 2016/10/28.
 */
public class ProjectProcessChangeDTO extends BaseDTO {

    /**
     * 流程Id
     */
    private String processId;

    /**
     * 当前节点名称
     */
    private String nodeName;

    /**
     * 当前节点Id
     */
    private String nodeId;

    /**
     * 操作状态
     */
    private int  status;

    private int indexPage;

    /**
     * 当前节点的排序
     */
    private int  nodeSeq;
    /**
     * 流程实例id
     */
    private String processInstanceId;

    private String companyUserId;

    private String taskManageId;

    private int isWsFlag;//是否是移动端

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }


    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }


    public int getNodeSeq() {
        return nodeSeq;
    }

    public void setNodeSeq(int nodeSeq) {
        this.nodeSeq = nodeSeq;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }

    public int getIsWsFlag() {
        return isWsFlag;
    }

    public void setIsWsFlag(int isWsFlag) {
        this.isWsFlag = isWsFlag;
    }

    public String getTaskManageId() {
        return taskManageId;
    }

    public void setTaskManageId(String taskManageId) {
        this.taskManageId = taskManageId;
    }
}
