package com.lvtulife.gtd.dao.impl;

import org.springframework.stereotype.Repository;
import com.lvtulife.base.dao.impl.BaseDaoImpl;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import com.lvtulife.gtd.dao.GtdInitParamsDaoI;
import com.lvtulife.gtd.model.GtdInitParams;

/**
 * 业务初始参数表
 * GtdInitParamsDaoImpl component. @author lvtulife Tools
 */
@Repository("GtdInitParamsDao")
public class GtdInitParamsDaoImpl extends BaseDaoImpl<GtdInitParams> implements GtdInitParamsDaoI {

	private static Logger logger = LoggerFactory.getLogger(GtdInitParams.class);

}
