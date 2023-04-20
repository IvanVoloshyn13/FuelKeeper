package com.example.fuelkeeper.presentation.refuelingLog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.RefuelingStatModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefuelRegisterViewModel @Inject constructor(
    //private val getRefuelRegisterStatListUseCase: GetRefuelRegisterStatListUseCase
) : ViewModel() {

    val _refuelStatListFlow =
        MutableStateFlow<Resource<List<RefuelingStatModel>>>(Resource.Loading())
    val refuelStatStateFlow = _refuelStatListFlow.asStateFlow()

//    fun getAllRefuelStatList() {
//        viewModelScope.launch {
//            val result = kotlin.runCatching {
//                getRefuelRegisterStatListUseCase.getRefuelStatList()
//            }
//            _refuelStatListFlow.value = result.fold(
//                onSuccess = { Resource.Success(it) },
//                onFailure = { Resource.Error(message = it.message, data = null) }
//            )
//        }
//    }
}