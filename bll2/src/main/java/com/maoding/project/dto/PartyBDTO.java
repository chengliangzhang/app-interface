package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：PartyBDTO
 * 类描述：乙方DTO
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:42:50
 */
public class PartyBDTO extends BaseDTO {

    /**
     * 名称
     */
    private String partyBName;

    public PartyBDTO() {
    }

    public String getPartyBName() {
        return partyBName;
    }

    public void setPartyBName(String partyBName) {
        this.partyBName = partyBName;
    }
}
