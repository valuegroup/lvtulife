package com.lvtulife.base.service.impl;

import com.lvtulife.base.component.message.BaseMessage;
import com.lvtulife.base.model.BaseMessageCode;
import com.lvtulife.base.service.BaseMessageCodeServiceI;
import com.lvtulife.base.utils.HqlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service(value = "BaseMessageCodeService")
public class BaseMessageCodeServiceImpl extends BaseServiceImpl<BaseMessageCode> implements BaseMessageCodeServiceI {
    private static Logger logger = LoggerFactory.getLogger(BaseMessageCodeServiceImpl.class);

    public List<BaseMessageCode> findBaseMessageCodes() {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addSort("t.codeValue");
        hqlFilter.addOrder("asc");
        return findByFilter(hqlFilter);
    }
}
