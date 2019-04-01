package com.csming.percent.record.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csming.percent.data.vo.Record
import com.csming.percent.repository.RecordRepository
import com.csming.percent.repository.impl.RecordRepositoryImpl.Companion.STATE_POST_NORMAL
import com.csming.percent.repository.impl.RecordRepositoryImpl.Companion.STATE_POST_TITLE_NULL
import com.csming.percent.repository.impl.RecordRepositoryImpl.Companion.STATE_UPDATE_TITLE_NULL
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/3.
 */

class AddRecordViewModel @Inject constructor(
        private val recordRepository: RecordRepository
) : ViewModel() {

    private var mPlanId: Int = 0
    private var mRecordId: Int = 0

    private val mPostState = MutableLiveData<Int>()

    init {
        mPostState.value = STATE_POST_NORMAL
    }

    fun setPlanId(planId: Int) {
        this.mPlanId = planId
    }

    fun setRecordId(recordId: Int) {
        this.mRecordId = recordId
    }

    fun getPlanId(): Int {
        return this.mPlanId
    }

    fun getPostState(): LiveData<Int> {
        return mPostState
    }

    fun postRecord(title: String, description: String) {
        if (TextUtils.isEmpty(title)) {
            mPostState.value = STATE_POST_TITLE_NULL
        }
        val record = Record()
        record.title = title
        record.description = description
        record.planId = mPlanId
        recordRepository.addRecord(record, mPostState)
    }

    fun updateRecord(title: String, description: String) {
        if (TextUtils.isEmpty(title)) {
            if (TextUtils.isEmpty(title)) {
                mPostState.value = STATE_UPDATE_TITLE_NULL
            }
        }
        recordRepository.updateRecord(mRecordId, title, description, mPostState)
    }
}