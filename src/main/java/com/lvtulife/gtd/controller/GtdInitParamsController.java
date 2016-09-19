package com.lvtulife.gtd.controller;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.controller.BaseController;
import com.lvtulife.gtd.model.GtdInitParams;
import com.lvtulife.gtd.service.GtdInitParamsServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 业务初始参数表
 * GtdInitParamsController component. @author lvtulife Tools
 */
@Controller
@RequestMapping(value = SysConstants.URL_HEAD_GTD + "/initParams/")
public class GtdInitParamsController extends BaseController<GtdInitParams> {

    private static Logger logger = LoggerFactory.getLogger(GtdInitParamsController.class);

    /**
     * 注入业务逻辑
     *
     * @param service
     */
    @Resource(name = "GtdInitParamsService")
    private void setService(GtdInitParamsServiceI service) {
        this.service = service;
    }

    /**
     * 初始化基础参数
     */
    public GtdInitParamsController() {
        LIST_PAGE_URL = "gtd/gtdInitParams";
        FORM_PAGE_URL = "gtd/gtdInitParamsForm";
        COMBO_PARAMS = new String[]{"static_yesOrNo","static_dataSource"};
        CLASS = this.getClass();
    }
}
