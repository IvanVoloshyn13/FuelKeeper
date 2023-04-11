package com.example.fuelkeeper.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.data.models.RefuelingEntity
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.models.SummaryRefuelLogModel
import com.example.fuelkeeper.domain.usecase.HomeFrag.GetAllRefuelListUseCase
import com.example.fuelkeeper.domain.usecase.HomeFrag.GetLastRefuelDetailUseCase
import com.example.fuelkeeper.domain.usecase.HomeFrag.GetSummaryRefuelDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllRefuelListUseCase: GetAllRefuelListUseCase,
    private val getLastRefuelDetailUseCase: GetLastRefuelDetailUseCase,
    private val getSummaryRefuelDetailUseCase: GetSummaryRefuelDetailUseCase
) :
    ViewModel() {
    init {
        getAllRefuelList()
    }

    private val _summaryRefuelDetailStateFlow = MutableStateFlow(SummaryRefuelLogModel())
    val summaryRefuelDetailStateFlow = _summaryRefuelDetailStateFlow.asStateFlow()
    private val _lastRefuelStateFlow = MutableStateFlow(LastRefuelDetailsModel())
    val lastRefuelStateFlow = _lastRefuelStateFlow.asStateFlow()
    fun getAllRefuelList() {
        viewModelScope.launch {
            val allRefuelLogList =
                getAllRefuelListUseCase.getAllRefuelList() // its ArrayList <RefuelEntity>
            val refuelList = allRefuelLogList.map { mapToRefuelingModel(it) } as ArrayList
            getSummaryRefuelDetails(refuelList)
            getLastRefuelDetails(refuelList)

        }
    }


    private fun getLastRefuelDetails(refuelList: ArrayList<RefuelingModel>) {
        val result = getLastRefuelDetailUseCase.getLastRefuelDetails(refuelList)
        _lastRefuelStateFlow.value = result
    }

    private fun getSummaryRefuelDetails(refuelList: ArrayList<RefuelingModel>) {
        val result = getSummaryRefuelDetailUseCase.getSummaryRefuelDetails(refuelList)
        _summaryRefuelDetailStateFlow.value = result
    }

    private fun mapToRefuelingModel(entity: RefuelingEntity): RefuelingModel {
        return RefuelingModel(
            refuelDate = entity.refuelDate,
            currentMileage = entity.currentMileage,
            fuelAmount = entity.fuelAmount,
            fuelPricePerLiter = entity.fuelPricePerLiter,
            notes = entity.notes,
            fillUp = entity.fillUp
        )
    }
}