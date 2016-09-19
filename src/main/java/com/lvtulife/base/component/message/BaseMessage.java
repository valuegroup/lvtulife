package com.lvtulife.base.component.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.lvtulife.base.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvtulife.base.model.BaseMessageCode;
import com.lvtulife.base.service.BaseMessageCodeServiceI;

public class BaseMessage {
    private static Logger logger = LoggerFactory.getLogger(BaseMessage.class);

    private static BaseMessage bean = null;
    private List<BaseMessageCode> list;

    private HashMap<Integer, Byte> codeTypes;
    private HashMap<Integer, Integer> codeValues;
    private HashMap<Integer, String> codeInfos;
    private HashMap<Integer, BaseMessageCode> codeBeans;

    public BaseMessage() {
        // 初始化
        list = new ArrayList<BaseMessageCode>();
        codeTypes = new HashMap<Integer, Byte>();
        codeValues = new HashMap<Integer, Integer>();
        codeInfos = new HashMap<Integer, String>();
        codeBeans = new HashMap<Integer, BaseMessageCode>();
    }

    public static BaseMessage getInstance() {
        if (bean == null) {
            //logger.info("编号代码加载中...");
            bean = new BaseMessage();
            /*String[] ctxFileName = new String[] { "spring.xml"};
			ApplicationContext ctx = new ClassPathXmlApplicationContext(ctxFileName);
			BaseMessageCodeServiceI service = (BaseMessageCodeServiceI) ctx.getBean("BaseMessageCodeService");
			service.initBaseMessageCode();*/

            BaseMessageCodeServiceI service = (BaseMessageCodeServiceI) SpringContextUtil.getBean("BaseMessageCodeService");
            List<BaseMessageCode> allCodes = service.findBaseMessageCodes();

            bean.setList(allCodes);
            Iterator<BaseMessageCode> iter = allCodes.iterator();
            while (iter.hasNext()) {
                BaseMessageCode codeTemp = iter.next();
                bean.getCodeTypes().put(codeTemp.getCodeValue(), codeTemp.getCodeType());
                bean.getCodeValues().put(codeTemp.getCodeValue(), codeTemp.getCodeValue());
                bean.getCodeInfos().put(codeTemp.getCodeValue(), codeTemp.getCodeInfo());
                bean.getCodeBeans().put(codeTemp.getCodeValue(), codeTemp);
            }
            logger.info("编号代码加载完成");
        }
        return bean;
    }

    public static BaseMessage getBean() {
        return bean;
    }

    public static void setBean(BaseMessage bean) {
        BaseMessage.bean = bean;
    }

    public List<BaseMessageCode> getList() {
        return list;
    }

    public void setList(List<BaseMessageCode> list) {
        this.list = list;
    }

    public HashMap<Integer, Byte> getCodeTypes() {
        return codeTypes;
    }

    public void setCodeTypes(HashMap<Integer, Byte> codeTypes) {
        this.codeTypes = codeTypes;
    }

    public HashMap<Integer, Integer> getCodeValues() {
        return codeValues;
    }

    public void setCodeValues(HashMap<Integer, Integer> codeValues) {
        this.codeValues = codeValues;
    }

    public HashMap<Integer, String> getCodeInfos() {
        return codeInfos;
    }

    public void setCodeInfos(HashMap<Integer, String> codeInfos) {
        this.codeInfos = codeInfos;
    }

    public HashMap<Integer, BaseMessageCode> getCodeBeans() {
        return codeBeans;
    }

    public void setCodeBeans(HashMap<Integer, BaseMessageCode> codeBeans) {
        this.codeBeans = codeBeans;
    }

}
