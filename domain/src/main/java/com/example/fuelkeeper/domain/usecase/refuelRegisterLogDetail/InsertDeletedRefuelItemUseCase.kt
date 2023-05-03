package com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail

import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository

class InsertDeletedRefuelItemUseCase(private val allRefuelLogRepository: AllRefuelLogRepository) {

    suspend fun insertDeletedElement(refuel: RefuelingModel) =
        allRefuelLogRepository.insertDeletedElement(refuel)

}