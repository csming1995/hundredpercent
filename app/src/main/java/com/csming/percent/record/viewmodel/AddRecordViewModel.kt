package com.csming.percent.record.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csming.percent.common.Contacts
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
    private var mRecordId: Int = 0

    private val mPostState = MutableLiveData<Int>()

    init {
        mPostState.value = Contacts.STATE_NORMAL
    }

    fun setPlanId(planId: Int) {
        this.mPlanId = planId
    }

    fun setRecordId(recordId: Int) {
        this.mRecordId = recordId
    }

    fun getPostState(): LiveData<Int> {
        return mPostState
    }

    fun postRecord(title: String, description: String, date: Long) {
        if (TextUtils.isEmpty(title)) {
            mPostState.postValue(Contacts.STATE_POST_TITLE_NULL)
            return
        }
        val record = Record()
        record.title = title
        record.description = description
        record.planId = mPlanId
        record.date = date
        recordRepository.addRecord(record, mPostState)
    }

    fun updateRecord(title: String, description: String, date: Long) {
        if (TextUtils.isEmpty(title)) {
            mPostState.postValue(Contacts.STATE_UPDATE_TITLE_NULL)
            return
        }
        recordRepository.updateRecord(mPlanId, mRecordId, title, description, date, mPostState)
    }
}