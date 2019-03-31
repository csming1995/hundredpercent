package com.csming.percent.plan.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import com.csming.percent.data.vo.Plan
import com.csming.percent.plan.vo.ColorEntity
import com.csming.percent.repository.PlanRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/3.
 */

class AddPlanViewModel @Inject constructor(
        private val planRepository: PlanRepository
) : ViewModel() {

    private var mColor: Int? = null
    private var mPlanTitle: String? = null
    private var mPlanDescription: String? = null

    // Edit
    private var mPlanId: Int = 0
    private var plan: Plan? = null
    private var planLiveData = MutableLiveData<Plan>()


    fun setPlanId(planId: Int) {
        this.mPlanId = planId
        plan = planRepository.findPlan(planId)
        planLiveData.value = plan
    }

    fun getPlan(): LiveData<Plan> {
        return planLiveData
    }

    fun setColor(color: Int) {
        mColor = color
    }

    fun postPlan(title: String, description: String): Int {
        if (TextUtils.isEmpty(title)) return STATE_POST_TITLE_NULL
        if (planRepository.findPlan(title) != null) return STATE_POST_PLAN_EXIST
        mPlanTitle = title
        mPlanDescription = description
        val plan = Plan()
        plan.title = title
        plan.description = description
        plan.color = mColor!!
        val order = planRepository.getOrder()
        plan.order = order
        planRepository.addPlan(plan)
        return STATE_POST_SUCCESS
    }

    fun updatePlan(title: String, description: String): Int {
        if (TextUtils.isEmpty(title)) return STATE_UPDATE_TITLE_NULL
        if (plan!!.title != title && planRepository.findPlan(title) != null) return STATE_UPDATE_PLAN_EXIST
        planRepository.updatePlan(mPlanId, title, description, mColor?: plan!!.color)
        return STATE_UPDATE_SUCCESS
    }

    companion object {
        const val STATE_POST_SUCCESS = 1
        const val STATE_POST_TITLE_NULL = 2
        const val STATE_POST_PLAN_EXIST = 3

        const val STATE_UPDATE_SUCCESS = 1
        const val STATE_UPDATE_TITLE_NULL = 2
        const val STATE_UPDATE_PLAN_EXIST = 3
    }

}