package com.lvtulife.system.controller;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.controller.BaseController;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysOnline;
import com.lvtulife.system.service.SysOnlineServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 系统用户登录监控
 *
 * @author lvtulife
 */
@Controller
@RequestMapping(value = SysConstants.URL_HEAD_SYSTEM + "/online")
public class SysOnlineController extends BaseController<SysOnline> {
    private static Logger logger = LoggerFactory.getLogger(SysOnlineController.class);

    /**
     * 注入用户组综合业务逻辑
     */
    @Resource(name = "SysUserGroupManager")
    private SysUserGroupManagerI sysUserGroupManageBean;

    /**
     * 注入业务逻辑
     *
     * @param service
     */
    @Resource(name = "SysOnlineService")
    private void setService(SysOnlineServiceI service) {
        this.service = service;
    }

    /**
     * 初始化基础参数
     */
    public SysOnlineController() {
        LIST_PAGE_URL = "system/sysOnline";
        COMBO_PARAMS = new String[]{"static_olType"};
        CLASS = this.getClass();
    }
}
