package com.csming.percent.data.vo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author Created by csming on 2019/3/27.
 */
@Entity(indices = {@Index(value = {"first_name", "last_name"},
        unique = true)})
public class Plan {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "title")
    private String title; // 标题

    @ColumnInfo(name = "description")
    private String description; // 计划的详情

    @ColumnInfo(name = "order")
    private long order; // 排列顺序

    @ColumnInfo(name = "count")
    private int count; // 记录的条目数量

    @ColumnInfo(name = "count")
    private int finished; // 已完成的条目数量
}