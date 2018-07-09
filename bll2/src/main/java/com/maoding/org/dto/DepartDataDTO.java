package com.maoding.org.dto;

public class DepartDataDTO {

    private String id;

    /**
     * 部门名称
     */
    private String departName;

    /**
     * 部门全名称
     */
    private String fullName;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 父部门id
     */
    private String pid;

    /**
     * 部门层级
     */
    private String departLevel;

    /**
     * 父部分的departPath
     */
    private String departPath;

    /**
     * 0：部门，1：分公司
     */
    private String orgType;

    /**
     * 类型图标
     */
    private String type;

    private String serverStation;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDepartLevel() {
        return departLevel;
    }

    public void setDepartLevel(String departLevel) {
        this.departLevel = departLevel;
    }

    public String getDepartPath() {
        return departPath;
    }

    public void setDepartPath(String departPath) {
        this.departPath = departPath;
    }


    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getServerStation() {
        return serverStation;
    }

    public void setServerStation(String serverStation) {
        this.serverStation = serverStation;
    }
}
