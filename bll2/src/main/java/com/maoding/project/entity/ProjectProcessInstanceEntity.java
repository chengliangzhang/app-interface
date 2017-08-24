package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

public class ProjectProcessInstanceEntity extends BaseEntity {
    private String processId;

    private String currentNodeId;

    private String currentName;

    private String currentHistoryId;

    private String lastNodeId;

    private int status;

    private String endTime;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId == null ? null : currentNodeId.trim();
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName == null ? null : currentName.trim();
    }

    public String getCurrentHistoryId() {
        return currentHistoryId;
    }

    public void setCurrentHistoryId(String currentHistoryId) {
        this.currentHistoryId = currentHistoryId == null ? null : currentHistoryId.trim();
    }

    public String getLastNodeId() {
        return lastNodeId;
    }

    public void setLastNodeId(String lastNodeId) {
        this.lastNodeId = lastNodeId == null ? null : lastNodeId.trim();
    }

    public int getStatus() {
        return status;
    }

    /**
     * -1：取消 0：暂停 1：进行中 2：已结束
     * */
    public void setStatus(int status) {
        this.status = status;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}