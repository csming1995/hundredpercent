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

class RecordsViewModel @Inject constructor(
        private val planRepository: PlanRepository,
        private val recordRepository: RecordRepository
) : ViewModel() {

    private var mPlanId: Int = 0
    private var mPlanTitle: String = ""

    private var plan: Plan? = null

    fun setPlanId(planId: Int) {
        this.mPlanId = planId
        plan = planRepository.findPlan(planId)
    }

    fun getPlanId(): Int {
        return this.mPlanId
    }

    fun setPlanTitle(title: String) {
        this.mPlanTitle = title
    }

    fun getPlanTitle(): String {
        return this.mPlanTitle
    }

    fun getRecords(): LiveData<List<Record>> {
        return recordRepository.getRecords(mPlanId)
    }

    fun delete(record: Record) {
        recordRepository.delete(record)
        plan!!.count--
        plan!!.finished--
        planRepository.updatePlanCount(mPlanId, plan!!.count - 1)
        if (record.isFinish) {
            planRepository.updatePlanFinished(mPlanId, plan!!.finished - 1)
        }

    }

    fun updateRecordFinish(record: Record, finish: Boolean) {
        if (record.isFinish == finish) return
        recordRepository.updateRecordFinish(record, finish)
        plan!!.finished = if (finish) plan!!.finished + 1 else plan!!.finished - 1
        planRepository.updatePlanFinished(mPlanId, plan!!.finished)
    }

}