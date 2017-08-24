package com.maoding.system.aop;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.StringUtil;
import com.maoding.system.controller.BaseWSController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**深圳市设计同道技术有限公司
 * 类    名：AuthorityCheckableAOP
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2015年8月31日-上午9:59:19
 */
@Component
@Aspect
public class AuthorityCheckableAOP {
	private final Logger log=LoggerFactory.getLogger(getClass());
	
	//@Pointcut("execution(@com.maoding.system.annotation.AuthorityCheckable * com.maoding.*.controller.*.*(..))")
	@Pointcut("@annotation(com.maoding.system.annotation.AuthorityCheckable)")
	public void _check_ws_authority(){}

    @SuppressWarnings("unchecked")
	@Around("_check_ws_authority()")
	public Object check_ws_authority(ProceedingJoinPoint call) throws Throwable{
		BaseWSController bc=(BaseWSController) call.getTarget();
		boolean passable=false;
		Object arg0=call.getArgs()[0];
		if(arg0 instanceof Map){
			Map<String, Object>param=(Map<String, Object>) arg0;
			passable =bc.checkToken((String) param.get("accountId"),(String) param.get("token"),(String) param.get("platform"),(String) param.get("IMEI"));
		}else if(arg0 instanceof BaseDTO){
			BaseDTO param=(BaseDTO) arg0;
			passable =bc.checkToken(param.getAccountId(),param.getToken(),param.getPlatform(),param.getIMEI());
		}
		if(passable){
//			log.debug("拦截到[{}]类方法::[{}({})]执行",bc.getClass(),ms.getMethod().getName(),StringUtil.join(",", call.getArgs()));
			return call.proceed();
		}else{
			log.debug("拦截到[{}]类方法::[{}({})]执行",bc.getClass(), call.getSignature().getName(),StringUtil.join(",", call.getArgs()));
			log.debug("用户鉴权失败，令牌过期或无效::[{}]",StringUtil.join(",", call.getArgs()));
			return bc.responseTokenError();
		}
	}



}
