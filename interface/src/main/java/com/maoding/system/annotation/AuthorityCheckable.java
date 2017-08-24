package com.maoding.system.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**深圳市设计同道技术有限公司
 * 类    名：AuthorityCheckable
 * 类描述：WS接口权限检查
 * 作    者：Chenxj
 * 日    期：2015年8月31日-上午9:56:32
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityCheckable {
	
}
