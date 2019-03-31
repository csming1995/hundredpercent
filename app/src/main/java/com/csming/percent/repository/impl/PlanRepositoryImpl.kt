package com.csming.percent.repository.impl

import androidx.lifecycle.LiveData
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

    override fun findPlan(planId: Int): Plan? {
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

}