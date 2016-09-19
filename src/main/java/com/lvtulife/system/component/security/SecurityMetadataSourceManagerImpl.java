package com.lvtulife.system.component.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.utils.StringUtil;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysResource;
import com.lvtulife.system.model.SysRole;
import com.lvtulife.system.model.SysRoleRes;
import com.lvtulife.system.service.SysResourceServiceI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

/**
 * [核心处理逻辑]
 * 提供给Spring Security
 * 资源源数据定义，即定义某一资源可以被哪些角色访问
 * 建立资源与权限的对应关系
 * <p>
 * 也可以直接使用Spring提供的类 DefaultFilterInvocationSecurityMetadataSource
 *
 * @author Lvtulife
 */
@Component(value = "SecurityMetadataSourceManager")
public class SecurityMetadataSourceManagerImpl implements FilterInvocationSecurityMetadataSource {
    private static Logger logger = LoggerFactory.getLogger(SecurityMetadataSourceManagerImpl.class);

    @Resource(name = "SysUserGroupManager")
    private SysUserGroupManagerI sysUserGroupManagerBean;

    @Resource(name = "SysResourceService")
    private SysResourceServiceI sysResourceBean;


    private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

    /**
     * 初始化资源配置
     * <p>
     * spring 调用该方法的方式有2种
     * 方式1，方法上加注解：
     *
     * @PostConstruct 方式2，配置文件中 init-method 属性指定：
     * <beans:bean id="webSecurityMetadataSource" init-method="initResource" class="com.tavenli.security.WebSecurityMetadataSource"/>
     */
    @PostConstruct
    public void initResource() {
        resourceMap.clear();

        //取得当前系统所有可用角色
        List<SysRole> roles = this.sysUserGroupManagerBean.findAvailableRoles();
        for (SysRole role : roles) {
            // 跳过超级管理员角色
            if (SysConstants.SYS_INSYS == role.getSource().byteValue() && ("ROLE_" + role.getRoleNameEn()).equals(SysConstants.ROLE_ROOT)) {
                continue;
            }

            // 读取各角色权限
            this.loadRole(role);
        }

        //为超级管理员添加所有资源权限
        this.initSuperUserResource();

        logger.info("角色资源权限加载完毕");
    }

    /**
     * 普通角色权限读取
     *
     * @param role
     */
    private void loadRole(SysRole role) {
        //这里的 role 参数为自己定义的，要和 UserDetailsService 中的 SimpleGrantedAuthority 参数对应
        //role 参数也可以直接使用角色名
        ConfigAttribute ca = new SecurityConfig("ROLE_" + role.getRoleNameEn());

        //取角色有哪些固定资源的权限(资源表中固定的)
        List<SysResource> list = this.sysUserGroupManagerBean.findRoleResourceFixedToRes(role.getId());
        setResourceMap(ca, list);

        //取角色有哪些动态资源的权限(资源表不存在，系统虚拟的)
        setAuthPoint(list, this.sysUserGroupManagerBean.findRoleResourceDynamic(role.getId()));
        setResourceMap(ca, list);
    }

    /**
     * 添加系统基础权限
     *
     * @param list
     * @param dynamic
     */
    private void setAuthPoint(List<SysResource> list, List<SysRoleRes> dynamic) {
        if (dynamic == null || dynamic.size() == 0) {
            return;
        }

        // 转成Map方便取数据
        HashMap<Integer, SysResource> resMap = ListToMap(list);

        // 清空旧数据
        list.clear();

        // 添加新数据
        for (SysRoleRes sysRoleRes : dynamic) {
            SysResource parentRes = null;

            // 查阅数据权限
            parentRes = resMap.get(sysRoleRes.getResId() - SysConstants.AUTH_POINT_FIND_VALUE);
            if (parentRes != null) {
                setFindAuthPoint(list, parentRes);
            }

            // 保存数据权限
            parentRes = resMap.get(sysRoleRes.getResId() - SysConstants.AUTH_POINT_SAVE_VALUE);
            if (parentRes != null) {
                list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_SAVE_SAVE));
            }

            // 编辑数据权限
            parentRes = resMap.get(sysRoleRes.getResId() - SysConstants.AUTH_POINT_EDIT_VALUE);
            if (parentRes != null) {
                list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_EDIT_UPDATE));
            }

            // 删除数据权限
            parentRes = resMap.get(sysRoleRes.getResId() - SysConstants.AUTH_POINT_REMOVE_VALUE);
            if (parentRes != null) {
                list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_REMOVE_DELETE));
                list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_REMOVE_DELETES));
            }
        }
    }

    private void setFindAuthPoint(List<SysResource> list, SysResource parentRes) {
        list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_FIND_FORM));
        list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_FIND_GETBYID));
        list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_FIND_LIST));
        list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_FIND_GRID));
        list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_FIND_GRIDALL));
        list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_FIND_TREEGRID));
        list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_FIND_FIND));
        list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_FIND_FINDALL));
        list.add(new SysResource(parentRes.getUrlHead(), parentRes.getUrlModule(), SysConstants.AUTH_POINT_FIND_GETCOMBOBOX));
    }

    /**
     * 添加超级管理员角色
     */
    private void initSuperUserResource() {
        ConfigAttribute ca = new SecurityConfig(SysConstants.ROLE_ROOT);
        // 超级管理员有所有菜单权限
        List<SysResource> list = this.sysUserGroupManagerBean.findAllResource();
        setResourceMap(ca, list);
    }


    /**
     * 设置资源Map,资源1角色N
     *
     * @param ca
     * @param list
     */
    private void setResourceMap(ConfigAttribute ca, List<SysResource> list) {
        int i = 0;
        for (SysResource r : list) {
            putMap(ca, StringUtil.splitJointUrl(r.getUrlHead(), r.getUrlModule(), r.getUrlMethod())); // 请求地址 xx
            //putMap(ca, r.getUrlPath()); // 资源地址 xx.jsp
        }
    }

    private void putMap(ConfigAttribute ca, String url) {
        //不是菜单地址，跳过(http:,/,null,'')
        if (isaMenu(url)) {
            return;
        }

        //如果是URL资源，则建立角色与资源关系
        if (resourceMap.containsKey(url)) {
            if(!resourceMap.get(url).contains(ca)) {
                resourceMap.get(url).add(ca);
            }
        } else {
            Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
            atts.add(ca);
            resourceMap.put(url, atts);
        }
    }

    /**
     * //不是菜单地址，跳过(http:,/,null,'')
     *
     * @param url
     * @return
     */
    private boolean isaMenu(String url) {
        return StringUtils.isBlank(url) || "/".equals(url.trim()) || url.indexOf(":") >= 0;
    }

    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        String url = ((FilterInvocation) object).getRequestUrl();
        logger.debug("requestUrl is :{}", url);

        /*int index = url.indexOf("?");
        if (index != -1) {
            url = url.substring(0, index);
        }*/

        Iterator<String> ite = resourceMap.keySet().iterator();


        while (ite.hasNext()) {
            String resourceURL = ite.next();
            //AntPathRequestMatcher : 来自于Ant项目，是一种简单易懂的路径匹配策略。
            //RegexRequestMatcher : 如果 AntPathRequestMatcher 无法满足需求，
            //还可以选择使用更强大的RegexRequestMatcher，它支持使用正则表达式对URL地址进行匹配
            RequestMatcher requestMatcher = new AntPathRequestMatcher(resourceURL);
            if (requestMatcher.matches(request)) {
            /*if (urlMatcher.pathMatchesUrl(url, resourceURL)) {*/
                return resourceMap.get(resourceURL);
            }
        }
        return null;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    /**
     * 重新加载资源权限列表，适用于角色授权，用户角色授予等
     */
    public void reloadResource() {
        this.initResource();
        this.addDefaultResourceMap();
    }


    /**
     * 将系统中所有的请求链接，加载至Security统一管理
     */
    public void addDefaultResourceMap() {
        ConfigAttribute superCA = new SecurityConfig(SysConstants.ROLE_ROOT);
        List<String> requestUrlAll = RequestUrlBean.getInstance().getRequestUrlAll();
        for (String requestUrl : requestUrlAll) {
            putMap(superCA, requestUrl); // 请求地址 xx
        }
    }

    private HashMap<Integer, SysResource> ListToMap(List<SysResource> list) {
        Iterator<SysResource> itr = list.iterator();
        HashMap<Integer, SysResource> map = new HashMap<Integer, SysResource>();
        while (itr.hasNext()) {
            SysResource r = itr.next();
            map.put(r.getId(), r);
        }
        return map;
    }

}
