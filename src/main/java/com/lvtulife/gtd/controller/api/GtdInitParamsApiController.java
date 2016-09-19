package com.lvtulife.gtd.controller.api;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.controller.StandardController;
import com.lvtulife.gtd.model.GtdInitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 业务初始参数表
 * GtdInitParamsApiController component. @author lvtulife Tools
 */
@Controller
@RequestMapping(value = SysConstants.API_HEAD_GTD + "/initParams/")
public class GtdInitParamsApiController extends StandardController {

    private static Logger logger = LoggerFactory.getLogger(GtdInitParamsApiController.class);

    @RequestMapping(value = "/temp")
    @ResponseBody
    public Message<Object> temp(HttpServletRequest request, HttpServletResponse response) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            msg.setCode(Code.C201, null, null);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

}
