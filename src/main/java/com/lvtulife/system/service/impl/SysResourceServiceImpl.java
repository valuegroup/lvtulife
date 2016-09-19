package com.lvtulife.system.service.impl;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.easyui.Tree;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.service.impl.BaseServiceImpl;
import com.lvtulife.base.utils.BeanUtils;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.base.utils.StringUtil;
import com.lvtulife.system.model.SysResource;
import com.lvtulife.system.service.SysResourceServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统资源业务逻辑
 *
 * @author valuegroup
 */
@Service(value = "SysResourceService")
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceServiceI {
    private static Logger logger = LoggerFactory.getLogger(SysResourceServiceImpl.class);

    public List<SysResource> findResources(HqlFilter hqlFilter) {
        // 角色
        String hql = "select t from SysResource t ";
        List<SysResource> resource_role = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());

        // 机构

        // 合集
        List<SysResource> list = new ArrayList<SysResource>(resource_role);

        // 去重
        list = new ArrayList<SysResource>(new HashSet<SysResource>(list));

        // 排序
        resourceSort(list);
        return list;
    }

    public List<Tree> findResourcesShowResAttrToTrees(HqlFilter hqlFilter) {
        List<Tree> tree = new ArrayList<Tree>();
        listConvertTreeShowResAttr(tree, findResources(hqlFilter));
        return tree;
    }

    public List<Tree> findResourcesToTrees(HqlFilter hqlFilter) {
        List<Tree> tree = new ArrayList<Tree>();
        listConvertTree(tree, findResources(hqlFilter));
        return tree;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateData(SysResource t) {
        if (t.getId() == null) {
            throw new CustomException(Code.C401, "主键为空!");
        }
        SysResource target = getById(t.getId());
        if (target == null) {
            throw new CustomException(Code.C401, "您所修改的资源信息不存在!");
        }
        if (isExistName(t)) {
            throw new CustomException(Code.C401, "资源名已存在,请尝试其他名称!");
        }
        // 判断节点是否越级(当前节点不可修改为当前节点的下级节点)
        if (isParentToChild(t, target)) {
            throw new CustomException(Code.C401, "节点越级,上级资源不可以为当前资源的子节点!");
        }
        SysResource parentRes = null;
        if (null != target.getPid() && 0 != target.getPid().intValue()) {
            parentRes = getById(target.getPid());
            if (parentRes == null) {
                throw new CustomException(Code.C401, "您所指定的上级资源信息不存在!");
            }
        }

        try {
            // 设置资源级别
            if (target.getPid() == null || 0 == target.getPid().intValue()) {
                t.setPid(0);
                t.setResLevel(new Byte("0"));
            } else {
                Byte s = (byte) ((target.getResLevel() == null ? 0 : parentRes.getResLevel()) + 1);
                t.setResLevel(s);
            }
            BeanUtils.copyNotNullProperties(t, target, new String[]{"createdDt", "resLeaf"});

            // 保存修改内容
            update(target);

            // 更新父节点为非叶子节点
            if (parentRes != null) {
                parentRes.setResLeaf(SysConstants.SYS_NO);
                update(parentRes);
            }
        } catch (Exception e) {
            logger.error("保存失败", e);
        }
    }

    /**
     * 资源删除方法
     *
     * @param id
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteData(Integer id) {
        SysResource t = getById(id);
        if (t == null) {
            throw new CustomException(Code.C400, "需删除的对象[" + id + "]不存在!");
        }

        // 删除时需要验证是否存在子节点
        List<SysResource> childRes = new ArrayList<SysResource>();
        setChildRes(childRes, find(), id);
        if (childRes.size() > 0) {
            throw new CustomException(Code.C400, "检测到当前节点[" + id + "]下存在子节点,请先删除子节点!");
        } else {
            delete(t);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDatas(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteData(id);
        }
    }

    /**
     * 判断节点是否越级,越级返回true
     *
     * @param newRes
     * @param oldRes
     * @return
     */
    public boolean isParentToChild(SysResource newRes, SysResource oldRes) {
        if (newRes.getPid().intValue() == oldRes.getPid().intValue()) {
            return false;
        }

        // 查询出所有的资源
        List<SysResource> childRes = new ArrayList<SysResource>();
        setChildRes(childRes, find(), oldRes.getId());
        for (SysResource child : childRes) {
            if (child.getId().intValue() == newRes.getPid().intValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得子节点
     *
     * @param childRes
     * @param allRes
     * @param id
     */
    private void setChildRes(List<SysResource> childRes, List<SysResource> allRes, Integer id) {
        for (SysResource sysResource : allRes) {
            if (sysResource.getPid() != null && sysResource.getPid().intValue() == id.intValue()) {
                childRes.add(sysResource);
                setChildRes(childRes, allRes, sysResource.getId());
            }
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveData(SysResource t) {
        // 保存前校验参数
        if (isExistName(t)) {
            throw new CustomException(Code.C401, "资源名已存在,请尝试其他名称!");
        }

        // 设置资源级别
        if (t.getPid() == null) {
            t.setPid(0);// 当没有指定父节点时，则自己为父节点
            t.setResLevel(new Byte("0"));
        } else {
            SysResource parentResource = getById(t.getPid());
            Byte s = (byte) ((parentResource.getResLevel() == null ? 0 : parentResource.getResLevel()) + 1);
            t.setResLevel(s);

        }
        // 是否为子节点
        t.setResLeaf(SysConstants.SYS_YES);

        // 保存资源信息
        save(t);

        // 角色资源信息

    }

    public boolean isExistName(SysResource t) {
        StringBuffer hql = new StringBuffer("select count(id) from SysResource t where t.resName=:resName ");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("resName", t.getResName());
        if (t.getId() != null) {
            hql.append(" and t.id!=:id");
            params.put("id", t.getId());
        }
        return count(hql.toString(), params) > 0;
    }

    public List<SysResource> findResourceByFilter(HqlFilter hqlFilter) {
        String hql = "select distinct t from SysResource t left join t.sysRespris role";
        return find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
    }

    public int getResourceMaxSeq(Integer pid) {
        String hql = "select MAX(t.sort) from SysResource t where t.pid=:pid";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pid", pid);
        int sort = minOrMax(hql, params);
        if (sort < 10) {
            return sort = 10;
        } else {
            return ++sort;
        }
    }

    public SysResource getRootResource(String type) {
        String hql = "from SysResource t where t.typeId=:typeId and t.id is null";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("typeId", type);
        return getByHql(hql, params);
    }

    public boolean isExistUrl(String url) {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#url_S_EQ", url);
        SysResource resource = getByFilter(hqlFilter);
        if (resource != null) {
            return true;
        }
        return false;
    }

    public HashMap<Integer, SysResource> findResourcesToMap() {
        List<SysResource> list = find();
        return listToMap(list);
    }

    public HashMap<Integer, SysResource> findResourcesToMap(Integer typeId) {
        if (null == typeId) {
            return findResourcesToMap();
        } else {
            HqlFilter hqlFilter = new HqlFilter();
            hqlFilter.addFilter("QUERY_t#typeId_I_EQ", typeId.toString());
            List<SysResource> list = findByFilter(hqlFilter);
            return listToMap(list);
        }
    }

    public HashMap<Integer, SysResource> findMenuToMap() {
        return listToMap(findMenu());
    }

    public HashMap<Integer, SysResource> findMenuToMap(Integer typeId) {
        if (null == typeId) {
            return findMenuToMap();
        } else {
            return listToMap(findMenu(typeId));
        }
    }

    public List<SysResource> findMenu() {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#resAttr_BT_EQ", SysConstants.SYS_RES_ATTR_MENU);
        return findByFilter(hqlFilter);
    }

    public List<SysResource> findMenu(Integer typeId) {
        if (null == typeId) {
            return findMenu();
        } else {
            HqlFilter hqlFilter = new HqlFilter();
            hqlFilter.addFilter("QUERY_t#resAttr_BT_EQ", SysConstants.SYS_RES_ATTR_MENU);
            hqlFilter.addFilter("QUERY_t#typeId_I_EQ", typeId.toString());
            return findByFilter(hqlFilter);
        }
    }

    private HashMap<Integer, SysResource> listToMap(List<SysResource> list) {
        Iterator<SysResource> itr = list.iterator();
        HashMap<Integer, SysResource> map = new HashMap<Integer, SysResource>();
        while (itr.hasNext()) {
            SysResource r = itr.next();
            map.put(r.getId(), r);
        }
        return map;
    }

    private HashMap<String, SysResource> listToUrlMap(List<SysResource> list) {
        Iterator<SysResource> itr = list.iterator();
        HashMap<String, SysResource> map = new HashMap<String, SysResource>();
        while (itr.hasNext()) {
            SysResource r = itr.next();
            map.put("/" + r.getUrlHead() + "/" + r.getUrlModule() + "/" + r.getUrlMethod(), r);
        }
        return map;
    }

    public void listConvertTree(List<Tree> tree, List<SysResource> resources) {
        // 将取出的资源排序
        resourceSort(resources);

        for (SysResource r : resources) {
            Tree node = new Tree();
            //BeanUtils.copyNotNullProperties(r, node);
            node.setId(r.getId());
            node.setPid(r.getPid());
            node.setText(r.getResName());
            node.setIconCls(r.getIconCls());
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("url", StringUtil.splitJointUrl(r.getUrlHead(), r.getUrlModule(), r.getUrlMethod()));
            attributes.put("target", r.getTarget());
            attributes.put("typeId", r.getTypeId().toString());
            node.setAttributes(attributes);
            tree.add(node);
        }
    }

    public void listConvertTreeShowResAttr(List<Tree> tree, List<SysResource> resources) {
        // 将取出的资源排序
        resourceSort(resources);


        for (SysResource r : resources) {
            Tree node = new Tree();
            //BeanUtils.copyNotNullProperties(r, node);

            boolean isMenu = r.getResAttr().byteValue() == SysConstants.SYS_RES_ATTR_MENU;

            node.setId(r.getId());
            node.setPid(r.getPid());
            node.setText(r.getResName() + (!isMenu ? "  [功能]" : ""));
            node.setIconCls(r.getIconCls());
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("url", StringUtil.splitJointUrl(r.getUrlHead(), r.getUrlModule(), r.getUrlMethod()));
            attributes.put("target", r.getTarget());
            attributes.put("typeId", r.getTypeId().toString());
            node.setAttributes(attributes);

            // 如果是含基础权限点，则需要添加四个基础功能节点（增删改查）
            if (r.getIsAuthPoint().byteValue() == SysConstants.SYS_YES) {
                node.getChildren().add(new Tree(r.getId() + SysConstants.AUTH_POINT_FIND_VALUE, treeTextStyle("查阅数据"), attributes, "ext-icon-bullet_orange", r.getId()));
                node.getChildren().add(new Tree(r.getId() + SysConstants.AUTH_POINT_SAVE_VALUE, treeTextStyle("新增数据"), attributes, "ext-icon-bullet_purple", r.getId()));
                node.getChildren().add(new Tree(r.getId() + SysConstants.AUTH_POINT_EDIT_VALUE, treeTextStyle("编辑数据"), attributes, "ext-icon-bullet_red", r.getId()));
                node.getChildren().add(new Tree(r.getId() + SysConstants.AUTH_POINT_REMOVE_VALUE, treeTextStyle("删除数据"), attributes, "ext-icon-bullet_blue", r.getId()));
            }

            tree.add(node);
        }
    }

    private String treeTextStyle(String name) {
        return StringUtil.formateString("<font color='blue'>{0}</font>  [基础功能]", name);
    }

    private void resourceSort(List<SysResource> resources) {
        Collections.sort(resources, new Comparator<SysResource>() {// 排序
            public int compare(SysResource o1, SysResource o2) {
                if (o1.getSort() == null) {
                    o1.setSort(1000);
                }
                if (o2.getSort() == null) {
                    o2.setSort(1000);
                }
                return o1.getSort().compareTo(o2.getSort());
            }
        });
    }


    public HashMap<String, SysResource> findAllMenuToUrlMap() {
        return this.listToUrlMap(findMenu());
    }

    public boolean isUrlExist(String urlHead, String urlModule, String urlMethod) {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#urlHead_S_EQ", urlHead);
        hqlFilter.addFilter("QUERY_t#urlModule_S_EQ", urlModule);
        hqlFilter.addFilter("QUERY_t#urlMethod_S_EQ", urlMethod);
        SysResource t = getByFilter(hqlFilter);
        if (t != null) {
            return true;
        }
        return false;
    }

}
