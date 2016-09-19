package com.lvtulife.system.controller;

import com.lvtulife.base.component.log.MethodEnum;
import com.lvtulife.base.component.log.annotation.LogController;
import com.lvtulife.base.controller.BaseController;
import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.utils.*;
import com.lvtulife.system.component.security.UserPrincipal;
import com.lvtulife.system.component.vo.SessionInfo;
import com.lvtulife.system.component.vo.SysUserVo;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysRole;
import com.lvtulife.system.model.SysUser;
import com.lvtulife.system.model.SysUserRole;
import com.lvtulife.system.service.SysUserServiceI;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping(value = SysConstants.URL_HEAD_SYSTEM + "/user")
public class SysUserController extends BaseController<SysUser> {
    private static Logger logger = LoggerFactory.getLogger(SysUserController.class);

    private static final String REG_CHART = "system/sysUserRegChart";
    private static final String USER_INFO = "system/sysUserInfo";
    public static final String USER_GRANT = "system/sysUserRoleGrant";
    public static final String USER_IMPORT = "system/sysUserImport";
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
    @Resource(name = "SysUserService")
    private void setService(SysUserServiceI service) {
        this.service = service;
    }

    /**
     * 初始化基础参数
     */
    public SysUserController() {
        LIST_PAGE_URL = "system/sysUser";
        FORM_PAGE_URL = "system/sysUserForm";
        COMBO_PARAMS = new String[]{"static_yesOrNo", "static_status", "static_sex", "static_ustatus"};
        CLASS = this.getClass();
    }

    /**
     * 用户信息
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @LogController(description = "查询用户信息", type = MethodEnum.Find)
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String home(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        UserPrincipal userDetails = getUserSession();
        model.addAttribute("userRoles", userDetails.getRoleNames());
        return USER_INFO;
    }


    /**
     * 角色授予
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @LogController(description = "用户角色授予", type = MethodEnum.Update)
    @RequestMapping(value = "/userGrant", method = RequestMethod.GET)
    public String userGrant(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam("id") Integer id) {
        model.addAttribute("id", id);
        return USER_GRANT;
    }


    /**
     * 用户信息导入
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/userImport", method = RequestMethod.GET)
    public String userImport(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return USER_IMPORT;
    }


    /**
     * 注册时间分布
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/regChart", method = RequestMethod.GET)
    public String regchart(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return REG_CHART;
    }

    @LogController(description = "新增用户信息", type = MethodEnum.Create)
    @RequestMapping(value = SysConstants.URL_SAVE)
    @ResponseBody
    public Message<Object> save(HttpServletRequest request, HttpServletResponse response, SysUser data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (!baseParamsValidate(msg, data, false)) {
            return msg;
        }
        try {
            ((SysUserServiceI) service).saveData(data);
            msg.setCode(Code.C201, null, null);
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
    @LogController(description = "更新用户信息", type = MethodEnum.Update)
    @RequestMapping(value = SysConstants.URL_UPDATE)
    @ResponseBody
    public Message<Object> update(HttpServletRequest request, HttpServletResponse response, SysUser data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (!baseParamsValidate(msg, data, true)) {
            return msg;
        }
        try {
            ((SysUserServiceI) service).updateData(data);
            msg.setCode(Code.C201, null, null);
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
    @LogController(description = "删除用户信息", type = MethodEnum.Delete)
    @RequestMapping(value = SysConstants.URL_DELETE)
    @ResponseBody
    public Message<Object> delete(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            ((SysUserServiceI) service).deleteData(id);
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
    @LogController(description = "批量删除用户信息", type = MethodEnum.Delete)
    @RequestMapping(value = SysConstants.URL_DELETES)
    @ResponseBody
    public Message<Object> deletes(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") String ids) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            List<Integer> list = idsToIntegers(ids);
            ((SysUserServiceI) service).deleteDatas(list);
            msg.setCode(Code.C201, null, "批量删除成功!");
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public Message<Object> login(HttpServletRequest request, HttpServletResponse response, SysUser data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (data == null || StringUtils.isBlank(data.getLoginName()) || StringUtils.isBlank(data.getPwd())) {
            return msg;
        }

        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#loginName_S_EQ", data.getLoginName());
        hqlFilter.addFilter("QUERY_t#pwd_S_EQ", MD5Util.md5(data.getPwd()));
        String resTypeIds = "";// 该字段用于存储当前用户可以访问的资源类型，值从已绑定的角色信息中获取，多个值通过逗号拼接而成，最终值在其他地方处理（如去重）
        String roleIds = "";
        try {
            SysUser user = service.getByFilter(hqlFilter);
            if (user != null) {
                List<SysUserRole> list = sysUserGroupManageBean.findUserRoles(user.getId());
                for (SysUserRole userRole : list) {
                    SysRole role = sysUserGroupManageBean.getRole(userRole.getRid());
                    roleIds += role.getId() + ",";

                    if (!StringUtils.isBlank(role.getResTypeIds()))
                        resTypeIds += role.getResTypeIds() + ",";// 该值在SysRoleRespriAction.grant()中赋值并保存
                }

                SysUserVo userVo = new SysUserVo();
                BeanUtils.copyNotNullProperties(user, userVo);
                userVo.setResTypeIds(resTypeIds);
                userVo.setRoleIds(roleIds);
                userVo.setIp(IpUtil.getIpAddr(request));

                SessionInfo sessionInfo = new SessionInfo();
                sessionInfo.setUser(userVo);
                request.getSession().setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
                msg.setCode(Code.C201);
            } else {
                msg.setCode(Code.C401, "用户名或密码错误！", null);
            }
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 用户信息导入
     * 引用：
     * sysUserImport.jsp
     *
     * @param request
     * @param response
     * @param impfile
     * @return
     */
    @LogController(description = "用户信息批量导入", type = MethodEnum.Create)
    @RequestMapping(value = "/importFile", method = RequestMethod.POST)
    @ResponseBody
    public Message<Object> importFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("impfile") MultipartFile impfile) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (impfile == null) {
            msg.setDetail("导入的文件不存在！");
            return msg;
        }
        try {
            Workbook book = Workbook.getWorkbook(impfile.getInputStream());
            List<SysUser> list = ((SysUserServiceI) service).importExcelFile(book.getSheets());
            ((SysUserServiceI) service).saveAll(list);
            msg.setCode(Code.C201);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 用户信息导出
     * 引用：
     * sysUserExport.jsp
     * sysUser.jsp
     *
     * @param request
     * @param response
     */
    @LogController(description = "用户信息批量导出", type = MethodEnum.Find)
    @RequestMapping(value = "/exportFile")
    @ResponseBody
    public void exportFile(HttpServletRequest request, HttpServletResponse response) {
        HqlFilter hqlFilter = new HqlFilter(request);
        OutputStream outputStream = null;
        WritableWorkbook workbook = null;

        String fileNameTemp = "用户信息.xls";
        String header = request.getHeader("User-Agent").toLowerCase();
        try {
            //处理文件名,避免不同浏览器之间乱码
            if (header.indexOf("firefox") > 0) {
                //火狐等
                fileNameTemp = new String(fileNameTemp.getBytes("UTF-8"), "ISO8859-1");
            } else {
                //IE if (header.indexOf("MSIE") > 0)
                fileNameTemp = URLEncoder.encode(fileNameTemp, "UTF-8");
            }

            outputStream = response.getOutputStream();
            response.reset();// 清空输出流
            response.setHeader("Connection", "close");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileNameTemp + "\"");// 设定输出文件头
            response.setContentType("application/msexcel;charset=utf-8");// 定义输出类型
            workbook = Workbook.createWorkbook(outputStream);
            ((SysUserServiceI) service).exportData(workbook, hqlFilter);// 写数据到 workbook 对象中
            workbook.write();
        } catch (CustomException e) {
            logger.error("导出数据异常！", e);
        } catch (Exception e) {
            logger.error("导出数据异常！", e);
        } finally {
            try {
                if (workbook != null)
                    workbook.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception e) {
                logger.error("导出数据异常！", e);
            }
        }
    }


    /**
     * 用户头像上传
     * 引用：incPlupload.jsp
     *
     * @param request
     * @param response
     * @param fileData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Message<Object> upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileData") MultipartFile fileData) throws Exception {
        Message<Object> msg = new Message<Object>(Code.C501);

        String uploadDir = "";
        if (OsType.isWindows()) {
            uploadDir = "D:\\photo";
        } else {
            uploadDir = "/usr/local/photo";
        }

        // 暂时上传至本地
        File dirPath = new File(uploadDir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        /*MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("fileData");// 这里是表单的名字，在swfupload.js中this.ensureDefault("file_post_name", "filedata");
*/
        InputStream stream = fileData.getInputStream();
        String fileNameFull = uploadDir + "\\" + fileData.getName();
        OutputStream os = new FileOutputStream(fileNameFull);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        stream.close();
        return msg;
    }

    /**
     * 更改对象的状态
     * 引用：
     * sysUser.jsp
     */
    @LogController(description = "更改用户状态", type = MethodEnum.Update)
    @RequestMapping(value = "/change")
    @ResponseBody
    public Message<Object> change(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id, @RequestParam("todo") String todo) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            ((SysUserServiceI) service).changeUserStatus(id, todo);
            msg.setCode(Code.C201, null, "设置成功!");
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 批量更改状态 传入ids,id用逗号拼接
     * 引用：
     * sysUser.jsp
     */
    @LogController(description = "批量更改用户状态", type = MethodEnum.Update)
    @RequestMapping(value = "/changes")
    @ResponseBody
    public Message<Object> changes(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") String ids, @RequestParam("todo") String todo) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            List<Integer> list = idsToIntegers(ids);
            ((SysUserServiceI) service).changeUserStatusMuiti(list, todo);
            msg.setCode(Code.C201, null, "批量设置成功!");
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 注册时间分布统计
     * 引用：sysUserRegChart.jsp
     */
    @RequestMapping(value = "/statiRegChart")
    @ResponseBody
    public List<Long> statiRegChart(HttpServletRequest request, HttpServletResponse response) {
        return sysUserGroupManageBean.statiUserCreateDtChart();
    }

    /**
     * 注销系统
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public Message<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
        return msg;
    }


    @RequestMapping(value = "/grantUserRole")
    @ResponseBody
    public Message<Object> grantUserRole(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id, @RequestParam("rids") String rids) {
        Message<Object> msg = new Message<Object>(Code.C501);
        try {
            if ("0".equals(rids)) {
                // 移除用户的所有角色
                sysUserGroupManageBean.clearUserRole(id);
            } else {
                sysUserGroupManageBean.grantUserRole(id, idsToIntegers(rids));
            }
            msg.setCode(Code.C201, null, null);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/reg")
    @ResponseBody
    synchronized public Message<Object> reg(HttpServletRequest request, HttpServletResponse response, SysUser data) {
        Message<Object> msg = new Message<Object>(Code.C501);
        if (data == null || StringUtils.isBlank(data.getLoginName()) || StringUtils.isBlank(data.getPwd())) {
            return msg;
        }

        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#loginName_S_EQ", data.getLoginName());

        try {
            SysUser user = service.getByFilter(hqlFilter);
            if (user != null) {
                msg.setCode(Code.C401, "用户名已存在!", null);
                return msg;
            } else {
                SysUser u = new SysUser();
                u.setLoginName(data.getLoginName());
                u.setPwd(MD5Util.md5(data.getPwd()));
                u.setAge(new Byte("1"));
                ((SysUserServiceI) service).saveData(u);
                login(request, response, data);
            }
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
    private boolean baseParamsValidate(Message<Object> msg, SysUser data, boolean isUpdate) {
        if (data == null) {
            msg.setDetail("保存的对象不能为空!");
            return false;
        }
        if (isUpdate && data.getId() == null) {
            msg.setDetail("对象中主键不能为空!");
            return false;
        }
        if (StringUtils.isBlank(data.getLoginName())) {
            msg.setDetail("对象中用户名称不能为空!");
            return false;
        }
        return true;
    }
}
