package com.maoding.user.dto;

import com.maoding.attach.dto.FilePathDTO;

public class AccountDataDTO extends FilePathDTO {

	private String id;
	/**
	 * 姓名
	 */
	private String userName;

    /**
     * 手机号码
     */
    private String cellphone;
    
    /**
     * 邮箱
     */
    private String email;

	/**用户头像**/
	private String headImg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
}
