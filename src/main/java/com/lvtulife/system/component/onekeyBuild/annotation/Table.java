package com.lvtulife.system.component.onekeyBuild.annotation;

import java.lang.annotation.*;

/**
 * Description:标识为实体类的ID
 * @author shizy
 * @createDateTime 
 * @version  
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2013
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
	public String value();
}
