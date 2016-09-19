package com.lvtulife.system.component.vo;

import com.lvtulife.base.component.easyui.Tree;
import com.lvtulife.system.model.SysResourceType;

import java.io.Serializable;
import java.util.List;

/**
 * 用户管理首页加载菜单资源信息
 */
public class MainMenuVo implements Serializable {

    // 菜单类型
    private SysResourceType mtype;

    // 菜单
    private List<Tree> menus;

    public MainMenuVo(SysResourceType mtype, List<Tree> menus) {
        this.mtype = mtype;
        this.menus = menus;
    }

    public SysResourceType getMtype() {
        return mtype;
    }

    public void setMtype(SysResourceType mtype) {
        this.mtype = mtype;
    }

    public List<Tree> getMenus() {
        return menus;
    }

    public void setMenus(List<Tree> menus) {
        this.menus = menus;
    }
}
