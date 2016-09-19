package com.lvtulife.system.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.system.component.security.UserPrincipal;
import com.lvtulife.system.component.vo.MainMenuVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lvtulife.base.controller.BaseController;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.component.easyui.Tree;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysResource;
import com.lvtulife.system.service.SysResourceServiceI;

@Controller
@RequestMapping(value = SysConstants.URL_HEAD_SYSTEM + "/resource")
public class SysResourceController extends BaseController<SysResource> {
    private static Logger logger = LoggerFactory.getLogger(SysResourceController.class);

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
    @Resource(name = "SysResourceService")
    private void setService(SysResourceServiceI service) {
        this.service = service;
    }

    /**
     * 初始化基础参数
     */
    public SysResourceController() {
        LIST_PAGE_URL = "system/sysResource";
        FORM_PAGE_URL = "system/sysResourceForm";
        COMBO_PARAMS = new String[]{"db_SysResourceType_typeName", "static_yesOrNo", "static_status", "static_resAttr"};
        CLASS = this.getClass();
    }

    @RequestMapping(value = SysConstants.URL_SAVE)
    @ResponseBody
    public Message<Object> save(HttpServletRequest request, HttpServletResponse response, SysResource data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (!baseParamsValidate(msg, data, false)) {
            return msg;
        }
        try {
            ((SysResourceServiceI) service).saveData(data);
            msg.setCode(Code.C201, null, null);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    @RequestMapping(value = SysConstants.URL_UPDATE)
    @ResponseBody
    public Message<Object> update(HttpServletRequest request, HttpServletResponse response, SysResource data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (!baseParamsValidate(msg, data, true)) {
            return msg;
        }
        try {
            ((SysResourceServiceI) service).updateData(data);
            msg.setCode(Code.C201, null, null);
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

    /**
     * 删除一个对象
     */
    @RequestMapping(value = SysConstants.URL_DELETE)
    @ResponseBody
    public Message<Object> delete(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            ((SysResourceServiceI) service).deleteData(id);
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
    @RequestMapping(value = SysConstants.URL_DELETES)
    @ResponseBody
    public Message<Object> deletes(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") String ids) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            List<Integer> list = idsToIntegers(ids);
            ((SysResourceServiceI) service).deleteDatas(list);
            msg.setCode(Code.C201, null, "批量删除成功!");
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    @RequestMapping(value = SysConstants.URL_TREEGRID)
    @ResponseBody
    public List<SysResource> treeGrid(HttpServletRequest request, HttpServletResponse response) {
        HqlFilter hqlFilter = new HqlFilter(request);
        try {
            return ((SysResourceServiceI) service).findResources(hqlFilter);
        } catch (CustomException e) {
            exception(e, null);
        } catch (Exception e) {
            exception(e, null);
        }
        return null;
    }


    /**
     * 获得资源tree 传typeid:查询指定类型的资源 不传typeid:查询所有
     * 获取指定分类的资源集合
     * 引用：sysResourceForm.jsp
     */
    @RequestMapping(value = "/getMenuForType", method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> getMenuForType(HttpServletRequest request, HttpServletResponse response, Integer id) {
        HqlFilter hqlFilter = new HqlFilter(request);
        List<Tree> tree = new ArrayList<Tree>();
        try {
            if (id != null) {
                hqlFilter.addFilter("QUERY_t#typeId_I_EQ", id);
            }
            return ((SysResourceServiceI) service).findResourcesToTrees(hqlFilter);
        } catch (CustomException e) {
            exception(e, null);
        } catch (Exception e) {
            exception(e, null);
        }
        return tree;
    }


    /**
     * 从session中获取当前用户的具有访问权限的资源信息,分类浏览
     * 首页菜单加载
     * 引用：main.jsp
     */
    @RequestMapping(value = "/getMenus", method = RequestMethod.POST)
    @ResponseBody
    public Message<Object> getMenus(HttpServletRequest request, HttpServletResponse response) {
        Message<Object> msg = new Message<Object>(Code.C400);
        UserPrincipal userDetails = getUserSession();
        try {
            List<MainMenuVo> result = sysUserGroupManageBean.getMenu(userDetails);
            msg.setCode(Code.C201, null, result);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 从session中获取当前用户的具有访问权限的资源信息，不分类
     * 引用：sysUserInfo
     */
    @RequestMapping(value = "/getMenusNoType", method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> getMenusNoType(HttpServletRequest request, HttpServletResponse response) {
        UserPrincipal userDetails = getUserSession();
        List<Tree> result = new ArrayList<Tree>();
        try {
            result = sysUserGroupManageBean.getMenuNoType(userDetails);
        } catch (CustomException e) {
            exception(e, null);
        } catch (Exception e) {
            exception(e, null);
        }
        return result;
    }


    /**
     * 角色授权页面，获得资源树
     * 会显示基础权限点
     * 引用：sysRoleGrant.jsp
     */
    @RequestMapping(value = "/getResourcesTree", method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> getResourcesTree(HttpServletRequest request, HttpServletResponse response) {
        HqlFilter hqlFilter = new HqlFilter(request);
        return ((SysResourceServiceI) service).findResourcesShowResAttrToTrees(hqlFilter);
    }


    /**
     * 取得当前父节点下所有子节点中最大的序号
     * 引用：sysResourceForm.jsp
     */
    @RequestMapping(value = "/getResourceMaxSeq", method = RequestMethod.POST)
    @ResponseBody
    public Message<Object> getResourceMaxSeq(HttpServletRequest request, HttpServletResponse response, Integer pid) {
        Message<Object> msg = new Message<Object>(Code.C400);
        try {
            int seq = ((SysResourceServiceI) service).getResourceMaxSeq(pid);
            msg.setCode(Code.C201, null, seq);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }


    /**
     * 数据保存或者修改时基础参数验证
     */
    private boolean baseParamsValidate(Message<Object> msg, SysResource data, boolean isUpdate) {
        if (data == null) {
            msg.setDetail("保存的对象不能为空!");
            return false;
        }
        if (isUpdate && data.getId() == null) {
            msg.setDetail("对象中主键不能为空!");
            return false;
        }
        if (StringUtils.isBlank(data.getResName())) {
            msg.setDetail("对象中资源名称不能为空!");
            return false;
        }
        if (data.getTypeId() == null) {
            msg.setDetail("对象中资源类型不能为空!");
            return false;
        }
        if (data.getResAttr() == null) {
            msg.setDetail("对象中资源属性不能为空!");
            return false;
        }
        return true;
    }
}
