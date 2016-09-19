package com.lvtulife.base.component.log;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtulife.base.component.log.annotation.LogController;
import com.lvtulife.base.component.log.annotation.LogService;
import com.lvtulife.base.model.BaseLog;
import com.lvtulife.base.service.BaseLogServiceI;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.base.utils.IpUtil;
import com.lvtulife.base.utils.JsonUtils;
import com.lvtulife.system.component.security.UserPrincipal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * 系统日志切面类
 *
 * @Aspect放在类头上，把这个类作为一个切面
 */
//声明这是一个组件
@Component
//声明这是一个切面Bean
@Aspect
@Order(1)
public class SysLogAspect {
    //本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    /**
     * JoinPoint接口
     *
     * @param joinPoint
     */
    /*public interface JoinPoint {
        String toString();         //连接点所在位置的相关信息
        String toShortString();     //连接点所在位置的简短相关信息
        String toLongString();     //连接点所在位置的全部相关信息
        Object getThis();         //返回AOP代理对象
        Object getTarget();       //返回目标对象
        Object[] getArgs();       //返回被通知方法参数列表
        Signature getSignature();  //返回当前连接点签名
        SourceLocation getSourceLocation();//返回连接点方法所在类文件中的位置
        String getKind();        //连接点类型
        StaticPart getStaticPart(); //返回连接点静态部分
    }*/

    //注入Service用于把日志保存数据库
    @Resource(name = "BaseLogService")
    private BaseLogServiceI baseLogService;

    //@Pointcut放在方法头上，定义一个可被别的方法引用的切入点表达式
    //Service层切点
    @Pointcut("@annotation(com.lvtulife.base.component.log.annotation.LogService)")
    public void serviceAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(com.lvtulife.base.component.log.annotation.LogController)")
    public void controllerAspect() {
    }

    /*@Before，前置通知，放在方法头上
    * @After，后置【finally】通知，放在方法头上
    * @AfterReturning，后置【try】通知，放在方法头上，使用returning来引用方法返回值
    * @AfterThrowing，后置【catch】通知，放在方法头上，使用throwing来引用抛出的异常
    * @Around，环绕通知，放在方法头上，这个方法要决定真实的方法是否执行，而且必须有返回值*/


    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before(value = "controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        saveLog(joinPoint, false);
    }


    @Before(value = "serviceAspect()")
    public void serviceDoBefore(JoinPoint joinPoint) {
        saveLog(joinPoint, true);
    }


    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     * @AfterThrowing，后置【catch】通知，放在方法头上，使用throwing来引用抛出的异常 事务 与 Afterthrowing 冲突的解决办法
     * "order"参数，这个参数是用来控制aop通知的优先级，值越小，优先级越高，控制log拦截器在事务的around之外
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
        //获取请求ip
        String ip = IpUtil.getIpAddr(request);
        String params = getParams(joinPoint);
        try {
            BaseLog log = new BaseLog();
            log.setExCode(e.getClass().getName());
            log.setLogType(new Byte("1"));
            log.setExDetail(e.getMessage());
            log.setClazz(joinPoint.getTarget().getClass().getName());
            log.setMethod(joinPoint.getSignature().getName());
            log.setParams(params);
            log.setCreatedId(userDetails.getUserId());
            log.setCreatedDt(new Date());
            log.setRequestIp(ip);
            setServiceMthodDescription(joinPoint, log);
            baseLogService.save(log);
        } catch (Exception ex) {
            //记录本地异常日志
            logger.error("异常信息:{}", ex.getMessage());
        }
         /*==========记录本地异常日志==========*/
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);
    }


    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    private void setServiceMthodDescription(JoinPoint joinPoint, BaseLog log)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    LogService ls = method.getAnnotation(LogService.class);
                    log.setDescription(ls.description());
                    log.setMethodType(ls.type().getValue());
                    break;
                }
            }
        }
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    private void setControllerMethodDescription(JoinPoint joinPoint, BaseLog log) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        //Class pojoClass = getPojo(targetClass);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    LogController ls = method.getAnnotation(LogController.class);
                    log.setDescription(ls.description());
                    log.setMethodType(ls.type().getValue());
                    break;
                }
            }
        }
    }

    // 获取泛型类
    private Class getPojo(Class targetClass) {
        try {
            Type genType = targetClass.getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            return (Class) params[0];
        } catch (Exception e) {

        }
        return Object.class;
    }


    private void saveLog(JoinPoint joinPoint, boolean isService) {

        //读取session中的用户
        UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //请求的IP
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = IpUtil.getIpAddr(request);
        try {
            BaseLog log = new BaseLog();
            if (isService) {
                setServiceMthodDescription(joinPoint, log);
            } else {
                setControllerMethodDescription(joinPoint, log);
            }
            log.setClazz(joinPoint.getTarget().getClass().getName());
            log.setMethod(joinPoint.getSignature().getName());
            log.setLogType(new Byte("0"));
            log.setRequestIp(ip);
            log.setExCode(null);
            log.setExDetail(null);
            log.setParams(getParams(joinPoint));
            log.setCreatedId(userDetails.getUserId());
            log.setCreatedDt(new Date());
            //保存数据库(保存数据库的方法不能记录日志，否则程序会出现死循环)
            baseLogService.save(log);
        } catch (Exception e) {
            //记录本地异常日志
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    private String getParams(JoinPoint joinPoint) {
        StringBuffer params = new StringBuffer();
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                try {
                    if (joinPoint.getArgs()[i] instanceof HttpServletRequest
                            || joinPoint.getArgs()[i] instanceof HttpServletResponse
                            || joinPoint.getArgs()[i] instanceof ModelAndView
                            || joinPoint.getArgs()[i] instanceof ModelMap
                            || joinPoint.getArgs()[i] instanceof HqlFilter) {
                        continue;
                    }
                    params.append(JsonUtils.toJson(joinPoint.getArgs()[i])).append(";");
                } catch (Exception e) {
                    logger.error("不可以转成JSON的对象", e);
                }
            }
        }
        return params.toString();
    }
}
