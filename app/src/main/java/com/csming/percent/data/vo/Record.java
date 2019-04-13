package com.csming.percent.data.vo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Created by csming on 2019/3/27.
 */
@Entity(tableName = "records")
public class Record {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "title")
    private String title; // 标题

    @ColumnInfo(name = "description")
    private String description; // 条目的详情

    @ColumnInfo(name = "order")
    private int order; // 排列顺序

    @ColumnInfo(name = "plan_id")
    private int planId; // 所属计划的id

    @ColumnInfo(name = "finish")
    private boolean finish = false;// 是否已完成

    @ColumnInfo(name = "date")
    private long date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}