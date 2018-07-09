package com.maoding.role.dto;

import com.maoding.org.dto.CompanyUserAppDTO;

import java.util.ArrayList;
import java.util.List;


public class PermissionDTO {

    private String id;

    /**
     * code值
     */
    private String code;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 父权限ID
     */
    private String pid;
    /**
     * 描述
     */
    private String description;

    private int isSingle;

    /**
     * 权限中对应的人员
     */
    List<CompanyUserAppDTO> companyUserList = new ArrayList<CompanyUserAppDTO>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }


    public List<CompanyUserAppDTO> getCompanyUserList() {
        return companyUserList;
    }

    public void setCompanyUserList(List<CompanyUserAppDTO> companyUserList) {
        this.companyUserList = companyUserList;
    }

    public int getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(int isSingle) {
        this.isSingle = isSingle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}