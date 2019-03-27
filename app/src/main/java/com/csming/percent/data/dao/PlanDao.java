package com.csming.percent.data.dao;

import com.csming.percent.data.vo.Plan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlan(Plan plan);
}