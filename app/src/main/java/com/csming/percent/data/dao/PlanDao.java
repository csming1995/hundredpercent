package com.csming.percent.data.dao;

import com.csming.percent.data.vo.Plan;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Plan plan);

    @Query("SELECT * FROM plans")
    LiveData<List<Plan>> loadPlans();

    @Query("SELECT * FROM plans WHERE `title` = :title")
    Plan findPlan(String title);

    @Query("SELECT count(id) FROM plans")
    long getCount();
}