package com.csming.percent.repository

import androidx.lifecycle.LiveData
import com.csming.percent.data.vo.Record

/**
 * @author Created by csming on 2018/10/4.
 */
interface RecordRepository {

    fun addRecord(record: Record)

    fun getRecords(planId: Int): LiveData<List<Record>>

    fun getOrder(planId: Int): Int

    fun delete(record: Record)
}