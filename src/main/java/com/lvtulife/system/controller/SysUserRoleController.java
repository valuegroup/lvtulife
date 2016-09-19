package com.lvtulife.system.controller;

import javax.annotation.Resource;

import com.lvtulife.base.component.constants.SysConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvtulife.base.controller.BaseController;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysUserRole;
import com.lvtulife.system.service.SysUserRoleServiceI;

@Controller
@RequestMapping(value = SysConstants.URL_HEAD_SYSTEM + "/userRole")
public class SysUserRoleController extends BaseController<SysUserRole> {
    private static Logger logger = LoggerFactory.getLogger(SysUserRoleController.class);

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
    @Resource(name = "SysUserRoleService")
    private void setService(SysUserRoleServiceI service) {
        this.service = service;
    }

}
