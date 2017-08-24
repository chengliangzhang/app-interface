package com.maoding.core.component.aspect;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.JedisUtils;
import com.maoding.core.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Idccapp22 on 2017/6/27.
 */
@Component
@Aspect
public class SecurityAspect {
    @Autowired
    private JedisUtils jedisUtils;

    /**类上注解情形 */
    @Pointcut("@annotation(com.maoding.core.annotation.securityVerify)")
    public void aspect(){

    }

    /**aop实际拦截两种情形*/
    @Around("aspect()")
    public Object doBefore(ProceedingJoinPoint point) throws Throwable{
        Object target = point.getTarget();
        Class<?> classz = target.getClass();
        Object[] args = point.getArgs();
        Signature sig = point.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }

        boolean isLogin = false;
        if (classz!=null && args != null && args.length>0) {
            if(args[0] instanceof Map){
                Map<String,Object> param = (Map)args[0];
                isLogin =isEqual((String) param.get("accountId"),(String) param.get("token"),(String) param.get("platform"),(String) param.get("IMEI"));
            }else if(args[0] instanceof BaseDTO){
                BaseDTO param = (BaseDTO)args[0];
                isLogin = isEqual(param.getAccountId(),param.getToken(),param.getPlatform(),param.getIMEI());
            }
            if(!isLogin){
                //返回错误
                return ResponseBean.responseError("登录状态已超时").setError(SystemParameters.SESSION_TIMEOUT_CODE);
            }
            point.proceed();
        }
        return null;
    }


    private boolean isEqual(String accountId,String token,String platform,String IMEI){
        if(StringUtil.isNullOrEmpty(accountId) || StringUtil.isNullOrEmpty(token)|| StringUtil.isNullOrEmpty(platform) || StringUtil.isNullOrEmpty(IMEI)){
            return false;
        }
        Map<String,Object> o = (Map)jedisUtils.getObject("userToken"+accountId);
        if(o!=null){
            if(token.equals(o.get("token")) && platform.equals(o.get("platform")) && IMEI.equals(o.get("IMEI"))){
                return true;
            }
        }
        return false;
    }
}
