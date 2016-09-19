package com.lvtulife.base.service.impl;

import com.lvtulife.base.component.dictionary.DictionaryBean;
import com.lvtulife.base.model.BaseDictionary;
import com.lvtulife.base.service.BaseDictionaryServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统字典参数业务逻辑
 *
 * @author lvtulife
 */
@Service(value = "BaseDictionaryService")
public class BaseDictionaryServiceImpl extends BaseServiceImpl<BaseDictionary> implements BaseDictionaryServiceI {
    private static Logger logger = LoggerFactory.getLogger(BaseDictionaryServiceImpl.class);

    public List<BaseDictionary> findDictionarys() {
        return find();
    }

}
