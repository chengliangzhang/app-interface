package com.maoding.org.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrgDataDTO {

    /**
     * 当前部门的全路径
     */
    private String departFullName ;

    /**
     * 父部门的名字
     */
    private String parentDepartName = "";

    /**
     * 当前部门的名称
     */
    private String departName;

    /**
     * 当前部门的子部门
     */
    private List<DepartAndUserDTO> departList = new ArrayList<>();

    /**
     * 父部门的 id 和 departName
     */
    private List<Map<String, String>> parentDepartList = new ArrayList<>();

    /**
     * 当前部门下的成员
     */
    private List<CompanyUserDataDTO> departUserList = new ArrayList<>();

    /**
     * 当前部门的路径的id
     */
    private List<String> idList = new ArrayList<>();

    /**
     * 当前节点的所有成员
     */

    List<CompanyUserDataDTO> userForCurrentNode = new ArrayList<>();

    public List<Map<String, String>> getParentDepartList() {
        return parentDepartList;
    }

    public void setParentDepartList(List<Map<String, String>> parentDepartList) {
        this.parentDepartList = parentDepartList;
    }

    public List<DepartAndUserDTO> getDepartList() {
        return departList;
    }

    public void setDepartList(List<DepartAndUserDTO> departList) {
        this.departList = departList;
    }

    public List<CompanyUserDataDTO> getDepartUserList() {
        return departUserList;
    }

    public void setDepartUserList(List<CompanyUserDataDTO> departUserList) {
        this.departUserList = departUserList;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getDepartFullName() {
        return departFullName;
    }

    public void setDepartFullName(String departFullName) {
        this.departFullName = departFullName;
    }

    public String getParentDepartName() {
        return parentDepartName;
    }

    public void setParentDepartName(String parentDepartName) {
        this.parentDepartName = parentDepartName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public List<CompanyUserDataDTO> getUserForCurrentNode() {
        return userForCurrentNode;
    }

    public void setUserForCurrentNode(List<CompanyUserDataDTO> userForCurrentNode) {
        this.userForCurrentNode = userForCurrentNode;
    }
}

