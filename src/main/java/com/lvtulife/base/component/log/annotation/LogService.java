package com.lvtulife.base.component.log.annotation;

import com.lvtulife.base.component.log.MethodEnum;

import java.lang.annotation.*;

/**
 * 自定义注解 记录Service层系统日志
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogService {
    String description() default "";
    MethodEnum type() default MethodEnum.Find;
}
