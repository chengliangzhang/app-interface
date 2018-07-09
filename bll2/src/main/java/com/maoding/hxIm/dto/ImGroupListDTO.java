package com.maoding.hxIm.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandy on 2017/9/15.
 */
public class ImGroupListDTO {
    private String nodeId; //项目id
    private String name; //群组名称

    private List<ImGroupDataDTO> groupList = new ArrayList<>();

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImGroupDataDTO> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<ImGroupDataDTO> groupList) {
        this.groupList = groupList;
    }
}
