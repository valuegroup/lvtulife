package com.lvtulife.base.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lvtulife on 2016-03-24 .
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "base_autocode")
public class BaseAutocode {
    private Integer id;
    private String typeCode;
    private String prefix;
    private Integer lastestNo;
    private String serviceCode;
    private Byte del;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type_code", nullable = false, length = 20)
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "prefix", nullable = false, length = 100)
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "lastest_no", nullable = false)
    public Integer getLastestNo() {
        return lastestNo;
    }

    public void setLastestNo(Integer lastestNo) {
        this.lastestNo = lastestNo;
    }

    @Basic
    @Column(name = "service_code", nullable = false, length = 20)
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Basic
    @Column(name = "del", nullable = false)
    public Byte getDel() {
        return del;
    }

    public void setDel(Byte del) {
        this.del = del;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseAutocode that = (BaseAutocode) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (typeCode != null ? !typeCode.equals(that.typeCode) : that.typeCode != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (lastestNo != null ? !lastestNo.equals(that.lastestNo) : that.lastestNo != null) return false;
        if (serviceCode != null ? !serviceCode.equals(that.serviceCode) : that.serviceCode != null) return false;
        if (del != null ? !del.equals(that.del) : that.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeCode != null ? typeCode.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (lastestNo != null ? lastestNo.hashCode() : 0);
        result = 31 * result + (serviceCode != null ? serviceCode.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
