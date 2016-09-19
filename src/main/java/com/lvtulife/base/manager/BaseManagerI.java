package com.lvtulife.base.manager;

import com.lvtulife.base.service.BaseMessageCodeServiceI;

/**
 * 基础综合业务接口类
 *
 * @author lvtulife
 */
public interface BaseManagerI {

    /**
     * 基础消息业务处理类
     *
     * @return
     */
    public BaseMessageCodeServiceI getBaseMessageCodeBean();
}
