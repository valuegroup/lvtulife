package com.lvtulife.base.controller;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.model.BaseDictionary;
import com.lvtulife.base.service.BaseDictionaryServiceI;
import com.lvtulife.base.utils.BeanUtils;
import com.lvtulife.base.utils.CommonUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping(value = SysConstants.URL_HEAD_BASE + "/dictionary")
public class BaseDictionaryController extends BaseController<BaseDictionary> {
    private static Logger logger = LoggerFactory.getLogger(BaseDictionaryController.class);

    /**
     * 注入业务逻辑
     *
     * @param service
     */
    @Resource(name = "BaseDictionaryService")
    private void setService(BaseDictionaryServiceI service) {
        this.service = service;
    }

    /**
     * 初始化基础参数
     */
    public BaseDictionaryController() {
        LIST_PAGE_URL = "forward:" + SysConstants.PATH_BASE + "/baseDictionary.jsp";
        FORM_PAGE_URL = "forward:" + SysConstants.PATH_BASE + "/baseDictionaryForm.jsp";
        COMBO_PARAMS = new String[]{"static_dataSource", "static_status"};
        CLASS = this.getClass();
    }


    @RequestMapping(value = SysConstants.URL_SAVE)
    @ResponseBody
    public Message<Object> save(HttpServletRequest request, HttpServletResponse response, BaseDictionary data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            data.setCreatedDt(new Date());
            data.setUpdatedDt(new Date());
            data.setDel(SysConstants.SYS_STATUS);
            service.save(data);
            msg.setCode(Code.C201, null, data);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    @RequestMapping(value = SysConstants.URL_UPDATE)
    @ResponseBody
    public Message<Object> update(HttpServletRequest request, HttpServletResponse response, BaseDictionary data) {
        Message<Object> msg = new Message<Object>(Code.C501);

        if (data == null) {
            msg.setDetail("修改的对象不能为空!");
            return msg;
        }

        String reflectId = null;

        try {
            if (data != null) {
                reflectId = (String) FieldUtils.readField(data, "id", true);
            }

            if (!CommonUtils.isNumeric(reflectId)) {
                BaseDictionary t = service.getById(Integer.parseInt(reflectId));
                String[] excludes = new String[]{"value", "createdDt", "updatedDt", "del"};
                BeanUtils.copyNotNullProperties(data, t, excludes);
                t.setUpdatedDt(new Date());
                service.update(t);

                msg.setCode(Code.C201, null, t);
            }
        } catch (CustomException e) {
            exception(e, msg);
        } catch (IllegalAccessException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }
}
