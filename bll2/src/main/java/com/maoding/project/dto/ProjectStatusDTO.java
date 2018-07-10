package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * @date : 2018/7/10
 * @description :
 */
public class ProjectStatusDTO extends BaseDTO {
    /** 状态名称 */
    private String name;

    public ProjectStatusDTO(String key, String name){
        setId(key);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
