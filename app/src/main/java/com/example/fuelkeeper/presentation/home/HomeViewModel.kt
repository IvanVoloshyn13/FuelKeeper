package com.example.fuelkeeper.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.models.SummaryRefuelLogModel
import com.example.fuelkeeper.domain.usecase.HomeFrag.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRefuelListUseCase: GetRefuelListUseCase,
    private val getLastRefuelDetailUseCase: GetLastRefuelDetailUseCase,
    private val getSummaryRefuelDetailUseCase: GetSummaryRefuelDetailUseCase,
    private val getAllTimeFuelAverageUseCase: GetAllTimeFuelAverageUseCase,
    private val getSummaryDrivingCostUseCase: GetSummaryDrivingCostUseCase,
    private val addNewRefuelStatUseCase: AddNewRefuelStatUseCase
) :
    ViewModel() {
    init {
        getAllRefuelList()
        getAllTimeFuelAverage()
    }

    private val _summaryRefuelDetailStateFlow =
        MutableStateFlow<Resource<SummaryRefuelLogModel>>(Resource.Loading())
    val summaryRefuelDetailStateFlow = _summaryRefuelDetailStateFlow.asStateFlow()

    private val _lastRefuelStateFlow =
        MutableStateFlow<Resource<LastRefuelDetailsModel>>(Resource.Loading())
    val lastRefuelStateFlow = _lastRefuelStateFlow.asStateFlow()

    private val _allTimeFuelAverageStateFlow =
        MutableStateFlow<Resource<Double>>(Resource.Loading())
    val allTimeFuelAverageStateFlow = _allTimeFuelAverageStateFlow.asStateFlow()

    private val _summaryDrivingCostStateFlow =
        MutableStateFlow<Resource<Double>>(Resource.Loading())
    val summaryDrivingCostStateFlow = _summaryDrivingCostStateFlow.asStateFlow()


    fun addNewRefuelStat(newRefuel: RefuelingModel, newRefuelStat: LastRefuelDetailsModel) {
        viewModelScope.launch {
            addNewRefuelStatUseCase.addNewRefuelStat(newRefuel, newRefuelStat)
        }
    }

    private fun getAllRefuelList() {
        viewModelScope.launch {
            val allRefuelLogList =
                getRefuelListUseCase.getRefuelList() // its ArrayList <RefuelEntity>
            getSummaryRefuelDetails(allRefuelLogList)
            getLastRefuelDetails(allRefuelLogList)
            getSummaryDrivingCost(allRefuelLogList)

        }
    }

    private fun getAllTimeFuelAverage() {
        viewModelScope.launch {
            try {
                val result = getAllTimeFuelAverageUseCase.getAllTimeFuelAverage()
                _allTimeFuelAverageStateFlow.value = Resource.Success(result)
            } catch (e: Exception) {
                _allTimeFuelAverageStateFlow.value =
                    errorMessage(e.message.toString())
            }
        }
    }


    private fun getLastRefuelDetails(refuelList: ArrayList<RefuelingModel>) {
        try {
            val result = getLastRefuelDetailUseCase.getLastRefuelDetails(refuelList)
            _lastRefuelStateFlow.value = Resource.Success(result)
        } catch (e: Exception) {
            _lastRefuelStateFlow.value = errorMessage(e.message.toString())
        }
    }

    private fun getSummaryRefuelDetails(refuelList: ArrayList<RefuelingModel>) {
        try {
            val result = getSummaryRefuelDetailUseCase.getSummaryRefuelDetails(refuelList)
            _summaryRefuelDetailStateFlow.value = Resource.Success(result)
        } catch (e: Exception) {
            _summaryRefuelDetailStateFlow.value =
                errorMessage(e.message.toString())
        }
    }

    private suspend fun getSummaryDrivingCost(
        refuelList: ArrayList<RefuelingModel>
    ) {
        try {
            val result = getSummaryDrivingCostUseCase.getAverageFuelPrice(refuelList)
            _summaryDrivingCostStateFlow.value = Resource.Success(result)
        } catch (e: Exception) {
            _summaryDrivingCostStateFlow.value =
                Resource.Error(message = e.message, data = null)
        }
    }

    private fun <T> errorMessage(e: String): Resource.Error<T> {
        return Resource.Error(data = null, message = e)
    }


}