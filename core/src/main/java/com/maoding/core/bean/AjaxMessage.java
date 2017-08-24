package com.maoding.core.bean;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：AjaxMessage
 * 类描述：ajax请求返回信息
 * 作    者：MaoSF
 * 日    期：2016年7月8日-上午10:48:22
 */
public class AjaxMessage{
	/**信息code*/
	private String code;
	/**信息*/
	private Object info;
	
	private Object data;
	public String getCode() {
		return code;
	}
	public AjaxMessage setCode(String code) {
		this.code = code;
		return this;
	}
	public Object getInfo() {
		return info;
	}
	public AjaxMessage setInfo(Object info) {
		this.info = info;
		return this;
	}
	public Object getData() {
		return data;
	}
	public AjaxMessage setData(Object data) {
		this.data = data;
		return this;
	}


	public static AjaxMessage failed(Object data){
		AjaxMessage m=new AjaxMessage();
		m.setCode("1");
		m.setInfo(data);
		if(data!=null)
			m.setData(data);
		return m;
	}

	public static AjaxMessage succeed(Object data){
		AjaxMessage m=new AjaxMessage();
		m.setCode("0");
		if(data!=null)
			m.setData(data);
		return m;
	}

}
