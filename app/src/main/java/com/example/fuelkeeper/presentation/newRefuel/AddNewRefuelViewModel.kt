package com.example.fuelkeeper.presentation.newRefuel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.usecase.addNewRefuelFrag.AddNewRefuelingUseCase
import com.example.fuelkeeper.domain.usecase.addNewRefuelFrag.GetLastRefuelCurrentMileageUseCase
import com.example.fuelkeeper.domain.usecase.addNewRefuelFrag.SetLocaleDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddNewRefuelViewModel @Inject constructor(
    private val addNewRefuelingUseCase: AddNewRefuelingUseCase, // must be usecase
    private val setLocaleDateUseCase: SetLocaleDateUseCase,
    private val getLastRefuelCurrentMileageUseCase: GetLastRefuelCurrentMileageUseCase

) :
    ViewModel() {


    private suspend fun insertRefuel(
        newRefuel: RefuelingModel
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val lastRefuelCurrentMileage =
                    getLastRefuelCurrentMileageUseCase.getLastRefuelCurrentMileage()
                addNewRefuelingUseCase.insertNewRefuel(newRefuel, lastRefuelCurrentMileage)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    fun addNewRefuel(refuel: RefuelingModel, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            callback(insertRefuel(refuel))
        }
    }


    fun setLocaleDate() = setLocaleDateUseCase.setLocaleDate()

}