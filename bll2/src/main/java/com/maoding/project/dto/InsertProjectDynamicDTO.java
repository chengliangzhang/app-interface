package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by Administrator on 2017/3/30.
 */
public class InsertProjectDynamicDTO extends BaseDTO {
    /**
     * 项目编号
     */
    private String projectId;
    /**
     * 公司编号
     */
    private String companyId;
    /**
     * 操作者的公司雇员编号
     */
    private String companyUserId;
    /**
     * 操作者编号
     */
    private String createBy;
    /**
     * 动态消息类型，操作类型定义可见SystemParameters.java文件内“项目动态动态类型”定义
     */
    private Integer type;
    /**
     * 操作者姓名（员工姓名）
     */
    private String userName;
    /**
     * 操作节点名，可以是项目名、任务树名、款项节点名、被更改元素的原名称
     */
    private String nodeName;
    /**
     * 更改对象，可以是“项目编号”、“付款金额”等字符串信息
     */
    private String action;
    /**
     * 更改对象内容，可以是新项目名、款项金额、发票描述、更改原因等信息
     */
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value == null) return;
        if (this.value == null) {
            this.value = value;
        } else {
            this.value += "；" + value;
        }
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        if (action == null) return;
        final Integer MAX_ACTION_LENGTH = 80;
        final String END_STRING = "等";
        if (this.action == null) {
            this.action = action;
        } else {
            if (this.action.length() < MAX_ACTION_LENGTH) {
                this.action += "，" + action;
            } else if (!this.action.endsWith(END_STRING)) {
                this.action += END_STRING;
            } else {
                return;
            }
        }
    }
}
