package com.csming.percent.record.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.csming.percent.data.vo.Record
import com.csming.percent.repository.RecordRepository
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/3.
 */

class RecordsViewModel @Inject constructor(
        private val recordRepository: RecordRepository
) : ViewModel() {

    private var mPlanId: Int = 0
    private var mPlanTitle: String = ""

    fun setPlanId(planId: Int) {
        this.mPlanId = planId
    }

    fun getPlanId(): Int {
        return this.mPlanId
    }

    fun setPlanTitle(title: String) {
        this.mPlanTitle = title
    }

    fun getPlanTitle(): String {
        return this.mPlanTitle
    }

    fun getRecords(): LiveData<List<Record>> {
        return recordRepository.getRecords(mPlanId)
    }

}