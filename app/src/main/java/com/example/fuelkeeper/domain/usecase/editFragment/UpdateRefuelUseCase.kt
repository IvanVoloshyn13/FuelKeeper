package com.example.fuelkeeper.domain.usecase.editFragment

import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.EditRepository
import javax.inject.Inject

class UpdateRefuelUseCase @Inject constructor(private val editRepository: EditRepository) {

    suspend fun updateRefuel(refuel: RefuelingModel): Boolean {
        return editRepository.updateRefuel(refuel)
    }
}