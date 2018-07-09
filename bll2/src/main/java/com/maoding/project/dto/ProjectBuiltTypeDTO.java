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

    /**
     * 是否默认功能分类
     */
    private Boolean isDefaulted;

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

    public Boolean getDefaulted() {
        return isDefaulted;
    }

    public void setDefaulted(Boolean defaulted) {
        isDefaulted = defaulted;
    }
}
