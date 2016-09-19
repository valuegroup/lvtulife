package com.lvtulife.system.component.onekeyBuild.annotation;

import java.lang.annotation.*;

/**
 * Description:标识该字段无对应的数据库，表列
 * @author shizy
 * @createDateTime 
 * @version  
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2013
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ignore {
}
