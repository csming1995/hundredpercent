package com.csming.percent.record.viewmodel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.csming.percent.data.vo.Plan
import com.csming.percent.data.vo.Record
import com.csming.percent.repository.RecordRepository
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/3.
 */

class AddRecordViewModel @Inject constructor(
        private val recordRepository: RecordRepository
) : ViewModel() {

    private var mPlanId: Int = 0

    fun setPlanId(planId: Int) {
        this.mPlanId = planId
    }

    fun getPlanId(): Int {
        return this.mPlanId
    }

    fun postRecord(title: String, description: String): Int {
        if (TextUtils.isEmpty(title)) return STATE_POST_TITLE_NULL
        val record = Record()
        record.title = title
        record.description = description
        record.planId = mPlanId
        val order = recordRepository.getOrder(mPlanId)
        record.order = order
        recordRepository.addRecord(record)
        return STATE_POST_SUCCESS
    }

    companion object {
        const val STATE_POST_SUCCESS = 1
        const val STATE_POST_TITLE_NULL = 2
    }
}