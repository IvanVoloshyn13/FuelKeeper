package com.example.fuelkeeper.presentation.refuelingLog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.models.RefuelingStatModel
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.DeleteRefuelLogUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.GetAllRefuelLogUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.InsertDeletedRefuelItemUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.ReturnDeletedElementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefuelRegisterViewModel @Inject constructor(
    private val allRefuelLogUseCase: GetAllRefuelLogUseCase,
    private val deleteRefuelLogUseCase: DeleteRefuelLogUseCase,
    private val insertDeletedRefuelItemUseCase: InsertDeletedRefuelItemUseCase,
    private val returnDeletedElementUseCase: ReturnDeletedElementUseCase,
) : ViewModel() {

    init {
        getAllRefuelLogList()
    }


    lateinit var deletedRefuelItem: RefuelingModel

    private val _refuelLogDetailListFlow =
        MutableStateFlow<Resource<List<RefuelingStatModel>>>(
            Resource.Loading()
        )
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

    fun saveDeletedRefuel(itemId: Int) {
        viewModelScope.launch {
            val result = returnDeletedElementUseCase.returnDeletedElement(itemId)
            result.data.let { data ->
                if (data != null)
                    deletedRefuelItem = data
            }
        }
    }

    fun removeRefuelItem(itemId: Int) {
        viewModelScope.launch {
            deleteRefuelLogUseCase.deleteRefuelLogById(itemId)
            getAllRefuelLogList()
        }
    }


    fun revertRemoving() {
        viewModelScope.launch {
            insertDeletedRefuelItemUseCase.insertDeletedElement(deletedRefuelItem)
            getAllRefuelLogList()
        }
    }

}