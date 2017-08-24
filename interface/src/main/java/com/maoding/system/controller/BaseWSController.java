package com.maoding.system.controller;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：BaseWSController
 * 类描述：ws抽象基类
 * 作    者：Chenxj
 * 日    期：2015年8月7日-下午3:29:50
 */
public abstract class BaseWSController{
	protected final Logger log=LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//	private JedisUtils jedisUtils;
//	/**文件服务器地址*/
//	@Value("${apache.url}")
//	protected String apacheUrl;
	@Value("${fastdfs.url}")
	protected String fastdfsUrl;

	@Value("${fastdfs.fileCenterUrl}")
	protected String fileCenterUrl;

	@Value("${upload.url}")
	protected String uploadUrl;

	@Value("${server.url}")
	protected String serverUrl;


	@Autowired
	@Qualifier("redissonClient_session")
	private RedissonClient redissonClient;

	/**
	 * 方法描述：令牌存储
	 * 作        者：Chenxj
	 * 日        期：2015年8月10日-下午2:35:26
	 * @param token,platform(android ,ios)
	 * @return
	 */
	protected boolean saveToken(Object userId,String token,String IMEI,String platform){
		try{
			RMap<String, String> map2= redissonClient.getMap("userToken"+userId);
			map2.put("token",token);
			map2.put("IMEI",StringUtil.isNullOrEmpty(IMEI)?"":IMEI);
			map2.put("platform",platform);
			return true;
		}catch (Exception e){
			return false;
		}
	}

	public boolean checkToken(String accountId,String token,String platform,String IMEI){
		IMEI = StringUtil.isNullOrEmpty(IMEI)?"":IMEI;
		if(StringUtil.isNullOrEmpty(accountId) || StringUtil.isNullOrEmpty(token)|| StringUtil.isNullOrEmpty(platform)){
			return false;
		}
		Map<String,String > o = redissonClient.getMap("userToken"+accountId);
		if(o!=null){
			if(token.equals(o.get("token")) && platform.equals(o.get("platform")) && IMEI.equals(o.get("IMEI"))){
				return true;
			}
		}
		return false;
	}

	/**
	 * 方法描述：检测验证码
	 * 作        者：Chenxj
	 * 日        期：2015年8月10日-上午11:25:50
	 * @param sessionid
	 * @param code
	 * @return
	 */
	public boolean checkCode(String sessionid,String code){
		if(getAttributeFromCache("code"+sessionid)==null){
			return false;
		}
		String  tokenBean=getAttributeFromCache("code"+sessionid).toString();
        return tokenBean != null && tokenBean.equals(code);
	}


	protected boolean saveCode(String sessionid,String code) {
		try {
			RMap<String, String> map2 = redissonClient.getMap("code" + sessionid);
			map2.put("code" + sessionid, code);
			return true;
		}catch (Exception e){
			return false;
		}
	}
	/**
	 * 方法描述：保存到cache
	 * 作        者：Chenxj
	 * 日        期：2015年8月13日-下午2:09:31
	 * @param key
	 * @param value
	 * @return
	 */
	protected boolean saveAttributeToCache(String key,String value) {
		try {
			RMap<String, String> map2 = redissonClient.getMap(key);
			map2.put(key, value);
			return true;
		}catch (Exception e){
			return false;
		}
	}
	/**
	 * 方法描述：从cache中获取对象
	 * 作        者：Chenxj
	 * 日        期：2015年8月13日-下午2:14:10
	 * @param key
	 * @return
	 */
	protected String getAttributeFromCache(String key) {
		RMap<String, String> map2 = redissonClient.getMap(key);
		return map2.get(key);
	}

	/**
	 * 方法描述：是否包含某个角色
	 * 作        者：Chenxj
	 * 日        期：2015年12月18日-下午3:34:23
	 * @param roles
	 * @return
	 */
	protected boolean hasRoles(String...roles){
		for(String r:roles){
			if(hasRole(r)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 方法描述：是否包含某个角色
	 * 作        者：Chenxj
	 * 日        期：2015年12月18日-下午3:34:26
	 * @param roles
	 * @return
	 */
	protected boolean hasRoles(Collection<String>roles) {
		for(String r:roles){
			if(hasRole(r)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 方法描述：是否有角色
	 * 作        者：Chenxj
	 * 日        期：2015年12月18日-下午3:34:11
	 * @param role
	 * @return
	 */
	protected boolean hasRole(String role){
	//	return httpServletRequest.getSession().getAttribute("role").hasRole(role);
		return true;
	}

	/**
	 * 方法描述：返回成功
	 * 作        者：Chenxj
	 * 日        期：2015年8月7日-下午3:52:07
	 * @return
	 */
	protected ResponseBean responseSuccess() {
		return new ResponseBean().setError("0");
	}
	/**
	 * 方法描述：返回失败
	 * 作        者：Chenxj
	 * 日        期：2015年8月7日-下午3:52:34
	 * @param msg
	 * @return
	 */
	protected ResponseBean responseError(String msg) {
		return new ResponseBean().setError("1").setMsg(msg);
	}

	/**
	 * 方法描述：返回失败
	 * 作        者：Chenxj
	 * 日        期：2015年8月7日-下午3:52:34
	 * @param msg
	 * @return
	 */
	protected ResponseBean responseSuccess(String msg) {
		return new ResponseBean().setError("0").setMsg(msg);
	}



	/**
	 * 方法描述：返回请求成功操作状态成功
	 * 作        者：Chenxj
	 * 日        期：2016年4月23日-下午1:37:14
	 * @return
	 */
	protected ResponseBean returnResponseBean(AjaxMessage ajax) {
		if(null!=ajax) {
			if (ajax.getCode().equals("0")) {
				if (null != ajax.getData()) {
					return responseSuccess((String) ajax.getInfo()).addData("successData", ajax.getData());
				}
				return  responseSuccess(ajax.getInfo().toString());
			} else {
				return  responseError((String) ajax.getInfo());
			}
		}
		return  responseError("操作失败");
	}
	/**
	 * 方法描述：返回token验证错误
	 * 作        者：Chenxj
	 * 日        期：2015年8月11日-上午9:32:56
	 * @return
	 */
	public ResponseBean responseTokenError() {
		return new ResponseBean().setError(SystemParameters.SESSION_TIMEOUT_CODE).setMsg("token错误或已失效");
	}
	
	@ExceptionHandler
	public ResponseBean exceptionHandler(Exception e){
        if (e instanceof SecurityException) {
            return ajaxResponseLoginExceptionError();


        }
		log.error("移动端接口调用发生异常!!!", e);
		return this.responseError("操作失败");
	}

    protected ResponseBean ajaxResponseLoginExceptionError(){
        ResponseBean m=new ResponseBean();
        m.setError(SystemParameters.SESSION_TIMEOUT_CODE);
        m.setMsg("登录状态已超时，请重新登录！");
        return m;
    }
}
