package com.maoding.project.dto;

import com.maoding.org.dto.CompanyUserAppDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wuwq on 2016/10/27.
 */
public class ProjectTaskProcessNodeDTO {

    private String nodeName;
    private Integer memberType;

    List<CompanyUserAppDTO> userList = new ArrayList<>();

    public ProjectTaskProcessNodeDTO(){

    }
    public ProjectTaskProcessNodeDTO(String nodeName,Integer memberType){
        this.nodeName = nodeName;
        this.memberType = memberType;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getNodeName() {
        if(memberType!=null){
            int type = memberType.intValue();
            switch (type){
                case 0:
                    return "立项人";
                case 1:
                    return "经营负责人";
                case 2:
                    return "设计负责人";
                case 3:
                    return "任务负责人";
                case 4:
                    return "设计人";
                case 5:
                    return "校对人";
                case 6:
                    return "审核人";
                default:
                    return "";
            }
        }
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<CompanyUserAppDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<CompanyUserAppDTO> userList) {
        this.userList = userList;
    }
}
