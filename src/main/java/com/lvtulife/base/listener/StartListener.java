package com.lvtulife.base.listener;

import com.lvtulife.base.component.dictionary.DictionaryBean;
import com.lvtulife.base.component.dictionary.DictionaryUtil;
import com.lvtulife.base.component.message.BaseMessage;
import com.lvtulife.base.component.message.BaseMessageUtils;
import com.lvtulife.base.utils.SpringContextUtil;
import com.lvtulife.system.component.security.RequestUrlBean;
import com.lvtulife.system.component.security.SecurityMetadataSourceManagerImpl;
import com.lvtulife.system.controller.SysPageController;
import com.octo.captcha.text.TextCaptchaFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.Iterator;
import java.util.Map;

/**
 * Lvtulife
 */
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {//ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用
    private static Logger logger = LoggerFactory.getLogger(StartListener.class);

    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("================================================Spring容易初始化完毕================================================");

        // 将系统中未在资源表中记录的请求加载到springSecurity中
        SecurityMetadataSourceManagerImpl s = (SecurityMetadataSourceManagerImpl) event.getApplicationContext().getBean("SecurityMetadataSourceManager");
        s.addDefaultResourceMap();

        DictionaryUtil dictionaryUtil = DictionaryUtil.getInstence();
        BaseMessageUtils messageUtils = BaseMessageUtils.getInstence();
    }

}
