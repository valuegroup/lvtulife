package com.lvtulife.system.component.onekeyBuild;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.utils.FileUtil;
import com.lvtulife.base.utils.StringUtil;

public class EntityInfo {
    private String tableComment;
    private String modular;
    private String entityName;
    private String tableName;
    private String urlModule;
    private Boolean hasDao;
    private Boolean hasDaoImpl;
    private Boolean hasService;
    private Boolean hasServiceImpl;
    private Boolean hasController;
    private Boolean hasApiController;
    private Boolean hasPageList;
    private Boolean hasPageForm;
    private Boolean hasMapper;
    private Boolean hasMapXML;
    private Boolean hasMenuResource;

    private String[] fileNames;
    private String[] fileNamesToFull;
    private String[] filePaths;
    private String[] filePathsToFull;
    private String[] filePackages;
    private String[] filePackagesToFull;
    private String modelPath;
    private String modelPathToFull;
    private String modelPackage;
    private String modelPackageToFull;


    public static void main(String[] args) {
        EntityInfo entityInfo = new EntityInfo("SysUser", "system");
    }

    public EntityInfo(String entityName, String modular) {

        this.entityName = entityName;
        this.modular = modular;

        // 组件名称
        fileNames = EntityUtil.getAllFileName(entityName, false);
        fileNamesToFull = EntityUtil.getAllFileName(entityName, true);
        // 组件路径
        filePaths = EntityUtil.getAllFilePath(modular, entityName, false);
        filePathsToFull = EntityUtil.getAllFilePath(modular, entityName, true);
        // 组件包
        filePackages = EntityUtil.getAllPackage(modular, entityName, false);
        filePackagesToFull = EntityUtil.getAllPackage(modular, entityName, true);

        this.hasDao = FileUtil.isExist(getDaoPath(true));
        this.hasDaoImpl = FileUtil.isExist(getDaoImplPath(true));
        this.hasService = FileUtil.isExist(getServicePath(true));
        this.hasServiceImpl = FileUtil.isExist(getServiceImplPath(true));
        this.hasController = FileUtil.isExist(getControllerPath(true));
        this.hasApiController = FileUtil.isExist(getControllerApiPath(true));
        this.hasPageList = FileUtil.isExist(getPageListPath(true));
        this.hasPageForm = FileUtil.isExist(getPageFormPath(true));
        this.hasMapper = FileUtil.isExist(getMapperPath(true));
        this.hasMapXML = FileUtil.isExist(getMapXMLPath(true));

        this.modelPath = EntityUtil.getModelPath(modular);
        this.modelPathToFull = modelPath + entityName + ".java";
        this.modelPackage = EntityUtil.getModelPackage(modular);
        this.modelPackageToFull = modelPackage + "." + entityName;

        this.tableName = ClassProperty.getTableName(modelPackageToFull);
        this.urlModule = StringUtil.replaceUnderlineAndFirstToUpper(tableName.substring(tableName.indexOf("_") + 1));
    }


    public String getDaoName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[0];
        } else {
            return fileNames[0];
        }
    }

    public String getDaoImplName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[1];
        } else {
            return fileNames[1];
        }
    }

    public String getServiceName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[2];
        } else {
            return fileNames[2];
        }
    }

    public String getServiceImplName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[3];
        } else {
            return fileNames[3];
        }
    }

    public String getControllerName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[4];
        } else {
            return fileNames[4];
        }
    }

    public String getControllerApiName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[5];
        } else {
            return fileNames[5];
        }
    }

    public String getPageListName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[6];
        } else {
            return fileNames[6];
        }
    }

    public String getPageFormName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[7];
        } else {
            return fileNames[7];
        }
    }

    public String getMapperName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[8];
        } else {
            return fileNames[8];
        }
    }

    public String getMapXMLName(boolean isFull) {
        if (isFull) {
            return fileNamesToFull[9];
        } else {
            return fileNames[9];
        }
    }

    public String getDaoPath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[0];
        } else {
            return filePaths[0];
        }
    }

    public String getDaoImplPath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[1];
        } else {
            return filePaths[1];
        }
    }

    public String getServicePath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[2];
        } else {
            return filePaths[2];
        }
    }

    public String getServiceImplPath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[3];
        } else {
            return filePaths[3];
        }
    }

    public String getControllerPath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[4];
        } else {
            return filePaths[4];
        }
    }

    public String getControllerApiPath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[5];
        } else {
            return filePaths[5];
        }
    }

    public String getPageListPath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[6];
        } else {
            return filePaths[6];
        }
    }

    public String getPageFormPath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[7];
        } else {
            return filePaths[7];
        }
    }

    public String getMapperPath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[8];
        } else {
            return filePaths[8];
        }
    }

    public String getMapXMLPath(boolean isFull) {
        if (isFull) {
            return filePathsToFull[9];
        } else {
            return filePaths[9];
        }
    }

    public String getDaoPackage(boolean isFull) {
        if (isFull) {
            return filePackagesToFull[0];
        } else {
            return filePackages[0];
        }
    }

    public String getDaoImplPackage(boolean isFull) {
        if (isFull) {
            return filePackagesToFull[1];
        } else {
            return filePackages[1];
        }
    }

    public String getServicePackage(boolean isFull) {
        if (isFull) {
            return filePackagesToFull[2];
        } else {
            return filePackages[2];
        }
    }

    public String getServiceImplPackage(boolean isFull) {
        if (isFull) {
            return filePackagesToFull[3];
        } else {
            return filePackages[3];
        }
    }

    public String getControllerPackage(boolean isFull) {
        if (isFull) {
            return filePackagesToFull[4];
        } else {
            return filePackages[4];
        }
    }

    public String getControllerApiPackage(boolean isFull) {
        if (isFull) {
            return filePackagesToFull[5];
        } else {
            return filePackages[5];
        }
    }

    public String getMapperPackage(boolean isFull) {
        if (isFull) {
            return filePackagesToFull[6];
        } else {
            return filePackages[6];
        }
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public String getModelPath() {
        return modelPath;
    }

    public String getModelPathToFull() {
        return modelPathToFull;
    }

    public String getModelPackageToFull() {
        return modelPackageToFull;
    }


    private Byte isExist(String path) {
        return FileUtil.isExist(path) ? SysConstants.SYS_YES : SysConstants.SYS_NO;
    }


    public String getModular() {
        return modular;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUrlModule() {
        return urlModule;
    }

    public void setUrlModule(String urlModule) {
        this.urlModule = urlModule;
    }

    public Boolean getHasDao() {
        return hasDao;
    }

    public Boolean getHasApiController() {
        return hasApiController;
    }

    public Boolean getHasDaoImpl() {
        return hasDaoImpl;
    }

    public Boolean getHasController() {
        return hasController;
    }

    public Boolean getHasService() {
        return hasService;
    }

    public Boolean getHasServiceImpl() {
        return hasServiceImpl;
    }

    public Boolean getHasPageForm() {
        return hasPageForm;
    }

    public Boolean getHasPageList() {
        return hasPageList;
    }

    public String getTableComment() {
        return tableComment;
    }

    public Boolean getHasMenuResource() {
        return hasMenuResource;
    }

    public Boolean getHasMapper() {
        return hasMapper;
    }

    public Boolean getHasMapXML() {
        return hasMapXML;
    }

    public void setHasMenuResource(Boolean hasMenuResource) {
        this.hasMenuResource = hasMenuResource;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}

