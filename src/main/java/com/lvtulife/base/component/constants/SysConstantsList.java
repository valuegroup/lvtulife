package com.lvtulife.base.component.constants;

import com.lvtulife.base.component.LabelValue;
import com.lvtulife.base.component.dictionary.DictionaryUtil;
import com.lvtulife.base.component.log.MethodEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统逻辑判断常量列表集合
 *
 * @author valuegroup
 */
public final class SysConstantsList {
    private static SysConstantsList sysConstantsList = new SysConstantsList();

    /**
     * 根据str字符串调用方法，变量i只是为了判断
     *
     * @return
     */
    public static Map<String, ArrayList<LabelValue>> getAllListforTableName() {
        Map<String, ArrayList<LabelValue>> lvMap = new HashMap<String, ArrayList<LabelValue>>();
        Map<String, String> map = getAllTableNameValue();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                ArrayList<LabelValue> lvlist = (ArrayList<LabelValue>) sysConstantsList.getClass().getMethod("init_" + entry.getKey() + "List", new Class[]{}).invoke(sysConstantsList, new Object[]{});
                lvMap.put(entry.getKey(), lvlist);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lvMap;
    }

    public static ArrayList<LabelValue> getListforTableNameNoQXZ(String table) {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        try {
            list = (ArrayList<LabelValue>) sysConstantsList.getClass().getMethod("init_" + table + "List", new Class[]{}).invoke(sysConstantsList, new Object[]{});
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<LabelValue> getListforTableName(String table) {
        ArrayList<LabelValue> list;
        try {
            list = (ArrayList<LabelValue>) sysConstantsList.getClass().getMethod("init_" + table + "_list", new Class[]{}).invoke(sysConstantsList, new Object[]{});
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLabelforTableNameValue(String table, String value) {
        try {
            ArrayList<LabelValue> list = (ArrayList<LabelValue>) sysConstantsList.getClass().getMethod("init_" + table + "List", new Class[]{}).invoke(sysConstantsList, new Object[]{});
            return getLabel(list, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<LabelValue> getListforTableName(String[] tables) {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        try {
            if (tables != null) {
                for (String table : tables) {
                    ArrayList<LabelValue> talList = (ArrayList<LabelValue>) sysConstantsList.getClass().getMethod("init_" + table + "List", new Class[]{}).invoke(sysConstantsList, new Object[]{});

                    for (LabelValue lv : talList) {
                        boolean yn = false;
                        if (list != null) {
                            for (LabelValue listLV : list) {
                                if (listLV.getValue().equals(lv.getValue()))
                                    yn = true;
                            }
                        }
                        if (!yn)
                            list.add(lv);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<LabelValue> getListforTableNameQXZ(String[] tables) {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        try {
            if (tables != null) {
                for (String table : tables) {
                    ArrayList<LabelValue> talList = (ArrayList<LabelValue>) sysConstantsList.getClass().getMethod("init_" + table + "List", new Class[]{}).invoke(sysConstantsList, new Object[]{});
                    list.addAll(talList);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> getAllTableNameValue() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "数据状态");
        map.put("enable", "启用状态");
        map.put("yesOrNo", "是否状态");
        map.put("openOrClose", "开启状态");
        map.put("dataSource", "数据来源");
        map.put("olType", "登录类型");
        map.put("rstatus", "角色状态");
        map.put("ustatus", "用户状态");
        map.put("sex", "用户性别");
        map.put("resAttr", "资源属性");
        map.put("method", "CURD操作类型");
        map.put("logType", "日志类型");


        // 仓储管理系统
        map.put("autoCodeType", "自动编码类型");
        map.put("brandOrigin", "产地");
        map.put("wmsInType", "入库类型");
        map.put("wmsInStat", "入库状态");
        map.put("dayType", "日期类型");

        // GTD 业务
        map.put("gtdStar", "星标列表");
        map.put("gtdTop", "置顶列表");
        map.put("gtdEncrypt", "加密列表");
        map.put("gtdStage", "阶段列表");
        map.put("gtdOpen", "公开列表");
        map.put("idealStatus", "理想状态列表");
        map.put("aimStatus", "目标状态列表");
        map.put("affairsStatus", "事务状态列表");
        map.put("aimUnit", "划分单位列表");
        map.put("reward", "奖励类别列表");
        map.put("gtdDo", "实施状态列表");
        map.put("repeat", "重复列表");
        map.put("repeatTerm", "重复条件列表");
        map.put("calendar", "日历类型列表");
        map.put("timeInterval", "时段列表");
        map.put("affairsSource", "事务来源列表");
        map.put("affairsImportance", "紧急重要程度列表");

        return map;
    }

    /**
     * CURD操作类型 列表
     */
    public static ArrayList<LabelValue> init_method_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue(MethodEnum.Find.getName(), MethodEnum.Find.getValue().toString()));
        list.add(new LabelValue(MethodEnum.Create.getName(), MethodEnum.Create.getValue().toString()));
        list.add(new LabelValue(MethodEnum.Update.getName(), MethodEnum.Update.getValue().toString()));
        list.add(new LabelValue(MethodEnum.Delete.getName(), MethodEnum.Delete.getValue().toString()));
        return list;
    }

    /**
     * 日志类型 列表
     */
    public static ArrayList<LabelValue> init_logType_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("操作", Byte.toString(SysConstants.SYS_LOG_TYPE_OP)));
        list.add(new LabelValue("异常", Byte.toString(SysConstants.SYS_LOG_TYPE_ERROR)));
        return list;
    }

    /**
     * 数据状态 列表
     */
    public static ArrayList<LabelValue> init_status_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("正常", Byte.toString(SysConstants.SYS_STATUS)));
        list.add(new LabelValue("删除", Byte.toString(SysConstants.SYS_STATUS_DELETE)));
        return list;
    }

    /**
     * 启用状态 列表
     */
    public static ArrayList<LabelValue> init_enable_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("启用", Byte.toString(SysConstants.SYS_ENABLE)));
        list.add(new LabelValue("禁用", Byte.toString(SysConstants.SYS_DISABLED)));
        return list;
    }

    /**
     * 是否状态 列表
     */
    public static ArrayList<LabelValue> init_yesOrNo_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("否", Byte.toString(SysConstants.SYS_NO)));// 建议否在先
        list.add(new LabelValue("是", Byte.toString(SysConstants.SYS_YES)));
        return list;
    }

    /**
     * 开启状态 列表
     */
    public static ArrayList<LabelValue> init_openOrClose_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("开启", Byte.toString(SysConstants.SYS_OPEN)));
        list.add(new LabelValue("关闭", Byte.toString(SysConstants.SYS_CLOSE)));
        return list;
    }

    /**
     * 数据来源 列表
     */
    public static ArrayList<LabelValue> init_dataSource_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("系统内置", Byte.toString(SysConstants.SYS_INSYS)));
        list.add(new LabelValue("用户设定", Byte.toString(SysConstants.SYS_INUSER)));
        return list;
    }

    /**
     * 用户状态 列表
     */
    public static ArrayList<LabelValue> init_ustatus_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("正常", Byte.toString(SysConstants.SYS_USTATUS)));
        list.add(new LabelValue("禁用", Byte.toString(SysConstants.SYS_USTATUS_BAN)));
        return list;
    }

    /**
     * 角色状态 列表
     */
    public static ArrayList<LabelValue> init_rstatus_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("正常", Byte.toString(SysConstants.SYS_RSTATUS)));
        list.add(new LabelValue("禁用", Byte.toString(SysConstants.SYS_RSTATUS_BAN)));
        return list;
    }

    /**
     * 登录类型 列表
     */
    public static ArrayList<LabelValue> init_olType_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("注销", Byte.toString(SysConstants.SYS_OLTYPE_OUT)));
        list.add(new LabelValue("登录", Byte.toString(SysConstants.SYS_OLTYPE_IN)));
        return list;
    }

    /**
     * 用户性别 列表
     */
    public static ArrayList<LabelValue> init_sex_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("男性", Byte.toString(SysConstants.SYS_SEX_MALE)));
        list.add(new LabelValue("女性", Byte.toString(SysConstants.SYS_SEX_FEMALE)));
        return list;
    }

    /**
     * 资源属性 列表
     */
    public static ArrayList<LabelValue> init_resAttr_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("菜单", Byte.toString(SysConstants.SYS_RES_ATTR_MENU)));
        list.add(new LabelValue("功能", Byte.toString(SysConstants.SYS_RES_ATTR_FUN)));
        return list;
    }

    /** 仓储管理系统 */

    /**
     * 自动编码类型 列表
     */
    public static ArrayList<LabelValue> init_autoCodeType_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("业务编号", Byte.toString(SysConstants.WMS_AUTO_CODE_TYPE_BUSINESS)));
        list.add(new LabelValue("工作号", Byte.toString(SysConstants.WMS_AUTO_CODE_TYPE_WORK)));
        return list;
    }

    /**
     * 产地 列表
     */
    public static ArrayList<LabelValue> init_brandOrigin_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("大陆", Byte.toString(SysConstants.WMS_BRAND_ORIGIN_DL)));
        list.add(new LabelValue("台湾", Byte.toString(SysConstants.WMS_BRAND_ORIGIN_TW)));
        list.add(new LabelValue("日韩", Byte.toString(SysConstants.WMS_BRAND_ORIGIN_RH)));
        list.add(new LabelValue("欧美", Byte.toString(SysConstants.WMS_BRAND_ORIGIN_OM)));
        return list;
    }

    /**
     * 入库类型 列表
     */
    public static ArrayList<LabelValue> init_wmsInType_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("普通入库", Byte.toString(SysConstants.WMS_IN_TYPE_PTRK)));
        list.add(new LabelValue("临时入库", Byte.toString(SysConstants.WMS_IN_TYPE_LSRK)));
        list.add(new LabelValue("其他入库", Byte.toString(SysConstants.WMS_IN_TYPE_QTRK)));
        list.add(new LabelValue("退货入库", Byte.toString(SysConstants.WMS_IN_TYPE_THRK)));
        list.add(new LabelValue("换货入库", Byte.toString(SysConstants.WMS_IN_TYPE_HHRK)));
        list.add(new LabelValue("本库转让", Byte.toString(SysConstants.WMS_IN_TYPE_BKZR)));
        list.add(new LabelValue("库组转让", Byte.toString(SysConstants.WMS_IN_TYPE_KZZR)));
        list.add(new LabelValue("区内转入", Byte.toString(SysConstants.WMS_IN_TYPE_QNZR)));
        return list;
    }

    /**
     * 入库状态 列表
     */
    public static ArrayList<LabelValue> init_wmsInStat_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("新建", Byte.toString(SysConstants.WMS_IN_STAT_NEW)));
        list.add(new LabelValue("已创建", Byte.toString(SysConstants.WMS_IN_STAT_CREATE)));
        list.add(new LabelValue("已确认", Byte.toString(SysConstants.WMS_IN_STAT_AFFIRM)));
        list.add(new LabelValue("已作废", Byte.toString(SysConstants.WMS_IN_STAT_CANCEL)));
        return list;
    }

    /***
     * 日期类型 列表
     */
    public static ArrayList<LabelValue> init_dayType_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("入库日期", Byte.toString(SysConstants.WMS_DAY_TYPE_IN)));
        list.add(new LabelValue("到货日期", Byte.toString(SysConstants.WMS_DAY_TYPE_ARRIVE)));
        list.add(new LabelValue("确认日期", Byte.toString(SysConstants.WMS_DAY_TYPE_AFFRIM)));
        list.add(new LabelValue("入库日期", Byte.toString(SysConstants.WMS_DAY_TYPE_CREATE)));
        return list;
    }


    /***
     * 星标 列表
     */
    public static ArrayList<LabelValue> init_gtdStar_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("是", Byte.toString(SysConstants.GTD_STAR_YES)));
        list.add(new LabelValue("否", Byte.toString(SysConstants.GTD_STAR_NO)));
        return list;
    }


    /***
     * 置顶 列表
     */
    public static ArrayList<LabelValue> init_gtdTop_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("是", Byte.toString(SysConstants.GTD_TOP_YES)));
        list.add(new LabelValue("否", Byte.toString(SysConstants.GTD_TOP_NO)));
        return list;
    }


    /***
     * 加密 列表
     */
    public static ArrayList<LabelValue> init_gtdEncrypt_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("加密", Byte.toString(SysConstants.GTD_ENCRYPT_YES)));
        list.add(new LabelValue("不加密", Byte.toString(SysConstants.GTD_ENCRYPT_NO)));
        return list;
    }


    /***
     * 阶段 列表
     */
    public static ArrayList<LabelValue> init_gtdStage_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("第一阶段", Byte.toString(SysConstants.GTD_STAGE_1)));
        list.add(new LabelValue("第二阶段", Byte.toString(SysConstants.GTD_STAGE_2)));
        list.add(new LabelValue("第三阶段", Byte.toString(SysConstants.GTD_STAGE_3)));
        list.add(new LabelValue("第四阶段", Byte.toString(SysConstants.GTD_STAGE_4)));
        return list;
    }


    /***
     * 公开 列表
     */
    public static ArrayList<LabelValue> init_gtdOpen_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("公开", Byte.toString(SysConstants.GTD_OPEN_YES)));
        list.add(new LabelValue("不公开", Byte.toString(SysConstants.GTD_OPEN_NO)));
        return list;
    }


    /***
     * 理想状态 列表
     */
    public static ArrayList<LabelValue> init_idealStatus_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("未达成", Byte.toString(SysConstants.GTD_IDEAL_WAIT)));
        list.add(new LabelValue("已达成", Byte.toString(SysConstants.GTD_IDEAL_COMPLETED)));
        list.add(new LabelValue("作废", Byte.toString(SysConstants.GTD_IDEAL_CANCEL)));
        return list;
    }

    /***
     * 目标状态 列表
     */
    public static ArrayList<LabelValue> init_aimStatus_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("未达成", Byte.toString(SysConstants.GTD_AIM_WAIT)));
        list.add(new LabelValue("已达成", Byte.toString(SysConstants.GTD_AIM_COMPLETED)));
        list.add(new LabelValue("作废", Byte.toString(SysConstants.GTD_AIM_CANCEL)));
        return list;
    }

    /***
     * 事务状态 列表
     */
    public static ArrayList<LabelValue> init_affairsStatus_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("未达成", Byte.toString(SysConstants.GTD_AFFAIRS_WAIT)));
        list.add(new LabelValue("已达成", Byte.toString(SysConstants.GTD_AFFAIRS_COMPLETED)));
        list.add(new LabelValue("作废", Byte.toString(SysConstants.GTD_AFFAIRS_CANCEL)));
        return list;
    }

    /***
     * 划分单位 列表
     */
    public static ArrayList<LabelValue> init_aimUnit_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("单元", Byte.toString(SysConstants.GTD_AIM_UNIT)));
        list.add(new LabelValue("月度", Byte.toString(SysConstants.GTD_AIM_UNIT_MONTH)));
        list.add(new LabelValue("季度", Byte.toString(SysConstants.GTD_AIM_UNIT_SEASON)));
        list.add(new LabelValue("年度", Byte.toString(SysConstants.GTD_AIM_UNIT_YEAR)));
        return list;
    }


    /***
     * 奖励类别 列表
     */
    public static ArrayList<LabelValue> init_reward_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("实物", Byte.toString(SysConstants.GTD_REWARD)));
        list.add(new LabelValue("虚拟", Byte.toString(SysConstants.GTD_REWARD_INVENTED)));
        return list;
    }


    /***
     * 实施状态 列表
     */
    public static ArrayList<LabelValue> init_gtdDo_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("已实施", Byte.toString(SysConstants.GTD_DO_YES)));
        list.add(new LabelValue("未实施", Byte.toString(SysConstants.GTD_DO_NO)));
        return list;
    }


    /***
     * 重复 列表
     */
    public static ArrayList<LabelValue> init_repeat_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("一次性活动", Byte.toString(SysConstants.GTD_REPEAT_DISPOSABLE)));
        list.add(new LabelValue("每天", Byte.toString(SysConstants.GTD_REPEAT_DAY)));
        list.add(new LabelValue("每个工作日", Byte.toString(SysConstants.GTD_REPEAT_WORK_DAY)));
        list.add(new LabelValue("每周", Byte.toString(SysConstants.GTD_REPEAT_WEEK)));
        list.add(new LabelValue("每月", Byte.toString(SysConstants.GTD_REPEAT_MONTH)));
        list.add(new LabelValue("每年", Byte.toString(SysConstants.GTD_REPEAT_YEAR)));
        list.add(new LabelValue("每年农历", Byte.toString(SysConstants.GTD_REPEAT_LUNAR)));
        return list;
    }


    /***
     * 重复条件 列表
     */
    public static ArrayList<LabelValue> init_repeatTerm_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("无重复", Byte.toString(SysConstants.GTD_REPEAT_TERM_NO)));
        list.add(new LabelValue("无限重复", Byte.toString(SysConstants.GTD_REPEAT_TERM_INFINITE)));
        list.add(new LabelValue("截止至某个日期", Byte.toString(SysConstants.GTD_REPEAT_TERM_DAY)));
        list.add(new LabelValue("限定次数", Byte.toString(SysConstants.GTD_REPEAT_TERM_COUNT)));
        return list;
    }

    /***
     * 日历类型 列表
     */
    public static ArrayList<LabelValue> init_calendar_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("公历", Byte.toString(SysConstants.GTD_CALENDAR)));
        list.add(new LabelValue("农历", Byte.toString(SysConstants.GTD_CALENDAR_LUNAR)));
        return list;
    }


    /***
     * 时段 列表
     */
    public static ArrayList<LabelValue> init_timeInterval_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("早晨", Byte.toString(SysConstants.GTD_TIME_INTERVAL_MORNING)));
        list.add(new LabelValue("上午", Byte.toString(SysConstants.GTD_TIME_INTERVAL_AM)));
        list.add(new LabelValue("中午", Byte.toString(SysConstants.GTD_TIME_INTERVAL_NOONTIME)));
        list.add(new LabelValue("下午", Byte.toString(SysConstants.GTD_TIME_INTERVAL_PM)));
        list.add(new LabelValue("傍晚", Byte.toString(SysConstants.GTD_TIME_INTERVAL_NIGHTFALL)));
        list.add(new LabelValue("晚间", Byte.toString(SysConstants.GTD_TIME_INTERVAL_EVENING)));
        list.add(new LabelValue("凌晨", Byte.toString(SysConstants.GTD_TIME_INTERVAL_DAWN)));
        return list;
    }


    /***
     * 事务来源 列表
     */
    public static ArrayList<LabelValue> init_affairsSource_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("目标", Byte.toString(SysConstants.GTD_AFFAIRS_SOURCE_AIM)));
        list.add(new LabelValue("习惯", Byte.toString(SysConstants.GTD_AFFAIRS_SOURCE_HABIT)));
        list.add(new LabelValue("日历", Byte.toString(SysConstants.GTD_AFFAIRS_SOURCE_CALENDAR)));
        list.add(new LabelValue("临时", Byte.toString(SysConstants.GTD_AFFAIRS_SOURCE_INTERIM)));
        return list;
    }


    /***
     * 紧急重要程度 列表
     */
    public static ArrayList<LabelValue> init_affairsImportance_list() {
        ArrayList<LabelValue> list = new ArrayList<LabelValue>();
        list.add(new LabelValue("不重要不紧急", Byte.toString(SysConstants.GTD_AFFAIRS_IMPORTANCE_NO_NO)));
        list.add(new LabelValue("不重要紧急", Byte.toString(SysConstants.GTD_AFFAIRS_IMPORTANCE_NO_YES)));
        list.add(new LabelValue("重要不紧急", Byte.toString(SysConstants.GTD_AFFAIRS_IMPORTANCE_YES_NO)));
        list.add(new LabelValue("重要紧急", Byte.toString(SysConstants.GTD_AFFAIRS_IMPORTANCE_YES_YES)));
        return list;
    }

    /**
     * @param enName
     * @return
     * @作者：liujiancheng
     * @方法描述：通过英文获取字典表结果集
     */
    public static ArrayList<LabelValue> init_commonList(String enName) {
        return DictionaryUtil.getInstence().getTvalueCnByLabelValue(enName);
    }

    /**
     * 根据一个LabelValue的list集合和其中的一个value取得对应的label
     *
     * @param list
     * @param value
     * @return
     */
    public static String getLabel(ArrayList<LabelValue> list, String value) {
        String label = "";
        for (LabelValue lv : list) {
            if (lv.getValue().equals(value)) {
                return lv.getLabel();
            }
        }
        return label;
    }

    /**
     * 根据一个LabelValue的list集合和value的数组取得对应的label数组。其他下标一样是一组
     *
     * @param list
     * @param values
     * @return
     */
    public static String[] getLabels(ArrayList<LabelValue> list, String values[]) {
        String label[] = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            label[i] = getLabel(list, values[i]);
        }
        return label;
    }

    /**
     * 根据一个LabelValue的list集合取得对应的label数组
     *
     * @param list
     * @return
     */
    public static String[] getLabels(ArrayList<LabelValue> list) {
        String label[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            label[i] = list.get(i).getLabel();
        }
        return label;
    }

    /**
     * 根据一个LabelValue的list集合取得对应的value数组
     *
     * @param list
     * @return
     */
    public static String[] getValues(ArrayList<LabelValue> list) {
        String label[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            label[i] = list.get(i).getValue();
        }
        return label;
    }
}
