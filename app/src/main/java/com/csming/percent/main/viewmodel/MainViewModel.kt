package com.csming.percent.main.viewmodel

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

class MainViewModel @Inject constructor(
        private val planRepository: PlanRepository,
        private val recordRepository: RecordRepository
) : ViewModel() {

    private val plans: LiveData<List<Plan>>
    private val recordsToday: LiveData<List<Record>>

    private var deletePlanStateLiveData = MutableLiveData<Int>()
    private var recordStateLiveData = MutableLiveData<Int>()

    private var planToDelete = -1

    init {
        plans = planRepository.getPlans()
        recordsToday = recordRepository.getRecordsToday()

        deletePlanStateLiveData.value = Contacts.STATE_NORMAL
        recordStateLiveData.value = Contacts.STATE_NORMAL
    }

    fun findPlans(): LiveData<List<Plan>> {
        return plans
    }

    fun findRecordsToday(): LiveData<List<Record>> {
        return recordsToday
    }

    fun getDeletePlanState(): LiveData<Int> {
        return deletePlanStateLiveData
    }

    /**
     * 初始创建一个Todo List的计划
     */
    fun initPlan(title: String, description: String, color: Int) {
        val plan = Plan()
        plan.title = title
        plan.description = description
        plan.color = color
        plan.order = 0
        planRepository.initPlan(plan)
    }

    fun setDeletePlan(planId: Int) {
        this.planToDelete = planId
    }

    fun deletePlan() {
        if (planToDelete > 0) {
            deletePlanStateLiveData.postValue(Contacts.STATE_LOADING)
            planRepository.deletePlan(planToDelete, deletePlanStateLiveData)
        }
    }

    fun updateRecordFinish(record: Record, finish: Boolean) {
        if (record.isFinish == finish) return
        recordStateLiveData.postValue(Contacts.STATE_LOADING)

        recordRepository.updateRecordFinish(record, finish, recordStateLiveData)
    }

}