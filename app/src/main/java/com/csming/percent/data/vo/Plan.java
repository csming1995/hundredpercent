package com.csming.percent.data.vo;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

/**
 * @author Created by csming on 2019/3/27.
 */
@Entity(tableName = "plans",
        indices = {@Index(value = "id", unique = true), @Index(value = "title", unique = true)})
public class Plan {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "title")
    private String title; // 标题

    @ColumnInfo(name = "description")
    private String description; // 计划的详情

    @ColumnInfo(name = "color")
    private int color; // 计划的封面颜色

    @ColumnInfo(name = "order")
    private long order = -1; // 排列顺序

    @ColumnInfo(name = "count")
    private int count = 0; // 记录的条目数量

    @ColumnInfo(name = "finished")
    private int finished = 0; // 已完成的条目数量

    public Plan() {
        super();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public int getCount() {
        return count;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}