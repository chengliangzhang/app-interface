package com.maoding.user.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：RegisterAccountDTO
 * 类描述：用户注册DTO
 * 作    者：MaoSF
 * 日    期：2016年7月7日-上午9:50:37
 */
@SuppressWarnings("serial")
public class AccountDTO extends BaseDTO {
	
/************************用户信息******************************/
	
	/**
	 * 姓名
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
     * 手机号码
     */
    private String cellphone;
    
    /**
     * 验证码
     */
    private String code;//前台验证码
    
    /**
     * 邮箱
     */
    private String email;
    /**
     * 默认企业id
     */
    private String defaultCompanyId;
    
    
	/**
	 * 出生日期
	 */
    private String birthday;
    /**
     * 性别
     */
    private String sex;
    
    /**
     * 账号状态(1:未激活，0：激活）
     */
    private String status;

	/**
	 * app端使用sessionId
     */
	private String sessionId;

    private String oldPassword;

	private String major;//专业

	/**用户头像**/
	private String headImg;

	private String activeTime;//激活时间

	private String filePath;//附件地址

	private String fileName;//附件名称

	private String fileGroup;//文件分组


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

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

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDefaultCompanyId() {
		return defaultCompanyId;
	}

	public void setDefaultCompanyId(String defaultCompanyId) {
		this.defaultCompanyId = defaultCompanyId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getFileGroup() {
		return fileGroup;
	}

	public void setFileGroup(String fileGroup) {
		this.fileGroup = fileGroup;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
}
