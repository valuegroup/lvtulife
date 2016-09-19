package com.lvtulife.system.controller.api;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.controller.StandardController;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = SysConstants.API_HEAD_SYSTEM + "/user/")
public class SysUserApiController extends StandardController {

    private static Logger logger = LoggerFactory.getLogger(SysUserApiController.class);

    /**
     * 注入用户组综合业务逻辑
     */
    @Resource(name = "SysUserGroupManager")
    private SysUserGroupManagerI sysUserGroupManageBean;


    @RequestMapping(value = "/getUser")
    @ResponseBody
    public Message<Object> save(HttpServletRequest request, HttpServletResponse response) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            SysUser user = sysUserGroupManageBean.getUser(1);
            msg.setCode(Code.C201, null, user);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

}
