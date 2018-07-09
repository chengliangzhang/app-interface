package com.maoding.user.dto;

import com.maoding.attach.dto.FilePathDTO;
import com.maoding.financial.dto.LeaveSimpleDTO;
import com.maoding.mytask.dto.MyTaskSimpleDTO;
import com.maoding.task.dto.TaskSimpleDTO;

import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：RegisterAccountDTO
 * 类描述：用户DTO
 * 作    者：MaoSF
 * 日    期：2016年7月7日-上午9:50:37
 */
@SuppressWarnings("serial")
public class UserInfoDTO extends FilePathDTO {
	
/************************用户信息******************************/
	private String id;

	/**
	 * 姓名
	 */
	private String userName;//user表中的姓名
	
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
    private String cellphone;//account表中的信息
    
    /**
     * 验证码
     */
    private String code;//前台验证码
    
    /**
     * 邮箱
     */
    private String email;//account表中的信息
    /**
     * 默认企业id
     */
    private String defaultCompanyId;
    
    
	/**
	 * 出生日期
	 */
    private String birthday;//user表中的信息
    /**
     * 性别
     */
    private String sex;//user表中的信息

    /**
     * 账号状态(1:未激活，0：激活）
     */
    private String status;

	/**
	 * 专业id
     */
	private String major;

	/**
	 * 专业名
     */
	private String majorName;

	/**
	 *
	 */

	/** 出勤情况 */
	private Integer workStatus;

	/** 请假列表 */
	private List<LeaveSimpleDTO> leaveList;

	/** 出差列表 */
	private List<LeaveSimpleDTO> workoutList;

	/** 轻量任务列表 */
	private List<MyTaskSimpleDTO> lightTaskList;

	/** 设计任务列表 */
	private List<TaskSimpleDTO> taskList;

	public List<LeaveSimpleDTO> getWorkoutList() {
		return workoutList;
	}

	public void setWorkoutList(List<LeaveSimpleDTO> workoutList) {
		this.workoutList = workoutList;
	}

	public Integer getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	public List<LeaveSimpleDTO> getLeaveList() {
		return leaveList;
	}

	public void setLeaveList(List<LeaveSimpleDTO> leaveList) {
		this.leaveList = leaveList;
	}

	public List<MyTaskSimpleDTO> getLightTaskList() {
		return lightTaskList;
	}

	public void setLightTaskList(List<MyTaskSimpleDTO> lightTaskList) {
		this.lightTaskList = lightTaskList;
	}

	public List<TaskSimpleDTO> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<TaskSimpleDTO> taskList) {
		this.taskList = taskList;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
}
