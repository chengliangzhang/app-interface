package com.maoding.role.dto;

public class OrgRoleTypeDTO {

    private String roleType;

    private String title;

    private String content;

    public OrgRoleTypeDTO(String roleType,String title,String content){
        this.roleType = roleType;
        this.title = title;
        this.content = content;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
