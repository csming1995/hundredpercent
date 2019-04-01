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

    @Query("SELECT * FROM records WHERE plan_id = :planId ORDER BY id DESC, finish DESC")
    LiveData<List<Record>> loadRecords(int planId);

    @Query("SELECT count(id) FROM records")
    int getCount();

    @Query("DELETE FROM records WHERE id = :recordId")
    void deleteByRecordId(int recordId);

    @Query("UPDATE records SET finish = :finish WHERE id = :recordId")
    void updateRecordFinish(int recordId, boolean finish);

    @Query("UPDATE records SET title = :title, description = :description WHERE id = :recordId")
    void updateRecord(int recordId, String title, String description);
}