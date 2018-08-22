package com.maoding.system.aop;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.StringUtil;
import com.maoding.exception.CustomException;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.system.controller.BaseWSController;
import com.maoding.version.dao.InterfaceVersionDao;
import com.maoding.version.entity.InterfaceVersionEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
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

	@Autowired
	private InterfaceVersionDao interfaceVersionDao;

	@Autowired
	private CompanyUserDao companyUserDao;

	//@Pointcut("execution(@com.maoding.system.annotation.AuthorityCheckable * com.maoding.*.controller.*.*(..))")
	@Pointcut("@annotation(com.maoding.system.annotation.AuthorityCheckable)")
	public void _check_ws_authority(){}

    @SuppressWarnings("unchecked")
	@Around("_check_ws_authority()")
	public Object check_ws_authority(ProceedingJoinPoint call) throws Throwable{
		BaseWSController bc=(BaseWSController) call.getTarget();
		boolean passable=false;
		Object arg0=call.getArgs()[0];
		Integer interfaceVersion = 0;
		String accountId = null;
		String appOrgId = null;
		if(arg0 instanceof Map){
			Map<String, Object>param=(Map<String, Object>) arg0;
			String version = (String)param.get("interfaceVersion");
			interfaceVersion = Integer.parseInt(version==null?"0":version);
			accountId = (String) param.get("accountId");
			appOrgId = (String) param.get("appOrgId");
			((Map) arg0).put("currentCompanyUserId",this.getCompanyUserId(accountId,appOrgId));
			passable =bc.checkToken((String) param.get("accountId"),(String) param.get("token"),(String) param.get("platform"),(String) param.get("IMEI"));
		}else if(arg0 instanceof BaseDTO){
			accountId = ((BaseDTO) arg0).getAccountId();
			appOrgId = ((BaseDTO) arg0).getAppOrgId();
			BaseDTO param=(BaseDTO) arg0;
			((BaseDTO) arg0).setCurrentCompanyUserId(this.getCompanyUserId(accountId,appOrgId));
			interfaceVersion = param.getInterfaceVersion();
			passable =bc.checkToken(param.getAccountId(),param.getToken(),param.getPlatform(),param.getIMEI());
		}


		//判断版本号是否匹配当前接口的版本号，如果app前端传递的
		if(!isSuitableVersion(call,interfaceVersion)){
			return bc.responseInterfaceVersionError();
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

	private String getCompanyUserId(String accountId,String companyId) {
		if(!StringUtil.isNullOrEmpty(companyId)){
			CompanyUserEntity u = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
			if(u==null){
				throw new CustomException("权限拦截中----》参数错误");
			}
			return u.getId();
		}
		return null;
	}

	/**
	 * 判断当前版本号是否是合适的版本号，app端的interfaceVersion>=后台的版本号，则可行，否则不可使用
	 */
	private boolean isSuitableVersion(ProceedingJoinPoint call,Integer interfaceVersion){
		if(interfaceVersion==null){
			return false;
		}
		Method targetMethod = ((MethodSignature)(call.getSignature())).getMethod();
		Class<?> classtest = targetMethod.getDeclaringClass();
		String classAnnotationValue = "";
		String methodAnnotationValue = "";
		Annotation[]  classAnnotation = classtest.getAnnotations();
		for (int i = 0; i < classAnnotation.length; i++) {
			if(classAnnotation[i] instanceof RequestMapping){
				RequestMapping requestmap = (RequestMapping) classAnnotation[i];
				if(requestmap.value()[0] != null){
					classAnnotationValue = (requestmap.value())[0];
				}
				break;
			}
		}

		if(!StringUtil.isNullOrEmpty(classAnnotationValue)) {
			Annotation[] methodAnnotation = targetMethod.getAnnotations();
			for (int i = 0; i < methodAnnotation.length; i++) {
				if (methodAnnotation[i] instanceof RequestMapping) {
					RequestMapping requestmap = (RequestMapping) methodAnnotation[i];
					if (requestmap.value()[0] != null) {
						methodAnnotationValue = (requestmap.value())[0];
					}
					break;
				}
			}
			if(StringUtil.isNullOrEmpty(classAnnotationValue) || StringUtil.isNullOrEmpty(methodAnnotationValue)){
				return false;
			}

			InterfaceVersionEntity entity = new InterfaceVersionEntity();
			entity.setClassName(classtest.getName());
			entity.setInterfaceName(classAnnotationValue + methodAnnotationValue);
			if(!classAnnotationValue.substring(classAnnotationValue.length()-1).equals("/") && !methodAnnotationValue.substring(0,1).equals("/")){
				entity.setInterfaceName(classAnnotationValue +"/"+ methodAnnotationValue);
			}
			List<InterfaceVersionEntity> list = this.interfaceVersionDao.selectInterfaceByParam(entity);
			if (!CollectionUtils.isEmpty(list) && list.size() == 1) {
				InterfaceVersionEntity version = list.get(0);
				if (interfaceVersion >= version.getVersion()) {
					//并且更新一下当前接口的使用次数及最后一次使用时间，一遍后面统计及维护
					version.setUseCount(version.getUseCount() + 1);
					version.setLastUseDate(new Date());
					interfaceVersionDao.update(version);
					return true;
				}
			}
		}
		return false;
	}

}
