package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：BuiltTypeDTO
 * 类描述：建筑功能DTO
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:42:50
 */
public class BuiltTypeDTO extends BaseDTO {

    /**
     * 名称
     */
    private String builtType;

    public BuiltTypeDTO() {
    }

    public String getBuiltType() {
        return builtType;
    }

    public void setBuiltType(String builtType) {
        this.builtType = builtType;
    }
}
