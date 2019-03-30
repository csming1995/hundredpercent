package com.csming.percent.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.csming.percent.data.vo.Plan
import com.csming.percent.repository.PlanRepository
import javax.inject.Inject

/**
 * @author Created by csming on 2018/10/3.
 */

class MainViewModel @Inject constructor(
        private val planRepository: PlanRepository
) : ViewModel() {


    fun findAllPlans(): LiveData<List<Plan>> {
        return planRepository.getPlans()
    }

}