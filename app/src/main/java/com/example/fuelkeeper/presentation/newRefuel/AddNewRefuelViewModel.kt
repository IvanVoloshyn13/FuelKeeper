package com.example.fuelkeeper.presentation.newRefuel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.data.repository.RefuelRepositoryImpl
import com.example.fuelkeeper.domain.models.RefuelingEntity
import com.example.fuelkeeper.domain.models.RefuelingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddNewRefuelViewModel @Inject constructor(val refuelRepositoryImp: RefuelRepositoryImpl) :
    ViewModel() {


    private suspend fun insertRefuel(refuelEntity: RefuelingEntity): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                refuelRepositoryImp.addNewRefuelLog(refuelEntity)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    fun addNewRefuel(refuel: RefuelingModel) {
        viewModelScope.launch {
            val refuelEntity: RefuelingEntity = refuel as RefuelingEntity
        }
    }
}