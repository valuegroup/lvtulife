package com.lvtulife.base.service;

import com.lvtulife.base.model.BaseMessageCode;

import java.util.List;

public interface BaseMessageCodeServiceI extends BaseServiceI<BaseMessageCode> {

    /**
     * 获取所有CODE
     */
    public List<BaseMessageCode> findBaseMessageCodes();
}
