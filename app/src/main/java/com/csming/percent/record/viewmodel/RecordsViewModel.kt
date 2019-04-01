package com.csming.percent.record.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csming.percent.data.vo.Plan
import com.csming.percent.data.vo.Record
import com.csming.percent.repository.PlanRepository
import com.csming.percent.repository.RecordRepository
import com.csming.percent.repository.impl.PlanRepositoryImpl
import com.csming.percent.repository.impl.PlanRepositoryImpl.Companion.STATE_LOADING
import com.csming.percent.repository.impl.RecordRepositoryImpl
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/3.
 */
// FIXME 一些数据库操作优化至异步线程
class RecordsViewModel @Inject constructor(
        private val planRepository: PlanRepository,
        private val recordRepository: RecordRepository
) : ViewModel() {

    private var mPlanId: Int = 0

    private var planLiveData: LiveData<Plan?>? = null

    private var recordStateLiveData = MutableLiveData<Int>()
    private var deletePlanStateLiveData = MutableLiveData<Int>()

    init {
        recordStateLiveData.value = RecordRepositoryImpl.STATE_NORMAL
        deletePlanStateLiveData.value = PlanRepositoryImpl.STATE_NORMAL
    }

    fun setPlanId(planId: Int) {
        this.mPlanId = planId
        planLiveData = planRepository.findPlan(planId)
    }

    fun getPlanId(): Int {
        return this.mPlanId
    }

    fun getPlan(): LiveData<Plan?> {
        return planLiveData!!
    }

    fun getRecords(): LiveData<List<Record>> {
        return recordRepository.getRecords(mPlanId)
    }

    fun getDeletePlanState(): LiveData<Int> {
        return deletePlanStateLiveData
    }

    fun getRecordState(): LiveData<Int> {
        return recordStateLiveData
    }

    fun delete(record: Record) {
        recordStateLiveData.value = STATE_LOADING
        recordRepository.delete(record, recordStateLiveData, planLiveData!!)
    }

    fun deletePlan() {
        deletePlanStateLiveData.value = STATE_LOADING
        planRepository.deletePlan(mPlanId, deletePlanStateLiveData)
    }

    fun updateRecordFinish(record: Record, finish: Boolean) {
        if (record.isFinish == finish) return
        recordStateLiveData.value = STATE_LOADING

        recordRepository.updateRecordFinish(record, finish, recordStateLiveData, planLiveData!!)
    }

}