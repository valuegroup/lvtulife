package com.lvtulife.system.component.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lvtulife on 2016-03-27 .
 */
public class SysUserVo implements Serializable {
    private String ip;// 此属性不存数据库，虚拟属性
    private String resTypeIds;// 此属性不存数据库，虚拟属性
    private String roleIds;// 此属性不存数据库，虚拟属性

    private Integer id;
    private String userName;
    private String loginName;
    private String email;
    private String photo;
    private Byte sex;
    private Byte ustatus;
    private Byte sort;
    private Byte age;
    private Date createdDt;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getResTypeIds() {
        return resTypeIds;
    }

    public void setResTypeIds(String resTypeIds) {
        this.resTypeIds = resTypeIds;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getUstatus() {
        return ustatus;
    }

    public void setUstatus(Byte ustatus) {
        this.ustatus = ustatus;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }
}
