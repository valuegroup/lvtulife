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
@Table(name = "gtd_aim_reward")
public class GtdAimReward {
    private Integer id;
    private String reward;
    private String punish;
    private Double budget;
    private Byte reType;
    private Date endDt;
    private Byte isDo;

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
    @Column(name = "reward", nullable = false, length = 255)
    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    @Basic
    @Column(name = "punish", nullable = false, length = 255)
    public String getPunish() {
        return punish;
    }

    public void setPunish(String punish) {
        this.punish = punish;
    }

    @Basic
    @Column(name = "budget", nullable = false, precision = 2)
    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    @Basic
    @Column(name = "re_type", nullable = false)
    public Byte getReType() {
        return reType;
    }

    public void setReType(Byte reType) {
        this.reType = reType;
    }

    @Basic
    @Column(name = "end_dt", nullable = false)
    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    @Basic
    @Column(name = "is_do", nullable = false)
    public Byte getIsDo() {
        return isDo;
    }

    public void setIsDo(Byte isDo) {
        this.isDo = isDo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GtdAimReward that = (GtdAimReward) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reward != null ? !reward.equals(that.reward) : that.reward != null) return false;
        if (punish != null ? !punish.equals(that.punish) : that.punish != null) return false;
        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        if (reType != null ? !reType.equals(that.reType) : that.reType != null) return false;
        if (endDt != null ? !endDt.equals(that.endDt) : that.endDt != null) return false;
        if (isDo != null ? !isDo.equals(that.isDo) : that.isDo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reward != null ? reward.hashCode() : 0);
        result = 31 * result + (punish != null ? punish.hashCode() : 0);
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (reType != null ? reType.hashCode() : 0);
        result = 31 * result + (endDt != null ? endDt.hashCode() : 0);
        result = 31 * result + (isDo != null ? isDo.hashCode() : 0);
        return result;
    }
}
