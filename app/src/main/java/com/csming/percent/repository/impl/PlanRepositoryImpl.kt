package com.csming.percent.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csming.percent.common.AppExecutors
import com.csming.percent.common.Contacts
import com.csming.percent.data.dao.PlanDao
import com.csming.percent.data.dao.RecordDao
import com.csming.percent.data.vo.Plan
import com.csming.percent.repository.PlanRepository
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/4.
 */
class PlanRepositoryImpl @Inject constructor(
        private val recordDao: RecordDao,
        private val planDao: PlanDao,
        private val executors: AppExecutors
) : PlanRepository {

    override fun addPlan(plan: Plan, stateLiveData: MutableLiveData<Int>) {
        executors.diskIO().execute {
            val order = getOrder()
            plan.order = order
            planDao.insert(plan)
            stateLiveData.postValue(Contacts.STATE_SUCCESS)
        }
    }

    override fun initPlan(plan: Plan) {
        executors.diskIO().execute {
            val order = getOrder()
            plan.order = order
            planDao.insert(plan)
        }
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

    override fun updatePlan(planId: Int, title: String, description: String, color: Int, stateLiveData: MutableLiveData<Int>) {
        executors.diskIO().execute {
            planDao.updatePlan(planId, title, description, color)
            stateLiveData.postValue(Contacts.STATE_SUCCESS)
        }
    }

    override fun deletePlan(planId: Int, result: MutableLiveData<Int>) {
        executors.diskIO().execute {
            planDao.deleteByPlanId(planId)
            recordDao.deleteByPlanId(planId)
            result.postValue(Contacts.STATE_SUCCESS)
        }

    }

}