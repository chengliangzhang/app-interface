package com.maoding.project.dto;

import com.maoding.commonModule.dto.ContentDTO;
import com.maoding.core.util.BeanUtilsEx;

/**
 * Created by Chengliang.zhang on 2017/8/14.
 * 自定义项目属性
 */
public class ProjectPropertyDTO extends ContentDTO {
    /** 属性名称 */
    private String fieldName;
    /** 属性值（统一格式化为字符串 */
    private String fieldValue;
    /** 属性单位 */
    private String unitName;
    /** 属性在自定义属性中的排序序号 */
    private Integer sequencing;
    /** 记录更新状态,-1：删除，0：未更改，1：新增，2：更改 */
    private Short changeStatus;
    /** 模板内容编号 */
    private String contentId;
    /** 选中状态 */
    private Boolean isSelected;
    /** 是否属于模板内容 */
    private Boolean isTemplate;

    public ProjectPropertyDTO(){}
    public ProjectPropertyDTO(Object obj){
        BeanUtilsEx.copyProperties(obj,this);
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public Boolean getTemplate() {
        return isTemplate;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getFieldName() {
        return (fieldName == null) ? getName() : fieldName;
    }

    public void setFieldName(String fieldName) {
        if (fieldName != null) {
            setName(fieldName);
        }
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getUnitName() {
        return (unitName == null) ? getDetailUnit() : unitName;
    }

    public void setUnitName(String unitName) {
        if (unitName != null) {
            setDetailUnit(unitName);
        }
        this.unitName = unitName;
    }

    public Integer getSequencing() {
        return (sequencing == null) ? getDetailId() : sequencing;
    }

    public void setSequencing(Integer sequencing) {
        if (sequencing != null) {
            setDetailId(sequencing);
        }
        this.sequencing = sequencing;
    }

    public Short getChangeStatus() {
        Short c = changeStatus;
        if (c == null){
            if (getSelected() == null){
                c = 0;
            } else if (getSelected()){
                c = 2;
            } else {
                c = -1;
            }
        }
        return c;
    }

    public void setChangeStatus(Short changeStatus) {
        if ((changeStatus != null) && (changeStatus != 0)){
            setSelected(changeStatus > 0);
        }
        this.changeStatus = changeStatus;
    }
}
