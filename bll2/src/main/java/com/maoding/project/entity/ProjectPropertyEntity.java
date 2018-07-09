package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;
import com.maoding.core.util.BeanUtilsEx;

/**
 * Created by Chengliang.zhang on 2017/8/16.
 */
public class ProjectPropertyEntity extends BaseEntity {
    /** 属性所在项目id */
    String projectId;
    /** 属性名称 */
    String fieldName;
    /** 属性值（统一格式化为字符串 */
    String fieldValue;
    /** 属性单位 */
    String unitName;
    /** 属性在自定义属性中的排序序号 */
    Integer sequencing;
    /** 删除标志 */
    Short deleted;

    public ProjectPropertyEntity(){}
    public ProjectPropertyEntity(Object obj){
        BeanUtilsEx.copyProperties(obj,this);
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getSequencing() {
        return sequencing;
    }

    public void setSequencing(Integer sequencing) {
        this.sequencing = sequencing;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }
}
