package com.lvtulife.system.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lvtulife on 2016-03-24 .
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "sys_resource_type")
public class SysResourceType implements Serializable {
    private Integer id;
    private String typeName;
    private String bewrite;
    private String typeMark;
    private String iconCls;
    private String panelTitle;
    private Integer sort;
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
    @Column(name = "type_name", nullable = false, length = 50)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "bewrite", nullable = true, length = 200)
    public String getBewrite() {
        return bewrite;
    }

    public void setBewrite(String bewrite) {
        this.bewrite = bewrite;
    }

    @Basic
    @Column(name = "type_mark", nullable = true, length = 50)
    public String getTypeMark() {
        return typeMark;
    }

    public void setTypeMark(String typeMark) {
        this.typeMark = typeMark;
    }

    @Basic
    @Column(name = "icon_cls", nullable = true, length = 200)
    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Basic
    @Column(name = "panel_title", nullable = true, length = 200)
    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    @Basic
    @Column(name = "sort", nullable = true)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

        SysResourceType that = (SysResourceType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (bewrite != null ? !bewrite.equals(that.bewrite) : that.bewrite != null) return false;
        if (typeMark != null ? !typeMark.equals(that.typeMark) : that.typeMark != null) return false;
        if (iconCls != null ? !iconCls.equals(that.iconCls) : that.iconCls != null) return false;
        if (panelTitle != null ? !panelTitle.equals(that.panelTitle) : that.panelTitle != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (del != null ? !del.equals(that.del) : that.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (bewrite != null ? bewrite.hashCode() : 0);
        result = 31 * result + (typeMark != null ? typeMark.hashCode() : 0);
        result = 31 * result + (iconCls != null ? iconCls.hashCode() : 0);
        result = 31 * result + (panelTitle != null ? panelTitle.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
