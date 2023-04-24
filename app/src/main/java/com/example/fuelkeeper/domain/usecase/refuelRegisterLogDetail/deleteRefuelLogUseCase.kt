package com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail

import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository
import javax.inject.Inject

class deleteRefuelLogUseCase @Inject constructor(val allRefuelLogRepository: AllRefuelLogRepository) {
    suspend fun deleteRefuelLogById(id: Int): Resource<Boolean> {
        return Resource.Success(allRefuelLogRepository.deleteRefuelLog(id = id))
    }
}