package com.csming.percent.record.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csming.percent.common.Contacts
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

    private var planLiveData: LiveData<Plan?>? = null

    private var recordStateLiveData = MutableLiveData<Int>()

    init {
        recordStateLiveData.value = Contacts.STATE_NORMAL
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

    fun getRecordState(): LiveData<Int> {
        return recordStateLiveData
    }

    fun delete(record: Record) {
        recordStateLiveData.postValue(Contacts.STATE_LOADING)
        recordRepository.delete(record, recordStateLiveData, planLiveData!!)
    }

    fun updateRecordFinish(record: Record, finish: Boolean) {
        if (record.isFinish == finish) return
        recordStateLiveData.postValue(Contacts.STATE_LOADING)

        recordRepository.updateRecordFinish(record, finish, recordStateLiveData, planLiveData!!)
    }

}