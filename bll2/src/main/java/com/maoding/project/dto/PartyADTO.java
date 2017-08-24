package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.Date;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：PartyADTO
 * 类描述：甲方DTO
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:42:50
 */
public class PartyADTO extends BaseDTO {

    /**
     * 名称
     */
    private String partyAName;

    public PartyADTO() {
    }

    public String getPartyAName() {
        return partyAName;
    }

    public void setPartyAName(String partyAName) {
        this.partyAName = partyAName;
    }
}
