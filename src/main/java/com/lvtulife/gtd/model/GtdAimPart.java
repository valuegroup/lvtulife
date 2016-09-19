package com.lvtulife.gtd.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lvtulife on 2016-03-24 .
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "gtd_aim_part")
public class GtdAimPart implements Serializable {
    private Integer id;
    private Byte dpart;
    private Date startDt;
    private Date endDt;

    public GtdAimPart() {
    }

    public GtdAimPart(Integer id, Byte dpart, Date startDt, Date endDt) {
        this.id = id;
        this.dpart = dpart;
        this.startDt = startDt;
        this.endDt = endDt;
    }

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
    @Column(name = "dpart", nullable = false)
    public Byte getDpart() {
        return dpart;
    }

    public void setDpart(Byte dpart) {
        this.dpart = dpart;
    }

    @Basic
    @Column(name = "start_dt", nullable = false)
    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    @Basic
    @Column(name = "end_dt", nullable = false)
    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GtdAimPart that = (GtdAimPart) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dpart != null ? !dpart.equals(that.dpart) : that.dpart != null) return false;
        if (startDt != null ? !startDt.equals(that.startDt) : that.startDt != null) return false;
        if (endDt != null ? !endDt.equals(that.endDt) : that.endDt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dpart != null ? dpart.hashCode() : 0);
        result = 31 * result + (startDt != null ? startDt.hashCode() : 0);
        result = 31 * result + (endDt != null ? endDt.hashCode() : 0);
        return result;
    }
}
