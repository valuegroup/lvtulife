package com.lvtulife.system.service.impl;

import java.util.*;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.easyui.Tree;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.utils.BeanUtils;
import com.lvtulife.base.utils.PinyinUtils;
import com.lvtulife.base.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lvtulife.base.service.impl.BaseServiceImpl;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.system.model.SysRole;
import com.lvtulife.system.service.SysRoleServiceI;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统角色业务逻辑
 *
 * @author valuegroup
 */
@Service(value = "SysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleServiceI {
    private static Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    public List<SysRole> findRoleByFilter(HqlFilter hqlFilter, int page, int rows) {
        // String hql = "select distinct t from SysRole t join t.sysUsers user";
        String hql = "select t from SysRole t ";
        return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams(), page, rows);
    }


    public List<SysRole> findRoleByFilter(HqlFilter hqlFilter) {
        String hql = "select t from SysRole t";
        return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
    }

    public Long countRoleByFilter(HqlFilter hqlFilter) {
        // String hql = "select count(distinct t) from SysRole t join t.sysUsers user";
        String hql = "select count(t) from SysRole t";
        return count(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveData(SysRole t) {
        // 保存前校验参数
        if (isExistName(t)) {
            throw new CustomException(Code.C401, "角色名称已存在,请尝试其他名称!");
        }

        if (StringUtils.isBlank(t.getRoleNameEn())) {
            t.setRoleNameEn((PinyinUtils.getChineseToPinyin(t.getRoleName())).toUpperCase());
        }
        t.setSource(SysConstants.SYS_INUSER);
        t.setCreatedDt(new Date());
        t.setUpdatedDt(new Date());
        t.setDel(SysConstants.SYS_STATUS);

        save(t);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteData(int id) {
        SysRole target = getById(id);
        if (target == null) {
            throw new CustomException(Code.C401, "您所修改的角色信息不存在!");
        }
        if (SysConstants.SYS_INSYS == target.getSource().byteValue()) {
            throw new CustomException(Code.C401, "系统内置角色部不允许删除!");
        }

        delete(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDatas(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteData(id);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateData(SysRole t) {
        if (t.getId() == null) {
            throw new CustomException(Code.C401, "主键为空!");
        }
        SysRole target = getById(t.getId());
        if (target == null) {
            throw new CustomException(Code.C401, "您所修改的角色信息不存在!");
        }
        if (SysConstants.SYS_INSYS == target.getSource().byteValue()) {
            throw new CustomException(Code.C401, "系统内置角色部不允许修改!");
        }
        if (isExistName(t)) {
            throw new CustomException(Code.C401, "角色名称已存在,请尝试其他名称!");
        }
        if (StringUtils.isBlank(t.getRoleNameEn())) {
            t.setRoleNameEn((PinyinUtils.getChineseToPinyin(t.getRoleName())).toUpperCase());
        }
        t.setUpdatedDt(new Date());
        BeanUtils.copyNotNullProperties(t, target, new String[]{"del", "createdDt", "resTypeIds", "source"});
        // 保存修改内容
        update(target);
    }

    public boolean isExistName(SysRole t) {
        StringBuffer hql = new StringBuffer("select count(id) from SysRole t where t.roleName=:roleName ");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleName", t.getRoleName());
        if (t.getId() != null) {
            hql.append(" and t.id!=:id");
            params.put("id", t.getId());
        }
        return count(hql.toString(), params) > 0;
    }

    public List<SysRole> findAvailableRoles() {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#rstatus_BT_EQ", Byte.toString(SysConstants.SYS_RSTATUS));
        hqlFilter.addFilter("QUERY_t#del_BT_EQ", Byte.toString(SysConstants.SYS_STATUS));
        return this.findRoleByFilter(hqlFilter);
    }

    public List<Tree> findCustomAvailableRoles() {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#rstatus_BT_EQ", Byte.toString(SysConstants.SYS_RSTATUS));
        hqlFilter.addFilter("QUERY_t#source_BT_EQ", Byte.toString(SysConstants.SYS_INUSER));//用户创建的
        hqlFilter.addFilter("QUERY_t#del_BT_EQ", Byte.toString(SysConstants.SYS_STATUS));
        List<SysRole> roles = this.findRoleByFilter(hqlFilter);

        Collections.sort(roles, new Comparator<SysRole>() {// 排序
            public int compare(SysRole o1, SysRole o2) {
                if (o1.getSort() == null) {
                    o1.setSort(1000);
                }
                if (o2.getSort() == null) {
                    o2.setSort(1000);
                }
                return o1.getSort().compareTo(o2.getSort());
            }
        });
        List<Tree> tree = new ArrayList<Tree>();
        for (SysRole role : roles) {
            Tree node = new Tree();
            node.setId(role.getId());
            node.setText(role.getRoleName());
            tree.add(node);
        }

        return tree;
    }
}
