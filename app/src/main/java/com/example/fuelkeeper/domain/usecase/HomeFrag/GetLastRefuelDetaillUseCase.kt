package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject

class GetLastRefuelDetailUseCase @Inject constructor(val repository: RefuelRepository) {

    fun getLastRefuelDetails(): RefuelingModel {
        val currentRefuelList = repository.allRefuelList
        val lastRefuelEntity = currentRefuelList.lastOrNull()
        if (lastRefuelEntity != null) {
            return RefuelingModel(
                refuelDate = lastRefuelEntity.refuelDate,
                currentMileage = lastRefuelEntity.currentMileage,
                fuelAmount = lastRefuelEntity.fuelAmount,
                fuelPricePerLiter = lastRefuelEntity.fuelPricePerLiter,
                notes = lastRefuelEntity.notes,
                fillUp = lastRefuelEntity.fillUp
            )
        } else {
            return RefuelingModel(
                refuelDate = "0.0",
                currentMileage = 0,
                fuelAmount = 0.0,
                fuelPricePerLiter = 0.0,
                notes = "",
                fillUp = false
            )
        }
    }


}