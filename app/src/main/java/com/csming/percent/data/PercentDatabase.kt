package com.csming.percent.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.csming.percent.data.dao.PlanDao
import com.csming.percent.data.dao.RecordDao
import com.csming.percent.data.vo.Plan
import com.csming.percent.data.vo.Record

/**
 * @author Created by csming on 2019/3/30.
 */
@Database(entities = [Plan::class, Record::class], version = 2)
abstract class PercentDatabase : RoomDatabase() {

    abstract val planDao: PlanDao

    abstract val recordDao: RecordDao
}