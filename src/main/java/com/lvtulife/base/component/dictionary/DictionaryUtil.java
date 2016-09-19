package com.lvtulife.base.component.dictionary;

import com.lvtulife.base.component.LabelValue;
import com.lvtulife.base.model.BaseDictionary;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * @ClassName: BaseDictionaryUtil.java
 * @author: liujiancheng
 * @version: 1.0
 * @CreateDate 2009-11-6
 * @Discription 此类为读取字典表数据并存入DictDate中，并提供增删改查操作
 */
public class DictionaryUtil {

    private final static Log log = LogFactory.getLog(DictionaryUtil.class);
    public static DictionaryUtil eztDictionaryUtil = null;
    private DictionaryBean eztDictionaryBean;

    private DictionaryUtil() {
        eztDictionaryBean = DictionaryBean.getInstance();
    }

    public static DictionaryUtil getInstence() {
        if (eztDictionaryUtil == null) {
            eztDictionaryUtil = new DictionaryUtil();
        }
        return eztDictionaryUtil;
    }

    /**
     * @param enString
     * @return List<BaseDictionary>
     * @作者：liujiancheng
     * @方法描述：通过英文获取字典
     * @创建时间: 2014-8-19 上午10:34:48
     */
    public List<BaseDictionary> getDictListByEn(String enString) {
        List<BaseDictionary> resultList = new ArrayList<BaseDictionary>();
        List<BaseDictionary> dictList = eztDictionaryBean.getDictList();
        for (int i = 0; i < dictList.size(); i++) {
            BaseDictionary tempBaseDictionary = (BaseDictionary) dictList.get(i);
            if (tempBaseDictionary.getTnameEn().equals(enString)) {
                resultList.add(tempBaseDictionary);
            }

        }
        return resultList;
    }

    /**
     * @param enString
     * @return
     * @作者：liujiancheng
     * @方法描述：通过英文获取sort排序最大值
     * @创建时间: 2014-8-19 上午10:34:11
     */
    public Integer getMaxDictSortByEn(String enString) {
        List<BaseDictionary> resultList = new ArrayList<BaseDictionary>();
        List<BaseDictionary> dictList = eztDictionaryBean.getDictList();
        int maxSort = 0;
        for (int i = 0; i < dictList.size(); i++) {
            BaseDictionary tempBaseDictionary = (BaseDictionary) dictList.get(i);
            if (tempBaseDictionary.getTnameEn().equals(enString)) {
                if (tempBaseDictionary.getSort() > maxSort) {
                    maxSort = tempBaseDictionary.getSort();
                }
            }

        }
        return maxSort;
    }

    public static boolean nullOrBlank(String param) {
        return (param == null || param.length() == 0 || param.trim().equals("")) ? true : false;
    }

    /**
     * @param enString
     * @param parm
     * @return Map<Integer,String>
     * @作者：liujiancheng
     * @方法描述：通过ID、parm获取Map,null和1返回中文对照，2返回英文，3返回中英文
     * @创建时间: 2014-8-19 上午10:35:21
     */
    public Map<String, String> getValueMapByEnParm(String enString, String parm) {
        TreeMap<String, String> resultMap = new TreeMap();
        List<BaseDictionary> dictList = eztDictionaryBean.getDictList();
        for (int i = 0; i < dictList.size(); i++) {

            BaseDictionary tempDict = (BaseDictionary) dictList.get(i);
            if (tempDict.getTnameEn().equals(enString)) {
                if (nullOrBlank(parm)) {
                    resultMap.put(tempDict.getId().toString(), tempDict.getTvalueCn());
                } else {
                    if (parm.equals("1")) {
                        resultMap.put(tempDict.getId().toString(), tempDict.getTvalueCn());
                    } else if (parm.equals("2")) {
                        resultMap.put(tempDict.getId().toString(), tempDict.getTvalueEn());
                    } else {
                        resultMap.put(tempDict.getId().toString(), tempDict.getTvalueCn() + "-" + tempDict.getTvalueEn());
                    }
                }
            }

        }
        return resultMap;
    }

    /**
     * @param id
     * @param parm
     * @return
     * @作者：liujiancheng
     * @方法描述：通过ID、parm获取对照内容,null和1返回中文对照，2返回英文，3返回中英文
     * @创建时间: 2014-8-19 上午10:36:04
     */
    public String getDValueByIdParm(Integer id, String parm) {
        String result = null;
        HashMap<Integer, String> tempMap = new HashMap<Integer, String>();
        if (nullOrBlank(parm)) {
            tempMap = eztDictionaryBean.getDictValueShowCn();
            result = (String) tempMap.get(id);
        } else {
            if (parm.equals("1")) {
                tempMap = eztDictionaryBean.getDictValueShowCn();
                result = (String) tempMap.get(id);
            } else if (parm.equals("2")) {
                tempMap = eztDictionaryBean.getDictValueShowEn();
                result = (String) tempMap.get(id);
            } else {
                tempMap = eztDictionaryBean.getDictValue();
                result = (String) tempMap.get(id);
            }
        }
        return result;
    }

    /**
     * @param id
     * @return
     * @作者：liujiancheng
     * @方法描述：通过ID获取一条记录
     * @创建时间: 2014-8-19 上午10:37:03
     */
    public BaseDictionary getDictBeanById(Integer id) {
        HashMap<Integer, BaseDictionary> tempMap = eztDictionaryBean.getDictRecoder();
        return tempMap.get(id);
    }

    /**
     * @param id
     * @return
     * @作者：liujiancheng
     * @方法描述： 通过ID获取中英对照内容
     * @创建时间: 2014-8-19 上午10:37:11
     */
    public String getDValueById(Integer id) {
        HashMap tempMap = eztDictionaryBean.getDictValue();
        String result = (String) tempMap.get(id);
        return result;
    }

    /**
     * @param id
     * @return
     * @作者：liujiancheng
     * @方法描述：通过ID获取中文对照内容
     * @创建时间: 2014-8-19 上午10:37:21
     */
    public String getDValueShowCnById(Integer id) {
        HashMap tempMap = eztDictionaryBean.getDictValueShowCn();
        String result = (String) tempMap.get(id);
        return result;
    }

    /**
     * @param id
     * @return
     * @作者：liujiancheng
     * @方法描述：通过ID获取英文对照内容
     * @创建时间: 2014-8-19 上午10:37:30
     */
    public String getDValueShowEnById(Integer id) {
        HashMap tempMap = eztDictionaryBean.getDictValueShowEn();
        String result = (String) tempMap.get(id);
        return result;
    }

    /**
     * @param enString
     * @param ContString
     * @return
     * @作者：liujiancheng
     * @方法描述：通过英文类型和中文内容获取id
     * @创建时间: 2014-8-19 上午10:37:40
     */
    public Integer getIdByEnValueCn(String enString, String ContString) {
        Integer id = null;
        List dictList = eztDictionaryBean.getDictList();
        for (int i = 0; i < dictList.size(); i++) {

            BaseDictionary tempDict = (BaseDictionary) dictList.get(i);
            if (tempDict.getTnameEn().equals(enString) && tempDict.getTvalueCn().equals(ContString)) {
                id = tempDict.getId();
            }
        }
        return id;
    }

    /**
     * @param id
     * @return
     * @作者：liujiancheng
     * @方法描述：通过ID获取sort
     * @创建时间: 2014-8-19 上午10:37:48
     */
    public Integer getSortById(Integer id) {
        HashMap tempMap = eztDictionaryBean.getDictSort();
        Integer result = (Integer) tempMap.get(id);
        return result;
    }

    /**
     * @param enString
     * @return
     * @作者：liujiancheng
     * @方法描述：通过英文获取英文对照字典LabelValue
     * @创建时间: 2014-8-19 上午10:38:04
     */
    public List<LabelValue> getValueByLabelValue(String enString) {
        List<LabelValue> resultList = new ArrayList<LabelValue>();
        List dictList = eztDictionaryBean.getDictList();
        for (int i = 0; i < dictList.size(); i++) {

            BaseDictionary tempDict = (BaseDictionary) dictList.get(i);
            if (tempDict.getTnameEn().equals(enString)) {
                resultList.add(new LabelValue(tempDict.getTvalueCn() + "-" + tempDict.getTvalueEn(), String.valueOf(tempDict.getId())));
            }

        }
        return resultList;
    }

    /**
     * @param enString
     * @return
     * @作者：liujiancheng
     * @方法描述：通过英文获取中文字典LabelValue
     * @创建时间: 2014-8-19 上午10:38:13
     */
    public ArrayList<LabelValue> getTvalueCnByLabelValue(String enString) {
        ArrayList<LabelValue> resultList = new ArrayList<LabelValue>();
        List<BaseDictionary> dictList = eztDictionaryBean.getDictList();
        for (int i = 0; i < dictList.size(); i++) {
            BaseDictionary tempDict = (BaseDictionary) dictList.get(i);
            if (tempDict.getTnameEn().equals(enString)) {
                resultList.add(new LabelValue(tempDict.getTvalueCn(), String.valueOf(tempDict.getId())));
            }
        }
        return resultList;
    }

    /**
     * @param enString
     * @return
     * @作者：liujiancheng
     * @方法描述：通过英文获取英文对照字典LabelValue
     * @创建时间: 2014-8-19 上午10:38:23
     */
    public List<LabelValue> getTvalueEnByLabelValue(String enString) {
        List<LabelValue> resultList = new ArrayList<LabelValue>();
        List dictList = eztDictionaryBean.getDictList();
        for (int i = 0; i < dictList.size(); i++) {

            BaseDictionary tempDict = (BaseDictionary) dictList.get(i);
            if (tempDict.getTnameEn().equals(enString)) {
                resultList.add(new LabelValue(tempDict.getTvalueEn(), String.valueOf(tempDict.getId())));
            }

        }
        return resultList;
    }

    /**
     * @param enString
     * @param parm
     * @return
     * @作者：liujiancheng
     * @方法描述：通过英文获取英文对照字典LabelValue
     * @创建时间: 2014-8-19 上午10:38:31
     */
    public List<LabelValue> getValueByLabelValue(String enString, String parm) {
        List<LabelValue> resultList = new ArrayList<LabelValue>();
        List dictList = eztDictionaryBean.getDictList();
        for (int i = 0; i < dictList.size(); i++) {

            BaseDictionary tempDict = (BaseDictionary) dictList.get(i);
            if (tempDict.getTnameEn().equals(enString)) {
                if (nullOrBlank(parm)) {
                    resultList.add(new LabelValue(tempDict.getTvalueCn(), String.valueOf(tempDict.getId())));
                } else {
                    if (parm.equals("1")) {
                        resultList.add(new LabelValue(tempDict.getTvalueCn(), String.valueOf(tempDict.getId())));
                    } else if (parm.equals("2")) {
                        resultList.add(new LabelValue(tempDict.getTvalueEn(), String.valueOf(tempDict.getId())));
                    } else {
                        resultList.add(new LabelValue(tempDict.getTvalueCn() + "-" + tempDict.getTvalueEn(), String.valueOf(tempDict.getId())));
                    }
                }

            }

        }
        return resultList;
    }
}
