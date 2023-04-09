package com.example.fuelkeeper.presentation.newRefuel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.data.repository.RefuelRepositoryImpl
import com.example.fuelkeeper.domain.models.RefuelingEntity
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.usecase.addNewRefueldFrag.AddNewRefuelingUseCase
import com.example.fuelkeeper.domain.usecase.addNewRefueldFrag.SetLocaleDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddNewRefuelViewModel @Inject constructor(
    private val addNewRefuelingUseCase: AddNewRefuelingUseCase, // must be usecase
    private val setLocaleDateUseCase: SetLocaleDateUseCase
) :
    ViewModel() {


    private suspend fun insertRefuel(
        refuelEntity: RefuelingEntity
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                addNewRefuelingUseCase.insertNewRefuel(refuelEntity)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    fun addNewRefuel(refuel: RefuelingModel, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val refuelEntity: RefuelingEntity = RefuelingEntity(
                refuelDate = refuel.refuelDate,
                currentMileage = refuel.currentMileage,
                fuelAmount = refuel.fuelAmount,
                fuelPricePerLiter = refuel.fuelPricePerLiter,
                notes = refuel.notes,
                fillUp = refuel.fillUp
            )
            callback(insertRefuel(refuelEntity))
        }
    }


    fun setLocaleDate() = setLocaleDateUseCase.setLocaleDate()

}