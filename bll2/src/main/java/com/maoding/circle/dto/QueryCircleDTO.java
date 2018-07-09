package com.maoding.circle.dto;

import com.maoding.core.base.dto.QueryDTO;

/**
 * Creator: sandy
 * Date:2017/11/15
 * 类名：app-interface
 */
public class QueryCircleDTO extends QueryDTO {

    private String projectId;

    private Integer isRead;

    //最新评论通知记录的id（用于前端删除消息列表，传递过来的参数）
    private String lastCircleReadId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getLastCircleReadId() {
        return lastCircleReadId;
    }

    public void setLastCircleReadId(String lastCircleReadId) {
        this.lastCircleReadId = lastCircleReadId;
    }
}
