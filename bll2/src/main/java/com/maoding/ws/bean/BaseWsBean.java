package com.maoding.ws.bean;

import com.maoding.core.bean.BaseBean;


/**深圳市设计同道技术有限公司
 * 类    名：BaseWsBean
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2015年8月14日-下午1:28:30
 */
public class BaseWsBean extends BaseBean{
	private static final long serialVersionUID = 2502107470152406868L;
	private String userId;
	private String token;
	/**
	 * 获取：userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * 设置：token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
}
