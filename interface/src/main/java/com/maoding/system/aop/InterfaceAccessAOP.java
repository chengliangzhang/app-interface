package com.maoding.system.aop;/*package com.maoding.system.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maoding.core.util.StringUtil;

*//**深圳市设计同道技术有限公司
 * 类    名：InterfaceAccessAOP
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2016年1月22日-下午3:52:18
 *//*
@Aspect
public class InterfaceAccessAOP {
	private final Logger log=LoggerFactory.getLogger(getClass());
	
	@Pointcut("execution( * com.maoding..controller.*.*(..))")
	public void _inerfaceAccess(){}
	
	@Around("_inerfaceAccess()")
	public Object inerfaceAccess(ProceedingJoinPoint call) throws Throwable {
		MethodSignature ms=(MethodSignature) call.getSignature();
		Object targetObj=call.getTarget();
		long t=System.currentTimeMillis();
		Object rt=call.proceed();
		t=System.currentTimeMillis()-t;
		log.debug("拦截到<span class=\"class\">[{}]</span>类方法::<span class=\"method\">[{}(<span class=\"parameter\">{}</span>)]</span>执行耗时::{}ms<br>返回结果::{}",
				targetObj.getClass(),ms.getMethod().getName(),StringUtil.join(",", call.getArgs()),t,rt);
		return rt;
	}
}*/
