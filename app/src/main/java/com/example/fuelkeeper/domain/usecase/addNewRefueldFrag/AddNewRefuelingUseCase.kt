package com.example.fuelkeeper.domain.usecase.addNewRefueldFrag

import com.example.fuelkeeper.domain.models.RefuelingEntity
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject

class AddNewRefuelingUseCase @Inject constructor(val refuelRepository: RefuelRepository) {

    suspend fun insertNewRefuel(newRefuel: RefuelingEntity) =
        refuelRepository.addNewRefuelLog(refuel = newRefuel)
}