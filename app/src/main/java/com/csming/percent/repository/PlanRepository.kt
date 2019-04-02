package com.csming.percent.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csming.percent.data.vo.Plan

/**
 * @author Created by csming on 2018/10/4.
 */
interface PlanRepository {

    fun addPlan(plan: Plan, stateLiveData: MutableLiveData<Int>)

    fun initPlan(plan: Plan)

    fun getPlans(): LiveData<List<Plan>>

    fun findPlan(planId: Int): LiveData<Plan?>

    fun findPlan(title: String): Plan?

    fun getOrder(): Int

    fun updatePlan(planId: Int, title: String, description: String, color: Int, stateLiveData: MutableLiveData<Int>)

    fun deletePlan(planId: Int, result: MutableLiveData<Int>)
}