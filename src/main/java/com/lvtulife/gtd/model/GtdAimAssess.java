package com.lvtulife.gtd.model;

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
@Table(name = "gtd_aim_assess")
public class GtdAimAssess {
    private Integer id;
    private String assess;
    private Byte ass1;
    private Byte ass2;
    private Byte ass3;
    private Byte ass4;
    private Byte ass5;

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
    @Column(name = "assess", nullable = false, length = 255)
    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    @Basic
    @Column(name = "ass1", nullable = false)
    public Byte getAss1() {
        return ass1;
    }

    public void setAss1(Byte ass1) {
        this.ass1 = ass1;
    }

    @Basic
    @Column(name = "ass2", nullable = false)
    public Byte getAss2() {
        return ass2;
    }

    public void setAss2(Byte ass2) {
        this.ass2 = ass2;
    }

    @Basic
    @Column(name = "ass3", nullable = false)
    public Byte getAss3() {
        return ass3;
    }

    public void setAss3(Byte ass3) {
        this.ass3 = ass3;
    }

    @Basic
    @Column(name = "ass4", nullable = false)
    public Byte getAss4() {
        return ass4;
    }

    public void setAss4(Byte ass4) {
        this.ass4 = ass4;
    }

    @Basic
    @Column(name = "ass5", nullable = false)
    public Byte getAss5() {
        return ass5;
    }

    public void setAss5(Byte ass5) {
        this.ass5 = ass5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GtdAimAssess that = (GtdAimAssess) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (assess != null ? !assess.equals(that.assess) : that.assess != null) return false;
        if (ass1 != null ? !ass1.equals(that.ass1) : that.ass1 != null) return false;
        if (ass2 != null ? !ass2.equals(that.ass2) : that.ass2 != null) return false;
        if (ass3 != null ? !ass3.equals(that.ass3) : that.ass3 != null) return false;
        if (ass4 != null ? !ass4.equals(that.ass4) : that.ass4 != null) return false;
        if (ass5 != null ? !ass5.equals(that.ass5) : that.ass5 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (assess != null ? assess.hashCode() : 0);
        result = 31 * result + (ass1 != null ? ass1.hashCode() : 0);
        result = 31 * result + (ass2 != null ? ass2.hashCode() : 0);
        result = 31 * result + (ass3 != null ? ass3.hashCode() : 0);
        result = 31 * result + (ass4 != null ? ass4.hashCode() : 0);
        result = 31 * result + (ass5 != null ? ass5.hashCode() : 0);
        return result;
    }
}
