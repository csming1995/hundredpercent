package com.csming.percent.repository

import androidx.lifecycle.LiveData
import com.csming.percent.data.vo.Plan

/**
 * @author Created by csming on 2018/10/4.
 */
interface PlanRepository {

    fun addPlan(plan: Plan)

    fun getPlans(): LiveData<List<Plan>>

    fun findPlan(title: String): Plan?

    fun getOrder(): Long
}