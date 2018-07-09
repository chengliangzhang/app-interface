package com.maoding.role.entity;

import com.maoding.core.base.entity.BaseEntity;


public class PermissionEntity extends BaseEntity {

    /**
     * code值
     */
    private String code;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 父权限ID
     */
    private String pid;
    /**
     * 根权限ID
     */
    private String rootId;
    /**
     * 排序
     */
    private int seq;

    /**
     * 权限描述
     */
    private String description;

    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId == null ? null : rootId.trim();
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}