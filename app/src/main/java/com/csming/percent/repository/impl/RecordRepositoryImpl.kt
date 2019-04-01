package com.csming.percent.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csming.percent.common.AppExecutors
import com.csming.percent.data.dao.PlanDao
import com.csming.percent.data.dao.RecordDao
import com.csming.percent.data.vo.Plan
import com.csming.percent.data.vo.Record
import com.csming.percent.repository.RecordRepository
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
            result.postValue(STATE_POST_SUCCESS)
        }
    }

    override fun getRecords(planId: Int): LiveData<List<Record>> {
        return recordDao.loadRecords(planId)
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
            recordStateLiveData.postValue(STATE_SUCCESS)
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
            recordStateLiveData.postValue(STATE_SUCCESS)
        }
    }

    override fun updateRecord(recordId: Int, title: String, description: String, result: MutableLiveData<Int>) {
        executors.diskIO().execute {
            recordDao.updateRecord(recordId, title, description)
            result.postValue(STATE_UPDATE_SUCCESS)
        }
    }

    companion object {
        const val STATE_POST_NORMAL = 0
        const val STATE_POST_LOADING = 1
        const val STATE_POST_SUCCESS = 2
        const val STATE_POST_TITLE_NULL = 3

        const val STATE_UPDATE_NORMAL = 0
        const val STATE_UPDATE_LOADING = 1
        const val STATE_UPDATE_SUCCESS = 2
        const val STATE_UPDATE_TITLE_NULL = 3

        const val STATE_NORMAL = 0
        const val STATE_LOADING = 1
        const val STATE_SUCCESS = 2

    }
}