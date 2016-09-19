package com.lvtulife.system.service.impl;

import java.util.*;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.log.MethodEnum;
import com.lvtulife.base.component.log.annotation.LogService;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.utils.BeanUtils;
import jxl.Sheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lvtulife.base.component.excel.ColumnFiled;
import com.lvtulife.base.component.excel.ExportData;
import com.lvtulife.base.service.impl.BaseServiceImpl;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.base.utils.MD5Util;
import com.lvtulife.base.component.excel.XlsDataConvertUtil;
import com.lvtulife.system.model.SysUser;
import com.lvtulife.system.service.SysUserServiceI;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统用户业务逻辑
 *
 * @author valuegroup
 */
@Service(value = "SysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserServiceI {
    private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    // 默认密码
    private static final String DEFAULT_PWD = "q";

    public List<Long> userCreateDatetimeChart() {
        List<Long> l = new ArrayList<Long>();
        int k = 0;
        for (int i = 0; i < 12; i++) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("a", k);
            params.put("b", k + 2);
            k = k + 2;
            l.add(count("select count(*) from SysUser t where HOUR(t.createdDt)>=:a and HOUR(t.createdDt)<:b", params));
        }
        return l;
    }

    public SysUser getUserByLoginName(String loginName) {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#loginName_S_EQ", loginName);
        return getByFilter(hqlFilter);
    }

    @LogService(description = "批量保存用户信息",type = MethodEnum.Create)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveAll(List<SysUser> entityList) {
        if (entityList.size() == 0) {
            throw new CustomException(Code.C402);
        }

        // 循环检查
        StringBuffer sb = new StringBuffer("登录名重复的有：");
        boolean isRepeat = false;
        for (SysUser o : entityList) {
            SysUser user = getUserByLoginName(o.getLoginName());
            if (user != null) {
                isRepeat = true;
                sb.append(user.getLoginName()+"\t");
            }
        }

        // 判断是否有重复数据，没有重复数据则批量保存
        if(isRepeat){
            throw new CustomException(Code.C401,sb.toString());
        }else{
            for (SysUser o : entityList) {
                saveData(o);
            }
        }
    }


    public List<SysUser> importExcelFile(Sheet[] sheets) {
        List<SysUser> entityList = new ArrayList<SysUser>();
        if (sheets != null && sheets.length <= 0) {
            return entityList;
        }

        // 默认只导入EXCEL第1个sheet
        Sheet sheet = sheets[0];
        XlsDataConvertUtil xlsDataConvert = new XlsDataConvertUtil();
        int rowNum = xlsDataConvert.getImportRowsCount(sheet);
        List<HashMap<String, Object>> rowList = xlsDataConvert.sheetToList(this.getFiled(), sheet);
        for (HashMap<String, Object> rowData : rowList) {

            String userName = xlsDataConvert.getStringRowData(rowData, "用户名");
            String loginName = xlsDataConvert.getStringRowData(rowData, "登录名");
            String email = xlsDataConvert.getStringRowData(rowData, "邮箱地址");
            Byte sex = xlsDataConvert.getByteRowData(rowData, "性别");
            Byte age = xlsDataConvert.getByteRowData(rowData, "年龄");

            SysUser user = new SysUser();
            setPinyins(userName, user);// 设置拼音
            user.setUserName(userName);
            user.setLoginName(loginName);
            user.setPwd(MD5Util.md5_SystemWideSaltSource(DEFAULT_PWD, user.getLoginName()));
            user.setEmail(email);
			user.setSex(sex);
            user.setAge(age);
            user.setIsSuper(SysConstants.SYS_NO);
            user.setUstatus(SysConstants.SYS_USTATUS);
            user.setDel(SysConstants.SYS_STATUS);
            entityList.add(user);
        }

        return entityList;
    }


    public void exportData(WritableWorkbook workbook, HqlFilter hqlFilter) throws WriteException {
        List<SysUser> list = findByFilter(hqlFilter);
        XlsDataConvertUtil xlsDataConvert = new XlsDataConvertUtil();
        ExportData exportData = xlsDataConvert.buildExportDataByBean("用户信息", this.getFiled(), list);
        xlsDataConvert.listToSheet(exportData, workbook);
    }

    public void setPinyins(String userName, SysUser user) {
        // 转拼音
        if (userName.trim().length() > 0) {
            /*user.setPinyin(PinyinUtils.getChineseToPinyin(userName));
            user.setPy(PinyinUtils.getChineseToPy(userName));*/
        }
    }

    public static List<ColumnFiled> getFiled() {
        List<ColumnFiled> columnFileds = new ArrayList<ColumnFiled>();
        // 构造表的列头
        columnFileds.add(new ColumnFiled("用户名", "userName", 0));
        columnFileds.add(new ColumnFiled("登录名", "loginName", 1));
        columnFileds.add(new ColumnFiled("邮箱地址", "email", 2));
        //columnFileds.add(new ColumnFiled("手机号码", "mobile", ));
        columnFileds.add(new ColumnFiled("性别", "sex", 3));
        columnFileds.add(new ColumnFiled("年龄", "age", 4));
        return columnFileds;
    }

    @LogService(description = "保存用户信息",type = MethodEnum.Create)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveData(SysUser t) {
        // 保存前校验参数
        if (isExistName(t)) {
            throw new CustomException(Code.C401, "用户名称已存在,请尝试其他名称!");
        }

        t.setPwd(MD5Util.md5_SystemWideSaltSource(DEFAULT_PWD, t.getLoginName()));
        if (t.getAge() == null) {
            t.setAge(new Byte("1"));
        }
        t.setCreatedDt(new Date());
        t.setUpdatedDt(new Date());
        t.setUstatus(SysConstants.SYS_USTATUS);
        t.setIsSuper(SysConstants.SYS_NO);
        t.setDel(SysConstants.SYS_STATUS);

        save(t);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @LogService(description = "修改用户信息",type = MethodEnum.Update)
    public void updateData(SysUser t) {
        if (t.getId() == null) {
            throw new CustomException(Code.C401, "主键为空!");
        }
        SysUser target = getById(t.getId());
        if (target == null) {
            throw new CustomException(Code.C401, "您所修改的用户信息不存在!");
        }
        if (isExistName(t)) {
            throw new CustomException(Code.C401, "用户名称已存在,请尝试其他名称!");
        }

        t.setUpdatedDt(new Date());

        BeanUtils.copyNotNullProperties(t, target, new String[]{"del", "createdDt", "pwd", "isSuper", "ustatus"});
        target.setIsSuper(SysConstants.SYS_NO);
        // 保存修改内容
        update(target);
    }

    @LogService(description = "删除用户信息",type = MethodEnum.Delete)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteData(int id) {
        SysUser target = getById(id);
        if (target == null) {
            throw new CustomException(Code.C401, "您所操作的用户信息不存在!");
        }
        if (SysConstants.SYS_YES == target.getIsSuper().byteValue()) {
            throw new CustomException(Code.C401, "管理员用户不允许直接删除，需解除管理员状态!");
        }

        delete(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDatas(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteData(id);
        }
    }

    public boolean isExistName(SysUser t) {
        StringBuffer hql = new StringBuffer("select count(id) from SysUser t where t.loginName=:loginName ");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("loginName", t.getLoginName());
        if (t.getId() != null) {
            hql.append(" and t.id!=:id");
            params.put("id", t.getId());
        }
        return count(hql.toString(), params) > 0;
    }

    public List<Long> statiUserCreateDtChart() {
        List<Long> l = new ArrayList<Long>();
        int k = 0;
        for (int i = 0; i < 12; i++) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("a", k);
            params.put("b", k + 2);
            k = k + 2;
            l.add(count("select count(*) from SysUser t where HOUR(t.createdDt)>=:a and HOUR(t.createdDt)<:b", params));
        }
        return l;
    }

    @LogService(description = "修改用户状态",type = MethodEnum.Update)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void changeUserStatus(int id, String todo) {
        if (0 == id || StringUtils.isBlank(todo)) {
            throw new CustomException(Code.C401, "参数不完完整!");
        }
        SysUser target = getById(id);
        if (target == null) {
            throw new CustomException(Code.C401, "您所修改的用户信息不存在!");
        }

        if ("1".equals(todo)) {
            // 设置超级管理员
            if (target.getIsSuper().byteValue() == SysConstants.SYS_NO) {
                target.setIsSuper(SysConstants.SYS_YES);
            } else {
                target.setIsSuper(SysConstants.SYS_NO);
            }
        } else if ("2".equals(todo)) {
            // 设置用户状态
            if (target.getUstatus().byteValue() == SysConstants.SYS_USTATUS_BAN) {
                target.setUstatus(SysConstants.SYS_USTATUS);
            } else {
                target.setUstatus(SysConstants.SYS_USTATUS_BAN);
            }
        }
        target.setUpdatedDt(new Date());

        // 保存修改内容
        update(target);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void changeUserStatusMuiti(List<Integer> ids, String todo) {
        for (Integer id : ids) {
            this.changeUserStatus(id, todo);
        }
    }
}