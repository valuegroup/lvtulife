package com.lvtulife.base.manager.impl;

import com.lvtulife.base.manager.BaseManagerI;
import com.lvtulife.base.service.BaseMessageCodeServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 基础综合业务处理类
 *
 * @author lvtulife
 */
@Service(value = "BaseManager")
public class BaseManagerImpl implements BaseManagerI {
    private static Logger logger = LoggerFactory.getLogger(BaseManagerImpl.class);

    @Resource(name = "BaseMessageCodeService")
    private BaseMessageCodeServiceI baseMessageCodeBean;

    public BaseMessageCodeServiceI getBaseMessageCodeBean() {
        return baseMessageCodeBean;
    }
}
