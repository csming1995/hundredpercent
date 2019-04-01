package com.csming.percent.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csming.percent.data.dao.PlanDao
import com.csming.percent.data.vo.Plan
import com.csming.percent.repository.PlanRepository
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/4.
 */
class PlanRepositoryImpl @Inject constructor(
        private val planDao: PlanDao
) : PlanRepository {

    override fun addPlan(plan: Plan) {
        planDao.insert(plan)
    }

    override fun getPlans(): LiveData<List<Plan>> {
        return planDao.loadPlans()
    }

    override fun findPlan(planId: Int): LiveData<Plan?> {
        return planDao.findPlan(planId)
    }

    override fun findPlan(title: String): Plan? {
        return planDao.findPlan(title)
    }

    override fun getOrder(): Int {
        return planDao.count
    }

    override fun updatePlanCount(planId: Int, count: Int) {
        planDao.updatePlanCount(planId, count)
    }

    override fun updatePlanFinished(planId: Int, finished: Int) {
        planDao.updatePlanFinished(planId, finished)
    }

    override fun updatePlan(planId: Int, title: String, description: String, color: Int) {
        planDao.updatePlan(planId, title, description, color)
    }

    override fun deletePlan(planId: Int, result: MutableLiveData<Int>) {

        planDao.deleteByPlanId(planId)
    }

}