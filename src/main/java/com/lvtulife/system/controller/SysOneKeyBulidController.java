package com.lvtulife.system.controller;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.easyui.Grid;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.controller.StandardController;
import com.lvtulife.base.utils.FileUtil;
import com.lvtulife.base.utils.StringUtil;
import com.lvtulife.system.component.onekeyBuild.ClassProperty;
import com.lvtulife.system.component.onekeyBuild.EntityInfo;
import com.lvtulife.system.component.onekeyBuild.EntityUtil;
import com.lvtulife.system.component.onekeyBuild.FieldInfo;
import com.lvtulife.system.model.SysResource;
import com.lvtulife.system.service.SysResourceServiceI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
@RequestMapping(value = SysConstants.URL_HEAD_SYSTEM + "/oneKeyBulid")
public class SysOneKeyBulidController extends StandardController {

    private static final Logger logger = LoggerFactory.getLogger(SysOneKeyBulidController.class);

    // 读取dev.properties 配置文件,获取各模块基础路径
    private static final ResourceBundle bundle = PropertyResourceBundle.getBundle("system");

    @Resource(name = "SysResourceService")
    private SysResourceServiceI SysResourceService;

    @RequestMapping("/oneKeyBulid")
    public String oneKeyBulid(HttpServletRequest request, HttpServletResponse response) {
        return "system/sysOneKeyBulid";
    }

    @RequestMapping("/bulidCheck")
    public String bulidCheck(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));

        List<EntityInfo> datas = new ArrayList<EntityInfo>();
        Iterator<EntityInfo> iterator = EntityUtil.getAllEntityInfo().iterator();
        while (iterator.hasNext()) {
            EntityInfo info = iterator.next();
            if (idList.contains(info.getEntityName())) {
                datas.add(info);
            }
        }
        request.setAttribute("datas", datas);
        return "system/sysOneKeyBulidCheck";
    }

    @RequestMapping(value = "/gridAll")
    @ResponseBody
    public Grid gridAll(HttpServletRequest request, HttpServletResponse response) {
        Grid datas = new Grid();
        try {
            Map<String, SysResource> resMap = SysResourceService.findAllMenuToUrlMap();
            List<EntityInfo> entitys = EntityUtil.getAllEntityInfo();

            // 匹配菜单资源是否存在
            for (EntityInfo entity : entitys) {
                String url = "/" + entity.getModular() + "/" + entity.getUrlModule() + "/" + "list";
                entity.setHasMenuResource(resMap.containsKey(url));
            }
            datas.setTotal((long) entitys.size());
            datas.setRows(entitys);
        } catch (CustomException e) {
            exception(e, null);
        } catch (Exception e) {
            exception(e, null);
        }
        return datas;
    }

    @RequestMapping(value = "/bulidCode")
    @ResponseBody
    public Message<Object> bulidCode(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "instructs") String instructs) {
        Message<Object> msg = new Message<Object>(Code.C401);
        //   SysInfo|system|0.1.1.0.1.1.0.0.0.1,SysUser|system|0.1.1.0.1.1.0.0.0.0
        String[] instructArr = instructs.split(",");

        try {
            for (int i = 0; i < instructArr.length; i++) {
                String[] ins = instructArr[i].split("\\|");

                //0:daoI; 1:daoImpl; 2:serviceI; 3:serviceImpl; 4:Controller; 5:ControllerApi; 6:pageList; 7:pageForm; 8:mapper; 9:mapper.xml 10:menuResource
                String[] privilege = ins[2].split("[.]");
                if (privilege.length != 11) {
                    msg.setCode(Code.C501);
                    return msg;
                }

                EntityInfo entity = new EntityInfo(ins[0], ins[1]); // 实体信息
                List<FieldInfo> fields = ClassProperty.getClassPropertyList(entity.getModelPackageToFull()); // 实体属性信息
                HashMap<String, String> tableNames = ClassProperty.getTableMapForDB();
                entity.setTableComment(tableNames.get(ClassProperty.getTableName(entity.getModelPackageToFull())));

                if (privilege[0].equals("1")) {
                    bulidController(entity);
                }
                if (privilege[1].equals("1")) {
                    bulidControllerApi(entity);
                }
                if (privilege[2].equals("1")) {
                    bulidService(entity);
                }
                if (privilege[3].equals("1")) {
                    bulidServiceImpl(entity);
                }
                if (privilege[4].equals("1")) {
                    bulidDao(entity);
                }
                if (privilege[5].equals("1")) {
                    bulidDaoImpl(entity);
                }
                if (privilege[6].equals("1")) {
                    bulidPageList(entity, fields);
                }
                if (privilege[7].equals("1")) {
                    bulidPageForm(entity, fields);
                }
                if (privilege[8].equals("1")) {
                    bulidMapper(entity, fields);
                }
                if (privilege[9].equals("1")) {
                    bulidMapXML(entity, fields);
                }
                if (privilege[10].equals("1")) {
                    bulidResource(entity);
                }
            }
            msg.setCode(Code.C201);
        } catch (CustomException e) {
            exception(e, msg);
        } catch (Exception e) {
            exception(e, msg);
        }
        return msg;
    }

    private void bulidDao(EntityInfo entity) {
        if (!FileUtil.isExist(entity.getDaoPath(false))) {
            FileUtil.makeDir(entity.getDaoPath(false));
        }
        if (entity.getHasDao()) {
            return;
        }

        String filePath = entity.getDaoPath(true);

        FileUtil.writeLine(filePath, "package " + entity.getDaoPackage(false) + ";");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.dao.BaseDaoI;");
        FileUtil.writeLine(filePath, "import " + entity.getModelPackageToFull() + ";");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "/**");
        FileUtil.writeLine(filePath, " * " + entity.getTableComment());
        FileUtil.writeLine(filePath, " * " + entity.getDaoName(false) + " component. @author lvtulife Tools");
        FileUtil.writeLine(filePath, " */");
        FileUtil.writeLine(filePath, "public interface " + entity.getDaoName(false) + " extends BaseDaoI<" + entity.getEntityName() + "> {");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "}");

        logger.info("{} 构建完毕！", entity.getDaoPath(true));
    }

    private void bulidDaoImpl(EntityInfo entity) {
        if (!FileUtil.isExist(entity.getDaoImplPath(false))) {
            FileUtil.makeDir(entity.getDaoImplPath(false));
        }
        if (entity.getHasDaoImpl()) {
            return;
        }

        String filePath = entity.getDaoImplPath(true);

        FileUtil.writeLine(filePath, "package " + entity.getDaoImplPackage(false) + ";");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "import org.springframework.stereotype.Repository;");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.dao.impl.BaseDaoImpl;");
        FileUtil.writeLine(filePath, "import org.slf4j.Logger; import org.slf4j.LoggerFactory;");
        FileUtil.writeLine(filePath, "import " + entity.getDaoPackage(true) + ";");
        FileUtil.writeLine(filePath, "import " + entity.getModelPackageToFull() + ";");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "/**");
        FileUtil.writeLine(filePath, " * " + entity.getTableComment());
        FileUtil.writeLine(filePath, " * " + entity.getDaoImplName(false) + " component. @author lvtulife Tools");
        FileUtil.writeLine(filePath, " */");
        FileUtil.writeLine(filePath, "@Repository(\"" + entity.getEntityName() + "Dao\")");
        FileUtil.writeLine(filePath, "public class " + entity.getDaoImplName(false) + " extends BaseDaoImpl<" + entity.getEntityName() + "> implements " + entity.getDaoName(false) + " {");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "	private static Logger logger = LoggerFactory.getLogger(" + entity.getEntityName() + ".class);");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "}");

        logger.info("{} 构建完毕！", entity.getDaoImplPath(true));
    }

    private void bulidService(EntityInfo entity) {
        if (!FileUtil.isExist(entity.getServicePath(false))) {
            FileUtil.makeDir(entity.getServicePath(false));
        }
        if (entity.getHasService()) {
            return;
        }

        String filePath = entity.getServicePath(true);
        FileUtil.writeLine(filePath, "package " + entity.getServicePackage(false) + ";");
        FileUtil.writeLine(filePath, "");
        //FileUtil.writeLine(filePath, "import com.lvtulife.base.service.BaseServiceI;");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.mapper.BaseMapperI;");
        FileUtil.writeLine(filePath, "import " + entity.getModelPackageToFull() + ";");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "/**");
        FileUtil.writeLine(filePath, " * " + entity.getTableComment());
        FileUtil.writeLine(filePath, " * " + entity.getServiceName(false) + " component. @author lvtulife Tools");
        FileUtil.writeLine(filePath, " */");
        //FileUtil.writeLine(filePath, "public interface " + entity.getServiceName(false) + " extends BaseServiceI<" + entity.getEntityName() + "> {");
        FileUtil.writeLine(filePath, "public interface " + entity.getServiceName(false) + " extends BaseMapperI<" + entity.getEntityName() + "> {");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "}");

        logger.info("{} 构建完毕！", entity.getServicePath(true));
    }

    private void bulidServiceImpl(EntityInfo entity) {
        if (!FileUtil.isExist(entity.getServiceImplPath(false))) {
            FileUtil.makeDir(entity.getServiceImplPath(false));
        }
        if (entity.getHasServiceImpl()) {
            return;
        }

        String filePath = entity.getServiceImplPath(true);
        FileUtil.writeLine(filePath, "package " + entity.getServiceImplPackage(false) + ";");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "import org.springframework.stereotype.Service;");
        FileUtil.writeLine(filePath, "import org.springframework.transaction.annotation.Propagation;");
        FileUtil.writeLine(filePath, "import org.springframework.transaction.annotation.Transactional;");
        FileUtil.writeLine(filePath, "import " + entity.getDaoPackage(true) + ";");
        FileUtil.writeLine(filePath, "import " + entity.getServicePackage(true) + ";");
        FileUtil.writeLine(filePath, "import " + entity.getModelPackageToFull() + ";");
        FileUtil.writeLine(filePath, "import " + entity.getMapperPackage(true) + ";");
        //FileUtil.writeLine(filePath, "import com.lvtulife.base.service.impl.BaseServiceImpl;");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.mapper.impl.BaseMapperImpl;");
        FileUtil.writeLine(filePath, "import org.slf4j.Logger;");
        FileUtil.writeLine(filePath, "import org.slf4j.LoggerFactory;");
        FileUtil.writeLine(filePath, "import javax.annotation.Resource;");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "/**");
        FileUtil.writeLine(filePath, " * " + entity.getTableComment());
        FileUtil.writeLine(filePath, " * " + entity.getServiceImplName(false) + " component. @author lvtulife Tools");
        FileUtil.writeLine(filePath, " */");
        FileUtil.writeLine(filePath, "@Service(\"" + entity.getEntityName() + "Service\")");
        FileUtil.writeLine(filePath, "@Transactional(readOnly = true, propagation = Propagation.REQUIRED)");
        //FileUtil.writeLine(filePath, "public class " + entity.getServiceImplName(false) + " extends BaseServiceImpl<" + entity.getEntityName() + "> implements " + entity.getEntityName() + "ServiceI {");
        FileUtil.writeLine(filePath, "public class " + entity.getServiceImplName(false) + " extends BaseMapperImpl<" + entity.getEntityName() + "> implements " + entity.getEntityName() + "ServiceI {");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "  private static Logger logger = LoggerFactory.getLogger(" + entity.getEntityName() + ".class);");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "  @Resource(name = \"" + entity.getEntityName() + "Dao\")");
        FileUtil.writeLine(filePath, "  private " + entity.getDaoName(false) + " " + StringUtil.capitalToLower(entity.getEntityName()) + "Dao;");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "	@Resource(name = \"" + entity.getEntityName() + "Mapper\")");
        FileUtil.writeLine(filePath, "	private " + entity.getEntityName() + "Mapper " + StringUtil.capitalToLower(entity.getEntityName()) + "Mapper;");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "}");

        logger.info("{} 构建完毕！", entity.getServiceImplPath(true));
    }

    private void bulidController(EntityInfo entity) {
        if (!FileUtil.isExist(entity.getControllerPath(false))) {
            FileUtil.makeDir(entity.getControllerPath(false));
        }
        if (entity.getHasController()) {
            return;
        }

        String filePath = entity.getControllerPath(true);
        FileUtil.writeLine(filePath, "package " + entity.getControllerPackage(false) + ";");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.component.constants.SysConstants;");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.controller.BaseController;");
        FileUtil.writeLine(filePath, "import " + entity.getModelPackageToFull() + ";");
        FileUtil.writeLine(filePath, "import " + entity.getServicePackage(true) + ";");
        FileUtil.writeLine(filePath, "import org.slf4j.Logger;");
        FileUtil.writeLine(filePath, "import org.slf4j.LoggerFactory;");
        FileUtil.writeLine(filePath, "import org.springframework.stereotype.Controller;");
        FileUtil.writeLine(filePath, "import org.springframework.web.bind.annotation.RequestMapping;");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "import javax.annotation.Resource;");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "/**");
        FileUtil.writeLine(filePath, " * " + entity.getTableComment());
        FileUtil.writeLine(filePath, " * " + entity.getControllerName(false) + " component. @author lvtulife Tools");
        FileUtil.writeLine(filePath, " */");
        FileUtil.writeLine(filePath, "@Controller");
        FileUtil.writeLine(filePath, "@RequestMapping(value = SysConstants.URL_HEAD_" + entity.getModular().toUpperCase() + " + \"/" + entity.getUrlModule() + "/\")");
        FileUtil.writeLine(filePath, "public class " + entity.getControllerName(false) + " extends BaseController<" + entity.getEntityName() + "> {");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "    private static Logger logger = LoggerFactory.getLogger(" + entity.getControllerName(false) + ".class);");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "    /**");
        FileUtil.writeLine(filePath, "     * 注入业务逻辑");
        FileUtil.writeLine(filePath, "     *");
        FileUtil.writeLine(filePath, "     * @param service");
        FileUtil.writeLine(filePath, "     */");
        FileUtil.writeLine(filePath, "    @Resource(name = \"" + entity.getEntityName() + "Service\")");
        FileUtil.writeLine(filePath, "    private void setService(" + entity.getServiceName(false) + " service) {");
        FileUtil.writeLine(filePath, "        this.service = service;");
        FileUtil.writeLine(filePath, "    }");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "    /**");
        FileUtil.writeLine(filePath, "     * 初始化基础参数");
        FileUtil.writeLine(filePath, "     */");
        FileUtil.writeLine(filePath, "    public " + entity.getControllerName(false) + "() {");
        FileUtil.writeLine(filePath, "        LIST_PAGE_URL = \"" + entity.getModular() + "/" + StringUtil.capitalToLower(entity.getEntityName()) + "\";");
        FileUtil.writeLine(filePath, "        FORM_PAGE_URL = \"" + entity.getModular() + "/" + StringUtil.capitalToLower(entity.getEntityName()) + "Form\";");
        FileUtil.writeLine(filePath, "        COMBO_PARAMS = new String[]{\"static_yesOrNo\",\"static_dataSource\"};");
        FileUtil.writeLine(filePath, "        CLASS = this.getClass();");
        FileUtil.writeLine(filePath, "    }");
        FileUtil.writeLine(filePath, "}");

        logger.info("{} 构建完毕！", entity.getControllerPath(true));
    }

    private void bulidControllerApi(EntityInfo entity) {
        if (!FileUtil.isExist(entity.getControllerApiPath(false))) {
            FileUtil.makeDir(entity.getControllerApiPath(false));
        }
        if (entity.getHasApiController()) {
            return;
        }

        String filePath = entity.getControllerApiPath(true);
        FileUtil.writeLine(filePath, "package " + entity.getControllerApiPackage(false) + ";");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.component.constants.SysConstants;");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.component.message.Code;");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.component.message.CustomException;");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.component.message.Message;");
        FileUtil.writeLine(filePath, "import com.lvtulife.base.controller.StandardController;");
        FileUtil.writeLine(filePath, "import " + entity.getModelPackageToFull() + ";");
        FileUtil.writeLine(filePath, "import org.slf4j.Logger;");
        FileUtil.writeLine(filePath, "import org.slf4j.LoggerFactory;");
        FileUtil.writeLine(filePath, "import org.springframework.stereotype.Controller;");
        FileUtil.writeLine(filePath, "import org.springframework.web.bind.annotation.RequestMapping;");
        FileUtil.writeLine(filePath, "import org.springframework.web.bind.annotation.ResponseBody;");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "import javax.annotation.Resource;");
        FileUtil.writeLine(filePath, "import javax.servlet.http.HttpServletRequest;");
        FileUtil.writeLine(filePath, "import javax.servlet.http.HttpServletResponse;");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "/**");
        FileUtil.writeLine(filePath, " * " + entity.getTableComment());
        FileUtil.writeLine(filePath, " * " + entity.getControllerApiName(false) + " component. @author lvtulife Tools");
        FileUtil.writeLine(filePath, " */");
        FileUtil.writeLine(filePath, "@Controller");
        FileUtil.writeLine(filePath, "@RequestMapping(value = SysConstants.API_HEAD_" + entity.getModular().toUpperCase() + " + \"/" + entity.getUrlModule() + "/\")");
        FileUtil.writeLine(filePath, "public class " + entity.getControllerApiName(false) + " extends StandardController {");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "    private static Logger logger = LoggerFactory.getLogger(" + entity.getControllerApiName(false) + ".class);");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "    @RequestMapping(value = \"/temp\")");
        FileUtil.writeLine(filePath, "    @ResponseBody");
        FileUtil.writeLine(filePath, "    public Message<Object> temp(HttpServletRequest request, HttpServletResponse response) {");
        FileUtil.writeLine(filePath, "        Message<Object> msg = new Message<Object>(Code.C501);");
        FileUtil.writeLine(filePath, "        try {");
        FileUtil.writeLine(filePath, "            msg.setCode(Code.C201, null, null);");
        FileUtil.writeLine(filePath, "        } catch (CustomException e) {");
        FileUtil.writeLine(filePath, "            exception(e, msg);");
        FileUtil.writeLine(filePath, "        } catch (Exception e) {");
        FileUtil.writeLine(filePath, "            exception(e, msg);");
        FileUtil.writeLine(filePath, "        }");
        FileUtil.writeLine(filePath, "        return msg;");
        FileUtil.writeLine(filePath, "    }");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "}");

        logger.info("{} 构建完毕！", entity.getControllerApiPath(true));
    }

    private void bulidPageList(EntityInfo entity, List<FieldInfo> fields) {
        if (!FileUtil.isExist(entity.getPageListPath(false))) {
            FileUtil.makeDir(entity.getPageListPath(false));
        }
        if (entity.getHasPageList()) {
            return;
        }

        String filePath = entity.getPageListPath(true);

        FileUtil.writeLine(filePath, "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\" %>");
        FileUtil.writeLine(filePath, "<% String tableName = \"" + entity.getTableComment() + "\";%>");
        FileUtil.writeLine(filePath, "<!DOCTYPE html>");
        FileUtil.writeLine(filePath, "<html>");
        FileUtil.writeLine(filePath, "<head>");
        FileUtil.writeLine(filePath, "    <title></title>");
        FileUtil.writeLine(filePath, "    <jsp:include page=\"/views/include/inc.jsp\"/>");
        FileUtil.writeLine(filePath, "    <script type=\"text/javascript\">");
        FileUtil.writeLine(filePath, "        var grid;");
        FileUtil.writeLine(filePath, "        var isTreeGrid = false;");
        FileUtil.writeLine(filePath, "        var baseUrl = sysExt.apiHead" + StringUtil.toUpperCaseFirstOne(entity.getModular()) + " + '/" + entity.getUrlModule() + "';");
        FileUtil.writeLine(filePath, "        var pageTableName = '<%=tableName%>';");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "        var tempFun = function (id) {");
        FileUtil.writeLine(filePath, "            var node = grid.datagrid('getSelected');");
        FileUtil.writeLine(filePath, "            if (node) {");
        FileUtil.writeLine(filePath, "                var dialog = parent.sysExt.modalDialog({");
        FileUtil.writeLine(filePath, "                    title: '示例',");
        FileUtil.writeLine(filePath, "                    url: baseUrl + '/temp?id=' + node.id,");
        FileUtil.writeLine(filePath, "                    toolbar: [");
        FileUtil.writeLine(filePath, "                        {");
        FileUtil.writeLine(filePath, "                            id: 'btn1', text: '示例', iconCls: 'ext-icon-shield',");
        FileUtil.writeLine(filePath, "                            handler: function () {");
        FileUtil.writeLine(filePath, "                                dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);");
        FileUtil.writeLine(filePath, "                            }");
        FileUtil.writeLine(filePath, "                        }");
        FileUtil.writeLine(filePath, "                    ]");
        FileUtil.writeLine(filePath, "                });");
        FileUtil.writeLine(filePath, "            }");
        FileUtil.writeLine(filePath, "        };");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "        $(function () {");
        FileUtil.writeLine(filePath, "            grid = $('#grid').datagrid({");
        FileUtil.writeLine(filePath, "                title: '',");
        FileUtil.writeLine(filePath, "                url: baseUrl + '/grid',");
        FileUtil.writeLine(filePath, "                striped: true,");
        FileUtil.writeLine(filePath, "                rownumbers: true,");
        FileUtil.writeLine(filePath, "                pagination: true,");
        FileUtil.writeLine(filePath, "                singleSelect: true,");
        FileUtil.writeLine(filePath, "                idField: 'id',");
        FileUtil.writeLine(filePath, "                sortName: 'id',");
        FileUtil.writeLine(filePath, "                sortOrder: 'asc',");
        FileUtil.writeLine(filePath, "                pageSize: 20,");
        FileUtil.writeLine(filePath, "                pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],");

//        FileUtil.writeLine(filePath, "                frozenColumns: [[{width: '100', title: '角色名称', field: 'roleName', sortable: true}]],");
//        FileUtil.writeLine(filePath, "                columns: [[");
//        FileUtil.writeLine(filePath, "                    {width: '100', title: '角色名称EN', field: 'roleNameEn'},");
//        FileUtil.writeLine(filePath, "                    {width: '300', title: '资源描述', field: 'bewrite'},");
//        FileUtil.writeLine(filePath, "                    {");
//        FileUtil.writeLine(filePath, "                        width: '60', title: '资源状态', field: 'rstatus', align: 'center', formatter: function (value, row, index) {");
//        FileUtil.writeLine(filePath, "                        return sysExt.getComboValue('rstatus', value);");
//        FileUtil.writeLine(filePath, "                    }");
//        FileUtil.writeLine(filePath, "                    },");
//        FileUtil.writeLine(filePath, "                    {");
//        FileUtil.writeLine(filePath, "                        width: '60', title: '数据来源', field: 'source', align: 'center', formatter: function (value, row, index) {");
//        FileUtil.writeLine(filePath, "                        return sysExt.getComboValue('dataSource', value);");
//        FileUtil.writeLine(filePath, "                    }");
//        FileUtil.writeLine(filePath, "                    },");
//        FileUtil.writeLine(filePath, "                    {width: '150', title: '创建时间', field: 'createdDt', sortable: true},");
//        FileUtil.writeLine(filePath, "                    {width: '150', title: '修改时间', field: 'updatedDt', sortable: true},");
//        FileUtil.writeLine(filePath, "                    {width: '60', title: '排序', field: 'sort', hidden: true, sortable: true}]], ");

        FileUtil.writeLine(filePath, "                frozenColumns : [[]],");
        FileUtil.writeLine(filePath, "                columns : [ [ ");


        boolean isStart = true;
        String comma = "";
        for (FieldInfo f : fields) {
            String width = "D".equals(f.getTypeShort()) ? "140" : "100";// 时间类型需更大宽度
            String align = "left";
            String combo = "";
            String chsName = f.getColumnComments();

            if (StringUtils.isBlank(chsName)
                    || "Other".equals(f.getTypeShort())
                    || "createdId".equals(f.getFieldName()) || "updatedId".equals(f.getFieldName()) || "del".equals(f.getFieldName()))
                continue;

            // 宽度自动换算

            // 当此字段的列头字符数大于6时或当此字段等于这些值的时候列的宽度固定位250
            if (chsName.length() > 6 || chsName.indexOf("备注") > -1 || chsName.indexOf("内容") > -1
                    || chsName.indexOf("地址") > -1 || chsName.indexOf("描述") > -1
                    || chsName.indexOf("说明") > -1 || chsName.indexOf("评价") > -1) {
                width = "250";
            }
            // Integer类型的参数时，设置为居中
            if ("I".equals(f.getTypeShort())) {
                align = "center";
            }

            // 设置下拉框
            if (isCombobox(chsName)) {
                combo = ",formatter:function(value,row,index){return sysExt.getComboValue('yesOrNo',value);}";
            }

            if (!isStart)
                comma = ",";// 非首行时加','
            FileUtil.writeLine(filePath, "                    " + comma + "{width : '" + width + "',title : '" + chsName + "',field : '" + f.getFieldName() + "',align : '" + align + "',hidden : " + f.getPrimaryKey() + ",sortable : true" + combo + "}");
            isStart = false;
        }
        FileUtil.writeLine(filePath, "                    ] ],");


        FileUtil.writeLine(filePath, "                toolbar: '#toolbar',");
        FileUtil.writeLine(filePath, "                onBeforeLoad: function (param) {");
        FileUtil.writeLine(filePath, "                    beforeLoadFun(param);");
        FileUtil.writeLine(filePath, "                },");
        FileUtil.writeLine(filePath, "                onLoadSuccess: function (data) {");
        FileUtil.writeLine(filePath, "                    loadSuccessFun(data);");
        FileUtil.writeLine(filePath, "                },");
        FileUtil.writeLine(filePath, "                onDblClickRow: showFun");
        FileUtil.writeLine(filePath, "            });");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "            $('#tempBtn').text('示例').linkbutton({iconCls: 'ext-icon-shield', plain: true, onClick: tempFun}).attr('href', 'javascript:void(0);');");
        FileUtil.writeLine(filePath, "        });");
        FileUtil.writeLine(filePath, "    </script>");
        FileUtil.writeLine(filePath, "    <jsp:include page=\"/views/include/incPageListD.jsp\"/>");
        FileUtil.writeLine(filePath, "</head>");
        FileUtil.writeLine(filePath, "<body class=\"easyui-layout\" data-options=\"fit:true,border:false\">");
        FileUtil.writeLine(filePath, "<div id=\"toolbar\" style=\"display: none;\">");
        FileUtil.writeLine(filePath, "    <div class=\"easyui-panel\" style=\"width:100%;\" data-options=\"border:false\">");
        FileUtil.writeLine(filePath, "        <form id=\"searchForm\">");
        FileUtil.writeLine(filePath, "            <ul class=\"sysToolBarUl\">");
        FileUtil.writeLine(filePath, "                <li><a id=\"addBtn\"></a></li>");
        FileUtil.writeLine(filePath, "                <li><a id=\"editBtn\"></a></li>");
        FileUtil.writeLine(filePath, "                <li><a id=\"moreBtn\"></a></li>");
        FileUtil.writeLine(filePath, "                <li><a id=\"tempBtn\"></a></li>");
        FileUtil.writeLine(filePath, "                <div id=\"mmore\">");
        FileUtil.writeLine(filePath, "                    <div id=\"showBtn\"></div>");
        FileUtil.writeLine(filePath, "                    <div class=\"menu-sep\"></div>");
        FileUtil.writeLine(filePath, "                    <div id=\"delBtn\"></div>");
        FileUtil.writeLine(filePath, "                </div>");
        FileUtil.writeLine(filePath, "                <li><a id=\"singleSelBtn\"></a></li>");

        //FileUtil.writeLine(filePath, "                <li><input id=\"query_1\" name=\"QUERY_t#roleName_S_LK\" data-options=\"prompt:'名称'\" style=\"width: 80px;height:24px;\" class=\"easyui-textbox\"/></li>");
        //FileUtil.writeLine(filePath, "                <li><select id=\"query_2\" name=\"QUERY_t#status_BT_EQ\" class=\"easyui-combobox easyui-textbox\" data-options=\"editable:false,data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',panelHeight:'auto',prompt:'角色状态'\" style=\"width: 80px;height:24px;\"></select></li>");

        // 普通查询
        boolean ifdate = false;
        for (FieldInfo f : fields) {
            String enName = f.getFieldName();
            String chsName = f.getColumnComments();

            if (StringUtils.isBlank(chsName) || "Other".equals(f.getTypeShort()) || "id".equals(f.getFieldName())
                    || "createdId".equals(f.getFieldName()) || "updatedId".equals(f.getFieldName())
                    || "createdDt".equals(f.getFieldName()) || "updatedDt".equals(f.getFieldName())
                    || "del".equals(f.getFieldName()) || "sort".equals(f.getFieldName()))
                continue;

            if (chsName.indexOf("备注") > -1 || chsName.indexOf("评价") > -1
                    || chsName.indexOf("内容") > -1 || chsName.indexOf("地址") > -1
                    || chsName.indexOf("描述") > -1 || chsName.indexOf("说明") > -1)
                continue;

            // 过滤 主键和字段类型为Other和时间
            if (!"D".equals(f.getTypeShort())) {

                // 获取操作符
                String operators = getOperators(f.getTypeShort());

                String width = "80";// 时间类型需更大宽度
                // 宽度自动换算
                if (!StringUtils.isBlank(chsName) && chsName.length() > 6) {
                    width = String.valueOf(chsName.length() * 11);// px
                }

                // 设置下拉框
                if (isCombobox(chsName)) {
                    FileUtil.writeLine(filePath, "				<li><input name=\"QUERY_t#" + enName + "_" + f.getTypeShort() + "_" + operators + "\" style=\"width: " + width + "px;height:24px;\" class=\"easyui-combobox easyui-textbox\" data-options=\"data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',panelHeight:'auto',editable:false,prompt:'" + chsName + "'\"/></li>");
                } else {
                    // 生成过滤字段
                    FileUtil.writeLine(filePath, "				<li><input name=\"QUERY_t#" + enName + "_" + f.getTypeShort() + "_" + operators + "\" style=\"width: " + width + "px;height:24px;\" class=\"easyui-textbox\" data-options=\"prompt:'" + chsName + "'\" /></li>");
                }
            }
            if ("D".equals(f.getTypeShort()))
                ifdate = true;
        }
        // 时间查询
        if (ifdate) {
            FileUtil.writeLine(filePath, "				<li><select name=\"SDATETYPE\" data-options=\"panelHeight:'auto',editable:false\" style=\"width: 80px;height:24px;\" class=\"easyui-combobox\">");
            for (FieldInfo f : fields) {
                String enName = f.getFieldName();
                String chsName = f.getColumnComments();
                if ("D".equals(f.getTypeShort()) && !"updatedDt".equals(enName) && !"createdDt".equals(enName))
                    FileUtil.writeLine(filePath, "					<option value=\"QUERY_t#" + enName + "_D_\">" + chsName + "</option>");
            }
            FileUtil.writeLine(filePath, "				</select></li>");
            FileUtil.writeLine(filePath, "				<li><input id=\"d_start\" name=\"GE\" title=\"时间或日期区间开始\" onclick=\"WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\\'d_end\\')||\\'2080-12-01 00:00:00\\'}'})\"  data-options=\"position:'left'\" class=\"Wdate easyui-tooltip sysSearchDateBox\"/></li>");
            FileUtil.writeLine(filePath, "				<li><input id=\"d_end\" name=\"LE\" title=\"时间或日期区间结束\" onclick=\"WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\\'d_start\\')}',maxDate:'2080-12-01 00:00:00'})\" data-options=\"position:'left'\" class=\"Wdate easyui-tooltip sysSearchDateBox\"/></li>");
        }

        FileUtil.writeLine(filePath, "                <li><a id=\"searchBtn\"></a></li>");
        FileUtil.writeLine(filePath, "                <li><a id=\"resetSearchBtn\"></a></li>");
        FileUtil.writeLine(filePath, "            </ul>");
        FileUtil.writeLine(filePath, "        </form>");
        FileUtil.writeLine(filePath, "    </div>");
        FileUtil.writeLine(filePath, "</div>");
        FileUtil.writeLine(filePath, "<div data-options=\"region:'center',fit:true,border:false\">");
        FileUtil.writeLine(filePath, "    <table id=\"grid\" data-options=\"fit:true,border:false\"></table>");
        FileUtil.writeLine(filePath, "</div>");
        FileUtil.writeLine(filePath, "</body>");
        FileUtil.writeLine(filePath, "</html>");

        logger.info("{} 构建完毕！", entity.getPageListPath(true));
    }


    private void bulidPageForm(EntityInfo entity, List<FieldInfo> fields) {
        if (!FileUtil.isExist(entity.getPageFormPath(false))) {
            FileUtil.makeDir(entity.getPageFormPath(false));
        }
        if (entity.getHasPageForm()) {
            return;
        }
        System.out.println(fields.toString());
        String filePath = entity.getPageFormPath(true);
        FileUtil.writeLine(filePath, "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\" %>");
        FileUtil.writeLine(filePath, "<% String tableName = \"" + entity.getTableComment() + "\";%>");
        FileUtil.writeLine(filePath, "<!DOCTYPE html>");
        FileUtil.writeLine(filePath, "<html>");
        FileUtil.writeLine(filePath, "<head>");
        FileUtil.writeLine(filePath, "    <title></title>");
        FileUtil.writeLine(filePath, "    <jsp:include page=\"/views/include/inc.jsp\"/>");
        FileUtil.writeLine(filePath, "    <script type=\"text/javascript\">");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "        var baseUrl = sysExt.apiHead" + StringUtil.toUpperCaseFirstOne(entity.getModular()) + " + '/" + entity.getUrlModule() + "';");
        FileUtil.writeLine(filePath, "        var pageTableName = '<%=tableName%>';");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "        var pageFormDataLoadCustom = function (data) {");
        FileUtil.writeLine(filePath, "        }");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "        var submitForm = function ($dialog, $grid, $pjq) {");
        FileUtil.writeLine(filePath, "            if ($('form').form('validate')) {");
        FileUtil.writeLine(filePath, "                disableToolbarButton(true);//禁用");
        FileUtil.writeLine(filePath, "                $.post(getUrl(), sysExt.serializeObject($('form')), function (result) {");
        FileUtil.writeLine(filePath, "                    if (result.success) {");
        FileUtil.writeLine(filePath, "                        unselectAllAndReload($grid, false);");
        FileUtil.writeLine(filePath, "                        $dialog.dialog('destroy');");
        FileUtil.writeLine(filePath, "                    } else {");
        FileUtil.writeLine(filePath, "                        messagerAlert(result);");
        FileUtil.writeLine(filePath, "                    }");
        FileUtil.writeLine(filePath, "                    disableToolbarButton(false);//启用");
        FileUtil.writeLine(filePath, "                }, 'json');");
        FileUtil.writeLine(filePath, "            }");
        FileUtil.writeLine(filePath, "        };");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "    </script>");
        FileUtil.writeLine(filePath, "    <jsp:include page=\"/views/include/incPageFormD.jsp\"/>");
        FileUtil.writeLine(filePath, "</head>");
        FileUtil.writeLine(filePath, "<body>");
        FileUtil.writeLine(filePath, "<form method=\"post\" class=\"formForm\">");
        FileUtil.writeLine(filePath, "    <table class=\"formTable\" style=\"width: 100%;\">");
        FileUtil.writeLine(filePath, "        <tr>");
        FileUtil.writeLine(filePath, "            <th><span class=\"tableTitle\"><%=tableName%></span></th>");
        FileUtil.writeLine(filePath, "            <td>");
        FileUtil.writeLine(filePath, "                <div class=\"lineStyle\"></div>");
        FileUtil.writeLine(filePath, "            </td>");
        FileUtil.writeLine(filePath, "        </tr>");
        FileUtil.writeLine(filePath, "        <tr id=\"primaryKeyTr\">");
        FileUtil.writeLine(filePath, "            <th>主键</th>");
        FileUtil.writeLine(filePath, "            <td>");
        FileUtil.writeLine(filePath, "                <a class=\"easyui-tooltip\" title=\"自动生成,无需编辑\" data-options=\"position:'bottom'\">");
        FileUtil.writeLine(filePath, "                    <input name=\"id\" value=\"${id}\" class=\"easyui-textbox\" data-options=\"readonly:true\"/>");
        FileUtil.writeLine(filePath, "                </a>");
        FileUtil.writeLine(filePath, "            </td>");
        FileUtil.writeLine(filePath, "        </tr>");


        // 主键/文本域
        for (FieldInfo f : fields) {
            String enName = f.getFieldName();
            String chsName = f.getColumnComments();
            String dateTypeShort = f.getTypeShort();
            boolean required = !f.getNullable();

            if (StringUtils.isBlank(chsName) || "Other".equals(f.getTypeShort()) || "id".equals(f.getFieldName()) || "createdId".equals(f.getFieldName()) || "updatedId".equals(f.getFieldName()) || "del".equals(f.getFieldName()))
                continue;

            if ("S".equals(dateTypeShort)) {
                if (f.getColumnLength() < 200) {
                    FileUtil.writeLine(filePath, "        <tr><th>" + chsName + "</th><td><input name=\"" + enName + "\" class=\"easyui-textbox\" data-options=\"required:" + required + ",validType:'length[0," + f.getColumnLength() + "]'\"/></td></tr>");//文本
                } else {
                    FileUtil.writeLine(filePath, "        <tr><th>" + chsName + "</th><td><input name=\"" + enName + "\" class=\"easyui-textbox\" data-options=\"required:" + required + ",multiline:true,validType:'length[0," + f.getColumnLength() + "]'\" style=\"height:100px\"/></td></tr>");//文本域
                }
            } else if ("D".equals(dateTypeShort)) {
                FileUtil.writeLine(filePath, "        <tr><th>" + chsName + "</th><td><input name=\"" + enName + "\" class=\"Wdate\" onclick=\"WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})\" /></td></tr>");//时间
            } else if (chsName.indexOf("排序") > -1 || enName.indexOf("order") > -1 || enName.indexOf("sort") > -1 || enName.indexOf("seq") > -1) {
                FileUtil.writeLine(filePath, "        <tr><th>" + chsName + "</th><td><input name=\"" + enName + "\" class=\"easyui-numberspinner\" value=\"100\" data-options=\"required:" + required + ",min:0,max:999999999,editable:false\" /></td></tr>");//排序
            } else if ("L".equals(dateTypeShort) || "I".equals(dateTypeShort) || "ST".equals(dateTypeShort)) {
                FileUtil.writeLine(filePath, "        <tr><th>" + chsName + "</th><td><input name=\"" + enName + "\" class=\"easyui-numberbox\" data-options=\"required:" + required + ",min:0,max:999999999\"/></td></tr>");//数字
            } else if ("BD".equals(dateTypeShort) || "FT".equals(dateTypeShort) || "DB".equals(dateTypeShort)) {
                FileUtil.writeLine(filePath, "        <tr><th>" + chsName + "</th><td><input name=\"" + enName + "\" class=\"easyui-numberbox\" data-options=\"required:" + required + ",precision:" + f.getColumnPrecision() + ",groupSeparator:',',decimalSeparator:'.',prefix:'',min:0,max:999999999\"/></td></tr>");//小数
            } else if ("BT".equals(dateTypeShort) || "BL".equals(dateTypeShort)) {
                if (isCombobox(chsName)) {
                    FileUtil.writeLine(filePath, "        <tr><th>" + chsName + "</th><td><input name=\"" + enName + "\" class=\"easyui-combobox\" data-options=\"data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',required:" + required + ",editable:false\"/></td></tr>");//状态
                } else {
                    FileUtil.writeLine(filePath, "        <tr><th>" + chsName + "</th><td><input name=\"" + enName + "\" class=\"easyui-numberbox\" data-options=\"required:" + required + ",min:0,max:999999999\"/></td></tr>");//数字
                }
            } else {
                FileUtil.writeLine(filePath, "        <tr><th>" + chsName + "</th><td><input name=\"" + enName + "\" class=\"easyui-textbox\" data-options=\"required:" + required + ",validType:'length[0," + f.getColumnLength() + "]'\"/></td></tr>");//文本
            }
        }

        FileUtil.writeLine(filePath, "    </table>");
        FileUtil.writeLine(filePath, "</form>");
        FileUtil.writeLine(filePath, "</body>");
        FileUtil.writeLine(filePath, "</html>");


        logger.info("{} 构建完毕！", entity.getPageFormPath(true));
    }


    private void bulidMapper(EntityInfo entity, List<FieldInfo> fields) {
        if (!FileUtil.isExist(entity.getMapperPath(false))) {
            FileUtil.makeDir(entity.getMapperPath(false));
        }
        if (entity.getHasMapper()) {
            return;
        }

        String filePath = entity.getMapperPath(true);

        FileUtil.writeLine(filePath, "package " + entity.getMapperPackage(false) + ";");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "import " + entity.getModelPackageToFull() + ";");
        FileUtil.writeLine(filePath, "import org.springframework.stereotype.Repository;");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "/**");
        FileUtil.writeLine(filePath, " * " + entity.getTableComment());
        FileUtil.writeLine(filePath, " * " + entity.getMapperName(false) + " component. @author lvtulife Tools");
        FileUtil.writeLine(filePath, " */");
        FileUtil.writeLine(filePath, "@Repository(\"" + entity.getEntityName() + "Mapper\")");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "public interface " + entity.getMapperName(false) + " {");
        FileUtil.writeLine(filePath, "    " + entity.getEntityName() + " find" + entity.getEntityName() + "(int id);");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "}");

        logger.info("{} 构建完毕！", entity.getMapperPath(true));
    }

    private void bulidMapXML(EntityInfo entity, List<FieldInfo> fields) {
        if (!FileUtil.isExist(entity.getMapXMLPath(false))) {
            FileUtil.makeDir(entity.getMapXMLPath(false));
        }
        if (entity.getHasMapXML()) {
            return;
        }

        String filePath = entity.getMapXMLPath(true);

        FileUtil.writeLine(filePath, "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        FileUtil.writeLine(filePath, "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        FileUtil.writeLine(filePath, "<!-- " + entity.getTableComment() + " Mybatis Dao实现 -->");
        FileUtil.writeLine(filePath, "<mapper namespace=\"" + entity.getMapperPackage(true) + "\">");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "    <select id=\"find" + entity.getEntityName() + "\" parameterType=\"int\" resultType=\"" + entity.getModelPackageToFull() + "\">");
        FileUtil.writeLine(filePath, "        select * from " + StringUtil.replaceFirstUpperToUnderline(entity.getEntityName()) + " where id=#{id}");
        FileUtil.writeLine(filePath, "    </select>");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "");
        FileUtil.writeLine(filePath, "</mapper>");

        logger.info("{} 构建完毕！", entity.getMapXMLPath(true));
    }


    /**
     * 查询资源是否存在，不存在则创建
     *
     * @param entity
     */
    private void bulidResource(EntityInfo entity) {
        boolean isExist = SysResourceService.isUrlExist(entity.getModular(), entity.getUrlModule(), "list");
        if (!isExist) {
            SysResource res = new SysResource();
            res.setTypeId(1);
            res.setPid(5);
            res.setResAttr(SysConstants.SYS_RES_ATTR_MENU);
            res.setResName(entity.getTableComment());
            res.setUrlHead(entity.getModular());
            res.setUrlModule(entity.getUrlModule());
            res.setUrlMethod("list");
            res.setBewrite(null);
            res.setIconCls("ext-icon-bullet_wrench");
            res.setTarget(null);
            res.setResLevel(Byte.parseByte("1"));
            res.setResLeaf(SysConstants.SYS_NO);
            res.setIsAuthPoint(SysConstants.SYS_YES);
            res.setSort(10);
            res.setCreatedDt(new Date());
            res.setUpdatedDt(new Date());
            res.setDel(SysConstants.SYS_STATUS);
            SysResourceService.save(res);

            logger.info("资源路径 数据库新增完毕！");
        }
    }


    /**
     * 依据类型匹配查询条件，如字符查询时使用like,整数类型使用等于
     *
     * @param typeShort
     * @return
     */
    private String getOperators(String typeShort) {
        String operators = "EQ";
        if (StringUtils.equalsIgnoreCase(typeShort, "S"))
            operators = "LK";
        if (StringUtils.equalsIgnoreCase(typeShort, "L"))
            operators = "EQ";
        if (StringUtils.equalsIgnoreCase(typeShort, "I"))
            operators = "EQ";
        if (StringUtils.equalsIgnoreCase(typeShort, "D"))
            operators = "EQ";
        if (StringUtils.equalsIgnoreCase(typeShort, "ST"))
            operators = "EQ";
        if (StringUtils.equalsIgnoreCase(typeShort, "BD"))
            operators = "EQ";
        if (StringUtils.equalsIgnoreCase(typeShort, "FT"))
            operators = "EQ";
        if (StringUtils.equalsIgnoreCase(typeShort, "BT"))
            operators = "EQ";
        if (StringUtils.equalsIgnoreCase(typeShort, "DB"))
            operators = "EQ";
        return operators;
    }

    /**
     * 判断是否为可下拉的选项
     *
     * @param chsName
     * @return
     */
    private boolean isCombobox(String chsName) {
        if (chsName.indexOf("状态") > -1 || chsName.indexOf("是否") > -1 || chsName.indexOf("类型") > -1 || chsName.indexOf("类别") > -1
                || chsName.indexOf("单位") > -1 || chsName.indexOf("性别") > -1 || chsName.indexOf("星标") > -1 || chsName.indexOf("置顶") > -1
                || chsName.indexOf("时段") > -1 || chsName.indexOf("来源") > -1 || chsName.indexOf("方式") > -1 || chsName.indexOf("难度") > -1
                || chsName.indexOf("重复") > -1 || chsName.indexOf("阶段") > -1 || chsName.indexOf("属性") > -1 || chsName.indexOf("级别") > -1
                ) {
            return true;
        }
        return false;
    }
}
