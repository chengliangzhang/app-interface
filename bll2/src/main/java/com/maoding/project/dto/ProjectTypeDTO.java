package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTypeDTO
 * 类描述：项目类型DTO
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:42:50
 */
public class ProjectTypeDTO extends BaseDTO {

    /**
     * 名称
     */
    private String typeName;

    public ProjectTypeDTO() {
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
