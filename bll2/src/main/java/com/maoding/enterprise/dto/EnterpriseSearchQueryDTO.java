package com.maoding.enterprise.dto;


import com.maoding.core.base.dto.QueryDTO;

public class EnterpriseSearchQueryDTO {

    private String enterpriseId;

    private String name;

    private int size;

    private String companyId;

    private String enterpriseOrgId;//app端查询甲方详情使用该字段

    private String projectId;

    private String accountId;

    boolean isSave;

    /**
     * 组织ID
     */
    private String id;
    /**
     * 分页时的页码
     */
    private Integer pageIndex;
    /**
     * 分页查询时的页面大小
     */
    private Integer pageSize;
    /**
     * 起始记录数
     */
    private Integer startLine;

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        if(size==0){
            size=20;
        }
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public String getEnterpriseOrgId() {
        return enterpriseOrgId;
    }

    public void setEnterpriseOrgId(String enterpriseOrgId) {
        this.enterpriseOrgId = enterpriseOrgId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPageIndex() {
        Integer i = pageIndex;
        if ((pageIndex == null) && (startLine != null) && (pageSize != null)){
            i = (startLine/pageSize) + 1;
        }
        return i;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        if ((this.pageIndex != null) && (pageSize != null)){
            startLine = this.pageIndex * pageSize;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        if ((this.pageSize != null) && (pageIndex != null)){
            startLine = pageIndex * this.pageSize;
        }
    }

    public Integer getStartLine() {
        Integer i = startLine;
        if ((startLine == null) && (pageIndex != null) && (pageSize != null)){
            i = pageIndex * pageSize;
        }
        return i;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
        if ((this.startLine != null) && (pageSize != null)){
            pageIndex = (this.startLine/pageSize) + 1;
        }
    }

}
