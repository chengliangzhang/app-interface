package com.maoding.user.entity;

import com.maoding.core.base.entity.BaseEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：AccountRegistErrorEntity
 * 类描述：个人注册信息
 * 作    者：MaoSF
 * 日    期：2016年7月6日-下午4:47:05
 */
public class AccountRegistErrorEntity extends BaseEntity implements java.io.Serializable {

	/**
	 * 用户名（冗余）
	 */
	private String userName;
	
	/**
	 * 昵称
	 */
    private String nickName;
    

    /**
     * 密码
     */
    private String password;


    /**
     * 账号状态(1:未激活，0：激活）
     */
    private String status;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}