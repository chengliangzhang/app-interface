package com.maoding.project.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wuwq on 2016/10/27.
 */
public class ProjectProcessShowDTO {

    /**
     * 节点名
     */
    private String nodeName;

    /**
     *操作人
     */
    List<ProjectProcessNodeDataDTO> userList = new ArrayList<ProjectProcessNodeDataDTO>();

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<ProjectProcessNodeDataDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<ProjectProcessNodeDataDTO> userList) {
        this.userList = userList;
    }
}
