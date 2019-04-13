package com.csming.percent.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csming.percent.common.AppExecutors
import com.csming.percent.common.Contacts
import com.csming.percent.data.dao.PlanDao
import com.csming.percent.data.dao.RecordDao
import com.csming.percent.data.vo.Plan
import com.csming.percent.data.vo.Record
import com.csming.percent.repository.RecordRepository
import java.util.*
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/4.
 */
class RecordRepositoryImpl @Inject constructor(
        private val recordDao: RecordDao,
        private val planDao: PlanDao,
        private val executors: AppExecutors
) : RecordRepository {

    override fun addRecord(record: Record, result: MutableLiveData<Int>) {
        executors.diskIO().execute {
            val order = getOrder(record.planId)
            record.order = order
            recordDao.insert(record)
            planDao.updatePlanCount(record.planId, record.order + 1)
            planDao.updatePlanUpdateTime(record.planId, Date().time)
            result.postValue(Contacts.STATE_SUCCESS)
        }
    }

    override fun getRecords(planId: Int): LiveData<List<Record>> {
        return recordDao.loadRecords(planId)
    }

    override fun getRecordsToday(): LiveData<List<Record>> {
        val cal = Calendar.getInstance()
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
        val now = cal.time.time
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) + 1, 0, 0, 0)
        val tomorrow = cal.time.time
        return recordDao.loadRecordsToday(now, tomorrow)
    }

    override fun getOrder(planId: Int): Int {
        return planDao.findRecordCount(planId)
    }

    override fun delete(record: Record, recordStateLiveData: MutableLiveData<Int>, planLiveData: LiveData<Plan?>) {
        executors.diskIO().execute {
            recordDao.deleteByRecordId(record.id)

            planLiveData.value!!.count--
            planDao.updatePlanCount(record.planId, planLiveData.value!!.count)
            if (record.isFinish) {
                planLiveData.value!!.finished--
                planDao.updatePlanFinished(record.planId, planLiveData.value!!.finished)
            }
            recordStateLiveData.postValue(Contacts.STATE_SUCCESS)
        }
    }

    override fun updateRecordFinish(record: Record, finish: Boolean, recordStateLiveData: MutableLiveData<Int>, planLiveData: LiveData<Plan?>) {
        executors.diskIO().execute {
            recordDao.updateRecordFinish(record.id, finish)
            if (finish) {
                planLiveData.value!!.finished = planLiveData.value!!.finished + 1
            } else {
                planLiveData.value!!.finished = planLiveData.value!!.finished - 1
            }
            planDao.updatePlanFinished(record.planId, planLiveData.value!!.finished)
            recordStateLiveData.postValue(Contacts.STATE_SUCCESS)
        }
    }

    override fun updateRecord(planId: Int, recordId: Int, title: String, description: String, date: Long, result: MutableLiveData<Int>) {
        executors.diskIO().execute {
            recordDao.updateRecord(recordId, title, description, date)
            planDao.updatePlanUpdateTime(planId, Date().time)
            result.postValue(Contacts.STATE_SUCCESS)
        }
    }
}