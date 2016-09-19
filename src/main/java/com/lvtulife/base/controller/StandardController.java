package com.lvtulife.base.controller;

import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.utils.CommonUtils;
import com.lvtulife.system.component.security.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 标准的Controller 含标准的底层方法
 */
@Controller
public class StandardController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回json对象方式，基于@ExceptionHandler异常处理
     */
    @ExceptionHandler
    @ResponseBody
    public Message<Object> exp(HttpServletRequest request, Exception ex) {
        logger.info("进入统一异常处理界面(Controller)！");
        logger.error("未处理异常: ", ex);//把漏网的异常信息记入日志
        Message<Object> msg = new Message<Object>(Code.C400);
        msg.setDetail("出现异常：【" + ex.getClass().getSimpleName() + "】  异常信息：【" + ex.getMessage().replaceAll("\"", "'") + "】");
        return msg;
    }


    /* 跳页面的方式
    @ExceptionHandler
    public String exp(HttpServletRequest request, Exception ex) {
        logger.info("进入统一异常处理界面(Controller)！");
        logger.error("Catch Exception: ",ex);//把漏网的异常信息记入日志
        request.setAttribute("ex", ex);
        // 根据不同错误转向不同页面
        if(ex instanceof CustomException) {
            return "error/error-business";
        }else if(ex instanceof ParameterException || ex instanceof MethodArgumentTypeMismatchException) {
            return "error/error-parameter";
        } else {
            return "error/error";
        }
    }*/


    public UserPrincipal getUserSession() {
        // 取出存在Security中的用户信息
        UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());

        return userDetails;
    }


    /**
     * 异常处理方法 非业务异常外的所有异常统一提示"程序或网络异常! 4000",消息使用Exception.getMessage() 当异常方法为外部调用时,应该使用外部的logger.
     *
     * @param msg
     * @param e
     */
    public void exception(Exception e, Message<Object> msg) {
        if (msg == null) {
            msg = new Message<Object>(Code.C400, e.getMessage(), null);
        }
        msg.setCode(Code.C400, e.getMessage(), null);
        logger.error("errorCode:{},error:{},errorDetail:{},errorData:{},exception:{}", msg.getCode(), msg.getInfo(), msg.getDetail(), msg.getData(), ExceptionUtils.getStackTrace(e));// ExceptionUtils.getFullStackTrace(e)
        e.printStackTrace();
    }

    /**
     * 业务异常处理方法 业务异常依据异常码自动获取相关描述,并获取详细描述 当异常方法为外部调用时,应该使用外部的logger.
     *
     * @param msg
     * @param e
     */
    public void exception(CustomException e, Message<Object> msg) {
        if (msg == null) {
            msg = new Message<Object>(Code.C400);
        }
        msg.setCode(e.getCode(), e.getDetailMsg(), null);
        logger.error("errorCode:{},error:{},errorDetail:{},errorData:{}", msg.getCode(), msg.getInfo(), msg.getDetail(), msg.getData());
    }


    /**
     * 将字符串的id,id,id转换成Integer集合
     *
     * @param ids
     * @return
     */
    public List<Integer> idsToIntegers(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new CustomException(Code.C400, "参数不能为空!");
        }

        List<Integer> list = new ArrayList<Integer>();
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            if (!CommonUtils.isNumeric(id)) {
                throw new CustomException(Code.C400, "参数[" + id + "]转换异常!");
            }
            list.add(Integer.parseInt(id));
        }
        return list;
    }
}
