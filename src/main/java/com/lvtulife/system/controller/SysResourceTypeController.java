package com.lvtulife.system.controller;

import com.lvtulife.base.controller.BaseController;
import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysResourceType;
import com.lvtulife.system.service.SysResourceTypeServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = SysConstants.URL_HEAD_SYSTEM + "/resourceType")
public class SysResourceTypeController extends BaseController<SysResourceType> {
    private static Logger logger = LoggerFactory.getLogger(SysResourceTypeController.class);

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
    @Resource(name = "SysResourceTypeService")
    private void setService(SysResourceTypeServiceI service) {
        this.service = service;
    }

    /**
     * 初始化基础参数
     */
    public SysResourceTypeController() {
        LIST_PAGE_URL = "system/sysResourceType";
        FORM_PAGE_URL = "system/sysResourceTypeForm";
        COMBO_PARAMS = new String[]{"static_yesOrNoType", "static_status"};
        CLASS = this.getClass();
    }


    /**
     * 取得最大的序号
     * 引用：
     * sysResourceTypeForm.jsp
     */
    @RequestMapping(value = "/getResTypeSeq", method = RequestMethod.GET)
    @ResponseBody
    public Message<Object> getResTypeSeq(HttpServletRequest request, HttpServletResponse response) {
        Message<Object> msg = new Message<Object>(Code.C400);
        try {
            int seq = ((SysResourceTypeServiceI) service).getResTypeSeq();
            msg.setCode(Code.C201, null, seq);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

}
