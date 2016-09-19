package com.lvtulife.system.controller;

import com.lvtulife.base.controller.BaseController;
import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.easyui.Tree;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysRole;
import com.lvtulife.system.model.SysRoleRes;
import com.lvtulife.system.model.SysUser;
import com.lvtulife.system.service.SysRoleServiceI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = SysConstants.URL_HEAD_SYSTEM + "/role")
public class SysRoleController extends BaseController<SysRole> {
    private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    private static final String ROLE_CHART = "system/sysRoleChart";
    private static final String ROLE_GRANT = "system/sysRoleGrant";

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
    @Resource(name = "SysRoleService")
    private void setService(SysRoleServiceI service) {
        this.service = service;
    }

    /**
     * 初始化基础参数
     */
    public SysRoleController() {
        LIST_PAGE_URL = "system/sysRole";
        FORM_PAGE_URL = "system/sysRoleForm";
        COMBO_PARAMS = new String[]{"static_yesOrNo", "static_status", "static_rstatus", "static_dataSource"};
        CLASS = this.getClass();
    }

    /**
     * 用户角色分布统计
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/roleChart", method = RequestMethod.GET)
    public String rolechart(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return ROLE_CHART;
    }


    /**
     * 角色授权
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/roleGrant", method = RequestMethod.GET)
    public String roleGrant(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam("id") Integer id) {
        model.addAttribute("id", id);
        return ROLE_GRANT;
    }

    /**
     * 用户角色分布统计
     * 引用：
     * sysRoleChart.jsp
     */
    @RequestMapping(value = "/statiRoleChart")
    @ResponseBody
    public List<Map<String, Object>> statiRoleChart(HttpServletRequest request, HttpServletResponse response) {
        return sysUserGroupManageBean.statiUserRoleChart();
    }


    @RequestMapping(value = SysConstants.URL_SAVE)
    @ResponseBody
    public Message<Object> save(HttpServletRequest request, HttpServletResponse response, SysRole data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (!baseParamsValidate(msg, data, false)) {
            return msg;
        }

        try {
            ((SysRoleServiceI) service).saveData(data);
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
    public Message<Object> update(HttpServletRequest request, HttpServletResponse response, SysRole data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (!baseParamsValidate(msg, data, true)) {
            return msg;
        }

        try {
            ((SysRoleServiceI) service).updateData(data);
            msg.setCode(Code.C201, null, data);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 删除一个对象
     */
    @RequestMapping(value = SysConstants.URL_DELETE)
    @ResponseBody
    public Message<Object> delete(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            ((SysRoleServiceI) service).deleteData(id);
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
            ((SysRoleServiceI) service).deleteDatas(list);
            msg.setCode(Code.C201, null, "批量删除成功!");
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 获得角色的授权信息
     * 引用：sysRoleGrant.jsp
     */
    @RequestMapping(value = "/getRoleAuthPoints")
    @ResponseBody
    public List<Tree> getRoleAuthPoints(HttpServletRequest request, HttpServletResponse response, @RequestParam("rid") Integer rid) {
        List<Tree> tree = new ArrayList<Tree>();
        try {
            List<SysRoleRes> list = sysUserGroupManageBean.findRoleRes(rid);
            for (SysRoleRes r : list) {
                Tree node = new Tree();
                node.setId(r.getResId());
                tree.add(node);
            }
        } catch (CustomException e) {
            exception(e, null);
        } catch (Exception e) {
            logger.error("获取用户角色资源信息失败！", e);
            exception(e, null);
        }
        return tree;
    }

    /**
     * 角色授权
     * 引用：sysRoleGrant.jsp
     *
     * @param request
     * @param response
     * @param rid
     * @param resIds
     * @param typeIds
     * @return
     */
    @RequestMapping(value = "/grantRoleRes")
    @ResponseBody
    public Message<Object> grantRoleRes(HttpServletRequest request, HttpServletResponse response, @RequestParam("rid") Integer rid, @RequestParam("resIds") String resIds, @RequestParam("typeIds") String typeIds) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            sysUserGroupManageBean.grantRoleRes(rid, idsToIntegers(resIds), typeIds);
            msg.setCode(Code.C201, null, null);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 获得所有角色树
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getRolesTree")
    @ResponseBody
    public List<Tree> getRolesTree(HttpServletRequest request, HttpServletResponse response) {
        return sysUserGroupManageBean.findCustomAvailableRoles();
    }

    /**
     * 获得当前用户的角色
     * 引用：
     * sysUserRoleGrant.jsp
     */
    @RequestMapping(value = "/getUserRoles")
    @ResponseBody
    public List<Tree> getUserRoles(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id) {
        List<Tree> tree = new ArrayList<Tree>();
        try {
            SysUser user = sysUserGroupManageBean.getUser(id);
            if (user == null) {
                logger.error("获取用户信息失败！");
                return tree;
            }

            List<SysRole> roles = sysUserGroupManageBean.findUserAllRole(user);
            for (SysRole role : roles) {
                Tree node = new Tree();
                node.setId(role.getId());
                tree.add(node);
            }
        } catch (Exception e) {
            logger.error("获取用户角色信息失败！", e);
        }
        return tree;
    }


    /**
     * 数据保存或者修改时基础参数验证
     */
    private boolean baseParamsValidate(Message<Object> msg, SysRole data, boolean isUpdate) {
        if (data == null) {
            msg.setDetail("保存的对象不能为空!");
            return false;
        }
        if (isUpdate && data.getId() == null) {
            msg.setDetail("对象中主键不能为空!");
            return false;
        }
        if (StringUtils.isBlank(data.getRoleName())) {
            msg.setDetail("对象中角色名称不能为空!");
            return false;
        }
        if (data.getRstatus() == null) {
            msg.setDetail("对象中角色状态不能为空!");
            return false;
        }
        return true;
    }
}
