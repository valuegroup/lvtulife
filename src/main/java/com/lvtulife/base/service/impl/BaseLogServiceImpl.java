package com.lvtulife.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.lvtulife.base.dao.BaseLogDaoI;
import com.lvtulife.base.service.BaseLogServiceI;
import com.lvtulife.base.model.BaseLog;
import com.lvtulife.base.mapper.BaseLogMapper;
import com.lvtulife.base.mapper.impl.BaseMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 系统日志
 * BaseLogServiceImpl component. @author lvtulife Tools
 */
@Service("BaseLogService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BaseLogServiceImpl extends BaseMapperImpl<BaseLog> implements BaseLogServiceI {

    private static Logger logger = LoggerFactory.getLogger(BaseLog.class);

    @Resource(name = "BaseLogDao")
    private BaseLogDaoI baseLogDao;

    @Resource(name = "BaseLogMapper")
    private BaseLogMapper baseLogMapper;

}
