package com.csming.percent.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.csming.percent.data.vo.Plan
import com.csming.percent.repository.PlanRepository
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/3.
 */

class MainViewModel @Inject constructor(
        private val planRepository: PlanRepository
) : ViewModel() {

    private val plans: LiveData<List<Plan>>

    init {
        plans = planRepository.getPlans()
    }

    fun findPlans(): LiveData<List<Plan>> {
        return plans
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

}