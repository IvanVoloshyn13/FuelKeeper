package com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository


class ReturnDeletedElementUseCase  (private val allRefuelLogRepository: AllRefuelLogRepository) {

    suspend fun returnDeletedElement(itemId: Int): Resource<RefuelingModel> =
        allRefuelLogRepository.returnDeletedElement(itemId)


}