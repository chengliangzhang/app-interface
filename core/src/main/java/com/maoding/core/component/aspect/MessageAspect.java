package com.maoding.core.component.aspect;

import com.maoding.core.annotation.messageAspect;
import com.maoding.core.base.dto.BaseDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Aspect
public class MessageAspect {

    /**类上注解情形 */
    @Pointcut("@annotation(com.maoding.core.annotation.messageAspect)")
    public void aspect(){

    }

    /**aop实际拦截两种情形*/
    @Around("aspect()")
    public Object doBefore(ProceedingJoinPoint point) throws Throwable{
        Object target = point.getTarget();
        Class<?> classz = target.getClass();
        Object[] args = point.getArgs();
        Signature sig = point.getSignature();
        Object result = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        if (classz!=null && args != null && args.length>0) {
            if(args[0] instanceof Map){
                Map<String,Object> param = (Map)args[0];
            }else if(args[0] instanceof BaseDTO){
                BaseDTO param = (BaseDTO)args[0];
            }
            result = point.proceed();

            //消息处理
            System.out.println("测试消息："+sig.getName());
        }
        return result;
    }

}
