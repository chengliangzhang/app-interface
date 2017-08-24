package com.maoding.system.bean;

import com.maoding.core.bean.BaseBean;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：TokenBean
 * 类描述：令牌或验证码
 * 作    者：Chenxj
 * 日    期：2015年8月10日-下午2:38:45
 */
public class TokenOrCodeBean extends BaseBean {
	private static final long serialVersionUID = -5092100263100124993L;
	/**令牌*/
	private String value;
	/**令牌创建时间*/
	private long createTime;
	public TokenOrCodeBean(){
		super();
	}
	public TokenOrCodeBean(String value,long createTime){
		super();
		this.value=value;
		this.createTime=createTime;
	}
	/**
	 * 获取：value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置：value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：createTime
	 */
	public long getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：createTime
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
