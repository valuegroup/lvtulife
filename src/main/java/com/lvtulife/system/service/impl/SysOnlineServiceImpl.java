package com.lvtulife.system.service.impl;

import com.lvtulife.base.component.constants.SysConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lvtulife.base.service.impl.BaseServiceImpl;
import com.lvtulife.system.model.SysOnline;
import com.lvtulife.system.service.SysOnlineServiceI;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 系统在线统计业务逻辑
 *
 * @author lvtulife
 */
@Service(value = "SysOnlineService")
public class SysOnlineServiceImpl extends BaseServiceImpl<SysOnline> implements SysOnlineServiceI {
    private static Logger logger = LoggerFactory.getLogger(SysOnlineServiceImpl.class);


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean userOnlineRegister(String loginName, String ip, Byte olType) {
        SysOnline online = new SysOnline();
        online.setOlType(olType);
        online.setLoginName(loginName);
        online.setIp(ip);
        online.setCreatedDt(new Date());
        online.setDel(SysConstants.SYS_STATUS);
        boolean result = false;
        try {
            this.save(online);
            result = true;
        } catch (Exception e) {
            logger.error("用户上下线记录保存失败！", e);
        }
        return result;
    }
}
