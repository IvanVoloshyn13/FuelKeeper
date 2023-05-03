package com.example.fuelkeeper.domain.usecase.addNewRefuelFrag

import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository

class AddNewRefuelingUseCase (val addNewRefuelRepository: AddNewRefuelRepository) {

    suspend fun insertNewRefuel(newRefuel: com.example.fuelkeeper.domain.models.RefuelingModel) =
        addNewRefuelRepository.addNewRefuelLog(newRefuel = newRefuel)

}