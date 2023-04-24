package com.example.fuelkeeper.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.SummaryRefuelStatModel
import com.example.fuelkeeper.domain.usecase.homeFrag.GetAllTimeDrivingCostUseCase
import com.example.fuelkeeper.domain.usecase.homeFrag.GetAllTimeFuelAverageUseCase
import com.example.fuelkeeper.domain.usecase.homeFrag.GetLastRefuelDetailUseCase
import com.example.fuelkeeper.domain.usecase.homeFrag.GetSummaryRefuelStatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLastRefuelDetailUseCase: GetLastRefuelDetailUseCase,
    val getSummaryRefuelStatUseCase: GetSummaryRefuelStatUseCase,
    val getAllTimeFuelAverageUseCase: GetAllTimeFuelAverageUseCase,
    val getAllTimeDrivingCostUseCase: GetAllTimeDrivingCostUseCase
) :
    ViewModel() {
    init {
        getLastRefuelDetail()
        getSummaryRefuelStat()
        getAllTimeFuelAverage()
        getAllTimeDrivingCost()
    }

    private val _summaryRefuelDetailStateFlow =
        MutableStateFlow<Resource<SummaryRefuelStatModel>>(Resource.Loading())
    val summaryRefuelDetailStateFlow = _summaryRefuelDetailStateFlow.asStateFlow()

    private val _lastRefuelStateFlow =
        MutableStateFlow<Resource<LastRefuelDetailsModel>>(Resource.Loading())
    val lastRefuelStateFlow = _lastRefuelStateFlow.asStateFlow()

    private val _allTimeFuelAverageStateFlow =
        MutableStateFlow<Resource<Double>>(Resource.Loading())
    val allTimeFuelAverageStateFlow = _allTimeFuelAverageStateFlow.asStateFlow()

    private val _allTimeDrivingCostStateFlow =
        MutableStateFlow<Resource<Double>>(Resource.Loading())
    val allTimeDrivingCostStateFlow = _allTimeDrivingCostStateFlow.asStateFlow()

    private fun getLastRefuelDetail() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    val resource = getLastRefuelDetailUseCase.getLastRefuelDetail()
                    _lastRefuelStateFlow.value = resource
                }.onFailure { e: Throwable ->
                    _lastRefuelStateFlow.value = Resource.Error(
                        message = e.message, data = LastRefuelDetailsModel(
                            lastRefuelDistance = 0,
                            lastRefuelPayment = 0.0,
                            lastRefuelFuelAverage = 0.0
                        )
                    )
                }
            }
        }
    }

    private fun getSummaryRefuelStat() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    val resource = getSummaryRefuelStatUseCase.getSummaryRefuelStat()
                    _summaryRefuelDetailStateFlow.value = resource
                }.onFailure { e: Throwable ->
                    _summaryRefuelDetailStateFlow.value =
                        Resource.Error(message = e.message, data = null)
                }
            }

        }
    }

    private fun getAllTimeFuelAverage() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    val resource = getAllTimeFuelAverageUseCase.getAllTimeFuelAverage()
                    _allTimeFuelAverageStateFlow.value = resource
                }.onFailure { e: Throwable ->
                    _allTimeFuelAverageStateFlow.value =
                        Resource.Error(message = e.message, data = null)
                }
            }

        }
    }

    private fun getAllTimeDrivingCost() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    val resource = getAllTimeDrivingCostUseCase.getAllTimeDrivingCost()
                    _allTimeDrivingCostStateFlow.value = resource
                }.onFailure { e: Throwable ->
                    _allTimeDrivingCostStateFlow.value =
                        Resource.Error(message = e.message, data = null)
                }
            }

        }
    }

}