package com.maoding.hxIm.dto;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ImGroupDataDTO {

    /**组织ID**/
    private String orgId;

    /**根组织ID**/
    private String rootOrgId;

    /**群ID (环信群ID:groupNo)**/
    private String groupId;

    /**群组名称 使用的是groupName**/
    private String name;

    /**管理员ID，逗号分隔(groupOwner)**/
    private String admin;

    /**图片地址(groupImg)**/
    private String img;

    /** 群类型：0-公司群 1-部门群 2-自定义群 3-项目群 */
    private Integer groupType;

    private String url;

    /**************群组成员信息**************/

    /**群成员总数**/
    private Integer members;

    /**群员信息**/
    private List<ImGroupMemberDataDTO> memberInfo = new ArrayList<>();

    private Integer isCompany;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRootOrgId() {
        return rootOrgId;
    }

    public void setRootOrgId(String rootOrgId) {
        this.rootOrgId = rootOrgId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Integer getMembers() {
        if(CollectionUtils.isEmpty(this.getMemberInfo())){
            members = 0;
        }else {
            members = memberInfo.size();
        }
        return  members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public List<ImGroupMemberDataDTO> getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(List<ImGroupMemberDataDTO> memberInfo) {
        this.memberInfo = memberInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsCompany() {
        return groupType;
    }

    public void setIsCompany(Integer isCompany) {
        this.isCompany = isCompany;
    }
}
