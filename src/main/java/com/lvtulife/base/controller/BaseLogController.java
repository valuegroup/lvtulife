package com.lvtulife.base.controller;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.controller.BaseController;
import com.lvtulife.base.model.BaseLog;
import com.lvtulife.base.service.BaseLogServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 系统日志
 * BaseLogController component. @author lvtulife Tools
 */
@Controller
@RequestMapping(value = SysConstants.URL_HEAD_BASE + "/log")
public class BaseLogController extends BaseController<BaseLog> {

    private static Logger logger = LoggerFactory.getLogger(BaseLogController.class);

    /**
     * 注入业务逻辑
     *
     * @param service
     */
    @Resource(name = "BaseLogService")
    private void setService(BaseLogServiceI service) {
        this.service = service;
    }

    /**
     * 初始化基础参数
     */
    public BaseLogController() {
        LIST_PAGE_URL = "base/baseLog";
        FORM_PAGE_URL = "base/baseLogForm";
        COMBO_PARAMS = new String[]{"static_yesOrNo","static_method","static_logType"};
        CLASS = this.getClass();
    }
}
