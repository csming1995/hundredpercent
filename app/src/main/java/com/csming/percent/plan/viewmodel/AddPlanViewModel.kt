package com.csming.percent.plan.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csming.percent.common.Contacts
import com.csming.percent.data.vo.Plan
import com.csming.percent.repository.PlanRepository
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/3.
 */

class AddPlanViewModel @Inject constructor(
        private val planRepository: PlanRepository
) : ViewModel() {

    private var mColor: Int? = null

    // Edit
    private var mPlanId: Int = 0
    private var planLiveData: LiveData<Plan?>? = null

    private val mPostState = MutableLiveData<Int>()

    init {
        mPostState.value = Contacts.STATE_NORMAL
    }

    fun setPlanId(planId: Int) {
        this.mPlanId = planId
        planLiveData = planRepository.findPlan(planId)
    }

    fun getPlan(): LiveData<Plan?> {
        return planLiveData!!
    }

    fun setColor(color: Int) {
        mColor = color
    }

    fun getStateLiveData(): LiveData<Int> {
        return mPostState
    }

    fun postPlan(title: String, description: String) {
        if (TextUtils.isEmpty(title)) {
            mPostState.postValue(Contacts.STATE_POST_TITLE_NULL)
            return
        }
        if (planRepository.findPlan(title) != null) {
            mPostState.postValue(Contacts.STATE_POST_PLAN_EXIST)
            return
        }
        val plan = Plan()
        plan.title = title
        plan.description = description
        plan.color = mColor!!
        planRepository.addPlan(plan, mPostState)
    }

    fun updatePlan(title: String, description: String) {
        if (TextUtils.isEmpty(title)) {
            mPostState.postValue(Contacts.STATE_UPDATE_TITLE_NULL)
            return
        }
        if (planLiveData!!.value!!.title != title && planRepository.findPlan(title) != null) {
            mPostState.postValue(Contacts.STATE_POST_PLAN_EXIST)
            return
        }
        planRepository.updatePlan(
                mPlanId,
                title,
                description,
                mColor ?: planLiveData!!.value!!.color,
                mPostState)
    }
}