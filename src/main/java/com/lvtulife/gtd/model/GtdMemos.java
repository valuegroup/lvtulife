package com.lvtulife.gtd.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lvtulife on 2016-03-24 .
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "gtd_memos")
public class GtdMemos {
    private Integer id;
    private Integer gid;
    private Integer uid;
    private String mtitle;
    private String mcontent;
    private Byte fontSize;
    private Byte bgStyle;
    private Byte star;
    private Byte isEncrypt;
    private Byte top;
    private Date createdDt;
    private Date updatedDt;
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
    @Column(name = "gid", nullable = false)
    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    @Basic
    @Column(name = "uid", nullable = false)
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "mtitle", nullable = true, length = 255)
    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    @Basic
    @Column(name = "mcontent", nullable = true, length = -1)
    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    @Basic
    @Column(name = "font_size", nullable = false)
    public Byte getFontSize() {
        return fontSize;
    }

    public void setFontSize(Byte fontSize) {
        this.fontSize = fontSize;
    }

    @Basic
    @Column(name = "bg_style", nullable = false)
    public Byte getBgStyle() {
        return bgStyle;
    }

    public void setBgStyle(Byte bgStyle) {
        this.bgStyle = bgStyle;
    }

    @Basic
    @Column(name = "star", nullable = false)
    public Byte getStar() {
        return star;
    }

    public void setStar(Byte star) {
        this.star = star;
    }

    @Basic
    @Column(name = "is_encrypt", nullable = false)
    public Byte getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(Byte isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    @Basic
    @Column(name = "top", nullable = false)
    public Byte getTop() {
        return top;
    }

    public void setTop(Byte top) {
        this.top = top;
    }

    @Basic
    @Column(name = "created_dt", nullable = false)
    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    @Basic
    @Column(name = "updated_dt", nullable = false)
    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
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

        GtdMemos gtdMemos = (GtdMemos) o;

        if (id != null ? !id.equals(gtdMemos.id) : gtdMemos.id != null) return false;
        if (gid != null ? !gid.equals(gtdMemos.gid) : gtdMemos.gid != null) return false;
        if (uid != null ? !uid.equals(gtdMemos.uid) : gtdMemos.uid != null) return false;
        if (mtitle != null ? !mtitle.equals(gtdMemos.mtitle) : gtdMemos.mtitle != null) return false;
        if (mcontent != null ? !mcontent.equals(gtdMemos.mcontent) : gtdMemos.mcontent != null) return false;
        if (fontSize != null ? !fontSize.equals(gtdMemos.fontSize) : gtdMemos.fontSize != null) return false;
        if (bgStyle != null ? !bgStyle.equals(gtdMemos.bgStyle) : gtdMemos.bgStyle != null) return false;
        if (star != null ? !star.equals(gtdMemos.star) : gtdMemos.star != null) return false;
        if (isEncrypt != null ? !isEncrypt.equals(gtdMemos.isEncrypt) : gtdMemos.isEncrypt != null) return false;
        if (top != null ? !top.equals(gtdMemos.top) : gtdMemos.top != null) return false;
        if (createdDt != null ? !createdDt.equals(gtdMemos.createdDt) : gtdMemos.createdDt != null) return false;
        if (updatedDt != null ? !updatedDt.equals(gtdMemos.updatedDt) : gtdMemos.updatedDt != null) return false;
        if (del != null ? !del.equals(gtdMemos.del) : gtdMemos.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (gid != null ? gid.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (mtitle != null ? mtitle.hashCode() : 0);
        result = 31 * result + (mcontent != null ? mcontent.hashCode() : 0);
        result = 31 * result + (fontSize != null ? fontSize.hashCode() : 0);
        result = 31 * result + (bgStyle != null ? bgStyle.hashCode() : 0);
        result = 31 * result + (star != null ? star.hashCode() : 0);
        result = 31 * result + (isEncrypt != null ? isEncrypt.hashCode() : 0);
        result = 31 * result + (top != null ? top.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        result = 31 * result + (updatedDt != null ? updatedDt.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
