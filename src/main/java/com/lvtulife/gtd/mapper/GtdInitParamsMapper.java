package com.lvtulife.gtd.mapper;

import com.lvtulife.gtd.model.GtdInitParams;
import org.springframework.stereotype.Repository;

/**
 * 业务初始参数表
 * GtdInitParamsMapper component. @author lvtulife Tools
 */
@Repository("GtdInitParamsMapper")

public interface GtdInitParamsMapper {
    GtdInitParams findGtdInitParams(int id);

}
