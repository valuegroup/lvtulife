package com.lvtulife.base.component.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lvtulife.base.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvtulife.base.model.BaseDictionary;
import com.lvtulife.base.service.BaseDictionaryServiceI;

public class DictionaryBean {
    private static Logger logger = LoggerFactory.getLogger(DictionaryBean.class);

    private static DictionaryBean dictionaryBean = null;
    private List<BaseDictionary> dictList;
    private HashMap<Integer, String> dictChineseName;// 中文
    private HashMap<Integer, String> dictEnglishName;// 英文
    private HashMap<Integer, Byte> dictType; // 字典类型
    private HashMap<Integer, String> dictValue;// 中-英显示
    private HashMap<Integer, String> dictValueShowEn;// 中-英显示
    private HashMap<Integer, String> dictValueShowCn;// 中-英显示
    private HashMap<Integer, Integer> dictSort;
    private HashMap<Integer, BaseDictionary> dictRecoder;

    private DictionaryBean() {
        dictList = new ArrayList<BaseDictionary>();
        dictChineseName = new HashMap<Integer, String>();
        dictEnglishName = new HashMap<Integer, String>();
        dictType = new HashMap<Integer, Byte>();
        dictValue = new HashMap<Integer, String>();
        dictValueShowEn = new HashMap<Integer, String>();
        dictValueShowCn = new HashMap<Integer, String>();
        dictSort = new HashMap<Integer, Integer>();
        dictRecoder = new HashMap<Integer, BaseDictionary>();
    }

    public static DictionaryBean getInstance() {
        if (dictionaryBean == null) {
            //logger.info("字典表加载中");
            dictionaryBean = new DictionaryBean();
            /*String[] ctxFileName = new String[]{"classpath:spring.xml"};
            ApplicationContext ctx = new ClassPathXmlApplicationContext(ctxFileName);
            BaseDictionaryServiceI dictionaryService = (BaseDictionaryServiceI) ctx.getBean("BaseDictionaryService");
            dictionaryService.initBaseDictionary();*/

            BaseDictionaryServiceI service = (BaseDictionaryServiceI) SpringContextUtil.getBean("BaseDictionaryService");
            List<BaseDictionary> allDate = service.findDictionarys();

            dictionaryBean.setDictList(allDate);
            BaseDictionary tempDict = null;
            for (int i = 0; i < allDate.size(); i++) {
                tempDict = (BaseDictionary) allDate.get(i);
                // 中文
                dictionaryBean.getDictChineseName().put(tempDict.getId(), tempDict.getTnameCn());
                // 英文
                dictionaryBean.getDictEnglishName().put(tempDict.getId(), tempDict.getTnameEn());
                // 类型
                dictionaryBean.getDictType().put(tempDict.getId(), tempDict.getDtype());
                // 中英文对照值
                dictionaryBean.getDictValue().put(tempDict.getId(), tempDict.getTvalueCn() + "-" + tempDict.getTvalueEn());
                // 中文对照值
                dictionaryBean.getDictValueShowCn().put(tempDict.getId(), tempDict.getTvalueCn());
                // 英文对照值
                dictionaryBean.getDictValueShowEn().put(tempDict.getId(), tempDict.getTvalueEn());
                // sort
                dictionaryBean.getDictSort().put(tempDict.getId(), tempDict.getSort());
                // recoder
                dictionaryBean.getDictRecoder().put(tempDict.getId(), tempDict);
            }
            logger.info("字典表加载完成");
        }
        return dictionaryBean;
    }

    public static DictionaryBean getDictionaryBean() {
        return dictionaryBean;
    }

    public static void setDictionaryBean(DictionaryBean dictionaryBean) {
        DictionaryBean.dictionaryBean = dictionaryBean;
    }

    public List<BaseDictionary> getDictList() {
        return dictList;
    }

    public void setDictList(List<BaseDictionary> dictList) {
        this.dictList = dictList;
    }

    public HashMap<Integer, String> getDictChineseName() {
        return dictChineseName;
    }

    public void setDictChineseName(HashMap<Integer, String> dictChineseName) {
        this.dictChineseName = dictChineseName;
    }

    public HashMap<Integer, String> getDictEnglishName() {
        return dictEnglishName;
    }

    public void setDictEnglishName(HashMap<Integer, String> dictEnglishName) {
        this.dictEnglishName = dictEnglishName;
    }

    public HashMap<Integer, Byte> getDictType() {
        return dictType;
    }

    public void setDictType(HashMap<Integer, Byte> dictType) {
        this.dictType = dictType;
    }

    public HashMap<Integer, String> getDictValue() {
        return dictValue;
    }

    public void setDictValue(HashMap<Integer, String> dictValue) {
        this.dictValue = dictValue;
    }

    public HashMap<Integer, String> getDictValueShowEn() {
        return dictValueShowEn;
    }

    public void setDictValueShowEn(HashMap<Integer, String> dictValueShowEn) {
        this.dictValueShowEn = dictValueShowEn;
    }

    public HashMap<Integer, String> getDictValueShowCn() {
        return dictValueShowCn;
    }

    public void setDictValueShowCn(HashMap<Integer, String> dictValueShowCn) {
        this.dictValueShowCn = dictValueShowCn;
    }

    public HashMap<Integer, Integer> getDictSort() {
        return dictSort;
    }

    public void setDictSort(HashMap<Integer, Integer> dictSort) {
        this.dictSort = dictSort;
    }

    public HashMap<Integer, BaseDictionary> getDictRecoder() {
        return dictRecoder;
    }

    public void setDictRecoder(HashMap<Integer, BaseDictionary> dictRecoder) {
        this.dictRecoder = dictRecoder;
    }
}
