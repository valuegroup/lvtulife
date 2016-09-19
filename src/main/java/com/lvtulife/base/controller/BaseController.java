package com.lvtulife.base.controller;

import com.lvtulife.base.component.LabelValue;
import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.constants.SysConstantsList;
import com.lvtulife.base.component.easyui.Grid;
import com.lvtulife.base.component.log.MethodEnum;
import com.lvtulife.base.component.log.annotation.LogController;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.service.BaseServiceI;
import com.lvtulife.base.utils.BeanUtils;
import com.lvtulife.base.utils.HqlFilter;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 含基础的增删改查方法
 *
 * @param <T>
 */
@Controller
public class BaseController<T> extends StandardController {
    public String LIST_PAGE_URL = "forward:" + SysConstants.PATH_ERROR + "/noPageUrlParams.jsp";
    public String FORM_PAGE_URL = "forward:" + SysConstants.PATH_ERROR + "/noPageUrlParams.jsp";
    public String[] COMBO_PARAMS = new String[]{"static_yesOrNo", "static_status"};

    public Class CLASS = this.getClass();
    private Logger logger = LoggerFactory.getLogger(CLASS);

    // protected T data;// 数据模型(与前台表单name相同，name="data.xxx")

    protected BaseServiceI<T> service;// 业务逻辑

    /**
     * 继承BaseAction的action需要先设置这个方法，使其获得当前action的业务服务 使当前action调用service.xxx的时候，直接是调用基础业务逻辑 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
     */
    public void setService(BaseServiceI<T> service) {
        this.service = service;
    }


    /*@RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "forward:index.jsp";
    }*/

    /**
     * 打开list界面,需要在子类的构造方法中或者方法中定义LIST_PAGE_URL的参数值
     *
     * @return
     */
    @RequestMapping(value = SysConstants.URL_LIST, method = RequestMethod.GET)
    public String list() {
        return LIST_PAGE_URL;
    }

    /**
     * 打开form界面,需要在子类的构造方法中或者方法中定义FORM_PAGE_URL的参数值
     *
     * @param model
     * @return
     */
    @RequestMapping(value = SysConstants.URL_FORM, method = RequestMethod.GET)
    public String form(ModelMap model, Integer id) {
        model.addAttribute("id", id);
        return FORM_PAGE_URL;
    }

    /**
     * 获取页面所需要的下拉选项集合,需要在子类的构造方法中或者方法中定义参数数组
     */
    @RequestMapping(value = SysConstants.URL_GETCOMBOBOX, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<LabelValue>> getCombobox() {
        Map<String, List<LabelValue>> map = getComboList(COMBO_PARAMS);
        return map;
    }

    /**
     * 获得一个对象
     */
    @LogController(description = "获得一个对象", type = MethodEnum.Find)
    @RequestMapping(value = SysConstants.URL_GETBYID)
    @ResponseBody
    public Message<Object> getById(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            T data = service.getById(id);
            msg.setCode(Code.C201, null, data);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 查找一批对象
     */
    @LogController(description = "查找一批对象", type = MethodEnum.Find)
    @RequestMapping(value = SysConstants.URL_FIND)
    @ResponseBody
    public Message<Object> find(HttpServletRequest request, HttpServletResponse response, @RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        Message<Object> msg = new Message<Object>(Code.C501);
        HqlFilter hqlFilter = new HqlFilter(request);
        try {
            List<T> datas = service.findByFilter(hqlFilter, page, rows);
            msg.setCode(Code.C201, null, datas);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 查找所有对象
     */
    @LogController(description = "查找所有对象", type = MethodEnum.Find)
    @RequestMapping(value = SysConstants.URL_FINDALL)
    @ResponseBody
    public Message<Object> findAll(HttpServletRequest request, HttpServletResponse response) {
        Message<Object> msg = new Message<Object>(Code.C501);
        HqlFilter hqlFilter = new HqlFilter(request);

        try {
            List<T> datas = service.findByFilter(hqlFilter);
            msg.setCode(Code.C201, null, datas);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 查找分页后的grid
     */
    @LogController(description = "查找分页后的grid", type = MethodEnum.Find)
    @RequestMapping(value = SysConstants.URL_GRID)
    @ResponseBody
    public Grid grid(HttpServletRequest request, HttpServletResponse response, @RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        Grid datas = new Grid();
        HqlFilter hqlFilter = new HqlFilter(request);
        try {
            datas.setTotal(service.countByFilter(hqlFilter));
            datas.setRows(service.findByFilter(hqlFilter, page, rows));
        } catch (CustomException e) {
            exception(e, null);
        } catch (Exception e) {
            exception(e, null);
        }
        return datas;
    }

    /**
     * 查找grid所有数据，不分页
     */
    @LogController(description = "查找grid所有数据，不分页", type = MethodEnum.Find)
    @RequestMapping(value = SysConstants.URL_GRIDALL)
    @ResponseBody
    public Grid gridAll(HttpServletRequest request, HttpServletResponse response) {
        HqlFilter hqlFilter = new HqlFilter(request);

        Grid datas = new Grid();
        try {
            List<T> list = service.findByFilter(hqlFilter);
            datas.setTotal((long) list.size());
            datas.setRows(list);
        } catch (CustomException e) {
            exception(e, null);
        } catch (Exception e) {
            exception(e, null);
        }
        return datas;
    }

    /**
     * 获得treeGrid，treeGrid由于提供了pid的扩展，所以不分页
     */
    @LogController(description = "获得treeGrid，treeGrid由于提供了pid的扩展，所以不分页", type = MethodEnum.Find)
    @RequestMapping(value = SysConstants.URL_TREEGRID)
    @ResponseBody
    public List<T> treeGrid(HttpServletRequest request, HttpServletResponse response) {
        HqlFilter hqlFilter = new HqlFilter(request);
        List<T> datas = new ArrayList<T>();
        try {
            return service.findByFilter(hqlFilter);
        } catch (CustomException e) {
            exception(e, null);
        } catch (Exception e) {
            exception(e, null);
        }
        return datas;
    }

    private Class<T> entityClass;

    /**
     * 保存一个对象
     */
    @LogController(description = "保存一个对象", type = MethodEnum.Create)
    @RequestMapping(value = SysConstants.URL_SAVE)
    @ResponseBody
    public Message<Object> save(HttpServletRequest request, HttpServletResponse response, T data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (data == null) {
            return msg.setDetail("保存的对象不能为空!");
        }
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

    /**
     * 更新一个对象
     */
    @LogController(description = "更新一个对象", type = MethodEnum.Update)
    @RequestMapping(value = SysConstants.URL_UPDATE)
    @ResponseBody
    public Message<Object> update(HttpServletRequest request, HttpServletResponse response, T data) {
        Message<Object> msg = new Message<Object>(Code.C501);

        if (data == null) {
            return msg.setDetail("修改的对象不能为空!");
        }

        Integer reflectId = null;
        try {
            if (data != null) {
                reflectId = (Integer) FieldUtils.readField(data, "id", true);
            }
            if (reflectId == null) {
                return msg.setDetail("修改的对象主键不能为空!");
            }

            T t = service.getById(reflectId);
            BeanUtils.copyNotNullProperties(data, t, new String[]{"createdDt"});
            service.update(t);

            msg.setCode(Code.C201, null, t);

        } catch (CustomException e) {
            exception(e, msg);
        } catch (IllegalAccessException e) {
            exception(e, msg);
        } catch (IllegalArgumentException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 删除一个对象
     */
    @LogController(description = "删除一个对象", type = MethodEnum.Delete)
    @RequestMapping(value = SysConstants.URL_DELETE)
    @ResponseBody
    public Message<Object> delete(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            service.delete(id);
            msg.setCode(Code.C201, null, "删除成功!");
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 批量删除 传入ids,id用逗号拼接
     */
    @LogController(description = "删除对象批量", type = MethodEnum.Delete)
    @RequestMapping(value = SysConstants.URL_DELETES)
    @ResponseBody
    public Message<Object> deletes(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") String ids) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            List<Integer> list = idsToIntegers(ids);
            service.deletes(list);
            msg.setCode(Code.C201, null, "批量删除成功!");
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }


    /**
     * 获取字典表中的下拉参数
     *
     * @param parms 参数数组
     * @方法描述：获取List中文对应参数
     */
    public Map<String, List<LabelValue>> getComboList(String[] parms) {
        Map<String, List<LabelValue>> comboMap = new HashMap<String, List<LabelValue>>();
        if (parms != null) {
            for (String parm : parms) {
                if (parm.startsWith("static_")) {
                    // 系统常量(取SysConstantsList中 init_xxx_list()的xxx加static_)
                    String cvalue = parm.substring("static_".length(), parm.length());
                    comboMap.put(cvalue, SysConstantsList.getListforTableName(cvalue));
                } else if (parm.startsWith("db_")) {
                    // 数据库表(取实体类字段,非表字段)
                    String[] values = parm.split("_");
                    List<LabelValue> list = service.findToLabelValue(values[1], values[2]);
                    comboMap.put(values[2], list);
                } else {
                    // 字典表(取字典表中englishName字段)
                    comboMap.put(parm, SysConstantsList.init_commonList(parm));
                }
            }
        }
        return comboMap;
    }

    /**
     * 获取字典表中的参数并放入Attribute中,可以选择转换成json
     *
     * @param parms
     * @param model
     * @param ifJson
     */
    /*@SuppressWarnings("unchecked")
    public void getComboList(String[] parms, ModelMap model, Boolean ifJson) {
        String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        className = StringUtil.capitalToLower(className);
        if (parms != null) {
            for (String parm : parms) {
                if (!parm.startsWith("static_")) {
                    if (ifJson) {
                        model.addAttribute(parm, JSON.toJSONString(SysConstantsList.init_commonList(parm)));
                    } else {
                        model.addAttribute(parm, SysConstantsList.init_commonList(parm));
                    }
                } else {
                    String tableNameString = parm.substring("static_".length(), parm.length());
                    ArrayList<LabelValue> temp = SysConstantsList.getListforTableName(tableNameString);
                    if (ifJson) {
                        model.addAttribute(tableNameString, JSON.toJSONString(temp));
                    } else {
                        model.addAttribute(tableNameString, temp);
                    }
                }
            }
        }
    }*/
}
