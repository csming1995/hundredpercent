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


    fun findAllPlans(): LiveData<List<Plan>> {
        return planRepository.getPlans()
    }

    /**
     * 初始创建一个Todo List的计划
     */
    fun initPlan(title: String, description: String, color: Int) {
        val plan = Plan()
        if (planRepository.getOrder() == 0) {
            plan.title = title
            plan.description = description
            plan.color = color
            val order = 0
            plan.order = order
            planRepository.addPlan(plan)
        }
    }

}