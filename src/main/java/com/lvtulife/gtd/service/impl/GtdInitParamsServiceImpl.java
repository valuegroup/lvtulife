package com.lvtulife.gtd.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.lvtulife.gtd.dao.GtdInitParamsDaoI;
import com.lvtulife.gtd.service.GtdInitParamsServiceI;
import com.lvtulife.gtd.model.GtdInitParams;
import com.lvtulife.gtd.mapper.GtdInitParamsMapper;
import com.lvtulife.base.mapper.impl.BaseMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 业务初始参数表
 * GtdInitParamsServiceImpl component. @author lvtulife Tools
 */
@Service("GtdInitParamsService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class GtdInitParamsServiceImpl extends BaseMapperImpl<GtdInitParams> implements GtdInitParamsServiceI {

    private static Logger logger = LoggerFactory.getLogger(GtdInitParams.class);

    @Resource(name = "GtdInitParamsDao")
    private GtdInitParamsDaoI gtdInitParamsDao;

    @Resource(name = "GtdInitParamsMapper")
    private GtdInitParamsMapper gtdInitParamsMapper;

}
