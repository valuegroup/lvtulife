package com.lvtulife.gtd.service;

import com.lvtulife.base.mapper.BaseMapperI;
import com.lvtulife.gtd.model.GtdInitParams;

/**
 * 业务初始参数表
 * GtdInitParamsServiceI component. @author lvtulife Tools
 */
public interface GtdInitParamsServiceI extends BaseMapperI<GtdInitParams> {


    /**
     * 设置初始年份
     *
     * 初次设置？
     * 修改年份？
     */

    /**
     * 获取用户所在的阶段，年度，季度，月度，月度第几周，年度第几周信息
     *
     * 依据当前时间判断
     */

    /**
     *  初始化所有参数
     *  ps: 用于参数初始化，和手动初始化数据
     *  匹配用户信息
     */

    /**
     *  获取初始参数
     *  ps:不存在时自动的创建，每个用户只存在一条记录
     */
}
