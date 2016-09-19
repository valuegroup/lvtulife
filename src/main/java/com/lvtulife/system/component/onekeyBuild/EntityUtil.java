package com.lvtulife.system.component.onekeyBuild;

import java.io.File;
import java.util.*;

import com.lvtulife.base.utils.FileUtil;
import com.lvtulife.base.utils.StringUtil;

public class EntityUtil {
    // main函数

    public static void main(String[] args) throws Exception {
        String entityName = "BaseDemo";
        String modular = "base";
        /*String projectPath = "F:\\IdeaProjects\\lvtulife-paas\\src\\main\\java\\";
        String daoTemplate = "com.lvtulife.#MODULAR#.dao.#ENTITY_NAME#DaoI#DOT#java";
        String daoImplTemplate = "com.lvtulife.#MODULAR#.dao.impl.#ENTITY_NAME#DaoImpl#DOT#java";
        String serviceTemplate = "com.lvtulife.#MODULAR#.service.#ENTITY_NAME#ServiceI#DOT#java";
        String serviceImplTemplate = "com.lvtulife.#MODULAR#.service.impl.#ENTITY_NAME#ServiceImpl#DOT#java";
        String controllerTemplate = "com.lvtulife.#MODULAR#.controller.#ENTITY_NAME#Controller#DOT#java";
        String controllerApiTemplate = "com.lvtulife.#MODULAR#.controller.api.#ENTITY_NAME#ApiController#DOT#java";*/

        /*System.out.println(getFilePath(DAOTEMPLATE, modular, entityName, true));
        System.out.println(getFilePath(DAOTEMPLATE, modular, entityName, false));
        System.out.println(getPackage(DAOTEMPLATE, modular, entityName, false));
        System.out.println(getPackage(DAOTEMPLATE, modular, entityName, true));
        System.out.println(getFileName(DAOTEMPLATE, entityName, false));
        System.out.println(getFileName(DAOTEMPLATE, entityName, true));*/
    }


    // 读取dev.properties 配置文件,获取各模块基础路径
    private static final ResourceBundle bundle = PropertyResourceBundle.getBundle("system");
    private static final String PROJECTPATH = "oneKeyBuild.projectPath";
    private static final String DAOTEMPLATE = "oneKeyBuild.daoTemplate";
    private static final String DAOIMPLTEMPLATE = "oneKeyBuild.daoImplTemplate";
    private static final String SERVICETEMPLATE = "oneKeyBuild.serviceTemplate";
    private static final String SERVICEIMPLTEMPLATE = "oneKeyBuild.serviceImplTemplate";
    private static final String CONTROLLERTEMPLATE = "oneKeyBuild.controllerTemplate";
    private static final String CONTROLLERAPITEMPLATE = "oneKeyBuild.controllerApiTemplate";
    private static final String MODELTEMPLATE = "oneKeyBuild.modelTemplate";
    private static final String MODULENAME = "oneKeyBuild.moduleName";
    private static final String PAGELIST = "oneKeyBuild.pageList";
    private static final String PAGEFORM = "oneKeyBuild.pageForm";
    private static final String MAPPERTEMPLATE = "oneKeyBuild.mapperTemplate";
    private static final String MAPXMLTEMPLATE = "oneKeyBuild.mapXMLTemplate";

    private static final String ENTITY_NAME = "#ENTITY_NAME#";
    private static final String DOT = "#DOT#";
    private static final String MODULAR = "#MODULAR#";


    public static List<EntityInfo> getAllEntityInfo() {
        List<EntityInfo> entitys = new ArrayList<EntityInfo>();
        List<String[]> pojoFileList = readPojoFileList();

        HashMap<String, String> tableNames = ClassProperty.getTableMapForDB();

        for (String[] string : pojoFileList) {
            //0:包含文件名的文件路径，1:文件所在路径，2:文件名带后缀名，3:文件名,4:路径序号
            EntityInfo entity = new EntityInfo(string[3], EntityUtil.readProperties(MODULENAME)[Integer.parseInt(string[4])]);
            entity.setTableComment(tableNames.get(entity.getTableName()));
            entitys.add(entity);
        }
        return entitys;
    }

    public static String[] readProperties(String key) {
        return readPropertie(key).split("@");
    }

    public static String readPropertie(String key) {
        return bundle.getString(key);
    }

    public static List<String[]> readPojoFileList() {
        // 获取所以的hibernate文件夹下的对象
        String[] pojoPaths = readProperties(MODULENAME);

        List<String[]> pojoFileList = new ArrayList<String[]>();
        for (int i = 0; i < pojoPaths.length; i++) {
            String path = EntityUtil.getModelPath(pojoPaths[i]);
            List<String[]> plist = FileUtil.getFileListByDirName(path, String.valueOf(i));
            if (plist != null) {
                for (String[] pName : plist) {
                    pojoFileList.add(pName);
                }
            }
        }
        return pojoFileList;
    }


    public static String[] getAllFileName(String entityName, boolean isFull) {
        String[] names = new String[10];
        names[0] = EntityUtil.getFileName(DAOTEMPLATE, entityName, isFull);
        names[1] = EntityUtil.getFileName(DAOIMPLTEMPLATE, entityName, isFull);
        names[2] = EntityUtil.getFileName(SERVICETEMPLATE, entityName, isFull);
        names[3] = EntityUtil.getFileName(SERVICEIMPLTEMPLATE, entityName, isFull);
        names[4] = EntityUtil.getFileName(CONTROLLERTEMPLATE, entityName, isFull);
        names[5] = EntityUtil.getFileName(CONTROLLERAPITEMPLATE, entityName, isFull);
        names[6] = EntityUtil.getFileName(PAGELIST, entityName, isFull);
        names[7] = EntityUtil.getFileName(PAGEFORM, entityName, isFull);
        names[8] = EntityUtil.getFileName(MAPPERTEMPLATE, entityName, isFull);
        names[9] = EntityUtil.getFileName(MAPXMLTEMPLATE, entityName, isFull);
        return names;
    }

    public static String[] getAllFilePath(String modular, String entityName, boolean isFull) {
        String[] names = new String[10];
        names[0] = EntityUtil.getFilePath(DAOTEMPLATE, modular, entityName, isFull);
        names[1] = EntityUtil.getFilePath(DAOIMPLTEMPLATE, modular, entityName, isFull);
        names[2] = EntityUtil.getFilePath(SERVICETEMPLATE, modular, entityName, isFull);
        names[3] = EntityUtil.getFilePath(SERVICEIMPLTEMPLATE, modular, entityName, isFull);
        names[4] = EntityUtil.getFilePath(CONTROLLERTEMPLATE, modular, entityName, isFull);
        names[5] = EntityUtil.getFilePath(CONTROLLERAPITEMPLATE, modular, entityName, isFull);
        names[6] = EntityUtil.getFilePath(PAGELIST, modular, entityName, isFull);
        names[7] = EntityUtil.getFilePath(PAGEFORM, modular, entityName, isFull);
        names[8] = EntityUtil.getFilePath(MAPPERTEMPLATE, modular, entityName, isFull);
        names[9] = EntityUtil.getFilePath(MAPXMLTEMPLATE, modular, entityName, isFull);
        return names;
    }

    public static String[] getAllPackage(String modular, String entityName, boolean isFull) {
        String[] names = new String[7];
        names[0] = EntityUtil.getPackage(DAOTEMPLATE, modular, entityName, isFull);
        names[1] = EntityUtil.getPackage(DAOIMPLTEMPLATE, modular, entityName, isFull);
        names[2] = EntityUtil.getPackage(SERVICETEMPLATE, modular, entityName, isFull);
        names[3] = EntityUtil.getPackage(SERVICEIMPLTEMPLATE, modular, entityName, isFull);
        names[4] = EntityUtil.getPackage(CONTROLLERTEMPLATE, modular, entityName, isFull);
        names[5] = EntityUtil.getPackage(CONTROLLERAPITEMPLATE, modular, entityName, isFull);
        names[6] = EntityUtil.getPackage(MAPPERTEMPLATE, modular, entityName, isFull);
        return names;
    }

    public static String getFileName(String templateKey, String entityName, boolean isFull) {
        String template = bundle.getString(templateKey);

        // 页面需特殊处理
        if (templateKey.equals(PAGELIST) || templateKey.equals(PAGEFORM)) {
            entityName = StringUtil.toLowerCaseFirstOne(entityName);
        }

        if (isFull)
            //BaseDemoDaoI.java
            return template.substring(template.indexOf(ENTITY_NAME)).replace(ENTITY_NAME, entityName).replace(DOT, ".");
        else
            //BaseDemoDaoI
            return template.substring(template.indexOf(ENTITY_NAME), template.indexOf(DOT)).replace(ENTITY_NAME, entityName);
    }

    public static String getFilePath(String templateKey, String modular, String entityName, boolean isFull) {
        String template = bundle.getString(templateKey);
        String projectPath = bundle.getString(PROJECTPATH).trim();

        // 页面需特殊处理
        if (templateKey.equals(PAGELIST) || templateKey.equals(PAGEFORM)) {
            projectPath = projectPath.replace("java", "webapp");
            entityName = StringUtil.toLowerCaseFirstOne(entityName);
        }

        if (isFull)
            // F:\IdeaProjects\lvtulife-paas\src\main\java\com\lvtulife\base\dao\BaseDemoDaoI.java
            return projectPath + (template.replace(MODULAR, modular).replace(ENTITY_NAME, entityName)).replace(".", File.separator).replace(DOT, ".");
        else
            // F:\IdeaProjects\lvtulife-paas\src\main\java\com\lvtulife\base\dao
            return projectPath + (template.substring(0, template.indexOf("." + ENTITY_NAME)).replace(MODULAR, modular)).replace(".", File.separator).replace(DOT, ".");
    }

    public static String getPackage(String templateKey, String modular, String entityName, boolean isFull) {
        String template = bundle.getString(templateKey);
        if (isFull)
            // com.lvtulife.base.dao.BaseDemoDaoI
            return template.substring(0,template.indexOf(DOT)).replace(MODULAR, modular).replace(ENTITY_NAME, entityName);
        else
            // com.lvtulife.base.dao
            return template.substring(0, template.indexOf("." + ENTITY_NAME)).replace(MODULAR, modular);
    }

    public static String getModelPath(String modular) {
        String projectPath = bundle.getString(PROJECTPATH).trim();
        return (projectPath + getModelPackage(modular)).replace(".", File.separator);
    }

    public static String getModelPackage(String modular) {
        String template = bundle.getString(MODELTEMPLATE);
        return template.replace(MODULAR, modular);
    }


    public static String capitalToUpper(String str) {
        if (str == null || "".equals(str.trim()))
            return str;
        return String.valueOf(Character.toUpperCase(str.charAt(0))) + str.substring(1);
    }
}
