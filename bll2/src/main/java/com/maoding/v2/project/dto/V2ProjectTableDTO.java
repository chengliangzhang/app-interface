package com.maoding.v2.project.dto;

import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.task.dto.ProejctTaskListDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：V2ProjectTableDTO
 * 作者：chenzhujie
 * 日期：2016/12/24
 */
public class V2ProjectTableDTO{

    private String id;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 组织Id
     */
    private String companyId;

    /**
     * 立项组织名
     */
    private String companyName;
    /**
     * 用户名
     */
    private String addProjectUserName;


    /**当前人员与项目的关系：0：无关，1：参与,2.关注*/
    private int flag;


    /**项目设置阶段名*/
    private String designContent;

    private String createBy;

    private int deleteFlag;

    private CompanyUserAppDTO user;

    private List<ProejctTaskListDTO> taskList = new ArrayList<ProejctTaskListDTO>();

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getAddProjectUserName() {
        return addProjectUserName;
    }

    public void setAddProjectUserName(String addProjectUserName) {
        this.addProjectUserName = addProjectUserName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDesignContent() {
        return designContent;
    }

    public void setDesignContent(String designContent) {
        this.designContent = designContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CompanyUserAppDTO getUser() {
        return user;
    }

    public void setUser(CompanyUserAppDTO user) {
        this.user = user;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public List<ProejctTaskListDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ProejctTaskListDTO> taskList) {
        this.taskList = taskList;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
