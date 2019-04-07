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

    @Query("SELECT * FROM plans WHERE `id` = :planId")
    LiveData<Plan> findPlan(int planId);

    @Query("SELECT plans.count FROM plans WHERE `id` = :planId")
    int findRecordCount(int planId);

    @Query("SELECT count(id) FROM plans")
    int getCount();

    @Query("UPDATE plans SET count = :count WHERE id = :planId")
    void updatePlanCount(int planId, int count);

    @Query("UPDATE plans SET last_update = :time WHERE id = :planId")
    void updatePlanUpdateTime(int planId, long time);

    @Query("UPDATE plans SET finished = :finish WHERE id = :planId")
    void updatePlanFinished(int planId, int finish);

    @Query("UPDATE plans SET title = :title, description = :description, color = :color WHERE id = :planId")
    void updatePlan(int planId, String title, String description, int color);

    @Query("DELETE FROM plans WHERE id = :planId")
    void deleteByPlanId(int planId);
}