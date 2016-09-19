package com.lvtulife.base.dao.impl;

import org.springframework.stereotype.Repository;
import com.lvtulife.base.dao.impl.BaseDaoImpl;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import com.lvtulife.base.dao.BaseLogDaoI;
import com.lvtulife.base.model.BaseLog;

/**
 * 系统日志
 * BaseLogDaoImpl component. @author lvtulife Tools
 */
@Repository("BaseLogDao")
public class BaseLogDaoImpl extends BaseDaoImpl<BaseLog> implements BaseLogDaoI {

	private static Logger logger = LoggerFactory.getLogger(BaseLog.class);

}
