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
@Table(name = "gtd_habit_remind")
public class GtdHabitRemind {
    private Integer id;
    private Integer hid;
    private String todo;
    private Date timeInterval;

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
    @Column(name = "hid", nullable = false)
    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    @Basic
    @Column(name = "todo", nullable = false, length = 255)
    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    @Basic
    @Column(name = "time_interval", nullable = false)
    public Date getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Date timeInterval) {
        this.timeInterval = timeInterval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GtdHabitRemind that = (GtdHabitRemind) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (hid != null ? !hid.equals(that.hid) : that.hid != null) return false;
        if (todo != null ? !todo.equals(that.todo) : that.todo != null) return false;
        if (timeInterval != null ? !timeInterval.equals(that.timeInterval) : that.timeInterval != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (hid != null ? hid.hashCode() : 0);
        result = 31 * result + (todo != null ? todo.hashCode() : 0);
        result = 31 * result + (timeInterval != null ? timeInterval.hashCode() : 0);
        return result;
    }
}
