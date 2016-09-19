package com.lvtulife.base.mapper;

import com.lvtulife.base.model.BaseLog;
import org.springframework.stereotype.Repository;

/**
 * 系统日志
 * BaseLogMapper component. @author lvtulife Tools
 */
@Repository("BaseLogMapper")

public interface BaseLogMapper {
    BaseLog findBaseLog(int id);

}
