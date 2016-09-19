package com.lvtulife.system.component.security;

import com.lvtulife.base.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * 有@RequestMapping()中的请求地址集合，在Spring启动完成后加载
 */
public class RequestUrlBean {
    private static Logger logger = LoggerFactory.getLogger(RequestUrlBean.class);


    // 所有@RequestMapping()中的请求地址集合，在Spring启动完成后加载
    private List<String> requestUrlAll = null;

    private static RequestUrlBean requestUrlBean = null;

    private RequestUrlBean() {
        requestUrlAll = new ArrayList<String>();
    }

    public static RequestUrlBean getInstance() {
        if (requestUrlBean == null) {
            requestUrlBean = new RequestUrlBean();
            try {
                RequestMappingHandlerMapping rmhp = SpringContextUtil.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
                Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
                List<String> urls = new ArrayList<String>();
                Iterator<?> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    RequestMappingInfo mappingInfo = (RequestMappingInfo) entry.getKey();
                    Set<String> set = mappingInfo.getPatternsCondition().getPatterns();
                    //System.out.println(set.toArray()[0]);
                    String url = set.toArray()[0].toString();
                    urls.add(url);
                }
                logger.info("监控所有权限资源{}", urls.size());
                requestUrlBean.getRequestUrlAll().addAll(urls);
            } catch (Exception e) {
                logger.error("监控所有权限资源异常", e);
            }
        }
        return requestUrlBean;
    }

    public List<String> getRequestUrlAll() {
        return requestUrlAll;
    }

    public void setRequestUrlAll(List<String> requestUrlAll) {
        this.requestUrlAll = requestUrlAll;
    }
}
