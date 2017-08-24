package com.maoding.task.dto;

import com.maoding.projectmember.dto.ProjectMemberDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectManagerListDTO
 * 类描述：其他合作团队的经营人和项目负责人DTO
 * 作    者：MaoSF
 * 日    期：2016年12月28日-上午10:13:28
 */
public class ProjectManagerListDTO {


    private String companyName;


    private String companyId;

    private int type;

    private List<ProjectMemberDTO> managerList = new ArrayList<>();


    List<ProjectManagerListDTO> childList = new ArrayList<ProjectManagerListDTO>();

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<ProjectMemberDTO> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<ProjectMemberDTO> managerList) {
        this.managerList = managerList;
    }

    public List<ProjectManagerListDTO> getChildList() {
        return childList;
    }

    public void setChildList(List<ProjectManagerListDTO> childList) {
        this.childList = childList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}