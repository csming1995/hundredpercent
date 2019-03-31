package com.csming.percent.repository

import android.util.EventLogTags
import androidx.lifecycle.LiveData
import com.csming.percent.data.vo.Plan

/**
 * @author Created by csming on 2018/10/4.
 */
interface PlanRepository {

    fun addPlan(plan: Plan)

    fun getPlans(): LiveData<List<Plan>>

    fun findPlan(planId: Int): Plan?

    fun findPlan(title: String): Plan?

    fun getOrder(): Int

    fun updatePlanCount(planId: Int, count: Int)

    fun updatePlanFinished(planId: Int, finished: Int)

    fun updatePlan(planId: Int, title: String, description: String, color: Int)
}