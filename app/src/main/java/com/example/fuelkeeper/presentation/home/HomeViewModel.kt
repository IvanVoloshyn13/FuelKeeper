package com.example.fuelkeeper.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.models.RefuelingEntity
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.usecase.HomeFrag.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val fuelAmountLogUseCase: GetFuelAmountLogUseCase,
    val getAllRefuelListUseCase: GetAllRefuelListUseCase,
    val getFuelPaymentsSumUseCase: GetFuelPaymentsSumUseCase,
    val getSummaryDistanceUseCase: GetSummaryDistanceUseCase,
    val getLastRefuelDetailUseCase: GetLastRefuelDetailUseCase

) :
    ViewModel() {
    init {
        getAllRefuelList()

    }

    val fuelAmountLiveData = MutableLiveData<Double>()
    val fuelPaymentsLiveData = MutableLiveData<Double>()
    val summaryDistanceLiveData = MutableLiveData<Int>()
    val lastRefuelLiveData = MutableLiveData<RefuelingModel>()
    fun getAllRefuelList() {
        viewModelScope.launch {
            getAllRefuelListUseCase.getAllRefuelList()
            getFuelAmount()
            getPaymentsSum()
            getSummaryDistance()
            getLastRefuelDetails()

        }
    }

    fun getFuelAmount() {
        val result = fuelAmountLogUseCase.getFuelAmountLog()
        fuelAmountLiveData.postValue(result)

    }

    fun getPaymentsSum() {
        val result = getFuelPaymentsSumUseCase.getSumOfPayments()
        fuelPaymentsLiveData.postValue(result)
    }

    fun getSummaryDistance() {
        val result = getSummaryDistanceUseCase.getSummaryDistance()
        summaryDistanceLiveData.postValue(result)
    }

    fun getLastRefuelDetails() {
        val result = getLastRefuelDetailUseCase.getLastRefuelDetails()
        lastRefuelLiveData.postValue(result)
    }
}