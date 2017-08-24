package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

public class ProjectProcessNodeHistoryEntity extends BaseEntity {
    private String processInstanceId;

    private String processNodeId;

    private String companyUserId;

    private String nodeName;

    private int nodeStatus;

    private int status;

    private String memo;

    private int seq;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    public String getProcessNodeId() {
        return processNodeId;
    }

    public void setProcessNodeId(String processNodeId) {
        this.processNodeId = processNodeId == null ? null : processNodeId.trim();
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId == null ? null : companyUserId.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public int getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(int nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}