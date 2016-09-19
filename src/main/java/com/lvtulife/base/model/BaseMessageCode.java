package com.lvtulife.base.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lvtulife on 2016/3/24 0024.
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "base_message_code")
public class BaseMessageCode {
    private Integer id;
    private Byte codeType;
    private Integer codeValue;
    private String codeInfo;
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
    @Column(name = "code_type", nullable = false)
    public Byte getCodeType() {
        return codeType;
    }

    public void setCodeType(Byte codeType) {
        this.codeType = codeType;
    }

    @Basic
    @Column(name = "code_value", nullable = false)
    public Integer getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(Integer codeValue) {
        this.codeValue = codeValue;
    }

    @Basic
    @Column(name = "code_info", nullable = false, length = 255)
    public String getCodeInfo() {
        return codeInfo;
    }

    public void setCodeInfo(String codeInfo) {
        this.codeInfo = codeInfo;
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

        BaseMessageCode that = (BaseMessageCode) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (codeType != null ? !codeType.equals(that.codeType) : that.codeType != null) return false;
        if (codeValue != null ? !codeValue.equals(that.codeValue) : that.codeValue != null) return false;
        if (codeInfo != null ? !codeInfo.equals(that.codeInfo) : that.codeInfo != null) return false;
        if (del != null ? !del.equals(that.del) : that.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (codeType != null ? codeType.hashCode() : 0);
        result = 31 * result + (codeValue != null ? codeValue.hashCode() : 0);
        result = 31 * result + (codeInfo != null ? codeInfo.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
