package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * 日    期 : 2018/6/25 17:24
 * 描    述 :
 */
public class ProjectBuiltTypeDTO extends BaseDTO {
    /**
     * 功能分类名称
     */
    private String name;
    /**
     * 选中状态：如果是取消选择的话，id不可为空
     */
    private Boolean isSelected;

    /** 选中状态的字符串形式，0-未选中，1-选中 */
    private String isSelectedStatus;

    /**
     * 是否默认功能分类
     */
    private Boolean isTemplate;

    /** 默认功能分类字符串形式，0-不是默认，1-是默认 */
    private String isTemplateStatus;

    public String getIsSelectedStatus() {
        return (isSelectedStatus == null) ? toString(getSelected()) : isSelectedStatus;
    }

    public void setIsSelectedStatus(String isSelectedStatus) {
        this.isSelectedStatus = isSelectedStatus;
        setSelected(toBoolean(isSelectedStatus));
    }

    public String getIsTemplateStatus() {
        return (isTemplateStatus == null) ? toString(getTemplate()) : isTemplateStatus;
    }

    public void setIsTemplateStatus(String isTemplateStatus) {
        this.isTemplateStatus = isTemplateStatus;
        setTemplate(toBoolean(isTemplateStatus));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
