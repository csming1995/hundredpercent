package com.csming.percent.repository.impl

import androidx.lifecycle.LiveData
import com.csming.percent.data.dao.PlanDao
import com.csming.percent.data.dao.RecordDao
import com.csming.percent.data.vo.Record
import com.csming.percent.repository.RecordRepository
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/4.
 */
class RecordRepositoryImpl @Inject constructor(
        private val recordDao: RecordDao,
        private val planDao: PlanDao
) : RecordRepository {
    override fun addRecord(record: Record) {
        recordDao.insert(record)
        planDao.updatePlan(record.planId, record.order + 1)
    }

    override fun getRecords(): LiveData<List<Record>> {
        return recordDao.loadRecords()
    }

    override fun getOrder(planId: Int): Int {
        val plan = planDao.findPlan(planId)
        return plan.count
    }


}