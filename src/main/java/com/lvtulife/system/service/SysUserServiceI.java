package com.lvtulife.system.service;

import java.util.List;

import com.lvtulife.base.component.log.MethodEnum;
import com.lvtulife.base.component.log.annotation.LogService;
import jxl.Sheet;
import jxl.write.WritableWorkbook;

import com.lvtulife.base.service.BaseServiceI;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.system.model.SysUser;
import jxl.write.WriteException;

/**
 * 系统用户业务
 *
 * @author valuegroup
 */
public interface SysUserServiceI extends BaseServiceI<SysUser> {

    /**
     * 统计用户注册时间图表
     *
     * @return
     */
    public List<Long> userCreateDatetimeChart();

    /**
     * 通过Excel批量导入
     *
     * @param sheets
     * @return
     */
    public List<SysUser> importExcelFile(Sheet[] sheets);

    /**
     * 批量导入
     *
     * @param entityList
     * @return
     */
    public void saveAll(List<SysUser> entityList);

    /**
     * 设置拼音
     *
     * @param userName
     * @param user
     */
    public void setPinyins(String userName, SysUser user);

    /**
     * 导出的用户数据
     *
     * @param workbook
     * @param hqlFilter
     */
    public void exportData(WritableWorkbook workbook, HqlFilter hqlFilter) throws WriteException;

    /**
     * 保存用户信息
     */
    public void saveData(SysUser data);

    /**
     * 更新用户信息
     */
    @LogService(description = "修改用户信息",type = MethodEnum.Update)
    public void updateData(SysUser data);

    /**
     * 删除用户
     * @param id
     */
    public void deleteData(int id);

    /**
     * 批量删除用户
     *
     * @param ids
     */
    public void deleteDatas(List<Integer> ids);


    /**
     * 查看登录名是否被占用
     *
     * @param t
     * @return
     */
    public boolean isExistName(SysUser t);

    /**
     * 通过用户名查找用户信息
     *
     * @param loginName
     * @return
     */
    public SysUser getUserByLoginName(String loginName);

    /**
     * 统计用户注册时间图表
     *
     * @return
     */
    public List<Long> statiUserCreateDtChart();

    /**
     * 更改用户状态（1设置超级管理员，2设置用户状态）
     *
     * @param todo
     */
    public void changeUserStatus(int id, String todo);

    /**
     * 批量更改用户状态（1设置超级管理员，2设置用户状态）
     *
     * @param todo
     */
    public void changeUserStatusMuiti(List<Integer> ids, String todo);
}
