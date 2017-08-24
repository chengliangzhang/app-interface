package com.maoding.task.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：QueryProjectTaskDTO
 * 类描述：任务查询条件DTO
 * 作    者：MaoSF
 * 日    期：2016年12月31日-上午10:13:28
 */
public class QueryProjectTaskDTO extends BaseDTO {

    Integer pageIndex;
    Integer pageSize;

    private String companyId;//当前公司id

    private String projectId;//当前项目

    /**
     * 是否是合作方（1：是，0：否）
     */
    private int isCooperator;//是否是立项方


     public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public int getIsCooperator() {
        return isCooperator;
    }

    public void setIsCooperator(int isCooperator) {
        this.isCooperator = isCooperator;
    }
}
