package com.csming.percent.record.viewmodel

import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/3.
 */

class RecordsViewModel @Inject constructor(
) : ViewModel() {

    private var mPlanId: Long = 0
    private var mPlanTitle: String = ""

    fun setPlanId(planId: Long) {
        this.mPlanId = planId
    }

    fun getPlanId(): Long {
        return this.mPlanId
    }

    fun setPlanTitle(title: String) {
        this.mPlanTitle = title
    }

    fun getPlanTitle(): String {
        return this.mPlanTitle
    }

}