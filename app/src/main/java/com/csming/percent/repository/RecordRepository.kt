package com.csming.percent.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csming.percent.data.vo.Plan
import com.csming.percent.data.vo.Record

/**
 * @author Created by csming on 2018/10/4.
 */
interface RecordRepository {

    fun addRecord(record: Record, result: MutableLiveData<Int>)

    fun getRecords(planId: Int): LiveData<List<Record>>

    fun getOrder(planId: Int): Int

    fun delete(record: Record, recordStateLiveData: MutableLiveData<Int>, planLiveData: LiveData<Plan?>) //planLiveData: ,

    fun updateRecordFinish(record: Record, finish: Boolean, recordStateLiveData: MutableLiveData<Int>, planLiveData: LiveData<Plan?>)

    fun updateRecord(recordId: Int, title: String, description: String, result: MutableLiveData<Int>)
}