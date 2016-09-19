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
@Table(name = "gtd_aim_branch")
public class GtdAimBranch {
    private Integer id;
    private Byte aimUnit;
    private Integer giid;
    private Integer yid;
    private Integer sid;
    private Integer moid;
    private Byte sort;

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
    @Column(name = "aim_unit", nullable = false)
    public Byte getAimUnit() {
        return aimUnit;
    }

    public void setAimUnit(Byte aimUnit) {
        this.aimUnit = aimUnit;
    }

    @Basic
    @Column(name = "giid", nullable = false)
    public Integer getGiid() {
        return giid;
    }

    public void setGiid(Integer giid) {
        this.giid = giid;
    }

    @Basic
    @Column(name = "yid", nullable = false)
    public Integer getYid() {
        return yid;
    }

    public void setYid(Integer yid) {
        this.yid = yid;
    }

    @Basic
    @Column(name = "sid", nullable = false)
    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    @Basic
    @Column(name = "moid", nullable = false)
    public Integer getMoid() {
        return moid;
    }

    public void setMoid(Integer moid) {
        this.moid = moid;
    }

    @Basic
    @Column(name = "sort", nullable = false)
    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GtdAimBranch that = (GtdAimBranch) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (aimUnit != null ? !aimUnit.equals(that.aimUnit) : that.aimUnit != null) return false;
        if (giid != null ? !giid.equals(that.giid) : that.giid != null) return false;
        if (yid != null ? !yid.equals(that.yid) : that.yid != null) return false;
        if (sid != null ? !sid.equals(that.sid) : that.sid != null) return false;
        if (moid != null ? !moid.equals(that.moid) : that.moid != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (aimUnit != null ? aimUnit.hashCode() : 0);
        result = 31 * result + (giid != null ? giid.hashCode() : 0);
        result = 31 * result + (yid != null ? yid.hashCode() : 0);
        result = 31 * result + (sid != null ? sid.hashCode() : 0);
        result = 31 * result + (moid != null ? moid.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }
}
