package com.lvtulife.base.component.log.annotation;

import com.lvtulife.base.component.log.MethodEnum;

import java.lang.annotation.*;

/**
 * 自定义注解 记录 Controller 层系统日志
 * @Target(ElementType.TYPE)   //接口、类、枚举、注解
 * @Target(ElementType.FIELD) //字段、枚举的常量
 * @Target(ElementType.METHOD) //方法
 * @Target(ElementType.PARAMETER) //方法参数
 * @Target(ElementType.CONSTRUCTOR)  //构造函数
 * @Target(ElementType.LOCAL_VARIABLE)//局部变量
 * @Target(ElementType.ANNOTATION_TYPE)//注解
 * @Target(ElementType.PACKAGE) ///包
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogController {
    String description() default "";
    MethodEnum type() default MethodEnum.Find;
}
