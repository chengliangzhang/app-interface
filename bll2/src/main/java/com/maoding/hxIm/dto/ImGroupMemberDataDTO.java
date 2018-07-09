package com.maoding.hxIm.dto;

import com.maoding.attach.dto.FilePathDTO;

/**
 * Created by sandy on 2017/8/7.
 */
public class ImGroupMemberDataDTO extends FilePathDTO {

    /**
     * companyUserId
     */
    private String id;

    private String userId;

    private String userName;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
