package com.maoding.hxIm.dto;


import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Idccapp21 on 2016/8/1.
 */
public class ImGroupCustomerDTO extends BaseDTO{

    /**
     * 组织Id
     */
    private String orgId;


    /**
     * 群Id (新表中的group_no,环信id)
     */
    private String groupId;

    /**
     * 管理员
     */
    private String admin;

    /**
     * 群主名称
     */
    private String name;

    /**
     * 群主名称
     */
    private String groupType;

    /**
     * 图标地址
     */
    private String img;

    private List<ImGroupCustomerUserDTO> imGroupCustomerUserList = new ArrayList<ImGroupCustomerUserDTO>();

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public List<ImGroupCustomerUserDTO> getImGroupCustomerUserList() {
        return imGroupCustomerUserList;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}