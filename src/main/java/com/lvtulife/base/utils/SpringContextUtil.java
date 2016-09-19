package com.lvtulife.base.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by lvtulife on 2016-04-02 .
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext paramApplicationContext)
            throws BeansException {
        if (applicationContext == null)
            applicationContext = paramApplicationContext;
    }

    public static Object getBean(String beanid) {
        return applicationContext.getBean(beanid);

    }

    public static boolean containsBean(String beanid) {
        return applicationContext.containsBean(beanid);
    }
}
