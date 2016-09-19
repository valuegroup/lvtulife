package com.lvtulife.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvtulife.base.component.easyui.Tree;
import com.lvtulife.base.service.BaseServiceI;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.system.model.SysResource;

/**
 * 资源业务逻辑
 *
 * @author valuegroup
 */
public interface SysResourceServiceI extends BaseServiceI<SysResource> {

    /**
     * 获得资源列表
     *
     * @return
     */
    public List<SysResource> findResources(HqlFilter hqlFilter);

    /**
     * 获得资源树列表,显示基础权限点
     */
    public List<Tree> findResourcesShowResAttrToTrees(HqlFilter hqlFilter);

    /**
     * 获得资源树列表
     */
    public List<Tree> findResourcesToTrees(HqlFilter hqlFilter);

    /**
     * 更新资源
     */
    public void updateData(SysResource syresource);

    /**
     * 保存一个资源
     */
    public void saveData(SysResource syresource);

    /**
     * 查找符合条件的资源
     */
    public List<SysResource> findResourceByFilter(HqlFilter hqlFilter);

    /**
     * 获取当前父节点下所有子节点中最大的序号
     *
     * @return
     */
    public int getResourceMaxSeq(Integer resPid);

    /**
     * 获取指定资源类型下根资源对象
     *
     * @param type
     * @return
     */
    public SysResource getRootResource(String type);

    /**
     * 验证指定URL是否存在
     *
     * @param url
     * @return
     */
    public boolean isExistUrl(String url);

    /**
     * 获取所有资源map
     *
     * @return
     */
    public HashMap<Integer, SysResource> findResourcesToMap();

    /**
     * 通过资源类型获取类型下所有资源map
     *
     * @param typeId
     * @return
     */
    public HashMap<Integer, SysResource> findResourcesToMap(Integer typeId);

    /**
     * 通过资源类型获取所有属性为菜单的资源map
     *
     * @param typeId
     * @return
     */
    public HashMap<Integer, SysResource> findMenuToMap(Integer typeId);

    /**
     * 获取所有属性为菜单的资源map
     *
     * @return
     */
    public HashMap<Integer, SysResource> findMenuToMap();

    /**
     * 通过资源类型获取所有属性为菜单的资源
     *
     * @param typeId
     * @return
     */
    public List<SysResource> findMenu(Integer typeId);

    /**
     * 获取所有属性为菜单的资源
     *
     * @return
     */
    public List<SysResource> findMenu();

    /**
     * 校验资源名是否存在,存在返回true
     * ps:适用于新增和修改,如新增时id需为空
     *
     * @param t
     * @return
     */
    public boolean isExistName(SysResource t);

    /**
     * 资源删除方法
     *
     * @param id
     */
    public void deleteData(Integer id);

    /**
     * 资源批量删除方法
     *
     * @param ids
     */
    public void deleteDatas(List<Integer> ids);

    /**
     * 将资源集合转换成tree集合
     *
     * @param tree
     * @param resources
     */
    public void listConvertTree(List<Tree> tree, List<SysResource> resources);

    /**
     * 查询所有菜单,转换为以url为Key的map
     * @return
     */
    public HashMap<String, SysResource> findAllMenuToUrlMap();

    /**
     * 判断资源链接是否存在
     * @param urlHead
     * @param urlModule
     * @param urlMethod
     */
    public boolean isUrlExist(String urlHead,String urlModule,String urlMethod);

}
