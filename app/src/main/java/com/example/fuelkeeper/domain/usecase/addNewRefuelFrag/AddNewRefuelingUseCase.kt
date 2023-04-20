package com.example.fuelkeeper.domain.usecase.addNewRefuelFrag

import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository
import javax.inject.Inject

class AddNewRefuelingUseCase @Inject constructor(val addNewRefuelRepository: AddNewRefuelRepository) {

    suspend fun insertNewRefuel(
        newRefuel: RefuelingModel,
        lastRefuelMileage: Int
    ) =
        addNewRefuelRepository.addNewRefuelLog(
            newRefuel = newRefuel,
            lastRefuelMileage = lastRefuelMileage
        )
}