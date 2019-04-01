package com.csming.percent.record.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csming.percent.data.vo.Plan
import com.csming.percent.data.vo.Record
import com.csming.percent.repository.PlanRepository
import com.csming.percent.repository.RecordRepository
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

    private var stateLiveData = MutableLiveData<Int>()

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

    fun delete(record: Record) {
        recordRepository.delete(record)
        planLiveData!!.value!!.count--
        planRepository.updatePlanCount(mPlanId, planLiveData!!.value!!.count)
        if (record.isFinish) {
            planLiveData!!.value!!.finished--
            planRepository.updatePlanFinished(mPlanId, planLiveData!!.value!!.finished)
        }
//        planLiveData.value = plan
    }

    fun deletePlan() {
        planRepository.deletePlan(mPlanId, stateLiveData)
    }

    fun updateRecordFinish(record: Record, finish: Boolean) {
        if (record.isFinish == finish) return
        recordRepository.updateRecordFinish(record, finish)
        planLiveData!!.value!!.finished = if (finish) planLiveData!!.value!!.finished + 1 else planLiveData!!.value!!.finished - 1
//        planLiveData.value = plan
        planRepository.updatePlanFinished(mPlanId, planLiveData!!.value!!.finished)
    }

}