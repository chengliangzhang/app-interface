package com.maoding.role.dto;

/**
 * 作    者 : DongLiu
 * 日    期 : 2018/1/5 10:54
 * 描    述 : 项目总览获取权限
 */
public enum PermissionEnum {
    ORG_MANAGER("sys_enterprise_logout", "11"), //组织解散
    PROJECT_MANAGER("project_manager", "51"),   //任务签发
    DESIGN_MANAGER("design_manager", "52"),     //生产安排
    SUPER_PROJECT_EDIT("super_project_edit", "53"),
    PROJECT_EDIT("project_edit", "20"), //项目信息编辑、项目总览、查看项目文档
    PROJECT_CHARGE_MANAGER("project_charge_manage", "49"),
    PROJECT_CHARGE_PAID("finance_back_fee", "402"),
    SYS_ROLE_PERMISSION("sys_role_permission", "8"),
    //BACKGROUND_MANAGER("background_management","104"),
    HR_ORG_SET("hr_org_set","14"),//组织架构设置
    ORG_PARTNER("org_partner","12"),//分公司，事业合伙人
    ORG_EDIT("com_enterprise_edit","12"),//组织信息管理
    PROJECT_OVERVIEW("project_overview", "56");

    private String id;
    private String code;

    PermissionEnum(String code, String id) {
        this.id = id;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
