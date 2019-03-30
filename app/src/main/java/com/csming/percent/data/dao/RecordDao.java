package com.csming.percent.data.dao;

import com.csming.percent.data.vo.Record;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Record record);

    @Query("SELECT * FROM records WHERE plan_id = :planId")
    LiveData<List<Record>> loadRecords(int planId);

    @Query("SELECT count(id) FROM records")
    int getCount();
}