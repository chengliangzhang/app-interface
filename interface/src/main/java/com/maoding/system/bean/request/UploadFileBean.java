package com.maoding.system.bean.request;

import org.springframework.web.multipart.MultipartFile;

import com.maoding.ws.bean.BaseWsBean;

/**深圳市设计同道技术有限公司
 * 类    名：UploadBasisFileBean
 * 类描述：附件上传bean
 * 作    者：Chenxj
 * 日    期：2016年4月26日-上午10:46:01
 */
public class UploadFileBean extends BaseWsBean{
	private static final long serialVersionUID = -2093721884138713756L;
	private MultipartFile file;
	/**
	 * 获取：file
	 */
	public MultipartFile getFile() {
		return file;
	}
	/**
	 * 设置：file
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
