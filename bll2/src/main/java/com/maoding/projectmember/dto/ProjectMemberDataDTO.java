package com.maoding.projectmember.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandy on 2017/8/30.
 * 项目综合数据--成员部分数据
 */
public class ProjectMemberDataDTO {

    /**
     * 参与组织数
     */
    private Integer orgCount;

    /**
     * 参与成员数
     */
    private Integer memberCount;

    /**
     * 经营负责人 + 设计负责人
     */
    private List<ProjectMemberDTO> managerList = new ArrayList<>();


    public Integer getOrgCount() {
        return orgCount;
    }

    public void setOrgCount(Integer orgCount) {
        this.orgCount = orgCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public List<ProjectMemberDTO> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<ProjectMemberDTO> managerList) {
        this.managerList = managerList;
    }
}
