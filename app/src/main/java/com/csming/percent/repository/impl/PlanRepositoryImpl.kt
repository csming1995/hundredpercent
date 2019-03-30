package com.csming.percent.repository.impl

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

    override fun getPlans(): List<Plan> {
        return planDao.loadPlans()
    }

    override fun findPlan(title: String): Plan? {
        return planDao.findPlan(title)
    }

    override fun getOrder(): Long {
        return planDao.count
    }

}