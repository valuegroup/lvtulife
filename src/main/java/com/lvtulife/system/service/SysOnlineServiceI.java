package com.lvtulife.system.service;

import com.lvtulife.base.service.BaseServiceI;
import com.lvtulife.system.model.SysOnline;

/**
 * 系统用户登录监控业务
 *
 * @author valuegroup
 */
public interface SysOnlineServiceI extends BaseServiceI<SysOnline> {

    /**
     * 用户上下线监听记录保存
     *
     * @param loginName
     * @param ip
     * @param olType
     * @return
     */
    public boolean userOnlineRegister(String loginName, String ip, Byte olType);
}
