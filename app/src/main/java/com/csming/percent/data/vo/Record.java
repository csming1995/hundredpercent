package com.csming.percent.data.vo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * @author Created by csming on 2019/3/27.
 */
@Entity(tableName = "records", foreignKeys = @ForeignKey(entity = Plan.class,
        parentColumns = "id",
        childColumns = "plan_id"))
public class Record {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "title")
    private String title; // 标题

    @ColumnInfo(name = "description")
    private String description; // 条目的详情

    @ColumnInfo(name = "order")
    private long order; // 排列顺序

    @ColumnInfo(name = "plan_id")
    private long planId; // 排列顺序
}