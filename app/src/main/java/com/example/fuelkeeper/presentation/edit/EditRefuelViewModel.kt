package com.example.fuelkeeper.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.usecase.editFragment.GetRefuelByIdUseCase
import com.example.fuelkeeper.domain.usecase.editFragment.UpdateRefuelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRefuelViewModel @Inject constructor(
    private val getRefuelByIdUseCase: GetRefuelByIdUseCase,
    private val updateRefuelUseCase: UpdateRefuelUseCase
) : ViewModel() {

    private val _refuelSharedFlow = MutableSharedFlow<Resource<RefuelingModel>>()
    val refuelSharedFlow = _refuelSharedFlow.asSharedFlow()

    fun getRefuelById(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                getRefuelByIdUseCase.getRefuelById(id)
            }.onSuccess { _refuelSharedFlow.emit(Resource.Success(it)) }
                .onFailure {
                    _refuelSharedFlow.emit(
                        Resource.Error(
                            message = it.message,
                            data = null
                        )
                    )
                }
        }
    }

    fun updateRefuel(refuel: RefuelingModel, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            callback(updateRefuelUseCase.updateRefuel(refuel))
        }
    }


}