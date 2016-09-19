package com.lvtulife.base.controller;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.manager.BaseManagerI;
import com.lvtulife.base.model.BaseDemo;
import com.lvtulife.base.service.BaseDemoServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping(value = SysConstants.URL_HEAD_BASE + "/demo")
public class DemoController extends BaseController<BaseDemo> {
    private static Logger logger = LoggerFactory.getLogger(DemoController.class);

    public DemoController() {
        LIST_PAGE_URL = "forward:" + SysConstants.PATH_BASE + "/baseDemo.jsp";
        FORM_PAGE_URL = "forward:" + SysConstants.PATH_BASE + "/baseDemoForm.jsp";
        //"redirect:regirst";
        COMBO_PARAMS = new String[]{"static_yesOrNoType", "static_status"};
        CLASS = this.getClass();
    }

    /**
     * 注入基础综合业务逻辑
     */
    @Resource(name = "BaseManager")
    private BaseManagerI baseManageBean;

    /**
     * 注入业务逻辑
     *
     * @param service
     */
    @Resource(name = "BaseDemoService")
    public void setService(BaseDemoServiceI service) {
        this.service = service;
    }


    @RequestMapping(value = "/getString")
    @ResponseBody
    public String getString(HttpServletRequest request, HttpServletResponse response) {
        return "这是测试字单个符串转换情况";
    }

    @RequestMapping(value = "parameterDemo")
    public String parameterDemo(ModelMap model) {
        model.addAttribute("data", new BaseDemo());
        System.out.println("已进入页面。。。");
        return "forward:" + SysConstants.PATH_BASE + "/examples/getSpringMvcParameter.jsp";
    }

    @RequestMapping(value = "getSpringMvcParameter1", method = RequestMethod.POST)
    @ResponseBody
    public Message<Object> getSpringMvcParameter1(@RequestParam(required = false) String param1, @RequestParam(required = false) Integer param2,
                                                  @RequestParam(required = false) Byte param3, @RequestParam(required = false) Boolean param4, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date param5) {
        System.out.println("进入取参数方法...");
        System.out.println(param1);
        System.out.println(param2);
        System.out.println(param3);
        System.out.println(param4);
        System.out.println(param5);
        return new Message<Object>(Code.C201);
    }

    /**
     * 保存一个对象
     */
    @RequestMapping(value = SysConstants.URL_SAVE)
    @ResponseBody
    public Message<Object> save(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("data") BaseDemo data) {
        Message<Object> msg = new Message<Object>(Code.C501);

        // 初始化参数
        data.setCreatedDt(new Date());
        data.setUpdatedDt(new Date());
        data.setDel(new Byte("0"));

        try {
            service.save(data);
            msg.setCode(Code.C201, null, data);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }


}
