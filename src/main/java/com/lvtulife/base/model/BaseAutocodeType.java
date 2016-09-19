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
@Table(name = "base_autocode_type")
public class BaseAutocodeType {
    private Integer id;
    private String typeCode;
    private String typeParentCode;
    private String typeName;
    private String typeLocalName;
    private String jobType;
    private Byte codeType;
    private String autoCodePrefix;
    private Byte seqNoLength;
    private String seqNoRule;
    private Integer seqNoMinValue;
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
    @Column(name = "type_parent_code", nullable = false, length = 20)
    public String getTypeParentCode() {
        return typeParentCode;
    }

    public void setTypeParentCode(String typeParentCode) {
        this.typeParentCode = typeParentCode;
    }

    @Basic
    @Column(name = "type_name", nullable = false, length = 50)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "type_local_name", nullable = false, length = 50)
    public String getTypeLocalName() {
        return typeLocalName;
    }

    public void setTypeLocalName(String typeLocalName) {
        this.typeLocalName = typeLocalName;
    }

    @Basic
    @Column(name = "job_type", nullable = false, length = 20)
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
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
    @Column(name = "auto_code_prefix", nullable = false, length = 100)
    public String getAutoCodePrefix() {
        return autoCodePrefix;
    }

    public void setAutoCodePrefix(String autoCodePrefix) {
        this.autoCodePrefix = autoCodePrefix;
    }

    @Basic
    @Column(name = "seq_no_length", nullable = false)
    public Byte getSeqNoLength() {
        return seqNoLength;
    }

    public void setSeqNoLength(Byte seqNoLength) {
        this.seqNoLength = seqNoLength;
    }

    @Basic
    @Column(name = "seq_no_rule", nullable = false, length = 100)
    public String getSeqNoRule() {
        return seqNoRule;
    }

    public void setSeqNoRule(String seqNoRule) {
        this.seqNoRule = seqNoRule;
    }

    @Basic
    @Column(name = "seq_no_min_value", nullable = false)
    public Integer getSeqNoMinValue() {
        return seqNoMinValue;
    }

    public void setSeqNoMinValue(Integer seqNoMinValue) {
        this.seqNoMinValue = seqNoMinValue;
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

        BaseAutocodeType that = (BaseAutocodeType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (typeCode != null ? !typeCode.equals(that.typeCode) : that.typeCode != null) return false;
        if (typeParentCode != null ? !typeParentCode.equals(that.typeParentCode) : that.typeParentCode != null)
            return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (typeLocalName != null ? !typeLocalName.equals(that.typeLocalName) : that.typeLocalName != null)
            return false;
        if (jobType != null ? !jobType.equals(that.jobType) : that.jobType != null) return false;
        if (codeType != null ? !codeType.equals(that.codeType) : that.codeType != null) return false;
        if (autoCodePrefix != null ? !autoCodePrefix.equals(that.autoCodePrefix) : that.autoCodePrefix != null)
            return false;
        if (seqNoLength != null ? !seqNoLength.equals(that.seqNoLength) : that.seqNoLength != null) return false;
        if (seqNoRule != null ? !seqNoRule.equals(that.seqNoRule) : that.seqNoRule != null) return false;
        if (seqNoMinValue != null ? !seqNoMinValue.equals(that.seqNoMinValue) : that.seqNoMinValue != null)
            return false;
        if (serviceCode != null ? !serviceCode.equals(that.serviceCode) : that.serviceCode != null) return false;
        if (del != null ? !del.equals(that.del) : that.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeCode != null ? typeCode.hashCode() : 0);
        result = 31 * result + (typeParentCode != null ? typeParentCode.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (typeLocalName != null ? typeLocalName.hashCode() : 0);
        result = 31 * result + (jobType != null ? jobType.hashCode() : 0);
        result = 31 * result + (codeType != null ? codeType.hashCode() : 0);
        result = 31 * result + (autoCodePrefix != null ? autoCodePrefix.hashCode() : 0);
        result = 31 * result + (seqNoLength != null ? seqNoLength.hashCode() : 0);
        result = 31 * result + (seqNoRule != null ? seqNoRule.hashCode() : 0);
        result = 31 * result + (seqNoMinValue != null ? seqNoMinValue.hashCode() : 0);
        result = 31 * result + (serviceCode != null ? serviceCode.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
