package com.maoding.dynamic.dto;

import com.maoding.dynamic.entity.OrgDynamicEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Idccapp22 on 2017/3/10.
 */
public class OrgDynamicDataDTO {

    private String dateTime;
    private Date createDate;

    List<OrgDynamicEntity> dynamicList = new ArrayList<OrgDynamicEntity>();

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<OrgDynamicEntity> getDynamicList() {
        return dynamicList;
    }

    public void setDynamicList(List<OrgDynamicEntity> dynamicList) {
        this.dynamicList = dynamicList;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


}
