package com.lvtulife.base.component.message;

import com.lvtulife.base.model.BaseMessageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class BaseMessageUtils {
    private static Logger logger = LoggerFactory.getLogger(BaseMessageUtils.class);
    private static BaseMessageUtils utils = null;
    private BaseMessage bean;

    public BaseMessageUtils() {
        bean = BaseMessage.getInstance();
    }

    public static BaseMessageUtils getInstence() {
        if (utils == null) {
            utils = new BaseMessageUtils();
        }
        return utils;
    }

    /**
     * 获取所有错误信息
     *
     * @return
     */
    public List<BaseMessageCode> getAllCodes() {
        return bean.getList();
    }

    /**
     * 获得指定编码的类型
     *
     * @param code
     * @return
     */
    public Byte getCodeType(Integer code) {
        HashMap<Integer, Byte> map = bean.getCodeTypes();
        return map.get(code);
    }

    /**
     * 获得指定编码的消息
     *
     * @param code
     * @return
     */
    public String getCodeInfo(Integer code) {
        HashMap<Integer, String> map = bean.getCodeInfos();
        return map.get(code);
    }

    /**
     * 获得指定编码对象
     *
     * @param code
     * @return
     */
    public BaseMessageCode getCodeBean(Integer code) {
        HashMap<Integer, BaseMessageCode> map = bean.getCodeBeans();
        return map.get(code);
    }

    /**
     * 获得指定编码状态
     *
     * @param code
     * @return
     */
    public Boolean getCodeSuccess(Integer code) {
        HashMap<Integer, Byte> map = bean.getCodeTypes();
        if (map.size() == 0) {
            logger.error("编码内容获取失败！size:{}", map.size());
            return false;
        }
        if (map.get(code).intValue() == 2) {
            return true;
        } else {
            return false;
        }
    }

}
