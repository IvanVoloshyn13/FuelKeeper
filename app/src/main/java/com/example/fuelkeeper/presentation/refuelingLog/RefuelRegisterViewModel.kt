package com.example.fuelkeeper.presentation.refuelingLog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.RefuelingStatModel
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.DeleteRefuelLogUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.GetAllRefuelLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefuelRegisterViewModel @Inject constructor(
    private val allRefuelLogUseCase: GetAllRefuelLogUseCase,
    private val deleteRefuelLogUseCase: DeleteRefuelLogUseCase
) : ViewModel() {

    init {
        getAllRefuelLogList()
    }

    private val _refuelLogDetailListFlow =
        MutableStateFlow<Resource<List<RefuelingStatModel>>>(Resource.Loading())
    val refuelStatStateFlow = _refuelLogDetailListFlow.asStateFlow()

    private fun getAllRefuelLogList() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                allRefuelLogUseCase.getAllRefuelLog()
            }
            _refuelLogDetailListFlow.value = result.fold(
                onSuccess = { Resource.Success(it.data) },
                onFailure = { Resource.Error(message = it.message, data = null) }
            )
        }
    }

    fun deleteRefuelItem(itemId: Int) {
        viewModelScope.launch {
            deleteRefuelLogUseCase.deleteRefuelLogById(itemId)
            getAllRefuelLogList()
        }
    }


}