package com.lvtulife.base.component.constants;

/**
 * 系统逻辑判断常量
 */
public class SysConstants {

    /***********************/
    /*******系统常量**********/
    /***********************/

    //服务器部署路径:[ROOTPATH]
    public static String ROOTPATH = "";
    //为开发环境和测试环境设置的windows上ROOTPATH路径:[DEV_ROOTPATH]
    public static String DEV_ROOTPATH = "";

    //内部使用 在各controller的@RequestMapping中使用，以便统一控制
    public final static String URL_HEAD_BASE = "/base";
    public final static String URL_HEAD_SYSTEM = "/system";
    public final static String URL_HEAD_GTD = "/gtd";

    //外部使用 在各controller.api的@RequestMapping中使用，对接口提供控制(建议按包名分，以后方便查询)
    public final static String API_HEAD = "/api";
    public final static String API_HEAD_BASE = API_HEAD + URL_HEAD_BASE;
    public final static String API_HEAD_SYSTEM = API_HEAD + URL_HEAD_SYSTEM;
    public final static String API_HEAD_GTD = API_HEAD + URL_HEAD_GTD;

    //指定各模块下JSP的根目录
    public final static String PATH_ROOT = "/views";
    public final static String PATH_ERROR = "/views/error";
    public final static String PATH_BASE = PATH_ROOT + URL_HEAD_BASE;

    // 系统内置用户角色标识
    public final static String ROLE_ROOT = "ROLE_ROOT";
    public final static String ROLE_USER = "ROLE_USER";
    public final static String ROLE_API = "ROLE_API";

    // 系统权限点即对应请求方法
    public final static Integer AUTH_POINT_FIND_VALUE = 100000;
    public final static Integer AUTH_POINT_SAVE_VALUE = 200000;
    public final static Integer AUTH_POINT_EDIT_VALUE = 300000;
    public final static Integer AUTH_POINT_REMOVE_VALUE = 400000;

    //查阅数据  [功能]
    public final static String AUTH_POINT_FIND_GETCOMBOBOX = "getCombobox", URL_GETCOMBOBOX = "/getCombobox";
    public final static String AUTH_POINT_FIND_FORM = "form", URL_FORM = "/form";
    public final static String AUTH_POINT_FIND_LIST = "list", URL_LIST = "/list";
    public final static String AUTH_POINT_FIND_GETBYID = "getById", URL_GETBYID = "/getById";
    public final static String AUTH_POINT_FIND_FIND = "find", URL_FIND = "/find";
    public final static String AUTH_POINT_FIND_FINDALL = "findAll", URL_FINDALL = "/findAll";
    public final static String AUTH_POINT_FIND_GRID = "grid", URL_GRID = "/grid";
    public final static String AUTH_POINT_FIND_GRIDALL = "gridAll", URL_GRIDALL = "/gridAll";
    public final static String AUTH_POINT_FIND_TREEGRID = "treeGrid", URL_TREEGRID = "/treeGrid";
    //新增数据  [功能]
    public final static String AUTH_POINT_SAVE_SAVE = "save", URL_SAVE = "/save";
    //编辑数据  [功能]
    public final static String AUTH_POINT_EDIT_UPDATE = "update", URL_UPDATE = "/update";
    //删除数据  [功能]
    public final static String AUTH_POINT_REMOVE_DELETE = "delete", URL_DELETE = "/delete";
    public final static String AUTH_POINT_REMOVE_DELETES = "deletes", URL_DELETES = "/deletes";


    //(批量新增和批量更新时)多少个数量级,flush一次
    public static final Integer FLUSH_CRITICAL_VAL = 100;
    //(批量新增和批量更新时)大数量级,flush一次
    public static final Integer FLUSH_BIG_CRITICAL_VAL = 99999;

    /**********************/
    /****业务常用参数常量*****/
    /**********************/

    //数据状态:正常状态[SYS_STATUS][0]
    public final static byte SYS_STATUS = 0;// 正常状态
    //数据状态:正常状态[SYS_STATUS_DELETE][-1]
    public final static byte SYS_STATUS_DELETE = -1;// 删除状态

    //启用状态:启用[SYS_ENABLE][1]
    public final static byte SYS_ENABLE = 1;
    //启用状态:禁用[SYS_DISABLED][0]
    public final static byte SYS_DISABLED = 0;

    //是否状态:是[SYS_YES][1]
    public final static byte SYS_YES = 1;
    //是否状态:否[SYS_NO][0]
    public final static byte SYS_NO = 0;

    //打开状态:开启[SYS_OPEN][1]
    public final static byte SYS_OPEN = 1;
    //打开状态:关闭[SYS_CLOSE][0]
    public final static byte SYS_CLOSE = 0;

    //数据来源:用户设定[SYS_INUSER][1]
    public final static byte SYS_INUSER = 1;
    //数据来源:系统内置[SYS_INSYS][0]
    public final static byte SYS_INSYS = 0;

    //用户状态:正常[SYS_USTATUS][0]
    public final static byte SYS_USTATUS = 0;
    //用户状态:禁用[SYS_USTATUS_BAN][9]
    public final static byte SYS_USTATUS_BAN = 9;

    //角色状态:正常[SYS_RSTATUS][0]
    public final static byte SYS_RSTATUS = 0;
    //角色状态:禁用[SYS_RSTATUS_BAN][9]
    public final static byte SYS_RSTATUS_BAN = 9;

    //登录类型:注销[SYS_RSTATUS][0]
    public final static byte SYS_OLTYPE_OUT = 0;
    //登录类型:登录[SYS_RSTATUS_BAN][9]
    public final static byte SYS_OLTYPE_IN = 1;

    //性别:女性[SYS_SEX_FEMALE][0]
    public final static byte SYS_SEX_FEMALE = 0;
    //性别:男性[SYS_SEX_MALE][1]
    public final static byte SYS_SEX_MALE = 1;

    //资源属性:菜单[SYS_RES_ATTR_MENU][0]
    public final static byte SYS_RES_ATTR_MENU = 0;
    //资源属性:功能[SYS_RES_ATTR_FUN][1]
    public final static byte SYS_RES_ATTR_FUN = 1;

    //日志类型:操作[SYS_LOG_TYPE_OP][0]
    public final static byte SYS_LOG_TYPE_OP = 0;
    //日志类型:异常[SYS_LOG_TYPE_ERROR][1]
    public final static byte SYS_LOG_TYPE_ERROR = 1;


    /**************************/
    /*****仓储管理业务参数常量*****/
    /**************************/

    //自动编码类型:业务编号[WMS_AUTO_CODE_TYPE_BUSINESS][1]
    public final static byte WMS_AUTO_CODE_TYPE_BUSINESS = 1;
    //自动编码类型:工作号[WMS_AUTO_CODE_TYPE_WORK][0]
    public final static byte WMS_AUTO_CODE_TYPE_WORK = 0;


    //产地:大陆[WMS_BRAND_ORIGIN_DL][1]
    public final static byte WMS_BRAND_ORIGIN_DL = 1;
    //产地:台湾[WMS_BRAND_ORIGIN_TW][2]
    public final static byte WMS_BRAND_ORIGIN_TW = 2;
    //产地:欧美[WMS_BRAND_ORIGIN_OM][3]
    public final static byte WMS_BRAND_ORIGIN_OM = 3;
    //产地:日韩[WMS_BRAND_ORIGIN_RH][4]
    public final static byte WMS_BRAND_ORIGIN_RH = 4;


    //入库类型:普通入库[WMS_IN_TYPE_PTRK][1]
    public final static byte WMS_IN_TYPE_PTRK = 1;
    //入库类型:本库转让[WMS_IN_TYPE_BKZR][2]
    public final static byte WMS_IN_TYPE_BKZR = 2;
    //入库类型:库组转让[WMS_IN_TYPE_KZZR][3]
    public final static byte WMS_IN_TYPE_KZZR = 3;
    //入库类型:区内转入[WMS_IN_TYPE_QNZR][4]
    public final static byte WMS_IN_TYPE_QNZR = 4;
    //入库类型:临时入库[WMS_IN_TYPE_LSRK][5]
    public final static byte WMS_IN_TYPE_LSRK = 5;
    //入库类型:其他入库[WMS_IN_TYPE_QTRK][6]
    public final static byte WMS_IN_TYPE_QTRK = 6;
    //入库类型:退货入库[WMS_IN_TYPE_THRK][7]
    public final static byte WMS_IN_TYPE_THRK = 7;
    //入库类型:换货入库[WMS_IN_TYPE_HHRK][8]
    public final static byte WMS_IN_TYPE_HHRK = 8;

    //入库状态:新建[WMS_IN_STAT_NEW][0]
    public final static byte WMS_IN_STAT_NEW = 0;
    //入库状态:已创建[WMS_IN_STAT_CREATE][1]
    public final static byte WMS_IN_STAT_CREATE = 1;
    //入库类型:已确认[WMS_IN_STAT_AFFIRM][2]
    public final static byte WMS_IN_STAT_AFFIRM = 2;
    //入库类型:已作废[WMS_IN_STAT_CANCEL][3]
    public final static byte WMS_IN_STAT_CANCEL = 3;

    //日期类型:入库日期[WMS_DAY_TYPE_IN][1]
    public final static byte WMS_DAY_TYPE_IN = 1;
    //日期类型:到货日期[WMS_DAY_TYPE_ARRIVE][2]
    public final static byte WMS_DAY_TYPE_ARRIVE = 2;
    //日期类型:确认日期[WMS_DAY_TYPE_AFFRIM][3]
    public final static byte WMS_DAY_TYPE_AFFRIM = 3;
    //日期类型:入库日期[WMS_DAY_TYPE_CREATE][4]
    public final static byte WMS_DAY_TYPE_CREATE = 4;


    /**************************/
    /*******GTD业务参数常量******/
    /**************************/

    //星标:是[GTD_STAR_YES][1]
    public final static byte GTD_STAR_YES = 1;
    //星标:否[GTD_STAR_NO][0]
    public final static byte GTD_STAR_NO = 0;


    //置顶:是[GTD_TOP_YES][1]
    public final static byte GTD_TOP_YES = 1;
    //置顶:否[GTD_TOP_NO][0]
    public final static byte GTD_TOP_NO = 0;


    //加密:加密[GTD_ENCRYPT_YES][1]
    public final static byte GTD_ENCRYPT_YES = 1;
    //加密:不加密[GTD_ENCRYPT_NO][0]
    public final static byte GTD_ENCRYPT_NO = 0;


    //阶段:第一阶段[GTD_STAGE_1][1]
    public final static byte GTD_STAGE_1 = 1;
    //阶段:第二阶段[GTD_STAGE_2][2]
    public final static byte GTD_STAGE_2 = 2;
    //阶段:第三阶段[GTD_STAGE_3][3]
    public final static byte GTD_STAGE_3 = 3;
    //阶段:第四阶段[GTD_STAGE_4][4]
    public final static byte GTD_STAGE_4 = 4;


    //公开:是[GTD_OPEN_YES][1]
    public final static byte GTD_OPEN_YES = 1;
    //公开:否[GTD_OPEN_NO][0]
    public final static byte GTD_OPEN_NO = 0;


    //理想状态:未达成[GTD_IDEAL_WAIT][0]
    public final static byte GTD_IDEAL_WAIT = 0;
    //理想状态:已达成[GTD_IDEAL_COMPLETED][1]
    public final static byte GTD_IDEAL_COMPLETED = 1;
    //理想状态:作废[GTD_IDEAL_CANCEL][9]
    public final static byte GTD_IDEAL_CANCEL = 9;

    //目标状态:未达成[GTD_AIM_WAIT][0]
    public final static byte GTD_AIM_WAIT = 0;
    //目标状态:已达成[GTD_AIM_COMPLETED][1]
    public final static byte GTD_AIM_COMPLETED = 1;
    //目标状态:作废[GTD_AIM_CANCEL][9]
    public final static byte GTD_AIM_CANCEL = 9;

    //事务状态:未达成[GTD_AFFAIRS_WAIT][0]
    public final static byte GTD_AFFAIRS_WAIT = 0;
    //事务状态:已达成[GTD_AFFAIRS_COMPLETED][1]
    public final static byte GTD_AFFAIRS_COMPLETED = 1;
    //事务状态:作废[GTD_AFFAIRS_CANCEL][9]
    public final static byte GTD_AFFAIRS_CANCEL = 9;

    //划分单位:单元[GTD_AIM_UNIT][0]
    public final static byte GTD_AIM_UNIT = 0;
    //划分单位:月度[GTD_AIM_UNIT_MONTH][1]
    public final static byte GTD_AIM_UNIT_MONTH = 1;
    //划分单位:季度[GTD_AIM_UNIT_SEASON][2]
    public final static byte GTD_AIM_UNIT_SEASON = 2;
    //划分单位:年度[GTD_AIM_UNIT_YEAR][3]
    public final static byte GTD_AIM_UNIT_YEAR = 3;


    //奖励类别:实物[GTD_REWARD][1]
    public final static byte GTD_REWARD = 1;
    //奖励类别:虚拟[GTD_REWARD_INVENTED][0]
    public final static byte GTD_REWARD_INVENTED = 0;


    //实施状态:已实施[GTD_DO_YES][1]
    public final static byte GTD_DO_YES = 1;
    //实施状态:未实施[GTD_DO_NO][0]
    public final static byte GTD_DO_NO = 0;


    //重复:一次性活动[GTD_REPEAT_DISPOSABLE][0]
    public final static byte GTD_REPEAT_DISPOSABLE = 0;
    //重复:每天[GTD_REPEAT_DAY][1]
    public final static byte GTD_REPEAT_DAY = 1;
    //重复:每个工作日[GTD_REPEAT_WORK_DAY][2]
    public final static byte GTD_REPEAT_WORK_DAY = 2;
    //重复:每周[GTD_REPEAT_WEEK][3]
    public final static byte GTD_REPEAT_WEEK = 3;
    //重复:每月[GTD_REPEAT_MONTH][4]
    public final static byte GTD_REPEAT_MONTH = 4;
    //重复:每年[GTD_REPEAT_YEAR][5]
    public final static byte GTD_REPEAT_YEAR = 5;
    //重复:每年农历[GTD_REPEAT_LUNAR][6]
    public final static byte GTD_REPEAT_LUNAR = 6;


    //重复条件:无重复[GTD_REPEAT_TERM_NO][0]
    public final static byte GTD_REPEAT_TERM_NO = 0;
    //重复条件:无限重复[GTD_REPEAT_TERM_INFINITE][1]
    public final static byte GTD_REPEAT_TERM_INFINITE = 1;
    //重复条件:截止至某个日期[GTD_REPEAT_TERM_DAY][2]
    public final static byte GTD_REPEAT_TERM_DAY = 2;
    //重复条件:限定次数[GTD_REPEAT_TERM_COUNT][3]
    public final static byte GTD_REPEAT_TERM_COUNT = 3;

    //日历类型:公历[GTD_CALENDAR][0]
    public final static byte GTD_CALENDAR = 0;
    //日历类型:农历[GTD_CALENDAR_LUNAR][1]
    public final static byte GTD_CALENDAR_LUNAR = 1;


    //时段:早晨[GTD_TIME_INTERVAL_MORNING][0]
    public final static byte GTD_TIME_INTERVAL_MORNING = 0;
    //时段:上午[GTD_TIME_INTERVAL_AM][1]
    public final static byte GTD_TIME_INTERVAL_AM = 1;
    //时段:中午[GTD_TIME_INTERVAL_NOONTIME][2]
    public final static byte GTD_TIME_INTERVAL_NOONTIME = 2;
    //时段:下午[GTD_TIME_INTERVAL_PM][3]
    public final static byte GTD_TIME_INTERVAL_PM = 3;
    //时段:傍晚[GTD_TIME_INTERVAL_NIGHTFALL][4]
    public final static byte GTD_TIME_INTERVAL_NIGHTFALL = 4;
    //时段:晚间[GTD_TIME_INTERVAL_EVENING][5]
    public final static byte GTD_TIME_INTERVAL_EVENING = 5;
    //时段:凌晨[GTD_TIME_INTERVAL_DAWN][6]
    public final static byte GTD_TIME_INTERVAL_DAWN = 6;


    //事务来源:目标[GTD_AFFAIRS_SOURCE_AIM][0]
    public final static byte GTD_AFFAIRS_SOURCE_AIM = 0;
    //事务来源:习惯[GTD_AFFAIRS_SOURCE_HABIT][1]
    public final static byte GTD_AFFAIRS_SOURCE_HABIT = 1;
    //事务来源:日历[GTD_AFFAIRS_SOURCE_CALENDAR][2]
    public final static byte GTD_AFFAIRS_SOURCE_CALENDAR = 2;
    //事务来源:临时[GTD_AFFAIRS_SOURCE_INTERIM][3]
    public final static byte GTD_AFFAIRS_SOURCE_INTERIM = 3;


    //紧急重要程度:不重要不紧急[GTD_AFFAIRS_IMPORTANCE_NO_NO][1]
    public final static byte GTD_AFFAIRS_IMPORTANCE_NO_NO = 1;
    //紧急重要程度:不重要紧急[GTD_AFFAIRS_IMPORTANCE_NO_YES][2]
    public final static byte GTD_AFFAIRS_IMPORTANCE_NO_YES = 2;
    //紧急重要程度:重要不紧急[GTD_AFFAIRS_IMPORTANCE_YES_NO][3]
    public final static byte GTD_AFFAIRS_IMPORTANCE_YES_NO = 3;
    //紧急重要程度:重要紧急[GTD_AFFAIRS_IMPORTANCE_YES_YES][4]
    public final static byte GTD_AFFAIRS_IMPORTANCE_YES_YES = 4;


}
