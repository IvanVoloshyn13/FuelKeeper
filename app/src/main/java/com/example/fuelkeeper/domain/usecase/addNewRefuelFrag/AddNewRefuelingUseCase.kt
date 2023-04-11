package com.example.fuelkeeper.domain.usecase.addNewRefuelFrag

import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject

class AddNewRefuelingUseCase @Inject constructor(val refuelRepository: RefuelRepository) {

    suspend fun insertNewRefuel(newRefuel: RefuelingModel) =
        refuelRepository.addNewRefuelLog(refuel = newRefuel)
}