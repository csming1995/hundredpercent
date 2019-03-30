package com.csming.percent.data.vo;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author Created by csming on 2019/3/30.
 */
public class PlanWithRecords {

    @Embedded
    public Plan plan;

    @Relation(parentColumn = "id", entityColumn = "plan_id", entity = Record.class)
    private List<Record> records;

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
